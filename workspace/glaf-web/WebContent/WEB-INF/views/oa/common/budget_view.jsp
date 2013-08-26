<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_config.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支出预算选择</title>
<meta name="Keywords" content="<%=appKeywords%>" />
<meta name="Description" content="<%=appDescription%>" />
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>

<script type="text/javascript">

   var contextPath='${contextPath}';
   var area = '<%=request.getParameter("area")%>' ;
   var appuser = '<%=request.getParameter("appuser")%>';
   
	jQuery(function() {
		jQuery('#mydatagrid')
				.datagrid(
						{
							width : 1000,
							height : 480,
							fit : true,
							fitColumns : true,
							nowrap : false,
							striped : true,
							collapsible : true,
							url : '${contextPath}/mx/oa/budgetView/json?area='
									+ area + '&appuser=' + appuser,
							sortName : 'id',
							sortOrder : 'desc',
							remoteSort : false,
							singleSelect : true,
							idField : 'budgetid',
							columns : [ [
									{
										title : '序号',
										field : 'startIndex',
										width : 80,
										sortable : false
									},
									{
										title : '预支编号',
										field : 'budgetno',
										width : 80
									},
									{
										title : '项目名称',
										field : 'proname',
										width : 120
									},
									{
										title : '预支时间',
										field : 'appdate',
										width : 80
									},
									{
										title : '预支金额',
										field : 'budgetsum',
										width : 80
									},
									{
										title : '币种',
										field : 'currency',
										width : 60,
										formatter : function(value, row, index) {
											var currency = "";
											jQuery
													.ajax({
														type : "POST",
														url : '${contextPath}/rs/dictory/jsonArray/money',
														dataType : 'json',
														async : false,
														success : function(data) {
															if (data != null
																	&& data.message != null) {
																alert(data.message);
															} else {
																for ( var i = 0; i < data.length; i++) {
																	if (data[i].code == value) {
																		currency = data[i].name;
																	}
																}
															}
														}
													});
											return currency;
										}
									},
									{
										field : 'functionKey',
										title : '功能键',
										align : 'center',
										width : 60,
										formatter : function(value, row, index) {
											var s = "<a href='#' class='easyui-linkbutton' onclick='selectrow("
													+ index + ")'>选择</a> ";
											return s;
										}
									} ] ],
							rownumbers : false,
							pagination : true,
							pageSize : 15,
							pageList : [ 10, 15, 20, 25, 30, 40, 50, 100 ]
						});

		var p = jQuery('#mydatagrid').datagrid('getPager');
		jQuery(p).pagination({
			onBeforeRefresh : function() {
				//alert('before refresh');
			}
		});
	});

	function selectrow(indexs) {
		$("#mydatagrid").datagrid("endEdit", indexs);
		$("#mydatagrid").datagrid("selectRow", indexs);
		var rows = $("#mydatagrid").datagrid("getSelections");
		var parent_window = getOpener();
		parent_window.fillBudget(rows[0].budgetno, rows[0].appdate,
				rows[0].budgetsum);
		window.close();
	}

	function searchWin() {
		jQuery('#dlg').dialog('open').dialog('setTitle', 'Budget查询');
		//jQuery('#searchForm').form('clear');
	}

	function resize() {
		jQuery('#mydatagrid').datagrid('resize', {
			width : 800,
			height : 400
		});
	}

	function reloadGrid() {
		jQuery('#mydatagrid').datagrid('reload');
	}

	function getSelected() {
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected) {
			alert(selected.code + ":" + selected.name + ":" + selected.addr
					+ ":" + selected.col4);
		}
	}

	function getSelections() {
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].code);
		}
		alert(ids.join(':'));
	}

	function clearSelections() {
		jQuery('#mydatagrid').datagrid('clearSelections');
	}

	function loadGridData(url) {
		jQuery.post(url, {
			qq : 'xx'
		}, function(data) {
			//var text = JSON.stringify(data); 
			//alert(text);
			jQuery('#mydatagrid').datagrid('loadData', data);
		}, 'json');
	}

	function searchData() {
		jQuery('#mydatagrid').datagrid('load',
				getMxObjArray(jQuery("#searchForm").serializeArray()));
		jQuery('#dlg').dialog('close');
	}
</script>
</head>
<body style="margin: 1px;">
	<div style="margin: 0;"></div>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<img src="${contextPath}/images/window.png"> &nbsp;<span
					class="x_content_title">支出预算选择</span>
				
				<a href="#" class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-search'"
					onclick="javascript:searchWin();">查找</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-cancel'"
					onclick="javascript:window.close();">关闭</a>
			</div>
		</div>
		<div data-options="region:'center',border:true">
			<table id="mydatagrid"></table>
		</div>
	</div>
	<div id="edit_dlg" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="editForm" name="editForm" method="post"></form>
	</div>
	<div id="dlg" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="searchForm" name="searchForm" method="post">
			<table class="easyui-form">
				<tbody>
					<tr>
						<td>预支编号</td>
						<td><input id="budgetnoLike" name="budgetnoLike"
							class="easyui-validatebox" type="text"></input></td>
					</tr>
					<tr>
						<td>项目名称</td>
						<td><input id="pronameLike" name="pronameLike"
							class="easyui-validatebox" type="text"></input></td>
					</tr>
					<tr>
						<td>预支时间</td>
						<td><input id="appdateGreaterThanOrEqual"
							name="appdateGreaterThanOrEqual"
							validType="isDate['','yyyy-MM-dd']" class="easyui-datebox"></input>
						</td>
					</tr>
					<tr>
						<td>至</td>
						<td><input id="appdateLessThanOrEqual"
							name="appdateLessThanOrEqual" validType="isDate['','yyyy-MM-dd']"
							class="easyui-datebox"></input></td>
					</tr>
					<tr>
				</tbody>
			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="javascript:searchData()">查询</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:jQuery('#dlg').dialog('close')">取消</a>
	</div>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>