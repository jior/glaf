<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.workspace.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
	String url = URLEncoder.encode(ParamUtil.getParameter(request, "url"), "GBK");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�����ҵĲ˵�</title>
<link href="<%= request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
<script src="<%= request.getContextPath() %>/js/css.js" language="javascript"></script>
<script type='text/javascript' src='<%= request.getContextPath() %>/js/main.js'></script>
<script type='text/javascript' src="<%= request.getContextPath() %>/js/verify.js"></script>
</head>

<body>
<div class="nav-title"><span class="Title">����̨</span>&gt;&gt; �����ҵĲ˵�</div>
<html:form action="/workspace/mymenu.do?method=saveAdd" method="post" onsubmit="return verifyAll(this);" >
<input name="showList" type="hidden" value="1">
<input name="url" type="hidden"  value="<%= url %>">
  <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
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
      <td class="box-mm"><table width="95%" border="0" align="center" cellpadding="5" cellspacing="0">
        <tr>
          <td width="20%" class="input-box">�˵�����*</td>
          <td><input name="title" type="text" class="input" size="45" datatype="string" nullable="no" maxsize="100" chname="�˵�����"></td>
        </tr>
      </table>
        <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td height="35" align="center" valign="bottom"><input name="btnSave" type="submit" class="button" value="����"></td>
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
</html:form>
</body>
</html>
