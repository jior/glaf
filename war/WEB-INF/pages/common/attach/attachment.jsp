<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="org.jpage.util.*" %>
<%@ page import="org.jpage.core.attachment.model.*" %>

<html>
<head>
<title>上传附件</title>
<%
 String contextPath = request.getContextPath();
 String styleFile = "skin/color/08/en_US.css";
 %>
<link rel="stylesheet" href="<%=contextPath%>/workflow/<%=styleFile%>" type="text/css"/>
<link rel="stylesheet" href="<%=contextPath%>/workflow/skin/style/styles.css" type="text/css"/>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%
    String service_key = request.getParameter("service_key");
	String fileType = request.getParameter("fileType");
	if (org.apache.commons.lang.StringUtils.isBlank(service_key)) {
		service_key = Constant.SESSION_ATTACHMENT_KEY;
	}
	if (org.apache.commons.lang.StringUtils.isBlank(fileType)) {
		fileType = "0";
	}
	Collection attachs = (Collection) request.getSession().getAttribute(service_key);
	if (attachs != null && attachs.size() > 0) {
%>
<br>
<table align="center" class="table-border" cellspacing="1" cellpadding="4" width="90%" >
<%
			Iterator iterator = attachs.iterator();
			while (iterator.hasNext()) {
				Attachment attach = (Attachment) iterator.next();
				if(!fileType.equals(String.valueOf(attach.getAttachType()))){
					continue;
				}
				String filename = attach.getFilename();
				filename = org.jpage.util.RequestUtil.encodeURL(filename);
				String folder = attach.getFolder();
				folder = folder.replace('\\','/');
				folder = org.jpage.util.RequestUtil.encodeURL(folder);
%>			
  <tr class="beta">
    <td class="table-title" width="18%" height="16" align="center">附件</td>
    <td class="table-content" width="62%">
	 <a href='DownloadService?actionType=DOWNLOAD_ATTACHMENT&filename=<%=filename%>&dataFile=<%=folder%>&deviceId=temp&service_key=<%=service_key%>&fileType=<%=fileType%>' target="newFrame"><iframe id="newFrame" name="newFrame" width="0" height="0"></iframe><font color=green><U><%=attach.getFilename()%></U></font></a>
    </td>
	<td class="table-content" width="10%" align=center>
       <a href='AttachmentService?actionType=REMOVE_ATTACHMENT&service_key=<%=service_key%>&attachId=<%=attach.getAttachId()%>&fileType=<%=fileType%>'><img src="<%=contextPath%>/workflow/images/delete.gif" border="0" alt="删除记录" style="cursor:hand;"></a>
	</td>
   </tr>
<%			
		}
	   out.println("</table>");
	}
%>
<br>
<form action="AttachmentService?actionType=UPLOAD_ATTACHMENT&service_key=<%=service_key%>&fileType=<%=fileType%>" 
      method="post"  ENCTYPE="multipart/form-data" name="attachForm" >
<table align="center" class="table-border" cellspacing="1" cellpadding="4" width="90%" >
  <tr class="beta">
    <td class="table-title" width="18%" height="16" align="center">附件1</td>
    <td class="table-content" width="82%">
      <input type="file" name="file1" size="30" class="txt">
    </td>
   </tr>
  <tr class="beta">
    <td class="table-title" width="18%" height="16" align="center">附件2</td>
    <td class="table-content" width="82%">
      <input type="file" name="file2" size="30" class="txt">
    </td>
   </tr>
  <tr class="beta">
    <td class="table-title" width="18%" height="16" align="center">附件3</td>
    <td class="table-content" width="82%">
      <input type="file" name="file3" size="30" class="txt">
    </td>
   </tr>
  <tr class="beta">
    <td class="table-title" width="18%" height="16" align="center">附件4</td>
    <td class="table-content" width="82%">
      <input type="file" name="file4" size="30" class="txt">
    </td>
   </tr>
  <tr class="beta">
    <td class="table-title" width="18%" height="16" align="center">附件5</td>
    <td class="table-content" width="82%">
      <input type="file" name="file5" size="30" class="txt">
    </td>
   </tr>
  <tr class="table-tool">
     <td colspan="4" align="center">
	 <input type="submit" class="functionButton" value="确定">&nbsp;
	 <input type="button" class="functionButton" value="关闭" onclick="javascript:window.close();">
  </tr>

 </table>
</form>
</body>
</html>