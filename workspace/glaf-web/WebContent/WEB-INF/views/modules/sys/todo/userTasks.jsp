<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.todo.*"%>
<%@ page import="com.glaf.base.modules.todo.model.*"%>
<%@ page import="com.glaf.base.modules.todo.service.*"%>
<%@ page import="com.glaf.core.todo.*" %>
<%@ page import="com.glaf.core.util.*" %>
<%@ page import="com.glaf.jbpm.model.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
    String context = request.getContextPath();
    List<TodoTotal> userTasks = (List<TodoTotal>)request.getAttribute("userTasks");

%>
 
<table width="96%" border="0" cellspacing="1" cellpadding="0">
  <%if(userTasks != null && userTasks.size()> 0){
	    int index = 1;
		Iterator iterator008 = userTasks.iterator();
		while(iterator008.hasNext()){
			TodoTotal todoTotal = (TodoTotal)iterator008.next();
            Todo todo = todoTotal.getTodo();
			pageContext.setAttribute("todoTotal", todoTotal);
			pageContext.setAttribute("todo", todo);
  %>
          <tr>
            <td height="22" align="left"  width="75%">
			&nbsp;&nbsp;&nbsp;&nbsp;<a href='<%=context%><c:out value="${todo.listLink}" escapeXml="true"/>&todoId=<c:out value="${todo.id}"/>&rowIds=${todoTotal.allBuffer}' target="_blank"
			     title='<c:out value="${todo.title}"/>   <c:out value="${todo.content}"/>'>
			     <c:out value="${todo.title}"/>
			</a>
			</td>
            <td width="25%" align="center" height="22" align="center">
			     <%=todoTotal.getRowIds().size()%>
			</td>
          </tr>
  <%}
 }%>
 </table>