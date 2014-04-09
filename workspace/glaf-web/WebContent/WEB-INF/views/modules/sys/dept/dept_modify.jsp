<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
SysDepartment bean=(SysDepartment)request.getAttribute("bean");
List  list = (List)request.getAttribute("parent");
//Set histortDeparts = bean.getHistoryDeparts();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script></head>
<script language="javascript">
function checkForm(form){
  if(verifyAll(form)){
     if(form.parent.value=='<%=bean.getId()%>'){
	   alert("当前部门不能选择为所属部门");
	 }else{
	   selectCB("historyId");
	   return true;
	 }
  }
   return false;
}
function setValue(obj){
  obj.value=obj[obj.selectedIndex].value;
}
  
 
</script>
</head>

<body>
<div class="nav-title"><span class="Title">部门管理</span>&gt;&gt;修改部门</div>
<html:form action="${contextPath}/sys/department.do?method=saveModify" method="post"  onsubmit="return checkForm(this);"> 
<input type="hidden" name="id" value="<%=bean.getId()%>">
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
	<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td class="input-box">上级部门</td>
        <td><select name="parent" onChange="javascript:setValue(this);" class="input">
          <%
			if(list!=null){
			  Iterator iter=list.iterator();   
			  while(iter.hasNext()){
				SysTree bean2=(SysTree)iter.next();
			%>
					  <option value="<%=bean2.getId()%>">
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
		  document.all.parent.value="<%=bean.getNode().getParentId()%>";
	    </script>		
		</td>
      </tr>
      <tr>
        <td class="input-box">部门名称*</td>
        <td><input name="name" type="text" class="input" id="name" value="<%=bean.getName()%>" size="37" datatype="string" nullable="no" maxsize="20" chname="部门名称"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">描　　述</td>
        <td><textarea name="desc" cols="35" rows="8" class="input" id="desc" datatype="string" nullable="yes" maxsize="1000" chname="描述"><%=bean.getDesc()%></textarea></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">代　　码*</td>
        <td><input name="code" type="text" class="input" id="code" value="<%=bean.getCode()%>" size="37"  datatype="string" nullable="no" maxsize="10" chname="代码"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">编　　码*</td>
        <td><input name="no" type="text" class="input" id="no" value="<%=bean.getNo()%>" size="37"  datatype="string" nullable="no" maxsize="10" chname="编码"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">部门区分*</td>
        <td><input name="code2" type="text" size="37" class="input" value="<%=bean.getCode2()%>" datatype="string" nullable="no" maxsize="10" chname="部门区分"></td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">部门层级</td>
        <td>
		    <select id="level" name="level">
			  <option value="0">----请选择----</option>
			  <c:forEach items="${dictories}" var="a">
				<option value="${a.ext11}">${a.name} [${a.code}]</option>
			  </c:forEach>
		   </select>
		   <script type="text/javascript">
		        document.getElementById("level").value="${bean.level}";
		   </script>
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">是否有效*</td>
        <td><input type="radio" name="status" value="0" <%=bean.getStatus()==0?"checked":""%>>
          是
          <input type="radio" name="status" value="1" <%=bean.getStatus()==1?"checked":""%>>
          否
		</td>
      </tr>

	  <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
              <input name="btn_save" type="submit" value="保存" class="button">
		</td>
      </tr>
    </table>
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
<script language="javascript">
attachFrame();
</script>
</html:form> 
</body>
</html>
