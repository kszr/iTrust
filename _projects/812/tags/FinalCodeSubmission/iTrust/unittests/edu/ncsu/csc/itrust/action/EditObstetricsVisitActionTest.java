package edu.ncsu.csc.itrust.action;

import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.beans.forms.EditObstetricsVisitForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * Test all obstetrics visit by doctors
 */
public class EditObstetricsVisitActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditObstetricsVisitAction action;
	private EditObstetricsVisitAction actionUC60; //UC60

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.hospitals();
		gen.hcp0();
		gen.patient1();
		gen.admin1();
		gen.obstetricsVisit1();
		gen.ndCodes();
		
		action = new EditObstetricsVisitAction(factory, 9000000001L, "1", "1");
	}

	
	/**
	 * testOVID
	 */
	public void testOVID() {
		try {
			action = new EditObstetricsVisitAction(factory, 0L, "1", "NaN");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("Obstetrics Visit ID is not a number: For input string: \"NaN\"", e.getMessage());
		}
	}

	/**
	 * testEvilDatabase
	 */
	public void testEvilDatabase() {
		try {
			action = new EditObstetricsVisitAction(EvilDAOFactory.getEvilInstance(), 0L, "1", "1");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals(
					"A database exception has occurred. Please see the log in the console for stacktrace", e
							.getMessage());
			DBException dbe = (DBException) e;
			assertEquals(EvilDAOFactory.MESSAGE, dbe.getSQLException().getMessage());
		}
	}

	/**
	 * testOVDoesntExist
	 */
	public void testOVDoesntExist() {
		try {
			action = new EditObstetricsVisitAction(TestDAOFactory.getTestInstance(), 0L, "1", "158");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("Obstetrics Visit 158 with Patient MID 1 does not exist", e.getMessage());
		}
	}

	/**
	 * Test patient obstetrics visit
	 * @throws ITrustException
	 */
	public void testGetObstetricsVisit() throws ITrustException {
		ObstetricsVisitBean ovb = action.getObstetricsVisit();
		assertEquals(1L, action.getOvID());
		assertEquals(9000000000L, ovb.getHcpID());
		assertEquals(1L, ovb.getID());
	}

	/**
	 * testUpdateInformationEmptyForm
	 */
	public void testUpdateInformationEmptyForm() {
		try {
			EditObstetricsVisitForm frm = new EditObstetricsVisitForm();
			action.updateInformation(frm, false);
			fail("should have thrown exception");
		} catch (FormValidationException fve) {
			//TODO
		}
	}

	/**
	 * Test if patient information is updated
	 * @throws FormValidationException
	 */
	public void testUpdateInformation() throws FormValidationException {
		EditObstetricsVisitForm frm = new EditObstetricsVisitForm();
		frm.setHcpID("9000000000");
		frm.setPatientID("1");
		frm.setVisitDate("05/02/2001");
		frm.setFetalHeartRate("120");
		frm.setFundalHeightOfUterus("23.1");
		frm.setDaysPregnant("3");
		frm.setWeeksPregnant("10");
		action.updateInformation(frm, false);
	}
	
	/**
	 * testUpdateInformationNewObstetricsVisit
	 * @throws Exception
	 */
	public void testUpdateInformationNewObstetricsVisit() throws Exception {
		action = new EditObstetricsVisitAction(factory, 9000000001L, "1");
		assertEquals(true, action.isUnsaved());
		assertEquals(-1, action.getOvID());
		EditObstetricsVisitForm frm = new EditObstetricsVisitForm();
		frm.setHcpID("9000000001");
		frm.setPatientID("1");
		frm.setVisitDate("05/02/2001");
		frm.setFetalHeartRate("120");
		frm.setFundalHeightOfUterus("23.1");
		frm.setDaysPregnant("3");
		frm.setWeeksPregnant("10");
		try {
			action.updateInformation(frm, false);
		} catch (FormValidationException e) {
			fail(e.getMessage());
		}
		assertEquals(false, action.isUnsaved());
		assertFalse(-1 == action.getOvID());
	}
}
