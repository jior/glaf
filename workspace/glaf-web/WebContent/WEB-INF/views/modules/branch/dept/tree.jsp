<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	pageContext.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门用户权限列表</title>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/artDialog/skins/default.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/icons.css">
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/artDialog/plugins/iframeTools.js"></script>
<script type="text/javascript">
 
	var setting = {
		async: {
				enable: true,
				url:"<%=request.getContextPath()%>/branch/department.do?method=treeJson",
				dataFilter: filter
			},
			callback: {
				beforeClick: zTreeBeforeClick,
				onClick: zTreeOnClick
			}
	};

	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			childNodes[i].icon="<%=request.getContextPath()%>/images/basic.gif";
		}
		return childNodes;
	}

	function zTreeOnExpand(treeId, treeNode){
        treeNode.icon="${contextPath}/scripts/ztree/css/zTreeStyle/img/diy/2.png";
	}

	function updateNode(treeId, treeNode){
		var zTree = jQuery.fn.zTree.getZTreeObj(treeId);
		zTree.setting.view.fontCss["color"] = "#0000ff";
		zTree.updateNode(treeNode);
	}

	function zTreeBeforeClick(treeId, treeNode, clickFlag) {
           
	}

	function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		jQuery("#nodeId").val(treeNode.id);
		window.parent.mainFrame.location.href ="<%=request.getContextPath()%>/branch/department.do?method=permission&parentId="+treeNode.id;
	}


	$(document).ready(function(){
		$.fn.zTree.init($("#myTree"), setting);
	});

	
</script>
</head>
<body leftmargin="0" topmargin="0">  
<input type="hidden" id="nodeId" name="nodeId" value="" >
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
    <div data-options="region:'west',split:true" style="width:220px;">
	  <div class="easyui-layout" data-options="fit:true">        
			 <div data-options="region:'center',border:false">
			    <ul id="myTree" class="ztree"></ul>  
			 </div> 
        </div>  
	</div> 	
</div>  
</body>  
</html>