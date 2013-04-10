<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
     String contextPath = request.getContextPath();
	 int sortNo = 1;
%>
<!DOCTYPE html>
<html>
<title>桌面板块设置</title>
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>

<script language="javascript">
 
function editPanel(panelId) {
    location.href="<%=request.getContextPath()%>/mx/panel/edit?isSystem=${isSystem}&panelId="+panelId;
}

function addPanel(){
	 location.href="<%=request.getContextPath()%>/mx/panel/edit?isSystem=${isSystem}";
}

</script>
</head>
<body style="padding-left:20px;padding-right:20px">
 <br>
<center>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="桌面板块列表">&nbsp;桌面板块列表</div>
<br>
<table align="right" class="table-border" cellspacing="0"
	cellpadding="0" width="90%">
	<tr>
		<td height="28" align="right"><input type="button" value="新增"
			name="addPanel" class="btn" onclick="javascript:addPanel();">
		</td>
	</tr>
</table>
<br><br>
<table align="center" class="x-table-border table table-striped table-bordered table-condensed" cellspacing="1"
	cellpadding="4" width="90%">
	<thead>
	<tr class="x-title">
		<td align="center" noWrap>序号</td>
		<td align="center" noWrap>名称</td>
		<td align="center" noWrap>创建日期</td>
 		<td align="center" noWrap>状态</td>
		<td align="center" width="10%" noWrap>功能键</td>
	</tr>
	</thead>
	<c:forEach items="${panels}" var="panel">
	<%
      Panel model = (Panel)pageContext.getAttribute("panel");
	  if(model != null){
        int lockedFlag = model.getLocked();
		String locked = lockedFlag+"";	
		String alt = "启用";
		String title = "";
		String lockedImg = "locked.gif";
		String createDate = DateUtils.getDate(model.getCreateDate());

		if(lockedFlag == 1){
			locked = "0";
			alt = "启用";
			title="<font color=\"red\">已锁定</font>";
		} else {
			lockedImg = "unlocked.gif";
		    locked = "1";
			alt = "锁定";
			title="<font color=\"green\">已启用</font>";
	   }
	    pageContext.setAttribute("lockedImg",lockedImg);
	    pageContext.setAttribute("locked",locked);
 	    pageContext.setAttribute("alt",alt);
	
  %>
		<tr onmouseover="this.className='x-row-over';"
			onmouseout="this.className='x-row-out';" class="x-content">
			<td align="left" noWrap>&nbsp; <%=sortNo++%></td>
			<td align="left" noWrap>&nbsp; 
			<a href="<%=request.getContextPath()%>/mx/panel/edit?isSystem=${isSystem}&panelId=${panel.id}"> ${panel.title}</a>
			&nbsp;
			<c:if test="${!empty panel.link }">
			   <a href="<%=request.getContextPath()%>/${panel.link}"><img
				src="<%=contextPath%>/images/link.png" border="0" title="查看链接内容"/></a>
			</c:if>
			</td>
            <td align="left" noWrap>&nbsp; <%=createDate%></td>
			<td align="left" noWrap>&nbsp; <b><%=title%></b></td>
			<td align="left" width="10%" noWrap>&nbsp; <a><img
				src="<%=contextPath%>/images/modify.gif" border="0"
				title="编辑面板板块" style="cursor: hand;"
				onclick="javascript:editPanel('${panel.id}');">编辑</a>
			&nbsp;</td>
		</tr>
		<%}%>
	</c:forEach>

</table>
</center>
</form>
<br>
<br>
</body>
</html>

