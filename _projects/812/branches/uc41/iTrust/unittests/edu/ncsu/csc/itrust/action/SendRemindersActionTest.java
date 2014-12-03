package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ApptDAO;
import edu.ncsu.csc.itrust.dao.mysql.MessageDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import junit.framework.TestCase;

public class SendRemindersActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private ApptDAO apptDAO = factory.getApptDAO();
	private MessageDAO messageDAO = factory.getMessageDAO();

	private ApptBean a1; 
	private ApptBean a2;
	
	private GregorianCalendar gCal;
	private SendRemindersAction smAction;
	private long patientId = 2L;
	private long hcpId = 9000000000L;
	private long adminId = 9000000001L;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hcp0();
		gen.patient2();
		gen.appointmentType();
		
		this.smAction = new SendRemindersAction(this.factory, this.adminId);
		this.gCal = new GregorianCalendar();
		
		a1 = new ApptBean();
		a1.setDate(new Timestamp(new Date().getTime()+1000*60*60*24*5 + 1000*60));	// 5 days later
		a1.setApptType("Ultrasound");
		a1.setHcp(this.hcpId);
		a1.setPatient(this.patientId);
		
		a2 = new ApptBean();
		a2.setDate(new Timestamp(new Date().getTime()+1000*60*60*24*7 + 1000*60));	// 7 days later
		a2.setApptType("Ultrasound");
		a2.setHcp(this.hcpId);
		a2.setPatient(this.patientId);
	}
	
	public void testSendReminders() throws ITrustException, SQLException, FormValidationException {
		List<MessageBean> mbList = this.messageDAO.getMessagesFor(this.patientId);
		assertEquals(0, mbList.size());
		
		apptDAO.scheduleAppt(a1);
		apptDAO.scheduleAppt(a2);
		
		this.smAction.sendReminders(6);
		
		mbList = this.messageDAO.getMessagesFor(this.patientId);
		assertEquals(1, mbList.size());
		
		MessageBean mb = mbList.get(0);
		assertEquals(0, mb.getFrom());
		assertEquals("Reminder: upcoming appointment in 5 day(s)", mb.getSubject());
	}
	
	public void testDaysUntil() {		
		long now = new Date().getTime();
		Date d = new Date(now + 1000*60*60*24*5);
		assertEquals(this.smAction.daysUntil(d), 5);
		
		d = new Date(now + 1000*60*60*24*5);
		assertEquals(this.smAction.daysUntil(d), 5);
	}
	
}
