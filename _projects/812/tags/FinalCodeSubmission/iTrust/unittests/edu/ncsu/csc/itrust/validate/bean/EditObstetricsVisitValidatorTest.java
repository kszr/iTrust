package edu.ncsu.csc.itrust.validate.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.forms.EditObstetricsVisitForm;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.EditObstetricsVisitValidator;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

/**
 * Test obstetrics visit validator
 *
 */
public class EditObstetricsVisitValidatorTest extends TestCase {
	public void testEditObstetricsVisitAllCorrect() throws Exception {
		EditObstetricsVisitForm form = new EditObstetricsVisitForm();
		form.setHcpID("99");
		form.setPatientID("309");
		form.setVisitDate("09/09/1982");
		form.setDaysPregnant("5");
		form.setWeeksPregnant("16");
		form.setFundalHeightOfUterus("3.4");
		form.setFetalHeartRate("120");
		new EditObstetricsVisitValidator().validate(form);
	}

	/**
	 * test errors made on patients
	 * @throws Exception
	 */
	public void testPatientAllErrors() throws Exception {
		EditObstetricsVisitForm form = new EditObstetricsVisitForm();
		form.setHcpID("99L");
		form.setPatientID("a309");
		form.setVisitDate("09.09.1982");
		form.setDaysPregnant("t5");
		form.setWeeksPregnant("-5");
		form.setFundalHeightOfUterus("454545.545435");
		form.setFetalHeartRate("-400");
		try {
			new EditObstetricsVisitValidator().validate(form);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("HCP ID: " + ValidationFormat.MID.getDescription(), e.getErrorList().get(0));
			assertEquals("Patient ID: " + ValidationFormat.MID.getDescription(), e.getErrorList().get(1));
			assertEquals("Visit Date: " + ValidationFormat.DATE.getDescription(), e.getErrorList().get(2));
			assertEquals("Weeks Pregnant: " + ValidationFormat.WEEKS_PREGNANT.getDescription(), e.getErrorList().get(3));
			assertEquals("Days Pregnant: " + ValidationFormat.DAYS_PREGNANT.getDescription(), e.getErrorList().get(4));
			assertEquals("Fetal Heart Rate: " + ValidationFormat.FETAL_HEART_RATE.getDescription(), e.getErrorList().get(5));
			assertEquals("Fundal Height of Uterus: " + ValidationFormat.FUNDAL_HEIGHT_OF_UTERUS.getDescription(), e.getErrorList().get(6));
			assertEquals("number of errors", 7, e.getErrorList().size());
		}
	}
}
