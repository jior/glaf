<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<title>jbpm</title>
<meta http-equiv="Content-Type" content="UTF-8">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/styles/styles.css">
<script src="<%=request.getContextPath()%>/scripts/jquery.min.js"
	language="javascript"></script>
<script
	src="<%=request.getContextPath()%>/scripts/jquery.form.js"
	language="javascript"></script>
<script language="javascript">
  var contextPath = "<%=request.getContextPath()%>";

 $(document).ready(function() {
    var options = {
	    dataType:  'json',
        beforeSubmit:  showMyRequest,
        success:  showMyResponse
    };

  jQuery("#iForm").submit(function() {
         $(this).ajaxSubmit(options);
         return false;
    });
});

function showMyRequest(formData, jqForm, options) {
   if(verifyAllElements(document.iForm)){
        if(confirm('确定提交吗？')){
            return true;
	    }
	}
	return false;
}

function showMyResponse(data)  {
       alert(data.message);
	   if(data.statusCode == 200){
		 
	   } else if(data.statusCode == 500){

	   }
}
 

function completeJbpmTask(form, x){
    form.status.value=1;
	form.route.value=x;
	form.isAgree.value=x;
	if(x == "false"){
		if(document.getElementById("opinion") != null){
			if(document.getElementById("opinion").value==""){
                alert("审核不通过请填写原因！");
				document.getElementById("opinion") .focus();
				return;
			}
		 }
	 }
	if(confirm("确认提交审核吗？")){
          form.submit();
	}
}

function completeJbpmTask2(form, x){
    form.status.value=1;
	form.route.value="";
	form.isAgree.value="N/A";
	form.transitionName.value=x;
	if(x == "false"){
		if(document.getElementById("opinion") != null){
			 if(document.getElementById("opinion").value==""){
                alert("审核不通过请填写原因！");
				document.getElementById("opinion") .focus();
				return;
			}
		 }
	}
	if(confirm("确认提交审核吗？")){
         form.submit();
	 }
  }

function completeJbpmTask3(form, x){
    form.status.value=1;
	form.route.value="";
	form.isAgree.value="N/A";
	form.jumpToNode.value=x;
	if(x == "false"){
		if(document.getElementById("opinion") != null){
			if(document.getElementById("opinion").value==""){
                alert("审核不通过请填写原因！");
				document.getElementById("opinion") .focus();
				return;
				}
			}
	 }
	if(confirm("确认提交审核吗？")){
         form.submit();
     }
  }
</script>
</head>
<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">
<center>
<form id="iForm" name="iForm" method="post" class="x-form"
	action="<%=request.getContextPath()%>/mx/jbpm/complete"><input
	type="hidden" id="status" name="status" value="0"> <input
	type="hidden" id="json" name="json" value="${json}">
<input type="hidden" id="rowId" name="rowId"
	value="${rowId}"> <input type="hidden"
	id="contextPath" name="contextPath"
	value="${contextPath}"> <input type="hidden"
	id="taskName" name="taskName"
	value="${taskItem.taskName}"> <input
	type="hidden" id="taskInstanceId" name="taskInstanceId"
	value="${taskItem.taskInstanceId}"> <input
	type="hidden" id="processName" name="processName"
	value="${taskItem.processName}"> <input
	type="hidden" id="processInstanceId" name="processInstanceId"
	value="${taskItem.processInstanceId}"> <input
	type="hidden" id="responseDataType" name="responseDataType"
	value="<c:out value="$responseDataType}"> <br>
