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
<title>查询管理</title>
<link href="<%=request.getContextPath()%>/scripts/artDialog/skins/default.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
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
			childNodes[i].icon="<%=request.getContextPath()%>/images/basic.gif";
		}
		return childNodes;
	}


    function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		jQuery("#nodeId").val(treeNode.id);
		loadMxData('<%=request.getContextPath()%>/mx/dts/query/json?nodeId='+treeNode.id);
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
				width:1000,
				height:480,
				fit:true,
				fitColumns:true,
				nowrap: false,
				striped: true,
				collapsible:true,
				url:'<%=request.getContextPath()%>/mx/dts/query/json',
				remoteSort: false,
				singleSelect:true,
				idField:'id',
				columns:[[
	                {title:'序号',field:'startIndex',width:80,sortable:false},
					{title:'名称',field:'name',width:220,sortable:false},
					{title:'标题',field:'title',width:220,sortable:false},
					{title:'目标表',field:'targetTableName',width:220,sortable:false},
					{title:'创建日期',field:'createDate',width:90,sortable:false},
					{title:'功能键',field:'functionKeys',width:120,sortable:false}
				]],
				rownumbers:false,
				pagination:true,
				pageSize:15,
				pageList: [10,15,20,25,30,40,50,100],
				onDblClickRow: onRowClick 
			});

			var p = jQuery('#easyui_data_grid').datagrid('getPager');
			jQuery(p).pagination({
				onBeforeRefresh:function(){
					//alert('before refresh');
				}
		    });

	});

		 
	function addNew(){
		var nodeId = jQuery("#nodeId").val();
		if(nodeId=='' || nodeId==null){
			alert("请在左边选择分类类型！");
			return;
		}
		var link="<%=request.getContextPath()%>/mx/dts/query/edit?nodeId="+nodeId;
	    //art.dialog.open(link, { height: 480, width: 680, title: "添加记录", lock: true, scrollbars:"no" }, false);
		location.href=link;
	}

	function onRowClick(rowIndex, row){
		var nodeId = jQuery("#nodeId").val();
	    var link = '<%=request.getContextPath()%>/mx/dts/query/edit?queryId='+row.id+"&nodeId="+nodeId;
		//art.dialog.open(link, { height: 480, width: 680, title: "修改记录", lock: true, scrollbars:"no" }, false);
		location.href=link;
	}

	function searchWin(){
	    jQuery('#dlg').dialog('open').dialog('setTitle','信息查询');
	}

	function resize(){
		jQuery('#easyui_data_grid').datagrid('resize', {
			width:800,
			height:400
		});
	}

	function editSelected(){
	    var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
	    var nodeId = jQuery("#nodeId").val();
	    if(rows == null || rows.length !=1){
		  alert("请选择其中一条记录。");
		  return;
	    }
	    var selected = jQuery('#easyui_data_grid').datagrid('getSelected');
	    if (selected ){
		  var link = "<%=request.getContextPath()%>/mx/dts/query/edit?queryId="+selected.id+"&nodeId="+nodeId;
		  //art.dialog.open(link, { height: 480, width: 680, title: "修改记录", lock: true, scrollbars:"no" }, false);
		  location.href=link;
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
		    location.href="<%=request.getContextPath()%>/mx/dts/query/edit?queryId="+selected.id;
		}
	}


	function deleteSelections(){
		var ids = [];
		var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].id);
		}
		if(ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")){
		    var str = ids.join(',');
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/dts/query/delete?queryIds='+str,
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
					   jQuery('#easyui_data_grid').datagrid('reload');
				   }
			 });
		} else {
			alert("请选择至少一条记录。");
		}
	}

	function reloadGrid(){
	    jQuery('#easyui_data_grid').datagrid('reload');
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
	    jQuery('#easyui_data_grid').datagrid('reload');	
	    jQuery('#dlg').dialog('close');
	}

</script>
</head>
<body style="margin:1px;">  

<div style="margin:0;"></div>  
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
	   <form id="iForm" name="iForm" method="post">
	    <input type="hidden" id="nodeId" name="nodeId" value="" >
		<div class="toolbar-backgroud"  > 
		<img src="<%=request.getContextPath()%>/images/window.png">
		&nbsp;<span class="x_content_title">查询管理</span>
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
		   onclick="javascript:addNew();">新增</a>  
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
		   onclick="javascript:editSelected();">修改</a> 
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'"
		   onclick="javascript:deleteSelections();">删除</a> 
	   </div> 
	   </form>
	  </div> 
	  <div data-options="region:'center',border:true">
		 <table id="easyui_data_grid"></table>
	  </div>  
    </div>
  </div>
</div>

</body>
</html>
