<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>上传文件</title>
 <%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
 
</script>
</head>
<body leftmargin=0 topmargin=0 marginwidth="0" marginheight="0">
<div class="content-block" style="width: 845px;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="邮件任务">&nbsp;邮件任务</div>
<fieldset class="x-fieldset" style="width: 96%;">
<form id="iForm" name="iForm" method="post" enctype="multipart/form-data" 
           action="<%=request.getContextPath()%>/mx/mail/mailTask/uploadMails?taskId=${taskId}">

<center>
 <img id="loading" src="<%=request.getContextPath()%>/images/loading.gif" style="display: none;" />
<div   width="98%" >
<br>
 请选择一个邮件地址文件&nbsp;
 <input id="fileToUpload" type="file" size="45" name="fileToUpload" class="input">
<br>
 只允许上传文本文件（.txt），并且邮件之间用“,”隔开
<br /><br />
<input type="submit" class="btn btn-primary" name="button" value=" 确 定 " >
&nbsp;&nbsp; 
<br><br>
</div>
</center>
</form>
  </fieldset>
  </div>
</body>
</html>