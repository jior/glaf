<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ page import="com.glaf.base.utils.upload.*"%>
<%
  double fileSizeMax = (int)(((double)(BackGroundService.UPLOAD_FILE_SIZE_MAX / 1024 / 1024.0)) * 100) / 100.0;
  double sizeMax = (int)(((double)(BackGroundService.UPLOAD_SIZE_MAX / 1024 / 1024.0)) * 100) / 100.0;
	String type = request.getParameter("type");
	if (type == null) {
	  type = "";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<base target="_self" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="./css/fileUpload.css" type="text/css" rel="stylesheet"/>
<link href="../../css/site.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="./js/prototype.js"></script>
<script type="text/javascript" src="./js/AjaxWrapper.js"></script>
<script type="text/javascript" src="../../js/verify.js"></script>
<script type="text/javascript">
<!--
 function checkFileUploadForm(form) {
	if (form.file.value == '') {
		alert('�ļ�����Ϊ��.');
		form.file.focus();
		return false;
	}
	var type = '<%= type %>';
	if (type.length > 0 && !IsUploadFileType(form.file.value, type)) {
	  alert('�ļ��ϴ����Ͳ���ȷ.�����ϴ��������У�' + type);
		form.file.focus();
		return false;
	}
	startProgress();
	return true;
 }
//-->
</script>
<title>�ļ��ϴ�</title>

</head>
<body>
<div id="controlPanel">
  <div id="readme">�ϴ�˵��:&nbsp;&nbsp;����ϴ���:<%= fileSizeMax %>M�������ļ���󳤶�:<%= sizeMax %>M</div>
  <div id="uploadFileUrl"></div>
  <form id="fileUploadForm" name="fileUploadForm" action="./BackGroundService.action" enctype="multipart/form-data" method="post" onsubmit="return checkFileUploadForm(this);">
    <div id="upload-box">
		<div><input name="file" type="file" id="file" size="40" />
		</div>
		</div>
    <br />
    <input name="uploadButton" type="submit" class="button" id="uploadButton" value="��ʼ�ϴ�"/>
    <input name="cancelUploadButton" type="button" class="button" id="cancelUploadButton" value="ȡ���ϴ�" disabled="disabled"/>
    <br />
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
<script type="text/javascript" src="./js/upload.js"></script>
</body>
</html>