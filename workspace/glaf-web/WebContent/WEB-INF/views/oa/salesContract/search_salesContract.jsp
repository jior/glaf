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
<title>合同申请查询</title>
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
	var contextPath = "${contextPath}";
	var earaRole =<%=request.getParameter("earaRole")%>;

	jQuery(function() {
		jQuery('#mydatagrid')
				.datagrid(
						{
							width : 1000,
							height : 480,
							fit : true,
							fitColumns : true,
							nowrap : true,
							striped : true,
							collapsible : true,
							url : '${contextPath}/mx/oa/salescontract/json?earaRole='
									+ earaRole,
							sortName : 'id',
							sortOrder : 'desc',
							remoteSort : false,
							singleSelect : false,
							idField : 'id',
							columns : [ [
									{
										title : '序号',
										field : 'startIndex',
										width : 40,
										sortable : false
									},
									{
										title : '地区',
										field : 'area',
										width : 80,
										sortable : false,
										formatter : function(value, row, index) {
											var areaname = "";
											jQuery
													.ajax({
														type : "POST",
														url : '${contextPath}/rs/dictory/jsonArray/eara',
														dataType : 'json',
														async : false,
														success : function(data) {
															if (data != null
																	&& data.message != null) {
																alert(data.message);
															} else {
																for ( var i = 0; i < data.length; i++) {
																	if (data[i].code == value) {
																		areaname = data[i].name;
																	}
																}
															}
														}
													});
											return areaname;
										}
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
										width : 120,
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

	function formatter1(value, row, rowIndex) {
		var res = '<a href="javascript:void(0)" onclick="javascript:onContractNoClick('
				+ rowIndex + ',' + row.id + ');">' + row.contractno + '</a>';
		return res;
	}

	
	function onContractNoClick(rowIndex, id) {
		var link = '${contextPath}/mx/oa/salescontract/review?id=' + id
				+ '&lookover=true';
		art.dialog.open(link, {
			height : 500,
			width : 900,
			title : "查看记录",
			lock : true,
			scrollbars : "no"
		}, false);
	}
	
	
	function formatter2(value, row, rowIndex) {
		if ("0" == row.status) {
			return "保存"
		}
		if ("1" == row.status) {
			return "已提交"
		}
		if ("2" == row.status) {
			return "审批完"
		}
		if ("3" == row.status) {
			return "已退回"
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
	
	
	function reviewZ(id) {
		salesID = id;
		jQuery('#dlgApprove').dialog('open').dialog('setTitle', '审核');
		jQuery('#approveForm').form('clear');
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
					class="x_content_title">合同申请查询列表</span>
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
								<%=request.getParameter("earaRole")%>
									;
									if (flag == 1) {
										jQuery('#area').removeAttr('disabled');
									}
								</script>
								<td>申请人</td>
								<td><input id="appuser" class="easyui-combobox"
									name="appuser"
									data-options="valueField:'code',textField:'name',url:'${contextPath}/mx/oa/baseData/getUserJson'" />
								</td>


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

								<td>申请日期</td>
								<td><input id="appdateGreaterThanOrEqual"
									validType="isDate['','yyyy-MM-dd']"
									name="appdateGreaterThanOrEqual" class="easyui-datebox"></input>
								</td>
								<td>至</td>
								<td><input id="appdateLessThanOrEqual"
									validType="isDate['','yyyy-MM-dd']"
									name="appdateLessThanOrEqual" class="easyui-datebox"></input></td>

								<td><a href="#" class="easyui-linkbutton" iconCls="icon-ok"
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
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>
