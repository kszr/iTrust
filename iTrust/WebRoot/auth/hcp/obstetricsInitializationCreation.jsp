<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditMonitoringListAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.TelemedicineBean"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Obstetrics Initialization";
%>

<%@include file="/header.jsp" %>

<% 
	String pidString = (String)session.getAttribute("pid");
	/* If the patient id doesn't check out, then kick 'em out to the exception handler */
	EditPatientAction action = new EditPatientAction(prodDAO, loggedInMID.longValue(), pidString);

	PatientBean p = action.getPatient(); 
%>

<p>Creating a new obstetrics initialization for:  <%= StringEscapeUtils.escapeHtml("" + (p.getLastName())) %>, <%= StringEscapeUtils.escapeHtml("" + (p.getFirstName())) %></p>

</br>
	<tr>
		<td class="subHeaderVertical">Last Menstural Period (LMP):</td>
		<td><input type=text name="LMPstring" maxlength="10" size="10"
			value=""> <input type=button value="Select Date"
			onclick="displayDatePicker('LMPstring');"></td>
	</tr>
	<br/>
	<br/>
	
	
	<table class="fTable" id="pregnancyTable">
    <tr class = "subHeader" colspan="10">
    	<th>Prior Pregnancies</th>
    	<th>Year of Contraception</th>
    	<th>Pregnancy Duration (weeks)</th>
    	<th>Time in labor (hours)</th>
    	<th>Pregnancy Result</th>
    </tr>
    <tr>
    	<td>Prior Pregnancy #1</td>
    	<td><input type=text name="conceptionYear" maxlength="10" size="10"
			value=""> <input type=button value="Select Date"
			onclick="displayDatePicker('conceptionYear');"></td>
    	<td><input type=text name="LMPstring" maxlength="10" size="10" value=""></td>
    	<td><input type=text name="LMPstring" maxlength="10" size="10" value=""></td>
    	<td><input type=text name="LMPstring" maxlength="10" size="10" value=""></td>
    </tr>
    <tr>
    	<td><a href="">add another prior pregnancy</a></td>
    	<td></td>
    	<td></td>
    	<td></td>
    	<td></td>
    </tr>
</table>

<%@include file="/footer.jsp" %>