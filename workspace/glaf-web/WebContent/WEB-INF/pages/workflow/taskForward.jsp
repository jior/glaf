<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    org.jpage.util.RequestUtil.setRequestParameterToAttribute(request);
%>
<script>
	location.href="<%=request.getContextPath()%>/workflow/task?businessKey=<c:out value="${businessKey}"/>&businessValue=<c:out value="${businessValue}"/>&<c:out value="${businessKey}"/>=<c:out value="${businessValue}"/>&forward=/workflow/process/task.jsp";
</script>
