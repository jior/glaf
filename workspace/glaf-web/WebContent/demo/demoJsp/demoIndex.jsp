<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>

<html>
  <head>
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/frameWorkUtility.js"></script>
  </head>
  
  <body>
    <a href="<%=request.getContextPath()%>/demo/demoJsp/Demo01.jsp">日历控件 锁定画面 上传下载</a> <br>
    <a href="<%=request.getContextPath()%>/demo/demoJsp/Demo02.jsp">树形结构</a> <br>
    <a href="<%=request.getContextPath()%>/demo/demoJsp/popTree.jsp">树形结构(新)</a> <br>
    <a href="startFlow.do">新启流程</a> <br>
    <a href="testPage.do">推动流程</a>

  </body>

<jsp:include page="/sys/sysJsp/common/showAllTypeMessage.jsp" flush="true"/>  
</html>
