package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.BioSurveillanceBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class RequestBiosurveillanceAnalysisTest extends TestCase{
	

	private TestDataGenerator gen = new TestDataGenerator();
	//private BioSurveillanceBean bioBean;
	private  RequestBioSurveillanceAnalysisAction action;
	
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		action = new RequestBioSurveillanceAnalysisAction(TestDAOFactory.getTestInstance());
		gen.clearAllTables();
		gen.standardData();
		
	}
	
	//15 people with influenza in the first week, 15 people with influenza in the second week
	public void testHaveInfluenza() throws Exception {
		gen.addThreePatiensWithSameZipCode();

		gen.addOfficeVisitsWithInfluenza();

		BioSurveillanceBean bioBean = new BioSurveillanceBean("487.00", "61820", "01/14/2005");
		System.out.println("bioBean " + action.requestBioAnalysis(bioBean));
		assertTrue(action.requestBioAnalysis(bioBean));

	}
	
	//Test if the year is different than when it is supposed to have influenza
	public void testDoNotHaveInfluenza() throws Exception {
		gen.addThreePatiensWithSameZipCode();

		gen.addOfficeVisitsWithInfluenza();

		BioSurveillanceBean bioBean = new BioSurveillanceBean("487.00", "61820", "01/14/2008");
		
		assertFalse(action.requestBioAnalysis(bioBean));

	}
	
	//Test if the Zipcode given for influenza is different than where there is influenza epidemic 
	public void testWrongZipCodeInfluenza() throws Exception {
		gen.addThreePatiensWithSameZipCode();

		gen.addOfficeVisitsWithInfluenza();

		BioSurveillanceBean bioBean = new BioSurveillanceBean("487.00", "11111", "01/14/2005");
		
		assertFalse(action.requestBioAnalysis(bioBean));

	}
	
	
	//Week 1: year 2000 = 15, 1999 = 12, 1998 = 12; Threshold % = 125%
	//Week 2: year 2000 = 18, 1999 = 12, 1998 = 12; Threshold % = 150%
	public void testHaveMalaria() throws Exception
	{
		gen.addThreePatiensWithSameZipCode();
		gen.addOfficeVisitWithMalaria();
		
		BioSurveillanceBean bioBean = new BioSurveillanceBean("084.5", "61820", "01/14/2000","120");
		//System.out.println("bioBean " + action.requestBioAnalysis(bioBean));
		assertTrue(action.requestBioAnalysis(bioBean));
		
	}
	
	//Test if we change the year to 1999, and the threshold is now too high. input threshold should be 100 in this case
	public void testDoNotHaveMalariaChangeYear() throws Exception
	{
		gen.addThreePatiensWithSameZipCode();
		gen.addOfficeVisitWithMalaria();
		
		BioSurveillanceBean bioBean = new BioSurveillanceBean("084.5", "61820", "01/14/1999","120");
		//System.out.println("bioBean " + action.requestBioAnalysis(bioBean));
		assertFalse(action.requestBioAnalysis(bioBean));
		
	}
	
	//Test if given threshold is higher than calculated threshold
	public void testDoNotHaveMalaria() throws Exception
	{
		gen.addThreePatiensWithSameZipCode();
		gen.addOfficeVisitWithMalaria();
		
		BioSurveillanceBean bioBean = new BioSurveillanceBean("084.5", "61820", "01/14/2000","150");
		//System.out.println("bioBean " + action.requestBioAnalysis(bioBean));
		assertFalse(action.requestBioAnalysis(bioBean));
	}
	
	//Test if not the same zipcode has area with malaria epidemic
	public void testWrongZipCodeMalaria() throws Exception
	{
		gen.addThreePatiensWithSameZipCode();
		gen.addOfficeVisitWithMalaria();
		
		BioSurveillanceBean bioBean = new BioSurveillanceBean("084.5", "11111", "01/14/2000","120");
		//System.out.println("bioBean " + action.requestBioAnalysis(bioBean));
		assertFalse(action.requestBioAnalysis(bioBean));
	}

	
	//Test if No patients with Malaria (divide by zero)
	public void testNoMalaria() throws Exception
	{
		gen.clearAllTables();
		gen.addThreePatiensWithSameZipCode();
		//gen.addOfficeVisitWithMalaria();
		
		BioSurveillanceBean bioBean = new BioSurveillanceBean("084.5", "11111", "01/14/2000","120");
		//System.out.println("bioBean " + action.requestBioAnalysis(bioBean));
		assertFalse(action.requestBioAnalysis(bioBean));
	}
	
}
