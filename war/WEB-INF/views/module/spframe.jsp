<%@ page contentType="text/html;charset=gbk" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ page import="org.springframework.web.context.*"%>
<%@ page import="com.glaf.base.modules.pay.PayConstants"%>
<%@ page import="org.springframework.web.context.support.*"%>
<%@ page import="com.glaf.base.modules.workspace.model.*"%>
<%@ page import="com.glaf.base.modules.workspace.service.*"%>
<%
SysUser user = (SysUser)session.getAttribute(SysConstants.LOGIN);
WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
String menu = (String)session.getAttribute(SysConstants.MENU);

MyMenuService myMenuService = (MyMenuService) wac.getBean("myMenuService");
List menuList = myMenuService.getMyMenuList(user.getId());
String context = request.getContextPath();

MessageService messageService = (MessageService) wac.getBean("messageService");
int msgPageSize = 5;
PageResult messagePager = messageService.getNoReadList(user.getId(), new HashMap(), 1, msgPageSize);
List messageList = messagePager.getResults();
int count = 0;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>基础平台系统</title>
<link href="<%=context%>/css/site.css" rel="stylesheet" type="text/css">
<!--link href="<%=context%>/css/system.css" type="text/css" rel="stylesheet"-->
<script src="<%=context%>/js/main.js" language="javascript"></script>
<style type="text/css"> 
@import url("<%=context%>/js/hmenu/skin-yp.css");
</style>
<script type="text/javascript">
_dynarch_menu_url = "<%=context%>/js/hmenu";
</script>
<script type="text/javascript" src="<%=context%>/js/hmenu/hmenu.js"></script>
<script type="text/javascript" src="<%=context%>/js/main.js"></script>
<script type="text/javascript" src="<%=context%>/js/site.js"></script>
<script language="javascript">
function openMoreMsg() {
  openWindow('<%=context%>/workspace/message.do?method=showReceiveList&flag=supplier', 600, 450);
}

function openMsg(id) {
  openWindow('<%=context%>/workspace/message.do?method=showMessage&id=' + id, 600, 450);
}

function exit(){
  var url="<%=context%>/sys/authorize.do?method=logout";
  if(confirm("真的要退出系统吗？")){
    window.location=url;
  }
}
function openDocument()
{
	var url = "<%=context%>/module/document/page1805.jsp";
	openMaxWindow(url);
}
</script>
</head>

<body onLoad="DynarchMenu.setup('menu', { context: true});MM_preloadImages('<%=context%>/images/i_05b.jpg')" id="document">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="35" colspan="2" valign="top">
	 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="index-top">
      <tr>
        <td class="top-l">&nbsp;</td>
		<td style="background:url('<%=context%>/images/i_001.jpg') no-repeat; width:200px;">&nbsp;</td>
        <td class="top-r">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top" class="m-left"><table width="192" border="0" align="right" cellpadding="0" cellspacing="0">
      <tr>
        <td height="105"><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1','','<%=context%>/images/i_03b.jpg',1)" class="hotspot1 context-align-bottom dynarch-menu-ctxbutton-both"><img src="<%=context%>/images/i_03.jpg" name="Image1" width="89" height="36" border="0"></a></td>
      </tr>
      <tr>
        <td height="120">
		  <ul id="menu" style="visibility:none">
		    <li class="context-class-a-hotspot1">
			  <ul>
			    <div style="display:none">
			      
				<li><a href="javascript:openWindow('<%=context%>/sp/queryPrice.do?method=changePasswordJump', 420, 260, 'yes')">修改密码</a></li>
				<li></li> 
				<li><a href="javascript:exit();">退出系统</a></li> 
			  </ul>
		    </li>
		  </ul>  
		</td>
      </tr>
      <tr>
        <td height="95"><a href="javascript:openDocument()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image3','','<%=context%>/images/i_05b.jpg',1)"><img src="<%=context%>/images/i_05.jpg" name="Image3" width="76" height="39" border="0"></a></td>
      </tr>
      <tr>
        <td height="95">
		<a href="javascript:exit();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image4','','<%=context%>/images/i_12b.jpg',1)"><img src="<%=context%>/images/i_12.jpg" name="Image4" width="89" height="36" border="0"></a>
		</td>
      </tr>
    </table></td>
    <td valign="top" class="m-right">
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="m-box">
        <tr>
		  <!--td height="30" align="left" valign="middle"><font size="+2" face="华文隶书" color="#CED4E0">供应商平台</font></td-->
          <td colspan="2" height="30" align="right"><jsp:include page="/WEB-INF/views/module/spLogin_info.jsp" flush="true"/>    
          &nbsp;&nbsp;&nbsp;</td>
        </tr>
        <tr>
          <td class="m-newsalarm">&nbsp;</td>
          <td align="right" class="m-more"><a href="javascript:openMoreMsg()">更多&gt;&gt;</a>&nbsp;&nbsp;&nbsp;</td>
        </tr>
        <tr>
          <td colspan="2">
		    <table width="98%" border="0" cellspacing="1" cellpadding="0" class="list-box">
              <tr class="list-title">
                <td width="20%" align="center">发布时间</td>
                <td width="62%" align="center">主 题</td>
                <td width="18%" align="center">发布者</td>
              </tr>
              <%
				if(messageList != null) {
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
            </table>
		  </td>
        </tr>
        <tr>
          <td class="m-todo">&nbsp;</td>
          <td class="m-more"><a href="#"></a></td>
        </tr>
        <tr>
          <td colspan="2"><jsp:include page="todo/sp_todo_index.jsp"/>    
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
