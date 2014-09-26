<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
SysRole bean=(SysRole)request.getAttribute("bean");
List  list = (List)request.getAttribute("parent");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script></head>

<body>
<html:form action="${contextPath}/sys/role.do?method=saveModify" method="post"  onsubmit="return verifyAll(this);">
<input type="hidden" name="id" value="<%=bean.getId()%>">
<div class="nav-title"><span class="Title">角色管理</span>&gt;&gt;修改角色</div>
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
        <td class="input-box">角色名称*</td>
        <td><input name="name" type="text" size="35" class="input" datatype="string" nullable="no" maxsize="20" chname="角色名称" value="<%=bean.getName()%>"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">描　　述</td>
        <td>
		<textarea name="desc" cols="35" rows="3" class="input-multi" datatype="string" nullable="yes" maxsize="100" chname="描述"><%=bean.getDesc() != null ? bean.getDesc() : ""%></textarea>        
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">代码*</td>
        <td><input name="code" type="text" size="35" value="<%=bean.getCode()%>" class="input" datatype="string" nullable="no" maxsize="20" chname="代码"></td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">是否开放分级管理</td>
        <td>
		    <select id="isUseBranch" name="isUseBranch">
			    <option value="Y">开放</option>
				<option value="N">不开放</option>
		    </select>
			<script type="text/javascript">
			     document.getElementById("isUseBranch").value="<%=bean.getIsUseBranch() != null ? bean.getIsUseBranch() : ""%>";
			</script>
        </td>
      </tr>
	  <!-- <tr>
        <td class="input-box2" valign="top">类型</td>
        <td>
		    <select id="type" name="type">
			    <option value="SYS">系统角色</option>
				<option value="APP">应用角色</option>
		    </select>
			<script type="text/javascript">
			     document.getElementById("type").value="<%=bean.getType() != null ? bean.getType() : ""%>";
			</script>
		</td>
      </tr> -->

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
