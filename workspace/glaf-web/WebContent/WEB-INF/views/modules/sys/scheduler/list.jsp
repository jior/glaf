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
<title>调度管理</title>
<link href="<%=request.getContextPath()%>/scripts/artDialog/skins/default.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/plugins/iframeTools.js"></script>
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
				url:'<%=request.getContextPath()%>/mx/system/scheduler/json?taskType=${taskType}',
				remoteSort: false,
				checkbox: true, 
				idField:'id',
				columns:[[
					{field:'ck', checkbox:true, width:60},
	                {title:'任务名称',field:'taskName',width:240},
					{title:'开始时间',field:'startDate', width:90},
					{title:'结束时间',field:'endDate', width:90},
					{title:'运行设置',field:'runType', width:120, formatter:formatterRunType},
					{title:'运行状态',field:'runStatus', width:120, formatter:formatterRunStatus},
					{title:'上次运行时间',field:'previousFireTime_datetime', width:120},
					{title:'下次运行时间',field:'nextFireTime_datetime', width:120},
					{title:'创建人',field:'createUserName', width:60},
					{title:'创建时间',field:'createDate', width:90},
					{title:'状态',field:'locked', width:80, formatter:formatterStatus }
				]],
				rownumbers:true,
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

    function formatterRunType(val, row){
       if(val == 0){
			return '一天一次';
	   } else if(val == 1){
			return '重复单实例';
	   } else  {
			return '可重复多次';
	   }  
	}

	function formatterRunStatus(val, row){
       if(val == 0){
			return '<span style="color:#9999ff; font: bold 13px 宋体;">等待运行</span>';
	   } else if(val == 1){
			return '<span style="color:#3366ff; font: bold 13px 宋体;">正在运行</span>';
	   } else if(val == 2){
			return '<span style="color:green; font: bold 13px 宋体;">运行成功</span>';
	   } else if(val == 3){
			return '<span style="color:red; font: bold 13px 宋体;">运行失败</span>';
	   } else  {
			return '未知';
	   }  
	}

	function formatterStatus(val, row){
       if(val == 0){
			return '<span style="color:green; font: bold 13px 宋体;">已启用</span>';
	   } else  {
			return '<span style="color:red; font: bold 13px 宋体;">已禁用</span>';
	   }  
	}


	function resize(){
		jQuery('#mydatagrid').datagrid('resize', {
			width:800,
			height:400
		});
	}

	 
	function reloadGrid(){
	    jQuery('#mydatagrid').datagrid('reload');
	}


	function clearSelections(){
	    jQuery('#mydatagrid').datagrid('clearSelections');
	}

	function loadGridData(url){
		  jQuery.post(url,{qq:'xx'},function(data){
		      var text = JSON.stringify(data); 
              alert(text);
			  jQuery('#mydatagrid').datagrid('loadData', data);
		  },'json');
	  }

    function editRow(){
		 location.href ='<%=request.getContextPath()%>/mx/system/scheduler/edit?taskType=${taskType}';
	}
	 

	function deleteRows(){
        var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows.length ==1){
		    var taskId = rows[0].taskId;
		    jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/mx/system/scheduler/delete?id='+taskId,
					   dataType: 'json',
					   error: function(data){
						   alert('服务器处理错误！');
					   },
					   success: function(data){
						   if(data != null && data.message != null){
							 alert(data.message);
						   } else {
							 alert('操作成功完成！');
						   }
						   reloadGrid();
					   }
				 });
		} else {
		    alert("请选择其中一条记录。");
		}
	}


	function updateRow(){
        var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows.length ==1){
		    var taskId = rows[0].taskId;
		   location.href ='<%=request.getContextPath()%>/mx/system/scheduler/edit?taskType=${taskType}&id='+taskId;
		} else {
		    alert("请选择其中一条记录。");
		}
	}

	function enableRows(){
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].taskId);
		}
		if(ids.length > 0 ){
			if(confirm("确定启用这些调度吗？")){
			  jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/mx/system/scheduler/enableRows?ids='+ids,
					   dataType: 'json',
					   error: function(data){
						   alert('服务器处理错误！');
					   },
					   success: function(data){
						   if(data != null && data.message != null){
							   alert(data.message);
						   } else {
							   alert('操作成功完成！');
						   }
						   reloadGrid();
					   }
				 });
			}
		}
	}

	function runIt(){
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].taskId);
		}
		if(ids.length > 0 ){
		  if(confirm("确定立即运行这些调度吗？")){
		    jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/system/scheduler/runIt?ids='+ids,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   reloadGrid();
				   }
			 });
		  }
		}
	}

	function disableRows(){
	    var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].taskId);
		}
		if(ids.length > 0 ){
			if(confirm("确定禁用这些调度吗？")){
			  jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/mx/system/scheduler/disableRows?ids='+ids,
					   dataType: 'json',
					   error: function(data){
						   alert('服务器处理错误！');
					   },
					   success: function(data){
						   if(data != null && data.message != null){
							   alert(data.message);
						   } else {
							   alert('操作成功完成！');
						   }
						   reloadGrid();
					   }
				 });
			}
		}
	}


	 function showLogs(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows.length ==1){
		    var taskId = rows[0].taskId;
		    location.href='<%=request.getContextPath()%>/mx/system/schedulerLog?taskId='+taskId;
		}else{
		    alert("请选择其中一条记录。");
		}
	 }

	 function onRowClick(rowIndex, row){
	    var link = '<%=request.getContextPath()%>/mx/system/scheduler/edit?taskType=${taskType}&id='+row.id;
	    art.dialog.open(link, { height: 425, width: 880, title: "调度详细信息", lock: true, scrollbars:"no" }, false);
	}

	function search(){
		 var params = jQuery("#iForm").formSerialize();
         jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/mx/system/scheduler/json',
					   dataType: 'json',
					   data: params,
					   error: function(data){
						   alert('服务器处理错误！');
					   },
					   success: function(data){
						   jQuery('#mydatagrid').datagrid({
							  queryParams: {
								  taskNameLike: document.getElementById("taskNameLike").value,
								  locked:document.getElementById("locked").value,
								  runStatus:document.getElementById("runStatus").value
							  }
						   });
						   jQuery('#mydatagrid').datagrid('loadData', data);
					   }
				 });
	}
	 

