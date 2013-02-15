<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ page import="org.springframework.web.context.*"%>
<%@ page import="org.springframework.web.context.support.*"%>
<%@ page import="com.glaf.base.utils.StringUtil"%>
<%@ page import="com.glaf.base.modules.workspace.model.*"%>
<%@ page import="com.glaf.base.modules.workspace.service.*"%>
<%
WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);
String menu = (String)session.getAttribute(SysConstants.MENU);

MyMenuService myMenuService = (MyMenuService) wac.getBean("myMenuService");
List menuList = myMenuService.getMyMenuList(user.getId());
String context = request.getContextPath();
%>
<style type="text/css"> @import url("<%=context%>/scripts/hmenu/skin-yp.css");</style>
<script type="text/javascript">
_dynarch_menu_url = "<%=context%>/scripts/hmenu";
context = "<%=context%>";
</script>
<script type="text/javascript" src="<%=context%>/scripts/hmenu/hmenu.js"></script>
<script type='text/javascript' src='<%= context %>/scripts/main.js'></script>
<script type='text/javascript' src='<%= context %>/scripts/site.js'></script>
<script type="text/javascript">
window.onload = function() {
  //DynarchMenu.setup('menu1', { context: true});
}
function modifyInfo(){	
  var url="<%=context%>/sys/user.do?method=prepareModifyInfo";
  var width=450;
  var height=350;
  var scroll="no";
  openWindow(url, width, height, scroll);	
}
function authorize(){	
  var url="<%=context%>/sys/sysUserRole.do?method=showSysAuth";
  var width=400;
  var height=380;
  var scroll="no";
  openWindow(url, width, height, scroll);	
}
function window.onbeforeunload(){
	if(event.clientX>document.body.clientWidth&&event.clientY<0||event.altKey){
		//window.event.returnValue="确定要退出基础平台系统吗?";
		window.open("<%=context%>/sys/authorize.do?method=logout");
	}
}
</script>

<!-- <table width="100%" border="0" cellspacing="0" cellpadding="0" class="topbar">
  <tr>
    <td width="165" class="topbar-l">&nbsp;</td>
    <td width="547">
	<font color="#FF0000" size="+2"><b>
		 
		</b></font>&nbsp;
	</td>
    <td width="115"><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image5','','<%=context%>/images/s_02b.jpg',1)" class="hotspot1 dynarch-menu-ctxbutton-both"><img src="<%=context%>/images/s_02.jpg" name="Image5" width="80" height="35" border="0"></a></td>
    <td width="135"><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image4','','<%=context%>/images/s_03b.jpg',1)" class="hotspot2 dynarch-menu-ctxbutton-both"><img src="<%=context%>/images/s_03.jpg" name="Image4" width="124" height="35" border="0"></a></td>
  </tr>
</table> -->
<ul id="menu1" style="display:none">
	<li class="context-class-a-hotspot1">
		<ul id="main-popup">
		  <li><a href="<%=context%>/sys/frame.do">我的工作台</a></li>
		  <li><a href="javascript:modifyInfo()">修改用户信息</a></li>
		  <li></li>
		  <%=menu%>
		  <li><a href="<%= request.getContextPath() %>/sys/authorize.do?method=logout">退出系统</a></li>
		</ul>
	</li>
	<li class="context-class-a-hotspot2">
		<ul>
			<li><a href="javascript:openWindow('<%=context%>/workspace/mymenu.do?method=showList', 600, 450)">管理我的菜单</a></li><li></li>
      <li><a href="javascript:addToMyMenu()">添加到我的菜单</a></li><li></li>
			<%
				for (int i = 0;menuList != null && i < menuList.size(); i++) {
					MyMenu myMenu = (MyMenu) menuList.get(i);
			%>
			<li><a href="javascript:jump('<%=context%><%= URLDecoder.decode(myMenu.getUrl()) %>')"><%= myMenu.getTitle() %></a></li>
			<%
				}
			%>
		</ul>
	</li>
</ul>
