<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.*"%>
<%@page import="org.jpage.jbpm.util.*"%>
<%
    Map deployInstanceMap = (Map)request.getAttribute("deployInstanceMap");
	if(deployInstanceMap == null){
		deployInstanceMap = new HashMap();
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">

</head>
<body onLoad="DynarchMenu.setup('menu1', { context: true});" id="document" >
<jsp:include page="/WEB-INF/views/module/header.jsp" flush="true"/>
<div style="height: 580px"> 
<br><br>
<div align="center"><b>流程列表</b></div><br>
<table id="processTb" align="center" class="list-box" cellspacing="1" cellpadding="0" width="95%" nowrap >
    <tr class="list-title" align="middle" nowrap>
	  <td align="center" width="10%"  nowrap>流程编号</td>
	  <td align="center" width="20%" nowrap>流程名称</td>
	  <td align="center" width="10%" nowrap>版本</td>
	  <td align="center" width="10%" nowrap>发布日期</td>
	  <td align="center" width="20%" nowrap>功能键</td>
    </tr>
    <c:forEach items="${processDefinitions}" var="processDefinition">
    <%
	pageContext.removeAttribute("deployInstance");
	org.jbpm.graph.def.ProcessDefinition pd = (org.jbpm.graph.def.ProcessDefinition)pageContext.getAttribute("processDefinition");
	 org.jpage.jbpm.model.DeployInstance deployInstance = (org.jpage.jbpm.model.DeployInstance)deployInstanceMap.get(String.valueOf(pd.getId()));
     pageContext.setAttribute("deployInstance", deployInstance);
	%>
    <tr class="list-a">
	  <td height="20" align="center"><c:out value="${processDefinition.id}"/></td>
	  <td align="left">
      <a href="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=processInstances&loadBusiness=<c:out value="${loadBusiness}"/>&running=1&processDefinitionId=<c:out value="${processDefinition.id}"/>&operationType=<c:out value="${operationType}"/>">
	  <c:out value="${processDefinition.name}"/>
	  </a>
	  </td>
	  <td align="center"><c:out value="${processDefinition.version}"/></td>
	  <td align="center">
	  <fmt:formatDate value="${deployInstance.deployDate}" pattern="yyyy-MM-dd HH:mm"/>
	  </td>
	  <td align="left">
	    &nbsp; <a href="<%=request.getContextPath()%>/workflow/processimage?definitionId=<c:out value="${processDefinition.id}"/>" target="_blank"><img src="images/process.gif" border="0"></a> 
	   &nbsp; <a href="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=processInstances&loadBusiness=<c:out value="${loadBusiness}"/>&running=1&processDefinitionId=<c:out value="${processDefinition.id}"/>&operationType=<c:out value="${operationType}"/>"><img src="images/view.gif" border="0"></a></td>
    </tr>
    </c:forEach>
  </table>
  </div>
 </body>
</html>