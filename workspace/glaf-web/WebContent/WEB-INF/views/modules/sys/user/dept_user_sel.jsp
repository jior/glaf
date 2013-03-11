<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
List list = (List)request.getAttribute("list");
int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
int roleId = ParamUtil.getIntParameter(request, "roleId", 0);
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
	<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
	<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
	<script language="javascript" src='<%=context%>/scripts/main.js'></script>
	<script type="text/javascript">
      context = "<%=context%>";
    </script>
	<script language="javascript">
	var num=0;
	function checkOperation(form){
	  num = getCheckedBoxNum(form,"id");
	  if(num>0){
	    document.all.btn_sel.disabled=false;
	  }else{
	    document.all.btn_sel.disabled=true;
	  }
	}
	function selDept(parent, refer){
	  var ret = selectDeptList2(parent);
	  if(ret==null) return;
	  if(refer)refer.value=ret[1];
	  $("deptId2").value=ret[0];
	}
	function addUser(form){
	  form.action="<%=context%>/sys/user.do?method=addRoleUser";
	  form.submit();
	}
	</script>
</head>

<body>
<html:form action="${contextPath}/sys/user.do?method=showSelUser" method="post" target="_self"> 
<input type="hidden" name="id" value="0">
<input type="hidden" name="deptId" value="<%=deptId%>"> 
<input type="hidden" name="roleId" value="<%=roleId%>"> 

<table width="95%" border="0" align="center" cellpadding="5" cellspacing="0">
  <tr>
    <td align="center">部门
      <input name="deptTitle" type="text" class="input" size="30" onClick="selDept(5, this)">
	  <input name="deptId2" type="hidden" searchflag="1">
	  <input name="btn_search" type="submit" value=" " class="submit-search">
	</td>
  </tr>
</table>
<div style="width:100%; height:270px;overflow-x:auto; overflow-y:auto;">
<table width="95%" border="0" align="center" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title" style="position:relative; top:expression(this.offsetParent.scrollTop-2);"> 
    <td width="5%" align="center"> 
	<input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);checkOperation(this.form)">    </td>
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
    <td align="center" height="30" valign="bottom"> 
	<input name="btn_sel" type="button" value="选择用户" onClick="addUser(this.form);" class="button" disabled></td>
    </tr>
</table>
</html:form> 
</body>
</html>
