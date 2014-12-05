package edu.ncsu.csc.itrust.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.beans.DiagnosisStatisticsBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PatientVisitBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ICDCodesDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * 
 * Action class for ViewCauseOfDeathTrendsReport.jsp
 *
 */
@SuppressWarnings("unused")
public class ViewCauseOfDeathTrendsReportAction {
	private long loggedInMID;
	private PatientDAO patientDAO;
	private PersonnelDAO personnelDAO;
	private ArrayList<PatientBean> deceased;
	private ICDCodesDAO icdDAO;

	/**
	 * Set up defaults
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID The MID of the person viewing the office visits.
	 */
	public ViewCauseOfDeathTrendsReportAction(DAOFactory factory, long loggedInMID) {
		this.loggedInMID = loggedInMID;
		this.personnelDAO = factory.getPersonnelDAO();
		this.patientDAO = factory.getPatientDAO();
		this.icdDAO = factory.getICDCodesDAO();
		
		deceased = new ArrayList<PatientBean>();
		
	}
	
	/**
	 * Adds all the office visits for the logged in HCP to a list.
	 * 
	 * @throws ITrustException
	 */
	private void processDeceasedPatients() throws ITrustException {
		try {
			List<PatientBean> plist = patientDAO.getAllPatients();
			
			for(PatientBean pb : plist) {
				// Create a new dead bean
				PatientBean deadBean = new PatientBean();
				
				// Add patient's information to the visit
				
				// this means the patient is dead if they have a date of death
				if (pb.getDateOfDeath() != null) {
					deadBean = pb;
					System.out.println(deadBean.getFullName());
					System.out.println(deadBean.getDateOfDeath());
					System.out.println(deadBean.getDateOfDeathStr());
					deceased.add(deadBean);
				}
			}
		}
		catch (DBException dbe) {
			throw new ITrustException(dbe.getMessage());
		}
	}
	
/**
 * Get the list of deceased patients
 * 
 * @return the list of deceased patients
 * @throws DBException
 */
	public List<PatientBean> getDeceasedPatients() throws DBException {
		
		try {
			processDeceasedPatients();
		}
		catch (ITrustException ie) {
			//TODO
		}

		return deceased;
	}
	/**
	 * Returns a PersonnelBean for the logged in HCP
	 * @return PersonnelBean for the logged in HCP
	 * @throws ITrustException
	 */
	public PersonnelBean getPersonnel() throws ITrustException {
		return personnelDAO.getPersonnel(loggedInMID);
	}
	
	public List<PatientBean> getDeathStatistics(String lowerDate, String upperDate) throws FormValidationException, ITrustException {
		
		ArrayList<PatientBean> plist = new ArrayList<PatientBean>();
		getDeceasedPatients();
		
		try {
			
			if (lowerDate == null || upperDate == null)
				return null;
			
			Date lower = new SimpleDateFormat("MM/dd/yyyy").parse(lowerDate);
			Date upper = new SimpleDateFormat("MM/dd/yyyy").parse(upperDate);

			if (lower.after(upper))
				throw new FormValidationException("Start date must be before end date!");
			
			for(PatientBean pb : deceased) {
				// Create a new dead bean
				PatientBean deadBean = new PatientBean();
				if (pb.getDateOfDeath().equals(lower)||pb.getDateOfBirth().after(lower)
						||pb.getDateOfBirth().equals(upper)||pb.getDateOfBirth().before(upper))
					plist.add(deadBean);
			}
			
		} catch (ParseException e) {
			throw new FormValidationException("Enter dates in MM/dd/yyyy");
		} 
		
		return plist;
	}
}