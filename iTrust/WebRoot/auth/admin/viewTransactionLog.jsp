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
<option value="fiat">Fiat</option>
<option value="audi">Audi</option>
</select></p>

<p>Secondary user
<select name="logged_in_role">
<option value="volvo">Volvo</option>
<option value="saab">Saab</option>
<option value="fiat">Fiat</option>
<option value="audi">Audi</option>
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
		<p>Transcation Type <select name="logged_in_role">
<option value="volvo">Volvo</option>
<option value="saab">Saab</option>
<option value="fiat">Fiat</option>
<option value="audi">Audi</option>
</select></p><br>
<p><input type="submit"  value="View">
<input type="submit"  value="Summarize">
</p>

</form>
</div>

<%@include file="/footer.jsp" %>
