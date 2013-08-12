<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_config.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.glaf.apps.assessquestion.model.*"%>
<%@ page import="com.glaf.apps.assesssort.model.*"%>
<%@ page import="com.glaf.apps.assesscontent.model.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>岗位考核指标查询</title>
<meta name="Keywords" content="<%=appKeywords%>" />
<meta name="Description" content="<%=appDescription%>" />
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>

<script type="text/javascript">
	var contextPath = "${contextPath}";

	jQuery(function() {
		return;
		//未用
		jQuery('#mydatagrid').datagrid({
			width : 1000,
			height : 480,
			fit : true,
			fitColumns : true,
			nowrap : false,
			striped : true,
			collapsible : true,
			url : '${contextPath}/mx/oa/assessquestion/json',
			sortName : 'id',
			sortOrder : 'desc',
			remoteSort : false,
			singleSelect : true,
			idField : 'qustionid',
			columns : [ [
			//{title:'序号', field:'startIndex', width:20, sortable:false},
			//  {title:'标题', field:'title', width:80, sortable:false},
			// {title:'有效期', field:'validdate', width:50, sortable:false},
			// {title:'频率', field:'rate', width:20, sortable:false},
			//{field:'iseffective',title:'是否有效',width:20,formatter:isEnabled}
			] ],
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

	function isEnabled(value, rec) {
		//alert(value);
		if (value == 1) {
			return "是";
		} else {
			return "否";
		}
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
			url : '${contextPath}/mx/oa/assessquestion/json',
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

	//添加指标类型
	function addAssessType() {

		var qid = $("#questionId").val();
		var link = "${contextPath}/mx/oa/assesssort/selectAssessType?questionId="
				+ qid;
		art.dialog.open(link, {
			height : 450,
			width : 380,
			title : "选择指标类型",
			lock : true,
			scrollbars : "yes"
		}, false);

	}
	//日期验证   是否为日期(yyyy[/-]mm[/-]dd)
	function IsDate(str) {
		var regExp = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/g;
		return regExp.test(str);
	}
	//表彰提交验证
	function checkAssess(f) {
		//标题
		var title = $("#title").val();
		if (title.length == 0) {
			alert("请输入标题");
			$("#title").focus();
			return false;
		}
		//日期
		//var regx=/\d{4}-\d{2}\d{2}/;
		var validDate = $("#validDate").val();
		if (!IsDate(validDate)) {
			//alert("请选择日期！");
			$("#validDate").focus();
			//return false;
		}

		return true;
	}

	//添加指标
	function addAssessContent(sortId) {
		var link = "${contextPath}/mx/oa/assesscontent/selectAssessContent?assessSortId="
				+ sortId;
		art.dialog.open(link, {
			height : 500,
			width : 800,
			title : "选择指标",
			lock : true,
			scrollbars : "yes"
		}, false);
	}
	//删除指标
	function deleteAssessContent(contentid) {
		if (contentid + "" == "undefinded" || contentid.length == 0) {
			return false;
		}
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/assesscontent/delete?contentid='
					+ contentid,
			dataType : 'json',
			error : function(data) {
				alert('服务器处理错误！');
			},
			success : function(data) {
				if (data != null && data.message != null) {
					alert(data.message);
					window.location.href = window.location.href
				} else {
					alert('操作成功完成！');
					window.location.href = window.location.href;

				}
			}
		});
	}
	function back() {
		window.location.href = "${contextPath}/mx/oa/assessquestion/view";
	}
</script>
<style type="text/css">
table {
	border: 1px solid black;
	border-collapse: collapse;
}

.tb tr td {
	border: 1px solid black;
	border-collapse: collapse;
}
</style>
</head>
<body style="margin: 1px;" class="bodyBg">
	<div style="margin: 0;"></div>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<span class="x_content_title">岗位考核指标查询</span>
				 

				<a href="#" class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-back'"
					onclick="javascript:back()">返回</a>

			</div>
		</div>

		<div data-options="region:'center',border:false,cache:true"">
			<form name="makeAssessIndex" id="makeAssessIndex" method="post"
				onsubmit=" return checkAssess(this);"
				action="${contextPath}/mx/oa/assessquestion/saveMakeAssessIndex">
				<input type="hidden" name="questionId" id="questionId"
					value="${assessquestion.qustionid }" />

				<table width="1000px" align="center">
					<tr>
						<td>
							<table width="1000px">
								<tr>
									<td colspan="7" align="center" style="font-size: 20px;">岗位考核指标查询</td>
								</tr>
								<tr>
									<td class="searchTitle">标题:</td>
									<td colspan="6">${assessquestion.title }</td>
								</tr>
								<tr>
									<td class="searchTitle">有效期</td>
									<td><fmt:formatDate pattern="yyyy年MM月dd日"
											value="${assessquestion.validdate }" type="both" /></td>
									<td class="searchTitle" colspan="2">频率</td>
									<td>${ assessRate }</td>
									<td class="searchTitle">是否有效</td>
									<td>${ assessEffective }</td>
									<td>&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table width="1000px" class="tb">
								<tr style="background-color: #0088AA">
									<td colspan="2">考评类型</td>
									<td>KPI关键绩效指标</td>
									<td>评分标准标</td>
									<td>评分依据</td>
								</tr>
								<!-- 一层分类 -->
								<%
									AssessquestionExt question = (AssessquestionExt) request
											.getAttribute("assessquestion");
									List<AssessQuestionTree> sortTreeList = question.getSortTreeList();//企业 或工厂标准
									for (AssessQuestionTree sortTree1 : sortTreeList) {
										List<Assesscontent> contentList = sortTree1.getContentList();
										for (Assesscontent content : contentList) {
								%>
								<tr>
									<td><%=sortTree1.getAname() + "\\"%></td>
									<td><%=content.getName()%></td>
									<td><%=content.getBasis()%></td>
									<td><%=content.getStandard()%></td>

								</tr>

								<%
									}
									}
								%>


							</table>

							</form>

							<table id="mydatagrid"></table>
							</div>
							</div>
							<div id="edit_dlg" class="easyui-dialog"
								style="width: 400px; height: 280px; padding: 10px 20px"
								closed="true" buttons="#dlg-buttons">
								<form id="editForm" name="editForm" method="post"></form>
							</div>
							<div id="dlg" class="easyui-dialog"
								style="width: 400px; height: 280px; padding: 10px 20px"
								closed="true" buttons="#dlg-buttons">
								<form id="searchForm" name="searchForm" method="post">
									<table class="easyui-form">


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