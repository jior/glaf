<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="/sys/sysTld/struts-html.tld"%>
<%@ taglib prefix="bean" uri="/sys/sysTld/struts-bean.tld"%>
<%@ taglib prefix="logic" uri="/sys/sysTld/struts-logic.tld"%>
<%@ taglib uri="/sys/sysTld/struts-nested.tld" prefix="nested"%>
<html>
	<head>
		<title>查询计划任务执行日志</title>
		
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/sysCss/menu/css.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/sysCss/menu/style-custom.css">
	</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/frameWorkUtility.js"></script>	
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/calendar/WdatePicker.js"></script>
	<body bgcolor="#f5f9ed" style="border:0px; margin:0px 0px 0px 0px">
		<html:form method="POST" action="showBatchExeLog.do"  styleId="form_search_local">
			<input type="hidden" value="runPageCtrl" name="actionMethodId">
			<input type="hidden" name="curBatName">
			<input type="hidden" name="curBatContext">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="27" class="Title" colspan="6">
						检索条件输入区
					</td>
				</tr>
				<tr>
					<td height="35" nowrap width="9%">
						<div align="left">
							&nbsp;&nbsp;计划任务ID
						</div>
					</td>
					<td nowrap width="14%">
						<html:text property="f_batchid"/>
					</td>
					<td height="35" nowrap width="9%">
						<div align="left">
							&nbsp;&nbsp;计划任务执行时间
						</div>
					</td>
					<td nowrap width="14%">
						<html:text property="f_batchExeStart"  styleId="sd"/>~<html:text property="f_batchExeEnd" styleId="ed"/>
					</td>
					<td height="35" nowrap width="9%">
						<div align="left">
							&nbsp;&nbsp;计划任务执行者
						</div>
					</td>
					<td nowrap width="14%">
						<html:text property="f_batchExeUser"/>
					</td>
				</tr>
				<tr>
					<td height="35" nowrap width="9%">
						<div align="left">
							&nbsp;&nbsp;计划任务名称
						</div>
					</td>
					<td nowrap width="14%">
						<html:text property="f_batchBusName"/>
					</td>
					<td height="35" nowrap width="9%">
						<div align="left">
							&nbsp;&nbsp;计划任务执行结果
						</div>
					</td>
					<td>
						<html:select property="f_batchExeResult" style="width:110px">
							<html:option value="ALL">&nbsp;</html:option>
							<html:option value="OK">OK</html:option>
							<html:option value="NG">NG</html:option>
						</html:select>
					</td>
					<td>
					</td>
					<td align="right">
						<input type="button" value="检索" name="select" onclick="doSearch()"
			       style="background:url('<%=request.getContextPath()%>/sys/sysImages/btn_01.gif');border:0;height:25px;width:82px"/>
					</td>
				</tr>
			</table>
			
			<div style="width: 813px;overflow-x:scroll;">
			<table class=EOS_table border=0 width="100%" align=center id="iscTab">
				<tr>
					<th width="40" nowrap class="T2">NO</th>
					<th width="60" nowrap class="T2">计划任务ID</th>
					<th width="140" nowrap class="T2">计划任务名称</th>
					<th width="140" nowrap class="T2">启动时间</th>
					<th width="140" nowrap class="T2">完了时间</th>
					<th width="60" nowrap class="T2">输入输出文件</th>
					<th width="60" nowrap class="T2">执行结果</th>
					<th width="200" nowrap class="T2">结果说明</th>
					<th width="60" nowrap class="T2">执行者</th>
					<th width="120" nowrap class="T2">检查出错数据</th>
					<th width="200" nowrap class="T2">日志文件</th>
					<th width="130" nowrap class="T2">再启动参数</th>
					<th width="70" nowrap class="T2">再启动</th>
				</tr>
				<nested:iterate id="details" property="showBatchExeLogDetail" >
				    <tr>
				      <td><bean:write name="details" property="no"/></td>
					  <td><bean:write name="details" property="batId"/></td>
					  <td><bean:write name="details" property="batName"/></td>
					  <td><bean:write name="details" property="exeStartDate"/></td>
					  <td><bean:write name="details" property="exeEndDate"/></td>
					  <td><a href="#" onclick="openIOFiles('<bean:write name="details" property="logid"/>')">查看</a></td>
					  <td><bean:write name="details" property="exeResult"/></td>
					  <td><bean:write name="details" property="exeResultMemo"/></td>
					  <td><bean:write name="details" property="exeUser"/></td>
					  <td><a href="#" onclick="openCheckErrorDataFiles('<bean:write name="details" property="logid"/>')">查看</a></td>
					  <td><a href="<%=request.getContextPath()%>/sys/sysJsp/batch/download.jsp?filepath=<bean:write name="details" property="logfilepath"/>&file=<bean:write name="details" property="logFile"/>">
					      <bean:write name="details" property="logFile"/></a></td>
					  <td><nested:text property="reExeContext"/></td>
					  <td><a href="#" onclick="reExeBatch('<bean:write name="details" property="batId"/>','<bean:write name="details" property="no"/>')">启动</a></td>
				    </tr>
				</nested:iterate>
			</table>
			</div>
			<div align="center">
				<jsp:include page="/sys/sysJsp/common/pageController.jsp" flush="true"/>
			</div>
		</html:form>
	<script type="text/javascript" language="Javascript">
	function doSearch(){
		doPageStart();
		}
	
	function openIOFiles(logid){
		window.open("showIOFile.do?logid=" + logid + "&actionMethodId=runPageLoad");
	}
	
	function openCheckErrorDataFiles(logid){
		window.open("showCheckErrorDataFile.do?logid=" + logid + "&actionMethodId=runPageLoad");
	}
	
	function reExeBatch(batid,curRowNo){
		objFrm = document.forms[0];
		objFrm.actionMethodId.value = "runReExeBatch";
		
		//获取当前行输入的再启动参数
		var no = curRowNo.substring(curRowNo.length-1,curRowNo.length);
		no = no - 1;
		batcontext = document.getElementById("showBatchExeLogDetail[" + no + "].reExeContext").value;
		
		document.getElementById("curBatName").value = batid;
		document.getElementById("curBatContext").value = batcontext;
		submitForm(objFrm, "<bean:message key='baseSample.doubleSubmit'/>");
	}
	
	function downloadLogFile(logFilePath){
		
	}
	
	showCalendar("sd");
	showCalendar("ed");
	</script>
	</body>
<jsp:include page="/sys/sysJsp/common/showAllTypeMessage.jsp" flush="true"/>
</html>
