<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.alibaba.fastjson.*"%>
<%@ page import="org.jbpm.graph.def.*"%>
<%@ page import="org.jbpm.graph.exe.*"%>
<%@ page import="org.jbpm.taskmgmt.def.*"%>
<%@ page import="com.glaf.core.el.*"%>
<%@ page import="com.glaf.core.identity.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.jbpm.util.*"%>
<%@ page import="com.glaf.jbpm.model.*"%>
<%@ page import="com.glaf.jbpm.context.*"%>
<%@ page import="com.glaf.jbpm.config.*"%>
<%@ page import="com.glaf.jbpm.container.*"%>
<%@ page import="com.glaf.jbpm.datafield.*"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib uri="http://www.glaf.com/tags" prefix="glaf"%>
<%
  
 	Map variables = (Map) request.getAttribute("variables");
	ProcessInstance processInstance = (ProcessInstance) request.getAttribute("processInstance");
	ProcessDefinition processDefinition = (ProcessDefinition) request.getAttribute("processDefinition");
 	List finishedTaskItems = (List) request.getAttribute("finishedTaskItems");
	List taskItems = (List) request.getAttribute("taskItems");
	Map userMap = (Map) request.getAttribute("userMap");
    String processInstanceId = request.getParameter("processInstanceId");
	if (!(StringUtils.isNumeric(processInstanceId))) {
			processInstanceId = "0";
	}
	
%>
<!DOCTYPE html>
<html>
<title>流程任务</title> 
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>

<script language="javascript">

 
    function suspendX(btn){
		 if(confirm("该流程实例的全部待办任务都会被暂停执行，确认吗？")){
			 var params = jQuery("#iForm").formSerialize();
			  jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/jbpm/task/suspend',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
						 alert('操作成功完成！');
						 window.location.reload();
				   }
			 });
		 }
     };

	function resumeX(btn){
		 if(confirm("该流程实例的全部待办任务都会被恢复执行，确认吗？")){
              var params = jQuery("#iForm").formSerialize();
			  jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/jbpm/task/resume',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
						 alert('操作成功完成！');
						 window.location.reload();
				   }
			 });
		  }
     };
 
 
  function chooseUserXY(){
	  location.href= "<%=request.getContextPath()%>/mx/jbpm/task/chooseUser?processInstanceId=<%=processInstanceId%>";
  }
 
 

 function taskNotice(actorIds, process_subject, task_subject){
	 var link="<%=request.getContextPath()%>/mx/mail/compose?mailType=jbpm&templateId=task_notice&actorIds="+actorIds;
	 link = link + "&x_encode_process_subject="+process_subject+"&x_encode_task_subject="+task_subject;
	 location.href=link;
 }

</script>

</head>
<body >
<br>
<center>
<form id="iForm" name="iForm" class="x-form"
	action="<%=request.getContextPath()%>/mx/jbpm/task"><input
	type="hidden" id="processInstanceId" name="processInstanceId"
	value="<%=processInstanceId%>"> <input type="hidden"
	id="method" name="method" value="task">

<div class="content-block" style="width: 95%;">
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="流程实例信息"> 流程实例信息</div>
<br>

