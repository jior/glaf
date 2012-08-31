<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.workspace.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
int pageSize = Constants.PAGE_SIZE;
PageResult pager = (PageResult)request.getAttribute("pager");
List list = pager.getResults();
String flag = (String)request.getAttribute("flag");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="../../css/site.css" type="text/css" rel="stylesheet">
<link href="<%= request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
<script type='text/javascript' src="<%= request.getContextPath() %>/js/css.js"></script>
<script type='text/javascript' src='<%= request.getContextPath() %>/js/main.js'></script>
<script type='text/javascript' src="<%= request.getContextPath() %>/js/verify.js"></script>
<script type="text/javascript">
function openMsg(id) {
  openWindow('message.do?method=showMessage&sended=1&id=' + id, 600, 450);
}
function sendMsg() {
  openWindow('message.do?method=prepareSend', 660, 450);
}
function replyMsg() {
  var id = getCheckboxValue('id');
	if (id.length == 0) {
	  return;
	}
	openWindow('message.do?method=prepareSend&id=' + id, 660, 450);
}
function del(form) {
  if(confirmDelete(form)) {
	  form.action = 'message.do?method=batchDelete';
	  form.target = '_blank';
	  form.submit();
	}
}
function checkOperation(){
  var num = getCheckedboxNums("id");
  if (num > 0) {
		$('btn_del').disabled = false;
  } else {
		$('btn_del').disabled = true;
	}
}

</script>
<title>我的已发送消息</title>
</head>

<body>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td class="nav-title"><span class="Title">工作台</span>&gt;&gt; 我的已发送消息</td>
  </tr>
</table>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
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
    <td class="box-mm"><html:form method="post" action="/workspace/message.do">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><table border="0" cellspacing="0" cellpadding="0" class="tabs-box">
              <tr>
                <td class="tab-l">&nbsp;</td>
                    <td width="60" class="tab-m"><a href="?method=showReceiveList">收件箱</a></td>
                    <td class="tab-r">&nbsp;</td>
                    <td class="tab-lc">&nbsp;</td>
                    <td width="60" class="tab-mc">已发送</td>
                    <td class="tab-rc">&nbsp;</td>
                </tr>
              </table>
                  <table width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
                    <tr class="list-title">
                      <td width="60"><input type="checkbox" name="chkall" value="checkbox" onClick="CheckAlls(this, 'id');checkOperation()">
                        全选</td>
                      <td width="40" align="center">序号</td> 
                      <td width="120" align="center">收件人</td>
                      <td align="center">主 题</td>
                      <td width="120" align="center">发送日期</td>
                    </tr>
                    <%
											int i = 0;
									  if (list != null) {
										  Iterator iter = list.iterator();
											while (iter.hasNext()) {
											  Message bean = (Message) iter.next();
												String recverList = bean.getRecverList() == null ? "" : bean.getRecverList();
									%>
                    <tr class="<%= i % 2 == 0 ? "list-a" : "list-w" %>">
                      <td height="20" class="td-cb"><input type="checkbox" name="id" value="<%= bean.getId() %>" onClick="checkOperation()">                      </td>
                      <td clase="td-no"><%=((pager.getCurrentPageNo()-1)*pageSize + i+1)%></td>
                      <td class="td-text" title="<%= recverList %>"><%= recverList %></td>
                      <td class="td-text"><a href="javascript:openMsg(<%= bean.getId() %>)" title="<%= bean.getTitle() %>"><%= bean.getTitle() %></a></td>
                      <td class="td-date"><%= WebUtil.dateToString(bean.getCreateDate(), "yyyy-MM-dd HH:mm:ss") %></td>
                    </tr>
                    <%
											  i++;
									    }
									  }
										for(; i < pageSize; i++) {
									%>
                    <tr class="<%= i % 2 == 0 ? "list-a" : "list-w" %>">
                      <td height="20">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <%
									  }
									%>
              </table>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="35" valign="bottom" nowrap>
					<%
					if(!"supplier".equals(flag)){
					%>
					<input name="btn_send" id="btn_send" type="button" class="button"  onclick="javascript:sendMsg();" value="发送消息">
					<%
					}
					%>
                      <input name="btn_del" id="btn_del" type="button" class="button" onClick="javascript:del(this.form);" value="删除" disabled="disabled"></td>
                    <td align="right" valign="bottom"><%
String params = "method=showSendedList";
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
      </html:form>
    </td></tr>
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
</body>
</html>
