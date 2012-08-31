<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="org.jbpm.graph.exe.*"%>
<%@ page import="org.jpage.actor.User"%>
<%@ page import="org.jpage.jbpm.model.*"%>
<%@ page import="org.jpage.jbpm.util.*"%>
<%@ page import="org.jpage.core.query.paging.Page" %>
<%
    int index = 1;
  %>
<HTML><HEAD>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/pages/common/style.jsp"%>
<LINK href="<%=request.getContextPath()%>/workflow/styles/<c:out value="${frame_skin}"/>/main/main.css" type=text/css rel=stylesheet>
</HEAD>
<script language="JavaScript">

  function taskQuery(){
	  location.href="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=taskQuery";
  }

</script>
<body leftMargin=0 topMargin=0 marginheight="0" marginwidth="0">
<br>
<table border="0" width="90%" align="center">
 <tr>
	  <td align="right">
	    <input type="button" name="button" class="button" value=" 查询 " onclick="javascript:taskQuery();">
	  </td>
 </tr>
</table>
<br>
<div align="center"><b>任务实例列表</b></div><br>

<table align="center" class="table-border" cellspacing="1" cellpadding="4" width="90%" nowrap>
    <tr class="table-bar" nowrap>
	  <td width="5%" height="15" align="center" class="table-title" nowrap>序号</td>
	  <td width="5%" height="15" align="center" class="table-title" nowrap>流程编号</td>
	  <td width="5%" height="15" align="center" class="table-title" nowrap>单据编号</td>
	  <td width="5%" height="15" align="center" class="table-title" nowrap>任务编号</td>
	  <td width="10%" height="15" align="center" class="table-title" nowrap>流程名称</td>
	  <td width="10%" height="15" align="center" class="table-title" nowrap>处理者</td>
      <td width="20%" height="15" align="center" class="table-title" nowrap>主题</td>
	  <td width="20%" height="15" align="center" class="table-title" nowrap>任务名称</td>
	  <td width="10%" height="15" align="center" class="table-title" nowrap>创建时间</td>
	  <td width="10%" height="15" align="center" class="table-title" nowrap>功能键</td>
    </tr>
    <c:forEach items="${taskItems}" var="a">
	<%
	  TaskItem taskItem = (TaskItem)pageContext.getAttribute("a");
      Map userMap = 	(Map) request.getAttribute("userMap");	
	  if(userMap != null && userMap.get(taskItem.getActorId()) != null){
		  User u = (User) userMap.get(taskItem.getActorId());
		  if(u != null){
		      taskItem.setActorName(u.getName());
		  }
	  }
	%>
    <tr class="beta">
	  <td width="5%" height="15" align="center" nowrap><%=index++%></td>
	  <td width="5%" height="15" align="center">
	  <a href="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=stateInstances&processInstanceId=<c:out value="${a.processInstanceId}"/>" title="查看流程实例" target="_blank">
	  <c:out value="${a.processInstanceId}"/>
	  </a>
	  </td>
	  <td width="5%" height="15" align="center" nowrap>
	    <c:out value="${a.businessValue}"/>
	  </td>
	  <td width="5%" height="15" align="center" nowrap>
	    <c:out value="${a.taskInstanceId}"/>
	  </td>
	  <td width="10%" height="15" align="left">
	  <a href="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=stateInstances&processInstanceId=<c:out value="${a.processInstanceId}"/>" target="_blank">
	      <c:out value="${a.processName}"/>
	  </a>
	  </td>
	  <td width="10%" height="15" align="left" nowrap>
        <c:out value="${a.actorId}"/> <c:out value="${a.actorName}"/>
	  </td>
      <td width="20%" height="15" align="left" nowrap>
	    <c:out value="${a.businessSubject}"/>
	  </td>
	  <td width="20%" height="15" align="left" nowrap>
	    <c:out value="${a.taskName}"/> <c:out value="${a.taskDescription}"/>
	  </td>
	  <td width="10%" height="15" align="center" nowrap>
	  <fmt:formatDate value="${a.taskCreateDate}" pattern="yyyy-MM-dd"/>
	  </td>
	  <td width="10%" height="15" align="center" nowrap>
	 
	  <a href="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=stateInstances&processInstanceId=<c:out value="${a.processInstanceId}"/>" title="查看流程实例" target="_blank"><img src="<%=request.getContextPath()%>/workflow/images/view.gif" border="0"> </a>
	  </td>
    </tr>
    </c:forEach>

</table>

 </BODY>
</HTML>