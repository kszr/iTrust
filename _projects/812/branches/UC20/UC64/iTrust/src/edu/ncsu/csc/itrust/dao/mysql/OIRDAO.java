package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.beans.OIRBean;


/**
 * DAO stands for Database Access Object. 
 * All DAOs are intended to be reflections of the database, that is,
 * one DAO per table in the database (most of the time). 
 * For more complex sets of queries, extra DAOs are
 * added. DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter 
 * to the constructor than a factory. All DAOs should be
 * accessed by DAOFactory (@see {@link DAOFactory}) 
 * and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 */
public class OIRDAO {
	private transient final DAOFactory factory;
	
	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, 
	 * which is used for obtaining SQL connections, etc.
	 */
	public OIRDAO(final DAOFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * Returns a list of obstetrics initialization records for the female patient
	 * @param pid A long for the pid of the patient we are looking up.
	 * @throws ITrustException
	 * @throws DBException
	 */
	public List<OIRBean> getOIRListForPatient(final long pid) throws ITrustException, DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM obstetricsinitializationrecords WHERE patientID = ? ORDER BY creationDate DESC");
			pstmt.setLong(1, pid);
			ResultSet results;
			
			results = pstmt.executeQuery();
			
			if(results.next()) {
				List<OIRBean> oirBeanList = new LinkedList<OIRBean>();
				OIRBean newBean = this.createBeanFromResultRow(results);
				oirBeanList.add(newBean);

				while(results.next()){
					newBean = this.createBeanFromResultRow(results);
					oirBeanList.add(newBean);
				}
				results.close();
				pstmt.close();
				return oirBeanList;
			} else {
				throw new ITrustException("User does not exist");
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	private OIRBean createBeanFromResultRow(final ResultSet results) throws SQLException{
		OIRBean newBean = new OIRBean();
		newBean.setPatientID(results.getLong("patientID"));
		newBean.setRecordID(results.getLong("recordID"));
		newBean.setCreationDate(results.getDate("creationDate"));
		newBean.setLMP(results.getDate("lastMensturalPeriod"));
		return newBean;
	}

	/**
	 * Returns an obstetrics initialization record
	 * @param OIR_id A long for the ID of the OIR we want
	 * @return The relevant OIRBean
	 * @throws ITrustException, DBException 
	 */
	public OIRBean getOIR(final long OIR_id) throws ITrustException, DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM obstetricsinitializationrecords WHERE recordID = ?");
			pstmt.setLong(1, OIR_id);
			ResultSet results;
			
			results = pstmt.executeQuery();
			
			if(results.next()) {
				OIRBean newBean = this.createBeanFromResultRow(results);

				results.close();
				pstmt.close();
				return newBean;
			} else {
				throw new ITrustException("User does not exist");
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	/**
	 * Creates a new obstetrics initialization record in the database
	 * @param newOIR The OIR to add
	 * @throws DBException
	 */
	public void createOIR(final OIRBean newOIR) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = factory.getConnection();
			
			pstmt = conn.prepareStatement("INSERT INTO obstetricsinitializationrecords(patientID, creationDate, lastMensturalPeriod) VALUES(?,?,?)");
			pstmt.setString(1, Long.valueOf(newOIR.getPatientID()).toString());
			pstmt.setDate(2, new java.sql.Date(newOIR.getCreationDate().getTime()));
			pstmt.setDate(3, new java.sql.Date(newOIR.getLMP().getTime()));
			pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
}
