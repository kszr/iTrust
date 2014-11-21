package edu.ncsu.csc.itrust.action;


import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.beans.ViewTransactionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ViewTransactionDAO;



public class ViewTransactionAction {
	private ViewTransactionDAO viewTransactionDao;

	
	public void VeiwTransactionAction(DAOFactory factory)
	{
		this.viewTransactionDao = factory.getViewTransactionDAO();
	}
	
	public List<TransactionBean>  getTransactionView(ViewTransactionBean viewTransactionBean)
	{
		List<TransactionBean> ret = new ArrayList<TransactionBean>();
		
		return ret;
		
	}
	

}
