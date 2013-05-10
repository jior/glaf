<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
    String context = request.getContextPath();
    List  list = (List)request.getAttribute("parent");
    int parent=ParamUtil.getIntParameter(request, "parent", 0);
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src='<%=context%>/scripts/main.js'></script>
<script type="text/javascript" src='<%=context%>/scripts/verify.js'></script> 
</head>
<body style="margin:10px;">
<html:form action="${contextPath}/sys/application.do?method=saveAdd" method="post" onsubmit="return verifyAll(this);" > 
<div class="easyui-panel" title="增加模块" style="width:550px;padding:10px">
<input type="hidden" name="parent" value="<%=parent%>">
 <table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td class="input-box">名　　称*</td>
        <td><input name="name" type="text" size="35" class="input easyui-validatebox" datatype="string" nullable="no" maxsize="20" chname="名称" data-options="required:true"></td>
      </tr>
	  <tr>
        <td class="input-box">权限编码</td>
        <td><input name="code" type="text" size="35" class="input easyui-validatebox" datatype="string" nullable="yes" maxsize="20" chname="代码" ></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">描　　述</td>
        <td><textarea name="desc" cols="38" rows="6" class="input-multi " datatype="string" nullable="yes" maxsize="100" chname="描述"></textarea></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">链　　接</td>
        <td>
		<textarea name="url" cols="38" rows="5" class="input-multi " datatype="string" nullable="yes" maxsize="100" chname="链接"></textarea>
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">是否弹出窗</td>
        <td><span class="fontlist">
          <input type="radio" name="showMenu" value="2">是
          <input type="radio" name="showMenu" value="1" checked>否</span>
		</td>
      </tr>
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
              <input name="btn_save" type="submit" value="保存" class="button"></td>
      </tr>
  </table> 
</div>
</html:form>
</body>
</html>
