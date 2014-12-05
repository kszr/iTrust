<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.OIRBean"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.action.OIRAction"%>

<%@page import="edu.ncsu.csc.itrust.enums.Gender"%>

<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>

<%@ page language="java" import="java.util.*" %>

<%@include file="/global.jsp" %>

<% pageTitle = "iTrust - Obstetrics Record Listing"; %>

<%@include file="/header.jsp" %>

<%
	/* Require a Patient ID first */
	String pidString = (String) session.getAttribute("pid");
	if (pidString == null || 1 > pidString.length()
			|| "false".equals(request.getParameter("confirmAction"))) {
		response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp/OIRList.jsp");
		return;
	}
%>

<%@include file="/util/getUserFrame.jsp"%>

<%
	/* If the patient id doesn't check out, then kick 'em out to the exception handler */
	EditPatientAction action = new EditPatientAction(prodDAO, loggedInMID.longValue(), pidString);

	/* Determine if user is an ob/gyn */
	PersonnelDAO perDAO = prodDAO.getPersonnelDAO();
	String loggedInSpecialty = perDAO.getSpecialty(loggedInMID.longValue());
	Boolean isOBGYN = loggedInSpecialty.equals("ob/gyn");
	
	/* Grab patient */
	PatientBean p = action.getPatient();
%>

<!-- Back Button & Patient Name [START] -->
<input type="submit" value="Select another patient"
	onClick="window.location= '/iTrust/auth/getPatientID.jsp?forward=hcp/OIRList.jsp'">
<br/>
<br/>
<br/>
<p>
	Patient:
	<%=StringEscapeUtils.escapeHtml("" + (p.getLastName()))%>,
	<%=StringEscapeUtils.escapeHtml("" + (p.getFirstName()))%></p>
<!-- Back Button & Patient Name [END] -->

<% 
	/* Determine if patient is female. Warn and quit if not */
	Gender gender = p.getGender();
	if (!gender.equals(Gender.Female)) {
		%>
		<p>The patient is not eligible for obstetrics care.</p>
		<%
		return;
	}

	/* Fetch obstetrics initialization record list */
	OIRAction OIRAction = new OIRAction(prodDAO,loggedInMID.longValue());
	List<OIRBean> oirBeanList = null;
	try {
		oirBeanList = OIRAction.getOIRList(Long.parseLong(pidString));
	} catch (Exception e) {
		System.out.println("ERROR: " + e);
		oirBeanList = null;
	}

	/* Create Table of OIRs if there is something to populate it with */
	if (oirBeanList != null && oirBeanList.size() > 0) {
%>

<!-- Table containing OIRs for patient -->
<table class="fTable" id="OIRTable">
	<tr>
		<th colspan="9">Current Obstetrics Intializations</th>
	</tr>
	<tr class="subHeader" colspan="10">
		<th>Creation Date</th>
		<th>Last Menstrual Period (LMP)</th>
		<th></th>
	</tr>

	<%
		/* Populate with OIRs */
		Iterator<OIRBean> itr = oirBeanList.iterator();
			while (itr.hasNext()) {
				OIRBean oirBean = itr.next();
	%>
	<tr>
		<td><%=oirBean.getCreationDate().toString()%></td>
		<td><%=oirBean.getLMP().toString()%></td>
		<td><input type="button" id="" value="view this record"
			onClick="viewRecord(<%=String.valueOf(oirBean.getRecordID())%>)"></td>
	</tr>
	<%
		}
	%>

</table>

<%
	/* If no OIRs exist, don't display table. */
	} else {
		%>
		<p>No records currently exist for this patient.</p>
		<%
	}
%>

<br/>
<br/>

<%
	/* Allow creation of new OIRs for ob/gyns */
	if (loggedInSpecialty.equals("ob/gyn")) {
		%>
		<p>
		<input type="button" id="" value="Create a new obstetrics record"
			onClick="window.location='OIRPage.jsp'">
		</p>
		<%
	/* Alert non ob/gyns that they cannot edit */
	} else {
		%>
		<p>You must be an ob/gyn to create a new obstetrics initilization record.</p>
		<%
	}
%>

<script type="text/javascript">
// Push user to relevant OIR Page
function viewRecord(recordNumber){
	var url = "OIRPage.jsp?OIRID=" + recordNumber;
	window.location.href = url;
}
</script>

<%@include file="/footer.jsp" %>