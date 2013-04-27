<%@ page language="java" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>GLAF基础应用框架</title>
 <script type="text/javascript">
  function setIframeHeigh(){
	var frameId = document.getElementById("mainFrame");
	frameId.style.pixelHeight = frameId.Document.body.scrollHeight+20;
	//frameId.style.pixelWidth = frameId.Document.body.scrollWidth+20;
 }
 </script>
</head>

<frameset rows="59,*" cols="*" frameborder="no" id="fraMain" border="0" framespacing="0">
  <frame src="<%=request.getContextPath()%>/main.do?method=top" name="topFrame" scrolling="no" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset id="frame_mid" cols="189,10,*" rows="*"  framespacing="1" frameborder="yes" border="1">
    <frame src="<%=request.getContextPath()%>/main.do?method=left" name="leftFrame" frameBorder="0" scrolling="yes" noresize="noresize" id="leftFrame" />
    <frame border="0" frameBorder="0" marginHeight="0" marginWidth="0" name="leftban" scrolling="no" scrolling="no" noresize="noresize" src="<%=request.getContextPath()%>/main.do?method=leftbar" style="BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; BORDER-RIGHT: medium none; BORDER-TOP: medium none" leftmargin="0" topmargin="0" target="_self">
     <frame src="<%=request.getContextPath()%>/main.do?method=index" name="mainFrame" id="mainFrame" title="mainFrame" frameBorder="0" scrolling="auto" />
  </frameset>
</frameset>

<noframes>
<body>
</body>
</noframes>
</html>
<script type="text/javascript">
 setIframeHeigh();
</script>