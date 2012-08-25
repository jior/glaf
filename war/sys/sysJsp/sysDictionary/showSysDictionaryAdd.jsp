<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="/sys/sysTld/struts-html.tld"%>
<%@ taglib prefix="bean" uri="/sys/sysTld/struts-bean.tld"%>
<%@ taglib prefix="logic" uri="/sys/sysTld/struts-logic.tld"%>
<%@ taglib uri="/sys/sysTld/struts-nested.tld" prefix="nested"%>
<html>
	<head>
		<title><bean:message key="SYSDICTIONARYADD.TITLE" /></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/sysCss/menu/css.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/sysCss/menu/style-custom.css">
	</head>
	<script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/frameWorkUtility.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/calendar/WdatePicker.js"></script>
	<body bgcolor="#f5f9ed" style="border:0px; margin:0px 0px 0px 0px">
		<html:form method="POST" action="showSysDictionaryAdd.do"  styleId="form_search_local">
		<input type="hidden" value="runPageCtrl" name="actionMethodId">
		<html:hidden property="flag"/>
			<table id="myTalbe" width="350px" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="right" height="35" nowrap width="100px">
							<bean:message key="SYSDICTIONARY.BIGTYPE" />:&nbsp;&nbsp;
					</td>
					<td align="left" nowrap width="250px" >
						<nested:text property="f_BigType" maxlength="10" />
					</td>
					
				</tr>
				<tr>
					<td align="right" height="35" nowrap  width="100px">
							<bean:message key="SYSDICTIONARY.SMALLTYPE" />:&nbsp;&nbsp;
					</td>
					<td align="left" nowrap>
						<nested:text property="f_SmallType" maxlength="10"/>
					</td>
				</tr>
				<tr>
					<td align="right" height="35" nowrap width="100px">
							<bean:message key="SYSDICTIONARY.CONS" />:&nbsp;&nbsp;
					</td>
					<td align="left" nowrap>
						<html:text property="f_Const" size="60" maxlength="50"/>
					</td>
					
				</tr>
				<tr>
					<td align="right" height="35" nowrap width="100px">
							<bean:message key="SYSDICTIONARY.REMARK" />:&nbsp;&nbsp;
					</td>
					<td align="left" nowrap>
						<html:text property="f_Remark" size="60" maxlength="200"/>
					</td>
				</tr>
				<tr>
					<td  height="35" colspan="2" align="right">
						&nbsp;&nbsp;<input type="button" value="<bean:message key="SYSDICTIONARY.SAVE" />" name="save" onclick="doSave()"
						style="background:url('<%=request.getContextPath()%>/sys/sysImages/btn_01.gif');border:0;height:25px;width:82px">
						&nbsp;&nbsp;<input type="button" value="<bean:message key="SYSDICTIONARY.RESET" />" name="reset" onclick="doReSet()"
						style="background:url('<%=request.getContextPath()%>/sys/sysImages/btn_01.gif');border:0;height:25px;width:82px">
						&nbsp;&nbsp;<input type="button" value="<bean:message key="SYSDICTIONARY.BACK" />" name="reset" onclick="doBack()"
						style="background:url('<%=request.getContextPath()%>/sys/sysImages/btn_01.gif');border:0;height:25px;width:82px">
					</td>
				</tr>
			</table>
		</html:form>
		<jsp:include page="/sys/sysJsp/common/showAllTypeMessage.jsp" flush="true"/>
	</body>
</html>
<script type="text/javascript" language="Javascript">
		function doSave(){
		    objFrm = document.forms[0];
		    clearErrorColor();
		    objFrm.actionMethodId.value = "runPageSave";
				submitForm(objFrm,"<bean:message key='baseSample.doubleSubmit'/>");
			}

		function doReSet(){
			var objFrm = document.forms[0];
			if(objFrm.flag.value=='add'){
				objFrm.f_BigType.value="";
				objFrm.f_SmallType.value="";
				objFrm.f_Const.value="";
				objFrm.f_Remark.value="";
			}else{
				objFrm.f_Const.value="";
				objFrm.f_Remark.value="";
			}
			
		}
		function doBack(){
			window.close();
		}
		function onLoad(){
			var objFrm = document.forms[0];
			var flag = objFrm.flag.value;
			var bigType = objFrm.f_BigType;
			var smallType = objFrm.f_SmallType;
			if(flag=='close'){
				var openFrm = window.opener.document.forms[0];
				openFrm.submit();
				alert("<bean:message key='exeSQL.ok'/>");
				window.close();
			}else if(flag=='up'){
				bigType.readOnly = true;
				smallType.readOnly = true;
				bigType.style.border = '0px';
				smallType.style.border = '0px';
				bigType.style.backgroundColor="#f5f9ed";
				smallType.style.backgroundColor="#f5f9ed";
			}
		}
		onLoad()
</script>