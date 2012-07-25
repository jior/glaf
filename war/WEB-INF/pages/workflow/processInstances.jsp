<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/jpage.tld" prefix="jpage" %>
<%@ page import="java.util.*"%>
<%@ page import="org.jbpm.graph.exe.*"%>
<%@ page import="org.jpage.jbpm.model.*"%>
<%@ page import="org.jpage.jbpm.util.*"%>
<%@ page import="org.jpage.core.query.paging.Page" %>
<%
  Page jpage = (Page)request.getAttribute("jpage");
  if(jpage == null)
  {
	  jpage = Page.EMPTY_PAGE;
  }
  request.setAttribute("processInstances",jpage.getRows());
  Map rowMap = (Map)request.getAttribute("rowMap");
  %>
<HTML><HEAD>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/pages/common/style.jsp"%>
<LINK href="<%=request.getContextPath()%>/workflow/styles/<c:out value="${frame_skin}"/>/main/main.css" type=text/css rel=stylesheet>
</HEAD>
<script language="JavaScript">
  function chooseStatus()
  {
    var viewType = document.iForm.viewType.value;
	if(viewType == "1"){
		document.iForm.running.value = "1";
	}
	if(viewType == "0"){
		document.iForm.running.value = "0";
	}
	document.iForm.submit();
  }

  function deleteRow(processInstanceId)
  {
	if(confirm("数据删除后不能恢复，确认删除吗？")){ 
	    document.getElementById("processInstanceId").value=processInstanceId;
		document.iForm.action="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=deleteProcessInstance";
		document.iForm.submit();
	}
	return false;
  }

  function suspend(processInstanceId)
  {
	if(confirm("流程实例将被暂停执行，确认吗？")){ 
	    document.getElementById("processInstanceId").value=processInstanceId;
		document.iForm.action="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=suspend";
		document.iForm.submit();
	}
	return false;
  }

  function resume(processInstanceId)
  {
	if(confirm("流程实例将被恢复执行，确认吗？")){ 
	    document.getElementById("processInstanceId").value=processInstanceId;
		document.iForm.action="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=resume";
		document.iForm.submit();
	}
	return false;
  }

  function sendMail(processInstanceId){
    window.open("<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=editMail&processInstanceId="+processInstanceId);
  }

  function queryProcess(){
	  location.href="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=query";
  }

</script>
<body leftMargin=0 topMargin=0 marginheight="0" marginwidth="0">
<form name="iForm" action="<%=request.getContextPath()%>/workflow/processMonitorController.jspa" method="post">
<input type="hidden" id="processInstanceId" name="processInstanceId">
<br>
<table border="0" width="90%" align="center">
 <tr>
      <td align="left">
        <select id="viewType" name="viewType" onchange="javascript:chooseStatus();" class="list"> 
		<c:choose>
		  <c:when test="${running == 1}">
			<option value="1" selected>进行中</option>
			<option value="0">已完成</option>
		  </c:when>
		  <c:otherwise>
			<option value="1">进行中</option>
			<option value="0" selected>已完成</option>
		  </c:otherwise>
		 </c:choose>		    
		</select>
      </td>
	  <td align="right">
	    <input type="button" name="button" class="button" value=" 查询 " onclick="javascript:queryProcess();">
	  </td>
 </tr>
</table>
<br>
<div align="center"><b>流程实例列表</b></div><br>
<input type="hidden" name="processDefinitionId" value="<c:out value="${processDefinitionId}"/>">
<input type="hidden" name="loadBusiness" value="<c:out value="${loadBusiness}"/>">
<input type="hidden" name="running" value="<c:out value="${running}"/>">
<input type="hidden" name="method" value="processInstances">

<table align="center" class="table-border" cellspacing="1" cellpadding="4" width="90%" nowrap>
    <tr class="gamma"> 
      <td colspan="10" class="gamma">
	      <jpage:paging form="iForm"/>
	  </td>
    </tr>
    <tr class="table-bar" nowrap>
	  <td width="5%" height="15" align="center" class="table-title" nowrap>编号</td>
	  <td width="15%" height="15" align="center" class="table-title" nowrap>流程名称</td>
	  <td width="5%" height="15" align="center" class="table-title" nowrap>版本</td>
	  <c:if test="${loadBusiness == 1}">
	  <td width="10%" height="15" align="center" class="table-title" nowrap>启动者</td>
      <td width="40%" height="15" align="center" class="table-title" nowrap>主题</td>
	  <td width="10%" height="15" align="center" class="table-title" nowrap>单据编号</td>
	  </c:if>
	  <td width="15%" height="15" align="center" class="table-title" nowrap>时间</td>
	  <td width="10%" height="15" align="center" class="table-title" nowrap>功能键</td>
    </tr>
    <c:forEach items="${processInstances}" var="processInstance">
	<%
	  ProcessInstance pi = (ProcessInstance)pageContext.getAttribute("processInstance");
	  BusinessInstance bi = null;
	  if(rowMap != null && rowMap.get(String.valueOf(pi.getId())) != null){
		  bi = (BusinessInstance)rowMap.get(String.valueOf(pi.getId()));
	  }
	%>
    <tr class="beta">
	  <td width="5%" height="15" align="center">
	  <a href="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=stateInstances&processInstanceId=<c:out value="${processInstance.id}"/>" title="查看流程实例" target="_blank">
	  <c:out value="${processInstance.id}"/>
	  </a>
	  </td>
	  <td width="15%" height="15" align="left">
	  <a href="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=stateInstances&processInstanceId=<c:out value="${processInstance.id}"/>">
	  <c:out value="${processInstance.processDefinition.name}"/>
	  </a>
	  </td>
	  <td width="5%" height="15" align="center">
	  <c:out value="${processInstance.processDefinition.version}"/>
	  </td>
	  <c:if test="${loadBusiness == 1}">
	  <td width="10%" height="15" align="center" nowrap>
      <%
	  if(bi != null && bi.getRequesterName() != null){
		  out.println(bi.getRequesterName());
	  }
	  %>
	  </td>
      <td width="40%" height="15" align="left" nowrap>
	  <%
	  if(bi != null && bi.getTitle() != null){
		  out.println(bi.getTitle());
	  }
	  %>
	  </td>
	  <td width="10%" height="15" align="left" nowrap>
	  <%
	  if(bi != null && bi.getBusinessValue() != null){
		  out.println(bi.getBusinessValue());
	  }
	  %>
	  </td>
	  </c:if>
	  <td width="15%" height="15" align="center" nowrap>
	  <fmt:formatDate value="${processInstance.start}" pattern="yyyy-MM-dd HH:mm"/>
	  </td>
	  <td width="10%" height="15" align="center" nowrap>
	  <a href="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=stateInstances&processInstanceId=<c:out value="${processInstance.id}"/>" title="查看流程实例" target="_blank"><img src="<%=request.getContextPath()%>/workflow/images/view.gif" border="0"> </a>
	  </td>
    </tr>
    </c:forEach>
  <tr class="gamma"> 
    <td colspan="10" class="gamma">
	    <jpage:paging form="iForm"/>
	</td>
  </tr>
</table>
 </BODY>
</HTML>