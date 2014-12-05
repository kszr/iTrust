package edu.ncsu.csc.itrust.action;

import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class AddObstetricsVisitActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private AddObstetricsVisitAction action;

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.patient1();
		action = new AddObstetricsVisitAction(factory, "1");
	}

	public void testAddEmpty() throws Exception {
			long hcpID = 3000L;
			long ovID = action.addEmptyObstetricsVisit(hcpID);
			ObstetricsVisitBean ov = factory.getObstetricsVisitDAO().getObstetricsVisit(ovID);
			assertEquals(hcpID, ov.getHcpID());
			assertEquals(1, ov.getPatientID());
			assertEquals(new ObstetricsVisitBean().getVisitDateStr(), ov.getVisitDateStr());
	}

	public void testGetObstetricsVisits() throws Exception {
		List<ObstetricsVisitBean> ovs = factory.getObstetricsVisitDAO().getAllObstetricsVisits(1);
		List<ObstetricsVisitBean> actualOvs = action.getAllObstetricsVisits();
		assertEquals(ovs.size(), actualOvs.size());
		for (int i = 0; i < ovs.size(); i++) {
			assertEquals(ovs.get(i).getID(), actualOvs.get(i).getID());
		}
	}
	
	public void testGetUserName() throws Exception {
		assertEquals("Random Person", action.getUserName());
	}
}
