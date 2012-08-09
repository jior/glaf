<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
  String msg = (String) request.getAttribute("msg");
  String url = (String) request.getAttribute("url");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基础平台系统</title>
<body>
<div id="messageDiv" style="display:none">
<%= msg == null ? "" : msg %>
</div>
<div id="urlBox"><%= url == null ? "" : url %></div>
</body>
<script language="javascript">
var messageDiv = document.getElementById("messageDiv");
var message = messageDiv.innerText;
if (message.length > 0) {
  alert(message);
}
var url = document.getElementById("urlBox").innerText;
if (url.length > 0) {
  window.location.href = url;
} else {
  history.back();
}
</script>
</html>