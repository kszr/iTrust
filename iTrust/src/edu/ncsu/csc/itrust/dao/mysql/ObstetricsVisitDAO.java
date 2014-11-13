package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.beans.loaders.DiagnosisBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.ObstetricsVisitLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for doing tasks related to obstetrics visits.
 * 
 * DAO stands for Database Access Object. All DAOs are intended to be reflections of the database, that is,
 * one DAO per table in the database (most of the time). For more complex sets of queries, extra DAOs are
 * added. DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor than a factory. All DAOs should be
 * accessed by DAOFactory (@see {@link DAOFactory}) and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 * 
 *  
 * 
 */
@SuppressWarnings("unused")
public class ObstetricsVisitDAO {
	private DAOFactory factory;
	private ObstetricsVisitLoader obstetricsVisitLoader = new ObstetricsVisitLoader();
	private DiagnosisBeanLoader diagnosisLoader = new DiagnosisBeanLoader(true);
	/*private PrescriptionBeanLoader prescriptionLoader = new PrescriptionBeanLoader();
	private PrescriptionReportBeanLoader prescriptionReportBeanLoader = new PrescriptionReportBeanLoader();
	private ProcedureBeanLoader procedureBeanLoader = new ProcedureBeanLoader(true);*/

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public ObstetricsVisitDAO(DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * Adds an visit and return its ID
	 * 
	 * @param ov The ObstetricsVisitBean to be added.
	 * @return A long indicating the unique ID for the obstetrics visit.
	 * @throws DBException
	 */
	public long add(ObstetricsVisitBean ov) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO obstetricsvisits (VisitDate, HCPID, PatientID, WeeksPregnant, DaysPregnant, FetalHeartRate, FundalHeightOfUterus) VALUES (?,?,?,?,?,?,?)");
			setValues(ps, ov);
			ps.executeUpdate();
			ps.close();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	private void setValues(PreparedStatement ps, ObstetricsVisitBean ov) throws SQLException {
		ps.setDate(1, new java.sql.Date(ov.getVisitDate().getTime()));
		ps.setLong(2, ov.getHcpID());
		ps.setLong(3, ov.getPatientID());
		ps.setInt(4, ov.getWeeksPregnant());
		ps.setInt(5, ov.getDaysPregnant());
		ps.setInt(6, ov.getFetalHeartRate());
		ps.setDouble(7, ov.getFundalHeightOfUterus());
	}

	/**
	 * Updates the information in a particular obstetrics visit.
	 * 
	 * @param ov The Obstetrics Visit bean representing the changes.
	 * @throws DBException
	 */
	public void update(ObstetricsVisitBean ov) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE obstetricsvisits SET VisitDate=?, HCPID=?, "
					+ "PatientID=?, WeeksPregnant=?, DaysPregnant=?, FetalHeartRate=?, FundalHeightOfUterus=? WHERE ID=?");
			setValues(ps, ov);
			ps.setLong(8, ov.getID());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns a particular obstetrics visit given an ID
	 * 
	 * @param visitID The unique ID of the obstetrics visit.
	 * @return An ObstetricsVisitBean with the specifics for that obstetrics visit.
	 * @throws DBException
	 */
	public ObstetricsVisitBean getObstetricsVisit(long visitID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("Select * From obstetricsvisits Where ID = ?");
			ps.setLong(1, visitID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				ObstetricsVisitBean result = obstetricsVisitLoader.loadSingle(rs); 
				rs.close();
				ps.close();
				return result;
			}
			else{
				rs.close();
				ps.close();
				return null;
			}
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Returns whether or not an obstetrics visit actually exists
	 * 
	 * @param ovID The ID of the obstetrics visit to be checked.
	 * @param pid The MID of the patient associated with this transaction.
	 * @return A boolean indicating its existence.
	 * @throws DBException
	 */
	public boolean checkObstetricsVisitExists(long ovID, long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM obstetricsvisits WHERE ID=? AND PatientID=?");
			ps.setLong(1, ovID);
			ps.setLong(2, pid);
			ResultSet rs = ps.executeQuery();
			boolean check = rs.next();
			rs.close();
			ps.close();
			return check;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns a list of all obstetrics visits for a given patient
	 * 
	 * @param pid The MID of the patient in question.
	 * @return A java.util.List of ObstetricsVisitBeans.
	 * @throws DBException
	 */
	public List<ObstetricsVisitBean> getAllObstetricsVisits(long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn
					.prepareStatement("SELECT * FROM obstetricsvisits WHERE PatientID=? ORDER BY VisitDate DESC");
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			List<ObstetricsVisitBean> loadlist = obstetricsVisitLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Returns a list of all obstetrics visits for a given patient
	 * 
	 * @param mid The MID of the LHCP you are looking up.
	 * @return A java.util.List of Obstetrics Visits.
	 * @throws DBException
	 */
	public List<ObstetricsVisitBean> getAllObstetricsVisitsForLHCP(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (mid == 0L) throw new SQLException("HCPID cannot be null");
			conn = factory.getConnection();
			ps = conn
					.prepareStatement("SELECT * FROM obstetricsvisits WHERE HCPID=? ORDER BY VisitDate DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<ObstetricsVisitBean> loadlist = obstetricsVisitLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}
