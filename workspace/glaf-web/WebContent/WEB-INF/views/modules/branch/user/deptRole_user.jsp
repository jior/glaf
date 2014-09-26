<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
Set list = (Set)request.getAttribute("list");
int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
int roleId = ParamUtil.getIntParameter(request, "roleId", 0);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript">
var num=0;
function checkOperation(form){
  num = getCheckedBoxNum(form,"id");
  if(num>0){
    document.all.btn_del.disabled=false;
  }else{
    document.all.btn_del.disabled=true;
  }
}
function addUser(){
  var url="user.do?method=showSelUser&deptId=<%=deptId%>&roleId=<%=roleId%>";
  var width=500;
  var height=350;
  var scroll="no";
  openWindow(url, width, height, scroll);
}
function delUser(form){  
  if(!confirm("确认要删除用户吗？")) return;
  form.target="hiddenFrame";
  form.submit();
}
</script>
</head>

<body>
<div class="nav-title">
<%
List nav = (List)request.getAttribute("nav");
Iterator navIter = nav.iterator();
while(navIter.hasNext()){
  SysDepartment bean = (SysDepartment)navIter.next();
%>  
  <%=bean.getName()%>&gt;&gt; 
<%
}
%>-
角色[<%=ParamUtil.getAttribute(request, "role")%>] 用户列表</div>
<html:form action="${contextPath}/branch/user.do?method=delRoleUser" method="post" target="_self"> 
<input type="hidden" name="deptId" value="<%=deptId%>"> 
<input type="hidden" name="roleId" value="<%=roleId%>"> 
<input type="hidden" name="id" value="0"> 
<div style="width:100%; height:250px;overflow-x:auto; overflow-y:auto;">
<table width="95%" border="0" align="center" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title" style="position:relative; top:expression(this.offsetParent.scrollTop-2);"> 
    <td width="5%" align="center"> <input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);checkOperation(this.form)">    </td>
    <td width="5%" align="center">序号</td>
    <td width="35%" align="center">用户名称</td>
    <td width="40%" align="center">所在部门</td>
  </tr>
  <%
	int i=0;
	if(list!=null){
	  Iterator iter=list.iterator();   
	  while(iter.hasNext()){
		SysUser bean=(SysUser)iter.next();
%>  
  <tr <%=i%2==0?"":"class='list-back'"%>> 
    <td class="td-cb"> <input type="checkbox" name="id" value="<%=bean.getId()%>" onClick="checkOperation(this.form)"></td>
    <td class="td-no"><%=i+1%></td>
    <td class="td-text"> <%=bean.getName()%></td>
    <td class="td-c"><%=bean.getDepartment().getName()%></td>
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
</div>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td align="left" height="30" valign="bottom"> &nbsp;&nbsp;&nbsp;&nbsp;
	  <!-- <input name="btn_add" type="button" value="增加用户" class="button" onClick="addUser()">&nbsp; -->
	  <input name="btn_del" type="button" value="删除用户" class="button" onClick="delUser(this.form)" disabled>
	</td>
  </tr>
</table>
</html:form> 
<script language="javascript">
attachFrame();
</script>
</body>
</html>
