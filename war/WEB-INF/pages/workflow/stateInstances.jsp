<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/jpage.tld" prefix="jpage" %>
<%@ page import="java.util.*" %>
<%@ page import="org.jpage.jbpm.util.*" %>
<%@ page import="org.jpage.jbpm.model.*" %>
<%
  Map userMap = (Map)request.getAttribute("userMap");
%>
<HTML>
<HEAD>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/pages/common/style.jsp"%>
<LINK href="<%=request.getContextPath()%>/workflow/styles/<c:out value="${frame_skin}"/>/main/main.css" type=text/css rel=stylesheet>
</HEAD>
<body>
<div align="center"><b>流程实例信息</b></div><br>

<table align="center" class="table-border" cellspacing="1" cellpadding="4" width="90%" nowrap>
  <tr class="beta">
    <td class="table-bar" width="12%" height="12" align="center"><b>流程名称</b></td>
    <td class="table-content" width="38%">
	<c:out value="${processDefinition.name}"/>
	</td>
    <td class="table-bar" width="12%" height="12" align="center"><b>流程版本</b></td>
    <td class="table-content" width="38%">
	<c:out value="${processDefinition.version}"/>
	</td>
  </tr>
  <tr class="beta">
	<td class="table-bar" width="12%" height="12" align="center"><b>启动者</td>
    <td class="table-content" width="38%">
	<c:out value="${starter.name}"/>[<c:out value="${starter.actorId}"/>]
	</td>
    <td class="table-bar" width="12%" height="12" align="center"><b>启动时间</b></td>
    <td class="table-content" width="38%">
	<fmt:formatDate value="${processInstance.start}" pattern="yyyy-MM-dd HH:mm:ss"/>
	</td>
  </tr>
  <tr class="beta">
	<td class="table-bar" width="12%" height="12" align="center"><b>业务编号</td>
    <td class="table-content" width="38%">
	<c:out value="${processInstance.key}"/>
	</td>
    <td class="table-bar" width="12%" height="12" align="center"><b>结束时间</b></td>
    <td class="table-content" width="38%">
	<fmt:formatDate value="${processInstance.end}" pattern="yyyy-MM-dd HH:mm:ss"/>
	</td>
  </tr>
   
 </table>
<br>
<table align="center" class="table-border" cellspacing="1" cellpadding="4" width="90%" nowrap>
<tr class="table-bar">
    <td height="12" align="center" class="table-title">任务名称</td>
    <td height="12" align="center" class="table-title">处理者</td>
    <td height="12" align="center" class="table-title">处理时间</td>
	<td height="12" align="center" class="table-title">是否通过</td>
	<td height="12" align="center" class="table-title">处理意见</td>
</tr>		
<%
    String taskInstanceId = null;
	String path = request.getContextPath()+"/workflow";
    pageContext.setAttribute("contextPath", request.getContextPath());
    List stateInstances = (List)request.getAttribute("rows");
     StringBuffer attachBuffer = new StringBuffer();

	if(stateInstances != null && stateInstances.size() > 0){
		Iterator iterator = stateInstances.iterator();
        while(iterator.hasNext()){
			StateInstance stateInstance = (StateInstance)iterator.next();
			org.jpage.actor.User user = (org.jpage.actor.User)userMap.get(stateInstance.getActorId());
			pageContext.setAttribute("stateInstance", stateInstance);
			taskInstanceId = stateInstance.getTaskInstanceId();
			attachBuffer.delete(0, attachBuffer.length());
	 
%>
<tr class="beta">
    <td class="table-content" align="left">
	<c:out value="${stateInstance.taskName}"/>&nbsp;&nbsp;<c:out value="${stateInstance.taskDescription}"/>
    </td>
    <td class="table-content" align="left"><%=user != null ? user.getName():""%>[<c:out value="${stateInstance.actorId}"/>]
    </td>
    <td class="table-content" align="center">
	<fmt:formatDate value="${stateInstance.startDate}" pattern="yyyy-MM-dd HH:mm"/>
    </td>
	<td class="table-content" align="center">
    <c:if test="${stateInstance.opinion == 1}">
	   是
    </c:if>
	<c:if test="${stateInstance.opinion == 0}">
	   否
    </c:if>
	<td class="table-content" align="left">
    <c:if test="${not empty stateInstance.content}">
	   <c:out value="${stateInstance.content}"/>
    </c:if>
	</td>
</tr>
<%}%>
<%}%>
</table>

<br>

<%
	Collection taskItems = (Collection)request.getAttribute("taskItems");
	
    if(taskItems != null && taskItems.size() > 0){
%>
<table align="center" class="table-border" cellspacing="1" cellpadding="4" width="90%" nowrap>
	    <%
	      Iterator iterator24 = taskItems.iterator();
          while(iterator24.hasNext()){
			TaskItem item = (TaskItem)iterator24.next();
			if(item.getActorId()!=null){
%>
<tr class="beta">
    <td class="table-bar" width="12%" height="16" align="center"><b>待办任务</b></td>
    <td class="table-content" width="88%" >
<%
	org.jpage.actor.User user = (org.jpage.actor.User)userMap.get(item.getActorId());
	
	if(item.getTaskCreateDate() != null){
        out.println(org.jpage.util.DateTools.getDateTime(item.getTaskCreateDate()));
	}
	if(item.getTaskDescription() != null){
		out.println("【"+item.getTaskInstanceId()+ "&nbsp;"+item.getTaskName() + "&nbsp;" + item.getTaskDescription()+"】");
	}else{
        out.println("【"+item.getTaskInstanceId()+ "&nbsp;"+item.getTaskName() + "&nbsp;");
	}
	out.println("<font color=\"#FF6600\"><b>");
	if(user != null){
		out.println(user.getName()+" ["+user.getActorId()+"]");
	}else{
        out.println(item.getActorId());
	}
	out.println("</b></font>");
  }
%>
    </td>
</tr>
<%
	 }
}%>
</table>
<br><br>

<c:if test="${tokenInstanceId > 0 }">
    <%
	    Long tokenInstanceId = (Long)request.getAttribute("tokenInstanceId");
	%>
    <center>
    <jpage:processimageToken token="<%=tokenInstanceId.longValue()%>"/> 
	</center>
</c:if>