package edu.ncsu.csc.itrust.beans;
/**
 * 
 * Store information about biosurveillance
 *
 */

public class BioSurveillanceBean {

	String diagnosisCode;
	String zipCode;
	String date;
	String threshold;
	/**
	 * Constructor for with threshold
	 * @param diagnosisCode
	 * @param zipCode
	 * @param date
	 */


	public BioSurveillanceBean(String diagnosisCode, String zipCode,
			String date, String threshold) {
		super();
		this.diagnosisCode = diagnosisCode;
		this.zipCode = zipCode;
		this.date = date;
		this.threshold = threshold;
	}

	/**
	 * Constructor for without threshold
	 * @param diagnosisCode
	 * @param zipCode
	 * @param date
	 */
	public BioSurveillanceBean(String diagnosisCode, String zipCode,
			String date) {

		this.diagnosisCode = diagnosisCode;
		this.zipCode = zipCode;
		this.date = date;
		this.threshold = "0";
	}
	
	/**
	 * 
	 * @return the diagnosisCode
	 */
	
	public String getDiagnosisCode() {
		return diagnosisCode;
	}
	/**
	 * 
	 * @param diagnosisCode
	 */
	public void setDiagnosisCode(String diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
	}
	/**
	 * 
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * 
	 * @param zipCode
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	/**
	 * 
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * 
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * 
	 * @return the threshold
	 */
	public String getThreshold() {
		return threshold;
	}

	/**
	 * 
	 * @param threshold
	 */
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	

}
