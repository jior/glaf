<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/jpage.tld" prefix="jpage"%>
<%@ page import="java.util.*"%>
<%@ page import="org.jpage.jbpm.util.*"%>
<%@ page import="org.jpage.jbpm.model.*"%>
<%
	Map userMap = (Map) request.getAttribute("userMap");
%>
<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<%@ include file="/WEB-INF/pages/common/style.jsp"%>
		<LINK
			href="<%=request.getContextPath()%>/workflow/styles/<c:out value="${frame_skin}"/>/main/main.css"
			type=text/css rel=stylesheet>
	</HEAD>
	<body>
		<div align="center">
			<b>流程实例信息</b>
		</div>

		<table align="center" class="table-border" cellspacing="1"
			cellpadding="4" width="90%" nowrap>
			<tr class="table-bar">
				<td width="25%" height="12" align="center" class="table-title">
					任务名称
				</td>
				<td width="15%" height="12" align="center" class="table-title">
					处理者
				</td>
				<td width="15%" height="12" align="center" class="table-title">
					处理时间
				</td>
				<td width="10%" height="12" align="center" class="table-title">
					是否通过
				</td>
				<td height="12" align="center" class="table-title">
					处理意见
				</td>
			</tr>
			<%
				String taskInstanceId = null;
				String path = request.getContextPath() + "/workflow";
				pageContext.setAttribute("contextPath", request.getContextPath());
				List stateInstances = (List) request.getAttribute("rows");
				StringBuffer attachBuffer = new StringBuffer();

				if (stateInstances != null && stateInstances.size() > 0) {
					Iterator iterator = stateInstances.iterator();
					while (iterator.hasNext()) {
						StateInstance stateInstance = (StateInstance) iterator
								.next();
						org.jpage.actor.User user = (org.jpage.actor.User) userMap
								.get(stateInstance.getActorId());
						pageContext.setAttribute("stateInstance", stateInstance);
						taskInstanceId = stateInstance.getTaskInstanceId();
						attachBuffer.delete(0, attachBuffer.length());
			%>
			<tr class="beta">
				<td class="table-content" width="25%" align="left">
					<c:out value="${stateInstance.taskName}" />
					&nbsp;&nbsp;
					<c:out value="${stateInstance.taskDescription}" />
				</td>
				<td class="table-content" width="15%" align="left"><%=user != null ? user.getName() : ""%>[
					<c:out value="${stateInstance.actorId}" />
					]
				</td>
				<td class="table-content" width="15%" align="center">
					<fmt:formatDate value="${stateInstance.startDate}"
						pattern="yyyy-MM-dd HH:mm" />
				</td>
				<td class="table-content" width="10%" align="center">
					<c:if test="${stateInstance.opinion == 1}">
	   是


    </c:if>
				<td class="table-content" align="left">
					<c:if test="${not empty stateInstance.content}">
						<c:out value="${stateInstance.content}" />
					</c:if>
				</td>
			</tr>
			<%
				}
			%>
			<%
				}
			%>
		</table>

		<br>

		<%
			Collection taskItems = (Collection) request
					.getAttribute("taskItems");

			if (taskItems != null && taskItems.size() > 0) {
		%>
		<table align="center" class="table-border" cellspacing="1"
			cellpadding="4" width="90%" nowrap>
			<%
				Iterator iterator24 = taskItems.iterator();
					while (iterator24.hasNext()) {
						TaskItem item = (TaskItem) iterator24.next();
						if (item.getActorId() != null) {
							org.jpage.actor.User user = (org.jpage.actor.User) userMap
									.get(item.getActorId());
							if (user == null) {
								user = new org.jpage.actor.User();
								user.setActorId(item.getActorId());
								user.setName("");
							}
			%>
			<tr class="beta">
				<td class="table-bar" width="12%" height="16" align="center">
					<b>待办任务</b>
				</td>
				<td class="table-content" width="68%">
					<%
						if (item.getTaskCreateDate() != null) {
										out.println(org.jpage.util.DateTools
												.getDateTime(item.getTaskCreateDate()));
									}
									if (item.getTaskDescription() != null) {
										out.println("【" + item.getTaskName() + "&nbsp;"
												+ item.getTaskDescription() + "】");
									} else {
										out.println("【" + item.getTaskName() + "&nbsp;");
									}
									out.println("<font color=\"#FF6600\"><b>"
											+ user.getName() + " [" + user.getActorId()
											+ "]</b></font>");
					%>
				</td>
				<td class="table-content" width="20%">
					<%=com.gzgi.utils.ImsUtil.getDeptName(user
										.getActorId())%>&nbsp;
				</td>
			</tr>
			<%
				}
					}
				}
			%>
		</table>
		<br>
		<br>
