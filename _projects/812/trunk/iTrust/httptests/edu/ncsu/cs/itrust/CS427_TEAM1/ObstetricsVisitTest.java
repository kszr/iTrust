package edu.ncsu.cs.itrust.CS427_TEAM1;

import com.meterware.httpunit.*;

import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

/**
 * Unit tests for prior pregnancies (Team 1 - UC63) 
 * @author vincent
 *
 */
public class ObstetricsVisitTest extends iTrustHTTPTest {

	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.OIR();
		gen.priorPregnancy();
	}
	
	public void testVisitCreation() throws Exception{
		/*
		 * First log in an obgyn HCP
		 */
		WebConversation wc = login("3000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		// choose patient Natalie Portman (MID 301)
		wr = wr.getLinkWith("Document Office Visit").click();
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "301");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		
		Button docButton = wr.getFormWithID("formMain").getButtons()[1];
		assertEquals("Yes, Document Obstetrics Visit", docButton.getValue());
		docButton.click();
		
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Document Obstetrics Visit", wr.getTitle());
		
		WebForm form = wr.getFormWithID("mainForm");
		assertNotNull(form);
		form.setParameter("visitDate", "11/22/2014");
		form.setParameter("weeksPregnant", "3");
		form.setParameter("daysPregnant", "5");
		form.setParameter("fetalHeartRate", "120");
		form.setParameter("fundalHeightOfUterus", "12.3");
		
		wr = form.submit();
		assertEquals("iTrust - Document Obstetrics Visit", wr.getTitle());
		HTMLElement passElement = wr.getElementWithID("message_success");
		assertNotNull(passElement);
	}
	
	public void testInvalidVisitCreation() throws Exception{
		/*
		 * First log in an obgyn HCP
		 */
		WebConversation wc = login("3000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		// choose patient Natalie Portman (MID 301)
		wr = wr.getLinkWith("Document Office Visit").click();
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "301");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		
		Button docButton = wr.getFormWithID("formMain").getButtons()[1];
		assertEquals("Yes, Document Obstetrics Visit", docButton.getValue());
		docButton.click();
		
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Document Obstetrics Visit", wr.getTitle());
		
		WebForm form = wr.getFormWithID("mainForm");
		assertNotNull(form);
		form.setParameter("visitDate", "11/22/2014");
		form.setParameter("weeksPregnant", "a3");
		form.setParameter("daysPregnant", "500");
		form.setParameter("fetalHeartRate", "-120");
		form.setParameter("fundalHeightOfUterus", "12.3321");
		
		wr = form.submit();
		assertEquals("iTrust - Document Obstetrics Visit", wr.getTitle());
		HTMLElement passElement = wr.getElementWithID("error_failure");
		assertNotNull(passElement);
	}
}
