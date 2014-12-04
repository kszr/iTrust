package edu.ncsu.csc.itrust.dao.pregnancy;

import java.util.List;

import edu.ncsu.csc.itrust.beans.PregnancyBean;
import edu.ncsu.csc.itrust.dao.mysql.PregnancyDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class PregnancyDAOTest extends TestCase {
	PregnancyDAO pregnancyDAO = TestDAOFactory.getTestInstance().getPregnancyDAO();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.priorPregnancy();
	}
	
	public void testDoesNotExist() throws Exception{
		try{
			pregnancyDAO.getPregnancyListForPatient(0L);
			fail("exception should have been thrown");
		} catch(ITrustException e){
			assertEquals("User does not exist", e.getMessage());
		}
	}
	
	public void testCreatePregnancy() throws Exception{
		PregnancyBean pBean = new PregnancyBean();
		pBean.setPatientID(12345L);
		pBean.setYearOfContraception(2000);
		pBean.setNumberOfWeeksPregnant(38);
		pBean.setNumberOfDaysPregnant(2);
		pBean.setHoursInLabor(7.5);
		pBean.setDeliveryType(PregnancyBean.DeliveryType.CAESAREAN_SECTION);
		
		try{
			pregnancyDAO.createPregnancy(pBean);
		} catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	public void testGetPregnancyList() throws Exception{
		List<PregnancyBean> pBeanList = null;
		try{
			pBeanList = pregnancyDAO.getPregnancyListForPatient(301L);
		} catch(Exception e){
			fail(e.getMessage());
		}
		
		//OIR.sql inserts two records for patient 301
		assertEquals(2, pBeanList.size());
		
	}
	
	public void testDeletePregnancy() throws Exception{
		List<PregnancyBean> pBeanList = null;
		long preg_id = 0;
		try{
			pBeanList = pregnancyDAO.getPregnancyListForPatient(301L);
			PregnancyBean pBean = pBeanList.get(0);
			preg_id = pBean.getPregnancyID();
			pregnancyDAO.deletePregnancy(preg_id);
			pBeanList = pregnancyDAO.getPregnancyListForPatient(301L);
		} catch(Exception e){
			fail(e.getMessage());
		}
		
		for(PregnancyBean p : pBeanList){
			assertNotSame(preg_id, p.getPregnancyID());
		}
		
	}
}
