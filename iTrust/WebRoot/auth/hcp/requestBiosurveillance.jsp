<%@page import="edu.ncsu.csc.itrust.action.RequestBioSurveillanceAnalysisAction"%>
<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List,java.util.Date,java.text.SimpleDateFormat"%>
<%@page import="edu.ncsu.csc.itrust.beans.BioSurveillanceBean"%>
<%@page import="edu.ncsu.csc.itrust.action.RequestBiosurveillanceTrendAction"%>


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
		String analysisDiagCode = request
				.getParameter("analysisDiagnosisCode");
		String analysisThreshold = request
				.getParameter("analysisThreshold");

			try {
				

				//variables for analysis

				String analysisZipCode = request
						.getParameter("analysisZipCode");
				String analysisDate = request
						.getParameter("analysisDate");
				//error checking for date
				Date parseDate = new Date();
				
				String[] brokenInput = analysisDate.split("/");
				Integer monthInt = Integer.parseInt(brokenInput[0]);
				Integer daysInt = Integer.parseInt(brokenInput[1]);
				Integer yearInt = Integer.parseInt(brokenInput[2]);		
				
				if(monthInt > 12 || monthInt < 1 )
				{
					%>
					<div align=center>
						<span class="iTrustError">Invalid month. Month must be < 12 or >0 </span>
					</div>
					<%
				}
				
				else if(daysInt > 31 || daysInt < 1 )
				{
					%>
					<div align=center>
						<span class="iTrustError">Invalid date. </span>
					</div>
					<%
				}
				
						
				else{

				BioSurveillanceBean bb = new BioSurveillanceBean(
						analysisDiagCode, analysisZipCode,
						analysisDate, analysisThreshold);

				RequestBioSurveillanceAnalysisAction ra = new RequestBioSurveillanceAnalysisAction(
						prodDAO);
				
				if (ra.requestBioAnalysis(bb)) {
					//System.out.println("success request analysis");
					if(bb.getDiagnosisCode().equals("084.5")){
						loggingAction.logEvent(TransactionType.REQUEST_BIOSURVEILLANCE_ANALYSIS_MALARIA_VIEW, loggedInMID.longValue(), 0, "View Malaria Analysis: Date " + bb.getDate() + ", ZipCode " + bb.getZipCode() + ", Threshold " + bb.getThreshold());
					}
					else if(bb.getDiagnosisCode().equals("487.00")){
						loggingAction.logEvent(TransactionType.REQUEST_BIOSURVEILLANCE_ANALYSIS_INFLUENZA_VIEW, loggedInMID.longValue(), 0, "View Influenza Analysis: Date " + bb.getDate() + ", ZipCode " + bb.getZipCode());
					}
					%>
					<div align=center>
						<span class="iTrustError"> The area you requested DOES contain
							the epidemic you have chosen!!!</span>
					</div>
					<%
				}

				else {
					
					if(bb.getDiagnosisCode().equals("084.5")){
						loggingAction.logEvent(TransactionType.REQUEST_BIOSURVEILLANCE_ANALYSIS_MALARIA_VIEW, loggedInMID.longValue(), 0, "View Malaria Analysis: Date " + bb.getDate() + ", ZipCode " + bb.getZipCode() + ", Threshold " + bb.getThreshold());
					}
					else if(bb.getDiagnosisCode().equals("487.00")){
						loggingAction.logEvent(TransactionType.REQUEST_BIOSURVEILLANCE_ANALYSIS_INFLUENZA_VIEW, loggedInMID.longValue(), 0, "View Influenza Analysis: Date " + bb.getDate() + ", ZipCode " + bb.getZipCode());
					}
					%>
					<div align=center>
						<span class="iTrustError"> The area you requested DOES NOT
							contain the epidemic you have chosen.</span>
					</div>
					<%
				}}
			}
			//print out the form validator
			catch (FormValidationException e) {
				%>
				<div align=center>
					<span class="iTrustError"><%=StringEscapeUtils.escapeHtml(e.getMessage())%></span>
				</div>
				<%
			}
		

	}
	//IF redirected from trend form submit
	if (trendFormIsFilled) {
		try {
		
			//variables for trend
			String trendDiagCode = request
					.getParameter("trendDiagnosisCode");
			
			String trendZipCode = request.getParameter("trendZipCode");
			String trendDate = request.getParameter("trendDate");
			
			//error checking for date
			Date parseDate = new Date();
			
			String[] brokenInput = trendDate.split("/");
		
			Integer monthInt = Integer.parseInt(brokenInput[0]);
			Integer daysInt = Integer.parseInt(brokenInput[1]);
			Integer yearInt = Integer.parseInt(brokenInput[2]);		
			
			if(monthInt > 12 || monthInt < 1 )
			{
				%>
				<div align=center>
					<span class="iTrustError">Invalid month. Month must be < 12 or >0 </span>
				</div>
				<%
			}
			
			else if(daysInt > 31 || daysInt < 1 )
			{
				%>
				<div align=center>
					<span class="iTrustError">Invalid date. </span>
				</div>
				<%
			}
			else{
			BioSurveillanceBean bb = new BioSurveillanceBean(
					trendDiagCode, trendZipCode, trendDate);

			RequestBiosurveillanceTrendAction rt = new RequestBiosurveillanceTrendAction(prodDAO);
			if(bb.isMalariaDiagCode()){
				loggingAction.logEvent(TransactionType.REQUEST_BIOSURVEILLANCE_TREND_MALARIA_VIEW, loggedInMID.longValue(), 0, "View Malaria Trend: Date " + bb.getDate() + ", ZipCode " + bb.getZipCode());
			}
			else if(bb.isInfluenzaDiagCode()){
				loggingAction.logEvent(TransactionType.REQUEST_BIOSURVEILLANCE_TREND_INFLUENZA_VIEW, loggedInMID.longValue(), 0, "View Influenza Trend: Date " + bb.getDate() + ", ZipCode " + bb.getZipCode());
			}
			
			if (rt.requestBioTrendVerify(bb)) {
				if (bb.isMalariaDiagCode() || bb.isInfluenzaDiagCode()) {
					String site = new String(
							"/iTrust/auth/hcp/requestSurveillanceTrendResult.jsp?zipcode="
									+ trendZipCode + "&date="
									+ trendDate + "&diagcode="
									+ trendDiagCode);
					response.setStatus(response.SC_MOVED_TEMPORARILY);
					response.setHeader("Location", site);
					
				}
				else {
					%>
					<div align=center>
						<span class="iTrustError"> There is no Epidemic Detection Algorithm for this Diagnosis Code. </span>
					</div>
					<%	
				}
			} else {

				
			}}
			//print out form validate error
		} catch (FormValidationException e) {
			%>
			<div align=center>
				<span class="iTrustError"><%=StringEscapeUtils.escapeHtml(e.getMessage())%></span>
			</div>
			<%
		}
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
		<input type="radio" name="analysisDiagnosisCode" value="084.5"
			required>&nbsp;Malaria<br> <input type="radio"
			name="analysisDiagnosisCode" value="487.00" required>&nbsp;Influenza<br>
		<br> <b>Zip Code:</b> <input type="text" name="analysisZipCode"
			required><br> <br> <b>Date:</b><input type="text"
			name="analysisDate" placeholder="mm/dd/yyyy" required><br>
		<br> <b>Threshold(Percentage):</b> <input type="text"
			name="analysisThreshold"> *Threshold will only be used for
		Malaria<br> <br> <input type="submit" value="Submit">
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
		<br> <b>Zip Code:</b> <input type="text" name="trendZipCode"
			required><br> <br> <b>Date:</b><input type="text"
			name="trendDate" placeholder="mm/dd/yyyy" required><br>
		<br> <input type="submit" value="Submit">
	</form>

</div>

<%@include file="/footer.jsp"%>
