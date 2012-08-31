<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.workspace.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
  Message bean = (Message) request.getAttribute("bean");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="../../css/site.css" type="text/css" rel="stylesheet">
<link href="<%= request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
<script type='text/javascript' src="<%= request.getContextPath() %>/js/css.js"></script>
<script type='text/javascript' src='<%= request.getContextPath() %>/js/main.js'></script>
<script type='text/javascript' src="<%= request.getContextPath() %>/js/verify.js"></script>
<title>消息内容</title>
</head>

<body>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td class="nav-title"><span class="Title">工作台</span>&gt;&gt; 消息内容</td>
  </tr>
</table>
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
    <td class="box-mm">
      <table width="95%" border="0" align="center" cellpadding="6" cellspacing="0">
        <%
					int sended = ParamUtil.getIntParameter(request, "sended", 0);
					if (sended == 1) {
				%>
				<tr>
          <td width="50" class="input-box">收件人</td>
          <td><%= bean.getRecverList() %></td>
        </tr>
				<%
					} else {
						String senderName = bean.getSender() == null ? "" : bean.getSender().getName();
						String colorClass = "";
						if (bean.getType() == 0) {
							String sysType = bean.getSysType()==0?"Alarm":"News";
							senderName = "系统自动("+sysType+")";
							colorClass = "redcolor";
						}
				%>
				<tr>
          <td width="50" class="input-box">发件人</td>
          <td class="<%= colorClass %>"><%= senderName %></td>
        </tr>
				<%
					}
				%>
        <tr>
          <td class="input-box">日&nbsp;&nbsp;期</td>
          <td><%= WebUtil.dateToString(bean.getCreateDate(), "yyyy-MM-dd HH:mm:ss") %></td>
        </tr>
        <tr>
          <td class="input-box">主&nbsp;&nbsp;题</td>
          <td><%= bean.getTitle() %></td>
          </tr>
        <tr>
          <td valign="top" class="input-box2">内&nbsp;&nbsp;容</td>
          <td><%= StringUtil.replace(bean.getContent(), "\n", "<br>") %></td>
        </tr>
        <tr>
          <td height="30" colspan="2" align="center"><input name="btn_close" type="button" class="button" value="关闭" onClick="javascript:self.close()"></td>
        </tr>
      </table>
        </form>
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
</body>
</html>
