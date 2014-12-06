package edu.ncsu.cs.itrust.CS427_TEAM3;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * Use Case 30
 */
@SuppressWarnings("unused")
public class SendRemindersTest extends iTrustHTTPTest {

	private String adminIdString = "9000000001";
	private long adminId = 9000000001L;
	private String adminPassword = "pw";
	
	protected void setUp() throws Exception {
		super.setUp(); // clear tables is called in super
		HttpUnitOptions.setScriptingEnabled(false);
		gen.clearAllTables();
		gen.standardData();
	}

	public void testAdminSendReminders() throws Exception {

		// Login
		WebConversation wc = login(this.adminIdString, this.adminPassword);
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, this.adminId, 0L, "");
		
		wr = wr.getLinkWith("Send Reminders").click();
		
		// Select Patient
		WebForm wf = wr.getFormWithID("mainForm");

		wf.getScriptableObject().setParameterValue("daysInAdvance", "10");
		wr = wf.submit();

		assertLogged(TransactionType.SENT_REMINDERS, this.adminId, 0L, "");
		
		// Create timestamp
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		String stamp = dateFormat.format(date);
		
		assertTrue(wr.getText().contains("Sent a reminder to patients with an appointment within 10 days."));
		
		// Check message in outbox
		wr = wr.getLinkWith("Reminders Outbox").click();
		assertTrue(wr.getText().contains("Sent Reminders"));
		assertTrue(wr.getText().contains("Random Person"));
		assertTrue(wr.getText().contains("Andy Programmer"));
	}
	
}
