package edu.ncsu.csc.itrust.beans;

import java.sql.Timestamp;

import edu.ncsu.csc.itrust.enums.TransactionType;



public class ViewTransactionBean {

	private String role;
	private String loggedInRole;
	private String secondaryRole;
	private String transactionType;
	private long transactionID;
	private String startDate;
	private String endDate;
	private Timestamp timestamp;
	private String additionalInfo;
	

	public ViewTransactionBean(String loggedInRole, String secondaryRole,
			String transactionType, String startDate, String endDate) {
		super();
		this.loggedInRole = loggedInRole;
		this.secondaryRole = secondaryRole;
		this.transactionType = transactionType;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public ViewTransactionBean(String role, long transactionID,
			String transactionType, Timestamp timestamp, String additionalInfo) {
		super();
		this.role = role;
		this.transactionID = transactionID;
		this.transactionType = transactionType;
		this.timestamp = timestamp;
		this.additionalInfo = additionalInfo;
	}

	public ViewTransactionBean(String loggedInRole, String secondaryRole,
			String transactionType, Timestamp timestamp, String additionalInfo) {
		super();
		this.loggedInRole = loggedInRole;
		this.secondaryRole = secondaryRole;
		this.transactionType = transactionType;
		this.timestamp = timestamp;
		this.additionalInfo = additionalInfo;
	}
	
	public ViewTransactionBean() {
	}

	public long getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(long transactionID) {
		this.transactionID = transactionID;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	
	public Timestamp getTimestamp() {
		return (Timestamp) timestamp.clone();
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = (Timestamp) timestamp.clone();
	}
	
	public String getLoggedInRole() {
		return loggedInRole;
	}

	public void setLoggedInRole(String loggedInRole) {
		this.loggedInRole = loggedInRole;
	}

	public String getSecondaryRole() {
		return secondaryRole;
	}

	public void setSecondaryRole(String secondaryRole) {
		this.secondaryRole = secondaryRole;
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
