<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MailItem</title>
<%@ include file="/WEB-INF/views/tm/header.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" >

		 function formatSendStatus(val,row){
			if (val =='1'){
				return '<span style="color:green;">成功</span>';
			} 
			if (val =='0'){
				return '<span style="color:black;">未发送</span>';
			} 
			return '<span style="color:red;">发送失败</span>';;
		}

	   function formatReceiveStatus(val,row){
			if (val =='0'){
				return '<span style="color:black;">未接收</span>';
			} else {
				return '<span style="color:green;">已接收</span>';;
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
				   url: '<%=request.getContextPath()%>/rs/mail/mailTask/deleteAll?itemIds='+rowIds,
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

		function sendmail(){
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/mail/mailTask/sendMails?taskId=${taskId}',
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


        function addMail(){
		    location.href="<%=request.getContextPath()%>/mx/mail/mailTask/addMail?taskId=${taskId}";
	    }

	    function uploadMail(){
		    location.href="<%=request.getContextPath()%>/mx/mail/mailTask/showUpload?taskId=${taskId}";
	    }

        function account(){		 
			location.href="<%=request.getContextPath()%>/mx/mail/mailTask/account?taskId=${taskId}";
		}

		function accountList(){
			location.href="<%=request.getContextPath()%>/mx/mail/mailAccount";
		}

		 
	</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div style="background:#fafafa;padding:2px;border:1px solid #ddd;font-size:12px"> 
	<span class="x_content_title">邮件列表</span>
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
		     onclick="javascript:addMail();">增加邮件</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
		     onclick="javascript:uploadMail();">上传邮件</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-mail" plain="true" 
		     onclick="javascript:sendmail();">立即发送</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-accounts" plain="true" 
		     onclick="javascript:accountList();">邮件帐户</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-actor" plain="true" 
		     onclick="javascript:account();">发送帐户</a>
 </div> 
  </div> 
  <div data-options="region:'center',border:false">
  <table id="easyui_data_grid" class="easyui-datagrid" style="width:900px;height:420px"
			url="<%=request.getContextPath()%>/rs/mail/mailTask/mailList?gridType=easyui&taskId=${taskId}"
			idField="id" fit="true" fitColumns="true"
			pageSize="15" pageList="[10,15,20,30,40,50,100]"
			remoteSort="true"  pagination="true"
			rownumbers="true" >
		<thead frozen="true">
			<tr>
				<th field="ck" checkbox="true" width="100"></th>
				<th field="id" width="80" align="left">ID</th>
				<th field="mailTo" width="180" align="left">收件人</th>
			</tr>
		</thead>
		<thead>
			<tr>
				<th field="sendStatus" width="100" align="center"  formatter="formatSendStatus">发送状态</th>
				<th field="sendDate" width="125" align="center">发送时间</th>
				<th field="receiveStatus" width="100" align="center"  formatter="formatReceiveStatus">接收状态</th>
				<th field="receiveDate" width="125" align="center">接收时间</th>
				<th field="receiveIP" width="90" align="center">接收IP</th>
			</tr>
		</thead>
	</table>
  </div>
</div>

</body>
</html>