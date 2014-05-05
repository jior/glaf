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
<title>字典设置</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

		function onClickRow(index){
			$('#dg').datagrid('selectRow', index).datagrid('beginEdit', index);	 
		}

		function formatterType(val, row){
		   if(val=="Long"){
			   return "整数型";
		   } else if(val=="Double"){
			   return "数值型";
		   } else if(val=="Date"){
			   return "日期型";
		   }
		   return "字符型";
		}

		function formatterRequired(val, row){
		   if(val=="1"){
			   return "<div style='color:red'>是</div>";
		   }  
		   return "否";
		}

       
        function saveChanges(){
			$('#dg').datagrid('acceptChanges');
			var params = "";
		    var rows = $('#dg').datagrid('getRows');
			for(var i=0;i<rows.length;i++){
				if(rows[i]['title'] != "" ){
                   params+="&"+rows[i]['name']+"_title="+rows[i]['title'];
                   params+="&"+rows[i]['name']+"_required="+rows[i]['required'];
				}
			}
			//alert(params);

		    jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/sys/dictoryDefinition.do?method=saveDictoryDefinition&nodeId=${nodeId}&target=${target}',
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
				   }
		    });
	    }
		 
</script>
</head>
<body style="margin:1px;">  
 
<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'center',border:true">
	 <table id="dg" class="easyui-datagrid" 
			data-options="
			    width:600,
				height:350,
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb',
				url: '<%=request.getContextPath()%>/sys/dictoryDefinition.do?method=json&nodeId=${nodeId}&target=${target}',
				onClickRow: onClickRow">
		<thead>
			<tr>
				<!-- <th data-options="field:'id', width:80">编号</th> -->
				<th data-options="field:'name', width:120">名称</th>
				<th data-options="field:'type', width:120,formatter:formatterType">类型</th>
				<th data-options="field:'title', width:180, editor:'text'">标题</th>
				<th data-options="field:'required', width:80, align:'center', formatter:formatterRequired, 
				       editor:{type:'checkbox',options:{on:'1',off:'0'}}">
				    是否必填
				</th>
			</tr>
		</thead>
	</table>

	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:saveChanges();">保存</a>
	</div>

  </div>  
</div>
 
</body>
</html>
