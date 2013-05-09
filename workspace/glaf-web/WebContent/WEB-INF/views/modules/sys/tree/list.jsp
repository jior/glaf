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
<title>分类树</title>
<link href="<%=request.getContextPath()%>/scripts/artDialog/skins/default.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/plugins/iframeTools.js"></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/scripts/main.js'></script>
<script type="text/javascript">

    var prevTreeNode;

    var setting = {
			async: {
				enable: true,
				//url: getAjaxTreeUrl,
				url: "<%=request.getContextPath()%>/rs/sys/tree/allTreeJson?nodeCode=0",
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
			if(childNodes[i].isParent){
			    //childNodes[i].icon="<%=request.getContextPath()%>/icons/icons/orm_root.gif";
			}
		}
		return childNodes;
	}

	function getAjaxTreeUrl(treeId, treeNode) {
		if(treeNode){
		    return "<%=request.getContextPath()%>/rs/sys/tree/treeJson?nodeCode=0&node="+treeNode.id;
		}
        return "<%=request.getContextPath()%>/rs/sys/tree/treeJson?nodeCode=0";
	}
 
    function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		jQuery("#nodeId").val(treeNode.id);
		loadData('<%=request.getContextPath()%>/sys/tree.do?method=json&parentId='+treeNode.id);
	}

	function loadData(url){
		  jQuery.get(url,{qq:'xx'},function(data){
		      //var text = JSON.stringify(data); 
              //alert(text);
			  jQuery('#mydatagrid').datagrid('loadData', data);
		  },'json');
	  }

    jQuery(document).ready(function(){
			jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	});

   jQuery(function(){
		jQuery('#mydatagrid').datagrid({
				width:1000,
				height:480,
				fit:true,
				fitColumns:true,
				nowrap: false,
				striped: true,
				collapsible:true,
				url:'<%=request.getContextPath()%>/sys/tree.do?method=json',
				sortName: 'id',
				sortOrder: 'desc',
				remoteSort: false,
				singleSelect:true,
				idField:'id',
				columns:[[
	                {title:'序号',field:'startIndex',width:80,sortable:true},
					{title:'名称',field:'name', width:120},
					{title:'代码',field:'code', width:120},
					{title:'描述',field:'desc', width:180},
					{title:'是否有效',field:'locked', width:90, formatter:formatterStatus}
				]],
				rownumbers:false,
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


		 
	function formatterStatus(val, row){
       if(val == 0){
			return '<span style="color:green; font: bold 13px 宋体;">是</span>';
	   } else  {
			return '<span style="color:red; font: bold 13px 宋体;">否</span>';
	   }  
	}


	function addNew(){
		var nodeId = jQuery("#nodeId").val();
		var link = "<%=request.getContextPath()%>/sys/tree.do?method=prepareAdd&parent="+nodeId;
	    art.dialog.open(link, { height: 400, width: 480, title: "添加分类", lock: true, scrollbars:"yes" }, false);
		//openWindow(link, 400, 480, "yes");
	}


	function onRowClick(rowIndex, row){
		var nodeId = jQuery("#nodeId").val();
	    var link = '<%=request.getContextPath()%>/sys/tree.do?method=prepareModify&id='+row.id;
	    art.dialog.open(link, { height: 400, width: 480, title: "修改分类", lock: true, scrollbars:"yes" }, false);
		//openWindow(link, 400, 480, "yes");
	}


	function searchWin(){
	    jQuery('#dlg').dialog('open').dialog('setTitle','分类树查询');
	    //jQuery('#searchForm').form('clear');
	}


	function resize(){
		jQuery('#mydatagrid').datagrid('resize', {
			width:800,
			height:400
		});
	}


	function editSelected(){
	    var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    if(rows == null || rows.length !=1){
		alert("请选择其中一条记录。");
		return;
	    }
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected ){
		  //location.href="<%=request.getContextPath()%>/sys/tree.do?method=edit&rowId="+selected.id;
		  var link = "<%=request.getContextPath()%>/sys/tree.do?method=prepareModify&id="+selected.id;
		  art.dialog.open(link, { height: 400, width: 480, title: "修改分类", lock: true, scrollbars:"yes" }, false);
		  //openWindow(link, 400, 480, "yes");
	    }
	}
 

	function viewSelected(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
		    location.href="<%=request.getContextPath()%>/sys/tree.do?method=prepareModify&id="+selected.id;
		}
	}

	function deleteSelections(){
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].id);
		}
		if(ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")){
		    var rowIds = ids.join(',');
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/sys/tree.do?method=delete&rowIds='+rowIds,
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
					   jQuery('#mydatagrid').datagrid('reload');
				   }
			 });
		} else {
			alert("请选择至少一条记录。");
		}
	}

	function reloadGrid(){
	    jQuery('#mydatagrid').datagrid('reload');
	}

	function getSelected(){
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected){
		alert(selected.code+":"+selected.name+":"+selected.addr+":"+selected.col4);
	    }
	}

	function getSelections(){
	    var ids = [];
	    var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    for(var i=0;i<rows.length;i++){
		ids.push(rows[i].code);
	    }
	    alert(ids.join(':'));
	}

	function clearSelections(){
	    jQuery('#mydatagrid').datagrid('clearSelections');
	}

	function searchData(){
	    var params = jQuery("#searchForm").formSerialize();
	    var queryParams = jQuery('#mydatagrid').datagrid('options').queryParams;
	    jQuery('#mydatagrid').datagrid('reload');	
	    jQuery('#dlg').dialog('close');
	}
		 
</script>
</head>
<body style="margin:1px;">  
<input type="hidden" id="nodeId" name="nodeId" value="" >
<div class="easyui-layout" data-options="fit:true">  
    <div data-options="region:'west',split:true" style="width:195px;">
	  <div class="easyui-layout" data-options="fit:true">  
           
			 <div data-options="region:'center',border:false">
			    <ul id="myTree" class="ztree"></ul>  
			 </div> 
			 
        </div>  
	</div> 
   <div data-options="region:'center'">   
		<div class="easyui-layout" data-options="fit:true">  
		   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
			<div class="toolbar-backgroud"  > 
			<img src="<%=request.getContextPath()%>/images/window.png">
			&nbsp;<span class="x_content_title">分类树列表</span>
			<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
			   onclick="javascript:addNew();">新增</a>  
			<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
			   onclick="javascript:editSelected();">修改</a>  
			<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'"
			   onclick="javascript:deleteSelections();">删除</a> 
			<!-- <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'"
			   onclick="javascript:searchWin();">查找</a> -->
		   </div> 
		  </div> 
		  <div data-options="region:'center',border:true">
			 <table id="mydatagrid"></table>
		  </div>  
      </div>
	</div>
</div>
<div id="edit_dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
	closed="true" buttons="#dlg-buttons">
    <form id="editForm" name="editForm" method="post">
         
    </form>
</div>
<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
	closed="true" buttons="#dlg-buttons">
    <form id="searchForm" name="searchForm" method="post">
	<table class="easyui-form" >
            <tbody>
	    </tbody>
        </table>
    </form>
</div>
<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:searchData()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:jQuery('#dlg').dialog('close')">取消</a>
</div>
</body>
</html>
