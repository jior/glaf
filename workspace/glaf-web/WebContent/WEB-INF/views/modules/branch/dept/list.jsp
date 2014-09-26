<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	pageContext.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门列表</title>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/artDialog/skins/default.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/icons.css">
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/artDialog/plugins/iframeTools.js"></script>
<script type="text/javascript">
 
	var setting = {
		async: {
				enable: true,
				url:"<%=request.getContextPath()%>/branch/department.do?method=treeJson",
				dataFilter: filter
			},
			callback: {
				beforeClick: zTreeBeforeClick,
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

	function zTreeOnExpand(treeId, treeNode){
        treeNode.icon="${contextPath}/scripts/ztree/css/zTreeStyle/img/diy/2.png";
	}

	function updateNode(treeId, treeNode){
		var zTree = jQuery.fn.zTree.getZTreeObj(treeId);
		zTree.setting.view.fontCss["color"] = "#0000ff";
		zTree.updateNode(treeNode);
	}

	function zTreeBeforeClick(treeId, treeNode, clickFlag) {
           
	}

	function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		jQuery("#nodeId").val(treeNode.id);
		loadData('<%=request.getContextPath()%>/branch/department.do?method=json&parentId='+treeNode.id);
	}

	function loadData(url){
		$.post(url,{qq:'xx'},function(data){
		      //var text = JSON.stringify(data); 
              //alert(text);
			  $('#mydatagrid').datagrid('loadData', data);
		},'json');
	}


	$(document).ready(function(){
		$.fn.zTree.init($("#myTree"), setting);
	});

	function onMyDbClickRow(rowIndex, row){
		var link = '<%=request.getContextPath()%>/branch/department.do?method=prepareModify&id='+row.id;
	    art.dialog.open(link, { height: 450, width: 680, title: "修改记录", lock: true, scrollbars:"yes" }, false);
	}

    function formatterFunctions(val, row){
		var str = "<a href='javascript:editDept(\""+row.id+"\");'>修改</a>";
            str+="&nbsp;<a href='javascript:deptUsers2(\""+row.id+"\");'>部门用户</a>";
			str+="&nbsp;<a href='javascript:deptRoles2(\""+row.id+"\");'>部门角色</a>";

	    return str;
	}

	function editDept(deptId){
	    var link = '<%=request.getContextPath()%>/branch/department.do?method=prepareModify&id='+deptId;
	    art.dialog.open(link, { height: 450, width: 680, title: "修改记录", lock: true, scrollbars:"yes" }, false);
	}

    function addNew(){
		var nodeId = jQuery("#nodeId").val();
		var link = "<%=request.getContextPath()%>/branch/department.do?method=prepareAdd&parent="+nodeId;
	    art.dialog.open(link, { height: 420, width: 680, title: "添加记录", lock: true, scrollbars:"yes" }, false);
	}

	function onRowClick(rowIndex, row){
	    var link = '<%=request.getContextPath()%>/branch/department.do?method=prepareModify&id='+row.id;
	    art.dialog.open(link, { height: 450, width: 680, title: "修改记录", lock: true, scrollbars:"yes" }, false);
	}

	function searchWin(){
	    jQuery('#dlg').dialog('open').dialog('setTitle','部门列表查询');
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
	    if ( selected ){
		  var link = "<%=request.getContextPath()%>/branch/department.do?method=prepareModify&id="+selected.id;
		  art.dialog.open(link, { height: 450, width: 680, title: "修改记录", lock: true, scrollbars:"yes" }, false);
	    }
	}

	function deptRoles(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    if(rows == null || rows.length !=1){
		  alert("请选择其中一条记录。");
		  return;
	    }
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if ( selected && selected.id != null && selected.id != 'undefined'){
		  var link = "<%=request.getContextPath()%>/branch/deptRole.do?deptId="+selected.id;
		  art.dialog.open(link, { height: 420, width: 680, title: "部门角色", lock: true, scrollbars:"yes" }, false);
	    }
	}

	function deptRoles2(deptId){
		var link = "<%=request.getContextPath()%>/branch/deptRole.do?deptId="+deptId;
		art.dialog.open(link, { height: 420, width: 680, title: "部门角色", lock: true, scrollbars:"yes" }, false);
	}

	function deptUsers(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    if(rows == null || rows.length !=1){
		  alert("请选择其中一条记录。");
		  return;
	    }
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if ( selected && selected.id != null && selected.id != 'undefined'){
		  var link = "<%=request.getContextPath()%>/branch/user.do?deptId="+selected.id;
		  art.dialog.open(link, { height: 430, width: 880, title: "部门用户", lock: true, scrollbars:"yes" }, false);
	    }
	}

	function deptUsers2(deptId){
		var link = "<%=request.getContextPath()%>/branch/user.do?deptId="+deptId;
		art.dialog.open(link, { height: 430, width: 880, title: "部门用户", lock: true, scrollbars:"yes" }, false);
	}

	function viewSelected(){
		var rows = jQuery('#mydatagrid').datagrid('getSelected');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
		    location.href="<%=request.getContextPath()%>/branch/department.do?method=prepareModify&id="+selected.id;
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
				   url: '<%=request.getContextPath()%>/branch/department.do?method=delete&rowIds='+rowIds,
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
<body>  
<input type="hidden" id="nodeId" name="nodeId" value="" >
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  

    <div data-options="region:'west',split:true" style="width:220px;">
	  <div class="easyui-layout" data-options="fit:true">  
           
			 <div data-options="region:'center',border:false">
			    <ul id="myTree" class="ztree"></ul>  
			 </div> 
			 
        </div>  
	</div> 
	
    <div data-options="region:'center'">  
        <div class="easyui-layout" data-options="fit:true">  

           <div data-options="region:'center',split:true,border:true, fit:true">
		   
             <div style="background:#fafafa;padding:2px;border:1px solid #ddd;font-size:12px"> 
			   <div class="toolbar-backgroud"  > 
				<img src="<%=request.getContextPath()%>/images/window.png">
				&nbsp;<span class="x_content_title">部门列表列表</span>
				<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
				   onclick="javascript:addNew();">新增</a>  
				<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
				   onclick="javascript:editSelected();">修改</a>  
				<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-user'"
				   onclick="javascript:deptUsers();">部门用户</a> 
				<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-actor'"
				   onclick="javascript:deptRoles();">部门角色</a>
			   </div> 
             </div>  

			 <table id="mydatagrid" class="easyui-datagrid" 
					data-options="url:'${contextPath}/branch/department.do?method=json', fit:true,fitColumns:true,nowrap:false,rownumbers:false,showFooter:true,singleSelect:true,onDblClickRow:onMyDbClickRow">
				<thead>
					<tr>
						<th data-options="field:'startIndex',width:60">序号</th>
						<th data-options="field:'name',width:150">名称</th>
						<th data-options="field:'desc',width:180">描述</th>
						<th data-options="field:'code',width:120">代码</th>
						<th data-options="field:'no',width:120">编码</th>
						<th data-options="field:'code2',width:120">部门区分</th>
						<th data-options="field:'xx',width:150, formatter:formatterFunctions">功能键</th>
					</tr>
				</thead>
			</table>  
	    
        </div>  
    </div>  
  </div>  

</body>  
</html>