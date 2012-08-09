<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
List  list = (List)request.getAttribute("parent");
int parent=ParamUtil.getIntParameter(request, "parent", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script></head>

<body>
<html:form action="/sys/application.do?method=saveAdd" method="post" onsubmit="return verifyAll(this);" > 
<input type="hidden" name="parent" value="<%=parent%>">
<div class="nav-title"><span class="Title">模块管理</span>&gt;&gt;增加模块</div>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
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
    <td class="box-mm"><table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td class="input-box">名　　称*</td>
        <td><input name="name" type="text" size="37" class="input" datatype="string" nullable="no" maxsize="20" chname="名称"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">描　　述</td>
        <td><textarea name="desc" cols="35" rows="8" class="input-multi" datatype="string" nullable="yes" maxsize="100" chname="描述"></textarea></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">链　　接</td>
        <td><input name="url" type="text" size="37" class="input"  datatype="string" nullable="yes" maxsize="200" chname="链接"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">是否弹出窗</td>
        <td><span class="fontlist">
          <input type="radio" name="showMenu" value="2">
是
<input type="radio" name="showMenu" value="1" checked>
否</span></td>
      </tr>
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
              <input name="btn_save" type="submit" value="保存" class="button"></td>
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
