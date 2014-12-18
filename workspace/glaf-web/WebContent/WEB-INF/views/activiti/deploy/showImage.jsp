<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
 <c:when test="${not empty path}">
  <img src="<%=request.getContextPath()%>/${path}">
 </c:when>
 <c:when test="${not empty processDefinition}">
  <div align="left" style="padding-left: 60px;">
    <br>
    <br>  部署编号： ${processDefinition.deploymentId}
	<br>  定义编号： ${processDefinition.id}
	<br>  流程名称： ${processDefinition.name}
	<br>  流程Key：  ${processDefinition.key}
	<br>  流程版本： ${processDefinition.version}
  </div>
 </c:when>
 <c:otherwise>
     流程发布完成！  
 </c:otherwise>
</c:choose>