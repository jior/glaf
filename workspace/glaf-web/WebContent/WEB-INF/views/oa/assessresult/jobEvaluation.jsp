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
    var questionId = '<%=request.getParameter("questionId")%>';

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
							url : '${contextPath}/mx/oa/assessresult/jsonForView?questionId='
									+ questionId,
							sortName : 'id',
							sortOrder : 'desc',
							remoteSort : false,
							singleSelect : true,
							idField : '',
							columns : [ [
									{
										title : '考评类型',
										field : 'treeName',
										width : 40
									},
									{
										title : '考评类型',
										field : 'dictoryName',
										width : 90
									},
									{
										title : 'KPI关键绩效指标',
										field : 'contentName',
										width : 120
									},
									{
										title : '评分标准',
										field : 'standard',
										width : 50
									},
									{
										title : '得分',
										field : 'score',
										align : 'center',
										width : 50,
										formatter : function(value, row, index) {
											return "<input type=\"hidden\" id=\"contentid_"+index+"\" name=\"contentid_"+index+"\" value=\""+row.contentId+"\" >"
													+ "<input id=\"score_"
													+ index
													+ "\" name=\"score_"
													+ index
													+ "\" style=\"text-align: center;\" maxlength=\"4\" onchange=\"this.value=changValue(this.value);\"   "
													+ "type=\"text\" size=\"4\" onblur=\"moveScore("
													+ index
													+ ","
													+ row.standard + ")\" >";
										}
									},
									{
										title : '扣分原因',
										field : 'reason',
										align : 'center',
										width : 100,
										formatter : function(value, row, index) {
											return "<textarea id=\"reason_"+index+"\" name=\"reason_"+index+"\" cols=\"14\" rows=\"2\" ></textarea>";
										}
									}, {
										title : '评分依据',
										field : 'basis',
										width : 220
									} ] ],
							rownumbers : false,
							pagination : false,
							onLoadSuccess : onLoadSuccess
						});

		//	onafterpaste=\"this.value=this.value.replace(/\\D.{0,1}\\D/g,'')\"
		var p = jQuery('#mydatagrid').datagrid('getPager');
		jQuery(p).pagination({
			onBeforeRefresh : function() {
				//alert('before refresh');
			}
		});
	});

	function changValue(value) {
		if (value < 0) {
			return 0;
		}
		var pattern = /^[\d]+$/i;
		if (!pattern.test(value.trim())) {
			return '';
		}
		return value;
	}
	function onLoadSuccess(data) {
		var merges = $
		{
			mergeCells
		}
		;
		var subMerges = $
		{
			subMergeCells
		}
		;
		for ( var i = 0; i < merges.length; i++) {
			$(this).datagrid('mergeCells', {
				index : merges[i].index,
				field : 'treeName',
				rowspan : merges[i].rowspan
			});
		}
		for ( var i = 0; i < subMerges.length; i++) {
			$(this).datagrid('mergeCells', {
				index : subMerges[i].subindex,
				field : 'dictoryName',
				rowspan : subMerges[i].subrowspan
			});
		}
	}
	function saveData() {

		var bol = jQuery("#iForm").form('validate');
		if (bol == false) {
			alert("请输入必须项！");
			return;
		}
		var flag1 = checkData();
		if (flag1 == 1) {
			alert("请输入得分！");
			return;
		}

		var params = jQuery("#iForm").formSerialize();
		jQuery
				.ajax({
					type : "POST",
					url : '${contextPath}/mx/oa/assessresult/saveAssessresult?questionId='
							+ questionId,
					data : params,
					dataType : 'json',
					error : function(data) {
						alert('服务器处理错误！');
					},
					success : function(data) {
						if (data != null && data.message != null) {
							alert(data.message);
						} else {
							alert('操作成功完成！');
						}
						if (window.opener) {
							window.opener.location.reload();
						} else if (window.parent) {
							window.parent.location.reload();
						}
					}
				});
	}
	function checkData() {
		var length = $
		{
			contentLength
		}
		;
		var flag = 0;//0代表OK
		for ( var i = 0; i < length; i++) {
			var score = "#score_" + i;
			if (jQuery(score).val() == "") {
				jQuery(score).attr('style', 'border-color :red');
				flag = 1;
			}
		}
		return flag;
	}

	function moveScore(index, maxlength) {
		var scoreid = "#score_" + index;
		var length = $
		{
			contentLength
		}
		;
		var allscore = 0;
		if (jQuery(scoreid).val() > maxlength) {
			alert("得分不能超过评分标准。");
			jQuery(scoreid).focus();
			return;
		}
		for ( var i = 0; i < length; i++) {
			if (jQuery("#score_" + i).val() != "") {
				allscore += parseFloat(jQuery("#score_" + i).val());
			}
		}
		jQuery('#score').val(allscore);
		jQuery('#score_').html(allscore);
	}
