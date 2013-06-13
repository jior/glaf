<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ page import="org.springframework.web.context.*"%>
<%@ page import="org.springframework.web.context.support.*"%>
<%@ page import="com.glaf.base.modules.workspace.model.*"%>
<%@ page import="com.glaf.base.modules.workspace.service.*"%>
<%
    List messageList = (List)request.getAttribute("messageList");
    int count = 0;
	int msgPageSize = 5;
%>
<script type="text/javascript">
    function openMoreMsg() {
	    openMMWindow('<%=request.getContextPath()%>/workspace/message.do?method=showReceiveList', 600, 450);
	}

	function openMsg(id) {
		openMMWindow('<%=request.getContextPath()%>/workspace/message.do?method=showMessage&id=' + id, 600, 450);
	}
</script>
    <table width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
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
			<td class="<%= colorClass %>"  height="22" width="75%">
			  &nbsp;&nbsp;<a href="#" onclick="javascript:openMsg(<%= msg.getId() %>);" title="<%= msg.getTitle() %>"><%= msg.getTitle() %></a>
			</td>
            <td class="<%= colorClass %>" height="20" align="center" width="25%">
			     [<%= com.glaf.core.util.DateUtils.getDate(msg.getCreateDate()) %>]
			</td>
          </tr>
		<%
			count++;
			}
		}
		for(; count < msgPageSize; count++) {
		%>
		<tr class="<%= count % 2 == 0 ? "list-w" : "list-a" %>">
            <td height="22">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
		<%
		}
		%>
  </table> 