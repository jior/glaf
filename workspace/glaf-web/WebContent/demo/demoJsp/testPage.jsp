<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="/sys/sysTld/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/sys/sysTld/struts-bean.tld" %>
<%@ taglib uri="/sys/sysTld/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/sys/sysTld/struts-logic.tld" prefix="logic"%>

<html>
  <head>
    <title>iscSample</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/baseStyle.css">  
  </head>	
  
  <script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/frameWorkUtility.js"></script>
  <body> 

  	<h5>A sample PGM for WorkFlow</h5>

	<html:form method="POST" action="testPage.do">
	<input type="hidden" value="runPageLoad" name="actionMethodId">

	<div
		style="padding: 5px 0px 5px 5px; background-color: white; border; border-width: 1px; border-style: solid; border-color: #87C897;">
		<table id="iscTab" style="font: normal 12px/ 25px simsun;">
			<tr>
				<td>流程实例ID</td>
				<td>任务实例ID</td>
				<td>任务ID</td>
				<td>任务名</td>
				<td>用户ID</td>
				<td>用户名</td>
				<td>用户Email</td>
				<td>当前任务状态</td>
			</tr>
			<nested:iterate id="details" property="wfTestDetails" >
			<tr>
				<td><nested:text size="7" readonly="true" property="processInstanceId"/></td>
				<td><nested:text size="7"  readonly="true" property="taskInstanceId"/></td>
				<td><nested:text size="7"  readonly="true" property="taskId"/></td>
				<td><nested:text size="7"  readonly="true" property="taskName"/></td>
				<td><nested:text size="7"  readonly="true" property="userId"/></td>
				<td><nested:text size="7"  readonly="true" property="userName"/></td>
				<td><nested:text size="7"  readonly="true" property="userEmail"/></td>
				<td><nested:text size="7"  readonly="true" property="currentTaskStatusValue"/></td>
				<td><nested:link href="testPage.do?actionMethodId=runWFSave" paramId="taskInstanceId" paramName="details" paramProperty="taskInstanceId">
				<bean:write name="details" property="taskInstanceId" /></nested:link></td>
			</tr>
			</nested:iterate>
 		</table>
	</div>
	
	<jsp:include page="/sys/sysJsp/common/pageController.jsp" flush="true"/>
	<input type="button" value="search" name="search" onclick="doSearch()">
	
	
	</html:form>
<script type="text/javascript" language="Javascript">
	function doSearch(){
		doPageStart();
		}
</script>

</body>
<html:javascript formName="wfTestForm"/>
<jsp:include page="/sys/sysJsp/common/showAllTypeMessage.jsp" flush="true"/>
</html>
