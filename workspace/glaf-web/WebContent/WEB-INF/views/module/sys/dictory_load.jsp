<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
int parent=ParamUtil.getIntParameter(request, "parent", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script>
<</head>

<body>
<html:form action="/sys/dictory.do?method=saveLoadDictory" method="post" onsubmit="return verifyAll(this);"> 
<input type="hidden" name="typeId" value="<%=parent%>">
<table width="100%" height="258"  border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <th> <table width="200" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td height="50" align="center"> <html:messages id="message" message="true"> 
            <bean:write name="message"/> </html:messages> </td>
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
