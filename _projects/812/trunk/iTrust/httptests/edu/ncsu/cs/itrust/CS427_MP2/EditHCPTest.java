package edu.ncsu.cs.itrust.CS427_MP2;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;



public class EditHCPTest extends iTrustHTTPTest {

	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testEditHCP() throws Exception{
		/*
		 * Try to edit the profile of Kelly Doctor,
		 * the right scenario should be without 
		 * any exceptions
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
		editPerson.getScriptableObject().setParameterValue("streetAddress1", "98765 Oak Hills");

		editPerson.getScriptableObject().setParameterValue("city", "Capitol City");
		editPerson.getScriptableObject().setParameterValue("state", "NC");
		editPerson.getScriptableObject().setParameterValue("zip", "28700");
		editPerson.getScriptableObject().setParameterValue("phone", "555-877-5100");
		editPerson.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		
		/*
		 * Try to click on the "Edit Personnel and
		 * find another personnel, the right scenario
		 * should be the searching entrance page just 
		 * like the first time
		 */
		wr.getLinkWith("Edit Personnel").click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/getPersonnelID.jsp?forward=staff/editPersonnel.jsp", wr.getURL().toString());
	}
	
	
	
	
	
	

	
	
}
