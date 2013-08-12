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
        var resultid = '<%=request.getParameter("resultid")%>';
        
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
    					url:'${contextPath}/mx/oa/assessresult/jsonForResultView?resultid='+resultid,
    					sortName: 'id',
    					sortOrder: 'desc',
    					remoteSort: false,
    					singleSelect:true,
    					idField:'',
    					columns:[[
    							{title:'考评类型',field:'treeName', width:40},
    							{title:'考评类型',field:'dictoryName', width:90},
    							{title:'KPI关键绩效指标',field:'contentName', width:120},
    							{title:'评分标准',field:'standard', width:50},
    							{title:'得分',field:'score',align:'center', width:50},
    							{title:'扣分原因',field:'reason',align:'center', width:100},
    							{title:'评分依据',field:'basis', width:220}
    					]],
    					rownumbers:false,
    					pagination:false,
    					onLoadSuccess:onLoadSuccess
    				});

    				var p = jQuery('#mydatagrid').datagrid('getPager');
    				jQuery(p).pagination({
    					onBeforeRefresh:function(){
    						//alert('before refresh');
    					}
    			    });
    		});

        function onLoadSuccess(data){
			var merges = ${mergeCells};
			var subMerges = ${subMergeCells};
			for(var i=0; i<merges.length; i++){
				$(this).datagrid('mergeCells',{
					index: merges[i].index,
					field: 'treeName',
					rowspan: merges[i].rowspan
				});
			}
			for(var i=0; i<subMerges.length; i++){
				$(this).datagrid('mergeCells',{
					index: subMerges[i].subindex,
					field: 'dictoryName',
					rowspan: subMerges[i].subrowspan
				});
			}
		}
		function print(id){
			window.location='${contextPath}/mx/oa/reports/exportAssessQuestion?resultid='+id;
		}
</script>
</head>

<body>
	<div style="margin: 0;"></div>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<span class="x_content_title">岗位考核结果查看</span> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-save'"
					onclick="javascript:print(${assessresult.resultid});">打印</a> <a
					href="#" class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-cancel'"
					onclick="javascript:art.dialog.close();">关闭</a>
			</div>
		</div>

		<div data-options="region:'center',border:false,cache:true"
			style="width: 100%">
			<form id="iForm" name="iForm" method="post">
				<input type="hidden" id="datagridLength" name="datagridLength"
					value="${contentLength}" />
				<table class="easyui-form" style="width: 960px;" align="center">
					<tbody>
						<tr>
							<td align="left">被考评人</td>
							<td align="left"><input id="beevaluation"
								class="easyui-combobox" name="beevaluation" disabled="disabled"
								value="${assessresult.beevaluation}" size="10" max="10"
								data-options="required:true,valueField:'code',textField:'name',required:true,url:'${contextPath}/mx/oa/baseData/getUserJson'" />
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
								class="easyui-combobox" editable="false" disabled="disabled">
									<option value="2012">2012</option>
									<option value="2013">2013</option>
									<option value="2014">2014</option>
									<option value="2015">2015</option>
									<option value="2016">2016</option>
									<option value="2017">2017</option>
									<option value="2018">2018</option>
									<option value="2019">2019</option>
									<option value="2020">2020</option>
							</select> <script type="text/javascript">
					jQuery('#year').val(${assessresult.year});
				</script></td>
							<td align="left">季度</td>
							<td align="left"><select id="season" name="season"
								class="easyui-combobox" editable="false" disabled="disabled">
									<option value="1">第一季度</option>
									<option value="2">第二季度</option>
									<option value="3">第三季度</option>
									<option value="4">第四季度</option>
							</select> <script type="text/javascript">
					jQuery('#season').val(${assessresult.season});
				</script></td>
							<td align="left">月度</td>
							<td align="left"><select id="month" name="month"
								class="easyui-combobox" editable="false" disabled="disabled">
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
							</select> <script type="text/javascript">
					jQuery('#month').val(${assessresult.month});
				</script></td>
						</tr>
						<tr>
							<td>考评评语</td>
							<td colspan="11"><textarea cols="80" rows="2"
									disabled="disabled">${assessresult.comment}</textarea></td>
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