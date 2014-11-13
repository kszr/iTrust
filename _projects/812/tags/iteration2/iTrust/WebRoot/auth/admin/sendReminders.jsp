<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@page import="java.net.URLEncoder" %>
<%@page import="java.util.List"%>

<%@page import="java.util.ArrayList" %>

<%@page import="edu.ncsu.csc.itrust.*"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.action.SendRemindersAction"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>

<%@include file="/global.jsp" %>
<% pageTitle = "iTrust - Send Reminders"; %>
<%@include file="/header.jsp"%>
<%
	String daysInAdvance = request.getParameter("daysInAdvance");
	
	/* If the patient id doesn't check out, then kick 'em out to the exception handler */
	SendRemindersAction action = new SendRemindersAction(prodDAO, loggedInMID.longValue());
	
	if (daysInAdvance != null && daysInAdvance != "") {
		action.sendReminders(Integer.parseInt(daysInAdvance));
%>
	<br />
	<div align=center>
		<span class="iTrustMessage">Sent a reminder to patients with an appointment within <%= daysInAdvance %> days.</span>
	</div>
	<br />
<%
	}
%>
<div align=center>
	<form id="mainForm" method="post" action="sendReminders.jsp">
		Reminder-in-advance Days: 
		<input type="text" name="daysInAdvance" /><br>
		<input type="submit" value="Send Appointment Reminders" name="sendReminders" />
	</form>
</div>
<br>
<br>
<%--
<%PersonnelBean personnelb = new PersonnelDAO(prodDAO).getPersonnel(loggedInMID);
%>

<div align="center" style="margin-bottom: 30px;">
	<img src="/iTrust/image/user/<%= StringEscapeUtils.escapeHtml("" + (loggedInMID.longValue() )) %>.png" alt="MID picture">
</div>

<div align="center">
<table width="165">
	<tr>
		<td>Name: </td>
		<td><%= StringEscapeUtils.escapeHtml("" + (personnelb.getFullName())) %></td>
	</tr>
	<tr>
		<td>Location: </td>
		<td>Somewhere</td>
	</tr>
</table>
</div>
--%>

<%@include file="/footer.jsp"%>
