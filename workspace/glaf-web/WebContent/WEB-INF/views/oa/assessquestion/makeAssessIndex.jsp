<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_config.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考核指标制作</title>
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
		var link = '${contextPath}/mx/oa/assessquestion/viewAssess?qustionid='
				+ row.id;
		window.location.href = link;
		//art.dialog.open(link, { height: 420, width: 680, title: "修改记录", lock: true, scrollbars:"no" }, false);
	}

	function searchWin() {
		jQuery('#dlg').dialog('open').dialog('setTitle', 'Assessquestion查询');
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
		//如果无questionid 则先进行保存标题头
		var qid = $("#questionId").val();
		if (qid == "") {
			var bol = jQuery("#makeAssessIndex").form('validate');
			if (bol == false) {
				alert("请先输入标题和有效期！");
				return;
			}
			var params = jQuery("#makeAssessIndex").formSerialize();
			jQuery
					.ajax({
						type : "POST",
						url : '${contextPath}/mx/oa/assessquestion/saveMakeAssessTitle',
						data : params,
						dataType : 'json',
						error : function(data) {
							alert('服务器处理错误！');
						},
						success : function(data) {
							if (data != null && data.message != null) {
								alert(data.message);
							}
							window.parent.reloadGrid();
							var link = "${contextPath}/mx/oa/assesssort/selectAssessType?questionId="
									+ data.questionid;
							art.dialog.open(link, {
								height : 430,
								width : 380,
								title : "选择指标类型",
								lock : true,
								scrollbars : "yes"
							}, false);
						}
					});
		} else {
			var link = "${contextPath}/mx/oa/assesssort/selectAssessType?questionId="
					+ qid;
			art.dialog.open(link, {
				height : 430,
				width : 380,
				title : "选择指标类型",
				lock : true,
				scrollbars : "yes"
			}, false);
		}

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
			height : 430,
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
		if (confirm("数据删除后不能恢复，确定删除吗？")) {
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
	}
	//删除指标类型
	function delAssessType(assesssortid) {
		if (assesssortid + "" == "undefinded" || assesssortid.length == 0) {
			return false;
		}
		if (confirm("数据删除后不能恢复，确定删除吗？")) {
			jQuery
					.ajax({
						type : "POST",
						url : '${contextPath}/mx/oa/assesscontent/deleteType?assesssortid='
								+ assesssortid,
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
	}

	function saveData() {

		var bol = jQuery("#makeAssessIndex").form('validate');
		if (bol == false) {
			alert("请输入必须项！");
			return;
		}
		var params = jQuery("#makeAssessIndex").formSerialize();
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/assessquestion/saveMakeAssessIndex',
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
				if (window.opener) {
					window.opener.location.reload();
				} else if (window.parent) {
					window.parent.location.reload();
				}
			}
		});

	}
	function ruash(url) {
		if (url) {
			window.location.href = url;
		} else {
			window.location.href = window.location.href;
		}
	}
	function back() {
		window.location.href = "${contextPath}/mx/oa/assessquestion/view";
	}
</script>
<style type="text/css">
.tb1 {
	border-collapse: collapse;
	background-color: white;
}

.tb1 tr td {
	margin: 0px;
	border: 1px solid #CC9999;
	border-collapse: collapse;
}

.trb {
	border: 1px solid #CC9999;
	border-collapse: collapse;
}

.test {
	
}
</style>
</head>
<body style="margin: 1px;" class="bodyBg">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<span class="x_content_title"><strong>岗位考核指标制作</strong></span> <a
					href="#" class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-save'"
					onclick="javascript:saveData();">保存</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-cancel'"
					onclick="javascript:art.dialog.close()">关闭</a>
			</div>
		</div>

		<div data-options="region:'center',border:true" style="width: 960px;">
			<form name="makeAssessIndex" id="makeAssessIndex" method="post"
				onsubmit=" return checkAssess(this);"
				action="${contextPath}/mx/oa/assessquestion/saveMakeAssessIndex">
				<input type="hidden" name="questionId" id="questionId"
					value="${assessquestion.qustionid }" />
				<table width="100%" style="width: 940px;">
					<tr>
						<td>标题*:</td>
						<td colspan="4"><input type="text" name="title" id="title"
							value="${assessquestion.title }" style="width: 350px;"
							maxlength="50" class="easyui-validatebox"
							data-options="required:true,validType:'notNull[]'" /></td>
						<td>有效期</td>
						<td><input type="text" name="validDate" id="validDate"
							class="easyui-datebox" data-options="required:true"
							editable="false"
							value="<fmt:formatDate pattern="yyyy-MM-dd" value="${assessquestion.validdate }" type="both"/>" />
						</td>
					</tr>
					<tr>
						<td>频率</td>
						<td><select name="rate" id="rate" class="easyui-combobox"
							editable="false">
								<c:forEach var="rate" items="${frequencyList }">
									<c:choose>
										<c:when test="${assessRate eq  rate.code}">
											<option value="${rate.code }" selected>${rate.name}</option>
										</c:when>
										<c:otherwise>
											<option value="${rate.code }">${rate.name}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
						</select></td>
						<td colspan="2">是否有效： <select name="iseffective"
							id="iseffective" class="easyui-combobox" editable="false">
								<c:forEach var="valid" items="${isValidList }"
									varStatus="validIndex">
									<c:choose>
										<c:when test="${assessEffective eq  valid.code}">
											<option value="${valid.code}" selected>${valid.name}</option>
										</c:when>
										<c:otherwise>
											<option value="${valid.code}">${valid.name}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
						</select></td>
						<td colspan="3">&nbsp;</td>
					</tr>
				</table>
				<table width="100%">
					<c:forEach var="questionDetails"
						items="${assessquestion.questionDetails }" varStatus="qIndex">
						<c:set var="sortTree" value="${questionDetails.sortTree }"></c:set>
						<c:set var="assessSort" value="${questionDetails.assessSort }"></c:set>
						<input type="hidden" name="sortIds" value="${sortTree.bid }" />

						<tr>
							<td colspan="4" style="font-size: 15px; color: #0099CC">${questionDetails.index}、${sortTree.tree }</td>
							<td colspan="3" align="left"><a
								href="javascript:delAssessType(${assessSort.assesssortid});"
								class="easyui-linkbutton">删除指标类型</a> <a
								href="javascript:addAssessContent(${assessSort.assesssortid});"
								class="easyui-linkbutton">添加指标</a></td>
						</tr>
						<tr>
							<td colspan="7">
								<table class="tb1">
									<tr style="background-color: #FFCCCC">
										<td width="40px">序号</td>
										<td width="300px">指标</td>
										<td width="500px">评分依据</td>
										<td width="80px">评分标准</td>
										<td width="80px">操作</td>
									</tr>
									<c:forEach var="content"
										items="${questionDetails.contentList }" varStatus="cIndex">
										<tr>
											<td>${cIndex.index+1 }<input type="hidden"
												name="contentIds${sortTree.bid }"
												value="${content.contentid }" /></td>
											<td>${content.name }</td>
											<td>${content.basis }</td>
											<td><input type="text" value="${content.standard }"
												size="5" name="standards${sortTree.bid }"
												class="easyui-numberbox" style="width: 40px;" max="99"
												min="0.1" precision="1" data-options="required:true" /></td>
											<td align="center"><a
												href="javascript:deleteAssessContent(${content.contentid });"
												class="easyui-linkbutton">删除</a></td>
										</tr>
									</c:forEach>

								</table>
					</c:forEach>

				</table>
				<table width="100%">
					<tr>
						<td align="center" colspan="7"><a
							href="javascript:addAssessType();" class="easyui-linkbutton">添加指标类型</a></td>
						<script type="text/javascript">
							jQuery('#').val();
						</script>
					</tr>


				</table>
			</form>

			<!-- 			<table id="mydatagrid"></table> -->
		</div>
	</div>
	<div id="edit_dlg" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="editForm" name="editForm" method="post"></form>
	</div>
	<div id="dlg" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="searchForm" name="searchForm" method="post">
			<table class="easyui-form">


			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<!-- 	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:searchData()">查询</a> -->
		<!-- 	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:jQuery('#dlg').dialog('close')">取消</a> -->
	</div>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>