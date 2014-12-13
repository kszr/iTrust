
<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.beans.DiagnosisBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.ICDCodesDAO"%>
<%@page
	import="edu.ncsu.csc.itrust.action.ViewCauseOfDeathTrendsReportAction"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Death Statistics</title>
</head>

<body>
<% //try to get the statistics. If there's an error, print it. If null is returned, it's the first page load
	try {
		List<PatientBean> patientDeceased = action.getDeathStatistics(startDate, endDate);
	} catch (FormValidationException e) {
		e.printHTML(pageContext.getOut());
	}
%>
</body>


</html>