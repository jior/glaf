<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${classDefinition.title}</title>
<%@ include file="/WEB-INF/views/tm/header.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/icon.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

        function initData(){
            $('#iForm').form('load','<%=request.getContextPath()%>/rs/apps/${modelName}/view/$F{rowId}');
        }

        function saveData(){
             var params = jQuery("#iForm").formSerialize();
	          jQuery.ajax({
          	   type: "POST",
          	   url: '<%=request.getContextPath()%>/rs/apps/${modelName}/save${entityName}',
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
	<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/pages/images/window.png"
	alt="${classDefinition.title}"> &nbsp;${classDefinition.title}
	</div>
	 <form id="iForm" name="iForm" method="post">
	    <input type="hidden" id="id" name="id"/>
	    <input type="hidden" id="${modelName}Id" name="${modelName}Id"/>
	    <table class="easyui-form" style="width:700px;height:250px" align="center">
		<tbody>
<#if pojo_fields?exists>
         <#list  pojo_fields as field>	
		   <#if field.editable>
          <tr>
          	 <td>${field.title}</td>
          	 <td>
          	 <#if field.type?exists && field.type== 'Date'>
          	  <input id="${field.name}" name="${field.name}" class="easyui-datebox" type="text" 
	               value='<fmt:formatDate value="$F{${modelName}.${field.name}}" pattern="yyyy-MM-dd" />'
	                   <#if field.nullable == false> required="true" </#if>></input>
                 <#elseif field.type?exists && field.type== 'Integer'>
          	 <input id="${field.name}" name="${field.name}" class="easyui-numberspinner" 
                        value="$F{${modelName}.${field.name}}" 
	                  increment="10" style="width:120px;" <#if field.nullable == false> required="true" </#if> type="text"></input>
          	 <#elseif field.type?exists && field.type== 'Long'>
          	 <input id="${field.name}" name="${field.name}" class="easyui-numberspinner" 
                        value="$F{${modelName}.${field.name}}"
	                  increment="100" style="width:120px;" <#if field.nullable == false> required="true" </#if> type="text"></input>
          	 <#elseif field.type?exists && field.type== 'Double'>
          	 <input id="${field.name}" name="${field.name}" class="easyui-numberbox"  precision="2" 
	              value="$F{${modelName}.${field.name}}"
          	  <#if field.nullable == false> required="true" </#if> type="text"/>
          	 <#else>
                 <input id="${field.name}" name="${field.name}" class="easyui-validatebox" type="text"
	              value="$F{${modelName}.${field.name}}"
          	 <#if field.nullable == false> required="true" </#if>></input>
          	 </#if>
          	 </td>
          </tr>
	  </#if>	 
    </#list>
</#if>
	  <tr>
		<td colspan="2" align="center">
		<input type="button" name="save" value="保存" class="button" onclick="javascript:saveData();" />
		<input name="btn_back" type="button" value=" 返 回 " class="button" onclick="javascript:history.back(0);">
		</td>
	 </tr>
	</tbody>
   </table>
  </form>
  <script type="text/javascript">
         initData();
  </script>
</body>
</html>