<fieldset class="x-fieldset" style="width: 95%;">
<legend>流程基本信息</legend>
<%if(processInstance != null){%>
<table class="x-table-border table table-striped table-bordered table-condensed" align="left" cellspacing="1" cellpadding="4" width="98%" nowrap>
	<tr>
		<td width="12%" height="12" align="left">流程名称</td>
		<td width="38%"><%=ParamUtils.getString(variables, Constant.PROCESS_NAME, "")%>
		</td>
		<td width="12%" height="12" align="left">流程编号</td>
		<td width="38%"><%=processInstance.getId()%></td>
	</tr>
	<tr>
		<td width="12%" height="12" align="left">流程主题</td>
		<td width="38%"><%=ParamUtils.getString(variables, Constant.PROCESS_TITLE, "")%>
		</td>
		<td width="12%" height="12" align="left">单据编号</td>
		<td width="38%"><%=ParamUtils.getString(variables, Constant.PROCESS_ROWID, "")%>
		</td>
	</tr>
	<tr>
		<td width="12%" height="12" align="left">启动者</td>
		<td width="38%"><%=ParamUtils.getString(variables, Constant.PROCESS_STARTERID, "")%>&nbsp;&nbsp;
		<%
        String actorId =  ParamUtils.getString(variables, Constant.PROCESS_STARTERID, "");
        if(StringUtils.isNotEmpty(actorId) && userMap != null){
            User user = (User)userMap.get(actorId);		
            if(user != null){
                  out.println(user.getName());
            }
		 }
	    %>
		</td>
		<td width="12%" height="12" align="left">启动时间</td>
		<td width="38%"><%=DateUtils.getDateTime(processInstance.getStart())%>
		</td>
	</tr>
</table>
<%}%>
</fieldset>
<br>
<fieldset class="x-fieldset" style="width: 95%;">
<legend>流程处理信息</legend>
<table class="x-table-border table table-striped table-bordered table-condensed" align="left" cellspacing="1" cellpadding="4" width="98%" nowrap>
	<%
        if(finishedTaskItems != null && finishedTaskItems.size() > 0 && userMap != null){
            Iterator iterator = finishedTaskItems.iterator();
            while(iterator.hasNext()){
            	com.glaf.jbpm.model.TaskItem ti = (com.glaf.jbpm.model.TaskItem)iterator.next();
            	User user = (User)userMap.get(ti.getActorId());					
   %>
	<tr>
		<td align="left"><%=ti.getTaskName()%>&nbsp;&nbsp;<%=ti.getTaskDescription()%>
		</td>
		<td align="left"><%=user != null ? user.getName():""%> [<%=user != null ?user.getActorId() : ""%>]
		</td>
		<td align="center"><%=DateUtils.getDateTime(ti.getStartDate())%>
		</td>
		<td align="left">
		<% 
	           if(StringUtils.equals(ti.getIsAgree(), "true")){
                      out.println("<b><font color=\"green\">通过</font></b>");
               } else if(StringUtils.equals(ti.getIsAgree(), "false")){
                      out.println("<b><font color=\"red\">不通过</font></b>");
               } 
            	%> &nbsp;&nbsp;<%=ti.getOpinion() != null ? "[ "+ti.getOpinion() +" ]": ""%>
		</td>
	</tr>
	<%}
      }%>
</table>
</fieldset>
<br>
<% if(taskItems != null && taskItems.size() > 0){%>
<fieldset class="x-fieldset"  style="width: 95%;">
<legend>待办任务</legend>
<table class="x-table-border table table-striped table-bordered table-condensed" align="left" cellspacing="1" cellpadding="4" width="98%" nowrap>
	<%
        Iterator iterator24 = taskItems.iterator();
        while(iterator24.hasNext()){
            com.glaf.jbpm.model.TaskItem item = (com.glaf.jbpm.model.TaskItem)iterator24.next();
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
            } else {
            	out.println("【"+item.getTaskName() + "&nbsp;");
            }

            out.println("&nbsp;&nbsp;任务实例编号<font color=\"#0066FF\"><b>");
            out.println(item.getTaskInstanceId());
            out.println("</b></font>");

            out.println("&nbsp;&nbsp;<font color=\"#FF6600\"><b>");
            if(user != null){
            	out.println(user.getName()+" ["+user.getActorId()+"]&nbsp;");
                if(StringUtils.isNotEmpty(user.getMail())){
%> <a
			href="<%=request.getContextPath()%>/mx/message/edit?messageType=jbpm&templateId=task_notice&actorIds=<%=user.getActorId()%>&x_encode_process_subject=<%=RequestUtils.encodeString(processDefinition.getDescription() != null ? processDefinition.getDescription() : processDefinition.getName() )%>&x_encode_task_subject=<%= RequestUtils.encodeString(item.getTaskDescription()  != null ? item.getTaskDescription() : item.getTaskName() )%>">
		发送消息&nbsp;<img
			src="<%=request.getContextPath()%>/images/sendmail.gif"
			style="cursor: pointer;" border="0" title="发送消息给<%=user.getName()%>">
		</a> <%
				}
            }else {
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

<%}%> <br>

<fieldset class="x-fieldset"  style="width: 95%;"><legend>流程图</legend>
<br />
<div id="task_processimage" align="center">
  <glaf:processimage processInstanceId="<%=new Long(processInstanceId).longValue()%>" />
</div>
<br />
</fieldset>



<div align="center"><br />
 <input type="button" value="暂停任务" id="suspend" name="suspend"
	class="btn" onclick="javascript:suspendX();" />
 <input type="button"
	value="恢复任务" id="resume" name="resume" class="btn"
	onclick="javascript:resumeX();" /> 
 <input type="button" value="重新分派任务"
	id="reassignx" name="reassignx" class="btn"
	onclick="javascript:chooseUserXY();" />  
 <input type="button"
	value="关闭" id="close" name="close" class="btn"
	onclick="javascript:window.close();" /> 
<br/>
<br/>
</div>

</div>
</div>
</form>
<br>
</center>
</body>
</html>