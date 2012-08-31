<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
int pageSize=20;
PageResult pager=(PageResult)request.getAttribute("pager");
List list = pager.getResults();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script>
<script language="JavaScript">
var num=0;
function checkOperation(form){
  num = getCheckedBoxNum(form,"id");
  if(num>0){
    document.all.btn_del.disabled=false;
	if(num==1){
	  document.all.btn_modify.disabled=false;
	  document.all.btn_role.disabled=false;
	}else{
	  document.all.btn_modify.disabled=true;
	  document.all.btn_role.disabled=true;
	}
  }else{
    document.all.btn_del.disabled=true;
	document.all.btn_modify.disabled=true;
	document.all.btn_role.disabled=true;
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
function doSearch(form){
  var url="user.do?method=showPasswordList&" + getSearchElement(form);
  window.location = url;
}
</script>
</head>

<body>

<html:form action="/sys/user.do?method=batchDelete" method="post" target="_self"> 
<input name="page_no" type="hidden" value="<%=pager.getCurrentPageNo()%>">
<input type="hidden" name="id" value="0">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="m-box">
  <tr>
    <td width="49%" class="nav-title">用户查询</td>
    <td width="51%" height="27" align="right"><jsp:include page="/WEB-INF/views/module/login_info.jsp" flush="true"/> &nbsp;&nbsp;&nbsp;</td>
  </tr>
</table>
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
    <td class="box-mm">
<table width="730" border="0" align="center" cellpadding="5" cellspacing="0">
    <tr>
    <td width="170" class="input-box">部门
        <input name="deptName" type="text" class="input" size="15" title="点击选择部门" readonly onClick="selectDept('5', document.all.deptId, this);">
    <input name="deptId" type="hidden" value="" searchflag="1">	  </td>
    <td width="150" class="input-box">帐号：
        <input name="account" type="text" class="input" searchflag="1" size="10">
    </td>
    <td width="150" class="input-box">姓名：
        <input name="userName" type="text" class="input" searchflag="1" size="10">
    </td>
	<td width="30"><input name="btn_search" type="button" value=" " onClick="doSearch(this.form)" class="submit-search"></td>
	</tr>
</table> 
<table width="99%" border="0" align="center" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title"> 
    <td width="5%" height="20" align="center">序号</td>
    <td width="8%" align="center" >帐号</td>
    <td width="10%" align="center" >姓名</td>
    <td width="15%" align="center" >密码</td>
    <td width="15%" align="center" >部门</td>
    <td width="20%" align="center" >角色</td>
    <td width="5%" align="center" >是否有效</td>
    <td width="10%" align="center" >创建日期</td>
    <td width="15%" align="center" >上次登陆时间</td>
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
		roleName+= role.getDeptRole().getDept().getName()+role.getDeptRole().getRole().getName();
		if(role.getAuthorized()==1){
		  roleName+= "[代]";
		}
		roleName+= " ";		
	  }
	}
%>
  <tr <%=i%2==0?"":"class='list-back'"%>> 
    <td class="td-no" height="20"><%=((pager.getCurrentPageNo()-1)*pageSize + i+1)%></td>
    <td class="td-no"><%=bean.getAccount()%></td>
    <td class="td-text"><%=bean.getName()%></td>
    <td class="td-no"><%=CryptUtil.DeCryptPassword(bean.getPassword())%></td>
    <td class="td-text"><%=bean.getDepartment().getName()%></td>
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp; </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
<%
}
%>
</table>
<br/>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
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
</html:form> 
<script language="javascript">
attachFrame();
</script>
</body>
</html>
