<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.utils.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
int pageSize=2*Constants.PAGE_SIZE;
PageResult pager=(PageResult)request.getAttribute("pager");
List list = pager.getResults();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=request.getContextPath()%>/scripts/verify.js'></script>
<script language="javascript" src='<%=request.getContextPath()%>/scripts/main.js'></script>
<link href="<%=request.getContextPath()%>/scripts/calendar/skins/aqua/theme.css"  type="text/css" title="Aqua" rel="stylesheet"/>
<script src="<%=context%>/scripts/calendar/calendar.js" language="javascript"></script>
<script src="<%=context%>/scripts/calendar/lang/calendar-en.js" language="javascript"></script>
<script src="<%=context%>/scripts/calendar/lang/calendar-setup.js" language="javascript"></script>

<script language="JavaScript">
var num=0;
function checkOperation(form){
  num = getCheckedBoxNum(form,"id");
  if(num>0){
	if(num==1){
	  document.all.btn_auth.disabled=false;
	}else{
	  document.all.btn_auth.disabled=true;
	}
  }else{
    document.all.btn_auth.disabled=true;
  }
}
function auth(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var url = context + "/sys/sysUserRole.do?method=showSysAuth&id="+id;
  var features="dialogHeight:580px; dialogWidth:600px; center: yes; resizable: no; status: no; help:no";
  window.showModalDialog(url, window, features);
  //window.open(url);
}
function doSearch(form){
  var url="sysUserRole.do?method=showUsers&" + getSearchElement(form);
  window.location = url;
}

 function setIframeHeigh(){
	var frameId = document.getElementById("mainFrame");
	frameId.style.pixelHeight = frameId.Document.body.scrollHeight+20;
	frameId.style.pixelWidth = frameId.Document.body.scrollWidth+20;
 }
</script>
</head>

<body>
<div class="nav-title">
<span class="Title">授权管理</span>&gt;&gt;
</div>
<html:form method="post" action="/sys/sysUserRole.do?method=showUsers" target="_self">
<input type="hidden" name="id" value="0">
  <table width="90%" border="0" align="center" cellpadding="5" cellspacing="0">
    <tr>
      <td width="170">
        部门
        <input name="deptName" type="text" class="input" size="15" title="点击选择部门" readonly onClick="selectDept('5', document.all.deptId, this);">
        <input name="deptId" type="hidden" value="" searchflag="1">	  </td>
      <td width="150">姓名
        <input name="name" type="text" class="input" searchflag="1" size="10"></td>
      <td width="380">授权有效日期:
        <input name="startDate" type="text" class="input" id="startDate" size="10" datatype="date" nullable="no" maxsize="20" chname="预计开始时间" value="" readonly searchflag=1>
          <img src="<%=context%>/images/system_pic_35.gif" style="cursor:pointer" onClick="return showCalendar('startDate', '%Y-%m-%d')"> -
          <input name="endDate" type="text" class="input" id="endDate" size="10" datatype="date" nullable="no" maxsize="20" chname="预计结束时间" value="" readonly searchflag=1>
          <img src="<%=context%>/images/system_pic_35.gif" style="cursor:pointer" onClick="return showCalendar('endDate', '%Y-%m-%d')"></td>
      <td width="30"><input name="btn_search" type="button" value=" " onClick="doSearch(this.form)" class="submit-search"></td>
    </tr>
  </table> 
  <table width="90%" border="0" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title"> 
    <td width="5%" align="center"><input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);checkOperation(this.form)"></td>
    <td width="5%" align="center">序号</td>
    <td width="10%" align="center">名称</td>
    <td width="15%" align="center">部门</td>
    <td width="65%" align="center">代理权限内容</td>
  </tr>
