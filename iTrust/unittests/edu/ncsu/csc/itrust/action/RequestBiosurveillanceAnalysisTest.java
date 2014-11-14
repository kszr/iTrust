package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.BioSurveillanceBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import junit.framework.TestCase;

public class RequestBiosurveillanceAnalysisTest extends TestCase{
	

	private TestDataGenerator gen;
	private BioSurveillanceBean bioBean;
	private  RequestBioSurveillanceAnalysisAction action;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		action = new RequestBioSurveillanceAnalysisAction();

	}
	
	public void testInfluenza() throws Exception {
		gen.addThreePatiensWithSameZipCode();
	
	gen.addOfficeVisitsWithInfluenza();
	
	bioBean = new BioSurveillanceBean("487.00", "61820",
			"01/14/2005");
	assertTrue(action.requestBioAnalysis(bioBean));
	
	
	
	
	}

}
