<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/tm/header.jsp"%>
<%
int sortNo =1;
%>
<center><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="用户信息">&nbsp;用户信息
</div>


<br>
<table align="center" class="x-table-border" cellspacing="1"
	cellpadding="4" width="98%">
	<thead>
		<tr class="x-title">
			<td align="center" noWrap>序号</td>
			<td align="center" noWrap>用户名</td>
			<td align="center" noWrap>姓名</td>
			<td align="center" noWrap>部门</td>
			<td align="center" noWrap>手机号码</td>
			<td align="center" noWrap>办公电话</td>
			<td align="center" noWrap>电子邮件</td>
			<td align="center" noWrap>功能键</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${userProfiles}" var="a">
			<tr onmouseover="this.className='x-row-over';"
				onmouseout="this.className='x-row-out';" class="x-content">
				<td align="center" noWrap><%=sortNo++%></td>
				<td align="left" noWrap><a
					href="<%=request.getContextPath()%>/system/user/edit?actionType=view&actorId=${a.actorId}">
				${a.actorId}</a></td>
				<td align="left" noWrap>${a.name}</td>
				<td align="left" noWrap>${a.deptName}</td>
				<td align="left" noWrap>${a.mobile}</td>
				<td align="left" noWrap>${a.phoneNumber}</td>
				<td align="center" noWrap>${a.mail}</td>
				<td align="center" noWrap>&nbsp;<a
					href="<%=request.getContextPath()%>/mx/activiti/agent/agent?assignFrom=${a.actorId}"><img
					src="<%=request.getContextPath()%>/images/linkman.gif"
					border="0" title="流程代理设置">代理</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</center>
</form>

<%@ include file="/WEB-INF/views/tm/footer.jsp"%>