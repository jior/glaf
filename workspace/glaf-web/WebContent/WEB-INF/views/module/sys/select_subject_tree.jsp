<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
long parent=ParamUtil.getIntParameter(request, "parent", 0);

%>
<html>
<head>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/js/dtree/dtree.css" type="text/css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/dtree/ttTree.js" type="text/javascript"></script>
<script language="javascript">
function selData(){  
  var a= new Array(0,null);
  window.returnValue=a;
  window.close();
}
</script>
<title>科目费用</title>
</head>

<body>
<table width="300" border="0" align="center" cellspacing="0" cellpadding="0">
  <tr>
    <td class="nav-title">请选择科目费用：&nbsp;</td>
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
    <td class="box-mm"><table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
      <tr>
	  	<td><a href="javascript:selData();">不选择</a></td>
		</tr><tr>
        <td><DIV id="ttTree" class='ttTree' style="width:100%; height:220px;overflow-x:auto; overflow-y:auto;"></DIV></td>
      </tr>
    </table></td>
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
function goto(action, obj){
  window.returnValue = obj ? new Array(obj.id, obj.name, obj.code, obj.feeSum) : null;
  window.close();
}
var root = new Node(0, -1, "", "javascript:goto(0)", "根目录");
var tree = new ttTree(root);
tree.setSubTreeUrl('/sys/subject.do?method=showSubjectTreeList&parent='+<%=parent%>);
//tree.setTarget('_blank');
tree.setDebugMode(false);
tree.setBaseUrl('');
tree.setTarget('');
tree.draw('ttTree');
</script>
</body>
</html>