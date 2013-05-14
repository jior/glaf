<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
int pageSize=Constants.PAGE_SIZE;
int parent=ParamUtil.getIntParameter(request, "parent", 0);
com.glaf.core.util.PageResult pager=(com.glaf.core.util.PageResult)request.getAttribute("pager");
List list = pager.getResults();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/scripts/artDialog/skins/default.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/plugins/iframeTools.js"></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="JavaScript">
var num=0;
function checkOperation(form){
  num = getCheckedBoxNum(form,"id");
  if(num>0){
	if(num==1){
	  document.all.btn_modify.disabled=false;
	  document.all.btn_user.disabled=false;
	  document.all.btn_role.disabled=false;
	}else{
	  document.all.btn_modify.disabled=true;
	  document.all.btn_user.disabled=true;
	  document.all.btn_role.disabled=true;
	}
  }else{
	  document.all.btn_modify.disabled=true;
	  document.all.btn_user.disabled=true;
	  document.all.btn_role.disabled=true;
  }
}
function add(){
  var url="department.do?method=prepareAdd&parent="+<%=parent%>;
  var link = "<%=request.getContextPath()%>/sys/"+url;
  var width=530;
  var height=410;
  var scroll="no";  
  //openWindow(url, width, height, scroll);
  art.dialog.open(link, { height: height, width: width, title: "添加部门", scrollbars:"no" , lock: false });
}
function modify(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var url="department.do?method=prepareModify&id="+id;
  var link = "<%=request.getContextPath()%>/sys/"+url;
  var width=530;
  var height=425;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  art.dialog.open(link, { height: height, width: width, title: "修改部门", scrollbars:"no" , lock: false });
}

function sort(id, operate){  
  //SysDepartmentAjaxService.sort(<%=parent%>, id, operate, {callback:function (){reloadPage();}});
       jQuery.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/sys/department.do?method=sort&parent=<%=parent%>&id='+id+'&operate='+operate,
			dataType:  'json',
				error: function(data){
					alert('服务器处理错误！');
				},
				success: function(data){
					   location.reload();
				 }
		});
}
function users(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var url="user.do?method=showList&parent="+id;
  var link = "<%=request.getContextPath()%>/sys/"+url;
  var width=880;
  var height=380;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  art.dialog.open(link, { height: height, width: width, title: "部门用户", scrollbars:"no" , lock: false });
}
function roles(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var url="deptRole.do?method=showList&parent="+id;
  var link = "<%=request.getContextPath()%>/sys/"+url;
  var width=680;
  var height=400;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  art.dialog.open(link, { height: height, width: width, title: "部门角色", scrollbars:"no" , lock: false });
}
</script>
</head>

<body style="padding-top:10px;padding-left:2px;padding-right:20px;">
<div class="nav-title"><span class="Title">部门管理</span>&gt;&gt;
<%
List nav = (List)request.getAttribute("nav");
Iterator navIter = nav.iterator();
while(navIter.hasNext()){
  SysDepartment bean = (SysDepartment)navIter.next();
%>  
  <a href="department.do?method=showList&parent=<%=bean.getNode().getId()%>"><%=bean.getName()%></a>&gt;&gt; 
<%
}
%>
</div>
<html:form action="${contextPath}/sys/department.do?method=batchDelete" method="post" target="_self"> 
<input name="page_no" type="hidden" value="<%=pager.getCurrentPageNo()%>">
<input name="parent" type="hidden" value="<%=parent%>">
<input type="hidden" name="id" value="0">
<table width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
  <col width="5%"/><col width="4%"/><col/><col width="10%"/><col width="15%"/>
  <col width="12%"/><col width="8%"/><col width="8%"/>
  <tr class="list-title"> 
    <td align="center"> <input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);checkOperation(this.form)">    </td>
    <td align="center">序号</td>
    <td align="center">名称</td>
    <td align="center">状态</td>
    <td align="center">代码</td>
    <td align="center">编码</td>
    <td align="center">部门区分</td>
    <td align="center">排序</td>
    </tr>
  <%
int i=0;
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysDepartment bean=(SysDepartment)iter.next();	
%>
  <tr <%=i%2==0?"":"class='list-back'"%>> 
    <td class="td-cb"> <input type="checkbox" name="id" value="<%=bean.getId()%>" onClick="checkOperation(this.form)">    </td>
    <td class="td-no"><%=((pager.getCurrentPageNo()-1)*pageSize + i+1)%></td>
    <td class="td-text"><a href="department.do?method=showList&id=<%=bean.getId()%>&parent=<%=bean.getNodeId()%>"><%=bean.getName()%></a> </td>
    <td class="td-no"><%=bean.getStatus()==0?"有效":"无效"%>&nbsp;</td>
    <td class="td-no"><%=bean.getCode()%>&nbsp;</td>
    <td class="td-no"><%=bean.getNo()%>&nbsp;</td>
    <td class="td-no"><%=bean.getCode2()%>&nbsp;</td>
    <td class="td-no"><a href="javascript:sort(<%=bean.getId()%>, 0);" title="上移"><img src="<%=context%>/images/up.gif" border="0" height="13" width="13"></a> <a href="javascript:sort(<%=bean.getId()%>, 1);" title="下移"><img src="<%=context%>/images/down.gif" border="0" height="13" width="13"></a></td>
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>	
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
    <td width="50%"> <input name="btn_add" type="button" value="增加" class="button" onClick="javascript:add();"> 
      <input name="btn_modify" type="button" value="修改" class="button" onClick="javascript:modify(this.form);" disabled>
      <input name="btn_user" type="button" value="用户管理" class="button" onClick="javascript:users(this.form);" disabled>
      <input name="btn_role" type="button" value="角色设置" class="button" onClick="javascript:roles(this.form);" disabled>
	  </td>
    <td width="50%"> 
      <%
String params = WebUtil.getQueryString(request);
%>
      <jsp:include page="/WEB-INF/views/inc/show_page.jsp" flush="true"> 
              <jsp:param name="total" value="<%=pager.getTotalRecordCount()%>"/>
              <jsp:param name="page_count" value="<%=pager.getTotalPageCount()%>"/>
              <jsp:param name="page_size" value="<%=pageSize%>"/>
              <jsp:param name="page_no" value="<%=pager.getCurrentPageNo()%>"/>
              <jsp:param name="url" value="department.do"/>
	    <jsp:param name="params" value="<%=java.net.URLEncoder.encode(params)%>"/>        </jsp:include> </td>
  </tr>
</table>
</html:form> 
<script language="javascript">
attachFrame();
</script>
</body>
</html>
