<%@page import="edu.ncsu.csc.itrust.beans.ViewTransactionBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewTransactionAction"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@page import="edu.ncsu.csc.itrust.beans.TransactionBean"%>
<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.action.AddERespAction"%>
<%@page import="edu.ncsu.csc.itrust.enums.Role"%>
<%@page import="edu.ncsu.csc.itrust.enums.TransactionType"%>
<%@page import="java.io.*, java.util.Date, java.util.Enumeration,java.sql.Timestamp" %> 

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View Transaction Log Table";
%>

<%
String startDate = request.getParameter("startdate");
String endDate = request.getParameter("enddate");
String loggedUser = request.getParameter("loggeduser");
String secondaryUser = request.getParameter("secondaryuser");
String type = request.getParameter("type");

ViewTransactionBean bean = new ViewTransactionBean(loggedUser,secondaryUser,startDate,endDate,type);

ViewTransactionAction action = new ViewTransactionAction(prodDAO);
%>
<%@include file="/header.jsp" %>


<h2 align="center">Transaction Log Table</h2>



<%@include file="/footer.jsp" %>
