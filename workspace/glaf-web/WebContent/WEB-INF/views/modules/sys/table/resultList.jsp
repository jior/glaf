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
<title>${tableName} 数据列表</title>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript">

	var x_height = Math.floor(window.screen.height * 0.52);
	var x_width = Math.floor(window.screen.width * 0.80);

	if(window.screen.height <= 768){
        x_height = Math.floor(window.screen.height * 0.50);
	}
	if(window.screen.width < 1200){
        x_width = Math.floor(window.screen.width * 0.80);
	} else if(window.screen.width > 1280){
        x_width = Math.floor(window.screen.width * 0.70);
	}  
    try{
	    x_width = document.getElementById("menu_line").offsetWidth-90;
	}catch(exe){
	}

	function onMyRowClick(rowIndex, row){
	    var link = '<%=request.getContextPath()%>/mx/sys/table/edit?tableName_enc=${tableName_enc}&businessKey='+row.${idColumn.columnName};
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
	 <table id="easyui_data_grid" class="easyui-datagrid" style="width:1000px;height:445px"
			url="<%=request.getContextPath()%>/rs/system/table/resultList?tableName_enc=<c:out value='${tableName_enc}'/>&gridType=easyui"
			title="${tableName} 数据列表 " iconCls="icon-list" remoteSort="true"  
			method="post" pageSize="15" pageList="[10,15,20,30,40,50,100,200,500]"
			rownumbers="true" pagination="true" fit="true" fitColumn="true"
			data-options="singleSelect:true, onClickRow:onMyRowClick">
		<thead>
			<tr>
			   <c:forEach items="${columns}" var="column">
				<th field="${column.columnName}" sortable="true" width="180">
				${column.columnName}
				</th>
				</c:forEach>
			</tr>
		</thead>
	</table>
  </div>  
</div>
</body>
</html>