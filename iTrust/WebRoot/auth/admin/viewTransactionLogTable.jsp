<%@page import="java.util.ArrayList"%>
<%@page import="edu.ncsu.csc.itrust.beans.ViewTransactionBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewTransactionAction"%>


<%@page import="edu.ncsu.csc.itrust.beans.TransactionBean"%>
<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewTransactionAction"%>




<%@page import="java.util.List"%>



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

ViewTransactionAction action = new ViewTransactionAction();
//ViewTransactionAction action = new ViewTransactionAction(prodDAO);
List<TransactionBean> transactionList = new ArrayList<TransactionBean>();
transactionList = action.getTransactionView(bean);


%>
<%@include file="/header.jsp" %>


<h2 align="center">Transaction Log Table</h2>

<table border = "1" style="width:100%">
<tr>
<th>transactionID</th>
<th>loggedInMid</th>
<th>secondaryMID</th>
<th>transactionCode</th>
<th>timeLogged</th>
<th>addedInfo</th>

</tr>
 
<%
for(int i =0;i<transactionList.size();i++)
{
	
	%>
	 <tr>
	<td><% transactionList.get(i).getTransactionID(); %></td>
    <td><% transactionList.get(i).getLoggedInMID(); %></td> 
    <td><% transactionList.get(i).getSecondaryMID(); %></td>
     <td><% transactionList.get(i).getTransactionType().getCode(); %></td>
    <td><% transactionList.get(i).getTimeLogged(); %></td> 
    <td><% transactionList.get(i).getAddedInfo(); %></td>
     </tr>
	<% 
}

%>
    
 
 
</table>


<%@include file="/footer.jsp" %>
