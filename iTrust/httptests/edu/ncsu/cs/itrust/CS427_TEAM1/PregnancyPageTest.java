package edu.ncsu.cs.itrust.CS427_TEAM1;

import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

public class PregnancyPageTest extends iTrustHTTPTest {

	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.OIR();
		gen.priorPregnancy();
	}
	
	public void testPregnancyCreation() throws Exception{
		
	}
}
