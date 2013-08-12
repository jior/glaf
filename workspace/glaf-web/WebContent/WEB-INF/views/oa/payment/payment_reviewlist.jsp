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
		jQuery('#mydatagrid').datagrid({
			width : 1000,
			height : 480,
			fit : true,
			fitColumns : true,
			nowrap : true,
			striped : true,
			collapsible : true,
			url : '${contextPath}/mx/oa/payment/getReviewList',
			sortName : 'id',
			sortOrder : 'desc',
			remoteSort : false,
			singleSelect : false,
			idField : 'paymentid',
			columns : [ [ {
				title : '序号',
				field : 'startIndex',
				width : 80,
				sortable : false
			}, {
				title : '凭证号',
				field : 'certificateno',
				width : 120,
				sortable : false,
				formatter : formatter4
			}, {
				title : '地区',
				field : 'area',
				width : 120,
				sortable : false,
				formatter : formatter4
			}, {
				title : '单位',
				field : 'company',
				width : 200,
				sortable : false,
				formatter : formatter4
			}, {
				title : '申请部门',
				field : 'dept',
				width : 120,
				sortable : false,
				formatter : formatter4
			}, {
				title : '申请金额',
				field : 'appsum',
				width : 120,
				sortable : false,
				formatter : formatter4
			}, {
				title : '用途',
				field : 'use',
				width : 120,
				sortable : false,
				formatter : formatter4
			}, {
				title : '预算编号',
				field : 'budgetno',
				width : 120,
				sortable : false,
				formatter : formatter4
			}, {
				title : '申请日期',
				field : 'appdate',
				width : 120,
				sortable : false
			}, {
				title : '申请人',
				field : 'appuser',
				width : 120,
				sortable : false,
				formatter : formatter4
			}, {
				title : '状态',
				field : 'status',
				width : 120,
				sortable : false,
				formatter : formatter2
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

	function addNew() {
		location.href = "${contextPath}/mx/oa/payment/edit";
	}

	function onRowClick(rowIndex, row) {
		var link = '${contextPath}/mx/oa/payment/edit?paymentid=' + row.id;
		art.dialog.open(link, {
			height : 420,
			width : 680,
			title : "修改记录",
			lock : true,
			scrollbars : "no"
		}, false);
	}

	function searchWin() {
		jQuery('#dlg').dialog('open').dialog('setTitle', '付款申请查询');
	}

	function resize() {
		jQuery('#mydatagrid').datagrid('resize', {
			width : 800,
			height : 400
		});
	}

	function editSelected() {
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (rows == null || rows.length != 1) {
			alert("请选择其中一条记录。");
			return;
		}
		if (selected) {
			var link = "${contextPath}/mx/oa/payment/review?paymentid="
					+ selected.id;
			art.dialog.open(link, {
				height : 430,
				width : 900,
				title : "审核记录",
				lock : true,
				scrollbars : "no"
			}, false);
		}
	}

	function viewSelected(id) {
		var link = "${contextPath}/mx/oa/payment/review?lookover=true&paymentid="
				+ id;
		art.dialog.open(link, {
			height : 430,
			width : 900,
			title : "查看记录",
			lock : true,
			scrollbars : "no"
		}, false);
	}

	function deleteSelections() {
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
			if (row[i].status == 1 || row[i].status == 2) {
				alert("只能删除已保存与已退回的记录。");
				return;
			}
		}
		if (ids.length > 0) {
			if (confirm("数据删除后不能恢复，确定删除吗？")) {
				var paymentids = ids.join(',');
				jQuery.ajax({
					type : "POST",
					url : '${contextPath}/mx/oa/payment/delete?paymentids='
							+ paymentids,
					dataType : 'json',
					error : function(data) {
						alert('服务器处理错误！');
					},
					success : function(data) {
						if (data != null && data.message != null) {
							alert(data.message);
						} else {
							alert('操作成功！');
						}
						jQuery('#mydatagrid').datagrid('reload');
					}
				});
			}
		} else {
			alert("请选择至少一条记录。");
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
		clearSelections();
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

	function formatter3(value, row, index) {
		var s = "<a href='#' onclick='print(" + row.paymentid + ");'>打印</a> ";
		var c = "";
		var d = "";
		if (row.processinstanceid != null) {
			c = "<a href='#' onclick='viewSelected(" + row.paymentid
					+ ")'>查看</a> ";
			d = "<a href='#' onclick='viewProc(" + row.processinstanceid
					+ ")'>流程</a> ";
		}
		return s + c + d;
	}
	function formatter4(value, row, rowIndex) {
		return "<div style='width:100%' title=" + value + "><nobr>" + value
				+ "</nobr></div>";
	}

	function viewProc(processinstanceid) {
		window.open('${contextPath}/mx/jbpm/task/task?processInstanceId='
				+ processinstanceid);
	}
	
	function print(id) {
		window.location = '${contextPath}/mx/oa/reports/exportPayment?paymentid='
				+ id;
	}

	function submit() {
		var ids = [];
		var statuses = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].paymentid);
			statuses.push(rows[i].status);
			if (rows[i].status != 0) {
				alert("只能提交已保存的记录。");
				return;
			}
		}
		if (ids.length > 0 && ids.length == 1) {
			if (confirm("确定提交吗？")) {
				jQuery.ajax({
					type : "POST",
					url : '${contextPath}/mx/oa/payment/submit?paymentid='
							+ rows[0].paymentid,
					dataType : 'json',
					error : function(data) {
						alert('服务器处理错误！');
					},
					success : function(data) {
						if (data != null && data.message != null) {
							alert(data.message);
						} else {
							alert('操作成功！');
						}
						jQuery('#mydatagrid').datagrid('reload');
					}
				});
			}
		} else {
			alert("请选择其中一条记录。");
		}
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

	function passData() {
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].paymentid);
		}
		if (ids.length > 0) {
			if (confirm("确认审核通过吗？")) {
				var ids = ids.join(',');
				var params = jQuery('#approveOpinion').serialize();
				jQuery.ajax({
					type : "POST",
					url : '${contextPath}/mx/oa/payment/submit?paymentids='
							+ ids,
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
						jQuery('#mydatagrid').datagrid('reload');
						jQuery('#mydatagrid').datagrid('clearSelections');
					}
				});
				jQuery('#dlgApprove').dialog('close');
			}
		} else {
			alert("请选择至少一条记录。");
		}
	}
	
	
	function noPassData() {
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].paymentid);
		}
		if (ids.length > 0) {
			if (jQuery('#approveOpinion').val().trim() == "") {
				alert("审批不通过需填写审批意见");
				return;
			}
			if (confirm("确认审核不通过吗？")) {
				var params = jQuery('#approveOpinion').serialize();
				var ids = ids.join(',');
				jQuery.ajax({
					type : "POST",
					url : '${contextPath}/mx/oa/payment/rollback?paymentids='
							+ ids,
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
						jQuery('#mydatagrid').datagrid('reload');
						jQuery('#mydatagrid').datagrid('clearSelections');
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
				<img src="${contextPath}/images/window.png"> &nbsp; <span
					class="x_content_title">付款申请列表</span>
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
	<div id="dlg" class="easyui-dialog"
		style="width: 550px; height: 250px; padding: 10px 20px" closed="true"
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
						<td>单位</td>
						<td><input id="company" class="easyui-combobox"
							name="company" size='25' value=""
							data-options="valueField:'code',textField:'name',url:'${contextPath}/mx/oa/baseData/getAllCompanyJson'" />
						</td>


					</tr>
					<tr>
						<td>凭证号</td>
						<td><input id="certificatenoLike" name="certificatenoLike"
							type="text" class="easyui-validatebox" /></td>
						<td>预算编号</td>
						<td><input id="budgetnoLike" name="budgetnoLike" type="text"
							class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>申请部门</td>
						<td><input id="dept" class="easyui-combobox" name="dept"
							data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'" />
						</td>
						<td>申请人</td>
						<td><input id="appuser" class="easyui-combobox"
							name="appuser"
							data-options="valueField:'code',textField:'name',url:'${contextPath}/mx/oa/baseData/getUserJson'" />
						</td>
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

	<%@ include file="/WEB-INF/views/oa/common/approve_foot.jsp"%>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>