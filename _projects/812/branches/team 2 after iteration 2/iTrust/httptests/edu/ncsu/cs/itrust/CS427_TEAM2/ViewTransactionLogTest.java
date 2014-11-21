package edu.ncsu.cs.itrust.CS427_TEAM2;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class ViewTransactionLogTest extends iTrustHTTPTest {
	
	/**ADDRESS*/
	public static final String ADDRESS = "http://localhost:8080/iTrust/auth/admin/viewTransactionLog.jsp";
	/**gen*/
	protected TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	/*
	 * Test whether the request viewTransactionType is only accessible to HCP user 
	 */
	public void testEnterPageLoggedInAsADMIN() throws Exception{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());

		
	}
	
	/*
	 * Test whether entering wrong start dateFormat gives error
	 */
	
	public void testEnterWrongStartDate() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());

		
		wr.getFormWithName("inputForm").setParameter("logged_in_role", "all");
		wr.getFormWithName("inputForm").setParameter("secondary_user", "all");
		wr.getFormWithName("inputForm").setParameter("startDate", "123");
		wr.getFormWithName("inputForm").setParameter("endDate", "06/02/2012");
		wr.getFormWithName("inputForm").setParameter("type", "all");


		
		wr = wr.getFormWithName("inputForm").submit();
		
		assertTrue(wr.getText().contains("Please input date in the right format (MM/DD/YYYY)"));

	}
	/*
	 * Test whether entering wrong end dateFormat gives error
	 */
	public void testEnterWrongEndDate() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());

		
		wr.getFormWithName("inputForm").setParameter("logged_in_role", "all");
		wr.getFormWithName("inputForm").setParameter("secondary_user", "all");
		wr.getFormWithName("inputForm").setParameter("startDate", "06/02/2012");
		wr.getFormWithName("inputForm").setParameter("endDate", "123");
		wr.getFormWithName("inputForm").setParameter("type", "all");


		
		wr = wr.getFormWithName("inputForm").submit();
		
		assertTrue(wr.getText().contains("Please input date in the right format (MM/DD/YYYY)"));

	}
	/*
	 * Test whether entering wrong date Sequence gives error
	 */
	public void testEnterWrongDateSequene() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());

		
		wr.getFormWithName("inputForm").setParameter("logged_in_role", "all");
		wr.getFormWithName("inputForm").setParameter("secondary_user", "all");
		wr.getFormWithName("inputForm").setParameter("startDate", "06/02/2012");
		wr.getFormWithName("inputForm").setParameter("endDate", "02/02/2012");
		wr.getFormWithName("inputForm").setParameter("type", "all");


		
		wr = wr.getFormWithName("inputForm").submit();
		
		assertTrue(wr.getText().contains("End Date must be before Start Date"));

	}
}
