<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>图表定义</title>
	<%@ include file="/WEB-INF/views/tm/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
		jQuery(function(){
			jQuery('#easyui_data_grid').datagrid({
				//title:'图表定义',
				//iconCls:'icon-save',
				//width:800,
				width:1000,
				height:480,
				fit:true,
				fitColumns:true,
				nowrap: false,
				striped: true,
				collapsible:true,
				url:'<%=request.getContextPath()%>/rs/bi/chart/list?gridType=easyui',
				sortName: 'id',
				sortOrder: 'desc',
				remoteSort: false,
				singleSelect:true,
				idField:'id',
				pageSize:20,
				columns:[[
	                {title:'编号',field:'id',width:80,sortable:true},
					{field:'subject',title:'标题',width:180},
					{field:'chartName',title:'图表名称',width:120},
					{field:'mapping',title:'别名',width:120},
					{field:'chartTitle',title:'图表主题',width:180},
					{field:'chartType',title:'图表类型',width:130,formatter:formatter1},
					{field:'createDate',title:'创建日期',width:120}
				]],
				pagination:true,
				rownumbers:false,
				onDblClickRow: onRowClick 
			});

			var p = jQuery('#easyui_data_grid').datagrid('getPager');
			jQuery(p).pagination({
				onBeforeRefresh:function(){
					alert('before refresh');
				}
			});
		});

		function onRowClick(rowIndex, row){
            window.open('<%=request.getContextPath()%>/mx/bi/chart/edit?chartId='+row.id);
		}

		function formatter1(value,row,index){
			 if(value=='pie'){
				return "饼图";
			} else if(value=='line'){
				return "线型图";
			} else if(value=='stackedbar'){
				return "堆叠条形图";
			}
			return "";
		}

		function searchWin(){
			jQuery('#dlg').dialog('open').dialog('setTitle','图表定义查询');
			//jQuery('#searchForm').form('clear');
		}

		function resize(){
			jQuery('#easyui_data_grid').datagrid('resize', {
				width:800,
				height:400
			});
		}

		function create(){						 
			location.href="<%=request.getContextPath()%>/mx/bi/chart/edit";
		}

		function editSelected(){
		    var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
			if(rows == null || rows.length !=1){
			     alert("请选择其中一条记录。");
				 return;
			}
			var selected = jQuery('#easyui_data_grid').datagrid('getSelected');
			if (selected ){
			  location.href="<%=request.getContextPath()%>/mx/bi/chart/edit?chartId="+selected.id;
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
			  location.href="<%=request.getContextPath()%>/mx/bi/chart/edit?chartId="+selected.id;
			}
		}

		function deleteSelections(){
			var ids = [];
			var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].id);
			}
			if(ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")){
			  var chartIds = ids.join(',');
			  jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/bi/chart/deleteAll?chartIds='+chartIds,
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

		 
	</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div style="background:#fafafa;padding:2px;border:1px solid #ddd;font-size:12px"> 
	<span class="x_content_title">图表定义列表</span>
    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
	onclick="create();">新增</a>  
    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
	onclick="editSelected();">修改</a>  
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'">删除</a> 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'">查找</a>
   </div> 
  </div> 
  <div data-options="region:'center',border:false">
	 <table id="easyui_data_grid"></table>
  </div>  
</div>
</body>
</html>