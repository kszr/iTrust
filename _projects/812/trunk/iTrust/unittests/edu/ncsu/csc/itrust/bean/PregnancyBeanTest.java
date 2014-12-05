package edu.ncsu.csc.itrust.bean;

import edu.ncsu.csc.itrust.beans.PregnancyBean;
import junit.framework.TestCase;

public class PregnancyBeanTest extends TestCase {
	private PregnancyBean pBean;
	
	@Override
	protected void setUp() throws Exception{
		pBean = new PregnancyBean();
	}
	
	public void testDeliveryType(){
		pBean = new PregnancyBean();
		pBean.setDeliveryType(PregnancyBean.DeliveryType.VAGINAL_DELIVERY);
		assertEquals("Vaginal Delivery", pBean.getDeliveryType().toString());
	}
	
	public void testNegativeHoursInLabor(){
		pBean = new PregnancyBean();
		pBean.setHoursInLabor(-4.5);
		assertEquals((double) 0, pBean.getHoursInLabor());
	}
	
	public void testHoursInLabor(){
		pBean = new PregnancyBean();
		pBean.setHoursInLabor(3.2);
		assertEquals((double) 3.2, pBean.getHoursInLabor());
	}
	
	public void testPatient(){
		pBean = new PregnancyBean();
		pBean.setPatientID((long)54321);
		
		assertEquals((long)54321, pBean.getPatientID());
	}
	
	public void testYearOfContraception(){
		pBean = new PregnancyBean();
		pBean.setYearOfContraception(2002);
		assertEquals(2002, pBean.getYearOfContraception());
	}
}
