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
<title>出差申请审核</title>
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
	var contextPath = "${contextPath}";

	function passData() {
		if (confirm("确认审核通过吗？")) {
			var params = jQuery("#iForm").formSerialize();
			params = params + "&approveOpinion="
					+ jQuery('#approveOpinion').val().trim();

			jQuery.ajax({
				type : "POST",
				url : '${contextPath}/mx/oa/stravel/submitData',
				data : params,
				dataType : 'json',
				error : function(data) {
					alert('服务器处理错误！');
				},
				success : function(data) {
					if (data != null && data.message != null) {
						alert(data.message);
					} else {
						alert('审批成功！');
					}
					jQuery('#dlgApprove').dialog('close');
					if (window.opener) {
						window.opener.location.reload();
					} else if (window.parent) {
						window.parent.location.reload();
					}
				}
			});
		}
	}

	function noPassData() {
		if (jQuery('#approveOpinion').val().trim() == "") {
			alert("审批不通过需填写审批意见。");
			return;
		}
		if (confirm("确认审核不通过吗?")) {
			var params = jQuery("#iForm").formSerialize();
			params = params + "&approveOpinion="
					+ jQuery('#approveOpinion').val().trim();
			jQuery.ajax({
				type : "POST",
				url : '${contextPath}/mx/oa/stravel/rollbackData',
				data : params,
				dataType : 'json',
				error : function(data) {
					alert('服务器处理错误！');
				},
				success : function(data) {
					if (data != null && data.message != null) {
						alert(data.message);
					} else {
						alert('该出差单已退回！');
					}
					jQuery('#dlgApprove').dialog('close');
					if (window.opener) {
						window.opener.location.reload();
					} else if (window.parent) {
						window.parent.location.reload();
					}
				}
			});
		}
	}

	jQuery(function() {
		if(${lookover}){
		  jQuery("#reviewDiv").hide();
		}
	});

	function approveWin() {
		$('#dlgApprove').dialog('open').dialog({
			title : '审核',
			left : 420,
			top : 100,
			closed : false,
			modal : true
		});
		jQuery('#dlgApprove').form('clear');
	}

	//出差人员明细列表
	jQuery(function() {
		var travelid = jQuery('#travelid').val();
		jQuery('#mydatagrid1')
				.datagrid(
						{
							width : 650,
							height : 220,
							fit : true,
							fitColumns : true,
							nowrap : false,
							striped : true,
							collapsible : true,
							url : '${contextPath}/mx/oa/travelpersonnel/json?travelid='
									+ travelid,
							sortName : 'personnelid',
							sortOrder : 'desc',
							remoteSort : false,
							singleSelect : true,
							idField : 'personnelid',
							columns : [ [
									{
										title : '序号',
										field : 'startIndex',
										width : 40,
										sortable : false
									},
									{
										title : '出差部门',
										field : 'dept',
										width : 80,
										formatter : function(value, row, index) {
											var dept = "";
											jQuery
													.ajax({
														type : "POST",
														url : '${contextPath}/rs/dictory/deptJsonArray/012',
														dataType : 'json',
														async : false,
														success : function(data) {
															if (data != null
																	&& data.message != null) {
																alert(data.message);
															} else {
																for ( var i = 0; i < data.length; i++) {
																	if (data[i].code == value) {
																		dept = data[i].name;
																	}
																}
															}
														}
													});
											return dept;
										}
									}, {
										title : '出差人员',
										field : 'personnel',
										width : 150
									}, {
										title : '备注',
										field : 'remark',
										width : 150
									} ] ],
							rownumbers : false,
							pagination : false
						});

		var p = jQuery('#mydatagrid1').datagrid('getPager');
		jQuery(p).pagination({
			onBeforeRefresh : function() {
				//alert('before refresh');
			}
		});
	});

	//出差行程明细列表
	jQuery(function() {
		var travelid = jQuery('#travelid').val();
		jQuery('#mydatagrid2').datagrid(
				{
					width : 650,
					height : 220,
					fit : true,
					fitColumns : true,
					nowrap : false,
					striped : true,
					collapsible : true,
					url : '${contextPath}/mx/oa/traveladdress/json?travelid='
							+ travelid,
					sortName : 'addressid',
					sortOrder : 'desc',
					remoteSort : false,
					singleSelect : true,
					idField : 'addressid',
					columns : [ [ {
						title : '序号',
						field : 'startIndex',
						width : 40,
						sortable : false
					}, {
						title : '出发地点',
						field : 'startadd',
						width : 150
					}, {
						title : '到达地点',
						field : 'endadd',
						width : 150
					}, {
						title : '交通工具',
						field : 'transportation',
						width : 150
					} ] ],
					rownumbers : false,
					pagination : false
				});

		var p = jQuery('#mydatagrid2').datagrid('getPager');
		jQuery(p).pagination({
			onBeforeRefresh : function() {
				//alert('before refresh');
			}
		});
	});

	//预计费用明细列表
	jQuery(function() {
		var travelid = jQuery('#travelid').val();
		jQuery('#mydatagrid3').datagrid({
			width : 650,
			height : 220,
			fit : true,
			fitColumns : true,
			nowrap : false,
			striped : true,
			collapsible : true,
			url : '${contextPath}/mx/oa/travelfee/json?travelid=' + travelid,
			sortName : 'feeid',
			sortOrder : 'desc',
			remoteSort : false,
			singleSelect : true,
			idField : 'feeid',
			columns : [ [ {
				title : '序号',
				field : 'startIndex',
				width : 40,
				sortable : false
			}, {
				title : '费用类型',
				field : 'feename',
				width : 150
			}, {
				title : '金额',
				field : 'feesum',
				width : 150
			}, {
				title : '备注',
				field : 'remark',
				width : 150
			} ] ],
			rownumbers : false,
			pagination : false,
			onLoadSuccess : function(data) {
				jQuery('#feesumAccount').html(data.feesumAccount);
			}
		});

		var p = jQuery('#mydatagrid3').datagrid('getPager');
		jQuery(p).pagination({
			onBeforeRefresh : function() {
				//alert('before refresh');
			}
		});
	});
