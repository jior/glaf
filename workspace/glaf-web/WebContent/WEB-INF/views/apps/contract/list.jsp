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
<title>合同</title>
<link href="<%=request.getContextPath()%>/scripts/artDialog/skins/default.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
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
				url:'<%=request.getContextPath()%>/apps/contract.do?method=json',
				sortName: 'id',
				sortOrder: 'desc',
				remoteSort: false,
				singleSelect:true,
				idField:'id',
				columns:[[
	                {title:'编号',field:'id',width:80,sortable:true},
					{title:'合同名称',field:'contactName', width:120},
					{title:'项目名称',field:'projrctName', width:120},
					{title:'我方签约单位',field:'companyName', width:120},
					{title:'对方签约单位',field:'suppliserName', width:120},
					{title:'结算币种',field:'currency', width:120},
					{title:'合同金额',field:'contractSum', width:120},
					{title:'付款方式',field:'payType', width:120},
					{title:'状态',field:'status', width:120},
					{title:'申请人',field:'appUser', width:120},
					{title:'申请时间',field:'appDate', width:120} 
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

		 
	function addNew(){
	    //location.href="<%=request.getContextPath()%>/apps/contract.do?method=edit";
	    var link="<%=request.getContextPath()%>/apps/contract.do?method=edit";
	    art.dialog.open(link, { height: 420, width: 680, title: "添加记录", lock: true, scrollbars:"no" }, false);
	}

	function onRowClick(rowIndex, row){
            //window.open('<%=request.getContextPath()%>/apps/contract.do?method=edit&id='+row.id);
	    var link = '<%=request.getContextPath()%>/apps/contract.do?method=edit&id='+row.id;
	    art.dialog.open(link, { height: 420, width: 680, title: "修改记录", lock: true, scrollbars:"no" }, false);
	}

	function searchWin(){
	    jQuery('#dlg').dialog('open').dialog('setTitle','合同查询');
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
		//location.href="<%=request.getContextPath()%>/apps/contract.do?method=edit&id="+selected.id;
		var link = "<%=request.getContextPath()%>/apps/contract.do?method=edit&id="+selected.id;
		art.dialog.open(link, { height: 420, width: 680, title: "修改记录", lock: true, scrollbars:"no" }, false);
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
		    location.href="<%=request.getContextPath()%>/apps/contract.do?method=edit&readonly=true&id="+selected.id;
		}
	}

	function deleteSelections(){
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].id);
		}
		if(ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")){
		    var ids = ids.join(',');
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/apps/contract.do?method=delete&ids='+ids,
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

	function loadGridData(url){
		  jQuery.post(url,{qq:'xx'},function(data){
		      var text = JSON.stringify(data); 
              alert(text);
			  jQuery('#mydatagrid').datagrid('loadData', data);
		  },'json');
	  }

	function searchData(){
	    var params = jQuery("#searchForm").formSerialize();
	    jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/apps/contract.do?method=json',
				   dataType:  'json',
				   data: params,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					  jQuery('#mydatagrid').datagrid('loadData', data);
				   }
			 });

	    jQuery('#dlg').dialog('close');
	}
		 
</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"  > 
	<img src="<%=request.getContextPath()%>/images/window.png">
	&nbsp;<span class="x_content_title">合同列表</span>
    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
	   onclick="javascript:addNew();">新增</a>  
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
    <tr>
	<td>合同名称</td>
	<td>
        <input id="contactNameLike" name="contactNameLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    <tr>
	<td>项目名称</td>
	<td>
        <input id="projrctNameLike" name="projrctNameLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    <tr>
	<td>我方签约单位</td>
	<td>
        <input id="companyNameLike" name="companyNameLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    <tr>
	<td>对方签约单位</td>
	<td>
        <input id="suppliserNameLike" name="suppliserNameLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    <tr>
	<td>结算币种</td>
	<td>
	<input id="currency" name="currency" class="easyui-numberbox" precision="0" ></input>
       </td>
     </tr>
    <tr>
	<td>合同金额</td>
	<td>
        <input id="contractSumLike" name="contractSumLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    <tr>
	<td>付款方式</td>
	<td>
	<input id="payType" name="payType" class="easyui-numberbox" precision="0" ></input>
       </td>
     </tr>
    <tr>
	<td>备注</td>
	<td>
        <input id="remarksLike" name="remarksLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    <tr>
	<td>附件</td>
	<td>
        <input id="attachmentLike" name="attachmentLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    <tr>
	<td>状态</td>
	<td>
	<input id="status" name="status" class="easyui-numberbox" precision="0" ></input>
       </td>
     </tr>
    <tr>
	<td>申请人</td>
	<td>
        <input id="appUserLike" name="appUserLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    <tr>
	<td>申请时间</td>
	<td>
	<input id="appDateLessThanOrEqual" name="appDateLessThanOrEqual" class="easyui-datebox"></input>
       </td>
     </tr>
    <tr>
	<td>修改人</td>
	<td>
        <input id="upUserLike" name="upUserLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    <tr>
	<td>修改时间</td>
	<td>
	<input id="upDatesLessThanOrEqual" name="upDatesLessThanOrEqual" class="easyui-datebox"></input>
       </td>
     </tr>
    <tr>
	<td>删除标识</td>
	<td>
	<input id="deleteFlag" name="deleteFlag" class="easyui-numberbox" precision="0" ></input>
       </td>
     </tr>
    <tr>
	<td>创建人</td>
	<td>
        <input id="createByLike" name="createByLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
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
