<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://github.com/jior/glaf/tags" prefix="glaf"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
//刷新父窗口，并关闭当前页面。
  String reload = (String)request.getAttribute("reload");
  String parastr=(String)request.getAttribute("parastr");
  String msgstr=(String)request.getAttribute("msgstr");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<SCRIPT src="/scripts/main.js"></SCRIPT>
<script type='text/javascript' src='<%= request.getContextPath() %>/scripts/close.js'></script>
<script language="javascript">
window.moveTo(10000, 10000);
window.resizeTo(0, 0);
</script>
<title>基础平台系统</title>
<body>
<div id="messageDiv" style="display:none">
<glaf:messages id="message" message="true"> 
   <glaf:write name="message"/> 
</glaf:messages>
</div>
</body>
<script language="javascript">
var messageDiv = document.getElementById("messageDiv");
var message = messageDiv.innerText;
var reloadStr = '<%= reload %>';
var parastr = '<%= parastr==null?"":parastr %>';
var msgstr = '<%= msgstr==null?"":msgstr %>';
if (message.length > 0) {
  alert(message);
}
if(reloadStr == 'true'){
  if (window.opener) {
	  window.opener.webreload(parastr,msgstr);
  }
}else if(reloadStr == 'false'){
	window.opener.showmessage(msgstr);
}
//Close();
window.close(); 
</script>
</html>