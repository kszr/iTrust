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
firstWeek = rt.requestBioTrend(bb,8);



%>

    <script type="text/javascript" src="https://www.google.com/jsapi"></script>

    <script type="text/javascript">
    google.load("visualization", "1.1", {packages:["bar"]});
    google.setOnLoadCallback(drawChart);
    function drawChart() {
      var data = google.visualization.arrayToDataTable([
        ['Weeks', 'Region', 'State', 'Nationwide'],
        ['1st', <% out.print("100"); %>, 400, 200],
        ['2nd', 1170, 460, 250],
        ['3rd', 660, 1120, 300],
        ['4th', 1030, 540, 350],
        ['5th', 1000, 400, 200],
        ['6th', 1170, 460, 250],
        ['7th', 660, 1120, 300],
        ['8th', 1030, 540, 350]
      ]);

      var options = {
        chart: {
          title: 'Number of Cases Per Week'
         
        }
      };

      var chart = new google.charts.Bar(document.getElementById('columnchart_material'));

      chart.draw(data, options);
    }
  </script>

    <h1>Malaria Trend</h1>
    <div id="columnchart_material" style="width: 900px; height: 500px;"></div>


	

<br />
<br />
<br />

<%@include file="/footer.jsp" %>


