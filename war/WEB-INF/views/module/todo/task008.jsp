<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="org.jpage.util.*" %>
<%@ page import="org.jpage.core.query.paging.*" %>
<%@ page import="org.jpage.orm.*" %>
<%@ page import="org.jpage.orm.model.*" %>
<%@ page import="org.jpage.orm.util.*" %>
<%@ page import="org.jpage.persistence.Executor" %>
<%@ page import="org.jbpm.JbpmContext" %>
<%@ page import="org.jpage.jbpm.context.*" %>
<%@ page import="org.jpage.jbpm.service.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
	   String actorId = request.getParameter("actorId");
	   if(actorId != null){
		    List  taskItems = ProcessContainer.getContainer().getTaskItems(actorId);
            out.println(taskItems);
		}
%>