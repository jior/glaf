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
<title>部门列表</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/artDialog/skins/default.css"/>
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/icons/styles.css">
<script type="text/javascript" src="${contextPath}/scripts/map.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/artDialog/plugins/iframeTools.js"></script>
<script type="text/javascript">

   var treemap = new Map();
 
   $(function(){
	$('#myTreeGrid').treegrid({
       width:700,
       height:245,//注意,高度太大或太小都会导致树节点换行
       nowrap: true,
	   striped:true,
       animate:true,
	   fitColumns:true,
       fit:true,
       //rownumbers: true,
       //showFooter:true,
       //collapsible:false,     
       //autoRowHeight:true,
       //resizable:true,
       url:'${contextPath}/sys/department.do?method=treegridJson&parentId=5',
       idField:'id',
       treeField:'deptName',
       rowStyler:function(row){
       	if (row.cell2 > 1){
       		return 'background:#ff0000;color:#fff';
       	}
       },
       onClickRow: onTreeGridClick,
       columns:[[
	        {title:'名称',field:'deptName',width:480,align:'left', editor:'text'},
			{title:'描述',field:'deptDesc', width:180, editor:'text'},
			{title:'代码',field:'deptCode', width:120, editor:'text'},
			{title:'编码',field:'deptNo', width:120, editor:'text'},
			{title:'部门区分',field:'deptCode2', width:120, editor:'text'} 
           ]]
		});

	$('#userGrid').datagrid({
       url: '${contextPath}/sys/user.do?method=json&parent=${parent}&deptId=${deptId}',
       width: 700,
       height: 'auto',
       fitColumns:true,
       fit:true,
	   singleSelect:true,
	   onClickRow: onUserRowClick,
       columns:[[
       	    {title:'序号',field:'startIndex',width:80},
			{title:'用户名',field:'actorId', width:120},
			{title:'姓名',field:'name', width:120},
			{title:'部门',field:'deptName', width:180},
			{title:'最近登录日期',field:'lastLoginTime', width:120},
			{title:'是否有效',field:'blocked', width:90, formatter:formatterStatus}
       ]]
	});

	   $('#roleGrid').datagrid({
       url: '${contextPath}/sys/department.do?method=deptRolesJson&nodeId=5',
       width: 700,
       height: 'auto',
	   fit:true,
       fitColumns:true,
	   singleSelect:true,
	   onClickRow: onRoleRowClick,
       columns:[[
			{title:'角色名称',field:'name', width:120},
			{title:'编码',field:'code', width:120},
			{title:'描述',field:'desc', width:120}
       ]]
	});

  });


  function onUserRowClick(rowIndex, row){
      var link = '${contextPath}/sys/user.do?method=showUser&parent=${parent}&deptId=${deptId}&userId='+row.id;
	  art.dialog.open(link, { height: 420, width: 520, title: "查看用户信息", lock: true, scrollbars:"no" }, false);
  }

  function onRoleRowClick(rowIndex, row){
      var link = '${contextPath}/sys/department.do?method=showDeptRoleUsers&nodeId='+row.nodeId+'&roleCode='+row.code;
	  art.dialog.open(link, { height: 420, width: 580, title: "角色用户", lock: true, scrollbars:"yes" }, false);
  }
 

	function formatterStatus(val, row){
       if(val == 0){
			return '<span style="color:green; font: bold 13px 宋体;">是</span>';
	   } else  {
			return '<span style="color:red; font: bold 13px 宋体;">否</span>';
	   }  
	}

	function onTreeGridClick(row){
       //alert("点击了\n"+row.indexName+"\nid="+row.indexId);
	    var url = '${contextPath}/sys/department.do?method=treegridJson&parentId='+row.id;
        loadData(row, url);
		updateUserGrid(row);
		updateRoleGrid(row);
	}

 
	function updateUserGrid(row){
		//alert("点击了\n"+row.indexName+"\nid="+row.indexId);
        var url = '${contextPath}/sys/user.do?method=json&parent='+row.deptId;
	    $.post(url,{qq:'mike'},function(data){
        //var text = JSON.stringify(data); 
        //alert(text);
        $('#userGrid').datagrid('loadData', data);
            },'json');
	}

	function updateRoleGrid(row){
		//alert("点击了\n"+row.indexName+"\nid="+row.indexId);
        var url = '${contextPath}/sys/department.do?method=deptRolesJson&nodeId='+row.id;
	    $.post(url,{qq:'mike'},function(data){
        //var text = JSON.stringify(data); 
        //alert(text);
        $('#roleGrid').datagrid('loadData', data);
            },'json');
	}

	 
	function loadData(row, url){
		//alert(url);
		jQuery.post(url,{qq:'mike'},function(data){
		//var text = JSON.stringify(data); 
        //alert(text);
		//$('#myTreeGrid').datagrid('loadData', data);
	    if(data.length > 0 ){
          var node = $('#myTreeGrid').treegrid('getSelected');
          if(treemap.get(node.id) == null){
            $('#myTreeGrid').treegrid('append',{
	           parent: node.id,  // the node has a 'id' value that defined through 'idField' property
	           data: data
		    });
            treemap.put(node.id, "1");
          }
		} else {
             //alert(row.indexName+"已经是叶节点了.");
			 }
		  },'json');
	  }

    function getWidth(percent){
        return $(window).width() * percent;
    }

	function getHeight(percent){
        return $(window).height() * percent;
    }

	function getHeight(){
        return $(window).height() * 0.6;
    }

	function getHeight2(){
        return $(window).height() * 0.4;
    }

        var editingId;

		function edit(){
			if (editingId != undefined){
				$('#myTreeGrid').treegrid('select', editingId);
				return;
			}
			var row = $('#myTreeGrid').treegrid('getSelected');
			if (row){
				//alert(row.id);
				editingId = row.id;
				$('#myTreeGrid').treegrid('beginEdit', editingId);
			}
		}

		function save(){
			if (editingId != undefined){
				var t = $('#myTreeGrid');
				t.treegrid('endEdit', editingId);
				var rows = t.treegrid('getChildren');
				for(var i=0; i<rows.length; i++){
					if(rows[i].id == editingId){
                        var text = JSON.stringify(rows[i]); 
                        alert(text);
					}
				}
				editingId = undefined;
			}
		}

		function cancel(){
			if (editingId != undefined){
				$('#myTreeGrid').treegrid('cancelEdit', editingId);
				editingId = undefined;
			}
		}

