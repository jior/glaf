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
	Map params = org.jpage.util.RequestUtil.getQueryParams(request);
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
	System.out.println("params:"+params);
	List rows = bean.getToDoInstanceList(params);
	Map todoMap = bean.getToDoMap();
	Map userMap = bean.getUserMap();
	Map roleMap = bean.getRoleMap();
	Map deptMap = bean.getDepartmentMap();
    StringBuffer userBuffer = new StringBuffer();
	StringBuffer deptBuffer = new StringBuffer();
	StringBuffer roleBuffer = new StringBuffer();
	StringBuffer todoBuffer = new StringBuffer();

	userBuffer.append("<option value=\"\">----请选择----</option>");
	deptBuffer.append("<option value=\"\">----请选择----</option>");
	roleBuffer.append("<option value=\"\">----请选择----</option>");
	todoBuffer.append("<option value=\"\">----请选择----</option>");

    Iterator it001 = userMap.keySet().iterator();
	while(it001.hasNext()){
		 String key = (String)it001.next();
		 SysUser u = (SysUser)userMap.get(key);
         userBuffer.append("\n<option value=\"").append(key).append("\">").
			                append(u.getAccount()).append(" [").append(u.getName()).append("]</option>");
	   }

    Iterator it002 = deptMap.keySet().iterator();
	while(it002.hasNext()){
		 Long key = (Long)it002.next();
		 SysDepartment u = (SysDepartment)deptMap.get(key);
         deptBuffer.append("\n<option value=\"").append(key).append("\">").
			                append(u.getName()).append("</option>");
	   }

    Iterator it003 = roleMap.keySet().iterator();
	while(it003.hasNext()){
		 Long key = (Long)it003.next();
		 SysRole u = (SysRole)roleMap.get(key);
         roleBuffer.append("\n<option value=\"").append(key).append("\">").
			                append(u.getName()).append("</option>");
	   }

    Map map = bean.getMMToDoMap();
	Iterator it004 = map.keySet().iterator();
	while(it004.hasNext()){
		 Long key = (Long)it004.next();
		 ToDo u = (ToDo)map.get(key);
         todoBuffer.append("\n<option value=\"").append(key).append("\">").
			                append(u.getTitle()).append("</option>");
	   }

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

<link href="<%=context%>/js/calendar/skins/aqua/theme.css"  type="text/css" title="Aqua" rel="stylesheet"/>
<script src="<%=context%>/js/calendar/calendar.js" language="javascript"></script>
<script src="<%=context%>/js/calendar/lang/calendar-en.js" language="javascript"></script>
<script src="<%=context%>/js/calendar/lang/calendar-setup.js" language="javascript"></script>
<script type="text/javascript">

</script>
</head>

<body id="document">
<br><br>
<form method="post" action="todo_list.jsp">

<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="box">
        <td class="box-lt">&nbsp;</td>
        <td class="box-mt">&nbsp;</td>
        <td class="box-rt">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="box-mm"><table width="90%" border="0" align="center" cellpadding="5" cellspacing="0">
      <tr>
        <td>用户代码&nbsp;<select name="query_actorId"><%=userBuffer.toString()%></select></td>
        <td>部门代码&nbsp;<select name="query_deptId"><%=deptBuffer.toString()%></select></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
      </tr>
	  <tr>
        <td>角色代码&nbsp;<select name="query_roleId"><%=roleBuffer.toString()%></select></td>
        <td>ToDo事项&nbsp;<select name="query_todoId"><%=todoBuffer.toString()%></select></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
      </tr>
      <tr>
        <td>开始时间
        <input name="query_startDate" type="text" class="input" id="query_startDate" size="10" datatype="date" nullable="no" maxsize="20" chname="预计开始时间" value="" searchflag=1>
         <img src="<%=context%>/images/system_pic_35.gif" style="cursor:pointer" onClick="return showCalendar('query_startDate', '%Y-%m-%d')"></td>
        <td>结束时间
        <input name="query_endDate" type="text" class="input" id="query_endDate" size="10" datatype="date" nullable="no" maxsize="20" chname="预计开始时间" value="" searchflag=1>
        <img src="<%=context%>/images/system_pic_35.gif" style="cursor:pointer" onClick="return showCalendar('query_endDate', '%Y-%m-%d')"></td>
        
		<td  class="td-no"><input type="submit" class="submit-search" value=" "></td>
		<td>&nbsp;</td>
      </tr>
    </table>
</form>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="line">
          <tr>
            <td>&nbsp;</td>
          </tr>
        </table>
      <br>
<table align="center" width="90%" border="0" cellspacing="1" cellpadding="0" class="list-box">
          <tr class="list-title">
            <td align="center">事&nbsp;&nbsp;项</td>
			<td width="95" align="center">用户</td>
			<td width="95" align="center">部门</td>
			<td width="95" align="center">角色</td>
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
				   if(iStatus >0 ){
					   if(iStatus != status){
						   continue;
					   }
				   }
				    SysUser u = (SysUser)userMap.get(tdi.getActorId());
				    if(u != null){
						   tdi.setActorName(u.getName());
				    }
				   SysDepartment d = (SysDepartment)deptMap.get(new Long(tdi.getDeptId()));
				   if(d != null){
						tdi.setDeptName(d.getName());
					}
				   SysRole r = (SysRole)roleMap.get(new Long(tdi.getRoleId()));
				   if(r != null){
				       tdi.setRoleName(r.getName());
				   }
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
			<td height="20" align="left"><c:out value="${tdi.actorName}"/>
			</td>
			<td height="20" align="left"><c:out value="${tdi.deptName}"/>
			</td>
			<td height="20" align="left"><c:out value="${tdi.roleName}"/>
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
</td>
                 
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="box">
        <td class="box-lb">&nbsp;</td>
        <td class="box-mb">&nbsp;</td>
        <td class="box-rb">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
		<br><br>