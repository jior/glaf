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
<title>合同申请审核</title>
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
	var contextPath = "${contextPath}";
	var editcount = 0;
	var salesID = ${salescontract.id};
	
	jQuery(function() {
		var area = jQuery('#area').val();
		if (jQuery('#currency').val() == '') {
			jQuery('#currency').combobox('setValue', 'RMB');
		}
		if (jQuery('#paytype').val() == '') {
			jQuery('#paytype').combobox('setValue', '1');
		}
		if ("" != area) {
			var url = '${contextPath}/rs/dictory/jsonArray/' + area;
			jQuery('#companyname').combobox('reload', url);
		}
	});
	

	jQuery(function() {
		var contractid = jQuery('#id').val();
		if (contractid == "") {
			contractid = 0;
		}
		jQuery('#mydatagrid').datagrid({
			width : 880,
			height : 80,
			fit : true,
			fitColumns : true,
			nowrap : false,
			collapsible : true,
			url : '${contextPath}/mx/oa/optional/json?id=' + contractid,
			sortName : 'id',
			sortOrder : 'desc',
			remoteSort : false,
			singleSelect : true,
			idField : 'optionalid',
			columns : [ [ {
				title : '序号',
				field : 'startIndex',
				width : 120,
				sortable : false
			}, {
				title : '选配代码 ',
				field : 'code',
				width : 120,
				sortable : false,
				editor : {
					type : 'validatebox',
					options : {
						required : true
					}
				}
			}, {
				title : '选配价格',
				field : 'price',
				width : 120,
				sortable : false,
				editor : {
					type : 'numberbox',
					options : {
						required : true,
						precision : 2
					}
				}
			}, {
				title : '备注 ',
				field : 'remark',
				width : 120,
				sortable : false,
				editor : {
					type : 'text'
				}
			}, {
				title : '主键ID',
				field : 'optionalId',
				width : 120,
				sortable : false,
				hidden : true
			} ] ]

		});
	});

	
	function addNew() {
		if (editcount > 0) {
			jQuery.messager.alert("警告", "当前还有" + editcount + "记录正在编辑，不能增加记录。");
			return;
		}
		var index = jQuery('#mydatagrid').datagrid('getRows').length;
		jQuery('#mydatagrid').datagrid('appendRow', {
			startIndex : index + 1
		});
		jQuery('#mydatagrid').datagrid('beginEdit', index);
	}
	
	
	function saverow(index) {
		var bol = $("#mydatagrid").datagrid("validateRow", index);
		if (bol == false) {
			alert("请填写完整的选配信息");
			return;
		}

		var contractid = jQuery('#id').val();
		if (null == contractid || "" == contractid) {
			contractid = 0;
		}
		jQuery('#mydatagrid').datagrid("selectRow", index);
		var params = jQuery('#mydatagrid').datagrid("getSelected");

		jQuery('#mydatagrid').datagrid('endEdit', index);
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/optional/save?id=' + contractid,
			data : params,
			dataType : 'json',
			error : function(data) {
				alert('服务器处理错误！');
			},
			success : function(data) {
				var rows = $("#mydatagrid").datagrid("getRows");
				var optionalsum = 0;
				for ( var i = 0; i < rows.length; i++) {
					optionalsum = optionalsum + parseFloat(rows[i].price);
				}
				$("#optionalsum").numberbox('setValue',
						Math.round(optionalsum100) / 100);
				if (data != null && data.message != null) {
					alert(data.message);
				} else {
					alert('操作成功！');
				}
				jQuery('#mydatagrid').datagrid('reload');
			}
		});
	}
	
	
	function editrow(index) {
		if (editcount > 0) {
			jQuery.messager.alert("警告", "当前还有" + editcount + "记录正在编辑，暂不能编辑。");
			return;
		}
		jQuery('#mydatagrid').datagrid('beginEdit', index);
	}
	
	
	function cancelrow(index) {
		var bol = $("#mydatagrid").datagrid("validateRow", index);
		jQuery('#mydatagrid').datagrid("cancelEdit", index);
		jQuery('#mydatagrid').datagrid("endEdit", index);
		jQuery('#mydatagrid').datagrid('refreshRow', index);
		$("#mydatagrid").datagrid("selectRow", index);
		var rows = $("#mydatagrid").datagrid("getSelections");
		if (!(rows[0].optionalId)) {
			jQuery('#mydatagrid').datagrid('deleteRow', index);
		}
	}
	

	function deleteSelections() {
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].optionalId);
		}
		if (ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")) {
			var optionalIds = ids.join(',');
			jQuery.ajax({
				type : "POST",
				url : '${contextPath}/mx/oa/optional/delete?optionalIds='
						+ optionalIds,
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
		} else {
			alert("请选择至少一条记录。");
		}
	}

	function review() {
		//jQuery('#dlgApprove').dialog('open').dialog('setTitle','批量审批');
		$('#dlgApprove').dialog('open').dialog({
			title : '审核',
			left : 200,
			top : 100,
			closed : false,
			modal : true
		});
		jQuery('#dlgApprove').form('clear');
	}

	jQuery(function() {
		if(${lookover}){
		  jQuery("#reviewDiv").hide();
		  jQuery("#titleSpan2").html("售车合同申请查看");
		}
	});

	function passData() {
		if (confirm("确认审核通过吗？")) {
			var approveOpinion1 = jQuery('#approveOpinion').val();
			var approveOpinion = $.trim(approveOpinion1);
			var params = jQuery('#approveOpinion').serialize();
			jQuery
					.ajax({
						type : "POST",
						url : '${contextPath}/mx/oa/salescontract/submit?id='
								+ salesID,
						dataType : 'json',
						data : params,
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
							jQuery('#mydatagrid').datagrid('reload');
						}

					});
			jQuery('#dlgApprove').dialog('close');
		}
	}
	
	
	function noPassData() {
		var approveOpinion1 = jQuery('#approveOpinion').val();
		var approveOpinion = $.trim(approveOpinion1);
		if (approveOpinion == "") {
			alert("审批不通过需填写审批意见");
			return;
		}
		if (confirm("确认审核不通过吗？")) {
			var params = jQuery('#approveOpinion').serialize();
			jQuery.ajax({
				type : "POST",
				url : '${contextPath}/mx/oa/salescontract/rollbackData?id='
						+ salesID,
				dataType : 'json',
				data : params,
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
					jQuery('#mydatagrid').datagrid('reload');
				}
			});
			jQuery('#dlgApprove').dialog('close');
		}
	}
