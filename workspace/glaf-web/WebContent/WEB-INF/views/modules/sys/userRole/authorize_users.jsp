<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.core.identity.*"%>
<%@ page import="com.glaf.core.security.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%
	String context = request.getContextPath();
	int pageSize= 15;
	com.glaf.core.util.PageResult pager=(com.glaf.core.util.PageResult)request.getAttribute("pager");
	List list = pager.getResults();
	Map userMap = IdentityFactory.getLongUserMap();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link href="<%=request.getContextPath()%>/scripts/artDialog/skins/default.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/scripts/calendar/skins/aqua/theme.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/plugins/iframeTools.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/verify.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/main.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/calendar/lang/calendar-en.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/calendar/lang/calendar-setup.js"></script>
<script language="javascript">
var context = "<%=request.getContextPath()%>";

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
  //art.dialog.open(url, { height: 580, width: 600, title: "用户授权", lock: true, scrollbars:"no" }, false);
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

<body style="margin-left:15px;">
<div class="nav-title">
<span class="Title">授权管理</span>&gt;&gt;
</div>
<html:form method="post" action="${contextPath}/sys/sysUserRole.do?method=showUsers" target="_self">
<input type="hidden" name="id" value="0">
  <table width="90%" border="0" align="center" cellpadding="5" cellspacing="0">
    <tr>
      <td width="20%">
        部门
        <input name="deptName" type="text" class="input" size="15" title="点击选择部门" readonly onClick="selectDept('5', document.all.deptId, this);">
        <input name="deptId" type="hidden" value="" searchflag="1">	  </td>
      <td width="20%">姓名
        <input name="name" type="text" class="input" searchflag="1" size="10"></td>
      <td width="40%">授权有效日期:
        <input name="startDate" type="text" class="input" id="startDate" size="10" datatype="date" nullable="no" maxsize="20" chname="预计开始时间" value="" readonly searchflag=1>
          <img src="<%=context%>/images/system_pic_35.gif" style="cursor:pointer" onClick="return showCalendar('startDate', '%Y-%m-%d')"> -
          <input name="endDate" type="text" class="input" id="endDate" size="10" datatype="date" nullable="no" maxsize="20" chname="预计结束时间" value="" readonly searchflag=1>
          <img src="<%=context%>/images/system_pic_35.gif" style="cursor:pointer" onClick="return showCalendar('endDate', '%Y-%m-%d')"></td>
      <td width="20%"><input name="btn_search" type="button" value=" " onClick="doSearch(this.form)" class="submit-search"></td>
    </tr>
  </table> 
  <table width="90%" border="0" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title"> 
    <td width="5%" align="center">
	<input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);checkOperation(this.form)">
	</td>
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
			User u = (User)userMap.get(userRole.getAuthorizeFrom());
			if(u != null){
               userRole.setAuthorizeFromName(u.getName());
			}
		    String processDescription = "";
			if(userRole.getProcessDescription() != null){
			    processDescription = userRole.getProcessDescription();
			}
			if(userRole.getAuthorizeFromName() !=null && authorizeFromNames.indexOf(userRole.getAuthorizeFromName())<0){
				processDescriptions = "";
			}
		    if(processDescriptions.indexOf(processDescription)<0){
				contentBuffer += userRole.getProcessDescription()+"[<font color=red>代"+userRole.getAuthorizeFromName();
				contentBuffer += " 有效期：" + DateUtils.getDateTime(userRole.getAvailDateStart()) +"至"+ DateUtils.getDateTime(userRole.getAvailDateEnd()) + "</font>] ";
				contentTitle += userRole.getProcessDescription()+"[代"+userRole.getAuthorizeFromName();
				contentTitle += " 有效期：" + DateUtils.getDateTime(userRole.getAvailDateStart()) +"至"+ DateUtils.getDateTime(userRole.getAvailDateEnd())+"]";
		    }
		    
			authorizeFromNames += userRole.getAuthorizeFromName();
		    processDescriptions += processDescription;
		  
		}		
	  }
	}
%>  
  <tr> 
    <td class="td-cb" height="20"> 
	  <input type="checkbox" name="id" value="<%=bean.getId()%>" onclick="checkOperation(this.form)">    
	</td>
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
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" 
       background="<%=request.getContextPath()%>/images/conetent_2_bg2.jpg">
  <tr> 
    <td height="5" background="<%=request.getContextPath()%>/images/content_2_bg2.jpg"></td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td width="40%"> 
	  <input name="btn_auth" type="button" value="授权" class="button" onclick="javascript:auth(this.form);" disabled>
	</td>
    <td width="40%" align="left">
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
<br/>
<br/>
<br/>
</body>
</html>
 