<%@ page language="java" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>广汽三菱  GMMC订单指示系统</title>
</head>

<frameset rows="59,*" cols="*" frameborder="no" border="0" framespacing="0">
<frame src="<%=request.getContextPath()%>/sys/sysJsp/top.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset rows="*" cols="189,*" framespacing="0" frameborder="no" border="0">
    <frame src="<%=request.getContextPath()%>/sys/sysJsp/left.jsp" name="leftFrame" scrolling="YES" noresize="noresize" id="leftFrame" title="leftFrame" />
	<frame src="<%=request.getContextPath()%>/sys/sysJsp/main.jsp" name="mainFrame" id="mainFrame" title="mainFrame" />
  </frameset>
</frameset>
<noframes><body>
</body>
</noframes></html>
