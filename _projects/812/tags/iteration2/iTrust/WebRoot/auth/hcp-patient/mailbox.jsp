
<%@page import="java.util.List"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@page import="edu.ncsu.csc.itrust.beans.MessageBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPersonnelAction"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyMessagesAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPersonnelAction"%>

<script src="/iTrust/DataTables/media/js/jquery.dataTables.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
	//Initializing the DataTable thingo with a sort function.
	jQuery.fn.dataTableExt.oSort['lname-asc'] = function(x, y) {
		var a = x.split(" ");
		var b = y.split(" ");
		return ((a[1] < b[1]) ? -1 : ((a[1] > b[1]) ? 1 : 0));
	};

	jQuery.fn.dataTableExt.oSort['lname-desc'] = function(x, y) {
		var a = x.split(" ");
		var b = y.split(" ");
		return ((a[1] < b[1]) ? 1 : ((a[1] > b[1]) ? -1 : 0));
	};
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#mailbox").dataTable({
			"aaColumns" : [ [ 2, 'dsc' ] ],
			"aoColumns" : [ {
				"sType" : "lname"
			}, null, null, {
				"bSortable" : false
			} ],
			"sPaginationType" : "full_numbers"
		});
	});
</script>
<style type="text/css" title="currentStyle">
@import "/iTrust/DataTables/media/css/demo_table.css";
</style>

