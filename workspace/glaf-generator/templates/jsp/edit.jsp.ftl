<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<title>${title?if_exists}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link type="text/css"
	href="<%=request.getContextPath()%>/scripts/calendar/skins/aqua/theme.css"
	rel="stylesheet" />
<script src="<%=request.getContextPath()%>/scripts/calendar/calendar.js"
	language="javascript"></script>
<script
	src="<%=request.getContextPath()%>/scripts/calendar/lang/calendar-zh.js"
	language="javascript"></script>
<script
	src="<%=request.getContextPath()%>/scripts/calendar/calendar-setup.js"
	language="javascript"></script>
<script src="<%=request.getContextPath()%>/scripts/jquery/jquery.min.js"
	language="javascript"></script>
 

<script language="javascript">
    var contextPath = "<%=request.getContextPath()%>";

    function ajaxRequestXY(elementId, elementValue, queryId, objectId, paramValue){
          var req = AjaxRequest();
          handleEmpty(elementId);
		  var handlerFunction = getReadyStateHandler(req, chooseXY, elementId, elementValue);
          req.onreadystatechange = handlerFunction;
          req.open("POST", "<%=request.getContextPath()%>/QueryService", true);
          req.setRequestHeader("Content-Type", 
                       "application/x-www-form-urlencoded");
		  if(paramValue == ""){
		     
		  }
          req.send("queryId="+queryId+"&"+objectId+"="+paramValue);
    }

	 function submitRequestXY(form){  
		form.status.value=1;
		form.action="<%=request.getContextPath()%>/apps/${modelName}.do";
		if(verifyAllElements(form)){
			 if(confirm("${res_submit_confirm?if_exists}")){
			    form.submit();
			 }
	   }
	 }

	function startProcessXY(form){  
		form.status.value=1;
		form.action="<%=request.getContextPath()%>/apps/${modelName}.do";
		if(verifyAllElements(form)){
			 if(confirm("${res_submit_confirm?if_exists}")){
			    form.submit();
			 }
	  }
	}

	function completeTaskXY(form, x){  
		form.status.value=1;
		form.route.value=x;
		form.isAgree.value=x;
		form.action="<%=request.getContextPath()%>/apps/${modelName}.do";
		if(verifyAllElements(form)){
			 if(confirm("${res_submit_confirm?if_exists}")){
			    form.submit();
			 }
	  }
	}

  function closeXY(){
     if(window.opener != null){
		 window.close();
	 } else {
        history.back();
	 }
  }

</script>
</head>
<body id="x-form-body" leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">
<center>
<br>
<form id="${modelName}" name="${modelName}" class="x-form" method="post"
	action="<%=request.getContextPath()%>/apps/${modelName}.do">
<input type="hidden" id="x_json" name="x_json" value="<c:out value="#F{x_json}" escapeXml="true"/>">
<input type="hidden" id="status" name="status" value="0">
<input type="hidden" id="method" name="method" value="x"> 
<input type="hidden" id="${idField.name}" name="${idField.name}" value="<c:out value="#F{${idField.name}}"/>">
<input type="hidden" id="gridType" name="gridType" value="<c:out value="#F{gridType}"/>">
<input type="hidden" id="taskInstanceId" name="taskInstanceId" value="<c:out value="#F{taskItem.taskInstanceId}"/>">
<c:if	test="#F{not empty ${modelName}.formName}">
<input type="hidden" id="formName" name="formName" value="<c:out value="#F{${modelName}.formName}"/>">
</c:if> 

<div class="content-block"  style=" width: 90%;" >
<br>
<div class="x_content_title">
<img
	src="<%=request.getContextPath()%>/pages/images/window.png"
	alt="${title?if_exists}">&nbsp;  ${title?if_exists} 
</div>

<fieldset class="x-fieldset" style=" width: 95%;">
<legend>${res_basic_info?if_exists}</legend>
 
 <table width="95%" border="0" cellspacing="1" cellpadding="1" align="center" >
   <tr>
    <td style="padding-top:20px; padding-left:20px; ">
		 ${x_form_content?if_exists}
    </td>
   </tr>
 </table>

</fieldset>

<div id="x_form_files">
<fieldset class="x-fieldset" style=" width: 95%;"><legend> <img
	src="<%=request.getContextPath()%>/pages/images/window.png"
	alt="${res_files?if_exists}">&nbsp; <font
	style="font-size: 15px;">${res_files?if_exists}</font> </legend>
