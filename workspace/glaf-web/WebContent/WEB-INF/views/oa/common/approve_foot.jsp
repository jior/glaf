<%@ page contentType="text/html;charset=UTF-8"%>
<div id="dlgApprove" class="easyui-dialog" closed="true"
	buttons="#dlgApprove-buttons" style="width: 365px;">
	<form id="appForm">
		<table class="easyui-form">
			<tr>
				<td>审批意见：</td>
			</tr>
			<tr>
				<td><textarea id="approveOpinion" name="approveOpinion"
						rows="9" cols="40"></textarea></td>
			</tr>
		</table>
	</form>
</div>
<div id="dlgApprove-buttons">
	<a href="#" class="easyui-linkbutton" onclick="javascript:dopassData()"
		id="pass">通过</a> <a href="#" class="easyui-linkbutton"
		onclick="javascript:donoPassData()" id="nopass">不通过</a> <a href="#"
		class="easyui-linkbutton" iconCls="icon-cancel"
		onclick="javascript:jQuery('#dlgApprove').dialog('close')">取消</a>
</div>
<script type="text/javascript">
	function dopassData() {
		var bol = jQuery("#approveOpinion").val();
		if (bol.length > 200) {
			alert("审批意见不能超过200字符");
			return;
		} else {
			passData();
		}
	}
	function donoPassData() {
		var bol = jQuery("#approveOpinion").val();
		if (bol.length > 200) {
			alert("审批意见不能超过200字符");
			return;
		} else {
			noPassData();
		}
	}
</script>