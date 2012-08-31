<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ page import="org.jpage.actor.*"%>
<%
    SysUser user = (SysUser) request.getSession().getAttribute(SysConstants.LOGIN);
	if( request.getSession().getAttribute(org.jpage.util.Constant.LOGIN_USER) == null){
		if(user != null){
           User x = new User();
		   x.setActorId(user.getAccount());
		   x.setName(user.getName());
		   request.getSession().setAttribute(org.jpage.util.Constant.LOGIN_USER, x) ;
		}else{
%>
    <script language="JavaScript">
        location.href="<%=request.getContextPath()%>/index.jsp";
    </script>
<%
		}
	}
    String message = (String)request.getAttribute(org.jpage.jbpm.util.Constant.APPLICATION_EXCEPTION_MESSAGE);
	if(message == null){
		message = "应用程序错误！";
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>提示信息</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/workflow/styles/template.jsp" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/workflow/skin/style/styles.css" type="text/css"/>
<style type=text/css>

td{font-size:14px;}
.p1{font-size:12px;}
.p2{
	font-size:14px;
	line-height:normal;
	color: 9C0C00;
}
.p3{
	font-size:12px;
	color: #999999;
}
.p4{font-size:14px;line-height:30px;}
a:hover{color:FF0000;}
.ch{cursor:hand;}
.style1 {font-size:14px;line-height:16px;color:#525552;}
.unnamed1 {height:30px;width:166px;font-size: 16px;font-weight:normal;}

</style>
</head>

<body>
<div id="Header">
 <div id="Ad_2005">
 </div>
</div>
<div id="Title" style=" text-indent:190px; ">
  <span id="Only_use_look"></span>
  提示信息
</div>
<form id="Content" action="" method="post" name="form" >
  <div id="Mid_content">
  
  <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="DAD1D1" class="p4">
    <tr valign="bottom"> 
      <td width="100%" colspan="2" align="center" valign="top" bgcolor="#FFFFFF">
	  <img src="<%=request.getContextPath()%>/workflow/images/tip.jpg" width="96" height="95"><br>
      <%=message%>
	  <br>
	  <br>
	  <input type="button" value="返回" name="back" class="button" onclick="javascript:history.back(0);" >
  </table>
  </div>
</form>
<div id="Footer">glaf基础平台系统</div>
</body>
</html>
