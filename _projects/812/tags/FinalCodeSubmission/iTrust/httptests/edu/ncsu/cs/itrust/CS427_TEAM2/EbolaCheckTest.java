package edu.ncsu.cs.itrust.CS427_TEAM2;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;


public class EbolaCheckTest extends iTrustHTTPTest {
	/**ADDRESS*/
	public static final String ADDRESS = "http://localhost:8080/iTrust/addon/ebolaCheck.jsp";
	/**gen*/
	protected TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testRiskFreePatient() throws Exception{
		WebConversation wc = new WebConversation();
		WebResponse wr = wc.getResponse(ADDRESS);
		
		wr.getLinkWith("EBOLA CHECK");
		
		assertEquals("iTrust - Ebola Check", wr.getTitle());
		
		wr.getFormWithName("ebolaForm").setParameter("countries", "Malaysia");

		
		wr = wr.getFormWithName("ebolaForm").submit();
		
		assertTrue(wr.getText().contains("The patient has no risk"));
		
		
	}
	public void testRiskPatient() throws Exception{
		WebConversation wc = new WebConversation();
		WebResponse wr = wc.getResponse(ADDRESS);
		
		wr.getLinkWith("EBOLA CHECK");
		
		assertEquals("iTrust - Ebola Check", wr.getTitle());
		
		wr.getFormWithName("ebolaForm").setParameter("countries", "Guinea");

		
		wr = wr.getFormWithName("ebolaForm").submit();
		
		assertTrue(wr.getText().contains("!!!!THE PATIENT IS AT RISK. Please follow protocol !!!!"));
		
		
	}

}
