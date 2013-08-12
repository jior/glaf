<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_config.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>指标查询列表</title>
<meta name="Keywords" content="<%=appKeywords%>" />
<meta name="Description" content="<%=appDescription%>" />
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>

<script type="text/javascript">
	var contextPath = "${contextPath}";

	jQuery(function() {
		jQuery('#mydatagrid').datagrid({
			width : 1000,
			height : 400,
			fit : true,
			fitColumns : true,
			nowrap : false,
			striped : true,
			collapsible : true,
			url : '${contextPath}/mx/oa/assessinfo/selectAssesInfo',
			sortName : 'indexid',
			sortOrder : 'desc',
			remoteSort : false,
			singleSelect : false,
			idField : 'indexid',
			columns : [ [ {
				title : '序号',
				field : 'startIndex',
				width : 20,
				sortable : false
			}, {
				title : '指标名称',
				field : 'name',
				width : 80,
				sortable : false
			}, {
				title : '评分依据',
				field : 'basis',
				width : 80,
				sortable : false
			}, {
				title : '评分标准 ',
				field : 'standard',
				width : 20,
				sortable : false
			}, {
				field : 'iseffective',
				title : '是否有效',
				width : 20,
				formatter : isEnabled
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
	function isEnabled(value, rec) {
		//alert(value);
		if (value == 1) {
			return "是";
		} else {
			return "否";
		}
	}

	function addNew() {
		//location.href="${contextPath}/mx/oa/assessinfo/edit";
		var link = "${contextPath}/mx/oa/assessinfo/edit";
		art.dialog.open(link, {
			height : 350,
			width : 680,
			title : "添加记录",
			lock : true,
			scrollbars : "no"
		}, false);
	}

	//双击选择
	function onRowClick(rowIndex, row) {
		 
	}
	
	function selectData() {
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		var assessSortId = jQuery('#assessSortId').val();
		var ids = [];
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].indexid);
		}
		if (rows.length > 0) {
			var indexIds = ids.join(',');
			jQuery
					.ajax({
						type : "POST",
						url : '${contextPath}/mx/oa/assesscontent/saveAssessContent?assessSortId='
								+ assessSortId + '&indexIds=' + indexIds,
						dataType : 'json',
						error : function(data) {
							alert('服务器处理错误！');
						},
						success : function(data) {
							if (data != null && data.message != null) {
								alert(data.message);
							}
							if (window.opener) {
								window.opener.location.reload();
							} else if (window.parent) {
								var win = art.dialog.open.origin;
								win.ruash();
								art.dialog.close();
							}
						}
					});
		} else {
			alert("请至少选择一条记录！");
		}

	}
	
	//保存指标信息到对应的指标制作单
	function saveAssessContent(indexid) {
		if (indexid + "" == "undefinded" || indexid.length == 0) {
			return false;
		}
		var assessSortId = $("#assessSortId").val();
		alert(assessSortId);
		return;
		if (indexid != "" || indexid.length > 0) {
			var params = {};
			params.indexid = indexid;
			params.assessSortId = assessSortId;
			jQuery.ajax({
				type : "POST",
				url : '${contextPath}/mx/oa/assesscontent/saveAssessContent',
				dataType : 'json',
				data : params,
				error : function(data) {
					alert('服务器处理错误！');
				},
				success : function(data) {
					if (data != null && data.message != null) {
						alert(data.message);
					}
					if (window.opener) {
						window.opener.location.reload();
					} else if (window.parent) {
						var win = art.dialog.open.origin;
						win.ruash();
						art.dialog.close();
					}
				}
			});
		} else {
			alert("请选择一条记录。");
		}
	}

	function searchWin() {
		jQuery('#dlg').dialog('open').dialog('setTitle', '查找');
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
		if (!selected) {
			alert(selected.code + ":" + selected.name + ":" + selected.addr
					+ ":" + selected.col4);
			return selected;
		}
		return false;
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
	<div style="margin: 0;"></div>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:true">
			<form id="searchForm" name="searchForm" method="post">
				<input type="hidden" name=assessSortId id="assessSortId"
					value="${assessSortId }" />
				<table>
					<tr>
						<td>指标名称：</td>
						<td><input id="nameLike" name="nameLike"
							class="easyui-validatebox" type="text" style="width: 250px;"></input>
						</td>
						<td><a href="#" class="easyui-linkbutton" iconCls="icon-ok"
							onclick="javascript:searchData()">查找</a> <a href="#"
							class="easyui-linkbutton" iconCls="icon-cancel"
							onclick="javascript:art.dialog.close()">取消</a></td>
					</tr>
					<tr>
						<td colspan="2"><input type="button" class="bt" value="确定"
							onclick="selectData();" /></td>
					</tr>
				</table>
			</form>
			<div style="width: 795px; height: 370px;">
				<table id="mydatagrid"></table>
			</div>
		</div>
	</div>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>