<div class="content-block" style="width: 92%"><br>
<c:if test="${not empty stepInstances}">
	<div id="x-history-bar" class="x-hide-display" align="center">
	<div class="x_content_title"><img
		src="<%=request.getContextPath()%>/images/window.png" alt="审核信息">&nbsp;审核信息
	</div>
	<br>
	<table border=0 cellspacing=0 cellpadding=2 width="90%">
		<tbody>
			<tr>
				<td colspan="10">
				<table align="center" width="100%" border="0" cellspacing="0"
					cellpadding="0">
					<c:forEach items="${stepInstances}" var="stepInstance">
						<tr>
							<td align="left" width="25%">&nbsp;<img
								src="<%=request.getContextPath()%>/images/config.gif"
								border="0"> &nbsp;<span class="x-topic"> <c:out
								value="${stepInstance.taskDescription} [<c:out
								value="${stepInstance.taskName}]</span></td>
							<td align="left" width="25%" height="22">&nbsp;<c:out
								value="${stepInstance.actorName} &nbsp; <c:out
								value="${stepInstance.actorId}</td>
							<td align="left" width="25%">&nbsp;<fmt:formatDate
								value="${stepInstance.date}" pattern="yyyy-MM-dd HH:mm" /></td>
							<td align="left" width="25%">&nbsp; <c:choose>
								<c:when test="${ stepInstance.isAgree eq 'true' }">
									<span style='color: green; font-weight: bold;'> 通过 </span>
								</c:when>
								<c:when test="${ stepInstance.isAgree eq 'false' }">
									<span style='color: red; font-weight: bold;'> 不通过 </span>
								</c:when>
								<c:otherwise>
									${stepInstance.isAgree}
								</c:otherwise>
							</c:choose></td>
						</tr>

						<c:if test="${  not empty stepInstance.content}">
							<tr>
								<td align="left" colspan="4" width="100%" height="22">
								&nbsp;审核意见：&nbsp;${stepInstance.content}"
									escapeXml="false" /></td>
							</tr>
						</c:if>

						<tr>
							<td align="left" colspan="4" width="100%" height="22">
							<div class="x_underline">
							</td>
						</tr>
					</c:forEach>
				</table>
				</td>
			</tr>
		</tbody>
	</table>
	</div>
	<br>
</c:if>

<table width="95%" border="0" cellspacing="0" cellpadding="2">
	<tbody>
		<tr>
			<td style="padding-left: 60px;" valign="middle" align="center">
			<div class="content-block-header">审核意见</div>
			<div id="x-opinion" align="left" style="padding-left: 0px;"><br>
			<textarea id="opinion" name="opinion" rows="10" cols="60"
				class="x-textarea" displayName="审核意见"
				style="width: 80%; text-align: left;"></textarea></div>
			</td>
		</tr>
		<tr>
			<td style="padding-left: 60px;" colspan="2"><c:if
				test="${ auditUploadFlag == 'Y'}">
				<div id="x_opinion_files" align="left" style="padding-left: 0px;"></div>
				<br>
				<script language="javascript">
				var link =  '<%=request.getContextPath()%>/dataFile?method=dataFiles&saveOrUpdate=true&objectId=taskInstanceId&objectValue=${taskItem.taskInstanceId}';
				$('#x_opinion_files').load(link);
			</script>
			</c:if></td>
		</tr>
	</tbody>
</table>

<div id="x-toolbar" align="center" style="width: 90%;"><br>
<input type="hidden" id="isAgree" name="isAgree" value="x"> <input
	type="hidden" id="route" name="route" value=""> <c:if
	test="${ not empty transitionNames}">
	<input type="hidden" id="transitionName" name="transitionName" value="">
	<c:forEach items="${transitionNames}" var="x_transition">
		<input type="button" name="button"
			value="${x_transition}" class="btn"
			onclick="javascript:completeJbpmTask2(this.form,'${x_transition}');">
	</c:forEach>
</c:if> <c:if test="${ not empty nodeNames}">
	<input type="hidden" id="jumpToNode" name="jumpToNode" value="">
	<c:forEach items="${nodeNames}" var="x_node">
		<input type="button" name="button" value="${x_node}"
			class="btn"
			onclick="javascript:completeJbpmTask3(this.form,'${x_node}');">
	</c:forEach>
	<br>
	<br>
</c:if> <input type="button" name="buttonY" value="审核通过" class="btn"
	onclick="javascript:completeJbpmTask(this.form,'true');"> <input
	type="button" name="buttonX" value="审核不通过" class="btn"
	onclick="javascript:completeJbpmTask(this.form,'false');"></div>
</div>
</form>
</center>
<br>
</body>
</html>