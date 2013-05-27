<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.glaf.core.util.*" %>
<%@ page import="com.glaf.base.utils.upload.*" %>
<!DOCTYPE html>
<html>
<base target="_self" />
<head>
<title>文件上传结果</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=request.getContextPath()%>/scripts/upload/css/fileUpload.css" type="text/css" rel="stylesheet"/>
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet"/>
<script type='text/javascript' src='<%=request.getContextPath()%>/scripts/main.js'></script>
<script type="text/javascript">
 
  function submitUpload() {
    window.returnValue = getValues('file'); 
    window.close();
  }

  function getValues(name){
    var inputObj = document.getElementsByName(name);
	var values = '';
	for (var i = 0; i < inputObj.length; i++) {
	  if (inputObj.item(i).value != '') {
		  values += inputObj.item(i).value + ',';
		}
	}
	if (values.substring(values.length - 1) == ",") {
	  values = values.substring(0, values.length - 1);
	}
	return values.replace(/\r\n/ig, "%0A");
}

</script>
</head>
<body>
<form id="fileUploadForm" name="fileUploadForm" method="post" action="" onsubmit="return submitUpload()">
  <div id="resultPanel">
    <div><span>上传文件列表:</span></div>
    <%
	FileUploadStatus uploadStatus=FileMgmtFactory.getStatusBean(request);
    int num = uploadStatus.getUploadFileUrlList().size();
	boolean isFailed = false;
	for(int i=0;i<num;i++){
		FileInfo fileInfo = (FileInfo)uploadStatus.getUploadFileUrlList().get(i);
		String fileName=fileInfo.getFilename();
		String fileId=fileInfo.getFileId();
		String json = fileInfo.toJsonObject().toJSONString();
		json = RequestUtils.encodeString(json);
	%>
    <div><a href="<%=request.getContextPath()%>/fileUploadService?download=true&fileId=<%=fileId%>"><%=fileName%></a>
        <input name="file" type="hidden" value="<%=json%>" />
    </div>
    <%
	}
	if (uploadStatus.getStatus().indexOf("错误")>=0){
	  isFailed = true;
	%>
    <div id='errorArea'><span>错误信息:<%=uploadStatus.getStatus() %></span></div>
    <%	
	}
	else if (uploadStatus.getCancel()){
	  isFailed = true;
	%>
    <div id='normalMessageArea'><span>由于用户取消上传，所以已经上传的文件均被删除</span></div>
    <%
	}
	FileMgmtFactory.getInstance().removeUploadStatus(RequestUtils.getIPAddress(request));
	
%>
    <%
  if (isFailed || num == 0) {
%>
    <div align="center">
      &nbsp;
      <input name="close" type="button" class="button" value="关闭" onclick="javascript:self.close()" />
    </div>
    <%
  } else {
%>
    <div align="center">
      <input name="Submit" type="submit" class="button" value="提交" />
    </div>
    <%
  }
%>
  </div>
</form>
<%
  if (!isFailed && num > 0) {
%>
<script type="text/javascript">
 
  submitUpload();
 
</script>
<%
	}
%>
</body>
</html>