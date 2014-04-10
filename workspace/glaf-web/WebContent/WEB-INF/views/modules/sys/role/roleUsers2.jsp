<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色用户</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

	function saveMyFormData(){
		var nodes = $('#tt').tree('getChecked');  
        var sx = '';  
		var code='';
        for(var i=0; i<nodes.length; i++){  
            if (sx != '') sx += ',';  
			code = nodes[i].text;
			code = code.substring(0, code.indexOf(" "));
            sx += code;  
        }  
        $("#userIds").val(sx);
		//alert($("#userIds").val());
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/sys/role.do?method=saveRoleUsers',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data.message != null){
						   alert(data.message);
					   } else {
						 alert('操作成功完成！');
					   }
				   }
			 });
	}

	function checkLeafOnly(){
		$('#tt').tree({onlyLeafCheck:$(this).is(':checked')});
	}


</script>
</head>

<body>

<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:30px"> 
    <div class="toolbar-backgroud"> 
	<span class="x_content_title">查看角色【${sysRole.name}】的用户</span>
	<!-- <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" 
	   onclick="javascript:saveMyFormData();" >保存</a> --> 
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
    <input type="hidden" id="groupId" name="groupId" value="${groupId}">
	<input type="hidden" id="userIds" name="userIds">
    <ul id="tt" class="easyui-tree" data-options="url:'<%=request.getContextPath()%>/sys/role.do?method=roleUsersJson&roleCode=${sysRole.code}',animate:true,checkbox:true,onlyLeafCheck:true"></ul>  
  </form>
</div>
</div>
<script type="text/javascript">
  	
</script>

</body>
</html>