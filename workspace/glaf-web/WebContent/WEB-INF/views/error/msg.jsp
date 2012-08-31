<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html>
<head>
<jsp:include page="../management/meta.jsp"/>
</head>

<body bgcolor="#f0f0f0" onload="document.loginform.username.focus();">
<br/><br/><br/><br/>
<table align="center" border="0" width="250" cellpadding="6" cellspacing="0" class="std">
  <tr> 
    <th colspan="2"><em><bean:message key="menu.title"/></em></th>
  </tr>
  <tr> 
    <td colspan="2" align="center" nowrap>
	<html:messages id="message" message="true"> 
      <bean:write name="message"/> 
    </html:messages> 
	</td>
  </tr>
</table>
<div align="center">
  <span style="font-size:7pt"><bean:message key="login.version"/></span>
</div>
</body>
</html>
