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
			var value = jQuery('#paymentdate1').datebox('getValue');
			jQuery('#paymentdate1').datebox({
				required : true
			});
			jQuery('#paymentdate1').datebox('setValue', value);
		} else {
			jQuery('#paymentdate1').datebox({
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
			if (totalMoney - jQuery('#budgetsum').val() != 0) {
				alert("支出预算总额与分期付款总数不相等,请检查输入");
				return;
			}

		}
		var bol = jQuery("#iForm").form('validate');
		if (bol == false) {
			alert("请检查输入！");
			return;
		}
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/budget/saveBudget',
			data : params,
			dataType : 'json',
			async : false,
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

	function saveAsData() {
		 
		if (jQuery('#paymenttype').combobox('getValue') == '1') {
			var value = jQuery('#paymentdate1').datebox('getValue');
			jQuery('#paymentdate1').datebox({
				required : true
			});
			jQuery('#paymentdate1').datebox('setValue', value);
		} else {
			jQuery('#paymentdate1').datebox({
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
			if (totalMoney - jQuery('#budgetsum').val() != 0) {
				alert("支出预算总额与分期付款总数不相等,请检查输入");
				return;
			}

		}
		var bol = jQuery("#iForm").form('validate');
		if (bol == false) {
			alert("请检查输入！");
			return;
		}
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/budget/save',
			data : params,
			dataType : 'json',
			async : false,
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
			jQuery('#mydatagrid')
					.datagrid(
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
								columns : [ [
										{
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
										},
										{
											title : '付款金额',
											field : 'paymemtsum',
											width : 80,
											sortable : false,
											editor : {
												type : 'numberbox',
												options : {
													required : true,
													precision : 2,

													max : 99999999,
													min : 0.01
												}
											}
										},
										{
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
										},
										{
											field : 'functionKey',
											title : '功能键',
											width : 120,
											formatter : function(value, row,
													index) {
												if (row.editing) {
													var s = "<a href='#' onclick='saverow("
															+ index
															+ ")'>保存</a> ";
													var c = "<a href='#' onclick='cancelrow("
															+ index
															+ ")'>取消</a> ";
													return s + c;
												} else {
													var e = "<a href='#' onclick='editrow("
															+ index
															+ ")'>编辑</a> ";
													return e;
												}
											}
										} ] ],
								rownumbers : false,
								onBeforeEdit : function(index, row) {
									row.editing = true;
									jQuery('#mydatagrid').datagrid(
											'refreshRow', index);
									editcount++;
								},
								onAfterEdit : function(index, row) {
									row.editing = false;
									jQuery('#mydatagrid').datagrid(
											'refreshRow', index);
									editcount--;
								},
								onCancelEdit : function(index, row) {
									row.editing = false;
									jQuery('#mydatagrid').datagrid(
											'refreshRow', index);
									editcount--;
								}
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
			async : false,
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
				<span class="x_content_title">支出预算申请</span> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-save'"
					onclick="javascript:saveData();">保存</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-submit'"
					onclick="javascript:saveAsData();">提交</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-cancel'"
					onclick="javascript:closeDialog();">关闭</a>

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
								type="text" maxlength="100" validType="notNull[]"
								data-options="required:true" class="easyui-validatebox"
								value="${budget.budgetno}" /></td>
							<td width="20%" align="center">地区</td>
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
								name="company" readonly="readonly"
								data-options="valueField:'code',textField:'name',editable:false,required:false,url:'${contextPath}/rs/dictory/jsonArray/${budget.area}'"
								value="${budget.company}" size='25' /></td>
						</tr>
						<tr>
							<td align="left">申请人</td>
							<td align="left"><input id="appuser" class="easyui-combobox"
								name="appuser" value="${budget.appuser}" size="12"
								data-options="required:true,valueField:'code',textField:'name',url:'${contextPath}/mx/oa/baseData/getUserJson',
onSelect: function(rec){ 
jQuery.ajax({
   type: 'POST',
   url: '${contextPath}/mx/oa/baseData/getSingleUserJson',
   data: {'userCode':rec.code},
   dataType:  'json',
   async:false,
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
   if(jQuery('#company')!=null){
   		jQuery('#company').combobox('setValue','');
   }
   var url = '${contextPath}/rs/dictory/jsonArray/'+data.area;    
                                $('#company').combobox('reload', url);
   }
 });
},onChange: function(rec){ 
jQuery.ajax({
   type: 'POST',
   url: '${contextPath}/mx/oa/baseData/getSingleUserJson',
   data: {'userCode':rec.code},
   dataType:  'json',
   async:false,
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
   if(jQuery('#company')!=null){
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
								class="easyui-validatebox" value="${budget.post}" readonly /></td>
							<td width="20%" align="center">部门</td>
							<td align="left"><input id="dept" class="easyui-combobox"
								name="dept" value="${budget.dept}" disabled
								data-options="valueField:'code',textField:'name',required:true,url:'${contextPath}/rs/dictory/deptJsonArray/012'" />
							</td>
						</tr>
						<tr>
							<td align="left">申请项目名称</td>
							<td align="left" colspan="5"><input id="proname"
								name="proname" type="text" maxlength="100" validType="notNull[]"
								data-options="required:true" class="easyui-validatebox" size=70
								value="${budget.proname}" /></td>
						</tr>
						<tr>
							<td align="left">申请项目内容</td>
							<td align="left" colspan="5"><textarea id="procontent"
									name="procontent" validType="notNullAndLength[200]"
									data-options="required:true" class="easyui-validatebox"
									rows="2" cols="80">${budget.procontent}</textarea></td>
						</tr>

						<tr>
							<td width="20%" align="left">支出预算总额</td>
							<td align="left"><input id="budgetsum" name="budgetsum"
								type="text" maxlength="11" data-options="required:true"
								class="easyui-numberbox" min="0.01" max="99999999" precision="2"
								size=12 value="${budget.budgetsum}" /></td>
							<td width="20%" align="center">币种</td>
							<td align="left"><input id="currency"
								class="easyui-combobox" name="currency"
								value="${budget.currency}" size=12
								data-options="valueField:'code',textField:'name',editable:false,required:true,url:'${contextPath}/rs/dictory/jsonArray/money'" />
							</td>
							<td width="20%" align="left">付款方式</td>
							<td align="left"><input id="paymentmodel"
								class="easyui-combobox" name="paymentmodel"
								value="${budget.paymentmodel}" size=15
								data-options="valueField:'code',textField:'name',editable:false,required:true,url:'${contextPath}/rs/dictory/jsonArray/paytype'" />
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">支付方式</td>
							<td align="left"><input id="paymenttype"
								class="easyui-combobox" name="paymenttype"
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
												type="text" class="easyui-datebox"
												data-options='editable:false'
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
											<td align="left"><a href="#" class="easyui-linkbutton"
												data-options="plain:true, iconCls:'icon-add'"
												onclick="javascript:addNew();">新增</a> <a href="#"
												class="easyui-linkbutton"
												data-options="plain:true, iconCls:'icon-remove'"
												onclick="javascript:deleteSelections();">删除</a></td>
										</tr>
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
								name="remark" type="text" size="40" class="easyui-validatebox"
								value="${budget.remark}" validType="Maxlength[200]" /></td>
							<td colspan="2"><a
								href="javascript:uploadFile(5, ${budget.budgetid}, 1)">附件上传</a>
								(共<span id="numAttachment5" name="numAttachment5">0</span>个) <script
									type="text/javascript">
									jQuery(function() {
										jQuery
												.get(
														'${contextPath}/others/attachment.do?method=getCount&referId=${budget.budgetid}&referType=5',
														{
															qq : 'xx'
														},
														function(data) {
															document
																	.getElementById("numAttachment5").innerHTML = data.message;
														}, 'json');
									});
								</script></td>
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
												type="text" size=30 maxlength="100"
												data-options="required:true" class="easyui-validatebox"
												value="${budget.supname}" /></td>
											<td width="20%" align="left">账号:</td>
											<td align="left"><input id="supaccount"
												name="supaccount" type="text" data-options="required:true"
												size=30 maxlength="100" class="easyui-validatebox"
												value="${budget.supaccount}" /></td>

										</tr>
										<tr>
											<td width="20%" align="left">开户行:</td>
											<td align="left"><input id="supbank" name="supbank"
												type="text" size=30 maxlength="100"
												data-options="required:true" class="easyui-validatebox"
												value="${budget.supbank}" /></td>
											<td width="20%" align="left">地址:</td>
											<td align="left"><input id="supaddress"
												name="supaddress" type="text" maxlength="100"
												data-options="required:true" size=30
												class="easyui-validatebox" value="${budget.supaddress}" />
											</td>
										</tr>


									</table>
								</div>
							</td>
						</tr>

					</tbody>
				</table>
			</form>
		</div>
	</div>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>