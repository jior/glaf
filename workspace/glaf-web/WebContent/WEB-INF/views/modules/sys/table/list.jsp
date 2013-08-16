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
<title>数据表</title>
<link href="<%=request.getContextPath()%>/scripts/artDialog/skins/default.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
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
				url:'<%=request.getContextPath()%>/mx/sys/table/json',
				sortName: 'tablename',
				sortOrder: 'asc',
				remoteSort: false,
				checkbox: true, 
				idField:'tablename',
				columns:[[
					{field:'ck', checkbox:true, width:60},
	                {title:'序号',field:'startIndex',width:80},
					{title:'表名',field:'tablename', width:180},
					{title:'Category',field:'cat', width:120},
					{title:'Schema',field:'schem', width:120}
				]],
				rownumbers:false,
				pagination:false,
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

    function genCreateScripts(){
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].tablename);
		}
		if(ids.length > 0 ){
		    var x_ids = ids.join(',');
			var dbType = jQuery('#dbType').val();
			if(confirm("确定导出'"+dbType+"'数据库的建表脚本吗？")){
			    jQuery("#tables").val(x_ids);
			    document.iForm.action="<%=request.getContextPath()%>/mx/sys/table/genCreateScripts?dbType="+dbType;
			    document.iForm.submit();
			}
		} else {
			alert("请选择至少一条记录。");
		}
	}


	function exportData(){
		var dbType = jQuery('#dbType').val();
		var list = document.exportForm.selectedTables; 
		var itemId = "";
        var len02 = list.length;
	    for (var i=0;i<len02;i++) {
           itemId = itemId+list.options[i].value+",";
	    }
        //alert(itemId);
	    document.getElementById("exportTables").value = itemId;
		document.exportForm.action="<%=request.getContextPath()%>/mx/sys/table/exportData?dbType="+dbType;
		document.exportForm.submit(); 
	}

	function exportSysTables(){
        var dbType = jQuery('#dbType').val();
		if(confirm("确定导出'"+dbType+"'数据库的初始化脚本吗？")){
		  document.iForm.action="<%=request.getContextPath()%>/mx/sys/table/exportSysTables?dbType="+dbType;
		  document.iForm.submit();
		}
	}


	function showInsertScripts(){
        var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0; i<rows.length; i++){
			ids.push(rows[i].tablename);
		}
		if(ids.length > 0 ){
		    var x_ids = ids.join(',');
			var dbType = jQuery('#dbType').val();
			jQuery("#tables").val(x_ids);
			var list = document.exportForm.selectedTables;     
			for(var i=0; i<rows.length; i++){
				//alert(rows[i].tablename);
                list.options[i].value = rows[i].tablename+"";
				list.options[i].text = rows[i].tablename+"";
			}
			jQuery('#exp_dlg').dialog('open').dialog('setTitle','导出插入语句');
		} else {
			alert("请选择至少一条记录。");
		}
	}

	function genMappings(){
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].tablename);
		}
		if(ids.length > 0 ){
		    var ids = ids.join(',');
			var dbType = jQuery('#dbType').val();
			jQuery("#tables").val(ids);
			document.iForm.action="<%=request.getContextPath()%>/mx/sys/table/genMappings?dbType="+dbType;
			document.iForm.submit();
		} else {
			alert("请选择至少一条记录。");
		}
	}

	function updateHibernateDDL(){
		if(confirm("确定配置文件已经修改完毕？")){
		  jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/table/updateHibernateDDL',
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
				   }
			 });
		}
	}

	function updateMetaInfo(){
	    var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].tablename);
		}
		if(ids.length > 0 ){
			if(confirm("确定更新表元数据信息吗？")){
			  jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/rs/system/table/updateMetaInfo?tables='+ids,
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
					   }
				 });
			}
		}
	}

	function showData(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows.length ==1){
		    var tablename = rows[0].tableName_enc;
		    window.open('<%=request.getContextPath()%>/mx/sys/table/resultList?q=1&tableName_enc='+tablename);
		}
	 }

	 function onRowClick(rowIndex, row){
	    var link = '<%=request.getContextPath()%>/mx/sys/table/resultList?q=1&tableName_enc='+row.tableName_enc;
	    art.dialog.open(link, { height: 425, width: 880, title: row.tablename+"列表信息", lock: true, scrollbars:"no" }, false);
	}
	 

	function moveUp() {
	    var list = document.exportForm.selectedTables;
		if (list.length > 0) {
			var selectedIndex = list.selectedIndex;
			if( selectedIndex > 0 ) {
				var tmpValue = list.options[selectedIndex - 1].value;
				var tmpText = list.options[selectedIndex - 1].text;
				list.options[selectedIndex - 1].value = list.options[selectedIndex].value;
				list.options[selectedIndex - 1].text = list.options[selectedIndex].text;
				list.options[selectedIndex].value = tmpValue;
				list.options[selectedIndex].text = tmpText;
				list.options[selectedIndex - 1].selected = true;
			}
		}
	 }

	function moveDown() {
		var list = document.exportForm.selectedTables;     
		if ( list.length > 0) {
			var selectedIndex = list.selectedIndex;
			if(selectedIndex < (list.length - 1) ) {
				var tmpValue = list.options[selectedIndex].value;
				var tmpText = list.options[selectedIndex].text;
				list.options[selectedIndex].value = list.options[selectedIndex  + 1].value;
				list.options[selectedIndex].text = list.options[selectedIndex  + 1].text;
				list.options[selectedIndex + 1].value = tmpValue;
				list.options[selectedIndex + 1].text = tmpText;
				list.selectedIndex = selectedIndex + 1;
			}
		}
	}

