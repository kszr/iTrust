package edu.ncsu.csc.itrust.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.validate.RequestBioSurveillanceValidator;
/*
 * Handle after request biosurveillance analysis(Top form) from requestBiosurveilance.jsp
 */

public class RequestBioSurveillanceAnalysisAction {
	
	private OfficeVisitDAO ovDAO;
	private DAOFactory factory;
	private PatientDAO patientDAO;

	public RequestBioSurveillanceAnalysisAction() {
	
		// TODO Auto-generated constructor stub
		
	}

	/**
	 * This function is called after submit button is clicked and POST method.
	 * Check whether the input (DiagnosisCode,ZipCode,Date) format is the right format
	 * @param requestBio
	 * @return boolean
	 * @throws FormValidationException
	 * @throws ITrustException
	 */
	//return true if success otherwise false
	public boolean requestBioAnalysis(BioSurveillanceBean requestBio) throws FormValidationException, ITrustException
	{
		new RequestBioSurveillanceValidator().validate(requestBio);
		factory = DAOFactory.getProductionInstance();
		ovDAO = new OfficeVisitDAO(factory);
		//malaria case
		if(requestBio.getDiagnosisCode().contains("084"))
				{
					System.out.println("malaria");
					return true;
				}
		//influenza case
		else if (requestBio.getDiagnosisCode().contains("487"))
		{
			System.out.println("influenza");
			int numberOfCasesWeekOne =0;
			int numberOfCasesWeekTwo =0;
			Date requestDate;
			try {
				requestDate= new SimpleDateFormat("MM/dd/yyyy").parse(requestBio.getDate());
				System.out.println(requestDate.toString());
				//find two weeks before the given date
				Calendar twoWeeksBefore = new GregorianCalendar();
				twoWeeksBefore.setTime(requestDate);
				twoWeeksBefore.add(Calendar.DATE, -14);
				
				Calendar oneWeekBefore = new GregorianCalendar();
				oneWeekBefore.setTime(requestDate);
				oneWeekBefore.add(Calendar.DATE, -7);
				
				Date twoWeeksDate = twoWeeksBefore.getTime();
				Date oneWeekDate = oneWeekBefore.getTime();
				System.out.println("Begin time"+twoWeeksDate.toString());
				
				List<OfficeVisitBean> beans = ovDAO.getAllOfficeVisitsForDiagnosis("487.00");
				System.out.println("BEAN size " + beans.size());
				for(int i =0 ;i <beans.size();i++)
				{
				System.out.println("BEAN DATE " + String.valueOf(beans.get(i).getVisitDate()));
				Date officeVisitDate = beans.get(i).getVisitDate();
				long patientID = beans.get(i).getPatientID();
				patientDAO = new PatientDAO(factory);
				PatientBean patient = patientDAO.getPatient(patientID);
				String patientZip = patient.getZip().substring(0, Math.min(patient.getZip().length(), 3));
				String inputZip = requestBio.getZipCode().substring(0,Math.min(requestBio.getZipCode().length(),3));
				System.out.println("Patient Zip Code = " + patientZip);
				System.out.println("Input Zip Code = " + inputZip);
				
				
//week one is two weeks before


				
				if(twoWeeksDate.compareTo(officeVisitDate)>=0 && oneWeekDate.compareTo(officeVisitDate) <= 0 && patientZip.equals(inputZip) )
				{
					numberOfCasesWeekOne++;
				}
				if(oneWeekDate.compareTo(officeVisitDate)>=0 && requestDate.compareTo(officeVisitDate) <= 0 && patientZip.equals(inputZip))
				{
					numberOfCasesWeekTwo++;
				}
				
				}
				
				
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Date reqDate= new SimpleDateFormat("MM/dd/yyyy").parse(requestBio.getDate());
				Calendar cal = new GregorianCalendar();
				cal.setTime(reqDate);
				double secondWeekOfRequestDate = cal.get(Calendar.WEEK_OF_YEAR);
				double firstWeekOfRequestDate = secondWeekOfRequestDate-1;
			
				if(numberOfCasesWeekOne>calcThreshold(firstWeekOfRequestDate) && numberOfCasesWeekTwo > calcThreshold(secondWeekOfRequestDate))
				{
				return true;
				}
				else
					return false;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
			
			
		}
		else
			return false;
		/*
		System.out.println(requestBio.getDiagnosisCode());
		System.out.println(requestBio.getZipCode());
		System.out.println(requestBio.getDate());
		System.out.println(requestBio.getThreshold());
		*/

	}
	
	double calcThreshold(double weekNumber) {
        return 5.34 + 0.271 * weekNumber + 3.45 * Math.sin(2 * Math.PI * weekNumber / 52.0)
                + 8.41 * Math.cos(2 * Math.PI * weekNumber / 52.0);
    } 

}