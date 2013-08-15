<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(context);
    SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);
	if (user != null) {
	    response.sendRedirect(request.getContextPath()+"/mx/my/main");
	} else {
        response.sendRedirect(request.getContextPath()+"/mx/login");
	}
%>