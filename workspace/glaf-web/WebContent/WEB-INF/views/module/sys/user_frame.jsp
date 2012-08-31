<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String url = "user.do?method=showList&parent=";
url=java.net.URLEncoder.encode(url);
int parent=ParamUtil.getIntAttribute(request, "parent", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet">
<title>基础平台系统</title>
<script language="JavaScript">
function add(){
  mainFrame.add();
}
function del(){
  mainFrame.del();
}
</script>
</head>
    <frameset id="allFrame" marginwidth="0" marginheight="0" leftmargin="0" topmargin="0"
        framespacing="3" border="1" frameborder="yes" cols="165,*" style="border-color: #000000;">
        
        <frame style="border-right: #99ccff 1px solid; border-top: #003366 1px solid;" leftmargin="0"
            topmargin="0" border="0" frameborder="no" scrolling="auto" id="leftFrame" name="leftFrame"
            src="tree.do?method=showLeft&parent=<%=parent%>&url=<%=url%>">
        
        <frame style="border-top: #003366 1px solid; border-left: #99ccff 2px groove;" border="0"
            frameborder="no" scrolling="auto" id="mainFrame" name="mainFrame" bordercolor="#DEE3E7"
            src="user.do?method=showList&parent=<%=parent%>">
    </frameset>
<noframes>
</noframes>
</html>
