<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表文件</title>
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/datepicker/skin/WdatePicker.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/datepicker/skin/default/datepicker.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/datepicker/config.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/datepicker/lang/zh-cn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/datepicker/WdatePicker.js"></script>
<script type="text/javascript">

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/bi/reportFile/save',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data.message != null){
						   alert(data.message);
					   } else {
						 alert('操作成功完成！');
					   }
					   location.href="<%=request.getContextPath()%>/mx/bi/reportFile";
				   }
			 });
	}

	function saveAsData(){
		document.getElementById("id").value="";
		document.getElementById("reportFileId").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/bi/reportFile/save',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data.message != null){
						   alert(data.message);
					   } else {
						 alert('操作成功完成！');
					   }
					   location.href="<%=request.getContextPath()%>/mx/bi/reportFile";
				   }
			 });
	}

</script>
</head>
<body style="padding-left:20px;padding-right:20px">

<div class="content-block" style="width: 845px;">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="报表文件">&nbsp;报表文件</div>
<br>
<form id="iForm" name="iForm" method="post">
<input type="hidden" id="id" name="id" value="${reportFile.id}"/>
<input type="hidden" id="reportFileId" name="reportFileId" value="${reportFile.id}"/>
<table class="easyui-form" style="width:800px;" align="center">
<tbody>
	<tr>
		<td width="20%" align="left" height="28">报表编号</td>
		<td align="left" height="28">
            <input id="reportId" name="reportId" type="text" 
			       class="input-xlarge span3  easyui-validatebox"  
			 required="true" 
				   value="${reportFile.reportId}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left" height="28">文件名称</td>
		<td align="left" height="28">
            <input id="filename" name="filename" type="text" 
			       class="input-xlarge span3  easyui-validatebox"  
			 required="true" 
				   value="${reportFile.filename}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left" height="28">文件大小</td>
		<td align="left" height="28">
			<input id="fileSize" name="fileSize" type="text" 
			       class="input-xlarge span3  easyui-numberspinner" value="0" 
				   increment="10"   required="true" 
				   value="${reportFile.fileSize}"/>
		</td>
	</tr>

	<tr>
		<td width="20%" align="left" height="28">文件年月日</td>
		<td align="left" height="28">
			<input id="reportYearMonthDay" name="reportYearMonthDay" type="text" 
			       class="input-xlarge span3  easyui-numberspinner" value="0" 
				   increment="10"   required="true" 
				   value="${reportFile.reportYearMonthDay}"/>
		</td>
	</tr>
<tr>
	<td colspan="4" align="center">
	<br />
	<input type="button" name="save" value=" 保存 " class="btn btn-primary" onclick="javascript:saveData();">
	<input type="button" name="saveAs" value=" 另存 " class="btn" onclick="javascript:saveAsData();">
	<input type="button" name="back" value=" 返回 " class="btn" onclick="javascript:history.back();">
	</td>
	</tr>
</tbody>
</table>
</form>
</div>

</body>
</html>