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
<style type="text/css"> @import url("<%=context%>/js/hmenu/skin-yp.css");</style>
<script type="text/javascript">
_dynarch_menu_url = "<%=context%>/js/hmenu";
</script>
<script type="text/javascript" src="<%=context%>/js/hmenu/hmenu.js"></script>
<script type='text/javascript' src='<%=context%>/js/main.js'></script>
<script type='text/javascript' src='<%=context%>/js/site.js'></script>
<script type="text/javascript">
window.onload = function() {
  DynarchMenu.setup('menu', { context: true});
}
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="index-top">
  <tr align="right">
  	<td class="top-l">&nbsp;</td>
	<td style="background:url('<%=context%>/images/i_001.jpg') no-repeat; width:200px;">&nbsp;</td>
    <td><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1','','<%=context%>/images/i_03b.jpg',1)" class="hotspot1 context-align-bottom dynarch-menu-ctxbutton-both"><img src="<%=context%>/images/i_03.jpg" name="Image1" width="89" height="36" border="0"></a></td>
  </tr>
  <tr>
    <td colspan="3">
	  <ul id="menu" style="display:none">
	    <li class="context-class-a-hotspot1">
		  <ul>
			<div style="display:none">
			  <li><a href="javascript:jump('<%=context%>/module/sp/business_recruits.html',1);">招募供应商</a></li>
			  <li></li>
			</div>
			<li><a href="<%=context%>/sys/spframe.do">我的工作台</a></li>
			<li></li>
			<li><a href="javascript:jump('<%=context%>/sp/queryPrice.do?method=getNoQuoteList',3);">报价</a></li>
			<li></li>
			<li><a href="javascript:jump('<%=context%>/sp/queryPrice.do?method=showBusinessOrderLiaisonList0',3);">价格联络书确认</a></li>
			<li></li>
			<li><a href="javascript:jump('<%=context%>/sp/queryPrice.do?method=showBusinessSendOrder&flag=0',3);">订单确认</a></li>
			<li></li>
			<li><a href="javascript:jump('<%=context%>/sp/queryPrice.do?method=showContract&flag=0',3);">合同意见交流</a></li>
			<li></li>
			<li><a href="javascript:jump('<%=context%>/sp/queryPrice.do?method=showPaymentList&flag=0',3);">应付款通知书确认</a></li>
			<li></li>
			<li><a href="javascript:jump('<%=context%>/sp/queryPrice.do?method=showProjectList',3);">项目进度查询</a></li>
			<li></li>
			<li><a href="javascript:jump('<%=context%>/sp/queryPrice.do?method=getGoodsManager',3);">目录采购物品管理</a></li>
			<li></li>
			<!--li>
			  <a href="javascript:jump('#',3);">公司资料</a>
			  <ul>
				<li><a href="javascript:openWindow('<%=context%>/sp/supplier.do?method=changePaymentJump', screen.width, 600, 'yes')">付款资料变更</a></li>
				<li></li>
				<li><a href="javascript:openWindow('<%=context%>/sp/supplier.do?method=changeMessageJump', screen.width,680, 'yes')">同一法人公司更名</a></li>    
			  </ul>
			</li>
			<li></li-->
			<li><a href="javascript:openWindow('<%=context%>/sp/queryPrice.do?method=changePasswordJump', 420, 260, 'yes')">修改密码</a></li>
			<li></li>
			<li><a href="<%=context%>/sys/authorize.do?method=logout">退出系统</a></li>
		  </ul>
		</li>
	  </ul>  
	</td>
  </tr>
</table>
