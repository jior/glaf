<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	Map params = new HashMap();
	TodoJobBean bean = (TodoJobBean)BaseDataManager.getInstance().getBean("todoJobBean");
	List rows = bean.getToDoInstanceList(params);
	Map todoMap = bean.getToDoMap();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
<table align="center" width="620" border="0" cellspacing="1" cellpadding="0" class="list-box">
          <tr class="list-title">
            <td align="center">事&nbsp;&nbsp;项</td>
            <td width="95" align="center">PastDue</td>
            <td width="95" align="center">Caution</td>
            <td width="95" align="center">OK</td>
          </tr>
		  <%if(rows != null && rows.size()> 0){
			  Iterator iterator008 = rows.iterator();
			  while(iterator008.hasNext()){
                   ToDoInstance tdi = (ToDoInstance)iterator008.next();
				   int status = TodoConstants.getTodoStatus(tdi);
				   tdi.setStatus(status);
				   ToDo todo = (ToDo) todoMap.get(new Long(tdi.getTodoId()));
                   String link = todo.getLink();
				   link = org.jpage.util.Tools.replaceIgnoreCase(link, "${rowId}", tdi.getRowId());
				   pageContext.setAttribute("tdi",tdi);
			  %>
          <tr class="list-a">
            <td height="20" align="left">
			<a href="<%=request.getContextPath()%><%=link%>" target="_blank">
			<c:out value="${tdi.content}"/></a>
			</td>
            <td align="center" class="red">
			<c:if test="${tdi.status == 3}">
			    <fmt:formatDate value="${tdi.pastDueDate}" pattern="yyyy-MM-dd"/>
			</c:if>
			</td>
            <td align="center" class="yellow">
			 <c:if test="${tdi.status == 2}">
			    <fmt:formatDate value="${tdi.alarmDate}" pattern="yyyy-MM-dd"/>
			 </c:if>
			</td>
            <td align="center">
			  <c:if test="${tdi.status == 1}">
			     <fmt:formatDate value="${tdi.endDate}" pattern="yyyy-MM-dd"/>
			  </c:if>
			</td>
          </tr>
		  <%     }
				  }
		  %>
        </table>