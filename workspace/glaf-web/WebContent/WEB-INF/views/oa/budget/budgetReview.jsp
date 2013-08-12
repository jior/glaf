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
	var budgetid = "${budget.budgetid}";
	var editcount = 0;
	var lookover = "${lookover}";

	jQuery(function() {
		if (lookover == 'true') {
			jQuery("#reviewDiv").hide();
			jQuery("#titleSpan2").html("支出预算申请查看");
		}
	});
	jQuery(function() {
		if (jQuery('#currency').val() == '') {
			jQuery('#currency').combobox('setValue', 'RMB');
		}
		if (jQuery('#paymentmodel').val() == '') {
			jQuery('#paymentmodel').combobox('setValue', '1');
		}
		if (jQuery('#paymenttype').val() == '') {
			jQuery('#paymenttype').combobox('setValue', '1');
		}

		if (jQuery('#paymenttype').combobox('getValue') == '1') {
			jQuery('#fqDiv').hide();
			jQuery('#fqtr').hide();
		}
		if (jQuery('#paymenttype').combobox('getValue') == '2') {
			jQuery('#onceDiv').hide();
			loadTable();
		}
	});

	function saveData() {
		if (jQuery('#paymenttype').combobox('getValue') == '1') {
			jQuery('#paymentdate').datebox({
				required : true
			});
		} else {
			jQuery('#paymentdate').datebox({
				required : false
			});
			if (editcount > 0) {
				alert("还有记录处于编辑状态,请先取消编辑");
				return;
			}
			var rows = jQuery('#mydatagrid').datagrid('getRows');
			if (rows.length < 2) {
				alert("分期至少两条记录");
				return;
			}
			var totalMoney = 0;
			for ( var i = 0; i < rows.length; i++) {
				totalMoney += rows[i].paymemtsum
			}
			alert(totalMoney);
			alert(totalMoney - jQuery('#budgetsum').val());
			if (totalMoney - jQuery('#budgetsum').val() != 0) {
				alert("支出预算总额与分期付款总数不相等,请检查输入");
				return;
			}

		}
		var bol = jQuery("#iForm").form('validate');
		if (bol == false) {
			jQuery.messager.alert("警告", "请检查输入。");
			return;
		}
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/budget/saveBudget',
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

	function changePaymenttype(record) {
		if (jQuery('#paymenttype').combobox('getValue') == '1') {
			jQuery('#fqDiv').hide();
			jQuery('#fqtr').hide();
			jQuery('#onceDiv').show();
		} else {
			jQuery('#onceDiv').hide();
			jQuery('#fqDiv').show();
			jQuery('#fqtr').show();
			loadTable();
		}
	}

	function loadTable() {
		jQuery(function() {
			jQuery('#mydatagrid').datagrid(
					{
						fit : true,
						fitColumns : true,
						nowrap : true,
						striped : true,
						collapsible : true,
						url : '${contextPath}/mx/oa/paymentplan/json?budgetid='
								+ budgetid,
						sortName : 'sequence',
						sortOrder : 'asc',
						remoteSort : false,
						singleSelect : true,
						autoRowHeight : false,
						idField : 'planid',
						columns : [ [ {
							title : '分期',
							field : 'sequence',
							width : 80,
							sortable : true,
							editor : {
								type : 'numberbox',
								options : {
									required : true
								}
							}
						}, {
							title : '付款金额',
							field : 'paymemtsum',
							width : 80,
							sortable : false,
							editor : {
								type : 'numberbox',
								options : {
									required : true,
									precision : 2
								}
							}
						}, {
							title : '付款日期',
							field : 'paymentdate',
							width : 80,
							sortable : false,
							editor : {
								type : 'datebox',
								options : {
									required : true,
									editable : false
								}
							}
						} ] ],
						rownumbers : false
					});
		});
	}
	
	
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
			alert("请填写完整的付款信息");
			return;
		}
		var rows = jQuery('#mydatagrid').datagrid('getRows');
		jQuery('#mydatagrid').datagrid("selectRow", index);
		var params = jQuery('#mydatagrid').datagrid("getSelected");
		jQuery('#mydatagrid').datagrid('endEdit', index);
		for ( var i = 0; i < rows.length; i++) {
			if (rows[i].sequence == params.sequence
					&& rows[i].planid != params.planid) {
				alert("请检查分期序号!");
				jQuery('#mydatagrid').datagrid('beginEdit', index);
				return;
			}
		}
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/paymentplan/save?budgetid=' + budgetid,
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
		if (!(rows[0].planid)) {
			jQuery('#mydatagrid').datagrid('deleteRow', index);
		}
	}
	
	
	function deleteSelections() {
		if (editcount > 0) {
			jQuery.messager.alert("警告", "当前还有" + editcount
					+ "记录正在编辑，请先取消编辑后，执行删除。");
			return;
		}
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if (rows.length > 0) {
			if (confirm("数据删除后不能恢复，确定删除吗？")) {
				jQuery.ajax({
					type : "POST",
					url : '${contextPath}/mx/oa/paymentplan/delete?planid='
							+ rows[0].planid,
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

	function passData() {
		if (confirm("确认审核通过吗？")) {
			var params = jQuery('#approveOpinion').serialize();
			jQuery
					.ajax({
						type : "POST",
						url : '${contextPath}/mx/oa/budget/submit?budgetid='
								+ budgetid,
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
						}
					});
			jQuery('#dlgApprove').dialog('close');
		}
	}
	
	
	function noPassData() {

		if (jQuery('#approveOpinion').val().trim() == "") {
			alert("审批不通过需填写审批意见");
			return;
		}
		if (confirm("确认审核不通过吗？")) {
			var params = jQuery('#approveOpinion').serialize();
			jQuery.ajax({
				type : "POST",
				url : '${contextPath}/mx/oa/budget/rollback?budgetid='
						+ budgetid,
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
				}
			});
			jQuery('#dlgApprove').dialog('close');
		}
	}
	function closeDialog() {
		window.close();
		window.parent.location.reload();
	}
</script>
</head>

<body>
	<div style="margin: 0;"></div>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<span class="x_content_title" id="titleSpan2">支出预算申请审核</span>
				
				<div style="display: inline" id="reviewDiv">
					<a href="#" class="easyui-linkbutton"
						data-options="plain:true, iconCls:'icon-submit'"
						onclick="javascript:review();">审核</a>
				</div>
				
			</div>
		</div>

		<div data-options="region:'center',border:false,cache:true">
			<form id="iForm" name="iForm" method="post">
				<input type="hidden" id="budgetid" name="budgetid"
					value="${budget.budgetid}" />
				<table class="easyui-form" style="width: 880px; height: 100%"
					align="center" cellspacing="3" cellpadding="3" scorll="auto">
					<tbody>
						<tr>
							<td width="20%" align="left">预算编号</td>
							<td align="left"><input id="budgetno" name="budgetno"
								type="text" readonly disabled data-options="required:true"
								class="easyui-validatebox" value="${budget.budgetno}" /></td>
							<td width="20%" align="center">
								<div style="width: 35px">地区</div>
							</td>
							<td align="left"><input id="area" class="easyui-combobox"
								name="area" size="12" value="${budget.area}" disabled
								data-options="valueField:'code',textField:'name',required:true,editable:false,url:'${contextPath}/rs/dictory/jsonArray/eara',
					onSelect:function(ret){										
					var url = '${contextPath}/rs/dictory/jsonArray/'+ret.code;
						jQuery('#company').combobox('clear');
						jQuery('#company').combobox('reload',url);
						jQuery('#area').val(ret.code);
					}																	
					" />
							</td>
							<td width="20%" align="center">单位</td>
							<td align="left"><input id="company" class="easyui-combobox"
								name="company" readonly="readonly" disabled
								data-options="valueField:'code',textField:'name',editable:false,required:true,url:'${contextPath}/rs/dictory/jsonArray/${budget.area}'"
								value="${budget.company}" size='25' /></td>
						</tr>
						<tr>
							<td width="20%" align="left">申请人</td>
							<td align="left"><input id="appuser" class="easyui-combobox"
								name="appuser" value="${budget.appuser}" size="12" disabled
								data-options="required:true,valueField:'code',textField:'name',url:'${contextPath}/mx/oa/baseData/getUserJson',
onSelect: function(rec){ 
jQuery.ajax({
   type: 'POST',
   url: '${contextPath}/mx/oa/baseData/getSingleUserJson',
   data: {'userCode':rec.code},
   dataType:  'json',
   error: function(data){
   alert('服务器处理错误！');
   },
   success: function(data){
   if(jQuery('#area')!=null) {
    $('#area').combobox('setValue',data.area);
   }
   if(jQuery('#dept')!=null) {
    $('#dept').combobox('setValue',data.deptCode);
   }
   if(jQuery('#post')!=null) {
    jQuery('#post').val(data.headship);
   }
   if(jQuery('#companyname')!=null){
   		jQuery('#company').combobox('setValue','');
   }
   var url = '${contextPath}/rs/dictory/jsonArray/'+data.area;    
                                $('#company').combobox('reload', url);
   }
 });
}" />
							</td>
							<td width="20%" align="center">职位</td>
							<td align="left"><input id="post" name="post" type="text"
								disabled class="easyui-validatebox" value="${budget.post}"
								readonly /></td>
							<td width="20%" align="center">部门</td>
							<td align="left"><input id="dept" class="easyui-combobox"
								name="dept" value="${budget.dept}" disabled
								data-options="valueField:'code',textField:'name',required:true,url:'${contextPath}/rs/dictory/deptJsonArray/012'" />
							</td>
						</tr>
						<tr>
							<td align="left">
								<div style="width: 80px;">申请项目名称</div>
							</td>
							<td align="left" colspan="5"><input id="proname"
								name="proname" type="text" readonly disabled
								data-options="required:true" class="easyui-validatebox" size=70
								value="${budget.proname}" /></td>
						</tr>
						<tr>
							<td align="left">申请项目内容</td>
							<td align="left" colspan="5"><textarea id="procontent"
									name="procontent" readonly disabled
									data-options="required:true" class="easyui-validatebox"
									rows="2" cols="80">${budget.procontent}</textarea></td>
						</tr>

						<tr>
							<td width="20%" align="left">支出预算总额</td>
							<td align="left"><input id="budgetsum" name="budgetsum"
								type="text" readonly disabled data-options="required:true"
								class="easyui-numberbox" precision="2" size=12
								value="${budget.budgetsum}" /></td>
							<td width="20%" align="center">币种</td>
							<td align="left"><input id="currency"
								class="easyui-combobox" name="currency"
								value="${budget.currency}" size=12 disabled
								data-options="valueField:'code',textField:'name',editable:false,required:true,url:'${contextPath}/rs/dictory/jsonArray/money'" />
							</td>
							<td width="20%" align="left">
								<div style="width: 60px;"></div>付款方式
							</td>
							<td align="left"><input id="paymentmodel"
								class="easyui-combobox" disabled name="paymentmodel"
								value="${budget.paymentmodel}" size=15
								data-options="valueField:'code',textField:'name',editable:false,required:true,url:'${contextPath}/rs/dictory/jsonArray/paytype'" />
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">支付方式</td>
							<td align="left"><input id="paymenttype"
								class="easyui-combobox" disabled name="paymenttype"
								value="${budget.paymenttype}" size=15
								data-options="valueField:'code',textField:'name',editable:false,required:true,url:'${contextPath}/rs/dictory/jsonArray/paymenttype',onSelect:changePaymenttype" />
							</td>
							<td colspan="2">
								<div style="display: inline; width: 99%" id="fqDiv">
									<table>

									</table>
								</div>
							</td>
							<td colspan="2">
								<div style="display: inline" id="onceDiv">
									<table>
										<tr>
											<td>付款期限</td>
											<td><input id="paymentdate1" name="paymentdate1"
												type="text" class="easyui-datebox" disabled
												value="<fmt:formatDate value='${budget.paymentdate}' pattern='yyyy-MM-dd'/>" />
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr id="fqtr">
							<td colspan="6">
								<div
									style="width: 90%; height: 160px; overflow-y: auto; border: 1px solid #CCCCCC">
									<table width="99%">
										<tr>
											<td>
												<div style="width: 100%; height: 120px; overflow-y: auto;">
													<table id="mydatagrid" border="1px" width="99%"></table>
												</div>

											</td>

										</tr>

									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">备注</td>
							<td align="left" colspan="3"><input id="remark"
								name="remark" type="text" size="40" disabled
								class="easyui-validatebox" value="${budget.remark}" readonly />

							</td>
							<td colspan="2"><a
								href="javascript:uploadFile(5, ${budget.budgetid}, 0)">附件下载</a>
								<jsp:include page="/others/attachment/showCount">
									<jsp:param name="referType" value="5" />
									<jsp:param name="referId" value="${budget.budgetid}" />
								</jsp:include></td>
						</tr>
						<tr>
							<td colspan="2">收款公司/支票抬头人信息：</td>
						</tr>
						<tr>
							<td colspan="6">
								<div style="width: 99%; border: 1px solid #CCCCCC">
									<table width="99%">
										<tr>
											<td width="20%" align="left">开户名:</td>
											<td align="left"><input id="supname" name="supname"
												type="text" size=30 readonly data-options="required:true"
												disabled class="easyui-validatebox"
												value="${budget.supname}" /></td>
											<td width="20%" align="left">账号:</td>
											<td align="left"><input id="supaccount"
												name="supaccount" type="text" data-options="required:true"
												size=30 readonly disabled class="easyui-validatebox"
												value="${budget.supaccount}" /></td>

										</tr>
										<tr>
											<td width="20%" align="left">开户行:</td>
											<td align="left"><input id="supbank" name="supbank"
												type="text" size=30 disabled readonly
												data-options="required:true" class="easyui-validatebox"
												value="${budget.supbank}" /></td>
											<td width="20%" align="left">地址:</td>
											<td align="left"><input id="supaddress"
												name="supaddress" type="text" disabled
												data-options="required:true" size=30 readonly
												class="easyui-validatebox" value="${budget.supaddress}" />
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="6">
								<div style="width: 100%">
									<input type="hidden" id="processInstanceId"
										name="processInstanceId" value="${budget.processinstanceid}" />
									<%@include file="/WEB-INF/views/oa/common/task.jsp"%>
								</div>

							</td>
						</tr>
					</tbody>
				</table>

			</form>
			<%@ include file="/WEB-INF/views/oa/common/approve_foot.jsp"%>
		</div>
	</div>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>