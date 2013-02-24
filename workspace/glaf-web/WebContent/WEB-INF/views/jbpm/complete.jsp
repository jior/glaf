<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>处理信息</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/styles/styles.css" />
</head>
<body>
<br>
<br>
<center>
<div class="content-block" style="width: 60%; height: 100px;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="提示信息">&nbsp;提示信息
</div>
<div align="left" style="padding-left: 60px;"><br>
状态：&nbsp;${statusCode} <br>
信息：&nbsp; 
<c:choose>
	<c:when test="${ statusCode == 200 }">
		<span style='color: green; font-weight: bold;'> ${message}</span>
	</c:when>
	<c:when test="${ statusCode == 500 }">
		<span style='color: red; font-weight: bold;'> ${message}</span>
	</c:when>
	<c:otherwise>
		${message}
	</c:otherwise>
</c:choose>
</div>
</div>
</center>
</body>
</html>