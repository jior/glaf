<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_config.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=appTitle%></title>
<meta name="Keywords" content="<%=appKeywords%>" />
<meta name="Description" content="<%=appDescription%>" />
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>

<script type="text/javascript">
	var contextPath = "${contextPath}";

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
							url : '${contextPath}/mx/oa/purchaseApprove/json',
							sortName : 'id',
							sortOrder : 'desc',
							remoteSort : false,
							singleSelect : false,
							idField : 'purchaseId',
							columns : [ [
									{
										title : '序号',
										field : 'startIndex',
										width : 80,
										sortable : false
									},
									{
										title : '采购编号',
										field : 'purchaseno',
										width : 120
									},
									{
										title : '地区',
										field : 'area',
										width : 120,
										formatter : function(value, row, index) {
											return row.areaname;
										}
									},
									{
										title : '单位',
										field : 'company',
										width : 210,
										formatter : function(value, row, index) {
											return row.companyname;
										}
									},
									{
										title : '申请部门',
										field : 'dept',
										width : 120,
										formatter : function(value, row, index) {
											return row.deptname;
										}
									},
									{
										title : '申请金额',
										field : 'purchasesum',
										width : 120
									},
									{
										title : '申请人',
										field : 'appuser',
										width : 140,
										formatter : function(value, row, index) {
											return row.appusername;
										}
									},
									{
										title : '申请日期',
										field : 'appdate',
										width : 120
									},
									{
										title : '状态',
										field : 'wdStatusFlag',
										width : 120,
										formatter : function(value, row, index) {
											if (row.wdStatusFlag == null) {
												return "未审核";
											} else {
												return "已审核";
											}
										}
									},
									{
										field : 'functionKey',
										title : '功能键',
										width : 120,
										formatter : function(value, row, index) {
											var s = "<a href='#' onclick='print("
													+ row.purchaseid
													+ ")'>打印</a> ";
											var c = "<a href='#' onclick='viewSelected("
													+ row.purchaseid
													+ ")'>查看</a> ";
											var v = "<a href='#' onclick='showProcess("
													+ row.processinstanceid
													+ ")'>流程</a> ";
											return s + c + v;
										}
									}, ] ],
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
	
	
	function print(id) {
		window.location = '${contextPath}/mx/oa/reports/exportPurchase?purchaseId='
				+ id;
	}
	
	
	function showProcess(id) {
		var link = "${contextPath}/mx/jbpm/task/task?processInstanceId=" + id;
		window.open(link);
	}

	//弹出查询窗口
	function searchWin() {
		jQuery('#dlg').dialog('open').dialog('setTitle', '采购申请审核查询');
		//jQuery('#searchForm').form('clear');
	}
	
	
	//弹出批量审核窗口
	function approveBatchWin() {
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			if (rows[i].wdStatusFlag > 0) {
				alert("只能对未审核的记录进行操作。");
				return;
			}
		}
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		if (ids.length > 0) {
			//jQuery('#dlgApprove').dialog('open').dialog('setTitle','批量审核');
			$('#dlgApprove').dialog('open').dialog({
				title : '批量审核',
				closed : false,
				modal : true
			});
			jQuery('#dlgApprove').form('clear');
		} else {
			alert("请选择至少一条记录。");
		}
	}

	function resize() {
		jQuery('#mydatagrid').datagrid('resize', {
			width : 800,
			height : 400
		});
	}

	//弹出审核页面
	function approve() {
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if (rows == null || rows.length != 1) {
			alert("请选择其中一条记录。");
			return;
		}
		for ( var i = 0; i < rows.length; i++) {
			if (rows[i].wdStatusFlag > 0) {
				alert("只能对未审核的记录进行操作。");
				return;
			}
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected) {
			var link = "${contextPath}/mx/oa/purchaseApprove/approve?purchaseId="
					+ selected.id;
			art.dialog.open(link, {
				height : 420,
				width : 760,
				title : "采购申请审核",
				lock : true,
				scrollbars : "no"
			}, false);
		}
	}

	//查看
	function viewSelected(id) {
		var link = "${contextPath}/mx/oa/purchase/view?purchaseId=" + id;
		art.dialog.open(link, {
			height : 420,
			width : 760,
			title : "查看记录",
			lock : true,
			scrollbars : "no"
		}, false);
	}

	//批量审核---通过
	function passData() {
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		var params = jQuery('#approveOpinion').serialize();
		if (ids.length > 0) {
			if (confirm("确认审核通过吗？")) {
				var purchaseIds = ids.join(',');
				jQuery
						.ajax({
							type : "POST",
							url : '${contextPath}/mx/oa/purchaseApprove/approveData?purchaseIds='
									+ purchaseIds + '&isAgree=isAgree',
							dataType : 'json',
							data : params,
							error : function(data) {
								alert('服务器处理错误！');
							},
							success : function(data) {
								if (data != null && data.message != null) {
									alert(data.message);
								} else {
									alert('操作成功！');
								}
								jQuery('#mydatagrid').datagrid(
										'clearSelections');
								jQuery('#mydatagrid').datagrid('reload');
							}
						});
				jQuery('#dlgApprove').dialog('close');
			}
		} else {
			alert("请选择至少一条记录。");
		}
	}

	//批量审核---不通过
	function noPassData() {
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		var params = jQuery('#approveOpinion').serialize();
		if (ids.length > 0) {
			if (jQuery('#approveOpinion').val().trim() == "") {
				alert("审核不通过需填写审核意见。");
				return;
			}
			if (confirm("确认审核不通过吗？")) {
				var purchaseIds = ids.join(',');
				jQuery
						.ajax({
							type : "POST",
							url : '${contextPath}/mx/oa/purchaseApprove/approveData?purchaseIds='
									+ purchaseIds,
							dataType : 'json',
							data : params,
							error : function(data) {
								alert('服务器处理错误！');
							},
							success : function(data) {
								if (data != null && data.message != null) {
									alert(data.message);
								} else {
									alert('操作成功！');
								}
								jQuery('#mydatagrid').datagrid(
										'clearSelections');
								jQuery('#mydatagrid').datagrid('reload');
							}
						});
				jQuery('#dlgApprove').dialog('close');
			}
		} else {
			alert("请选择至少一条记录。");
		}
	}

	//重载
	function reloadGrid() {
		jQuery('#mydatagrid').datagrid('reload');
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

	//查询
	function searchData() {
		var status = jQuery('#status').combobox('getValue');
		if (status == 2) {
			jQuery('#approve_').attr('style', 'display: none;');
			jQuery('#approve_batch').attr('style', 'display: none;');
		} else {
			jQuery('#approve_').removeAttr('style');
			jQuery('#approve_batch').removeAttr('style');
		}
		jQuery('#mydatagrid').datagrid('clearSelections');

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
				<img src="${contextPath}/images/window.png"> &nbsp; <span
					class="x_content_title">采购申请审核列表</span> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-search'"
					onclick="javascript:searchWin();">查找</a> <a href="#"
					class="easyui-linkbutton" id="approve_"
					data-options="plain:true, iconCls:'icon-submit'"
					onclick="javascript:approve();">审核</a> <a href="#"
					class="easyui-linkbutton" id="approve_batch"
					data-options="plain:true, iconCls:'icon-submit'"
					onclick="javascript:approveBatchWin();">批量审核</a>
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
						<td>采购编号</td>
						<td><input id="purchaseNoLike" name="purchaseNoLike"
							class="easyui-validatebox" type="text" size="12"></input></td>
						<td>申请部门</td>
						<td><input id="dept" class="easyui-combobox" name="dept"
							size="12"
							data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'" />
						</td>
					</tr>
					<tr>

						<td>地区</td>
						<td><input id="area" class="easyui-combobox" name="area"
							size="12" value="${purchase.area}"
							data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/eara',
					onSelect:function(ret){										
					var url = '${contextPath}/rs/dictory/jsonArray/'+ret.code;
						jQuery('#company').combobox('clear');
						jQuery('#company').combobox('reload',url);
						jQuery('#area').val(ret.code);
					}																	
					" /></td>
						<td>单位</td>
						<td><input id="company" class="easyui-combobox"
							name="company" size="12" readonly="readonly"
							data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/${purchase.area}'"
							value="${purchase.company}" /></td>
					</tr>
					<tr>
						<td>申请人</td>
						<td><input id="appuser" class="easyui-combobox"
							name="appuser" value="${purchase.appuser}" size="12"
							data-options="valueField:'code',textField:'name',url:'${contextPath}/mx/oa/baseData/getUserJson'" />
						</td>
						<td>状态</td>
						<td><select id="status" name="status" class="easyui-combobox">
								<option value="1">未审核</option>
								<option value="2">已审核</option>
						</select></td>
					</tr>
					<tr>
						<td>申请日期</td>
						<td><input id="appdateGreaterThanOrEqual"
							validType="isDate['','yyyy-MM-dd']"
							name="appdateGreaterThanOrEqual" class="easyui-datebox" size="12"></input></td>
						<td>至</td>
						<td><input id="appdateLessThanOrEqual"
							validType="isDate['','yyyy-MM-dd']" name="appdateLessThanOrEqual"
							class="easyui-datebox" size="12"></input></td>
					</tr>
					<tr>
					</tr>
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
	<%@ include file="/WEB-INF/views/oa/common/approve_foot.jsp"%>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>