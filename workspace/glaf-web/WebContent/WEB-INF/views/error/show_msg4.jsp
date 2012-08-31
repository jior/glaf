<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
//刷新父窗口，并关闭当前页面。
  String refresh = ParamUtil.getParameter(request,"refresh");
  if(refresh == null || refresh.equals("")){
  	refresh = request.getAttribute("refresh")!=null?request.getAttribute("refresh").toString():"";
  }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<SCRIPT src="/js/main.js"></SCRIPT>
<script type='text/javascript' src='<%= request.getContextPath() %>/js/close.js'></script>
<script language="javascript">
window.moveTo(10000, 10000);
window.resizeTo(0, 0);
</script>
<title>基础平台系统</title>
<body>
<div id="messageDiv" style="display:none">
<html:messages id="message" message="true"> 
   <bean:write name="message"/> 
</html:messages>
</div>
</body>
<script language="javascript">
var messageDiv = document.getElementById("messageDiv");
var message = messageDiv.innerText;
var refreshStr = '<%= refresh %>';
if (message.length > 0) {
  alert(message);
}
if(refreshStr != 'false'){
  if (window.opener) {
  //解决重新发送信息刷新页面的问题
	  window.opener.location.href=window.opener.location.href;
	}
}
Close();
</script>
</html>