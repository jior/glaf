<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    double fileSizeMax = (int)(((double)(5 * 1024 * 1024 / 1024 / 1024.0)) * 100) / 100.0;
    double sizeMax = (int)(((double)(5 * 1024 * 1024 / 1024 / 1024.0)) * 100) / 100.0;
	String type = request.getParameter("type");
	if (type == null) {
	  type = "";
	}
%>
<!DOCTYPE html>
<html>
<base target="_self" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=request.getContextPath()%>/scripts/upload/css/fileUpload.css" type="text/css" rel="stylesheet"/>
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/upload/js/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/upload/js/AjaxWrapper.js"></script>

<script type="text/javascript">
 var contextPath="<%=request.getContextPath()%>";


 function checkFileUploadForm(form) {
	if (form.file.value == '') {
		alert('文件不能为空.');
		form.file.focus();
		return false;
	}
	var type = '<%= type %>';
	if (type.length > 0 && !IsUploadFileType(form.file.value, type)) {
	    alert('文件上传类型不正确.允许上传的类型有：' + type);
		form.file.focus();
		return false;
	}
	startProgress();
	return true;
 }
 
</script>
<title>文件上传</title>

</head>
<body>
<div id="controlPanel">
  <div id="readme">上传说明:&nbsp;&nbsp;最大上传量:<%= fileSizeMax %>M，单个文件最大长度:<%= sizeMax %>M</div>
  <div id="uploadFileUrl"></div>
  <form id="fileUploadForm" name="fileUploadForm" method="post"
        action="<%=request.getContextPath()%>/fileUploadService?referType=${referType}&referId=${referId}&viewType=${viewType}"
        enctype="multipart/form-data" onsubmit="return checkFileUploadForm(this);">
    <div id="upload-box">
		<div>
		   <input name="file" type="file" id="file" size="40" />
		</div>
	</div>
    <br/>
    <input name="uploadButton" type="submit" class="button" id="uploadButton" value="开始上传"/>
    <input name="cancelUploadButton" type="button" class="button" id="cancelUploadButton" value="取消上传" disabled="disabled"/>
    <br/>
  </form>
  <div id="progressBar">
    <div id="theMeter">
      <div id="progressBarText"></div>
      <div id="totalProgressBarBox">
        <div id="totalProgressBarBoxContent"></div>
      </div>
    </div>
    <div id="progressStatusText"></div>
  </div>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/upload/js/upload.js"></script>
</body>
</html>