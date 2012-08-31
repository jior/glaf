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
<%@ page import="org.jpage.jbpm.service.ProcessContainer" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
    String context = request.getContextPath();
    SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);
	Map params = new HashMap();
	
	TodoJobBean bean = (TodoJobBean)BaseDataManager.getInstance().getBean("todoJobBean");
	Collection taskInstanceIds = ProcessContainer.getContainer().getRunningTaskInstanceIds(user.getAccount());
	
	if(taskInstanceIds != null && taskInstanceIds.size() > 0){
		System.out.println("------>taskInstanceIds:"+taskInstanceIds);
        params.put("taskInstanceIds", taskInstanceIds);
		params.put("actorId", user.getAccount());
	}else{
		params.put("actorId", user.getAccount());
	}

	Collection rows = bean.getToDoInstanceList(params);
	System.out.println("------>rows:"+rows.size());
	
	Map dataMap = new TreeMap();
	Map todoMap = bean.getToDoMap();
	Map roleMap = bean.getRoleMap();
	Map todoxMap = bean.getUserTodoMap(user.getAccount());
	Map xMap = new TreeMap();

	if(rows != null && rows.size()> 0){
			  Iterator iterator008 = rows.iterator();
			  while(iterator008.hasNext()){
                   ToDoInstance tdi = (ToDoInstance)iterator008.next();
				   int status = TodoConstants.getTodoStatus(tdi);
				   tdi.setStatus(status);
				   ToDo todo = (ToDo)todoMap.get(new Long(tdi.getTodoId()));
				   ToDoInstance xx = (ToDoInstance)dataMap.get(new Long(tdi.getTodoId()));
				    if(todo != null){
						todoxMap.put(new Long(tdi.getTodoId()), todo);
						xMap.put(todo.getTitle(), todo);
					}
                  
				   if(xx == null){
					   xx = new ToDoInstance();
					   xx.setTodoId(tdi.getTodoId());
                       xx.setDeptId(tdi.getDeptId());
					   xx.setRoleCode(tdi.getRoleCode());
					   if(todo != null){
						    xx.setTitle(todo.getTitle());
					        xx.setLink(todo.getLink());
							xx.setListLink(todo.getListLink());
					   }
				   }

                   switch(status){
					    case TodoConstants.OK_STATUS:
							xx.setQty01(xx.getQty01()+1);
							break;
						case TodoConstants.CAUTION_STATUS:
							xx.setQty02(xx.getQty02()+1);
							break;
						case TodoConstants.PAST_DUE_STATUS:
							xx.setQty03(xx.getQty03()+1);
							break;
						default:
							xx.setQty03(xx.getQty03()+1);
							break;
				   }

				   dataMap.put(new Long(tdi.getTodoId()), xx);

			  }
	}

	rows = xMap.values();

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
				   ToDo todo = (ToDo)iterator008.next();
                   ToDoInstance tdi = (ToDoInstance)dataMap.get(new Long(todo.getId()));
				   if(tdi == null){
					   tdi = new ToDoInstance();
				   }

				   if("R011".equals(todo.getRoleCode()) && todo.getId() == 6003){
                        if(!( tdi.getDeptId() == 270 ||  tdi.getDeptId() == 320 || tdi.getDeptId() == 321)){
                              continue;
						}
				   }
				   
				   pageContext.setAttribute("todo", todo);
				   pageContext.setAttribute("tdi",tdi);
			  %>
          <tr class="list-a">
            <td height="20" align="left">
			<a href="<%=context%><c:out value="${todo.listLink}" escapeXml="true"/>&todoId=<c:out value="${todo.id}"/>" title="<c:out value="${todo.title}"/>">
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