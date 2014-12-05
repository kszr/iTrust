package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.PregnancyBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class PregnancyActionTest extends TestCase {
	private TestDataGenerator gen = new TestDataGenerator();
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DAOFactory evil = EvilDAOFactory.getEvilInstance();
	@Override
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.priorPregnancy();
	}
	
	
	public void testGetPregnancies() throws Exception{
		List<PregnancyBean> pBeanList = null;
		
		PregnancyAction pregnancyAction = new PregnancyAction(factory, 9000000000L);
		pBeanList = pregnancyAction.getPregnancies(301L);
		try{
			pBeanList = pregnancyAction.getPregnancies(301L);
		} catch (Exception e){
			fail(e.getMessage());
		}
		
		assertEquals(2, pBeanList.size());
	}
	
	public void testGetPregnanciesOfInvalidUser() throws Exception{
		PregnancyAction pregnancyAction = new PregnancyAction(factory, 9000000000L);
		try{
			List<PregnancyBean> pBeanList = pregnancyAction.getPregnancies(9999L);
			fail("Exception should be thrown");
		} catch (Exception e){
			assertEquals("Empty Set", e.getMessage());
		}
		
	}
	
	public void testDeletePregnancy() throws Exception{
		List<PregnancyBean> pBeanList = null;
		
		PregnancyAction pregnancyAction = new PregnancyAction(factory, 9000000000L);
		pBeanList = pregnancyAction.getPregnancies(301L);
		long preg_id = 0;
		try{
			pBeanList = pregnancyAction.getPregnancies(301L);
			PregnancyBean pBean = pBeanList.get(0);
			preg_id = pBean.getPregnancyID();
			pregnancyAction.deletePregnancy(preg_id);
			
			pBeanList = pregnancyAction.getPregnancies(301L);
		} catch (Exception e){
			fail(e.getMessage());
		}
		
		for(PregnancyBean p : pBeanList){
			assertNotSame(preg_id, p.getPregnancyID());
		}
	}
	
	public void testCreatePregnancy() throws Exception{
		PregnancyBean p = new PregnancyBean();
		p.setPatientID(12345L);
		p.setNumberOfWeeksPregnant(12);
		p.setNumberOfDaysPregnant(2);
		p.setHoursInLabor(3.4);
		p.setYearOfContraception(2011);
		p.setDeliveryType(PregnancyBean.DeliveryType.MISCARRIAGE);
		
		PregnancyAction pregnancyAction = new PregnancyAction(factory, 9000000000L);
		try{
			pregnancyAction.createPregnancy(p);
		} catch (Exception e){
			fail(e.getMessage());
		}
		
		
	}

}
