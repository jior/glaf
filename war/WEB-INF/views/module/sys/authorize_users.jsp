<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title></title>
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=request.getContextPath()%>/js/verify.js'></script>
<script language="javascript" src='<%=request.getContextPath()%>/js/main.js'></script>
<link href="<%=request.getContextPath()%>/js/calendar/skins/aqua/theme.css"  type="text/css" title="Aqua" rel="stylesheet"/>
<script src="<%=context%>/js/calendar/calendar.js" language="javascript"></script>
<script src="<%=context%>/js/calendar/lang/calendar-en.js" language="javascript"></script>
<script src="<%=context%>/js/calendar/lang/calendar-setup.js" language="javascript"></script>

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

function maxium() {  
  if (document.layers) {  
    width=screen.availWidth;  
    height=screen.availHeight;  
  } else {  
    var width=screen.availWidth;  
    var height=screen.availHeight;  
  }  
  self.resizeTo(width, height);  
  self.moveTo(0, 0);  
}
</script>
</head>

<body onLoad="maxium();">
<div class="nav-title"><span class="Title">��Ȩ����</span>&gt;&gt;

</div>
<html:form method="post" action="/sys/sysUserRole.do?method=showUsers" target="_self">
<input type="hidden" name="id" value="0">
  <table width="730" border="0" align="center" cellpadding="5" cellspacing="0">
    <tr>
      <td width="170">
        ����
        <input name="deptName" type="text" class="input" size="15" title="���ѡ����" readonly onClick="selectDept('5', document.all.deptId, this);">
        <input name="deptId" type="hidden" value="" searchflag="1">	  </td>
      <td width="150">����
        <input name="name" type="text" class="input" searchflag="1" size="10"></td>
      <td width="380">��Ȩ��Ч����:
        <input name="startDate" type="text" class="input" id="startDate" size="10" datatype="date" nullable="no" maxsize="20" chname="Ԥ�ƿ�ʼʱ��" value="" readonly searchflag=1>
          <img src="<%=context%>/images/system_pic_35.gif" style="cursor:pointer" onClick="return showCalendar('startDate', '%Y-%m-%d')"> -
          <input name="endDate" type="text" class="input" id="endDate" size="10" datatype="date" nullable="no" maxsize="20" chname="Ԥ�ƽ���ʱ��" value="" readonly searchflag=1>
          <img src="<%=context%>/images/system_pic_35.gif" style="cursor:pointer" onClick="return showCalendar('endDate', '%Y-%m-%d')"></td>
      <td width="30"><input name="btn_search" type="button" value=" " onClick="doSearch(this.form)" class="submit-search"></td>
    </tr>
  </table> 
  <table width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title"> 
    <td width="5%" align="center"><input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);checkOperation(this.form)"></td>
    <td width="5%" align="center">���</td>
    <td width="10%" align="center">����</td>
    <td width="15%" align="center">����</td>
    <td width="65%" align="center">����Ȩ������</td>
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
				contentBuffer += userRole.getProcessDescription()+"[<font color=red>��"+userRole.getAuthorizeFrom().getName();
				contentBuffer += " ��Ч�ڣ�" + BaseUtil.dateToString(userRole.getAvailDateStart()) +"��"+ BaseUtil.dateToString(userRole.getAvailDateEnd()) + "</font>] ";
				contentTitle += userRole.getProcessDescription()+"[��"+userRole.getAuthorizeFrom().getName();
				contentTitle += " ��Ч�ڣ�" + BaseUtil.dateToString(userRole.getAvailDateStart()) +"��"+ BaseUtil.dateToString(userRole.getAvailDateEnd())+"]";
		    }
		    //if(null==processDescription || "".equals(processDescription)){
		    //	if(authorizeFromNames.indexOf(userRole.getAuthorizeFrom().getName())<0){
			//    	contentBuffer += "ȫ�ִ���"+"[<font color=red>��"+userRole.getAuthorizeFrom().getName();
			//		contentBuffer += " ��Ч�ڣ�" + BaseUtil.dateToString(userRole.getAvailDateStart()) +"��"+ BaseUtil.dateToString(userRole.getAvailDateEnd()) + "</font>] ";
		    //	}
		    //}
			authorizeFromNames += userRole.getAuthorizeFrom().getName();
		    processDescriptions += processDescription;
		  //out.print(userRole.getDeptRole().getRole().getName());
		  //out.print(userRole.getProcessDescription());
		  //out.print("[<font color=red>��");
		  //out.print(userRole.getAuthorizeFrom().getName());
		  //out.print(" ��Ч�ڣ�" + BaseUtil.dateToString(userRole.getAvailDateStart()) +"��"+ BaseUtil.dateToString(userRole.getAvailDateEnd()));
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
    <td width="50%"> 
	  <input name="btn_auth" type="button" value="��Ȩ" class="button" onClick="javascript:auth(this.form);" disabled></td>
    <td width="50%" align="right">
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