</script>
</head>

<body>
	<div style="margin: 0;"></div>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<span class="x_content_title" id='titleSpan2'>审核合同申请</span>
				
				<div style="display: inline" id="reviewDiv">
					<a href="#" class="easyui-linkbutton"
						data-options="plain:true, iconCls:'icon-submit'"
						onclick="javascript:review();">审核</a>
				</div>

			</div>
		</div>

		<div data-options="region:'center',border:false,cache:true">
			<form id="iForm" name="iForm" method="post">
				<input type="hidden" id="id" name="id" value="${salescontract.id}" />
				<input type="hidden" id="status" name="status"
					value="${salescontract.status}" />
				<table class="easyui-form" style="width: 900px;" align="center"
					border="0px ">
					<tbody>
						<tr>
							<td width="30%" align="left">合同编号</td>
							<td align="left"><input id="contractno" name="contractno"
								type="text" disabled class="easyui-validatebox"
								value="${salescontract.contractno}" maxlength="50" readonly />
							</td>
							<td width="30%" align="left">合同名称</td>
							<td align="left"><input id="contactname" name="contactname"
								type="text" class="easyui-validatebox" disabled
								value="${salescontract.contactname}" maxlength="100" readonly />
							</td>
							<td width="30%" align="left">项目名称</td>
							<td align="left" colspan="1"><input id="projrctname"
								name="projrctname" type="text" class="easyui-validatebox"
								disabled value="${salescontract.projrctname}" maxlength="100"
								readonly /></td>


						</tr>
						<tr>

							<td width="20%" align="left">地区</td>
							<td align="left" colspan="1"><input id="area"
								class="easyui-combobox" name="area" disabled
								value="${salescontract.area}" disabled
								data-options="valueField:'code',textField:'name',required:true,url:'${contextPath}/rs/dictory/jsonArray/eara',
										onSelect:function(ret){										
										var url = '${contextPath}/rs/dictory/jsonArray/'+ret.code;
											jQuery('#companyname').combobox('clear');
											jQuery('#companyname').combobox('reload',url);
											jQuery('#area').val(ret.code);
										}																	
										" />
							</td>
							<td width="30%" align="left">
								<div style="width: 80px;">我方签约单位</div>
							</td>
							<td align="left" colspan="1"><input id="companyname"
								class="easyui-combobox" name="companyname" disabled
								data-options="valueField:'code',textField:'name',required:true"
								value="${salescontract.companyname}" size='26' /></td>
							<td width="30%" align="left">
								<div style="width: 80px;">对方签约单位</div>
							</td>
							<td align="left" colspan="1"><input id="supplisername"
								name="supplisername" type="text" class="easyui-validatebox"
								disabled value="${salescontract.supplisername}" maxlength="100"
								readonly /></td>
						</tr>

						<tr>
							<td width="30%" align="left">币种</td>
							<td align="left"><input id="currency"
								class="easyui-combobox" name="currency"
								value="${salescontract.currency}" disabled
								data-options="valueField:'code',textField:'name',required:true,url:'${contextPath}/rs/dictory/jsonArray/money'" />
							</td>
							<td width="30%" align="left">付款方式</td>
							<td align="left"><input id="paytype" class="easyui-combobox"
								name="paytype" value="${salescontract.paytype}" disabled
								data-options="valueField:'code',textField:'name',required:true,url:'${contextPath}/rs/dictory/jsonArray/paytype'" />

							</td>
							<td width="30%" align="left"">合同金额</td>
							<td align="left"><input id="contractsum" name="contractsum"
								type="text" class="easyui-numberbox" precision="2" disabled
								value="${salescontract.contractsum}" maxlength="15" readonly />
							</td>
						</tr>
						<tr>
							<td width="30%" align="left">备注:</td>
						</tr>
						<tr>
							<td colspan="6">
								<div
									style="width: 860px; height: 290px; border: 1px solid #CCCCCC;">
									<table width="100%">

										<tr>
											<td width="30%" align="left"></td>
											<td><input id="optionalsum" name="optionalsum"
												type="text" class="easyui-numberbox" precision="2" disabled
												value="${salescontract.optionalsum}" maxlength="50" readonly />
											</td>
											<td width="30%" align="left">首付款</td>
											<td><input id="firstpay" name="firstpay" type="text"
												class="easyui-numberbox" precision="2" disabled
												value="${salescontract.firstpay}" maxlength="50" readonly />
											</td>
											<td width="30%" align="left">尾款</td>
											<td><input id="lastpay" name="lastpay" type="text"
												class="easyui-numberbox" precision="2" disabled
												value="${salescontract.lastpay}" maxlength="50" readonly />
											</td>
										</tr>
										<tr>
											<td width="30%" align="left"></td>
											<td></td>
											<td width="30%" align="left"></td>
											<td></td>
											<td width="30%" align="left"></td>
											<td></td>
										</tr>
										<tr>
											<td width="30%" align="left"></td>
											<td></td>
											<td width="30%" align="left">销售顾问</td>
											<td><input id="sales" name="sales" type="text" disabled
												class="easyui-validatebox" value="${salescontract.sales}"
												maxlength="50" readonly /></td>
											<td width="30%" align="left">合同留存销售顾问</td>
											<td><input id="contractsales" name="contractsales"
												type="text" class="easyui-validatebox" disabled
												value="${salescontract.contractsales}" maxlength="50"
												readonly /></td>
										</tr>
										<tr>
											<td width="30%" align="left"></td>
											<td></td>
											<td width="30%" align="left"></td>
											<td></td>
											<td width="30%" align="left">备注条款</td>
											<td><input id="remark" name="remark" type="text"
												class="easyui-validatebox" value="${salescontract.remark}"
												maxlength="50" readonly /></td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2"><a
								href="javascript:uploadFile(3, ${salescontract.id}, 0)">附件下载</a>
								<jsp:include page="/others/attachment/showCount">
									<jsp:param name="referType" value="3" />
									<jsp:param name="referId" value="${salescontract.id}" />
								</jsp:include></td>
							</td>
						</tr>
						<tr>
							<td width="30%" align="left">申请人*</td>
							<td align="left"><input type="hidden" id="appuser"
								name="appuser" value="${salescontract.appuser}" /> <input
								id="appusername" name="appusername" type="text"
								class="easyui-validatebox" value="${appusername}"
								onclick="javascript:selectUserContainHeadShip('iForm', 'appuser','appusername','headship','dept');"
								disabled /></td>
							<td>职位</td>
							<td><input id="headship" name="headship" type="text"
								class="easyui-validatebox" value='${salescontract.headship}'
								readonly /> <input type="hidden" id='dept' name='dept'>
							</td>
							<td width="20%" align="left">部门</td>
							<td><input id="dept" class="easyui-combobox" name="dept"
								value="${salescontract.dept}" disabled
								data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'" />
							</td>
						</tr>
						<tr>
							<td colspan="6"><input type="hidden" id="processInstanceId"
								name="processInstanceId"
								value="${salescontract.processinstanceid}" />
								<div style="width: 850px;">
									<%@ include file="/WEB-INF/views/oa/common/task.jsp"%>
								</div></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/oa/common/approve_foot.jsp"%>
</body>
</html>