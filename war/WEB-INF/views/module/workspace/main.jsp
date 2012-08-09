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
SysUser user = (SysUser)session.getAttribute(Global.LOGIN);
int userId =0;
if(user!=null) userId = (int)user.getId();
int parentId = 3;

String menu = service.getMenu(parentId, userId);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="../../css/system.css" type="text/css" rel="stylesheet">
<script src="../../js/css.js" language="javascript"></script>
<style type="text/css"> @import url("../../js/hmenu/skin-yp.css");</style>
<style type="text/css">
.hotspot { 
  position: absolute; 
  border: 1px solid #333333; 
  background-color:#FFFFFF; 
  font-size:20px;
  color:#666666;
  font-weight:bold; 
  padding-left:10px;
  padding-right:10px;
  cursor:pointer;
  width:120px;
  text-align:center;
}
</style>
<script type="text/javascript">
_dynarch_menu_url = "../../js/hmenu";
</script>
<script type="text/javascript" src="../../js/hmenu/hmenu.js"></script>
</head>

<body onLoad="DynarchMenu.setup('menu1', { context: true});" id="document">
<table width="800" border="0" cellspacing="2" cellpadding="0" align="center">
  <tr>
    <td height="30" colspan="3" valign="top" class="title">XXXX基础平台系统</td>
  </tr>
  <tr>
    <td width="46%" valign="top">
      <table width="98%" border="0">
        <tr>
          <td height="56">
     <div class="hotspot dynarch-menu-ctxbutton-left">Menu</div>

     <ul id="menu1">
      <li class="context-class-div-hotspot">
        <ul id="main-popup">
		  <%=menu%>
		  <li><a href="<%=request.getContextPath()%>/sys/authorize.do?method=logout">退出系统</a>
		</ul>
      </li>
    </ul>		  
		 </td>
        </tr>
        <tr>
          <td height="104">
		  <div class="hotspot">My Menu</div>
		  </td>
        </tr>
        <tr>
          <td><table width="100%" border="0" cellpadding="0" cellspacing="0" background="../../images/sys_13.gif">
            <tr>
              <td width="18%" height="26" nowrap="nowrap" background="../../images/sys_12.gif" class="Title">News</td>
              <td width="82%" align="right" background="../../images/sys_13.gif" ><a href="#">最新显示</a></td>
            </tr>
            
          </table></td>
        </tr>
        <tr>
          <td valign="top"><table width="100%" border="1" cellpadding="0" cellspacing="0" class="tableList">
            <tr>
              <td height="22" align="center" class="tableTitle"> 内容</td>
              <td width="20%" height="22" align="center" class="tableTitle">件数</td>
            </tr>
            <tr>
              <td height="22"> <a href="#">xxxxxxxx</a></td>
              <td height="22" align="center">2</td>
            </tr>
            <tr>
              <td height="22">&nbsp;</td>
              <td height="22" align="center">&nbsp;</td>
            </tr>
            <tr>
              <td height="22">&nbsp;</td>
              <td height="22" align="center">&nbsp;</td>
            </tr>
            <tr>
              <td height="22">&nbsp;</td>
              <td height="22" align="center">&nbsp;</td>
            </tr>
          </table></td>
        </tr>
      </table>      
    <a href="#" class="menu"></a></td>
    <td width="1%">&nbsp;</td>
    <td width="53%" valign="top"><table width="98%" border="0">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" background="../../images/sys_13.gif">
          <tr>
            <td width="18%" height="26" nowrap="nowrap" background="../../images/sys_12.gif" class="Title">预警</td>
            <td width="82%" align="right" background="../../images/sys_13.gif" ><a href="#">最新显示</a></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td valign="top"><table width="100%" border="1" cellpadding="0" cellspacing="0" class="tableList">
          <tr>
            <td height="22" align="center" class="tableTitle"> 内容</td>
            <td width="20%" height="22" align="center" class="tableTitle">件数</td>
          </tr>
          <tr>
            <td height="22"> <a href="#">xxxxxxxx</a></td>
            <td height="22" align="center">2</td>
          </tr>
          <tr>
            <td height="22">&nbsp;</td>
            <td height="22" align="center">&nbsp;</td>
          </tr>
          <tr>
            <td height="22">&nbsp;</td>
            <td height="22" align="center">&nbsp;</td>
          </tr>
          <tr>
            <td height="22">&nbsp;</td>
            <td height="22" align="center">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" background="../../images/sys_13.gif">
          <tr>
            <td width="18%" height="26" nowrap="nowrap" background="../../images/sys_12.gif" class="Title">ToDo</td>
            <td width="82%" align="right" background="../../images/sys_13.gif" ><a href="#">最新显示</a></td>
          </tr>
          
        </table></td>
      </tr>
      <tr>
        <td valign="top"><table width="100%" border="1" cellpadding="0" cellspacing="0" class="tableList">
          <tr>
            <td height="22" align="center" class="tableTitle"> 内容</td>
            <td width="20%" height="22" align="center" class="tableTitle">件数</td>
          </tr>
          <tr>
            <td height="22"> <a href="#">合同审核</a></td>
            <td height="22" align="center">2</td>
          </tr>
          <tr>
            <td height="22"><a href="#">定厂定价审核</a></td>
            <td height="22" align="center">3</td>
          </tr>
          <tr>
            <td height="22">&nbsp;</td>
            <td height="22" align="center">&nbsp;</td>
          </tr>
          <tr>
            <td height="22">&nbsp;</td>
            <td height="22" align="center">&nbsp;</td>
          </tr>
          <tr>
            <td height="22">&nbsp;</td>
            <td height="22" align="center">&nbsp;</td>
          </tr>
          <tr>
            <td height="22">&nbsp;</td>
            <td height="22" align="center">&nbsp;</td>
          </tr>
          <tr>
            <td height="22">&nbsp;</td>
            <td height="22" align="center">&nbsp;</td>
          </tr>
          <tr>
            <td height="22">&nbsp;</td>
            <td height="22" align="center">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
