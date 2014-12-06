package edu.ncsu.cs.itrust.CS427_TEAM3;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

public class DeceasedPatientsTest extends iTrustHTTPTest {

	private String HCP1IdString = "9000000000";
	private long HCP1Id = 9000000000L;
	private String HCP1Password = "pw";
	
	protected void setUp() throws Exception {
		super.setUp(); // clear tables is called in super
		HttpUnitOptions.setScriptingEnabled(false);
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testDeceasedPatients() throws Exception {
		WebConversation wc = login(this.HCP1IdString, this.HCP1Password);
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, this.HCP1Id, 0L, "");
		
		wr = wr.getLinkWith("List of Deceased Patients").click();
		assertEquals("iTrust - View List of Deceased Patients", wr.getTitle());
		
		assertEquals("Patient", wr.getTables()[0].getCellAsText(0, 0));
		assertEquals("Gender", wr.getTables()[0].getCellAsText(0, 1));
		assertEquals("Cause Of Death", wr.getTables()[0].getCellAsText(0, 2));
		assertEquals("Date Of Death", wr.getTables()[0].getCellAsText(0, 3));
		assertEquals("Andy Programmer", wr.getTables()[0].getCellAsText(1, 0));

		
	}
	
}
