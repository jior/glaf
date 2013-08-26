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
<script type="text/javascript" src="${contextPath}/scripts/glaf-base.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/artDialog/plugins/iframeTools.js"></script>
<script type="text/javascript">
	var contextPath = "${contextPath}";

	function saveData() {
		var isValid = $("#iForm").form('validate');
		if (!isValid) {
			alert("请检查输入！");
			return isValid;
		}

		if (!duibi($("#startdate").datebox('getValue'), $("#enddate").datebox(
				'getValue'))) {
			return false;
		}

		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/overtime/saveOvertime',
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

	function closeDlg() {
		window.parent.location.reload();
	}

	function submit() {
		var isValid = $("#iForm").form('validate');
		if (!isValid) {
			alert("请检查输入！");
			return isValid;
		}

		if (!duibi($("#startdate").datebox('getValue'), $("#enddate").datebox(
				'getValue'))) {
			return false;
		}

		var params = jQuery("#iForm").formSerialize();
		jQuery
				.ajax({
					type : "POST",
					url : '${contextPath}/mx/oa/overtime/saveOvertime',
					data : params,
					dataType : 'json',
					error : function(data) {
						alert('服务器处理错误！');
					},
					success : function(data) {
						if (data != null && data.message != null) {
							if (data.statusCode == "200") {
								var id = data.id;
								if (id != null && id != "" && id != "0") {
									jQuery
											.ajax({
												type : "POST",
												url : '${contextPath}/mx/oa/overtime/getOvertime?id='
														+ id,
												dataType : 'json',
												error : function(data) {
													alert('服务器处理错误！');
												},
												success : function(data) {
													if (data != null) {
														if (data.status == "0"
																|| data.status == "3") {
															jQuery
																	.ajax({
																		type : "POST",
																		url : '${contextPath}/mx/oa/overtime/submit?id='
																				+ id,
																		dataType : 'json',
																		error : function(
																				data) {
																			alert('服务器处理错误！');
																		},
																		success : function(
																				data) {
																			if (data != null
																					&& data.message != null) {
																				alert(data.message);
																			} else {
																				alert('操作成功！');
																			}
																			if (window.opener) {
																				window.opener.location
																						.reload();
																			} else if (window.parent) {
																				window.parent.location
																						.reload();
																			}
																		}
																	});
														} else {
															alert("只能提交已保存的记录！");
														}
													}
												}
											});
								} else {
									alert("只能提交已保存的记录！");
								}
							}
						}

					}
				});
	}

	function duibi(a, b) {
		var arr = a.split("-");
		var starttime = new Date(arr[0], arr[1], arr[2]);
		var starttimes = starttime.getTime();

		var arrs = b.split("-");
		var lktime = new Date(arrs[0], arrs[1], arrs[2]);
		var lktimes = lktime.getTime();

		if (starttimes > lktimes) {
			alert('开始日期不能晚于结束日期!');
			return false;
		} else
			return true;
	}
</script>
</head>

<body>
	<div style="margin: 0;"></div>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<span class="x_content_title">加班申请</span> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-save'"
					onclick="javascript:saveData();">保存</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-submit'"
					onclick="javascript:submit();">提交</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-cancel'"
					onclick="javascript:closeDlg();">关闭</a>

			</div>
		</div>

		<div data-options="region:'center',border:false,cache:true">
			<form id="iForm" name="iForm" method="post">
				<input type="hidden" id="id" name="id" value="${overtime.id}" />
				<table class="easyui-form" style="width: 700px;" align="center">
					<tbody>
						<tr>
							<td width="20%" align="left">地区：</td>
							<td align="left"><input id="area" class="easyui-combobox"
								name="area" value="${overtime.area}" disabled="disabled"
								data-options="required:true,valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/eara'" />
							</td>
							<td width="20%" align="left">单位：</td>
							<td align="left"><input id="company" class="easyui-combobox"
								name="company" value="${overtime.company}" editable="false"
								data-options="required:false,valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/${overtime.area}'" />
							</td>
							<td width="20%" align="left">部门：</td>
							<td align="left"><input id="dept" class="easyui-combobox"
								name="dept" value="${overtime.dept}" disabled="disabled"
								data-options="required:true,	valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'" />
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">申请人：</td>
							<td align="left"><input id="appuser" class="easyui-combobox"
								name="appuser" value="${overtime.appuser}"
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
							<td width="20%" align="left">职位：</td>
							<td align="left"><input id="post" name="post" type="text"
								readonly="readonly" class="easyui-validatebox"
								value="${overtime.post}" /></td>
							<td width="20%" align="left">申请日期：</td>
							<td align="left"><input id="appdate" name="appdate"
								type="text" disabled="disabled" class="easyui-datebox"
								data-options="required:true" value="${appdate}"
								pattern="yyyy-MM-dd" /></td>
						</tr>
						<tr>
							<td width="20%" align="left">加班类型：</td>
							<td align="left"><input id="type" class="easyui-combobox"
								name="type" value="${overtime.type}" editable="false"
								data-options="required:true,valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/JBLX'" />
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">加班实施内容：</td>
							<td align="left" colspan="5"><textarea id="content"
									name="content" style="width: 91%" data-options="required:true"
									validType="notNullAndLength[200]" class="easyui-validatebox">${overtime.content}</textarea>
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">实施时间：</td>
							<td align="left" colspan="5"><input id="startdate"
								name="startdate" type="text" class="easyui-datebox"
								style="width: 130px" data-options="required:true"
								missingMessage="日期必须填写" editable="false" value="${startdate}"
								pattern="yyyy-MM-dd" /> &nbsp;&nbsp;<input id="starttime"
								name="starttime" type="text" data-options="required:true"
								class="easyui-numberbox" min="0" max="24" style="width: 25px"
								value="${overtime.starttime}" />时起至 <input id="enddate"
								name="enddate" type="text" class="easyui-datebox"
								style="width: 130px" data-options="required:true"
								missingMessage="日期必须填写" editable="false" value="${enddate}"
								pattern="yyyy-MM-dd" /> &nbsp;&nbsp;<input id="endtime"
								name="endtime" type="text" class="easyui-numberbox" min="0"
								max="24" style="width: 25px" data-options="required:true"
								value="${overtime.endtime}" />时止共<input id="overtimesum"
								name="overtimesum" type="text" data-options="required:true"
								class="easyui-numberbox" precision="2" style="width: 25px"
								oninput="jQuery('#hour').val(this.value*8);"
								onpropertychange="jQuery('#hour').val(this.value*8);"
								value="${overtime.overtimesum}" />天&nbsp;计<input id="hour"
								name="hour" type="text" class="easyui-validatebox"
								disabled="disabled" style="width: 25px"
								value="${overtime.overtimesum*8}" />小时</td>
						</tr>
						<tr>
							<td width="20%" align="left">备注：</td>
							<td align="left" colspan="5"><textarea id="remark"
									name="remark" style="width: 91%" validType="Maxlength[200]"
									class="easyui-validatebox">${overtime.remark}</textarea></td>
						</tr>
						<tr>
							<td width="20%" align="left">附件：</td>
							<td align="left" colspan="5"><a
								href="javascript:uploadFile(12, ${overtime.id}, 1)">附件上传 <img
									src="${contextPath}/images/folder3_document.png" border="0"></a>
								(共<span id="numAttachment12" name="numAttachment12">0</span>个) <script
									type="text/javascript">
									jQuery(function() {
										jQuery
												.get(
														'${contextPath}/others/attachment.do?method=getCount&referId=${overtime.id}&referType=12',
														{
															qq : 'xx'
														},
														function(data) {
															document
																	.getElementById("numAttachment12").innerHTML = data.message;
														}, 'json');
									});
								</script></td>
						</tr>

					</tbody>
				</table>
			</form>
		</div>
	</div>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>