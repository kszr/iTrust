
<%@page import="java.util.ArrayList"%>
<%@page import="edu.ncsu.csc.itrust.beans.ViewTransactionBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewTransactionAction"%>


<%@page import="edu.ncsu.csc.itrust.beans.TransactionBean"%>
<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewTransactionAction"%>
<%@page import="java.io.*, java.util.Date,java.util.Calendar, java.util.Enumeration,java.sql.Timestamp" %> 




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


ViewTransactionBean bean = new ViewTransactionBean(loggedUser,secondaryUser,type,startDate,endDate);


//ViewTransactionAction action = new ViewTransactionAction();
ViewTransactionAction action = new ViewTransactionAction(prodDAO);
List<ViewTransactionBean> transactionList = new ArrayList<ViewTransactionBean>();
transactionList = action.getTransactionView(bean);
System.out.println(transactionList.size());



%>
<%@include file="/header.jsp" %>


<h2 align="center">Transaction Log Table</h2>

<table border = "1" style="width:100%">
<tr>

<th>Logged In Role</th>
<th>Secondary Role</th>
<th>Transaction Type Name</th>
<th>Additional Info</th>
<th>TimeStamp</th>

</tr>
 
<%
for(int i =0;i<transactionList.size();i++)
{
	
	%>
	 <tr>
	<td><% out.print(transactionList.get(i).getLoggedInRole()); %></td>
    <td><% out.print(transactionList.get(i).getSecondaryRole()); %></td> 
    <td><% out.print(transactionList.get(i).getTransactionType()); %></td>
	    <td><% out.print(transactionList.get(i).getAdditionalInfo()); %></td>
  
    <td><%out.print( transactionList.get(i).getTimestamp().toString()); %></td>
     </tr>
	<% 
}

%>
    
 
 
</table>


<%@include file="/footer.jsp" %>
