<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="baseSrc.framework.*" %>
<%@ page import="baseSrc.common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ taglib prefix="html" uri="/sys/sysTld/struts-html.tld"%>
<%@ taglib prefix="bean" uri="/sys/sysTld/struts-bean.tld"%>
<%@ taglib uri="/sys/sysTld/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/sys/sysTld/struts-logic.tld" prefix="logic"%>
<%
BaseCom baseCom = (BaseCom)request.getSession().getAttribute(BaseConstants.ISC_BASE_BEANCOM_ID);
String userName = baseCom.getUserName();
if(null==userName){
	userName = "";
}
 %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<LINK rel=stylesheet type=text/css href="<%=request.getContextPath()%>/sys/sysCss/menu/css.css">
<LINK rel=stylesheet type=text/css href="<%=request.getContextPath()%>/sys/sysCss/menu/style-sys.css">
<LINK rel=stylesheet type=text/css href="<%=request.getContextPath()%>/sys/sysCss/menu/style-custom.css">
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
	
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	//提交页面
	xmlhttp.open("POST",
			objFrm.action + "?actionMethodId=runLogOut", true);
	xmlhttp.send();

	//回调函数中刷新main画面
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var h = window.parent.location.href;
			window.parent.location.href = h;
		}
	}
}
</script>
</head>

<body>
<html:form method="POST" action="funTop.do">
<html:hidden property="languageName"/>
<input type="hidden" value="runChangeLanguage" name="actionMethodId">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="59" background="<%=request.getContextPath()%>/sys/sysImages/TOPBackG.gif"><table width="100%" height="56" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="20" colspan="5">
        	<div align="left">
        		<img src="<%=request.getContextPath()%>/sys/sysImages/TOPlogo.png" width="460" height="50" />
        	</div>
        </td>
        <td width="48%" height="20">
        <div align="right">
       Language：<select style="width:80px " name="languages" id="languages" onchange="changeLanguage();">
        		<option value="zh">中文</option>
        		<option value="ja">にほんご</option>
        	  </select>
        	  &nbsp;&nbsp;&nbsp;&nbsp;
        <span class="T1">
<%=baseCom.getUserId()%>&nbsp;&nbsp;<%=userName %>
</span> 您好！欢迎登录&nbsp;&nbsp;<a href="#" onclick="logOut();">注销</a>&nbsp;&nbsp;</div></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
<script language="javascript" type="text/javascript">
<%
Locale locLocale = (Locale)request.getSession().getAttribute(Globals.LOCALE_KEY);
%>
document.getElementById("languages").value = '<%=locLocale.getLanguage()%>';
</script>
</html>
