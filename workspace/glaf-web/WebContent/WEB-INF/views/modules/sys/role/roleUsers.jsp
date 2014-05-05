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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>角色用户</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript">

	 var setting = {
			async: {
				enable: true,
				url: "<%=request.getContextPath()%>/rs/sys/role/roleUsersJson?roleCode=${sysRole.code}",
				dataFilter: filter
			},
			check: {
				enable: true
			},
			callback: {
				onClick: zTreeOnClick
			}
		};
  
  	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
            //if(childNodes[i].iconCls=='icon-user'){
			   // childNodes[i].icon="<%=request.getContextPath()%>/images/user.gif";
		    //}
		}
		return childNodes;
	}

	function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		//jQuery("#nodeId").val(treeNode.id);
		//loadData('<%=request.getContextPath()%>/sys/application.do?method=json&parentId='+treeNode.id);
	}


    jQuery(document).ready(function(){
			jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	});

</script>
</head>

<body style="margin:0;"> 
<div class="toolbar-backgroud"> 
<span class="x_content_title">查看角色【${sysRole.name}】的用户</span>
</div> 
<div>
	<ul id="myTree" class="ztree"></ul> 
</div>
</body>
</html>