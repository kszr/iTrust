package edu.ncsu.csc.itrust.beans;

import java.sql.Timestamp;



public class ViewTransactionBean {

	private String loggedInMID;
	private String secondaryMID;
	private String transactionType;
	private String startDate;
	private String endDate;
	
	public ViewTransactionBean(String loggedInMID, String secondaryMID,
			String transactionType, String startDate, String endDate) {
		super();
		this.loggedInMID = loggedInMID;
		this.secondaryMID = secondaryMID;
		this.transactionType = transactionType;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public String getLoggedInMID() {
		return loggedInMID;
	}

	public void setLoggedInMID(String loggedInMID) {
		this.loggedInMID = loggedInMID;
	}

	public String getSecondaryMID() {
		return secondaryMID;
	}

	public void setSecondaryMID(String secondaryMID) {
		this.secondaryMID = secondaryMID;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	
	

}
