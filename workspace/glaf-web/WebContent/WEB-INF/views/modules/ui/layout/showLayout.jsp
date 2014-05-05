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
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
 
 
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
  function checkX(value){
      document.getElementById("layoutName").value=value;
	  if(value=='P2'){
		  $("#p2_div").show();
		  $("#p3_div").hide();
	  } else {
          $("#p3_div").show();
		  $("#p2_div").hide();
	  }
  }
 function saveX(){
	 if(confirm("您确定要重新设置首页布局吗？")){
	    document.iForm.submit();
	 }
 }
 </script>

<div class="content-block" style="width: 95%;" align="center"><br>

<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="首页布局设置">&nbsp;首页布局设置</div>
<br>

<form name="iForm" method="post"
	action="<%=request.getContextPath()%>/mx/layout/save">
<input type="hidden" id="layoutName" name="layoutName" value="${userPanel.layoutName}"> 
<c:if test="${ isSystem eq 'true'}">
<input type="hidden" id="isSystem" name="isSystem" value="${isSystem}">
</c:if>

<div align="center">&nbsp;&nbsp;<input type="button" name="button"
	value="保存设置" onclick="javascript:saveX();" class="btn btn-primary" /></div>
<br>
<table width="90%" border="0" cellspacing="1" cellpadding="1" align="center">
	<tr>
		<td width="90%" valign="top" align="center">
		<table width="90%" border="0" cellspacing="1" cellpadding="1" class="x_table">
			<tr>
				<td align="center">
				<input type="radio" name="layout" onclick="javascript:checkX('P2')"
					<c:if test="${userPanel.layoutName eq 'P2'}">checked</c:if>>两栏
				&nbsp;
				<input type="radio" name="layout" onclick="javascript:checkX('P3')"
					<c:if test="${userPanel.layoutName eq 'P3'}">checked</c:if>>三栏
				</td>
			</tr>
			<tr id="p2_div">
				<td>
				 <table width="90%" cellspacing="1" cellpadding="5" border="1"
					class="x_table_2">
					<tr>
						<td class="x-title">&nbsp;左栏</td>
						<td class="x-title">&nbsp;右栏</td>
					</tr>
					<%for(int k=1;k<=9;k++){%>
					<tr>
						<td class="x_td">&nbsp;<%=k*2-1%></td>
						<td class="x_td">&nbsp;<%=k*2%></td>
					</tr>
					<%}%> 
				 </table>
				</td>
			</tr>
			<tr id="p3_div">
			  <td>
				<table width="90%" cellspacing="1" cellpadding="5" border="1"
					class="x_table_2">
					<tr>
						<td class="x-title">&nbsp;左栏</td>
						<td class="x-title">&nbsp;中栏</td>
						<td class="x-title">&nbsp;右栏</td>
					</tr>
					<%for(int k=1;k<=6;k++){%>
					<tr>
						<td class="x_td">&nbsp;<%=k*3-2%></td>
						<td class="x_td">&nbsp;<%=k*3-1%></td>
						<td class="x_td">&nbsp;<%=k*3%></td>
					</tr>
					<%}%> 
				</table>
			  </td>
			</tr>
			<tr>
			  <td>
			    <br>
				<table width="90%" border="0" cellspacing="0" cellpadding="0" class="x_table_2">
					<tr>
						<td width="35%" height="25" align="left" class="x-title">位置</td>
						<td height="25" align="left" class="x-title">链接内容</td>
					</tr>
					<tr>
						<td width="35%" height="25" align="left" >&nbsp;</td>
						<td height="25" align="left" >&nbsp;</td>
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
						//System.out.println(names);
						for(int dataIndex=1; dataIndex<=18; dataIndex++){
					%>
							<tr id="x_tr_<%=dataIndex%>">
								<td width="25%" align="center"><%=dataIndex%></td>
								<td align="left"><input type="hidden"
									id="x_grid_<%=dataIndex%>" name="x_grid_<%=dataIndex%>"
									value="<%=dataIndex%>"> <select id="panel_<%=dataIndex%>"
									name="panel_<%=dataIndex%>" class="span3">
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
		</td>
	</tr>
</table>
<div style="width: 98%" align="center"><br>
&nbsp;&nbsp;<input type="button" name="button" value="保存设置"
	onclick="javascript:saveX();" class="btn btn-primary" /></div>
</form>
</div>
<script type="text/javascript">
    checkX('${userPanel.layoutName}');
</script>