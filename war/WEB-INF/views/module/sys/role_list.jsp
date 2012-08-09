<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
int pageSize=Constants.PAGE_SIZE;
PageResult pager=(PageResult)request.getAttribute("pager");
List list = pager.getResults();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/interface/SysRoleAjaxService.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/util.js'></script>
<script language="JavaScript">
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
function add(){
  var url="role.do?method=prepareAdd";
  var width=450;
  var height=250;
  var scroll="no";
  openWindow(url, width, height, scroll);
}
function modify(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var url="role.do?method=prepareModify&id="+id;
  var width=450;
  var height=250;
  var scroll="no";
  openWindow(url, width, height, scroll);
}
function del(){
  var form = document.all.GenericForm;
  if(confirmDelete(form)){
    form.target="hiddenFrame";
    form.action="role.do?method=batchDelete";
	form.submit();
  }
}

function sort(id, operate){  
  SysRoleAjaxService.sort(id, operate, {callback:function (){reloadPage();}});
}
</script>
</head>

<body>
<html:form action="/sys/role.do?method=batchDelete" method="post" target="_self"> 
<input name="page_no" type="hidden" value="<%=pager.getCurrentPageNo()%>">
<input type="hidden" name="id" value="0">
<div class="nav-title"><span class="Title">角色管理</span>&gt;&gt;角色列表</div>
<table width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title"> 
    <td width="3%" align="center"> <input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);checkOperation(this.form)">    </td>
    <td width="12%" align="center">序号</td>
    <td width="24%" align="center">角色名称</td>
    <td width="16%" align="center">代码</td>
    <td width="33%" align="center">描述</td>
    <td width="12%" align="center">排序</td>
  </tr>
  <%
int i=0;
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysRole bean=(SysRole)iter.next();
%>
  <tr <%=i%2==0?"":"class='list-back'"%>> 
    <td class="td-cb"> <input type="checkbox" name="id" value="<%=bean.getId()%>" onClick="checkOperation(this.form)">    </td>
    <td class="td-no"><%=((pager.getCurrentPageNo()-1)*pageSize + i+1)%></td>
    <td class="td-text"><%=bean.getName()%>&nbsp; </td>
    <td class="td-text"><%=bean.getCode()%></td>
    <td class="td-text"><%=bean.getDesc()%>&nbsp;</td>
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
  </tr>
<%
}
%>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td width="50%"> <input name="btn_add" type="button" value="增加" class="button" onClick="javascript:add();"> 
      <input name="btn_del" type="button" value="删除" class="button" onClick="javascript:del();" disabled>
      <input name="btn_modify" type="button" value="修改" class="button" onClick="javascript:modify(this.form);" disabled></td>
    <td width="50%"> 
      <%
        String params = WebUtil.getQueryString(request);
		//System.out.println("params:"+java.net.URLEncoder.encode(params));
      %>
      <jsp:include page="/WEB-INF/views/inc/show_page.jsp" flush="true"> 
              <jsp:param name="total" value="<%=pager.getTotalRecordCount()%>"/>
              <jsp:param name="page_count" value="<%=pager.getTotalPageCount()%>"/>
              <jsp:param name="page_size" value="<%=pageSize%>"/>
              <jsp:param name="page_no" value="<%=pager.getCurrentPageNo()%>"/>
              <jsp:param name="url" value="role.do"/>
			  <jsp:param name="params" value="<%=java.net.URLEncoder.encode(params)%>"/> 
	  </jsp:include> 
	</td>
  </tr>
</table>
</html:form>
<script language="javascript">
attachFrame();
</script> 
</body>
</html>