<%
int i=0;
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysUser bean=(SysUser)iter.next();	
	Set userRoles=bean.getUserRoles();
	String processDescriptions = "";
	String authorizeFromNames = "";
	String contentBuffer = "";
	String contentTitle = "";
	if(userRoles!=null){
	  Iterator userRoleIter = userRoles.iterator();
	  while(userRoleIter.hasNext()){
	    SysUserRole userRole = (SysUserRole)userRoleIter.next();
		if(userRole.getAuthorized()==1){
		    String processDescription = null == userRole.getProcessDescription()?"":userRole.getProcessDescription();
			if(authorizeFromNames.indexOf(userRole.getAuthorizeFrom().getName())<0)
				processDescriptions = "";
		    if(processDescriptions.indexOf(processDescription)<0){
				contentBuffer += userRole.getProcessDescription()+"[<font color=red>代"+userRole.getAuthorizeFrom().getName();
				contentBuffer += " 有效期：" + BaseUtil.dateToString(userRole.getAvailDateStart()) +"至"+ BaseUtil.dateToString(userRole.getAvailDateEnd()) + "</font>] ";
				contentTitle += userRole.getProcessDescription()+"[代"+userRole.getAuthorizeFrom().getName();
				contentTitle += " 有效期：" + BaseUtil.dateToString(userRole.getAvailDateStart()) +"至"+ BaseUtil.dateToString(userRole.getAvailDateEnd())+"]";
		    }
		    //if(null==processDescription || "".equals(processDescription)){
		    //	if(authorizeFromNames.indexOf(userRole.getAuthorizeFrom().getName())<0){
			//    	contentBuffer += "全局代理"+"[<font color=red>代"+userRole.getAuthorizeFrom().getName();
			//		contentBuffer += " 有效期：" + BaseUtil.dateToString(userRole.getAvailDateStart()) +"至"+ BaseUtil.dateToString(userRole.getAvailDateEnd()) + "</font>] ";
		    //	}
		    //}
			authorizeFromNames += userRole.getAuthorizeFrom().getName();
		    processDescriptions += processDescription;
		  //out.print(userRole.getDeptRole().getRole().getName());
		  //out.print(userRole.getProcessDescription());
		  //out.print("[<font color=red>代");
		  //out.print(userRole.getAuthorizeFrom().getName());
		  //out.print(" 有效期：" + BaseUtil.dateToString(userRole.getAvailDateStart()) +"至"+ BaseUtil.dateToString(userRole.getAvailDateEnd()));
		  //out.print("</font>] ");
		}		
	  }
	}
%>  
  <tr> 
    <td class="td-cb" height="20"> <input type="checkbox" name="id" value="<%=bean.getId()%>" onClick="checkOperation(this.form)">    </td>
    <td class="td-no"><%=(pager.getCurrentPageNo()-1)*10 + i+1%></td>
    <td class="td-text"><%=bean.getName()%></td>
    <td class="td-no"><%=bean.getDepartment().getName()%></td>
    <td class="td-text" title="<%=contentTitle%>"><%=contentBuffer%></td>
  </tr>
<%
    i++;
  }
}
for(; i<pageSize; i++){
%>
  <tr> 
    <td class="td-cb" height="20">&nbsp;</td>
    <td class="td-no">&nbsp;</td>
    <td class="td-text">&nbsp; </td>
    <td class="td-no">&nbsp;</td>
    <td class="td-no">&nbsp;</td>
  </tr>
<%
}
%>	
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" background="<%=request.getContextPath()%>/images/conetent_2_bg2.jpg">
  <tr> 
    <td height="5" background="<%=request.getContextPath()%>/images/content_2_bg2.jpg"></td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td width="40%"> 
	  <input name="btn_auth" type="button" value="授权" class="button" onClick="javascript:auth(this.form);" disabled></td>
    <td width="40%" align="center">
<%
String params = WebUtil.getQueryString(request);
%>
    <jsp:include page="/WEB-INF/views/inc/show_page.jsp" flush="true">
      <jsp:param name="total" value="<%=pager.getTotalRecordCount()%>"/>      
      <jsp:param name="page_count" value="<%=pager.getTotalPageCount()%>"/>      
      <jsp:param name="page_size" value="<%=pageSize%>"/>      
      <jsp:param name="page_no" value="<%=pager.getCurrentPageNo()%>"/>      
      <jsp:param name="url" value="sysUserRole.do"/>      
      <jsp:param name="params" value="<%=java.net.URLEncoder.encode(params)%>"/>      
    </jsp:include>	
	</td>
  </tr>
</table> 
</html:form>
</body>
</html>
 