</script>
</head>

<body>
	<div style="margin: 0;"></div>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<span class="x_content_title" id="titleSpan">出差申请审核</span> <a
					id="reviewDiv" href="#" class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-submit'"
					onclick="javascript:approveWin()">审核</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-cancel'"
					onclick="javascript:art.dialog.close();">关闭</a>
			</div>
		</div>

		<div data-options="region:'center',border:false,cache:true">
			<form id="iForm" name="iForm" method="post">
				<input type="hidden" id="travelid" name="travelid"
					value="${stravel.travelid}" /> <input type="hidden"
					id="processInstanceId" name="processInstanceId"
					value="${stravel.processinstanceid}" />
				<table class="easyui-form" style="width: 940px;" align="center">
					<tbody>
						<tr>
							<td width="20%" align="left">申请单位：</td>
							<td align="left"><input id="company" class="easyui-combobox"
								name="company" value="${stravel.company}" editable="false"
								disabled="disabled"
								data-options="required:true,valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/${stravel.area}'" />
							</td>
							<td width="20%" align="left">地区：</td>
							<td align="left"><input id="area" class="easyui-combobox"
								name="area" value="${stravel.area}" disabled="disabled"
								data-options="required:true,valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/eara'" />
							</td>
							<td width="20%" align="left">部门：</td>
							<td align="left"><input id="dept" class="easyui-combobox"
								name="dept" value="${stravel.dept}" disabled="disabled"
								data-options="required:true,	valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'" />
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">申请人：</td>
							<td align="left"><input id="appuser" class="easyui-combobox"
								name="appuser" value="${stravel.appuser}" disabled="disabled"
								data-options="required:true,valueField:'code',textField:'name',url:'${contextPath}/mx/oa/baseData/getUserJson'" />
							</td>
							<td width="20%" align="left">职位：</td>
							<td align="left"><input id="post" name="post" type="text"
								disabled="disabled" class="easyui-validatebox"
								value="${stravel.post}" /></td>
							<td width="20%" align="left">申请日期：</td>
							<td align="left"><input id="appdate" name="appdate"
								type="text" disabled="disabled" class="easyui-datebox"
								data-options="required:true" value="${appdate}"
								pattern="yyyy-MM-dd" /></td>
						</tr>
						<tr>
							<td width="20%" align="left">起讫时间：</td>
							<td align="left" colspan="5"><input id="startdate"
								name="startdate" type="text" disabled="disabled"
								class="easyui-datebox" style="width: 130px"
								data-options="required:true" missingMessage="日期必须填写"
								editable="false" value="${startdate}" pattern="yyyy-MM-dd" />至
								<input id="enddate" name="enddate" type="text"
								disabled="disabled" class="easyui-datebox" style="width: 130px"
								data-options="required:true" missingMessage="日期必须填写"
								editable="false" value="${enddate}" pattern="yyyy-MM-dd" />共 <input
								id="travelsum" name="travelsum" type="text"
								data-options="required:true" disabled="disabled"
								class="easyui-numberbox" precision="2" style="width: 25px"
								value="${stravel.travelsum}" />天</td>
						</tr>
						<tr>
							<td width="20%" align="left">事由：</td>
							<td align="left" colspan="5"><textarea id="content"
									name="content" style="width: 99%" data-options="required:true"
									disabled="disabled" class="easyui-validatebox">${stravel.content}</textarea>
							</td>
						</tr>
						<tr>
							<td align="left" colspan="6">
								<div class="toolbar-backgroud">
									<img src="${contextPath}/images/window.png"> &nbsp;<span
										class="x_content_title">出差人员:</span>
								</div>
								<div data-options="region:'center',border:true"
									style="height: 120px">
									<table id="mydatagrid1">
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td align="left" colspan="6">
								<div class="toolbar-backgroud">
									<img src="${contextPath}/images/window.png"> &nbsp;<span
										class="x_content_title">出差行程:</span>
								</div>
								<div data-options="region:'center',border:true"
									style="height: 120px">
									<table id="mydatagrid2">
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">出差补贴：</td>
							<td align="left" colspan="5"><input type="radio"
								name="traveltype" id="traveltype"
								${stravel.traveltype==0?'checked=\"true\"':''} value="0"
								disabled="disabled">因公出差补助（国内）：60元/人/天 <input
								type="radio" name="traveltype" id="traveltype"
								${stravel.traveltype==1?'checked=\"true\"':''} value="1"
								disabled="disabled">因公出差补助（国外）：如属于权限内正常开销范畴，实报实销</td>
						</tr>
						<tr>
							<td align="left" colspan="6">
								<div class="toolbar-backgroud">
									<img src="${contextPath}/images/window.png"> &nbsp;<span
										class="x_content_title">预计费用（单位：人民币元）:</span>
								</div>
								<div data-options="region:'center',border:true"
									style="height: 120px">
									<table id="mydatagrid3">
									</table>
								</div>

								<div class="toolbar-backgroud"
									style="height: 15px; font-size: 13px;">
									合计: <span id="feesumAccount"></span>
								</div>
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">其他事项：</td>
							<td align="left" colspan="5"><textarea id="others"
									name="others" style="width: 99%" disabled="disabled"
									class="easyui-validatebox">${stravel.others}</textarea></td>
						</tr>
						<tr>
							<td width="20%" align="left">附件：</td>
							<td align="left" colspan="5"><a
								href="javascript:uploadFile(9, ${stravel.travelid}, 0)">附件上传</a>
								<jsp:include page="/others/attachment/showCount">
									<jsp:param name="referType" value="9" />
									<jsp:param name="referId" value="${stravel.travelid}" />
								</jsp:include></td>
						</tr>
						<tr>
							<td align="center" colspan="6"><%@ include
									file="/WEB-INF/views/oa/common/task.jsp"%>
							</td>

						</tr>

					</tbody>
				</table>
				<%@ include file="/WEB-INF/views/oa/common/approve_foot.jsp"%>
			</form>

		</div>
	</div>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>