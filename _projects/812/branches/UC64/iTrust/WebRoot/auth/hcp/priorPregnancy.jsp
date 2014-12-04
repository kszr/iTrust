<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditMonitoringListAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.TelemedicineBean"%>

<%@page import="edu.ncsu.csc.itrust.beans.PregnancyBean"%>
<%@page import="edu.ncsu.csc.itrust.action.PregnancyAction"%>

<%@ page language="java" import="java.util.*" %>

<%@include file="/global.jsp" %>

<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>

<%
pageTitle = "iTrust - Prior Pregnancy Creation";
%>

<%@include file="/header.jsp" %>

<% 
	String pidString = (String)session.getAttribute("pid");
	/* If the patient id doesn't check out, then kick 'em out to the exception handler */
	EditPatientAction action = new EditPatientAction(prodDAO, loggedInMID.longValue(), pidString);
	
	String recordNum = (String)request.getParameter("pregInitRecordNum");
	
	int pregnancyDuration = 0;
	int yearOfContraception = 0;
	double timeInLabor = 0;
	String deliveryType = "";
	
	boolean pregnancyDurationError = false;
	boolean yearOfContraceptionError = false;
	boolean timeInLaborError = false;
	
	if(request.getParameter("createPregnancy") != null && request.getParameter("createPregnancy").equals("Create")){
		
		try{
			pregnancyDuration = Integer.parseInt(request.getParameter("pregnancyDuration"));
			if(pregnancyDuration < 0)
				pregnancyDurationError = true;
			
		} catch (Exception e){
			pregnancyDurationError = true;
		}
		try{
			yearOfContraception = Integer.parseInt(request.getParameter("yearOfContraception"));
			if(yearOfContraception < 1900 || yearOfContraception > 2014)
				yearOfContraceptionError = true;
			
		} catch (Exception e){
			yearOfContraceptionError = true;
		}
		if(request.getParameter("timeInLabor").length() > 0){
			try{
				timeInLabor = Double.parseDouble(request.getParameter("timeInLabor"));
				if(timeInLabor < 0.0)
					timeInLaborError = true;
					
			} catch (Exception e) {
				timeInLaborError = true;
			}
		}

		deliveryType = request.getParameter("deliveryType");
		
		if(pregnancyDurationError == false && yearOfContraceptionError == false && timeInLaborError == false){
			PregnancyBean pBean = new PregnancyBean();
			pBean.setPatientID(Long.parseLong(pidString));
			pBean.setYearOfContraception(yearOfContraception);
			pBean.setNumberOfDaysPregnant(0);
			pBean.setNumberOfWeeksPregnant(pregnancyDuration);
			pBean.setHoursInLabor(timeInLabor);
			pBean.setDeliveryType(deliveryType);
			
			PregnancyAction pregnancyAction = new PregnancyAction(prodDAO, loggedInMID.longValue());
			try{
				pregnancyAction.createPregnancy(pBean);
				%>
				Prior Pregnancy added! <a href="OIRPage.jsp?mode=back&pregInitRecordNum=<%= recordNum%>">Back.</a>
				<%
				return;
			} catch (Exception e){
				%>
				<font color="red"><%=e %></font>
				<%
			}

		}else{
			System.out.println("pregnancy errors exist");
		}

	}
	
	PregnancyBean ppBean = null;
%>
<form id="mainForm" method="post" action="priorPregnancy.jsp?pregInitRecordNum=<%= recordNum%>">
	<p>
		Year of Contraception: <input type="text" id="yearOfContraception" name="yearOfContraception">
	</p>
	<p>
		Pregnancy Duration (weeks): <input type="text" id="pregnancyDuration" name="pregnancyDuration">
	</p>
	<p>
		Time in labor (hours): <input type="text" id="timeInLabor" name="timeInLabor">
	</p>
	<p>
		Pregnancy Result: 
		<select id="deliveryType" name="deliveryType">
	    	<option value="Vaginal Delivery">Vaginal Delivery</option>
	    	<option value="Caesarean Section">Caesarean Section</option>
	    	<option value="Miscarriage">Miscarriage</option>
		</select>
	</p>
	
	<input type="hidden" id="createPregnancy" name="createPregnancy" value=""/>
	<input type="button" value="Back" onclick="history.go(-1);"/>
	<input type="submit" id="createButton" value="CREATE" onclick="document.getElementById('createPregnancy').value='Create'" />
</form>

<%
String errorString = "";
if(yearOfContraceptionError){
	errorString = errorString + "Year of contraception must be greater than 1900 and less than or equal to 2014.\n";
}
if(pregnancyDurationError){
	errorString = errorString + "Pregnancy duration must be positive double.\n";
}
if(timeInLaborError){
	errorString = errorString + "Time in labor must be positive integer.\n";
}
%>
<font color="red"><%=errorString %></font>


<%@include file="/footer.jsp" %>