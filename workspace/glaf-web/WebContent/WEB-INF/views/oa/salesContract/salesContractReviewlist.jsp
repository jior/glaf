<%@ page contentType="text/html;charset=UTF-8"%>
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
<title>合同申请审核</title>
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
	var contextPath = "${contextPath}";

	jQuery(function() {
		jQuery('#mydatagrid').datagrid({
			width : 1000,
			height : 480,
			fit : true,
			fitColumns : true,
			nowrap : true,
			striped : true,
			collapsible : true,
			url : '${contextPath}/mx/oa/salescontract/getReviewSalesContract',
			sortName : 'id',
			sortOrder : 'desc',
			remoteSort : false,
			singleSelect : false,
			idField : 'id',
			columns : [ [ {
				title : '序号',
				field : 'startIndex',
				width : 60,
				sortable : false
			}, {
				title : '合同名称',
				field : 'contactname',
				width : 120,
				formatter : formatter4
			}, {
				title : '合同编号',
				field : 'contractno',
				width : 120,
				formatter : formatter4
			}, {
				title : '项目名称',
				field : 'projrctname',
				width : 120,
				formatter : formatter4
			}, {
				title : '我方签约单位',
				field : 'companyname',
				width : 200,
				formatter : formatter4
			}, {
				title : '对方签约单位',
				field : 'supplisername',
				width : 200,
				formatter : formatter4
			}, {
				title : '合同金额',
				field : 'contractsum',
				width : 120,
				formatter : formatter4
			}, {
				title : '状态',
				field : 'status',
				width : 80,
				formatter : formatter2
			}, {
				title : '申请人',
				field : 'appuser',
				width : 120,
				formatter : formatter4
			}, {
				title : '申请日期',
				field : 'appdate',
				width : 120
			}, {
				field : 'functionKey',
				title : '功能键',
				width : 120,
				formatter : formatter3
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

	function formatter4(value, row, rowIndex) {
		return "<div style='width:100%' title=" + value + "><nobr>" + value
				+ "</nobr></div>";
	}
	
	
	function formatter3(value, row, index) {
		var s = "<a href='#' onclick='print(" + row.id + ");'>打印</a> ";
		var c = "";
		var d = "";
		if (row.processinstanceid != null) {
			c = "<a href='#' onclick='viewSelected(" + row.id + ")'>查看</a> ";
			d = "<a href='#' onclick='viewProc(" + row.processinstanceid
					+ ")'>流程</a> ";

		}
		return s + c + d;
	}
	
	
	function print(id) {
		window.location = '${contextPath}/mx/oa/reports/exportSalesContract?id='
				+ id;
	}

	function addNew() {
		var link = "${contextPath}/mx/oa/salescontract/edit";
		art.dialog.open(link, {
			height : 500,
			width : 900,
			title : "添加记录",
			lock : true,
			scrollbars : "no"
		}, false);
	}
	

	function onRowClick(rowIndex, row) {
		var link = '${contextPath}/mx/oa/salescontract/review?id=' + row.id
				+ '&lookover=true';
		art.dialog.open(link, {
			height : 500,
			width : 900,
			title : "合同申请查看",
			lock : true,
			scrollbars : "no"
		}, false);
	}
	

	function searchWin() {
		jQuery('#dlg').dialog('open').dialog('setTitle', '合同申请审核查询');
	}

	function resize() {
		jQuery('#mydatagrid').datagrid('resize', {
			width : 800,
			height : 400
		});
	}

	function editSelected() {
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if (rows == null || rows.length != 1) {
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected) {
			var link = "${contextPath}/mx/oa/salescontract/review?id="
					+ selected.id;
			art.dialog.open(link, {
				height : 440,
				width : 900,
				title : "审核记录",
				lock : true,
				scrollbars : "no"
			}, false);
		}
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
		if (jQuery("#workedProcessFlag").val() == 'PD') {
			jQuery("#reviewDiv").hide();
		} else {
			jQuery("#reviewDiv").show();
		}
		jQuery('#mydatagrid').datagrid('load',
				getMxObjArray(jQuery("#searchForm").serializeArray()));

		jQuery('#dlg').dialog('close');
		jQuery('#mydatagrid').datagrid('clearSelections');
	}

	function formatter2(value, row, rowIndex) {
		var ret = "未审核";
		if ($('#workedProcessFlag').val() != "") {
			if ($('#workedProcessFlag').val() == 'PD') {
				ret = "已审核";
			}
		}
		return ret;
	}

	function reviewBatch() {
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if (rows.length == 0) {
			alert("请选择至少一条记录。");
			return;
		}
		jQuery('#dlgApprove').dialog('open').dialog('setTitle', '批量审核');
		jQuery('#approveForm').form('clear');
	}
	
	
	function reviewZ(id) {
		salesID = id;
		jQuery('#dlgApprove').dialog('open').dialog('setTitle', '审核');
		jQuery('#approveForm').form('clear');
	}

	function passData() {
		var approveOpinion1 = jQuery('#approveOpinion').val();
		var approveOpinion = $.trim(approveOpinion1);
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		if (ids.length > 0) {
			if (confirm("确认审核通过吗？")) {
				var x_ids = ids.join(',');
				var params = jQuery('#approveOpinion').serialize();
				jQuery.ajax({
					type : "POST",
					url : '${contextPath}/mx/oa/salescontract/submit?ids='
							+ x_ids,
					dataType : 'json',
					data : params,
					error : function(data) {
						alert('服务器处理错误！');
					},
					success : function(data) {
						if (data != null && data.message != null) {
							alert(data.message);
						} else {
							alert('操作成功完成！');
						}
						jQuery('#mydatagrid').datagrid('clearSelections');
						jQuery('#mydatagrid').datagrid('reload');
					}
				});
			}
		} else {
			alert("请选择至少一条记录。");
		}
		jQuery('#dlgApprove').dialog('close');
	}
	
	
	function noPassData() {
		var approveOpinion1 = jQuery('#approveOpinion').val();
		var approveOpinion = $.trim(approveOpinion1);
		if (approveOpinion == "") {
			alert("不通过必须填写审批意见");
			return;
		}
		var params = jQuery('#approveOpinion').serialize();

		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		if (ids.length > 0) {
			if (confirm("确认审核不通过吗？")) {
				var x_ids = ids.join(',');
				jQuery
						.ajax({
							type : "POST",
							url : '${contextPath}/mx/oa/salescontract/rollbackData?ids='
									+ x_ids,
							dataType : 'json',
							data : params,
							error : function(data) {
								alert('服务器处理错误！');
							},
							success : function(data) {
								if (data != null && data.message != null) {
									alert(data.message);
								} else {
									alert('操作成功完成！');
								}
								jQuery('#mydatagrid').datagrid(
										'clearSelections');
								jQuery('#mydatagrid').datagrid('reload');
							}
						});
			}
		} else {
			alert("请选择至少一条记录。");
		}
		jQuery('#dlgApprove').dialog('close');
	}
	
	
	function viewcancle() {
		jQuery('#dlgApprove').dialog('close');
		salesID = 0;
	}

	
	function viewSelected(id) {
		var link = '${contextPath}/mx/oa/salescontract/review?id=' + id
				+ '&lookover=true';
		art.dialog.open(link, {
			height : 440,
			width : 900,
			title : "查看记录",
			lock : true,
			scrollbars : "no"
		}, false);
	}

	function viewProc(processinstanceid) {
		window.open('${contextPath}/mx/jbpm/task/task?processInstanceId='
				+ processinstanceid);
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
					class="x_content_title">合同申请审核列表</span>
				<div style="display: inline" id="searchDiv">
					<a href="#" class="easyui-linkbutton"
						data-options="plain:true, iconCls:'icon-search'"
						onclick="javascript:searchWin();">查找</a>
				</div>
				<div style="display: inline" id="reviewDiv">
					<a href="#" class="easyui-linkbutton"
						data-options="plain:true, iconCls:'icon-submit'"
						onclick="javascript:editSelected();">审核</a> <a href="#"
						class="easyui-linkbutton"
						data-options="plain:true, iconCls:'icon-submit'"
						onclick="javascript:reviewBatch();">批量审核</a>
				</div>

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
		style="width: 500px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="searchForm" name="searchForm" method="post">
			<table class="easyui-form">
				<tbody>
					<tr>
						<td>地区</td>
						<td><input id="area" class="easyui-combobox" name="area"
							size="10"
							data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/eara'" />
						</td>
						<td>申请人</td>
						<td><input id="appuser" class="easyui-combobox"
							name="appuser"
							data-options="valueField:'code',textField:'name',url:'${contextPath}/mx/oa/baseData/getUserJson'" />
						</td>

					</tr>
					<tr>
						<td>合同编号</td>
						<td><input id="contractnoLike" name="contractnoLike"
							class="easyui-validatebox" type="text"></input></td>
						<td>合同名称</td>
						<td><input id="contactnameLike" name="contactnameLike"
							class="easyui-validatebox" type="text"></input></td>
					</tr>
					<tr>
						<td>项目名称</td>
						<td><input id="projrctnameLike" name="projrctnameLike"
							class="easyui-validatebox" type="text"></input></td>
					</tr>
					<tr>
						<td>申请日期</td>
						<td><input id="appdateGreaterThanOrEqual"
							validType="isDate['','yyyy-MM-dd']"
							name="appdateGreaterThanOrEqual" class="easyui-datebox"></input>
						</td>
						<td>至</td>
						<td><input id="appdateLessThanOrEqual"
							name="appdateLessThanOrEqual" validType="isDate['','yyyy-MM-dd']"
							class="easyui-datebox"></input></td>
					</tr>
					<tr>
						<td>状态</td>
						<td><select id="workedProcessFlag" name="workedProcessFlag"
							class="list">
								<option value="WD">未审核</option>
								<option value="PD">已审核</option>
						</select></td>
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

	<div id="dlgApprove" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlgApprove-buttons">
		<form id="approveForm" name="approveForm" method="post">
			<table class="easyui-form">
				<tbody>
					<tr>
						<td>审批意见：</td>
					</tr>
					<tr>
						<td><textarea id="approveOpinion" name="approveOpinion"
								rows="9" cols="40"></textarea></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div id="dlgApprove-buttons">
		<a href="#" class="easyui-linkbutton" onclick="javascript:passData()">通过</a>
		<a href="#" class="easyui-linkbutton"
			onclick="javascript:noPassData()">不通过</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:viewcancle()">取消</a>
	</div>
</body>
</html>
