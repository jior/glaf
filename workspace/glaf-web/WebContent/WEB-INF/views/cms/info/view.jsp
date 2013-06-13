<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.alibaba.fastjson.*"%>
<%@ page import="com.glaf.core.el.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.entity.*"%>
<%@ page import="com.glaf.cms.info.model.*"%>
<%@ taglib uri="http://github.com/jior/glaf/tags" prefix="glaf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
        PublicInfo publicInfo = (PublicInfo)request.getAttribute("publicInfo");
	    String content = publicInfo.getContent();
		if(content==null){
			content="";
		}
	    
%>
<!DOCTYPE html>
<html>
<title>${publicInfo.subject}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/info.css" />
</head>
<body style="margin:2px;">  
<div class="news_main">
<div class="news">
<div class="clearfix">
<div class="subject">
<h3><a href='' title='${publicInfo.subject}'>${publicInfo.subject}</a></h3>
</div>
</div>
<div id="content" style="font-size: 15px"><%=content%></div>
<div id="x_files" style="font-size: 12px; padding-left: 60px;"><iframe
	id="newFrame" name="newFrame" width="0" height="0"></iframe> 
<c:forEach items="${dataFiles}" var="dataFile">
	<a
		href='<%=request.getContextPath()%>/mx/lob/lob/download?fileId=${dataFile.fileId}'
		target="newFrame"> ${dataFile.filename}&nbsp;<img
		src="<%=request.getContextPath()%>/images/download.gif"
		border="0"> </a>
</c:forEach></div>
<br />
 
</div>

<div class="news_info" style="background-color: transparent;"><span
	class='date'><fmt:formatDate value="${publicInfo.createDate}"
	pattern="yyyy-MM-dd HH:mm" /></span> by ${publicInfo.createByName} <br />
 <span class='view'>有${publicInfo.viewCount}人浏览</span>  
</div>

</body>
</html>
 