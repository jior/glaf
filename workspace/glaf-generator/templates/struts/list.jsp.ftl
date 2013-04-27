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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/#F{theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/icon.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

   jQuery(function(){
		jQuery('#mydatagrid').datagrid({
				width:1000,
				height:480,
				fit:true,
				fitColumns:true,
				nowrap: false,
				striped: true,
				collapsible:true,
				url:'<%=request.getContextPath()%>/apps/${modelName}.do?method=json',
				sortName: 'id',
				sortOrder: 'desc',
				remoteSort: false,
				singleSelect:true,
				idField:'${idField.name}',
				columns:[[
	                {title:'编号',field:'${idField.name}',width:80,sortable:true},
					<#if pojo_fields?exists>
					<#list  pojo_fields as field>
					 <#if field.displayType == 4>
					{title:'${field.title?if_exists}',field:'${field.name}', width:120},
					 </#if>
					</#list>
					</#if>	 
					{field:'functionKey',title:'功能键',width:120}
				]],
				rownumbers:false,
				pagination:true,
				pageSize:15,
				pageList: [10,15,20,25,30,40,50,100],
				onDblClickRow: onRowClick 
			});

			var p = jQuery('#mydatagrid').datagrid('getPager');
			jQuery(p).pagination({
				onBeforeRefresh:function(){
					//alert('before refresh');
				}
		    });
	});

		 
	function addNew(){
		location.href="<%=request.getContextPath()%>/apps/${modelName}.do?method=edit";
	}

	function onRowClick(rowIndex, row){
            window.open('<%=request.getContextPath()%><%=request.getContextPath()%>/apps/${modelName}.do?method=edit&${idField.name}='+row.id);
	}

	function searchWin(){
		jQuery('#dlg').dialog('open').dialog('setTitle','${classDefinition.title}查询');
		//jQuery('#searchForm').form('clear');
	}

	function resize(){
		jQuery('#mydatagrid').datagrid('resize', {
			width:800,
			height:400
		});
	}

	function editSelected(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
			location.href="<%=request.getContextPath()%>/apps/${modelName}.do?method=edit&${idField.name}="+selected.id;
		}
	}

	function viewSelected(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
		    location.href="<%=request.getContextPath()%>/apps/${modelName}.do?method=edit&readonly=true&${idField.name}="+selected.id;
		}
	}

	function deleteSelections(){
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].id);
		}
		if(ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")){
		    var ${idField.name}s = ids.join(',');
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/apps/${modelName}.do?method=delete&${idField.name}s='+${idField.name}s,
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
					   jQuery('#mydatagrid').datagrid('reload');
				   }
			 });
		} else {
			alert("请选择至少一条记录。");
		}
	}

	function getSelected(){
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected){
			alert(selected.code+":"+selected.name+":"+selected.addr+":"+selected.col4);
		}
	}

	function getSelections(){
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].code);
		}
		alert(ids.join(':'));
	}

	function clearSelections(){
		jQuery('#mydatagrid').datagrid('clearSelections');
	}

	function searchData(){
		var params = jQuery("#searchForm").formSerialize();
		var queryParams = jQuery('#mydatagrid').datagrid('options').queryParams;
	    <#if pojo_fields?exists>
         <#list  pojo_fields as field>	
		   <#if field.editable>
		queryParams.${field.name} = document.getElementById("query_${field.name}").value;
	      </#if>	 
         </#list>
        </#if>		   
		jQuery('#mydatagrid').datagrid('reload');	
		jQuery('#dlg').dialog('close');
	}
		 
</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div style="background:#fafafa;padding:2px;border:1px solid #ddd;font-size:12px"> 
	<img src="<%=request.getContextPath()%>/images/window.png">
	&nbsp;<span class="x_content_title">${classDefinition.title}列表</span>
    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
	   onclick="javascript:addNew();">新增</a>  
    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
	   onclick="javascript:editSelected();">修改</a>  
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'"
	   onclick="javascript:deleteSelections();">删除</a> 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'"
	   onclick="javascript:searchWin();">查找</a>
   </div> 
  </div> 
  <div data-options="region:'center',border:true">
	 <table id="mydatagrid"></table>
  </div>  
</div>
<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
	closed="true" buttons="#dlg-buttons">
 <form id="searchForm" name="searchForm" method="post">
	 <table class="easyui-form" >
		<tbody>
        <#if pojo_fields?exists>
         <#list  pojo_fields as field>	
		   <#if field.editable>
			<tr>
				 <td>${field.title}</td>
				 <td>
				 <#if field.javaType?exists && field.javaType== 'Date'>
				  <input id="query_${field.name}" name="query_${field.name}" class="easyui-datebox"></input>
                 <#elseif field.javaType?exists && field.javaType== 'Integer'>
				 <input id="query_${field.name}" name="query_${field.name}" class="easyui-numberbox" precision="0" ></input>
				 <#elseif field.javaType?exists && field.javaType== 'Long'>
				 <input id="query_${field.name}" name="query_${field.name}" class="easyui-numberbox" precision="0" ></input>
				 <#elseif field.javaType?exists && field.javaType== 'Double'>
				 <input id="query_${field.name}" name="query_${field.name}" class="easyui-numberbox" ></input>
				 <#else>
                 <input id="query_${field.name}" name="query_${field.name}" class="easyui-validatebox" type="text"></input>
				 </#if>
				</td>
			</tr>
	  </#if>	 
    </#list>
    </#if>
	</tbody>
</table>
</div>
<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:searchData()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:jQuery('#dlg').dialog('close')">取消</a>
</div>
</body>
</html>
 