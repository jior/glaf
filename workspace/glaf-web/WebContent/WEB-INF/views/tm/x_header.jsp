<%@ page import="java.util.*"%>
<%@ page import="com.alibaba.fastjson.*"%>
<%@ page import="org.apache.commons.lang.*"%>
<%@ page import="com.glaf.core.base.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.entity.*"%>
<%@ page import="com.glaf.core.identity.*"%>
<%@ page import="com.glaf.core.security.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://www.opensymphony.com/oscache" prefix="oscache"%>
<%@ taglib uri="http://github.com/jior/glaf/tags" prefix="glaf"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
 
    String userAgent = request.getHeader("User-Agent");
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/bootstrap/css/bootstrap-responsive.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/bootstrap/css/docs.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
