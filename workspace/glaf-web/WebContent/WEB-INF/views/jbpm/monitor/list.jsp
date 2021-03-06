<%@ page contentType="text/html;charset=UTF-8"%>
<%
        int sortNo = 1;
%>
<!DOCTYPE html>
<html>
<title>流程监控</title> 
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
</head>
<body style="padding-left:20px;padding-right:20px">
<center><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="流程定义信息">&nbsp;流程定义信息</div>
<br>

<table align="center" class="x-table-border table table-striped table-bordered table-condensed" cellspacing="1"
	cellpadding="4" width="90%">
	<thead>
		<tr class="x-title">
			<td align="center" noWrap>序号</td>
			<td align="center" noWrap>名称</td>
			<td align="center" noWrap>描述</td>
			<td align="center" noWrap>版本</td>
			<td align="center" noWrap>功能键</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${processDefinitions}" var="a">
			<tr onmouseover="this.className='x-row-over';"
				onmouseout="this.className='x-row-out';" class="x-content">
				<td align="center" noWrap><%=sortNo++%></td>
				<td align="left" noWrap>&nbsp; <a
					href="<%=request.getContextPath()%>/mx/jbpm/monitor/processInstances?processName=${a.name}"> 
					 ${a.name}</a></td>
				<td align="left" noWrap>&nbsp;${a.description}
				</td>
				<td align="right" noWrap>&nbsp;${a.version}&nbsp;
				</td>
				<td align="center" noWrap>&nbsp;<a
					href="<%=request.getContextPath()%>/mx/jbpm/monitor/processInstances?processDefinitionId=${a.id}"><img
					src="<%=request.getContextPath()%>/images/task.gif"
					border="0" title="查看全部流程实例"> 全部</a> &nbsp;<a
					href="<%=request.getContextPath()%>/mx/jbpm/monitor/processInstances?processDefinitionId=${a.id}&actionType=running"><img
					src="<%=request.getContextPath()%>/images/lightbulb.png"
					border="0" title="查看运行中的流程实例"> 运行中</a> &nbsp;<a
					href="<%=request.getContextPath()%>/mx/jbpm/monitor/processInstances?processDefinitionId=${a.id}&actionType=finished"><img
					src="<%=request.getContextPath()%>/images/lightbulb_off.png"
					border="0" title="查看已经完成的流程实例"> 已完成</a> &nbsp;<a
					href="<%=request.getContextPath()%>/mx/jbpm/image?processDefinitionId=${a.id}"
					target="_blank"><img
					src="<%=request.getContextPath()%>/images/process.gif"
					border="0" title="查看流程图"> 流程图</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</center>


<%@ include file="/WEB-INF/views/inc/footer.jsp"%>