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
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/pages/common/style.jsp"%>
</HEAD>
<BODY leftMargin=0 topMargin=0 marginheight="0" marginwidth="0" >
<div style="height: 580px"> 
<br><br>
<div align="center"><b>流程列表</b></div><br>
<table id="processTb" align="center" class="table-border" cellspacing="1" cellpadding="4" width="90%" nowrap >
    <tr class="table-bar" align="middle" nowrap>
	  <td align="center" class="table-title" nowrap>流程编号</td>
	  <td align="center" class="table-title" nowrap>流程名称</td>
	  <td align="center" class="table-title" nowrap>版本</td>
	  <td align="center" class="table-title" nowrap>发布日期</td>
	  <td align="center" class="table-title" nowrap>流程图</td>
	  <td align="center" class="table-title" nowrap>流程实例</td>
    </tr>
    <c:forEach items="${processDefinitions}" var="processDefinition">
    <%
	pageContext.removeAttribute("deployInstance");
	org.jbpm.graph.def.ProcessDefinition pd = (org.jbpm.graph.def.ProcessDefinition)pageContext.getAttribute("processDefinition");
	 org.jpage.jbpm.model.DeployInstance deployInstance = (org.jpage.jbpm.model.DeployInstance)deployInstanceMap.get(String.valueOf(pd.getId()));
     pageContext.setAttribute("deployInstance", deployInstance);
	%>
    <tr class="beta">
	  <td align="center"><c:out value="${processDefinition.id}"/></td>
	  <td align="left">
      <a href="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=processInstances&loadBusiness=<c:out value="${loadBusiness}"/>&running=1&processDefinitionId=<c:out value="${processDefinition.id}"/>&operationType=<c:out value="${operationType}"/>">
	  <c:out value="${processDefinition.name}"/>
	  </a>
	  </td>
	  <td align="center"><c:out value="${processDefinition.version}"/></td>
	  <td align="center">
	  <fmt:formatDate value="${deployInstance.deployDate}" pattern="yyyy-MM-dd HH:mm"/>
	  </td>
	  <td align="center"  nowrap> <a href="<%=request.getContextPath()%>/workflow/processimage?definitionId=<c:out value="${processDefinition.id}"/>" target="_blank"><img src="images/process.gif" border="0"></a></td>
	  <td align="center"  nowrap> <a href="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=processInstances&loadBusiness=<c:out value="${loadBusiness}"/>&running=1&processDefinitionId=<c:out value="${processDefinition.id}"/>&operationType=<c:out value="${operationType}"/>"><img src="images/view.gif" border="0"></a></td>
    </tr>
    </c:forEach>
  </table>
  </div>
 </BODY>
</HTML>