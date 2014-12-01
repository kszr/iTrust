
<%@page import="java.util.ArrayList"%>
<%@page import="edu.ncsu.csc.itrust.beans.ViewTransactionBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewTransactionAction"%>


<%@page import="edu.ncsu.csc.itrust.beans.TransactionBean"%>
<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewTransactionAction"%>
<%@page
	import="java.io.*, java.util.Date,java.util.Calendar, java.util.Enumeration,java.sql.Timestamp,java.util.HashMap"%>





<%@page import="java.util.List,java.util.Iterator,java.util.Map"%>



<%@include file="/global.jsp"%>

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




ViewTransactionAction action = new ViewTransactionAction(prodDAO);
List<ViewTransactionBean> transactionList = new ArrayList<ViewTransactionBean>();
transactionList = action.getTransactionView(bean);
System.out.println(transactionList.size());

Boolean nullCheckFirst = false;
Boolean nullCheckSecond = false;
Boolean nullCheckThird = false;
Boolean nullCheckFourth = false;
//check for distinct 
HashMap first_graph_map = new HashMap();
first_graph_map = action.getDistinctLoggedUser(transactionList);
if(first_graph_map.size() == 0)
{
	nullCheckFirst = true;
}


HashMap second_graph_map = new HashMap();
second_graph_map = action.getDistinctSecondaryUser(transactionList);
if(second_graph_map.size() == 0)
{
	nullCheckSecond = true;
}

Map third_graph_map = new HashMap();
third_graph_map = action.getDateCount(transactionList);
if(third_graph_map.size() == 0)
{
	nullCheckThird = true;
}

HashMap fourth_graph_map = new HashMap();
fourth_graph_map = action.getTransactionTypeCount(transactionList);
if(fourth_graph_map.size() == 0)
{
	nullCheckFourth = true;
}



%>
<%@include file="/header.jsp"%>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart);
function drawChart() {

  var data = google.visualization.arrayToDataTable([
    ['Role', 'Number of Transaction'],
  <% 
  
  Iterator it = first_graph_map.entrySet().iterator();
  while (it.hasNext()) {

      Map.Entry pairs = (Map.Entry)it.next();
	  if(it.hasNext()==true){
      out.print("['" + pairs.getKey() + "', " + pairs.getValue()+"],");

    
	  }
	  else
	  {
		
      out.print("['" + pairs.getKey() + "', " + pairs.getValue()+"]");

	  }
      
     
      it.remove(); // avoids a ConcurrentModificationException
  }
  

  %>

  ]);

  var options = {
    title: 'Number of Transaction vs Logged-In Role',
    hAxis: {title: 'Logged-In Role', titleTextStyle: {color: 'red'}
    }
  };
  
  

  var chart = new google.visualization.ColumnChart(document.getElementById('logged_in_chart'));
<%
if(!nullCheckFirst){
%>
  chart.draw(data, options);
  <% }%>
  //second chart
  
  var data = google.visualization.arrayToDataTable([
                                                    ['Role', 'Number of Transaction'],
<%                                                   it = second_graph_map.entrySet().iterator();
                                                    while (it.hasNext()) {

                                                        Map.Entry pairs = (Map.Entry)it.next();
                                                  	  if(it.hasNext()==true){
                                                        out.print("['" + pairs.getKey() + "', " + pairs.getValue()+"],");

                                                      
                                                  	  }
                                                  	  else
                                                  	  {
                                                  		
                                                        out.print("['" + pairs.getKey() + "', " + pairs.getValue()+"]");

                                                  	  }
                                                        
                                                       
                                                        it.remove(); // avoids a ConcurrentModificationException
                                                    }
                                                    

                                                    %>
                                                  ]);

  var options = {
	 title: 'Number of Transaction vs Secondary Role',
	hAxis: {title: 'Secondary Role', titleTextStyle: {color: 'red'}
                                                    }
                                                  };
  
  var chart = new google.visualization.ColumnChart(document.getElementById('secondary_chart'));

  <%
  if(!nullCheckSecond){
  %>
    chart.draw(data, options);
    <% }%>
  
  //third chart
  var data = google.visualization.arrayToDataTable([
                                                    ['Month', 'Number of Transaction'],
<%                                                   it = third_graph_map.entrySet().iterator();
                                                    while (it.hasNext()) {

                                                        Map.Entry pairs = (Map.Entry)it.next();
                                                  	  if(it.hasNext()==true){
                                                        out.print("['" + pairs.getKey() + "', " + pairs.getValue()+"],");

                                                      
                                                  	  }
                                                  	  else
                                                  	  {
                                                  		
                                                        out.print("['" + pairs.getKey() + "', " + pairs.getValue()+"]");

                                                  	  }
                                                        
                                                       
                                                        it.remove(); // avoids a ConcurrentModificationException
                                                    }
                                                    

                                                    %>
         
                                                  ]);

  var options = {
	 title: 'Number of Transaction vs Months',
	hAxis: {title: 'Months', titleTextStyle: {color: 'red'}
                                                    }
                                                  };
  
  var chart = new google.visualization.ColumnChart(document.getElementById('month_chart'));

  <%
  if(!nullCheckThird){
  %>
    chart.draw(data, options);
    <% }%>
  
  //fouth chart
  var data = google.visualization.arrayToDataTable([
                                                    ['Month', 'Number of Transaction'],
                                                    <% 
                                                    
                                                    it = fourth_graph_map.entrySet().iterator();
                                                    while (it.hasNext()) {

                                                        Map.Entry pairs = (Map.Entry)it.next();
                                                  	  if(it.hasNext()==true){
                                                        out.print("['" + pairs.getKey() + "', " + pairs.getValue()+"],");

                                                      
                                                  	  }
                                                  	  else
                                                  	  {
                                                  		
                                                        out.print("['" + pairs.getKey() + "', " + pairs.getValue()+"]");

                                                  	  }
                                                        
                                                       
                                                        it.remove(); // avoids a ConcurrentModificationException
                                                    }
                                                    

                                                    %>
                                                  ]);

  var options = {
	 title: 'Number of Transaction vs Transaction Type',
	hAxis: {title: 'Transaction Type', titleTextStyle: {color: 'red'}
                                                    }
                                                  };
  
  var chart = new google.visualization.ColumnChart(document.getElementById('type_chart'));

  <%
  if(!nullCheckFourth){
  %>
    chart.draw(data, options);
    <% }%>
  

}
    </script>
<h2 align="center">Summary</h2>
<%
if(nullCheckFirst){

	out.print("<div>No transcation found for Number of Transaction vs Logged-In Role chart</div>");
   }
if(nullCheckSecond)
{
	out.print("<div>No transcation found for Number of Transaction vs Secondary Role chart</div>");
}
if (nullCheckThird)
{
	out.print("<div>No transcation found for Number of Transaction vs Months chart</div>");
}
 if(nullCheckFourth)
{
	out.print("<div>No transcation found for Number of Transaction vs Transaction Type chart</div>");
}
else {
%>


<div id="logged_in_chart" style="width: 900px; height: 500px;"></div>

<div id="secondary_chart" style="width: 900px; height: 500px;"></div>
<div id="month_chart" style="width: 900px; height: 500px;"></div>
<div id="type_chart" style="width: 900px; height: 500px;"></div>
<%} %>
<%@include file="/footer.jsp"%>
