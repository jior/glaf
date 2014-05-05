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
<title>数据表信息</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/entity/saveFormDefinition?type=${type}&nodeId=${nodeId}',
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
				   }
			 });
	}

	function saveAsData(){
		document.getElementById("id").value="";
		document.getElementById("rowId").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/entity/saveFormDefinition?type=${type}&nodeId=${nodeId}',
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
				   }
			 });
	}

	function submitRequest(form){  
       var file = document.getElementById("file").value;
 	   if(file == ""){
		   alert('请选择表单定义文件！');
		   return ;
	   }

	    if(confirm('您准备发布新的表单版本，确认吗？')){
            document.iForm.submit();
		}

	}

</script>
</head>

<body>
<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"> 
	<span class="x_content_title">编辑数据表定义</span>
	<!-- <input type="button" name="save" value=" 保存 " class="button btn btn-primary" onclick="javascript:saveData();">
	<input type="button" name="saveAs" value=" 另存 " class="button btn" onclick="javascript:saveAsData();">
	<input type="button" name="back" value=" 返回 " class="button btn" onclick="javascript:history.back();"> -->
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" 
	   onclick="javascript:submitRequest(this.form);" >保存</a> 
	<!-- 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-saveas'" onclick="javascript:saveAsData();" >另存</a> 
        -->
	<!-- <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-back'" onclick="javascript:history.back();">返回</a>	 -->
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form action="<%=request.getContextPath()%>/mx/sys/entity/deploy?type=${type}&nodeId=${nodeId}"
	    method="post" enctype="multipart/form-data" id="iForm" name="iForm" class="x-form">
  <input type="hidden" id="nodeId" name="nodeId" value="${nodeId}" >
  <input type="hidden" id="id" name="id" value="${entityDefinition.id}"/>
  <input type="hidden" id="rowId" name="rowId" value="${entityDefinition.id}"/>
  <table class="easyui-form" style="width:600px;" align="center">
    <tbody>
       <tr>
	   <td>
	     <label for="file">&nbsp;&nbsp; 请选择实体模板</label>&nbsp;&nbsp; 
		 <input type="file" id="file" name="file" size="50" class="easyui-validatebox" data-options="required:true">
	   </td>
	   </tr>
	   <tr>
	   <td>
	      
	   </td>
	   </tr>
    </tbody>
  </table>
  </form>
</div>
</div>
</body>
</html>