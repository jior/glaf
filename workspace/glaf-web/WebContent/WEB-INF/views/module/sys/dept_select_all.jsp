<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
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
<link href="<%=request.getContextPath()%>/js/dtree/dtree.css" type="text/css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/dtree/deptAllTree.js" type="text/javascript"></script>
<script language="javascript" src='<%=context%>/js/main.js'></script>
<script language="javascript">
function selData(value, title){  
  var a= new Array(value, title);
  window.returnValue=a;
  window.close();
}
function confirmData(){
	var obj = document.getElementsByTagName("input");
	var values = new Array();
	var index = 0;
	var isReturnString = true;
	for (var i = 0; i < obj.length; i++)
	{
		var e = obj.item(i);
		if(e.type =="checkbox")
		{
			if (e.checked)
			{
				if (arguments.length >= 2)
				{
					isReturnString = false;
					var m = 0;
					values[index] = new Array();
					for (var j = 1; j < arguments.length; j++)
					{
						if (arguments[j] == true || arguments[j] == false)
						{
							isReturnString = arguments[j];
						}
						else
						{
							values[index][m] = e.getAttribute(arguments[j]);
							m++;
						}
					}
				}
				else
				{
					values[index] = e.value;
				}
				index++;
			}
		}
	}
	
	if (isReturnString)
	{
		 window.returnValue=values;
		 window.close();
	}
	else
	{
	  	window.returnValue=values;
		window.close();
	}
}
</script>
</head>
<body>
<form action="#" method="post" name="form">
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
		  <td><a href="javascript:selData('1','2');">不选择</a></td>
		  <td><a href="javascript:confirmData();">确认</a></td>
	    </tr>
		<tr>
		  <td colspan="2"><DIV id="ttTree" class='ttTree' style="width:100%; height:180px;overflow-x:auto; overflow-y:auto;"></DIV></td>
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
</form>
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
