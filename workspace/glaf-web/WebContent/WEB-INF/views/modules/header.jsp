<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ page import="com.glaf.base.utils.StringUtil"%>
<%@ page import="com.glaf.base.modules.workspace.model.*"%>
<%@ page import="com.glaf.base.modules.workspace.service.*"%>
<%
	SysUser user = com.glaf.base.utils.RequestUtil
			.getLoginUser(request);

	MyMenuService myMenuService = ContextFactory
			.getBean("myMenuService");
	List menuList = myMenuService.getMyMenuList(user.getId());
	String context = request.getContextPath();
%>