<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
SysUser bean=(SysUser)request.getAttribute("bean");
List  list = (List)request.getAttribute("parent");
int nodeId=ParamUtil.getIntParameter(request, "nodeId", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script></head>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script language="javascript">

var contextPath = "<%=request.getContextPath()%>";

function checkForm(form){
  if(verifyAll(form)){
     //if(form.password.value!=form.password2.value){
	 //  alert("密码与确认密码不匹配");
	 //}else{
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
<div class="nav-title"><span class="Title">用户管理</span>&gt;&gt;修改用户</div>
<html:form id="iForm" name="iForm" action="${contextPath}/branch/user.do?method=saveModify" 
      method="post"  onsubmit="return checkForm(this);"> 
<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name="nodeId" value="<%=nodeId%>">
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
        <td width="80%"><%=bean.getCode()%></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">部　　门</td>
        <td>
		<select name="parent" onchange="javascript:setValue(this);">
          <%
			if(list!=null){
			  Iterator iter=list.iterator();   
			  while(iter.hasNext()){
				SysTree bean2=(SysTree)iter.next();	
				SysDepartment dept = bean2.getDepartment();
			%>
			<option value="<%=dept!=null?dept.getId():""%>">
			<%
			for(int i=1;i<bean2.getDeep();i++){
			  out.print("&nbsp;&nbsp;");
			}
			out.print(bean2.getName());
			%>
			</option>
			<%    
			  }
			}
			%>
        </select>
		<script language="javascript">								
          document.all.parent.value="<%=bean.getDepartment().getId()%>";	
	    </script>		
	   </td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">姓　　名*</td>
        <td><input name="name" type="text" size="28" class="input" value="<%=bean.getName()%>" datatype="string" nullable="no" maxsize="20" chname="姓名"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">性　　别</td>
        <td>
          <input type="radio" name="gender" value="0" <%=bean.getGender()==0?"checked":""%>>男
          <input type="radio" name="gender" value="1" <%=bean.getGender()==1?"checked":""%>>女
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">手　　机</td>
        <td>
          <input name="mobile" type="text" size="30" class="input" datatype="string" 
		         value="<%=bean.getMobile() != null ? bean.getMobile() :""%>" 
			     nullable="yes" maxsize="12" chname="手机">        
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">邮　　件</td>
        <td>
          <input name="email" type="text" size="30" class="input" datatype="email" 
		         value="<%=bean.getEmail() != null ? bean.getEmail() : ""%>" 
				 nullable="yes" maxsize="50" chname="邮件">       
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">办公电话</td>
        <td>
          <input name="telephone" type="text" size="30" class="input" datatype="string" 
		         value="<%=bean.getTelephone() != null ? bean.getTelephone() : ""%>" 
				 nullable="yes" maxsize="20" chname="办公电话">        
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
          <input id="superiorIds" name="superiorIds" type="hidden" 
		         value="<%=bean.getSuperiorIds() != null ?bean.getSuperiorIds() :""%>"> 
		  <textarea cols="40" id="x_users_name" name="x_users_name" rows="8"  wrap="yes" readonly  
             onclick="javascript:selectUser('iForm', 'superiorIds','x_users_name');"
		     style="height:25px;width:250px;color: #000066; background: #ffffff;">${x_users_name}</textarea>       
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">是否有效</td>
        <td>
          <input type="radio" name="blocked" value="0" <%=bean.getBlocked()==0?"checked":""%>>是
          <input type="radio" name="blocked" value="1" <%=bean.getBlocked()==1?"checked":""%>>否
		</td>
      </tr>
 
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
              <input name="btn_save2" type="submit" value="保存" class="button">
	    </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	 <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="box">
        <td class="box-lb">&nbsp;</td>
        <td class="box-mb">&nbsp;</td>
        <td class="box-rb">&nbsp;</td>
      </tr>
    </table>
	</td>
  </tr>
</table>
</html:form>
</body>
</html>
