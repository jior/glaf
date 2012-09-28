<%@ page language="java" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>GLAF基础应用框架</title>
</head>

<frameset rows="59,*" cols="*" frameborder="no" id="fraMain" border="0" framespacing="0">
  <frame src="top.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset id="frame_mid" cols="189,10,*" rows="*"  framespacing="1" frameborder="YSE" border="1">
    <frame src="left.jsp" name="leftFrame" scrolling="YES" noresize="noresize" id="leftFrame" title="leftFrame" />
    <frame border="0" frameBorder="0" marginHeight="0" marginWidth="0" name="leftban" scrolling="no" scrolling="No" noresize="noresize" src="leftban.html" style="BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; BORDER-RIGHT: medium none; BORDER-TOP: medium none" LEFTMARGIN="0" TOPMARGIN="0" target="_self">
     <frame src="main.jsp" name="mainFrame" id="mainFrame" title="mainFrame" frameBorder="0" scrolling="auto" />
  </frameset>
</frameset>

<noframes><body>
</body>
</noframes></html>
