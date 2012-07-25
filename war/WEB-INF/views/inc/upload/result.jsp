<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ page import="com.glaf.base.utils.upload.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<base target="_self" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="./css/fileUpload.css" type="text/css" rel="stylesheet"/>
<link href="../../css/site.css" type="text/css" rel="stylesheet"/>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/js/main.js'></script>
<script type="text/javascript">
<!--
function submitUpload() {
  window.returnValue = getValues('file'); 
  window.close();
}
//-->
</script>
<title>�ļ��ϴ����</title>
</head>
<body>
<form id="fileUploadForm" name="fileUploadForm" method="post" action="" onsubmit="return submitUpload()">
  <div id="resultPanel">
    <div><span>�ϴ��ļ��б�:</span></div>
    <%
	
	FileUploadStatus fUploadStatus=BackGroundService.getStatusBean(request);
  int num = fUploadStatus.getUploadFileUrlList().size();
	boolean isFailed = false;
	for(int i=0;i<num;i++){
		String fileName=(String)fUploadStatus.getUploadFileUrlList().get(i);
		String url=fUploadStatus.getBaseDir()+"/"+fileName;
	%>
    <div><a href="<%=request.getContextPath() + url%>"><%=fileName%></a>
        <input name="file" type="hidden" value="<%= fileName %>" />
    </div>
    <%
	}
	if (fUploadStatus.getStatus().indexOf("����")>=0){
	  isFailed = true;
	%>
    <div id='errorArea'><span>������Ϣ:<%=fUploadStatus.getStatus() %></span></div>
    <%	
	}
	else if (fUploadStatus.getCancel()){
	  isFailed = true;
	%>
    <div id='normalMessageArea'><span>�����û�ȡ���ϴ��������Ѿ��ϴ����ļ�����ɾ��</span></div>
    <%
	}
	BeanControler.getInstance().removeUploadStatus(request.getRemoteAddr());
	
%>
    <%
  if (isFailed || num == 0) {
%>
    <div align="center"><a href="<%=request.getContextPath()%>/WEB-INF/views/inc/upload/fileUpload.jsp">�����ϴ�</a>
      &nbsp;
      <input name="close" type="button" class="button" value="�ر�" onclick="javascript:self.close()" />
    </div>
    <%
  } else {
%>
    <div align="center">
      <input name="Submit" type="submit" class="button" value="�ύ" />
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
<!--
  submitUpload();
//-->
</script>
<%
	}
%>
</body>
</html>