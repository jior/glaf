<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="/sys/sysTld/struts-html.tld"%>
<%@ taglib prefix="bean" uri="/sys/sysTld/struts-bean.tld"%>
<%@ taglib prefix="logic" uri="/sys/sysTld/struts-logic.tld"%>
<%@ taglib uri="/sys/sysTld/struts-nested.tld" prefix="nested"%>
<html>
	<head>
		<title>输入输出文件列表</title>
		
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/sysCss/menu/css.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/sysCss/menu/style-custom.css">
	</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/frameWorkUtility.js"></script>	
	<body bgcolor="#f5f9ed" style="border:0px; margin:0px 0px 0px 0px">
		<h1>输入输出文件列表
		</h1>
		<html:form method="POST" action="showIOFile.do" styleId="form_search_local">
			<input type="hidden" value="runPageCtrl" name="actionMethodId">
			<div style="width: 614px;overflow-x:scroll;">
			<table class=EOS_table border=0 width="100%" align=center id="iscTab">
				<tr>
					<th width="40" nowrap class="T2">NO</th>
					<th width="60" nowrap class="T2">类型</th>
					<th width="180" nowrap class="T2">文件</th>
				</tr>
				<nested:iterate id="details" property="showIOFileDetail" >
				    <tr>
					  <td><bean:write name="details" property="no"/></td>
					  <td><bean:write name="details" property="fileType"/></td>
					  <td><a href="<%=request.getContextPath()%>/sys/sysJsp/batch/download.jsp?filepath=<bean:write name="details" property="filePath"/>&file=<bean:write name="details" property="fileName"/>"><bean:write name="details" property="fileName"/></a></td>
				    </tr>
				</nested:iterate>
			</table>
			</div>
		</html:form>
	</body>
<jsp:include page="/sys/sysJsp/common/showAllTypeMessage.jsp" flush="true"/>
</html>
