<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
  request.setAttribute("businessInstances",jpage.getRows());
  %>
<HTML><HEAD>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/pages/common/style.jsp"%>
<LINK href="<%=request.getContextPath()%>/workflow/styles/<c:out value="${frame_skin}"/>/main/main.css" type=text/css rel=stylesheet>
</HEAD>
<script language="JavaScript">

  function queryProcess(){
	  location.href="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=query";
  }

</script>
<body leftMargin=0 topMargin=0 marginheight="0" marginwidth="0">
<form name="iForm" action="<%=request.getContextPath()%>/workflow/processMonitorController.jspa" method="post">
<input type="hidden" name="method" value="businessInstances" >
<br>
<table border="0" width="90%" align="center">
 <tr>
	  <td align="right">
	    <input type="button" name="button" class="button" value=" 查询 " onclick="javascript:queryProcess();">
	  </td>
 </tr>
</table>
<br>
<div align="center"><b>流程实例列表</b></div><br>


<table align="center" class="table-border" cellspacing="1" cellpadding="4" width="90%" nowrap>
    <tr class="gamma"> 
      <td colspan="10" class="gamma">
	      <jpage:paging form="iForm"/>
	  </td>
    </tr>
    <tr class="table-bar" nowrap>
	  <td height="15" align="center" class="table-title" nowrap>编号</td>
	  <td height="15" align="center" class="table-title" nowrap>流程名称</td>
	  <td height="15" align="center" class="table-title" nowrap>版本</td>
	  <td height="15" align="center" class="table-title" nowrap>启动者</td>
      <td height="15" align="center" class="table-title" nowrap>主题</td>
	  <td height="15" align="center" class="table-title" nowrap>业务编号</td>
	  <td height="15" align="center" class="table-title" nowrap>时间</td>
	  <td height="15" align="center" class="table-title" nowrap>功能键</td>
    </tr>
    <c:forEach items="${businessInstances}" var="businessInstance">
     <%
	     BusinessInstance bi = (BusinessInstance)pageContext.getAttribute("businessInstance");
	 %>
    <tr class="beta">
	  <td height="15" align="center">
	  <a href="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=stateInstances&processInstanceId=<c:out value="${businessInstance.processInstanceId}"/>">
	  <c:out value="${businessInstance.processInstanceId}"/>
	  </a>
	  </td>
	  <td height="15" align="left">
	  <a href="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=stateInstances&processInstanceId=<c:out value="${businessInstance.processInstanceId}"/>">
	  <c:out value="${businessInstance.processName}"/>
	  </a>
	  </td>
	  <td height="15" align="center">
	  <c:out value="${businessInstance.processVersion}"/>
	  </td>
	  <td height="15" align="center" nowrap>
      <%
	  if(bi != null && bi.getRequesterName() != null){
		  out.println(bi.getRequesterName());
	  }
	  %>
	  </td>
      <td height="15" align="left" nowrap>
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
	  <td height="15" align="center" class="table-title" nowrap>
	  <fmt:formatDate value="${businessInstance.startDate}" pattern="yyyy-MM-dd HH:mm"/>
	  </td>
	  <td height="15" align="center" class="table-title" nowrap>
	  <a href="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=stateInstances&processInstanceId=<c:out value="${businessInstance.processInstanceId}"/>" target="_blank" title="查看流程实例"><img src="<%=request.getContextPath()%>/workflow/images/view.gif" border="0"> </a>
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