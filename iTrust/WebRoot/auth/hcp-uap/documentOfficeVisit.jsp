<%@page import="edu.ncsu.csc.itrust.beans.ObstetricsVisitBean"%>
<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.action.AddOfficeVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.action.AddObstetricsVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.OfficeVisitBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.ObstetricsVisitBean"%>

<%@include file="/global.jsp"%>

<%
	String visitName = "Office Visit";
	if (userRole.equals("er")) {
		visitName = "ER Visit";
	}
	pageTitle = "iTrust - Document " + visitName;
%>

<%@include file="/header.jsp"%>
<itrust:patientNav thisTitle="Document Office Visit" />
<%
	/* Require a Patient ID first */
	String pidString = (String) session.getAttribute("pid");
	if (pidString == null || 1 > pidString.length()) {
		response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=/iTrust/auth/hcp-uap/documentOfficeVisit.jsp");
		return;
	}

	AddOfficeVisitAction officeAction = new AddOfficeVisitAction(prodDAO,
			pidString);
	AddObstetricsVisitAction obstetricsAction = new AddObstetricsVisitAction(prodDAO, pidString);
	long pid = officeAction.getPid();
	List<OfficeVisitBean> officeVisits = officeAction.getAllOfficeVisits();
	List<ObstetricsVisitBean> obstetricsVisits = obstetricsAction.getAllObstetricsVisits();
	if ("true".equals(request.getParameter("formIsFilled"))) {
		if (request.getParameter("officeVisit") != null) {
			response.sendRedirect("/iTrust/auth/hcp-uap/editOfficeVisit.jsp");
		} else if (request.getParameter("obstetricsVisit") != null) {
			response.sendRedirect("/iTrust/auth/hcp-uap/editObstetricsVisit.jsp");
		}
		return;
	}
%>

<div align=center>
	<form action="documentOfficeVisit.jsp" method="post" id="formMain">
		<input type="hidden" name="formIsFilled" value="true" /> <br /> <br />
		Are you sure you want to document a <em>new</em> visit for <b><%=StringEscapeUtils.escapeHtml("" + (officeAction.getUserName()))%></b>?<br />
		<br /> <input style="font-size: 150%; font-weight: bold;" type=submit
			name=officeVisit value="Yes, Document <%=visitName%>">
		<br /> <input style="font-size: 150%; font-weight: bold;" type=submit
			name=obstetricsVisit value="Yes, Document Obstetrics Visit">
	</form>
	<br /> Click on an old office visit to modify:<br />
	<%
		for (OfficeVisitBean ov : officeVisits) {
	%>
	<%
		if (ov.isERIncident()) {
	%>
	<a
		href="/iTrust/auth/hcp-uap/editOfficeVisit.jsp?ovID=<%=StringEscapeUtils.escapeHtml("" + (ov.getID()))%>"><%=StringEscapeUtils.escapeHtml("ER: "
							+ (ov.getVisitDateStr()))%></a><br />
	<%
		} else {
				if (!userRole.equals("er")) {
	%>
	<a
		href="/iTrust/auth/hcp-uap/editOfficeVisit.jsp?ovID=<%=StringEscapeUtils.escapeHtml(""
								+ (ov.getID()))%>"><%=StringEscapeUtils.escapeHtml(""
								+ (ov.getVisitDateStr()))%></a><br />
	<%
				}
			}
	%>
	<%
		}
	%>
	
	<br /> Click on an old obstetrics visit to modify:<br />
	<%
		for (ObstetricsVisitBean ov : obstetricsVisits) {
			if (!userRole.equals("er")) {
	%>
	<a
				href="/iTrust/auth/hcp-uap/editObstetricsVisit.jsp?ovID=<%=StringEscapeUtils.escapeHtml(""
								+ (ov.getID()))%>"><%=StringEscapeUtils.escapeHtml(""
								+ (ov.getVisitDateStr()))%></a><br />
	<%
			}
		}
	%>
	<br /> <br /> <br />
</div>
<%@include file="/footer.jsp"%>
