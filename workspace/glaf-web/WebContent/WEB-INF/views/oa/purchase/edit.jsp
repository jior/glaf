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
	//保存
	function saveData() {
		var params = jQuery("#iForm").formSerialize();
		var bol = jQuery("#iForm").form('validate');
		if (bol == false) {
			alert("请检查输入！");
			return;
		}
		var purchasesum = jQuery('#purchasesum').val();
		if (purchasesum == "" || purchasesum == 0) {
			alert("请填写采购明细信息。");
			return;
		}
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/purchase/save',
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
	//提交
	function saveAsData() {
		var params = jQuery("#iForm").formSerialize();
		var bol = jQuery("#iForm").form('validate');
		if (bol == false) {
			alert("请检查输入！");
			return;
		}
		var purchasesum = jQuery('#purchasesum').val();
		if (purchasesum == "" || purchasesum == 0) {
			alert("请填写采购明细信息。");
			return;
		}
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/purchase/submit',
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

	//明细列表
	var editcount = 0;
	jQuery(function() {
		var purchaseId = jQuery('#purchaseid').val();
		jQuery('#mydatagrid')
				.datagrid(
						{
							width : 745,
							height : 220,
							fit : true,
							fitColumns : true,
							nowrap : false,
							striped : true,
							collapsible : true,
							url : '${contextPath}/mx/oa/purchaseitem/json?purchaseId='
									+ purchaseId,
							sortName : 'id',
							sortOrder : 'desc',
							remoteSort : false,
							singleSelect : true,
							idField : 'purchaseItemId',
							columns : [ [
									{
										title : '序号',
										field : 'startIndex',
										width : 80,
										sortable : false
									},
									{
										title : '内容',
										field : 'content',
										width : 120,
										editor : {
											type : 'validatebox',
											options : {
												required : true,
												validType : 'notNullAndLength[200]'
											}
										}
									},
									{
										title : '规格要求',
										field : 'specification',
										width : 120,
										editor : {
											type : 'validatebox',
											options : {
												required : true,
												validType : 'Maxlength[50]'
											}
										}
									},
									{
										title : '数量',
										field : 'quantity',
										width : 120,
										editor : {
											type : 'numberbox',
											options : {
												required : true,
												min : 1,
												max : 99999999
											}
										}
									},
									{
										title : '参考单价',
										field : 'referenceprice',
										width : 120,
										editor : {
											type : 'numberbox',
											options : {
												required : true,
												min : 0.01,
												max : 99999999,
												precision : 2
											}
										}
									},
									{
										title : '总价',
										field : 'totalprice',
										width : 120
									},
									{
										title : '备注',
										field : 'remark',
										width : 120,
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
										width : 120,
										formatter : function(value, row, index) {
											if (row.editting) {
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
									} ] ],
							rownumbers : false,
							pagination : false,
							//pageSize:15,
							//pageList: [10,15,20,25,30,40,50,100]
							onBeforeEdit : function(index, row) {
								row.editting = true;
								jQuery('#mydatagrid').datagrid('refreshRow',
										index);
								editcount++;
							},
							onAfterEdit : function(index, row) {
								row.editting = false;
								jQuery('#mydatagrid').datagrid('refreshRow',
										index);
								editcount--;
							},
							onCancelEdit : function(index, row) {
								row.editting = false;
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
	
	
	//明细增加
	function addNew() {
		if (editcount > 0) {
			alert("当前还有" + editcount + "记录正在编辑，不能增加记录。");
			return;
		}
		var index = jQuery("#mydatagrid").datagrid("getRows").length;
		jQuery("#mydatagrid").datagrid("appendRow", {});
		jQuery("#mydatagrid").datagrid("beginEdit", index);
	}
	
	
	//明细删除
	function deleteSelections() {
		if (editcount > 0) {
			alert("当前还有" + editcount + "记录正在编辑，暂不能删除。");
			return;
		}
		var purchaseId = jQuery('#purchaseid').val();
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		if (ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")) {
			var purchaseitemIds = ids.join(',');
			jQuery
					.ajax({
						type : "POST",
						url : '${contextPath}/mx/oa/purchaseitem/delete?purchaseitemIds='
								+ purchaseitemIds + '&purchaseId=' + purchaseId,
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
							jQuery('#mydatagrid').datagrid('reload');
						}
					});
			var allRows = $("#mydatagrid").datagrid("getRows");
			var allPrices = 0;
			for ( var i = 0; i < allRows.length; i++) {
				if (allRows[i].id == ids) {
					continue;
				}
				allPrices += allRows[i].quantity * allRows[i].referenceprice;
			}
			jQuery("#purchasesum").val(allPrices);
			jQuery("#purchasesum_").html(allPrices);
		} else {
			alert("请选择至少一条记录。");
		}
	}
	
	
	//明细取消
	function cancelrow(indexs) {
		var bol = $("#mydatagrid").datagrid("validateRow", indexs);
		jQuery('#mydatagrid').datagrid("cancelEdit", indexs);
		jQuery('#mydatagrid').datagrid("endEdit", indexs);
		jQuery('#mydatagrid').datagrid('refreshRow', indexs);
		$("#mydatagrid").datagrid("selectRow", indexs);
		var rows = $("#mydatagrid").datagrid("getSelections");
		if (bol == false) {
			if (!(rows[0].purchaseitemid)) {
				jQuery('#mydatagrid').datagrid('deleteRow', indexs);
			}
		}
	}
	
	
	//明细编辑
	function editrow(indexs) {
		if (editcount > 0) {
			alert("当前还有" + editcount + "记录正在编辑，暂不能编辑。");
			return;
		}
		jQuery('#mydatagrid').datagrid("beginEdit", indexs);
	}
	
	
	//明细保存
	function saverow(indexs) {
		var purchaseId = jQuery('#purchaseid').val();
		$("#mydatagrid").datagrid("endEdit", indexs);
		$("#mydatagrid").datagrid("selectRow", indexs);
		var rows = $("#mydatagrid").datagrid("getSelections");
		var bol = $("#mydatagrid").datagrid("validateRow", indexs);
		if (bol == false) {
			alert("请检查输入！");
			return;
		}
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/purchaseitem/save?purchaseId='
					+ purchaseId,
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
				jQuery('#mydatagrid').datagrid('reload');
			}
		});
		var allRows = $("#mydatagrid").datagrid("getRows");
		var allPrices = 0;
		for ( var i = 0; i < allRows.length; i++) {
			allPrices += allRows[i].quantity * allRows[i].referenceprice;
		}
		jQuery("#purchasesum").val(allPrices);
		jQuery("#purchasesum_").html(allPrices);
	}
</script>
</head>

<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<span class="x_content_title">采购申请</span> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-save'"
					onclick="javascript:saveData();">保存</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-submit'"
					onclick="javascript:saveAsData();">提交</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-cancel'"
					onclick="javascript:art.dialog.close();">关闭</a>
			</div>
		</div>
		<div data-options="region:'center',border:false,cache:true">
			<form id="iForm" name="iForm" method="post">
				<input type="hidden" id="purchaseid" name="purchaseid"
					value="${purchase.purchaseid}" />
				<div style="width: 100%">
					<table style="width: 720px;" align="center">
						<tbody>
							<tr>
								<td align="left">采购编号</td>
								<td align="left"><input id="purchaseno" name="purchaseno"
									type="text" class="easyui-validatebox"
									data-options="required:true" maxlength="20" size="10"
									value="${purchase.purchaseno}" /></td>
								<td align="left">申请人</td>
								<td align="left"><input id="appuser"
									class="easyui-combobox" name="appuser"
									value="${purchase.appuser}" size="10"
									data-options="required:true,valueField:'code',textField:'name',required:true,url:'${contextPath}/mx/oa/baseData/getUserJson',
					onSelect: function(rec){ 
					jQuery.ajax({
					   type: 'POST',
					   url: '${contextPath}/mx/oa/baseData/getSingleUserJson',
					   data: {'userCode':rec.code},
					   dataType:  'json',
					    async:false ,
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
					    jQuery('#post').combobox('setValue',data.headship);
					   }	
					   	    $('#company').combobox('clear');
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
						   var url = '${contextPath}/rs/dictory/jsonArray/'+data.area;    
						                                $('#company').combobox('reload', url);
						   }
						 });
                    }" />
								</td>
								<td align="left">部门</td>
								<td align="left"><input id="dept" name="dept" type="text"
									class="easyui-combobox" editable="false"
									data-options="required:true,valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'"
									disabled="disabled" size="10" value="${purchase.dept}" /></td>
								<td align="left">职位</td>
								<td align="left"><input id="post" name="post" type="text"
									class="easyui-combobox" disabled="disabled"
									data-options="required:true" size="10" value="${purchase.post}" />
								</td>
							</tr>
							<tr>
								<td align="left">申请日期</td>
								<td align="left"><input id="appdate" name="appdate"
									type="text" disabled="disabled" class="easyui-datebox"
									data-options="required:true" size="10" editable="false"
									value="<fmt:formatDate value="${purchase.appdate}" pattern="yyyy-MM-dd"/>" />
								</td>
								<td align="left">地区</td>
								<td align="left"><input id="area" class="easyui-combobox"
									name="area" size="10" value="${purchase.area}" editable="false"
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
								<td align="left">单位</td>
								<td align="left" colspan="3"><input id="company"
									class="easyui-combobox" name="company" editable="false"
									data-options="valueField:'code',textField:'name',required:true,url:'${contextPath}/rs/dictory/jsonArray/${purchase.area}'"
									value="${purchase.company}" size='26' /></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div style="margin: 0;"></div>
				<div data-options="region:'north',split:true,border:true"
					style="height: 40px">
					<div class="toolbar-backgroud">
						<img src="${contextPath}/images/window.png"> &nbsp; <span
							class="x_content_title">采购明细:</span> <a href="#"
							class="easyui-linkbutton"
							data-options="plain:true, iconCls:'icon-add'"
							onclick="javascript:addNew();">增加</a> <a href="#"
							class="easyui-linkbutton"
							data-options="plain:true, iconCls:'icon-remove'"
							onclick="javascript:deleteSelections();">删除</a>
					</div>
					<div data-options="region:'center',border:true"
						style="width: 759px; height: 220px">
						<table id="mydatagrid">
						</table>
					</div>
					<div class="toolbar-backgroud" style="">
						总价: <input id="purchasesum" name="purchasesum" type="hidden"
							value="${purchase.purchasesum}" /> <span id="purchasesum_">${purchase.purchasesum}</span>
						<script type="text/javascript">
							var purchasesum_ = $
							{
								purchase.purchasesum
							};
							jQuery(function() {
								jQuery('#purchasesum_').html(
										parseFloat(purchasesum_));
							});
						</script>
					</div>
					<div style="margin: 0; height: 4px"></div>
					<div>
						<a href="javascript:uploadFile(10, ${purchase.purchaseid}, 1)">附件上传</a>
						(共<span id="numAttachment10" name="numAttachment10">0</span>个)
						<script type="text/javascript">
							jQuery(function() {
								jQuery
										.get(
												'${contextPath}/others/attachment.do?method=getCount&referId=${purchase.purchaseid}&referType=10',
												{
													qq : 'xx'
												},
												function(data) {
													document
															.getElementById("numAttachment10").innerHTML = data.message;
												}, 'json');
							});
						</script>
					</div>
					<div>
						<table width="100%">
							<tr align="center">
								<td>
									<!-- 
					    <a href="#" class="easyui-linkbutton"  onclick="javascript:saveAsData()">提交</a>
					    <a href="#" class="easyui-linkbutton"  onclick="javascript:saveData()" >保存</a>
					    <a href="#" class="easyui-linkbutton"  onclick="javascript:closeDialog()">关闭</a>
						<input type="button" name="saveAs" value=" 提交 " class="button btn" onclick="javascript:saveAsData();">
						<input type="button" name="saveAs" value=" 保存 " class="button btn" onclick="javascript:saveData();">
						<input type="button" name="saveAs" value=" 关闭 " class="button btn" onclick="javascript:closeDialog();">
					     -->
								</td>
							</tr>
						</table>
					</div>
			</form>

		</div>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>