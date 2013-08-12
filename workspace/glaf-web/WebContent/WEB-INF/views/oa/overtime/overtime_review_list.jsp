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
<title>加班申请审核列表</title>
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
							url : '${contextPath}/mx/oa/overtime/getReviewOvertime',
							sortName : 'id',
							sortOrder : 'desc',
							remoteSort : false,
							singleSelect : false,
							idField : 'id',
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
										title : '单位',
										field : 'company',
										width : 160,
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
										title : '加班类型',
										field : 'type',
										width : 80,
										sortable : false
									},
									{
										title : '时间（小时）',
										field : 'overtimesum',
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
										field : 'strstauts',
										width : 80,
										sortable : false
									},
									{
										field : 'functionKey',
										title : '功能键',
										width : 120,
										formatter : function(value, ot) {
											var link = ' <a href="javascript:void(0)" onclick="print('
													+ ot.id + ')">打印</a> ';
											if (ot.processinstanceid != null) {
												link += ' <a href="javascript:void(0)" onclick="view('
														+ ot.id + ')">查看</a> ';
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
		window.location = "${contextPath}/mx/oa/reports/exportOverTime?id="
				+ id;
	}

	function view(id) {
		var link = "${contextPath}/mx/oa/overtime/review?lookover=true&id="
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

	function onRowClick(rowIndex, row) {
		var link = "${contextPath}/mx/oa/overtime/review?lookover=true&id="
				+ row.id;
		window.location = link;

	}

	function searchWin() {
		jQuery('#dlg').dialog('open').dialog('setTitle', '加班申请审核列表查询');
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
		if (jQuery("#workedProcessFlag").val() == 'PD') {
			jQuery("#reviewDiv").hide();
		} else {
			jQuery("#reviewDiv").show();
		}
		var params = jQuery("#searchForm").formSerialize();
		jQuery('#mydatagrid').datagrid('load',
				getMxObjArray(jQuery("#searchForm").serializeArray()));

		jQuery('#dlg').dialog('close');
	}

	//弹出批量审核窗口
	function approveBatchWin() {
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
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

	function passData() {
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		if (ids.length > 0) {
			if (confirm("确认审核通过吗？")) {
				var ids = ids.join(',');
				var params = jQuery("#iForm").formSerialize();
				params = params + "&approveOpinion="
						+ jQuery('#approveOpinion').val().trim();
				jQuery
						.ajax({
							type : "POST",
							url : '${contextPath}/mx/oa/overtime/submitData?ids='
									+ ids,
							data : params,
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
								jQuery('#dlgApprove').dialog('close');
							}
						});
			}

		} else {
			alert("请选择至少一条记录。");
		}

	}

	function noPassData() {
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		if (ids.length > 0) {
			if (jQuery('#approveOpinion').val().trim() == "") {
				alert("审批不通过需填写审批意见。");
				return;
			}
			if (confirm("确认审核不通过吗？")) {
				var ids = ids.join(',');
				var params = jQuery("#iForm").formSerialize();
				params = params + "&approveOpinion="
						+ jQuery('#approveOpinion').val().trim();
				jQuery.ajax({
					type : "POST",
					url : '${contextPath}/mx/oa/overtime/submitData?ids=' + ids
							+ "&passFlag=1",
					data : params,
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
						jQuery('#dlgApprove').dialog('close');
					}
				});
			}

		} else {
			alert("请选择至少一条记录。");
		}

	}

	function editSelected() {
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if (rows == null || rows.length != 1) {
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected) {
			//location.href="${contextPath}/mx/oa/contract/review?id="+selected.id;
			var link = "${contextPath}/mx/oa/overtime/review?id=" + selected.id;
			art.dialog.open(link, {
				height : 420,
				width : 950,
				title : "加班申请审核",
				lock : true,
				scrollbars : "no"
			}, false);
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
					class="x_content_title">加班申请审核列表</span>
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
						onclick="javascript:approveBatchWin();">批量审核</a>
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
		style="width: 500px; height: 350px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="searchForm" name="searchForm" method="post">
			<table class="easyui-form">
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
					</tr>
					<tr>
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
						<td>职位：</td>
						<td><input id="postLike" name="postLike" type="text"
							class="easyui-validatebox" value="" /></td>
						<td>加班类型：</td>
						<td><input id="type" class="easyui-combobox" name="type"
							value=""
							data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/JBLX'" /></td>
					</tr>
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