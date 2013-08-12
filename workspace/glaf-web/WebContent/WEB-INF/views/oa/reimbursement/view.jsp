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
												required : true
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
												precision : 0
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
												precision : 2
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
</script>
</head>

<body>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<span class="x_content_title">报销申请单</span> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-cancel'"
					onclick="javascript:art.dialog.close();">关闭</a>
			</div>
		</div>

		<div data-options="region:'center',border:false,cache:true">
			<form id="iForm" name="iForm" method="post">
				<input type="hidden" id="reimbursementid" name="reimbursementid"
					value="${reimbursement.reimbursementid}" /> <input type="hidden"
					id="processInstanceId" name="processInstanceId"
					value="${reimbursement.processinstanceid}" />
				<table style="width: 780px;">
					<tbody>
						<tr>
							<td align="left"><div style="width: 45px;">申请人</div></td>
							<td align="left"><input id="appuser" class="easyui-combobox"
								name="appuser" value="${reimbursement.appuser}" size="10"
								disabled="disabled"
								data-options="required:true,valueField:'code',textField:'name',required:true,url:'${contextPath}/mx/oa/baseData/getUserJson',
					" />
							</td>
							<td align="left"><div style="width: 25px;">地区</div></td>
							<td align="left"><input id="area" class="easyui-combobox"
								name="area" size="10" value="${reimbursement.area}"
								disabled="disabled"
								data-options="valueField:'code',textField:'name',required:true,url:'${contextPath}/rs/dictory/jsonArray/eara',
					onSelect:function(ret){										
					var url = '${contextPath}/rs/dictory/jsonArray/'+ret.code;
						jQuery('#company').combobox('clear');
						jQuery('#company').combobox('reload',url);
						jQuery('#area').val(ret.code);
					}																	
					" />
							</td>
							<td align="left"><div style="width: 25px;">部门</div></td>
							<td align="left"><input id="dept" name="dept" type="text"
								class="easyui-combobox" editable="false" disabled="disabled"
								data-options="required:true,valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'"
								size="10" readonly="readonly" value="${reimbursement.dept}" /></td>
							<td align="left"><div style="width: 25px;">职位</div></td>
							<td align="left"><input id="post" name="post" type="text"
								class="easyui-combobox" readonly="readonly" disabled="disabled"
								data-options="required:true" size="16"
								value="${reimbursement.post}" /></td>
						</tr>

						<tr>
							<td align="left" width="45px;">事由</td>
							<td align="left" colspan="7"><textarea rows="2" cols="70"
									id="subject" name="subject" class="easyui-validatebox"
									disabled="disabled" data-options="required:true">${reimbursement.subject}</textarea></td>
						</tr>
						<tr>
							<td colspan="8">
								<div class="toolbar-backgroud">
									<img src="${contextPath}/images/window.png"> &nbsp;<span
										class="x_content_title">报销明细:</span>
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
								id="budgetno" name="budgetno" size="10"
								value="${reimbursement.budgetno}" disabled="disabled"
								readonly="readonly" class="easyui-validatebox"
								data-options="required:true" /></td>
							<td colspan="5">预算日期：<input id="budgetDate"
								name="budgetDate" type="hidden"
								" value="${reimbursement.budgetDate}" /> <span id="budgetDate_"><fmt:formatDate
										value="${reimbursement.budgetDate}" /></span>
							</td>
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
										<td align="center"><span id="budget_user"></span></td>
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
						href="javascript:uploadFile(1, ${reimbursement.reimbursementid}, 0)">附件下载</a>
					<jsp:include page="/others/attachment/showCount">
						<jsp:param name="referType" value="1" />
						<jsp:param name="referId" value="${reimbursement.reimbursementid}" />
					</jsp:include>
				</div>
				<div style="width: 775px;">
					<table width="100%">
						<tr>
							<td width="775px"><%@ include
									file="/WEB-INF/views/oa/common/task.jsp"%>
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>