<%
	boolean outbox = (Boolean) session.getAttribute("outbox");
	boolean isHCP = (Boolean) session.getAttribute("isHCP");

	String pageName = "messageInbox.jsp";
	if (outbox) {
		pageName = "messageOutbox.jsp";
	}

	PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
	PatientDAO patientDAO = new PatientDAO(prodDAO);

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	ViewMyMessagesAction action = new ViewMyMessagesAction(prodDAO, loggedInMID.longValue());

	EditPersonnelAction fps_action = null;
	EditPatientAction fpa_action = null;
	if(isHCP) {
		fps_action = new EditPersonnelAction(prodDAO, loggedInMID.longValue(), loggedInMID.toString());
	} else {
		fpa_action = new EditPatientAction(prodDAO, loggedInMID.longValue(), loggedInMID.toString());
	}
	
	List<MessageBean> messages = null;
	session.setAttribute("messages", messages);

	//PersonnelDAO dao = new PersonnelDAO(prodDAO);

	//Edit Filter backend
	boolean editing = false;
	String headerMessage = "";
	String[] fields = new String[6];
	if (request.getParameter("edit") != null
			&& request.getParameter("edit").equals("true")) {
		editing = true;

		int i;
		for (i = 0; i < 6; i++) {
			fields[i] = "";
		}

		if (request.getParameter("cancel") != null)
			response.sendRedirect("messageInbox.jsp");
		else if (request.getParameter("test") != null
				|| request.getParameter("save") != null) {
			boolean error = false;
			String nf = "";
			nf += request.getParameter("sender").replace(",", "") + ",";
			nf += request.getParameter("subject").replace(",", "") + ",";
			nf += request.getParameter("hasWords").replace(",", "") + ",";
			nf += request.getParameter("notWords").replace(",", "") + ",";
			nf += request.getParameter("startDate").replace(",", "") + ",";
			nf += request.getParameter("endDate");

			//Validate Filter
			nf = action.validateAndCreateFilter(nf);
			if (nf.startsWith("Error")) {
				error = true;
				headerMessage = nf;
			}

			if (!error) {
				if (request.getParameter("test") != null) {
					response.sendRedirect("messageInbox.jsp?edit=true&testFilter=" + nf);
				} else if (request.getParameter("save") != null) {
					if(isHCP) {
						fps_action.editMessageFilter(nf, loggedInMID.longValue());
					} else {
						fpa_action.editMessageFilter(nf, loggedInMID.longValue());
					}
					response.sendRedirect("messageInbox.jsp?filter=true");
				}
			}
		}

		if (request.getParameter("testFilter") != null) {
			String filter = request.getParameter("testFilter");
			String[] f = filter.split(",", -1);
			for (i = 0; i < 6; i++) {
				try {
					fields[i] = f[i];
				} catch (ArrayIndexOutOfBoundsException e) {
					//do nothing
				}
			}
		} else {
			String filter = isHCP ? personnelDAO.getPersonnel(loggedInMID.longValue()).getMessageFilter() :
									patientDAO.getPatient(loggedInMID.longValue()).getMessageFilter();
			if (!filter.equals("")) {
				String[] f = filter.split(",", -1);
				for (i = 0; i < 6; i++) {
					try {
						fields[i] = f[i];
					} catch (ArrayIndexOutOfBoundsException e) {
						//do nothing
					}
				}
			}
		}
	}

	//Sorts messages
	if (request.getParameter("sort") != null) {
		if (request.getParameter("sortby").equals("name")) {
			if (request.getParameter("sorthow").equals("asce")) {
				if(isHCP) {
					messages = outbox ? action.filterMessages(action.getAllMySentMessagesNameAscending(), 
																personnelDAO.getPersonnel(loggedInMID.longValue()).getMessageFilter()) : 
										action.filterMessages(action.getAllMyMessagesNameAscending(),
																personnelDAO.getPersonnel(loggedInMID.longValue()).getMessageFilter());
				} else {
					messages = outbox ? action.filterMessages(action.getAllMySentMessagesNameAscending(), 
																patientDAO.getPatient(loggedInMID.longValue()).getMessageFilter()) : 
										action.filterMessages(action.getAllMyMessagesNameAscending(),
																patientDAO.getPatient(loggedInMID.longValue()).getMessageFilter());
				}
			} else {
				if(isHCP) {
					messages = outbox ? action.filterMessages(action.getAllMySentMessagesNameDescending(), 
																personnelDAO.getPersonnel(loggedInMID.longValue()).getMessageFilter()) : 
										action.filterMessages(action.getAllMyMessagesNameDescending(),
																personnelDAO.getPersonnel(loggedInMID.longValue()).getMessageFilter());
				} else {
					messages = outbox ? action.filterMessages(action.getAllMySentMessagesNameDescending(), 
																patientDAO.getPatient(loggedInMID.longValue()).getMessageFilter()) : 
										action.filterMessages(action.getAllMyMessagesNameDescending(),
																patientDAO.getPatient(loggedInMID.longValue()).getMessageFilter());
				}
				//messages = outbox ? action.getAllMySentMessagesNameDescending() : 
				//					action.getAllMyMessagesNameDescending();
			}
		} else if (request.getParameter("sortby").equals("time")) {
			if (request.getParameter("sorthow").equals("asce")) {
				if(isHCP) {
					messages = outbox ? action.filterMessages(action.getAllMySentMessagesTimeAscending(), 
																personnelDAO.getPersonnel(loggedInMID.longValue()).getMessageFilter()) : 
										action.filterMessages(action.getAllMyMessagesTimeAscending(),
																personnelDAO.getPersonnel(loggedInMID.longValue()).getMessageFilter());
				} else {
					messages = outbox ? action.filterMessages(action.getAllMySentMessagesTimeAscending(), 
																patientDAO.getPatient(loggedInMID.longValue()).getMessageFilter()) : 
										action.filterMessages(action.getAllMyMessagesTimeAscending(),
																patientDAO.getPatient(loggedInMID.longValue()).getMessageFilter());
				}	
				//messages = outbox ? action.getAllMySentMessagesTimeAscending() : 
				//					action.getAllMyMessagesTimeAscending();
			} else {
				if(isHCP) {
					messages = outbox ? action.filterMessages(action.getAllMySentMessagesTimeDescending(), 
																personnelDAO.getPersonnel(loggedInMID.longValue()).getMessageFilter()) : 
										action.filterMessages(action.getAllMyMessagesTimeDescending(),
																personnelDAO.getPersonnel(loggedInMID.longValue()).getMessageFilter());
				} else {
					messages = outbox ? action.filterMessages(action.getAllMySentMessagesTimeDescending(), 
																patientDAO.getPatient(loggedInMID.longValue()).getMessageFilter()) : 
										action.filterMessages(action.getAllMyMessagesTimeDescending(),
																patientDAO.getPatient(loggedInMID.longValue()).getMessageFilter());
				}	
				//messages = outbox ? action.getAllMySentMessagesTimeDescending() : 
				//					action.getAllMyMessagesTimeDescending();
			}
		}
	} else {
		if(isHCP) {
			messages = outbox ? action.filterMessages(action.getAllMySentMessages(), 
														personnelDAO.getPersonnel(loggedInMID.longValue()).getMessageFilter()) : 
								action.filterMessages(action.getAllMyMessages(),
														personnelDAO.getPersonnel(loggedInMID.longValue()).getMessageFilter());
		} else {
			messages = outbox ? action.filterMessages(action.getAllMySentMessages(), 
														patientDAO.getPatient(loggedInMID.longValue()).getMessageFilter()) : 
								action.filterMessages(action.getAllMyMessages(),
														patientDAO.getPatient(loggedInMID.longValue()).getMessageFilter());
		}	
		//messages = outbox ? action.getAllMySentMessages() : 
		//					action.getAllMyMessages();
	}

	if (messages.size() > 0) {
		session.setAttribute("messages", messages);
	}

	//Filters Messages
	boolean is_filtered = false;
	if ((request.getParameter("filter") != null && request
			.getParameter("filter").equals("true"))
			|| request.getParameter("testFilter") != null) {
		String filter = "";
		if (request.getParameter("testFilter") != null) {
			filter = request.getParameter("testFilter");
		} else {
			filter = isHCP ? personnelDAO.getPersonnel(loggedInMID.longValue()).getMessageFilter():
							 patientDAO.getPatient(loggedInMID.longValue()).getMessageFilter();
		}
		if (!filter.equals("") && !filter.equals(",,,,,")) {
			List<MessageBean> filtered = action.filterMessages(messages, filter);
			messages = filtered;
			is_filtered = true;
		}
	}

	if (editing) {
%>
<div class="filterEdit">
	<div align="center">
		<span style="font-size: 13pt; font-weight: bold;">Edit Message
			Filter</span>
		<%=headerMessage.equals("") ? ""
						: "<br /><span class=\"iTrustMessage\">"
								+ headerMessage + "</span><br /><br />"%>
		<form method="post" action="messageInbox.jsp?edit=true">
			<table>
				<tr style="text-align: right;">
					<td><label for="sender">Sender: </label> <input type="text"
						name="sender" id="sender"
						value="<%=StringEscapeUtils.escapeHtml("" + (fields[0]))%>" />
					</td>
					<td style="padding-left: 10px; padding-right: 10px;"><label
						for="hasWords">Has the words: </label> <input type="text"
						name="hasWords" id="hasWords"
						value="<%=StringEscapeUtils.escapeHtml("" + (fields[2]))%>" />
					</td>
					<td><label for="startDate">Start Date: </label> <input
						type="text" name="startDate" id="startDate"
						value="<%=StringEscapeUtils.escapeHtml("" + (fields[4]))%>" />
						<input type="button" value="Select Date"
						onclick="displayDatePicker('startDate');" /></td>
				</tr>
				<tr style="text-align: right;">
					<td><label for="subject">Subject: </label> <input type="text"
						name="subject" id="subject"
						value="<%=StringEscapeUtils.escapeHtml("" + (fields[1]))%>" />
					</td>
					<td style="padding-left: 10px; padding-right: 10px;"><label
						for="notWords">Does not have the words: </label> <input
						type="text" name="notWords" id="notWords"
						value="<%=StringEscapeUtils.escapeHtml("" + (fields[3]))%>" />
					</td>
					<td><label for="endDate">End Date: </label> <input type="text"
						name="endDate" id="endDate"
						value="<%=StringEscapeUtils.escapeHtml("" + (fields[5]))%>" />
						<input type="button" value="Select Date"
						onclick="displayDatePicker('endDate');" /></td>
				</tr>
				<tr style="text-align: center;">
					<td colspan="3"><input type="submit" name="test"
						value="Test Filter" /> <input type="submit" name="save"
						value="Save" /> <input type="submit" name="cancel" value="Cancel" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<br />
<%
	}
