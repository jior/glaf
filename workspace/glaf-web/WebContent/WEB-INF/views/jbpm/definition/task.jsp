<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<title>流程定义列表</title> 
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
</head>
<body style="padding-left:20px;padding-right:20px">
<%
        int sortNo = 1;
 %>
<center><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="${processDefinition.description}任务信息">&nbsp;${processDefinition.description}任务信息</div>
<br>

<table align="center" class="x-table-border table table-striped table-bordered table-condensed" cellspacing="1"
	cellpadding="4" width="90%">
	<thead>
		<tr class="x-title">
			<td align="center" noWrap>序号</td>
			<td align="center" noWrap>任务名称</td>
			<td align="center" noWrap>任务描述</td>
			<td align="center" noWrap>优先级</td>
			<td align="center" noWrap>是否发信号</td>
			<td align="center" noWrap>功能键</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${tasks}" var="a">
			<tr onmouseover="this.className='x-row-over';"
				onmouseout="this.className='x-row-out';" class="x-content">
				<td align="center" noWrap><%=sortNo++%></td>
				<td align="left" noWrap>&nbsp; ${a.name}</td>
				<td align="left" noWrap>&nbsp;${a.description}
				</td>
				<td align="center" noWrap>&nbsp;${a.priority}
				</td>
				<td align="center" noWrap>&nbsp; <c:choose>
					<c:when test="${ a.signalling }">
						<font color="#6666FF"><b>是</b></font>
					</c:when>
					<c:otherwise>
						<font color="#FFCC33"><b>否</b></font>
					</c:otherwise>
				</c:choose></td>
				<td align="center" noWrap>&nbsp;
				<c:if test="${showFormConfig eq 'true'}">
				   	&nbsp;<a
						href="<%=request.getContextPath()%>/mx/form/formAccess?processName=${processDefinition.name}&taskName=${a.name}&processDefinitionId=${processDefinition.id}&formName=${formName}&app_name=${app_name}&appId=${appId}"
						target="_blank"> <img
						src="<%=request.getContextPath()%>/images/form.png"
						border="0" title="设置任务表单属性"> 表单</a>
				</c:if></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</center>


<%@ include file="/WEB-INF/views/tm/footer.jsp"%>