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
<%!
      private String getMyValue(String temp){
		  if(temp != null){
			  if(temp.startsWith("[") && temp.indexOf("]") != -1){
				  String value = temp.substring(1,  temp.indexOf("]"));
				  return value;
			  }
		  }
		  return temp;
      }
%>
<%
    String context = request.getContextPath();
    SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);
	
	Map params = com.glaf.util.RequestUtil.getQueryParams(request);
	params.put("actorIdx", user.getAccount());
	String x = request.getParameter("x");
	String y_user = request.getParameter("y_user");
	String y_dept = request.getParameter("y_dept");
	String y_role = request.getParameter("y_role");
	String y_todo = request.getParameter("y_todo");

    if(y_user !=null){
        params.put("actorId", getMyValue(y_user));
	}
	if(y_dept != null){
	    params.put("deptId", getMyValue(y_dept));
	}
	if(y_role != null){
	    params.put("roleId", getMyValue(y_role));
	}
	if(y_todo != null){
    	params.put("todoId", getMyValue(y_todo));
	}

	int iStatus = -1;
	if("OK".equals(x)){
		iStatus = 1;
	}else if("Caution".equals(x)){
		iStatus = 2;
	}else if("Past Due".equals(x)){
		iStatus = 3;
	}

	TodoJobBean bean = (TodoJobBean)BaseDataManager.getInstance().getBean("todoJobBean");
	Collection rows = bean.getTodoInstanceList(params);
	Map dataMap = new TreeMap();
	Map todoMap = bean.getTodoMap();
	Map roleMap = bean.getRoleMap();
	Map xMap = new TreeMap();

	if(rows != null && rows.size()> 0){
			  Iterator iterator008 = rows.iterator();
			  while(iterator008.hasNext()){
                   TodoInstance tdi = (TodoInstance)iterator008.next();
				   int status = TodoConstants.getTodoStatus(tdi);
				   tdi.setStatus(status);
				   TodoInstance xx = (TodoInstance)dataMap.get(new Long(tdi.getTodoId()));
				   if(xx == null){
					   xx = new TodoInstance();
					   xx.setTodoId(tdi.getTodoId());
					   Todo todo = (Todo)todoMap.get(new Long(tdi.getTodoId()));
					   if(todo != null){
						    xx.setTitle(todo.getTitle());
							xx.setContent(todo.getContent());
					        xx.setLink(todo.getLink());
							xx.setListLink(todo.getListLink());
							xMap.put(todo.getTitle(), todo);
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
							break;
				   }

				   dataMap.put(new Long(tdi.getTodoId()), xx);

			  }
	}

	rows = xMap.values();

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

</script>
</head>

<body id="document">
<br><br>
<table align="center" width="90%" border="0" cellspacing="1" cellpadding="0" class="list-box">
          <tr class="list-title">
            <td align="center">事&nbsp;&nbsp;项</td>
			<td align="center">内&nbsp;&nbsp;容</td>
            <td width="95" align="center">PastDue</td>
            <td width="95" align="center">Caution</td>
            <td width="95" align="center">OK</td>
          </tr>
		  <%if(rows != null && rows.size()> 0){
			  Iterator iterator008 = rows.iterator();
			  while(iterator008.hasNext()){
				   Todo todo = (Todo)iterator008.next();
                   TodoInstance tdi = (TodoInstance)dataMap.get(new Long(todo.getId()));
				   if(tdi == null){
					   tdi = new TodoInstance();
				   }
				   pageContext.setAttribute("tdi",tdi);
			  %>
          <tr class="list-a">
            <td height="20" align="left">
			<a href="<%=context%><c:out value="${tdi.listLink}" escapeXml="true"/>&todoId=<c:out value="${tdi.todoId}"/>" target="_blank">
			<c:out value="${tdi.title}"/>
			</a>
			</td>
			<td height="20" align="left">
			<c:out value="${tdi.content}"/>
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