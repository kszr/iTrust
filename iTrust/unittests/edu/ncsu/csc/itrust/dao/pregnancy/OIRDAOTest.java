package edu.ncsu.csc.itrust.dao.pregnancy;

import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.beans.OIRBean;
import edu.ncsu.csc.itrust.dao.mysql.OIRDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class OIRDAOTest extends TestCase {
	OIRDAO oirDAO = TestDAOFactory.getTestInstance().getOIRDAO();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.OIR();
	}
	
	public void testDoesNotExist() throws Exception{
		try{
			oirDAO.getOIRListForPatient(0L);
			fail("exception should have been thrown");
		} catch(ITrustException e){
			assertEquals("User does not exist", e.getMessage());
		}
	}
	
	public void testAddOIR() throws Exception{
		OIRBean oirBean = new OIRBean();
		oirBean.setCreationDate(new Date());
		oirBean.setLMP(new Date());
		oirBean.setPatientID(54321L);
		try{
			oirDAO.createOIR(oirBean);
		} catch (Exception e){
			fail(e.getMessage());
		}
	}
	
	public void testRetrieveOIR() throws Exception{
		Date currDate = new Date();
		OIRBean oirBean = new OIRBean();
		oirBean.setCreationDate(currDate);
		oirBean.setLMP(currDate);
		oirBean.setPatientID(98765L);
		try{
			oirDAO.createOIR(oirBean);
		} catch (Exception e){
			fail(e.getMessage());
		}
		
		List<OIRBean> oirBeanList = null;
		try{
			oirBeanList = oirDAO.getOIRListForPatient(98765L);
		} catch (Exception e){
			fail(e.getMessage());
		}
		
		assertNotNull(oirBeanList);
		assertEquals(1, oirBeanList.size());
		OIRBean fetchedBean = oirBeanList.get(0);
		assertNotNull(fetchedBean);
	}
}
