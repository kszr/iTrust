package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.OIRBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.OIRDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

import java.util.List;

/**
 * Used in OIRList.jsp and OIR.jsp to access obstetrics initialization records
 * @author vincent
 *
 */
public class OIRAction {
	private OIRDAO OIRDAO;
	private AuthDAO authDAO;
	private long loggedInMID;
	
	public OIRAction(DAOFactory factory, long loggedInMID){
		this.OIRDAO = factory.getOIRDAO();
		this.loggedInMID = loggedInMID;
		this.authDAO = factory.getAuthDAO();
	}
	
	
	/**
	 * Gets a list of obstetrics initialization records
	 * @param pid The ID of the patient whose records we will get
	 * @return A list of beans, each representing an obstetrics initialization record
	 * @throws ITrustException 
	 * @throws DBException 
	 */
	public List<OIRBean> getOIRList(final long pid) throws DBException, ITrustException{
		return this.OIRDAO.getOIRListForPatient(pid);
	}
	
	/**
	 * Gets an obstetrics initialization record
	 * @param OIR_ID The ID of the record in question
	 * @return A bean of the record
	 * @throws ITrustException 
	 * @throws DBException 
	 */
	public OIRBean getOIR(final long OIR_ID) throws DBException, ITrustException{
		return this.OIRDAO.getOIR(OIR_ID);
	}
	
	/**
	 * Creates a new obstetrics initialization record
	 * @param newOIR The new record
	 * @throws DBException 
	 */
	public void createOIR(OIRBean newOIR) throws DBException{
		this.OIRDAO.createOIR(newOIR);
	}
	
}
