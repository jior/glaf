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
<title>用户查询</title>
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
				url: '<%=request.getContextPath()%>/identity/user.do?method=json',
				remoteSort: false,
				singleSelect: true,
				idField: 'id',
				columns:[[
				        {title:'序号', field:'startIndex', width:80, sortable:false},
						{title:'用户名',field:'actorId', width:90},
						{title:'用户姓名',field:'name', width:90},
					    {title:'电子邮件',field:'email', width:90},
					    {title:'部门',field:'deptName', width:120},
						{title:'最后登录时间',field:'loginDate_datetime', width:120},
						{title:'登录IP',field:'loginIP', width:120},
						{title:'最后更新时间',field:'updateDate_datetime', width:120},
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
		var str = "<a href='javascript:viewUser(\""+row.actorId+"\");'>查看</a>";
	    return str;
	}

	function viewUser(actorId){
		var link = '<%=request.getContextPath()%>/identity/user.do?method=view&actorId='+actorId;
		art.dialog.open(link, { height: 420, width: 480, title: "查看用户信息", lock: true, scrollbars:"no" }, false);
	}

    
	function resize(){
		jQuery('#mydatagrid').datagrid('resize', {
			width:800,
			height:400
		});
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
        document.getElementById("searchWord").value = searchWord;
		var params = jQuery("#iForm").formSerialize();
        jQuery.ajax({
                    type: "POST",
                    url: '<%=request.getContextPath()%>/identity/user.do?method=json',
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
		 		 
</script>
</head>
<body style="margin:1px;"> 

<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"  > 
	<img src="<%=request.getContextPath()%>/images/window.png">
	&nbsp;<span class="x_content_title">用户列表</span>
	
	<input id="searchWord2" name="searchWord2" type="text" 
	       class="x-searchtext" size="50" maxlength="200"/>
    
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'"
	   onclick="javascript:searchData();">查找</a>
   </div> 
  </div> 
  <div data-options="region:'center',border:true">
	 <table id="mydatagrid"></table>
  </div>  
</div>
<form id="iForm" name="iForm" method="post">
   <input type="hidden" id="searchWord" name="searchWord">
</form> 
</body>
</html>
