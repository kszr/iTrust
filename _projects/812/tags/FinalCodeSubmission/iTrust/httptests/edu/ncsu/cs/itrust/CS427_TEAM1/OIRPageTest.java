package edu.ncsu.cs.itrust.CS427_TEAM1;

import com.meterware.httpunit.*;

import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

/**
 * Unit tests for obstetrics initialization records (Team 1 - UC63)
 * @author vincent
 *
 */
public class OIRPageTest extends iTrustHTTPTest{
	
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.OIR();
		gen.priorPregnancy();
	}
	
	/**
	 * Tests that a non-ob/gyn HCP can view everything but not create anything
	 * @throws Exception
	 */
	public void testOIRViewAsNonObgyn() throws Exception{
		/*
		 * First log in a non-obgyn HCP
		 */
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		// choose patient Natalie Portman (MID 301)
		wr = wr.getLinkWith("Obstetrics").click();
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "301");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Obstetrics Record Listing", wr.getTitle());
		
		 //Check that there is no "Create new obstetrics record" link
		 WebLink createNewOIRLink = wr.getLinkWithID("CreateNewOIR");
		 assertNull(createNewOIRLink);
		 
		 //View patient's first OIR by clicking on "view this record" link
		 WebLink viewFirstRecordLink = wr.getLinkWithID("viewRecord1");
		 assertNotNull(viewFirstRecordLink);
		 wr = viewFirstRecordLink.click();
		 assertEquals("iTrust - Obstetrics Record", wr.getTitle());
		 
		 //Check that there is no "Add a prior pregnancy" button
		 WebLink priorPregnancyLink = wr.getLinkWithID("addPriorPregnancy");
		 assertNull(priorPregnancyLink);
		 
		 //Check that there is no "Create New Record" button
		 WebForm form = wr.getFormWithID("mainForm");
		 assertNotNull(form);
		 Button submitButton = form.getButtonWithID("createButton");
		 assertNull(submitButton);
		 
	}
	
	/** 
	 * Tests that an ob/gyn HCP can enter view only mode
	 * @throws Exception
	 */
	public void testOIRViewAsObgyn() throws Exception{
		/*
		 * First log in an obgyn HCP
		 */
		WebConversation wc = login("3000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		// choose patient Natalie Portman (MID 301)
		wr = wr.getLinkWith("Obstetrics").click();
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "301");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Obstetrics Record Listing", wr.getTitle());
		
		//Check that there IS a "Create new obstetrics record" link
		HTMLElement createNewOIRLink = wr.getElementWithID("CreateNewOIR");
		assertNotNull(createNewOIRLink);
		 
		//View patient's first OIR by clicking on "view this record" link
		WebLink viewFirstRecordLink = wr.getLinkWithID("viewRecord1");
		assertNotNull(viewFirstRecordLink);
		wr = viewFirstRecordLink.click();
		assertEquals("iTrust - Obstetrics Record", wr.getTitle());
		
		//Check that there is no "Add a prior pregnancy" button
		WebLink priorPregnancyLink = wr.getLinkWithID("addPriorPregnancy");
		assertNull(priorPregnancyLink);
		
		//Check that there is no "Create New Record" button
		WebForm form = wr.getFormWithID("mainForm");
		assertNotNull(form);
		Button submitButton = form.getButtonWithID("createButton");
		assertNull(submitButton);
	}
	
	/**
	 * Tests that an ob/gyn HCP can create a new OIR
	 * @throws Exception
	 */
	public void testOIRCreate() throws Exception{
		/*
		 * First log in an obgyn HCP
		 */
		WebConversation wc = login("3000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		// choose patient Natalie Portman (MID 301)
		wr = wr.getLinkWith("Obstetrics").click();
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "301");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Obstetrics Record Listing", wr.getTitle());
		
		//Check that there IS a "Create new obstetrics record" link
		WebLink createNewOIRLink = wr.getLinkWithID("CreateNewOIR");
		assertNotNull(createNewOIRLink);
		
		//Click "Create new obstetrics record" button to begin OIR creation process
		wr = createNewOIRLink.click();
		assertEquals("iTrust - Obstetrics Record", wr.getTitle());

		//Check that there IS an "Add a prior pregnancy" button
		WebLink priorPregnancyLink = wr.getLinkWithID("addPriorPregnancy");
		assertNotNull(priorPregnancyLink);
		
		//Check that there IS a "Create New Record" button
		WebForm form = wr.getFormWithID("mainForm");
		assertNotNull(form);
		Button submitButton = form.getButtonWithID("createButton");
		assertNotNull(submitButton);
		
		//Enter a valid date and click submit
		form.setParameter("LMPDate", "2014-11-11");
		wr = form.submit();
		
		//Verify successful OIR creation
		assertEquals("iTrust - Obstetrics Record", wr.getTitle());
		HTMLElement passElement = wr.getElementWithID("OIR_CREATION_SUCCESS");
		HTMLElement failElement = wr.getElementWithID("OIR_CREATION_FAILURE");
		assertNotNull(passElement);
		assertNull(failElement);
	}
	
	/**
	 * Tests that an ob/gyn HCP cannot create an invalid OIR
	 * @throws Exception
	 */
	public void testOIRCreateInvalid() throws Exception{
		/*
		 * First log in an obgyn HCP
		 */
		WebConversation wc = login("3000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		// choose patient Natalie Portman (MID 301)
		wr = wr.getLinkWith("Obstetrics").click();
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "301");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Obstetrics Record Listing", wr.getTitle());
		
		//Check that there IS a "Create new obstetrics record" link
		WebLink createNewOIRLink = wr.getLinkWithID("CreateNewOIR");
		assertNotNull(createNewOIRLink);
		
		//Click "Create new obstetrics record" button to begin OIR creation process
		wr = createNewOIRLink.click();
		assertEquals("iTrust - Obstetrics Record", wr.getTitle());

		//Check that there IS an "Add a prior pregnancy" button
		WebLink priorPregnancyLink = wr.getLinkWithID("addPriorPregnancy");
		assertNotNull(priorPregnancyLink);
		
		//Check that there IS a "Create New Record" button
		WebForm form = wr.getFormWithID("mainForm");
		assertNotNull(form);
		Button submitButton = form.getButtonWithID("createButton");
		assertNotNull(submitButton);
		
		//Enter an INVALID date and click submit
		form.setParameter("LMPDate", "2024-11-11");
		wr = form.submit();
		
		//Verify failure of OIR creation
		assertEquals("iTrust - Obstetrics Record", wr.getTitle());
		HTMLElement passElement = wr.getElementWithID("OIR_CREATION_SUCCESS");
		HTMLElement failElement = wr.getElementWithID("OIR_CREATION_FAILURE");
		assertNull(passElement);
		assertNotNull(failElement);
	}
}

