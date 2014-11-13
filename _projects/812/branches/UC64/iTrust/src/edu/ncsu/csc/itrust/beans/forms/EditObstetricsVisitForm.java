package edu.ncsu.csc.itrust.beans.forms;

/**
 * A form to contain data coming from editing an obstetrics visit.
 * 
 * A form is a bean, kinda. You could say that it's a form of a bean :) 
 * Think of a form as a real-life administrative form that you would fill out to get 
 * something done, not necessarily making sense by itself.
 */
public class EditObstetricsVisitForm {
	private String ovID;
	private String hcpID;
	private String patientID;
	private String visitDate;
	private String weeksPregnant;
	private String daysPregnant;
	private String fetalHeartRate;
	private String fundalHeightOfUterus;
	
	/**
	 * @return the ovID
	 */
	public String getOvID() {
		return ovID;
	}
	/**
	 * @param ovID the ovID to set
	 */
	public void setOvID(String ovID) {
		this.ovID = ovID;
	}
	/**
	 * @return the hcpID
	 */
	public String getHcpID() {
		return hcpID;
	}
	/**
	 * @param hcpID the hcpID to set
	 */
	public void setHcpID(String hcpID) {
		this.hcpID = hcpID;
	}
	/**
	 * @return the patientID
	 */
	public String getPatientID() {
		return patientID;
	}
	/**
	 * @param patientID the patientID to set
	 */
	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}
	/**
	 * @return the visitDate
	 */
	public String getVisitDate() {
		return visitDate;
	}
	/**
	 * @param visitDate the visitDate to set
	 */
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
	/**
	 * @return the weeksPregnant
	 */
	public String getWeeksPregnant() {
		return weeksPregnant;
	}
	/**
	 * @param weeksPregnant the weeksPregnant to set
	 */
	public void setWeeksPregnant(String weeksPregnant) {
		this.weeksPregnant = weeksPregnant;
	}
	/**
	 * @return the daysPregnant
	 */
	public String getDaysPregnant() {
		return daysPregnant;
	}
	/**
	 * @param daysPregnant the daysPregnant to set
	 */
	public void setDaysPregnant(String daysPregnant) {
		this.daysPregnant = daysPregnant;
	}
	/**
	 * @return the fetalHeartRate
	 */
	public String getFetalHeartRate() {
		return fetalHeartRate;
	}
	/**
	 * @param fetalHeartRate the fetalHeartRate to set
	 */
	public void setFetalHeartRate(String fetalHeartRate) {
		this.fetalHeartRate = fetalHeartRate;
	}
	/**
	 * @return the fundalHeightOfUterus
	 */
	public String getFundalHeightOfUterus() {
		return fundalHeightOfUterus;
	}
	/**
	 * @param fundalHeightOfUterus the fundalHeightOfUterus to set
	 */
	public void setFundalHeightOfUterus(String fundalHeightOfUterus) {
		this.fundalHeightOfUterus = fundalHeightOfUterus;
	}
}
