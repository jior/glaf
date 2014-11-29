<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.job.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%
 String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>任务历史信息</title>
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/scripts/calendar/skins/aqua/theme.css"/>
 
  
<body id="document" style="padding-left:120px;padding-right:120px">
 
<br> 
 
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="调度历史信息"> &nbsp;调度历史信息
</div>
<br>
 
<table border="0" cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-condensed">
	<tr>
		<td width="25%" height="24">名称&nbsp;</td>
		<td height="24">${schedulerLog.taskName}</td>
	</tr>

	<tr>
		<td width="25%" height="24">内容&nbsp;&nbsp;</td>
		<td height="24"><c:out value="${schedulerLog.content}" /></td>
	</tr>

	<tr>
		<td width="25%" height="24">开始日期&nbsp;&nbsp;</td>
		<td height="24"> 
		  <fmt:formatDate value="${schedulerLog.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</td>
	</tr>

	<tr>
		<td width="25%" height="24">结束日期&nbsp;&nbsp;</td>
		<td height="24">
            <fmt:formatDate value="${schedulerLog.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</td>
	</tr>

	<tr>
		<td width="25%" height="24">运行状态&nbsp;</td>
		<td height="24">
		   <c:choose> 
		     <c:when test="${schedulerLog.status==0}">等待运行</c:when>
			 <c:when test="${schedulerLog.status==1}">运行中</c:when>
			 <c:when test="${schedulerLog.status==2}">运行成功</c:when>
			 <c:when test="${schedulerLog.status==3}">运行失败</c:when>
		   </c:choose>
		</td>
	</tr>
 
    <c:if test="${schedulerLog.status==3}">
	<tr>
		<td width="25%" height="24">错误信息&nbsp;</td>
		<td height="24">
		    <pre><div style="color:#ff0000"><c:out value="${schedulerLog.exitMessage}" /></div></pre>
		</td>
	</tr>
	</c:if>

	 
    </table> 

	<!-- <div align="center">
	 <input name="close" type="button" value="关闭" class="btn btn-primary" onclick="javascript:window.close();">
	</div> -->
 
<br/>
 
</body> 
</html>