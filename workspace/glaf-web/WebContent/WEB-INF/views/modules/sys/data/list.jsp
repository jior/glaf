<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据服务</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
   var contextPath="<%=request.getContextPath()%>";

   jQuery(function(){
		jQuery('#mydatagrid').datagrid({
				width:1000,
				height:480,
				fit:true,
				fitColumns: true,
				nowrap: false,
				striped: true,
				collapsible: true,
				url: '<%=request.getContextPath()%>/mx/sys/data/service/json',
				remoteSort: false,
				singleSelect: true,
				idField: 'id',
				columns:[[
				        {title:'序号', field:'startIndex', width:80, sortable:false},
						{title:'服务编号',field:'id', width:90},
						{title:'服务名称',field:'title', width:180},
						{title:'创建时间',field:'createDate_datetime', width:120},
						{title:'输出类型',field:'type', width:120},
						{title:'权限集',field:'perms', width:120},
					    {title:'功能键', field:'functionKey', align:'center', width:120, formatter:formatterKeys}
				]],
				rownumbers: false,
				pagination: true,
				pageSize: <%=com.glaf.core.util.Paging.DEFAULT_PAGE_SIZE%>,
				pageList: [10,15,20,25,30,40,50,100],
				pagePosition: 'both'
			});

			var p = jQuery('#mydatagrid').datagrid('getPager');
			jQuery(p).pagination({
				onBeforeRefresh:function(){
					//alert('before refresh');
				}
		    });
	});

 
    function formatterKeys(val, row){
		var str = "<a href='javascript:editRow(\""+row.id+"\");'>修改</a>&nbsp;<a href='javascript:viewXmlData(\""+row.id+"\");'>XML数据</a>&nbsp;<a href='javascript:viewJsonData(\""+row.id+"\");'>JSON数据</a>";
	    return str;
	}


	function editRow(id){
		var link = "<%=request.getContextPath()%>/mx/sys/data/service/edit?id="+id;
	    art.dialog.open(link, { height: 420, width: 660, title: "修改记录", lock: true, scrollbars:"yes" }, false);
	}

	function viewJsonData(id){
		 window.open("<%=request.getContextPath()%>/rs/data/service/response/"+id+"?dataType=json");
	}

	function viewXmlData(id){
		window.open("<%=request.getContextPath()%>/rs/data/service/response/"+id);
	}


	function resize(){
		jQuery('#mydatagrid').datagrid('resize', {
			width:800,
			height:400
		});
	}
  

	function reload(){
		var ids = [];
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if(selected && confirm("确定重新加载系统配置吗？")){
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/data/service/reload',
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
					   jQuery('#mydatagrid').datagrid('reload');
				   }
			 });
		} 
	}

	function reloadGrid(){
	    jQuery('#mydatagrid').datagrid('reload');
	}

	function getSelected(){
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected){
		    alert(selected.code+":"+selected.name+":"+selected.addr+":"+selected.col4);
	    }
	}

	function getSelections(){
	    var ids = [];
	    var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    for(var i=0;i<rows.length;i++){
		    ids.push(rows[i].code);
	    }
	    alert(ids.join(':'));
	}

	function clearSelections(){
	    jQuery('#mydatagrid').datagrid('clearSelections');
	}

	function loadGridData(url){
        jQuery.post(url,{qq:'xx'},function(data){
            //var text = JSON.stringify(data); 
            //alert(text);
            jQuery('#mydatagrid').datagrid('loadData', data);
        },'json');
	}

    function searchData(){
		var searchWord = document.getElementById("searchWord2").value.trim();
        document.getElementById("titleLike").value = searchWord;
		var params = jQuery("#iForm").formSerialize();
        jQuery.ajax({
                    type: "POST",
                    url: '<%=request.getContextPath()%>/mx/sys/data/service/json',
                    dataType:  'json',
				    data: params,
                    error: function(data){
                            alert('服务器处理错误！');
                    },
                    success: function(data){
                            jQuery('#mydatagrid').datagrid('loadData', data);
                    }
               });
	}

	function reloadData(){
		if(confirm("确定重新加载配置吗？")){
           jQuery.ajax({
                    type: "POST",
                    url: '<%=request.getContextPath()%>/mx/sys/data/service/reload',
                    dataType:  'json',
                    error: function(data){
                            alert('服务器处理错误！');
                    },
                    success: function(data){
						    alert('操作成功！');
                            jQuery('#mydatagrid').datagrid('loadData', data);
                    }
               });
		}
	}
		 		 
</script>
</head>
<body style="margin:1px;"> 

<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"  > 
	<img src="<%=request.getContextPath()%>/images/window.png">
	&nbsp;<span class="x_content_title">数据服务列表</span>
 
	<input id="searchWord2" name="searchWord2" type="text" 
	       class="x-searchtext" size="50" maxlength="200"/>
    
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'"
	   onclick="javascript:searchData();">查找</a>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-imp'"
	   onclick="javascript:reloadData();">重新加载配置</a>
   </div> 
  </div> 
  <div data-options="region:'center',border:true">
	 <table id="mydatagrid"></table>
  </div>  
</div>
<form id="iForm" name="iForm" method="post">
   <input type="hidden" id="titleLike" name="titleLike">
</form> 
</body>
</html>
