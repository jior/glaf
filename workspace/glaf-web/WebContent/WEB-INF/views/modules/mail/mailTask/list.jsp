<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邮件任务</title>
<%@ include file="/WEB-INF/views/tm/header.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
		 
		function addNew(){
			 location.href="<%=request.getContextPath()%>/mx/mail/mailTask/edit";
		}

		 function formatStatus(val,row){
			if (val ==1){
				return '<span style="color:red;">锁定</span>';
			} else {
				return '<span style="color:green;">使用中</span>';
			}
		}

		function searchWin(){
			jQuery('#dlg').dialog('open').dialog('setTitle','邮件任务查询');
			//jQuery('#searchForm').form('clear');
		}

		function account(){
			 var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
			if(rows == null || rows.length !=1){
			     alert("请选择其中一条记录。");
				 return;
			}
			var selected = jQuery('#easyui_data_grid').datagrid('getSelected');
			if (selected ){
			    location.href="<%=request.getContextPath()%>/mx/mail/mailTask/account?taskId="+selected.id;
			}
		}

		function addMail(){
			 var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
			if(rows == null || rows.length !=1){
			     alert("请选择其中一条记录。");
				 return;
			}
			var selected = jQuery('#easyui_data_grid').datagrid('getSelected');
			if (selected ){
			    location.href="<%=request.getContextPath()%>/mx/mail/mailTask/addMail?taskId="+selected.id;
			}
		}

		function uploadMail(){
			var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
			if(rows == null || rows.length !=1){
			     alert("请选择其中一条记录。");
				 return;
			}
			var selected = jQuery('#easyui_data_grid').datagrid('getSelected');
			if (selected ){
			    location.href="<%=request.getContextPath()%>/mx/mail/mailTask/showUpload?taskId="+selected.id;
			}
		}

		function sendmail(){
            var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
			if(rows == null || rows.length !=1){
			     alert("请选择其中一条记录。");
				 return;
			}
			var selected = jQuery('#easyui_data_grid').datagrid('getSelected');
			if (selected ){
				   //alert(params);
				   jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/mail/mailTask/sendMails?taskId='+selected.id,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   alert('操作成功！');
					   location.href="<%=request.getContextPath()%>/mx/mail/mailTask";
				   }
			 });
			}
		}

		function mailList(){
			 var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
			if(rows == null || rows.length !=1){
			     alert("请选择其中一条记录。");
				 return;
			}
			var selected = jQuery('#easyui_data_grid').datagrid('getSelected');
			if (selected ){
			    location.href="<%=request.getContextPath()%>/mx/mail/mailTask/mailList?taskId="+selected.id;
			}
		}

		function accountList(){
			location.href="<%=request.getContextPath()%>/mx/mail/mailAccount";
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
				 location.href="<%=request.getContextPath()%>/mx/mail/mailTask/edit?taskId="+selected.id;
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
				 location.href="<%=request.getContextPath()%>/mx/mail/mailTask/edit?readonly=true&taskId="+selected.id;
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
		    queryParams.startDate = document.getElementById("query_startDate").value;
		    queryParams.endDate = document.getElementById("query_endDate").value;		   
			jQuery('#easyui_data_grid').datagrid('reload');	
			jQuery('#dlg').dialog('close');
		}
		 
	</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud" > 
	<span class="x_content_title">邮件任务列表</span>
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
		     onclick="javascript:addNew();">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
		     onclick="javascript:editSelected();">修改</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" 
		     onclick="javascript:viewSelected();">查看</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" 
		     onclick="javascript:searchWin();"> 检索 </a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
		     onclick="javascript:addMail();">增加邮件</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
		     onclick="javascript:uploadMail();">上传邮件</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-mail" plain="true" 
		     onclick="javascript:sendmail();">立即发送</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-mail" plain="true" 
		     onclick="javascript:mailList();">邮件列表</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-accounts" plain="true" 
		     onclick="javascript:accountList();">邮件帐户</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-actor" plain="true" 
		     onclick="javascript:account();">发送帐户</a>
   </div> 
  </div> 
  <div data-options="region:'center',border:false">
	 <table id="easyui_data_grid" class="easyui-datagrid" style="width:900px;height:420px"
			url="<%=request.getContextPath()%>/rs/mail/mailTask/list?gridType=easyui"
			idField="id" fit="true"
			pageSize="15" pageList="[10,15,20,30,40,50,100]"
			remoteSort="true"  pagination="true"
			rownumbers="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="subject" width="240">主题</th>
				<th field="startDate" width="120">开始时间</th>
				<th field="endDate" width="120">结束时间</th>
				<th field="createBy" width="120">创建人</th>
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
				 <td>主题</td>
				 <td>
                 <input id="query_subject" name="query_subject" class="x-text easyui-validatebox" type="text"></input>
				</td>
			</tr>
		
			<tr>
				 <td>开始时间</td>
				 <td>
				  <input id="query_startDate" name="query_startDate" class="x-text easyui-datebox"></input>
				</td>
			</tr>
			<tr>
				 <td>结束时间</td>
				 <td>
				  <input id="query_endDate" name="query_endDate" class="x-text easyui-datebox"></input>
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