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
<title>部门</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript">

    var setting = {
			async: {
				enable: true,
				url: "<%=request.getContextPath()%>/rs/base/identity/choose/deptJson?viewType=1&selecteds=${selecteds}"
			},
			check: {
				enable: true,
				chkStyle: "radio",radioType: "all",
				chkboxType: { "Y": "s", "N": "s" } 
			}
		};


    jQuery(document).ready(function(){
		  jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	});



	function chooseMyFormData(){
		var zTree = $.fn.zTree.getZTreeObj("myTree");
        var selectedNodes  = zTree.getCheckedNodes(true);

        var sx = '';
		var sx_name = '';
		var codes='';
		var name='';
        for(var i=0; i<selectedNodes.length; i++){  
            if (sx != ''){ 
				sx += ','; 
			}
			if (sx_name != ''){ 
				sx_name += ','; 
			}
			if (codes != ''){ 
				codes += ','; 
			}
			if(selectedNodes[i].deptId){
			  sx += selectedNodes[i].deptId; 
              sx_name += selectedNodes[i].name; 
			  codes += selectedNodes[i].code;
			}
        } 

		var parent_window = getOpener();
	    var x_elementId = parent_window.document.getElementById("${elementId}");
        var x_element_name = parent_window.document.getElementById("${elementName}");
		var x_element_code = parent_window.document.getElementById("${elementCode}");
		x_elementId.value=sx;
		if(null!=x_element_name && x_element_name!=undefined){
			x_element_name.value=sx_name;
		}
		if(null!=x_element_code && x_element_code!=undefined){
			x_element_code.value=codes;
		}
		window.close(); 
	}

</script>
</head>

<body>

<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"> 
	<span class="x_content_title">部门选择</span>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-ok'" 
	   onclick="javascript:chooseMyFormData();" >确定</a> 
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
    <input type="hidden" id="groupId" name="groupId" value="${groupId}">
	<input type="hidden" id="userIds" name="userIds">
	<ul id="myTree" class="ztree"></ul> 
  </form>
</div>
</div>
<script type="text/javascript">
  	
</script>

</body>
</html>