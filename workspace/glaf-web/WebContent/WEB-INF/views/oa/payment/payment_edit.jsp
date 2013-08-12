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
	
	jQuery(function() {
		if (jQuery('#currency').val() == '') {
			jQuery('#currency').combobox('setValue', 'RMB');
		}
	});

	function saveData() {
		var bol = jQuery("#iForm").form('validate');
		if (bol == false) {
			alert("请检查输入！");
			return;
		}
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/payment/savePayment',
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
		var bol = jQuery("#iForm").form('validate');
		if (bol == false) {
			alert("请检查输入！");
			return;
		}
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/payment/save',
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
	
	function back() {
		location.href = "${contextPath}/mx/oa/payment";
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
				<span class="x_content_title">付款申请</span>
				<a href="#" class="easyui-linkbutton"
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
				<input type="hidden" id="paymentid" name="paymentid"
					value="${payment.paymentid}" />
				<div style="width: 880px; border: 1px solid #CCCCCC;">
					<table class="easyui-form" style="width: 850px;" align="center"
						cellspacing="4" cellpadding="4">
						<tbody>
							<tr>
								<td width="20%" align="left">地区</td>
								<td align="left"><input id="area" class="easyui-combobox"
									name="area" size="12" value="${payment.area}" disabled
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
								<td align="left"><input id="company"
									class="easyui-combobox" name="company" readonly="readonly"
									data-options="valueField:'code',textField:'name',editable:false,required:true,url:'${contextPath}/rs/dictory/jsonArray/${payment.area}'"
									value="${payment.company}" size='25' /></td>
								<td width="20%" align="center">凭证号</td>
								<td align="left"><input id="certificateno"
									name="certificateno" type="text" maxlength=20
									data-options="required:true" validType="notNull[]"
									class="easyui-validatebox" value="${payment.certificateno}" />
								</td>

							</tr>
							<tr>
								<td width="20%" align="left">申请人</td>
								<td align="left"><input id="appuser"
									class="easyui-combobox" name="appuser"
									value="${payment.appuser}"
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
									readonly class="easyui-validatebox" value="${payment.post}" />
								</td>
								<td width="20%" align="center">部门</td>
								<td align="left"><input id="dept" class="easyui-combobox"
									name="dept" value="${payment.dept}" disabled
									data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'" />
								</td>
							</tr>
							<tr>
								<td width="20%" align="left">收据/支票号</td>
								<td align="left"><input id="receiptno" name="receiptno"
									type="text" maxlength=30 data-options="required:true"
									validType="notNull[]" class="easyui-validatebox"
									value="${payment.receiptno}" /></td>
								<td width="20%" align="center">付款到期日期</td>
								<td align="left"><input id="maturitydate"
									name="maturitydate" type="text" class="easyui-datebox"
									data-options="editable:false,required:true"
									value="<fmt:formatDate value="${payment.maturitydate}" pattern="yyyy-MM-dd"/>" />
								</td>
								<td width="20%" align="center">申请金额</td>
								<td align="left"><input id="appsum" name="appsum"
									type="text" maxlength=11 min="0.01" max="99999999"
									data-options="required:true" class="easyui-numberbox"
									precision="2" value="${payment.appsum}" /></td>
							</tr>
							<tr>
								<td width="20%" align="left">币种</td>
								<td align="left"><input id="currency"
									class="easyui-combobox" name="currency"
									value="${payment.currency}" size=12
									data-options="valueField:'code',textField:'name',editable:false,required:true,url:'${contextPath}/rs/dictory/jsonArray/money'" />
								</td>
								<td width="20%" align="center">预算编号</td>
								<td align="left"><input id="budgetno" name="budgetno"
									type="text" maxlength="20" class="easyui-validatebox"
									value="${payment.budgetno}" /></td>
							</tr>
							<tr>
								<td width="20%" align="left">申请用途</td>
								<td align="left" colspan="5"><input id="use" name="use"
									type="text" size="80" maxlength=100 validType="notNull[]"
									data-options="required:true" class="easyui-validatebox"
									value="${payment.use}" /></td>
							</tr>

							<tr>
								<td align="left">收款单位:</td>
							</tr>
							<tr>
								<td colspan="6">
									<div
										style="width: 95%; height: 80px; border: 1px solid #CCCCCC">
										<table>
											<tr>
												<td width="20%" align="left">户口名</td>
												<td align="left" colspan="3"><input id="supname"
													name="supname" type="text" size="110" maxlength="100"
													data-options="required:true" validType="notNull[]"
													class="easyui-validatebox" value="${payment.supname}" /></td>
											</tr>
											<tr>
												<td width="20%" align="left">开户行</td>
												<td align="left"><input id="supbank" name="supbank"
													type="text" maxlength=50 data-options="required:true"
													validType="notNull[]" class="easyui-validatebox" size="40"
													value="${payment.supbank}" /></td>
												<td width="20%" align="center">账号</td>
												<td align="left"><input id="supaccount"
													name="supaccount" type="text" maxlength=50
													data-options="required:true" validType="notNull[]"
													class="easyui-validatebox" size="40"
													value="${payment.supaccount}" /></td>

											</tr>
											<tr>
												<td width="20%" align="left">地址</td>
												<td align="left" colspan="3"><input id="supaddress"
													name="supaddress" type="text" maxlength=100
													data-options="required:true" size="110"
													validType="notNull[]" class="easyui-validatebox"
													value="${payment.supaddress}" /></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td width="20%" align="left">列支科目</td>
								<td align="left" colspan="5"><input id="subject"
									name="subject" type="text" class="easyui-validatebox" size="80"
									maxlength=50 data-options="required:true"
									value="${payment.subject}" /></td>
							</tr>

							<tr>
								<td width="20%" align="left">支票/账户号码</td>
								<td align="left" colspan="5"><input id="checkno"
									name="checkno" type="text" maxlength=50
									data-options="required:true" class="easyui-validatebox"
									size="80" value="${payment.checkno}" /></td>
							</tr>
							<tr>
								<td width="20%" align="left">备注</td>
								<td align="left" colspan="5"><textarea id="remark"
										name="remark" class="easyui-validatebox" rows="3" cols="70"
										validType="Maxlength[200]">${payment.remark}</textarea></td>
							</tr>
							<tr>
								<td colspan="2"><c:if test="${payment.paymentid==null}">
										<a href="javascript:uploadFile(6, 0, 1)">附件上传</a>
											(共<span id="numAttachment6" name="numAttachment6">0</span>个)
									<script type="text/javascript">
										jQuery(function() {
											jQuery
													.get(
															'${contextPath}/others/attachment.do?method=getCount&referId=0&referType=6',
															{
																qq : 'xx'
															},
															function(data) {
																document
																		.getElementById("numAttachment6").innerHTML = data.message;
															}, 'json');
										});
									</script>
									</c:if> <c:if test="${payment.paymentid!=null}">
										<a href="javascript:uploadFile(6, ${payment.paymentid}, 1)">附件上传</a>
											(共<span id="numAttachment6" name="numAttachment6">0</span>个)
									<script type="text/javascript">
										jQuery(function() {
											jQuery
													.get(
															'${contextPath}/others/attachment.do?method=getCount&referId=${payment.paymentid}&referType=6',
															{
																qq : 'xx'
															},
															function(data) {
																document
																		.getElementById("numAttachment6").innerHTML = data.message;
															}, 'json');
										});
									</script>
									</c:if>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>