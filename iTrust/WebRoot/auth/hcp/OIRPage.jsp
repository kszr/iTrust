<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.OIRBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.PregnancyBean"%>

<%@page import="edu.ncsu.csc.itrust.action.EditPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.action.PregnancyAction"%>
<%@page import="edu.ncsu.csc.itrust.action.OIRAction"%>

<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>

<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.text.SimpleDateFormat" %>

<%@include file="/global.jsp" %>

<% pageTitle = "iTrust - Obstetrics Record"; %>

<%@include file="/header.jsp" %>

<%
	/* Require a Patient ID first */
	String pidString = (String) session.getAttribute("pid");
	if (pidString == null || 1 > pidString.length()
			|| "false".equals(request.getParameter("confirmAction"))) {
		response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp/OIRList.jsp");
		return;
	}

	/* If the patient id doesn't check out, then kick 'em out to the exception handler */
	EditPatientAction action = new EditPatientAction(prodDAO, loggedInMID.longValue(), pidString);

	/* Determine if user is an ob/gyn */
	PersonnelDAO perDAO = prodDAO.getPersonnelDAO();
	String loggedInSpecialty = perDAO.getSpecialty(loggedInMID.longValue());
	Boolean isOBGYN = loggedInSpecialty.equals("ob/gyn");
	
	/* Grab OIRID if it exists */
	String OIRIDString = request.getParameter("OIRID");
	
	/* Create mode is entered if OIRID string is not provided */
	final boolean isViewMode = (OIRIDString != null && OIRIDString.length() > 0);
	/* For readability. Always the opposite if isViewMode */
	final boolean isCreateMode = !isViewMode;
	
	/* Grab Patient */
	PatientBean p = action.getPatient();
	
	/* User is attempting to create a new OIR [START] */
	if(request.getParameter("createOIR") != null && request.getParameter("createOIR").equals("Create")){
		/* Will hold error state */
		StringBuffer errorBuffer = new StringBuffer("");
		
		Date creationDate = new Date();
		String dateString = request.getParameter("LMPDate");
		Date LMPDate = null;
		
		/* Validate user input */
		if(dateString == null || dateString.length() < 1){
			errorBuffer.append("Date cannot be pared. \n");

		}else{
			LMPDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateString);
			if(LMPDate.after(creationDate)){
				errorBuffer.append("Last menstural period cannot be after creation date. \n");
			}
		}
		
		String errorString = errorBuffer.toString();
		
		/* If no errors exist at first (this block will check for backend errors) */
		if(errorString.length() == 0){
			/* Populate the OIR Bean with validated values */
			OIRBean oirBean = new OIRBean();
			oirBean.setPatientID(Long.parseLong(pidString));
			oirBean.setCreationDate(creationDate);
			oirBean.setLMP(LMPDate);
	
			/* Attempt to save OIR to database */
			OIRAction oirAction = new OIRAction(prodDAO, loggedInMID.longValue());
			try{
				oirAction.createOIR(oirBean);
				loggingAction.logEvent(TransactionType.CREATE_INITIAL_OBSTETRICS_RECORD, loggedInMID.longValue(), Long.parseLong(pidString), oirBean.getEDD().toString());
				%>
				<input type="submit" value="Back"
					onClick="window.location= '/iTrust/auth/hcp/OIRList.jsp'">
				</br>
				<p>Obstetrics Initialization Record successfully added!</p>
				<%
				return;
				
			} catch (Exception e){
				errorBuffer.append(e.getMessage());
				errorString = errorBuffer.toString();
				%>
				<font color="red">Error: <%= errorBuffer.toString() %></font>
				<br/>
				<%
			}

		/* Errors exist that were picked up by frontend */
		}else{
			%>
			<font color="red">Error: <%= errorBuffer.toString() %></font>
			<br/>
			<%
		}
	}
	/* User is attempting to create a new OIR [END] */
	
	/* Default values will be displayed if on create mode or there is an error parsing OIRIDString or fetching that OIR */
	String creationDateString = (new Date()).toString();
	String LMPString = "???";
	String EDDString = "???";
	String WeeksPregnantString = "?";
	
	/* Run if OIRID has been provided. We will only view (Use Case does not call for editing)  */
	OIRBean oirBean = null;
	if(isViewMode){
		OIRAction oirAction = new OIRAction(prodDAO, loggedInMID.longValue());
		
		try{
			/* Grab the bean and */
			oirBean = oirAction.getOIR(Long.parseLong(OIRIDString));
			Date LMP = oirBean.getLMP();
			creationDateString = oirBean.getCreationDate().toString();
			
			LMPString = LMP.toString();
			EDDString = oirBean.getEDD().toString();
			WeeksPregnantString = "" + oirBean.getWeeksPregnant();
			
			/* Also log the viewing event */
			loggingAction.logEvent(TransactionType.VIEW_INITIAL_OBSTETRICS_RECORD, loggedInMID.longValue(), Long.parseLong(pidString), oirBean.getEDD().toString());

		} catch (Exception e){
			System.out.println("ERROR: " + e);
			oirBean = null;
		}
	}
	
	/* Grabs all pregnancies of the patient */
	PregnancyAction pregnancyAction = new PregnancyAction(prodDAO, loggedInMID.longValue());
	List<PregnancyBean> pBeanList = null;
	try{
		pBeanList = pregnancyAction.getPregnancies(Long.parseLong(pidString));
	} catch (Exception e){
		System.out.println("ERROR: " + e);
		pBeanList = null;
	}
