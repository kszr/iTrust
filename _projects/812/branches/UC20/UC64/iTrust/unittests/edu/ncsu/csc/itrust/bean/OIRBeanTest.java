package edu.ncsu.csc.itrust.bean;

import java.util.Date;

import edu.ncsu.csc.itrust.beans.OIRBean;
import junit.framework.TestCase;

public class OIRBeanTest extends TestCase {
	private OIRBean oirBean;
	
	@Override
	protected void setUp() throws Exception {
		oirBean = new OIRBean();
	}
	
	public void testPatient(){
		oirBean = new OIRBean();
		oirBean.setPatientID((long)54321);
		
		assertEquals((long)54321, oirBean.getPatientID());
	}
	
	public void testCreationDate(){
		oirBean = new OIRBean();
		Date date = new Date();
		oirBean.setCreationDate(date);
		assertEquals(date, oirBean.getCreationDate());
	}
	
	public void testLMP(){
		oirBean = new OIRBean();
		Date date = new Date();
		oirBean.setLMP(date);
		assertEquals(date, oirBean.getLMP());
	}
}
