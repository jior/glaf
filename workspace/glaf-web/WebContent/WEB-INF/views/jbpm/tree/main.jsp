<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    com.glaf.core.util.RequestUtils.setRequestParameterToAttribute(request);
%>
<frameset name="frm" cols="225,*" border="5" class="frame-border"
	frameborder="1" framespacing="5"
	bordercolor="${frame_backgroud}">
	<frame name="treeframe" target="treeframe"
		src="<%=request.getContextPath()%>/mx/jbpm/tree/exttree?location=${location}">
	<frame name="mainframe" target="mainframe"
		src="<%=request.getContextPath()%>/mx/jbpm/tree"
		scrolling="auto">
	<noframes>
	<body>
	<p>此网页使用了框架，但您的浏览器不支持框架。</p>
	</body>
	</noframes>
</frameset>
</html>