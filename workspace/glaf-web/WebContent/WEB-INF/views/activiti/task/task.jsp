<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.activiti.engine.*"%>
<%@ page import="org.activiti.engine.history.*"%>
<%@ page import="org.activiti.engine.repository.*"%>
<%@ page import="org.activiti.engine.task.*"%>
<%@ page import="org.activiti.engine.runtime.*"%>
<%@ page import="com.glaf.core.base.*"%>
<%@ page import="com.glaf.core.identity.*"%>
<%@ page import="com.glaf.activiti.model.*"%>
<%
     String x_script = (String)request.getAttribute("x_script");
%>
<!DOCTYPE html>
<html>
<head>
<title>查看流程实例</title>
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>

</head>
<body>
<br />
<div class="txt_content_frame" style=" width: 98%;  ">
<div class="txt_content_box">
 
<div style=" width: 92%; padding-left:40px; " >
<br>
<div class="x_content_title"> <img
	src="<%=request.getContextPath()%>/images/window.png" alt="流程实例信息">
 流程实例信息
</div>
<br>
<fieldset class="x-fieldset" style=" width: 95%;" >
<legend>流程基本信息</legend>

<table align="left" cellspacing="1" cellpadding="4" width="90%" class="x-table-border table table-striped table-bordered table-condensed"  nowrap>
	<tr>
		<td width="12%" height="12" align="left">流程名称</td>
		<td width="38%">${processDefinition.name}
		</td>
		<td width="12%" height="12" align="left">流程版本</td>
		<td width="38%">${processDefinition.version}</td>
	</tr>
	<tr>
		<td width="12%" height="12" align="left">业务编号</td>
		<td width="38%"> 
		    ${historyProcessInstance.businessKey}
		</td>
		<td width="12%" height="12" align="left">启动者</td>
		<td width="38%"> 
            ${historyProcessInstance.startUserId}
		</td>
	</tr>
	<tr>
		<td width="12%" height="12" align="left">启动时间</td>
		<td width="38%">
            <fmt:formatDate value="${historyProcessInstance.startTime}" pattern="yyyy-MM-dd HH:mm" />
		</td>
		<td width="12%" height="12" align="left">结束时间</td>
		<td width="38%"> 
		     <c:choose>
				<c:when test="${ not empty historyProcessInstance.endTime}">
				  <font color="#00CC33" >
				  <fmt:formatDate value="${historyProcessInstance.endTime}" pattern="yyyy-MM-dd HH:mm" />
				  </font>
				</c:when>
				<c:otherwise>
                      <font color="#0066FF" ><b> 运行中 </b></font>
				</c:otherwise>
			</c:choose>
		    
		</td>
	</tr>
</table>
</fieldset>

<%
 List<Object> historyTaskItems = (List<Object>)request.getAttribute("historyTaskItems");
 if(historyTaskItems != null && historyTaskItems.size() > 0){
 %>
<br>
<fieldset class="x-fieldset" style=" width: 95%;">
<legend>历史任务信息</legend>
<table align="left" cellspacing="1" cellpadding="4" width="90%" class="x-table-border table table-striped table-bordered table-condensed" nowrap>
  <tr align="left">
	  <td>&nbsp;任务名称</td>
	  <td>&nbsp;执行人</td>
	  <td>&nbsp;创建时间</td>
	  <td>&nbsp;处理时间</td>
 </tr>
  <%
  	Iterator iterator = historyTaskItems.iterator();
    while(iterator.hasNext()){
  	  com.glaf.activiti.model.TaskItem taskItem = (com.glaf.activiti.model.TaskItem)iterator.next();
  	  pageContext.setAttribute("taskItem", taskItem);
  	  String assignee = taskItem.getActorId();
  	  User  userProfile = IdentityFactory.getUser(assignee);
  	  pageContext.setAttribute("userProfile", userProfile);
  %>
    <tr align="left">
	  <td>&nbsp; ${taskItem.taskDefinitionKey} &nbsp;${taskItem.taskName} [${taskItem.taskInstanceId}]</td>
	  <td>&nbsp; ${taskItem.actorId} [${userProfile.name}] </td>
	  <td>&nbsp; <fmt:formatDate value="${taskItem.startTime}" pattern="yyyy-MM-dd HH:mm" /> </td>
	  <td>&nbsp; <fmt:formatDate value="${taskItem.endTime}" pattern="yyyy-MM-dd HH:mm" /> </td>
	</tr>
 <%}%>
</table>
</fieldset>
 <%}%>

  <%
      List<com.glaf.activiti.model.TaskItem> taskItems = (List<com.glaf.activiti.model.TaskItem>)request.getAttribute("taskItems");
	  if(taskItems != null && taskItems.size() > 0){
%>
<br>
<fieldset class="x-fieldset" style=" width: 95%;">
<legend>待办任务</legend>
<table align="left" cellspacing="1" cellpadding="4" width="90%" class="x-table-border table table-striped table-bordered table-condensed" nowrap>
  <tr>
	  <td>&nbsp;任务编号</td>
	  <td>&nbsp;任务名称</td>
	  <td>&nbsp;执行人</td>
	  <td>&nbsp;创建时间</td>
	  <td>&nbsp;状态</td>
 </tr>
  <%
		  for(com.glaf.activiti.model.TaskItem taskItem :taskItems){
			  pageContext.setAttribute("taskItem", taskItem);            
  %>
    <tr>
	  <td>&nbsp;<font color="#0066FF"><b>${taskItem.taskInstanceId}</b></font> </td>
	  <td>&nbsp;【${taskItem.taskDefinitionKey} &nbsp; ${taskItem.taskName}】 </td>
	  <td>&nbsp;
	  <c:choose>
			<c:when test="${ not empty taskItem.actorId  }">
			   <font color="#FF6600" ><b>${taskItem.actorId}[${taskItem.actorName}] </b></font>
			</c:when>
			<c:when test="${not empty taskItem.groupId  }">
			    组 ${taskItem.groupId}[${taskItem.groupName}] 
			</c:when>
	        <c:otherwise>
				 尚未分配用户
			</c:otherwise>
		</c:choose>
	  </td>
	  <td>&nbsp;<fmt:formatDate value="${taskItem.startTime}" pattern="yyyy-MM-dd HH:mm" /> </td>
	  <td>&nbsp;<font color="#0066FF" ><b> 未处理 </b></font> </td>
	</tr>
	<%}%>
 </table>
</fieldset>
 <%}%>
<br />
 <div align="center" style="width:100%">
  <iframe id="processimage" align="center" frameborder="0" width="1020" height="800" scrolling="no" 
       src="<%=request.getContextPath()%>/mx/activiti/view?processInstanceId=${processInstanceId}" >
  </iframe>
  </div>
  
 </div>
 </div>
</div>
<br />
</body>
</html>