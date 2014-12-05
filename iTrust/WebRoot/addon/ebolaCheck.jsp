<%@page import="java.text.ParseException"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="edu.ncsu.csc.itrust.beans.ApptBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.ApptTypeBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.ApptTypeDAO"%>
<%@page import="edu.ncsu.csc.itrust.beans.HCPVisitBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewVisitedHCPsAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>
<%@page import="edu.ncsu.csc.itrust.action.AddApptRequestAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.ApptRequestBean"%>
<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.action.CheckEbolaAction"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Ebola Check";
%>

<%@include file="/header.jsp"%>
<% 
String countries = "";
if (request.getParameter("request") != null) {

	countries = request.getParameter("countries");
	
	CheckEbolaAction ebolaAction = new CheckEbolaAction(countries);
	int ebolaRisk = ebolaAction.checkEbolaRisk();
	
	if(ebolaRisk>0){
		loggingAction.logEvent(TransactionType.PATIENT_EBOLA_RISK, 0, 0, "Non-registered Patient");
		%>
		<span class="iTrustError">!!!!THE PATIENT IS AT RISK. Please follow protocol !!!!</span>
		<% 
	}
	else
	{
		%>
		<span class="iTrustError">The patient has no risk</span>
		<% 
	}

	
	
}
%>
<h1>Check Ebola Risk</h1>

<form action="ebolaCheck.jsp" method="post" name="ebolaForm">
<p>Please List Countries You Have Been Traveled to over The Past 30 days.</p> <br/>
	<p>(Sample Input Format : Thailand,Singapore,Vietnam)</p>
	 <textarea name="countries" cols="100" rows="10"></textarea>
	 <br /> <br /> <input type="submit" name="request" value="Request" />
</form>
<br /> <br />
 <a href="/iTrust/login.jsp">back</a> 

<%@include file="/footer.jsp"%>
