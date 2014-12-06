package edu.ncsu.csc.itrust.action;

import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.beans.OIRBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class OIRActionTest extends TestCase {
	private TestDataGenerator gen = new TestDataGenerator();
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DAOFactory evil = EvilDAOFactory.getEvilInstance();
	@Override
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.OIR();
	}
	
	public void testGetOIRList() throws Exception{
		OIRAction oirAction = new OIRAction(factory, 9000000000L);
		
		List<OIRBean> oirBeanList = null;
		try{
			oirBeanList = oirAction.getOIRList(1L);
		} catch (Exception e){
			fail(e.getMessage());
		}
		
		assertNotNull(oirBeanList);
		assertEquals(1, oirBeanList.size());
	}
	
	public void testGetOIR() throws Exception{
		OIRAction oirAction = new OIRAction(factory, 9000000000L);
		
		List<OIRBean> oirBeanList = null;
		long oir_id = 0;
		try{
			oirBeanList = oirAction.getOIRList(1L);
			OIRBean oirBean = oirBeanList.get(0);
			oir_id = oirBean.getRecordID();
			OIRBean fetchedBean = oirAction.getOIR(oir_id);
			assertNotNull(fetchedBean);
		} catch (Exception e){
			fail(e.getMessage());
		}

	}
	
	public void testGetInvalidOIR() throws Exception{
		OIRAction oirAction = new OIRAction(factory, 9000000000L);
		try{
			OIRBean oirBean = oirAction.getOIR(99999L);
			fail("Exception should have been thrown");
		} catch (Exception e){
			assertEquals("Empty Set", e.getMessage());
		}
	}
	
	public void testCreateOIR() throws Exception{
		OIRAction oirAction = new OIRAction(factory, 9000000000L);
		OIRBean oirBean = new OIRBean();
		Date today = new Date();
		oirBean.setCreationDate(today);
		oirBean.setLMP(today);
		oirBean.setPatientID(12345L);
		
		try{
			oirAction.createOIR(oirBean);
		} catch (Exception e){
			fail(e.getMessage());
		}
	}
	
}
