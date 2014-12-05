package edu.ncsu.cs.itrust.CS427_TEAM1;

import com.meterware.httpunit.*;

import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

/**
 * Unit tests for prior pregnancies (Team 1 - UC63) 
 * @author vincent
 *
 */
public class PregnancyPageTest extends iTrustHTTPTest {

	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.OIR();
		gen.priorPregnancy();
	}
	
	public void testPregnancyCreation() throws Exception{
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

		//Check that there IS an "Add a prior pregnancy" link and click it
		WebLink priorPregnancyLink = wr.getLinkWithID("addPriorPregnancy");
		assertNotNull(priorPregnancyLink);
		wr = priorPregnancyLink.click();
		assertEquals("iTrust - Prior Pregnancy Creation", wr.getTitle());
		
		//Enter valid info and click "Create" button
		WebForm form = wr.getFormWithID("mainForm");
		assertNotNull(form);
		form.setParameter("yearOfContraception", "2011");
		form.setParameter("pregnancyWeeks", "38");
		form.setParameter("pregnancyDays", "2");
		form.setParameter("timeInLabor", "10");
		form.setParameter("deliveryType", "Caesarean Section");
		
		//Verify successful pregnancy creation
		wr = form.submit();
		assertEquals("iTrust - Prior Pregnancy Creation", wr.getTitle());
		HTMLElement passElement = wr.getElementWithID("OIR_CREATION_SUCCESS");
		HTMLElement failElement = wr.getElementWithID("OIR_CREATION_FAILURE");
		assertNotNull(passElement);
		assertNull(failElement);
	}
	
	public void testPregnancyCreationInvalid() throws Exception{
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

		//Check that there IS an "Add a prior pregnancy" link and click it
		WebLink priorPregnancyLink = wr.getLinkWithID("addPriorPregnancy");
		assertNotNull(priorPregnancyLink);
		wr = priorPregnancyLink.click();
		assertEquals("iTrust - Prior Pregnancy Creation", wr.getTitle());
		
		//Enter INVALID info and click "Create" button
		WebForm form = wr.getFormWithID("mainForm");
		assertNotNull(form);
		form.setParameter("yearOfContraception", "2011");
		form.setParameter("pregnancyWeeks", "38");
		form.setParameter("pregnancyDays", "3000");
		form.setParameter("timeInLabor", "10");
		form.setParameter("deliveryType", "Caesarean Section");
		
		//Verify failure of pregnancy creation
		wr = form.submit();
		assertEquals("iTrust - Prior Pregnancy Creation", wr.getTitle());
		HTMLElement passElement = wr.getElementWithID("OIR_CREATION_SUCCESS");
		HTMLElement failElement = wr.getElementWithID("OIR_CREATION_FAILURE");
		assertNull(passElement);
		assertNotNull(failElement);
	}
}
