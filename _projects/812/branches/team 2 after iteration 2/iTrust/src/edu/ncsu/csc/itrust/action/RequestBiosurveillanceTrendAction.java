package edu.ncsu.csc.itrust.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import edu.ncsu.csc.itrust.beans.BioSurveillanceBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.RequestBioSurveillanceValidator;

/*
 * Handle after request biosurveillance trend(Bottom form) from requestBiosurveilance.jsp
 */
public class RequestBiosurveillanceTrendAction {

	private OfficeVisitDAO ovDAO;
	private PatientDAO patientDAO;

	public RequestBiosurveillanceTrendAction(DAOFactory factory) {

		// TODO Auto-generated constructor stub
		this.ovDAO = factory.getOfficeVisitDAO();
		this.patientDAO = factory.getPatientDAO();
	}

	/**
	 * This function is called after submit button is clicked and POST method.
	 * Check whether the input (DiagnosisCode,ZipCode,Date) format is the right
	 * format
	 * 
	 * @param requestBio
	 * @return
	 * @throws FormValidationException
	 * @throws ITrustException
	 */
	// return true if success otherwise false
	public boolean requestBioTrendVerify(BioSurveillanceBean requestBio)
			throws FormValidationException, ITrustException {
		new RequestBioSurveillanceValidator().validate(requestBio);
		/*
		 * System.out.println(requestBio.getDiagnosisCode());
		 * System.out.println(requestBio.getZipCode());
		 * System.out.println(requestBio.getDate());
		 */

		return true;
	}

	public List<Integer> requestBioTrend(BioSurveillanceBean requestBio,
			int weekBefore) throws ParseException, DBException {
		// list of integer : region,state, nationwide
		List<Integer> ret = new ArrayList<Integer>();
		Date requestDate;
		requestDate = new SimpleDateFormat("MM/dd/yyyy").parse(requestBio
				.getDate());

		// find date before the given date
		Calendar WeekBefore = new GregorianCalendar();
		WeekBefore.setTime(requestDate);
		WeekBefore.add(Calendar.DATE, (-7 * (weekBefore)));

		Date startDate = WeekBefore.getTime();
	

		WeekBefore.add(Calendar.DATE, 6);
		Date endDate = WeekBefore.getTime();
		
		List<OfficeVisitBean> beans = new ArrayList<OfficeVisitBean>();
		if (requestBio.getDiagnosisCode().contains("084")) {
			beans = ovDAO.getAllOfficeVisitsForDiagnosis("084.5");
		} else if (requestBio.getDiagnosisCode().contains("487")) {
			beans = ovDAO.getAllOfficeVisitsForDiagnosis("487.00");
		}
		Integer regionalCase = 0;
		Integer stateCase = 0;
		Integer nationwideCase = 0;

		String regionZipCode = requestBio.getZipCode().substring(0, 3);
		String stateZipCode = requestBio.getZipCode().substring(0, 2);
		
		for (int i = 0; i < beans.size(); i++) {
			Date officeVisitDate = beans.get(i).getVisitDate();
			long patientID = beans.get(i).getPatientID();
			PatientBean patient = patientDAO.getPatient(patientID);
			String patientRegionZip = patient.getZip().substring(0,
					Math.min(patient.getZip().length(), 3));
			String patientStateZip = patient.getZip().substring(0,
					Math.min(patient.getZip().length(), 2));
			
			// check date in range
			if (officeVisitDate.compareTo(startDate) >= 0
					&& officeVisitDate.compareTo(endDate) <= 0) {
				if (regionZipCode.equals(patientRegionZip)) {
					regionalCase++;
				} if (stateZipCode.equals(patientStateZip)) {
					stateCase++;
				}
				nationwideCase++;
			}
		}

		ret.add(regionalCase);
		ret.add(stateCase);
		ret.add(nationwideCase);
		return ret;
	}

}
