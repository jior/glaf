<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>列表显示字段</title>
<style type="text/css">
* { margin:0; padding:0; }
div.centent {
   float:left;
   text-align: center;
   margin: 10px;
}
div.tool {
   float:left;
   text-align: center;
   margin: 10px;
}
span { 
	display:block; 
	margin:2px 2px;
	padding:4px 10px; 
	background:#3366cc;
	cursor:pointer;
	font-size:12px;
	color:white;
}
</style>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript">
  $(function(){
	//移到右边
	$('#add').click(function() {
	//获取选中的选项，删除并追加给对方
		$('#unselect option:selected').appendTo('#selected');
	});
	//移到左边
	$('#remove').click(function() {
		$('#selected option:selected').appendTo('#unselect');
	});
	//全部移到右边
	$('#add_all').click(function() {
		//获取全部的选项,删除并追加给对方
		$('#unselect option').appendTo('#selected');
	});
	//全部移到左边
	$('#remove_all').click(function() {
		$('#selected option').appendTo('#unselect');
	});
	//双击选项
	$('#unselect').dblclick(function(){ //绑定双击事件
		//获取全部的选项,删除并追加给对方
		$("option:selected",this).appendTo('#selected'); //追加给对方
	});
	//双击选项
	$('#selected').dblclick(function(){
	   $("option:selected",this).appendTo('#unselect');
	});
 
    $('#save').click(function() {
		var array = $("#selected option");
        var objectIds="";
		for(var i=0;i<array.length;i++){
            objectIds = objectIds +","+ array[i].value;
		}
		//alert(objectIds);
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/system/form/application/saveListColumns?appId=${appId}&objectIds='+objectIds,
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
				   }
			 });
	});
	
  });

   
</script>
</head>
<body>
	<div class="centent">
		<select multiple="multiple" id="unselect" style="width:250px;height:260px;">
			<c:forEach items="${unselectColumns}" var="a">
				<option value="${a.columnName}">${a.title} [${a.name}]</option>
			</c:forEach>
		</select>
		<div>
			<span id="add" >选中添加到右边&gt;&gt;</span>
			<span id="add_all" >全部添加到右边&gt;&gt;</span>
		</div>
	</div>

	<div class="centent">
		<select multiple="multiple" id="selected" style="width: 250px;height:260px;">
			<c:forEach items="${selectedColumns}" var="b">
				<option value="${b.columnName}">${b.title} [${b.name}]</option>
			</c:forEach>
		</select>
		<div>
			<span id="remove">&lt;&lt;选中删除到左边</span>
			<span id="remove_all">&lt;&lt;全部删除到左边</span>
		</div>
	</div>

   <div class="tool">
       <span id="save" class="button">保存设置</span>
   </div>

</body>
</html>