<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.*"%>
<%@page import="org.jpage.actor.*"%>
<%@page import="org.jpage.jbpm.model.*"%>
<%@ include file="/WEB-INF/pages/common/checkLogin.jsp"%>
<HTML><HEAD>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/pages/common/style.jsp"%>
<LINK href="<%=request.getContextPath()%>/workflow/styles/<c:out value="${frame_skin}"/>/main/main.css" type=text/css rel=stylesheet>
<script language="JavaScript">

</script>
</head>
<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0" >
<br><br>
<table align="center" class="table-border" cellspacing="1" cellpadding="4" width="90%" nowrap>
    <tr class="table-bar" nowrap>
	  <td width="25%" height="15" align="center" class="table-title" nowrap>用户</td>
	  <td width="75%" height="15" align="center" class="table-title" nowrap>代理人</td>
    </tr>
    <c:forEach items="${users}" var="user">
    <tr class="beta">
	  <td width="25%" height="15" align="left">
	  <c:out value="${user.name}"/>[<c:out value="${user.actorId}"/>]&nbsp;<a href="<%=request.getContextPath()%>/workflow/actorController.jspa?method=chooseAgents&actorId=<c:out value="${user.actorId}"/>&objectId=agent&objectValue=<c:out value="${objectValue}"/>"><img src="images/rolegroup.gif" border="0"></a>
	  </td>
      <td width="75%" height="15" align="left"  nowrap>
	      <%
		      User x = (User)pageContext.getAttribute("user");
			  if(x != null){
				 Map map = x.getProperties();
				 if(map !=null && map.size() > 0){
					 Iterator iter = map.values().iterator();
					 while(iter.hasNext()){
						 Object obj = iter.next();
						 if(obj instanceof User){
							 User y = (User)obj;
							 out.println(y.getName()+"["+y.getActorId()+"]&nbsp;&nbsp;");
						 }
					 }
				 }
			  }
		  %>
	  </td>
    </tr>
    </c:forEach>
   </table>

 </BODY>
</HTML>