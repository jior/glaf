<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%
	String context = request.getContextPath();
	List list = (List)request.getAttribute("list");
	//Integer roleId = Integer.parseInt(request.getAttribute("roleId").toString());
	SysRole role = (SysRole)request.getAttribute("role");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script type='text/javascript' src="<%=context%>/scripts/glaf-base.js"></script>
<script language="javascript">
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
  if(document.all.departmentIds.value==""){
	  alert("请选择设置权限的部门");
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
function clearSelected(){
	document.all.departmentIds.value="";
	document.all.departmentNames.value="";
}
function cancle(form){
	document.all.saveType.value="1";
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
	    return;
	  }
	  if(document.all.departmentIds.value==""){
		  alert("请选择设置权限的部门");
		  return;
	  }
		form.submit(); 
}
</script>
</head>

<body>
<div class="nav-title"><span class="Title">角色管理</span>&gt;&gt;<%=role.getName()%>角色 <b></b> 全局权限的设置</div>
<html:form action="${contextPath}/sys/deptRole.do?method=setPrivilegeWhole" method="post" target="_self" onsubmit="return checkForm(this);">
<input type="hidden" name="roleId" value="<%=role.getId()%>" />
<input type="hidden" name="saveType" id="saveType" value="0" />
<table width="95%" border="0" align="center" cellspacing="10" cellpadding="0" >
<tr>
	<td align="right" width="20%">选择部门：</td>
	<td width="60%">
	<input type="hidden" id="departmentIds" name="departmentIds" value="">
	<textarea cols="30" id="departmentNames" name="departmentNames" rows="8"  wrap="yes" readonly  
	    style="height:50px;width:320px;color: #000066; background: #ffffff;"></textarea>
	</td>
	<td height="27" width="20%"   valign="bottom" noWrap>
	    &nbsp;&nbsp;<input type="button" name="button" value="添加" class="button" 
		           onclick="javascript:selectDept('iForm', 'departmentIds','departmentNames');"> 
		<br><br>
		&nbsp;&nbsp;<input type="button" name="button" value="清空" class="button"
		           onclick="javascript:clearSelected('departmentIds','departmentNames');"> 
	</td>
</tr>
</table>
<table width="95%" border="0" align="center" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title"  > 
    <td width="5%" class="listborder">序号</td>
    <td width="35%" class="listborder">模块名称</td>
    <td width="60%" class="listborder">操作</td>
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
	  <input type="checkbox" name="access<%=id%>" value="1" >访问权限<br>
    </td>
  </tr>
  <%
    i++;
  }
}
%>
</table>
 
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" height="30" valign="bottom">
	<input name="btn_add" type="submit"  onclick="" value="保存权限" class="button" >
	<input name="btn_add" type="button"  onclick="cancle(this.form);" value="取消权限" class="button" >
	</td>
  </tr>
</table>
</html:form>
<br><br>
</body>
</html>
