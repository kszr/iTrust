<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.beans.DiagnosisBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.ICDCodesDAO"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewListOfDeceasedPatientsAction"%>

<%@include file="/global.jsp" %>

<%
	pageTitle = "iTrust - View List of Deceased Patients";
%>

<%@include file="/header.jsp" %>

<%
	ViewListOfDeceasedPatientsAction action = new ViewListOfDeceasedPatientsAction(prodDAO, loggedInMID.longValue());
ICDCodesDAO icdDAO = new ICDCodesDAO(prodDAO);
List<PatientBean> patientDeceased = action.getDeceasedPatients();
loggingAction.logEvent(TransactionType.PATIENT_LIST_VIEW, loggedInMID, 0, "");
%>
			<script src="/iTrust/DataTables/media/js/jquery.dataTables.min.js" type="text/javascript"></script>
			<script type="text/javascript">
				jQuery.fn.dataTableExt.oSort['lname-asc']  = function(x,y) {
					var a = x.split(" ");
					var b = y.split(" ");
					return ((a[1] < b[1]) ? -1 : ((a[1] > b[1]) ?  1 : 0));
				};
				
				jQuery.fn.dataTableExt.oSort['lname-desc']  = function(x,y) {
					var a = x.split(" ");
					var b = y.split(" ");
					return ((a[1] < b[1]) ? 1 : ((a[1] > b[1]) ?  -1 : 0));
				};
			</script>
			<script type="text/javascript">	
   				$(document).ready(function() {
       				$("#deceasedList").dataTable( {
       					"aaColumns": [ [0,'dsc'] ],
       					"aoColumns": [ { "sType": "lname" }, null, null, null],
       					"bStateSave": true,
       					"sPaginationType": "full_numbers"
       				});
   				});
			</script>
			<style type="text/css" title="currentStyle">
				@import "/iTrust/DataTables/media/css/demo_table.css";		
			</style>

<br />
	<h2>Deceased Patients</h2>
<form action="viewListOfDeceasedPatients.jsp" method="post" name="myform">
<table class="display fTable" id="deceasedList" align="center">
	
	<thead>


	<tr class="">
		<th>Patient</th>
		<th>Gender</th>
		<th>Cause Of Death</th>
		<th>Date Of Death</th>

	</tr>
	</thead>
	<tbody>
	<%
		//List<PatientBean> patients = new ArrayList<PatientBean>();
		int index = 0;
		for (PatientBean bean : patientDeceased) {
	%>
	<tr>
		<td >
			
			<%= StringEscapeUtils.escapeHtml("" + (bean.getFullName())) %>	
	
			</td>
		<td ><%= StringEscapeUtils.escapeHtml("" + (bean.getGender())) %></td>
		<td ><%= StringEscapeUtils.escapeHtml("" + (icdDAO.getICDCode(bean.getCauseOfDeath())).getDescription()) %></td>
		<td ><%= StringEscapeUtils.escapeHtml("" + (bean.getDateOfDeathStr())) %></td>
	</tr>
	<%
			index ++;
		}
		//session.setAttribute("patients", patients);
	%>
	</tbody>
</table>
</form>
<br />
<br />

<%@include file="/footer.jsp" %>
