<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.beans.DiagnosisBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.ICDCodesDAO"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewCauseOfDeathTrendsReportAction"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - View Cause of Death Trends Report";
%>

<%@include file="/header.jsp"%>

<%

ViewCauseOfDeathTrendsReportAction action = new ViewCauseOfDeathTrendsReportAction(
		prodDAO, loggedInMID.longValue());
ICDCodesDAO icdDAO = new ICDCodesDAO(prodDAO);
//loggingAction.logEvent(TransactionType.PATIENT_LIST_VIEW,	loggedInMID, 0, "");

	//get form data
	String startDate = request.getParameter("startDate");
	String endDate = request.getParameter("endDate");

	if (startDate == null)
		startDate = "";
	if (endDate == null)
		endDate = "";
	
	//try to get the statistics. If there's an error, print it. If null is returned, it's the first page load
if (!startDate.equals("") || !endDate.equals("")) {
	try {
		List<PatientBean> patientDeceased = action.getDeathStatistics(startDate, endDate);
	} catch (FormValidationException e) {
		e.printHTML(pageContext.getOut());
	}
}
%>	

<br />
<form action="viewCauseOfDeathTrendsReport.jsp" method="post" id="formMain">
	<input type="hidden" name="viewSelect" value="trends" />
	<table class="fTable" align="center"

		<tr class="subHeader">
			<td>Start Date:</td>
			<td><input name="startDate"
				value="<%=StringEscapeUtils.escapeHtml("" + (startDate))%>"
				size="10"> <input type=button value="Select Date"
				onclick="displayDatePicker('startDate');"></td>
			<td>End Date:</td>
			<td><input name="endDate"
				value="<%=StringEscapeUtils.escapeHtml("" + (endDate))%>"
				size="10"> <input type=button value="Select Date"
				onclick="displayDatePicker('endDate');"></td>
		</tr>
		<tr>
			<td colspan="4" style="text-align: center;"><input type="submit"
				id="select_diagnosis" value="View Statistics"></td>
		</tr>
		
	</table>


</form>
	



<br />

