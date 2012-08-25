<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
List available = (List)request.getAttribute("available");
List unavailable = (List)request.getAttribute("unavailable");
SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/interface/SysUserRoleAjaxService.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/engine.js'></script>
</head>
<style type="text/css">
#b{
  width:220px; 
  height:240px;
  position:relative;
  top:-2px;
  left:-2px;
}
</style>
<script language="javascript">
var fromUserId=<%=user.getId()%>;
function add(){
  if($("available").selectedIndex==-1){
    alert("请选择要授权的用户");
	$("available").focus();
	return;	
  }
  var opt = $("available").options($("available").selectedIndex);
  
  SysUserRoleAjaxService.addRole(fromUserId, opt.value,  function(reply){
    if(reply){
      //删除选中的记录
      $("available").remove($("available").selectedIndex);
      //增加到已授权列表
      $("unavailable").add(opt);
    }
  });
}
function remove(){
  if($("unavailable").selectedIndex==-1){
    alert("请选择要已授权的用户");
	$("unavailable").focus();
	return;
  }
  var opt = $("unavailable").options($("unavailable").selectedIndex);
  
  SysUserRoleAjaxService.removeRole(fromUserId, opt.value,  function(reply){
    if(reply){
      //删除选中的记录
      $("unavailable").remove($("unavailable").selectedIndex);
      //增加到可授权列表
      $("available").add(opt);
    }
  });  
}
</script>
<body>
<html:form action="/sys/application.do?method=saveAdd" method="post" onsubmit="return verifyAll(this);" >
  <div class="nav-title"><span class="Title">权限管理</span>&gt;&gt;用户授权</div>
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
    <td class="box-mm">
	<table width="590" border="0" align="center" cellpadding="5" cellspacing="0">
      <tr>
        <td colspan="3" align="center"><b><jsp:include page="/WEB-INF/views/module/login_info.jsp" flush="true"/></b></td>
        </tr>
      <tr>
        <td width="298" align="center">可授权用户</td>
        <td width="83">&nbsp;</td>
        <td width="280" align="center">已授权用户</td>
      </tr>
      <tr>
        <td rowspan="6" align="center" valign="top">
		<select name="available" size=10 id="b">
		<%
		Iterator iter = available.iterator();
		while(iter.hasNext()){
		  SysUser bean = (SysUser)iter.next();
		%>
		  <option value="<%=bean.getId()%>"><%=bean.getName()%>--[<%=bean.getCode()%>]</option>		  
		<%
		}
		%>
		</select>		</td>
        <td height="27" align="center" valign="bottom">&nbsp;</td>
        <td rowspan="6" align="center">
		<select name="unavailable" size=10 id="b">
        <%
		iter = unavailable.iterator();
		while(iter.hasNext()){
		  SysUser bean = (SysUser)iter.next();
		%>
		  <option value="<%=bean.getId()%>"><%=bean.getName()%>--[<%=bean.getCode()%>]</option>		  
		<%
		}
		%>
        </select>		</td>
      </tr>
      
      <tr>
        <td height="26" align="center" valign="bottom">&nbsp;</td>
      </tr>
      <tr>
        <td height="34" align="center" valign="bottom">
		<input name="btn_add" type="button" value="授权>>" class="button" onClick="add();">
		</td>
      </tr>
      <tr>
        <td height="47" align="center" valign="top">
		<input name="btn_remove" type="button" value="取消<<" class="button" onClick="remove();">
		</td>
      </tr>
      <tr>
        <td height="24" align="center" valign="top">&nbsp;</td>
      </tr>
      <tr>
        <td align="center" valign="top">&nbsp;</td>
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
