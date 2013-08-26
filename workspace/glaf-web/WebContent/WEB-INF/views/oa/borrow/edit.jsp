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

	function saveData(){
		var flag = checkData() ;
		var bol = jQuery("#iForm").form('validate') ;
		if(bol==false||flag==1){
			 alert("请输入必须项！");
			 return ;
		}
		var flag1 = checkDate();
		if(flag1==1){return;}
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '${contextPath}/mx/oa/borrow/save',
				   data: params,
				   dataType:  'json',
				   async:false ,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						 alert(data.message);
					   } else {
						 alert('操作成功！');
						 removeattrCheckData();
					   }
					   if (window.opener) {
							window.opener.location.reload();
						   } else if (window.parent) {
							window.parent.location.reload();
						   }
				   }
			 });
	}

	function saveAsData(){
		var flag = checkData() ;
		var bol = jQuery("#iForm").form('validate') ;
		if(bol==false||flag==1){
			 alert("请输入必须项！");
			 return ;
		}
		var flag1 = checkDate();
		if(flag1==1){return;}
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '${contextPath}/mx/oa/borrow/submit',
				   data: params,
				   dataType:  'json',
				   async:false ,
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
	}


	function append(index){
		var addId = '#add_'+index ;
		var reId = '#re_'+index ;
		jQuery(addId).attr('style','display:none');
		jQuery(reId).attr('style','display:none');
		index++ ;
		$("#borrowAddress_").append("<tr id=\"tr_"+index+"\"><td>出差地点由：\n<input maxlength=\"20\" data-options=\"required:true\"  class=\"easyui-validatebox\" id=\"start_"+index+"\" name=\"start_"+index+"\"  type='text' />\n至\n<input maxlength=\"20\" data-options=\"required:true\"  class=\"easyui-validatebox\" id=\"reach_"+index+"}\" name=\"reach_"+index+"\"  type='text'/>\n<a style=\""+(index==9?'display:none;':'')+"\" id=\"add_"+index+"\" href='#' onclick='javascript:append("+index+");'><img src=\"${contextPath}/images/add.png\" /></a>\n<a id=\"re_"+index+"\" href='#' onclick='javascript:removed("+index+");'><img src=\"${contextPath}/images/close.png\" /></a>\n</td></tr>");
	}
	
	function removed(index){
		var trId = '#tr_'+index ;
		index-- ;
		var addId = '#add_'+index ;
		var reId = '#re_'+index ;
		jQuery(addId).removeAttr('style');
		if(index!=0){
		jQuery(reId).removeAttr('style');
			}
		jQuery(trId).remove();
	}
	
	function reback(){
		location.href="${contextPath}/mx/oa/borrow" ;
	}

	function checkData(){
		var flag = 0;//0代表合格
		if(jQuery('#content').combobox('getValue')!=1){
			return 0;
		}
		var startdate = jQuery('#startdate').datebox('getValue');
		var enddate = jQuery('#enddate').datebox('getValue');
		var daynum = jQuery('#daynum').val();
		if(startdate==""){
			flag = 1;
		}
		if(enddate==""){
			flag = 1;
		}
		if(daynum==""){
			jQuery('#daynum').attr('style','border-color :red');
			flag = 1;
		}
		for(var i=0;i<10;i++){
			var start =	"#start_"+i;
			var reach =	"#reach_"+i;
			if(jQuery(start)!=null&&jQuery(start).val()==""){
				jQuery(start).attr('style','border-color :red');
				flag=1;
			}
			if(jQuery(reach)!=null&&jQuery(reach).val()==""){
				jQuery(reach).attr('style','border-color :red');
				flag=1;
			}
		}
		return flag;	
	}
	
	function checkDate(){
		var flag =0 ;
		var startDate = jQuery("#startdate").datebox('getValue');
		var endDate = jQuery("#enddate").datebox('getValue');
		if(startDate!="" && endDate!="" && startDate>endDate){
			alert("预计的开始日期不能晚于结束日期。");
			flag = 1;
		}
		return flag ;
	}
	
	function removeattrCheckData(){
			jQuery('#daynum').removeAttr('style');
		for(var i=0;i<10;i++){
			var start =	"#start_"+i;
			var reach =	"#reach_"+i;
			jQuery(start).removeAttr('style');
			jQuery(reach).removeAttr('style');
		}
	}
