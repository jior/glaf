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
							height : 480,
							fit : true,
							fitColumns : true,
							nowrap : false,
							striped : true,
							collapsible : true,
							url : '${contextPath}/mx/oa/withdrawalSearch/json?areaRole='
									+ areaRole,
							sortName : 'id',
							sortOrder : 'desc',
							remoteSort : false,
							singleSelect : true,
							idField : 'withdrawalid',
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
										formatter : function(value, row, index) {
											return row.areaname;
										}
									},
									{
										title : '单位',
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
										field : 'functionKey',
										title : '功能键',
										width : 120,
										formatter : function(value, row, index) {
											var s = "<a href='#' onclick='print("
													+ row.withdrawalid
													+ ")'>打印</a>    ";
											var c = "";
											var v = "";
											if (row.processinstanceid != null) {
												c = "<a href='#' onclick='viewSelected("
														+ row.withdrawalid
														+ ")'>查看</a> ";
												v = "<a href='#' onclick='showProcess("
														+ row.processinstanceid
														+ ")'>流程</a> ";
											}
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
					class="x_content_title">取现申请查询列表</span>
			</div>
			<div style="margin: 0;">
				<form id="searchForm" name="searchForm" method="post">
					<table class="easyui-form" style="width: 80%">
						<tbody>
							<tr>
								<td>地区</td>
								<td><input id="area" class="easyui-combobox" name="area"
									size="12" value="${withdrawal.area}" disabled="disabled"
									data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/eara',
					onSelect:function(ret){										
					var url = '${contextPath}/rs/dictory/jsonArray/'+ret.code;
						jQuery('#company').combobox('clear');
						jQuery('#company').combobox('reload',url);
						jQuery('#area').val(ret.code);
					}																	
					" />
								</td>
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
									name="company" size="12" readonly="readonly"
									data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/${withdrawal.area}'" />
								</td>
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
								<td>申请日期</td>
								<td><input id="appdateGreaterThanOrEqual"
									name="appdateGreaterThanOrEqual"
									validType="isDate['','yyyy-MM-dd']" class="easyui-datebox"
									size="12"></input></td>
								<td>至</td>
								<td><input id="appdateLessThanOrEqual"
									name="appdateLessThanOrEqual"
									validType="isDate['','yyyy-MM-dd']" class="easyui-datebox"
									size="12"></input></td>
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
	</div>



</body>
</html>
