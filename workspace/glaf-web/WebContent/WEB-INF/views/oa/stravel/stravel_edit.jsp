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

		if (editcount1 > 0) {
			alert("当前还有" + editcount1 + "列出差人员记录正在编辑，请先保存出差人员列表。");
			return;
		}

		if (jQuery("#mydatagrid1").datagrid("getRows").length == 0) {
			alert("至少保存一行出差人员明细！");
			return;
		}

		if (editcount2 > 0) {
			alert("当前还有" + editcount2 + "列出差行程记录正在编辑，请先保存出差行程列表。");
			return;
		}

		if (jQuery("#mydatagrid2").datagrid("getRows").length == 0) {
			alert("至少保存一行出差行程明细！");
			return;
		}

		if (editcount3 > 0) {
			alert("当前还有" + editcount3 + "列预计费用记录正在编辑，请先保存预计费用列表。");
			return;
		}

		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/stravel/saveStravel',
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

		if (editcount1 > 0) {
			alert("当前还有" + editcount1 + "列出差人员记录正在编辑，请先保存出差人员列表。");
			return;
		}

		if (jQuery("#mydatagrid1").datagrid("getRows").length == 0) {
			alert("至少保存一行出差人员明细！");
			return;
		}

		if (editcount2 > 0) {
			alert("当前还有" + editcount2 + "列出差行程记录正在编辑，请先保存出差行程列表。");
			return;
		}

		if (jQuery("#mydatagrid2").datagrid("getRows").length == 0) {
			alert("至少保存一行出差行程明细！");
			return;
		}

		if (editcount3 > 0) {
			alert("当前还有" + editcount3 + "列预计费用记录正在编辑，请先保存预计费用列表。");
			return;
		}

		var params = jQuery("#iForm").formSerialize();
		jQuery
				.ajax({
					type : "POST",
					url : '${contextPath}/mx/oa/stravel/saveStravel',
					data : params,
					dataType : 'json',
					error : function(data) {
						alert('服务器处理错误！');
					},
					success : function(data) {
						if (data != null && data.message != null) {
							if (data.statusCode == "200") {
								var travelid = data.id;
								if (travelid != null && travelid != ""
										&& travelid != "0") {
									jQuery
											.ajax({
												type : "POST",
												url : '${contextPath}/mx/oa/stravel/getStravel?travelid='
														+ travelid,
												dataType : 'json',
												error : function(data) {
													alert('服务器处理错误！');
												},
												success : function(data) {
													if (data != null) {
														if (data.company != null
																&& data.company != undefined
																&& data.company != "") {
															if (data.status == "0"
																	|| data.status == "3") {
																jQuery
																		.ajax({
																			type : "POST",
																			url : '${contextPath}/mx/oa/stravel/submit?travelid='
																					+ travelid,
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
														} else {
															alert("该单还有必填项未保存，请保存后再提交");
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

	
	//出差人员明细列表
	var editcount1 = 0;
	jQuery(function() {
		var travelid = jQuery('#travelid').val();
		jQuery('#mydatagrid1')
				.datagrid(
						{
							width : 650,
							height : 220,
							fit : true,
							fitColumns : true,
							nowrap : false,
							striped : true,
							collapsible : true,
							url : '${contextPath}/mx/oa/travelpersonnel/json?travelid='
									+ travelid,
							sortName : 'personnelid',
							sortOrder : 'desc',
							remoteSort : false,
							singleSelect : true,
							idField : 'personnelid',
							columns : [ [
									{
										title : '序号',
										field : 'startIndex',
										width : 40,
										sortable : false
									},
									{
										title : '出差部门',
										field : 'dept',
										width : 80,
										editor : {
											type : 'combobox',
											options : {
												required : true,
												editable : false,
												url : '${contextPath}/rs/dictory/deptJsonArray/012',
												valueField : 'code',
												textField : 'name'
											}
										},
										formatter : function(value, row, index) {
											var dept = "";
											jQuery
													.ajax({
														type : "POST",
														url : '${contextPath}/rs/dictory/deptJsonArray/012',
														dataType : 'json',
														async : false,
														success : function(data) {
															if (data != null
																	&& data.message != null) {
																alert(data.message);
															} else {
																for ( var i = 0; i < data.length; i++) {
																	if (data[i].code == value) {
																		dept = data[i].name;
																	}
																}
															}
														}
													});
											return dept;
										}
									},
									{
										title : '出差人员',
										field : 'personnel',
										width : 150,
										editor : {
											type : 'validatebox',
											options : {
												required : true,
												validType : 'notNullAndLength[200]'
											}
										}
									},
									{
										title : '备注',
										field : 'remark',
										width : 150,
										editor : {
											type : 'validatebox',
											options : {
												validType : 'Maxlength[200]'
											}
										}
									},
									{
										title : '操作',
										field : 'functionKey',
										width : 70,
										formatter : function(value, row, index) {
											if (row.editting) {
												var s = "<a href='#' onclick='savePersonrow("
														+ index + ")'>保存</a> ";
												var c = "<a href='#' onclick='cancelPersonrow("
														+ index + ")'>取消</a> ";
												return s + c;
											} else {
												var e = "<a href='#' onclick='editPersonrow("
														+ index + ")'>编辑</a> ";
												return e;
											}
										}
									} ] ],
							rownumbers : false,
							pagination : false,
							//pageSize:15,
							//pageList: [10,15,20,25,30,40,50,100]
							onBeforeEdit : function(index, row) {
								row.editting = true;
								jQuery('#mydatagrid1').datagrid('refreshRow',
										index);
								editcount1++;
							},
							onAfterEdit : function(index, row) {
								row.editting = false;
								jQuery('#mydatagrid1').datagrid('refreshRow',
										index);
								editcount1--;
							},
							onCancelEdit : function(index, row) {
								row.editting = false;
								jQuery('#mydatagrid1').datagrid('refreshRow',
										index);
								editcount1--;
							}
						});

		var p = jQuery('#mydatagrid1').datagrid('getPager');
		jQuery(p).pagination({
			onBeforeRefresh : function() {
				//alert('before refresh');
			}
		});
	});
	

	//出差人员明细增加
	function addNewPerson() {
		if (editcount1 > 0) {
			alert("当前还有" + editcount1 + "记录正在编辑，不能增加记录。");
			return;
		}
		var index = jQuery("#mydatagrid1").datagrid("getRows").length;
		jQuery("#mydatagrid1").datagrid("appendRow", {});
		jQuery("#mydatagrid1").datagrid("beginEdit", index);
	}

	//出差人员明细删除
	function deleteSelectionPerson() {
		if (editcount1 > 0) {
			alert("当前还有" + editcount1 + "记录正在编辑，暂不能删除。");
			return;
		}
		var rows = jQuery('#mydatagrid1').datagrid('getSelections');
		if (rows == null || rows.length != 1) {
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid1').datagrid('getSelected');
		if (selected) {
			if (confirm("数据删除后不能恢复，确定删除吗？")) {
				jQuery
						.ajax({
							type : "POST",
							url : '${contextPath}/mx/oa/travelpersonnel/delete?personnelid='
									+ selected.personnelid,
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
								jQuery('#mydatagrid1').datagrid('reload');
							}
						});
			}
		}
	}

	//出差人员明细取消
	function cancelPersonrow(indexs) {
		var bol = $("#mydatagrid1").datagrid("validateRow", indexs);
		jQuery('#mydatagrid1').datagrid("cancelEdit", indexs);
		jQuery('#mydatagrid1').datagrid("endEdit", indexs);
		jQuery('#mydatagrid1').datagrid('refreshRow', indexs);
		$("#mydatagrid1").datagrid("selectRow", indexs);
		var rows = $("#mydatagrid1").datagrid("getSelections");
		if (bol == false) {
			if (!(rows[0].personnelid)) {
				jQuery('#mydatagrid1').datagrid('deleteRow', indexs);
			}
		}
	}

	//出差人员明细编辑
	function editPersonrow(indexs) {
		if (editcount1 > 0) {
			alert("当前还有" + editcount1 + "记录正在编辑，暂不能编辑。");
			return;
		}
		jQuery('#mydatagrid1').datagrid("beginEdit", indexs);
	}

	//出差人员明细保存
	function savePersonrow(indexs) {
		var travelid = jQuery('#travelid').val();
		$("#mydatagrid1").datagrid("endEdit", indexs);
		$("#mydatagrid1").datagrid("selectRow", indexs);
		var rows = $("#mydatagrid1").datagrid("getSelections");
		var bol = $("#mydatagrid1").datagrid("validateRow", indexs);
		if (bol == false) {
			alert("请检查输入！");
			return;
		}
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/travelpersonnel/save?travelid='
					+ travelid,
			data : rows[0],
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
				jQuery('#mydatagrid1').datagrid('reload');
			}
		});
	}

	//出差行程明细列表
	var editcount2 = 0;
	jQuery(function() {
		var travelid = jQuery('#travelid').val();
		jQuery('#mydatagrid2')
				.datagrid(
						{
							width : 650,
							height : 220,
							fit : true,
							fitColumns : true,
							nowrap : false,
							striped : true,
							collapsible : true,
							url : '${contextPath}/mx/oa/traveladdress/json?travelid='
									+ travelid,
							sortName : 'addressid',
							sortOrder : 'desc',
							remoteSort : false,
							singleSelect : true,
							idField : 'addressid',
							columns : [ [
									{
										title : '序号',
										field : 'startIndex',
										width : 40,
										sortable : false
									},
									{
										title : '出发地点',
										field : 'startadd',
										width : 150,
										editor : {
											type : 'validatebox',
											options : {
												required : true,
												validType : 'notNullAndLength[100]'
											}
										}
									},
									{
										title : '到达地点',
										field : 'endadd',
										width : 150,
										editor : {
											type : 'validatebox',
											options : {
												required : true,
												validType : 'notNullAndLength[100]'
											}
										}
									},
									{
										title : '交通工具',
										field : 'transportation',
										width : 150,
										editor : {
											type : 'validatebox',
											options : {
												required : true,
												validType : 'notNullAndLength[100]'
											}
										}
									},
									{
										title : '操作',
										field : 'functionKey',
										width : 70,
										formatter : function(value, row, index) {
											if (row.editting) {
												var s = "<a href='#' onclick='saveAddressrow("
														+ index + ")'>保存</a> ";
												var c = "<a href='#' onclick='cancelAddressrow("
														+ index + ")'>取消</a> ";
												return s + c;
											} else {
												var e = "<a href='#' onclick='editAddressrow("
														+ index + ")'>编辑</a> ";
												return e;
											}
										}
									} ] ],
							rownumbers : false,
							pagination : false,
							//pageSize:15,
							//pageList: [10,15,20,25,30,40,50,100]
							onBeforeEdit : function(index, row) {
								row.editting = true;
								jQuery('#mydatagrid2').datagrid('refreshRow',
										index);
								editcount2++;
							},
							onAfterEdit : function(index, row) {
								row.editting = false;
								jQuery('#mydatagrid2').datagrid('refreshRow',
										index);
								editcount2--;
							},
							onCancelEdit : function(index, row) {
								row.editting = false;
								jQuery('#mydatagrid2').datagrid('refreshRow',
										index);
								editcount2--;
							}
						});

		var p = jQuery('#mydatagrid2').datagrid('getPager');
		jQuery(p).pagination({
			onBeforeRefresh : function() {
				//alert('before refresh');
			}
		});
	});

	//出差行程明细增加
	function addNewAddress() {
		if (editcount2 > 0) {
			alert("当前还有" + editcount2 + "记录正在编辑，不能增加记录。");
			return;
		}
		var index = jQuery("#mydatagrid2").datagrid("getRows").length;
		jQuery("#mydatagrid2").datagrid("appendRow", {});
		jQuery("#mydatagrid2").datagrid("beginEdit", index);
	}

	//出差行程明细删除
	function deleteSelectionAddress() {
		if (editcount2 > 0) {
			alert("当前还有" + editcount2 + "记录正在编辑，暂不能删除。");
			return;
		}
		var rows = jQuery('#mydatagrid2').datagrid('getSelections');
		if (rows == null || rows.length != 1) {
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid2').datagrid('getSelected');
		if (selected) {
			if (confirm("数据删除后不能恢复，确定删除吗？")) {
				jQuery
						.ajax({
							type : "POST",
							url : '${contextPath}/mx/oa/traveladdress/delete?addressid='
									+ selected.addressid,
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
								jQuery('#mydatagrid2').datagrid('reload');
							}
						});
			}
		}
	}

	//出差行程明细取消
	function cancelAddressrow(indexs) {
		var bol = $("#mydatagrid2").datagrid("validateRow", indexs);
		jQuery('#mydatagrid2').datagrid("cancelEdit", indexs);
		jQuery('#mydatagrid2').datagrid("endEdit", indexs);
		jQuery('#mydatagrid2').datagrid('refreshRow', indexs);
		$("#mydatagrid2").datagrid("selectRow", indexs);
		var rows = $("#mydatagrid2").datagrid("getSelections");
		if (bol == false) {
			if (!(rows[0].addressid)) {
				jQuery('#mydatagrid2').datagrid('deleteRow', indexs);
			}
		}
	}

	//出差行程明细编辑
	function editAddressrow(indexs) {
		if (editcount2 > 0) {
			alert("当前还有" + editcount2 + "记录正在编辑，暂不能编辑。");
			return;
		}
		jQuery('#mydatagrid2').datagrid("beginEdit", indexs);
	}

	//出差行程明细保存
	function saveAddressrow(indexs) {
		var travelid = jQuery('#travelid').val();
		$("#mydatagrid2").datagrid("endEdit", indexs);
		$("#mydatagrid2").datagrid("selectRow", indexs);
		var rows = $("#mydatagrid2").datagrid("getSelections");
		var bol = $("#mydatagrid2").datagrid("validateRow", indexs);
		if (bol == false) {
			alert("请检查输入！");
			return;
		}
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/traveladdress/save?travelid='
					+ travelid,
			data : rows[0],
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
				jQuery('#mydatagrid2').datagrid('reload');
			}
		});
	}

	//预计费用明细列表
	var editcount3 = 0;
	jQuery(function() {
		var travelid = jQuery('#travelid').val();
		jQuery('#mydatagrid3')
				.datagrid(
						{
							width : 650,
							height : 220,
							fit : true,
							fitColumns : true,
							nowrap : false,
							striped : true,
							collapsible : true,
							url : '${contextPath}/mx/oa/travelfee/json?travelid='
									+ travelid,
							sortName : 'feeid',
							sortOrder : 'desc',
							remoteSort : false,
							singleSelect : true,
							idField : 'feeid',
							columns : [ [
									{
										title : '序号',
										field : 'startIndex',
										width : 40,
										sortable : false
									},
									{
										title : '费用类型',
										field : 'feename',
										width : 150,
										editor : {
											type : 'validatebox',
											options : {
												required : true,
												validType : 'notNullAndLength[100]'
											}
										}
									},
									{
										title : '金额',
										field : 'feesum',
										width : 150,
										editor : {
											type : 'numberbox',
											options : {
												required : true,
												precision : 2,
												max : 99999999,
												min : 0.01
											}
										}
									},
									{
										title : '备注',
										field : 'remark',
										width : 150,
										editor : {
											type : 'validatebox',
											options : {
												validType : 'Maxlength[200]'
											}
										}
									},
									{
										title : '操作',
										field : 'functionKey',
										width : 70,
										formatter : function(value, row, index) {
											if (row.editting) {
												var s = "<a href='#' onclick='saveFeerow("
														+ index + ")'>保存</a> ";
												var c = "<a href='#' onclick='cancelFeerow("
														+ index + ")'>取消</a> ";
												return s + c;
											} else {
												var e = "<a href='#' onclick='editFeerow("
														+ index + ")'>编辑</a> ";
												return e;
											}
										}
									} ] ],
							rownumbers : false,
							pagination : false,
							//pageSize:15,
							//pageList: [10,15,20,25,30,40,50,100]
							onBeforeEdit : function(index, row) {
								row.editting = true;
								jQuery('#mydatagrid3').datagrid('refreshRow',
										index);
								editcount3++;
							},
							onAfterEdit : function(index, row) {
								row.editting = false;
								jQuery('#mydatagrid3').datagrid('refreshRow',
										index);
								editcount3--;
							},
							onCancelEdit : function(index, row) {
								row.editting = false;
								jQuery('#mydatagrid3').datagrid('refreshRow',
										index);
								editcount3--;
							},
							onLoadSuccess : function(data) {
								jQuery('#feesumAccount').html(
										data.feesumAccount);
							}
						});

		var p = jQuery('#mydatagrid3').datagrid('getPager');
		jQuery(p).pagination({
			onBeforeRefresh : function() {
				//alert('before refresh');
			}
		});
	});
	

	//预计费用明细增加
	function addNewFee() {
		if (editcount3 > 0) {
			alert("当前还有" + editcount3 + "记录正在编辑，不能增加记录。");
			return;
		}
		var index = jQuery("#mydatagrid3").datagrid("getRows").length;
		jQuery("#mydatagrid3").datagrid("appendRow", {});
		jQuery("#mydatagrid3").datagrid("beginEdit", index);
	}

	//预计费用明细删除
	function deleteSelectionFee() {
		if (editcount3 > 0) {
			alert("当前还有" + editcount3 + "记录正在编辑，暂不能删除。");
			return;
		}
		var rows = jQuery('#mydatagrid3').datagrid('getSelections');
		if (rows == null || rows.length != 1) {
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid3').datagrid('getSelected');
		if (selected) {
			if (confirm("数据删除后不能恢复，确定删除吗？")) {
				jQuery.ajax({
					type : "POST",
					url : '${contextPath}/mx/oa/travelfee/delete?feeid='
							+ selected.feeid,
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
						jQuery('#mydatagrid3').datagrid('reload');

						jQuery('#feesumAccount')
								.html(
										jQuery('#mydatagrid3').datagrid(
												'getData').feesumAccount);
					}
				});
			}
		}
	}

	//预计费用明细取消
	function cancelFeerow(indexs) {
		var bol = $("#mydatagrid3").datagrid("validateRow", indexs);
		jQuery('#mydatagrid3').datagrid("cancelEdit", indexs);
		jQuery('#mydatagrid3').datagrid("endEdit", indexs);
		jQuery('#mydatagrid3').datagrid('refreshRow', indexs);
		$("#mydatagrid3").datagrid("selectRow", indexs);
		var rows = $("#mydatagrid3").datagrid("getSelections");
		if (bol == false) {
			if (!(rows[0].feeid)) {
				jQuery('#mydatagrid3').datagrid('deleteRow', indexs);
			}
		}
	}

	//预计费用明细编辑
	function editFeerow(indexs) {
		if (editcount3 > 0) {
			alert("当前还有" + editcount3 + "记录正在编辑，暂不能编辑。");
			return;
		}
		jQuery('#mydatagrid3').datagrid("beginEdit", indexs);
	}

	//预计费用明细保存
	function saveFeerow(indexs) {
		var travelid = jQuery('#travelid').val();
		$("#mydatagrid3").datagrid("endEdit", indexs);
		$("#mydatagrid3").datagrid("selectRow", indexs);
		var rows = $("#mydatagrid3").datagrid("getSelections");
		var bol = $("#mydatagrid3").datagrid("validateRow", indexs);
		if (bol == false) {
			alert("请检查输入！");
			return;
		}
		jQuery
				.ajax({
					type : "POST",
					url : '${contextPath}/mx/oa/travelfee/save?travelid='
							+ travelid,
					data : rows[0],
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
						jQuery('#mydatagrid3').datagrid('reload');

						jQuery('#feesumAccount')
								.html(
										jQuery('#mydatagrid3').datagrid(
												'getData').feesumAccount);
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
				<span class="x_content_title">出差申请</span> <a href="#"
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
				<input type="hidden" id="travelid" name="travelid"
					value="${stravel.travelid}" />
				<table class="easyui-form" style="width: 940px;" align="center">
					<tbody>
						<tr>
							<td width="20%" align="left">申请单位：</td>
							<td align="left"><input id="company" class="easyui-combobox"
								name="company" value="${stravel.company}" editable="false"
								data-options="required:false,valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/${stravel.area}'" />
							</td>
							<td width="20%" align="left">地区：</td>
							<td align="left"><input id="area" class="easyui-combobox"
								name="area" value="${stravel.area}" disabled="disabled"
								data-options="required:false,valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/jsonArray/eara'" />
							</td>
							<td width="20%" align="left">部门：</td>
							<td align="left"><input id="dept" class="easyui-combobox"
								name="dept" value="${stravel.dept}" disabled="disabled"
								data-options="required:true,	valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'" />
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">申请人：</td>
							<td align="left"><input id="appuser" class="easyui-combobox"
								name="appuser" value="${stravel.appuser}"
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
								value="${stravel.post}" /></td>
							<td width="20%" align="left">申请日期：</td>
							<td align="left"><input id="appdate" name="appdate"
								type="text" disabled="disabled" class="easyui-datebox"
								data-options="required:true" value="${appdate}"
								pattern="yyyy-MM-dd" /></td>
						</tr>
						<tr>
							<td width="20%" align="left">起讫时间：</td>
							<td align="left" colspan="5"><input id="startdate"
								name="startdate" type="text" class="easyui-datebox"
								style="width: 130px" data-options="required:true"
								missingMessage="日期必须填写" editable="false" value="${startdate}"
								pattern="yyyy-MM-dd" />至 <input id="enddate" name="enddate"
								type="text" class="easyui-datebox" style="width: 130px"
								data-options="required:true" missingMessage="日期必须填写"
								editable="false" value="${enddate}" pattern="yyyy-MM-dd" />共 <input
								id="travelsum" name="travelsum" type="text"
								data-options="required:true,onkeyup:function(value,row,index) {
											                            	this.value=this.value.replace(/[^\\d]/g,'');
											                            },onafterpaste:function(value,row,index) {
											                            	this.value=this.value.replace(/[^\\d]/g,'');
											                            }"
								class="easyui-numberbox" precision="2" style="width: 25px"
								value="${stravel.travelsum}" />天</td>
						</tr>
						<tr>
							<td width="20%" align="left">事由：</td>
							<td align="left" colspan="5"><textarea id="content"
									name="content"  style="width: 580px;height:90px;"  data-options="required:true"
									validType="notNullAndLength[200]" class="easyui-validatebox">${stravel.content}</textarea>
							</td>
						</tr>
						<tr>
							<td align="left" colspan="6">
								<div class="toolbar-backgroud">
									<img src="${contextPath}/images/window.png"> &nbsp;<span
										class="x_content_title">出差人员:</span> <a href="#"
										class="easyui-linkbutton"
										data-options="plain:true, iconCls:'icon-add'"
										onclick="javascript:addNewPerson();">增加</a> <a href="#"
										class="easyui-linkbutton"
										data-options="plain:true, iconCls:'icon-remove'"
										onclick="javascript:deleteSelectionPerson();">删除</a>
								</div>
								<div data-options="region:'center',border:true"
									style="height: 120px">
									<table id="mydatagrid1">
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td align="left" colspan="6">
								<div class="toolbar-backgroud">
									<img src="${contextPath}/images/window.png"> &nbsp;<span
										class="x_content_title">出差行程:</span> <a href="#"
										class="easyui-linkbutton"
										data-options="plain:true, iconCls:'icon-add'"
										onclick="javascript:addNewAddress();">增加</a> <a href="#"
										class="easyui-linkbutton"
										data-options="plain:true, iconCls:'icon-remove'"
										onclick="javascript:deleteSelectionAddress();">删除</a>
								</div>
								<div data-options="region:'center',border:true"
									style="height: 120px">
									<table id="mydatagrid2">
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">出差补贴：</td>
							<td align="left" colspan="5"><input type="radio"
								name="traveltype" id="traveltype"
								${stravel.traveltype==0?'checked=\"true\"':''} value="0">因公出差补助（国内）：60元/人/天

								<input type="radio" name="traveltype" id="traveltype"
								${stravel.traveltype==1?'checked=\"true\"':''} value="1">因公出差补助（国外）：如属于权限内正常开销范畴，实报实销
							</td>
						</tr>
						<tr>
							<td align="left" colspan="6">
								<div class="toolbar-backgroud">
									<img src="${contextPath}/images/window.png"> &nbsp;<span
										class="x_content_title">预计费用（单位：人民币元）:</span> <a href="#"
										class="easyui-linkbutton"
										data-options="plain:true, iconCls:'icon-add'"
										onclick="javascript:addNewFee();">增加</a> <a href="#"
										class="easyui-linkbutton"
										data-options="plain:true, iconCls:'icon-remove'"
										onclick="javascript:deleteSelectionFee();">删除</a>
								</div>
								<div data-options="region:'center',border:true"
									style="height: 120px">
									<table id="mydatagrid3">
									</table>
								</div>

								<div class="toolbar-backgroud"
									style="height: 15px; font-size: 13px;">
									合计: <span id="feesumAccount"></span>
								</div>
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">其他事项：</td>
							<td align="left" colspan="5"><textarea id="others"
									name="others" style="width: 91%" validType="Maxlength[200]"
									class="easyui-validatebox">${stravel.others}</textarea></td>
						</tr>
						<tr>
							<td width="20%" align="left">附件：</td>
							<td align="left" colspan="5"><a
								href="javascript:uploadFile(9, ${stravel.travelid}, 1)">附件上传
									<img src="${contextPath}/images/folder3_document.png"
									border="0">
							</a> (共<span id="numAttachment9" name="numAttachment9">0</span>个) <script
									type="text/javascript">
								jQuery(function() {
									jQuery
											.get(
													'${contextPath}/others/attachment.do?method=getCount&referId=${stravel.travelid}&referType=9',
													{
														qq : 'xx'
													},
													function(data) {
														document
																.getElementById("numAttachment9").innerHTML = data.message;
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