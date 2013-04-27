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
<title>出差申请</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/apps/trip.do?method=saveTrip',
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
					   if (window.opener) {
						window.opener.location.reload();
					   } else if (window.parent) {
						window.parent.location.reload();
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
				   url: '<%=request.getContextPath()%>/apps/trip.do?method=saveTrip',
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

</script>
</head>

<body>
<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"> 
	<span class="x_content_title">编辑出差申请</span>
	<!-- <input type="button" name="save" value=" 保存 " class="button btn btn-primary" onclick="javascript:saveData();">
	<input type="button" name="saveAs" value=" 另存 " class="button btn" onclick="javascript:saveAsData();">
	<input type="button" name="back" value=" 返回 " class="button btn" onclick="javascript:history.back();"> -->
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveData();" >保存</a> 
	<!-- 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-saveas'" onclick="javascript:saveAsData();" >另存</a> 
        -->
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-back'" onclick="javascript:history.back();">返回</a>	
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="${trip.id}"/>
  <table class="easyui-form" style="width:600px;" align="center">
    <tbody>
	<tr>
		<td width="20%" align="left">开始日期</td>
		<td align="left">
			<input id="startDate" name="startDate" type="text" 
			       class="easyui-datebox"
			 required="true" data-options="required:true" 
				  value="<fmt:formatDate value="${trip.startDate}" pattern="yyyy-MM-dd"/>"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">事由</td>
		<td align="left">
            <input id="cause" name="cause" type="text" 
			       class="easyui-validatebox"  
			 required="true" data-options="required:true" 
				   value="${trip.cause}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">结束日期</td>
		<td align="left">
			<input id="endDate" name="endDate" type="text" 
			       class="easyui-datebox"
			 required="true" data-options="required:true" 
				  value="<fmt:formatDate value="${trip.endDate}" pattern="yyyy-MM-dd"/>"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">申请日期</td>
		<td align="left">
			<input id="applyDate" name="applyDate" type="text" 
			       class="easyui-datebox"
			 required="true" data-options="required:true" 
				  value="<fmt:formatDate value="${trip.applyDate}" pattern="yyyy-MM-dd"/>"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">交通工具</td>
		<td align="left">
            <input id="transType" name="transType" type="text" 
			       class="easyui-validatebox"  
			
				   value="${trip.transType}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">天数</td>
		<td align="left">
			<input id="days" name="days" type="text"
			       class="easyui-numberbox"  precision="2" 
			 required="true" data-options="required:true" 
				  value="${trip.days}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">费用</td>
		<td align="left">
			<input id="money" name="money" type="text"
			       class="easyui-numberbox"  precision="2" 
			 required="true" data-options="required:true" 
				  value="${trip.money}"/>
		</td>
	</tr>
 
    </tbody>
  </table>
  </form>
</div>
</div>
</body>
</html>