<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
       String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<title>模板管理</title>
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
 <script language="javascript">

function createTemplateId(){
    location.href="<%=request.getContextPath()%>/mx/sys/template/edit?nodeId=${nodeId}";
}

function modifyTemplate(templateId){
    location.href="<%=request.getContextPath()%>/mx/sys/template/edit?templateId="+templateId+"&nodeId=${nodeId}";
}
 
</script>
</head>
<body style="padding-left:20px;padding-right:20px">
<br>
<center>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="模板列表">&nbsp;模板列表
</div>

<table align="center" class="table-border" cellspacing="0"
	cellpadding="0" width="90%">
	<tr>
		<td height="28" align="right" noWrap><c:if
			test="${not empty nodeId}">
			<input type="button" value="新增模板" name="createTemplateId"
				class="btn" onclick="javascript:createTemplateId();">
		</c:if></td>
	</tr>
</table>
<br>

<table align="center" class="x-table-border table table-striped table-bordered table-condensed" cellspacing="1"
	cellpadding="4" width="90%">
	<thead>
	<tr class="x-title">
		<td align="center" noWrap>名称</td>
		<!-- <td  align="center" noWrap>类型</td>
		-->
		<td align="center" noWrap>模板文件</td>
		<!--<td  align="center" noWrap>创建者</td>
		<td  align="center" noWrap>创建日期</td>-->
		<td align="center" noWrap>状态</td>
		<td align="center" noWrap>功能键</td>
	</tr>
	</thead>
	<c:forEach items="${templates}" var="template">

		<tr onmouseover="this.className='x-row-over';"
			onmouseout="this.className='x-row-out';" class="x-content">
			<td align="left" noWrap>&nbsp; <a
				href="<%=request.getContextPath()%>/mx/sys/template/view?templateId=${template.templateId}"> ${template.name}</a></td>
			 
			<td align="left" noWrap><a
				href="<%=request.getContextPath()%>/mx/lob/lob/download?fileId=${template.templateId}"
				target="newFrame">${template.dataFile}&nbsp;<img
				src="<%=request.getContextPath()%>/images/download.gif"
				border="0"></a></td>
			 
			<td align="center" noWrap><c:choose>
				<c:when test="${template.locked== 0}">
						 &nbsp;<font color="green"><b>已启用</b></font>
				</c:when>
				<c:when test="${template.locked==1}">
						 &nbsp;<font color="red"><b>已禁用</b></font>
				</c:when>
			</c:choose></td>
			<td align="center" noWrap><a><img
				src="<%=contextPath%>/images/modify.gif" border="0" alt="编辑模板"
				style="cursor: hand;"
				onclick="javascript:modifyTemplate('${template.templateId}');">编辑</a>
			</td>
		</tr>

	</c:forEach>

</table>
<iframe id="newFrame" name="newFrame" width="0" height="0"></iframe></center>
</form>

<%@ include file="/WEB-INF/views/tm/footer.jsp"%>