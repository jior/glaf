<%@ page contentType="text/html;charset=gbk" language="java"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
//ˢ�¸����ڣ����رյ�ǰҳ�档
  String refresh = ParamUtil.getParameter(request,"refresh");
  if(refresh == null || refresh.equals("")){
  	refresh = request.getAttribute("refresh")!=null?request.getAttribute("refresh").toString():"";
  }
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
<title>����ƽ̨ϵͳ</title>
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
  //������·�����Ϣˢ��ҳ�������
	  window.opener.location.href=window.opener.location.href;
	}
}
Close();
</script>
</html>