<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%
    org.jpage.util.RequestUtil.setRequestParameterToAttribute(request);
%>
<script>
	location.href="<%=request.getContextPath()%>/workflow/task?businessKey=<c:out value="${businessKey}"/>&businessValue=<c:out value="${businessValue}"/>&<c:out value="${businessKey}"/>=<c:out value="${businessValue}"/>&forward=/workflow/process/task.jsp";
</script>
