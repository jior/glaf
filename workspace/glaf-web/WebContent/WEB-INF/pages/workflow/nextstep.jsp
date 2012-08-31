<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>
<%@ page import="org.jpage.jbpm.util.*" %>
<%@ page import="org.jpage.jbpm.model.*" %>
<%
    String taskInstanceId = (String)request.getAttribute("taskInstanceId");
    List stateInstances = (List)request.getAttribute("stateInstances");
    List dataInstances = (List)request.getAttribute("dataInstances");
    StringBuffer attachBuffer = new StringBuffer();

	if(stateInstances != null && stateInstances.size() > 0){
		Iterator iterator = stateInstances.iterator();
        while(iterator.hasNext()){
			StateInstance stateInstance = (StateInstance)iterator.next();
			pageContext.setAttribute("stateInstance", stateInstance);
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
			if(taskInstanceId != null && taskInstanceId.equals(stateInstance.getTaskInstanceId())){
				pageContext.setAttribute("myStateInstance", stateInstance);
				request.setAttribute("__stateInstanceId__", stateInstance.getStateInstanceId());
				continue;
			}
%>
<script language="JavaScript">
  function chooseFile(url, x, y)
  {
	var topx=(screen.height-y)/2;
	var lefty=(screen.width-x)/2;
    var feature="resizable=1,left="+lefty+",top="+topx+",width="+x+",height="+y+",scrollbars=1";
    window.open(url,"",feature);
  }
</script>
<br><br>
<table align="center" class="table-border" cellspacing="1" cellpadding="4" width="98%" nowrap>
<tbody>
<tr>
  <td class="beta" align="center" colspan="10" valign="top">
  <b><%=stateInstance.getTaskName()%>&nbsp;
     <%=stateInstance.getTaskDescription() != null ? stateInstance.getTaskDescription() : ""%>
  </b>
  </td>
</tr>
</tbody>
<tr class="beta">
    <td class="table-title" width="18%" height="16" align="center">处理者</td>
    <td class="table-content" width="82%" ><c:out value="${stateInstance.operatorName}"/>[<c:out value="${stateInstance.operatorId}"/>]
    </td>
</tr>
<tr class="beta">
    <td class="table-title" width="18%" height="16" align="center">处理时间</td>
    <td class="table-content" width="82%" ><%=org.jpage.util.DateTools.getChinaDateTime(stateInstance.getStartDate())%>
    </td>
</tr>

<c:if test="${stateInstance.opinion >= 0}">
<tr class="beta">
	<td class="table-title" width="18%" height="16" align="center">是否同意</td>
	<td class="table-content" width="82%" >
	<c:choose>
	  <c:when test="${stateInstance.opinion == 1}">同意</c:when>
	  <c:otherwise>不同意</c:otherwise>
	</c:choose>
	</td>
</tr>
</c:if>

<c:if test="${not empty stateInstance.content}">
<tr class="beta">
	<td class="table-title" width="12%" height="16" align="center">处理意见</td>
	<td class="table-content" width="88%" >
	<c:out value="${stateInstance.content}"/>
	</td>
</tr>
</c:if>

<% if(attachBuffer.length() > 0){%>
<tr class="beta">
    <td class="table-title" width="12%" height="16" align="center">附&nbsp;&nbsp;件</td>
    <td class="table-content" width="88%" >
      <%=attachBuffer.toString()%>
    </td>
</tr>
<%}%>

</table>
<%
	   }
	}
%>

<script language="JavaScript">
  function chooseFile(url, x, y)
  {
	var topx=(screen.height-y)/2;
	var lefty=(screen.width-x)/2;
    var feature="resizable=1,left="+lefty+",top="+topx+",width="+x+",height="+y+",scrollbars=1";
    window.open(url,"",feature);
  }

</script>
<c:if test="${opinion_enabled == 1}">
<script language="JavaScript">
  function setControlField(isAgree){
      document.getElementById("<c:out value="${isAgreeControlField}"/>").value = isAgree;
  }
