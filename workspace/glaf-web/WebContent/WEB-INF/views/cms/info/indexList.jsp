<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%
  String context = request.getContextPath();
 
%>

<table width="96%" border="0" cellspacing="1" cellpadding="0">
	<c:forEach items="${rows}" var="info">
		<tr>
			<td width="75%" align="left" height="22" >&nbsp;&nbsp;<a
				href='<%=request.getContextPath()%>/mx/public/info/view?id=${info.id}'
				title='${info.subject}' target="_blank">${info.subject}</a>
			</td>
			<td width="25%" align="center" height="22" align="center">&nbsp;[<fmt:formatDate
				value="${info.createDate}" pattern="yyyy-MM-dd" />]
			</td>
		</tr>
	</c:forEach>
</table>
