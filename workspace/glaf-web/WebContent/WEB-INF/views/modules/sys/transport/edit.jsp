<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>传输设置</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";

	function saveData(){
		if(jQuery("#port").val()*1 < 1 || jQuery("#port").val()*1 >65536){
             alert('端口不合法，必须大约1并且小于65536！');
			 document.getElementById("port").focus();
			 return;
		}
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/transport/saveTransport',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   /**
					   if (window.opener) {
						window.opener.location.reload();
					   } else if (window.parent) {
						window.parent.location.reload();
					   }**/
					   location.href='<%=com.glaf.core.util.RequestUtils.decodeURL(request.getParameter("fromUrl"))%>';
				   }
			 });
	}

	function saveAsData(){
		document.getElementById("id").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/transport/saveTransport',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   location.href='<%=com.glaf.core.util.RequestUtils.decodeURL(request.getParameter("fromUrl"))%>';
				   }
			 });
	}

	function changePort(){
        <c:if test="${empty transport}">
        var type = document.getElementById("type").value;
		//alert(type);
		if(type=="http"){
			document.getElementById("portLabel").innerHTML="80";
		}
		if(type=="https"){
			document.getElementById("portLabel").innerHTML="443";
		}
		if(type=="ftp"){
			document.getElementById("portLabel").innerHTML="21";
		}
		if(type=="ftps"){
			document.getElementById("portLabel").innerHTML="21";
		}
		if(type=="sftp"){
			document.getElementById("portLabel").innerHTML="22";
		}
		if(type=="smb"){
			document.getElementById("portLabel").innerHTML="445";
		}
		</c:if>
	}

	function verify(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/transport/verify',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert(data.message);
					   } 
				   }
			 });  
	}

</script>
</head>

<body>
<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"> 
	<span class="x_content_title">编辑记录</span>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" 
	   onclick="javascript:saveData();" >保存</a>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-ok'" 
	   onclick="javascript:verify();" >验证</a>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-back'" 
	   onclick="javascript:location.href='<%=com.glaf.core.util.RequestUtils.decodeURL(request.getParameter("fromUrl"))%>';" >返回</a>
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="${transport.id}"/>
  <table class="easyui-form" style="width:600px;" align="left">
    <tbody>
	<tr>
		<td width="20%" align="left">主题</td>
		<td align="left">
            <input id="title" name="title" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${transport.title}" size="50"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">编码</td>
		<td align="left">
            <input id="code" name="code" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${transport.code}" size="50"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">传输类型</td>
		<td align="left">
             <select id="type" name="type" onchange="javascript:changePort();">
			    <option value="http">HTTP</option>
				<option value="https">HTTPS</option>
				<option value="ftp">FTP</option>
				<option value="ftps">FTPS</option>
				<option value="sftp">SFTP</option>
				<option value="smb">SMB</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("type").value="${transport.type}";
             </script>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">主机</td>
		<td align="left">
            <input id="host" name="host" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${transport.host}"  size="50"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">端口</td>
		<td align="left">
			<input id="port" name="port" type="text" 
			       class="easyui-validatebox easyui-numberbox x-text" 
				   increment="1"  
				   value="${transport.port}" size="5" maxlength="5"/>
		    <label id="portLabel"></label>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">用户名</td>
		<td align="left">
            <input id="user" name="user" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${transport.user}" size="50"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">密码</td>
		<td align="left">
            <input id="password" name="password" type="password" 
			       class="easyui-validatebox  x-text"  
				   <c:if test="${!empty transport.password}"> value="88888888" </c:if>  size="50"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">根目录</td>
		<td align="left">
            <input id="path" name="path" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${transport.path}"  size="50"/>
		</td>
	</tr>
	
	<tr>
		<td width="20%" align="left">是否启用</td>
		<td align="left">
		<input type="radio" name="active" value="1" <c:if test="${transport.active == '1'}">checked</c:if>>启用&nbsp;&nbsp;
	    <input type="radio" name="active" value="0" <c:if test="${transport.active == '0'}">checked</c:if>>禁用
		</td>
	</tr>
 
    </tbody>
  </table>
  </form>
</div>
</div>
</body>
</html>