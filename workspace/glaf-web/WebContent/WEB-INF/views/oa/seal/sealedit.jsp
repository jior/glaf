<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_config.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>印章申请</title>
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
	var contextPath = "${contextPath}";
	var sealstype = "${seal.sealtype}";
	var sealid = "${seal.sealid}";


	jQuery(function() {
		//jQuery('#sealtype').combobox('reload', '${contextPath}/rs/dictory/jsonArray/seal');
	});


	jQuery(function() {
		if (sealid == "" || sealid == null) {
		} else {
			jQuery('#sealtype').combobox('setValues', sealstype.split(','));
		}
	});

	
	function saveData() {
		var bol = jQuery("#iForm").form('validate');
		if (bol == false) {
			alert("请检查输入！");
			return;
		}
		var sealtypes = jQuery('#sealtype').combobox('getValues');
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/seal/saveseal?sealtypes=' + sealtypes,
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
		var sealtypes = jQuery('#sealtype').combobox('getValues');
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/seal/save?sealtypes=' + sealtypes,
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
				<span class="x_content_title">印章申请</span>
				 
				<a href="#" class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-save'"
					onclick="javascript:saveData();">保存</a> 
				<a href="#" class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-submit'"
					onclick="javascript:saveAsData();">提交</a> 
				<a href="#" class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-cancel'"
					onclick="javascript:closeDialog();">关闭</a>
			</div>
		</div>

		<div data-options="region:'center',border:false,cache:true">
			<form id="iForm" name="iForm" method="post">
				<input type="hidden" id="sealid" name="sealid"
					value="${seal.sealid}" />
				<table class="easyui-form" align="center" cellspacing="3"
					cellpadding="0" width="660" height="400">
					<tbody>
						<tr>
							<td align="left">地区</td>
							<td align="left" colspan="1"><input id="area"
								class="easyui-combobox" name="area" size="12"
								value="${seal.area}" disabled
								data-options="valueField:'code',textField:'name',required:true,editable:false,url:'${contextPath}/rs/dictory/jsonArray/eara',
					onSelect:function(ret){										
					var url = '${contextPath}/rs/dictory/jsonArray/'+ret.code;
						jQuery('#company').combobox('clear');
						jQuery('#company').combobox('reload',url);
						jQuery('#area').val(ret.code);
					}																	
					" />
							</td>
							<td align="right">申报单位</td>
							<td align="left" colspan="1"><input id="company"
								class="easyui-combobox" name="company" readonly="readonly"
								data-options="valueField:'code',textField:'name',editable:false,required:false,url:'${contextPath}/rs/dictory/jsonArray/${seal.area}'"
								value="${seal.company}" size='25' /></td>

							<td align="left">部门</td>
							<td align="left"><input id="dept" class="easyui-combobox"
								name="dept" value="${seal.dept}" disabled
								data-options="valueField:'code',textField:'name',required:true,url:'${contextPath}/rs/dictory/deptJsonArray/012'" />
							</td>


						</tr>
						<tr>
							<td align="left">申请人</td>
							<td align="left"><input id="appuser" class="easyui-combobox"
								name="appuser" value="${seal.appuser}" size="12"
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
							<td align="left">职位</td>
							<td align="left"><input id="post" name="post" type="text"
								size="15" class="easyui-validatebox" value="${seal.post}"
								readonly /></td>
							<td>金额</td>

							<td><input id="money" name="money" type="text"
								class="easyui-numberbox" precision="2" min="0.01" max="99999999"
								value="${seal.money}" maxlength="11" /></td>
						</tr>
						<tr>

							<td align="left" colspan="2">申请印章类型</td>
							<td align="left" colspan="4">
							<input id="sealtype" name="sealtype" class="easyui-combobox" size='25'
								data-options="valueField:'code',textField:'name', required:true,editable:false,multiple:true,url:'${contextPath}/rs/dictory/jsonArray/seal'"/> 
							</td>
						</tr>
						<tr>
							<td align="left" colspan="2">申请盖章文件之主送单位</td>
							<td align="left" colspan="4"><input id="supplier"
								name="supplier" type="text" size='40' maxlength="50"
								data-options="required:true" class="easyui-validatebox"
								value="${seal.supplier}" />
							</td>
						</tr>
						<tr>
							<td align="left" colspan="2">申请盖章文件之内容</td>
							<td align="left" colspan="4">
							<textarea id="content" name="content"
									data-options="required:true,validType:'notNullAndLength[200]'"
									class="easyui-validatebox" rows="5" cols="50">${seal.content}</textarea>
							</td>
						</tr>
						<tr>
							<td align="left" colspan="2">申请盖章文件之份数</td>
							<td align="left" colspan="4"><input id="num" name="num"
								type="text" maxlength="3" min="1" max="999" size="5"
								class="easyui-numberbox" value="${seal.num}"
								data-options="required:true" />
							</td>
						</tr>

						<tr>
							<td align="left" colspan="2">备注</td>
							<td align="left" colspan="4"><textarea id="remark"
									name="remark" class="easyui-validatebox" rows="5" cols="50"
									validType="Maxlength[200]">${seal.remark}</textarea>
							</td>
						</tr>
						<tr>
							<td colspan="2">
							<c:if test="${seal.sealid==null}">
									<a href="javascript:uploadFile(4, 0, 1)">附件上传</a>
										(共<span id="numAttachment4" name="numAttachment4">0</span>个)
								<script type="text/javascript">
									jQuery(function() {
										jQuery
												.get(
														'${contextPath}/others/attachment.do?method=getCount&referId=0&referType=4',
														{
															qq : 'xx'
														},
														function(data) {
															document
																	.getElementById("numAttachment4").innerHTML = data.message;
														}, 'json');
									});
								</script>

								</c:if> <c:if test="${seal.sealid!=null}">
									<a href="javascript:uploadFile(4, ${seal.sealid}, 1)">附件上传</a>
										(共<span id="numAttachment4" name="numAttachment4">0</span>个)
								<script type="text/javascript">
									jQuery(function() {
										jQuery
												.get(
														'${contextPath}/others/attachment.do?method=getCount&referId=${seal.sealid}&referType=4',
														{
															qq : 'xx'
														},
														function(data) {
															document
																	.getElementById("numAttachment4").innerHTML = data.message;
														}, 'json');
									});
								</script>

								</c:if>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</body>
</html>