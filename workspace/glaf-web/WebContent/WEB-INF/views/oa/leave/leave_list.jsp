<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_config.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>请假申请列表</title>
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
							url : '${contextPath}/mx/oa/leave/json?rstatus='
									+ (
<%=request.getParameter("status")%>
	!= null ?
<%=request.getParameter("status")%>
	: ''),
							remoteSort : false,
							singleSelect : true,
							idField : 'leaveid',
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
										width : 120,
										sortable : false
									},
									{
										title : '申请部门',
										field : 'dept',
										width : 100,
										sortable : false
									},
									{
										title : '职位',
										field : 'post',
										width : 100,
										sortable : false
									},
									{
										title : '申请人',
										field : 'appuser',
										width : 100,
										sortable : false
									},
									{
										title : '请假类型',
										field : 'type',
										width : 80,
										sortable : false
									},
									{
										title : '时间（小时）',
										field : 'leavesum',
										width : 80,
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
													+ ot.leaveid + ')">打印</a> ';
											if (ot.processinstanceid != null) {
												link += ' <a href="javascript:void(0)" onclick="view('
														+ ot.leaveid
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
		window.location = "${contextPath}/mx/oa/reports/exportLeave?leaveid="
				+ id;
	}

	function view(id) {
		var link = "${contextPath}/mx/oa/leave/review?lookover=true&leaveid="
				+ id;
		art.dialog.open(link, {
			height : 420,
			width : 750,
			title : "查看记录",
			lock : true,
			scrollbars : "no"
		}, false);
	}

	function viewProc(processinstanceid) {
		window.open("${contextPath}/mx/jbpm/task/task?processInstanceId="
				+ processinstanceid);
	}

	function addNew() {
		var link = "${contextPath}/mx/oa/leave/addOrEdit";
		art.dialog.open(link, {
			height : 420,
			width : 850,
			title : "添加记录",
			lock : true,
			scrollbars : "no"
		}, false);
	}

	function searchWin() {
		jQuery('#dlg').dialog('open').dialog('setTitle', '请假申请列表查询');
		//jQuery('#searchForm').form('clear');
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
			jQuery
					.ajax({
						type : "POST",
						url : '${contextPath}/mx/oa/leave/getLeave?leaveid='
								+ selected.leaveid,
						dataType : 'json',
						error : function(data) {
							alert('服务器处理错误！');
						},
						success : function(data) {
							if (data != null) {
								if (data.status == "0" || data.status == "3") {
									var link = "${contextPath}/mx/oa/leave/addOrEdit?leaveid="
											+ selected.leaveid;
									art.dialog.open(link, {
										height : 420,
										width : 850,
										title : "修改记录",
										lock : true,
										scrollbars : "no"
									}, false);
								} else {
									alert("已提交和审批完的记录不能修改！");
								}
							}
						}
					});

		}
	}

	function submit() {
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if (rows == null || rows.length != 1) {
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected) {
			jQuery
					.ajax({
						type : "POST",
						url : '${contextPath}/mx/oa/leave/getLeave?leaveid='
								+ selected.leaveid,
						dataType : 'json',
						error : function(data) {
							alert('服务器处理错误！');
						},
						success : function(data) {
							if (data != null) {
								if (data.status == "0") {
									jQuery
											.ajax({
												type : "POST",
												url : '${contextPath}/mx/oa/leave/submit?leaveid='
														+ selected.leaveid,
												dataType : 'json',
												error : function(data) {
													alert('服务器处理错误！');
												},
												success : function(data) {
													if (data != null
															&& data.message != null) {
														alert(data.message);
													} else {
														alert('操作成功！');
													}
													jQuery('#mydatagrid')
															.datagrid('reload');
												}
											});
								} else {
									alert("只能提交已保存的记录！");
								}
							}
						}
					});

		}
	}

	function deleteSelection() {
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if (rows == null || rows.length != 1) {
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected) {
			jQuery
					.ajax({
						type : "POST",
						url : '${contextPath}/mx/oa/leave/getLeave?leaveid='
								+ selected.leaveid,
						dataType : 'json',
						error : function(data) {
							alert('服务器处理错误！');
						},
						success : function(data) {
							if (data != null) {
								if (data.status == "0" || data.status == "3") {
									if (confirm("数据删除后不能恢复，确定删除吗？")) {
										jQuery
												.ajax({
													type : "POST",
													url : '${contextPath}/mx/oa/leave/delete?leaveid='
															+ selected.leaveid,
													dataType : 'json',
													error : function(data) {
														alert('服务器处理错误！');
													},
													success : function(data) {
														if (data != null
																&& data.message != null) {
															alert(data.message);
														} else {
															alert('操作成功！');
														}
														jQuery('#mydatagrid')
																.datagrid(
																		'reload');
													}
												});
									}
								} else {
									alert("已提交与审批完的记录不能删除！");
								}
							}
						}
					});

		}
	}

	function reloadGrid() {
		jQuery('#mydatagrid').datagrid('reload');
	}

	function getSelected() {
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected) {
			//alert(selected.code+":"+selected.name+":"+selected.addr+":"+selected.col4);
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
			style="height: 40px">
			<div class="toolbar-backgroud">
				<img src="${contextPath}/images/window.png"> &nbsp;<span
					class="x_content_title">请假申请清单</span> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-add'"
					onclick="javascript:addNew();">新增</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-submit'"
					onclick="javascript:submit();">提交</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-edit'"
					onclick="javascript:editSelected();">修改</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-remove'"
					onclick="javascript:deleteSelection();">删除</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-search'"
					onclick="javascript:searchWin();">查找</a>
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
						<td>申请日期：</td>
						<td><input id="appdateGreaterThanOrEqual"
							name="appdateGreaterThanOrEqual" class="easyui-datebox"></input></td>
						<td>至</td>
						<td><input id="appdateLessThanOrEqual"
							name="appdateLessThanOrEqual" class="easyui-datebox"></input></td>
					</tr>
					<tr>
						<td>状态：</td>
						<td><input id="status" class="easyui-combobox" name="status"
							value=""
							data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/followStatus'" /></td>
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
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>