package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ncsu.csc.itrust.beans.ViewTransactionBean;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ViewTransactionActionTest extends TestCase{
	private TestDataGenerator gen = new TestDataGenerator();

	private  ViewTransactionAction action;
	
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		action = new ViewTransactionAction(TestDAOFactory.getTestInstance());
		gen.clearAllTables();
		gen.standardData();
		
	}
	
	public void testgetDistinctLoggedUser()
	{
		ViewTransactionBean bean = new ViewTransactionBean("all","all","all","06/24/1992","06/25/2007");
		List<ViewTransactionBean> transactionList = new ArrayList<ViewTransactionBean>();
		transactionList = action.getTransactionView(bean);
		
		HashMap first_graph_map = new HashMap();
		first_graph_map = action.getDistinctLoggedUser(transactionList);
		
		assertEquals(3, first_graph_map.size());

		
	}
	public void testgetDistinctSecondaryUser()
	{
		ViewTransactionBean bean = new ViewTransactionBean("all","all","all","06/24/1992","06/25/2007");
		List<ViewTransactionBean> transactionList = new ArrayList<ViewTransactionBean>();
		transactionList = action.getTransactionView(bean);
		
		HashMap map = new HashMap();
		map = action.getDistinctSecondaryUser(transactionList);
		//System.out.println(map.size());
		assertEquals(2, map.size());
		
		
	}
	public void testgetDateCount()
	{
		ViewTransactionBean bean = new ViewTransactionBean("all","all","all","06/24/1992","06/25/2007");
		List<ViewTransactionBean> transactionList = new ArrayList<ViewTransactionBean>();
		transactionList = action.getTransactionView(bean);
		
		Map map = new HashMap();
		map = action.getDateCount(transactionList);
		
		assertEquals(4, map.size());
		
		
	}
	public void testgetTransactionTypeCount()
	{
		ViewTransactionBean bean = new ViewTransactionBean("all","all","all","06/24/1992","06/25/2007");
		List<ViewTransactionBean> transactionList = new ArrayList<ViewTransactionBean>();
		transactionList = action.getTransactionView(bean);
		
		HashMap map = new HashMap();
		map = action.getTransactionTypeCount(transactionList);
		//System.out.println(map.size());
		assertEquals(4, map.size());
		
		
	}
	

}
