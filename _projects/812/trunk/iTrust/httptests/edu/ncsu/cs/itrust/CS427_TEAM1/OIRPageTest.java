package edu.ncsu.cs.itrust.CS427_TEAM1;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

public class OIRPageTest extends iTrustHTTPTest{
	
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.OIR();
		gen.priorPregnancy();
	}
	
	public void testOIRViewAsNonObgyn() throws Exception{
		/*
		 * First log in a non-obgyn HCP
		 */
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Immunization Report").click();
		// choose patient Charlie Chaplin (MID 308)
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "308");
		patientForm.getButtons()[1].click();
	}
	
	public void testOIRViewAsObgyn() throws Exception{
		
	}
	
	public void testOIRCreate() throws Exception{
		
	}
}
