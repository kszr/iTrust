package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.PregnancyBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.PregnancyDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

import java.util.List;

/**
 * Used in OIR.jsp and priorPregnancy.jsp to access prior pregnancies
 * @author vincent
 *
 */
public class PregnancyAction {
	private PregnancyDAO pregnancyDAO;
	private AuthDAO authDAO;
	private long loggedInMID;
	
	
	public PregnancyAction(DAOFactory factory, long loggedInMID){
		this.pregnancyDAO = factory.getPregnancyDAO();
		this.loggedInMID = loggedInMID;
		this.authDAO = factory.getAuthDAO();
	}

	/**
	 * Returns a list of prior pregnancies
	 * @param pid The ID of the female patient we are looking up
	 * @return A list of PregnancyBeans associated with the patient
	 * @throws ITrustException 
	 * @throws DBException 
	 */
	public List<PregnancyBean> getPregnancies(final long pid) throws DBException, ITrustException{
		return this.pregnancyDAO.getPregnancyListForPatient(pid);
	}
	
	/**
	 * Deletes a pregnancy from the database
	 * @param pregnancy_id The ID of the pregnancy to delete
	 * @throws DBException 
	 */
	public void deletePregnancy(final long pregnancy_id) throws DBException{
		this.pregnancyDAO.deletePregnancy(pregnancy_id);
	}
	
	/**
	 * Creates a new pregnancy in the database
	 * @param newPregnancy The pregnancy to add
	 * @throws DBException 
	 */
	public void createPregnancy(PregnancyBean newPregnancy) throws DBException{
		this.pregnancyDAO.createPregnancy(newPregnancy);
	}
}