%>
<form method="post"
	action="messageInbox.jsp<%=StringEscapeUtils.escapeHtml(""
					+ (is_filtered ? "?filter=true" : ""))%>">
	<table>
		<tr>
			<td><select name="sortby">
					<option value="time">Sort</option>
					<option value="name">Name</option>
					<option value="time">Time</option>
			</select></td>
			<td><select name="sorthow">
					<option value="desc">Order</option>
					<option value="asce">Ascending</option>
					<option value="desc">Descending</option>
			</select></td>
			<td><input type="submit" name="sort" value="Sort" /></td>
		</tr>
		<tr>
			<td><a href="messageInbox.jsp?edit=true">Edit Filter</a></td>
			<td><a href="messageInbox.jsp?filter=true">Apply Filter</a></td>
		</tr>
	</table>
</form>
<br />

<%
	if (messages.size() > 0) {
%>
<table id="mailbox" class="display fTable">
	<thead>
		<tr>
			<th><%=outbox ? "Receiver" : "Sender"%></th>
			<th>Subject</th>
			<th><%=outbox ? "Sent" : "Received"%></th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<%
			int index = -1;
				for (MessageBean message : messages) {
					String style = "";
					if (message.getRead() == 0) {
						style = "style=\"font-weight: bold;\"";
					}

					if (!outbox || message.getOriginalMessageId() == 0) {
						index++;
						String primaryName = action.getName(outbox ? message
								.getTo() : message.getFrom());
						List<MessageBean> ccs = action.getCCdMessages(message
								.getMessageId());
						String ccNames = "";
						int ccCount = 0;
						for (MessageBean cc : ccs) {
							ccCount++;
							long ccMID = cc.getTo();
							ccNames += action.getPersonnelName(ccMID) + ", ";
						}
						ccNames = ccNames.length() > 0 ? ccNames.substring(0,
								ccNames.length() - 2) : ccNames;
						String toString = primaryName;
						if (ccCount > 0) {
							String ccNameParts[] = ccNames.split(",");
							toString = toString + " (CC'd: ";
							for (int i = 0; i < ccNameParts.length - 1; i++) {
								toString += ccNameParts[i] + ", ";
							}
							toString += ccNameParts[ccNameParts.length - 1]
									+ ")";
						}
		%>
		<tr <%=style%>>
			<td><%=StringEscapeUtils.escapeHtml("" + (toString))%></td>
			<td><%=StringEscapeUtils.escapeHtml(""
								+ (message.getSubject()))%></td>
			<td><%=StringEscapeUtils.escapeHtml(""
								+ (dateFormat.format(message.getSentDate())))%></td>
			<td><a
				href="<%=outbox ? "viewMessageOutbox.jsp?msg="
								+ StringEscapeUtils.escapeHtml("" + (index))
								: "viewMessageInbox.jsp?msg="
										+ StringEscapeUtils.escapeHtml(""
												+ (index))%>">Read</a></td>
		</tr>
		<%
			}

				}
		%>
	</tbody>
</table>
<%
	} else {
%>
<div>
	<i>You have no messages</i>
</div>
<%
	}
%>