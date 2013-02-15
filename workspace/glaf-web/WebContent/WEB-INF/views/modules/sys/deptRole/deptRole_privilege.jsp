<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.utils.*"%>
<%
String context = request.getContextPath();
List list = (List)request.getAttribute("list");
SysDeptRole role = (SysDeptRole)request.getAttribute("role");

Set appId=new HashSet();
Iterator temp = role.getApps().iterator();
while(temp.hasNext()){
  SysApplication bean=(SysApplication)temp.next();
  appId.add(new Long(bean.getId()));
}
Set funcId=new HashSet();
temp = role.getFunctions().iterator();
while(temp.hasNext()){
  SysFunction bean=(SysFunction)temp.next();
  funcId.add(new Long(bean.getId()));
}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="JavaScript">
function checkForm(form){
  var isChecked = false;
  for(var i = 0; i < form.elements.length; i++){
    var e = form.elements[i];
    if(e.name != "chkall" && e.type == "checkbox" && e.checked == true){
      isChecked = true;
      break;
    }
  }
  if(!isChecked){
    alert("您还没有选择要授权的模块.");
    return false;
  }
}
function selApp(id){
  var temp;
  eval("temp=document.all.oper1_"+ id +".checked");
  if(!temp){
    eval("document.all.oper1_"+ id +".checked=true");
  }
}
function selAllApp(id){
  var temp;
  eval("temp=document.all.operall_"+ id +".checked");
  if(!temp){
    eval("document.all.oper1_"+ id +".checked=false");
    eval("document.all.oper2_"+ id +".checked=false");
    eval("document.all.oper3_"+ id +".checked=false");
    eval("document.all.oper4_"+ id +".checked=false");
    eval("document.all.oper5_"+ id +".checked=false");
  }else{
    eval("document.all.oper1_"+ id +".checked=true");
    eval("document.all.oper2_"+ id +".checked=true");
    eval("document.all.oper3_"+ id +".checked=true");
    eval("document.all.oper4_"+ id +".checked=true");
    eval("document.all.oper5_"+ id +".checked=true");  
  }
}
function unSelApp(id){
  var temp;
  eval("temp=document.all.oper1_"+ id +".checked");
  if(!temp){
    eval("document.all.oper2_"+ id +".checked=false");
    eval("document.all.oper3_"+ id +".checked=false");
    eval("document.all.oper4_"+ id +".checked=false");
    eval("document.all.oper5_"+ id +".checked=false");
	eval("document.all.operall_"+ id +".checked=false");
  }
}
</SCRIPT>
</head>

<body>
<div class="nav-title"><span class="Title">角色管理</span>&gt;&gt;设置角色 <b><%=role.getDept().getName()+role.getRole().getName()%></b> 的权限</div>
<html:form action="${contextPath}/sys/deptRole.do?method=setPrivilege" method="post" target="_self" onsubmit="return checkForm(this);">
<input type="hidden" name="roleId" value="<%=role.getId()%>" />
<div style="border:1px solid #FFFFFF;">
<div style="width:100%; height:500px;overflow-x:auto; overflow-y:auto;">
<table width="95%" border="0" align="center" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title" style="position:relative; top:expression(this.offsetParent.scrollTop-2);"> 
    <td width="5%" class="listborder">序号</td>
    <td width="30%" class="listborder">模块名称</td>
    <td width="65%" class="listborder">操作</td>
  </tr>
  <%
int i=0;
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysTree bean=(SysTree)iter.next();
	if(bean.getApp()==null){
	  //out.print(bean.getName());
	  continue;
	}
    long id = bean.getApp().getId();
%>
  <tr <%=i%2==0?"":"class='list-back'"%>>
    <td align="center" class="td-no"><%= i+1%></td>
    <td class="td-text"><%
if(bean.getDeep()>1){  
  for(int j=1; j<=bean.getDeep()-1; j++){
    out.print("　　");
  }
  out.print("--");
}else{
  out.print("＋");
}
out.print(bean.getName());
%>
      &nbsp;
      <input type="hidden" name="appId" value="<%=id%>">
    </td>
    <td class="list">
	  <input type="checkbox" name="access<%=id%>" value="1" <%=appId.contains(new Long(id))?"checked":""%>>访问权限<br>
      <%
			Iterator functions = bean.getApp().getFunctions().iterator();
			int j=0;
			while(functions.hasNext()){
			  SysFunction func = (SysFunction)functions.next();
	  %>
      <input type="checkbox" name="funcId" value="<%=func.getId()%>" <%=funcId.contains(new Long(func.getId()))?"checked":""%>><%=func.getName()%>
      <%
			  if(j%4==3){
			    out.print("<br>");
			  }
			  j++;
			}
	  %>
    </td>
  </tr>
  <%
    i++;
  }
}
%>
</table>
</div></div>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" height="30" valign="bottom"><input name="btn_add" type="submit"  onclick="" value="保存" class="button" ></td>
  </tr>
</table>
</html:form>
</body>
</html>
