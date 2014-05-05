<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邮件任务</title>
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript">

	    function saveData(){
			var params = jQuery("#iForm").formSerialize();
		    //alert(params);
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/mail/mailTask/saveMails',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   alert('操作成功！');
					   location.href="<%=request.getContextPath()%>/mx/mail/mailTask/mailList?taskId=${taskId}";
				   }
			 });
		}

</script>
</head>
<body>
<div class="content-block" style="width: 845px;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="发送测试邮件">&nbsp;发送测试邮件</div>
<fieldset class="x-fieldset" style="width: 96%;">
	 <form id="iForm" name="iForm" method="post">
 	    <input type="hidden" id="taskId" name="taskId" value="${taskId}">
		  
	    <table class="easyui-form" style="width:700px;height:250px">
		<tbody>
		    <tr>
				 <td align="left">
                   &nbsp;&nbsp; 邮件地址
				 </td>
				 <td  align="left">
                    <textarea id="addresses" name="addresses" rows="12" cols="80" class="x-textarea span5" style="width:600px;height:320px"></textarea>
					<br><br>
					(多个邮件地址之间以半角逗号","隔开)
				 </td>
			</tr>
			<tr>
				 <td colspan="4" align="center">
				     <br />
				     <input type="button" name="save" value="保存" class=" btn btn-primary" 
					        onclick="javascript:saveData();" />
					 <input type="button" name="back" value="返回" class=" btn" 
					        onclick="javascript:history.back();" />
				 </td>
			</tr>
		</tbody>
	</table>
  </form>
  </fieldset>
  </div>
</body>
</html>