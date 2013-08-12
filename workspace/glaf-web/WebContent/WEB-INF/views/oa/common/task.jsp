<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	var processInstanceId = jQuery('#processInstanceId').val();

	jQuery
			.ajax({
				type : "POST",
				url : '${contextPath}/mx/oa/baseData/getJBPMActivityJson?processInstanceId='
						+ processInstanceId,
				dataType : 'json',
				error : function(data) {
					alert('服务器处理错误！');
				},
				success : function(data) {
					if (data != null) {
						for ( var i = 0; i < data.rows.length; i++) {
							var tr = "<tr><td>" + data.rows[i].taskName
									+ "</td><td>" + data.rows[i].actorName
									+ "</td><td>" + data.rows[i].starDate
									+ "</td><td>" + data.rows[i].endDate
									+ "</td><td>";
							if (data.rows[i].isAgree == 'true') {
								tr = tr + "通过" + "</td>";
							} else if (data.rows[i].isAgree == 'false') {
								tr = tr + "不通过" + "</td>";
							} else {
								tr = tr + "</td>";
							}
							tr = tr + "<td title ="+data.rows[i].content+">";
							tr = tr + data.rows[i].subcontent + "</td></tr>";
							jQuery("#taskdatagrid").append(tr);
						}
					}
				}
			});
</script>
<div class="toolbar-backgroud">
	<img src="${contextPath}/images/window.png"> &nbsp; <span
		class="x_content_title">审批进度信息:</span>
</div>
<div style="width: 100%; height: 150px; overflow-y: auto"
	data-options="region:'center',border:true">
	<table id="taskdatagrid" width="100%" style="BORDER-COLLAPSE: collapse"
		bordercolor="#CCCCCC" cellpadding="4" border="1">
		<tr>
			<td width="15%">任务名称</td>
			<td width="15%">执行者</td>
			<td width="15%">开始时间</td>
			<td width="15%">结束时间</td>
			<td width="15%">审批结果</td>
			<td width="25%">审批意见</td>
		</tr>
	</table>
</div>