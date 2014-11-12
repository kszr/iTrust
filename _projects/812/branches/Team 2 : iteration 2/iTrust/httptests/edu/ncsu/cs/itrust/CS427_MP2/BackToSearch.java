package edu.ncsu.cs.itrust.CS427_MP2;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

public class BackToSearch extends iTrustHTTPTest {

	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	 public void testSearach(){
		WebConversation wc;
			
				try {
					wc = login("9000000001", "pw");
				
		
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
			/**
			 * After interacting with Kelly Doctor's profile,
			 * try to edit another personnel's information
			 */
			wr.getForms()[0].getSubmitButtons()[0].click();
			wr = wc.getCurrentPage();
			wr = wr.getLinkWith("Edit Personnel").click();
			assertEquals(ADDRESS + "auth/getPersonnelID.jsp?forward=staff/editPersonnel.jsp", wr.getURL().toString());
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	 }
		 
}
