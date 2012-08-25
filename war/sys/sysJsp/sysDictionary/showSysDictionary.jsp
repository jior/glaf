<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="/sys/sysTld/struts-html.tld"%>
<%@ taglib prefix="bean" uri="/sys/sysTld/struts-bean.tld"%>
<%@ taglib uri="/sys/sysTld/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/sys/sysTld/struts-logic.tld" prefix="logic"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title><bean:message key="SYSDICTIONARY.TITLE" /></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<LINK rel="stylesheet" type="text/css" href="sys/sysCss/menu/css.css">
<LINK rel=stylesheet type=text/css href="sys/sysCss/menu/style-custom.css">
<link href="sys/sysCss/menu/jquery.css" rel="stylesheet" type="text/css"
	media="screen">
	<style type="text/css">
		body {
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
			overflow-X: hidden;
		}
		</style>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/frameWorkUtility.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/calendar/WdatePicker.js"></script>
	<body bgcolor="#f5f9ed" style="border:0px; margin:0px 0px 0px 0px">
		<html:form method="POST" action="showSysDictionary.do"  styleId="form_search_local">
		<html:hidden property="flag"/>
		<input type="hidden" value="runPageCtrl" name="actionMethodId">
		<table width="800px" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="27" class="Title style1" width="100%" colspan="1" >
					<bean:message key="SYSDICTIONARY.TITLE" />
				</td>
			</tr>
			<tr >
				<!-- 检索条件区域 -->
				<td align="left" style="line-height: 20px" height="30" bgcolor="#f5f9ed" >
					
					<table width="100%" border="0">
						<tr align="left">
							<td width="50px" align="right" height="35" nowrap >
							<div align="left">
								&nbsp;&nbsp;<bean:message key="SYSDICTIONARY.BIGTYPE" />
							</div>
							</td>
							<td width="100px" align="left" nowrap >
								<html:text property="f_BigType" maxlength="10"></html:text>
							</td>
							<td width="50px" align="right" height="35" nowrap >
								<div align="left">
									&nbsp;&nbsp;<bean:message key="SYSDICTIONARY.SMALLTYPE" />
								</div>
							</td>
							<td width="100px" align="left"  nowrap >
								<html:text property="f_SmallType" maxlength="10"></html:text>
							</td>
							<td width="60px" align="right" height="35" nowrap >
								<div align="left">
									&nbsp;&nbsp;<bean:message key="SYSDICTIONARY.ISLOCK" />
								</div>
							</td>
							<td width="40px" align="left"  nowrap>
								<html:select property="f_IsLock">
									<html:option value="">&nbsp;</html:option>
									<html:option value="1"><bean:message key="SYSDICTIONARY.LOCKED" /></html:option>
									<html:option value="0"><bean:message key="SYSDICTIONARY.NOLOCKED" /></html:option>
								</html:select>
							</td>
							<td width="40px" align="right" height="35" nowrap>
								<div align="left">
									&nbsp;&nbsp;<bean:message key="SYSDICTIONARY.CONS" />
								</div>
							</td>
							<td align="left"  nowrap>
								<html:text property="f_Const" maxlength="50"/>
							</td>
						</tr>
						
						<tr align="left">
							<td width="50px" align="right" height="35" nowrap  >
							<div align="left">
								&nbsp;&nbsp;<bean:message key="SYSDICTIONARY.REMARK" />
							</div>
							</td>
							<td width="100px" align="left" nowrap colspan="7" >
								<html:text property="f_Comon" size="100" maxlength="200"></html:text>
							</td>
						</tr>
						
					</table>
				</td>
			</tr>
			<tr>
			<!-- 按钮区域 -->
				<td align="right" style="line-height: 20px" height="30"	bgcolor="#f5f9ed" >
					<table width="100%" border="0">
						<tr>
							<td align="left">
								<input type="button" value="<bean:message key="SYSDICTIONARY.SEARCH" />" name="select" onclick="doSearch()"
						        style="background:url('<%=request.getContextPath()%>/sys/sysImages/btn_01.gif');border:0;height:25px;width:82px"/>
								&nbsp;&nbsp;<input type="button" value="<bean:message key="SYSDICTIONARY.ADD" />" name="search" onclick="doAdd()"
								 style="background:url('<%=request.getContextPath()%>/sys/sysImages/btn_01.gif');border:0;height:25px;width:82px">
								&nbsp;&nbsp;<input type="button" value="<bean:message key="SYSDICTIONARY.DELETE" />" name="search" onclick="doDelete()"
								 style="background:url('<%=request.getContextPath()%>/sys/sysImages/btn_01.gif');border:0;height:25px;width:82px">
								&nbsp;&nbsp;<input type="button" value="<bean:message key="SYSDICTIONARY.LOCK" />" name="search" onclick="doSave('1')"
								 style="background:url('<%=request.getContextPath()%>/sys/sysImages/btn_01.gif');border:0;height:25px;width:82px">
								 <input type="button" value="<bean:message key="SYSDICTIONARY.UNLOCK" />" name="search" onclick="doSave('0')"
								 style="background:url('<%=request.getContextPath()%>/sys/sysImages/btn_01.gif');border:0;height:25px;width:82px">
							
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
					</table>
				</td>	
			</tr>
			<tr>
				<!-- 数据显示区域 -->
				<td colspan="1">
					<table id="showTable" width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="center" style="line-height: 20px;" height="30" bgcolor="#f5f9ed" colspan="2">
								<div style="width: 800px; overflow-x: scroll;">
								
									<table width="799px" id="iscTab" class=EOS_table border=0 align="left">
									
											<tr>
												<th nowrap class="T2"><input type="checkbox" onclick="chekAll(this)"/><bean:message key="SYSDICTIONARY.CHEK" /></th>
												<th nowrap class="T2"><bean:message key="SYSDICTIONARY.BIGTYPE" /></th>
												<th nowrap class="T2"><bean:message key="SYSDICTIONARY.SMALLTYPE" /></th>
												<th nowrap class="T2"><bean:message key="SYSDICTIONARY.CONS" /></th>
												<th nowrap class="T2"><bean:message key="SYSDICTIONARY.REMARK" /></th>
												<th nowrap class="T2"><bean:message key="SYSDICTIONARY.ISLOCK" /></th>
												<th nowrap class="T2"><bean:message key="SYSDICTIONARY.CZ" /></th>
											</tr>
											<nested:iterate id="details" property="sysDictionaryDetail" >
											 	<nested:hidden property="fbigtype" />
										    	<nested:hidden property="fsmalltype" /> 
										    	<nested:hidden property="fconst" />
										    	<nested:hidden property="fremark" />
											<tr>
												<td width="60"><nested:checkbox property="check"/></td>
										        <td width="60" ><bean:write name="details" property="fbigtype"/></td>
											    <td width="60" ><bean:write name="details" property="fsmalltype"/></td>
											    <td><bean:write name="details" property="fconst"/></td>
											    <td><bean:write name="details" property="fremark"/></td>
												<td width="60" ><bean:write name="details" property="fislock"/></td>
												<td width="40" >
												   <c:if test="${details.fislock=='NO'}">
														<html:link href="#" onclick="doUpdate('&f_BigType=${details.fbigtype}&f_SmallType=${details.fsmalltype}')">修改</html:link>
												   </c:if>
												   <c:if test="${details.fislock!='YES'}">
												   &nbsp;
												   </c:if>
											  </td>
											</tr>
											</nested:iterate>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</td>		
			</tr>
		</table>	
		<jsp:include page="/sys/sysJsp/common/pageController.jsp" flush="true"/>	
	</html:form>
	
	<script type="text/javascript" language="Javascript">
	
	function doSearch(){
		doPageStart();
			}
	function doSave(lockFlag){
	    var objFrm = document.forms[0];
	    var isShowMeg = true;
	    var mytable = document.getElementById("showTable");
		var inputs = mytable.getElementsByTagName("input");
		for(var i=0;i<inputs.length;i++){
			if(inputs[i].type=='checkbox'){
				if(inputs[i].checked){
					isShowMeg = false;
				}
			}
		}
	    
		
		
		objFrm.actionMethodId.value = "runPageSave";
	    objFrm.flag.value=lockFlag;
	    if(isShowMeg){
			alert('<bean:message key='SYSDICTIONARY.LOCKERROR'/>');
			return false;
		}
	    if(lockFlag=='1'){
		    if(confirm("<bean:message key='SYSDICTIONARY.LOCKMEG'/>")){
		    	submitForm(objFrm,"<bean:message key='baseSample.doubleSubmit'/>");
		    }
	    }else{
	    	if(confirm("<bean:message key='SYSDICTIONARY.UNLOCKMEG'/>")){
		    	submitForm(objFrm,"<bean:message key='baseSample.doubleSubmit'/>");
		    }
	    }
		}
	function doDelete(){
	    objFrm = document.forms[0];
	    objFrm.actionMethodId.value = "runPageDelete";
   		if(confirm("<bean:message key='baseSample.deleteMsg'/>")){
			submitForm(objFrm,"<bean:message key='baseSample.doubleSubmit'/>");
		}
		}
	function doUpdate(url){
		window.open("showSysDictionaryAdd.do?flag=up"+url, 'newwidow', "height=250px,width=500px,top=350px,left=400px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
		}

	function doAdd(){
		window.open("showSysDictionaryAdd.do?actionMethodId=runPageLoad&flag=add", 'newwidow', "height=250px,width=500px,top=350px,left=400px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
	}
	function chekAll(che){
		var mytable = document.getElementById("showTable");
		var inputs = mytable.getElementsByTagName("input");
		for(var i=0;i<inputs.length;i++){
			if(inputs[i].type=='checkbox'){
				if(che.checked){
					inputs[i].checked=true;
				}else{
					inputs[i].checked=false;
				}
			}
		}
		
	}
	</script>	
</body>
 <jsp:include page="/sys/sysJsp/common/showAllTypeMessage.jsp" flush="true"/>
</html>