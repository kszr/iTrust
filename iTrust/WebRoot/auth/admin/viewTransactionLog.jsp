<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.action.AddERespAction"%>
<%@page import="edu.ncsu.csc.itrust.enums.Role"%>
<%@page import="edu.ncsu.csc.itrust.enums.TransactionType"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View Transaction Log";
%>

<%@include file="/header.jsp" %>

<h2 align="center">View Transaction Log</h2>
<form action="viewTransactionLog.jsp" method="post">
<input type="hidden" name="formIsFilled" value="true"><br />
<p>Logged in as 
<select name="logged_in_role">
<% 
for(int i =0 ; i < Role.values().length; i++)
{
out.print("<option value= " + Role.values()[i] +"> " + Role.values()[i].getUserRolesString() +"</option>"); 
}

%>
</select></p>

<p>Secondary user
<select name="secondary_user">
<% 
for(int i =0 ; i < Role.values().length; i++)
{
out.print("<option value= " + Role.values()[i] +"> " + Role.values()[i].getUserRolesString() +"</option>"); 
}

%>
</select></p>
<p>
Start Date
	<input name="startDate"
		value="" placeholder="mm/dd/yyyy">
	<input type=button value="Select Date"
		onclick="displayDatePicker('startDate');">
		</p>
		<p>
End Date
	<input name="endDate"
		value="" placeholder="mm/dd/yyyy" >
	<input type=button value="Select Date" 
		onclick="displayDatePicker('endDate');">
		</p>
		
<p>Transaction Type <select name="logged_in_role">
<% 
out.print("<option value=  \"all\">  Show All Transaction Type </option>"); 

for(int i =0 ; i < TransactionType.values().length; i++)
{
out.print("<option value= " + TransactionType.values()[i] +"> " + TransactionType.values()[i] +"</option>"); 
}

%>

</select></p><br>
<p><input type="submit"  value="View">
<input type="submit"  value="Summarize">
</p>

</form>
</div>

<%@include file="/footer.jsp" %>
