<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邮件信息</title>
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
<link type="text/css"
	href="<%=request.getContextPath()%>/scripts/calendar/skins/aqua/theme.css"
	rel="stylesheet" />
<script src="<%=request.getContextPath()%>/scripts/calendar/calendar.js"
	language="javascript"></script>
<script
	src="<%=request.getContextPath()%>/scripts/calendar/lang/calendar-zh.js"
	language="javascript"></script>
<script
	src="<%=request.getContextPath()%>/scripts/calendar/calendar-setup.js"
	language="javascript"></script>
 
<script language="javascript" src="<%=request.getContextPath()%>/scripts/kindeditor/kindeditor-min.js"></script>
 
<script language="javascript">
    var contextPath = "<%=request.getContextPath()%>";
</script>
<script language="javascript" type="text/javascript">
	 
  KE.show({  id : 'content',
	               allowFileManager : true,
	               imageUploadJson : '<%=request.getContextPath()%>/mx/uploadJson',
			       fileManagerJson : '<%=request.getContextPath()%>/mx/fileManagerJson'
                });

	function saveAndSend(isSendMail){
     document.getElementById("isSendMail").value = isSendMail;
	 
	 if(document.getElementById("mailTo") != null){
	 var mailTo = document.getElementById("mailTo").value;
	  if(mailTo == ""){
		  alert("收件人是必须的！");
          document.getElementById("mailTo").focus();
		  return;
	  }
	 }

    if(document.getElementById("receiverIds") != null){
	 var receiverIds = document.getElementById("receiverIds").value;
	  if(receiverIds == ""){
		  alert("收件人是必须的！");
          document.getElementById("x_users_name").focus();
		  return;
	  }
	 }

	  var subject = document.getElementById("subject").value;
	  if(subject == ""){
		  alert("标题是必须的！");
          document.getElementById("subject").focus();
		  return;
	  }
     
	  document.getElementById("content").value=KE.html('content');
	  alert(document.getElementById("content").value);
	  document.getElementById("iForm").submit();
	}

</script>
<center><br>
<div class="content-block" style="width: 745px;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="发送邮件">
&nbsp;发送邮件</div>
<br>
<br>
<form id="iForm" name="iForm" method="post" class="x-form"
	action="<%=request.getContextPath()%>/mx/mail/send?mailType=${mailType}&x_ticket=${x_ticket}">
<input type="hidden" id="method" name="method" value="send"> <input
	type="hidden" id="isSave" name="isSave" value="true"> <input
	type="hidden" id="isSendMail" name="isSendMail" value=""> <input
	type="hidden" id="templateId" name="templateId"
	value="${templateId}"> <input type="hidden"
	id="receiverIds" name="receiverIds"
	value="${receiverIds}" />
<table style="width: 575px; text-align: left;" border="0"
	cellspacing="1" cellpadding="1" align="center">



	<tr>
		<td noWrap width='100%' height="27" colspan="4" noWrap>
		主&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题
		 <input id="subject" name="subject" type="text" size="80"
			value="${subject}" class="input-xlarge span9" />&nbsp;<span class="required">*</span> </td>
	</tr>

	<tr>
		<td noWrap width='100%' height="27" colspan="4" noWrap>收件人&nbsp;
		<input type="text" id="x_users_name" name="x_users_name"
			class="input-xlarge span9" size="80" readonly style="cursor: pointer;"
			onclick="javacsript:selectUser( 'receiverIds','x_users_name');"
			value="${x_users_name}">&nbsp; <span class="required">*</span></td>
	</tr>



	<tr>
		<td noWrap colspan="10" noWrap>
		<div id="x-content" align="left" style="padding-left: 10px;"><textarea
			id="content" name="content" rows="8" cols="100"  
			style="width: 745px; height: 450px; text-align: left;"><c:out
			value="${x_content}" escapeXml="false" /></textarea></div>
		</td>
	</tr>

	<tr>
		<td noWrap colspan="10"><br>
		<div id="x_files" align="left" style="padding-left: 20px;"></div>
		<script language="javascript">
	   link='<%=request.getContextPath()%>/mx/upload/showUpload?serviceKey=mail';
	   $('#x_files').load(link);
	  </script></td>
	</tr>
</table>
</td>
</tr>
</table>
<br />

<div align="center"><input type="button" id="submit_button"
	name="button" value="保存" onclick="javascript:saveAndSend('false');"
	class="btn"> <input type="button" id="submit_button"
	name="button" value="保存并发送" onclick="javascript:saveAndSend('true');"
	class="btn btn-primary"> <input type="button" name="button" value="返回 "
	class="btn" onclick="javascript:history.back();"></div>
<br />
<br />
</div>

</form>
</div>
</center>
<%@ include file="/WEB-INF/views/tm/footer.jsp"%>