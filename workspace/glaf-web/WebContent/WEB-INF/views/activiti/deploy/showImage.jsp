<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
 <c:when test="${not empty path}">
  <img src="<%=request.getContextPath()%>/${path}">
 </c:when>
 <c:when test="${not empty processDefinition}">
  <div align="left" style="padding-left: 60px;">
    <br>
    <br>  �����ţ� ${processDefinition.deploymentId}
	<br>  �����ţ� ${processDefinition.id}
	<br>  �������ƣ� ${processDefinition.name}
	<br>  ����Key��  ${processDefinition.key}
	<br>  ���̰汾�� ${processDefinition.version}
  </div>
 </c:when>
 <c:otherwise>
     ���̷�����ɣ�  
 </c:otherwise>
</c:choose>