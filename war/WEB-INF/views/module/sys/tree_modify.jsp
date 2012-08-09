<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
SysTree bean=(SysTree)request.getAttribute("bean");
List  list = (List)request.getAttribute("parent");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/main.js'></script>
<script language="javascript" src='<%=context%>/js/verify.js'></script></head>
<script language="JavaScript">
function checkForm(form){
  if(verifyAll(form)){
     if(form.parent.value=='<%=bean.getId()%>'){
	   alert("当前节点不能选择为所属节点");
	 }else{
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
<div class="nav-title"><span class="Title">目录管理</span>&gt;&gt;修改节点</div>
<html:form action="/sys/tree.do?method=saveModify" method="post"  onsubmit="return checkForm(this);"> 
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
    <td class="box-mm"><table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td class="input-box">上级节点</td>
        <td><select name="parent" onChange="javascript:setValue(this);">
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
		<script language="JavaScript">
		document.all.parent.value="<%=bean.getParent()%>";	
	    </script>
		</td>
      </tr>
      <tr>
        <td class="input-box">名　　称*</td>
        <td><input name="name" type="text" size="37" class="input" value="<%=bean.getName()%>" datatype="string" nullable="no" maxsize="30" chname="名称"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">描　　述</td>
        <td><textarea name="desc" cols="35" rows="8" class="input" datatype="string" nullable="yes" maxsize="100" chname="描述"><%=bean.getDesc()%></textarea></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">编　　码*</td>
        <td><input name="code" type="text" size="10" class="input" value="<%=bean.getCode()%>"  datatype="string" nullable="no" maxsize="20" chname="编码"></td>
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
