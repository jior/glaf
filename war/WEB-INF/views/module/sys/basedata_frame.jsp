<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
int parent=ParamUtil.getIntParameter(request, "parent", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">
<link type="text/css" href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet">
<SCRIPT src="<%=request.getContextPath()%>/js/main.js"></SCRIPT>
<script language="javascript">
function add(){
  mainFrame.add();
}
function del(){
  mainFrame.del();
}
</script>
</head>

<body>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <th width="11">&nbsp;</th>
    <th width="995" valign="top"><div align="left"> 
        <table width="100%"  border="0" cellpadding="0" cellspacing="0" background="<%=request.getContextPath()%>/images/content_title_bg.jpg">
          <tr> 
            <td width="200" height="45" valign="bottom"><table width="100%" height="100%"  border="0" align="right" cellpadding="0" cellspacing="0">
                <tr> 
                  <td width="25"><div align="center"><img src="<%=request.getContextPath()%>/images/content_lt.jpg" width="11" height="34"></div></td>
                  <td><span class="style2">基础数据</span></td>
                </tr>
              </table></td>
            <td valign="middle"><table width="95%"  border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td><div align="right"></div></td>
                </tr>
              </table></td>
          </tr>
        </table>
        <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
          <tr> 
            <td>
      <jsp:include page="/sys/application.do?method=showNavMenu" flush="true"> 
        <jsp:param name="parent" value="<%=parent%>"/>     
      </jsp:include>
			 </td>
          <tr> 
            <td colspan="4" valign="top" class="tdborder"> <table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
                <tr> 
                  <td><iframe id="mainFrame" name="mainFrame" src="dictory.do?method=showFrame" width="100%" frameborder="0" height="370"></iframe></td>
                </tr>
              </table></td>
          </tr>
        </table>
      </div></th>
  </tr>
</table>
</body>
</html>
