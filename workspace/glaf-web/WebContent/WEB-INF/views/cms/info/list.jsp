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
<title>${treeModel.name}信息管理</title>
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
<script type="text/javascript">

   var setting = {
			async: {
				enable: true,
				url:"<%=request.getContextPath()%>/rs/tree/treeJson?nodeCode=${serviceKey}",
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
		loadData('<%=request.getContextPath()%>/mx/cms/info/json?serviceKey=${serviceKey}&nodeId='+treeNode.id);
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
				url:'<%=request.getContextPath()%>/mx/cms/info/json?serviceKey=${serviceKey}&workedProcessFlag=${workedProcessFlag}',
				sortName: 'id',
				sortOrder: 'desc',
				remoteSort: false,
				singleSelect:true,
				idField:'id',
				columns:[[
	                {title:'序号',field:'startIndex',width:80,sortable:false},
					{title:'主题',field:'subject',width:350,sortable:false},
					{title:'栏目名称',field:'categoryName',width:120,sortable:false},
					{title:'发布单位',field:'unitName',width:120,sortable:false},
					{title:'发布日期',field:'createDate',width:90,sortable:false},
					{title:'发布状态',field:'publishFlag',width:90,sortable:false, formatter:formatterPublishFlag},
					{title:'状态',field:'status',width:90,sortable:false, formatter:formatterStatus},
					{title:'浏览次数',field:'viewCount',width:90,sortable:false},
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

			jQuery('#dd_audit').dialog('close');
	});

	function formatterPublishFlag(val, row){
         if(val == 1){
			  return '<span style="color:green; font: bold 13px 宋体; ">已发布</span>';
		 }
		 return "未发布";
	}

	function formatterStatus(val, row){
       if(val == 50){
			return '<span style="color:green; font: bold 13px 宋体;">审核通过</span>';
	   } else if(val == -1){
			return '<span style="color:red; font: bold 13px 宋体;">已退回</span>';
	   } else if(val == 0){
			return '未提交';
	   }
	   return '<span style="color:blue; font: bold 13px 宋体;">审核中</span>';
	}


	function switchTasks(){
		 var nodeId = jQuery("#nodeId").val();
		 var workedProcessFlag = jQuery("#workedProcessFlag").val();
		 var url = "<%=request.getContextPath()%>/mx/cms/info/json?serviceKey=${serviceKey}&nodeId="+nodeId+"&workedProcessFlag="+workedProcessFlag;
		 //alert(url);
         jQuery.get(url,{qq:'xx'},function(data){
		      //var text = JSON.stringify(data); 
              //alert(text);
			  jQuery('#mydatagrid').datagrid('loadData', data);
		  },'json');
	}

		 
	function addNew(){
	    //location.href="<%=request.getContextPath()%>/mx/cms/info/edit";
		var nodeId = jQuery("#nodeId").val();
		var link="<%=request.getContextPath()%>/mx/cms/info/edit?serviceKey=${serviceKey}&nodeId="+nodeId;
	    //art.dialog.open(link, { height: 420, width: 680, title: "添加记录", lock: true, scrollbars:"no" }, false);
		location.href=link;
	}

	function onRowClick(rowIndex, row){
        //window.open('<%=request.getContextPath()%>/mx/cms/info/edit?id='+row.id);
	    var link = '<%=request.getContextPath()%>/mx/cms/info/edit?serviceKey=${serviceKey}&id='+row.id;
	    //art.dialog.open(link, { height: 420, width: 680, title: "修改记录", lock: true, scrollbars:"no" }, false);
		location.href=link;
	}

	function searchWin(){
	    jQuery('#dlg').dialog('open').dialog('setTitle','信息查询');
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
		  //location.href="<%=request.getContextPath()%>/mx/cms/info/edit&id="+selected.id;
		  var link = "<%=request.getContextPath()%>/mx/cms/info/edit?serviceKey=${serviceKey}&id="+selected.id;
		  //art.dialog.open(link, { height: 420, width: 680, title: "修改记录", lock: true, scrollbars:"no" }, false);
		  location.href=link;
	    }
	}

	function submitSelected(){
        var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    if(rows == null || rows.length !=1){
		    alert("请选择其中一条记录。");
		    return;
	    }
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected){
           if(selected.processInstanceId && selected.processInstanceId != null){
			   if(selected.wfStatus == 9999){
                  alert("该记录已经审核完成，不能提交。");
			   } else {
			     jQuery('#rowId').val(selected.id);
                 jQuery('#dd_audit').dialog('open');
			   }
		   } else {
              jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/cms/info/startProcess?id='+selected.id,
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
					   switchTasks();
				   }
			 });
		   }
		}
	}


	function completeTask(isAgree){
        if(isAgree == 'false'){
			if(jQuery('#opinion').val() == ""){
				alert("请输入不通过的原因。");
				document.getElementById('#opinion').focus();
				return;
			}
		}
		var rowId = jQuery('#rowId').val();
		var params = jQuery("#auditForm").formSerialize();
        jQuery.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/mx/cms/info/completeTask?id='+rowId+'&isAgree='+isAgree,
			data: params,
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
				switchTasks();
				jQuery('#dd_audit').dialog('close');
			}
		 });
	}

	 
	function viewSelected(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
		    location.href="<%=request.getContextPath()%>/mx/cms/info/edit?serviceKey=${serviceKey}&id="+selected.id;
		}
	}

	function viewProcess(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected &&  selected.processInstanceId && selected.processInstanceId != null){
		    window.open('<%=request.getContextPath()%>/mx/jbpm/task/task?processInstanceId='+selected.processInstanceId);
		} else {
            alert("该记录没有启动工作流，不能查看流程进度。");
		}
	}

	function deleteSelections(){
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].id);
		}
		if(ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")){
		    var str = ids.join(',');
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/cms/info/delete?ids='+str,
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
	    // var params = jQuery("#searchForm").formSerialize();
	    // var queryParams = jQuery('#mydatagrid').datagrid('options').queryParams;
	    jQuery('#mydatagrid').datagrid('reload');	
	    jQuery('#dlg').dialog('close');
	}

	function editCatProps(){
        var nodeId = jQuery("#nodeId").val();
		var link = "<%=request.getContextPath()%>/mx/cms/info/props?nodeId="+nodeId+"&serviceKey=${serviceKey}";
        art.dialog.open(link, { height: 420, width: 620, title: "栏目属性设置", lock: true, scrollbars:"no" }, false);
	}
		 
