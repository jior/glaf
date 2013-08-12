<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_config.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>印章申请审核</title>
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
	var contextPath = "${contextPath}";
	var sealstype = "${seal.sealtype}";
	var sealid = "${seal.sealid}";
	var lookover = "${lookover}";

	jQuery(function() {
		if (sealid == "" || sealid == null) {

		} else {
			jQuery('#sealtype').combobox('setValues', sealstype.split(','));
		}
	});

	jQuery(function() {
		if (lookover == 'true') {
			jQuery("#reviewDiv").hide();
			jQuery("#titleSpan2").html("印章申请查看");
		}
	});

	function closeDialog() {
		window.close();
		window.parent.location.reload();
	}

	function passData() {
		if (confirm("确认审核通过吗？")) {
			var params = jQuery('#approveOpinion').serialize();
			jQuery.ajax({
				type : "POST",
				url : '${contextPath}/mx/oa/seal/submit?sealid=' + sealid
						+ '&option=' + jQuery('#approveOpinion').val().trim(),
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
				url : '${contextPath}/mx/oa/seal/rollback?sealids=' + sealid
						+ '&option=' + jQuery('#approveOpinion').val().trim(),
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

	function review() {
		$('#dlgApprove').dialog('open').dialog({
			title : '审核',
			left : 180,
			top : 100,
			closed : false,
			modal : true
		});
		jQuery('#dlgApprove').form('clear');
	}
</script>
</head>

<body>
	<div style="margin: 0;"></div>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<span class="x_content_title" id='titleSpan2'>印章申请审核</span>
				<div style="display: inline" id="reviewDiv">
					<a href="#" class="easyui-linkbutton"
						data-options="plain:true, iconCls:'icon-submit'"
						onclick="javascript:review();">审核</a>
				</div>

				<!-- <input type="button" name="save" value=" 保存 " class="button btn btn-primary" onclick="javascript:saveData();">
	<input type="button" name="saveAs" value=" 另存 " class="button btn" onclick="javascript:saveAsData();">
	<input type="button" name="back" value=" 返回 " class="button btn" onclick="javascript:history.back();"> -->

				<!-- 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-saveas'" onclick="javascript:saveAsData();" >另存</a> 
        -->
				<!--
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-back'" onclick="javascript:history.back();">返回</a>
	-->
			</div>
		</div>

		<div data-options="region:'center',border:false,cache:true">
			<form id="iForm" name="iForm" method="post">
				<input type="hidden" id="sealid" name="sealid"
					value="${seal.sealid}" />
				<table class="easyui-form" align="center" cellspacing="3"
					cellpadding="0" width="660" height="450">
					<tbody>
						<tr>
							<td align="left">
								<div style="width: 40px;">地区</div>
							</td>
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
							<td align="right">
								<div style="width: 60px;">申报单位</div>
							</td>
							<td align="left" colspan="1"><input id="company"
								class="easyui-combobox" name="company" readonly="readonly"
								disabled
								data-options="valueField:'code',textField:'name',editable:false,required:true,url:'${contextPath}/rs/dictory/jsonArray/${seal.area}'"
								value="${seal.company}" size='25' /></td>

							<td align="left">
								<div style="width: 40px;">部门</div>
							</td>
							<td align="left"><input id="dept" class="easyui-combobox"
								name="dept" value="${seal.dept}" disabled
								data-options="valueField:'code',textField:'name',required:true,url:'${contextPath}/rs/dictory/deptJsonArray/012'" />
							</td>


						</tr>
						<tr>
							<td align="left">申请人</td>
							<td align="left"><input id="appuser" class="easyui-combobox"
								name="appuser" value="${seal.appuser}" size="12" disabled
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
							<td align="center">职位</td>
							<td align="left"><input id="post" name="post" type="text"
								size="15" disabled class="easyui-validatebox"
								value="${seal.post}" readonly /></td>
							<td>金额</td>

							<td><input id="money" name="money" type="text" disabled
								class="easyui-numberbox" precision="2" value="${seal.money}"
								maxlength="8" /></td>
						</tr>
						<tr>

							<td align="left" colspan="2">申请印章类型</td>
							<td align="left" colspan="4"><input id="sealtype"
								class="easyui-combobox" name="sealtype" disabled
								data-options="valueField:'code',textField:'name',required:true,editable:false,multiple:true,url:'${contextPath}/rs/dictory/jsonArray/seal'"
								size='25' /></td>
						</tr>
						<tr>
							<td align="left" colspan="2">申请盖章文件之主送单位</td>
							<td align="left" colspan="4"><input id="supplier"
								name="supplier" type="text" size='40'
								data-options="required:true" class="easyui-validatebox" disabled
								value="${seal.supplier}" /></td>
						</tr>
						<tr>
							<td align="left" colspan="2">申请盖章文件之内容</td>
							<td align="left" colspan="4"><textarea id="content"
									name="content" disabled data-options="required:true"
									class="easyui-validatebox" rows="3" cols="50">${seal.content}</textarea>
							</td>
						</tr>
						<tr>
							<td align="left" colspan="2">申请盖章文件之份数</td>
							<td align="left" colspan="4"><input id="num" name="num"
								type="text" maxlength="12" disabled class="easyui-numberbox"
								value="${seal.num}" data-options="required:true" /></td>
						</tr>

						<tr>
							<td align="left" colspan="2">备注</td>
							<td align="left" colspan="4"><textarea id="remark"
									name="remark" class="easyui-validatebox" disabled rows="3"
									cols="50">${seal.remark}</textarea></td>
						</tr>
						<tr>
							<td colspan="2"><a
								href="javascript:uploadFile(4, ${seal.sealid}, 0)">附件下载</a> <jsp:include
									page="/others/attachment/showCount">
									<jsp:param name="referType" value="4" />
									<jsp:param name="referId" value="${seal.sealid}" />
								</jsp:include></td>
						</tr>
						<tr>
							<td colspan="6"><input type="hidden" id="processInstanceId"
								name="processInstanceId" value="${seal.processinstanceid}" /> <%@include
									file="/WEB-INF/views/oa/common/task.jsp"%>
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