<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    com.glaf.core.util.RequestUtils.setRequestParameterToAttribute(request);
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tree</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/scripts/extjs/resources/css/ext-all.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/extjs/ext-all.js"></script>
<script language="javascript">
Ext.BLANK_IMAGE_URL="<%=request.getContextPath()%>/images/s.gif";
Ext.onReady(function(){
    // shorthand
    var Tree = Ext.tree;

	var x_dataUrl = '<%=request.getContextPath()%>/mx/jbpm/tree/json?location=${location}';
    
    var tree = new Tree.TreePanel({
		el:'tree-div',
        useArrows: false,
        autoScroll:true,
        animate:true,
        enableDD:true,
		border: false,
        containerScroll: true, 
        loader: new Tree.TreeLoader({
            dataUrl: x_dataUrl
        })
    });


	var root = new Tree.AsyncTreeNode({
        text: '流程树',
        draggable:false,
        id:'source'
    });
    tree.setRootNode(root);
 
    tree.render();
    root.expand();
 
});
</script>
</head>

<body>
<div id="tree-div"
	style="overflow: hidden; width: 100%; height: 100%; border: 1px solid #c3daf9;"></div>
</body>

</html>