<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*" %> 
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单权限列表</title>
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
				   url: '<%=request.getContextPath()%>/branch/department.do?method=saveUserRole&actorId='+actorId+'&roleId='+roleId+'&operation='+operation,
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
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="菜单权限设置">&nbsp;菜单权限设置</div>

<% 
    List<SysRole> roleList = (List<SysRole>)request.getAttribute("roles");
	if(roleList != null && !roleList.isEmpty()){
        for(SysRole role: roleList){
            List<SysUser> userList= role.getUsers();
             if(userList != null && !userList.isEmpty()){
%> 
		<table width="95%" height="80" border="0" cellspacing="1" cellpadding="0" class="mainTable">
		  <tr>
			<td colspan="10" class="list-title">
			  <table width="100%" cellspacing="0" cellpadding="0">
			   <tr>
				<td align="left"><font color="#0066ff"><b>角色：<%=role.getName()%></b></font></td>
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
				 String checked = "checked";
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
<%      
			 }
      }
 } else{
	  out.println("&nbsp;&nbsp;<br><br>暂时没有授权给任何角色。");
 }
%>
 
<br>
<br>
<br>
<br>
</center>
</body>  
</html>