<div id="x_files"></div>
<script language="javascript">
	$('#x_files').load('<%=request.getContextPath()%>/dataFile.do?method=dataFiles&serviceKey=${modelName}&resourceId=<c:out value="#F{requestQuery.resourceId}"/>&saveOrUpdate=<c:if test="#F{x_method eq 'save' or x_method eq 'update' or x_method eq 'saveOrUpdate'}">true</c:if>');
</script>
<br />
</fieldset>
</div>

<c:if test="#F{x_method eq 'submit' and canSubmit}">
	<div id="x-opinion-bar" align="center">
	<fieldset class="x-fieldset" style=" width: 95%;"><legend> <img
		src="<%=request.getContextPath()%>/pages/images/window.png"
		alt="${res_audit_info?if_exists}">&nbsp; <font
		style="font-size: 15px;">${res_audit_info?if_exists}</font> </legend>
	<div id="x_opinion_files" align="left"></div>
	<br>
	<script language="javascript">
		var link =  '<%=request.getContextPath()%>/dataFile.do?method=dataFiles&serviceKey=${modelName}&saveOrUpdate=true&objectId=taskInstanceId&objectValue=<c:out value="#F{taskItem.taskInstanceId}"/>';
		 $('#x_opinion_files').load(link);
	</script>
	<div id="x-opinion" align="left"><textarea id="opinion"
		name="opinion" displayName="${res_audit_info?if_exists}"
		style="height: 150px; width: 80%; text-align: left;"
		class="x-form-text"></textarea></div>
	</fieldset>
	</div>
</c:if>

<div id="x-toolbar" align="center"><br />
<br />
<c:choose>
	<c:when test="#F{x_method eq 'save' or x_method eq 'update' or x_method eq 'saveOrUpdate'}">
		<script language="javascript"> 
			document.getElementById("method").value="${persistType?if_exists}";
		</script>
		<c:choose>
			<c:when test="#F{!empty ${modelName} and canUpdate }">
				<input type="button" name="button" value="${res_save?if_exists}"
					class="button" onclick="javascript:submitRequestXY(this.form);">
				<script language="javascript"> 
						document.getElementById("method").value="update";
				</script>
			</c:when>
			<c:when test="#F{empty ${modelName}}">
				<input type="button" name="button" value="${res_save?if_exists}"
					class="button" onclick="javascript:submitRequestXY(this.form);">
			</c:when>
		</c:choose>
	</c:when>
	<c:when test="#F{x_method eq 'submit' and canSubmit}">
		<c:choose>
			<c:when test="#F{  ${modelName}.wfStatus == 0}">
				<input type="button" name="button" value="${res_submit?if_exists}"
					class="button" onclick="javascript:startProcessXY(this.form);">
				<script language="javascript"> 
						document.getElementById("method").value="startProcess";
				</script>
			</c:when>
			<c:when
				test="#F{(!empty ${modelName}.processInstanceId) and (${modelName}.status == -1) }">
				<input type="hidden" id="isAgree" name="isAgree" value="x">
				<input type="hidden" id="route" name="route" value="">
				<input type="button" name="button" value="${res_resubmit?if_exists}"
					class="button"
					onclick="javascript:completeTaskXY(this.form,'true');">
				<script language="javascript"> 
						document.getElementById("method").value="completeTask";
				</script>
			</c:when>
			<c:when
				test="#F{(!empty ${modelName}.processInstanceId) and (${modelName}.wfStatus  != 9999) }">
				<input type="hidden" id="isAgree" name="isAgree" value="x">
				<input type="hidden" id="route" name="route" value="">
				<input type="button" name="button"
					value="${res_submit_yes?if_exists}" class="button"
					onclick="javascript:completeTaskXY(this.form,'true');">
				<input type="button" name="button"
					value="${res_submit_no?if_exists}" class="button"
					onclick="javascript:completeTaskXY(this.form,'false');">
				<script language="javascript"> 
						document.getElementById("method").value="completeTask";
				</script>
			</c:when>
		</c:choose>
	</c:when>
	<c:when test="#F{x_method eq 'query' }">
		<input type="submit" name="submit" value="${res_search?if_exists}"
			class="button">
		<script language="javascript"> 
						document.getElementById("method").value="list";
		</script>
	</c:when>
</c:choose> 
<c:choose>
	<c:when test="#F{x_method eq 'view' }">
		<input type="button" name="button" value="${res_closeWin?if_exists}"
			class="button" onclick="javacsript:closeXY();">
	</c:when>
	<c:otherwise>
		<input type="button" name="button"
			value="${res_button_back?if_exists}" class="button"
			onclick="javacsript:closeXY();">
	</c:otherwise>
</c:choose> 
<br />
</div>

</div>

</form>

</center>
<br />
<br />
<%@ include file="/pages/tm/footer.jsp"%>
</body>
</html>