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
int parent=ParamUtil.getIntParameter(request, "parent", 0);
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
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/SysTreeAjaxService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
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
  var url="tree.do?method=prepareAdd&parent=<%=parent%>";
  var width=450;
  var height=350;
  var scroll="no";
  openWindow(url, width, height, scroll);
}

function modify(form){
  var id = 0;
  num = getCheckedBoxNum(form,"id");
  if(num == 1){
      var arr = document.getElementsByName("id");
	  for (var i=0;i<arr.length;i++) {
		var e = arr[i];
		if (e.checked){
		   id = e.value;
		}     
	  }
	  var url="tree.do?method=prepareModify&id="+id;
	  var width=450;
	  var height=350;
	  var scroll="no";
	  openWindow(url, width, height, scroll);
  }
}

function del(){
  var form = document.all.GenericForm;
  if(confirmDelete(form)){
    form.target="hiddenFrame";
    form.action="tree.do?method=batchDelete";
	form.submit();
  }
}
function sort(id, operate){  
  SysTreeAjaxService.sort(<%=parent%>, id, operate, {callback:function (){reloadPage();}});
}
</script>
</head>

<body>
<div class="nav-title"><span class="Title">目录管理</span>&gt;&gt;节点列表</div>
<html:form action="/sys/tree.do?method=batchDelete" method="post" target="_self"> 
 
<input name="page_no" type="hidden" value="<%=pager.getCurrentPageNo()%>">
<input name="parent" type="hidden" value="<%=parent%>">
<table width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title"> 
    <td width="5%" align="center"> <input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);checkOperation(this.form)">    </td>
    <td width="5%" align="center">序号</td>
    <td width="30%" align="center">名称</td>
    <td width="40%" align="center">描述</td>
	<td width="10%" align="center">编码</td>
    <td width="10%" align="center">排序</td>
    </tr>
  <%
int i=0;
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysTree bean=(SysTree)iter.next();	
%>
  <tr <%=i%2==0?"":"class='list-back'"%>> 
    <td class="td-cb"><input type="checkbox" name="id" value="<%=bean.getId()%>" onClick="checkOperation(this.form);"></td>
    <td class="td-no"><%=((pager.getCurrentPageNo()-1)*pageSize + i+1)%></td>
    <td class="td-text"><%=bean.getName()%>&nbsp;</td>
    <td class="td-text"><%=bean.getDesc()%>&nbsp;</td>
	<td class="td-no"><%=bean.getCode()%>&nbsp;</td>
    <td class="td-no"><a href="javascript:sort(<%=bean.getId()%>, 0);" title="上移"><img src="<%=context%>/images/up.gif" border="0" height="13" width="13"></a> <a href="javascript:sort(<%=bean.getId()%>, 1);" title="下移"><img src="<%=context%>/images/down.gif" border="0" height="13" width="13"></a>&nbsp;</td>
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
%>
      <jsp:include page="/WEB-INF/views/inc/show_page.jsp" flush="true"> 
              <jsp:param name="total" value="<%=pager.getTotalRecordCount()%>"/>
              <jsp:param name="page_count" value="<%=pager.getTotalPageCount()%>"/>
              <jsp:param name="page_size" value="<%=pageSize%>"/>
              <jsp:param name="page_no" value="<%=pager.getCurrentPageNo()%>"/>
              <jsp:param name="url" value="tree.do"/>
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
