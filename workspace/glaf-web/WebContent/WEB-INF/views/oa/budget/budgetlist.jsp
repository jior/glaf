<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_config.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支付预算申请</title>
<meta name="Keywords" content="<%=appKeywords%>" />
<meta name="Description" content="<%=appDescription%>" />
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
	var contextPath = "${contextPath}";
	var rstatus =<%=request.getParameter("status")%>;
	
	jQuery(function() {
		jQuery('#mydatagrid').datagrid({
			width : 1000,
			height : 480,
			fit : true,
			fitColumns : true,
			nowrap : true,
			striped : true,
			collapsible : true,
			url : '${contextPath}/mx/oa/budget/json?rstatus=' + rstatus,
			sortName : 'id',
			sortOrder : 'desc',
			remoteSort : false,
			singleSelect : true,
			idField : 'budgetid',
			columns : [ [ {
				title : '序号',
				field : 'startIndex',
				width : 80,
				sortable : false
			}, {
				title : '预算编号',
				field : 'budgetno',
				width : 120,
				sortable : false,
				formatter : formatter4
			}, {
				title : '地区',
				field : 'area',
				width : 80,
				sortable : false,
				formatter : formatter4
			}, {
				title : '单位',
				field : 'company',
				width : 200,
				sortable : false,
				formatter : formatter4
			}, {
				title : '部门',
				field : 'dept',
				width : 120,
				sortable : false,
				formatter : formatter4
			}, {
				title : '职位',
				field : 'post',
				width : 150,
				sortable : false,
				formatter : formatter4
			}, {
				title : '预算金额',
				field : 'budgetsum',
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
				width : 80,
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
		//location.href="${contextPath}/mx/oa/budget/edit";
		var link = "${contextPath}/mx/oa/budget/edit";
		art.dialog.open(link, {
			height : 430,
			width : 900,
			title : "添加记录",
			lock : true,
			scrollbars : "no"
		}, false);
	}

	function onRowClick(rowIndex, row) {
		//window.open('${contextPath}/mx/oa/budget/edit?budgetid='+row.id);
		var link = '${contextPath}/mx/oa/budget/edit?budgetid=' + row.id;
		art.dialog.open(link, {
			height : 420,
			width : 680,
			title : "修改记录",
			lock : true,
			scrollbars : "no"
		}, false);
	}

	function searchWin() {
		jQuery('#dlg').dialog('open').dialog('setTitle', '支付预算申请查询');
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
		if (selected.status == '1' || selected.status == '2') {
			alert("已提交和审批完的记录不能修改！");
			return;
		}
		if (selected) {
			//location.href="${contextPath}/mx/oa/budget/edit?budgetid="+selected.id;
			var link = "${contextPath}/mx/oa/budget/edit?budgetid="
					+ selected.budgetid;
			art.dialog.open(link, {
				height : 430,
				width : 900,
				title : "修改记录",
				lock : true,
				scrollbars : "no"
			}, false);
		}
	}

	function viewSelected(id) {
		var link = "${contextPath}/mx/oa/budget/review?lookover=true&budgetid="
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
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if (rows == null || rows.length != 1) {
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected.status == '1' || selected.status == '2') {
			alert("已提交与审批完的记录不能删除");
			return;
		}
		if (confirm("数据删除后不能恢复，确定删除吗？")) {
			jQuery.ajax({
				type : "POST",
				url : '${contextPath}/mx/oa/budget/delete?budgetid='
						+ selected.budgetid,
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

	function submit() {
		var ids = [];
		var statuses = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].budgetid);
			statuses.push(rows[i].status);
			if (rows[i].status != 0) {
				alert("只能提交已保存的记录");
				return;
			}
		}
		if (ids.length > 0 && ids.length == 1) {
			if (confirm("确定提交吗？")) {
				jQuery.ajax({
					type : "POST",
					url : '${contextPath}/mx/oa/budget/submit?budgetid='
							+ rows[0].budgetid,
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

	function formatter3(value, row, index) {
		var s = "<a href='#' onclick='print(" + row.budgetid + ");'>打印</a> ";
		var c = "";
		var d = "";
		if (row.processinstanceid != null) {
			c = "<a href='#' onclick='viewSelected(" + row.budgetid
					+ ")'>查看</a> ";
			d = "<a href='#' onclick='viewProc(" + row.processinstanceid
					+ ")'>流程</a> ";

		}
		return s + c + d;
	}

	function print(id) {
		window.location = '${contextPath}/mx/oa/reports/exportBudget?budgetid='+ id;
	}

	function viewProc(processinstanceid) {
		window.open('${contextPath}/mx/jbpm/task/task?processInstanceId='+ processinstanceid);
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
			style="height: 40px">
			<div class="toolbar-backgroud">
				<img src="${contextPath}/images/window.png"> &nbsp; <span
					class="x_content_title">支付预算申请列表</span> <a href="#"
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
					onclick="javascript:deleteSelections();">删除</a> <a href="#"
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
		style="width: 500px; height: 200px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="searchForm" name="searchForm" method="post">
			<table class="easyui-form">
				<tbody>
					<tr>
						<td>编号</td>
						<td><input id="budgetnoLike" name="budgetnoLike"
							class="easyui-validatebox" type="text"></input></td>
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
						<td><select id="status" name="status" class="list">
								<option value="">请选择</option>
								<option value="0">保存</option>
								<option value="1">已提交</option>
								<option value="2">审批完</option>
								<option value="3">已退回</option>
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
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>