<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="com.glaf.base.modules.sys.model.*" %>
<%@ page import="com.glaf.base.utils.*" %>
<%
 
String userName = RequestUtil.getActorId(request);
SysUser user = RequestUtil.getLoginUser(request);
if(null==userName){
	userName = "";
}
String contextPath = request.getContextPath();
 %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>GLAF基础应用框架</title>
<LINK rel=stylesheet type=text/css href="<%=request.getContextPath()%>/css/css.css">
<LINK rel=stylesheet type=text/css href="<%=request.getContextPath()%>/css/style-custom.css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<script language="javascript" type="text/javascript">
function changeLanguage(){
	objFrm = document.forms[0];
	
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	//提交页面
	xmlhttp.open("POST",
			objFrm.action + "?actionMethodId=runChangeLanguage&languageName=" + objFrm.languages.value, true);
	xmlhttp.send();

	//回调函数中刷新main画面
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			window.parent.document.frames('leftFrame').window.location.reload();
			var h = window.parent.document.frames('mainFrame').window.location.href;
			window.parent.document.frames('mainFrame').window.location.href = h;
		}
	}
}

function logOut(){
	objFrm = document.forms[0];
	var url="<%=contextPath%>/sys/authorize.do?method=logout";
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	//提交页面
	xmlhttp.open("POST",url, true);
	xmlhttp.send();

	//回调函数中刷新main画面
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var h = window.parent.location.href;
			window.parent.location.href = h;
		}
	}
}
function exit(){
	  var url="<%=contextPath%>/sys/authorize.do?method=logout";
	  if(confirm("真的要退出系统吗？")){
	    parent.window.location=url;
	  }
	}
</script>
</head>

<body>
 
<input type="hidden" value="runChangeLanguage" name="actionMethodId">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="65" background="<%=request.getContextPath()%>/images/topback.gif"><table width="100%" height="60" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="20" colspan="5">
        	<div align="left">
        		<img src="<%=request.getContextPath()%>/images/logo.png" width="45" height="45"/>&nbsp;
				<span style="font-size:34px;">GLAF基础应用框架</span>
				<span style="font-size:18px;">&nbsp;Global Application Framework</span>
        	</div>
        </td>
        <td width="48%" height="20">
        <div align="right">
        
        <span class="T1">
         <%=user.getName()%>&nbsp;&nbsp;<%=userName %>
        </span> 您好！欢迎登录&nbsp;&nbsp;<a a href="javascript:exit();">注销</a>&nbsp;&nbsp;</div></td>
      </tr>
    </table></td>
  </tr>
</table>
 
</body>
 
</html>
