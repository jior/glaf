<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.workspace.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
int pageSize = Constants.PAGE_SIZE;
PageResult pager = (PageResult)request.getAttribute("pager");
List list = pager.getResults();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>我的菜单</title>
<link href="<%= request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/MyMenuAjaxService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src="<%= request.getContextPath() %>/js/css.js"></script>
<script type='text/javascript' src='<%= request.getContextPath() %>/js/main.js'></script>
<script type='text/javascript' src='<%= request.getContextPath() %>/js/site.js'></script>
<script language="JavaScript">
function add(){
  var url="mymenu.do?method=prepareAdd";
  var width=450;
  var height=350;
  var scroll="no";
  openWindow(url, width, height, scroll);
}
function modify(){
  var id = getCheckboxValue('id');
	if (id.length == 0) {
	  return;
	}
  var url="mymenu.do?method=prepareModify&id="+id;
  var width=450;
  var height=350;
  var scroll="no";
  openWindow(url, width, height, scroll);
}
function del(){
  var form = document.all.MyMenuForm;
  if(confirmDelete(form)){
    form.target="_blank";
    form.action="mymenu.do?method=batchDelete";
	form.submit();
  }
}
function sort(id, operate){  
  MyMenuAjaxService.sort(id, operate,{callback:function (){reloadPage();}});
}
var num=0;
function checkOperation(form){
  num = getCheckedBoxNum(form, "id");
  if (num > 0) {
    $('btn_del').disabled = false;
		if (num == 1) {
			$('btn_modify').disabled = false;
		} else {
			$('btn_modify').disabled = true;
		}
  } else {
		$('btn_del').disabled = true;
		$('btn_modify').disabled = true;
  }
}
</script>
</head>

<body>
<div class="nav-title"><span class="Title">工作台</span>&gt;&gt; 我的菜单</div>
  <html:form action="/workspace/mymenu.do?method=batchDelete" method="post" target="_self"> 
<input name="page_no" type="hidden" value="<%= pager.getCurrentPageNo() %>">
  <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td><table width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
            <tr class="list-title">
              <td width="10%">
                <input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);checkOperation(this.form)">
              
              全选</td>
              <td width="17%" align="center">菜单标题</td>
              <td width="58%" align="center">访问链接</td>
              <td width="10%" align="center">排序</td>
            </tr>
                  <%
						int i = 0;
				    if (list != null) {
					  Iterator iter = list.iterator();
					  while (iter.hasNext()) {
					    MyMenu bean = (MyMenu) iter.next();
				  %>
            <tr class="<%= i % 2 == 0 ? "list-back" : "" %>">
              <td height="22" class="td-cb"><input type="checkbox" name="id" value="<%= bean.getId() %>" onClick="checkOperation(this.form)"></td>
              <td height="22" class="td-text"><%= bean.getTitle() %></td>
              <td height="22" class="td-text"><a href="<%= URLDecoder.decode(bean.getUrl()) %>" target="_blank"><%= URLDecoder.decode(bean.getUrl()) %></a>&nbsp;</td>
              <td height="22" align="center" class="td-order"><a href="javascript:sort(<%=bean.getId()%>, 0);" title="上移"><img src="<%=request.getContextPath()%>/images/up.gif" border="0" height="13" width="13"></a> <a href="javascript:sort(<%=bean.getId()%>, 1);" title="下移"><img src="<%=request.getContextPath()%>/images/down.gif" border="0" height="13" width="13"></a></td>
            </tr>
				  <%
							i++;
				      }
					}
					for(; i < pageSize; i++) {
				  %>
            <tr class="<%= i % 2 == 0 ? "list-back" : "" %>">
              <td height="22">&nbsp;</td>
              <td height="22" class="">&nbsp;</td>
              <td height="22" class="">&nbsp;</td>
              <td height="22" align="center" class="tableList">&nbsp;</td>
            </tr>
					<%
					  }
					%>
          </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="56%" height="35" valign="bottom"><input name="btn_add" id="btn_add" type="button" class="button"  onclick="javascript:add();" value="增加">
                <input name="btn_del" id="btn_del" type="button" class="button" onClick="javascript:del();" value="删除" disabled="disabled">
                <input name="btn_modify" id="btn_modify" type="button" value="修改" class="button" onClick="javascript:modify();" disabled="disabled"></td>
              <td width="44%" align="right" valign="bottom">
<%
String params = "method=showList";
%>
                <jsp:include page="/WEB-INF/views/inc/show_page.jsp" flush="true">
                <jsp:param name="total" value="<%=pager.getTotalRecordCount()%>"/>          
                <jsp:param name="page_count" value="<%=pager.getTotalPageCount()%>"/>          
                <jsp:param name="page_size" value="<%=pageSize%>"/>          
                <jsp:param name="page_no" value="<%=pager.getCurrentPageNo()%>"/>          
                <jsp:param name="url" value=""/>          
                <jsp:param name="params" value="<%=java.net.URLEncoder.encode(params)%>"/>          
                </jsp:include>
							</td>
            </tr>
        </table></td>
    </tr>
  </table>
  </td>
  </tr>
  </table>
  </html:form>
</body>
</html>
