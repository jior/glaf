<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
int pageSize=5;
List list = (List)request.getAttribute("list");
int parent=ParamUtil.getIntParameter(request, "parent", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/interface/SysFunctionAjaxService.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/util.js'></script>
<script language="javascript">
var num=0;
function checkOperation(form){
  num = getCheckedBoxNum(form,"id");
  if(num>0){
    document.all.btn_del.disabled=false;
	if(num==1){
	  document.all.btn_modify.disabled=false;
	}else{
	  document.all.btn_modify.disabled=true;
	}
  }else{
    document.all.btn_del.disabled=true;
	document.all.btn_modify.disabled=true;
  }
}
function del(form){
  if(confirmDelete(form)){
    form.target="hiddenFrame";
    form.action="function.do?method=batchDelete";
	form.submit();
  }
}
function create(){
  var newDiv = document.all.newDiv;
  newDiv.style.display="block";
  var modifyDiv = document.all.modifyDiv;
  modifyDiv.style.display="none";
}
function modify(form){
  var modifyDiv= document.all.modifyDiv;
  modifyDiv.style.display="block";
  var newDiv = document.all.newDiv;
  newDiv.style.display="none";
  
  var id =0;
  var i=0;
  for (;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	  break;
	}
  }
  var modifyForm = document.all.modifyForm;
  modifyForm.funcId.value=form.id[i].value;
  modifyForm.funcName.value=form.funcName[i].value;
  modifyForm.funcMethod.value=form.funcMethod[i].value;
}
function sort(id, operate){  
  SysFunctionAjaxService.sort(id, operate, {callback:function (){reloadPage();}});
}
</script>
</head>

<body>
<div class="nav-title"><span class="Title">模块管理</span>&gt;&gt;功能列表</div>
<html:form action="/sys/function.do?method=batchDelete" method="post" target="_self"> 
<input type="hidden" name="parent" value="<%=parent%>">
<input type="hidden" name="id" value="0">
<input type="hidden" name="funcName" value="">
<input type="hidden" name="funcMethod" value="">
<table width="95%" border="0" cellspacing="1" cellpadding="0" class="list-box" align="center">
  <tr class="list-title"> 
    <td width="5%" align="center"> <input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);checkOperation(this.form)">    </td>
    <td width="20%" align="center">功能名称</td>
    <td width="60%" align="center">调用方法</td>
    <td width="15%" align="center">排序</td>
  </tr>
<%
int i=0;
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysFunction bean=(SysFunction)iter.next();	
%>
  <tr> 
    <td align="center" class="td-cb"> <input type="checkbox" name="id" value="<%=bean.getId()%>" onClick="checkOperation(this.form);">   
	<input type="hidden" name="funcName" value="<%=bean.getName()%>">
	<input type="hidden" name="funcMethod" value="<%=bean.getFuncMethod()%>">
	</td>
    <td class="td-text"><%=bean.getName()%>&nbsp;</td>
    <td class="td-text"><%=bean.getFuncMethod()%>&nbsp;</td>
    <td class="td-no"> 
      <a href="#" onClick="modify(<%=bean.getId()%>, '<%=bean.getName()%>', '<%=bean.getFuncMethod()%>')"></a>
	  <a href="javascript:sort(<%=bean.getId()%>, 0);" title="上移"><img src="<%=context%>/images/up.gif" border="0" height="13" width="13"></a>
	  <a href="javascript:sort(<%=bean.getId()%>, 1);" title="下移"><img src="<%=context%>/images/down.gif" border="0" height="13" width="13"></a>	</td>
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
    <td>&nbsp; </td>
    <td>&nbsp;</td>
  </tr>
<%
}
%>
</table>
<br>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td width="50%"><input name="btn_new" type="button" value="新增" class="button" onClick="javascript:create();">
      <input name="btn_del" type="button" value="删除" class="button" onClick="javascript:del(this.form);" disabled>
      <input name="btn_modify" type="button" value="修改" class="button" onClick="javascript:modify(this.form);" disabled></td>
    <td width="50%">&nbsp;</td>
  </tr>
</table>
</html:form> 
<br>
<div id="newDiv" style="display:none">
<html:form action="/sys/function.do?method=saveAdd" method="post" target="hiddenFrame" onsubmit="return verifyAll(this);" > 
<input type="hidden" name="parent" value="<%=parent%>">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
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
		<table width="95%" border="0" align="center" cellpadding="0" cellspacing="2">
		  <tr>
			<td class="fontname_12">功能名称*</td>
			<td>
			<input name="funcName" type="text" size="80" class="input" datatype="string" nullable="no" maxsize="80" chname="功能名称">
			</td>
		  </tr>
		  <tr>
			<td width="15%" class="fontname_12">调用方法*</td>
			<td width="85%">
			<input name="funcMethod" type="text" size="80" class="input" datatype="string" nullable="no" maxsize="200" chname="调用方法">
			</td>
		  </tr>
		  <tr>
			<td><input name="btn_save" type="submit" value="保存" class="button"></td>
			<td>&nbsp;</td>
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
</div>
<div id="modifyDiv" style="display:none">
<form name="modifyForm" action="function.do?method=saveModify" method="post" target="hiddenFrame" onSubmit="return verifyAll(this);" > 
<input type="hidden" name="funcId" value="">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
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
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="2">
  <tr>
    <td class="fontname_12">功能名称：</td>
    <td><input name="funcName" type="text" size="80" class="input" datatype="string" nullable="no" maxsize="80" chname="功能名称"></td>
  </tr>
  <tr>
    <td width="15%" class="fontname_12">调用方法：</td>
    <td width="85%"><input name="funcMethod" type="text" size="80" class="input" datatype="string" nullable="no" maxsize="200" chname="调用方法"></td>
  </tr>
  <tr>
    <td><input name="btn_save" type="submit" value="保存" class="button"></td>
    <td>&nbsp;</td>
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
</form>
</div>
<script language="javascript">
attachFrame();
</script>
</body>
</html>
