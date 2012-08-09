<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<SCRIPT src="<%=request.getContextPath()%>/js/main.js"></SCRIPT>
<title></title>
<link type="text/css" href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet">
</head>

<body>
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <th width="100%" valign="top"><div align="left">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0" background="<%=request.getContextPath()%>/images/content_title_bg.jpg">
        <tr>
          <td width="100" height="45" valign="bottom"><table width="100%" height="100%"  border="0" align="right" cellpadding="0" cellspacing="0">
            <tr>
              <td width="25"><div align="center"><img src="<%=request.getContextPath()%>/images/content_lt.jpg" width="11" height="34"></div></td>
                  <td><span class="style2">Message</span></td>
            </tr>
          </table></td>
          <td valign="middle"><table width="95%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><div align="right"> </div></td>
            </tr>
          </table></td>
        </tr>
      </table>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
        <table width="95%" align="center" border="0" cellpadding="0" cellspacing="0">
          <tr> 
            <td class="tdborder"> <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td ><table width="265" align="center"  border="0" cellpadding="2" cellspacing="2">
                      <tr> 
                        <td height="20" align="center" class="fontlist" ><img src="<%=request.getContextPath()%>/images/icon_6.jpg" width="6" height="7"> 
                         <bean:message key="error.global"/></td>
                      </tr>
                      <tr> 
                        <td height="20" align="center">&nbsp; </td>
                      </tr>
                    </table> </td>
                </tr>
              </table></td>
          </tr>
        </table>
       </div></th>
  </tr>
</table>
</body>
</html>

