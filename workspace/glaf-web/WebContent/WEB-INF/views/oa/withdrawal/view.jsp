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
</script>
</head>

<body>
	<div style="margin: 0;"></div>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<span class="x_content_title">取现申请查看</span> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-cancel'"
					onclick="javascript:art.dialog.close();">关闭</a>
			</div>
		</div>

		<div data-options="region:'center',border:false,cache:true">
			<form id="iForm" name="iForm" method="post">
				<input type="hidden" id="withdrawalid" name="withdrawalid"
					value="${withdrawal.withdrawalid}" /> <input type="hidden"
					id="processInstanceId" name="processInstanceId"
					value="${withdrawal.processinstanceid}" />
				<table class="easyui-form" style="width: 700px;" cellspacing="15"
					cellpadding="0">
					<tbody>
						<tr>
							<td align="left">申请人</td>
							<td align="left"><input id="appuser" class="easyui-combobox"
								name="appuser" value="${withdrawal.appuser}" size="12"
								disabled="disabled"
								data-options="required:true,valueField:'code',textField:'name',required:true,url:'${contextPath}/mx/oa/baseData/getUserJson',
					" />
							</td>
							<td align="left">职位</td>
							<td align="left"><input id="post" name="post" type="text"
								disabled="disabled" class="easyui-combobox" readonly="readonly"
								data-options="required:true" size="18"
								value="${withdrawal.post}" /></td>
							<td align="left">部门</td>
							<td align="left"><input id="dept" name="dept" type="text"
								class="easyui-combobox" disabled="disabled"
								data-options="required:true,valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'"
								size="12" value="${withdrawal.dept}" /></td>
						</tr>
						<tr>
							<td align="left"><span>地区</span></td>
							<td align="left"><input id="area" class="easyui-combobox"
								name="area" size="12" value="${withdrawal.area}"
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
							<td align="left" style="width: 30px;">单位</td>
							<td align="left"><input id="company" class="easyui-combobox"
								name="company" readonly="readonly" disabled="disabled"
								data-options="valueField:'code',textField:'name',required:true,url:'${contextPath}/rs/dictory/jsonArray/${withdrawal.area}'"
								value="${withdrawal.company}" size='18' /></td>
							<td align="left" style="width: 60px;">申请日期</td>
							<td align="left"><input id="appdate" name="appdate"
								type="text" disabled="disabled" class="easyui-datebox" size="12"
								data-options="required:true"
								value="<fmt:formatDate value="${withdrawal.appdate}" pattern="yyyy-MM-dd"/>" />
							</td>
						</tr>

						<tr>
							<td align="left"><div style="width: 80px;">申请金额小写</div></td>
							<td align="left"><input id="appsum" name="appsum"
								type="text" data-options="required:true"
								class="easyui-numberbox" precision="2" size="12"
								disabled="disabled" value="${withdrawal.appsum}" /></td>
							<td align="left" colspan="4">申请金额(人民币)： <span id="appMon"></span>
								<script type="text/javascript">
									function changeAppSum() {
										var appsum = jQuery('#appsum').val();
										if (appsum > 99999999) {
											appsum = '99999999';
										}
										appsum = parseFloat(appsum) + "";
										var appsums = appsum.split(".");
										var str = appsums[0].toString();
										var numStr = [ '壹', '贰', '叁', '肆', '伍',
												'陆', '柒', '捌', '玖', '零' ];
										var capsStr = [ '', '拾', '佰', '仟', '万',
												'拾', '佰', '仟', '亿' ];
										var appMon = '';
										for ( var i = 0; i < str.length; i++) {
											var num = str.charAt(i);
											if (num == 0) {
												if ((i + 1) != str.length) {
													if (num == str
															.charAt(i + 1)) {
														if (i == (str.length - 5)) {
															appMon += capsStr[4];
														} else {
															appMon += '';
														}
													} else {
														if (i == (str.length - 5)) {
															appMon += capsStr[str.length
																	- i - 1];
														} else {
															appMon += numStr[9];
														}
													}
												}
											} else {
												appMon += numStr[num - 1]
														+ capsStr[str.length
																- i - 1];
											}
										}
										appMon = appMon + '元整';
										jQuery('#appMon').html(appMon);
									}
									changeAppSum();
								</script>
							</td>
						</tr>
						<tr>
							<td align="left">事由</td>
							<td align="left" colspan="5"><textarea rows="3" cols="60"
									id="content" name="content" disabled="disabled"
									class="easyui-validatebox" data-options="required:true">${withdrawal.content}</textarea>
							</td>
						</tr>
						<tr>
							<td align="left">备注</td>
							<td align="left" colspan="5"><textarea rows="3" cols="60"
									id="remark" name="remark" disabled="disabled"
									class="easyui-validatebox">${withdrawal.remark}</textarea></td>
						</tr>
						<tr>
							<td colspan="6"><a
								href="javascript:uploadFile(11, ${withdrawal.withdrawalid}, 0)">附件下载</a>
								<jsp:include page="/others/attachment/showCount">
									<jsp:param name="referType" value="11" />
									<jsp:param name="referId" value="${withdrawal.withdrawalid}" />
								</jsp:include></td>
						</tr>
						<tr>
							<td colspan="6"><%@ include
									file="/WEB-INF/views/oa/common/task.jsp"%>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</body>
</html>