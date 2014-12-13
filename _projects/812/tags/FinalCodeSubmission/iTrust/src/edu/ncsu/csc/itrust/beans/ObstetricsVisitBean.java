package edu.ncsu.csc.itrust.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A bean for storing data about an obstetrics visit at the hospital.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
@SuppressWarnings("unused")
public class ObstetricsVisitBean {
	private long visitID = 0;
	private long patientID = 0;
	private long hcpID = 0;
	private String visitDateStr = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
	private int weeksPregnant = 0;
	private int daysPregnant = 0;
	private int fetalHeartRate = 0;
	private double fundalHeightOfUterus = 0;
	
	/**
	 * For use ONLY by DAOs
	 * setters and getters method
	 * @param visitID
	 */
	public ObstetricsVisitBean(long visitID) {
		this.visitID = visitID;
	}
	
	public ObstetricsVisitBean(){
		
	}
	
	public long getID() {
		return visitID;
	}
	
	public void setVisitID(long visitID) {
		this.visitID = visitID;
	}
	
	public long getPatientID() {
		return patientID;
	}
	
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}
	
	public long getHcpID() {
		return hcpID;
	}
	
	public void setHcpID(long hcpID) {
		this.hcpID = hcpID;
	}
	
	public String getVisitDateStr() {
		return visitDateStr;
	}
	
	public void setVisitDateStr(String visitDateStr) {
		this.visitDateStr = visitDateStr;
	}
	
	public Date getVisitDate() {
		Date d = null; 
		try {
			d = new SimpleDateFormat("MM/dd/yyyy").parse(visitDateStr);
		} catch (ParseException e) {
			System.out.println(e.toString());
		}
		
		return d;
	}
	
	public int getWeeksPregnant() {
		return weeksPregnant;
	}
	
	public void setWeeksPregnant(int weeksPregnant) {
		this.weeksPregnant = weeksPregnant;
	}
	
	public int getDaysPregnant() {
		return daysPregnant;
	}
	
	public void setDaysPregnant(int daysPregnant) {
		this.daysPregnant = daysPregnant;
	}
	
	public int getFetalHeartRate() {
		return fetalHeartRate;
	}
	
	public void setFetalHeartRate(int fetalHeartRate) {
		this.fetalHeartRate = fetalHeartRate;
	}
	
	public double getFundalHeightOfUterus() {
		return fundalHeightOfUterus;
	}
	
	public void setFundalHeightOfUterus(double fundalHeightOfUterus) {
		this.fundalHeightOfUterus = fundalHeightOfUterus;
	}
	
}