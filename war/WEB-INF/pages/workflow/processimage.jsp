<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/jpage.tld" prefix="jpage"%>
<%@page import="java.util.*"%>
<%@page import="org.jpage.jbpm.util.*"%>
<%
    long taskInstanceId = new Long(request.getParameter("taskInstanceId")).longValue();
	String path = request.getContextPath()+"/workflow";
%>

<jpage:processimage task="<%=taskInstanceId%>"/> 