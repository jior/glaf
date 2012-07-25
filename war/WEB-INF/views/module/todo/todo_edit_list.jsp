<%@ page contentType="text/html;charset=gbk" language="java"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.todo.*"%>
<%@ page import="com.glaf.base.modules.todo.model.*"%>
<%@ page import="com.glaf.base.modules.todo.service.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ page import="org.jpage.util.*" %>
<%@ page import="org.jpage.core.query.paging.*" %>
<%@ page import="com.glaf.base.modules.*" %>
<%@ page import="com.glaf.base.modules.todo.service.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
    String context = request.getContextPath();
    SysUser user = (SysUser) request.getSession().getAttribute(SysConstants.LOGIN);
	
	Map params = org.jpage.util.RequestUtil.getQueryParams(request);

	TodoJobBean bean = (TodoJobBean)BaseDataManager.getInstance().getBean("todoJobBean");
	Collection rows = bean.getAllToDoList();

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>基础平台系统</title>
<link href="../css/site.css" rel="stylesheet" type="text/css">
<link href="<%=context%>/css/site.css" rel="stylesheet" type="text/css">
<script src="<%=context%>/js/main.js" language="javascript"></script>
<style type="text/css"> 
@import url("<%=context%>/js/hmenu/skin-yp.css");
.STYLE1 {color: #FF0000}
</style>
<script type="text/javascript">
_dynarch_menu_url = "<%=context%>/js/hmenu";
</script>
<script type="text/javascript" src="<%=context%>/js/hmenu/hmenu.js"></script>
<script type="text/javascript" src="<%=context%>/js/main.js"></script>
<script type="text/javascript" src="<%=context%>/js/site.js"></script>
<script type="text/javascript">

</script>
</head>

<body id="document">
<br><br>
<table align="center" width="90%" border="0" cellspacing="1" cellpadding="0" class="list-box">
          <tr class="list-title">
		    <td align="center" width="60">序号</td>
            <td align="center" width="120">模块名称</td>
			<td align="center">标题</td>
			<td align="center">内容</td>
			<!-- <td align="center" width="180">流程名称</td> -->
            <!-- <td align="center" width="60">角色代码</td> -->
            <td align="center" width="60">期限</td>
			<td align="center" width="60">a(小时)</td>
			<td align="center" width="60">b(小时)</td>
			<td align="center" width="90">是否启用</td>
			<td align="center" width="60">功能键</td>
          </tr>
		  <%if(rows != null && rows.size()> 0){
			  int index = 1;
			  Iterator iterator008 = rows.iterator();
			  while(iterator008.hasNext()){
                   ToDo todo = (ToDo)iterator008.next();
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
			<td align="right" width="60">
			    <c:out value="${todo.xa}"/>
			</td>
			<td align="right" width="60">
			    <c:out value="${todo.xb}"/>
			</td>
			<td align="center" width="90">
			   <c:if test="${todo.enableFlag == 1}">
			      <strong><font color="green"> 启用 </font></strong>
			   </c:if>
			   <c:if test="${todo.enableFlag == 0}">
			      <strong><font color="red"> 禁用 </font></strong>
			   </c:if>
			</td>
			<td align="center" width="60">
			    <a href="todo_edit.jsp?id=<c:out value="${todo.id}"/>"><img src="modify.gif" border="0"></a>
			</td>
          </tr>
		  <%     }
				  }
		  %>
        </table>