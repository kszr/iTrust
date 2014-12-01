package edu.ncsu.cs.itrust.CS427_TEAM2;

import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
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
	 * Test whether the request viewTransactionType is only accessible to admin user 
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
	 * Test whether entering wrong start dateFormat gives error (for view)
	 */
	
	public void testEnterWrongStartDateForView() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());

		WebForm wf = wr.getFormWithName("inputForm");
		wf.setParameter("logged_in_role", "all");
		wf.setParameter("secondary_user", "all");
		wf.setParameter("startDate", "123");
		wf.setParameter("endDate", "06/02/2012");
		wf.setParameter("type", "all");
		
		SubmitButton[] buttons = wf.getSubmitButtons();
		wr = wf.submit(buttons[0]);	
		
		assertTrue(wr.getText().contains("Please input date in the right format (MM/DD/YYYY)"));
	}
	
	/*
	 * Test whether entering invalid start date Month gives error (for view)
	 */
	public void testEnterInvalidStartDateMonthForView() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());

		WebForm wf = wr.getFormWithName("inputForm");
		wf.setParameter("logged_in_role", "all");
		wf.setParameter("secondary_user", "all");
		wf.setParameter("startDate", "15/02/2012");
		wf.setParameter("endDate", "06/02/2012");
		wf.setParameter("type", "all");

		SubmitButton[] buttons = wf.getSubmitButtons();
		wr = wf.submit(buttons[0]);	

		assertTrue(wr.getText().contains("Invalid month. Month must be < 12 or >0"));
	}
	
	/*
	 * Test whether entering invalid start date Day gives error (for view)
	 */
	public void testEnterInvalidStartDateDayForView() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());

		WebForm wf = wr.getFormWithName("inputForm");
		wf.setParameter("logged_in_role", "all");
		wf.setParameter("secondary_user", "all");
		wf.setParameter("startDate", "12/52/2012");
		wf.setParameter("endDate", "06/02/2012");
		wf.setParameter("type", "all");

		SubmitButton[] buttons = wf.getSubmitButtons();
		wr = wf.submit(buttons[0]);	

		assertTrue(wr.getText().contains("Invalid date."));
	}
	
	/*
	 * Test whether entering wrong start dateFormat gives error (for summary)
	 */
	
	public void testEnterWrongStartDateForSummarize() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());

		WebForm wf = wr.getFormWithName("inputForm");
		wf.setParameter("logged_in_role", "all");
		wf.setParameter("secondary_user", "all");
		wf.setParameter("startDate", "123");
		wf.setParameter("endDate", "06/02/2012");
		wf.setParameter("type", "all");
		
		SubmitButton[] buttons = wf.getSubmitButtons();
		wr = wf.submit(buttons[1]);	

		assertTrue(wr.getText().contains("Please input date in the right format (MM/DD/YYYY)"));
	}
	
	/*
	 * Test whether entering invalid start date Month gives error (for Summarize)
	 */
	public void testEnterInvalidStartDateMonthForSummarize() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());

		WebForm wf = wr.getFormWithName("inputForm");
		wf.setParameter("logged_in_role", "all");
		wf.setParameter("secondary_user", "all");
		wf.setParameter("startDate", "15/02/2012");
		wf.setParameter("endDate", "06/02/2012");
		wf.setParameter("type", "all");

		SubmitButton[] buttons = wf.getSubmitButtons();
		wr = wf.submit(buttons[1]);	

		assertTrue(wr.getText().contains("Invalid month. Month must be < 12 or >0"));
	}
	
	/*
	 * Test whether entering invalid start date Day gives error (for Summarize)
	 */
	public void testEnterInvalidStartDateDayForSummarize() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());

		WebForm wf = wr.getFormWithName("inputForm");
		wf.setParameter("logged_in_role", "all");
		wf.setParameter("secondary_user", "all");
		wf.setParameter("startDate", "12/52/2012");
		wf.setParameter("endDate", "06/02/2012");
		wf.setParameter("type", "all");

		SubmitButton[] buttons = wf.getSubmitButtons();
		wr = wf.submit(buttons[1]);	

		assertTrue(wr.getText().contains("Invalid date."));
	}
	
	/*
	 * Test whether entering wrong end dateFormat gives error (for view)
	 */
	public void testEnterWrongEndDateForView() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());
		
		WebForm wf = wr.getFormWithName("inputForm");
		wf.setParameter("logged_in_role", "all");
		wf.setParameter("secondary_user", "all");
		wf.setParameter("startDate", "06/02/2012");
		wf.setParameter("endDate", "123");
		wf.setParameter("type", "all");

		SubmitButton[] buttons = wf.getSubmitButtons();
		wr = wf.submit(buttons[0]);	

		assertTrue(wr.getText().contains("Please input date in the right format (MM/DD/YYYY)"));
	}
	
	/*
	 * Test whether entering invalid end date Month gives error (for view)
	 */
	public void testEnterInvalidEndDateMonthForView() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());

		WebForm wf = wr.getFormWithName("inputForm");
		wf.setParameter("logged_in_role", "all");
		wf.setParameter("secondary_user", "all");
		wf.setParameter("startDate", "01/02/2012");
		wf.setParameter("endDate", "15/02/2012");
		wf.setParameter("type", "all");

		SubmitButton[] buttons = wf.getSubmitButtons();
		wr = wf.submit(buttons[0]);	

		assertTrue(wr.getText().contains("Invalid month. Month must be < 12 or >0"));
	}
	
	/*
	 * Test whether entering invalid end date Day gives error (for view)
	 */
	public void testEnterInvalidEndDateDayForView() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());

		WebForm wf = wr.getFormWithName("inputForm");
		wf.setParameter("logged_in_role", "all");
		wf.setParameter("secondary_user", "all");
		wf.setParameter("startDate", "01/01/2012");
		wf.setParameter("endDate", "12/52/2012");
		wf.setParameter("type", "all");

		SubmitButton[] buttons = wf.getSubmitButtons();
		wr = wf.submit(buttons[0]);	

		assertTrue(wr.getText().contains("Invalid date."));
	}
	
	/*
	 * Test whether entering wrong end dateFormat gives error (for summary)
	 */
	public void testEnterWrongEndDateForSummary() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());
		
		WebForm wf = wr.getFormWithName("inputForm");
		wf.setParameter("logged_in_role", "all");
		wf.setParameter("secondary_user", "all");
		wf.setParameter("startDate", "06/02/2012");
		wf.setParameter("endDate", "123");
		wf.setParameter("type", "all");
		
		SubmitButton[] buttons = wf.getSubmitButtons();
		wr = wf.submit(buttons[1]);	
		
		assertTrue(wr.getText().contains("Please input date in the right format (MM/DD/YYYY)"));
	}
	
	/*
	 * Test whether entering invalid end date Month gives error (for Summarize)
	 */
	public void testEnterInvalidEndDateMonthForSummarize() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());

		WebForm wf = wr.getFormWithName("inputForm");
		wf.setParameter("logged_in_role", "all");
		wf.setParameter("secondary_user", "all");
		wf.setParameter("startDate", "01/02/2012");
		wf.setParameter("endDate", "15/02/2012");
		wf.setParameter("type", "all");

		SubmitButton[] buttons = wf.getSubmitButtons();
		wr = wf.submit(buttons[1]);	

		assertTrue(wr.getText().contains("Invalid month. Month must be < 12 or >0"));
	}
	
	/*
	 * Test whether entering invalid end date Day gives error (for Summarize)
	 */
	public void testEnterInvalidEndDateDayForSummarize() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());

		WebForm wf = wr.getFormWithName("inputForm");
		wf.setParameter("logged_in_role", "all");
		wf.setParameter("secondary_user", "all");
		wf.setParameter("startDate", "12/12/2012");
		wf.setParameter("endDate", "06/52/2013");
		wf.setParameter("type", "all");

		SubmitButton[] buttons = wf.getSubmitButtons();
		wr = wf.submit(buttons[1]);	

		assertTrue(wr.getText().contains("Invalid date."));
	}
	
	
	/*
	 * Test whether entering wrong date Sequence gives error (for view)
	 */
	public void testEnterWrongDateSequeneForView() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());
		
		WebForm wf = wr.getFormWithName("inputForm");
		wf.setParameter("logged_in_role", "all");
		wf.setParameter("secondary_user", "all");
		wf.setParameter("startDate", "06/02/2012");
		wf.setParameter("endDate", "02/02/2012");
		wf.setParameter("type", "all");
		


		SubmitButton[] buttons = wf.getSubmitButtons();
		wr = wf.submit(buttons[0]);	

		
		assertTrue(wr.getText().contains("End Date must be before Start Date"));

	}
	
	/*
	 * Test whether entering wrong date Sequence gives error (for summary)
	 */
	public void testEnterWrongDateSequeneForSummary() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());
		
		WebForm wf = wr.getFormWithName("inputForm");
		wf.setParameter("logged_in_role", "all");
		wf.setParameter("secondary_user", "all");
		wf.setParameter("startDate", "06/02/2012");
		wf.setParameter("endDate", "02/02/2012");
		wf.setParameter("type", "all");
		


		SubmitButton[] buttons = wf.getSubmitButtons();
		wr = wf.submit(buttons[1]);	

		
		assertTrue(wr.getText().contains("End Date must be before Start Date"));

	}
	/*
	 * Test whether successful form validation and click View lead to view result page
	 */
	public void testEnterView() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());

		
		WebForm wf = wr.getFormWithName("inputForm");
		wf.setParameter("logged_in_role", "all");
		wf.setParameter("secondary_user", "all");
		wf.setParameter("startDate", "06/02/2012");
		wf.setParameter("endDate", "08/02/2012");
		wf.setParameter("type", "all");
		


		SubmitButton[] buttons = wf.getSubmitButtons();
		wr = wf.submit(buttons[0]);	

		
		assertTrue(wr.getText().contains("Transaction Log Table"));

	}
	
	/*
	 * Test whether successful form validation and click View lead to table page
	 */
	public void testEnterSummarize() throws Exception
	{
		WebConversation wc = login("9000000001","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000001L,0L,"");
		
		assertEquals("iTrust - Admin Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Transaction Log").click();
		assertEquals("iTrust - View Transaction Log", wr.getTitle());

		
		WebForm wf = wr.getFormWithName("inputForm");
		wf.setParameter("logged_in_role", "all");
		wf.setParameter("secondary_user", "all");
		wf.setParameter("startDate", "06/02/2012");
		wf.setParameter("endDate", "08/02/2012");
		wf.setParameter("type", "all");
		


		SubmitButton[] buttons = wf.getSubmitButtons();
		wr = wf.submit(buttons[1]);	

		
		assertTrue(wr.getText().contains("Summary"));

	}
}
