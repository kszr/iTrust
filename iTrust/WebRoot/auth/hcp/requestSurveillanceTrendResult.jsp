<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.beans.BioSurveillanceBean"%>
<%@page
	import="edu.ncsu.csc.itrust.action.RequestBiosurveillanceTrendAction"%>
<%@page import="java.util.List"%>
<%@page import = "java.util.ArrayList" %>
<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - BioSurveillance Trend Result";
%>

<%@include file="/header.jsp" %>

<%
System.out.println("in new");

String date = request.getParameter("date");
String zipcode = request.getParameter("zipcode");
String diagcode = request.getParameter("diagcode");
System.out.println(date+zipcode+diagcode);

RequestBiosurveillanceTrendAction rt = new RequestBiosurveillanceTrendAction(prodDAO);

BioSurveillanceBean bb = new BioSurveillanceBean(
		diagcode, zipcode,
		date);

List<Integer> firstWeek = new ArrayList<Integer>();


%>


	

<br />
<br />
<br />

<%@include file="/footer.jsp" %>


