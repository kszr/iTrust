package edu.ncsu.csc.itrust.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ObstetricsVisitBean;

public class ObstetricsVisitBeanTest extends TestCase {
	
	public void testDateFailure() {
		ObstetricsVisitBean ov = new ObstetricsVisitBean();
		ov.setVisitDateStr("bad date");
		assertNull(ov.getVisitDate());
	}

}
