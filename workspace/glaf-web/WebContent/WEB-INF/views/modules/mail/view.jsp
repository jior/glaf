<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<title>查看邮件信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
</head>
<body >
<br />
<center><br>
<div style="width: 80%;" align="center">
<div class="content-block"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="查看邮件信息">&nbsp;查看邮件信息</div>
<br>
<table border="0" cellpadding="0" cellspacing="0" width="95%">
	<tr>
		<td align="left" width="18%" height="27">主题</td>
		<td align="left" rowspan="1" colspan="4" width="82%" height="27">${mail.subject}</td>
	</tr>
	<tr>
		<td align="left" width="18%" height="27">发件人</td>
		<td align="left" rowspan="1" colspan="4" width="82%" height="27">${mail.senderName} ${mail.mailFrom}</td>
	</tr>
	<tr>
		<td align="left" width="18%" height="27">接收人</td>
		<td align="left" rowspan="1" colspan="4" width="82%" height="27">${mail.receiverName} ${mail.mailTo}</td>
	</tr>
	<tr>
		<td align="left" width="18%" height="27">创建日期</td>
		<td align="left" rowspan="1" colspan="4" width="82%" height="27">
		<fmt:formatDate value="${mail.createDate}" pattern="yyyy-MM-dd HH:mm" />
		</td>
	</tr>

	<tr>
		<td align="left" width="18%" height="27">内容</td>
		<td align="left" rowspan="1" colspan="4" width="82%">
		<div id="content" style="font-size: 15px">
		<c:out value="${mail.content}" escapeXml="false"/>
		</div>
		</td>
	</tr>
	<c:if test="${not empty mail.dataFiles}">
		<tr>
			<td align="left" width="18%" height="27">附件</td>
			<td align="left" rowspan="1" colspan="4" width="82%"><br>
			<div id="x_files" style="font-size: 12px;"><iframe
				id="newFrame" name="newFrame" width="0" height="0"></iframe> 
			<c:forEach
				items="${mail.dataFiles}" var="dataFile">
				<a
					href='<%=request.getContextPath()%>/mx/lob/lob/download?fileId=${dataFile.fileId}'
					target="newFrame"> ${dataFile.filename}&nbsp;<img
					src="<%=request.getContextPath()%>/images/download.gif"
					border="0"> </a>
			</c:forEach></div>
			</td>
		</tr>
	</c:if>
</table>
<br />
<br />
<div style="width: 90%;" align="center"><input type="button"
	name="button" value=" 关 闭 " class="btn"
	onclick="javascript:window.close();"></div>
<br />

</div>
</div>

</center>
<%@ include file="/WEB-INF/views/tm/footer.jsp"%>

</body>
</html>