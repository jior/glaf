<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.glaf.ui.model.*"%>
<%
    String context = request.getContextPath();
	Panel panel = (Panel)request.getAttribute("panel");
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<body>
  <%if(panel != null && panel.getContent() != null){out.println(panel.getContent());}%>
</body>
</html>
