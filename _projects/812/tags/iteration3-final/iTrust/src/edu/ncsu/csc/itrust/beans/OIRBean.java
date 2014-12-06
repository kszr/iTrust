package edu.ncsu.csc.itrust.beans;

import java.util.Date;

/**
 * A bean for storing data about a female patient's obstetrics initialization record.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters� 
 * to create these easily)
 */
public class OIRBean {
	private static long oneDay = 24*60*60*1000; // hours*minutes*seconds*milliseconds
	
	private long recordID = 0;
	private long patientID = 0;
	private Date creationDate = null;
	private Date LMP = null;
	
	public long getPatientID() {
		return patientID;
	}
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}
	public long getRecordID() {
		return recordID;
	}
	public void setRecordID(long recordID) {
		this.recordID = recordID;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLMP() {
		return LMP;
	}
	public void setLMP(Date lMP) {
		LMP = lMP;
	}
	
	public Date getEDD(){
		return new Date(LMP.getTime() + oneDay * 280);
	}
	
	public double getWeeksPregnant(){
		long diffTime = Math.abs(creationDate.getTime() - LMP.getTime());
		long diffDays = diffTime/oneDay;
		double diffWeeks = Math.floor(diffDays/7);
		return diffWeeks;
	}

}
