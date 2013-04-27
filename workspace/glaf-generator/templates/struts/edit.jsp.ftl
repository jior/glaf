<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${classDefinition.title}</title>
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/#F{theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/icon.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/apps/${modelName}.do?method=save${entityName}',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data.message != null){
						   alert(data.message);
					   } else {
						 alert('操作成功完成！');
					   }
				   }
			 });
	}

	function saveAsData(){
		document.getElementById("id").value="";
		document.getElementById("${idField.name}").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/apps/${modelName}.do?method=save${entityName}',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data.message != null){
						   alert(data.message);
					   } else {
						 alert('操作成功完成！');
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
    <div style="background:#fafafa;padding:2px;border:1px solid #ddd;font-size:12px"> 
	编辑${classDefinition.title}
	<!-- <input type="button" name="save" value=" 保存 " class="button btn btn-primary" onclick="javascript:saveData();">
	<input type="button" name="saveAs" value=" 另存 " class="button btn" onclick="javascript:saveAsData();">
	<input type="button" name="back" value=" 返回 " class="button btn" onclick="javascript:history.back();"> -->
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveData();" >保存</a> 
	<!-- 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-saveas'" onclick="javascript:saveAsData();" >另存</a> 
        -->
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-back'" onclick="javascript:history.back();">返回</a>	
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="#F{${modelName}.${idField.name}}"/>
  <input type="hidden" id="${idField.name}" name="${idField.name}" value="#F{${modelName}.${idField.name}}"/>
  <table class="easyui-form" style="width:800px;" align="center">
    <tbody>
  <#if pojo_fields?exists>
    <#list  pojo_fields as field>	
	<#if field.editable>
	<tr>
		<td width="20%" align="left">${field.title}</td>
		<td align="left">
		<#if field.type?exists && field.type== 'Date'>
			<input id="${field.name}" name="${field.name}" type="text" 
			       class="easyui-datebox"
			<#if field.nullable == false> required="true" </#if>
				  value="<fmt:formatDate value="#F{${modelName}.${field.name}}" pattern="yyyy-MM-dd"/>"/>
            <#elseif field.type?exists && field.type== 'Integer'>
			<input id="${field.name}" name="${field.name}" type="text" 
			       class="easyui-numberspinner" value="0" 
				   increment="10"  <#if field.nullable == false> required="true" </#if>
				   value="#F{${modelName}.${field.name}}"/>
			<#elseif field.type?exists && field.type== 'Long'>
			<input id="${field.name}" name="${field.name}" type="text"
			       class="easyui-numberspinner" value="0" 
				   increment="100"  <#if field.nullable == false> required="true" </#if>
				   value="#F{${modelName}.${field.name}}"/>
			<#elseif field.type?exists && field.type== 'Double'>
			<input id="${field.name}" name="${field.name}" type="text"
			       class="easyui-numberbox"  precision="2" 
			<#if field.nullable == false> required="true" </#if>
				  value="#F{${modelName}.${field.name}}"/>
			<#else>
            <input id="${field.name}" name="${field.name}" type="text" 
			       class="easyui-validatebox"  
			<#if field.nullable == false> required="true" </#if>
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