</script>
</head>
<body style="margin:1px;"> 

<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:46px"> 
    <div class="toolbar-backgroud"  > 
      <form id="iForm" name="iForm" method="post" action="">  
      <table>
		   <tr>
		   <td>
            <img src="<%=request.getContextPath()%>/images/window.png">
	        &nbsp;<span class="x_content_title">调度列表</span>
		   </td>
		   <td>
			   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
				  onclick="javascript:editRow();">新增</a> 

			   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
				  onclick="javascript:updateRow();">修改</a>

			   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'"
				  onclick="javascript:deleteRows();">删除</a>

			   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-list'"
				  onclick="javascript:showLogs();">查看运行历史</a>

			   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-ok'"
				  onclick="javascript:enableRows();">启用</a> 

			   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-no'"
				  onclick="javascript:disableRows();">禁用</a> 

			   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-sys'"
				  onclick="javascript:runIt();">运行</a> 

			</td>
		    <td>任务名&nbsp;</td>
			<td><input type="text" id="taskNameLike" name="taskNameLike" class="x-searchtext"></td>
			<td>状态&nbsp;</td>
			<td>
			 <select id="locked" name="locked">
			    <option value=""></option>
				<option value="0">启用</option>
				<option value="1">禁用</option>
			 </select>
			</td>
			<td>运行状态&nbsp;</td>
			<td>
			 <select id="runStatus" name="runStatus">
			    <option value=""></option>
				<option value="0">未运行</option>
				<option value="1">运行中</option>
				<option value="2">运行成功</option>
				<option value="3">运行失败</option>
			 </select>
			</td>
			<td>
			  <input type="button" value="查找" class="  btnGray" onclick="javascript:search();">
			</td>
		   </tr>
		</table>
		<input type="hidden" id="ids" name="ids">
       </form>
   </div> 
  </div> 
  <div data-options="region:'center',border:true">
	 <table id="mydatagrid"></table>
  </div>  
</div>


</body>
</html>
