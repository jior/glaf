<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/jpage.tld" prefix="jpage" %>
<%@ page import="java.util.*" %>
<%@ page import="org.jpage.jbpm.util.*" %>
<%@ page import="org.jpage.jbpm.model.*" %>
<%
  Map userMap = (Map)request.getAttribute("userMap");
%>
<HTML>
<HEAD>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/pages/common/style.jsp"%>
<LINK href="<%=request.getContextPath()%>/workflow/styles/<c:out value="${frame_skin}"/>/main/main.css" type=text/css rel=stylesheet>
</HEAD>
<body>
<div align="center"><b>流程实例信息</b></div><br>

<table align="center" class="table-border" cellspacing="1" cellpadding="4" width="90%" nowrap>
  <tr class="beta">
    <td class="table-bar" width="12%" height="12" align="center"><b>流程名称</b></td>
    <td class="table-content" width="38%">
	<c:out value="${businessInstance.processName}"/>
	</td>
    <td class="table-bar" width="12%" height="12" align="center"><b>流程版本</b></td>
    <td class="table-content" width="38%">
	<c:out value="${businessInstance.processVersion}"/>
	</td>
  </tr>
  <tr class="beta">
	<td class="table-bar" width="12%" height="12" align="center"><b>启动者</td>
    <td class="table-content" width="38%">
	<c:out value="${businessInstance.requesterName}"/>[<c:out value="${businessInstance.requesterId}"/>]
	</td>
    <td class="table-bar" width="12%" height="12" align="center"><b>启动时间</b></td>
    <td class="table-content" width="38%">
	<fmt:formatDate value="${businessInstance.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
	</td>
  </tr>
  <tr class="beta">
	<td class="table-bar" width="12%" height="12" align="center"><b>主  题</b></td>
    <td class="table-content" width="38%">
	<c:out value="${businessInstance.title}"/>
	</td>
    <td class="table-bar" width="12%" height="12" align="center"><b>优先级</b></td>
    <td class="table-content" width="38%">
	<c:out value="${businessInstance.priority}"/>
	</td>
  </tr>
 </table>
<br>
<table align="center" class="table-border" cellspacing="1" cellpadding="4" width="90%" nowrap>
<tr class="table-bar">
    <td width="20%" height="12" align="center" class="table-title">任务名称</td>
    <td width="15%" height="12" align="center" class="table-title">处理者</td>
    <td width="15%" height="12" align="center" class="table-title">处理时间</td>
	<td width="10%" height="12" align="center" class="table-title">是否通过</td>
	<td width="40%" height="12" align="center" class="table-title">处理意见</td>
</tr>		
<%
    String taskInstanceId = null;
	String path = request.getContextPath()+"/workflow";
    pageContext.setAttribute("contextPath", request.getContextPath());
    List stateInstances = (List)request.getAttribute("rows");
	List dataInstances = (List)request.getAttribute("dataInstances");
    StringBuffer attachBuffer = new StringBuffer();

	if(stateInstances != null && stateInstances.size() > 0){
		Iterator iterator = stateInstances.iterator();
        while(iterator.hasNext()){
			StateInstance stateInstance = (StateInstance)iterator.next();
			org.jpage.actor.User user = (org.jpage.actor.User)userMap.get(stateInstance.getActorId());
			pageContext.setAttribute("stateInstance", stateInstance);
			taskInstanceId = stateInstance.getTaskInstanceId();
			attachBuffer.delete(0, attachBuffer.length());
			 if(dataInstances != null && dataInstances.size() > 0){
				Iterator iter = dataInstances.iterator();
				while(iter.hasNext()){
				  DataInstance file = (DataInstance)iter.next();
				  if("file".equals(file.getObjectId())){
				    if(file.getStateInstanceId().equals(stateInstance.getStateInstanceId())){
					  	String filename = org.jpage.util.RequestUtil.encodeURL(file.getObjectName());
					  	attachBuffer.append("<a href=\"")
							.append(request.getContextPath())
							.append("/DownloadService?actionType=VFS_DOWNLOAD&dataFile=")
							.append(org.jpage.util.RequestUtil.encodeURL(file.getDataFile()))
							.append("&filename=").append(filename)
							.append("&deviceId=").append(file.getDeviceId())
							.append("\" target=\"newFrame\">")
							.append("<iframe id=\"newFrame\" name=\"newFrame\" width=\"0\" height=\"0\"></iframe>")
							.append("( ").append(file.getObjectName()).append(" )</a>&nbsp;");
				    }
				  }
				 }
			  }
%>
<tr class="beta">
    <td class="table-content" width="20%" align="left">
	<c:out value="${stateInstance.taskName}"/>&nbsp;&nbsp;<c:out value="${stateInstance.taskDescription}"/>
    </td>
    <td class="table-content" width="15%" align="left"><%=user != null ? user.getName():""%>[<c:out value="${stateInstance.actorId}"/>]
    </td>
    <td class="table-content" width="15%" align="center">
	<fmt:formatDate value="${stateInstance.startDate}" pattern="yyyy-MM-dd HH:mm"/>
    </td>
	<td class="table-content" width="10%" align="center">
    <c:if test="${stateInstance.opinion == 1}">
	   是
    </c:if>
	<td class="table-content" width="40%" align="left">
    <c:if test="${not empty stateInstance.content}">
	   <c:out value="${stateInstance.content}"/>
    </c:if>
	</td>
</tr>
<%}%>
<%}%>
</table>

<br>

<%
	Collection taskItems = (Collection)request.getAttribute("taskItems");
	
    if(taskItems != null && taskItems.size() > 0){
%>
<table align="center" class="table-border" cellspacing="1" cellpadding="4" width="90%" nowrap>

<tr class="beta">
    <td class="table-bar" width="12%" height="16" align="center"><b>待办任务</b></td>
    <td class="table-content" width="88%" >
	    <%
	      Iterator iterator24 = taskItems.iterator();
          while(iterator24.hasNext()){
			TaskItem item = (TaskItem)iterator24.next();
			if(item.getActorId()!=null){
			  org.jpage.actor.User user = (org.jpage.actor.User)userMap.get(item.getActorId());
			  if(user != null){
				  out.println(user.getName()+" ["+user.getActorId()+"]&nbsp;&nbsp;");
			  }else{
                  out.println(item.getActorId()+"&nbsp;&nbsp;");
			  }
		    }
		  }
	     %>
    </td>
</tr>
<%}%>
</table>
<br><br>

<c:if test="${tokenInstanceId > 0 }">
    <%
	    Long tokenInstanceId = (Long)request.getAttribute("tokenInstanceId");
	%>
    <center>
    <jpage:processimageToken token="<%=tokenInstanceId.longValue()%>"/> 
	</center>
</c:if>