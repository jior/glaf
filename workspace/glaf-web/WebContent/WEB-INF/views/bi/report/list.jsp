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
<title>报表定义</title>
<link href="<%=request.getContextPath()%>/scripts/artDialog/skins/default.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/plugins/iframeTools.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript">

   var setting = {
			async: {
				enable: true,
				url:"<%=request.getContextPath()%>/rs/tree/treeJson?nodeCode=report_category",
				dataFilter: filter
			},
			callback: {
				onClick: zTreeOnClick
			}
		};
  
  	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			childNodes[i].icon="<%=request.getContextPath()%>/icons/icons/basic.gif";
		}
		return childNodes;
	}


    function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		jQuery("#nodeId").val(treeNode.id);
		loadMxData('<%=request.getContextPath()%>/mx/bi/report/json?nodeId='+treeNode.id);
	}

	function loadMxData(url){
		  jQuery.get(url+'&randnum='+Math.floor(Math.random()*1000000),{qq:'xx'},function(data){
		      //var text = JSON.stringify(data); 
              //alert(text);
			  jQuery('#easyui_data_grid').datagrid('loadData', data);
			  //jQuery('#easyui_data_grid').datagrid('load',getMxObjArray(jQuery("#iForm").serializeArray()));
		  },'json');
	}

    jQuery(document).ready(function(){
			jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	});

		jQuery(function(){
			jQuery('#easyui_data_grid').datagrid({
				//title:'报表定义',
				//iconCls:'icon-grid',
				//width:800,
				width:980,
				height:480,
				fit:true,
				fitColumns:true,
				nowrap: false,
				striped: true,
				collapsible:false,
				singleSelect:true,
				url:'<%=request.getContextPath()%>/mx/bi/report/json',
				sortName: 'id',
				sortOrder: 'desc',
				remoteSort: false,
				idField:'id',
				pageSize:10,
				columns:[[
					//{field:'ck',checkbox:true},
				    {field:'id', title:'编号',width:80,sortable:true},
					{field:'name',title:'名称',width:90},
					{field:'subject',title:'标题',width:240},
					{field:'reportName',title:'报表名称',width:320},
					{field:'type',title:'报表类型',width:90},
					{field:'createDate',title:'创建日期',width:90}
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

		function searchWin(){
			jQuery('#dlg').dialog('open').dialog('setTitle','报表定义查询');
			//jQuery('#iForm').form('clear');
		}

		function resize(){
			jQuery('#easyui_data_grid').datagrid('resize', {
				width:800,
				height:400
			});
		}

		function create(){						 
			location.href="<%=request.getContextPath()%>/mx/bi/report/edit?nodeId="+jQuery("#nodeId").val();
		}

		function editSelected(){
		    var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
			if(rows == null || rows.length !=1){
			     alert("请选择其中一条记录。");
				 return;
			}
			var selected = jQuery('#easyui_data_grid').datagrid('getSelected');
			if (selected ){
			  location.href="<%=request.getContextPath()%>/mx/bi/report/edit?reportId="+selected.id;
			}
		}

		function onRowClick(rowIndex, row){
            window.open('<%=request.getContextPath()%>/mx/bi/report/edit?reportId='+row.id);
		}

		function importData(){
		  if(confirm("确定重新导入数据吗？")){
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/bi/report/importDataAndFetch',
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data.message != null){
						   alert(data.message);
					   } else {
						 alert('操作完成！');
					   }
				   }
			     });
		   }
		}

		function sendMail(){
			if(confirm("确定生成报表并发送邮件吗？")){
			var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
			if(rows != null && rows.length ==1){
				  var selected = jQuery('#easyui_data_grid').datagrid('getSelected');
			      jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/bi/report/sendMail?reportId='+selected.id,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data.message != null){
						   alert(data.message);
					   } else {
						 alert('操作完成！');
					   }
				   }
			     });
			} else {
                jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/bi/report/sendMailAllInOne',
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data.message != null){
						   alert(data.message);
					   } else {
						 alert('操作完成！');
					   }
				   }
			     });
			  }
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
			  location.href="<%=request.getContextPath()%>/mx/bi/report/edit&reportId="+selected.id;
			}
		}

		function deleteSelections(){
			var ids = [];
			var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].id);
			}
			if(ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")){
			  var reportIds = ids.join(',');
			  jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/bi/report/deleteAll?reportIds='+reportIds,
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
<body style="margin:0px;">  
<div style="margin:0;"></div>  
<input type="hidden" id="nodeId" name="nodeId" value="" >
<div class="easyui-layout" data-options="fit:true">  
    <div data-options="region:'west',split:true" style="width:180px;">
	  <div class="easyui-layout" data-options="fit:true">  
           
			 <div data-options="region:'center',border:false">
			    <ul id="myTree" class="ztree"></ul>  
			 </div> 
			 
        </div>  
	</div> 
   <div data-options="region:'center'"> 
	<div class="easyui-layout" data-options="fit:true">  
	   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
		<div class="toolbar-backgroud"> 
		<span class="x_content_title">报表定义列表</span>
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
		   onclick="javascript:create();">新增</a>  
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
		   onclick="javascript:editSelected();">修改</a>  
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'">删除</a> 
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'">查找</a>
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-imp'"
		   onclick="javascript:importData();">加载数据</a> 
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-mail'"
		   onclick="javascript:sendMail();">发送邮件</a>
	   </div> 
	  </div> 
	  <div data-options="region:'center',border:false">
		 <table id="easyui_data_grid"></table>
	  </div>  
	</div>
  </div>
</div>
</body>
</html>