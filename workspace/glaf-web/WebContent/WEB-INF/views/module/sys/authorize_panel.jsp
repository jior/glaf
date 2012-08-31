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
SysUser user = (SysUser)request.getAttribute("user");
List list = (List)request.getAttribute("authorizedUser");
List processList = (List)request.getAttribute("processList");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>基础平台系统</title>
<base target="_self">
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script>
<link href="<%=request.getContextPath()%>/js/calendar/skins/aqua/theme.css"  type="text/css" title="Aqua" rel="stylesheet"/>
<script src="<%=context%>/js/calendar/calendar.js" language="javascript"></script>
<script src="<%=context%>/js/calendar/lang/calendar-en.js" language="javascript"></script>
<script src="<%=context%>/js/calendar/lang/calendar-setup.js" language="javascript"></script>
<script type='text/javascript' src='<%=context%>/dwr/interface/SysUserRoleAjaxService.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/util.js'></script>
</head>
<script language="javascript">
var fromUserId=<%=user.getId()%>;
var num=0;
var num2=0;
var mark=0;
function checkOperation(form){
  num = getCheckedBoxNum(form,"id");
  if(num>0){
    document.all.btn_cancel.disabled=false;
  }else{
    document.all.btn_cancel.disabled=true;
  }
}
function checkOperation2(form){
  num2 = getCheckedBoxNum(form,"processId");
  if(num2>0){
    document.all.btn_save.disabled=false;
  }else{
    document.all.btn_save.disabled=true;
  }
}
function checkAll_1(form, obj) {
  for (var i=0;i<form.elements.length;i++) {
    var e = form.elements[i];
    if (e.type=='checkbox' && e.disabled==false && e.name=="id"){
		 e.checked = obj.checked;
	}
  }
}
function checkAll_2(form, obj) {
  if(obj.checked){
		mark=1;//表示全局代理
		document.all.btn_save.disabled=false;
  }else{
		mark=0;
		document.all.btn_save.disabled=true;
  }
  for (var i=0;i<form.elements.length;i++) {
    var e = form.elements[i];
    if (e.type=='checkbox' && e.name=="processId"){
		 e.checked = obj.checked;
	}
	if(e.type=='checkbox' && e.name=="processId"){
		if(obj.checked){
			e.disabled = true;
		}else{
			e.disabled = false;
		}
	}
  }
}
function selectSysUser(referId,referTitle){
  var url = '<%=context%>/sys/user.do?method=selectSysUser&multDate=1';
  return ShowDialog(url,430,450,false,false,referId, referTitle, false);
}

function cancel(form){
  if(confirm("真的要取消授权吗？")){
    var ids="";	
    for (var i=0; i<form.id.length; i++) {
      var e = form.id[i];
      if (e.checked) ids = ids + e.value + ",";
	}
	SysUserRoleAjaxService.removeRoleUser(fromUserId, ids, function(reply){
      if(reply){
        $("link").click();
      }
    });
  }
}
function add(){
  var list = document.getElementsByName("processId");
  var processNames = "";
  var processDescriptions = "";
  for (var i=0;i<list.length;i++) {
	var e = list[i];
	if (e.checked){
		processNames += e.getAttribute("processName")+",";
		processDescriptions += e.getAttribute("processDescription")+",";
	}
  }
  if($("userId").value.length==0){
    alert("请选择用户");
	return;
  }
  if($("startDate").value.length==0){
    alert("请选择开始日期");
	return;
  }
  if($("endDate").value.length==0){
    alert("请选择结束日期");
	return;
  }
  if(checkDate($("startDate").value, $("endDate").value, true)){
    alert("开始日期不能晚于结束日期");
	return;
  }
  if(confirm("确认要授权吗？")){
    SysUserRoleAjaxService.addRole(fromUserId, $("userId").value,$("startDate").value,$("endDate").value,mark,processNames,processDescriptions, function(reply){
      if(reply){	    
        $("link").click();
      }else{
	    alert($("userName").value + "不能重复授权!");
	  }
    });
  }
}
</script>
<body>
<a href="<%=context%>/sys/sysUserRole.do?method=showSysAuth&id=<%=user.getId()%>" id="link"></a>
<html:form method="post" action="/sys/sysUserRole.do?method=showSysAuth" target="_self">
<input type="hidden" name="id" value="0">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="27" colspan="4" align="center"><b>用户<%=user.getName()%>[<%=user.getAccount()%>]的权限</b></td>
  </tr>
