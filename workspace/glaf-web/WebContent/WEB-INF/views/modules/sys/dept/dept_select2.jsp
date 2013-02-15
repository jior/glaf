<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
SysTree parent= (SysTree)request.getAttribute("parent");
%>
<html>
<head>
<base target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/scripts/dtree/dtree.css" type="text/css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/scripts/dtree/deptTree.js" type="text/javascript"></script>
<script type="text/javascript">
      context = "<%=context%>";
</script>
<script language="javascript">
function selData(value, title){  
  var a= new Array(value, title);
  window.returnValue=a;
  window.close();
}
</script>
</head>

<table width="330" border="0" align="center" cellspacing="0" cellpadding="0">      
      <tr>
        <td class="nav-title">请选择：&nbsp;</td>
      </tr>
    </table>
<table width="300" border="0" align="center" cellpadding="0" cellspacing="0" class="box">  
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">      
      <tr class="box">
        <td class="box-lt">&nbsp;</td>
        <td class="box-mt">&nbsp;</td>
        <td class="box-rt">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="box-mm">
	  <table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
	    <tr>
		  <td>
		  <a href="javascript:selData('2','各使用部门');">各使用部门</a></td>
		</tr>
		<tr>
		  <td>
		  <a href="javascript:selData('0','无');">无使用部门</a>
		</tr>		
		<tr>
		  <td>
		  有使用部门请选择部门：
		  <DIV id="ttTree" class='ttTree' style="width:100%; height:180px;overflow-x:auto; overflow-y:auto;"></DIV></td>
		</tr>
	  </table>	
    </td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="box">
        <td class="box-lb">&nbsp;</td>
        <td class="box-mb">&nbsp;</td>
        <td class="box-rb">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
<DIV id="debugbox" class='ttTree' style="overflow:auto;width:100%;"></DIV>
<script language="javascript">
var root = new Node(<%=parent.getId()%>, -1, "<%=parent.getName()%>", "", "<%=parent.getName()%>",0);
var tree = new ttTree(root);
tree.setDebugMode(false);
tree.setTarget("");
tree.draw('ttTree');
</script>
</body>
</html>
