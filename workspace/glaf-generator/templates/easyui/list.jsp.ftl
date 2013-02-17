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
    jQuery(function(){
	jQuery('#easyui_data_grid').datagrid({
	    title:'${classDefinition.title}',
	    //iconCls:'icon-save',
	    //width:800,
	    width:"90%",
	    height:380,
	    //fit:true,
	    nowrap: false,
	    striped: true,
	    collapsible:true,
	    url:'<%=request.getContextPath()%>/rs/apps/${modelName}/list?gridType=easyui',
	    sortName: 'id',
	    sortOrder: 'desc',
	    remoteSort: false,
	    idField:'id',
	    columns:[[
		{field:'ck',checkbox:true},
	        {title:'编号',field:'id',width:80,sortable:true},
<#if pojo_fields?exists>
    <#list  pojo_fields as field>
	<#if field.displayType == 4>
		{field:'${field.name}',title:'${field.title}',width:120},
	</#if>
    </#list>
</#if>
		{field:'opt',title:'功能键',width:100,align:'center', 
		  formatter:function(value,rec){
			return '<span style="color:red">Edit Delete</span>';
			}
		}
		]],
	pagination:true,
	rownumbers:false,
	toolbar:[{
		id:'btnadd',
		text:'新增',
		iconCls:'icon-add',
		handler:function(){                 	 
			location.href="<%=request.getContextPath()%>/apps/${modelName}.do?method=edit";
		 }
	        },{
                id:'btnedit',
                 text:'修改',
                 iconCls:'icon-edit',
                 handler: editSelected
				},{
                 id:'btndelete',
                 text:'删除',
                 iconCls:'icon-remove',
                 handler:deleteSelections
				},{
                 id:'btnview',
                 text:'查看',
                 iconCls:'icon-edit',
                 handler: viewSelected
				},{
                 id:'btnsearch',
                 text:'搜索',
                 iconCls:'icon-search',
                 handler: searchWin
		}]
	    });

	var p = jQuery('#easyui_data_grid').datagrid('getPageResultr');
	jQuery(p).pagination({
		onBeforeRefresh:function(){
                 alert('before refresh');
		}
	  });
	});

	function searchWin(){
		jQuery('#dlg').dialog('open').dialog('setTitle','${classDefinition.title}查询');
		//jQuery('#searchForm').form('clear');
	}

	function resize(){
		jQuery('#easyui_data_grid').datagrid('resize', {
		        width:800,
			height:400
		});
	}

	function editSelected(){
		   var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
			if(rows == null || rows.length !=1){
			     alert("请选择其中一条记录。");
				 return;
			}
			var selected = jQuery('#easyui_data_grid').datagrid('getSelected');
			if (selected ){
			  location.href="<%=request.getContextPath()%>/apps/${modelName}.do?method=edit&rowId="+selected.id;
			}
		}

		function viewSelected(){
		    var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
			if(rows == null || rows.length !=1){
			     alert("请选择其中一条记录。");
				 return;
			}
			var selected = jQuery('#easyui_data_grid').datagrid('getSelected');
			if (selected ){
			  location.href="<%=request.getContextPath()%>/apps/${modelName}.do?method=edit&rowId="+selected.id;
			}
		}

		function deleteSelections(){
			var ids = [];
			var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].id);
			}
			if(ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")){
			  var rowIds = ids.join(',');
			  jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/apps/${modelName}/deleteAll?rowIds='+rowIds,
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
                    jQuery('#easyui_data_grid').datagrid('reload');
				   }
			 });
			} else {
			    alert("请选择至少一条记录。");
			}
		}

		function getSelected(){
			var selected = jQuery('#easyui_data_grid').datagrid('getSelected');
			if (selected){
				alert(selected.code+":"+selected.name+":"+selected.addr+":"+selected.col4);
			}
		}

		function getSelections(){
			var ids = [];
			var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].code);
			}
			alert(ids.join(':'));
		}

		function clearSelections(){
			jQuery('#easyui_data_grid').datagrid('clearSelections');
		}

		function searchData(){
		    var params = jQuery("#searchForm").formSerialize();
			var queryParams = jQuery('#easyui_data_grid').datagrid('options').queryParams;
	<#if pojo_fields?exists>
         <#list  pojo_fields as field>	
		   <#if field.editable>
		    queryParams.${field.name} = document.getElementById("query_${field.name}").value;
	      </#if>	 
    </#list>
</#if>		   
			jQuery('#easyui_data_grid').datagrid('reload');	
			jQuery('#dlg').dialog('close');
		}
		 
	</script>
</head>
<body>
	
	<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/pages/images/window.png"
	alt="${classDefinition.title}"> &nbsp;${classDefinition.title}
	</div>

	<table id="easyui_data_grid"></table>

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
				 <#if field.type?exists && field.type== 'Date'>
				  <input id="query_${field.name}" name="query_${field.name}" class="easyui-datebox"></input>
                 <#elseif field.type?exists && field.type== 'Integer'>
				 <input id="query_${field.name}" name="query_${field.name}" class="easyui-numberbox" precision="0" ></input>
				 <#elseif field.type?exists && field.type== 'Long'>
				 <input id="query_${field.name}" name="query_${field.name}" class="easyui-numberbox" precision="0" ></input>
				 <#elseif field.type?exists && field.type== 'Double'>
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