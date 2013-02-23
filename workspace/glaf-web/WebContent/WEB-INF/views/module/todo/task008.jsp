<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.glaf.util.*" %>
<%@ page import="com.glaf.core.query.paging.*" %>
<%@ page import="com.glaf.orm.*" %>
<%@ page import="com.glaf.orm.model.*" %>
<%@ page import="com.glaf.orm.util.*" %>
<%@ page import="com.glaf.persistence.Executor" %>
<%@ page import="org.jbpm.JbpmContext" %>
<%@ page import="com.glaf.jbpm.context.*" %>
<%@ page import="com.glaf.jbpm.service.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
	   String actorId = request.getParameter("actorId");
	   if(actorId != null){
		    List  taskItems = ProcessContainer.getContainer().getTaskItems(actorId);
            out.println(taskItems);
		}
%>