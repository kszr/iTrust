package edu.ncsu.csc.itrust.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.BioSurveillanceBean;


/**
 * Test for BioSurveillanceBean
 */
public class BioSurveillanceBeanTest extends TestCase {
	/**
	 * Test for a bean.
	 */
	public  void testBeanWithThreshold()
	{
		BioSurveillanceBean bean = new BioSurveillanceBean("084", "61801", "10/10/2005", "20");
		
		assertTrue(bean.getDiagnosisCode().equals("084"));
		assertTrue(bean.getZipCode().equals("61801"));
		assertTrue(bean.getDate().equals("10/10/2005"));
		assertTrue(bean.getThreshold().equals("20"));

		bean.setDiagnosisCode("123");
		bean.setZipCode("11111");
		bean.setDate("02/18/1992");
		bean.setThreshold("40");
		
		assertTrue(bean.getDiagnosisCode().equals("123"));
		assertTrue(bean.getZipCode().equals("11111"));
		assertTrue(bean.getDate().equals("02/18/1992"));
		assertTrue(bean.getThreshold().equals("40"));	
	}
	
	public  void testBeanWithoutThreshold()
	{
		BioSurveillanceBean bean = new BioSurveillanceBean("084", "61801", "10/10/2005");
		
		assertTrue(bean.getDiagnosisCode().equals("084"));
		assertTrue(bean.getZipCode().equals("61801"));
		assertTrue(bean.getDate().equals("10/10/2005"));

		bean.setDiagnosisCode("123");
		bean.setZipCode("11111");
		bean.setDate("02/18/1992");
		
		assertTrue(bean.getDiagnosisCode().equals("123"));
		assertTrue(bean.getZipCode().equals("11111"));
		assertTrue(bean.getDate().equals("02/18/1992"));
	}
}




