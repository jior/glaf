<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ page import="org.springframework.web.context.*"%>
<%@ page import="org.springframework.web.context.support.*"%>
<%@ page import="com.glaf.base.modules.workspace.model.*"%>
<%@ page import="com.glaf.base.modules.workspace.service.*"%>
<%
SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);
WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
MessageService messageService = (MessageService) wac.getBean("messageService");
int msgPageSize = 5;
PageResult messagePager = messageService.getNoReadList(user.getId(), new HashMap(), 1, msgPageSize);
List messageList = messagePager.getResults();
int count = 0;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>首页</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style-custom.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/main.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/site.js"></script>
<script type="text/javascript">

   function openMoreMsg() {
	    openWindow('<%=request.getContextPath()%>/workspace/message.do?method=showReceiveList', 600, 450);
	}

	function openMsg(id) {
		openWindow('<%=request.getContextPath()%>/workspace/message.do?method=showMessage&id=' + id, 600, 450);
	}

</script>
</head>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td valign="top" class="m-right">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="m-box">
      <tr><td>&nbsp;</td></tr>
      <tr>
        <td class="m-newsalarm" >&nbsp;</td>
        <td class="m-more" width="130"><a href="javascript:openMoreMsg()">更多&gt;&gt;</a></td>
      </tr>
      <tr>
        <td colspan="2"><table width="620" border="0" cellspacing="1" cellpadding="0" class="list-box">
          <tr class="list-title">
            <td width="120" align="center">发布时间</td>
            <td align="center">主 题</td>
            <td width="125" align="center">发布者</td>
          </tr>
          <%
		 if (messageList != null) {
			count = 0;
			Iterator msgIter = messageList.iterator();
			while(msgIter.hasNext()) {
				Message msg = (Message) msgIter.next();
				SysUser sender = msg.getSender();
				String senderName = sender == null ? "" : sender.getName();
				String colorClass = "";
				if (msg.getType() == 0) {
					String sysType = msg.getSysType()==0?"Alarm":"News";
					senderName = "系统自动("+sysType+")";
					colorClass = "redcolor";
				}
			%>
			<tr class="<%= count % 2 == 0 ? "list-w" : "list-a" %>">
            <td class="<%= colorClass %>" height="20" align="center"><%= WebUtil.dateToString(msg.getCreateDate(), "yyyy-MM-dd HH:mm:ss") %></td>
            <td class="<%= colorClass %>"><a href="javascript:openMsg(<%= msg.getId() %>)" title="<%= msg.getTitle() %>"><%= msg.getTitle() %></a></td>
            <td class="<%= colorClass %>" align="center"><%= senderName %></td>
          </tr>
		<%
			count++;
			}
		}
		for(; count < msgPageSize; count++) {
		%>
		<tr class="<%= count % 2 == 0 ? "list-w" : "list-a" %>">
            <td height="20">&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
		<%
		}
		%>
        </table></td>
      </tr>
      <tr>
        <td class="m-todo">&nbsp;</td>
        <td class="m-more"><a href="#"></a></td>
      </tr>
      <tr>
        <td colspan="2">
             <jsp:include page="/WEB-INF/views/module/todo/todo_index.jsp" />
		</td>
      </tr>
      <tr>
        <td height="20" colspan="2">&nbsp;</td>
      </tr>
    </table>
	</td>
</tr>
</table>
</body>
</html>