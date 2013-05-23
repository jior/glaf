<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
	String context = request.getContextPath();
	SysTree bean=(SysTree)request.getAttribute("bean");
	List list = (List)request.getAttribute("parent");
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=context%>/css/core.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script language="javascript">
 
    var contextPath="<%=request.getContextPath()%>";

	function clearSelected(elementId, elementName){
		if(document.getElementById(elementId) != null){
			document.getElementById(elementId).value="" ;
		}
		if(document.getElementById(elementName) != null){
			document.getElementById(elementName).value="" ;
		}
	 }

    function saveForm(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/sys/tree.do?method=savePerms',
				   dataType: 'json',
				   data: params,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						 alert('操作成功完成！');
					   }
				   }
			 });
    }

	function switchPerms(){
        var entryKey = jQuery("#entryKey").val();
		location.href="<%=request.getContextPath()%>/sys/tree.do?method=showPerms&nodeId=<%=bean.getId()%>&entryKey="+entryKey;
	}
</script>
</head>

<body style="margin:10px;">
 
<div class="easyui-panel" title="栏目权限" style="width:668px;padding:5px"> 
<html:form id="iForm" name="iForm" action="${contextPath}/sys/tree.do?method=savePerms" method="post"  > 
<input type="hidden" name="nodeId" value="<%=bean.getId()%>">
<input type="hidden" name="id" value="${entityEntry.id}">
 <table width="98%" align="center" border="0" cellspacing="0" cellpadding="5">
      
      <tr>
        <td width="20%" >权限类别</td>
        <td colspan="2">
           <select id="entryKey" name="entryKey" onchange="javascript:switchPerms();">
			<option value="r" selected>访问（可读）</option>
			<option value="rw">操作（读写）</option>
			<option value="admin">管理权限</option>
           </select>
		   <script type="text/javascript">
		       jQuery("#entryKey").val("${entryKey}");
		   </script>
		</td>
      </tr>

	  <tr>
        <td width="20%" >是否扩展到子孙节点</td>
        <td colspan="2">
           <select id="isPropagationAllowed" name="isPropagationAllowed">
			<option value="0" selected>否</option>
			<option value="1">是</option>
           </select>
		</td>
		<td></td>
      </tr>
	 <tr>
		<td height="27" width="20%" valign="middle" noWrap> 授权范围（角色）</td>
		<td height="27" valign="middle" noWrap> 
		<input type="hidden" id="x_roles" name="x_roles" value="${x_roles}">
        <textarea cols="40" id="x_roles_name" name="x_roles_name" rows="8"  wrap="yes" readonly  
		    style="height:100px;width:350px;color: #000066; background: #ffffff;">${x_roles_name}</textarea>
		</td>
		<td height="27" width="20%" valign="bottom" noWrap>
		    &nbsp;&nbsp;<input type="button" name="button" value="添加" class="button" 
			           onclick="javascript:selectRole('iForm', 'x_roles','x_roles_name');"> 
			<br><br>
			&nbsp;&nbsp;<input type="button" name="button" value="清空" class="button" 
			           onclick="javascript:clearSelected('x_roles','x_roles_name');"> 
		</td>
	</tr>
    <tr >
		<td height="27" width="20%" valign="middle" noWrap> 授权范围（部门）</td>
		<td height="27" valign="middle" noWrap> 
		<input type="hidden" id="x_departments" name="x_departments" value="${x_departments}">
        <textarea cols="40" id="x_departments_name" name="x_departments_name" rows="8"  wrap="yes" readonly  
		    style="height:100px;width:350px;color: #000066; background: #ffffff;">${x_departments_name}</textarea>
		</td>
		<td height="27" width="20%"   valign="bottom" noWrap>
		    &nbsp;&nbsp;<input type="button" name="button" value="添加" class="button" 
			           onclick="javascript:selectDept('iForm', 'x_departments','x_departments_name');"> 
			<br><br>
			&nbsp;&nbsp;<input type="button" name="button" value="清空" class="button"
			           onclick="javascript:clearSelected('x_departments','x_departments_name');"> 
		</td>
	</tr>
    <tr >
		<td height="27" width="20%" valign="middle" noWrap> 授权范围（用户）</td>
		<td height="27" valign="middle" noWrap> 
		<input type="hidden" id="x_users" name="x_users" value="${x_users}">
        <textarea cols="40" id="x_users_name" name="x_users_name" rows="8"  wrap="yes" readonly  
		    style="height:100px;width:350px;color: #000066; background: #ffffff;">${x_users_name}</textarea>
		</td>
		<td height="27" width="20%"  valign="bottom" noWrap>
		    &nbsp;&nbsp;<input type="button" name="button" value="添加" class="button" 
			           onclick="javascript:selectUser('iForm', 'x_users','x_users_name');"> 
			<br><br>
			&nbsp;&nbsp;<input type="button" name="button" value="清空" class="button" 
			           onclick="javascript:clearSelected('x_users','x_users_name');"> 
		</td>
	</tr>
    </table> 
	<div align="center">
      <input type="button" name="button" value=" 保 存 " class="button" onclick="javascript:saveForm();"> 
    </div>
</html:form>
</div>
</body>
</html>
