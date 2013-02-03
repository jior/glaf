<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<SCRIPT src="/js/main.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/close.js'></script>
<title>基础平台系统</title>
<body>
<div id="messageDiv" style="display:none">
<glaf:messages id="message" message="true"> 
   <bean:write name="message"/> 
</glaf:messages>
</div>
</body>
<script language="javascript">
//刷新当前页面，不关闭页面
var messageDiv = document.getElementById("messageDiv");
var message = messageDiv.innerText;
if (message.length > 0) {
  alert(message);
}
if (window.parent) {
  window.parent.location.reload();
}
</script>
</html>