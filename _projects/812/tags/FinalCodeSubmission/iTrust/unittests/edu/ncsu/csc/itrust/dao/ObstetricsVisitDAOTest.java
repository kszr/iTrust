package edu.ncsu.csc.itrust.dao;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.dao.mysql.ObstetricsVisitDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class ObstetricsVisitDAOTest extends TestCase {
	private ObstetricsVisitDAO ovDAO = TestDAOFactory.getTestInstance().getObstetricsVisitDAO();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hcp0();
		gen.patient1();
		gen.obstetricsVisit1();
	}

	public void testAddNewObstetricsVisit() throws Exception {
		ObstetricsVisitBean ovPut = new ObstetricsVisitBean();
		long newOVID = ovDAO.add(ovPut);
		ObstetricsVisitBean ovGet = ovDAO.getObstetricsVisit(newOVID);
		assertEquals(newOVID, ovGet.getID());
	}
	
	public void testAddExistingVisit() throws Exception {
		try {
			ObstetricsVisitBean newVisit = new ObstetricsVisitBean();
			newVisit.setPatientID(-1337);
			ovDAO.add(newVisit);
			fail("Exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals(e.getMessage(), "A database exception has occurred. Please see the log in the console for stacktrace");
		}
	}
	
	public void testUpdate() throws Exception {
		ObstetricsVisitBean ovOld = ovDAO.getObstetricsVisit(1);
		ovOld.setFetalHeartRate(1337);
		ovDAO.update(ovOld);
		ObstetricsVisitBean ovNew = ovDAO.getObstetricsVisit(1);
		assertEquals(1337, ovNew.getFetalHeartRate());
	}

	public void testGetObstetricsVisit() {
		try {
			ObstetricsVisitBean ov = ovDAO.getObstetricsVisit(1);
			assertEquals(1, ov.getID());
			assertEquals(9000000000L, ov.getHcpID());
			assertEquals(1, ov.getPatientID());
		}
		catch(Exception e) {
			//TODO
		}
	}
	
	public void testCheckObstetricsVisitExists() {
		try {
			assertTrue(ovDAO.checkObstetricsVisitExists(1, 1));
		}
		catch(Exception e) {
			
		}
	}

	public void testGetEmptyObstetricsVisit() throws Exception {
		assertNull(ovDAO.getObstetricsVisit(0L));
	}
	
	public void testGetAllObstetricsVisits() throws Exception {
		try {
			List<ObstetricsVisitBean> ovList = ovDAO.getAllObstetricsVisits(1);
			assertEquals(1, ovList.size());
		}
		catch(Exception e) {
			//TODO
		}
	}
}
