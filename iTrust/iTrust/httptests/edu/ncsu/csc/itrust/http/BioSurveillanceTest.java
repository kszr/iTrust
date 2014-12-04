package edu.ncsu.csc.itrust.http;

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

	/*
	 * The URL for iTrust, change as needed
	 */
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

	public void testEnterPage() throws Exception{
		WebConversation wc = login("9000000000","pw");
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW,9000000000L,0L,"");
		
		
	}
}
