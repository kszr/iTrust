<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>


<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.PregnancyBean"%>

<%@page import="edu.ncsu.csc.itrust.action.EditPatientAction"%>

<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>

<%@page import="edu.ncsu.csc.itrust.action.PregnancyAction"%>

<%@ page language="java" import="java.util.*" %>

<%@include file="/global.jsp" %>

<% pageTitle = "iTrust - Prior Pregnancy Creation"; %>

<%@include file="/header.jsp" %>

<% 
	String pidString = (String)session.getAttribute("pid");
	/* If the patient id doesn't check out, then kick 'em out to the exception handler */
	EditPatientAction action = new EditPatientAction(prodDAO, loggedInMID.longValue(), pidString);
	
	/* Grab Patient */
	PatientBean p = action.getPatient();
	
	/* User is attempting to create a new prior pregnancy [START] */
	if(request.getParameter("createPregnancy") != null && request.getParameter("createPregnancy").equals("Create")){
		StringBuffer errorBuffer = new StringBuffer("");
		
		int pregnancyWeeks = -1;
		int pregnancyDays = -1;
		int yearOfContraception = -1;
		double timeInLabor = -1;
		String deliveryType = request.getParameter("deliveryType");
		
		/* Validate user input */
		try{
			pregnancyWeeks = Integer.parseInt(request.getParameter("pregnancyWeeks"));
			if(pregnancyWeeks < 0)
				errorBuffer.append("Pregnancy weeks must be a non-negative integer. \n");
			
		} catch (Exception e){
			errorBuffer.append("Pregnancy weeks must be a non-negative integer. \n");
		}
		try{
			pregnancyDays = Integer.parseInt(request.getParameter("pregnancyDays"));
			if(pregnancyDays < 0 || pregnancyDays > 6)
				errorBuffer.append("Pregnancy days must be between 0 and 6. \n");
			
		} catch (Exception e){
			errorBuffer.append("Pregnancy days must be a positive integer. \n");
		}
		try{
			yearOfContraception = Integer.parseInt(request.getParameter("yearOfContraception"));
			if(yearOfContraception < 1964 || yearOfContraception > 2014)
				errorBuffer.append("Year of contraception must be after 1964 and before 2015. \n");
			
		} catch (Exception e){
			errorBuffer.append("Year of contraception must be after 1964 and before 2015. \n");
		}
		if(request.getParameter("timeInLabor").length() > 0){
			try{
				timeInLabor = Double.parseDouble(request.getParameter("timeInLabor"));
				if(timeInLabor < 0.0)
					errorBuffer.append("Time in labor must be a non-negative double (or empty). \n");
					
			} catch (Exception e) {
				errorBuffer.append("Time in labor must be a non-negative double (or empty). \n");
			}
		}
		
		String errorString = errorBuffer.toString();
		
		/* If no errors exist at first (this block will check for backend errors) */
		if(errorString.length() == 0){
			/* Populate the Pregnancy Bean with validated values */
			PregnancyBean pBean = new PregnancyBean();
			pBean.setPatientID(Long.parseLong(pidString));
			pBean.setYearOfContraception(yearOfContraception);
			pBean.setNumberOfWeeksPregnant(pregnancyWeeks);
			pBean.setNumberOfDaysPregnant(pregnancyDays);
			pBean.setHoursInLabor(timeInLabor);
			pBean.setDeliveryType(deliveryType);
			
			/* Attempt to save pregnancy to database */
			PregnancyAction pregnancyAction = new PregnancyAction(prodDAO, loggedInMID.longValue());
			try{
				pregnancyAction.createPregnancy(pBean);
				%>
				<input type="submit" value="Back to obstetrics record creation page"
					onClick="window.location= '/iTrust/auth/hcp/OIRPage.jsp'">
				<br/>
				<br/>
				<br/>
				<p>Prior Pregnancy successfully added!</p>
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
	/* User is attempting to create a new prior pregnancy [END] */
	
	PregnancyBean ppBean = null;
%>

<!-- Back Button & Patient Name [START] -->
<input type="submit" value="Back to obstetrics record creation page"
	onClick="window.location= '/iTrust/auth/hcp/OIRPage.jsp'">
<br/>
<br/>
<br/>
<p>
	Patient:
	<%=StringEscapeUtils.escapeHtml("" + (p.getLastName()))%>,
	<%=StringEscapeUtils.escapeHtml("" + (p.getFirstName()))%></p>
<!-- Back Button & Patient Name [END] -->


<form id="mainForm" method="post" action="priorPregnancy.jsp">
	<p>
		Year of Contraception: <input type="text" id="yearOfContraception"
			name="yearOfContraception">
	</p>
	<p>
		Pregnancy Duration: <input type="text" id="pregnancyWeeks"
			name="pregnancyWeeks"> weeks and <input type="text" id="pregnancyDays"
			name="pregnancyDays"> days
	</p>
	<p>
		Time in labor (hours): <input type="text" id="timeInLabor"
			name="timeInLabor">
	</p>
	<p>
		Pregnancy Result: <select id="deliveryType" name="deliveryType">
			<option value="Vaginal Delivery">Vaginal Delivery</option>
			<option value="Caesarean Section">Caesarean Section</option>
			<option value="Miscarriage">Miscarriage</option>
		</select>
	</p>

	<input type="hidden" id="createPregnancy" name="createPregnancy"
		value="" /> 
	<input type="submit" id="createButton" value="CREATE"
		onclick="document.getElementById('createPregnancy').value='Create'" />
</form>

<%@include file="/footer.jsp" %>