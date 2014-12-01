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
pageTitle = "iTrust - View Transaction Log";
%>

<%@include file="/header.jsp" %>


<h2 align="center">View Transaction Log</h2>
<% 
//System.out.println(request.getParameter("formIsFilled"));
TransactionBean tb = new TransactionBean();

if(request.getParameter("formIsFilled") != null && request.getParameter("formIsFilled").equals("true") )
{

	String date = request.getParameter("startDate");
	String endDate = request.getParameter("endDate");
	//error checking for date

	try{
	String[] brokenInput = date.split("/");
	Integer monthInt = Integer.parseInt(brokenInput[0]);
	Integer daysInt = Integer.parseInt(brokenInput[1]);
	Integer yearInt = Integer.parseInt(brokenInput[2]);		
	
	String[] brokenInput_endDate = endDate.split("/");
	Integer monthInt_endDate = Integer.parseInt(brokenInput_endDate[0]);
	Integer daysInt_endDate = Integer.parseInt(brokenInput_endDate[1]);
	Integer yearInt_endDate = Integer.parseInt(brokenInput_endDate[2]);	
	
	if(monthInt > 12 || monthInt < 1 ||monthInt_endDate > 12 || monthInt_endDate < 1 )
	{
		%>
		<div align=center>
			<span class="iTrustError">Invalid month. Month must be < 12 or >0 </span>
		</div>
		<%
	}
	
	else if(daysInt > 31 || daysInt < 1 ||daysInt_endDate > 31 || daysInt_endDate < 1)
	{
		%>
		<div align=center>
			<span class="iTrustError">Invalid date. </span>
		</div>
		<%
	}
	
	else{

	Date parseDate = new Date();
	try{

			Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
			Date finishDate = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);

			if(finishDate.before(startDate))
			{
				%>
						<div align=center>
			<span class="iTrustError">End Date must be before Start Date </span>
		</div>
				<% 
			}
			
			else{
			String logUser = request.getParameter("logged_in_role");
			String secondUser = request.getParameter("secondary_user");
			String type = request.getParameter("type");
			String site = new String();
			if(request.getParameter("option").equals("View"))
			{
			site = 
					"/iTrust/auth/admin/viewTransactionLogTable.jsp?loggeduser="
							+ logUser + "&secondaryuser="
							+ secondUser + "&startdate="
							+ date +"&enddate="
							+ endDate +  "&type=" + type;
			}
			else if (request.getParameter("option").equals("Summarize"))
			{
				site = new String(
						"/iTrust/auth/admin/viewTransactionLogChart.jsp?loggeduser="
								+ logUser + "&secondaryuser="
								+ secondUser + "&startdate="
								+ date +"&enddate="
								+ endDate +  "&type=" + type);
			}
			response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", site);
			}
			
	}
	catch(Exception e)
	{
		%>
		<div align=center>
			<span class="iTrustError">Please input date in the right format (MM/DD/YYYY)</span>
		</div>
		<%
	}}}
	catch(Exception e)
	{
		%>
		<div align=center>
			<span class="iTrustError">Please input date in the right format (MM/DD/YYYY) </span>
		</div>
		<%
	}
	
}

%>


<form action="viewTransactionLog.jsp" name="inputForm" method="post">
<input type="hidden" name="formIsFilled" value="true"><br />
<p>Logged in as 
<select name="logged_in_role">


<% 
out.print("<option value=  \"all\">  Show All Role </option>"); 
for(int i =0 ; i < Role.values().length; i++)
{
out.print("<option value= " + Role.values()[i] +"> " + Role.values()[i].getUserRolesString() +"</option>"); 
}

%>
</select></p>

<p>Secondary user
<select name="secondary_user">
<% 
out.print("<option value=  \"all\">  Show All Role </option>"); 
for(int i =0 ; i < Role.values().length; i++)
{
out.print("<option value= " + Role.values()[i] +"> " + Role.values()[i].getUserRolesString() +"</option>"); 
}

%>
</select></p>
<p>
Start Date
	<input required name="startDate"
		value="" placeholder="mm/dd/yyyy">
	<input type=button value="Select Date"
		onclick="displayDatePicker('startDate');">
		</p>
		<p>
End Date
	<input required name="endDate"
		value="" placeholder="mm/dd/yyyy" >
	<input type=button value="Select Date" 
		onclick="displayDatePicker('endDate');">
		</p>
		
<p>Transaction Type <select name="type">
<% 
out.print("<option value=  \"all\">  Show All Transaction Type </option>"); 

for(int i =0 ; i < TransactionType.values().length; i++)
{
out.print("<option value= " + TransactionType.values()[i] +"> " + TransactionType.values()[i] +"</option>"); 
}

%>

</select></p><br>
<p><input type="submit" name="option"  value="View">
<input type="submit" name="option"  value="Summarize">
</p>

</form>
</div>

<%@include file="/footer.jsp" %>
