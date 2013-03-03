<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/WEB-INF/views/tm/header.jsp"%>
 
 
<style>
body {
	font-size: 12px;
}

table {
	font-size: 12px;
}

td {
	font-size: 12px;
}

.x_header {
	font-size: 12px;
	text-align: center;
}

.x_td {
	background-color: #FFB76F;
	text-align: center;
	font-size: 12px;
}
</style>
<script language="javascript">
  function checkX(value, num){
       document.getElementById("layoutName").value=value;
  }
 function saveX(){
	   document.iForm.submit();
 }
 </script>

<div class="content-block" style="width: 95%;" align="center"><br>

<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="首页布局设置">&nbsp;首页布局设置</div>
<br>

<form name="iForm" method="post"
	action="<%=request.getContextPath()%>/mx/layout/save"><input
	type="hidden" id="layoutName" name="layoutName"
	value="${userPanel.layout.name}"> <c:if
	test="${ isSystem eq 'true'}">
	<input type="hidden" id="isSystem" name="isSystem"
		value="${isSystem}">
</c:if>

<div align="center">&nbsp;&nbsp;<input type="button" name="button"
	value="保存设置" onclick="javascript:saveX();" class="btn btn-primary" /></div>
<br>
<table width="98%" border="1" cellspacing="1" cellpadding="1">
	<tr>
		<td width="25%" align="center">
		<table width="98%" border="0" cellspacing="1" cellpadding="1">
			<tr>
				<td align="center"><input type="radio" name="layout"
					onclick="javascript:checkX('A1', 1)"
					<c:if test="${userPanel.layout.name eq 'A1'}">checked</c:if>>A1型
				</td>
			</tr>
			<tr>
				<td class="x_header">
				<table width="100%" cellspacing="1" cellpadding="5" border="1"
					class="x_table_2">
					<tr>
						<td height="80" class="x_td">&nbsp;1</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
		<td width="25%">
		<table width="98%" border="0" cellspacing="1" cellpadding="1"
			class="x_table">
			<tr>
				<td align="center"><input type="radio" name="layout"
					onclick="javascript:checkX('B1', 2)"
					<c:if test="${userPanel.layout.name eq 'B1'}">checked</c:if>>B1型
				</td>
			</tr>
			<tr>
				<td class="x_header">
				<table width="100%" cellspacing="1" cellpadding="5" border="1"
					class="x_table_2">
					<tr>
						<td height="40" class="x_td">&nbsp;1</td>
					</tr>
					<tr>
						<td height="40" class="x_td">&nbsp;2</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
		<td width="25%">
		<table width="98%" border="0" cellspacing="1" cellpadding="1"
			class="x_table">
			<tr>
				<td align="center"><input type="radio" name="layout"
					onclick="javascript:checkX('B2', 2)"
					<c:if test="${userPanel.layout.name eq 'B2'}">checked</c:if>>B2型
				</td>
			</tr>
			<tr>
				<td class="x_header">
				<table width="100%" cellspacing="1" cellpadding="5" border="1"
					class="x_table_2">
					<tr>
						<td height="80" class="x_td">&nbsp;1</td>
						<td height="80" class="x_td">&nbsp;2</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
		<td width="25%">
		<table width="98%" border="0" cellspacing="1" cellpadding="1"
			class="x_table">
			<tr>
				<td align="center"><input type="radio" name="layout"
					onclick="javascript:checkX('C1', 3)"
					<c:if test="${userPanel.layout.name eq 'C1'}">checked</c:if>>C1型
				</td>
			</tr>
			<tr>
				<td class="x_header">
				<table width="100%" cellspacing="1" cellpadding="5" border="1"
					class="x_table_2">
					<tr>
						<td height="40" class="x_td" colspan="2">&nbsp;1</td>
					</tr>
					<tr>
						<td height="40" class="x_td">&nbsp;2</td>
						<td height="40" class="x_td">&nbsp;3</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td width="25%">
		<table width="98%" border="0" cellspacing="1" cellpadding="1"
			class="x_table">
			<tr>
				<td align="center"><input type="radio" name="layout"
					onclick="javascript:checkX('C2', 3)"
					<c:if test="${userPanel.layout.name eq 'C2'}">checked</c:if>>C2型
				</td>
			</tr>
			<tr>
				<td class="x_header">
				<table width="100%" cellspacing="1" cellpadding="5" border="1"
					class="x_table_2">
					<tr>
						<td noWrap class="x_td">&nbsp;1</td>
						<td noWrap class="x_td">&nbsp;2</td>
					</tr>
					<tr>
						<td noWrap class="x_td" colspan="2">&nbsp;3</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
		<td width="25%">
		<table width="98%" border="0" cellspacing="1" cellpadding="1"
			class="x_table">
			<tr>
				<td align="center"><input type="radio" name="layout"
					onclick="javascript:checkX('C3', 3)"
					<c:if test="${userPanel.layout.name eq 'C3'}">checked</c:if>>C3型
				</td>
			</tr>
			<tr>
				<td class="x_header">
				<table width="100%" cellspacing="1" cellpadding="5" border="1"
					class="x_table_2">
					<tr>
						<td noWrap rowspan="2" class="x_td">&nbsp;1</td>
						<td noWrap class="x_td">&nbsp;2</td>
					</tr>
					<tr>
						<td class="x_td">&nbsp;3</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
		<td width="25%">
		<table width="98%" border="0" cellspacing="1" cellpadding="1"
			class="x_table">
			<tr>
				<td align="center"><input type="radio" name="layout"
					onclick="javascript:checkX('C4', 3)"
					<c:if test="${userPanel.layout.name eq 'C4'}">checked</c:if>>C4型
				</td>
			</tr>
			<tr>
				<td class="x_header">
				<table width="100%" cellspacing="1" cellpadding="5" border="1"
					class="x_table_2">
					<tr>
						<td noWrap class="x_td">&nbsp;1</td>
						<td rowspan="2" class="x_td">&nbsp;3</td>
					</tr>
					<tr>
						<td noWrap class="x_td">&nbsp;2</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
		<td width="25%">
		<table width="98%" border="0" cellspacing="1" cellpadding="1"
			class="x_table">
			<tr>
				<td align="center"><input type="radio" name="layout"
					onclick="javascript:checkX('D1', 4)"
					<c:if test="${userPanel.layout.name eq 'D1'}">checked</c:if>>D1型
				</td>
			</tr>
			<tr>
				<td class="x_header">
				<table width="100%" cellspacing="1" cellpadding="5" border="1"
					class="x_table_2">
					<tr>
						<td noWrap class="x_td">&nbsp;1</td>
						<td noWrap class="x_td">&nbsp;2</td>
					</tr>
					<tr>
						<td noWrap class="x_td">&nbsp;3</td>
						<td noWrap class="x_td">&nbsp;4</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td width="25%">
		<table width="98%" border="0" cellspacing="1" cellpadding="1"
			class="x_table">
			<tr>
				<td align="center"><input type="radio" name="layout"
					onclick="javascript:checkX('D2', 4)"
					<c:if test="${userPanel.layout.name eq 'D2'}">checked</c:if>>
				D2型</td>
			</tr>
			<tr>
				<td class="x_header">
				<table width="100%" cellspacing="1" cellpadding="5" border="1"
					class="x_table_2">
					<tr>
						<td noWrap rowspan="3" class="x_td">&nbsp;1</td>
						<td noWrap class="x_td">&nbsp;2</td>
					</tr>
					<tr>
						<td class="x_td">&nbsp;3</td>
					</tr>
					<tr>
						<td class="x_td">&nbsp;4</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
		<td width="25%">
		<table width="98%" border="0" cellspacing="1" cellpadding="1"
			class="x_table">
			<tr>
				<td align="center"><input type="radio" name="layout"
					onclick="javascript:checkX('D3', 4)"
					<c:if test="${userPanel.layout.name eq 'D3'}">checked</c:if>>D3型
				</td>
			</tr>
			<tr>
				<td class="x_header">
				<table width="100%" cellspacing="1" cellpadding="5" border="1"
					class="x_table_2">

					<tr>
						<td class="x_td">&nbsp;2</td>
						<td rowspan="3" class="x_td">&nbsp;1</td>
					</tr>
					<tr>
						<td class="x_td">&nbsp;3</td>
					</tr>
					<tr>
						<td class="x_td">&nbsp;4</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
		<td width="25%">
		<table width="98%" border="0" cellspacing="1" cellpadding="1"
			class="x_table">
			<tr>
				<td align="center"><input type="radio" name="layout"
					onclick="javascript:checkX('D4', 4)"
					<c:if test="${userPanel.layout.name eq 'D4'}">checked</c:if>>D4型
				</td>
			</tr>
			<tr>
				<td class="x_header">
				<table width="100%" cellspacing="1" cellpadding="5" border="1"
					class="x_table_2">
					<tr>
						<td height="40" colspan="3" class="x_td">&nbsp;1</td>
					</tr>
					<tr>
						<td height="40" class="x_td">&nbsp;2</td>
						<td height="40" class="x_td">&nbsp;3</td>
						<td height="40" class="x_td">&nbsp;4</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
		<td width="25%">
		<table width="98%" border="0" cellspacing="1" cellpadding="1"
			class="x_table">
			<tr>
				<td align="center"><input type="radio" name="layout"
					onclick="javascript:checkX('D5', 4)"
					<c:if test="${userPanel.layout.name eq 'D5'}">checked</c:if>>D5型
				</td>
			</tr>
			<tr>
				<td class="x_header">
				<table width="100%" cellspacing="1" cellpadding="5" border="1"
					class="x_table_2">
					<tr>
						<td height="40" class="x_td">&nbsp;1</td>
						<td height="40" class="x_td">&nbsp;2</td>
						<td height="40" class="x_td">&nbsp;3</td>
					</tr>
					<tr>
						<td height="40" colspan="3" class="x_td">&nbsp;4</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td width="25%">
		<table width="98%" border="0" cellspacing="1" cellpadding="1"
			class="x_table">
			<tr>
				<td align="center"><input type="radio" name="layout"
					onclick="javascript:checkX('E1', 5)"
					<c:if test="${userPanel.layout.name eq 'E1'}">checked</c:if>>E1型
				</td>
			</tr>
			<tr>
				<td class="x_header">
				<table width="100%" cellspacing="1" cellpadding="5" border="1"
					class="x_table_2">
					<tr>
						<td colspan="2" class="x_td">&nbsp;1</td>
					</tr>
					<tr>
						<td class="x_td">&nbsp;2</td>
						<td class="x_td">&nbsp;3</td>
					</tr>
					<tr>
						<td class="x_td">&nbsp;4</td>
						<td class="x_td">&nbsp;5</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
		<td width="25%">
		<table width="98%" border="0" cellspacing="1" cellpadding="1"
			class="x_table">
			<tr>
				<td align="center"><input type="radio" name="layout"
					onclick="javascript:checkX('E2', 5)"
					<c:if test="${userPanel.layout.name eq 'E2'}">checked</c:if>>E2型
				</td>
			</tr>
			<tr>
				<td class="x_header">
				<table width="100%" cellspacing="1" cellpadding="5" border="1"
					class="x_table_2">
					<tr>
						<td class="x_td">&nbsp;1</td>
						<td class="x_td">&nbsp;2</td>
					</tr>
					<tr>
						<td class="x_td">&nbsp;3</td>
						<td class="x_td">&nbsp;4</td>
					</tr>
					<tr>
						<td colspan="2" class="x_td">&nbsp;5</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
		<td width="25%">
		<table width="98%" border="0" cellspacing="1" cellpadding="1"
			class="x_table">
			<tr>
				<td align="center"><input type="radio" name="layout"
					onclick="javascript:checkX('F1', 6)"
					<c:if test="${userPanel.layout.name eq 'F1'}">checked</c:if>>F1型
				</td>
			</tr>
			<tr>
				<td class="x_header">
				<table width="100%" cellspacing="1" cellpadding="5" border="1"
					class="x_table_2">
					<tr>
						<td class="x_td">&nbsp;1</td>
						<td class="x_td">&nbsp;2</td>
					</tr>
					<tr>
						<td class="x_td">&nbsp;3</td>
						<td class="x_td">&nbsp;4</td>
					</tr>
					<tr>
						<td class="x_td">&nbsp;5</td>
						<td class="x_td">&nbsp;6</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
		<td width="25%">
		<table width="98%" border="0" cellspacing="1" cellpadding="1"
			class="x_table">
			<tr>
				<td align="center"><input type="radio" name="layout"
					onclick="javascript:checkX('F2', 8)"
					<c:if test="${userPanel.layout.name eq 'F2'}">checked</c:if>>F2型
				</td>
			</tr>
			<tr>
				<td class="x_header">
				<table width="100%" cellspacing="1" cellpadding="5" border="1"
					class="x_table_2">
					<tr>
						<td class="x_td">&nbsp;1</td>
						<td class="x_td">&nbsp;3</td>
						<td class="x_td">&nbsp;5</td>
					</tr>
					<tr>
						<td class="x_td">&nbsp;2</td>
						<td class="x_td">&nbsp;4</td>
						<td class="x_td">&nbsp;6</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td colspan="20">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="x_table">
			<tr>
				<td width="25%" align="center">位置</td>
				<td align="center">链接内容</td>
			</tr>
	<%
	    UserPanel userPanel = (UserPanel)request.getAttribute("userPanel");
		Map names = new HashMap();
		if(userPanel != null && userPanel.getPanelInstances() != null){
			Set<PanelInstance> panelInstances = userPanel.getPanelInstances();
			for(PanelInstance model: panelInstances){
               if(model.getPanel() != null && StringUtils.isNotEmpty(model.getName())){
					 names.put(model.getName(), model.getPanel() .getName());
				 }
		     }
		}
		System.out.println(names);
		for(int dataIndex=1; dataIndex<=8; dataIndex++){
	%>
			<tr id="x_tr_<%=dataIndex%>">
				<td width="25%" align="center"><%=dataIndex%></td>
				<td align="left"><input type="hidden"
					id="x_grid_<%=dataIndex%>" name="x_grid_<%=dataIndex%>"
					value="<%=dataIndex%>"> <select id="panel_<%=dataIndex%>"
					name="panel_<%=dataIndex%>">
					<option value="">----请选择----</option>
					<c:forEach items="${panels}" var="panel">
			<%
		      String selected = "";
		      Panel panel = (Panel)pageContext.getAttribute("panel");
			  if(names.get(String.valueOf(dataIndex)) != null){
				    String name = (String)names.get(String.valueOf(dataIndex));
					if(StringUtils.equals(name, panel.getName())){
				        selected = "selected";
 					}
			  }
		     %>
						<option value="${panel.name}" <%=selected%>>${panel.title}</option>
					</c:forEach>
				</select></td>
			</tr>
			<%}%>
		</table>
		</td>
	</tr>
</table>
<div style="width: 98%" align="center"><br>
&nbsp;&nbsp;<input type="button" name="button" value="保存设置"
	onclick="javascript:saveX();" class="btn btn-primary" /></div>
</form>
</div>
