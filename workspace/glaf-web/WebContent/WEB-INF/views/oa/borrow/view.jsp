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
	<div class="easyui-layout" data-options="fit:true" align="center"
		style="height: 100%; overflow-y: auto">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud" align="left">
				<span class="x_content_title">员工借支申请</span> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-cancel'"
					onclick="javascript:art.dialog.close();">关闭</a>
			</div>
		</div>

		<div data-options="region:'center',border:false,cache:true">
			<form id="iForm" name="iForm" method="post">
				<input type="hidden" id="borrowid" name="borrowid"
					value="${borrow.borrowid}" /> <input type="hidden"
					id="processInstanceId" name="processInstanceId"
					value="${borrow.processinstanceid}" />
				<table style="width: 680px;" align="center" cellspacing="4">
					<tbody>
						<tr>
							<td align="left">申请人</td>
							<td align="left"><input id="appuser" class="easyui-combobox"
								name="appuser" value="${borrow.appuser}" size="10"
								disabled="disabled"
								data-options="required:true,valueField:'code',textField:'name',required:true,url:'${contextPath}/mx/oa/baseData/getUserJson',
					" />
							</td>
							<td align="left">职位</td>
							<td align="left"><input id="post" name="post" type="text"
								disabled="disabled" class="easyui-combobox" size="20"
								readonly="readonly" data-options="required:true"
								value="${borrow.post}" /></td>
							<td align="left">部门</td>
							<td align="left"><input id="dept" name="dept" type="text"
								disabled="disabled" class="easyui-combobox" size="10"
								readonly="readonly"
								data-options="required:true,valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'"
								value="${borrow.dept}" /></td>
						</tr>
						<tr>
							<td align="left" width="60px;">地区</td>
							<td align="left"><input id="area" class="easyui-combobox"
								name="area" size="10" value="${borrow.area}" editable="false"
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
							<td align="left" width="100px;">申请单位</td>
							<td align="left"><input id="company" class="easyui-combobox"
								name="company" editable="false" disabled="disabled"
								data-options="valueField:'code',textField:'name',required:true,url:'${contextPath}/rs/dictory/jsonArray/${borrow.area}'"
								value="${borrow.company}" size='20' /></td>
							<td align="left" width="100px;">申请日期</td>
							<td align="left"><input id="appdate" name="appdate"
								type="text" disabled="disabled" class="easyui-datebox" size="10"
								editable="false"
								value="<fmt:formatDate value="${borrow.appdate}" pattern="yyyy-MM-dd"/>" />
							</td>
						</tr>

						<tr>
							<td align="left">事由</td>
							<td align="left"><input id="content" class="easyui-combobox"
								editable="false" name="content" value="${borrow.content}"
								size="10" disabled="disabled"
								data-options="valueField:'code',textField:'name',required:true,url:'${contextPath}/rs/dictory/jsonArray/content',
				onSelect:function(ret){
					if(ret.code=='1'){
						jQuery('#travel_').show();
					}else{
						jQuery('#travel_').hide();
					}
			}" />
							</td>
							<td align="left">编号</td>
							<td align="left"><input id="borrowNo" name="borrowNo"
								type="text" class="easyui-validatebox" size="10"
								data-options="required:true" disabled="disabled"
								value="${borrow.borrowNo}" /></td>
						</tr>
						<tr>
							<td align="left" colspan="6">
								<div id="travel_">
									<fieldset style="height: 110px;">
										<div style="width: 650px; height: 110px; overflow-y: auto">
											<table id="borrowAddress_">
												<tr>
													<td>预计日期由： <input id="startdate" name="startdate"
														disabled="disabled" class="easyui-datebox" size="10"
														editable="false"
														value="<fmt:formatDate value="${borrow.startdate}" pattern="yyyy-MM-dd"/>" />
														至 <input id="enddate" name="enddate" disabled="disabled"
														class="easyui-datebox" size="10" editable="false"
														value="<fmt:formatDate value="${borrow.enddate}" pattern="yyyy-MM-dd"/>" />
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 共<input id="daynum"
														name="daynum" type="text" min="0"
														class="easyui-numberspinner" size="8" disabled="disabled"
														increment="1" value="${borrow.daynum}" /> 天
													</td>
												</tr>
												<c:forEach items="${addresslist}" varStatus="status"
													var="address">
													<tr id="tr_${status.index}">
														<td><input type="hidden"
															id="addressid_${status.index}"
															name="addressid_${status.index}"
															value="${address.addressid}"> 出差地点由： <input
															class="easyui-validatebox" disabled="disabled"
															id="start_${status.index}" name="start_${status.index}"
															type="text" value="${address.start}" /> 至 <input
															class="easyui-validatebox" disabled="disabled"
															id="reach_${status.index}" name="reach_${status.index}"
															type="text" value="${address.reach}" /></td>
													</tr>
												</c:forEach>
											</table>
										</div>
									</fieldset>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="6" align="left">
								<fieldset>
									<div>
										明细：
										<textarea rows="3" cols="60" disabled="disabled" id="details"
											name="details" class="easyui-validatebox"
											data-options="required:true">${borrow.details}</textarea>
									</div>
								</fieldset>
							</td>
						</tr>
						<tr>
							<td align="left" colspan="3">申请金额(人民币)： <input id="appsum"
								name="appsum" type="text" disabled="disabled"
								class="easyui-numberbox" size="10" data-options="required:true"
								value="${borrow.appsum}" />
							</td>
						</tr>
						<script type="text/javascript">
							if (jQuery('#content').val() == 1) {
								jQuery('#travel_').show();
							} else {
								jQuery('#travel_').hide();
							}
						</script>
						<tr>
							<td colspan="6" align="left">
								<table>
									<c:forEach items="${moneylist}" varStatus="status" var="money">
										<c:if test="${status.index%4==0}">
											<tr>
										</c:if>
										<td><input id="borrowmoneyid_${status.index}"
											disabled="disabled" name="borrowmoneyid_${status.index}"
											value="${money.borrowmoneyid}" type="hidden"> <input
											id="feename_${status.index}" name="feename_${status.index}"
											class="easyui-combobox" editable="false" name="brands"
											value="${money.feename}" size="5" disabled="disabled"
											data-options="valueField:'code',textField:'name',required:true,url:'${contextPath}/rs/dictory/jsonArray/freeName',
						onSelect:function(ret){
					}" />
											<input id="feesum_${status.index}"
											name="feesum_${status.index}" type="text" disabled="disabled"
											class="easyui-numberbox" size="8"
											data-options="required:true" value="${money.feesum}"
											onchange="changetotelPrice()" /></td>
										<c:if test="${status.index%4==3}">
											</tr>
										</c:if>
									</c:forEach>
								</table>
							</td>
						</tr>
						<tr>
							<td align="left" colspan="4">合计金额： <span id="appMon"></span></td>
							<td align="left" colspan="2">￥： <input id="totelPrice"
								name="totelPrice" type="text" disabled="disabled"
								class="easyui-numberbox" size="8" readonly="readonly"
								value="${totelPrice}" />
							</td>
							<script type="text/javascript">
								function changetotelPrice() {
									var totelPrice = 0;
									for ( var i = 0; i < 4; i++) {
										var feesumid = "#feesum_" + i;
										var feesum = jQuery(feesumid).val();
										if (feesum == "") {
											continue;
										}
										feesum = parseFloat(feesum);
										totelPrice += feesum;
									}
									jQuery('#totelPrice').numberbox('setValue',
											totelPrice);
									changeAppSum();
								}

								function changeAppSum() {
									var appsum = jQuery('#totelPrice').val();
									appsum = parseFloat(appsum);
									if (appsum > 99999999) {
										appsum = '99999999';
									}
									appsum = appsum + ".00";
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
												if (num == str.charAt(i + 1)) {
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
													+ capsStr[str.length - i
															- 1];
										}
									}
									appMon = appMon + '元整';
									jQuery('#appMon').html(appMon);
								}
								changeAppSum();
							</script>
						</tr>
						<tr>
							<td colspan="6" align="left">
								<div>
									<a href="javascript:uploadFile(7, ${borrow.borrowid}, 0)">附件下载</a>
									<jsp:include page="/others/attachment/showCount">
										<jsp:param name="referType" value="7" />
										<jsp:param name="referId" value="${borrow.borrowid}" />
									</jsp:include>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="6" align="left"><%@ include
									file="/WEB-INF/views/oa/common/approve_foot.jsp"%>
								<%@include file="/WEB-INF/views/oa/common/task.jsp"%>
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