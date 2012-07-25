<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/jpage.tld" prefix="jpage"%>
<%@page import="java.util.*"%>
<%@page import="org.jpage.jbpm.util.*"%>
<%
    long taskInstanceId = new Long(request.getParameter("taskInstanceId")).longValue();
	String path = request.getContextPath()+"/workflow";
%>

<jpage:processimage task="<%=taskInstanceId%>"/> 