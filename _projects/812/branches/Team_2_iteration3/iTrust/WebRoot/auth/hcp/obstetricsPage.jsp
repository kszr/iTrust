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
/* Require a Patient ID first */
String pidString = (String)session.getAttribute("pid");
String addOrRemove = "Add";
if (pidString == null || 1 > pidString.length() || "false".equals(request.getParameter("confirmAction"))) {
	response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp/obstetricsPage.jsp");
   	return;
}
%>
<% 
	/* If the patient id doesn't check out, then kick 'em out to the exception handler */
	EditPatientAction action = new EditPatientAction(prodDAO, loggedInMID.longValue(), pidString);


	PatientBean p = action.getPatient(); 
%>

<p>Female Patient:  <%= StringEscapeUtils.escapeHtml("" + (p.getLastName())) %>, <%= StringEscapeUtils.escapeHtml("" + (p.getFirstName())) %></p>

<table class="fTable" id="obstetricsInitTable">
    <tr>
        <th colspan="9">Current Obstetrics Intializations</th>
    </tr>
    <tr class = "subHeader" colspan="10">
    	<th>Creation Date</th>
    	<th>Last Menstrual Period (LMP)</th>
    	<th></th>
    </tr>
    <tr>
    <td>11/12/2014</td>
    <td>9/1/2014</td>
    <td><a href="">view</a></td>
    </tr>
</table>
<br/>

<p><a href="obstetricsInitializationCreation.jsp">Create a new obstetrics initilization record here.</a></p>


<br />
<br />
<br />

<%@include file="/footer.jsp" %>
