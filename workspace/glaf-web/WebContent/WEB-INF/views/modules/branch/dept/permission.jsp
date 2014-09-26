<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*" %> 
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门用户权限列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript">

     function setUserRole(actorId, roleId){
		 var operation="invoke";
         if(!document.getElementById(actorId+"_"+roleId).checked ){
			 operation="revoke";
		 }
		 //alert(operation);
		 jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/branch/department.do?method=setUserRole&actorId='+actorId+'&roleId='+roleId+'&operation='+operation,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					    
				   }
			 });
	}

</script>
</head>
<body leftmargin="0" topmargin="0">  
<center>
<br>
<% 
    List<SysRole> roleList = (List<SysRole>)request.getAttribute("roleList");
	List<SysUser> userList = (List<SysUser>)request.getAttribute("users");
	if(roleList != null && !roleList.isEmpty() && userList != null){
        for(SysRole role: roleList){
%> 
		<table width="95%" height="80" border="0" cellspacing="1" cellpadding="0" class="mainTable">
		  <tr>
			<td colspan="10" class="list-title">
			  <table width="100%" cellspacing="0" cellpadding="0">
			   <tr>
				<td align="center"><font color="#0066ff"><b><%=role.getName()%></b></font></td>
			   </tr>
			  </table>
			</td>
		  </tr>
		  <%
		  int size = userList.size() / 5 ;
		  for(int j=0; j<=size; j++) { 
		  %>
		  <tr>
			<%
			for(int i=j*4; i<(j+1)*4; i++) { 
			  if(i<userList.size()){
                 SysUser user = userList.get(i);
				 String checked = "";
				 if(user.getRoleCodes().contains(role.getCode())){
					 checked = "checked";
				 }
			%>
			<td class="weekend" align="left" height="25" width="180">
			  
			  <input type="checkbox" id="<%=user.getActorId()%>_<%=role.getId()%>" name="id" <%=checked%>
			  onclick="javascript:setUserRole('<%=user.getActorId()%>','<%=role.getId()%>')"  >
				<%				 				      
					out.println(user.getName()+"&nbsp;["+user.getActorId()+"]");	    
				%>
				
			</td>
			<%
			   } else{
			%>
			<td class="weekend" align="left" height="25" width="180">&nbsp;</td>
			<%  }
			
		    }%>
		  </tr>
		  <%}%>
		</table>
		<br>
<%   }
 }
%>
<br>
<br>
<br>
<br>
</center>
</body>  
</html>