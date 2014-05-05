<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
	request.setAttribute("contextPath", request.getContextPath());
	//mx/system/param/edit?serviceKey=chart&businessKey=29
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>参数设置</title>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

    var mycolumns = [[  
        {field:'name',title:'属性名',width:200,sortable:true},  
        {field:'value',title:'属性值',width:150,resizable:false}  
    ]];  


    function saveData(){
		var str = '?q=1&';
		var rows = $('#tt').propertygrid('getChanges');
		for(var i=0; i<rows.length; i++){
			var tmp = rows[i].name;
			tmp = tmp.substring(tmp.lastIndexOf("(")+1, tmp.lastIndexOf(")"));
			str += tmp + '=' + rows[i].value + '&';
		}
		//alert(str);
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/system/param/saveAll'+str,
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
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div style="background:#fafafa;padding:2px;border:1px solid #ddd;font-size:12px"> 
	<span class="x_content_title">参数设置</span>
    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" 
	   onclick="saveData();">保存</a>  
   </div> 
  </div> 
  <div data-options="region:'center',border:false">
     <form id="iForm" name="iForm">
	   <input type="hidden" id="serviceKey" name="serviceKey" value="${serviceKey}">
	   <input type="hidden" id="businessKey" name="businessKey" value="${businessKey}">
	   <table id="tt" class="easyui-propertygrid" style="width:580px;height:300px"
			data-options="url:'<%=request.getContextPath()%>/rs/system/param/json?serviceKey=${serviceKey}&businessKey=${treeModel.code}',showGroup:true,scrollbarSize:0, columns: mycolumns "></table>
	</form>
  </div>  
</div>
</body>
</html>