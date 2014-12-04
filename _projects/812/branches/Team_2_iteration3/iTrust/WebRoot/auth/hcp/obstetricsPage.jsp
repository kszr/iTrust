<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.action.EditMonitoringListAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.TelemedicineBean"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Edit Patient List";
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

Female obstetrics patient selected.
	

<br />
<br />
<br />

<%@include file="/footer.jsp" %>
