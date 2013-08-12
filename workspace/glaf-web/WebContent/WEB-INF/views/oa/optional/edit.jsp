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

	function saveData() {
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/optional/saveOptional',
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

	function saveAsData() {
		document.getElementById("id").value = "";
		document.getElementById("optionalid").value = "";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/optional/saveOptional',
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
			}
		});
	}
</script>
</head>

<body>
	<div style="margin: 0;"></div>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<span class="x_content_title">编辑Optional</span>
				
				<a href="#" class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-save'"
					onclick="javascript:saveData();">保存</a>
				
			</div>
		</div>

		<div data-options="region:'center',border:false,cache:true">
			<form id="iForm" name="iForm" method="post">
				<input type="hidden" id="optionalid" name="optionalid"
					value="${optional.optionalid}" />
				<table class="easyui-form" style="width: 600px;" align="center">
					<tbody>
						<tr>
							<td width="20%" align="left">id</td>
							<td align="left"><input id="id" name="id" type="text"
								class="easyui-numberspinner" value="0" increment="100"
								value="${optional.id}" /></td>
						</tr>
						<tr>
							<td width="20%" align="left">code</td>
							<td align="left"><input id="code" name="code" type="text"
								class="easyui-validatebox" value="${optional.code}" /></td>
						</tr>
						<tr>
							<td width="20%" align="left">price</td>
							<td align="left"><input id="price" name="price" type="text"
								class="easyui-numberbox" precision="2" value="${optional.price}" />
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">remark</td>
							<td align="left"><input id="remark" name="remark"
								type="text" class="easyui-validatebox"
								value="${optional.remark}" /></td>
						</tr>

					</tbody>
				</table>
			</form>
		</div>
	</div>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>