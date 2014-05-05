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
<title>模块列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
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
				url:'<%=request.getContextPath()%>/sys/app.do?method=json',
				sortName: 'id',
				sortOrder: 'desc',
				remoteSort: false,
				//singleSelect:true,
				idField:'id',
				columns:[[
	                {title:'序号',field:'startIndex',width:80,sortable:true},
					{title:'名称',field:'name', width:120},
					{title:'描述',field:'desc', width:120},
					{title:'链接',field:'url', width:120},
					{title:'是否有效',field:'locked', width:120, formatter:formatterStatus},
					{field:'functionKey',title:'功能键',width:120}
				]],
				rownumbers:false,
				pagination:true,
				pageSize:15,
				pageList: [10,15,20,25,30,40,50,100],
				onDblClickRow: onMyRowClick 
			});

			var p = jQuery('#mydatagrid').datagrid('getPager');
			jQuery(p).pagination({
				onBeforeRefresh:function(){
					//alert('before refresh');
				}
		    });
	});

		 
	function addNew(){
		var url="role.do?method=prepareAdd";
	    var width=450;
	    var height=280;
	    var scroll="no";
	    openWindow(url, width, height, scroll);
	}


	function formatterStatus(val, row){
       if(val == 0){
			return '<span style="color:green; font: bold 13px 宋体;">是</span>';
	   } else  {
			return '<span style="color:red; font: bold 13px 宋体;">否</span>';
	   }  
	}

	function onMyRowClick(rowIndex, row){
		//alert(row.id+"  "+row.name);
		var url="role.do?method=prepareModify&id="+row.id;
	    var width=450;
	    var height=280;
	    var scroll="no";
	    //openWindow(url, width, height, scroll);
		jQuery('#edit_dlg').dialog('open').dialog('setTitle','模块编辑');
		jQuery('#editForm').form('load', '<%=request.getContextPath()%>/rs/sys/app/detail?id='+row.id);
	}

	function searchWin(){
		jQuery('#dlg').dialog('open').dialog('setTitle','模块查询');
		//jQuery('#searchForm').form('clear');
	}

	function editData(){
		jQuery("#id").val('');
		jQuery("#name").val('');
		jQuery("#code").val('');
		jQuery("#desc").val('');
        jQuery('#edit_dlg').dialog('open').dialog('setTitle','模块编辑');
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
			//alert("请选择其中一条记录。");
			jQuery.messager.alert('Info', '请选择其中一条记录。', 'info');
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
			 jQuery('#edit_dlg').dialog('open').dialog('setTitle','模块编辑');
			 jQuery('#editForm').form('load', '<%=request.getContextPath()%>/rs/sys/app/detail?id='+selected.id);
		}
	}

	function editSelected2(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			//alert("请选择其中一条记录。");
			jQuery.messager.alert('Info', '请选择其中一条记录。', 'info');
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
			var url="app.do?method=prepareModify&id="+selected.id;
			var width=450;
			var height=280;
			var scroll="no";
			openWindow(url, width, height, scroll);
		}
	}

	function viewSelected(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			//alert("请选择其中一条记录。");
			jQuery.messager.alert('Info', '请选择其中一条记录。', 'info');
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
		    var url="app.do?method=prepareModify&id="+selected.id;
			var width=450;
			var height=280;
			var scroll="no";
			openWindow(url, width, height, scroll);
		}
	}

	function saveData(){
		var res = 'saveAdd';
		var params = jQuery("#editForm").formSerialize();
		var id = jQuery("#id").val();
		if(id != ''){
			res = 'saveModify';
		}
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/sys/app/'+res,
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   //alert('服务器处理错误！');
					   jQuery.messager.alert('Info', '服务器处理错误！', 'info');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   //alert(data.message);
						   jQuery.messager.alert('Info', data.message, 'info');
					   } else {
						   //alert('操作成功完成！');
						   jQuery.messager.alert('Info', '操作成功完成！', 'info');
					   }
					   jQuery('#mydatagrid').datagrid('reload');
					   jQuery('#edit_dlg').dialog('close');
				   }
			 });
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
				   url: '<%=request.getContextPath()%>/rs/sys/app/batchDelete?rowIds='+rowIds,
				   dataType:  'json',
				   error: function(data){
					   if(data != null && data.message != null){
						   //alert(data.message);
						   jQuery.messager.alert('Info', data.message, 'info');
					   } else{
					       //alert('服务器处理错误！');
						   jQuery.messager.alert('Info', '服务器处理错误！', 'info');
					   }
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   //alert(data.message);
						   jQuery.messager.alert('Info', data.message, 'info');
					   } else {
						   //alert('操作成功完成！');
						   jQuery.messager.alert('Info', '操作成功完成！', 'info');
					   }
					   jQuery('#mydatagrid').datagrid('reload');
				   }
			 });
		} else {
			//alert("请选择至少一条记录。");
			jQuery.messager.alert('Info', '请选择至少一条记录。', 'info');
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

	function displayField(){
		var preCtr = document.getElementById("preCtr").value
        var field = document.getElementById("field").value;
        layerId = "searchForm_"+field+"_div";
		if(preCtr !=""){
           preCtr = preCtr+"_div";
           jQuery('#'+preCtr).hide();
		}
		document.getElementById("preCtr").value="searchForm_"+field;
		jQuery('#'+layerId).show();
	}
		 
</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"> 
	<img src="<%=request.getContextPath()%>/images/window.png">
	&nbsp;<span class="x_content_title">模块列表</span>
    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
	   onclick="javascript:editData();">新增</a>  
    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
	   onclick="javascript:editSelected();">修改</a>  
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'"
	   onclick="javascript:deleteSelections();">删除</a> 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'"
	   onclick="javascript:searchWin();">查找</a>
   </div> 
  </div> 
  <div data-options="region:'center',border:true">
	 <table id="mydatagrid"></table>
  </div>  
</div>

<div id="edit_dlg" class="easyui-dialog" style="width:480px;height:280px;padding:10px 20px"
	closed="true" >
    <form id="editForm" name="editForm" method="post">
	     <input type="hidden" id="id" name="id" value="">
         <table class="easyui-form" width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
		  <tr>
			<td width="20%" align="left" class="input-box">模块名称*</td>
			<td><input id="name" name="name" type="text" size="35" class="easyui-validatebox" datatype="string" nullable="no" maxsize="20" chname="模块名称" data-options="required:true"></td>
		  </tr>
		  <tr>
			<td width="20%" align="left" class="input-box2" valign="top">代码*</td>
			<td><input id="code" name="code" type="text" size="35" class="easyui-validatebox" datatype="string" nullable="no" maxsize="20" chname="模块代码" data-options="required:true"></td>
		  </tr>
		  <tr>
			<td width="20%" align="left" class="input-box2" valign="top">描　　述</td>
			<td><textarea id="desc" name="desc" cols="28" rows="5" class="easyui-textbox" datatype="string" nullable="yes" maxsize="100" chname="模块描述"></textarea>        
			</td>
		  </tr>
		  <tr>
			<td colspan="2" align="center" valign="bottom" height="30">&nbsp;
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" 
				   onclick="javascript:saveData()">保存</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" 
				   onclick="javascript:jQuery('#edit_dlg').dialog('close')">取消</a>
			</td>
		  </tr>
		</table>
    </form>
</div>

<div id="dlg" class="easyui-dialog" style="width:580px;height:150px;padding:10px 20px"
	closed="true" buttons="#dlg-buttons">
    <form id="searchForm" name="searchForm" method="post">
	 <input type="hidden" name="preCtr" id="preCtr"> 
	 <table class="easyui-form" >
		<tbody>
		 <tr>
		  <td width='40%' align="right"  >
          字段选择&nbsp;&nbsp;
			  <select id="field" name="field" onchange="javascript:displayField();" >
				<option value="" selected>----请选择----</option>
				<option value="name">模块名称</option>
				<option value="code">模块代码</option>
				<option value="desc">模块描述</option>
			  </select>
		  <td width="60%" align="left">
			 <div id="searchForm_name_div" style="display:none">
			   <span>
			   <select id="searchForm_name_filter" name="searchForm_name_filter">
				<option value="like" selected>包含</option>
				<option value="not like">不包含</option>
				<option value="=" >等于</option>
				<option value="!=" >不等于</option>
			   </select>
			   <input id="searchForm_name" name="searchForm_name" type="text" size="30" class="easyui-validatebox" data-options="required:true"></input>
			   </span>
			 </div>
			 <div id="searchForm_code_div" style="display:none">
			   <span>
			    <select id="searchForm_code_filter" name="searchForm_code_filter">
				<option value="like" selected>包含</option>
				<option value="not like">不包含</option>
				<option value="=" >等于</option>
				<option value="!=" >不等于</option>
			    </select>
			   <input id="searchForm_code" name="searchForm_code" type="text" size="30" class="easyui-validatebox" data-options="required:true"></input>
			   </span>
			 </div>
			 <div id="searchForm_desc_div" style="display:none">
			   <span>
			   <select id="searchForm_desc_filter" name="searchForm_desc_filter">
				<option value="like" selected>包含</option>
				<option value="not like">不包含</option>
				<option value="=" >等于</option>
				<option value="!=" >不等于</option>
			   </select>
			   <input id="searchForm_desc" name="searchForm_desc" type="text" size="30" class="easyui-validatebox" data-options="required:true"></input>
			   </span>
			 </div>
		 </td>
		 <!-- <td width="30%" align="left">
		   <div style="padding-left:5px;padding-top:5px;font-size:12px"> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:submitXY('R', false);">检索</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:submitXY('S', false);">再检索</a>
		   </div>
		  </td> -->
		  </tr>
	    </tbody>
     </table>
    </form>
</div>
<div id="dlg-buttons">
	<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:searchData()">查询</a> -->
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:submitXY('R', false);">检索</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:submitXY('S', false);">再检索</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:jQuery('#dlg').dialog('close')">取消</a>
</div>
</body>
</html>
