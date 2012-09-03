<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.*"%>
<HTML>
<HEAD>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/pages/common/style.jsp"%>
<LINK
	href="<%=request.getContextPath()%>/workflow/styles/<c:out value="${frame_skin}"/>/main/main.css"
	type=text/css rel=stylesheet>
<script language="JavaScript">
	
</script>
</head>
<body leftmargin=0 topmargin=0 marginheight="0" marginwidth="0">
	<br>
	<br>
	<div align="center">
		<b>人员列表</b>
	</div>
	<br>
	<table align="center" class="table-border" cellspacing="1"
		cellpadding="4" width="80%" nowrap>
		<tr class="table-bar" nowrap>
			<td width="25%" height="15" align="center" class="table-title" nowrap>用户编号</td>
			<td width="25%" height="15" align="center" class="table-title" nowrap>姓名</td>
			<td width="25%" height="15" align="center" class="table-title" nowrap>电子邮件</td>
			<td width="25%" height="15" align="center" class="table-title" nowrap>手机</td>
		</tr>
		<c:forEach items="${users}" var="user">
			<tr class="beta">
				<td width="25%" height="15" align="left"><c:out
						value="${user.actorId}" /></td>
				<td width="25%" height="15" align="left"><c:out
						value="${user.name}" /></td>
				<td width="25%" height="15" align="left"><c:out
						value="${user.mail}" /></td>
				<td width="25%" height="15" align="left"><c:out
						value="${user.mobile}" /></td>
			</tr>
		</c:forEach>
	</table>

</BODY>
</HTML>