</script>
</head>

<body>
	<div style="margin: 0;"></div>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<span class="x_content_title">岗位考核</span> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-save'"
					onclick="javascript:saveData();">保存</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-cancel'"
					onclick="javascript:art.dialog.close();">关闭</a>
			</div>
		</div>

		<div data-options="region:'center',border:false,cache:true"
			style="width: 100%">
			<form id="iForm" name="iForm" method="post">
				<input type="hidden" id="datagridLength" name="datagridLength"
					value="${contentLength}" /> <input type="hidden" id="title"
					name="title" value="${assessquestion.title}" />
				<table class="easyui-form" style="width: 960px;" align="center">
					<tbody>
						<tr>
							<td align="left">被考评人</td>
							<td align="left"><input id="beevaluation"
								class="easyui-combobox" name="beevaluation"
								value="${assessresult.beevaluation}" size="10" max="10"
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
					    $('#dept').combobox('setValue',data.deptName);
					   }
					   if(jQuery('#post')!=null) {
					    jQuery('#post').combobox('setValue',data.headship);
					   }	
					   	    $('#company').combobox('clear');
					 	  var url = '${contextPath}/rs/dictory/jsonArray/'+data.area;    
					  	  $('#company').combobox('reload', url);
					   }
					 })},onChange: function(rec){ 
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
							<td align="left">考评人</td>
							<td align="left" width="100px;"><input id="evaluation"
								name="evaluation" type="text" class="easyui-combobox" size="10"
								value="${assessresult.evaluation}" disabled="disabled"
								data-options="required:true,valueField:'code',textField:'name',required:true,
						url:'${contextPath}/mx/oa/baseData/getUserJson'" /></td>
						</tr>
						<tr>
							<td align="left">地区</td>
							<td align="left"><input id="area" class="easyui-combobox"
								disabled="disabled" name="area" size="10"
								value="${assessresult.area}"
								data-options="valueField:'code',textField:'name',required:true,url:'${contextPath}/rs/dictory/jsonArray/eara',
					onSelect:function(ret){										
					var url = '${contextPath}/rs/dictory/jsonArray/'+ret.code;
						jQuery('#company').combobox('clear');
						jQuery('#company').combobox('reload',url);
						jQuery('#area').val(ret.code);
					}																	
					" />
							</td>
							<td align="left">部门</td>
							<td align="left"><input id="dept" name="dept" type="text"
								class="easyui-combobox" editable="false" disabled="disabled"
								data-options="required:true" size="10" readonly="readonly"
								value="${assessresult.dept}" /></td>
							<td align="left">职位</td>
							<td align="left"><input id="post" name="post" type="text"
								class="easyui-combobox" readonly="readonly" disabled="disabled"
								data-options="required:true" size="16"
								value="${assessresult.post}" /></td>
							<td align="left">年度</td>
							<td align="left"><select id="year" name="year"
								class="easyui-combobox" editable="false">
									<option value="2012">2012</option>
									<option value="2013">2013</option>
									<option value="2014">2014</option>
									<option value="2015">2015</option>
									<option value="2016">2016</option>
									<option value="2017">2017</option>
									<option value="2018">2018</option>
									<option value="2019">2019</option>
									<option value="2020">2020</option>
							</select></td>
							<td align="left">季度</td>
							<td align="left"><select id="season" name="season"
								class="easyui-combobox" editable="false">
									<option value="1">第一季度</option>
									<option value="2">第二季度</option>
									<option value="3">第三季度</option>
									<option value="4">第四季度</option>
							</select></td>
							<td align="left">月度</td>
							<td align="left"><select id="month" name="month"
								class="easyui-combobox" editable="false">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
							</select></td>
						</tr>
						<tr>
							<td>考评评语</td>
							<td colspan="11"><textarea id="comment" name="comment"
									cols="80" rows="2"></textarea></td>
						</tr>
					</tbody>
				</table>
				<div data-options="region:'north',split:true,border:true"
					style="height: 40px">
					<div class="toolbar-backgroud">
						<img src="${contextPath}/images/window.png"> &nbsp;<span
							class="x_content_title">考评内容:</span>
					</div>
					<div data-options="region:'center',border:true"
						style="width: 960px; height: 263px">
						<table id="mydatagrid">
						</table>
					</div>
					<div class="toolbar-backgroud" style="">
						<table>
							<tr>
								<td width="100px;">总分：${allScore}</td>
								<td>本月KPI得分: <input id="score" name="score" type="hidden"
									value="${assessresult.score}" /> <span id="score_">${assessresult.score}</span>
								</td>
							</tr>
						</table>
					</div>
			</form>
		</div>
	</div>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>