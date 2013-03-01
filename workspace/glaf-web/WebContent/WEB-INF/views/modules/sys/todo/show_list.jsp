<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.todo.*" %>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ page import="com.glaf.base.modules.todo.*"%>
<%@ page import="com.glaf.base.modules.todo.model.*"%>
<%@ page import="com.glaf.base.modules.todo.service.*"%>
<%@ page import="com.glaf.base.modules.todo.util.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
    String context = request.getContextPath();
    SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);
	
	Map params = com.glaf.core.util.RequestUtils.getQueryParams(request);

	List rows = (List)request.getAttribute("rows");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>基础平台系统</title>
<link href="../css/site.css" rel="stylesheet" type="text/css">
<link href="<%=context%>/css/site.css" rel="stylesheet" type="text/css">
<script src="<%=context%>/scripts/main.js" language="javascript"></script>
<style type="text/css"> 
@import url("<%=context%>/scripts/hmenu/skin-yp.css");
.STYLE1 {color: #FF0000}
</style>
<script type="text/javascript">
_dynarch_menu_url = "<%=context%>/scripts/hmenu";
</script>
<script type="text/javascript" src="<%=context%>/scripts/hmenu/hmenu.js"></script>
<script type="text/javascript" src="<%=context%>/scripts/main.js"></script>
<script type="text/javascript" src="<%=context%>/scripts/site.js"></script>
<script type="text/javascript">
    function importXls(){
		location.href="<%=context%>/sys/todo.do?method=showUpload";
    }
</script>
</head>
<body  id="document">
<jsp:include page="/WEB-INF/views/module/header.jsp" flush="true"/>
<br>
<table align="center" class="table-border" cellspacing="0"
	cellpadding="0" width="90%">
	<tr>
		<td colspan="10" height="30" align="right"><input type="button" value="导入"
			name="createScheduler" class="button"
			onclick="javascript:importXls();"></td>
	</tr>
</table>
<br>

 <table align="center" width="90%" border="0" cellspacing="1" cellpadding="0" class="list-box">
          <tr class="list-title">
		    <td align="center" width="60">序号</td>
            <td align="center" width="120">模块名称</td>
			<td align="center" width="150">标题</td>
			<td align="center" width="180">内容</td>
			<!-- <td align="center" width="180">流程名称</td> -->
            <!-- <td align="center" width="60">角色代码</td> -->
            <td align="center" width="60">期限</td>
			<!-- <td align="center" width="60">a(小时)</td>
			<td align="center" width="60">b(小时)</td> -->
			<td align="center" width="90">是否启用</td>
			<td align="center" width="60">功能键</td>
          </tr>
		  <%if(rows != null && rows.size()> 0){
			  int index = 1;
			  Iterator iterator008 = rows.iterator();
			  while(iterator008.hasNext()){
                   Todo todo = (Todo)iterator008.next();
				   pageContext.setAttribute("todo", todo);
			  %>
          <tr class="list-a">
		    <td height="20" align="center">
			    <%=index++%>
			</td>
            <td height="20" align="left">
			    <c:out value="${todo.moduleName}"/>
			</td>
			<td height="20" align="left">
			   <c:out value="${todo.title}"/>
			</td>
            <td align="left" >
			   <c:out value="${todo.content}"/>
			</td>
			<!-- <td align="left">
			    <c:out value="${todo.processName}"/>
			</td>
            <td align="left" class="yellow" width="60">
			   <c:out value="${todo.roleCode}"/>
			</td> -->
            <td align="right" width="60">
			    <c:out value="${todo.limitDay}"/>
			</td>
			<!-- <td align="right" width="60">
			    <c:out value="${todo.xa}"/>
			</td>
			<td align="right" width="60">
			    <c:out value="${todo.xb}"/>
			</td> -->
			<td align="center" width="90">
			   <c:if test="${todo.enableFlag == 1}">
			      <strong><font color="green"> 启用 </font></strong>
			   </c:if>
			   <c:if test="${todo.enableFlag == 0}">
			      <strong><font color="red"> 禁用 </font></strong>
			   </c:if>
			</td>
			<td align="center" width="60">
			    <a href="<%=context%>/sys/todo.do?method=showModify&id=<c:out value="${todo.id}"/>"><img src="<%=context%>/images/edit_profile.png" border="0"></a>
			</td>
          </tr>
		  <%     }
				  }
		  %>
        </table>