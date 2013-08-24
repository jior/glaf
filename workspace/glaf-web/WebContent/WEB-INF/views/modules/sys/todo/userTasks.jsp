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
<script type="text/javascript">
	function openTask(url,title){
		window.parent.addTab(title,url,"");
	}
</script>
<table width="98%" border="1" cellspacing="1" cellpadding="0" 
       style="border:1px solid #B1CDE3;border-collapse:collapse;margin:5px 5px;padding:0;">
	<tr>
	<td height="22" align="center" width="75%" class="todo_index_td">事项</td>
	<td width="25%" align="center" height="22" class="todo_index_td">任务数</td>
	</tr>
  <%if(userTasks != null && userTasks.size()> 0){
	    int index = 1;
		Iterator iterator008 = userTasks.iterator();
		while(iterator008.hasNext()){
			TodoTotal todoTotal = (TodoTotal)iterator008.next();
            Todo todo = todoTotal.getTodo();
			pageContext.setAttribute("todoTotal", todoTotal);
			pageContext.setAttribute("todo", todo);
			String urlChar = "?";
			if(todo.getListLink().indexOf("?")>0){
				urlChar = "&";
			}
			String url = todo.getListLink()+urlChar+"todoId="+todo.getId()+"&rowIds="+todoTotal.getAllBuffer();
  %>
          <tr>
            <td height="22" align="left"  width="75%" style="border:1px solid #B1CDE3;"><a href="#" onclick="openTask('<%=url%>','${todo.title}')" class="aa"><c:out value="${todo.title}"/></a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<!--<a href='<c:out value="${todo.listLink}" escapeXml="true"/><%=urlChar%>todoId=<c:out value="${todo.id}"/>&rowIds=${todoTotal.allBuffer}' target="_blank"
			     title='<c:out value="${todo.title}"/>   <c:out value="${todo.content}"/>' class="aa">
			     <c:out value="${todo.title}"/>
			</a>-->
			</td>
            <td width="25%" align="center" height="22" style="border:1px solid #B1CDE3;">
			     <%=todoTotal.getRowIds().size()%>
			</td>
          </tr>
  <%}
 }%>
 </table>