<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=request.getContextPath()%>">
<title>文件上传</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">   
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/uploadify/css/uploadify.css">

<style type="text/css" media="screen">
.my-uploadify-button {
	background:none;
	border: none;
	text-shadow: none;
	border-radius:0;
}

.uploadify:hover .my-uploadify-button {
	background:none;
	border: none;
}

.fileQueue {
	width: 400px;
	height: 150px;
	overflow: auto;
	border: 1px solid #E5E5E5;
	margin-bottom: 10px;
}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dwz/dwz.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/uploadify/scripts/jquery.uploadify.min.js"></script>
</head>
<body style="font-size: 12px;">
	<input id="testFileInput" type="file" name="image" 
		uploaderOption="{
			swf:'<%=request.getContextPath()%>/scripts/uploadify/scripts/uploadify.swf',
			uploader:'<%=request.getContextPath()%>/mx/myupload?method=upload&serviceKey=${serviceKey}&resourceId=${resourceId}',
			formData:{aa:'xxx', bb:1},
			buttonText:'请选择文件',
			fileSizeLimit:'2000KB',
			fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
			fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
			auto:true,
			multi:true,
			method:'post',
			onUploadSuccess:uploadifySuccess,
			onQueueComplete:uploadifyQueueComplete
		}"
	/>

	<div class="divider"></div>

	<input id="testFileInput2" type="file" name="image2" 
		uploaderOption="{
			swf:'<%=request.getContextPath()%>/scripts/uploadify/scripts/uploadify.swf',
			uploader:'<%=request.getContextPath()%>/mx/myupload?method=upload&serviceKey=${serviceKey}&resourceId=${resourceId}',
			formData:{aa:'xxx', bb:1},
			queueID:'fileQueue',
			buttonImage:'<%=request.getContextPath()%>/scripts/uploadify/img/add.jpg',
			buttonClass:'my-uploadify-button',
			method:'post',
			width:102,
			auto:false
		}"
	/>
	
	<div id="fileQueue" class="fileQueue"></div>
	
	<input type="image" src="<%=request.getContextPath()%>/scripts/uploadify/img/upload.jpg" onclick="$('#testFileInput2').uploadify('upload', '*');"/>
	<input type="image" src="<%=request.getContextPath()%>/scripts/uploadify/img/cancel.jpg" onclick="$('#testFileInput2').uploadify('cancel', '*');"/>


	<div class="divider"></div>
</div>	
</body>
</html>
