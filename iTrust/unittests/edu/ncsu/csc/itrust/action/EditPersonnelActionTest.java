package edu.ncsu.csc.itrust.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class EditPersonnelActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditPersonnelAction personnelEditor;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
	}

	public void testNotAuthorized() throws Exception {
		gen.standardData();
		try {
			personnelEditor = new EditPersonnelAction(factory, 9000000000L, "9000000003");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("You can only edit your own demographics!", e.getMessage());
		}
	}
	
	public void testNotAuthorized2() throws Exception {
		gen.standardData();
		try {
			personnelEditor = new EditPersonnelAction(factory, 9000000000L, "9000000001");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("You are not authorized to edit this record!", e.getMessage());
		}
	}
	
	public void testNonExistent() throws Exception {
		try {
			personnelEditor = new EditPersonnelAction(factory, 0L, "8999999999");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("Personnel does not exist", e.getMessage());
		}
	}

	public void testWrongFormat() throws Exception {
		try {
			gen.hcp0();
			personnelEditor = new EditPersonnelAction(factory, 0L, "hello!");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("Personnel ID is not a number: For input string: \"hello!\"", e.getMessage());
		}
	}

	public void testNull() throws Exception {
		try {
			gen.hcp0();
			personnelEditor = new EditPersonnelAction(factory, 0L, null);
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("Personnel ID is not a number: null", e.getMessage());
		}
	}
	
	public void testUpdateInformation() throws Exception {
		gen.uap1();
		personnelEditor = new EditPersonnelAction(factory, 8000000009L, "8000000009");
		PersonnelBean j = factory.getPersonnelDAO().getPersonnel(8000000009l);
//		j.setPassword("isntRetrieved");
//		j.setConfirmPassword("isntRetrieved");
		j.setStreetAddress2("second line");
		personnelEditor.updateInformation(j);
		j = factory.getPersonnelDAO().getPersonnel(8000000009l);
		assertEquals("second line", j.getStreetAddress2());
	}

	/**
	 * Tests whether the message filter successfully saved.
	 * 
	 * For some reason this fails... Keep working on it for Iteration 3
	 * @throws Exception
	 */
	public void testEditMessageFilter() throws Exception {
		gen.uap1();
		personnelEditor = new EditPersonnelAction(factory, 8000000009L, "8000000009");
		PersonnelBean j = factory.getPersonnelDAO().getPersonnel(8000000009L);
		assertEquals(",,,,,", j.getMessageFilter());
		String filter = "Bob,Cat,Bat,,,";
		personnelEditor.editMessageFilter(filter, 8000000009L);
		j = factory.getPersonnelDAO().getPersonnel(8000000009L);
		assertEquals("Bob,Cat,Bat,,,", j.getMessageFilter());
	}
	
	/**
	 * Tests the getMessageFilter() function in PatientDAO.
	 * @throws Exception
	 */
	public void testGetMessageFilter() throws Exception {
		gen.uap1();
		personnelEditor = new EditPersonnelAction(factory, 8000000009L, "8000000009");
		PersonnelBean j = factory.getPersonnelDAO().getPersonnel(8000000009L);
		assertEquals(",,,,,", j.getMessageFilter());
		String filter = "Bob,Cat,Bat,,,";
		personnelEditor.editMessageFilter(filter, 8000000009L);
		j = factory.getPersonnelDAO().getPersonnel(8000000009L);
		assertEquals("Bob,Cat,Bat,,,", factory.getPersonnelDAO().getMessageFilter(8000000009L));
	}
	
	/**
	 * Tests the getMessageFilter() function in PatientDAO.
	 * @throws Exception
	 */
	public void testSetMessageFilter() throws Exception {
		gen.uap1();
		personnelEditor = new EditPersonnelAction(factory, 8000000009L, "8000000009");
		PersonnelBean j = factory.getPersonnelDAO().getPersonnel(8000000009L);
		assertEquals(",,,,,", j.getMessageFilter());
		String filter = "Bob,Cat,Bat,,,";
		factory.getPersonnelDAO().setMessageFilter(filter, 8000000009L);
		assertEquals("Bob,Cat,Bat,,,", factory.getPersonnelDAO().getMessageFilter(8000000009L));
		j = factory.getPersonnelDAO().getPersonnel(8000000009L);
		assertEquals("Bob,Cat,Bat,,,", j.getMessageFilter());
	}
}
