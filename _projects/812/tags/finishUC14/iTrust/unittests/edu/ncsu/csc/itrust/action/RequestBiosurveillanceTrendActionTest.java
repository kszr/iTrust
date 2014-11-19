package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.BioSurveillanceBean;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class RequestBiosurveillanceTrendActionTest extends TestCase{

	private TestDataGenerator gen = new TestDataGenerator();
	//private BioSurveillanceBean bioBean;
	private  RequestBiosurveillanceTrendAction action;
	
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		action = new RequestBiosurveillanceTrendAction(TestDAOFactory.getTestInstance());
		gen.clearAllTables();
		gen.standardData();
		
	}
	
	//Shows Malaria 1 total, 1 in 07/19/2011
	public void testDisplayMalaria() throws Exception {
		BioSurveillanceBean bioBean = new BioSurveillanceBean("084", "27607", "07/20/2011");
		List<Integer> eigthWeek = new ArrayList<Integer>();
		
		eigthWeek = action.requestBioTrend(bioBean, 1);
		
		assertTrue(eigthWeek.get(0).equals(1) && eigthWeek.get(1).equals(1) && eigthWeek.get(2).equals(1));
	}
	
	//Shows Influenza 2 total, 1 in 10/10/2005, other in 08/30/2011
	public void testDisplayInfluenza() throws Exception {
		BioSurveillanceBean bioBean = new BioSurveillanceBean("487", "27607", "10/11/2005");
		List<Integer> eigthWeek = new ArrayList<Integer>();
		
		eigthWeek = action.requestBioTrend(bioBean, 1);
		
		assertTrue(eigthWeek.get(0).equals(1) && eigthWeek.get(1).equals(1) && eigthWeek.get(2).equals(2));
	}
}
