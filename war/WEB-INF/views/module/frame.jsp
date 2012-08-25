<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
String menu = (String)session.getAttribute(SysConstants.MENU);

MyMenuService myMenuService = (MyMenuService) wac.getBean("myMenuService");
List menuList = myMenuService.getMyMenuList(user.getId());
String contextPath = request.getContextPath();
com.glaf.base.modules.utils.ContextUtil.getInstance().setContextPath(contextPath);
//System.out.println("-----------------------------------------contextPath:"+contextPath);
MessageService messageService = (MessageService) wac.getBean("messageService");
int msgPageSize = 5;
PageResult messagePager = messageService.getNoReadList(user.getId(), new HashMap(), 1, msgPageSize);
List messageList = messagePager.getResults();
int count = 0;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>基础平台系统</title>
<link href="../css/site.css" rel="stylesheet" type="text/css">
<link href="<%=contextPath%>/css/site.css" rel="stylesheet" type="text/css">
<script src="<%=contextPath%>/js/main.js" language="javascript"></script>
<style type="text/css"> 
@import url("<%=contextPath%>/js/hmenu/skin-yp.css");
.STYLE1 {color: #FF0000}
</style>
<script type="text/javascript">
_dynarch_menu_url = "<%=contextPath%>/js/hmenu";
</script>
<script type="text/javascript" src="<%=contextPath%>/js/hmenu/hmenu.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/main.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/site.js"></script>
<script type="text/javascript">
<!--
  function openMoreMsg() {
	  openWindow('<%=contextPath%>/workspace/message.do?method=showReceiveList', 600, 450);
	}
	function openMsg(id) {
		openWindow('<%=contextPath%>/workspace/message.do?method=showMessage&id=' + id, 600, 450);
	}
function modifyInfo(){	
  var url="<%=contextPath%>/sys/user.do?method=prepareModifyInfo";
  var width=450;
  var height=350;
  var scroll="no";
  openWindow(url, width, height, scroll);	
}

function modifyPwd(){	
  var url="<%=contextPath%>/sys/user.do?method=prepareModifyPwd";
  var width=450;
  var height=250;
  var scroll="no";
  openWindow(url, width, height, scroll);	
}

function authorize(){	
  var url="<%=contextPath%>/sys/sysUserRole.do?method=showSysAuth";
  var width=400;
  var height=380;
  var scroll="no";
  openWindow(url, width, height, scroll);	
}
function exit(){
  var url="<%=contextPath%>/sys/authorize.do?method=logout";
  if(confirm("真的要退出系统吗？")){
    window.location=url;
  }
}
function window.onbeforeunload(){
	if(event.clientX>document.body.clientWidth&&event.clientY<0||event.altKey){
		//window.event.returnValue="确定要退出基础平台系统吗?";
		window.open("<%=contextPath%>/sys/authorize.do?method=logout");
	}
}
function openDocument()
{
	var url = "<%=contextPath%>/module/document/page1805.jsp";
	openMaxWindow(url);
}
//-->
</script>
</head>

<body onLoad="DynarchMenu.setup('menu', { context: true});MM_preloadImages('<%=contextPath%>/images/i_05b.jpg')" id="document">

<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="35" colspan="2" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="index-top">
      <tr>
        <td class="top-l">&nbsp;</td>
        <td class="top-r">
		<font color="#FF0000" size="+2"><b>
		 
		</b></font>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top" class="m-left"><table width="192" border="0" align="right" cellpadding="0" cellspacing="0">
      <tr>
        <td height="105"><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1','','<%=contextPath%>/images/i_03b.jpg',1)" class="hotspot1 context-align-bottom dynarch-menu-ctxbutton-both"><img src="<%=contextPath%>/images/i_03.jpg" name="Image1" width="89" height="36" border="0"></a></td>
      </tr>
      <tr>
        <td height="120"><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2','','<%=contextPath%>/images/i_04b.jpg',1)" class="hotspot2 context-align-bottom dynarch-menu-ctxbutton-both"><img src="<%=contextPath%>/images/i_04.jpg" name="Image2" width="143" height="40" border="0"></a>
	  <ul id="menu" style="display:none">
      <li class="context-class-a-hotspot1">
        <ul>
		  <li><a href="<%=contextPath%>/sys/frame.do">我的工作台</a></li>
		  <li><a href="javascript:modifyInfo()">修改用户信息</a></li>
		  <li><a href="javascript:modifyPwd()">修改密码</a></li>
		  <li></li>
		  <%=menu%>
	      <li><a href="<%=contextPath%>/sys/authorize.do?method=logout">退出系统</a></li>
	    </ul>
      </li>
	    <li class="context-class-a-hotspot2">
				<ul>
					<li><a href="javascript:openWindow('<%=contextPath%>/workspace/mymenu.do?method=showList', 600, 450)">管理我的菜单</a></li><li></li>
					<%
					if(menuList!=null){
					  for (int i = 0; i < menuList.size(); i++) {
						  MyMenu myMenu = (MyMenu) menuList.get(i);
					%>
					<li><a href="javascript:jump('<%=request.getContextPath()%>/<%= URLDecoder.decode(myMenu.getUrl()) %>')"><%= myMenu.getTitle() %></a></li>
					<%
					  }
					}
					%>
				</ul>
      </li>
    </ul>				</td>
      </tr>
      <tr>
        <td height="95">
		<a href="javascript:exit();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image4','','<%=contextPath%>/images/i_12b.jpg',1)"><img src="<%=contextPath%>/images/i_12.jpg" name="Image4" width="89" height="36" border="0"></a>
		</td>
      </tr>
    </table></td>
    <td valign="top" class="m-right"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="m-box">
      <tr>
        <td height="27" colspan="2" align="right"><jsp:include page="/WEB-INF/views/module/login_info.jsp" flush="true"/> &nbsp;&nbsp;&nbsp;</td>
      </tr>
      <tr>
        <td class="m-newsalarm">&nbsp;</td>
        <td class="m-more"><a href="javascript:openMoreMsg()">更多&gt;&gt;</a></td>
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
             <jsp:include page="todo/todo_index.jsp" />
		</td>
      </tr>
      <tr>
        <td height="20" colspan="2">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
