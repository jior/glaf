<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

  <script language="JavaScript">
  	function openTree(){
  	
  	}
  </script>
    <base href="<%=basePath%>"> 
    <title>��Ӧ��ѡ��</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>



<Frameset Cols="20%,80%">
<Frame name="left" SRC="<%=request.getContextPath()%>/demo/demoJsp/Tree.jsp">
<frame name="right" SRC="">
</Frameset>

<noframes>����������հ׵Ļ��棬˵������������ǲ�֧�ֿ�ܻ�����</noframes>
</html>
