package edu.ncsu.csc.itrust.validate.regex;

import edu.ncsu.csc.itrust.testutils.ValidatorProxy;
import edu.ncsu.csc.itrust.validate.ValidationFormat;
import junit.framework.TestCase;

public class ThresholdValidatorTest extends TestCase{
	private ValidatorProxy validatorProxy = new ValidatorProxy();
	private static final ValidationFormat VALIDATION_FORMAT = ValidationFormat.THRESHOLDFORMALARIA;
	private static final String PASSED = "";
	private static final String FAILED = "Name: " + VALIDATION_FORMAT.getDescription();

	public void testGoodAnswer() throws Exception {
		String value = "20";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}

	public void testBadInputType() throws Exception {
		String value = "abasdfa";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}

	public void testBadInputType2() throws Exception {
		String value = "1234a34";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}
	
	public void testBadInputType3() throws Exception {
		String value = "1234 34";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}
}
