<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
int parent=ParamUtil.getIntParameter(request, "parent", 0);
int pageSize=Constants.PAGE_SIZE;
PageResult pager=(PageResult)request.getAttribute("pager");
List list = pager.getResults();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基础平台系统</title>
<script type='text/javascript' src='<%=context%>/dwr/interface/DictoryAjaxService.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/util.js'></script>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script>
<script language="JavaScript">
var num=0;
function checkOperation(form){
  num = getCheckedBoxNum(form,"id");
  if(num>0){
  	<%if(parent != 17){%>
    document.all.btn_del.disabled=false;
    <%}%>
	if(num==1){
	  document.all.btn_modify.disabled=false;
	}else{
	  document.all.btn_modify.disabled=true;
	}
  }else{
  <%if(parent != 17){%>
    document.all.btn_del.disabled=true;
  <%}%>
	document.all.btn_modify.disabled=true;
  }
}
function add(){
  var url="dictory.do?method=prepareAdd&parent="+<%=parent%>;
  var width=450;
  var height=300;
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
  var url="dictory.do?method=prepareModify&id="+id+"&parent="+<%=parent%>;
  var width=450;
  var height=300;
  var scroll="no";
  openWindow(url, width, height, scroll);
}
function del(){
  var form = document.DictoryForm;
  if(confirmDelete(form)){
    form.action="dictory.do?method=batchDelete";
    form.target="hiddenFrame";
    form.submit();
  }
}
function sort(id, operate){  
  DictoryAjaxService.sort(<%=parent%>, id, operate, {callback:function (){reloadPage();}});
}
</script>
</head>

<body>
<div class="nav-title"><span class="Title">字典管理</span>&gt;&gt;字典列表</div>
<html:form action="/sys/dictory.do?method=batchDelete" method="post" target="_self">
<input type="hidden" name="id" value="0">  
<table width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title"> 
    <td width="5%" align="center">
        <input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this)";checkOperation(this.form)>
    </td>
    <td width="5%" align="center"> <div align="center" class="fontname_12">序号</div></td>
    <td width="30%" align="center"> <div align="center" class="fontname_12">名称</div></td>
    <td width="20%" align="center"> <div align="center" class="fontname_12">代码</div></td>
    <td width="20%" align="center"><div align="center" class="fontname_12">是否有效</div></td>
    <td width="20%" align="center"><div align="center" class="fontname_12">排序</div></td>
  </tr>
  <%
int i=0;
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    Dictory bean=(Dictory)iter.next();	
%>
  <tr <%=i%2==0?"":"class='list-back'"%>> 
    <td width="5%" class="td-cb"> <input type="checkbox" name="id" value="<%=bean.getId()%>" onClick="checkOperation(this.form)">    </td>
    <td class="td-no"><%=(pager.getCurrentPageNo()-1)*10 + i+1%>&nbsp;</td>
    <td class="td-text"><%=bean.getName()%>&nbsp;</td>
    <td class="td-no"><%=bean.getCode()%>&nbsp;</td>
    <td class="td-no"><%=bean.getBlocked()==0?"是":"否"%></td>
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
      <td width="50%"><input name="btn_add" type="button" value="增加" class="button" onClick="javascript:add();">
      	<%
      	if(parent != 17){
      	%>
        <input name="btn_del" type="button" value="删除" class="button" onClick="javascript:del();" disabled>
        <%
        }
        %>
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
              <jsp:param name="url" value="dictory.do"/>
			  <jsp:param name="params" value="<%=java.net.URLEncoder.encode(params)%>"/>
        </jsp:include> </td>
    </tr>
  </table>
</html:form>
<script language="javascript">
attachFrame();
</script>
</body>
</html>
