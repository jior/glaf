<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同申请</title>
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
	var contextPath = "${contextPath}";

	jQuery(function() {
		if (jQuery('#currency').val() == '') {
			jQuery('#currency').combobox('setValue', 'RMB');
		}
		if (jQuery('#paytype').val() == '') {
			jQuery('#paytype').combobox('setValue', '1');
		}

	});

	function saveData() {
		var params = jQuery("#iForm").formSerialize();
		var bol = jQuery("#iForm").form('validate');
		if (bol == false) {
			alert("请检查输入！");
			return;
		}

		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/contract/saveContract',
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

		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/contract/save',
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
				<span class="x_content_title">编辑合同申请</span> <a href="#"
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
				<input type="hidden" id="id" name="id" value="${contract.id}" /> <input
					type="hidden" id="status" name="status" value="${contract.status}" />
				<table class="easyui-form" width="90%" align="center">
					<tbody>
						<tr>
							<td width="20%" align="left">地区</td>
							<td align="left"><input id="area" class="easyui-combobox"
								name="area" size="12" value="${contract.area}" disabled
								data-options="valueField:'code',textField:'name',required:true,editable:false,url:'${contextPath}/rs/dictory/jsonArray/eara',
					onSelect:function(ret){										
					var url = '${contextPath}/rs/dictory/jsonArray/'+ret.code;
						jQuery('#companyname').combobox('clear');
						jQuery('#companyname').combobox('reload',url);
						jQuery('#area').val(ret.code);
					}																	
					" />
							</td>
							<td width="20%" align="left">申请人</td>
							<td align="left"><input id="appuser" class="easyui-combobox"
								name="appuser" value="${contract.appuser}"
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
   if(jQuery('#companyname')!=null){
   		jQuery('#companyname').combobox('setValue','');
   }
   var url = '${contextPath}/rs/dictory/jsonArray/'+data.area;    
                                $('#companyname').combobox('reload', url);
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
   if(jQuery('#companyname')!=null){
   		jQuery('#companyname').combobox('setValue','');
   }
   var url = '${contextPath}/rs/dictory/jsonArray/'+data.area;    
                                $('#companyname').combobox('reload', url);
   }
 });
                    }" />

							</td>
						</tr>
						<tr>
							<td width="20%" align="left">职位</td>
							<td align="left"><input id="post" name="post" readonly
								type="text" class="easyui-validatebox" value="${contract.post}" />
							</td>
							<td width="20%" align="left">部门</td>
							<td align="left"><input id="dept" class="easyui-combobox"
								name="dept" value="${contract.dept}" disabled
								data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'" />
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">合同名称</td>
							<td align="left"><input id="contactname" name="contactname"
								type="text" validType="notNull[]" data-options="required:true"
								class="easyui-validatebox" value="${contract.contactname}"
								maxlength="100" /></td>
							<td width="20%" align="left">合同编号</td>

							<td align="left"><input id="contractno" name="contractno"
								type="text" validType="notNull[]" data-options="required:true"
								class="easyui-validatebox" value="${contract.contractno}"
								maxlength="50" /></td>

						</tr>
						<tr>
							<td width="20%" align="left">项目名称</td>
							<td align="left"><input id="projrctname" name="projrctname"
								type="text" validType="notNull[]" data-options="required:true"
								class="easyui-validatebox" value="${contract.projrctname}"
								maxlength="100" /></td>
							<td width="20%" align="left">我方签约单位</td>
							<td align="left"><input id="companyname"
								class="easyui-combobox" name="companyname" readonly="readonly"
								data-options="valueField:'code',textField:'name',editable:false,required:true,url:'${contextPath}/rs/dictory/jsonArray/${contract.area}'"
								value="${contract.companyname}" size='25' /></td>

						</tr>

						<tr>
							<td width="20%" align="left">对方签约单位</td>
							<td align="left"><input id="supplisername"
								name="supplisername" type="text" data-options="required:true"
								validType="notNull[]" class="easyui-validatebox"
								value="${contract.supplisername}" maxlength="100" /></td>
							<td width="20%" align="left"></td>
							<td align="left"></td>
						</tr>

						<tr>
							<td width="20%" align="left">币种</td>
							<td align="left"><input id="currency"
								class="easyui-combobox" name="currency"
								value="${contract.currency}"
								data-options="valueField:'code',textField:'name',editable:false,required:true,url:'${contextPath}/rs/dictory/jsonArray/money'" />
							</td>
							<td width="20%" align="left">付款方式</td>
							<td align="left"><input id="paytype" class="easyui-combobox"
								name="paytype" value="${contract.paytype}"
								data-options="valueField:'code',textField:'name',editable:false,required:true,url:'${contextPath}/rs/dictory/jsonArray/paytype'" />

							</td>


						</tr>
						<tr>
							<td width="20%" align="left">合同金额</td>
							<td align="left"><input id="contractsum" name="contractsum"
								type="text" class="easyui-numberbox" precision="2"
								data-options="required:true" min="0.01" max="99999999"
								value="${contract.contractsum}" maxlength="11" /></td>
						</tr>
						<tr>
							<td width="20%" align="left">备注</td>
							<td align="left" colspan="3"><textarea id="remarks"
									name="remarks" class="easyui-validatebox" rows="3" cols="60"
									validType="Maxlength[200]">${contract.remarks}</textarea></td>
						</tr>
						<tr>
							<td colspan="2"><c:if test="${contract.id==null}">
									<a href="javascript:uploadFile(2, 0, 1)">附件上传</a>
										(共<span id="numAttachment2" name="numAttachment2">0</span>个)
									<script type="text/javascript">
										jQuery(function() {
											jQuery
													.get(
															'${contextPath}/others/attachment.do?method=getCount&referId=${contract.id}&referType=2',
															{
																qq : 'xx'
															},
															function(data) {
																document
																		.getElementById("numAttachment2").innerHTML = data.message;
															}, 'json');
										});
									</script>
								</c:if> <c:if test="${contract.id!=null}">
									<a href="javascript:uploadFile(2, ${contract.id}, 1)">附件上传</a>
										(共<span id="numAttachment2" name="numAttachment2">0</span>个)
									<script type="text/javascript">
										jQuery(function() {
											jQuery
													.get(
															'${contextPath}/others/attachment.do?method=getCount&referId=${contract.id}&referType=2',
															{
																qq : 'xx'
															},
															function(data) {
																document
																		.getElementById("numAttachment2").innerHTML = data.message;
															}, 'json');
										});
									</script>
								</c:if></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</body>
</html>