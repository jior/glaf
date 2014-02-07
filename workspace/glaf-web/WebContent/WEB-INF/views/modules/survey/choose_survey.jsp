<%--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--%>
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
<title>调查列表</title>
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
				url: '<%=request.getContextPath()%>/mx/base/survey/json',
				//sortName: 'id',
				//sortOrder: 'desc',
				remoteSort: false,
				idField: 'id',
				columns:[[
					    {field:'ck', checkbox:true},
				        {title:'序号', field:'startIndex', width:80, sortable:false},
					    {title:'主题',field:'title', width:220},
					    {title:'是否有效',field:'status', width:90, formatter:formatterStatus},
					    {title:'是否记名',field:'signFlag', width:90, formatter:formatterStatus2},
					    {title:'是否多选',field:'multiFlag', width:90, formatter:formatterStatus2},
					    {title:'是否限制IP',field:'limitFlag', width:120, formatter:formatterStatus2},
					    {title:'限制时间间隔（分钟）',field:'limitTimeInterval', width:150},
					    {title:'开始日期',field:'startDate', width:90},
					    {title:'结束日期',field:'endDate', width:90}
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


	function formatterStatus(val, row){
       if(val == 1){
			return '<span style="color:green; font: bold 13px 宋体;">是</span>';
	   } else  {
			return '<span style="color:red; font: bold 13px 宋体;">否</span>';
	   }  
	}


	function formatterStatus2(val, row){
       if(val == 1){
			return '<span style="color:blue; font: bold 13px 宋体;">是</span>';
	   } else  {
			return '<span style="color:blue; font: bold 13px 宋体;">否</span>';
	   }  
	}


	function searchWin(){
	    jQuery('#dlg').dialog('open').dialog('setTitle','调查查询');
	    //jQuery('#searchForm').form('clear');
	}

	function resize(){
		jQuery('#mydatagrid').datagrid('resize', {
			width:800,
			height:400
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
		    location.href="<%=request.getContextPath()%>/mx/base/survey/edit?fromUrl=${fromUrl}&id="+selected.id;
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
            var params = jQuery("#searchForm").formSerialize();
            jQuery.ajax({
                        type: "POST",
                        url: '<%=request.getContextPath()%>/mx/base/survey/json',
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

	 
	
	function chooseSelected(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    if(rows == null || rows.length == 0){
		  alert("请选择至少一条记录。");
		  return;
	    }
		var ids = [];
		var names = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].id);
			names.push(rows[i].title);
		}
	    var str = ids.join(',');
		var str2 = names.join('<br/>');
		var parent_window = getOpener();
	    var x_elementId = parent_window.document.getElementById("${elementId}");
        var x_element_name = parent_window.document.getElementById("${elementName}");
		x_elementId.value=str;
		x_element_name.innerHTML=str2;
		//alert();
		window.close(); 
	}
	
</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"  > 
	<img src="<%=request.getContextPath()%>/images/window.png">
	&nbsp;<span class="x_content_title">调查列表</span>
     
    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-ok'"
	   onclick="javascript:chooseSelected();">确定</a>  
	 
   </div> 
  </div> 
  <div data-options="region:'center',border:true">
	 <table id="mydatagrid"></table>
  </div>  
</div>
<div id="edit_dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
	closed="true" buttons="#dlg-buttons">
    <form id="editForm" name="editForm" method="post">
         
    </form>
</div>
<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
	closed="true" buttons="#dlg-buttons">
  <form id="searchForm" name="searchForm" method="post">
  <table class="easyui-form" >
    <tbody>
    <tr>
	<td>主题</td>
	<td>
        <input id="titleLike" name="titleLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    <tr>
	<td>内容</td>
	<td>
        <input id="contentLike" name="contentLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    <tr>
	<td>主题图片</td>
	<td>
        <input id="iconLike" name="iconLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    <tr>
	<td>状态</td>
	<td>
	<input id="status" name="status" class="easyui-numberbox" precision="0" ></input>
       </td>
     </tr>
    <tr>
	<td>是否记名</td>
	<td>
	<input id="signFlag" name="signFlag" class="easyui-numberbox" precision="0" ></input>
       </td>
     </tr>
    <tr>
	<td>是否多选</td>
	<td>
	<input id="multiFlag" name="multiFlag" class="easyui-numberbox" precision="0" ></input>
       </td>
     </tr>
    <tr>
	<td>是否限制IP</td>
	<td>
	<input id="limitFlag" name="limitFlag" class="easyui-numberbox" precision="0" ></input>
       </td>
     </tr>
    <tr>
	<td>限制时间间隔</td>
	<td>
	<input id="limitTimeInterval" name="limitTimeInterval" class="easyui-numberbox" precision="0" ></input>
       </td>
     </tr>
    <tr>
	<td>开始日期</td>
	<td>
	<input id="startDateLessThanOrEqual" name="startDateLessThanOrEqual" class="easyui-datebox"></input>
       </td>
     </tr>
    <tr>
	<td>结束日期</td>
	<td>
	<input id="endDateLessThanOrEqual" name="endDateLessThanOrEqual" class="easyui-datebox"></input>
       </td>
     </tr>
    <tr>
	<td>创建人</td>
	<td>
        <input id="createByLike" name="createByLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    <tr>
	<td>创建日期</td>
	<td>
	<input id="createDateLessThanOrEqual" name="createDateLessThanOrEqual" class="easyui-datebox"></input>
       </td>
     </tr>
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
