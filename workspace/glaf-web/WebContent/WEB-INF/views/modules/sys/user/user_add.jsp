<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
pageContext.setAttribute("contextPath", context);
List  list = (List)request.getAttribute("parent");
int parent=ParamUtil.getIntParameter(request, "parent", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script></head>
<script language="javascript">
function checkForm(form){
  if(verifyAll(form)){
     if(form.password.value!=form.password2.value){
	   alert("密码与确认密码不匹配!");
	 }else{
	   return true;
	 }
  }
   return false;
}
</script>
</head>

<body>
<div class="nav-title"><span class="Title">用户管理</span>&gt;&gt;增加用户</div>
<html:form action="${contextPath}/sys/user.do?method=saveAdd" method="post" onsubmit="return checkForm(this);" > 
<input type="hidden" name="parent" value="<%=parent%>">
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
        <td width="20%" class="input-box">员工编码*</td>
        <td width="80%">
		<input name="code" type="text" size="30" class="input" datatype="string" nullable="no" maxsize="10" chname="员工编码">
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">密　　码*</td>
        <td>
		<input name="password" type="password" size="30" class="input" datatype="string" nullable="no" minsize="6" maxsize="20" chname="密码">
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">确认密码*</td>
        <td>
		<input name="password2" type="password" size="30" class="input"  datatype="string" nullable="no" minsize="6" maxsize="20" chname="确认密码">
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">姓　　名*</td>
        <td>
		<input name="name" type="text" size="30" class="input" datatype="string" nullable="no" maxsize="20" chname="姓名">
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">性　　别</td>
        <td>
          <input type="radio" name="gender" value="0">男
          <input type="radio" name="gender" value="1" checked>女
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">手　　机</td>
        <td>
          <input name="mobile" type="text" size="30" class="input" datatype="string" nullable="yes" maxsize="12" chname="手机"> </td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">邮　　件</td>
        <td>
          <input name="email" type="text" size="30" class="input" datatype="email" nullable="yes" maxsize="50" chname="邮件">
		  </td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">办公电话</td>
        <td>
          <input name="telephone" type="text" size="30" class="input" datatype="string" nullable="yes" maxsize="20" chname="办公电话">        
		  </td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">职位</td>
        <td>
		    <select id="headship" name="headship">
			  <option value="0">----请选择----</option>
			  <c:forEach items="${dictories}" var="a">
				<option value="${a.code}">${a.name} [${a.code}]</option>
			  </c:forEach>
		   </select>
		   <script type="text/javascript">
		        document.getElementById("headship").value="${bean.headship}";
		   </script>
		</td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">直接上级</td>
        <td>
          <input name="superiorIds" type="text" size="30" class="input" datatype="string" nullable="yes" maxsize="20" chname="直接上级">
          <br>（直接上级领导的账号，如果有多个，用半角的逗号“,”分隔）       
		  </td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">是否有效</td>
        <td>
          <input type="radio" name="blocked" value="0" checked>是
          <input type="radio" name="blocked" value="1">否</td>
      </tr>
       
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
         <input name="btn_save" type="submit" value="保存" class="button">
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
</body>
</html>
