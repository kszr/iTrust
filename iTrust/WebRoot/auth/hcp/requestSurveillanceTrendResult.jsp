<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.action.EditMonitoringListAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.TelemedicineBean"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - BioSurveillance Trend Result";
%>

<%@include file="/header.jsp" %>

<%
System.out.println("in new");
System.out.println(request.getParameter("date"));
%>


	

<br />
<br />
<br />

<%@include file="/footer.jsp" %>


