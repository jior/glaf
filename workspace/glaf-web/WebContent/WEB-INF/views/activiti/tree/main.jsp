<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String theme = com.glaf.core.util.RequestUtils.getTheme(request);
request.setAttribute("theme", theme);
request.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Layout</title>
<%@ include file="/WEB-INF/views/tm/header.jsp"%>
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<link rel="stylesheet" type="text/css" href="${contextPath}/css/icons.css">
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
  
     $(function(){
        $('#myGridData').datagrid({
				width: 700,
				height: 'auto',
				nowrap:false,
				singleSelect:true,
				striped:true,
			    animate:true,
			    fitColumns:true,
			    fit:true,
				pagination:true,
				pageSize:15,
                pageList:[10,15,20,30,40,50,100],
				onDblClickRow: onMyDbClickRow,
				onBeforeLoad: onMyBeforeLoad,
				columns:[[
					{field:'sortNo',title:'序号',width:90,align:'left'},
					{field:'processInstanceId',title:'流程实例编号',width:120,align:'left'},
					{field:'businessKey',title:'业务主键',width:280,align:'left'},
					{field:'startUserId',title:'启动者',width:120,align:'left'},
					{field:'startTime',title:'启动时间',width:150,align:'left'},
					{field:'endTime',title:'结束时间',width:150,align:'left'}
				]]
			});
        });

		var setting = {
			async: {
				enable: true,
				url:"${contextPath}/mx/activiti/tree/json",
				//autoParam:["id","id=treetopIndexId"],
				//otherParam:{"otherParam":"zTreeAsyncTest"},
				dataFilter: filter
			},
			callback: {
				onExpand: myTreeOnExpand,
				onClick: myTreeOnClick
			}
		};

		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}

		function myTreeOnExpand(treeId, treeNode){
			var zTree1 = $.fn.zTree.getZTreeObj("myTree");
			if(treeNode.nlevel == 0){
				//treeNode.iconSkin = "process_folder" ;
                //treeNode.icon="${contextPath}/scripts/ztree/css/zTreeStyle/img/diy/8.png";
			} else if(treeNode.nlevel == 1){
				//treeNode.iconSkin = "process_leaf" ;
                //treeNode.icon="${contextPath}/scripts/ztree/css/zTreeStyle/img/diy/2.png";
			}
			zTree1.updateNode(treeNode);
	    }

		function myTreeOnClick(event, treeId, treeNode, clickFlag) {
             //alert(treeNode.id);
			 //updateNode(treeId, treeNode);
			 //loadData('data/datagrid_data32.json');
			 //jQuery('#iForm').form('load', treeNode);
			 //alert("level="+treeNode.nlevel);
			 if(treeNode.nlevel==0){
			     jQuery('#processDefinitionId').val('');
				 jQuery('#processDefinitionKey').val(treeNode.key);
			 } else if(treeNode.nlevel==1){
				 jQuery('#processDefinitionId').val(treeNode.id);
				 jQuery('#processDefinitionKey').val(treeNode.key);
			 }
             var actionType = jQuery('#actionType').val();
			 if(actionType == '1'){
				 if(treeNode.nlevel==0){
                    url = '${contextPath}/mx/activiti/tree/processInstances?processDefinitionKey='+treeNode.key;
				 } else if(treeNode.nlevel==1){
                    url = '${contextPath}/mx/activiti/tree/processInstances?processDefinitionId='+treeNode.id;
				 }
                
			 } else if(actionType == '2'){
				 if(treeNode.nlevel==0){
                   url = '${contextPath}/mx/activiti/tree/historyProcessInstances?processDefinitionKey='+treeNode.id;
				 } else if(treeNode.nlevel==1){
                   url = '${contextPath}/mx/activiti/tree/historyProcessInstances?processDefinitionId='+treeNode.id;
				 }
			 }
			 //alert(url);
			 loadData(url);
		}

		function onMyBeforeLoad(param){
			//alert(param);
            var processDefinitionId = jQuery('#processDefinitionId').val();
		    var processDefinitionKey = jQuery('#processDefinitionKey').val();
            if(processDefinitionId !=''){
               param.processDefinitionId=processDefinitionId;
			}
			if(processDefinitionKey !=''){
               param.processDefinitionKey=processDefinitionKey;
			}
			//var text = JSON.stringify(param); 
            //alert(text);
		}

		function loadData(url){
		  $.post(url,{q:'mike'},function(data){
		      //var text = JSON.stringify(data); 
              //alert(text);
			  $('#myGridData').datagrid({url:url});
			  $('#myGridData').datagrid({pageNumber:1});
			  $('#myGridData').datagrid('loadData', data);
		  },'json');
	  }

    function onMyDbClickRow(rowIndex, row){
       window.open('${contextPath}/mx/activiti/task?processInstanceId='+row.id+"&processDefinitionId="+row.processDefinitionId);
	 }

	$(document).ready(function(){
		$.fn.zTree.init($("#myTree"), setting);
	});
	 
</script>
<style type="text/css">
.ztree li span.button.process_folder_ico_open{margin-right:2px; background: url(${contextPath}/images/orm_root.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.process_folder_ico_close{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.process_leaf_ico_docu{margin-right:2px; background: url(${contextPath}/images/pkg.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
</style>
</head>
<body>  
<input type="hidden" id="processDefinitionId" name="processDefinitionId">
<input type="hidden" id="processDefinitionKey" name="processDefinitionKey">

<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  

    <div data-options="region:'west',split:true" style="width:220px;">
	  <div class="easyui-layout" data-options="fit:true">  
            <div data-options="region:'north',split:true,border:true" style="height:40px">

			 <div style="background:#fafafa;padding:5px;border:1px solid #ddd">  
              <select id="actionType" name="actionType">
				<option value="1" selected>流程实例</option>
				<option value="2">历史流程实例</option>
              </select>
             </div>  
			</div>

			 <div data-options="region:'center',border:false">
			    <ul id="myTree" class="ztree"></ul>  
			 </div> 
			 
        </div>  
	</div> 
	
    <div data-options="region:'center'">  
        <div class="easyui-layout" data-options="fit:true">  

           <div data-options="region:'center',split:true,border:true, fit:true">
  	           <table id="myGridData" class="easyui-datagrid"  ></table>  
 		  </div>  
        </div>  
    </div>  

   </div>  

</body>  
</html>  