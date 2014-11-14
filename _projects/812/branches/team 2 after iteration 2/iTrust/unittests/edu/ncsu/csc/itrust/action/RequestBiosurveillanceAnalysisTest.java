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
	
	public void testInfluenza() throws Exception {
		gen.addThreePatiensWithSameZipCode();

		gen.addOfficeVisitsWithInfluenza();

		BioSurveillanceBean bioBean = new BioSurveillanceBean("487.00", "61820", "01/14/2005");
		System.out.println("bioBean " + action.requestBioAnalysis(bioBean));
		assertTrue(action.requestBioAnalysis(bioBean));

	}

}
