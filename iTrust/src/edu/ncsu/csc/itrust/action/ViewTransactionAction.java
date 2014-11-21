package edu.ncsu.csc.itrust.action;


import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.beans.ViewTransactionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ViewTransactionDAO;
import edu.ncsu.csc.itrust.exception.DBException;



public class ViewTransactionAction {
	private ViewTransactionDAO viewTransactionDao;
	
	public ViewTransactionAction(DAOFactory factory)
	{
		this.viewTransactionDao = factory.getViewTransactionDAO();
	}
	
	public List<ViewTransactionBean>  getTransactionView(ViewTransactionBean viewTransactionBean)
	{
		List<ViewTransactionBean> ret = new ArrayList<ViewTransactionBean>();
		
		try {
//			System.out.println("logguser: " + viewTransactionBean.getLoggedInRole());
//			System.out.println("second: " + viewTransactionBean.getSecondaryRole());
//			System.out.println("startdate: " + viewTransactionBean.getStartDate());
//			System.out.println("enddate: " + viewTransactionBean.getEndDate());
//			System.out.println("Transactiontype: " + viewTransactionBean.getTransactionType());
			ret = viewTransactionDao.getSelectedTransactions(viewTransactionBean);
			System.out.println("hey");
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
		
	}
	

}
