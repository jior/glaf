<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_config.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>seal</title>
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
	var contextPath = "${contextPath}";
	var areaRole =<%=request.getParameter("areaRole")%>;

	jQuery(function() {
		jQuery('#mydatagrid').datagrid({
			width : 1000,
			height : 400,
			fit : true,
			fitColumns : true,
			nowrap : true,
			striped : true,
			collapsible : true,
			url : '${contextPath}/mx/oa/seal/json?areaRole=' + areaRole,
			remoteSort : false,
			singleSelect : true,
			idField : 'sealid',
			columns : [ [ {
				title : '序号',
				field : 'startIndex',
				width : 80

			}, {
				title : '申请单位',
				field : 'company',
				width : 200,
				formatter : formatter4
			}, {
				title : '申请人',
				field : 'appuser',
				width : 120,
				formatter : formatter4
			}, {
				title : '申请印章类型',
				field : 'sealtype',
				width : 150,
				formatter : formatter4
			}, {
				title : '内容',
				field : 'content',
				width : 120,
				formatter : formatter4
			}, {
				title : '份数',
				field : 'num',
				width : 120,
				formatter : formatter4
			}, {
				title : '申请日期',
				field : 'appdate',
				width : 120
			}, {
				title : '状态',
				field : 'status',
				width : 120,
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
		var link = '${contextPath}/mx/oa/seal/edit?sealid=' + row.sealid;
		art.dialog.open(link, {
			height : 420,
			width : 680,
			title : "修改记录",
			lock : true,
			scrollbars : "no"
		}, false);
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
		var s = "<a href='#' onclick='print(" + row.sealid + ");'>打印</a> ";
		var c = "";
		var d = "";
		if (row.processinstanceid != null) {
			c = "<a href='#' onclick='viewSelected(" + row.sealid
					+ ")'>查看</a> ";
			d = "<a href='#' onclick='viewProc(" + row.processinstanceid
					+ ")'>流程</a> ";
		}
		return s + c + d;
	}

	function print(id) {
		window.location = '${contextPath}/mx/oa/reports/exportSeal?sealid='
				+ id;
	}

	function viewProc(processinstanceid) {
		window.open('${contextPath}/mx/jbpm/task/task?processInstanceId='
				+ processinstanceid);
	}

	function viewSelected(id) {
		var link = "${contextPath}/mx/oa/seal/review?sealid=" + id
				+ "&lookover=true";
		art.dialog.open(link, {
			height : 450,
			width : 800,
			title : "查看记录",
			lock : true,
			scrollbars : "no"
		}, false);
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
					class="x_content_title">印章申请查看列表</span>
			</div>

			<div style="margin: 0;">
				<form id="searchForm" name="searchForm" method="post">
					<table class="easyui-form" width="85%">
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
								<td>部门</td>
								<td><input id="deptN" class="easyui-combobox" name="dept"
									data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'" />
								</td>

								<td>申请人</td>
								<td><input id="appuser" class="easyui-combobox"
									name="appuser"
									data-options="valueField:'code',textField:'name',url:'${contextPath}/mx/oa/baseData/getUserJson'" />
								</td>
								<td>印章类型</td>
								<td><input id="sealtypeLike" class="easyui-combobox"
									name="sealtypeLike" readonly
									data-options="valueField:'code',textField:'name',editable:false,url:'${contextPath}/rs/dictory/jsonArray/seal'" />
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
									validType="isDate['','yyyy-MM-dd']"
									name="appdateLessThanOrEqual" class="easyui-datebox"></input></td>

								<td align="left"><a href="#" class="easyui-linkbutton"
									iconCls="icon-ok" onclick="javascript:searchData();">查询</a></td>
							</tr>

						</tbody>
					</table>
				</form>
			</div>
		</div>
		<div data-options="region:'center',border:true">
			<table id="mydatagrid"></table>
		</div>
	</div>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>