<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    String context = request.getContextPath();
	com.glaf.base.modules.utils.ContextUtil.getInstance().setContextPath(context);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基础平台系统</title>
<link href="../css/system.css" type="text/css" rel="stylesheet">
<link href="../css/site.css" rel="stylesheet" type="text/css">
<script src="../js/css.js" language="javascript"></script>
<script src="../js/verify.js" language="javascript"></script>
</head>
<body class="body">
<script language="javascript">
/*var openWinFlag;
function resizeWindow(){
  var openWinFlag = window.open(top.location, "TMS", "toolbar=no,status=1,scrollbars=yes,resizable=1,menubar=no");
  openWinFlag.resizeTo(1024, 768);
  openWinFlag.focus();
}
if(window.name != "TMS"){
  resizeWindow();  
  window.opener=null;
  self.close();
}*/
</script>
<br><br><br><br>
<html:form method="post" action="/sys/authorize.do?method=login" onsubmit="return verifyAll(this);" > 
<table border="0" align="center" cellpadding="0" cellspacing="0" id="login">
  <!-- <tr>
    <td colspan="2"><img src="../images/l_01.jpg" width="148" height="44"></td>
  </tr> -->
  <tr>
    <td width="201" valign="top" background="../images/l_03.jpg"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <!-- <tr>
        <td><img src="../images/l_02.jpg" width="201" height="62"></td>
      </tr> -->
      <tr>
        <td height="262"><table border="0" align="center" cellpadding="4" cellspacing="0">
          <tr>
            <td align="center" class="login-box">
			<html:messages id="message" message="true"> 
              <bean:write name="message"/> 
            </html:messages> 
			</td>
          </tr>
          <tr>
            <td align="center" class="login-box">招聘号：
              <input name="account" type="text" size="15" class="input" datatype="string" nullable="no" maxsize="20" chname="Account"></td>
          </tr>
          <tr>
            <td align="center" class="login-box">密&nbsp;&nbsp;码：
              <input name="password" type="password" size="15" class="input" datatype="string" nullable="no" maxsize="20" chname="Password"></td>
          </tr>
          <tr>
            <td align="right"><input name="login" type="submit" id="login" value="登录" class="login-btn">
              &nbsp;
              <input name="f" type="reset" id="f" value="重置" class="login-btn"></td>
          </tr>
        </table></td>
      </tr>
      <!-- <tr>
        <td><img src="../images/l_04.jpg" width="201" height="12"></td>
      </tr> -->
    </table></td>
    <!-- <td width="395" valign="top"><img src="../images/l_05.jpg" width="395" height="53"><img src="../images/l_06.jpg" width="180" height="224"><img src="../images/l_07.jpg" width="215" height="224"><img src="../images/l_08.jpg" width="395" height="59"></td> -->
  </tr>
</table>
</html:form>
<script type="text/javascript">
<!--
function setVmiddle() {
  var obj = document.getElementById('login');
	var clientHeight = document.body.clientHeight;
	obj.style.marginTop = (clientHeight - 380) / 2;
}
//setVmiddle();
//-->
</script>
</body>
</html>
