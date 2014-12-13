package edu.ncsu.csc.itrust.beans;

/**
 * A bean for storing data about a female patient's prior pregnancy.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters� 
 * to create these easily)
 */
public class PregnancyBean {
	public enum DeliveryType{
		VAGINAL_DELIVERY{
			public String toString(){
				return "Vaginal Delivery";
			}
		},
		CAESAREAN_SECTION{
			public String toString(){
				return "Caesarean Section";
			}
		},
		MISCARRIAGE{
			public String toString(){
				return "Miscarriage";
			}
		}
	}
	private DeliveryType deliveryType = null;
	
	private long pregnancyID = 0;
	private long patientID = 0;
	private int yearOfContraception = 0;
	private int numberOfDaysPregnant = 0;
	private int numberOfWeeksPregnant = 0;
	private double hoursInLabor = 0;

	public long getPregnancyID(){
		return pregnancyID;
	}
	
	public void setPregnancyID(long pregID){
		this.pregnancyID = pregID;
	}
	
	public long getPatientID() {
		return patientID;
	}

	public void setPatientID(long patient_id) {
		this.patientID = patient_id;
	}

	public int getYearOfContraception() {
		return yearOfContraception;
	}

	public void setYearOfContraception(int yearOfContraception) {
		this.yearOfContraception = yearOfContraception;
	}

	public int getNumberOfDaysPregnant() {
		return numberOfDaysPregnant;
	}

	public void setNumberOfDaysPregnant(int numberOfDaysPregnant) {
		this.numberOfDaysPregnant = numberOfDaysPregnant;
	}
	
	public int getNumberOfWeeksPregnant() {
		return numberOfWeeksPregnant;
	}

	public void setNumberOfWeeksPregnant(int numberOfWeeksPregnant) {
		this.numberOfWeeksPregnant = numberOfWeeksPregnant;
	}

	public double getHoursInLabor() {
		return hoursInLabor;
	}

	public void setHoursInLabor(double hoursInLabor) {
		this.hoursInLabor = hoursInLabor;
	}

	public DeliveryType getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}
	
	public void setDeliveryType(String deliveryType){
		if(deliveryType.equals(DeliveryType.VAGINAL_DELIVERY.toString())){
			this.deliveryType = DeliveryType.VAGINAL_DELIVERY;
			
		}else if(deliveryType.equals(DeliveryType.CAESAREAN_SECTION.toString())){
			this.deliveryType = DeliveryType.CAESAREAN_SECTION;
			
		}else if(deliveryType.equals(DeliveryType.MISCARRIAGE.toString())){
			this.deliveryType = DeliveryType.MISCARRIAGE;
			
		}
	}
	
	public String getWeeksAndDaysPregnantString(){
		return "" + this.getNumberOfWeeksPregnant() + " weeks and " + this.getNumberOfDaysPregnant() + " days";
	}
	
}
