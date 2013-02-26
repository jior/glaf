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
<title>报表任务定义</title>
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
				url:'<%=request.getContextPath()%>/rs/bi/reportTask/list?gridType=easyui',
				sortName: 'id',
				sortOrder: 'desc',
				remoteSort: false,
				singleSelect:true,
				idField:'reportTask.id',
				columns:[[
	                {title:'编号',field:'reportTask.id',width:80,sortable:true},
					{title:'名称',field:'name', width:120},
					{title:'标题',field:'subject', width:120},
					{title:'发送标题',field:'sendTitle', width:120},
					{title:'创建日期',field:'createDate', width:120},
					{title:'创建人',field:'createBy', width:120},
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
		location.href="<%=request.getContextPath()%>/mx/bi/reportTask/edit";
	}

	function onRowClick(rowIndex, row){
        window.open('<%=request.getContextPath()%>/mx/bi/reportTask/edit?reportTaskId='+row.id);
	}

	function searchWin(){
		jQuery('#dlg').dialog('open').dialog('setTitle','报表任务定义查询');
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
			location.href="<%=request.getContextPath()%>/mx/bi/reportTask/edit?reportTaskId="+selected.id;
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
		    location.href="<%=request.getContextPath()%>/mx/bi/reportTask/edit?readonly=true&reportTaskId="+selected.id;
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
				   url: '<%=request.getContextPath()%>/rs/bi/reportTask/deleteAll?rowIds='+rowIds,
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
		queryParams.reportIds = document.getElementById("query_reportIds").value;
		queryParams.name = document.getElementById("query_name").value;
		queryParams.subject = document.getElementById("query_subject").value;
		queryParams.mailRecipient = document.getElementById("query_mailRecipient").value;
		queryParams.mobileRecipient = document.getElementById("query_mobileRecipient").value;
		queryParams.sendTitle = document.getElementById("query_sendTitle").value;
		queryParams.sendContent = document.getElementById("query_sendContent").value;
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
	&nbsp;<span class="x_content_title">报表任务定义列表</span>
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
	 <table id="mx_datagrid"></table>
  </div>  
</div>
<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
	closed="true" buttons="#dlg-buttons">
 <form id="searchForm" name="searchForm" method="post">
	 <table class="easyui-form" >
		<tbody>
			<tr>
				 <td>报表集编号</td>
				 <td>
                 <input id="query_reportIds" name="query_reportIds" class="easyui-validatebox" type="text"></input>
				</td>
			</tr>
			<tr>
				 <td>名称</td>
				 <td>
                 <input id="query_name" name="query_name" class="easyui-validatebox" type="text"></input>
				</td>
			</tr>
			<tr>
				 <td>标题</td>
				 <td>
                 <input id="query_subject" name="query_subject" class="easyui-validatebox" type="text"></input>
				</td>
			</tr>
			<tr>
				 <td>邮件接收人</td>
				 <td>
                 <input id="query_mailRecipient" name="query_mailRecipient" class="easyui-validatebox" type="text"></input>
				</td>
			</tr>
			<tr>
				 <td>手机接收人</td>
				 <td>
                 <input id="query_mobileRecipient" name="query_mobileRecipient" class="easyui-validatebox" type="text"></input>
				</td>
			</tr>
			<tr>
				 <td>发送标题</td>
				 <td>
                 <input id="query_sendTitle" name="query_sendTitle" class="easyui-validatebox" type="text"></input>
				</td>
			</tr>
			<tr>
				 <td>发送内容</td>
				 <td>
                 <input id="query_sendContent" name="query_sendContent" class="easyui-validatebox" type="text"></input>
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
