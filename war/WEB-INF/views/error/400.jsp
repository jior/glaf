<%@ page contentType="text/html;charset=gbk" language="java"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Ӧ�ó������</title>
<link type="text/css" href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet">
<SCRIPT type="text/javascript">
function showDetailInfo(){
  var infoDiv = document.getElementById("detailInfoDiv");
  if(infoDiv.style.display == 'block'){
  	infoDiv.style.display = 'none'
  }else{
  	infoDiv.style.display = 'block'
  }
}
</SCRIPT>
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
                  <td align="center"><table width="609" height="300" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" style="table-layout:fixed;word-break:break-all;">
                      <tr> 
                        <td height="20" align="center" class="fontlist" ><img src="<%=request.getContextPath()%>/images/icon_6.jpg" width="6" height="7"> 
                          Err 400: System occurs error<br/>
                      Ӧ�ô���<%=exception.getMessage()%>;���ϵͳ����Ա��ϵ���������<br>
		      [<a onclick="showDetailInfo();" style="cursor:hand;">�鿴��ϸ��Ϣ</a>]
			  </td>
                      </tr>
                      <tr>
          <td width="609" align="center" valign="top">
		      <div id="detailInfoDiv" align="left" style="display:none;">
			      <b>״̬����</b>:<%= request.getAttribute("javax.servlet.error.status_code") %><br>
						<b>���ʵ�url</b>:<%= request.getAttribute("javax.servlet.error.request_uri") %><br>
						<b>���ʵ�Servlet����</b>:<%= request.getAttribute("javax.servlet.error.servlet_name") %><br>
						<b>�쳣����</b>:<%= request.getAttribute("javax.servlet.error.exception_type") %><br>
						<b>�쳣��Ϣ</b>:<%= request.getAttribute("javax.servlet.error.exception") %><br>
						<b>������Ϣ</b>:<%= request.getAttribute("javax.servlet.error.message") %><br>
						<b>�쳣��ջ��Ϣ</b>:<br>
						<% java.io.StringWriter stackTrace = new java.io.StringWriter();
						   java.io.PrintWriter writer = new java.io.PrintWriter(stackTrace);
						   exception.printStackTrace(writer);
						   writer.close();
						   stackTrace.close();
						%>
						<%=stackTrace%>
				  </div>
		      </td>
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
