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

List<Integer> eighthWeek = new ArrayList<Integer>();
List<Integer> seventhWeek = new ArrayList<Integer>();
List<Integer> sixthWeek = new ArrayList<Integer>();
List<Integer> fifthWeek = new ArrayList<Integer>();
List<Integer> fouthWeek = new ArrayList<Integer>();
List<Integer> thirdWeek = new ArrayList<Integer>();
List<Integer> secondWeek = new ArrayList<Integer>();
List<Integer> firstWeek = new ArrayList<Integer>();

eighthWeek = rt.requestBioTrend(bb,1);
seventhWeek = rt.requestBioTrend(bb,2);
sixthWeek = rt.requestBioTrend(bb,3);
fifthWeek = rt.requestBioTrend(bb,4);
fouthWeek = rt.requestBioTrend(bb,5);
thirdWeek = rt.requestBioTrend(bb,6);
secondWeek = rt.requestBioTrend(bb,7);
firstWeek = rt.requestBioTrend(bb,8);



%>

    <script type="text/javascript" src="https://www.google.com/jsapi"></script>

    <script type="text/javascript">
    google.load("visualization", "1.1", {packages:["bar"]});
    google.setOnLoadCallback(drawChart);
    function drawChart() {
      var data = google.visualization.arrayToDataTable([
        ['Weeks', 'Region', 'State', 'Nationwide'],
        ['1st', <% out.print(firstWeek.get(0)); %>, <% out.print(firstWeek.get(1)); %>, <% out.print(firstWeek.get(2)); %>],
        ['2nd', <% out.print(secondWeek.get(0)); %>, <% out.print(secondWeek.get(1)); %>, <% out.print(secondWeek.get(2)); %>],
        ['3rd', <% out.print(thirdWeek.get(0)); %>, <% out.print(thirdWeek.get(1)); %>, <% out.print(thirdWeek.get(2)); %>],
        ['4th', <% out.print(fouthWeek.get(0)); %>, <% out.print(fouthWeek.get(1)); %>, <% out.print(fouthWeek.get(2)); %>],
        ['5th', <% out.print(fifthWeek.get(0)); %>, <% out.print(fifthWeek.get(1)); %>, <% out.print(fifthWeek.get(2)); %>],
        ['6th', <% out.print(sixthWeek.get(0)); %>, <% out.print(sixthWeek.get(1)); %>, <% out.print(sixthWeek.get(2)); %>],
        ['7th', <% out.print(seventhWeek.get(0)); %>, <% out.print(seventhWeek.get(1)); %>, <% out.print(seventhWeek.get(2)); %>],
        ['8th', <% out.print(eighthWeek.get(0)); %>, <% out.print(eighthWeek.get(1)); %>, <% out.print(eighthWeek.get(2)); %>]
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


