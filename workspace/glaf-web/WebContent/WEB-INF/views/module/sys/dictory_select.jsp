<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%
String context = request.getContextPath();
Iterator iter= (Iterator)request.getAttribute("list");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/main.js'></script>
<script language="javascript" src='<%=context%>/js/verify.js'></script>
</head>
<script language="javascript">
function selData(){
  var el = document.all.id;
  for (var i=0; i<el.length; i++) {
    var e = el[i];
    if(e.checked){
	  break;
	}
  }
  var a= new Array(e.value, e.title, e.code);
  window.returnValue=a;
  window.close();
}
</script>
<body>
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
	  <div style="width:100%; height:180px;overflow-x:auto; overflow-y:auto;">
      <table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
	    <tr>
		  <td width="7%" align="center"><input type="radio" name="id" value="" checked onDblClick="selData()"></td>
		  <td width="93%">不选择</td>
		</tr>
<%
while(iter.hasNext()){
  BaseDataInfo bdi = (BaseDataInfo)iter.next();
%>        		
		<tr>
          <td align="center"><input type="radio" name="id" value="<%=bdi.getId()%>" title="<%=bdi.getName()%>" code="<%=bdi.getCode()%>" onDblClick="selData()"></td>
          <td>
<%
for(int i=1; i<bdi.getDeep();i++){
  out.print("&nbsp;&nbsp;");
}
out.print("- "+bdi.getName());
%></td>
        </tr>
<%
}
%>		
      </table>
	  </div>
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
<table width="300" border="0" align="center" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center" height="30"><input type="button" name="btn_save" value="确定" class="button" onClick="selData()" /></td>
  </tr>
</table>
</body>
</html>
