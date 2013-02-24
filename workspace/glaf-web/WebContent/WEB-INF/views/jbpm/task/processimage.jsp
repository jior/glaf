<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib uri="http://www.glaf.com/tags" prefix="glaf"%>
<%
    String processInstanceId = request.getParameter("processInstanceId");
	if (!(StringUtils.isNumeric(processInstanceId))) {
		processInstanceId = "0";
	}
	
%>
<glaf:processimage processInstanceId="<%=new Long(processInstanceId).longValue()%>" />