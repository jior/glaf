<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>配置完成</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/styles/styles.css" />
<style type="text/css">
body {
	margin: 0;
	padding: 0;
}

#expandcontractdiv {
	border: 1px dotted #dedede;
	margin: 0 0 5px 0;
	padding: 4px;
}

#tree {
	background: #FFF;
	padding: 1px;
	margin-top: 1px;
}
</style>

</head>
<body>
<br>
<br>
<center>
<div class="content-block" style="width: 60%; height: 100px;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="配置完成">
配置完成</div>
<div align="center"><br>
<br>
状态：${message}</div>
</div>
</center>
</body>
</html>