</script>
</head>
<body>  
<div id="container" class="easyui-layout" data-options="fit:true">  
  <div id="main_info" data-options="region:'center', split:true, border:true"> 
      <div style="background:#fafafa;padding:2px;border:1px solid #ddd;font-size:12px;"> 
		   &nbsp;<img src="${contextPath}/images/window.png">
		   &nbsp;<span class="x_content_title">部门列表</span>
		   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'">新增</a>   
		   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'" 
		      onclick="javascript:edit();">修改</a>  
		   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" 
		      onclick="javascript:save();">保存</a>
		   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-cancel'" 
		      onclick="javascript:cancel();">取消</a>
       </div>
			
	   <table id="myTreeGrid"></table> 
	
	</div>  

    <div id="dept_info" data-options="region:'south',border:true,split:true"  style="height:185px">		
          <div class="easyui-tabs" data-options="fit:true,border:false,plain:true">	
	       
			<div style="padding:10px" title="部门用户">
				<table id="userGrid"></table>
			</div>

			<div style="padding:10px" title="部门角色">
				<table id="roleGrid"></table>
			</div>

          </div>

        </div>
	
    </div> 

 </div>  

 <div id="mm1" style="width:150px;"> 
	<div>新增同级</div>
	<div>新增下级</div>
 </div>

  <div id="mm3" style="width:150px;"> 
	<div>删除本节点</div>
	<div>删除本节点及子孙</div>
 </div>

</body>  
</html>  
 