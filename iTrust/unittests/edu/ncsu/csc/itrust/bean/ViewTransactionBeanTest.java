package edu.ncsu.csc.itrust.bean;

import java.sql.Timestamp;

import edu.ncsu.csc.itrust.beans.ViewTransactionBean;
import junit.framework.TestCase;

/**
 * Test for ViewTransactionBean
 */
public class ViewTransactionBeanTest extends TestCase {

	/**
	 * Test for a bean.
	 */
	public  void testBeanWithInput()
	{
		ViewTransactionBean bean = new ViewTransactionBean("hcp", "patient", "PRESCRIPTION_REPORT_VIEW", "06/24/2007", "06/25/2007");
		
		assertTrue(bean.getLoggedInRole().equals("hcp"));
		assertTrue(bean.getSecondaryRole().equals("patient"));
		assertTrue(bean.getStartDate().equals("06/24/2007"));
		assertTrue(bean.getEndDate().equals("06/25/2007"));
		assertTrue(bean.getTransactionType().equals("PRESCRIPTION_REPORT_VIEW"));

		bean.setLoggedInRole("patient");
		bean.setSecondaryRole("hcp");
		bean.setStartDate("02/18/1992");
		bean.setEndDate("06/25/2014");
		bean.setTransactionType("DEATH_TRENDS_VIEW");
		
		assertTrue(bean.getLoggedInRole().equals("patient"));
		assertTrue(bean.getSecondaryRole().equals("hcp"));
		assertTrue(bean.getStartDate().equals("02/18/1992"));
		assertTrue(bean.getEndDate().equals("06/25/2014"));
		assertTrue(bean.getTransactionType().equals("DEATH_TRENDS_VIEW"));
	}
	
	public  void testBeanWithQuery()
	{
		long ID = 9439;
		Timestamp time = new Timestamp(2007, 06, 24, 0, 0, 0, 0);
		ViewTransactionBean bean = new ViewTransactionBean("hcp", ID, "PRESCRIPTION_REPORT_VIEW", time, "testcase additional info");
		
		assertTrue(bean.getRole().equals("hcp"));
		assertTrue(bean.getTransactionID() == ID);
		assertTrue(bean.getTransactionType().equals("PRESCRIPTION_REPORT_VIEW"));
		assertTrue(bean.getTimestamp().equals(time));
		assertTrue(bean.getAdditionalInfo().equals("testcase additional info"));

	}
	
	public  void testBeanWithOutput()
	{
		Timestamp time = new Timestamp(2007, 06, 24, 0, 0, 0, 0);
		ViewTransactionBean bean = new ViewTransactionBean("hcp", "patient", "PRESCRIPTION_REPORT_VIEW", time, "testcase additional info");
		
		assertTrue(bean.getLoggedInRole().equals("hcp"));
		assertTrue(bean.getSecondaryRole().equals("patient"));
		assertTrue(bean.getTransactionType().equals("PRESCRIPTION_REPORT_VIEW"));
		assertTrue(bean.getTimestamp().equals(time));
		assertTrue(bean.getAdditionalInfo().equals("testcase additional info"));

	}
}
