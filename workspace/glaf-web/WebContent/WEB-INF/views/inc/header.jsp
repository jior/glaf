<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.*"%>
<%@ page import="com.glaf.core.base.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.entity.*"%>
<%@ page import="com.glaf.core.identity.*"%>
<%@ page import="com.glaf.core.security.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://github.com/jior/glaf/tags" prefix="glaf"%>
<%

	String userAgent = request.getHeader("User-Agent");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<script language="javascript"
	src="<%=request.getContextPath()%>/scripts/main.js"></script>
<script language="javascript"
	src="<%=request.getContextPath()%>/scripts/verify.js"></script>
