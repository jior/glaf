<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_config.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考核查询列表</title>
<meta name="Keywords" content="<%=appKeywords%>" />
<meta name="Description" content="<%=appDescription%>" />
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>

<script type="text/javascript">

   var contextPath="${contextPath}";
   var areaRole = '<%=request.getParameter("areaRole")%>';
	jQuery(function() {

		jQuery('#mydatagrid')
				.datagrid(
						{
							width : 1200,
							height : 480,
							fit : true,
							fitColumns : true,
							nowrap : false,
							striped : true,
							collapsible : true,
							url : '${contextPath}/mx/oa/assessresult/assessresultJson?areaRole='
									+ areaRole,
							sortName : 'id',
							sortOrder : 'desc',
							remoteSort : false,
							singleSelect : true,
							idField : 'resultid',
							columns : [ [ {
								title : '序号',
								field : 'startIndex',
								width : 20,
								sortable : false
							}, {
								title : '标题',
								field : 'title',
								width : 120
							}, {
								title : '地区',
								field : 'area',
								width : 80,
								formatter : funoperator4
							}, {
								title : '部门',
								field : 'dept',
								width : 80
							}, {
								title : '职位',
								field : 'post',
								width : 120
							}, {
								title : '年度',
								field : 'year',
								width : 40
							}, {
								title : '季度',
								field : 'season',
								width : 40,
								formatter : funoperator2
							}, {
								title : '月度',
								field : 'month',
								width : 40,
								formatter : funoperator3
							}, {
								title : '被考核人',
								field : 'beevaluation',
								width : 80,
								formatter : funoperator5
							}, {
								title : '得分',
								field : 'score',
								width : 80
							}, {
								field : 'functionKey',
								title : '操作',
								width : 80,
								formatter : funoperator
							} ] ],
							rownumbers : false,
							pagination : true,
							pageSize : 15,
							pageList : [ 10, 15, 20, 25, 30, 40, 50, 100 ],
							onDblClickRow : onRowClick
						});

		var p = jQuery('#mydatagrid').datagrid('getPager');
		jQuery(p).pagination({
			onBeforeRefresh : function() {
				//alert('before refresh');
			}
		});
	});
	function funoperator5(value, row, index) {
		var appusername = "";
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/baseData/getUserJson',
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data != null && data.message != null) {
					alert(data.message);
				} else {
					for ( var i = 0; i < data.length; i++) {
						if (data[i].code == value) {
							appusername = data[i].name;
						}
					}
				}
			}
		});
		return appusername;
	}
	function funoperator4(value, row, index) {
		var areaname = "";
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/rs/dictory/jsonArray/eara',
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data != null && data.message != null) {
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
	function funoperator2(value, row, index) {
		var str = ""
		if (value == 1) {
			str = "第一季度";
		} else if (value == 2) {
			str = "第二季度";
		} else if (value == 3) {
			str = "第三季度";
		} else if (value == 4) {
			str = "第四季度";
		}
		return str;
	}
	function funoperator3(value, row, index) {
		return value + "月";
	}
	function funoperator(value, row, index) {
		var str = "<a href='#' onclick='viewResult(" + row.resultid
				+ ")'>查看</a>&nbsp;";
		str += "<a href='#' onclick='exportResult(" + row.resultid
				+ ")'>导出</a>";
		return str;
	}
	function viewResult(resultid) {
		var link = "${contextPath}/mx/oa/assessresult/resultView?resultid="
				+ resultid;
		art.dialog.open(link, {
			height : 450,
			width : 980,
			title : "查看记录",
			lock : true,
			scrollbars : "no"
		}, false);
	}
	function exportResult(resultid) {
		window.location = '${contextPath}/mx/oa/reports/exportAssessQuestion?resultid='
				+ resultid;
	}

	function addNew() {
		//location.href="${contextPath}/mx/oa/assessresult/edit";
		var link = "${contextPath}/mx/oa/assessresult/edit";
		art.dialog.open(link, {
			height : 420,
			width : 680,
			title : "添加记录",
			lock : true,
			scrollbars : "no"
		}, false);
	}

	function onRowClick(rowIndex, row) {
		return;
		//window.open('${contextPath}/mx/oa/assessresult/edit?resultid='+row.id);
		var link = '${contextPath}/mx/oa/assessresult/edit?resultid=' + row.id;
		art.dialog.open(link, {
			height : 420,
			width : 680,
			title : "修改记录",
			lock : true,
			scrollbars : "no"
		}, false);
	}

	function searchWin() {
		jQuery('#dlg').dialog('open').dialog('setTitle', 'Assessresult查询');
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
			//location.href="${contextPath}/mx/oa/assessresult/edit?resultid="+selected.id;
			var link = "${contextPath}/mx/oa/assessresult/edit?resultid="
					+ selected.id;
			art.dialog.open(link, {
				height : 420,
				width : 680,
				title : "修改记录",
				lock : true,
				scrollbars : "no"
			}, false);
		}
	}

	function viewSelected() {
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if (rows == null || rows.length != 1) {
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected) {
			location.href = "${contextPath}/mx/oa/assessresult/edit?readonly=true&resultid="
					+ selected.id;
		}
	}

	function deleteSelections() {
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		if (ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")) {
			var resultids = ids.join(',');
			jQuery.ajax({
				type : "POST",
				url : '${contextPath}/mx/oa/assessresult/delete?resultids='
						+ resultids,
				dataType : 'json',
				error : function(data) {
					alert('服务器处理错误！');
				},
				success : function(data) {
					if (data != null && data.message != null) {
						alert(data.message);
					} else {
						alert('操作成功完成！');
					}
					jQuery('#mydatagrid').datagrid('reload');
				}
			});
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
		jQuery('#mydatagrid').datagrid('load',
				getMxObjArray(jQuery("#searchForm").serializeArray()));
		jQuery('#dlg').dialog('close');
	}
	function back() {
		window.location.href = "${contextPath}/mx/oa/assessquestion/view";
	}
</script>
<style type="text/css">
.tb tr td {
	border: 1px solid black;
	cellpadding: 0px;
}

.tb1 {
	border: 1px solid black;
}
</style>
</head>
<body style="margin: 1px;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 89px">
			<div class="toolbar-backgroud">
				<img src="${contextPath}/images/window.png"> &nbsp;<span
					class="x_content_title">岗位考核查询列表</span>
			</div>
			<div style="margin: 0;">
				<form id="searchForm" name="searchForm" method="post">
					<table class="easyui-form" style="width: 80%">
						<tbody>
							<tr>
								<td>标题</td>
								<td><input id="titleLike" class="easyui-validatebox"
									name="titleLike" size="12" /></td>
								<td>职位</td>
								<td><input id="post" class="easyui-combobox" name="post"
									size="12"
									data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/UserHeadship'" />
								</td>
								<td>年度</td>
								<td><select id="year" name="year" class="easyui-combobox"
									editable="false">
										<option value="">请选择</option>
										<option value="2012">2012</option>
										<option value="2013">2013</option>
										<option value="2014">2014</option>
										<option value="2015">2015</option>
										<option value="2016">2016</option>
										<option value="2017">2017</option>
										<option value="2018">2018</option>
										<option value="2019">2019</option>
										<option value="2020">2020</option>
								</select></td>
								<td>季度</td>
								<td><select id="season" name="season"
									class="easyui-combobox" editable="false">
										<option value="">请选择</option>
										<option value="1">第一季度</option>
										<option value="2">第二季度</option>
										<option value="3">第三季度</option>
										<option value="4">第四季度</option>
								</select></td>
								<td>月度</td>
								<td><select id="month" name="month" class="easyui-combobox"
									editable="false">
										<option value="">请选择</option>
										<option value="1">1月</option>
										<option value="2">2月</option>
										<option value="3">3月</option>
										<option value="4">4月</option>
										<option value="5">5月</option>
										<option value="6">6月</option>
										<option value="7">7月</option>
										<option value="8">8月</option>
										<option value="9">9月</option>
										<option value="10">10月</option>
										<option value="11">11月</option>
										<option value="12">12月</option>
								</select></td>
							</tr>
							<tr>
								<td>被考核人</td>
								<td><input id="beevaluation" class="easyui-combobox"
									name="beevaluation" size="12"
									data-options="valueField:'code',textField:'name',url:'${contextPath}/mx/oa/baseData/getUserJson'" />
								</td>
								<td></td>
								<td></td>
								<td colspan="6" align="right"><a href="#"
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