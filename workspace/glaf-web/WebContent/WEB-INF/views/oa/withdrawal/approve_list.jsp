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
				.datagrid({
							width : 1000,
							height : 480,
							fit : true,
							fitColumns : true,
							nowrap : false,
							striped : true,
							collapsible : true,
							url : '${contextPath}/mx/oa/withdrawalApprove/json',
							remoteSort : false,
							singleSelect : false,
							idField : 'withdrawalid',
							columns : [ [
									{
										title : '序号',
										field : 'startIndex',
										width : 40,
										sortable : false
									},
									{
										title : '申请单位',
										field : 'company',
										width : 180,
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
										title : '申请人',
										field : 'appuser',
										width : 120,
										formatter : function(value, row, index) {
											return row.appusername;
										}
									},
									{
										title : '职位',
										field : 'post',
										width : 120
									},
									{
										title : '申请金额',
										field : 'appsum',
										width : 120
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
													+ row.withdrawalid
													+ ")'>打印</a>   ";
											var c = "<a href='#' onclick='viewSelected("
													+ row.withdrawalid
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
		window.location = '${contextPath}/mx/oa/reports/exportWithDrawal?withdrawalid='
				+ id;
	}
	function showProcess(id) {
		var link = "${contextPath}/mx/jbpm/task/task?processInstanceId=" + id;
		window.open(link);
	}

	function searchWin() {
		jQuery('#dlg').dialog('open').dialog('setTitle', '取现申请审核列表查询');
		//jQuery('#searchForm').form('clear');
	}

	function resize() {
		jQuery('#mydatagrid').datagrid('resize', {
			width : 800,
			height : 400
		});
	}

	function viewSelected(id) {
		var link = "${contextPath}/mx/oa/withdrawal/view?withdrawalid=" + id;
		art.dialog.open(link, {
			height : 420,
			width : 720,
			title : "查看记录",
			lock : true,
			scrollbars : "no"
		}, false);
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
			jQuery('#mydatagrid').datagrid('loadData', data);
		}, 'json');
	}

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

	//弹出审核页面
	function approveWin() {
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
			var link = "${contextPath}/mx/oa/withdrawalApprove/approve?withdrawalids="
					+ selected.id;
			art.dialog.open(link, {
				height : 420,
				width : 720,
				title : "取现申请审核",
				lock : true,
				scrollbars : "no"
			}, false);
		}
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
				var withdrawalids = ids.join(',');
				jQuery
						.ajax({
							type : "POST",
							url : '${contextPath}/mx/oa/withdrawalApprove/approveData?withdrawalids='
									+ withdrawalids + '&isAgree=isAgree',
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
				var withdrawalids = ids.join(',');
				jQuery
						.ajax({
							type : "POST",
							url : '${contextPath}/mx/oa/withdrawalApprove/approveData?withdrawalids='
									+ withdrawalids,
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
</script>
</head>
<body style="margin: 1px;">
	<div style="margin: 0;"></div>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<img src="${contextPath}/images/window.png"> &nbsp;<span
					class="x_content_title">取现申请审核列表</span> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-search'"
					onclick="javascript:searchWin();">查找</a> <a href="#"
					class="easyui-linkbutton" id="approve_"
					data-options="plain:true, iconCls:'icon-submit'"
					onclick="javascript:approveWin();">审核</a> <a href="#"
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
						<td>地区</td>
						<td><input id="area" class="easyui-combobox" name="area"
							size="12" value="${withdrawal.area}"
							data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/eara',
					onSelect:function(ret){										
					var url = '${contextPath}/rs/dictory/jsonArray/'+ret.code;
						jQuery('#company').combobox('clear');
						jQuery('#company').combobox('reload',url);
						jQuery('#area').val(ret.code);
					}																	
					" />
						</td>
						<td>单位</td>
						<td><input id="company" class="easyui-combobox"
							name="company" size="12" readonly="readonly"
							data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/${withdrawal.area}'" />
						</td>
					</tr>
					<tr>
						<td>申请部门</td>
						<td><input id="dept" class="easyui-combobox" name="dept"
							size="12"
							data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'" />
						</td>
						<td>申请人</td>
						<td><input id="appuser" class="easyui-combobox"
							name="appuser" size="12"
							data-options="valueField:'code',textField:'name',url:'${contextPath}/mx/oa/baseData/getUserJson'" />
						</td>
					</tr>
					<tr>
						<td>职位</td>
						<td><input id="post" class="easyui-combobox" name="post"
							size="12"
							data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/UserHeadship'" />
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
							name="appdateGreaterThanOrEqual" class="easyui-datebox" size="12"></input>
						</td>
						<td>至</td>
						<td><input id="appdateLessThanOrEqual"
							validType="isDate['','yyyy-MM-dd']" name="appdateLessThanOrEqual"
							class="easyui-datebox" size="12"></input></td>
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
