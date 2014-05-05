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
<title>${formApplication.title}</title>
<link href="<%=request.getContextPath()%>/scripts/artDialog/skins/default.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/plugins/iframeTools.js"></script>
<script type="text/javascript">

   jQuery(function(){
		jQuery('#mydatagrid').datagrid({
				width:1000,
				height:480,
				fit:true,
				fitColumns:true,
				nowrap: false,
				striped: true,
				collapsible:true,
				url:'<%=request.getContextPath()%>/mx/form/json?appId=${formApplication.id}',
				sortName: 'id',
				sortOrder: 'desc',
				remoteSort: false,
				singleSelect:true,
				idField:'id',
				columns:[[
	                {title:'序号',field:'startIndex',width:80,sortable:false}
					<c:forEach items="${columns}" var="col">
					,{title:'${col.title}',field:'${col.name}',width:180,sortable:false}
					</c:forEach>
					,{title:'状态',field:'status',width:90,sortable:false, formatter:formatterStatus}
				]],
				rownumbers:false,
				pagination:true,
				pageSize:15,
				pageList: [10, 15, 20, 25, 30, 40, 50, 100],
				onDblClickRow: onRowClick 
			});

			var p = jQuery('#mydatagrid').datagrid('getPager');
			jQuery(p).pagination({
				onBeforeRefresh:function(){
					//alert('before refresh');
				}
		    });
	});


	function formatterStatus(val, row){
       if(val == 50){
			return '<span style="color:green; font: bold 13px 宋体;">审核通过</span>';
	   } else if(val == -1){
			return '<span style="color:red; font: bold 13px 宋体;">已退回</span>';
	   } else if(val == 0){
			return '未提交';
	   }
	   return '<span style="color:blue; font: bold 13px 宋体;">审核中</span>';
	}

		 
	function addNew(){
	    var link="<%=request.getContextPath()%>/mx/form/edit?appId=${formApplication.id}";
	    art.dialog.open(link, { height: 520, width: 880, title: "添加记录", lock: true, scrollbars:"no" }, false);
	}

	function onRowClick(rowIndex, row){
	    var link = '<%=request.getContextPath()%>/mx/form/edit?appId=${formApplication.id}&businessKey='+row.businessKey;
	    art.dialog.open(link, { height: 520, width: 880, title: "修改记录", lock: true, scrollbars:"no" }, false);
	}

	function searchWin(){
	    jQuery('#dlg').dialog('open').dialog('setTitle','${formApplication.title}查询');
	}

	function resize(){
		jQuery('#mydatagrid').datagrid('resize', {
			width:800,
			height:400
		});
	}

	function editSelected(){
	    var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    if(rows == null || rows.length !=1){
		  alert("请选择其中一条记录。");
		  return;
	    }
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected ){
		  var link = "<%=request.getContextPath()%>/mx/form/edit?appId=${formApplication.id}&businessKey="+selected.businessKey;
		  art.dialog.open(link, { height: 520, width: 880, title: "修改记录", lock: true, scrollbars:"no" }, false);
	    }
	}

	function viewSelected(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
		    location.href="<%=request.getContextPath()%>/mx/form/edit?readonly=true&appId=${formApplication.id}&businessKey="+selected.businessKey;
		}
	}

	function deleteSelections(){
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].id);
		}
		if(ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")){
		    var businessKeys = ids.join(',');
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/form/delete?appId=${formApplication.id}&businessKeys='+businessKeys,
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
		} else {
			alert("请选择至少一条记录。");
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
		      var text = JSON.stringify(data); 
              alert(text);
			  jQuery('#mydatagrid').datagrid('loadData', data);
		  },'json');
	  }

	function searchData(){
	    var params = jQuery("#searchForm").formSerialize();
	    jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/form/json?appId=${formApplication.id}',
				   dataType:  'json',
				   data: params,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					  jQuery('#mydatagrid').datagrid('loadData', data);
				   }
			 });

	    jQuery('#dlg').dialog('close');
	}

    function submitSelected(){
        var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    if(rows == null || rows.length !=1){
		    alert("请选择其中一条记录。");
		    return;
	    }
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected){
           if(selected.processInstanceId && selected.processInstanceId != null){
			   jQuery('#businessKey').val(selected.id);
               jQuery('#dd_audit').dialog('open');
		   } else {
              jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/form/startProcess?appId=${formApplication.id}&businessKey='+selected.businessKey,
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
					   switchTasks();
				   }
			 });
		   }
		}
	}


	function completeTask(isAgree){
        if(isAgree == 'false'){
			if(jQuery('#opinion').val() == ""){
				alert("请输入不通过的原因。");
				document.getElementById('#opinion').focus();
				return;
			}
		}
		var businessKey = jQuery('#businessKey').val();
		var params = jQuery("#auditForm").formSerialize();
        jQuery.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/mx/form/completeTask?appId=${formApplication.id}&businessKey='+businessKey+'&isAgree='+isAgree,
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
				switchTasks();
			}
		 });
	}

	 
	function viewSelected(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
		    location.href="<%=request.getContextPath()%>/mx/form/edit?serviceKey=news&id="+selected.id;
		}
	}

	function viewProcess(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected &&  selected.processInstanceId && selected.processInstanceId != null){
		    window.open('<%=request.getContextPath()%>/mx/jbpm/task/task?processInstanceId='+selected.processInstanceId);
		} else {
            alert("该记录没有启动工作流，不能查看流程进度。");
		}
	}

	function switchTasks(){
		 var workedProcessFlag = jQuery("#workedProcessFlag").val();
		 var url = "<%=request.getContextPath()%>/mx/form/json?appId=${formApplication.id}&workedProcessFlag="+workedProcessFlag;
         jQuery.get(url,{qq:'xx'},function(data){
			  jQuery('#mydatagrid').datagrid('loadData', data);
		  },'json');
	}
	
</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"  > 
	<img src="<%=request.getContextPath()%>/images/window.png">
	&nbsp;<span class="x_content_title">${formApplication.title}列表</span>
	<span>状态&nbsp;
	  <input type="hidden" id="businessKey" name="businessKey" value="" >
	  <select id="workedProcessFlag" name="workedProcessFlag" onchange="javascript:switchTasks();">
		<option value="">--请选择--</option>
		<option value="DF">未提交</option>
		<option value="PD">待审</option>
		<option value="WD">已审</option>
		<option value="END">已完成</option>
	  </select>
	</span>
    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
	   onclick="javascript:addNew();">新增</a>  
    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
	   onclick="javascript:editSelected();">修改</a>  
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'"
	   onclick="javascript:deleteSelections();">删除</a> 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'"
	   onclick="javascript:searchWin();">查找</a>
   </div> 
  </div> 
  <div data-options="region:'center',border:true">
	 <table id="mydatagrid"></table>
  </div>  
</div>

<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
	closed="true" buttons="#dlg-buttons">
  <form id="searchForm" name="searchForm" method="post">
    <table class="easyui-form" >
      <tbody>
     
      </tbody>
    </table>
  </form>
</div>

<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:searchData()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:jQuery('#dlg').dialog('close')">取消</a>
</div>

</body>
</html>
