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
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * Use Case 30
 */
@SuppressWarnings("unused")
public class SendRemindersTest extends iTrustHTTPTest {

	private long adminId = 9000000001L;
	private long adminPassword = "pw";
	
	protected void setUp() throws Exception {
		super.setUp(); // clear tables is called in super
		HttpUnitOptions.setScriptingEnabled(false);
		gen.clearAllTables();
		gen.standardData();
		gen.clearAppointments();
	}

	// @TODO
	public void testAdminSendReminders() throws Exception {

		// Login
		WebConversation wc = login(this.adminId, this.adminPassword);
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Message Outbox").click();
		assertLogged(TransactionType.OUTBOX_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Compose a Message").click();
		
		// Select Patient
		WebForm wf = wr.getFormWithID("mainForm");

		wf.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		wr = wf.submit();

		// Submit message
		wf = wr.getFormWithID("mainForm");
		wf.getScriptableObject().setParameterValue("subject", "Visit Request");
		wf.getScriptableObject().setParameterValue("messageBody", "We really need to have a visit.");
		wr = wf.submit();
		assertLogged(TransactionType.SENT_REMINDERS, this.adminId, 2L, "");
		
		
		// Create timestamp
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		String stamp = dateFormat.format(date);
		
		assertTrue(wr.getText().contains("My Sent Messages"));
		
		// Check message in outbox
		wr = wr.getLinkWith("Message Outbox").click();
		assertTrue(wr.getText().contains("Visit Request"));
		assertTrue(wr.getText().contains("Andy Programmer"));
		assertTrue(wr.getTableWithID("mailbox").getText().contains(stamp));
		assertLogged(TransactionType.OUTBOX_VIEW, 9000000000L, 0L, "");
		
		// Check bolded message appears in patient
		wr = wr.getLinkWith("Logout").click();
		assertLogged(TransactionType.LOGOUT, 9000000000L, 9000000000L, "");
		
		//wr = wr.getLinkWith("Log into iTrust").click();
		
		wc = login("2", "pw");
		wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		wr = wr.getLinkWith("Message Inbox").click();
		assertLogged(TransactionType.INBOX_VIEW, 2L, 0L, "");
		
		assertEquals("font-weight: bold;", wr.getTableWithID("mailbox").getRows()[1].getAttribute("style"));
		assertTrue(wr.getTableWithID("mailbox").getRows()[1].getText().contains("Kelly Doctor"));
		assertTrue(wr.getTableWithID("mailbox").getRows()[1].getText().contains("Visit Request"));
		assertTrue(wr.getTableWithID("mailbox").getRows()[1].getText().contains(stamp));		
	}
	
}
