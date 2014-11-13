<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld" %>
<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.action.EditObstetricsVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.action.AddObstetricsVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.ObstetricsVisitBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.forms.EditObstetricsVisitForm"%>
<%@page import="edu.ncsu.csc.itrust.exception.ITrustException"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>

<%@include file="/global.jsp" %>

<%
String visitName = "Obstetrics Visit";
pageTitle = "iTrust - Document "+visitName;
%>

<%@include file="/header.jsp" %>

<%
	boolean createVisit = false;

    String submittedFormName = request.getParameter("formName");
    
	String ovIDString = request.getParameter("ovID");
	String pidString = (String)session.getAttribute("pid");
	
    if (pidString == null || pidString.length() == 0) {
        response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=iTrust/auth/hcp-uap/editOfficeVisit.jsp?ovID=" + ovIDString);
        return;
    }
	
	EditObstetricsVisitAction ovaction = null;
	
	if(ovIDString == null || ovIDString.length() == 0 || ovIDString.equals("-1")) {
		// submittedFormName must be: "mainForm" or null
		ovaction = new EditObstetricsVisitAction(prodDAO, loggedInMID, pidString);
		createVisit = true;
	} else {
		ovaction = new EditObstetricsVisitAction(prodDAO, loggedInMID, pidString, ovIDString);
	}
	
    String confirm = "";
    String warning = "";
    if ("mainForm".equals(submittedFormName)) {
    	ObstetricsVisitBean visit = ovaction.getObstetricsVisit();
    	EditObstetricsVisitForm form = new BeanBuilder<EditObstetricsVisitForm>().build(request.getParameterMap(), new EditObstetricsVisitForm());
    	form.setHcpID("" + visit.getHcpID());
        form.setPatientID("" + visit.getPatientID());
        try {
        	confirm = ovaction.updateInformation(form, false);
        	ovIDString = ""+ovaction.getOvID();
        	if (createVisit) {        		
       			//ovaction.logOfficeVisitEvent(TransactionType.OFFICE_VISIT_CREATE);
        		createVisit = false;
        	} else {
                //ovaction.logOfficeVisitEvent(TransactionType.OFFICE_VISIT_EDIT);
        	}
        } catch (FormValidationException e) {
            e.printHTML(pageContext.getOut());
            confirm = "Input not valid";
        }
    } else if (!createVisit) {
        //ovaction.logOfficeVisitEvent(TransactionType.OFFICE_VISIT_VIEW);
    }
    
    String disableSubformsString = createVisit ? "disabled=\"true\"" : "";
    String disableMainformString = createVisit ? "" : "disabled=\"true\"";
    
	ObstetricsVisitBean ovbean = ovaction.getObstetricsVisit();
	
%>

<%
if (!"".equals(confirm)) {
	if ("false".equals(request.getParameter("checkPresc"))){ %>
		<span class="iTrustMessage">Operation Canceled</span>
	<% } else if ("success".equals(confirm)) { 
			
	%>
		<span class="iTrustMessage">Information Successfully Updated</span>
<%	}
	else { %>
		<span class="iTrustError"><%= StringEscapeUtils.escapeHtml("" + (confirm)) %></span>		
<%	}
}	
%>

<div align=center id="general">
<form action="editObstetricsVisit.jsp" method="post" id="mainForm">
<input type="hidden" name="formIsFilled" value="true" />
<input type="hidden" name="formName" value="mainForm" />
<input type="hidden" name="ovID" value="<%= StringEscapeUtils.escapeHtml("" + (ovaction.getOvID())) %>" />

<table class="fTable" align="center">
	<tr>
		<th colspan="2"><a href="#" class="topLink">[Top]</a><%=visitName %></th>
	</tr>
	<tr>
		<td class="subHeaderVertical">Patient ID:</td>
		<td><%= StringEscapeUtils.escapeHtml("" + (prodDAO.getAuthDAO().getUserName(ovbean.getPatientID()))) %> </td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Date of Visit:</td>
		<td>
		  <input name="visitDate" value="<%= StringEscapeUtils.escapeHtml("" + (ovbean.getVisitDateStr())) %>" <%= disableMainformString %>/>
		  <input type="button" value="Select Date" onclick="displayDatePicker('visitDate');" <%= disableMainformString %>/>
		</td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Weeks Pregnant:</td>
		<td>
		  <input name="weeksPregnant" value="<%= StringEscapeUtils.escapeHtml("" + (ovbean.getWeeksPregnant())) %>"/>
		</td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Days Pregnant:</td>
		<td>
		  <input name="daysPregnant" value="<%= StringEscapeUtils.escapeHtml("" + (ovbean.getDaysPregnant())) %>"/>
		</td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Fetal Heart Rate:</td>
		<td>
		  <input name="fetalHeartRate" value="<%= StringEscapeUtils.escapeHtml("" + (ovbean.getFetalHeartRate())) %>"/>
		</td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Fundal Height of the Uterus:</td>
		<td>
		  <input name="fundalHeightOfUterus" value="<%= StringEscapeUtils.escapeHtml("" + (ovbean.getFundalHeightOfUterus())) %>"/>
		</td>
	</tr>
	<tr>
	   <td colspan="2" align="center">
		<% if (createVisit) { %>
		    <input type="submit" name="update" id="update" value="Create" >
		<% } else { %>
		    <input type="submit" name="update" id="update" value="Update" >
		<% } %>
		</td>
    </tr>
</table>
</form>
</div>

<br /><br /><br />

<%if(userRole.equals("hcp")){%>
	<div align="center"><itrust:patientNav /></div>
<%}%>
<br />

<%@include file="/footer.jsp" %>


