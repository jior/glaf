<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://github.com/jior/glaf/tags" prefix="glaf"%>
<html>
<head>
</head>

<body bgcolor="#f0f0f0" onload="document.loginform.username.focus();">
<br/><br/><br/><br/>
<table align="center" border="0" width="250" cellpadding="6" cellspacing="0" class="std">
  <tr> 
    <th colspan="2"><em><glaf:message key="menu.title"/></em></th>
  </tr>
  <tr> 
    <td colspan="2" align="center" nowrap>
	<glaf:messages id="message" message="true"> 
      <glaf:write name="message"/> 
    </glaf:messages> 
	</td>
  </tr>
</table>
<div align="center">
  <span style="font-size:7pt"><glaf:message key="login.version"/></span>
</div>
</body>
</html>
