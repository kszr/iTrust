<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPrescriptionRenewalNeedsAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean" %>
<%@page import="edu.ncsu.csc.itrust.beans.PrescriptionBean" %>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean" %>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PatientDAO" %>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO" %>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Request BioSurveillance";
%>

<%@include file="/header.jsp"%>

<div align="center">
<h1><b>Request BioSurveillance</b></h1>
</div>
<div >
<h3><b>Epidemic Analysis Request</b></h3>
</div>
<div >

	<b>Diagnosis type</b>
	<br/>
	<form name="diagnosisForm" action="" method="get">
<input type="radio" name="disease" value="malaria">&nbsp;Malaria<br>
<input type="radio" name="disease" value="influenza">&nbsp;Influenza<br><br>
<b>Zip Code:</b> <input type="text" name="zipcode"><br><br>
<b>Date:</b><input type="text" name="date" placeholder="mm/dd/yyyy"><br><br>
<input type="submit" value="Submit">
</form>
</div>
<hr>
<div >
<h3><b>Examine Recent Trends in Diagnosis</b></h3>
</div>
<div>
<form name="diagnosisForm" action="" method="get">
<b>Diagnosis Code:</b> <input type="text" name="diagnosisCode"><br><br>
<b>Zip Code:</b> <input type="text" name="zipcode"><br><br>
<b>Date:</b><input type="text" name="date" placeholder="mm/dd/yyyy"><br><br>
<input type="submit" value="Submit">
</form>

</div>

<%@include file="/footer.jsp"%>
