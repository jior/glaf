<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="/sys/sysTld/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/sys/sysTld/struts-bean.tld" %>
<%@ taglib prefix="logic" uri="/sys/sysTld/struts-logic.tld" %>
<%@ taglib prefix="pri" uri="/sys/sysTld/privilegeTag.tld" %>

<html>
  <head>
    <title>我的测试浏览</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<LINK rel=stylesheet type=text/css href="<%=request.getContextPath()%>/sys/sysCss/menu/css.css">
	<LINK rel=stylesheet type=text/css href="<%=request.getContextPath()%>/sys/sysCss/menu/style-sys.css">
	<LINK rel=stylesheet type=text/css href="<%=request.getContextPath()%>/sys/sysCss/menu/style-custom.css">
	<link rel="stylesheet" type="text/css" href="css/baseStyle.css">  
  </head>
   <script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/frameWorkUtility.js"></script>
  <body>
    This is my JSP page. <br>
    <html:form action="csvTest.do" method="post">
    	<input type="hidden" value="csvDo" name="actionMethodId">
    	<div>
    		<table>
			<tr>
			<td>变量A<br></td>
			<td><html:text maxlength="9" property="xmA"/></td>
			<td>非空检查</td>
		   </tr>
		   <tr>
		   <td>变量B<br></td>
			<td><html:text maxlength="19" property="xmB"/></td>
		   <td>非空检查，最小长度检查</td>
		   </tr>
		</table>
    	</div>
    	<input type="submit" value="save" name="save" onclick="doSave()"/>
    </html:form>
<script type="text/javascript" language="Javascript">
	function doSave(){
		objFrm = document.forms[0];
		clearErrorColor();
		if (validateLoginForm(objFrm)){
			submitForm(objFrm,"<bean:message key='baseSample.doubleSubmit'/>");
	    }
		}
</script>

</body>
<html:javascript formName="csvTestForm"/>
<jsp:include page="/sys/sysJsp/common/showAllTypeMessage.jsp" flush="true"/>  
</html>
