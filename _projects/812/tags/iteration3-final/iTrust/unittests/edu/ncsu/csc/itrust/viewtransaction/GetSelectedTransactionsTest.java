package edu.ncsu.csc.itrust.viewtransaction;

import java.sql.Timestamp;
import java.util.List;

import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.beans.ViewTransactionBean;
import edu.ncsu.csc.itrust.dao.mysql.ViewTransactionDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class GetSelectedTransactionsTest extends TestCase{
	private ViewTransactionDAO tranDAO = TestDAOFactory.getTestInstance().getViewTransactionDAO();
	private TestDataGenerator gen;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		
	}
	
	
	public void testGetSelectedTransactions() throws Exception {
		ViewTransactionBean input = new ViewTransactionBean("hcp", "patient", "PRESCRIPTION_REPORT_VIEW", "06/24/2007", "06/25/2007");
		List<ViewTransactionBean> transactions = tranDAO.getSelectedTransactions(input);
		
		assertEquals(6, transactions.size());
		assertEquals("hcp", transactions.get(0).getLoggedInRole());
		assertEquals("patient", transactions.get(0).getSecondaryRole());
		assertEquals("PRESCRIPTION_REPORT_VIEW", transactions.get(0).getTransactionType());
		assertEquals("Viewed patient records", transactions.get(0).getAdditionalInfo());
		
	}
	
	public void testGetSelectedTransactionsCheckForNULL() throws Exception {
		ViewTransactionBean input = new ViewTransactionBean("patient", "all", "all", "06/24/1992", "06/25/2014");
		List<ViewTransactionBean> transactions = tranDAO.getSelectedTransactions(input);
		
		assertEquals(4, transactions.size());
		assertEquals("patient", transactions.get(3).getLoggedInRole());
		assertEquals(null, transactions.get(3).getSecondaryRole());
		assertEquals("DEMOGRAPHICS_EDIT", transactions.get(3).getTransactionType());
		assertEquals("", transactions.get(3).getAdditionalInfo());
	}
	
	public void testGetSelectedTransactionsCheckForAllCases() throws Exception {
		ViewTransactionBean input = new ViewTransactionBean("all", "all", "all", "06/24/2007", "06/25/2007");
		List<ViewTransactionBean> transactions = tranDAO.getSelectedTransactions(input);
		
		assertEquals(8, transactions.size());
		assertEquals("hcp", transactions.get(0).getLoggedInRole());
		assertEquals("patient", transactions.get(0).getSecondaryRole());
		assertEquals("PRESCRIPTION_REPORT_VIEW", transactions.get(0).getTransactionType());
		assertEquals("Viewed patient records", transactions.get(0).getAdditionalInfo());
		
	}
}
