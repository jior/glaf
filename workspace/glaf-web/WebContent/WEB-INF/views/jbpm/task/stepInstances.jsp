<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.alibaba.fastjson.*"%>
<%@ page import="org.jbpm.graph.def.*"%>
<%@ page import="org.jbpm.graph.exe.*"%>
<%@ page import="org.jbpm.taskmgmt.def.*"%>
<%@ page import="com.glaf.core.el.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.jbpm.util.*"%>
<%@ page import="com.glaf.jbpm.model.*"%>
<%@ page import="com.glaf.jbpm.context.*"%>
<%@ page import="com.glaf.jbpm.config.*"%>
<%@ page import="com.glaf.jbpm.container.*"%>
<%@ page import="com.glaf.jbpm.datafield.*"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ include file="/WEB-INF/views/inc/x_header.jsp"%>
<%
     
	Map variables = (Map) request.getAttribute("variables");
	ProcessInstance processInstance = (ProcessInstance) request.getAttribute("processInstance");
	List finishedTaskItems = (List) request.getAttribute("finishedTaskItems");
	List taskItems = (List) request.getAttribute("taskItems");
	Map userMap = (Map) request.getAttribute("userMap");
    String processInstanceId = request.getParameter("processInstanceId");
	if (!(StringUtils.isNumeric(processInstanceId))) {
			processInstanceId = "0";
	}
	
%>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>

<br>
<center>
<div class="content-block" style="width: 95%;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="流程实例信息"> ${tree.name}流程实例信息</div>
<br>
<form id="iForm" name="iForm" class="x-form" action=""><input
	type="hidden" id="processInstanceId" name="processInstanceId"
	value="<%=processInstanceId%>"> <% if(taskItems != null && taskItems.size() > 0){%>
<fieldset class="x-fieldset" style="width: 95%;"><legend>待办任务</legend>
<table align="center" cellspacing="1" cellpadding="4" width="95%" nowrap>
	<%
						  Iterator iterator24 = taskItems.iterator();
						  while(iterator24.hasNext()){
							TaskItem item = (TaskItem)iterator24.next();
							if(item.getActorId()!=null){
					%>
	<tr>
		<td>
		<%
						User user = (User)userMap.get(item.getActorId());
						
						if(item.getCreateDate() != null){
							out.println(DateUtils.getDateTime(item.getCreateDate()));
						}
						if(item.getTaskDescription() != null){
							out.println("【"+item.getTaskName() + "&nbsp;" + item.getTaskDescription()+"】");
						}else{
							out.println("【"+item.getTaskName() + "&nbsp;");
						}

						out.println("&nbsp;&nbsp;任务实例编号<font color=\"#0066FF\"><b>");
						out.println(item.getTaskInstanceId());
						out.println("</b></font>");

						out.println("&nbsp;&nbsp;<font color=\"#FF6600\"><b>");
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
	<%}%>
</table>
</fieldset>
<br>
<%}%>

<fieldset class="x-fieldset" style="width: 95%;"><legend>流程图</legend>
<br />
<div id="task_processimage" align="center"><jpage:processimage
	processInstanceId="<%=new Long(processInstanceId).longValue()%>" /></div>
<br />
</fieldset>
</form>
</div>