</script>
</head>
<body style="margin:1px;">  
<input type="hidden" id="nodeId" name="nodeId" value="" >
<input type="hidden" id="rowId" name="rowId" value="" >
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
		<div class="toolbar-backgroud"  > 
		<img src="<%=request.getContextPath()%>/images/window.png">
		&nbsp;<span class="x_content_title">${treeModel.name}信息列表</span>
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-props'" 
		   onclick="javascript:editCatProps();">栏目属性</a> 
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
		   onclick="javascript:addNew();">新增</a>  
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
		   onclick="javascript:editSelected();">修改</a> 
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-submit'"
		   onclick="javascript:submitSelected();">提交</a>
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-process'"
		   onclick="javascript:viewProcess();">流程</a>
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'"
		   onclick="javascript:deleteSelections();">删除</a> 
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'"
		   onclick="javascript:searchWin();">查找</a>
		   <span>
		      状态&nbsp;
			  <select id="workedProcessFlag" name="workedProcessFlag" onchange="javascript:switchTasks();">
				<option value="">--请选择--</option>
				<option value="DF">未提交</option>
				<option value="PD">待审</option>
				<option value="WD">已审</option>
				<option value="END">已完成</option>
			  </select>
		   </span>
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

<div id="dd_audit" class="easyui-dialog" style="padding:5px;width:400px;height:240px;"
			title="审批意见" iconCls="icon-ok"
			buttons="#dlg2-buttons">
	<form id="auditForm" name="auditForm" method="post">
	    <input type="hidden" id="isAgree" name="isAgree" value="true">
		<textarea id="opinion" name="opinion" rows="7" cols="42" style="padding:5px;width:365px;height:150px;"></textarea>
    </form>
</div>
<div id="dlg2-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:completeTask('true')">通过</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:completeTask('false')">不通过</a>
	<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dd_audit').dialog('close')">取消</a> -->
</div>

</body>
</html>
