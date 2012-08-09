<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
SysTree parent= (SysTree)request.getAttribute("parent");
String url = ParamUtil.getParameter(request, "url", "tree.do?method=showList&parent=");
int showCode = ParamUtil.getIntParameter(request, "showCode",0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/js/dtree/dtree.css" type="text/css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/dtree/ttTree.js" type="text/javascript"></script>
<title>树</title>
</head>
<body>
<DIV id="ttTree" class='ttTree'></DIV>
<DIV id="debugbox" class='ttTree' style="overflow:auto;width:100%;"></DIV>
<script language="javascript">
var context = "<%=request.getContextPath()%>";
function goto(action){
  parent.document.all.item("mainFrame").src=action;
}
<%
String code = "";
if(showCode==1) code = "("+parent.getCode()+")";
%>
var root = new Node(<%=parent.getId()%>, -1, "<%=parent.getName() + code%>", "javascript:goto(<%=url+parent.getId()%>)", "<%=parent.getName()%>");
var tree = new ttTree(root);
//tree.setTarget('_blank');
tree.setDebugMode(false);
tree.setBaseUrl('<%=url%>');
tree.draw('ttTree');
</script>
</body>
</html>

