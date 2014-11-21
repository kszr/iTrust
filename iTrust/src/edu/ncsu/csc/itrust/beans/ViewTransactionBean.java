package edu.ncsu.csc.itrust.beans;

import java.sql.Timestamp;

import edu.ncsu.csc.itrust.enums.TransactionType;



public class ViewTransactionBean {

	private String loggedInRole;
	private String secondaryRole;
	private String transactionType;
	private String startDate;
	private String endDate;
	private Timestamp timestamp;
	

	public ViewTransactionBean(String loggedInRole, String secondaryRole,
			String transactionType, String startDate, String endDate) {
		super();
		this.loggedInRole = loggedInRole;
		this.secondaryRole = secondaryRole;
		this.transactionType = transactionType;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public ViewTransactionBean(String loggedInRole, String secondaryRole,
			String transactionType, Timestamp timestamp) {
		super();
		this.loggedInRole = loggedInRole;
		this.secondaryRole = secondaryRole;
		this.transactionType = transactionType;
		this.timestamp = timestamp;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
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
		String[] temp = startDate.split("/");
		String DataBaseDate = temp[2] + "/" + temp[0] + "/" + temp[1];
		
		return DataBaseDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		String[] temp = endDate.split("/");
		String DataBaseDate = temp[2] + "/" + temp[0] + "/" + temp[1];
		
		return DataBaseDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getCodeFromTransactionTypeName(String TransactionName) {
		
		return 0;
	}

}
