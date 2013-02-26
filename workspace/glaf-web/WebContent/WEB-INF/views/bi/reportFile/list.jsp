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
<title>报表文件</title>
<%@ include file="/WEB-INF/views/tm/header.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

   jQuery(function(){
		jQuery('#mx_datagrid').datagrid({
				width:1000,
				height:480,
				fit:true,
				fitColumns:true,
				nowrap: false,
				striped: true,
				collapsible:true,
				url:'<%=request.getContextPath()%>/rs/bi/reportFile/list?gridType=easyui',
				sortName: 'id',
				sortOrder: 'desc',
				remoteSort: false,
				singleSelect:true,
				idField:'reportFile.id',
				columns:[[
	                {title:'编号',field:'reportFile.id',width:80,sortable:true},
					{title:'报表编号',field:'reportId', width:120},
					{title:'文件名称',field:'filename', width:120},
					{title:'文件大小',field:'fileSize', width:120},
					{title:'文件年月日',field:'reportYearMonthDay', width:120},
					{title:'创建日期',field:'createDate', width:120},
					{field:'functionKey',title:'功能键',width:120}
				]],
				rownumbers:false,
				pagination:true,
				pageSize:50,
				pageList: [10,15,20,25,30,40,50,100],
				onDblClickRow: onRowClick 
			});

			var p = jQuery('#mx_datagrid').datagrid('getPager');
			jQuery(p).pagination({
				onBeforeRefresh:function(){
					//alert('before refresh');
				}
		    });
	});

		 
	function addNew(){
		location.href="<%=request.getContextPath()%>/mx/bi/reportFile/edit";
	}

	function onRowClick(rowIndex, row){
        window.open('<%=request.getContextPath()%><%=request.getContextPath()%>/mx/bi/reportFile/edit?reportFileId='+row.id);
	}

	function searchWin(){
		jQuery('#dlg').dialog('open').dialog('setTitle','报表文件查询');
		//jQuery('#searchForm').form('clear');
	}

	function resize(){
		jQuery('#mx_datagrid').datagrid('resize', {
			width:800,
			height:400
		});
	}

	function editSelected(){
		var rows = jQuery('#mx_datagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mx_datagrid').datagrid('getSelected');
		if (selected ){
			location.href="<%=request.getContextPath()%>/mx/bi/reportFile/edit?reportFileId="+selected.id;
		}
	}

	function viewSelected(){
		var rows = jQuery('#mx_datagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mx_datagrid').datagrid('getSelected');
		if (selected ){
		    location.href="<%=request.getContextPath()%>/mx/bi/reportFile/edit?readonly=true&reportFileId="+selected.id;
		}
	}

	function deleteSelections(){
		var ids = [];
		var rows = jQuery('#mx_datagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].id);
		}
		if(ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")){
		    var rowIds = ids.join(',');
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/bi/reportFile/deleteAll?rowIds='+rowIds,
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
					   jQuery('#mx_datagrid').datagrid('reload');
				   }
			 });
		} else {
			alert("请选择至少一条记录。");
		}
	}

	function getSelected(){
		var selected = jQuery('#mx_datagrid').datagrid('getSelected');
		if (selected){
			alert(selected.code+":"+selected.name+":"+selected.addr+":"+selected.col4);
		}
	}

	function getSelections(){
		var ids = [];
		var rows = jQuery('#mx_datagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].code);
		}
		alert(ids.join(':'));
	}

	function clearSelections(){
		jQuery('#mx_datagrid').datagrid('clearSelections');
	}

	function searchData(){
		var params = jQuery("#searchForm").formSerialize();
		var queryParams = jQuery('#mx_datagrid').datagrid('options').queryParams;
		queryParams.reportId = document.getElementById("query_reportId").value;
		queryParams.filename = document.getElementById("query_filename").value;
		queryParams.fileSize = document.getElementById("query_fileSize").value;
		queryParams.reportYearMonthDay = document.getElementById("query_reportYearMonthDay").value;
		jQuery('#mx_datagrid').datagrid('reload');	
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
	&nbsp;<span class="x_content_title">报表文件列表</span>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'"
	   onclick="javascript:deleteSelections();">删除</a> 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'"
	   onclick="javascript:searchWin();">查找</a>
   </div> 
  </div> 
  <div data-options="region:'center',border:true">
	 <table id="mx_datagrid"></table>
  </div>  
</div>
<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
	closed="true" buttons="#dlg-buttons">
 <form id="searchForm" name="searchForm" method="post">
	 <table class="easyui-form" >
		<tbody>
			<tr>
				 <td>报表编号</td>
				 <td>
                 <input id="query_reportId" name="query_reportId" class="easyui-validatebox" type="text"></input>
				</td>
			</tr>
			<tr>
				 <td>文件名称</td>
				 <td>
                 <input id="query_filename" name="query_filename" class="easyui-validatebox" type="text"></input>
				</td>
			</tr>
			<tr>
				 <td>文件大小</td>
				 <td>
				 <input id="query_fileSize" name="query_fileSize" class="easyui-numberbox" precision="0" ></input>
				</td>
			</tr>
			<tr>
				 <td>文件年月日</td>
				 <td>
				 <input id="query_reportYearMonthDay" name="query_reportYearMonthDay" class="easyui-numberbox" precision="0" ></input>
				</td>
			</tr>
	</tbody>
</table>
</div>
<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:searchData()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:jQuery('#dlg').dialog('close')">取消</a>
</div>
</body>
</html>
