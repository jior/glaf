<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/tm/header.jsp"%>
</head>
<body >
<br><br>
<center>
<div class="content-block"  style=" width: 60%; height:150px;" >
<br>
<div class="x_content_title">
<img
	src="<%=request.getContextPath()%>/images/window.png" alt="流程定义信息">
流程定义信息
</div>
<div align="left" style="padding-left: 60px;">
    <br>
    <br>  部署编号： ${processDefinition.deploymentId}
	<br>  定义编号： ${processDefinition.id}
	<br>  流程名称： ${processDefinition.name}
	<br>  流程Key：  ${processDefinition.key}
	<br>  流程版本： ${processDefinition.version}
</div>
</div>
</center>
</body>
</html>