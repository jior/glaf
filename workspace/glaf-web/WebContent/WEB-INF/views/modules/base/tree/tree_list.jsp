<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/scripts/artDialog/skins/default.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/plugins/iframeTools.js"></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript">
var num=0;
function checkOperation(form){
  num = getCheckedBoxNum(form,"id");
  if(num>0){
    //document.all.btn_del.disabled=false;
	if(num==1){
	  document.all.btn_modify.disabled=false;
	  document.all.btn_dict.disabled=false;
	}else{
	  document.all.btn_modify.disabled=true;
	  document.all.btn_dict.disabled=true;
	}
  }else{
    //document.all.btn_del.disabled=true;
	document.all.btn_modify.disabled=true;
	document.all.btn_dict.disabled=true;
  }
}
function add(){
  var url="tree.do?method=prepareAdd&parent=<%=parent%>";
  var link = "<%=request.getContextPath()%>/base/"+url;
  var width=450;
  var height=350;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  art.dialog.open(link, { height: height, width: width, title: "添加分类", lock: false, scrollbars:"no" }, false);
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
	  var link = "<%=request.getContextPath()%>/base/"+url;
	  var width=450;
	  var height=350;
	  var scroll="no";
	  //openWindow(url, width, height, scroll);
	  art.dialog.open(link, { height: height, width: width, title: "修改分类", lock: false, scrollbars:"no" }, false);
  }
}

function dict(form){
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
	  var url="dictoryDefinition.do?method=edit&target=sys_dictory&nodeId="+id;
	  var link = "<%=request.getContextPath()%>/sys/"+url;
	  var width=650;
	  var height=780;
	  var scroll="no";
	  //openWindow(url, width, height, scroll);
	  art.dialog.open(link, { height: height, width: width, title: "分类字典", lock: false, scrollbars:"no" }, false);
  }
}

/**
function del(){
  var form = document.all.GenericForm;
  if(confirmDelete(form)){
    form.target="hiddenFrame";
    form.action="tree.do?method=batchDelete";
	form.submit();
  }
}**/

function sort(id, operate){  
  //SysTreeAjaxService.sort(<%=parent%>, id, operate, {callback:function (){reloadPage();}});
         jQuery.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/base/tree.do?method=sort&parent=<%=parent%>&id='+id+'&operate='+operate,
			dataType:  'json',
				error: function(data){
					alert('服务器处理错误！');
				},
				success: function(data){
					   location.reload();
				 }
		});
}
</script>
</head>
<body>
<div class="nav-title"><span class="Title">目录管理</span>&gt;&gt;节点列表</div>
<html:form action="${contextPath}/base/tree.do?method=batchDelete" method="post" target="_self"> 
 
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
    <td width="50%"> 
	  <input name="btn_add" type="button" value="增加" class="button" onClick="javascript:add();"> 
      <input name="btn_modify" type="button" value="修改" class="button" onClick="javascript:modify(this.form);" disabled>
	  <c:if test="${parent.id == 4 or parent.discriminator == 'Y'}">
	  <input name="btn_dict" type="button" value="字典" class="button" onClick="javascript:dict(this.form);" disabled>
	  </c:if>
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