%>

<!-- Back Button & Patient Name [START] -->
<input type="submit" value="Select another obstetrics record"
	onClick="window.location= '/iTrust/auth/hcp/OIRList.jsp'">
<br/>
<br/>
<br/>
<p>
	Patient:
	<%=StringEscapeUtils.escapeHtml("" + (p.getLastName()))%>,
	<%=StringEscapeUtils.escapeHtml("" + (p.getFirstName()))%></p>
<!-- Back Button & Patient Name [END] -->

<%
	/* Alert user they are in view only mode */
	if(isViewMode){
		%>
		<font color="red"><i>View only mode.</i></font>
		<%
	}
%>

<form id="mainForm" method="post" action="OIRPage.jsp">
	<tr>
	<p>
		Creation Date: <%= creationDateString %>
	</p>
	</tr>
	<tr>
		<td class="subHeaderVertical">Last Menstural Period (LMP):</td>

<%
	/* Only display date picker if on create mode */
	if(isCreateMode){
		%>
		<td><input type="date" id="LMPDate" name="LMPDate"
			onchange="dateChange(this)"></td>
		<%
	}else{
		%>
		<td><%= LMPString %></td>
		<%
	} 
%>
	</tr>
	<br/>
	<br/>
	<p>
		Estimated Due Date (EDD): <i id='edd'><%=EDDString%></i>
	</p>
	<p>
		Number of weeks pregnant: <i id='weeksPregnant'><%=WeeksPregnantString%></i> weeks
	</p>
	</br> <input type="hidden" id="createOIR" name="createOIR" value="" />


<% 	
	/* Only display create button if on create mode */
	if(isCreateMode){ 
		%>
		<input type="submit" id="createButton" value="Create New Record" onclick="document.getElementById('createOIR').value='Create'" >
		<%
	} 
%>
</form>

<%
	/* Only display pregnancy table if prior pregnancies exist */
	if(pBeanList != null){
	%>
		<table class="fTable" id="pregnancyTable">
	    <tr class = "subHeader">
	    	<th>Prior Pregnancies</th>
	    	<th>Year of Contraception</th>
	    	<th>Pregnancy Duration</th>
	    	<th>Time in labor</th>
	    	<th>Pregnancy Result</th>
	    </tr>

	<%
		/* Iterate through pregnancies for the patient */
		Iterator<PregnancyBean> itr = pBeanList.iterator();
	    int count = 0;
	    while(itr.hasNext()){
	    	count++;
	    	PregnancyBean pBean = itr.next();
			%>
			<tr>
			<td>Prior Pregnancy #<%=count%> 
			
			<%
			/* Deletion of prior pregnancies currently removed */
 			if(false && isCreateMode){
 				%> 
 				<input type="button" id="delete-<%=count%>" value="Delete" onclick="confirmDeletePregnancy(<%=count%>)"> 
 				<%
 			}
 			%>

		</td>
			<!-- Populate pregnancy table -->
			<td><%=pBean.getYearOfContraception()%></td>
			<td><%=pBean.getWeeksAndDaysPregnantString()%></td>
			<td><%=pBean.getHoursInLabor()%> hours</td>
			<td><%=pBean.getDeliveryType()%></td>
		</tr>
	<%
		}
	}else{
		/* Do not display the table if no prior pregnancies exist for the patient*/
		%>
		<br/>
		<br/>
		<br/>
		<br/>
		<p>No prior pregnancies</p>
		<%
	}
%>
</table>
<br/>

<%
	/* Show prior pregnancy button if on create mode */
	if(isCreateMode){
		%>
		<input type=button id="addPriorPregnancy" value="Add a prior pregnancy"
			onclick="location.href='/iTrust/auth/hcp/priorPregnancy.jsp'">
		<%
	}
%>

<script type="text/javascript">
//Handles date change 
function dateChange(item){
	var today = new Date();
	var LMPDate = new Date(item.value);
	LMPDate.setHours(0,0,0,0);
	LMPDate.setDate(LMPDate.getDate() + 1);
	
	//Ensures LMP is in the past
	if(today < LMPDate){
		alert("Last Menstural Period must be before today's date.");
		this.value = "mm/dd/yyyy";
		document.getElementById("edd").innerHTML = "--/--/----";
		document.getElementById("weeksPregnant").innerHTML = "-";
		return;
	}
	
	//Calculates weeks between LMP and current date
	var oneDay = 24*60*60*1000; // hours*minutes*seconds*milliseconds
	var diffDays = Math.round(Math.abs((today.getTime() - LMPDate.getTime())/(oneDay)));
	var diffWeeks = Math.floor(diffDays/7);
	
	var EDD = new Date();
	EDD.setTime(EDD.getTime() + oneDay * 280);
	document.getElementById("edd").innerHTML = EDD.toDateString();
	document.getElementById("weeksPregnant").innerHTML = diffWeeks;
}

/* 
//Provides a confirm dialog to the user to delete the pregnancy
function confirmDeletePregnancy(pregnancyNumber){
	var confirm = window.confirm("Delete pregnancy #" + pregnancyNumber + "? This action cannot be undone.");
	if(confirm){
		
	}
}
*/
</script>

<%@include file="/footer.jsp" %>