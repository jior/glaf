<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
SysDepartment department = (SysDepartment)request.getAttribute("department");
List list = (List)request.getAttribute("list");
Set roleId=new HashSet();
Iterator roles = department.getRoles().iterator();
while(roles.hasNext()){
  SysDeptRole deptRole=(SysDeptRole)roles.next();    
  roleId.add(new Long(deptRole.getRole().getId()));  
}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script>
<script language="JavaScript">
function privilege(roleId){
  var url="deptRole.do?method=showPrivilege&deptId=<%=department.getId()%>&roleId="+roleId;
  var width=650;
  var height=600;
  var scroll="no";
  openWindow(url, width, height, scroll);
}
function users(roleId){
  var url="user.do?method=showRoleUser&deptId=<%=department.getId()%>&roleId=" + roleId;
  var width=600;
  var height=350;
  var scroll="no";
  openWindow(url, width, height, scroll);
}
</script>
</head>

<body>
<div class="nav-title"><span class="Title">部门管理</span>&gt;&gt;<%=department.getName()%>-角色列表</div>
<html:form action="/sys/deptRole.do?method=setRole" method="post" target="_self" onsubmit="return confirm('确认要重新设置吗？')"> 
<input type="hidden" name="deptId" value="<%=department.getId()%>"> 
<input type="hidden" name="id" value="0"> 
<div style="width:100%; height:300px;overflow-x:auto; overflow-y:auto;">
<table width="95%" border="0" align="center" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title" style="position:relative; top:expression(this.offsetParent.scrollTop-2);"> 
    <td width="5%" align="center"> <input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);">    </td>
    <td width="5%" align="center">序号</td>
    <td width="35%" align="center">角色名称</td>
    <td width="40%" align="center">权限</td>
  </tr>
  <%
int i=0;
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysRole bean=(SysRole)iter.next();
%>  
  <tr <%=i%2==0?"":"class='list-back'"%>> 
    <td class="td-cb"> <input type="checkbox" name="id" value="<%=bean.getId()%>" <%=roleId.contains(new Long(bean.getId()))?"checked":""%>>    </td>
    <td class="td-no"><%=i+1%></td>
    <td class="td-text"> <%=bean.getName()%>&nbsp; </td>
    <td class="td-text"><a href="javascript:privilege(<%=bean.getId()%>)">设置权限</a> <a href="javascript:users(<%=bean.getId()%>)">用户列表</a></td>
  </tr>
  <%
    i++;
  }
}
for(; i<10; i++){
%>
  <tr <%=i%2==0?"":"class='list-back'"%>> 
    <td height="20">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp; </td>
    <td>&nbsp;</td>
  </tr>
<%
}
%>
</table>

</DIV>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
     <td align="center" height="30" valign="bottom"> <!-- <input name="btn_save" type="submit" value="重新设置" class="button">--></td>
    </tr>
</table>
</html:form> 
</body>
</html>
