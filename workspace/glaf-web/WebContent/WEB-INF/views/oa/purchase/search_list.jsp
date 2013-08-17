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
	var areaRole =<%=request.getParameter("areaRole")%>;
	
	jQuery(function() {
		jQuery('#mydatagrid')
				.datagrid(
						{
							width : 1000,
							height : 100,
							fit : true,
							fitColumns : true,
							nowrap : false,
							striped : true,
							collapsible : true,
							url : '${contextPath}/mx/oa/purchaseSearch/json?areaRole='
									+ areaRole,
							sortName : 'id',
							sortOrder : 'desc',
							remoteSort : false,
							singleSelect : true,
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

	function resize() {
		jQuery('#mydatagrid').datagrid('resize', {
			width : 800,
			height : 400
		});
	}
	
	//编辑   修改
	function editSelected() {
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if (rows == null || rows.length != 1) {
			alert("请选择其中一条记录。");
			return;
		}
		for ( var i = 0; i < rows.length; i++) {
			if (rows[i].status > 0 && rows[i].status != 3) {
				alert("已提交和审批完的记录不能修改！");
				return;
			}
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected) {
			var link = "${contextPath}/mx/oa/purchase/edit?purchaseId="
					+ selected.id;
			art.dialog.open(link, {
				height : 420,
				width : 760,
				title : "修改记录",
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

	//重载
	function reloadGrid() {
		jQuery('#mydatagrid').datagrid('reload');
	}

	//获取列
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
</script>
</head>
<body style="margin: 1px;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 89px">
			<div class="toolbar-backgroud">
				<img src="${contextPath}/images/window.png"> &nbsp;<span
					class="x_content_title">采购申请查询列表</span>
			</div>
			<div>
				<form id="searchForm" name="searchForm" method="post">
					<table class="easyui-form">
						<tbody>
							<tr>
								<td>采购编号</td>
								<td><input id="purchaseNoLike" name="purchaseNoLike"
									class="easyui-validatebox" type="text"></input></td>
								<td>地区</td>
								<td><input id="area" class="easyui-combobox" name="area"
									value="${purchase.area}" disabled="disabled"
									data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/eara',
					onSelect:function(ret){										
					var url = '${contextPath}/rs/dictory/jsonArray/'+ret.code;
						jQuery('#company').combobox('clear');
						jQuery('#company').combobox('reload',url);
						jQuery('#area').val(ret.code);
					}																	
					" /></td>
								<script type="text/javascript">
									var flag =
								<%=request.getParameter("areaRole")%>
									;
									if (flag == 1) {
										jQuery('#area').removeAttr('disabled');
									}
								</script>
								<td>单位</td>
								<td><input id="company" class="easyui-combobox"
									name="company" readonly="readonly"
									data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/${purchase.area}'"
									value="${purchase.company}" /></td>
								<td>申请部门</td>
								<td><input id="dept" class="easyui-combobox" name="dept"
									data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'" />
								</td>
							</tr>
							<tr>
								<td>申请人</td>
								<td><input id="appuser" class="easyui-combobox"
									name="appuser" value="${purchase.appuser}"
									data-options="valueField:'code',textField:'name',url:'${contextPath}/mx/oa/baseData/getUserJson'" />
									<!-- 
			<input type="hidden" id="appuser" name="appuser" value="${purchase.appuser}" />
			<input id="appusername" name="appusername" type="text" 
				class="easyui-validatebox" value="${purchase.appuser}" 
				onclick="javascript:selectUser('iForm', 'appuser','appusername');" /></input>
			 --></td>
								<td>申请日期</td>
								<td><input id="appdateGreaterThanOrEqual"
									validType="isDate['','yyyy-MM-dd']"
									name="appdateGreaterThanOrEqual" class="easyui-datebox"></input></td>
								<td>至</td>
								<td><input id="appdateLessThanOrEqual"
									validType="isDate['','yyyy-MM-dd']"
									name="appdateLessThanOrEqual" class="easyui-datebox"></input></td>
								<td><a href="#" class="easyui-linkbutton" iconCls="icon-ok"
									onclick="javascript:searchData()">查询</a></td>
							</tr>
							<tr>
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