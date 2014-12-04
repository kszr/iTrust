<%@page import="edu.ncsu.csc.itrust.action.RequestBioSurveillanceAnalysis"%>
<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.beans.BioSurveillanceBean"%>
<%@page import="edu.ncsu.csc.itrust.action.RequestBiosurveillanceTrend"%>


<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Request BioSurveillance";
%>

<%@include file="/header.jsp"%>

<%
	//check whether reach the page after POST by checking GET URL parameter
	//check for analysis Form (Top Form)
	boolean analysisFormIsFilled = request
			.getParameter("analysisFormIsFilled") != null
			&& request.getParameter("analysisFormIsFilled").equals(
					"true");
			
	//check for trend Form (Bottom Form)
	boolean trendFormIsFilled = request
			.getParameter("trendFormIsFilled") != null
			&& request.getParameter("trendFormIsFilled").equals("true");

	//IF redirected from analysis form submit
	if (analysisFormIsFilled) {
		try{
		System.out.println("in analysis");

		//variables for analysis
		String analysisDiagCode =  request
				.getParameter("analysisDiagnosisCode");;
		String analysisZipCode = request.getParameter("analysisZipCode");
		String analysisDate =  request.getParameter("analysisDate");
		String analysisThreshold = request.getParameter("analysisThreshold");

		if(analysisThreshold.isEmpty() && analysisDiagCode.contains("084.5"))
		{
			%>
<div align=center>
	<span class="iTrustError">Please input percentage threshold to analyze Malaria.</span>
</div>
<%
			
		}
			System.out.println("Need threshold for malaria");

		BioSurveillanceBean bb = new BioSurveillanceBean(analysisDiagCode,
				analysisZipCode, analysisDate,analysisThreshold);

		RequestBioSurveillanceAnalysis ra = new RequestBioSurveillanceAnalysis();

		if (ra.requestBioAnalysis(bb))
			System.out.println("success request analysis");
		else
			System.out.println("FAIL request analysis");
		}
		//print out the form validator
	catch(FormValidationException e){
			%>
<div align=center>
	<span class="iTrustError"><%=StringEscapeUtils.escapeHtml(e.getMessage()) %></span>
</div>
<%}

	}
	//IF redirected from trend form submit
	if (trendFormIsFilled) {
		try{
		System.out.println("in Analysis");



		//variables for trend
		String trendDiagCode = request
				.getParameter("trendDiagnosisCode");

		String trendZipCode = request.getParameter("trendZipCode");
		String trendDate = request.getParameter("trendDate");
		BioSurveillanceBean bb = new BioSurveillanceBean(trendDiagCode,
				trendZipCode, trendDate);

		RequestBiosurveillanceTrend rt = new RequestBiosurveillanceTrend();

		if (rt.requestBioTrend(bb))
			System.out.println("success request trend");
		else
			System.out.println("FAIL request trend");
		//print out form validate error
	}catch(FormValidationException e){
		%>
<div align=center>
	<span class="iTrustError"><%=StringEscapeUtils.escapeHtml(e.getMessage()) %></span>
</div>
<%}
	}
%>

<div align="center">
	<h1>
		<b>Request BioSurveillance</b>
	</h1>
</div>
<div>
	<h3>
		<b>Epidemic Analysis Request</b>
	</h3>
</div>
<div>

	<b>Diagnosis type</b> <br />
	<form name="analysisRequestForm" action="requestBiosurveillance.jsp"
		method="post">
		<input type="hidden" name="analysisFormIsFilled" value="true">
		<input type="radio" name="analysisDiagnosisCode" value="084.5" required>&nbsp;Malaria<br>
		<input type="radio" name="analysisDiagnosisCode" value="487.00" required>&nbsp;Influenza<br>
		<br> <b>Zip Code:</b> <input type="text" name="analysisZipCode" required><br>
		<br> <b>Date:</b><input type="text" name="analysisDate"
			placeholder="mm/dd/yyyy" required><br> <br>
			<b>Threshold(Percentage):</b> <input type="text" name="analysisThreshold"> *Threshold will only be used for Malaria<br>
			<br> <input
			type="submit" value="Submit">
	</form>
</div>
<hr>
<div>
	<h3>
		<b>Examine Recent Trends in Diagnosis</b>
	</h3>
</div>
<div>
	<form name="trendRequestForm" action="requestBiosurveillance.jsp"
		method="post">
		<input type="hidden" name="trendFormIsFilled" value="true"> <b>Diagnosis
			Code:</b> <input type="text" name="trendDiagnosisCode" required><br>
		<br> <b>Zip Code:</b> <input type="text" name="trendZipCode" required><br>
		<br> <b>Date:</b><input type="text" name="trendDate"
			placeholder="mm/dd/yyyy" required><br> <br> <input
			type="submit" value="Submit">
	</form>

</div>

<%@include file="/footer.jsp"%>
