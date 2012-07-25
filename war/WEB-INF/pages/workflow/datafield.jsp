<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ page import="java.util.*"%>
<%@page import="org.jpage.util.*"%>
<%@page import="org.jpage.jbpm.util.*"%>
<%@page import="org.jpage.jbpm.model.*"%>
<%@page import="org.jpage.jbpm.def.model.*"%>
<%
    StringBuffer buffer = new StringBuffer();
    List controlFields = (List) request.getAttribute("controlFields");
	if(controlFields != null && controlFields.size() > 0){
		Iterator iterator = controlFields.iterator();
		while(iterator.hasNext()){
			ControlField field = (ControlField) iterator.next();
			String fieldName = field.getFieldName();
			String description = field.getDescription();
			String initialValue = field.getInitialValue();
			if(description == null){
				description = fieldName;
			}
			if(initialValue == null){
				initialValue = "";
			}
			int controlType = field.getControlType();
			switch (controlType) {
			    case ControlType.DATAFIELD_TYPE:
					buffer.append("<tr class=\"beta\">\n");
					buffer.append("<td class=\"table-title\" width=\"18%\" height=\"16\" align=\"center\">")
					      .append(description).append("</td>\n");
				    buffer.append("<td class=\"table-content\" width=\"82%\" height=\"16\" align=\"left\">\n")
					      .append("<input type=\"text\" name=\"").append(org.jpage.jbpm.util.Constant.PROCESS_DATAFIELD_PREFIX).append(fieldName).append("\"");
				    buffer.append(" value=\"").append(initialValue).append("\" size=\"50\" class=\"txt\">\n");
					buffer.append("</td>\n");
					buffer.append("</tr>\n");
				    break;
				default:
					break;
			}
		}
	}
%>

<%if(buffer.length() > 0){%>
<table align="center" class="table-border" cellspacing="1" cellpadding="4" width="98%">
<%=buffer.toString()%>
</table>
<%}%>