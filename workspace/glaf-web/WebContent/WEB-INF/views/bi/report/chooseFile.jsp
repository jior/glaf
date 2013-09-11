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
<title>文件列表</title>
<link href="<%=request.getContextPath()%>/scripts/artDialog/skins/default.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/plugins/iframeTools.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript">

    var setting = {
		async: {
			enable: true,
			url: getUrl,
			dataFilter: filter
		},
		callback: {
			onClick: zTreeOnClick
		}
	};
  

  	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			childNodes[i].icon="<%=request.getContextPath()%>/images/folder.png";
		}
		return childNodes;
	}


	function getUrl(treeId, treeNode) {
		if(treeNode != null){
		    var param = "path="+treeNode.path;
		    return "<%=request.getContextPath()%>/rs/file/dir?"+param;
		}
		return "<%=request.getContextPath()%>/rs/file/dir?path=${path}";
	}


    function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		jQuery("#path").val(treeNode.path);
		loadMxData('<%=request.getContextPath()%>/rs/file/json?path='+treeNode.path);
	}

	function loadMxData(url){
		  jQuery.get(url+'&randnum='+Math.floor(Math.random()*1000000),{qq:'xx'},function(data){
		      //var text = JSON.stringify(data); 
              //alert(text);
			  jQuery('#easyui_data_grid').datagrid('loadData', data);
		  },'json');
	}

    jQuery(document).ready(function(){
			jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	});

    jQuery(function(){
		jQuery('#easyui_data_grid').datagrid({
				width:1000,
				height:480,
				fit:true,
				fitColumns:true,
				nowrap: false,
				striped: true,
				collapsible:true,
				url:'<%=request.getContextPath()%>/rs/file/json?path=${path}',
				remoteSort: false,
				singleSelect:true,
				idField:'id',
				columns:[[
	                {title:'序号',field:'startIndex',width:80,sortable:false},
					{title:'名称',field:'name',width:220,sortable:false},
					{title:'修改日期',field:'date',width:150,sortable:false},
					{title:'大小',field:'size',width:90,sortable:false}
				]],
				rownumbers:false
			});

			var p = jQuery('#easyui_data_grid').datagrid('getPager');
			jQuery(p).pagination({
				onBeforeRefresh:function(){
					//alert('before refresh');
				}
		    });
	});

 
	function resize(){
		jQuery('#easyui_data_grid').datagrid('resize', {
			width:800,
			height:400
		});
	}

	function reloadGrid(){
	    jQuery('#easyui_data_grid').datagrid('reload');
	}

	function selectedFile(){
	    var selected = jQuery('#easyui_data_grid').datagrid('getSelected');
	    if (selected){
		    //alert(selected.code+":"+selected.name+":"+selected.addr+":"+selected.col4);
			var parent_window = getOpener();
			var x_elementId = parent_window.document.getElementById("${elementId}");
            var x_element_name = parent_window.document.getElementById("${elementName}");
			if(confirm("您确定选择'"+selected.path+"'作为报表模板吗？")){
			    x_elementId.value=selected.path;
			    x_element_name.value=selected.path;
			    window.close();
			}
	    }
	}
 
</script>
</head>
<body style="margin:1px;">  

<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
    <div data-options="region:'west',split:true" style="width:180px;">
	  <div class="easyui-layout" data-options="fit:true">  
           
			 <div data-options="region:'center',border:false">
			    <ul id="myTree" class="ztree"></ul>  
			 </div> 
			 
        </div>  
	</div> 
   <div data-options="region:'center'">  
     <div class="easyui-layout" data-options="fit:true"> 
	   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
	   <form id="iForm" name="iForm" method="post">
	    <input type="hidden" id="path" name="path" value="" >
		<div class="toolbar-backgroud"  > 
		<img src="<%=request.getContextPath()%>/images/window.png">
		&nbsp;<span class="x_content_title">文件列表</span>
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-ok'"
		   onclick="javascript:selectedFile();">选择</a> 
	   </div> 
	   </form>
	  </div> 
	  <div data-options="region:'center',border:true">
		 <table id="easyui_data_grid"></table>
	  </div>  
    </div>
  </div>
</div>

</body>
</html>
