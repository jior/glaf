<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.config.admin.*"%>
<%@ page import="com.glaf.setup.conf.*"%>
<%
       String contextPath = request.getContextPath();
       String appPath = com.glaf.core.context.ApplicationContext.getAppPath();
       DatabaseConfig cfg = new DatabaseConfig();
	   List<Database> rows = cfg.getDatabases(appPath);

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据源配置</title>
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/css/site.css" />

<script language="javascript">
  String.prototype.trim = function() {
     return this.replace(/(^\s*)|(\s*$)/g, "");
  }

 function submitRequest(form, actionType){
    var type = document.getElementById("type").value.trim();
	var host = document.getElementById("host").value.trim();
	var username = document.getElementById("username").value.trim();
	var databaseName = document.getElementById("databaseName").value.trim();
	var datasourceName = document.getElementById("datasourceName").value.trim();


    if(type == ""){
		alert("数据库类型不能为空！");
		document.getElementById("type").focus();
		return ;
	}

	if(datasourceName == ""){
	
		if(host == ""){
			alert("服务器地址不能为空！");
			document.getElementById("host").focus();
			return ;
		 }

		 if(databaseName == ""){
			alert("数据库名称不能为空！");
			document.getElementById("databaseName").focus();
			return ;
		 }

		if(username == ""){
			alert("用户名不能为空！");
			document.getElementById("username").focus();
			return ;
		 }
	 
	}

    if(actionType == "reconfig"){
       if(confirm("您真的要重新配置数据源，确认吗？")){
		    document.getElementById("actionType").value = "reconfig";
	        form.submit();
	   }
	} else {
		form.submit();
	}

 }

</script>
<body>
<center><br>
<br>

<form name="iForm" method="post" action="<%=request.getContextPath()%>/config/saveDataSource.jsp"
	class="x-form">
<input type="hidden" id="actionType"	name="actionType" value="test"> 
<div class="content-block"  style=" width: 625px;" >
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="数据源配置">&nbsp;数据源配置 
 </div>

<fieldset class="x-fieldset" style=" width: 95%;">
<legend>基本属性</legend>

<table border=0 cellspacing=0 cellpadding=2>
<tbody>
 <tr class="x-content-hight">
  <td style="width: 140px; padding-right: 10px">
      <span class="field-name-required">数据库类型</span>
  </td>
  <td> 
       <select id="type" name="type">
	   <option value="" selected>--------请选择数据库类型--------</option>
		<%
	     for(Database db : rows){
	    %>
		<option value="<%=db.getName()%>"><%=db.getSubject()%></option>
	   <%
	       }
	    %>
		</select>
  </td>
 </tr>
 <tr class="x-content-hight" style="display:none">
  <td style="width: 140px; padding-right: 10px">
      <span class="field-name-required">JNDI引用名称</span>
  </td>
  <td> 
  <input id="datasourceName" name="datasourceName" size="25" class="x-text" maxlength="255" value="">
  &nbsp; (例如 java:comp/env/jdbc/glafdb)
  </td>
 </tr>

 <tr class="x-content-hight">
  <td style="width: 140px; padding-right: 10px">
      <span class="field-name-required">服务器IP</span>
  </td>
  <td> 
  <input id="host" name="host" size="25" class="x-text" maxlength="255" value="127.0.0.1">
  &nbsp; (默认127.0.0.1)
  </td>
 </tr>

<tr class="x-content-hight" style="display:none">
  <td style="width: 140px; padding-right: 10px">
      <span class="field-name-required">数据库端口 </span>
  </td>
  <td> 
  <input id="port" name="port" size="25"  class="x-text" maxlength="5" value="">
  &nbsp; (使用默认端口无需填写)
  </td>
 </tr>

<tr class="x-content-hight">
  <td style="width: 140px; padding-right: 10px">
      <span class="field-name-required">数据库名称</span>
  </td>
  <td> 
  <input id="databaseName"
	name="databaseName" size="25" class="x-text" maxlength="255" value="">
  </td>
 </tr>

<tr class="x-content-hight">
  <td style="width: 140px; padding-right: 10px">
       用户名 
  </td>
  <td> 
  <input id="username"
	name="username" size="25" class="x-text" maxlength="255" value="">
  </td>
 </tr>

<tr class="x-content-hight">
  <td style="width: 140px; padding-right: 10px">
       密码 
  </td>
  <td> 
  <input id="password"
	name="password" type="password" size="26" class="x-text"
	maxlength="255" value=""> 
	</td>
 </tr>

</table>
</fieldset>

<div align="center">
<br />
<input type="button" class="button" value="重新配置"
	onclick="javascript:submitRequest(this.form, 'reconfig');" />
<!-- <input type="button" class="button" value="测试连接"
	onclick="javascript:submitRequest(this.form, 'test');" /> --> 
<br />
<br />
</div>

</div>
</form>
</center>