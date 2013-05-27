<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.DateUtils"%>
<%@ page import="com.glaf.base.modules.others.*"%>
<%@ page import="com.glaf.base.modules.others.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%
int referType=ParamUtil.getIntParameter(request, "referType", 0);
int referId=ParamUtil.getIntParameter(request, "referId", 0);
String context = request.getContextPath();
List list = (List)request.getAttribute("list");

String referIds = StringUtils.replace(ParamUtil.getParameter(request, "referIds"), ",", "");
%>
<html>
<base target="_self" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>GLAF基础平台系统</title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript">
function uploadFile(){
  var file = openUpload();
  if(file==null || file==""){
  }else{
  	 
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
}
function refreshCount() {
  if (window.opener) {
	  try {
	    window.opener.getCount<%= referType + (referId == 0 ? referIds : referId + "") %>();
		} catch(e){}
	}
}
</script>
</head>

<body onUnload="refreshCount()">
<html:form action="/others/attachment.do?method=batchDelete" method="post">
<table width="98%" id="main" border="0" cellspacing="1" cellpadding="0" align="center">
  <tr>
    <td height="20">附件列表</td>
  </tr>
</table>
<div style="width:100%; height:200px;overflow-x:auto; overflow-y:auto;">
<table width="98%" id="main" border="0" cellspacing="1" cellpadding="0" class="list-box" align="center">
<tr class="list-title" style="position:relative; top:expression(this.offsetParent.scrollTop-2);">
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
  <TD height="20" class="td-text"><a href="<%=request.getContextPath()%>/others/attachment.do?method=download&referType=<%=referType%>&referId=<%=referId%>&id=<%=bean.getId()%>"><%=bean.getName()%></a></TD>
   <TD class="td-date"><%=DateUtils.getDateTime(bean.getCreateDate())%></TD>
</TR>
<%
  }
}
%>
</TABLE>
</div>
</html:form>
</body>
</html>