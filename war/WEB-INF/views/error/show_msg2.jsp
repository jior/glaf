<%@ page contentType="text/html;charset=gbk" language="java"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<SCRIPT src="/js/main.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/close.js'></script>
<title>����ƽ̨ϵͳ</title>
<body>
<div id="messageDiv" style="display:none">
<html:messages id="message" message="true"> 
   <bean:write name="message"/> 
</html:messages>
</div>
</body>
<script language="javascript">
//ˢ�µ�ǰҳ�棬���ر�ҳ��
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