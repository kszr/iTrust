package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.beans.PregnancyBean;

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
public class PregnancyDAO {
	private transient final DAOFactory factory;
	
	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, 
	 * which is used for obtaining SQL connections, etc.
	 */
	public PregnancyDAO(final DAOFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * Returns all of the female patient's prior pregnancies
	 * 
	 * @param pid The ID of the patient in question
	 * @throws DBException, ITrustException
	 */
	public List<PregnancyBean> getPregnancyListForPatient(final long pid) throws ITrustException, DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM priorpregnancies WHERE patientID = ? ORDER BY yearOfContraception");
			pstmt.setLong(1, pid);
			ResultSet results;
			
			results = pstmt.executeQuery();
			
			List<PregnancyBean> pBeanList = new LinkedList<PregnancyBean>();
			if(results.next()){
				PregnancyBean pBean = this.createBeanFromResultsRow(results);
				pBeanList.add(pBean);
				
				while(results.next()){
					pBean = this.createBeanFromResultsRow(results);
					pBeanList.add(pBean);
				}
				results.close();
				pstmt.close();
				return pBeanList;
			} else {
				throw new ITrustException("Empty Set");
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	private PregnancyBean createBeanFromResultsRow(final ResultSet results) throws SQLException {
		PregnancyBean pBean = new PregnancyBean();
		pBean.setPregnancyID(results.getLong("pregnancyID"));
		pBean.setPatientID(results.getLong("patientID"));
		pBean.setYearOfContraception(results.getInt("yearOfContraception"));
		pBean.setNumberOfWeeksPregnant(results.getInt("numberOfWeeksPregnant"));
		pBean.setNumberOfDaysPregnant(results.getInt("numberOfDaysPregnant"));
		pBean.setHoursInLabor(results.getDouble("hoursInLabor"));
		pBean.setDeliveryType(results.getString("deliveryType"));
		return pBean;
	}
	
	/**
	 * Deletes the pregnancy with priorPregnancyID
	 * 
	 * @param priorPregnancyID The ID of the pregnancy to delete
	 * @throws DBException
	 */
	public void deletePregnancy(final long priorPregnancyID) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
		
			pstmt = conn.prepareStatement("DELETE FROM priorpregnancies WHERE pregnancyID=?");
			pstmt.setLong(1, priorPregnancyID);
		
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
	
	/**
	 * Creates a prior pregnancy in the database
	 * 
	 * @param pBean The prior pregnancy to create
	 * @throws DBException
	 */
	public void createPregnancy(final PregnancyBean pBean) throws DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = factory.getConnection();
			
			pstmt = conn.prepareStatement("INSERT INTO priorpregnancies(patientID, yearOfContraception, numberOfWeeksPregnant, numberOfDaysPregnant, hoursInLabor, deliveryType) VALUES(?,?,?,?,?,?)");
			pstmt.setLong(1, pBean.getPatientID());
			pstmt.setInt(2, pBean.getYearOfContraception());
			pstmt.setInt(3, pBean.getNumberOfWeeksPregnant());
			pstmt.setInt(4, pBean.getNumberOfDaysPregnant());
			pstmt.setDouble(5, pBean.getHoursInLabor());
			pstmt.setString(6, pBean.getDeliveryType().toString());
			
			pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException e) {
			System.out.println(e);
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}
}
