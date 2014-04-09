<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://github.com/jior/glaf/tags" prefix="glaf"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
pageContext.setAttribute("contextPath", context);
int parent=ParamUtil.getIntParameter(request, "parent", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
</head>

<body>
<html:form action="${contextPath}/sys/dictory.do?method=saveLoadDictory" method="post" onsubmit="return verifyAll(this);"> 
<input type="hidden" name="nodeId" value="<%=parent%>">
<table width="100%" height="258"  border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <th> <table width="200" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td height="50" align="center">
		  </td>
        </tr>
        <tr> 
          <td align="center"><img src="<%=request.getContextPath()%>/images/icon_6.jpg" width="6" height="7">重新装载基础数据</td>
        </tr>
        <tr> 
          <td align="center">&nbsp; <input name="btn_save" type="submit" value="确定" class="butt-normal"> 
          </td>
        </tr>
      </table></th>
  </tr>
</table>
</html:form> 
</body>
</html>
