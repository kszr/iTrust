package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ObstetricsVisitDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Used for Document Obstetrics Visit page (documentObstetricsVisit.jsp). This just adds an empty obstetrics visit, and
 * provides a list of obstetrics visits in case you want to edit an old obstetrics visit.
 * 
 * Very similar to {@link AddPatientAction}
 * 
 * 
 */
public class AddObstetricsVisitAction extends PatientBaseAction {
	private DAOFactory factory;
	private ObstetricsVisitDAO ovDAO;

	/**
	 * Sets up the defaults for the class
	 * @param factory
	 * @param pidString
	 *            Patient ID to be validated by the superclass, {@link PatientBaseAction}
	 * @throws ITrustException
	 */
	public AddObstetricsVisitAction(DAOFactory factory, String pidString) throws ITrustException {
		super(factory, pidString);
		this.factory = factory;
		ovDAO = factory.getObstetricsVisitDAO();
	}

	/**
	 * Adds an empty obstetrics visit
	 * 
	 * @param loggedInMID
	 *            For logging purposes
	 * @return Obstetrics visit ID (primary key) of the new obstetrics visit
	 * @throws DBException
	 */
	public long addEmptyObstetricsVisit(long loggedInMID) throws DBException {
		ObstetricsVisitBean ov = new ObstetricsVisitBean();
		ov.setHcpID(loggedInMID);
		ov.setPatientID(pid);
		long visitID = ovDAO.add(ov);
		return visitID;
	}

	/**
	 * Lists all obstetrics visits for a particular patient, regardless of who originally documented the obstetrics
	 * visit.
	 * 
	 * @return List of obstetrics visits,
	 * @throws ITrustException
	 */
	public List<ObstetricsVisitBean> getAllObstetricsVisits() throws ITrustException {
		return ovDAO.getAllObstetricsVisits(pid);
	}

	/**
	 * Returns the full name of the patient with this MID
	 * 
	 * @return name in the form of a string
	 * @throws DBException
	 * @throws ITrustException
	 */
	public String getUserName() throws DBException, ITrustException {
		return factory.getAuthDAO().getUserName(pid);
	}
}
