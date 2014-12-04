package edu.ncsu.cs.itrust.CS427_TEAM2;

import java.net.ConnectException;
import java.util.List;

import junit.framework.TestCase;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;

import edu.ncsu.csc.itrust.testutils.TestDAOFactory;


 public class BioSurveillanceTest extends iTrustHTTPTest {


	/**ADDRESS*/
	public static final String ADDRESS = "http://localhost:8080/iTrust/auth/hcp/requestBiosurveillance.jsp";
	/**gen*/
	protected TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	/*
	 * Test whether the request bioSurveillance is only accessible to HCP user 
	 */
	public void testEnterPageLoggedInAsHCP() throws Exception{
		WebConversation wc = login("9000000000","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000000L,0L,"");
		
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Request BioSurveillance").click();
		assertEquals("iTrust - Request BioSurveillance", wr.getTitle());

		
	}
	/*
	 * Test whether entering wrong zip code gives error (for Malaria)
	 */
	
	public void testEnterMalriaAnalysisWrongZipCode() throws Exception
	{
		WebConversation wc = login("9000000000","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000000L,0L,"");
		
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Request BioSurveillance").click();
		assertEquals("iTrust - Request BioSurveillance", wr.getTitle());
		
		wr.getFormWithName("analysisRequestForm").setParameter("analysisDiagnosisCode", "084.5");
		wr.getFormWithName("analysisRequestForm").setParameter("analysisZipCode", "6182");
		wr.getFormWithName("analysisRequestForm").setParameter("analysisDate", "02/12/2014");
		wr.getFormWithName("analysisRequestForm").setParameter("analysisThreshold", "120");
		
		wr = wr.getFormWithName("analysisRequestForm").submit();
		
		assertTrue(wr.getText().contains("This form has not been validated correctly. The following field are not properly filled in: [Zip Code: xxxxx or xxxxx-xxxx]"));

	}
	/*
	 * Test whether entering wrong date gives error (for Malaria)
	 */
	
	public void testEnterMalriaAnalysisWrongDate() throws Exception
	{
		WebConversation wc = login("9000000000","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000000L,0L,"");
		
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Request BioSurveillance").click();
		assertEquals("iTrust - Request BioSurveillance", wr.getTitle());
		
		wr.getFormWithName("analysisRequestForm").setParameter("analysisDiagnosisCode", "084.5");
		wr.getFormWithName("analysisRequestForm").setParameter("analysisZipCode", "61820");
		wr.getFormWithName("analysisRequestForm").setParameter("analysisDate", "02/12/24");
		wr.getFormWithName("analysisRequestForm").setParameter("analysisThreshold", "120");
		
		wr = wr.getFormWithName("analysisRequestForm").submit();
		
		assertTrue(wr.getText().contains("This form has not been validated correctly. The following field are not properly filled in: [Date: MM/DD/YYYY]"));
	}
	
	/*
	 * Test whether without entering threshold gives error (for Malaria)
	 */
	public void testEnterMalriaAnalysisNULLThreshold() throws Exception
	{
		WebConversation wc = login("9000000000","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000000L,0L,"");
		
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Request BioSurveillance").click();
		assertEquals("iTrust - Request BioSurveillance", wr.getTitle());
		
		wr.getFormWithName("analysisRequestForm").setParameter("analysisDiagnosisCode", "084.5");
		wr.getFormWithName("analysisRequestForm").setParameter("analysisZipCode", "61820");
		wr.getFormWithName("analysisRequestForm").setParameter("analysisDate", "02/12/24");
		wr.getFormWithName("analysisRequestForm").setParameter("analysisThreshold", "");
		
		wr = wr.getFormWithName("analysisRequestForm").submit();
		
		assertTrue(wr.getText().contains("Please input percentage threshold to analyze Malaria."));
		
		
	}
	/*
	 * Test whether entering wrong threshold gives error (for Malaria)
	 */
	
	public void testEnterMalriaAnalysisWrongThreshold() throws Exception
	{
		WebConversation wc = login("9000000000","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000000L,0L,"");
		
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Request BioSurveillance").click();
		assertEquals("iTrust - Request BioSurveillance", wr.getTitle());
		
		wr.getFormWithName("analysisRequestForm").setParameter("analysisDiagnosisCode", "084.5");
		wr.getFormWithName("analysisRequestForm").setParameter("analysisZipCode", "61820");
		wr.getFormWithName("analysisRequestForm").setParameter("analysisDate", "02/12/24");
		wr.getFormWithName("analysisRequestForm").setParameter("analysisThreshold", "a");
		
		wr = wr.getFormWithName("analysisRequestForm").submit();
		
		assertTrue(wr.getText().contains("This form has not been validated correctly."));
	}
	/*
	 * Test whether entering wrong zip code gives error (for Influenza)
	 */
	
	public void testEnterInflueAnalysisWrongZipCode() throws Exception
	{
		WebConversation wc = login("9000000000","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000000L,0L,"");
		
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Request BioSurveillance").click();
		assertEquals("iTrust - Request BioSurveillance", wr.getTitle());
		
		wr.getFormWithName("analysisRequestForm").setParameter("analysisDiagnosisCode", "487.00");
		wr.getFormWithName("analysisRequestForm").setParameter("analysisZipCode", "6182");
		wr.getFormWithName("analysisRequestForm").setParameter("analysisDate", "02/12/2014");
		wr.getFormWithName("analysisRequestForm").setParameter("analysisThreshold", "120");
		
		wr = wr.getFormWithName("analysisRequestForm").submit();
		
		assertTrue(wr.getText().contains("This form has not been validated correctly. The following field are not properly filled in: [Zip Code: xxxxx or xxxxx-xxxx]"));

	}
	/*
	 * Test whether entering wrong date gives error (for Influenza)
	 */
	public void testEnterInflueAnalysisWrongDate() throws Exception
	{
		WebConversation wc = login("9000000000","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000000L,0L,"");
		
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Request BioSurveillance").click();
		assertEquals("iTrust - Request BioSurveillance", wr.getTitle());
		
		wr.getFormWithName("analysisRequestForm").setParameter("analysisDiagnosisCode", "487.00");
		wr.getFormWithName("analysisRequestForm").setParameter("analysisZipCode", "61820");
		wr.getFormWithName("analysisRequestForm").setParameter("analysisDate", "02/12/24");
		wr.getFormWithName("analysisRequestForm").setParameter("analysisThreshold", "120");
		
		wr = wr.getFormWithName("analysisRequestForm").submit();
		
		assertTrue(wr.getText().contains("This form has not been validated correctly. The following field are not properly filled in: [Date: MM/DD/YYYY]"));
	}
	
	/*
	 * Test whether entering wrong date give error (for trend)
	 */
	public void testEnterTrendWrongDate() throws Exception
	{
		WebConversation wc = login("9000000000","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000000L,0L,"");
		
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Request BioSurveillance").click();
		assertEquals("iTrust - Request BioSurveillance", wr.getTitle());
		
		wr.getFormWithName("trendRequestForm").setParameter("trendDiagnosisCode", "487.00");
		wr.getFormWithName("trendRequestForm").setParameter("trendZipCode", "61820");
		wr.getFormWithName("trendRequestForm").setParameter("trendDate", "02/12/24");

		
		wr = wr.getFormWithName("trendRequestForm").submit();
		
		assertTrue(wr.getText().contains("This form has not been validated correctly. The following field are not properly filled in: [Date: MM/DD/YYYY]"));
	}
	/*
	 * Test whether entering wrong zip code give error (for trend)
	 */
	public void testEnterTrendWrongZipCode() throws Exception
	{
		WebConversation wc = login("9000000000","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000000L,0L,"");
		
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Request BioSurveillance").click();
		assertEquals("iTrust - Request BioSurveillance", wr.getTitle());
		
		wr.getFormWithName("trendRequestForm").setParameter("trendDiagnosisCode", "487.00");
		wr.getFormWithName("trendRequestForm").setParameter("trendZipCode", "618");
		wr.getFormWithName("trendRequestForm").setParameter("trendDate", "02/12/2014");

		
		wr = wr.getFormWithName("trendRequestForm").submit();
		
		assertTrue(wr.getText().contains("This form has not been validated correctly. The following field are not properly filled in: [Zip Code: xxxxx or xxxxx-xxxx]"));
	}
	/*
	 * Test whether entering wrong diagnosis give error (for trend)
	 */
	public void testEnterTrendWrongDiagcode() throws Exception
	{
		WebConversation wc = login("9000000000","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000000L,0L,"");
		
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Request BioSurveillance").click();
		assertEquals("iTrust - Request BioSurveillance", wr.getTitle());
		
		wr.getFormWithName("trendRequestForm").setParameter("trendDiagnosisCode", "a");
		wr.getFormWithName("trendRequestForm").setParameter("trendZipCode", "618");
		wr.getFormWithName("trendRequestForm").setParameter("trendDate", "02/12/2014");

		
		wr = wr.getFormWithName("trendRequestForm").submit();
		
		assertTrue(wr.getText().contains("This form has not been validated correctly."));
	}
 }
