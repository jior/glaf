<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Activiti工作流审批演示</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/icon.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">

    function doXY(){		 
        var selected = jQuery('#dg').datagrid('getSelected');
		if (selected){
			alert("单据编号:"+selected.ID_);
			if(selected.PROCESSINSTANCEID_ != null 
				 && selected.PROCESSINSTANCEID_ != '' 
				 && selected.PROCESSINSTANCEID_.length  > 0 
				 && selected.WFSTATUS_ != 9999){

			    var approve = 'false';

				if(confirm("审核通过吗？")){
					approve = "true";
				}

			   var actorId = document.getElementById("actorId").value;
			   var link = 'completeTask.jsp?processInstanceId='+selected.PROCESSINSTANCEID_+'&approve='+approve+"&actorId="+actorId;
               //alert(link);

			   jQuery.ajax({
					   type: "POST",
					   url: link,
					   dataType:  'json',
					   error: function(data){
						   alert(data.message);
						   location.href="index.jsp";
					   },
					   success: function(data){
						   if(data.message != null){
							   alert(data.message);
						   } else {
							 alert('操作成功完成！');
						   }
						   location.href="index.jsp";
					   }
			   });

			}else{
				alert('启动流程 startProcess.jsp?rowId='+selected.ID_);
				jQuery.ajax({
					   type: "POST",
					   url: 'startProcess.jsp?rowId='+selected.ID_,
					   dataType: 'json',
					   error: function(data){
						   alert(data.message);
						   location.href="index.jsp";
					   },
					   success: function(data){
						   if(data.message != null){
							   alert(data.message);
						   } else {
							 alert('操作成功完成！');
						   }
						   location.href="index.jsp";
					   }
			   });
			}
		} else {
			 alert("请选择一条记录。");
		}
	}

	function viewProc(){
		var selected = jQuery('#dg').datagrid('getSelected');
		if (selected){
	       if(selected.PROCESSINSTANCEID_ != ''){
              window.open('<%=request.getContextPath()%>/mx/activiti/task?processInstanceId='+selected.PROCESSINSTANCEID_);
		   }  
		}else {
			 alert("请选择一条记录。");
		}
	}

	function reloadData(){
		if(confirm("确定要重新加载测试数据吗？")){
			location.href="reload_data.jsp";
		}
	}

	function deployJpdl(){
		window.open('<%=request.getContextPath()%>/mx/activiti/deploy');
	}
   
</script>
</head>
<body>
	<h2>工作流审批范例</h2>
	流程包在workspace/glaf-activiti/bpmn/process目录下，打包必须是zip格式。
	<br>参与者<input type="text" id="actorId" name="actorId" size="20" value="joy">
 	<input type="button" name="btn"  value="提交审核" onclick="javascript:doXY();" />
	<input type="button" name="btn2" value="查看流程" onclick="javascript:viewProc();" />
	<input type="button" name="btn3" value="重新加载测试数据" onclick="javascript:reloadData();" />
	<input type="button" name="btn4" value="发布流程包" onclick="javascript:deployJpdl();" />
	（注：在流程监控图中可以看到参与者。）
    
 	<table id="dg" title="列表数据" class="easyui-datagrid" style="width:800px;height:380px"
			url="get_data.jsp" pagination="true" rownumbers="true" fitColumns="true" singleSelect="true" 
			onDblClickRow ="viewProc" >
		<thead>
			<tr>
				<th field="ID_" width="50">编号</th>
				<th field="PROCESSNAME_" width="50">流程名称</th>
				<th field="PROCESSINSTANCEID_" width="50">流程实例编号</th>
				<th field="STATUS_" width="50">业务状态</th>
				<th field="WFSTATUS_" width="50">工作流状态</th>
			</tr>
		</thead>
	</table>
	
</body>
</html>