</script>
</head>

<body>
	<div class="easyui-layout" data-options="fit:true" align="center"
		style="height: 100%; overflow-y: auto">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud" align="left">
				<span class="x_content_title">员工借支申请</span>
				
				<a href="#" class="easyui-linkbutton"
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
				<input type="hidden" id="borrowid" name="borrowid"
					value="${borrow.borrowid}" />
				<table class="easyui-form" style="width: 680px;" align="center"
					cellspacing="10">
					<tbody>
						<tr>
							<td align="left"><div style="width: 40px;">申请人</div></td>
							<td align="left"><input id="appuser" class="easyui-combobox"
								name="appuser" value="${borrow.appuser}" size="10"
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
					   	    $('#company').combobox('setValue','');
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
						   var url = '${contextPath}/rs/dictory/jsonArray/'+data.area;    
						                                $('#company').combobox('reload', url);
						   }
						 });
                    }" />
							</td>
							<td align="left">职位</td>
							<td align="left"><input id="post" name="post" type="text"
								class="easyui-combobox" size="22" disabled="disabled"
								readonly="readonly" data-options="required:true"
								value="${borrow.post}" /></td>
							<td align="left">部门</td>
							<td align="left"><input id="dept" name="dept" type="text"
								class="easyui-combobox" size="10" disabled="disabled"
								readonly="readonly"
								data-options="required:true,valueField:'code',textField:'name',url:'${contextPath}/rs/dictory/deptJsonArray/012'"
								value="${borrow.dept}" /></td>
						</tr>
						<tr>
							<td align="left">地区</td>
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
							<td align="left"><div style="width: 60px;">申请单位</div></td>
							<td align="left"><input id="company" class="easyui-combobox"
								name="company" editable="false"
								data-options="valueField:'code',textField:'name',required:false,url:'${contextPath}/rs/dictory/jsonArray/${borrow.area}'"
								value="${borrow.company}" size='22' /></td>
							<td align="left"><div style="width: 60px;">申请日期</div></td>
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
								size="10"
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
								data-options="required:true" maxlength="20"
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
														class="easyui-datebox" size="10" editable="false"
														value="<fmt:formatDate value="${borrow.startdate}" pattern="yyyy-MM-dd"/>" />
														至 <input id="enddate" name="enddate"
														class="easyui-datebox" size="10" editable="false"
														value="<fmt:formatDate value="${borrow.enddate}" pattern="yyyy-MM-dd"/>" />
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 共<input id="daynum"
														name="daynum" type="text" min="0"
														class="easyui-numberspinner" size="8" increment="1"
														value="${borrow.daynum}" /> 天
													</td>
												</tr>
												<c:forEach items="${addresslist}" varStatus="status"
													var="address">
													<tr id="tr_${status.index}">
														<td><input type="hidden"
															id="addressid_${status.index}"
															name="addressid_${status.index}"
															value="${address.addressid}"> 出差地点由： <input
															class="easyui-validatebox" maxlength="20"
															id="start_${status.index}" name="start_${status.index}"
															type="text" value="${address.start}" /> 至 <input
															class="easyui-validatebox" maxlength="20"
															id="reach_${status.index}" name="reach_${status.index}"
															type="text" value="${address.reach}" /> <a href="#"
															id="add_${status.index}"
															style="${((addresslist.size()-status.index)==1&&addresslist.size()>0)?'':'display:none;'}"
															onclick="javascript:append(${status.index});"><img
																src="${contextPath}/images/add.png" /></a> <a href="#"
															id="re_${status.index}"
															style="${((addresslist.size()-status.index)==1&&addresslist.size()>1)?'':'display:none;'}"
															onclick="javascript:removed(${status.index});"><img
																src="${contextPath}/images/close.png" /></a></td>
													</tr>
												</c:forEach>
											</table>
										</div>
									</fieldset>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="6" align="left" valign="middle">
								<fieldset>
									<div>
										明细：
										<textarea rows="3" cols="60" id="details" name="details"
											class="easyui-validatebox"  style="width: 580px;height:60px;" 
											data-options="required:true,validType:'Maxlength[200]'">${borrow.details}</textarea>
									</div>
								</fieldset>
							</td>
						</tr>
						<tr>
							<td align="left" colspan="3">申请金额(人民币)： <input id="appsum"
								name="appsum" type="text" class="easyui-numberbox" size="10"
								max="99999999" min="0.01" data-options="required:true"
								value="${borrow.appsum}" />
							</td>
						</tr>
						<script type="text/javascript">
		if(jQuery('#content').val()==1){
			jQuery('#travel_').show();
		}else{
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
											name="borrowmoneyid_${status.index}"
											value="${money.borrowmoneyid}" type="hidden"> <input
											id="feename_${status.index}" name="feename_${status.index}"
											class="easyui-combobox" editable="false" name="brands"
											value="${money.feename}" size="5"
											data-options="valueField:'code',textField:'name',required:true,url:'${contextPath}/rs/dictory/jsonArray/freeName',
						onSelect:function(ret){
					}" />
											<input id="feesum_${status.index}"
											name="feesum_${status.index}" type="text"
											class="easyui-numberbox" size="8"
											data-options="required:true" max="9999999" min="1"
											value="${money.feesum}" onchange="changetotelPrice()" /></td>
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
								name="totelPrice" type="text" class="easyui-numberbox" size="8"
								readonly="readonly" value="${totelPrice}" />
							</td>
							<script type="text/javascript">
			
			function changetotelPrice(){
				var totelPrice = 0 ;
				for(var i=0;i<4;i++){
					var feesumid = "#feesum_"+i ;
					var feesum = jQuery(feesumid).val();
					if(feesum==""){continue;}
					feesum = parseFloat(feesum);
					if(feesum<1){feesum = 1 ;}else if(feesum>9999999){feesum=9999999 ;}
					totelPrice += feesum ; 
				}
				jQuery('#totelPrice').numberbox('setValue',totelPrice);
				changeAppSum();
			}

			
			function changeAppSum(){
				var appsum = jQuery('#totelPrice').val();
				appsum=parseFloat(appsum);
				if(appsum>99999999){
					appsum = '99999999' ;
				}
				appsum=appsum+".00";	
				var appsums = appsum.split(".") ;
				var str = appsums[0].toString();
				var numStr = ['壹','贰','叁','肆','伍','陆','柒','捌','玖','零'] ;
				var capsStr = ['','拾','佰','仟','万','拾','佰','仟','亿'] ;
				var appMon = '' ;
				for(var i=0;i<str.length ;i++){
					var num = str.charAt(i) ;
					if(num==0){
						if((i+1)!=str.length){
							if(num==str.charAt(i+1)){
								if(i==(str.length-5)){
									appMon+= capsStr[4];
								}else{
									appMon+='';
								}
							}else{
								if(i==(str.length-5)){
									appMon += capsStr[str.length-i-1] ;
								}else{
									appMon+= numStr[9];
								}
							}
						}
					}else{
						appMon += numStr[num-1]+capsStr[str.length-i-1] ;
					}
				}
				appMon = appMon+'元整';
				jQuery('#appMon').html(appMon);
			}
			changeAppSum();
		</script>
						</tr>
						<tr>
							<td colspan="6" align="left">
								<div>
									<a href="javascript:uploadFile(7, ${borrow.borrowid}, 1)">附件上传</a>
									(共<span id="numAttachment7" name="numAttachment7">0</span>个)
									<script type="text/javascript">
				jQuery(function(){
					jQuery.get('${contextPath}/others/attachment.do?method=getCount&referId=${borrow.borrowid}&referType=7',{qq:'xx'},function(data){
					document.getElementById("numAttachment7").innerHTML=data.message;
					  },'json');
					});
				</script>
								</div>
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