<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
int pageSize=Constants.PAGE_SIZE;
SysDepartment department = (SysDepartment)request.getAttribute("department");
PageResult pager=(PageResult)request.getAttribute("pager");
List list = pager.getResults();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="JavaScript">
var num=0;
function checkOperation(form){
  num = getCheckedBoxNum(form,"id");
  if(num>0){
    document.all.btn_del.disabled=false;
	if(num==1){
	  document.all.btn_modify.disabled=false;
	  document.all.btn_role.disabled=false;
	  document.all.btn_reset_pwd.disabled=false;
	}else{
	  document.all.btn_modify.disabled=true;
	  document.all.btn_reset_pwd.disabled=true;
	  document.all.btn_role.disabled=true;
	}
  }else{
    document.all.btn_del.disabled=true;
	document.all.btn_modify.disabled=true;
	document.all.btn_reset_pwd.disabled=true;
	document.all.btn_role.disabled=true;
  }
}

function add(){
  var url="user.do?method=prepareAdd&parent=<%=department.getId()%>";
  var width=450;
  var height=450;
  var scroll="no";
  openWindow(url, width, height, scroll);
}
function modify(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var url="user.do?method=prepareModify&id="+id;
  var width=450;
  var height=450;
  var scroll="no";
  openWindow(url, width, height, scroll);
}

function resetPwd(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var url="user.do?method=prepareResetPwd&id="+id;
  var width=450;
  var height=300;
  var scroll="no";
  openWindow(url, width, height, scroll);
}

function del(){
  var form = document.all.GenericForm;
  if(confirmDelete(form)){
    form.target="hiddenFrame";
    form.action="user.do?method=batchDelete";
	form.submit();
  }
}
function roles(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var url="user.do?method=showRole&user_id="+id;
  var width=450;
  var height=350;
  var scroll="yes";
  openWindow(url, width, height, scroll);
}
</script>
</head>

<body>
<div class="nav-title"><span class="Title">用户管理</span>&gt;&gt;
<%
List nav = (List)request.getAttribute("nav");
Iterator navIter = nav.iterator();
while(navIter.hasNext()){
  SysDepartment bean = (SysDepartment)navIter.next();
%>  
  <%=bean.getName()%>>>
<%
}
%> 
</div>
<html:form action="/sys/user.do?method=batchDelete" method="post" target="_self"> 
<input name="page_no" type="hidden" value="<%=pager.getCurrentPageNo()%>">
<input name="parent" type="hidden" value="<%=department.getId()%>">
<input type="hidden" name="id" value="0">
<table width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title"> 
    <td width="5%" align="center"> <input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);checkOperation(this.form)">    </td>
    <td width="5%" align="center">序号</td>
    <td width="8%" align="center" >帐号</td>
    <td width="10%" align="center" >姓名</td>
    <td width="15%" align="center" >部门</td>
    <td width="20%" align="center" >角色</td>
    <td width="5%" align="center" >是否有效</td>
    <td width="12%" align="center" >创建日期</td>
    <td width="20%" align="center" >上次登陆时间</td>
    </tr>
  <%
int i=0;
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysUser bean=(SysUser)iter.next();
	String roleName = "";
	Set userRoles=bean.getUserRoles();
	if(userRoles!=null){
	  Iterator roleIter = userRoles.iterator();
	  while(roleIter.hasNext()){
	    SysUserRole role = (SysUserRole)roleIter.next();
		if(role.getDeptRole() !=null && role.getDeptRole().getDept() !=null && role.getDeptRole().getRole() != null){
		  roleName+= role.getDeptRole().getDept().getName()+role.getDeptRole().getRole().getName();
		}
		if(role.getAuthorized()==1){
		  roleName+= "[代]";
		}
		roleName+= " ";		
	  }
	}
%>
  <tr <%=i%2==0?"":"class='list-back'"%>> 
    <td class="td-cb"> <input type="checkbox" name="id" value="<%=bean.getId()%>" onClick="checkOperation(this.form)">    </td>
    <td class="td-no"><%=((pager.getCurrentPageNo()-1)*pageSize + i+1)%></td>
    <td class="td-text"><%=bean.getAccount()%>&nbsp;</td>
    <td class="td-text"><%=bean.getName()%>&nbsp;</td>
    <td class="td-c"><%=bean.getDepartment().getName()%>&nbsp;</td>
    <td class="td-text" title="<%=roleName%>"><%=roleName%>
	</td>
    <td class="td-no"><%=bean.getBlocked()==1?"否":"是"%>&nbsp;</td>
    <td class="td-time"><%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(bean.getCreateTime())%>&nbsp;</td>
    <td align="center" class="list"><%=new java.text.SimpleDateFormat("yyyy-MM-dd mm:ss").format(bean.getLastLoginTime())%>&nbsp;</td>
    </tr>
  <%
    i++;
  }
}
for(; i<pageSize; i++){
%>
  <tr <%=i%2==0?"":"class='list-back'"%>> 
    <td height="20">&nbsp;</td>
    <td>&nbsp; </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp; </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
<%
}
%>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td width="60%"> <input name="btn_add" type="button" value="增加" class="button" onClick="javascript:add();"> 
      <input name="btn_del" type="button" value="删除" class="button" onClick="javascript:del();" disabled>
      <input name="btn_modify" type="button" value="修改" class="button" onClick="javascript:modify(this.form);" disabled>
	  <input name="btn_reset_pwd" type="button" value="重置密码" class="button" onClick="javascript:resetPwd(this.form);" disabled>
      <input name="btn_role" type="button" value="角色设置" class="button" onClick="javascript:roles(this.form);" disabled></td>
    <td width="50%"> 
      <%
String params = WebUtil.getQueryString(request);
%>
      <jsp:include page="/WEB-INF/views/inc/show_page.jsp" flush="true"> 
              <jsp:param name="total" value="<%=pager.getTotalRecordCount()%>"/>
              <jsp:param name="page_count" value="<%=pager.getTotalPageCount()%>"/>
              <jsp:param name="page_size" value="<%=pageSize%>"/>
              <jsp:param name="page_no" value="<%=pager.getCurrentPageNo()%>"/>
              <jsp:param name="url" value="user.do"/>
	    <jsp:param name="params" value="<%=java.net.URLEncoder.encode(params)%>"/>        </jsp:include> </td>
  </tr>
</table>
</html:form> 
<script language="javascript">
attachFrame();
</script>
</body>
</html>