</script>
	<br><br>
	<table align="center" class="table-border" cellspacing="1" cellpadding="4" width="98%" nowrap>
	<tbody>
	<tr><td class="beta" align="center" colspan="10" valign="top"><b><c:out value="${task.name}"/>&nbsp;&nbsp;<c:out value="${task.description}"/></b></td></tr>
	</tbody>
	<tr class="beta">
		<td class="table-title" width="18%" height="16" align="center">是否同意</td>
		<td class="table-content" width="82%" >
		<input type="hidden" name="stateInstanceId" value="<c:out value="${__stateInstanceId__}"/>">
		<input type="hidden" id="<c:out value="${isAgreeControlField}"/>" name="<c:out value="${isAgreeControlField}"/>">
		<c:choose>
		  <c:when test="${myStateInstance.opinion == 0}">
		  <input type="radio" name="<%=Constant.IS_AGREE%>" value="1" onclick="setControlField('true')">同意&nbsp;&nbsp;
		   <input type="radio" name="<%=Constant.IS_AGREE%>" value="0" checked onclick="setControlField('false')">不同意
           <script> document.getElementById("<c:out value="${isAgreeControlField}"/>").value = "false"; </script>
		  </c:when>
		  <c:otherwise>
		   <input type="radio" name="<%=Constant.IS_AGREE%>" value="1" checked onclick="setControlField('true')">同意&nbsp;&nbsp;
		   <input type="radio" name="<%=Constant.IS_AGREE%>" value="0" onclick="setControlField('false')">不同意
		   <script> document.getElementById("<c:out value="${isAgreeControlField}"/>").value = "true"; </script>
		  </c:otherwise>
		</c:choose>
		</td>
	</tr>
	<tr class="beta">
	  <td class="table-title" width="18%" height="16" align="center">附件</td>
	  <td class="table-content" width="82%" >
	  <input type="button" class="functionButton" value="选择附件" onclick="javascript:chooseFile('<%=request.getContextPath()%>/AttachmentService?actionType=DISPLAY_FORM&service_key=<%=Constant.SESSION_PROCESS_ATTACHMENT_KEY%>&fileType=9999',400,300);">
	</td>
    </tr>
	<tr class="beta">
		<td class="table-title" width="18%" height="16" align="center">处理意见</td>
		<td class="table-content" width="82%" >
		  <textarea name="<%=Constant.PROCESS_OPINION%>" rows="5" cols="66">
		  <c:out value="${myStateInstance.content}"/></textarea>
		</td>
	</tr>
</table>
</c:if>

<script language=javascript>
     function submitForm(actionType, nextStepId){
         document.getElementById("actionType").value = actionType;
         document.getElementById("<%=Constant.NEXT_STEP_ID%>").value = nextStepId;
         document.iForm.submit();
    }
 </script>
<br>
<table cellspacing="0" cellpadding="0" border="0">
<tr>
<td valign="top" width="45%">
<input type="hidden" id="actionType"  name="actionType" >
<input type="hidden" id="businessKey"  name="businessKey"   value="<c:out value="${businessKey}"/>" >
<input type="hidden" id="businessValue"  name="businessValue" value="<c:out value="${businessValue}"/>" >
<input type="hidden" id="<c:out value="${businessKey}"/>"   name="<c:out value="${businessKey}"/>" value="<c:out value="${businessValue}"/>" >

<input type="hidden" id="<%=Constant.NEXT_STEP_ID%>" name="<%=Constant.NEXT_STEP_ID%>">
<input type="hidden" name="taskInstanceId" value="<c:out value="${taskBean.taskInstanceId}"/>">
<c:choose>
    <c:when test="${!empty taskBean.availableTransitions}">
      下一步任务 
      <c:forEach var="availableTransition" items="${taskBean.availableTransitions}">
        <c:set var="availableTransition" scope="request" value="${availableTransition}"/>  
		<input  type="button"   name="button" class="button"  value="<c:out value="${availableTransition.name}" />"  onclick="javascript:submitForm('<%=ProcessActionType.COMPLETE_TASK%>','<c:out value="${availableTransition.name}" />');"/> 
      </c:forEach>
    </c:when>
    <c:otherwise>
      <input  type="button" name="button" class="button"  value="完成任务" onclick="javascript:submitForm('<%=ProcessActionType.COMPLETE_TASK%>','<%=Constant.SAVE_AND_CLOSE_TASK%>');" /> 
    </c:otherwise>
  </c:choose>
  <input  type="button" name="button"  class="button" value="保存数据" onclick="javascript:submitForm('<%=ProcessActionType.SAVA_DATA%>','');" /> 
  <input  type="button" name="cacel" class="button" value="取消"  onclick="javascript:history.back(0);"/> 
</form>
</td>
</tr>
</table>
