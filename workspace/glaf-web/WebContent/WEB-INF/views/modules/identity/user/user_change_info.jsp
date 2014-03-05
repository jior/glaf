<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
SysUser bean=(SysUser)request.getAttribute("bean");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改用户信息</title>
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script></head>
<script language="javascript">
function checkForm(form){
  if(verifyAll(form)){
     //if(form.password.value!=form.password2.value){
	 //  alert("密码与确认密码不匹配");
	 // }else{
	 //  return true;
	 //}
	 return true;
  }
   return false;
}
function setValue(obj){
  obj.value=obj[obj.selectedIndex].value;
}
</script>
</head>

<body>
<br><br>
<html:form action="${contextPath}/identity/user.do?method=saveModifyInfo" method="post" target="hiddenFrame" 
           onsubmit="return checkForm(this);"> 
<table width="500" border="0" align="center" cellpadding="0" cellspacing="0" class="box">

      <tr>
        <td height="30" width="20%" class="input-box">用户名</td>
        <td height="30" width="80%"><%=bean.getCode()%></td>
      </tr>
      <tr>
        <td height="30"class="input-box2" valign="top">姓　　名</td>
        <td height="30" ><%=bean.getName()%></td>
      </tr>
      <tr>
        <td height="30" class="input-box2" valign="top">手　　机*</td>
        <td height="30">
          <input name="mobile" type="text" size="30"  class="input span3 x-text" datatype="string" value="<%=bean.getMobile()%>" nullable="no" maxsize="12" chname="手机">        
		</td>
      </tr>
      <tr>
        <td height="30" class="input-box2" valign="top">邮　　件*</td>
        <td height="30">
          <input name="email" type="text" size="30"  class="input span3 x-text" datatype="email" value="<%=bean.getEmail()%>" nullable="no" maxsize="50" chname="邮件">        
		</td>
      </tr>
      <tr>
        <td height="30" class="input-box2" valign="top">办公电话*</td>
        <td height="30">
          <input name="telephone" type="text" size="30"  class="input span3 x-text" datatype="string" value="<%=bean.getTelephone()%>" nullable="no" maxsize="20" chname="办公电话">        
		</td>
      </tr>
      <tr>
	    <td >&nbsp;</td>
        <td align="left" valign="bottom" height="30">&nbsp;
             <br> <input name="btn_save2" type="submit" value="保存" class="  btn btn-primary">
		</td>
      </tr>
   
</table>
</html:form>
<script language="javascript">
attachFrame();
</script>
</body>
</html>
