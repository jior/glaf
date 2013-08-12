<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_config.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>指标维护</title>
<meta name="Keywords" content="<%=appKeywords%>" />
<meta name="Description" content="<%=appDescription%>" />
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>

<script type="text/javascript">
	var contextPath = "${contextPath}";

	function saveData() {
		if (!validateForm()) {
			return false;
		}
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/assessinfo/saveAssessinfo',
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
		document.getElementById("indexid").value = "";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/oa/assessinfo/saveAssessinfo',
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

	function validateForm() {
		var v = $("#name").val();
		if ($.trim(v).length == 0) {
			alert("请输入指标名称！");
			return false;
		}

		v = $("#standard").val();
		if ($.trim(v).length == 0) {
			alert("请输入评分标准！");
			return false;
		}
		return true;

	}
	function closeDialog() {
		window.parent.location.href = window.parent.location.href;

	}
	function back() {
		window.location.href = "${contextPath}/mx/oa/assessinfo/query";
	}
</script>

<style type="text/css">
.textWidth {
	width: 450px;
}
</style>
</head>

<body>
	<div style="margin: 0;"></div>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true"
			style="height: 40px">
			<div class="toolbar-backgroud">
				<span class="x_content_title">指标维护</span> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-save'"
					onclick="javascript:saveData();">保存</a> <a href="#"
					class="easyui-linkbutton"
					data-options="plain:true, iconCls:'icon-cancel'"
					onclick="javascript:art.dialog.close()">关闭</a>

			</div>
		</div>

		<div data-options="region:'center',border:false,cache:true">
			<form id="iForm" name="iForm" method="post">
				<input type="hidden" id="indexid" name="indexid"
					value="${assessinfo.indexid}" />
				<table class="easyui-form" style="width: 600px;" align="center">
					<tbody>
						<tr>
							<td width="20%" align="left">指标名称*：</td>
							<td align="left"><input id="name" name="name" type="text"
								class="easyui-validatebox textWidth" value="${assessinfo.name}" />
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">评分依据：</td>
							<td align="left"><textarea id="basis" name="basis" rows="5"
									cols="50" class="easyui-validatebox textWidth">${assessinfo.basis}</textarea>
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">评分标准*:</td>
							<td align="left"><input id="standard" name="standard"
								type="text" class="easyui-numberbox" max="99" min="1"
								value="${assessinfo.standard}" /></td>
						</tr>
						<tr>
							<td width="20%" align="left">是否有效*：</td>
							<td align="left"><select id="iseffective" name="iseffective"
								class="eqsyuui-combox">
									<option value="1">是</option>
									<option value="0">否</option>
							</select></td>
						</tr>
						<!-- 	<tr> -->
						<!-- 		<td> -->
						<!-- 		<input type="button" name="save" value=" 保存 " class="button btn btn-primary" onclick="javascript:saveData();"/> -->
						<!-- 		</td> -->
						<!-- 		<td><input type="button" name="back" value=" 关闭 "  class="button btn btn-primary" onclick="javascript:closeDialog();"></td> -->
						<!-- 	</tr> -->
					</tbody>
				</table>
				<script>
					$("#iseffective").val("${assessinfo.iseffective}");
				</script>
			</form>
		</div>
	</div>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>