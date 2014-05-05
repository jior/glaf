<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="org.apache.commons.lang.*"%>
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>

<br>
<br>
<center>
<div class="content-block" style="width: 60%; height: 200px;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="邮件发送完成"> 邮件发送完成</div>
<div align="center"><br>
<br>
<c:choose>
	<c:when test="${mail.sendStatus == 1}">
		   发送状态：<font color="#00ff00"><strong>发送成功</strong></font>
	</c:when>
	<c:when test="${mail.sendStatus < 0}">
		   发送状态：<font color="#ff0000"><strong>发送失败</strong></font>
	</c:when>
	<c:when test="${mail.sendStatus == 0}">
		  发送状态： <font color="#000000"><strong>未发送</strong></font>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose> <br>
<br>
邮件已经保存，点击 <a
	href="<%=request.getContextPath()%>/mx/mail/mailList?mailBox=S&mailType=${mailType}">这里</a>
查看。</div>
</div>
</center>
