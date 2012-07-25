<%@ page contentType="text/html;charset=gbk" language="java"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
//刷新父窗口，并关闭当前页面。
  String reload = (String)request.getAttribute("reload");
  String parastr=(String)request.getAttribute("parastr");
  String msgstr=(String)request.getAttribute("msgstr");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
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
Close();
</script>
</html>