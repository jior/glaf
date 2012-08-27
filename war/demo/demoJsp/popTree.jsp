<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="sysSrc.framework.*" %>
<%@ page import="baseSrc.framework.*" %>
<%@ page import="baseSrc.common.*" %>
<%@ page import="sysSrc.common.*" %>
<%@ page import="java.util.*" %>
<%
	String context = request.getContextPath();
	
	BaseCom baseCom = (BaseCom)request.getSession().getAttribute(BaseConstants.ISC_BASE_BEANCOM_ID);
	
	//取得权限
	Map<String, List> privilegeMap = baseCom.getUserPrivilageMap();
	
	//生成菜单
	String menuString = "";
	PopTree treeManager = PopTree.getInstance();
	Map menusMap = treeManager.getMenusMap();
	
	//资材品番申请
	Map menuMap = (Map)menusMap.get("menu1");
	if(null != menuMap && 0 < menuMap.size()){
		String subString = treeManager.getMenusToString(context, menuMap, privilegeMap);
		if(!"".equals(subString)){
			menuString = menuString + "<li class=\"context-class-td-hotspot1\">" + subString + "</li>";
		}
	}
	
	//供应商管理

	menuMap = (Map)menusMap.get("menu2");
	if(null != menuMap && 0 < menuMap.size()){
		String subString = treeManager.getMenusToString(context, menuMap, privilegeMap);
		if(!"".equals(subString)){
			menuString = menuString + "<li class=\"context-class-td-hotspot2\">" + subString + "</li>";
		}
	}
	
	//系统管理
	menuMap = (Map)menusMap.get("menu3");
	if(null != menuMap && 0 < menuMap.size()){
		String subString = treeManager.getMenusToString(context, menuMap, privilegeMap);
		if(!"".equals(subString)){
			menuString = menuString + "<li class=\"context-class-td-hotspot3\">" + subString + "</li>";
		}
	}
	
	if(!"".equals(menuString)){
		menuString = "<ul id=\"menu1\" style=\"display:none\">" + menuString + "</ul>";
	}else{
		menuString = "<ul id=\"menu1\" style=\"display:none\"><li></li></ul>";
	}
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>menuSample</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="menu,hmenu">
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<link rel="stylesheet" type="text/css" href="<%=context %>/sys/sysJs/hmenu/skin-yp.css" />
	<script type="text/javascript" src="<%=context%>/sys/sysJs/hmenu/hmenu.js"></script>
  </head>
  <script type="text/javascript">
	window.onload = function() {
		DynarchMenu.setup('menu1', { context: true});
	}
  </script>
  <body>
	<div style="text-align:center" class="sidebar" id="t">
		<table width="203">
			<tr>
				<td class="hotspot1 context-align-right dynarch-menu-ctxbutton-both menuBg">资材品番申请</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="hotspot2 context-align-right dynarch-menu-ctxbutton-both menuBg">供应商管理</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="hotspot3 context-align-right dynarch-menu-ctxbutton-both menuBg">系统管理</td>
			</tr>
		</table>
		<%=menuString %>
	</div>
  </body>
</html>
