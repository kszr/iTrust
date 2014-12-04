<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.action.OIRAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditMonitoringListAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.TelemedicineBean"%>
<%@page import="edu.ncsu.csc.itrust.enums.Gender"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.beans.OIRBean"%>

<%@ page language="java" import="java.util.*" %>

<%@include file="/global.jsp" %>

<!--  -->
<%@page import="edu.ncsu.csc.itrust.action.SearchUsersAction" %>
<!--  -->

<%
	pageTitle = "iTrust - Obstetrics Overview";
%>

<%@include file="/header.jsp" %>

<%
	/* Require a Patient ID first */
String pidString = (String)session.getAttribute("pid");
String addOrRemove = "Add";
if (pidString == null || 1 > pidString.length() || "false".equals(request.getParameter("confirmAction"))) {
	response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp/OIRList.jsp");
   	return;
}
%>

<%@include file="/util/getUserFrame.jsp"%>

<%
	/* If the patient id doesn't check out, then kick 'em out to the exception handler */
	EditPatientAction action = new EditPatientAction(prodDAO, loggedInMID.longValue(), pidString);
	PersonnelDAO perDAO = prodDAO.getPersonnelDAO();
	String loggedInSpecialty = perDAO.getSpecialty(loggedInMID.longValue());

	//No non-female patients allowed
	PatientBean p = action.getPatient(); 
	Gender gender = p.getGender();
	if(!gender.equals(Gender.Female)){
%>	
		<p>The patient is not eligible for obstetric care.</p>
		<a href="/iTrust/auth/getPatientID.jsp?forward=hcp/OIRList.jsp">Back</a>	
		<%
		return;
	}
				
	OIRAction OIRAction = new OIRAction(prodDAO, loggedInMID.longValue());
		
	List<OIRBean> oirBeanList;
	try{
		oirBeanList = OIRAction.getOIRList(Long.parseLong(pidString));
	} catch (Exception e){
		System.out.println("ERROR: " + e);
		oirBeanList = null;
	}
%>

<script type="text/javascript">

function viewRecord(recordNumber){
	//alert("viewing oir at index " + recordNumber);
	var url = "OIRPage.jsp?mode=viewOld&pregInitRecordNum=" + recordNumber;
	window.location.href = url;
}

</script>

<a href="/iTrust/auth/getPatientID.jsp?forward=hcp/OIRList.jsp">Back</a>
<br/>
<p>Patient:  <%=StringEscapeUtils.escapeHtml("" + (p.getLastName()))%>, <%=StringEscapeUtils.escapeHtml("" + (p.getFirstName()))%></p>

<%
	if(oirBeanList != null){
%>

<table class="fTable" id="obstetricsInitTable">
    <tr>
        <th colspan="9">Current Obstetrics Intializations</th>
    </tr>
    <tr class = "subHeader" colspan="10">
	    	<th>Creation Date</th>
	    	<th>Last Menstrual Period (LMP)</th>
	    	<th></th>
	    </tr>
    
    <%
        	Iterator<OIRBean> itr = oirBeanList.iterator();
            int count = 0;
            while(itr.hasNext()){
            	count++;
            	OIRBean oirBean = (OIRBean)itr.next();
        %>
	    <tr>
	    <td><%= oirBean.getCreationDate().toString() %></td>
	    <td><%= oirBean.getLMP().toString() %></td>
	    <td><a href="javascript:void(0)" onclick="viewRecord(<%= String.valueOf(oirBean.getRecordID()) %>)">view</a></td>
	    </tr>
    <%} %>
    
</table>

<%}else{ %>
	<p>No records currently exist for this patient.</p>

<% } %>

<br/>


<% if(loggedInSpecialty.equals("ob/gyn")){ //TODO: remove true
%>
	<p><a href="OIRPage.jsp?mode=createNew">Create a new obstetrics initilization record here.</a></p>
<% }else{ %>
	<p>You must be an ob/gyn to create a new obstetrics initilization record.</p>
<% } %>

<br />
<br />
<br />

<%@include file="/footer.jsp" %>


