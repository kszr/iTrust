package edu.ncsu.cs.itrust.CS427_TEAM3;

import java.util.Date;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
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
public class MessageFilterTest extends iTrustHTTPTest {

	protected void setUp() throws Exception {
		super.setUp(); // clear tables is called in super
		HttpUnitOptions.setScriptingEnabled(false);
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testHCPtestMessageFilter() throws Exception {
		// Create DB for this test case
		String DIR = "sql/data";
		DAOFactory factory = TestDAOFactory.getTestInstance();
		new DBBuilder(factory).executeSQLFile(DIR + "/clearMessages.sql");
		new DBBuilder(factory).executeSQLFile(DIR + "/messageCase3.sql");
		
		// Login
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Message Inbox").click();
		assertLogged(TransactionType.INBOX_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Edit Filter").click();
		
		// Enter filter information where has words is influenza
		wr.getForms()[0].setParameter("sender", "");
		wr.getForms()[0].setParameter("subject", "");
		wr.getForms()[0].setParameter("hasWords", "influenza");
		wr.getForms()[0].setParameter("startDate", "");
		wr.getForms()[0].setParameter("notWords", "");
		wr.getForms()[0].setParameter("endDate", "");
		wr = wr.getForms()[0].submit(wr.getForms()[0].getSubmitButton("test"));
		
		// Make sure the proper message exists in the right order
		assertTrue(wr.getTables()[2].getRows()[1].getText().contains("Andy Programmer"));
		assertTrue(wr.getTables()[2].getRows()[1].getText().contains("RE: Influenza Vaccine"));
		assertTrue(wr.getTables()[2].getRows()[1].getText().contains("2010-03-25 16:30"));
		
		assertTrue(wr.getTables()[2].getRows()[2].getText().contains("Andy Programmer"));
		assertTrue(wr.getTables()[2].getRows()[2].getText().contains("Influenza Vaccine"));
		assertTrue(wr.getTables()[2].getRows()[2].getText().contains("2010-03-25 16:15"));
		
		assertTrue(wr.getTables()[2].getRows()[3].getText().contains("Random Person"));
		assertTrue(wr.getTables()[2].getRows()[3].getText().contains("Flu Season"));
		assertTrue(wr.getTables()[2].getRows()[3].getText().contains("2009-12-03 08:26"));
		
		assertTrue(wr.getTables()[2].getRows()[4].getText().contains("Baby Programmer"));
		assertTrue(wr.getTables()[2].getRows()[4].getText().contains("Bad cough"));
		assertTrue(wr.getTables()[2].getRows()[4].getText().contains("2008-06-02 20:46"));	
	}
	
	public void testpatientApplyMessageFilter() throws Exception {
		// Create DB for this test case
		String DIR = "sql/data";
		DAOFactory factory = TestDAOFactory.getTestInstance();
		new DBBuilder(factory).executeSQLFile(DIR + "/clearMessages.sql");
		new DBBuilder(factory).executeSQLFile(DIR + "/messageCase4.sql");
		
		// Login
		WebConversation wc = login("2", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		wr = wr.getLinkWith("Message Inbox").click();
		assertLogged(TransactionType.INBOX_VIEW, 2L, 0L, "");
		wr = wr.getLinkWith("Apply Filter").click();
		
		// Make sure the proper message exists in the right order
		assertTrue(wr.getTableWithID("mailbox").getRows()[1].getText().contains("Kelly Doctor"));
		assertTrue(wr.getTableWithID("mailbox").getRows()[1].getText().contains("RE: Influenza Vaccine"));
		assertTrue(wr.getTableWithID("mailbox").getRows()[1].getText().contains("2010-03-25 16:39"));
		
		assertTrue(wr.getTableWithID("mailbox").getRows()[2].getText().contains("Kelly Doctor"));
		assertTrue(wr.getTableWithID("mailbox").getRows()[2].getText().contains("RE: Vaccines"));
		assertTrue(wr.getTableWithID("mailbox").getRows()[2].getText().contains("2010-01-21 20:22"));	
	}
	
	public void testpatientApplyMessageFilter2() throws Exception {
		// Create DB for this test case
		String DIR = "sql/data";
		DAOFactory factory = TestDAOFactory.getTestInstance();
		new DBBuilder(factory).executeSQLFile(DIR + "/clearMessages.sql");
		new DBBuilder(factory).executeSQLFile(DIR + "/messageCase3.sql");
		
		// Login
		WebConversation wc = login("2", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		wr = wr.getLinkWith("Message Inbox").click();
		assertLogged(TransactionType.INBOX_VIEW, 2L, 0L, "");
		wr = wr.getLinkWith("Edit Filter").click();
		
		// Enter filter information where has words is influenza
		wr.getForms()[0].setParameter("sender", "");
		wr.getForms()[0].setParameter("subject", "");
		wr.getForms()[0].setParameter("hasWords", "");
		wr.getForms()[0].setParameter("startDate", "04/08/2010");
		wr.getForms()[0].setParameter("notWords", "");
		wr.getForms()[0].setParameter("endDate", "04/07/2010");
		wr = wr.getForms()[0].submit(wr.getForms()[0].getSubmitButton("test"));
	}
	
	public void testHCPtestMessageFilter2 () throws Exception {
		// Create DB for this test case
		String DIR = "sql/data";
		DAOFactory factory = TestDAOFactory.getTestInstance();
		new DBBuilder(factory).executeSQLFile(DIR + "/clearMessages.sql");
		new DBBuilder(factory).executeSQLFile(DIR + "/messageCase5.sql");
		
		// Login
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Message Inbox").click();
		assertLogged(TransactionType.INBOX_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Edit Filter").click();
		
		// Enter filter information where has words is influenza
		wr.getForms()[0].setParameter("sender", "");
		wr.getForms()[0].setParameter("subject", "");
		wr.getForms()[0].setParameter("hasWords", "influenza");
		wr.getForms()[0].setParameter("startDate", "");
		wr.getForms()[0].setParameter("notWords", "");
		wr.getForms()[0].setParameter("endDate", "");
		wr = wr.getForms()[0].submit(wr.getForms()[0].getSubmitButton("test"));
		
		// Make sure the proper message exists in the right order
		assertTrue(wr.getTables()[2].getRows()[1].getText().contains("Andy Programmer"));
		assertTrue(wr.getTables()[2].getRows()[1].getText().contains("Influenza Vaccine"));
		assertTrue(wr.getTables()[2].getRows()[1].getText().contains("2010-03-25 16:15"));
	}
}
