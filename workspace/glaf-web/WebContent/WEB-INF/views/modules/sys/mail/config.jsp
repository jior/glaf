<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%
       String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邮件服务配置</title>
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>


<script language="javascript">
  String.prototype.trim = function() {
     return this.replace(/(^\s*)|(\s*$)/g, "");
  }

 function submitRequest(form, actionType){
    var host = document.getElementById("host").value.trim();
	var mailFrom = document.getElementById("mailFrom").value.trim();
	var text = document.getElementById("text").value.trim();
	document.getElementById("actionType").value = actionType;

    if(host == ""){
		alert("邮件服务器地址不能为空！");
		document.getElementById("host").focus();
		return ;
	 }
	if(mailFrom == ""){
		alert("邮件发送者不能为空！");
		document.getElementById("mailFrom").focus();
		return ;
	 }
	 if(text == ""){
		alert("邮件内容不能为空！");
		document.getElementById("text").focus();
		return ;
	 }

    if(actionType == "reconfig"){
       if(confirm("您真的要重新配置邮件服务，确认吗？")){
	        form.submit();
	   }
	}else{
		form.submit();
	}

 }

</script>
</head>
<body>
<center> 

<form name="dataItemForm" method="post"
	action="<%=request.getContextPath()%>/mx/sys/mailConfig/save"
	class="x-form"><input type="hidden" id="actionType"
	name="actionType" value="test"> <input type="hidden" id="auth"
	name="auth" value="false">
<div class="content-block" style="width: 585px;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="邮件服务配置">&nbsp;邮件服务配置</div>
<br>
 
<table border=0 cellspacing=0 cellpadding=2 >
	<tbody>
		<tr class="x-content-hight" >
			<td style="width: 140px; padding-right: 10px" align="left"><span
				class="field-name-required">邮件服务器地址 *</span></td>
			<td align="left"><input id="host" name="host" type="text" size="40" class="input-xlarge span3"
				maxlength="255" value=""></td>
		</tr>

		<tr class="x-content-hight">
			<td style="width: 140px; padding-right: 10px" align="left"><span
				class="field-name-required">发送端口 *</span></td>
			<td align="left"><input id="port" name="port" type="text" size="40" class="input-xlarge span3"
				maxlength="5" value="25"></td>
		</tr>

		<tr class="x-content-hight">
			<td style="width: 140px; padding-right: 10px" align="left"><span
				class="field-name-required">发送者名称 *</span></td>
			<td align="left"><input id="mailFrom" name="mailFrom" type="text" size="40"
				class="input-xlarge span3" maxlength="255" value=""></td>
		</tr>

		<tr class="x-content-hight">
			<td style="width: 140px; padding-right: 10px" align="left">用户名</td>
			<td align="left"><input id="username" name="username" type="text" size="40"
				class="input-xlarge span3" maxlength="255" value=""></td>
		</tr>

		<tr class="x-content-hight">
			<td style="width: 140px; padding-right: 10px" align="left">密码</td>
			<td align="left"><input id="password" name="password" type="password"
				size="41" class="input-xlarge span3" maxlength="255" value=""></td>
		</tr>

		<tr class="x-content-hight">
			<td style="width: 140px; padding-right: 10px" align="left">是否需要认证</td>
			<td align="left"><input type="radio" name="x_auth"
				onclick="javascript:document.getElementById('auth').value=true;">
			需要 <input type="radio" name="x_auth"
				onclick="javascript:document.getElementById('auth').value=false;">
			不需要</td>
		</tr>

		<tr class="x-content-hight">
			<td style="width: 140px; padding-right: 10px" align="left">邮件编码</td>
			<td align="left"><select name="encoding" class="span2">
				<option value="GBK">GBK</option>
				<option value="GB2312">GB2312</option>
				<option value="GB18030">GB18030</option>
				<option value="BIG5">BIG5</option>
				<option value="UTF-8">UTF-8</option>
				<option value="UTF-16">UTF-16</option>
				<option value="ISO-8859-1">ISO-8859-1</option>
			</select></td>
		</tr>

		<tr class="x-content-hight">
			<td style="width: 140px; padding-right: 10px" align="left">请填写测试文本 *</td>
			<td align="left"><textarea id="text" name="text" rows="8" cols="32"
				class="input-xlarge span4"></textarea></td>
		</tr>
</table>
 

<div align="center"><br />

<input type="button" class="btn btn-primary" value="重新配置"
	onclick="javascript:submitRequest(this.form, 'reconfig');" /> <input
	type="button" class="btn" value="测试连接"
	onclick="javascript:submitRequest(this.form, 'test');" /> <br />
<br />
</div>


</div>
</form>
</center>
<br />
<br />