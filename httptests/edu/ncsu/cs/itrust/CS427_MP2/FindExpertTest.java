package edu.ncsu.cs.itrust.CS427_MP2;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;


public class FindExpertTest extends iTrustHTTPTest {
	
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testFindExpert()throws Exception{
		/*
		 * First log in as admin to edit Kelly Doctor's profile
		 */
		WebConversation wc = login("9000000001", "pw");	
		WebResponse wr = wc.getCurrentPage();
		wr = wr.getLinkWith("Edit Personnel").click();
		WebForm HCPForm = wr.getForms()[1];
		HCPForm.getScriptableObject().setParameterValue("FIRST_NAME", "Kelly");
		HCPForm.getScriptableObject().setParameterValue("LAST_NAME", "Doctor");
		HCPForm.getSubmitButtons()[1].click();
		wr = wc.getCurrentPage();
		HCPForm = wr.getForms()[2];
		HCPForm.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/staff/editPersonnel.jsp", wr.getURL().toString());
		WebForm editPerson = wr.getForms()[0];
		editPerson.getScriptableObject().setParameterValue("firstName", "Kelly");
		editPerson.getScriptableObject().setParameterValue("lastName", "Doctor");
		editPerson.getScriptableObject().setParameterValue("streetAddress1", "Oak Hills");

		editPerson.getScriptableObject().setParameterValue("city", "Capitol City");
		editPerson.getScriptableObject().setParameterValue("state", "NC");
		editPerson.getScriptableObject().setParameterValue("zip", "28700");
		editPerson.getScriptableObject().setParameterValue("phone", "555-877-5100");
		editPerson.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		/*
		 * Admin logs out and Patient 1 logs in,
		 * Patient 1 tries to find an expert
		 */
		wr.getLinkWith("Logout").click();
		wr = wc.getCurrentPage();
		assertEquals("http://localhost:8080/iTrust/auth/forwardUser.jsp", wr.getURL().toString());
		WebForm login = wr.getForms()[0];
		login.getScriptableObject().setParameterValue("j_username", "1");
		login.getScriptableObject().setParameterValue("j_password", "pw");
		login.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		
		 /*Log in as Patient 1 and find an expert,
	     * the right senario should be that information
	     * of Kelly Doctor is contained in this page
	     */
		wr = wr.getLinkWith("Find an Expert").click();
		WebForm expertForm = wr.getForms()[0];
		expertForm.getScriptableObject().setParameterValue("specialty", "Surgeon");
	
		expertForm.getButtons()[0].click();
		wr = wc.getCurrentPage();

		assertEquals(1,wr.getElementsWithAttribute("href", "#9000000000Modal").length);
		
	}

}
