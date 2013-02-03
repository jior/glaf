<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ page import="org.springframework.web.context.*"%>
<%@ page import="org.springframework.web.context.support.*"%>
<%
WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
SysApplicationService service = (SysApplicationService) wac.getBean("sysApplicationService");
SysUser user =com.glaf.base.utils.RequestUtil.getLoginUser(request);
int userId =0;
if(user!=null) userId = (int)user.getId();
int parentId = 3;

String menu = service.getMenu(parentId, userId);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css"> @import url("../js/hmenu/skin-yp.css");</style>
<style type="text/css">
      .hotspot { position: absolute; border: 1px solid #f00; background-color: #fea; color: #000; padding: 10px; }
</style>
<script type="text/javascript">
_dynarch_menu_url = "<%=request.getContextPath()%>/js/hmenu";
</script>
<script type="text/javascript" src="../js/hmenu/hmenu.js"></script>
</head>
<body onLoad="DynarchMenu.setup('menu1', { context: true});" id="document">
<div class="hotspot dynarch-menu-ctxbutton-left" style="left: 50px; top: 100px">Menu</div>

     <ul id="menu1">
      <li class="context-class-div-hotspot">
        <ul id="main-popup"><%=menu%></ul>
      </li>
    </ul>
</body>
</html>