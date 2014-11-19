<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.action.AddERespAction"%>
<%@page import="edu.ncsu.csc.itrust.enums.Role"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View Transaction Log";
%>

<%@include file="/header.jsp" %>

<h2 align="center">View Transaction Log</h2>
<form action="viewTransactionLog.jsp" method="post">
<input type="hidden" name="formIsFilled" value="true"><br />
<p>Logged in as 
<select name="logged_in_role">
<option value="HCP">Health Care Personnel (HCP)</option>
<option value="patient">Patient</option>
<option value="administrator">Administrator</option>
<option value="LHCP">Licensed Health Care Professional (LHCP)</option>
<option value="DLHCP">Designated Licensed Health Care Professional (DLHCP)</option>
<option value="ER">Emergency Responder (ER)</option>
<option value="UAP">Unlicensed Authorized Personnel (UAP)</option>
<option value="software_tester">Software Tester</option>
<option value="personal_representative">Personal Representative</option>
<option value="PHA">Public Health Agent (PHA)</option>
<option value="LT">Lab Technician (LT)</option>
</select></p>

<p>Secondary user
<select name="logged_in_role">
<option value="HCP">Health Care Personnel (HCP)</option>
<option value="patient">Patient</option>
<option value="administrator">Administrator</option>
<option value="LHCP">Licensed Health Care Professional (LHCP)</option>
<option value="DLHCP">Designated Licensed Health Care Professional (DLHCP)</option>
<option value="ER">Emergency Responder (ER)</option>
<option value="UAP">Unlicensed Authorized Personnel (UAP)</option>
<option value="software_tester">Software Tester</option>
<option value="personal_representative">Personal Representative</option>
<option value="PHA">Public Health Agent (PHA)</option>
<option value="LT">Lab Technician (LT)</option>
</select></p>
<p>
Start Date
	<input name="startDate"
		value="" >
	<input type=button value="Select Date"
		onclick="displayDatePicker('startDate');">
		</p>
		<p>
End Date
	<input name="startDate"
		value="" >
	<input type=button value="Select Date"
		onclick="displayDatePicker('startDate');">
		</p>
		
<p>Transaction Type <select name="logged_in_role">
<option value="HCP">Health Care Personnel (HCP)</option>
<option value="patient">Patient</option>
<option value="administrator">Administrator</option>
<option value="LHCP">Licensed Health Care Professional (LHCP)</option>
<option value="DLHCP">Designated Licensed Health Care Professional (DLHCP)</option>
<option value="ER">Emergency Responder (ER)</option>
<option value="UAP">Unlicensed Authorized Personnel (UAP)</option>
<option value="software_tester">Software Tester</option>
<option value="personal_representative">Personal Representative</option>
<option value="PHA">Public Health Agent (PHA)</option>
<option value="LT">Lab Technician (LT)</option>
</select></p><br>
<p><input type="submit"  value="View">
<input type="submit"  value="Summarize">
</p>

</form>
</div>

<%@include file="/footer.jsp" %>
