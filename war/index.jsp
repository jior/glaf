<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%
	String context = request.getContextPath();
	com.glaf.base.modules.utils.ContextUtil.getInstance().setContextPath(context);
    SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);
	if (user != null && user.getAccountType() == 1) {
	  response.sendRedirect(request.getContextPath()+"/sys/spframe.do");
	} else {
      response.sendRedirect(request.getContextPath()+"/login.do");
	}
%>