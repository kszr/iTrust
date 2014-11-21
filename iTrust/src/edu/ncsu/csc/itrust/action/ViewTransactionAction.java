package edu.ncsu.csc.itrust.action;


import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ViewTransactionDAO;



public class ViewTransactionAction {
	private ViewTransactionDAO viewTransactionDao;

	
	public void VeiwTransactionAction(DAOFactory factory)
	{
		this.viewTransactionDao = factory.getViewTransactionDAO();
	}
	
	

}
