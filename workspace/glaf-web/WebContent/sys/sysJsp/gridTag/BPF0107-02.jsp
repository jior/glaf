<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="/sys/sysTld/struts-html.tld"%>
<%@ taglib prefix="bean" uri="/sys/sysTld/struts-bean.tld"%>
<%@ taglib uri="/sys/sysTld/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/sys/sysTld/struts-logic.tld" prefix="logic"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>统计查询</title>
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
	
<body>
	<html:form method="POST" action="BPF0107-02.do">
		<input type="hidden" value="runPageIscSample" name="actionMethodId">
		<input type="hidden" name="screenId" value="${yfclForm.screenId }">
		
		<table width="800px" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="27" class="Title style1" width="100%" colspan="2" >
					
					统计查询
				</td>
			</tr>
			<tr >
				<!-- 检索条件区域 -->
				<td align="left" style="line-height: 20px" height="30" bgcolor="#f5f9ed" width="70%" >
					
					<table width="100%" border="0">
						<tr align="left">
							<td width="60px" bgcolor="#f5f9ed" style="line-height: 20px" align="right" nowrap="nowrap"  >
								处理年月
							</td>
							<td style="line-height: 20px" height="30" bgcolor="#f5f9ed" align="left" >
								<html:text property="cxny" styleId="cxny" />
							</td>
						</tr>
					</table>
				</td>
				<!-- 按钮区域 -->
				<td align="right" style="line-height: 20px" height="30"	bgcolor="#f5f9ed" >
					<table width="100%" border="0">
						<tr>
							<td align="right">
								<input type="button" 
									style="background:url('<%=request.getContextPath()%>/sys/sysImages/btn_01.gif');border:0;height:25px;width:82px"
									value="检索" 
									name="search" 
									onclick="doSearch();" >
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
				<td colspan="2">
					<table id="showTable" width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="10" class="Title" >
								共计
								<nested:write property="allCount" />
								条
							</td>
						</tr>
						<tr>
							<td align="left" style="line-height: 20px;" height="30" bgcolor="#f5f9ed" colspan="2">
								<div style="width: 820px; height:350px; overflow-y: scroll;">
									<table id="iscTab" class=EOS_table border=0 width="800px" align="left">
									<thead>
											<tr>
												<th  nowrap class="T2">NO</th>
												<th  nowrap class="T2">处理年月</th>
												<th  nowrap class="T2">供应商</th>
												<th  nowrap class="T2">品番</th>
												<th  nowrap class="T2">品名</th>
												<th  nowrap class="T2">销售金额</th>
												<th  nowrap class="T2">基准金额</th>
											</tr>
										</thead>
										<nested:iterate id="details" property="BPF010702Details">
											<tr>
												<td>
													<bean:write name="details" property="no" />
												</td>
											<c:choose>
												<c:when test="${details.no!=''&&details.no!=null}">
												<td align="center">
													<bean:write name="details" property="yfkjrny" />
												</td>
												<td>
													<bean:write name="details" property="cjbh" />
												</td>
												<td>
													<bean:write name="details" property="pf" /><nested:hidden property="pf"/>
												</td>
												<td>
													<bean:write name="details" property="pm" />
												</td>
												</c:when>
												<c:otherwise>
													<td colspan="4" align="left">
													<bean:write name="details" property="yfkjrny" />
													</td>
												</c:otherwise>
											</c:choose>
												<td align="right">
													<bean:write name="details" property="bhsje" />
												</td>
												<td align="right">
													<bean:write name="details" property="hsje" />
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
	</html:form>
	
	<script type="text/javascript" language="Javascript">
	
	showCalendar('cxny','yyyy-MM');
		
	function doSearch() {
		objFrm = document.forms[0];
   		objFrm.actionMethodId.value = "runPageSearch";
		objFrm.submit();
	}
	var icount = 0;
	function selChange(xzzt){
		var save = document.getElementsByName('save');
		if(xzzt.checked){
			icount++;
		}else{
			icount--;
		}
		
		if(icount>0){
			save[0].removeAttribute('disabled');
		}else{
			save[0].setAttribute('disabled', 'disabled');
		}
	}
	</script>	
</body>
 <jsp:include page="/sys/sysJsp/common/showAllTypeMessage.jsp" flush="true"/>
</html>