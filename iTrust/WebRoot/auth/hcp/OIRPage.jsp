<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditMonitoringListAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.TelemedicineBean"%>
<%@page import="edu.ncsu.csc.itrust.action.PregnancyAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.PregnancyBean"%>

<%@page import="edu.ncsu.csc.itrust.beans.OIRBean"%>
<%@page import="edu.ncsu.csc.itrust.action.OIRAction"%>

<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.text.SimpleDateFormat" %>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Obstetrics Initialization";
%>

<%@include file="/header.jsp" %>

<% 
	String pidString = (String)session.getAttribute("pid");
	/* If the patient id doesn't check out, then kick 'em out to the exception handler */
	EditPatientAction action = new EditPatientAction(prodDAO, loggedInMID.longValue(), pidString);

	PatientBean p = action.getPatient();
	
	
	///CREATE THE OIR
	Enumeration params = request.getParameterNames(); 
	while(params.hasMoreElements()){
	 String paramName = (String)params.nextElement();
	 System.out.println("Attribute Name - "+paramName+", Value - "+request.getParameter(paramName));
	}
	if(request.getParameter("createOIR") != null && request.getParameter("createOIR").equals("Create")){
		Boolean errors = false;
		String dateString = request.getParameter("LMPDate");
		
		Date creationDate = new Date();
		Date LMPDate = null;
		
		if(dateString == null || dateString.length() < 1){
			System.out.println("dateString is null");
			errors = true;
		}else{
			LMPDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateString);
			if(LMPDate.after(creationDate)){
				System.out.println("LMP cannot be after current date");
				errors = true;
			}
		}
		
		
		System.out.println("one");
		if(errors == false){
			OIRBean oirBean = new OIRBean();
			oirBean.setPatientID(Long.parseLong(pidString));
			oirBean.setCreationDate(creationDate);
			oirBean.setLMP(LMPDate);
			
			OIRAction oirAction = new OIRAction(prodDAO, loggedInMID.longValue());
			try{
				oirAction.createOIR(oirBean);
				%>
				Obstetrics Initialization Record added! <a href="OIRList.jsp">Back.</a>
				<%
				return;
			} catch (Exception e){
				%>
				<font color="red"><%=e %></font>
				<%
			}
			return;
		}else{
			%>Errors parsing!<%
			return;
		}
	}
	
	String mode = (String)request.getParameter("mode");
	boolean createMode = mode.equals("createNew");
	
	String recordNum = (String)request.getParameter("pregInitRecordNum");
	System.out.println("viewing record " + recordNum);

	
	String LMPString = "--/--/----";
	String EDDString = "--/--/----";
	String WeeksPregnantString = "-";
	
	OIRBean oirBean = null;
	if(recordNum != null && recordNum.length() > 0){ //Only attempt to get OIR if a record number is provided
		OIRAction oirAction = new OIRAction(prodDAO, loggedInMID.longValue());
		
		try{
			oirBean = oirAction.getOIR(Long.parseLong(recordNum));
			Date LMP = oirBean.getLMP();
			
			LMPString = LMP.toString();
			EDDString = oirBean.getEDD().toString();
			WeeksPregnantString = "" + oirBean.getWeeksPregnant();

		} catch (Exception e){
			System.out.println("ERROR: " + e);
			oirBean = null;
		}
	}
	
	PregnancyAction pregnancyAction = new PregnancyAction(prodDAO, loggedInMID.longValue());
	List<PregnancyBean> pBeanList;
	try{
		pBeanList = pregnancyAction.getPregnancies(Long.parseLong(pidString));
	} catch (Exception e){
		System.out.println("ERROR: " + e);
		pBeanList = null;
	}
	
	if(createMode){
		%><p>Creating a new obstetrics initialization for:  <%= StringEscapeUtils.escapeHtml("" + (p.getLastName())) %>, <%= StringEscapeUtils.escapeHtml("" + (p.getFirstName())) %></p><%
	}else{
		%><p>Viewing obstetrics initialization for:  <%= StringEscapeUtils.escapeHtml("" + (p.getLastName())) %>, <%= StringEscapeUtils.escapeHtml("" + (p.getFirstName()))  %> <b>(VIEW ONLY)</b></p><%
	}
%>



<form id="mainForm" method="post" action="OIRPage.jsp?pregInitRecordNum=<%= recordNum%>">
	<tr>
		<td class="subHeaderVertical">Last Menstural Period (LMP):</td>
		
	<%if(createMode){%>
		<td>
			<input type="date" id="LMPDate" name="LMPDate" onchange="dateChange(this)">
		</td>
	<%}else{%>
		<td><%= LMPString %></td>
			
	<%} %>
			
		
	</tr>
	<br/>
	<br/>
	<p>Estimated Due Date (EDD): <i id='edd'><%= EDDString %></i></p>
	<p>Number of weeks pregnant: <i id='weeksPregnant'><%= WeeksPregnantString %></i> weeks</p>
	</br>
	<input type="hidden" id="createOIR" name="createOIR" value=""/>
<% if(createMode){ %>
	<input type="submit" id="createButton" value="Create New Record" onclick="document.getElementById('createOIR').value='Create'" >
<%} %>
</form>
<%
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
    Iterator<PregnancyBean> itr = pBeanList.iterator();
    int count = 0;
    while(itr.hasNext()){
    	count++;
    	PregnancyBean pBean = itr.next();
    	//System.out.println((String)pregnancy + " " + Integer.toString(count));
    	%>
    	
    	<tr>
    		<td>Prior Pregnancy #<%= count %>
    		<% if(createMode && false){ %>
    			<input type="button" id="delete-<%= count %>" value="Delete" onclick="confirmDeletePregnancy(<%= count%>)">
    		<%} %>
    		
    		</td>
    		<td><%= pBean.getYearOfContraception() %></td>
    		<td><%= pBean.getWeeksAndDaysPregnantString() %></td>
    		<td><%= pBean.getHoursInLabor() %> hours</td>
    		<td><%= pBean.getDeliveryType() %></td>
    	</tr>
    	
    	<%
    }
	}else{
		%><p>No prior pregnancies</p><%
	}

%>
    
</table>
<br />
<% if(createMode){ %>
	<input type=button id="addPriorPregnancy" value="Add a prior pregnancy" onclick="location.href = '/iTrust/auth/hcp/priorPregnancy.jsp?pregInitRecordNum=<%= recordNum%>'" >
<%} %>

<br/>
<br/>
<input type=button value="Back" onclick="location.href = '/iTrust/auth/hcp/OIRList.jsp'">

<script type="text/javascript">
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

function confirmDeletePregnancy(pregnancyNumber){
	var confirm = window.confirm("Delete pregnancy #" + pregnancyNumber + "? This action cannot be undone.");
	if(confirm){
		<% 
		//pregnancyAction.deletePregnancy(0);
		%>
	}
}
</script>

<%@include file="/footer.jsp" %>