<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
 
SysDepartment department = (SysDepartment)request.getAttribute("department");
 
Set list = (Set)request.getAttribute("users");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门角色用户列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script> 
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
</head>
<body style="margin-left:5px;">
<div class="nav-title">
   <span class="Title">用户列表</span> 
</div>
<table width="95%" border="0" cellspacing="1" cellpadding="0" class="list-box" style="margin-left:5px;">
  <tr class="list-title"> 
    <td width="5%" align="center">序号</td>
    <td width="8%" align="center" >帐号</td>
    <td width="10%" align="center" >姓名</td>
    <td width="8%" align="center" >是否有效</td>
    <td width="12%" align="center" >创建日期</td>
    <td width="12%" align="center" >上次登陆时间</td>
  </tr>
  <%
int i=0;
if(list!=null && !list.isEmpty()){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysUser bean=(SysUser)iter.next();
%>
  <tr <%=i%2==0?"":"class='list-back'"%>> 

    <td width="5%" class="td-no"><%=(i+1)%></td>
    <td width="8%" class="td-text"><%=bean.getAccount()%>&nbsp;</td>
    <td width="10%" class="td-text"><%=bean.getName()%>&nbsp;</td>
    <td width="8%" class="td-no"><%=bean.getBlocked()==1?"否":"是"%>&nbsp;</td>
    <td width="12%" class="td-time">
	<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(bean.getCreateTime())%>&nbsp;
	</td>
    <td width="12%" align="center" class="list">
	<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(bean.getLastLoginTime())%>&nbsp;
	</td>
    </tr>
  <%
    i++;
  }
}
%>

</table>

</body>
</html>
