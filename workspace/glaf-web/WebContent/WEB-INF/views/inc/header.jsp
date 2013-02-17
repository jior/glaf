<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%

	String userAgent = request.getHeader("User-Agent");
%>
<html>
<head>
<script language="javascript"
	src="<%=request.getContextPath()%>/scripts/main.js"></script>
<script language="javascript"
	src="<%=request.getContextPath()%>/scripts/verify.js"></script>
