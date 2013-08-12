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
        var contextPath="${contextPath}";
	//明细列表
	var editcount = 0;
	 jQuery(function(){
		var purchaseId = jQuery('#purchaseid').val() ;
			jQuery('#mydatagrid').datagrid({
					width:745,
					height:220,
					fit:true,
					fitColumns:true,
					nowrap: false,
					striped: true,
					collapsible:true,
					url:'${contextPath}/mx/oa/purchaseitem/json?purchaseId='+purchaseId,
					sortName: 'id',
					sortOrder: 'desc',
					remoteSort: false,
					singleSelect:true,
					idField:'purchaseItemId',
					columns:[[
					        {title:'序号', field:'startIndex', width:80, sortable:false},
							{title:'内容',field:'content', width:120,editor:{
									                            type:'validatebox',
									                            options:{required:true}}},
							{title:'规格要求',field:'specification', width:120,editor:{
								                            	type:'validatebox',
									                            options:{required:true}}},
							{title:'数量',field:'quantity', width:120,editor:{ 
									                            type:'numberbox',
									                            options:{required:true,min:1}}},
							{title:'参考单价',field:'referenceprice', width:120,editor:{
									                            type:'numberbox',
									                            options:{required:true,min:0.01,precision:2}}},
							{title:'总价',field:'totalprice', width:120},
							{title:'备注',field:'remark', width:120,editor:'text'},
					]],
					rownumbers:false,
					pagination:false,
					//pageSize:15,
					//pageList: [10,15,20,25,30,40,50,100]
					onBeforeEdit:function(index,row){
						row.editting = true;   
						jQuery('#mydatagrid').datagrid('refreshRow',index);
						editcount++;
						},
					onAfterEdit:function(index,row){
							row.editting = false ;
							jQuery('#mydatagrid').datagrid('refreshRow',index);
							editcount--;
							},
					onCancelEdit:function(index,row){
						row.editting = false ;
						jQuery('#mydatagrid').datagrid('refreshRow',index);
						editcount--;
						}
				});

				var p = jQuery('#mydatagrid').datagrid('getPager');
				jQuery(p).pagination({
					onBeforeRefresh:function(){
						//alert('before refresh');
					}
			    });
		});
	//弹出审核窗口
		function approveWin(){
		   	 //jQuery('#dlgApprove').dialog('open').dialog('setTitle','批量审核');
		   	$('#dlgApprove').dialog('open').dialog({   
		   	    title: '审核',   
		   	    left: 180,   
		   	    top: 100, 
		   	    closed: false,
		   	    modal: true  
		   	}); 
		    jQuery('#dlgApprove').form('clear');
		}

		//批量审核---通过
		function passData(){
				if( confirm("确认审核通过吗？")){
					var purchaseIds = jQuery('#purchaseid').val();
					var params = jQuery('#approveOpinion').serialize();
					jQuery.ajax({
						   type: "POST",
						   url: '${contextPath}/mx/oa/purchaseApprove/approveData?purchaseIds='+purchaseIds+'&isAgree=isAgree',
						   dataType:  'json',
						   data: params,
						   error: function(data){
							   alert('服务器处理错误！');
						   },
						   success: function(data){
							   if(data != null && data.message != null){
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

		//批量审核---不通过
		function noPassData(){
				if(jQuery('#approveOpinion').val().trim()==""){alert("审核不通过需填写审核意见。");return;}
				if( confirm("确认审核不通过吗？")){
					var purchaseIds = jQuery('#purchaseid').val();
					var params = jQuery('#approveOpinion').serialize();
					jQuery.ajax({
						   type: "POST",
						   url: '${contextPath}/mx/oa/purchaseApprove/approveData?purchaseIds='+purchaseIds,
						   dataType:  'json',
						   data: params,
						   error: function(data){
							   alert('服务器处理错误！');
						   },
						   success: function(data){
							   if(data != null && data.message != null){
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
			
</script>
</head>

<body>
	<div style="margin: 0;"></div>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<span class="x_content_title">采购申请审核</span> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-submit'"
					onclick="javascript:approveWin()">审核</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-cancel'"
					onclick="javascript:art.dialog.close();">关闭</a>
			</div>
		</div>
		<div data-options="region:'center',border:false,cache:true">
			<form id="iForm" name="iForm" method="post">
				<input type="hidden" id="purchaseid" name="purchaseid"
					value="${purchase.purchaseid}" /> <input type="hidden"
					id="processInstanceId" name="processInstanceId"
					value="${purchase.processinstanceid}" />
				<div style="width: 100%">
					<table style="width: 700px;" align="center">
						<tbody>
							<tr>
								<td align="left">采购编号</td>
								<td align="left"><input id="purchaseno" name="purchaseno"
									disabled="disabled" type="text" class="easyui-validatebox"
									data-options="required:true" maxlength="20" size="10"
									readonly="readonly" value="${purchase.purchaseno}" /></td>
								<td align="left">申请人</td>
								<td align="left"><input id="appuser"
									class="easyui-combobox" name="appuser"
									value="${purchase.appuser}" size="12" disabled="disabled"
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
					    jQuery('#post').val(data.headship);
					   }	
					   	    $('#company').combobox('setValue','');
					 	  var url = '${contextPath}/rs/dictory/jsonArray/'+data.area;    
					  	  $('#company').combobox('reload', url);
					   }
					 });
					}" />
								<td align="left">部门</td>
								<td align="left"><input id="dept" name="dept" type="text"
									disabled="disabled" class="easyui-combobox"
									data-options="required:true,valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'"
									size="10" readonly="readonly" value="${purchase.dept}" /></td>
								<td align="left">职位</td>
								<td align="left"><input id="post" name="post" type="text"
									class="easyui-validatebox" readonly="readonly"
									disabled="disabled" data-options="required:true" size="10"
									value="${purchase.post}" /></td>
							</tr>
							<tr>
								<td align="left">申请日期</td>
								<td align="left"><input id="appdate" name="appdate"
									type="text" class="easyui-validatebox"
									data-options="required:true" size="10" readonly="readonly"
									disabled="disabled"
									value="<fmt:formatDate value="${purchase.appdate}" pattern="yyyy-MM-dd"/>" />
								</td>
								<td align="left">地区</td>
								<td align="left"><input id="area" class="easyui-combobox"
									name="area" size="10" disabled="disabled"
									value="${purchase.area}"
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
									class="easyui-combobox" disabled="disabled" name="company"
									readonly="readonly"
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
						<img src="${contextPath}/images/window.png"> &nbsp;<span
							class="x_content_title">采购明细:</span>
						<!-- 
					    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
						   onclick="javascript:addNew();">增加</a>  
						<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'"
						   onclick="javascript:deleteSelections();">删除</a> 
						 -->
					</div>
					<div data-options="region:'center',border:true"
						style="width: 742px; height: 220px">
						<table id="mydatagrid">
						</table>
					</div>
					<div class="toolbar-backgroud" style="">
						总价: <input id="purchasesum" name="purchasesum" type="hidden"
							value="${purchase.purchasesum}" /> <span id="purchasesum_">${purchase.purchasesum}</span>
						<script type="text/javascript">
								var purchasesum_ = ${purchase.purchasesum};
								jQuery(function(){
									jQuery('#purchasesum_').html(parseFloat(purchasesum_));
								});
								
							</script>
					</div>
					<div style="margin: 0; height: 4px"></div>
					<div>
						<a href="javascript:uploadFile(10, ${purchase.purchaseid}, 0)">附件下载</a>
						<jsp:include page="/others/attachment/showCount">
							<jsp:param name="referType" value="10" />
							<jsp:param name="referId" value="${purchase.purchaseid}" />
						</jsp:include></div>
					<div style="width: 742px;">
						<%@ include file="/WEB-INF/views/oa/common/task.jsp"%></div>
					<div>
 
					</div>

					<%@ include file="/WEB-INF/views/oa/common/approve_foot.jsp"%>
			</form>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>