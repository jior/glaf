<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邮件帐户</title>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/mxcore.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

          <c:if test="${not empty accountId}">
			function initData(){
				var lk='<%=request.getContextPath()%>/rs/mail/mailAccount/view/${accountId}';
			    $('#iForm').form('load',lk);
			}
           </c:if>

			function saveData(){
			       var params = jQuery("#iForm").formSerialize();
				    jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/mail/mailAccount/save',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   alert('操作成功！');				 
					   location.href="<%=request.getContextPath()%>/mx/mail/mailAccount";
				   }
			 });
			}

	 </script>
</head>
<body>
<div class="content-block" style="width: 845px;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="邮件帐户">&nbsp;邮件帐户</div>
<fieldset class="x-fieldset" style="width: 96%;">
	 <form id="iForm" name="iForm" method="post">
	     <input type="hidden" id="id" name="id"/>
	     <input type="hidden" id="accountId" name="accountId"/>
	     <table class="easyui-form" style="width:700px;height:250px">
		<tbody>
			<tr>
				 <td>邮件地址</td>
				 <td>
                 <input id="mailAddress" name="mailAddress" size="50" class="x-text easyui-validatebox" type="text" required="true"
				 ></input>
				 </td>
			</tr>
			<tr>
				 <td>显示姓名</td>
				 <td>
                 <input id="showName" name="showName" size="50" class="x-text easyui-validatebox" type="text" required="true"
				 ></input>
				 </td>
			</tr>
			<tr>
				 <td>用户名</td>
				 <td>
                 <input id="username" name="username" size="50" class="x-text easyui-validatebox" type="text" required="true"
				 ></input>
				 </td>
			</tr>
			<tr>
				 <td>密码</td>
				 <td>
                 <input id="password" name="password" size="51" class="x-text easyui-validatebox" type="password"
				 ></input>
				 </td>
			</tr>
			<tr>
				 <td>POP3服务器</td>
				 <td>
                 <input id="pop3Server" name="pop3Server" size="50" class="x-text easyui-validatebox" type="text" required="true"
				 ></input>
				 </td>
			</tr>
			<tr>
				 <td>POP3端口</td>
				 <td>
				 <input type="text" id="receivePort" name="receivePort"  class="x-text easyui-numberspinner"
				     value="110" max="65535" increment="1" style="width:60px;" maxLength="5" ></input>
				 </td>
			</tr>
			<tr>
				 <td>SMTP服务器</td>
				 <td>
                 <input id="smtpServer" name="smtpServer" size="50" class="x-text easyui-validatebox" type="text" required="true"
				 ></input>
				 </td>
			</tr>
			<tr>
				 <td>SMTP端口</td>
				 <td>
				 <input type="text" id="sendPort" name="sendPort" class="x-text  easyui-numberspinner" value="25" 
				            increment="1" style="width:60px;" max="65535" maxLength="5"></input>
				 </td>
			</tr>
			<tr>
				 <td>自动接收</td>
				 <td>
				  <select id="autoReceive" name="autoReceive">
					<option value="true" >是</option>
					<option value="false">否</option>
				  </select>
				 </td>
			</tr>
			<tr>
				 <td>记住密码</td>
				 <td>
				 <select id="rememberPassword" name="rememberPassword">
					<option value="true" >是</option>
					<option value="false">否</option>
				  </select>
				 </td>
			</tr>

			<tr>
				 <td>需要认证</td>
				 <td>
				 <select id="authFlag" name="authFlag">
					<option value="true" >需要</option>
					<option value="false">需要</option>
				  </select>
				 </td>
			</tr>
			<tr>
				 <td>是否启用</td>
				 <td>
				  <select id="locked" name="locked">
					<option value="0" >启用</option>
					<option value="1">锁定</option>
				  </select>
				 </td>
			</tr>
			<tr>
				 <td colspan="4" align="center">
				     <br />
				     <input type="button" name="save" value="保存" class="button btn" onclick="javascript:saveData();" />
					 <input type="button" name="back" value="返回" class="button btn" onclick="javascript:history.back();" />
				 </td>
			</tr>
		</tbody>
	</table>
  </form>
  </fieldset>
  </div>
  <c:if test="${not empty accountId}">
  <script type="text/javascript">
         initData();
  </script>
  </c:if>
</body>
</html>