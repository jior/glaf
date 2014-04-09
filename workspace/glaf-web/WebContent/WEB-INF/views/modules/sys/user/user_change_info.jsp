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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script></head>
<script language="JavaScript">
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
<html:form action="${contextPath}/sys/user.do?method=saveModifyInfo" method="post" target="hiddenFrame" onsubmit="return checkForm(this);"> 
<table width="500" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
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
        <td width="20%" class="input-box"  height="30">员工编码</td>
        <td width="80%"  height="30"><%=bean.getCode()%></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top"  height="30">姓　　名</td>
        <td  height="30"><%=bean.getName()%></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top"  height="30">手　　机*</td>
        <td  height="30">
          <input name="mobile" type="text" size="30" class="input" datatype="string" 
		  value="<%=bean.getMobile() != null ? bean.getMobile() : ""%>" 
		  nullable="no" maxsize="12" chname="手机">        
		</td>
      </tr>
      <tr>
        <td  height="30" class="input-box2" valign="top">邮　　件*</td>
        <td  height="30"> 
          <input name="email" type="text" size="30" class="input" datatype="email" 
		  value="<%=bean.getEmail() != null ? bean.getEmail():""%>" 
		  nullable="no" maxsize="50" chname="邮件">        
		</td>
      </tr>
      <tr>
        <td  height="30" class="input-box2" valign="top">办公电话*</td>
        <td  height="30">
          <input name="telephone" type="text" size="30" class="input" datatype="string" 
		  value="<%=bean.getTelephone() != null ? bean.getTelephone() :""%>" nullable="no" maxsize="20" chname="办公电话">       
		</td>
      </tr>
      <tr>
        <td  height="30">&nbsp;</td>
		<td  align="left" valign="bottom" height="30">&nbsp;
           <br><input name="btn_save2" type="submit" value="修改密码" class="btn btn-primary">
		</td> 
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
<script language="javascript">
attachFrame();
</script>
</body>
</html>
