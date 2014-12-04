package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.ncsu.csc.itrust.EmailUtil;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ApptDAO;
import edu.ncsu.csc.itrust.dao.mysql.MessageDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.EMailValidator;
import edu.ncsu.csc.itrust.validate.MessageValidator;

/**
 * Class for SendMessage.jsp.  
 */

public class SendRemindersAction {
	private long loggedInMID;
	private EmailUtil emailer;
	private ApptDAO apptDAO;
	private PatientDAO patientDAO;
	private PersonnelDAO personnelDAO;
	private MessageDAO messageDAO;
	private EMailValidator emailVal;
	private MessageValidator messVal;

	/**
	 * Sets up defaults
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID The MID of the user sending the message.
	 */
	public SendRemindersAction(DAOFactory factory, long loggedInMID) {
		this.loggedInMID = loggedInMID;
		this.patientDAO = factory.getPatientDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.emailer = new EmailUtil(factory);
		this.messageDAO = factory.getMessageDAO();
		this.emailVal = new EMailValidator();
		this.messVal = new MessageValidator();
		this.apptDAO = factory.getApptDAO();
	}
	
	/**
	 * Sends a System Reminder to patients with appointments within daysInAdvance days.
	 * 
	 * @param daysInAdvance
	 * @throws ITrustException
	 * @throws SQLException
	 * @throws FormValidationException
	 */
	public void sendReminders(int daysInAdvance) throws ITrustException, SQLException, FormValidationException {
		/* [S1] A row for showing the reminder message's subject, the name of the recipient, and 
		 * the timestamp is then visible in the system's reminder message outbox. A bolded row 
		 * for showing the message subject, the name of the sender, and the timestamp is then 
		 * visible in the patient�s message inbox, and a fake email (see the notes in the end 
		 * of this page) is sent to the patient that indicates that he/she has a new message 
		 * from the "System Reminder". For a reminder message for an upcoming appointment, the 
		 * message sender shall be "System Reminder". The message subject shall be "Reminder: 
		 * upcoming appointment in N day(s)" where N is the number of days between the upcoming 
		 * appointment date and the current date. The text of the message shall be "You have an 
		 * appointment on <TIME>, <DATE> with Dr. <DOCTOR>" where <TIME> is the appointment start 
		 * time, <DATE> is the appointment date, and <DOCTOR> is the name of the LHCP in the 
		 * appointment.  The event is logged [UC5, S44].
		 */
		List<ApptBean> futureAppts = this.apptDAO.getFutureAppts(daysInAdvance);
		
		for(ApptBean appt : futureAppts) {
			MessageBean message = new MessageBean();
			
			String hcpName = this.personnelDAO.getName(appt.getHcp());
			String subject = String.format("Reminder: upcoming appointment in %d day(s)", this.daysUntil(appt.getDate()));
			DateFormat dateFormatter = new SimpleDateFormat("HH:mm, MM-dd-yyyy");
			String body = String.format("You have an appointment on %s with Dr. %s.", dateFormatter.format(appt.getDate()), hcpName);
			System.out.println(body);
			message.setBody(body);
			message.setSubject(subject);
			message.setRead(0);
			message.setFrom(0);
			message.setTo(appt.getPatient());
			
			messVal.validate(message);
			messageDAO.addMessage(message);
			
			Email email = new Email();
			email.setSubject("A new message from System Reminder");
			email.setFrom("noreply@itrust.com");
			
			PatientBean receiver = patientDAO.getPatient(appt.getPatient());
			email.setToList(Arrays.asList(receiver.getEmail()));
			
			email.setBody("You have received a new message from System Reminder in iTrust. To view it, go to \"http://localhost:8080/iTrust/auth/patient/messageInbox.jsp\" and log in to iTrust using your username and password.");
			
			emailer.sendEmail(email);
		}
	}
	
	public long daysUntil(Date date) {
		long diffInMilles = date.getTime() - (new Date()).getTime();
		return TimeUnit.DAYS.convert(diffInMilles, TimeUnit.MILLISECONDS);
	}
}
