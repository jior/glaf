<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<c:out value="${businessInstance.title}"/>&nbsp;&nbsp;
	</td>
    <td class="table-bar" width="12%" height="12" align="center"><b>单据编号</b></td>
    <td class="table-content" width="38%">
	<c:out value="${businessInstance.businessValue}"/>
	</td>
  </tr>
 </table>
<br>
<table align="center" class="table-border" cellspacing="1" cellpadding="4" width="90%" nowrap>
<tr class="table-bar">
    <td height="12" align="center" class="table-title">任务名称</td>
    <td height="12" align="center" class="table-title">处理者</td>
    <td height="12" align="center" class="table-title">处理时间</td>
	<td height="12" align="center" class="table-title">是否通过</td>
	<td height="12" align="center" class="table-title">处理意见</td>
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
			if(user == null){
				user = (org.jpage.actor.User)userMap.get(stateInstance.getActorId().trim());
			}
			org.jpage.actor.User agent = null;
			if(stateInstance.getAgentId() != null){
                agent = (org.jpage.actor.User)userMap.get(stateInstance.getAgentId());
				if(agent == null){
					    agent = (org.jpage.actor.User)userMap.get(stateInstance.getAgentId().trim());
				}
			}
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

       String headship = "";
	   if(user != null){
			Map properties = user.getProperties();
			if(properties != null && properties.get("headship") != null){
				 headship =  properties.get("headship").toString();
			}
	   }
%>
<tr class="beta">
    <td class="table-content" align="left">
	<c:out value="${stateInstance.taskName}"/>&nbsp;&nbsp;<c:out value="${stateInstance.taskDescription}"/>
    </td>
    <td class="table-content" align="left"><%=user != null ? user.getName():""%> [<c:out value="${stateInstance.actorId}"/>] 
	 <%if(agent != null){
          out.println("&nbsp;<strong><font color='red'>代理"+agent.getName()+"["+agent.getActorId()+"]执行该任务</font></strong>");
	  }%>
    </td>
    <td class="table-content" align="center">
	<fmt:formatDate value="${stateInstance.startDate}" pattern="yyyy-MM-dd HH:mm"/>
    </td>
	<td class="table-content" align="center">
    <c:if test="${stateInstance.opinion == 1}">
	   是
    </c:if>
	<c:if test="${stateInstance.opinion == 0}">
	   否
    </c:if>
	<td class="table-content" align="left">
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
	    <%
	      Iterator iterator24 = taskItems.iterator();
          while(iterator24.hasNext()){
			TaskItem item = (TaskItem)iterator24.next();
			if(item.getActorId()!=null){
%>
<tr class="beta">
    <td class="table-bar" width="12%" height="16" align="center"><b>待办任务</b></td>
    <td class="table-content" width="88%" >
<%
	org.jpage.actor.User user = (org.jpage.actor.User)userMap.get(item.getActorId());
	if(user == null){
		user = (org.jpage.actor.User)userMap.get(item.getActorId().trim());
	}
	if(item.getTaskCreateDate() != null){
        out.println(org.jpage.util.DateTools.getDateTime(item.getTaskCreateDate()));
	}
	if(item.getTaskDescription() != null){
		out.println("【"+item.getTaskName() + "&nbsp;" + item.getTaskDescription()+"】");
	}else{
        out.println("【"+item.getTaskName() + "&nbsp;");
	}

	out.println("&nbsp;&nbsp;任务实例编号<font color=\"#0066FF\"><b>");
    out.println(item.getTaskInstanceId());
	out.println("</b></font>");

	out.println("&nbsp;&nbsp;<font color=\"#FF6600\"><b>");
	if(user != null){
		Map properties = user.getProperties();
		String headship = "";
        if(properties != null && properties.get("headship") != null){
			 headship =  properties.get("headship").toString();
		}
		out.println(user.getName()+" ["+user.getActorId()+"] " );
	}else{
        out.println(item.getActorId());
	}
	out.println("</b></font>");

  }
%>
    </td>
</tr>
<%
	 }
}%>
</table>
<br><br>