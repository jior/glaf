<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邮件帐户</title>
<%@ include file="/WEB-INF/views/tm/header.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/mxcore.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
		 
		function addNew(){
			 location.href="<%=request.getContextPath()%>/mx/mail/mailAccount/edit";
		}

		 function formatStatus(val,row){
			if (val =='1'){
				return '<span style="color:red;">锁定</span>';
			} else {
				return '<span style="color:green;">使用中</span>';;
			}
		}

		function searchWin(){
			jQuery('#dlg').dialog('open').dialog('setTitle','邮件帐户查询');
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
				 location.href="<%=request.getContextPath()%>/mx/mail/mailAccount/edit?accountId="+selected.id;
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
				 location.href="<%=request.getContextPath()%>/mx/mail/mailAccount/edit?accountId="+selected.id;
			}
		}

		function deleteSelections(){
			 var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
			if(rows == null || rows.length !=1){
			     alert("请选择其中一条记录。");
				 return;
			}
			 
			if(  confirm("数据删除后不能恢复，确定删除吗？")){
			  var selected = jQuery('#easyui_data_grid').datagrid('getSelected');
			  jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/mail/mailAccount/delete?accountId='+selected.id,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   alert('操作成功！');
					   jQuery('#easyui_data_grid').datagrid('reload');
				   }
			 });
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
		    queryParams.mailAddress = document.getElementById("query_mailAddress").value;
		    queryParams.showName = document.getElementById("query_showName").value;
		    queryParams.username = document.getElementById("query_username").value;
		    queryParams.pop3Server = document.getElementById("query_pop3Server").value;
		    queryParams.smtpServer = document.getElementById("query_smtpServer").value;
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
	<span class="x_content_title">邮件帐户列表</span>
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
		     onclick="javascript:addNew();">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
		     onclick="javascript:editSelected();">修改</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" 
		     onclick="javascript:deleteSelections();">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" 
		     onclick="javascript:viewSelected();">查看</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" 
		     onclick="javascript:searchWin();"> 检索 </a>
    </div> 
  </div> 
  <div data-options="region:'center',border:false">
	<table id="easyui_data_grid" class="easyui-datagrid" style="width:900px;height:420px"
			url="<%=request.getContextPath()%>/rs/mail/mailAccount/list?gridType=easyui"
			idField="id" fit="true" fitColumns="true"
			pageSize="15" pageList="[10,15, 20,30,40,50,100]"
			remoteSort="true"  pagination="true"
			rownumbers="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="mailAddress" width="150">邮件地址</th>
				<th field="showName" width="120">显示姓名</th>
				<th field="username" width="120">用户名</th>
				<th field="pop3Server" width="120">POP3服务器</th>
				<th field="smtpServer" width="120">SMTP服务器</th>
				<th field="locked" width="80" formatter="formatStatus">状态</th>
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
				 <td>邮件地址</td>
				 <td>
                 <input id="query_mailAddress" name="query_mailAddress" class="x-text easyui-validatebox" type="text"></input>
				</td>
			</tr>
			<tr>
				 <td>显示姓名</td>
				 <td>
                 <input id="query_showName" name="query_showName" class="x-text  easyui-validatebox" type="text"></input>
				</td>
			</tr>
			<tr>
				 <td>用户名</td>
				 <td>
                 <input id="query_username" name="query_username" class="x-text easyui-validatebox" type="text"></input>
				</td>
			</tr>
			<tr>
				 <td>POP3服务器</td>
				 <td>
                 <input id="query_pop3Server" name="query_pop3Server" class="x-text easyui-validatebox" type="text"></input>
				</td>
			</tr>
			<tr>
				 <td>SMTP服务器</td>
				 <td>
                 <input id="query_smtpServer" name="query_smtpServer" class="x-text easyui-validatebox" type="text"></input>
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