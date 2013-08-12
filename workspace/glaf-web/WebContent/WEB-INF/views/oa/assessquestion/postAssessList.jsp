<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_config.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>岗位考核列表</title>
<meta name="Keywords" content="<%=appKeywords%>" />
<meta name="Description" content="<%=appDescription%>" />
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>

<script type="text/javascript">
	var contextPath = "${contextPath}";

	jQuery(function() {
		jQuery('#mydatagrid').datagrid({
			width : 1000,
			height : 480,
			fit : true,
			fitColumns : true,
			nowrap : false,
			striped : true,
			collapsible : true,
			url : '${contextPath}/mx/oa/assessquestion/queryPostAssess',
			sortName : 'id',
			sortOrder : 'desc',
			remoteSort : false,
			singleSelect : true,
			idField : 'qustionid',
			columns : [ [ {
				title : '序号',
				field : 'startIndex',
				width : 20,
				sortable : false
			}, {
				title : '标题',
				field : 'title',
				width : 80,
				sortable : false
			}, {
				title : '频率',
				field : 'rate',
				width : 20,
				sortable : false,
				formatter : foroperator2
			}, {
				field : '操作',
				title : '操作',
				width : 20,
				formatter : foroperator
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

	function foroperator2(value, row, index) {
		var str = "";
		if (value == 1) {
			str = "季度";
		} else if (value == 2) {
			str = "月度";
		} else if (value == 3) {
			str = "年度";
		}
		return str;
	}
	function foroperator(value, rec) {
		var str = "";
		str = "<a href='javascript:jobEvaluationView(" + rec.qustionid
				+ ")'>查看</a>&nbsp;&nbsp;" + "<a href='javascript:jobEvaluate("
				+ rec.qustionid + ")'>考核</a>";
		return str;
	}
	function jobEvaluationView(questionId) {
		var link = '${contextPath}/mx/oa/assessresult/jobEvaluationView?questionId='
				+ questionId;
		art.dialog.open(link, {
			height : 450,
			width : 880,
			title : "查看记录",
			lock : true,
			scrollbars : "no"
		}, false);
		// window.location.href=link;
	}
	function jobEvaluate(questionId) {
		var link = '${contextPath}/mx/oa/assessresult/jobEvaluate?questionId='
				+ questionId;
		art.dialog.open(link, {
			height : 450,
			width : 980,
			title : "岗位考核",
			lock : true,
			scrollbars : "no"
		}, false);
		// window.location.href=link;
	}
	function addNew() {
		//location.href="${contextPath}/mx/oa/assessquestion/edit";
		var link = "${contextPath}/mx/oa/assessquestion/edit";
		art.dialog.open(link, {
			height : 42350,
			width : 680,
			title : "添加记录",
			lock : true,
			scrollbars : "no"
		}, false);
	}

	function onRowClick(rowIndex, row) {
		//window.open('${contextPath}/mx/oa/assessquestion/edit?qustionid='+row.id);
		var link = '${contextPath}/mx/oa/assessquestion/edit?qustionid='
				+ row.id;
		art.dialog.open(link, {
			height : 420,
			width : 680,
			title : "修改记录",
			lock : true,
			scrollbars : "no"
		}, false);
	}

	function searchWin() {
		jQuery('#dlg').dialog('open').dialog('setTitle', '岗位考核查询');
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
			//location.href="${contextPath}/mx/oa/assessquestion/edit?qustionid="+selected.id;
			var link = "${contextPath}/mx/oa/assessquestion/edit?qustionid="
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
			location.href = "${contextPath}/mx/oa/assessquestion/edit?readonly=true&qustionid="
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
			var qustionids = ids.join(',');
			jQuery.ajax({
				type : "POST",
				url : '${contextPath}/mx/oa/assessquestion/delete?qustionids='
						+ qustionids,
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
		var params = jQuery("#searchForm").formSerialize();
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/assessquestion/queryPostAssess',
			dataType : 'json',
			data : params,
			error : function(data) {
				alert('服务器处理错误！');
			},
			success : function(data) {
				jQuery('#mydatagrid').datagrid('loadData', data);
			}
		});

		jQuery('#dlg').dialog('close');
	}
</script>
<style type="text/css">
.tb tr td {
	border: 1px solid black;
	cellpadding: 0px;
}
</style>
</head>
<body style="margin: 1px;" class="bodyBg">
	<div style="margin: 0;"></div>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			bordercolor="#000000" style="height: 40px">
			<div class="toolbar-backgroud">
				<img src="${contextPath}/images/window.png"> &nbsp;<span
					class="x_content_title">岗位考核列表：</span>
				 
				<a href="#" class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-search'"
					onclick="javascript:searchWin();">查找</a>
			</div>
		</div>
		<div data-options="region:'center',border:true">
			<table class="easyui-form tb" width="100%" border="1px"
				cellspacing="0px" style="border-collapse: collapse">
				<tbody>
				 
					<c:forEach var="assess" items="${assessList }"
						varStatus="assessIndex">
						<tr>
							<td witdth="5%" class="td-text">${assessIndex.index+1 }</td>
							<td witdth="70%" class="td-text">${assess.title }</td>
							<td witdth="10%" class="td-text">${assess.rateText }</td>
							<td witdth="10%" class="td-text"><a href="#">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
								href="#">考核</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<table id="mydatagrid"></table>
		</div>
	</div>
	<div id="edit_dlg" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="editForm" name="editForm" method="post"></form>
	</div>
	<div id="dlg" class="easyui-dialog"
		style="width: 400px; height: 200px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="searchForm" name="searchForm" method="post">
			<table align="center">
				<tr>
					<td>标题</td>
					<td><input type="text" style="size: 350px" name="titleLike" /></td>
				</tr>

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