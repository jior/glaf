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
	var areaRole = ${areaRole};
	
	jQuery(function() {
		jQuery('#mydatagrid').datagrid({
			width : 1000,
			height : 480,
			fit : true,
			fitColumns : true,
			nowrap : true,
			striped : true,
			collapsible : true,
			url : '${contextPath}/mx/oa/payment/json?areaRole=' + areaRole,
			remoteSort : false,
			singleSelect : true,
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
		clearSelections();
	}

	function formatter2(value, row, rowIndex) {
		if ("0" == row.status) {
			return "保存";
		}
		if ("1" == row.status) {
			return "已提交";
		}
		if ("2" == row.status) {
			return "审批完";
		}
		if ("3" == row.status) {
			return "已退回";
		}
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

	function viewProc(processinstanceid) {
		window.open('${contextPath}/mx/jbpm/task/task?processInstanceId='
				+ processinstanceid);
	}

	function print(id) {
		window.location = '${contextPath}/mx/oa/reports/exportPayment?paymentid='
				+ id;
	}

	function formatter4(value, row, rowIndex) {
		return "<div style='width:100%' title=" + value + "><nobr>" + value
				+ "</nobr></div>";
	}
</script>
</head>
<body style="margin: 1px;">
	<div style="margin: 0;"></div>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 90px">
			<div class="toolbar-backgroud">
				<img src="${contextPath}/images/window.png"> &nbsp; <span
					class="x_content_title">付款申请查询列表</span>
			</div>
			<div style="margin: 0;">
				<form id="searchForm" name="searchForm" method="post">
					<table class="easyui-form" width="90%">
						<tbody>
							<tr>
								<td>地区</td>
								<td><input id="area" class="easyui-combobox" name="area"
									size="10" disabled value="${area}"
									data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/eara'" />
								</td>
								<script type="text/javascript">
									var flag =
								<%=request.getParameter("areaRole")%>
									;
									if (flag == 1) {
										jQuery('#area').removeAttr('disabled');
									}
								</script>
								<td>凭证号</td>
								<td><input id="certificatenoLike" name="certificatenoLike"
									type="text" class="easyui-validatebox" /></td>
								<td>预算编号</td>
								<td><input id="budgetnoLike" name="budgetnoLike"
									type="text" class="easyui-validatebox" /></td>
								<td>单位</td>
								<td><input id="company" class="easyui-combobox"
									name="company" value="" size="25"
									data-options="valueField:'code',textField:'name',url:'${contextPath}/mx/oa/baseData/getAllCompanyJson'" />
								</td>

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

								<td>申请日期</td>
								<td><input id="appdateGreaterThanOrEqual"
									validType="isDate['','yyyy-MM-dd']"
									name="appdateGreaterThanOrEqual" class="easyui-datebox"></input>
								</td>
								<td>至</td>
								<td><input id="appdateLessThanOrEqual"
									validType="isDate['','yyyy-MM-dd']"
									name="appdateLessThanOrEqual" class="easyui-datebox"></input></td>
								<td align="center"><a href="#" class="easyui-linkbutton"
									iconCls="icon-ok" onclick="javascript:searchData()">查询</a></td>
							</tr>

						</tbody>
					</table>
				</form>
			</div>
		</div>
		<div data-options="region:'center',border:true" style="height: 400px">
			<table id="mydatagrid"></table>
		</div>
	</div>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>