</table>
<div id="listDiv" style="width:595px; height:220px;overflow-x:auto; overflow-y:auto;">
<table width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title" style="position:relative; top:expression(this.offsetParent.scrollTop-2);">
    <td width="10%" align="center">选择</td>
    <td width="25%" align="center">工作流模块</td>
    <td width="30%" align="center">工作流描述</td>
    <td width="35%" align="center">工作流名称</td>
  </tr>
  <tr>
    <td class="td-cb" height="20">
	<input type="checkbox" name="chkall" value="checkbox" onClick="checkAll_2(this.form, this);">
	</td>
    <td class="td-text" colspan="3" title="全局代理">全局代理</td>
  </tr>	
<%
if(null!=processList && processList.size()>0){
	Iterator iter2 = processList.iterator();
	while(iter2.hasNext()){
	  Object[] bean = (Object[])iter2.next();
	  String moduleName = bean[0].toString();
	  String processName = bean[1].toString();
	  String objectValue = bean[2].toString();
%>
  <tr>
    <td class="td-cb" height="20">
	<input type="checkbox" name="processId" value="" processName="<%=processName%>" processDescription="<%=objectValue%>" onClick="checkOperation2(this.form)">
	</td>
    <td class="td-text" title="<%=moduleName%>"><%=moduleName%></td>
    <td class="td-text" title="<%=objectValue%>"><%=objectValue%></td>
    <td class="td-text" title="<%=processName%>"><%=processName%></td>
  </tr>	
<%
}
}
%>
</table>
</div>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="22">&nbsp;</td>
    <td height="22">&nbsp;</td>
  </tr>
  <tr>
    <td height="22">&nbsp;</td>
    <td height="22">授权给</td>
  </tr>
  <tr>
    <td height="22">&nbsp;</td>
    <td height="22">用　户:
      <input name="userName" type="text" class="input" size="30" title="点击选择用户" readonly onClick="selectSysUser( $('userId'), $('userName'))">
	<input type="hidden" name="userId" value="">  
	</td>
  </tr>
  <tr>
    <td height="22">&nbsp;</td>
    <td height="22">有效期:
      <input name="startDate" type="text" class="input" id="startDate" size="10" datatype="date" nullable="no" maxsize="20" chname="预计开始时间" value="" readonly searchflag=1>
      <img src="<%=context%>/images/system_pic_35.gif" style="cursor:pointer" onClick="return showCalendar('startDate', '%Y-%m-%d')"> -
      <input name="endDate" type="text" class="input" id="endDate" size="10" datatype="date" nullable="no" maxsize="20" chname="预计结束时间" value="" readonly searchflag=1>
      <img src="<%=context%>/images/system_pic_35.gif" style="cursor:pointer" onClick="return showCalendar('endDate', '%Y-%m-%d')"> </td>
  </tr>
  <tr>
    <td height="22">&nbsp;</td>
    <td height="22"><input type="button" name="btn_save" value="确定" onClick="add()" disabled></td>
  </tr>
</table>
<br/>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="m-box">
  <tr>
    <td>&nbsp;</td>
    <td height="27" align="center"><b>用户<%=user.getName()%>[<%=user.getAccount()%>]的权限</b></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>已授权给</td>
    </tr>
</table> 
<div id="listDiv" style="width:595px; height:120px;overflow-x:auto; overflow-y:auto;">
<table width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title" style="position:relative; top:expression(this.offsetParent.scrollTop-2);">
    <td width="10%" align="center"><input type="checkbox" name="chkall" value="checkbox" onClick="checkAll_1(this.form, this);checkOperation(this.form)"></td>
    <td width="20%" align="center">用户</td>
    <td width="25%" align="center">有效期</td>
    <td width="45%" align="center">授权内容</td>
  </tr>
<%
Iterator iter = list.iterator();
while(iter.hasNext()){
  Object[] bean = (Object[])iter.next();
  SysUser user2 = (SysUser)bean[0];
  Date startDate=(Date)bean[1];
  Date endDate=(Date)bean[2];
  String processDescription = null==bean[3]?"":bean[3].toString();
%>
  <tr>
    <td class="td-cb" height="20" ><input type="checkbox" name="id" value="<%=user2.getId()%>" onClick="checkOperation(this.form)"></td>
    <td class="td-text" title="<%=user2.getName()%>[<%=user2.getCode()%>]"><%=user2.getName()%>[<%=user2.getCode()%>]</td>
    <td class="td-date" ><%=BaseUtil.dateToString(startDate)%> - <%=BaseUtil.dateToString(endDate)%></td>
    <td class="td-date" title="<%=processDescription%>"><%=processDescription%></td>
  </tr>	
<%
}
%>
</table>
</div>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="22">&nbsp;&nbsp;<input type="button" name="btn_cancel" value="取消授权" onClick="cancel(this.form)" disabled></td>
  </tr>
</table>
</html:form>
</body>
</html>
