<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.query.*"%>
<%@ page import="com.glaf.core.service.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.core.base.*"%>
<%@ page import="com.glaf.core.service.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%
     String contextPath = request.getContextPath();
	 Date today = new Date();
	 ISysSchedulerService sysSchedulerService = ContextFactory.getBean("sysSchedulerService");
	 List<Scheduler> list = sysSchedulerService.getSchedulers(com.glaf.dts.util.Constants.DTS_TASK_TYPE);
	 pageContext.setAttribute("schedulers", list);

%>
<!DOCTYPE html>
<html>
<title>任务调度管理</title>
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>	
<script language="javascript">

function edit() {
    location.href="<%=request.getContextPath()%>/mx/dts/scheduler/edit";
}

function edit(taskId) {
    location.href="<%=request.getContextPath()%>/mx/dts/scheduler/edit?taskId="+taskId;
}

function deleteScheduler(taskId) {
		if(confirm("数据删除后不能恢复，您真的要删除吗？")) { 			              
			jQuery.ajax({
				   type: "POST",
				   url: "<%=request.getContextPath()%>/rs/dts/scheduler/delete/"+taskId,
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
}

function lockedScheduler(taskId, locked) {
	if(locked == "1"){
		if(confirm("锁定将无法进行任务调度，您真的要锁定吗？")) { 			              
			 	jQuery.ajax({
				   type: "POST",
				   url: "<%=request.getContextPath()%>/rs/dts/scheduler/locked/"+taskId+"/"+locked,
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
	}else{
		jQuery.ajax({
				   type: "POST",
				   url: "<%=request.getContextPath()%>/rs/dts/scheduler/locked/"+taskId+"/"+locked,
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
}

function schedulerXY(taskId, startup) {
	if(startup == "false"){
		if(confirm("您真的要停止任务调度吗？")) { 
		   jQuery.ajax({
				   type: "POST",
				   url: "<%=request.getContextPath()%>/rs/dts/scheduler/startup/"+taskId+"/"+startup,
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
	} else{
		jQuery.ajax({
				   type: "POST",
				   url: "<%=request.getContextPath()%>/rs/dts/scheduler/startup/"+taskId+"/"+startup,
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
}

</script>
</head>
<body style="padding-left:20px;padding-right:20px">
<br>
<center>
<div class="x_content_title">
    <img src="<%=request.getContextPath()%>/images/window.png" alt="DTS任务调度">&nbsp;DTS任务调度
</div>
<br>
<table align="center" class="table-border" cellspacing="0"
	cellpadding="0" width="90%">
	<tr>
		<td height="28" align="right">
		    <input type="button" value="新增任务调度" name="edit" class="btn" onclick="javascript:edit();">
		</td>
	</tr>
</table>

<br>

<table align="center" class="x-table-border table table-striped table-bordered table-condensed" cellspacing="1"
	cellpadding="4" width="90%" border="0">
	<tr class="x-title">
		<td align="center" noWrap>任务名称</td>
		<td align="center" noWrap>主题</td>
		<td align="center" noWrap>开始时间</td>
		<td align="center" noWrap>结束时间</td>
		<td align="center" noWrap>状态</td>
		<td align="center" noWrap>功能键</td>
	</tr>
	<c:forEach items="${schedulers}" var="scheduler">
		<%
        Scheduler model = (Scheduler)pageContext.getAttribute("scheduler");
		String startupImg = "stop.gif";
		String label = "启动任务";
		String status = "<font color=red><b>已停止</b><font>";
		int running = -1;
		if(model.getEndDate() != null &&
			   model.getEndDate().getTime() < today.getTime()){
			      startupImg = "stop.gif";
				  label = "任务过期";
				  status = "<font color=blue><b>已过期</b><font>";
		} else {
			if(!model.isSchedulerStartup()){
                running = 0;
				label = "启动任务";
			}else{
				startupImg = "running.gif";
				running = 1;
				label = "停止任务";
				status = "<font color=green><b>运行中</b><font>";
			}
		}

        int lockedFlag = model.getLocked();
		String locked = lockedFlag+"";	
		String alt = "启用";
		String title = "";
		String lockedImg = "locked0.gif";

		if(lockedFlag == 1){
			locked = "0";
			alt = "启用";
			title="<font color=\"red\">已锁定</font>";
			lockedImg = "locked1.gif";
		} else {
			lockedImg = "locked0.gif";
		    locked = "1";
			alt = "锁定";
			title="<font color=\"green\">已启用</font>";
	   }
	    pageContext.setAttribute("lockedImg",lockedImg);
	    pageContext.setAttribute("locked",locked);
		pageContext.setAttribute("title",title);
	    pageContext.setAttribute("alt",alt);
 
  %>
		<tr  class="x-content">
			<td align="left" noWrap>&nbsp; <a
				href="<%=request.getContextPath()%>/mx/dts/scheduler/edit?taskId=${scheduler.taskId}">
			${scheduler.taskName} </a></td>
			<td align="left" noWrap>&nbsp; ${scheduler.title}
			</td>
			<td align="center" noWrap>&nbsp; <fmt:formatDate
				value="${scheduler.startDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			<td align="center" noWrap>&nbsp; <fmt:formatDate
				value="${scheduler.endDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			
			<td align="center" noWrap>&nbsp; <b>${title}</b></td>
			<td align="left" valign="center" noWrap>&nbsp; <a
				href="<%=request.getContextPath()%>/mx/dts/scheduler/edit?taskId=${scheduler.taskId}">
			<img src="<%=request.getContextPath()%>/images/update.gif"
				title="修改记录" border="0" style="cursor: hand;">修改</a> 
			  
			   <a href="#" onclick="javascript:deleteScheduler('${scheduler.taskId}');"  style="cursor: hand;"  ><img src="<%=request.getContextPath()%>/images/delete.gif"
				title="删除记录" border="0"> 删除 </a>
				
				&nbsp; <img
				src="<%=request.getContextPath()%>/images/${lockedImg}"
				title="${alt}" border="0" style="cursor: hand;"
				onclick="javascript:lockedScheduler('${scheduler.taskId}','${locked}');">
			<%
			if(model.getLocked() == 0){
			  if(running > 0){%> &nbsp; <img
				src="<%=request.getContextPath()%>/images/<%=startupImg%>"
				title="<%=label%>" border="0" style="cursor: hand;" height="18"
				width="18"
				onclick="javascript:schedulerXY('${scheduler.taskId}','false');">
			<%}else{%> &nbsp; <img
				src="<%=request.getContextPath()%>/images/stop.gif" border="0"
				style="cursor: hand;" height="18" width="18"
				onclick="javascript:schedulerXY('${scheduler.taskId}','true');">
			<%}%> <%=status%> <%}%>
			</td>
		</tr>
	</c:forEach>

</table>
</center>
</form>
</body>
</html>

