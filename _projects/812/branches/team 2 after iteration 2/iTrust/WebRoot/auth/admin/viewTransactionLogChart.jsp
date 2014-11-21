
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



ViewTransactionAction action = new ViewTransactionAction(prodDAO);
//List<ViewTransactionBean> transactionList = new ArrayList<ViewTransactionBean>();
//transactionList = action.getTransactionView(bean);
//System.out.println(transactionList.size());



%>
<%@include file="/header.jsp" %>

 <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart);
function drawChart() {

  var data = google.visualization.arrayToDataTable([
    ['Role', 'Number of Transaction'],
    ['Admin',  1000],
    ['HCP',  1000]
  ]);

  var options = {
    title: 'Number of Transaction vs Logged-In Role',
    hAxis: {title: 'Logged-In Role', titleTextStyle: {color: 'red'}
    }
  };
  
  

  var chart = new google.visualization.ColumnChart(document.getElementById('logged_in_chart'));

  chart.draw(data, options);
  
  //second chart
  
  var data = google.visualization.arrayToDataTable([
                                                    ['Role', 'Number of Transaction'],
                                                    ['Admin',  1000],
                                                    ['HCP',  1000]
                                                  ]);

  var options = {
	 title: 'Number of Transaction vs Secondary Role',
	hAxis: {title: 'Secondary Role', titleTextStyle: {color: 'red'}
                                                    }
                                                  };
  
  var chart = new google.visualization.ColumnChart(document.getElementById('secondary_chart'));

  chart.draw(data, options);
  
  //third chart
  var data = google.visualization.arrayToDataTable([
                                                    ['Month', 'Number of Transaction'],
                                                    ['10-2014',  1000],
                                                    ['11-2014',  1000]
                                                  ]);

  var options = {
	 title: 'Number of Transaction vs Months',
	hAxis: {title: 'Months', titleTextStyle: {color: 'red'}
                                                    }
                                                  };
  
  var chart = new google.visualization.ColumnChart(document.getElementById('month_chart'));

  chart.draw(data, options);
  
  //fouth chart
  var data = google.visualization.arrayToDataTable([
                                                    ['Month', 'Number of Transaction'],
                                                    ['10-2014',  1000],
                                                    ['11-2014',  1000]
                                                  ]);

  var options = {
	 title: 'Number of Transaction vs Transaction Type',
	hAxis: {title: 'Months', titleTextStyle: {color: 'red'}
                                                    }
                                                  };
  
  var chart = new google.visualization.ColumnChart(document.getElementById('type_chart'));

  chart.draw(data, options);
  

}
    </script>
<h2 align="center">Summary</h2>


 
 <div id="logged_in_chart" style="width: 900px; height: 500px;"></div>
 <div id="secondary_chart" style="width: 900px; height: 500px;"></div>
 <div id="month_chart" style="width: 900px; height: 500px;"></div>
 <div id="type_chart" style="width: 900px; height: 500px;"></div>
<%@include file="/footer.jsp" %>
