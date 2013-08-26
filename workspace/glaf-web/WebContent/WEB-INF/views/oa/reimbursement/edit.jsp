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
	
	function closeDialog() {
		window.close();
		window.parent.location.reload();
	}
	
	function saveData() {
		var params = jQuery("#iForm").formSerialize();
		var bol = jQuery("#iForm").form('validate');
		if (bol == false) {
			alert("请检查输入！");
			return;
		}

		var appsum = jQuery('#appsum').val();
		if (appsum == "" || appsum == 0) {
			alert("请填写报销明细信息。");
			return;
		}
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/reimbursement/save',
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
		var params = jQuery("#iForm").formSerialize();
		var bol = jQuery("#iForm").form('validate');
		if (bol == false) {
			alert("请检查输入！");
			return;
		}
		var appsum = jQuery('#appsum').val();
		if (appsum == "" || appsum == 0) {
			alert("请填写报销明细信息。");
			return;
		}
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/reimbursement/submit',
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

	//明细列表
	var editcount = 0;
	jQuery(function() {
		var reimbursementid = jQuery('#reimbursementid').val();
		jQuery('#mydatagrid')
				.datagrid(
						{
							width : 650,
							height : 220,
							fit : true,
							fitColumns : true,
							nowrap : false,
							striped : true,
							collapsible : true,
							url : '${contextPath}/mx/oa/ritem/json?reimbursementid='
									+ reimbursementid,
							sortName : 'id',
							sortOrder : 'desc',
							remoteSort : false,
							singleSelect : true,
							idField : 'ritemid',
							columns : [ [
									{
										title : '序号',
										field : 'startIndex',
										width : 40,
										sortable : false
									},
									{
										title : '费用类型',
										field : 'feetype',
										width : 80,
										editor : {
											type : 'combobox',
											options : {
												required : true,
												editable : false,
												url : '${contextPath}/rs/dictory/jsonArray/FYLX',
												valueField : 'code',
												textField : 'name'
											}
										},
										formatter : function(value, row, index) {
											var feetype = "";
											jQuery
													.ajax({
														type : "POST",
														url : '${contextPath}/rs/dictory/jsonArray/FYLX',
														dataType : 'json',
														async : false,
														success : function(data) {
															if (data != null
																	&& data.message != null) {
																alert(data.message);
															} else {
																for ( var i = 0; i < data.length; i++) {
																	if (data[i].code == value) {
																		feetype = data[i].name;
																	}
																}
															}
														}
													});
											return feetype;
										}
									},
									{
										title : '日期',
										field : 'feedate',
										width : 100,
										editor : {
											type : 'datebox',
											options : {
												required : true,
												editable : false
											}
										}
									},
									{
										title : '事由',
										field : 'subject',
										width : 150,
										editor : {
											type : 'validatebox',
											options : {
												required : true,
												validType : 'notNullAndLength[100]'
											}
										}
									},
									{
										title : '币种',
										field : 'currency',
										width : 60,
										editor : {
											type : 'combobox',
											options : {
												required : true,
												editable : false,
												url : '${contextPath}/rs/dictory/jsonArray/money',
												valueField : 'code',
												textField : 'name'
											}
										},
										formatter : function(value, row, index) {
											var currency = "";
											jQuery
													.ajax({
														type : "POST",
														url : '${contextPath}/rs/dictory/jsonArray/money',
														dataType : 'json',
														async : false,
														success : function(data) {
															if (data != null
																	&& data.message != null) {
																alert(data.message);
															} else {
																for ( var i = 0; i < data.length; i++) {
																	if (data[i].code == value) {
																		currency = data[i].name;
																	}
																}
															}
														}
													});
											return currency;
										}
									},
									{
										title : '金额',
										field : 'itemsum',
										width : 60,
										editor : {
											type : 'numberbox',
											options : {
												required : true,
												min : 1,
												precision : 0,
												max : 99999999
											}
										}
									},
									{
										title : '汇率',
										field : 'exrate',
										width : 50,
										editor : {
											type : 'numberbox',
											options : {
												required : true,
												min : 0.01,
												precision : 2,
												max : 99
											}
										}
									},
									{
										title : '等价人民币金额',
										field : 'createby',
										width : 100,
										formatter : function(value, row, index) {
											if (row.itemsum && row.exrate) {
												return accMul(row.exrate,
														row.itemsum);
											} else {
												return 0;
											}
											//	return row.itemsum * row.exrate==NaN?0:row.itemsum * row.exrate  ;
										}
									},
									{
										title : '操作',
										field : 'functionKey',
										width : 70,
										formatter : function(value, row, index) {
											if (row.editting) {
												var s = "<a href='#' onclick='saverow("
														+ index + ")'>保存</a> ";
												var c = "<a href='#' onclick='cancelrow("
														+ index + ")'>取消</a> ";
												return s + c;
											} else {
												var e = "<a href='#' onclick='editrow("
														+ index + ")'>编辑</a> ";
												return e;
											}
										}
									} ] ],
							rownumbers : false,
							pagination : false,
							//pageSize:15,
							//pageList: [10,15,20,25,30,40,50,100]
							onBeforeEdit : function(index, row) {
								row.editting = true;
								jQuery('#mydatagrid').datagrid('refreshRow',
										index);
								editcount++;
							},
							onAfterEdit : function(index, row) {
								row.editting = false;
								jQuery('#mydatagrid').datagrid('refreshRow',
										index);
								editcount--;
							},
							onCancelEdit : function(index, row) {
								row.editting = false;
								jQuery('#mydatagrid').datagrid('refreshRow',
										index);
								editcount--;
							}
						});

		var p = jQuery('#mydatagrid').datagrid('getPager');
		jQuery(p).pagination({
			onBeforeRefresh : function() {
				//alert('before refresh');
			}
		});
	});

	//明细增加
	function addNew() {
		if (editcount > 0) {
			alert("当前还有" + editcount + "记录正在编辑，不能增加记录。");
			return;
		}
		var index = jQuery("#mydatagrid").datagrid("getRows").length;
		jQuery("#mydatagrid").datagrid("appendRow", {});
		jQuery("#mydatagrid").datagrid("beginEdit", index);
	}
	
	//明细删除
	function deleteSelections() {
		if (editcount > 0) {
			alert("当前还有" + editcount + "记录正在编辑，暂不能删除。");
			return;
		}
		var reimbursementid = jQuery('#reimbursementid').val();
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if (rows == null || rows.length != 1) {
			alert("请选择其中一条记录。");
			return;
		}
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		if (ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")) {
			var ritemids = ids.join(',');
			jQuery.ajax({
				type : "POST",
				url : '${contextPath}/mx/oa/ritem/delete?ritemids=' + ritemids
						+ '&reimbursementid=' + reimbursementid,
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
			var allRows = $("#mydatagrid").datagrid("getRows");
			var allPrices = 0;
			for ( var i = 0; i < allRows.length; i++) {
				if (allRows[i].id == ids) {
					continue;
				}
				allPrices = accAdd(allPrices, accMul(allRows[i].itemsum,
						allRows[i].exrate));
			}
			jQuery("#appsum").val(allPrices);
			jQuery("#appsum_").html(allPrices);
			calculate(allPrices);
		}
	}

	//明细取消
	function cancelrow(indexs) {
		var bol = $("#mydatagrid").datagrid("validateRow", indexs);
		jQuery('#mydatagrid').datagrid("cancelEdit", indexs);
		jQuery('#mydatagrid').datagrid("endEdit", indexs);
		jQuery('#mydatagrid').datagrid('refreshRow', indexs);
		$("#mydatagrid").datagrid("selectRow", indexs);
		var rows = $("#mydatagrid").datagrid("getSelections");
		if (bol == false) {
			if (!(rows[0].purchaseitemid)) {
				jQuery('#mydatagrid').datagrid('deleteRow', indexs);
			}
		}
	}
	
	//明细编辑
	function editrow(indexs) {
		if (editcount > 0) {
			alert("当前还有" + editcount + "记录正在编辑，暂不能编辑。");
			return;
		}
		jQuery('#mydatagrid').datagrid("beginEdit", indexs);
	}
	
	//明细保存
	function saverow(indexs) {
		var reimbursementid = jQuery('#reimbursementid').val();
		$("#mydatagrid").datagrid("endEdit", indexs);
		$("#mydatagrid").datagrid("selectRow", indexs);
		var rows = $("#mydatagrid").datagrid("getSelections");
		var bol = $("#mydatagrid").datagrid("validateRow", indexs);
		if (bol == false) {
			alert("请检查输入！");
			return;
		}
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/ritem/save?reimbursementid='
					+ reimbursementid,
			data : rows[0],
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
		var allRows = $("#mydatagrid").datagrid("getRows");
		var allPrices = 0;
		for ( var i = 0; i < allRows.length; i++) {
			allPrices = accAdd(allPrices, accMul(allRows[i].itemsum,
					allRows[i].exrate));
		}
		jQuery("#appsum").val(allPrices);
		jQuery("#appsum_").html(allPrices);

		calculate(allPrices);
	}

	function getBudgetNo() {
		var area = jQuery('#area').combobox('getValue');
		var appuser = jQuery('#appuser').combobox('getValue');
		if (area == "" || appuser == "") {
			alert("请先选择申请人。");
			return;
		}
		var url = "${contextPath}/mx/oa/budgetView?area=" + area + "&appuser="
				+ appuser;
		var x = 150;
		var y = 50;
		if (is_ie) {
			x = document.body.scrollLeft + event.clientX - event.offsetX - 200;
			y = document.body.scrollTop + event.clientY - event.offsetY - 200;
		}
		openWindow(url, self, x, y, 565, 400);
	}
	
	function fillBudget(budgetno, appdate, budgetsum) {
		jQuery('#budgetno').val(budgetno);
		jQuery('#budgetDate').val(appdate);
		jQuery('#budgetDate_').html(appdate);
		jQuery('#budgetsum').val(budgetsum);
		jQuery('#budgetsum_').html(budgetsum);
		calculate();
	}
</script>
</head>

<body>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<span class="x_content_title">报销申请单</span> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-save'"
					onclick="javascript:saveData();">保存</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-submit'"
					onclick="javascript:saveAsData();">提交</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-cancel'"
					onclick="javascript:art.dialog.close();">关闭</a>
			</div>
		</div>

		<div data-options="region:'center',border:false,cache:true">
			<form id="iForm" name="iForm" method="post">
				<input type="hidden" id="reimbursementid" name="reimbursementid"
					value="${reimbursement.reimbursementid}" />
				<table style="width: 780px;">
					<tbody>
						<tr>
							<td align="left" width="55px;">
								<div style="width: 55px;">申请人</div>
							</td>
							<td align="left"><input id="appuser" class="easyui-combobox"
								name="appuser" value="${reimbursement.appuser}" size="10"
								max="10"
								data-options="required:true,valueField:'code',textField:'name',required:true,url:'${contextPath}/mx/oa/baseData/getUserJson',
					onSelect: function(rec){ 
					jQuery.ajax({
					   type: 'POST',
					   url: '${contextPath}/mx/oa/baseData/getSingleUserJson',
					   data: {'userCode':rec.code},
					   dataType:  'json',
					    async:false ,
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
					    jQuery('#post').combobox('setValue',data.headship);
					   }	
					   	    $('#company').combobox('clear');
					 	  var url = '${contextPath}/rs/dictory/jsonArray/'+data.area;    
					  	  $('#company').combobox('reload', url);
					   }
					 })},onChange: function(rec){ 
						jQuery.ajax({
						   type: 'POST',
						   url: '${contextPath}/mx/oa/baseData/getSingleUserJson',
						   data: {'userCode':rec.code},
						   dataType:  'json',
						   async:false ,
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
						   var url = '${contextPath}/rs/dictory/jsonArray/'+data.area;    
						                                $('#company').combobox('reload', url);
						   }
						 });
                    }" />

							</td>
							<td align="left" width="45px;">地区</td>
							<td align="left"><input id="area" class="easyui-combobox"
								disabled="disabled" name="area" size="10"
								value="${reimbursement.area}" editable="false"
								data-options="valueField:'code',textField:'name',required:true,url:'${contextPath}/rs/dictory/jsonArray/eara',
					onSelect:function(ret){										
					var url = '${contextPath}/rs/dictory/jsonArray/'+ret.code;
						jQuery('#company').combobox('clear');
						jQuery('#company').combobox('reload',url);
						jQuery('#area').val(ret.code);
					}																	
					" />
							</td>
							<td align="left" width="35px;">部门</td>
							<td align="left"><input id="dept" name="dept" type="text"
								class="easyui-combobox" editable="false" disabled="disabled"
								data-options="required:true,valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'"
								size="10" value="${reimbursement.dept}" /></td>
							<td align="left" width="35px;">职位</td>
							<td align="left"><input id="post" name="post" type="text"
								class="easyui-combobox" readonly="readonly" disabled="disabled"
								data-options="required:true" size="16"
								value="${reimbursement.post}" /></td>
						</tr>

						<tr>
							<td align="left" width="45px;">事由</td>
							<td align="left" colspan="7"><textarea rows="5" cols="80"
									id="subject" name="subject" class="easyui-validatebox"
									validType="notNullAndLength[200]" maxlength="200"
									data-options="required:true">${reimbursement.subject}</textarea></td>
						</tr>
						<tr>
							<td colspan="8">
								<div class="toolbar-backgroud">
									<img src="${contextPath}/images/window.png"> &nbsp;<span
										class="x_content_title">报销明细:</span> <a href="#"
										class="easyui-linkbutton"
										data-options="plain:true, iconCls:'icon-add'"
										onclick="javascript:addNew();">增加</a> <a href="#"
										class="easyui-linkbutton"
										data-options="plain:true, iconCls:'icon-remove'"
										onclick="javascript:deleteSelections();">删除</a>
								</div>
								<div data-options="region:'center',border:true"
									style="width: 775px; height: 120px">
									<table id="mydatagrid">
									</table>
								</div>

								<div class="toolbar-backgroud"
									style="height: 15px; font-size: 13px;">
									等价人民币总额: <input id="appsum" name="appsum" type="hidden"
										value="${reimbursement.appsum}" /> <span id="appsum_">${reimbursement.appsum}</span>
								</div> <script type="text/javascript">
									var appsum_ = $
									{
										reimbursement.appsum
									};
									jQuery(function() {
										jQuery('#appsum_').html(
												parseFloat(appsum_));
									});
								</script>
							</td>
						</tr>
						<tr>
							<td colspan="3">预算编号<b style="color: red">*</b>： <input
								id="budgetno" name="budgetno" size="10" onclick="getBudgetNo()"
								value="${reimbursement.budgetno}" readonly="readonly"
								class="easyui-validatebox" data-options="required:true" /></td>
							<td colspan="5">预算日期：<input id="budgetDate"
								name="budgetDate" type="hidden"
								" value="${reimbursement.budgetDate}" /> <span id="budgetDate_"><fmt:formatDate
										value="${reimbursement.budgetDate}" /></span></td>
						</tr>
						<tr>
							<td colspan="8">
								<table style="BORDER-COLLAPSE: collapse" bordercolor="#CCCCCC"
									cellpadding="4" border="1">
									<tr>
										<td align="center" width="320px;">预算金额</td>
										<td align="center" width="220px;"></td>
										<td align="center" width="320px;"><input type="hidden"
											id="budgetsum" name="budgetsum"
											value="${reimbursement.budgetsum}" /> <span id="budgetsum_">${reimbursement.budgetsum}</span></td>
									</tr>
									<tr>
										<td align="center">公司应付申请人</td>
										<td align="center"></td>
										<td align="center"><span id="budget_company"></span></td>
									</tr>
									<tr>
										<td align="center">向申请人应收</td>
										<td align="center"></td>
										<td align="center" style="BORDER: 0.5px solid #CCCCCC;"><span
											id="budget_user"></span></td>
									</tr>
									<script type="text/javascript">
										function calculate(allprice) {
											var reimbursementsum = jQuery(
													'#appsum').val();
											if (allprice != null
													&& allprice != "") {
												reimbursementsum = allprice;
											}
											var budgetsum = jQuery('#budgetsum')
													.val();
											if (reimbursementsum - budgetsum > 0) {
												jQuery('#budget_company').html(
														Subtr(reimbursementsum,
																budgetsum));
												jQuery('#budget_user').html(0);
											} else if (reimbursementsum
													- budgetsum < 0) {
												jQuery('#budget_company').html(
														0);
												jQuery('#budget_user')
														.html(
																Subtr(
																		budgetsum,
																		reimbursementsum));
											} else {
												jQuery('#budget_company').html(
														0);
												jQuery('#budget_user').html(0);
											}
										}
										calculate();
									</script>
								</table>
							</td>
						</tr>
					</tbody>
				</table>

				<div style="margin: 0; height: 4px"></div>
				<div>
					&nbsp; <a
						href="javascript:uploadFile(1, ${reimbursement.reimbursementid}, 1)">附件上传</a>
					(共<span id="numAttachment1" name="numAttachment1">0</span>个)
					<script type="text/javascript">
						jQuery(function() {
							jQuery
									.get(
											'${contextPath}/others/attachment.do?method=getCount&referId=${reimbursement.reimbursementid}&referType=1',
											{
												qq : 'xx'
											},
											function(data) {
												document
														.getElementById("numAttachment1").innerHTML = data.message;
											}, 'json');
						});
					</script>
				</div>
			</form>

		</div>
	</div>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>