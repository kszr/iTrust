package edu.ncsu.csc.itrust.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class CheckEbolaActionTest extends TestCase{
	private TestDataGenerator gen = new TestDataGenerator();
	//private BioSurveillanceBean bioBean;
	private  CheckEbolaAction action;
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();

		gen.clearAllTables();
		gen.standardData();
		
	}
	//If user visit two at risk High Risk countries
	public void testCheckEbolaTwoHighRisk() throws Exception 
	{
		action = new CheckEbolaAction("Guinea,Liberia");
		assertEquals(40, action.checkEbolaRisk());

	}
	
	//If user visit risk country
	public void testCheckEbolaRisk() throws Exception 
	{
		action = new CheckEbolaAction("Spain");
		assertEquals(5, action.checkEbolaRisk());

	}
	
	//If user visit no-risk country
	public void testCheckEbolaNoRisk() throws Exception 
	{
		action = new CheckEbolaAction("Thailand");
		assertEquals(0, action.checkEbolaRisk());

	}
	//If user does not visit any country
	public void testCheckEbolaNullCountry() throws Exception 
	{
		action = new CheckEbolaAction("");
		assertEquals(0, action.checkEbolaRisk());

	}
}
