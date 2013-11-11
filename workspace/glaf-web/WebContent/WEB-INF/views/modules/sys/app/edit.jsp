<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
    String context = request.getContextPath();
	String method = "saveAdd";
    SysApplication bean=(SysApplication)request.getAttribute("bean");
    List  list = (List)request.getAttribute("parent");
	int parentId=ParamUtil.getIntParameter(request, "parent", 0);
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	request.setAttribute("contextPath", request.getContextPath());
	if(bean != null){
		method = "saveModify";
	}
	request.setAttribute("method", method);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src='<%=context%>/scripts/main.js'></script>
<script type="text/javascript" src='<%=context%>/scripts/verify.js'></script> 
<script language="javascript">
function checkForm(form){
  if(verifyAll(form)){
     if(form.parent.value=='${bean.id}'){
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

function saveData(){
	if(checkForm(document.getElementById("iForm"))){
		var params = jQuery("#iForm").formSerialize();
		//alert(params);
		jQuery.ajax({
				   type: "POST",
				   url: '<%=context%>/sys/application.do?method=save',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   if (window.opener) {
						  //window.opener.location.reload();
						  window.opener.reloadGrid();
					   } else if (window.parent) {
						  //window.parent.location.reload();
						  window.parent.reloadGrid();
					   }
					   window.close();
				   }
			 });
	}
}

</script>
</head>

<body style="margin:10px;">
<form id="iForm" name="iForm" method="post"> 
<div class="easyui-panel" title="修改模块" style="width:550px;padding:10px">
<input type="hidden" name="id" value="${bean.id}">
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td class="input-box">上级模块</td>
        <td>
		<select name="parent" onChange="javascript:setValue(this);" class="input">
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
		<c:choose>
		  <c:when test="${!empty bean}">
		      document.all.parent.value="${bean.node.parentId}";	
		  </c:when>
		  <c:otherwise>
		      document.all.parent.value="<%=parentId%>";	
		  </c:otherwise>
		</c:choose>
	    </script>
		</td>
      </tr>
      <tr>
        <td class="input-box">名　　称*</td>
        <td><input name="name" type="text" class="input easyui-validatebox" value="${bean.name}" size="35" datatype="string" nullable="no" maxsize="20" chname="名称" data-options="required:true"></td>
      </tr>
	  <tr>
        <td class="input-box">权限编码</td>
        <td><input name="code" type="text" value="${bean.code}" size="35" class="input easyui-validatebox" datatype="string" nullable="yes" maxsize="50" chname="代码"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">描　　述</td>
        <td><textarea name="desc" cols="38" rows="6" class="input-multi" datatype="string" nullable="yes" maxsize="100" chname="描述">${bean.desc}</textarea></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">链　　接</td>
        <td> 
		<textarea name="url" cols="38" rows="5" class="input-multi" datatype="string" nullable="yes" maxsize="100" chname="链接">${bean.url}</textarea>
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">是否弹出窗</td>
        <td>
          <input type="radio" name="showMenu" value="2" <c:if test="${bean.showMenu == 2}">checked</c:if>/>是
		  <input type="radio" name="showMenu" value="1" <c:if test="${bean.showMenu != 2}">checked</c:if>/>否
		  </td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">是否有效</td>
        <td>
		  <input type="radio" name="locked" value="0" <c:if test="${bean.locked == 0}">checked</c:if>/>是
          <input type="radio" name="locked" value="1" <c:if test="${bean.locked == 1}">checked</c:if>/>否
        </td>
      </tr>
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
            <input name="btn_save" type="button" value="保存" class="button" onclick="javascript:saveData();">
		</td>
      </tr>
    </table>
</div>
</form>
</body>
</html>
