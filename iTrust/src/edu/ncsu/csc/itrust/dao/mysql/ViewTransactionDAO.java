package edu.ncsu.csc.itrust.dao.mysql;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.beans.ViewTransactionBean;
import edu.ncsu.csc.itrust.beans.loaders.TransactionBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Used for the logging mechanism.
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
public class ViewTransactionDAO {
	private DAOFactory factory;
	private TransactionBeanLoader loader = new TransactionBeanLoader();
	
	public ViewTransactionDAO(DAOFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * Returns the selected transaction log
	 * 
	 * @return
	 * @throws DBException
	 */
	public List<ViewTransactionBean> getSelectedTransactions(ViewTransactionBean input) throws DBException {		
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		
		try {
			conn = factory.getConnection();
			System.out.println("hey");
			ps = conn.prepareStatement(//"SELECT * FROM transactionlog ORDER BY timeLogged DESC");
//					"CREATE TABLE IF NOT EXISTS BLAH1 AS ("
					"SELECT users.role, transactionlog.transactionID, transactionlog.transactionCode, transactionlog.timeLogged, transactionlog.addedInfo "
					+ "FROM transactionlog "
					+ "INNER JOIN users "
					+ "ON transactionlog.loggedInMID=users.MID "
					+ "AND users.Role=\'" + input.getLoggedInRole().toLowerCase() + "\' "
					+ "ORDER BY timeLogged DESC;");
					
			ResultSet rs = ps.executeQuery();
			List<ViewTransactionBean> Primaryloadlist = loader.loadViewList(rs);
//					+ "Select * From BLAH1; "
//					+ "DROP TEMPORARY TABLE BLAH1 ");
					
//					+ "CREATE TEMPORARY TABLE IF NOT EXISTS BLAH2 AS ("
//					+ "SELECT transactionlog.* "
//					+ "FROM transactionlog "
//					+ "INNER JOIN users "
//					+ "ON transactionlog.secondaryMID=users.MID "
//					+ "AND users.Role=\'" + input.getSecondaryRole().toLowerCase() + "\'); "
//					
//					+ "SELECT BLAH1.* "
//					+ "FROM BLAH1, BLAH2 "
//					+ "WHERE BLAH1.transactionID=BLAH2.transactionID "
//					+ "AND DATE(BLAH1.timeLogged) >= \'" + input.getStartDate() + "\' "
//					+ "AND DATE(BLAH1.timeLogged) <= \'" + input.getEndDate() + "\' "
//					+ "AND BLAH1.transactionCode = \'" 
//					+ TransactionType.getCodeFromTransactionTypeName(input.getTransactionType()) + "\' "
//					+ "ORDER BY timeLogged DESC, transactionID DESC; "
//					
//					+ "DROP TEMPORARY TABLE BLAH1, BLAH2;");
			
			System.out.println("ps: " + ps.toString());
			
			
			System.out.println("went to the end 1");
			ps1 = conn.prepareStatement(
					"SELECT users.role, transactionlog.transactionID, transactionlog.transactionCode, transactionlog.timeLogged, transactionlog.addedInfo "
					+ "FROM transactionlog "
					+ "INNER JOIN users "
					+ "ON transactionlog.secondaryMID=users.MID "
					+ "AND users.Role=\'" + input.getSecondaryRole().toLowerCase() + "\' "
					+ "ORDER BY timeLogged DESC;");	
			
			ResultSet rs1 = ps1.executeQuery();
			
			List<ViewTransactionBean> Secondaryloadlist = loader.loadViewList(rs1);
			System.out.println("went to the end 2 " + Primaryloadlist.toString());
			
			for(int i=0; i < Primaryloadlist.size()-1; i++)
			{
				System.out.println(Primaryloadlist.get(i).getRole() +  " " + Primaryloadlist.get(i).getTransactionID());
			}
			System.out.println("went to the end 3 " + Secondaryloadlist.toString());
			for(int i=0; i < Secondaryloadlist.size(); i++)
			{
				System.out.println(Secondaryloadlist.get(i).getRole() +  " " + Secondaryloadlist.get(i).getTransactionID());
			}
			
			rs.close();
			rs1.close();
			ps.close();
			ps1.close();
			System.out.println("went to the end 3");
			
			List<ViewTransactionBean> newList = new ArrayList<ViewTransactionBean>();

			 for(int i = 0; i < Primaryloadlist.size(); i++)
			 {
				 for(int j = 0; j <Secondaryloadlist.size(); j++)
				 {
					  if(Primaryloadlist.get(i).getTransactionID() == Secondaryloadlist.get(j).getTransactionID()
							  && withinDate(Primaryloadlist.get(i), input)
							  && input.getTransactionType().equals(Primaryloadlist.get(i).getTransactionType()))
					  {
						  newList.add(Primaryloadlist.get(i));
					  }
				 }
			 }
			 
			 
			 for(int i = 0; i < newList.size(); i++)
			 {
				 for(int j = 0; j < Primaryloadlist.size(); j++)
				 {
					 if(newList.get(i).getTransactionID() == Primaryloadlist.get(j).getTransactionID())
					 {
						 newList.get(i).setLoggedInRole(Primaryloadlist.get(j).getRole());
					 }
				 }
			 }
			 
			 for(int i = 0; i < newList.size(); i++)
			 {
				 for(int j = 0; j < Secondaryloadlist.size(); j++)
				 {
					 if(newList.get(i).getTransactionID() == Secondaryloadlist.get(j).getTransactionID())
					 {
						 newList.get(i).setSecondaryRole(Secondaryloadlist.get(j).getRole());
					 }
				 }
			 }
			 
			 
			 System.out.println("added" + newList);
			for(int i=0; i < newList.size(); i++)
			{
				System.out.println(newList.get(i).getLoggedInRole() + " " + newList.get(i).getSecondaryRole() + " " +  newList.get(i).getTransactionID());
			}
			
			return newList;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	
	public boolean withinDate(ViewTransactionBean Bean, ViewTransactionBean input){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try {
			Date parsedStartDate = dateFormat.parse(input.getStartDate());
			Date parsedEndDate = dateFormat.parse(input.getEndDate());
			Timestamp startTimestamp = new Timestamp(parsedStartDate.getTime());
			Timestamp endTimestamp = new Timestamp(parsedEndDate.getTime());
			endTimestamp.setHours(23);
			endTimestamp.setMinutes(59);
			endTimestamp.setSeconds(59);
			endTimestamp.setNanos(999999999);
			
			System.out.println(endTimestamp);
			
			if(Bean.getTimestamp().compareTo(startTimestamp) >= 0 && Bean.getTimestamp().compareTo(endTimestamp) <= 0)
			{
				return true;
			}
			
			return false;
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	

}
