<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.action.ViewMyMessagesAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.MessageBean"%>
<%@page import="java.util.List"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Sent Reminder";
%>

<%@include file="/header.jsp" %>

<%
	loggingAction.logEvent(TransactionType.OUTBOX_VIEW, loggedInMID.longValue(), 0, "");
	
	ViewMyMessagesAction action = new ViewMyMessagesAction(prodDAO, 0);
	MessageBean original = null;

	if (request.getParameter("msg") != null) {
		String msgParameter = request.getParameter("msg");
		int msgIndex = 0;
		try {
			msgIndex = Integer.parseInt(msgParameter);
		} catch (NumberFormatException nfe) {
			response.sendRedirect("remindersOutbox.jsp");
		}
		List<MessageBean> messages = null; 
		if (session.getAttribute("messages") != null) {
			messages = (List<MessageBean>) session.getAttribute("reminders");
			if(msgIndex > messages.size() || msgIndex < 0) {
				msgIndex = 0;
				response.sendRedirect("oops.jsp");
			}
		} else {
			response.sendRedirect("remindersOutbox.jsp");
		}
		original = (MessageBean)messages.get(msgIndex);
		session.setAttribute("message", original);
	}
	else {
		response.sendRedirect("remindersOutbox.jsp");
	}
	
%>
	<div>
		<table width="99%">
			<tr>
				<td><b>To:</b> <%= StringEscapeUtils.escapeHtml("" + ( action.getName(original.getTo()) )) %></td>
			</tr>
			<tr>
				<td><b>Subject:</b> <%= StringEscapeUtils.escapeHtml("" + ( original.getSubject() )) %></td>
			</tr>
			<tr>
				<td><b>Date &amp; Time:</b> <%= StringEscapeUtils.escapeHtml("" + ( original.getSentDate() )) %></td>
			</tr>
		</table>
	</div>
	
	<table>
		<tr>
			<td colspan="2"><b>Message:</b></td>
		</tr>
		<tr>
			<td colspan="2"><%= StringEscapeUtils.escapeHtml("" + ( original.getBody() )).replace("\n","<br/>") %></td>
		</tr>
		<tr>
			<td colspan="2"><a href="remindersOutbox.jsp">Back</a></td>
		</tr>
	</table>


<%@include file="/footer.jsp" %>
