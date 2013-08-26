<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_config.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=appTitle%></title>
<meta name="Keywords" content="<%=appKeywords%>" />
<meta name="Description" content="<%=appDescription%>" />
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>

<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";
	var status = ${status};
	function saveData(id){
		var params = jQuery("#iForm").formSerialize();
		var isValid = $("#iForm").form('validate');
		if (!isValid){
			$.messager.alert('Warning','请检查输入！');
			return isValid; // 返回false将停止form提交 
		}
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/cms/fullCalendar/saveFullCalendar',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.title!=undefined){
						   window.parent.saveCallback(data.title,data.start,data.end,data.id,status);
						   art.dialog.close();
					   }
				   }
			 });
	}

	function saveAsData(){
		document.getElementById("id").value="";
		document.getElementById("id").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/cms/fullCalendar/saveFullCalendar',
				   data: params,
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
	}

	function deleteRecord(id){
		if(id){
		    if(confirm("数据删除后不能恢复，确定删除吗？")){
				jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/mx/cms/fullCalendar/delete?ids='+id,
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
						   window.parent.removeCallback(id);
						   art.dialog.close();
					   }
				 });
		    }
		} else {
			alert("请选择至少一条记录。");
		}
	}

</script>
</head>

<body>
<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"> 
	<span class="x_content_title">编辑个人日程</span>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveData(<c:out value="${fullCalendar.id}"></c:out>);" >保存</a> 
	
	<c:if test="${fullCalendar.id!=null}">
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'" onclick="javascript:deleteRecord(<c:out value="${fullCalendar.id}"></c:out>);" >删除</a>
	</c:if>
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="${fullCalendar.id}"/>
  <input type="hidden" id="status" name="status" value="${fullCalendar.status==null?status:fullCalendar.status}"/>
  <fieldset >
	<legend>
		个人日程
	</legend>
  <table class="easyui-form" style="width:600px;" align="center">
    <tbody>
	    <tr>
		<td width="20%" class="editTitle">开始时间:</td>
		<td align="left">
			<input id="dateStart" name="dateStart" type="text" 
				class="easyui-datetimebox"  style="width:135px;" data-options="required:true,showSeconds:false";
				value="<fmt:formatDate value="${start==null?fullCalendar.dateStart:start}" pattern="yyyy-MM-dd HH:mm"/>"/>
		</td>
		<td width="20%" class="editTitle">结束时间:</td>
		<td align="left">
			<input id="dateEnd" name="dateEnd" type="text" 
				class="easyui-datetimebox"  style="width:135px;" data-options="required:true,showSeconds:false";
				value="<fmt:formatDate value="${end==null?fullCalendar.dateEnd:end}" pattern="yyyy-MM-dd HH:mm"/>"/>
		</td>
	    </tr>
		<tr>
		<td width="20%" class="editTitle">标题:</td>
		<td align="left" colspan="3">
			<input id="title" name="title" type="text" 
				class="easyui-validatebox editInput"  style="width:480px;" data-options="required:true"
				value="${fullCalendar.title}"/>
		</td>
	    </tr>
		<tr>
		<td width="20%" class="editTitle">地点:</td>
		<td align="left" colspan="3">
			<input id="address" name="address" type="text" 
				class="easyui-validatebox editInput"  style="width:480px;"
				value="${fullCalendar.address}"/>
		</td>
		</tr>
		<tr>
		<td width="20%" class="editTitle">内容:</td>
		<td align="left" colspan="3">
			<textarea id="content" name="content"
				class="easyui-validatebox editTextarea" rows="4" style="width:479px;">${fullCalendar.content}</textarea>
		</td>
	    </tr>
		<tr>
		<td width="20%" class="editTitle">备注:</td>
		<td align="left" colspan="3">
			<textarea id="remark" name="remark"
				class="easyui-validatebox editTextarea" rows="4" style="width:479px;">${fullCalendar.remark}</textarea>
		</td>
	    </tr>
 
    </tbody>
  </table>
  </fieldset>
  </form>
</div>
</div>
</body>
</html>
<script type="text/javascript">

</script>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>