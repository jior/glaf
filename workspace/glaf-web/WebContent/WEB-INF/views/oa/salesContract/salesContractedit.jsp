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
	var editcount = 0;

	jQuery(function() {
		var area = jQuery('#area').val();
		if (jQuery('#currency').val() == '') {
			jQuery('#currency').combobox('setValue', 'RMB');
		}
		if (jQuery('#paytype').val() == '') {
			jQuery('#paytype').combobox('setValue', '1');
		}
		if ("" != area) {
			var url = '${contextPath}/rs/dictory/jsonArray/' + area;
			jQuery('#companyname').combobox('reload', url);
		}

	});

	
	jQuery(function() {
		var contractid = jQuery('#id').val();
		if (contractid == "") {
			contractid = 0;
			jQuery('#companyname').combobox('reload', url);
		}

		jQuery('#mydatagrid')
				.datagrid(
						{
							width : 880,
							height : 80,
							fit : true,
							fitColumns : true,
							nowrap : false,
							collapsible : true,
							url : '${contextPath}/mx/oa/optional/json?id='
									+ contractid,
							sortName : 'id',
							sortOrder : 'desc',
							remoteSort : false,
							singleSelect : true,
							idField : 'optionalid',
							columns : [ [
									{
										title : '序号',
										field : 'startIndex',
										width : 120,
										sortable : false
									},
									{
										title : '选配代码 ',
										field : 'code',
										width : 120,
										sortable : false,
										editor : {
											type : 'validatebox',
											options : {
												required : true,
												validType : 'notNullAndLength[20]'

											}
										}
									},
									{
										title : '选配价格',
										field : 'price',
										width : 120,
										sortable : false,
										editor : {
											type : 'numberbox',
											options : {
												required : true,
												precision : 2,
												min : 0.00,
												max : 99999999
											}
										}
									},
									{
										title : '备注 ',
										field : 'remark',
										width : 120,
										sortable : false,
										editor : {
											type : 'text'
										}
									},
									{
										field : 'functionKey',
										title : '功能键',
										width : 120,
										formatter : function(value, row, index) {
											if (row.editing) {
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
									}, {
										title : '主键ID',
										field : 'optionalId',
										width : 120,
										sortable : false,
										hidden : true
									} ] ],
							onBeforeEdit : function(index, row) {
								row.editing = true;
								jQuery('#mydatagrid').datagrid('refreshRow',
										index);
								editcount++;
							},
							onAfterEdit : function(index, row) {
								row.editing = false;
								jQuery('#mydatagrid').datagrid('refreshRow',
										index);
								editcount--;
							},
							onCancelEdit : function(index, row) {
								row.editing = false;
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

	
	function addNew() {
		if (editcount > 0) {
			jQuery.messager.alert("警告", "当前还有" + editcount + "记录正在编辑，不能增加记录。");
			return;
		}

		var index = jQuery('#mydatagrid').datagrid('getRows').length;
		jQuery('#mydatagrid').datagrid('appendRow', {
			startIndex : index + 1
		});
		jQuery('#mydatagrid').datagrid('beginEdit', index);
	}
	
	
	function saverow(index) {
		var bol = $("#mydatagrid").datagrid("validateRow", index);
		if (bol == false) {
			alert("请填写完整的选配信息");
			return;
		}

		var contractid = jQuery('#id').val();
		if (null == contractid || "" == contractid) {
			contractid = 0;
		}
		jQuery('#mydatagrid').datagrid("selectRow", index);
		var params = jQuery('#mydatagrid').datagrid("getSelected");

		jQuery('#mydatagrid').datagrid('endEdit', index);
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/optional/save?id=' + contractid,
			data : params,
			dataType : 'json',
			async : false,
			error : function(data) {
				alert('服务器处理错误！');
			},
			success : function(data) {

				var rows = $("#mydatagrid").datagrid("getRows");
				var optionalsum = 0;
				for ( var i = 0; i < rows.length; i++) {
					optionalsum = optionalsum + parseFloat(rows[i].price);
				}
				$("#optionalsum").numberbox('setValue',
						Math.round(optionalsum * 100) / 100);
				if (data != null && data.message != null) {
					alert(data.message);
				} else {
					alert('操作成功！');
				}
				jQuery('#mydatagrid').datagrid('reload');
			}
		});
	}
	
	
	function editrow(index) {
		if (editcount > 0) {
			jQuery.messager.alert("警告", "当前还有" + editcount + "记录正在编辑，暂不能编辑。");
			return;
		}
		jQuery('#mydatagrid').datagrid('beginEdit', index);
	}
	
	
	function cancelrow(index) {
		var bol = $("#mydatagrid").datagrid("validateRow", index);
		jQuery('#mydatagrid').datagrid("cancelEdit", index);
		jQuery('#mydatagrid').datagrid("endEdit", index);
		jQuery('#mydatagrid').datagrid('refreshRow', index);
		$("#mydatagrid").datagrid("selectRow", index);
		var rows = $("#mydatagrid").datagrid("getSelections");
		if (!(rows[0].optionalId)) {
			jQuery('#mydatagrid').datagrid('deleteRow', index);
		}
	}

	function deleteSelections() {
		if (editcount > 0) {
			jQuery.messager.alert("警告", "当前还有" + editcount
					+ "记录正在编辑，请先取消编辑后，执行删除。");
			return;
		}
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if (rows == null || rows.length != 1) {
			alert("请选择其中一条记录。");
			return;
		}
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].optionalId);
		}
		if (confirm("数据删除后不能恢复，确定删除吗？")) {
			var optionalIds = ids.join(',');
			jQuery.ajax({
				type : "POST",
				url : '${contextPath}/mx/oa/optional/delete?optionalIds='
						+ optionalIds,
				dataType : 'json',
				error : function(data) {
					alert('服务器处理错误！');
				},
				success : function(data) {

					var rows = $("#mydatagrid").datagrid("getRows");
					var optionalsum = 0;
					for ( var i = 0; i < rows.length; i++) {
						if (rows[i].optionalId == optionalIds) {
							continue;
						}
						optionalsum = optionalsum + parseFloat(rows[i].price);
					}
					$("#optionalsum").numberbox('setValue',
							Math.round(optionalsum * 100) / 100);

					if (data != null && data.message != null) {
						alert(data.message);
					} else {
						alert('操作成功！');
					}
					jQuery('#mydatagrid').datagrid('reload');

				}
			});
		}
	}

	function saveData() {
		var params = jQuery("#iForm").formSerialize();
		var bol = jQuery("#iForm").form('validate');
		if (bol == false) {
			alert("请检查输入！");
			return;
		}
		if (editcount > 0) {
			alert("还有正在编辑的选配信息,请先保存");
			return;
		}
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/salescontract/save',
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
		if (editcount > 0) {
			alert("还有正在编辑的选配信息,请先保存");
			return;
		}
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/salescontract/saveSalescontract',
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
				<span class="x_content_title">合同申请</span>
				<!-- <input type="button" name="save" value=" 保存 " class="button btn btn-primary" onclick="javascript:saveData();">
	<input type="button" name="saveAs" value=" 另存 " class="button btn" onclick="javascript:saveAsData();">
	<input type="button" name="back" value=" 返回 " class="button btn" onclick="javascript:history.back();"> -->
				<a href="#" class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-save'"
					onclick="javascript:saveData();">保存</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-submit'"
					onclick="javascript:saveAsData();">提交</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-cancel'"
					onclick="javascript:closeDialog();">关闭</a>
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
				<input type="hidden" id="id" name="id" value="${salescontract.id}" />
				<input type="hidden" id="status" name="status"
					value="${salescontract.status}" />
				<table class="easyui-form" style="width: 880px;" align="center"
					border="0px ">
					<tbody>
						<tr>
							<td width="30%" align="left">
								<div style="width: 60px;">合同编号*</div>

							</td>
							<td align="left"><input id="contractno" name="contractno"
								type="text" validType="notNull[]" class="easyui-validatebox"
								value="${salescontract.contractno}" maxlength="50"
								data-options="required:true" /></td>
							<td width="30%" align="left">合同名称*</td>
							<td align="left"><input id="contactname" name="contactname"
								type="text" class="easyui-validatebox" validType="notNull[]"
								value="${salescontract.contactname}" maxlength="100"
								data-options="required:true" /></td>
							<td width="30%" align="left">项目名称*</td>
							<td align="left" colspan="1"><input id="projrctname"
								name="projrctname" type="text" class="easyui-validatebox"
								value="${salescontract.projrctname}" maxlength="100"
								data-options="required:true" /></td>


						</tr>
						<tr>

							<td width="20%" align="left">地区</td>
							<td align="left"><input id="area" class="easyui-combobox"
								name="area" size="12" value="${salescontract.area}" disabled
								data-options="valueField:'code',textField:'name',required:true,editable:false,url:'${contextPath}/rs/dictory/jsonArray/eara',
					onSelect:function(ret){										
					var url = '${contextPath}/rs/dictory/jsonArray/'+ret.code;
						jQuery('#companyname').combobox('clear');
						jQuery('#companyname').combobox('reload',url);
						jQuery('#area').val(ret.code);
					}																	
					" />
							</td>
							<td width="30%" align="left">
								<div style="width: 85px;">我方签约单位*</div>

							</td>
							<td align="left" colspan="1"><input id="companyname"
								class="easyui-combobox" name="companyname"
								data-options="valueField:'code',textField:'name',required:true,editable:false,url:'${contextPath}/rs/dictory/jsonArray/${salescontract.area}'"
								value="${salescontract.companyname}" size='26' /></td>
							<td width="30%" align="left">
								<div style="width: 85px;">对方签约单位*</div>

							</td>
							<td align="left" colspan="1"><input id="supplisername"
								name="supplisername" type="text" class="easyui-validatebox"
								validType="notNull[]" value="${salescontract.supplisername}"
								maxlength="100" data-options="required:true" /></td>
						</tr>

						<tr>
							<td width="30%" align="left">币种*</td>
							<td align="left"><input id="currency"
								class="easyui-combobox" name="currency"
								value="${salescontract.currency}"
								data-options="valueField:'code',textField:'name',required:true,editable:false ,url:'${contextPath}/rs/dictory/jsonArray/money'" />
							</td>
							<td width="30%" align="left">付款方式*</td>
							<td align="left"><input id="paytype" class="easyui-combobox"
								name="paytype" value="${salescontract.paytype}"
								data-options="valueField:'code',textField:'name',required:true,editable:false,url:'${contextPath}/rs/dictory/jsonArray/paytype'" />

							</td>
							<td width="30%" align="left"">合同金额*</td>
							<td align="left"><input id="contractsum" name="contractsum"
								type="text" class="easyui-numberbox" precision="2" min="0.00"
								max="99999999" value="${salescontract.contractsum}"
								maxlength="11" data-options="required:true" /></td>
						</tr>
						<tr>
							<td width="30%" align="left">备注:</td>
						</tr>
						<tr>
							<td colspan="6">
								<div
									style="width: 860px; height: 290px; border: 1px solid #CCCCCC;">
									<table width="100%">
										<tr>
											<td width="30%" align="left"></td>
											<td align="left"></td>
											<td width="30%" align="left"></td>
											<td></td>
											<td width="30%" align="left"></td>
											<td></td>
										</tr>
										<tr>

											<td width="30%" align="left"></td>
											<td></td>
											<td width="30%" align="left"></td>
											<td></td>
											<td width="30%" align="left"></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td><a href="#" class="easyui-linkbutton"
												data-options="plain:true, iconCls:'icon-add'"
												onclick="javascript:addNew();">新增</a></td>
											<td><a href="#" class="easyui-linkbutton"
												data-options="plain:true, iconCls:'icon-remove'"
												onclick="javascript:deleteSelections();">删除</a></td>
										</tr>
										<tr>
											<td colspan="6">
												<div
													style="width: 99%; height: 90px; overflow-y: auto; border: 1px solid #CCCCCC">
													<table width="100%" border="1px" class="easyui-datagrid"
														id="mydatagrid" align="center">

													</table>
												</div>
											</td>
										</tr>
										<tr>
											<td width="30%" align="left"></td>
											<td></td>
											<td width="30%" align="left">首付款*</td>
											<td><input id="firstpay" name="firstpay" type="text"
												class="easyui-numberbox" precision="2" min="0.00"
												max="99999999" value="${salescontract.firstpay}"
												maxlength="11" data-options="required:true" /></td>
											<td width="30%" align="left">尾款*</td>
											<td><input id="lastpay" name="lastpay" type="text"
												class="easyui-numberbox" precision="2" min="0.00"
												max="99999999" value="${salescontract.lastpay}"
												maxlength="11" data-options="required:true" /></td>
										</tr>
										<tr>
											<td width="30%" align="left"></td>
											<td></td>
											<td width="30%" align="left"></td>
											<td></td>
											<td width="30%" align="left"></td>
											<td></td>
										</tr>
										<tr>
											<td width="30%" align="left"></td>
											<td></td>
											<td width="30%" align="left">销售顾问*</td>
											<td><input id="sales" name="sales" type="text"
												validType="notNull[]" class="easyui-validatebox"
												value="${salescontract.sales}" maxlength="50"
												data-options="required:true" /></td>
											<td width="30%" align="left">合同留存销售顾问*</td>
											<td><input id="contractsales" name="contractsales"
												type="text" class="easyui-validatebox" validType="notNull[]"
												value="${salescontract.contractsales}" maxlength="50"
												data-options="required:true" /></td>
										</tr>
										<tr>
											<td width="30%" align="left"></td>
											<td></td>
											<td width="30%" align="left"></td>
											<td></td>
											<td width="30%" align="left">备注条款</td>
											<td><input id="remark" name="remark" type="text"
												class="easyui-validatebox" value="${salescontract.remark}"
												maxlength="50" /></td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2"><c:if test="${salescontract.id==null}">
									<a href="javascript:uploadFile(3, 0, 1)">附件上传</a>
										(共<span id="numAttachment3" name="numAttachment3">0</span>个)
								<script type="text/javascript">
									jQuery(function() {
										jQuery
												.get(
														'${contextPath}/others/attachment.do?method=getCount&referId=0&referType=3',
														{
															qq : 'xx'
														},
														function(data) {
															document
																	.getElementById("numAttachment3").innerHTML = data.message;
														}, 'json');
									});
								</script>
								</c:if> <c:if test="${salescontract.id!=null}">
									<a href="javascript:uploadFile(3, ${salescontract.id}, 1)">附件上传</a>
										(共<span id="numAttachment3" name="numAttachment3">0</span>个)
								<script type="text/javascript">
									jQuery(function() {
										jQuery
												.get(
														'${contextPath}/others/attachment.do?method=getCount&referId=${salescontract.id}&referType=3',
														{
															qq : 'xx'
														},
														function(data) {
															document
																	.getElementById("numAttachment3").innerHTML = data.message;
														}, 'json');
									});
								</script>
								</c:if></td>
						</tr>
						<tr>
							<td width="20%" align="left">申请人*</td>
							<td align="left"><input id="appuser" class="easyui-combobox"
								name="appuser" value="${salescontract.appuser}"
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
   if(jQuery('#headship')!=null) {
    jQuery('#headship').val(data.headship);
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
   if(jQuery('#headship')!=null) {
    jQuery('#headship').val(data.headship);
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
							<td>职位</td>
							<td><input id="headship" name="headship" type="text"
								readonly class="easyui-validatebox"
								value='${salescontract.headship}' /></td>
							<td width="20%" align="left">部门</td>
							<td><input id="dept" class="easyui-combobox" name="dept"
								value="${salescontract.dept}" disabled
								data-options="valueField:'code',textField:'name',required:true,url:'${contextPath}/rs/dictory/deptJsonArray/012'" />
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</body>
</html>