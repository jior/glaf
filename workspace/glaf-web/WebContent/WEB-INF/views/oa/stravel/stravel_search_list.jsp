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
							url : '${contextPath}/mx/oa/stravel/searchJson?areaRole=${areaRole}',
							sortName : 'travelid',
							sortOrder : 'desc',
							remoteSort : false,
							singleSelect : true,
							idField : 'travelid',
							columns : [ [
									{
										title : '序号',
										field : 'startIndex',
										width : 80,
										sortable : false
									},
									{
										title : '地区',
										field : 'area',
										width : 200,
										sortable : false
									},
									{
										title : '单位',
										field : 'company',
										width : 200,
										sortable : false
									},
									{
										title : '申请部门',
										field : 'dept',
										width : 100,
										sortable : false
									},
									{
										title : '开始时间',
										field : 'startdate',
										width : 120,
										sortable : false
									},
									{
										title : '结束时间',
										field : 'enddate',
										width : 120,
										sortable : false
									},
									{
										title : '申请金额',
										field : 'feesumAccount',
										width : 160,
										sortable : false
									},
									{
										title : '申请人',
										field : 'appuser',
										width : 100,
										sortable : false
									},
									{
										title : '申请日期',
										field : 'appdate',
										width : 120,
										sortable : false
									},
									{
										title : '状态',
										field : 'status',
										width : 80,
										sortable : false
									},
									{
										field : 'functionKey',
										title : '功能键',
										width : 120,
										formatter : function(value, ot) {
											var link = ' <a href="javascript:void(0)" onclick="print('
													+ ot.travelid
													+ ')">打印</a> ';
											if (ot.processinstanceid != null) {
												link += ' <a href="javascript:void(0)" onclick="view('
														+ ot.travelid
														+ ')">查看</a> ';
											}
											if (ot.processinstanceid != null) {
												link += ' <a href="javascript:void(0)" onclick="viewProc('
														+ ot.processinstanceid
														+ ')">流程</a> ';
											}
											return link;

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

	function print(id) {
		window.location = "${contextPath}/mx/oa/reports/exportSTravel?travelid="
				+ id;
	}

	function view(id) {
		var link = "${contextPath}/mx/oa/stravel/review?lookover=true&travelid="
				+ id;
		art.dialog.open(link, {
			height : 420,
			width : 950,
			title : "查看记录",
			lock : true,
			scrollbars : "no"
		}, false);
	}

	function viewProc(processinstanceid) {
		window.open("${contextPath}/mx/jbpm/task/task?processInstanceId="
				+ processinstanceid);
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
		var params = jQuery("#searchForm").formSerialize();
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
			style="height: 100px">
			<div class="toolbar-backgroud">
				<img src="${contextPath}/images/window.png"> &nbsp;<span
					class="x_content_title">出差申请查询</span>
			</div>
			<div align="left">
				<form id="searchForm" name="searchForm" method="post">
					<table>
						<tbody>
							<tr>
								<td>地区：</td>
								<td><input id="area" class="easyui-combobox" name="area"
									value=""
									data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/eara'" /></td>
								<td>单位：</td>
								<td><input id="company" class="easyui-combobox"
									name="company" value=""
									data-options="valueField:'code',textField:'name',url:'${contextPath}/mx/oa/baseData/getAllCompanyJson'" /></td>
								<td>申请部门：</td>
								<td><input id="dept" class="easyui-combobox" name="dept"
									value=""
									data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'" /></td>
								<td>申请人：</td>
								<td><input id="appuser" class="easyui-combobox"
									name="appuser" value=""
									data-options="valueField:'code',textField:'name',url:'${contextPath}/mx/oa/baseData/getUserJson'" /></td>
							</tr>
							<tr>
								<td>申请日期：</td>
								<td><input id="appdateGreaterThanOrEqual"
									name="appdateGreaterThanOrEqual" class="easyui-datebox"></input></td>
								<td>至</td>
								<td><input id="appdateLessThanOrEqual"
									name="appdateLessThanOrEqual" class="easyui-datebox"></input></td>
								<td colspan="4" align="center"><a href="#"
									class="easyui-linkbutton" iconCls="icon-ok"
									onclick="javascript:searchData()">查询</a></td>
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