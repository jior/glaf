<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
    String context = request.getContextPath();
    SysApplication bean=(SysApplication)request.getAttribute("bean");
    List  list = (List)request.getAttribute("parent");
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	request.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src='<%=context%>/scripts/main.js'></script>
<script type="text/javascript" src='<%=context%>/scripts/verify.js'></script> 
<script language="javascript">
function checkForm(form){
  if(verifyAll(form)){
     if(form.parent.value=='<%=bean.getId()%>'){
	   alert("当前模块不能选择为所属模块");
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

<body style="margin:10px;">
<html:form action="${contextPath}/sys/application.do?method=saveModify" method="post"  onsubmit="return checkForm(this);"> 
<div class="easyui-panel" title="修改模块" style="width:550px;padding:10px">
<input type="hidden" name="id" value="<%=bean.getId()%>">
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td class="input-box">上级模块</td>
        <td><select name="parent" onChange="javascript:setValue(this);" class="input">
          <%
			if(list!=null){
			  Iterator iter=list.iterator();   
			  while(iter.hasNext()){
				SysTree bean2=(SysTree)iter.next();
			%>
					  <option value="<%=bean2.getId()%>">
					  <%
			for(int i=0;i<bean2.getDeep();i++){
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
        <td class="input-box">名　　称*</td>
        <td><input name="name" type="text" class="input easyui-validatebox" value="<%=bean.getName()%>" size="35" datatype="string" nullable="no" maxsize="20" chname="名称" data-options="required:true"></td>
      </tr>
	  <tr>
        <td class="input-box">权限编码</td>
        <td><input name="code" type="text" value="<%=bean.getCode()!=null?bean.getCode():""%>" size="35" class="input easyui-validatebox" datatype="string" nullable="yes" maxsize="50" chname="代码"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">描　　述</td>
        <td><textarea name="desc" cols="38" rows="6" class="input-multi" datatype="string" nullable="yes" maxsize="100" chname="描述"><%=bean.getDesc() != null ? bean.getDesc() : ""%></textarea></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">链　　接</td>
        <td> 
		<textarea name="url" cols="38" rows="5" class="input-multi" datatype="string" nullable="yes" maxsize="100" chname="链接"><%=bean.getUrl() != null ? bean.getUrl() :""%></textarea>
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">是否弹出窗</td>
        <td>
          <input type="radio" name="showMenu" value="2" <%=bean.getShowMenu()==2?"checked":""%>>是
		  <input type="radio" name="showMenu" value="1" <%=bean.getShowMenu()!=2?"checked":""%>>否
		  </td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">是否下放给分级管理员</td>
        <td>
		  <input type="radio" name="openFlag" value="1" <%=("1".equals(bean.getOpenFlag()))?"checked":""%>>是
		  <input type="radio" name="openFlag" value="0" <%=("0".equals(bean.getOpenFlag()))?"checked":""%>>否
        </td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">是否有效</td>
        <td>
		  <input type="radio" name="locked" value="0" <%=bean.getLocked()==0?"checked":""%>>是
          <input type="radio" name="locked" value="1" <%=bean.getLocked()==1?"checked":""%>>否
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
