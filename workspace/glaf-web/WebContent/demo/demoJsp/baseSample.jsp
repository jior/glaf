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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/sysCss/baseStyle.css">
  </head>
  <script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/calendar/WdatePicker.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/frameWorkUtility.js"></script>
  <body> 
  	<h5>A sample PGM for DBAccess</h5>
	<html:form method="POST" action="baseSample.do">
	<input type="hidden" value="runPagebaseSample" name="actionMethodId">

	<div
		style="padding: 5px 0px 5px 5px; background-color: white; border; border-width: 1px; border-style: solid; border-color: #87C897;">
	<table>
		<tr>
		<td>系统番号<br></td>
		<td><html:text property="s_Sysno"/></td>
		<td>姓名<br></td>
		<td><html:text property="s_Name"/></td>
		<td>性别<br></td>
		<td><html:select property="s_Sex">
			<html:option value="">&nbsp;</html:option>
			<html:option value="1">男</html:option>
			<html:option value="2">女</html:option>
			</html:select></td>
		<td>年龄<br></td>
		<td><html:text property="s_Age"/></td>
		<td>城市<br></td>
			<td><html:select property="s_City">
			<html:options name="defaultDto" property="values" labelName="defaultDto" labelProperty="labels"></html:options>
			</html:select></td>
		
		<td>出生年<br></td>
		<td><html:text property="s_DeadYear"/></td>
		<td><input type="button" value="create" name="create" onclick="doCreate()">
		</td>
		<td>下拉框测试<br></td>
			<td><html:select property="v_combTest">
			<html:optionsCollection name="baseSampleForm" property="combTestCollection"></html:optionsCollection>
			</html:select>
			</td>
	   </tr>
	</table>
    </div>
    
    <br>
    
	<div
		style="padding: 5px 0px 5px 5px; background-color: white; border; border-width: 1px; border-style: solid; border-color: #87C897;">
		<table id="iscTab" style="font: normal 12px/ 25px simsun;">
			<tr>
				<td>选择</td>
				<td>系统编号	</td>
				<td>姓名</td>
				<td>是否男性</td>
				<td>年龄</td>
				<td>城市</td>
				<td>出生年</td>
				<td>金额</td>
			</tr>
			<nested:iterate id="details" property="baseSampleDetails" >
			<tr>
				<td><nested:checkbox property="chk"/></td>
				<td><nested:text readonly="true" property="sysno"/></td>
				<td><bean:write name="details" property="name"/></td>
				<td><nested:checkbox property="sex"/></td>
				<td><nested:text property="age"/></td>
				<td><nested:select property="city" >
					<html:options name="defaultDto" property="values" labelName="defaultDto" labelProperty="labels"></html:options>
					</nested:select>
				</td>
				<td><bean:write  name="details" property="deadyear"/></td>
				<td><bean:write  name="details" property="money" format="###,###.####"/></td>
			</tr>
			</nested:iterate>
 		</table>
	</div>
	
	<jsp:include page="/sys/sysJsp/common/pageController.jsp" flush="true"/>
	<input type="button" value="search" name="search" onclick="doSearch()">
	<input type="button" value="sqlsearch" name="sqlsearch" onclick="doSqlSearch()">
	<input type="button" value="sqlsearchFY" name="sqlsearchFY" onclick="doSqlSearchFY()">
	<input type="hidden" value="doSqlSearch" name="searchFlag">
	<input type="button" value="save" name="save" onclick="doSave()">
	<input type="button" value="download" name="download" onclick="doDownload()">
<hr/>	
	<div style="padding: 5px 0px 5px 5px; background-color: white; border; border-width: 1px; border-style: solid; border-color: #87C897;">
	<table>
		<tr><td colspan="5">以下为数值处理(自动用逗号分隔符格式化)示例:</td></tr>
		<tr>
		<td>默认<br></td>
		<td><html:text property="s_Df" onblur="this.value=formatNum(this.value);"/></td>
		<td>0位小数<br></td>
		<td><html:text property="s_Pe0" onblur="this.value=formatNum(this.value,0);"/></td>
		<td>1位小数<br></td>
		<td><html:text property="s_Pe1" onblur="this.value=formatNum(this.value,1);"/></td>
		<td>2位小数<br></td>
		<td><html:text property="s_Pe2" onblur="this.value=formatNum(this.value,2);"/></td>
		<td>6位小数<br></td>
		<td><html:text property="s_Pe6" onblur="this.value=formatNum(this.value,6);"/></td>
	   </tr>
	</table>
    </div>
    </html:form>

<script type="text/javascript" language="Javascript">
	function doSearch(){
		objFrm = document.forms[0];
		objFrm.searchFlag.value = "search";
		doPageStart();
		}
	function doSqlSearch(){
		objFrm = document.forms[0];
		objFrm.searchFlag.value = "sqlsearch";
		doPageStart();
		}
	function doSqlSearchFY(){
		objFrm = document.forms[0];
		objFrm.searchFlag.value = "sqlsearchFY";
		doPageStart();
		}
	function doSave(){
	    objFrm = document.forms[0];
	    clearErrorColor();
	    objFrm.actionMethodId.value = "runPageBaseSampleSave";
		submitForm(objFrm,"<bean:message key='baseSample.doubleSubmit'/>");
		}
	function doDelete(){
	    objFrm = document.forms[0];
	    objFrm.actionMethodId.value = "runPageBaseSampleDelete";
   		if(confirm("<bean:message key='baseSample.deleteMsg'/>")){
			submitForm(objFrm,"<bean:message key='baseSample.doubleSubmit'/>");
		}
		}
	function doCreate(){
		    objFrm = document.forms[0];
		    objFrm.actionMethodId.value = "runPageBaseSampleCreate";
		    clearErrorColor();
		    if(validateBaseSampleForm(objFrm)){
		    	submitForm(objFrm,"<bean:message key='baseSample.doubleSubmit'/>");
		    }
		}
		
	function doDownload(){
		objFrm = document.forms[0];
		objFrm.actionMethodId.value = "runDownload";
		objFrm.submit();
	}


</script>

</body>
<html:javascript formName="baseSampleForm"/>
<jsp:include page="/sys/sysJsp/common/showAllTypeMessage.jsp" flush="true"/>
</html>
