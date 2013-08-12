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
							columns : [ [ {
								title : '考评类型',
								field : 'treeName',
								width : 40
							}, {
								title : '考评类型',
								field : 'dictoryName',
								width : 90
							}, {
								title : 'KPI关键绩效指标',
								field : 'contentName',
								width : 120
							}, {
								title : '评分标准',
								field : 'standard',
								width : 50
							}, {
								title : '评分依据',
								field : 'basis',
								width : 220
							} ] ],
							rownumbers : false,
							pagination : false,
							//pageSize:15,
							//pageList: [10,15,20,25,30,40,50,100]
							onLoadSuccess : onLoadSuccess
						});

		var p = jQuery('#mydatagrid').datagrid('getPager');
		jQuery(p).pagination({
			onBeforeRefresh : function() {
				//alert('before refresh');
			}
		});
	});

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
</script>
</head>

<body>
	<div style="margin: 0;"></div>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<span class="x_content_title">岗位考核指标查看</span> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-cancel'"
					onclick="javascript:art.dialog.close();">关闭</a>
			</div>
		</div>

		<div data-options="region:'center',border:false,cache:true"
			style="width: 100%">
			<form id="iForm" name="iForm" method="post">
				<!-- 
  <input type="hidden" id="resultid" name="resultid" value="${assessresult.questerid}"/>
   -->
				<table class="easyui-form" style="width: 860px;" align="center">
					<tbody>
						<tr>
							<td align="left">标题:</td>
							<td align="left">
								<!-- 
			<input id="title" name="title" type="text"
			       class="easyui-validatebox" 
				   value="${assessquestion.title}"/>
		 -->
								<div>${assessquestion.title}</div>
							</td>
						</tr>
						<tr>
							<td align="left">有效期:</td>
							<td align="left">
								<!-- 
			<input id="area" name="area" type="text"
			       class="easyui-validatebox" 
				   value="${assessquestion.validdate}"/>
		 --> <fmt:formatDate value="${assessquestion.validdate}" />
							</td>
							<td align="left">频率:</td>
							<td align="left" width="100px;">
								<!-- 
            <input id="company" name="company" type="text" 
			       class="easyui-validatebox"  
				   value="${assessquestion.rate}"/>
				    -->${assessquestion.rate==1?"季度":(assessquestion.rate==2?"月度":"年度")}
							</td>
							<td align="left">是否有效:</td>
							<td align="left">
								<!-- 
            <input id="dept" name="dept" type="text" 
			       class="easyui-validatebox"  
				   value="${assessquestion.iseffective}"/>
				    --> ${assessquestion.iseffective==1?"有效":"无效"}
							</td>
							<td width="50%"></td>
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
						style="width: 875px; height: 330px">
						<table id="mydatagrid">
						</table>
					</div>
			</form>
		</div>
	</div>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>