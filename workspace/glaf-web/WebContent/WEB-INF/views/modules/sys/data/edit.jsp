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
<title>修改记录</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/data/service/saveSysData',
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
					   
					   if (window.opener) {
						  window.opener.location.reload();
					   } else if (window.parent) {
						  window.parent.location.reload();
					   }
					   location.href='<%=com.glaf.core.util.RequestUtils.decodeURL(request.getParameter("fromUrl"))%>';
				   }
			 });
	}

	function saveAsData(){
		document.getElementById("id").value="";
		document.getElementById("id").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/data/service/saveSysData',
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

</script>
</head>

<body>
<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"> 
	<span class="x_content_title">编辑</span>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveData();" >保存</a> 
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="${sysData.id}"/>
  <table class="easyui-form" style="width:640px;" align="center">
    <tbody>
	<tr>
		<td width="15%" align="left">标题</td>
		<td align="left">
            <input id="title" name="title" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${sysData.title}" size="50"/>
		</td>
	</tr>
	<tr>
		<td width="15%" align="left">描述</td>
		<td align="left">
			<textarea id="description" name="description" rows="6" cols="36" class="x-textarea"  >${sysData.description}</textarea>
		</td>
	</tr>
	<tr>
		<td width="15%" align="left">数据模板</td>
		<td align="left">
            <input id="path" name="path" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${sysData.path}" readonly="true" size="50"/>
		</td>
	</tr>
	<tr>
		<td width="15%" align="left">访问权限</td>
		<td align="left">
			<input type="hidden" id="perms" name="perms" value="${sysData.perms}">
            <textarea  id="x_roles_name" name="x_roles_name" rows="6" cols="36" class="x-textarea" readonly  
		    >${x_role_names}</textarea>
			<input type="button" name="button" value="添加" class="button" 
			       onclick="javascript:selectRole('iForm', 'perms','x_roles_name');"> 
			&nbsp;
			<input type="button" name="button" value="清空" class="button" 
			       onclick="javascript:clearSelected('perms','x_roles_name');">
		</td>
	</tr>
	<tr>
		<td width="15%" align="left">允许访问IP地址</td>
		<td align="left">
		    <textarea id="addressPerms" name="addressPerms" rows="6" cols="36" class="x-textarea"  >${sysData.addressPerms}</textarea>
			<br>允许使用*为通配符，多个地址之间用半角的逗号“,”隔开。
			<br>例如：192.168.*.*，那么192.168.1.100及192.168.142.100都可访问该服务。
            <br>192.168.142.*，那么192.168.1.100不能访问但192.168.142.100可访问该服务。
			<br>如果配置成192.168.1.*,192.168.142.*，那么192.168.1.100及192.168.142.100均可访问该服务。
		</td>
	</tr>
	<tr>
		<td width="15%" align="left">类型</td>
		<td align="left">
		    <select id="type" name="type">
				<option value="xml">XML</option>
				<option value="json">JSON</option>
				<option value="jsonArray">JSON数组</option>
		    </select>
			<script type="text/javascript">
			     document.getElementById("type").value="${sysData.type}";
			</script>
		</td>
	</tr>
	<tr>
		<td width="15%" align="left">是否有效</td>
        <td>
		  <input type="radio" name="locked" value="0" <c:if test="${sysData.locked == 0}">checked</c:if>/>是
          <input type="radio" name="locked" value="1" <c:if test="${sysData.locked == 1}">checked</c:if>/>否
        </td>
	</tr>
 
    </tbody>
  </table>
  </form>
  <br/>
  <br/>
</div>
</div>

</body>
</html>