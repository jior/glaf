<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.utils.*"%>
<%@ page import="com.glaf.base.modules.others.*"%>
<%@ page import="com.glaf.base.modules.others.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
int referType=ParamUtil.getIntParameter(request, "referType", 0);
int referId=ParamUtil.getIntParameter(request, "referId", 0);
String context = request.getContextPath();
List list = (List)request.getAttribute("list");
%>
<html>
<base target="_self" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>XXXX基础平台系统</title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/interface/AttachmentAjaxService.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/util.js'></script>
<script language="javascript">
function uploadFile(){
  var file = openUpload();
  if(file==null || file==""){
  }else{
  	AttachmentAjaxService.create(<%=referType%>, <%=referId%>, file, {callback:function (){reloadPage();refreshCount();}});
  }
}
var num=0;
function checkOperation(form){
  num = getCheckedBoxNum(form,"id");
  if(num>0){
    document.all.btn_del.disabled=false;
  }else{
    document.all.btn_del.disabled=true;
  }
}
function del(form){
  if(confirmDelete(form)){
    form.target="hiddenFrame";
    form.action="attachment.do?method=batchDelete";
	form.submit();
  }
  refreshCount();
}
function refreshCount() {
  if (window.opener) {
	  try {
	    window.opener.location.reload();//getCount<%= referType + "" + referId %>();
		} catch(e){}
	}else{
	window.parent.location.reload();
}
}
</script>
</head>

<body>
<html:form action="/others/attachment.do?method=batchDelete" method="post">
<table width="98%" id="main" border="0" cellspacing="1" cellpadding="0" align="center">
  <tr>
    <td height="20">附件列表</td>
  </tr>
</table>
<div style="width:100%; height:200px;overflow-x:auto; overflow-y:auto;">
<table width="98%" id="main" border="0" cellspacing="1" cellpadding="0" class="list-box" align="center">
<tr class="list-title" style="position:relative; top:expression(this.offsetParent.scrollTop-2);">
  <TD width="10%" align="center"><input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);checkOperation(this.form)"></TD>
   <TD width="60%" align="center">附件名称</TD>
   <TD width="30%" align="center">日期</TD>
</TR>
<%
if(list!=null){
  Iterator iter = list.iterator();
  while(iter.hasNext()){
    Attachment bean = (Attachment)iter.next();
%>
<TR>
  <TD class="td-c"><span class="td-cb">
    <input type="checkbox" name="id" value="<%=bean.getId()%>" onClick="checkOperation(this.form)">
  </span></TD>
   <TD height="20" class="td-text"><a href="../attachment/download.do?referType=<%=referType%>&referId=<%=referId%>&id=<%=bean.getId()%>"><%=bean.getName()%></a></TD>
   <TD class="td-date"><%=glafUtil.dateToString(bean.getCreateDate())%></TD>
</TR>
<%
  }
}
%>
</TABLE>
</div>
<table width="98%" id="main" border="0" cellspacing="1" cellpadding="0" align="center">
  <tr>
    <td height="25">
	<input type="button" name="upload" value="上传文件" onClick="javascript:uploadFile();" class="button">
    <input name="btn_del" type="button" class="button" value="删除" onClick="javascript:del(this.form);" disabled></td>
  </tr>
</table>
</html:form>
<script language="javascript">
attachFrame();
</script>
</body>
</html>