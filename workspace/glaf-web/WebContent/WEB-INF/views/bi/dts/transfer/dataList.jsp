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
<title>${dataTransfer.tableName} 数据列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript">

    jQuery(function(){
		jQuery('#easyui_data_grid').datagrid({
				width: ${dataTransfer.columns.size()*190},
				height:400,
				fit:false,
				fitColumns: true,
				nowrap: false,
				striped: true,
				collapsible: true,
				url: '<%=request.getContextPath()%>/rs/system/table/resultList?tableName_enc=${tableName_enc}&gridType=easyui',
				remoteSort: false,
				singleSelect: true,
				idField: '${dataTransfer.idColumn.columnName}',
				columns:[[
				        {title:'序号', field:'startIndex', width:80, sortable:false}
                        <c:forEach items="${dataTransfer.columns}" var="column">
						  <c:choose>
							<c:when test="${!empty column.title }">
						,{title:'${column.title}', field:'${column.columnName}', width:180, sortable:false}
							</c:when>
							<c:otherwise>
						,{title:'${column.columnName}', field:'${column.columnName}', width:180, sortable:false}
							</c:otherwise>
						  </c:choose>
						</c:forEach>
				]],
				rownumbers: false,
				pagination: true,
				pageSize: 10,
				pageList: [10,15,20,25,30,40,50,100,500],
				onClickRow: onMyRowClick 
			});

			var p = jQuery('#easyui_data_grid').datagrid('getPager');
			jQuery(p).pagination({
				onBeforeRefresh:function(){
					//alert('before refresh');
				}
		    });
	});

	function onMyRowClick(rowIndex, row){
	    var link = '<%=request.getContextPath()%>/mx/sys/table/edit?tableName_enc=${tableName_enc}&businessKey='+row.${dataTransfer.idColumn.columnName};
	    var x=50;
        var y=50;
        if(is_ie) {
	      x=document.body.scrollLeft+event.clientX-event.offsetX-100;
	      y=document.body.scrollTop+event.clientY-event.offsetY-50;
        }
        openWindow(link, self, x, y, 620, 580);
	}

</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div> 
<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'center',border:false">
	  <table id="easyui_data_grid"></table>
  </div>  
</div>
</body>
</html>