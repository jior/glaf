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

  	<h5>A sample PGM for start a flow</h5>

	<html:form method="POST" action="startFlow.do">
		<input type="hidden" value="runPageLoad" name="actionMethodId">
	
		<div
			style="padding: 5px 0px 5px 5px; background-color: white; border; border-width: 1px; border-style: solid; border-color: #87C897;">
		<table>
			<tr>
			<td>申请单号<br></td>
			<td><html:text maxlength="9" property="fstuffapplyno"/></td>
			<td>变更原因<br></td>
			<td><html:text maxlength="200" property="fchangeereason"/></td>
			<td>安全类型<br></td>
			<td><html:text maxlength="3" property="fsafetypeid"/></td>
			<td>使用类型<br></td>
			<td><html:text maxlength="3" property="fapplytypeid"/></td>
		   </tr>
		</table>
	    </div>
		
		<input type="button" value="save" name="save" onclick="doSave()">
	</html:form>
	
<script type="text/javascript" language="Javascript">
	function doSave(){
	    objFrm = document.forms[0];
	    clearErrorColor();
	    objFrm.actionMethodId.value = "runPageSave";
		submitForm(objFrm,"<bean:message key='baseSample.doubleSubmit'/>");
		}
</script>

</body>
<html:javascript formName="startFlowForm"/>
<jsp:include page="//sys/sysJsp/common/showAllTypeMessage.jsp" flush="true"/>
</html>
