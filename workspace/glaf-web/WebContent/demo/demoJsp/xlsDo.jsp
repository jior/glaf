<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="/sys/sysTld/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/sys/sysTld/struts-bean.tld" %>
<%@ taglib uri="/sys/sysTld/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/sys/sysTld/struts-logic.tld" prefix="logic"%>
<html>

  <head>
    
  </head>
  <script language="javascript" type="text/javascript" src="js/calendar/WdatePicker.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/frameWorkUtility.js"></script>
  <body> 
  <html:form method="POST" action="xlsDo.do">
    <html:text property="fileName"/>
  	
    <input type="button" name="DaoRu" value="导入" onclick="doDaoRu()">
    <input type="button" name="DaoChu" value="导出" onclick="doDaoChu()">
    <input type="button" name="PDFSC" value="PDF生成" onclick="doPDF()">
  	<input type="hidden" value="" name="actionMethodId">
  	</html:form>
  	<script type="text/javascript" language="Javascript">    
	function doDaoRu(){
	    objFrm = document.forms[0];
	    objFrm.actionMethodId.value = "DaoRuXls";
	    objFrm.submit();
	}
	function doDaoChu(){
	    objFrm = document.forms[0];
	    objFrm.actionMethodId.value = "DaoChuXls";
	    objFrm.submit();
	}
	function doPDF(){
	    objFrm = document.forms[0];
	    objFrm.actionMethodId.value = "PDFSC";
	    objFrm.submit();
	}
</script>
</body>
<jsp:include page="/sys/sysJsp/common/showAllTypeMessage.jsp" flush="true"/>
</html>