</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"  > 
	<img src="<%=request.getContextPath()%>/images/window.png">
	&nbsp;<span class="x_content_title">数据表</span>
        数据库类型：<select id="dbType" name="dbType">
			<option value="h2" selected>H2</option>
			<option value="db2">DB2</option>
			<option value="oracle">Oracle</option>
			<option value="mysql">MySQL</option>
			<option value="sqlserver">SQLServer</option>
			<option value="postgresql">PostgreSQL</option>
		</select>

	   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'"
	      onclick="javascript:genCreateScripts();">生成数据库建表脚本</a> 

	   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'"
	      onclick="javascript:showInsertScripts();">生成插入数据脚本</a>

	   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'"
	      onclick="javascript:exportSysTables();">生成基础表数据脚本</a>

	   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'"
	      onclick="javascript:genMappings();">生成Mapping文件</a> 

	   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'"
	      onclick="javascript:updateMetaInfo();">更新元数据</a> 

	   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'"
	      onclick="javascript:updateHibernateDDL();">更新本数据库结构</a> 

	   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-list'"
	      onclick="javascript:showData();">查看数据</a> 
   </div> 
  </div> 
  <div data-options="region:'center',border:true">
	 <table id="mydatagrid"></table>
  </div>  
</div>
<form id="iForm" name="iForm" method="post" action="">
	<input type="hidden" id="tables" name="tables">
</form>

<div id="exp_dlg" class="easyui-dialog" style="width:450px;height:400px;padding:10px 20px" closed="true" >
    <form id="exportForm" name="exportForm" method="post">
	     <input type="hidden" id="exportTables" name="exportTables" >
         <table class="easyui-form" width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
		  <tr>
			<td>
			   <select id="selectedTables" name="selectedTables" style="width: 250px;" size="22">
			      <%for(int i=0;i<20;i++){%>
			       <option value=""></option>
				  <%}%>
			   </select>
            </td>
			<td  align="center" valign="top" height="30">&nbsp;
			    <a href="#" class="easyui-linkbutton" iconCls="icon-up" 
				   onclick="javascript:moveUp();">向上</a>
				<br/><br/>
				<a href="#" class="easyui-linkbutton" iconCls="icon-down" 
				   onclick="javascript:moveDown();">向下</a>
                <br/><br/>
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" 
				   onclick="javascript:exportData();">确定</a>
				<br/><br/>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" 
				   onclick="javascript:jQuery('#exp_dlg').dialog('close');">取消</a>
			</td>
		  </tr>
		</table>
    </form>
</div>

</body>
</html>
