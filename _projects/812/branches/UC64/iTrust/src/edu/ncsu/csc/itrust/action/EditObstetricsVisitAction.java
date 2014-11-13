package edu.ncsu.csc.itrust.action;

import java.util.Arrays;
import java.util.List;

import edu.ncsu.csc.itrust.action.base.EditOfficeVisitBaseAction;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.forms.EditObstetricsVisitForm;
import edu.ncsu.csc.itrust.beans.forms.EditOfficeVisitForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.dao.mysql.ObstetricsVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.EditOfficeVisitValidator;

/**
 * Edits the office visits of a patient Used by editOfficeVisit.jsp.  This 
 * exists in two states: saved and unsaved.  If unsaved, data cannot be saved 
 * to sub actions (if this is attempted, exceptions will be raised).  Once it 
 * is saved, however, the sub actions can be saved.  
 * 
 *  
 * 
 */
public class EditObstetricsVisitAction extends EditOfficeVisitBaseAction {
	
	private EditOfficeVisitValidator validator = new EditOfficeVisitValidator();
	private PersonnelDAO personnelDAO;
	private HospitalsDAO hospitalDAO;
	private ObstetricsVisitDAO ovDAO;
	private PatientDAO patDAO;
	
	private EventLoggingAction loggingAction;
	
	private long loggedInMID;


	/**
	 * Patient id and office visit id validated by super class
	 * 
	 * @param factory The DAOFactory to be used in creating the DAOs for this action.
	 * @param loggedInMID The MID of the user who is authorizing this action.
	 * @param pidString The patient who this action is performed on.
	 * @param ovIDString The ID of the office visit in play.
	 * @throws ITrustException
	 */
	public EditObstetricsVisitAction(DAOFactory factory, long loggedInMID, String pidString, String ovIDString)
			throws ITrustException {
		super(factory, loggedInMID, pidString, ovIDString);
		pid = Long.parseLong(pidString);
		ovDAO = factory.getObstetricsVisitDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.hospitalDAO = factory.getHospitalsDAO();
		this.patDAO = factory.getPatientDAO();
		this.loggingAction = new EventLoggingAction(factory);
		
		this.loggedInMID = loggedInMID;
	}
	
	/**
	 * Create an OfficeVisitAction that is not yet associated with an actual 
	 * office visit.  When update() is called, it will be saved.  Until then, 
	 * any attempt to save health records, prescriptions, procedures, lab procedures, 
	 * immunizations, or diagnoses will raise an exception.
	 * @param factory
	 * @param loggedInMID
	 * @param pidString
	 * @throws ITrustException
	 */
	public EditObstetricsVisitAction(DAOFactory factory, long loggedInMID, String pidString)
			throws ITrustException {
		super(factory, loggedInMID, pidString);
		pid = Long.parseLong(pidString);
		ovDAO = factory.getObstetricsVisitDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.hospitalDAO = factory.getHospitalsDAO();
		this.patDAO = factory.getPatientDAO();
		

		this.loggingAction = new EventLoggingAction(factory);
		
		this.loggedInMID = loggedInMID;
	}
	
	/**
	 * Used to update the sub actions once a office visit is saved.  Until this 
	 * is called, attempting to save sub actions will raise an exception.
	 * @throws ITrustException
	 */
	private void reinitializeSubActions() throws ITrustException {
		if (isUnsaved()) {
			throw new ITrustException("Cannot initalize EditOfficeVisit sub actions.  No ovID is present.");
		}
		DAOFactory factory = getFactory();
		String pidString = ""+getPid();
		String ovIDString = ""+getOvID();
	}

	/**
	 * Returns the obstetrics visit bean for the obstetrics visit
	 * 
	 * @return the OfficeVisitBean of the office visit
	 * @throws ITrustException
	 */
	public ObstetricsVisitBean getObstetricsVisit() throws ITrustException {
		return getBean();
	}
	
	/**
	 * Combines two lists of hospitals
	 * 
	 * @param hcpsHospitals hospitals the HCP is assigned to
	 * @param allHospitals all hospitals
	 * @return the combined list
	 */
	private List<HospitalBean> combineLists(List<HospitalBean> hcpsHospitals, List<HospitalBean> allHospitals) {
		for (HospitalBean hos : allHospitals) {
			if (!hcpsHospitals.contains(hos))
				hcpsHospitals.add(hos);
		}
		return hcpsHospitals;
	}

	/**
	 * Updates the office visit with information from the form passed in.  If 
	 * the office visit has not yet been saved, calling this method will save 
	 * it as well as make the sub actions able to be saved.
	 * 
	 * @param form
	 *            information to update
	 * @return "success" or exception's message
	 * @throws FormValidationException
	 */
	public String updateInformation(EditObstetricsVisitForm form, boolean isERIncident) throws FormValidationException {
		String confirm = "";
		try {
			updateOv(form, isERIncident);
			confirm = "success";
			return confirm;
		} catch (ITrustException e) {
			
			return e.getMessage();
		}
	}

	/**
	 * Updates the office visit.
	 * 
	 * @param form form with all the information
	 * @throws DBException
	 * @throws FormValidationException
	 */
	private void updateOv(EditObstetricsVisitForm form, boolean isERIncident) throws DBException, FormValidationException, ITrustException {
		//validator.validate(form);
		ObstetricsVisitBean ov = getBean();
		ov.setVisitDateStr(form.getVisitDate());
		ov.setHcpID(Long.valueOf(form.getHcpID()));
		ov.setPatientID(Long.valueOf(form.getPatientID()));
		ov.setDaysPregnant(Integer.valueOf(form.getDaysPregnant()));
		ov.setWeeksPregnant(Integer.valueOf(form.getWeeksPregnant()));
		ov.setFetalHeartRate(Integer.valueOf(form.getFetalHeartRate()));
		ov.setFundalHeightOfUterus(Double.valueOf(form.getFundalHeightOfUterus()));
		updateBean(ov);
	}
	
	
	/**
	 * @return The OfficeVisitBean associated with this office visit, or if it 
	 * has not been saved, a default OfficeVisitBean with the HCP id and 
	 * patient id filled in.
	 * @throws DBException
	 */
	private ObstetricsVisitBean getBean() throws DBException {
		if (isUnsaved()) {
			ObstetricsVisitBean b = new ObstetricsVisitBean();
			b.setHcpID(getHcpid());
			b.setPatientID(getPid());
			return b;
		} else {
			return ovDAO.getObstetricsVisit(ovID);
		}
	}
	
	/**
	 * Update the office visit with the given data.  If the office visit has 
	 * not yet been saved, this will save it and reinitialize the sub actions.
	 * @param ov The data with which to update the office visit.
	 * @throws DBException
	 * @throws ITrustException
	 */
	private void updateBean(ObstetricsVisitBean ov) throws DBException, ITrustException {
		if (isUnsaved()) {
			// bean.getID() == -1
			ovID = ovDAO.add(ov);
			reinitializeSubActions();
		} else {
			ovDAO.update(ov);
		}
	}

}
