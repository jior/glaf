<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邮件存储</title>
<%@ include file="/WEB-INF/views/tm/header.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
		 
		 function formatStatus(val,row){
			if (val ==1){
				return '<span style="color:red;">空间满</span>';
			} else {
				return '<span style="color:green;">使用中</span>';;
			}
		}

		function formatStorageType(val,row){
			if (val == '1') {
				return '<span style="color:blue;">MongoDB</span>';
			} 
			if (val == '2'){
				return '<span style="color:blue;">Cassandra</span>';
			} 
			if (val == '3'){
				return '<span style="color:blue;">HBase</span>';
			} 
			
			return '<span style="color:blue;">关系数据库</span>';
		}

		function addNew(){
			 location.href="<%=request.getContextPath()%>/mx/mail/mailStorage/edit";
		}

		function searchWin(){
			jQuery('#dlg').dialog('open').dialog('setTitle','邮件存储查询');
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
				 location.href="<%=request.getContextPath()%>/mx/mail/mailStorage/edit?storageId="+selected.id;
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
				 location.href="<%=request.getContextPath()%>/mx/mail/mailStorage/edit?readonly=true&storageId="+selected.id;
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
		    queryParams.subject = document.getElementById("query_subject").value;
			jQuery('#easyui_data_grid').datagrid('reload');	
			jQuery('#dlg').dialog('close');
		}
		 
	</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div style="background:#fafafa;padding:2px;border:1px solid #ddd;font-size:12px"> 
	<span class="x_content_title">邮件存储列表</span>
     <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
		     onclick="javascript:addNew();">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
		     onclick="javascript:editSelected();">修改</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" 
		     onclick="javascript:viewSelected();">查看</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" 
		     onclick="javascript:searchWin();"> 检索 </a>
   </div> 
  </div> 
  <div data-options="region:'center',border:false">
	<table id="easyui_data_grid" class="easyui-datagrid" style="padding-left:20px;width:820px;height:420px"
			url="<%=request.getContextPath()%>/rs/mail/mailStorage/list?gridType=easyui"
			idField="id"
			pageSize="15" pageList="[10,15, 20,30,40,50,100]"
			remoteSort="true"  pagination="true" fit="true"
			rownumbers="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="subject" width="200">主题</th>
				<th field="dataSpace" width="90">数据存储</th>
				<th field="dataTable" width="90">数据表</th>
				<th field="storageType" width="120" formatter="formatStorageType">存储类型</th>
				<th field="status" width="80" formatter="formatStatus">使用状态</th>
				<th field="createBy" width="80">创建人</th>
				<th field="createDate" width="125">创建日期</th>
			</tr>
		</thead>
	</table>
  </div>  
</div>

<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
  <form id="searchForm" name="searchForm" method="post">
	<table class="easyui-form" >
		<tbody>
			<tr>
				 <td>主题</td>
				 <td>
                 <input id="query_subject" name="query_subject" class="inpu-xlarge" type="text"></input>
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