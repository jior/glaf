<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%
	String contextPath = request.getContextPath();
	com.glaf.base.utils.ContextUtil.setContextPath(contextPath);
	com.glaf.core.util.ContextUtils.setContextPath(contextPath);
	SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);
	if (user != null) {
		response.sendRedirect(request.getContextPath() + "/mx/my/main");
	} else {
		response.sendRedirect(request.getContextPath() + "/mx/login");
	}
%>