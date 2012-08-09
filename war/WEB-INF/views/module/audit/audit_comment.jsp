<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.glaf.base.modules.purchase.*"%>
<%@ page import="com.glaf.base.modules.purchase.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%
String context = request.getContextPath();
String id = (String)request.getAttribute("id");
//int referType = ParamUtil.getIntParameter(request, "referType", 0);
//int referId = ParamUtil.getIntParameter(request, "referId", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script>
<script language="javascript">
window.resizeTo(380, 350);
window.moveTo((screen.width - 380) / 2, (screen.height - 350) / 2);
window.opener.location.reload();

/*if(!confirm("是否输入审批意见？")){
  window.close();  
}*/
var count =0;
function activeWindow(){
  count++;
  if(count==1){
    window.focus();
  }
}
</script>
</head>
<body onBlur="activeWindow();">
<div class="nav-title">审批意见</div>
<html:form method="post" action="/others/audit.do?method=saveComment" onsubmit="return verifyAll(this)">
<input type="hidden" name="id" value="<%=id%>">
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
  <tr>
    <td>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr class="box">
          <td class="box-lt">&nbsp;</td>
          <td class="box-mt">&nbsp;</td>
          <td class="box-rt">&nbsp;</td>
      	</tr>
      </table>
	</td>
  </tr>
  <tr>
    <td class="box-mm"><table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td>
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td class="input-box">意  见<font color="red">*</font></td>
            </tr>
            <tr>
              <td><textarea name="memo" class="input" cols="45" rows="10" datatype="string" chname="意见" nullable="no" maxlength="500"></textarea></td>
            </tr>
            <tr><td align="center" height="30"><input type="submit" name="btnSubmit" value="提交" class="button"><input type="button" name="btnClose" value="关闭" class="button" onclick="window.close();"></td></tr>
          </table>
		</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr class="box">
		  <td class="box-lb">&nbsp;</td>
		  <td class="box-mb">&nbsp;</td>
		  <td class="box-rb">&nbsp;</td>
		</tr>
	  </table>
	</td>
  </tr>
</table>
</html:form>
</body>
</html>
