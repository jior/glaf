<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	request.setAttribute("contextPath", request.getContextPath());

	//全局变量定义
	String appTitle="GLAF";//应用标题标签
	String appKeywords="";//应用关键字标签
	String appDescription="";//应用描述标签
%>