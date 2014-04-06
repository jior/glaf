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
<title>${classDefinition.title}</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/${classDefinition.moduleName}/${modelName}/save${entityName}',
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
		document.getElementById("${idField.name}").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/${classDefinition.moduleName}/${modelName}/save${entityName}',
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
	<span class="x_content_title">编辑${classDefinition.title}</span>
	<!-- <input type="button" name="save" value=" 保存 " class="button btn btn-primary" onclick="javascript:saveData();">
	<input type="button" name="saveAs" value=" 另存 " class="button btn" onclick="javascript:saveAsData();">
	<input type="button" name="back" value=" 返回 " class="button btn" onclick="javascript:history.back();"> -->
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveData();" >保存</a> 
	<!-- 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-saveas'" onclick="javascript:saveAsData();" >另存</a> 
        -->
	<!--
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-back'" onclick="javascript:history.back();">返回</a>
	-->
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="${idField.name}" name="${idField.name}" value="#F{${modelName}.${idField.name}}"/>
  <table class="easyui-form" style="width:600px;" align="center">
    <tbody>
  <#if pojo_fields?exists>
    <#list  pojo_fields as field>	
	<#if field.editable>
	<tr>
		<td width="20%" align="left">${field.title}</td>
		<td align="left">
		<#if field.type?exists && field.type== 'Date'>
			<input id="${field.name}" name="${field.name}" type="text" 
			       class="easyui-datebox x-text"
			<#if field.nullable == false> required="true" data-options="required:true" </#if>
				  value="<fmt:formatDate value="#F{${modelName}.${field.name}}" pattern="yyyy-MM-dd"/>"/>
            <#elseif field.type?exists && field.type== 'Integer'>
			<input id="${field.name}" name="${field.name}" type="text" 
			       class="easyui-numberbox x-text" 
				   increment="10"  <#if field.nullable == false> required="true" data-options="required:true" </#if>
				   value="#F{${modelName}.${field.name}}"/>
			<#elseif field.type?exists && field.type== 'Long'>
			<input id="${field.name}" name="${field.name}" type="text"
			       class="easyui-numberbox x-text"
				   increment="100"  <#if field.nullable == false> required="true" data-options="required:true" </#if>
				   value="#F{${modelName}.${field.name}}"/>
			<#elseif field.type?exists && field.type== 'Double'>
			<input id="${field.name}" name="${field.name}" type="text"
			       class="easyui-numberbox  x-text"  precision="2" 
			<#if field.nullable == false> required="true" data-options="required:true" </#if>
				  value="#F{${modelName}.${field.name}}"/>
			<#else>
            <input id="${field.name}" name="${field.name}" type="text" 
			       class="easyui-validatebox  x-text"  
			<#if field.nullable == false> required="true" data-options="required:true" </#if>
				   value="#F{${modelName}.${field.name}}"/>
			</#if>
		</td>
	</tr>
  </#if>	 
 </#list>
</#if>
 
    </tbody>
  </table>
  </form>
</div>
</div>
</body>
</html>