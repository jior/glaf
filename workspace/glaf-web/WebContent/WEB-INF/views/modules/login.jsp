<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://github.com/jior/glaf/tags" prefix="glaf"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%
    String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(context);
	pageContext.setAttribute("contextPath", context);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基础平台系统</title>
<link href="${contextPath}/css/system.css" type="text/css" rel="stylesheet">
<link href="${contextPath}/css/site.css" rel="stylesheet" type="text/css">
<script src="${contextPath}/scripts/css.js" language="javascript"></script>
<script src="${contextPath}/scripts/verify.js" language="javascript"></script>
</head>
<body class="body">
 
<br><br><br><br>
<html:form method="post" action="${contextPath}/login.do?method=login" onsubmit="return verifyAll(this);" > 
<table border="0" align="center" cellpadding="0" cellspacing="0" id="login">

  <tr>
    <td width="201" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
  
      <tr>
        <td height="262"><table border="0" align="center" cellpadding="4" cellspacing="0">
          <tr>
            <td align="center" class="login-box">
			</td>
          </tr>
          <tr>
            <td align="center"  >&nbsp;&nbsp;账号：&nbsp;&nbsp;
              <input name="x" type="text" size="15" class="input" datatype="string" nullable="no" maxsize="20" chname="账号" value="root"></td>
          </tr>
          <tr>
            <td align="center"  >&nbsp;&nbsp;密码：&nbsp;&nbsp;
              <input name="y" type="password" size="16" class="input" datatype="string" nullable="no" maxsize="20" chname="密码" value="111111"></td>
          </tr>
          <tr>
            <td align="right">
			  <input name="login" type="submit" id="login" value="登录" class="login-btn">
              &nbsp;
              <!-- <input name="f" type="reset" id="f" value="重置" class="login-btn"> -->
			</td>
          </tr>
        </table></td>
      </tr>
    
    </table></td>
    
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
