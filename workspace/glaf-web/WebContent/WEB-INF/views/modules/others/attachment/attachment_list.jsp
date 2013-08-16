<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.DateUtils"%>
<%@ page import="com.glaf.base.modules.others.*"%>
<%@ page import="com.glaf.base.modules.others.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
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
<title>GLAF基础平台系统</title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/plugins/iframeTools.js"></script>
<script language="javascript">
var contextPath="<%=request.getContextPath()%>";
var dataStr='numAttachment<%=referType%>';
function uploadFile(){
  var json = openUpload();
  if(json==null || json==""){
  }else{
  	jQuery.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/others/attachment.do?method=save&referId=<%=referId%>&referType=<%=referType%>&json='+json,
			dataType:  'json',
				error: function(data){
					alert('服务器处理错误！');
				},
				success: function(data){
					//refreshCount();
					location.reload();
				}
		});
  }
}
function reLoadPage(json){
	if(json==null || json==""){
	}else{
  		jQuery.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/others/attachment.do?method=save&referId=<%=referId%>&referType=<%=referType%>&json='+json,
			dataType:  'json',
				error: function(data){
					alert('服务器处理错误！');
				},
				success: function(data){
					//refreshCount();
					location.reload();
				}
		});
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
  //refreshCount();
}
function refreshCount() {
  if (window.opener) {
	  try {
	    //window.opener.location.reload();
		if(window.opener.document.getElementById(dataStr)!=null){
			window.opener.document.getElementById(dataStr).innerHTML='<%=list != null ? list.size() : 0 %>';
		}
	  } catch(e){}
	}else{
		var origin = artDialog.open.origin;
		//alert(origin.document.getElementById(dataStr)==undefined);
		if(origin.document.getElementById(dataStr)!=null){
			origin.document.getElementById(dataStr).innerHTML='<%=list != null ? list.size() : 0 %>';
		}
		//origin.location.reload();
	}
}
function openUpload(obj, type){  
	var userAgent = navigator.userAgent.toLowerCase();
	var isSafari = userAgent.indexOf("Safari")>=0;
	var is_opera = userAgent.indexOf('opera') != -1 && opera.version();
	var is_moz = (navigator.product == 'Gecko') && userAgent.substr(userAgent.indexOf('firefox') + 8, 3);
	var is_ie = (userAgent.indexOf('msie') != -1 && !is_opera) && userAgent.substr(userAgent.indexOf('msie') + 5, 3);
	var link = '<%=request.getContextPath()%>/others/attachment.do?method=showUpload' + (type ? '?type=' + type : '');
	if(is_ie){
		return ShowDialog(link, 450, 230, 'no', false, obj);
	}else{
		art.dialog.open(link, { height: 230, width: 450, title: "上传", lock: true, scrollbars:"no" }, false);
	}
}
refreshCount();
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
   <TD height="20" class="td-text" title="<%=bean.getName()%>"><a href="<%=request.getContextPath()%>/others/attachment.do?method=download&referType=<%=referType%>&referId=<%=referId%>&id=<%=bean.getId()%>"><%=bean.getName()%></a></TD>
   <TD class="td-date"><%=DateUtils.getDateTime(bean.getCreateDate())%></TD>
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