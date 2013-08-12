<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
	request.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同申请审核</title>
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
var contextPath = "${contextPath}";
var id = "${contract.id}";

function saveData() {
	var params = jQuery("#iForm").formSerialize();
	jQuery
			.ajax( {
				type : "POST",
				url : '${contextPath}/mx/oa/contract/saveContract',
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



jQuery(function() {

	if(${lookover}){
		jQuery("#reviewDiv").hide();
		jQuery("#titleSpan").html("合同申请查看");
	}
	 
});

function review() {
	//jQuery('#dlgApprove').dialog('open').dialog('setTitle','批量审批');
	$('#dlgApprove').dialog('open').dialog( {
		title : '审核',
		left : 200,
		top : 100,
		closed : false,
		modal : true
	});
	jQuery('#dlgApprove').form('clear');
}


function passData() {
		if (confirm("确认审核通过吗？")) {
			var params = jQuery('#approveOpinion').serialize();
			jQuery
					.ajax( {
						type : "POST",
						url : '${contextPath}/mx/oa/contract/submitData?id=' + id,
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
							jQuery('#mydatagrid').datagrid('reload');
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
			jQuery
					.ajax( {
						type : "POST",
						url : '${contextPath}/mx/oa/contract/rollbackData?id='+ id,
						dataType : 'json',
						data: params,
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
							jQuery('#mydatagrid').datagrid('reload');
						}
					});
			jQuery('#dlgApprove').dialog('close');
		}
}
</script>
</head>

<body>
	<div style="margin: 0;"></div>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<span class="x_content_title" id="titleSpan">合同申请审核</span>

				<div style="display: inline" id="reviewDiv">
					<a href="#" class="easyui-linkbutton"
						data-options="plain:true, iconCls:'icon-submit'"
						onclick="javascript:review();">审核</a>
				</div>
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
								name="appuser" value="${contract.appuser}" disabled
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
							<td align="left"><input id="post" name="post"
								type="easyui-combobox" readonly disabled
								class="easyui-validatebox" value="${contract.post}" /></td>
							<td width="20%" align="left">部门</td>
							<td align="left"><input id="dept" class="easyui-combobox"
								name="dept" value="${contract.dept}" disabled
								data-options="valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'" />
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">合同名称</td>
							<td align="left"><input id="contactname" name="contactname"
								type="text" data-options="required:true"
								class="easyui-validatebox" disabled
								value="${contract.contactname}" maxlength="100" /></td>
							<td width="20%" align="left">合同编号</td>

							<td align="left"><input id="contractno" name="contractno"
								type="text" disabled data-options="required:true"
								class="easyui-validatebox" value="${contract.contractno}"
								maxlength="50" /></td>

						</tr>
						<tr>
							<td width="20%" align="left">项目名称</td>
							<td align="left"><input id="projrctname" name="projrctname"
								type="text" disabled data-options="required:true"
								class="easyui-validatebox" value="${contract.projrctname}"
								maxlength="100" /></td>
							<td width="20%" align="left">
								<div style="width: 80px;">我方签约单位</div>

							</td>
							<td align="left"><input id="companyname"
								class="easyui-combobox" disabled name="companyname"
								readonly="readonly"
								data-options="valueField:'code',textField:'name',editable:false,required:true,url:'${contextPath}/rs/dictory/jsonArray/${contract.area}'"
								value="${contract.companyname}" size='25' /></td>

						</tr>

						<tr>
							<td width="20%" align="left">
								<div style="width: 80px;">对方签约单位</div>
							</td>
							<td align="left"><input id="supplisername"
								name="supplisername" type="text" data-options="required:true"
								class="easyui-validatebox" value="${contract.supplisername}"
								disabled maxlength="100" /></td>
							<td width="20%" align="left"></td>
							<td align="left"></td>
						</tr>

						<tr>
							<td width="20%" align="left">币种</td>
							<td align="left"><input id="currency"
								class="easyui-combobox" name="currency"
								value="${contract.currency}" disabled
								data-options="valueField:'code',textField:'name',editable:false,required:true,url:'${contextPath}/rs/dictory/jsonArray/money'" />
							</td>
							<td width="20%" align="left">付款方式</td>
							<td align="left"><input id="paytype" class="easyui-combobox"
								name="paytype" value="${contract.paytype}" disabled
								data-options="valueField:'code',textField:'name',editable:false,required:true,url:'${contextPath}/rs/dictory/jsonArray/paytype'" />

							</td>


						</tr>
						<tr>
							<td width="20%" align="left">合同金额</td>
							<td align="left"><input id="contractsum" name="contractsum"
								type="text" class="easyui-numberbox" precision="2" disabled
								data-options="required:true" value="${contract.contractsum}"
								maxlength="8" /></td>
						</tr>
						<tr>
							<td width="20%" align="left">备注</td>
							<td align="left" colspan="3"><textarea id="remarks"
									name="remarks" disabled class="easyui-validatebox" rows="3"
									cols="60">${contract.remarks}</textarea></td>
						</tr>
						<tr>
							<td colspan="2"><a
								href="javascript:uploadFile(2, ${contract.id}, 0)">附件下载</a> <jsp:include
									page="/others/attachment/showCount">
									<jsp:param name="referType" value="2" />
									<jsp:param name="referId" value="${contract.id}" />
								</jsp:include></td>
						</tr>
						<tr>

							<td colspan="4"><input type="hidden" id="processInstanceId"
								name="processInstanceId" value="${contract.processinstanceid}" />
								<div style="width: 750px;">
									<%@ include file="/WEB-INF/views/oa/common/task.jsp"%>
								</div></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<%@ include file="/WEB-INF/views/oa/common/approve_foot.jsp"%>
	</div>
</body>
</html>