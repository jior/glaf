<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.todo.*"%>
<%@ page import="com.glaf.base.modules.todo.model.*"%>
<%@ page import="com.glaf.base.modules.todo.service.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ page import="com.glaf.core.todo.*" %>
<%@ page import="com.glaf.core.util.*" %>
<%@ page import="com.glaf.jbpm.model.*" %>
<%@ page import="com.glaf.jbpm.container.ProcessContainer" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
    String context = request.getContextPath();
    String actorId = com.glaf.core.util.RequestUtils.getActorId(request);
	//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@"+actorId);
	TodoJobBean bean = (TodoJobBean)BaseDataManager.getInstance().getBean("todoJobBean");
    List rows = null;
	 try{
		Collection agentIds = ProcessContainer.getContainer().getAgentIds(actorId);
		bean.createTasksFromWorkflow(actorId);
		if(agentIds != null && agentIds.size() > 0){
			Iterator iter = agentIds.iterator();
			while(iter.hasNext()){
				String agentId = (String)iter.next();
				bean.createTasksFromWorkflow(agentId);
			}
		}
		rows = bean.getTodoInstances(actorId);
 	 } catch(Exception ex){
		 ex.printStackTrace();
	 }

%>
 
<table width="620" border="0" cellspacing="1" cellpadding="0" class="list-box">
    <tr class="list-title">
            <td align="center">事&nbsp;&nbsp;项</td>
            <td width="95" align="center">PastDue</td>
            <td width="95" align="center">Caution</td>
            <td width="95" align="center">OK</td>
          </tr>
		  <%if(rows != null && rows.size()> 0){
			  Iterator iterator008 = rows.iterator();
			  while(iterator008.hasNext()){
	               TodoInstance tdi = (TodoInstance) iterator008.next();
				   pageContext.setAttribute("todo", tdi.getTodo());
				   pageContext.setAttribute("tdi",tdi);
			  %>
          <tr class="list-a">
            <td height="20" align="left">
			<a href='<%=context%><c:out value="${todo.listLink}" escapeXml="true"/>&todoId=<c:out value="${todo.id}"/>' 
			     title='<c:out value="${todo.title}"/>   <c:out value="${todo.content}"/>'>
			     <c:out value="${todo.title}"/>
			</a>
			</td>
            <td align="center" class="red">
			     <c:out value="${tdi.qty03}"/>
			</td>
            <td align="center" class="yellow">
			     <c:out value="${tdi.qty02}"/>
			</td>
            <td align="center" class="green">
			     <c:out value="${tdi.qty01}"/>
			</td>
          </tr>
		  <%     }
			}
		  %>
 </table>