<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表任务定义</title>
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/datepicker/skin/WdatePicker.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/datepicker/skin/default/datepicker.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/datepicker/config.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/datepicker/lang/zh-cn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/datepicker/WdatePicker.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/scripts/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript">

    KE.show({  id : 'sendContent',
	           allowFileManager : true
    });

	function saveData(){
		document.getElementById("sendContent").value=KE.html('sendContent');
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/bi/reportTask/save',
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
					   location.href="<%=request.getContextPath()%>/mx/bi/reportTask";
				   }
			 });
	}

	function saveAsData(){
		document.getElementById("id").value="";
		document.getElementById("reportTaskId").value="";
		document.getElementById("sendContent").value=KE.html('sendContent');
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/bi/reportTask/save',
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
					   location.href="<%=request.getContextPath()%>/mx/bi/reportTask";
				   }
			 });
	}

		function openReport(){
            var selected = jQuery("#reportIds").val();
            var link = '<%=request.getContextPath()%>/mx/bi/report/reportTree?elementId=reportIds&elementName=reportNames&nodeCode=report_category&selected='+selected;
			var x=100;
			var y=100;
			if(is_ie) {
				x=document.body.scrollLeft+event.clientX-event.offsetX-200;
				y=document.body.scrollTop+event.clientY-event.offsetY-200;
			}
			openWindow(link,self,x, y, 495, 480);
		}

        function openReport2(){
                var link = '<%=request.getContextPath()%>/mx/bi/reportTask/chooseReport?rowId=${reportTask.id}&elementId=reportIds&elementName=reportNames';
				var x=100;
				var y=100;
				if(is_ie) {
					x=document.body.scrollLeft+event.clientX-event.offsetX-200;
					y=document.body.scrollTop+event.clientY-event.offsetY-200;
				 }
				openWindow(link,self,x, y, 695, 480);
			}
</script>
</head>
<body style="padding-left:20px;padding-right:20px">

<div class="content-block" style="width: 845px;">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="报表任务定义">&nbsp;报表任务定义</div>
<br>
<form id="iForm" name="iForm" method="post">
<input type="hidden" id="id" name="id" value="${reportTask.id}"/>
<input type="hidden" id="reportTaskId" name="reportTaskId" value="${reportTask.id}"/>
<table class="easyui-form" style="width:920px;" align="center">
<tbody>
	<tr>
		<td width="20%" align="left">名称</td>
		<td align="left">
            <input id="name" name="name" type="text" 
			       class="x-text input-xlarge span7  easyui-validatebox"  
			       required="true" size="50"
				   value="${reportTask.name}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">标题</td>
		<td align="left">
            <input id="subject" name="subject" type="text" 
			       class="x-text input-xlarge span7  easyui-validatebox"  
			       required="true" size="50"
				   value="${reportTask.subject}"/>
		</td>
	</tr>
	
	<tr>
		<td width="20%" align="left">报表集编号</td>
		<td align="left">
            <input id="reportIds" name="reportIds" type="hidden" 
				   value="${reportTask.reportIds}"/>
			<input id="reportNames" name="reportNames" type="text" 
			       class="x-text input-xlarge span7  easyui-validatebox"  
			       required="true" onclick="javascript:openReport();"
				   value="${reportNames}" size="50"/>
			 &nbsp;
			<a href="#" onclick="javascript:openReport();">
				<img src="<%=request.getContextPath()%>/images/report.gif" border="0">
			</a>
		</td>
	</tr>
	<tr>
		<td>是否启用</td>
		<td colspan="3">
		  <select id="enableFlag" name="enableFlag" class="span2">
			<option value="1">启用</option>
			<option value="0">不启用</option>
		  </select>
		  <script type="text/javascript">
		    $('#enableFlag').val('${reportTask.enableFlag}');
		  </script>
		  &nbsp;&nbsp;&nbsp;&nbsp;
          cron表达式
		  &nbsp;&nbsp; 
		 <input type="text" id="cronExpression" name="cronExpression" value="${reportTask.cronExpression}" size="20"
				class="x-text span2"> 
		  &nbsp;&nbsp;
		  (可以参考<a href="<%=request.getContextPath()%>/quartz.txt" target="_blank">quartz</a>文件)
		 </td>
	</tr>
	<tr>
		<td width="20%" align="left">发送标题</td>
		<td align="left">
            <input id="sendTitle" name="sendTitle" type="text" 
			       class="x-text input-xlarge span7  easyui-validatebox"  
				   value="${reportTask.sendTitle}" size="50"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">发送内容</td>
		<td align="left">
			<textarea type="textarea" id="sendContent" name="sendContent"  
				      class="x-textarea"  style="width:725px;height:420px;"><c:out value="${reportTask.sendContent}" escapeXml="true"/></textarea>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">邮件接收人</td>
		<td align="left">
		     <textarea type="textarea" id="mailRecipient" name="mailRecipient"  
				      class="x-textarea"  style="width:715px;height:120px;">${reportTask.mailRecipient}</textarea>
		</td>
	</tr>
<tr>
	<td colspan="4" align="center">
	<br />
	<input type="button" name="save" value=" 保存 " class="btn btn-primary" onclick="javascript:saveData();">
	<input type="button" name="saveAs" value=" 另存 " class="btn" onclick="javascript:saveAsData();">
	<input type="button" name="back" value=" 返回 " class="btn" onclick="javascript:history.back();">
	</td>
	</tr>
</tbody>
</table>
</form>
</div>

</body>
</html>