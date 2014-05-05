<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邮件任务定义</title>
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/kindeditor/skins/default.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/mxcore.js"></script>
<script type="text/javascript">

           KE.show({  id : 'textContent',
	               allowFileManager : true
           });

			function initData(){
			   // $('#iForm').form('load','<%=request.getContextPath()%>/rs/mail/sender/view/${rowId}');
			}

			function saveData(){
				   document.getElementById("textContent").value=KE.html('textContent');
			       var params = jQuery("#iForm").formSerialize();
				   jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/mail/sender/saveMail',
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

		 function saveAsData(){
			       document.getElementById("id").value="";
				   document.getElementById("mailId").value="";
				   document.getElementById("textContent").value=KE.html('textContent');
			       var params = jQuery("#iForm").formSerialize();
				   jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/mail/sender/saveMail',
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
 

		function sendTestMail(){
            jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/mail/sender/sendMail?mailId=${mailPathSender.id}',
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data.message != null){
						   alert(data.message);
					   } else {
						 alert('操作完成！');
					   }
				   }
			     });
		}


	 </script>
</head>
<body>
    <br>
	<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="邮件任务定义"> &nbsp;邮件任务定义
	</div>
	 <form id="iForm" name="iForm" method="post">
	    <input type="hidden" id="id" name="id" value="${mailPathSender.id}"/>
	    <input type="hidden" id="mailId" name="mailId" value="${mailPathSender.id}"/>
	    <table class="easyui-form" style="width:920px;" align="center">
		<tbody>
			 
			<tr>
				 <td height="28">名称</td>
				 <td colspan="3">
                 <input id="name" name="name" class="span7 x-text" type="text"
				        value="${mailPathSender.name}" size="80"
				 ></input>
				 </td>
			</tr>
			<tr>
				 <td height="28">标题</td>
				 <td colspan="3">
                 <input id="subject" name="subject" class="span7 x-text" type="text"
				        value="${mailPathSender.subject}" size="80"
				 ></input>
				 </td>
			</tr>
			
			<tr>
				 <td height="28">是否启用</td>
				 <td colspan="3">
				 <select id="enableFlag" name="enableFlag" class="span2">
					<option value="1">启用</option>
					<option value="0">不启用</option>
				 </select>
				 <script type="text/javascript">
				    $('#enableFlag').val('${mailPathSender.enableFlag}');
				 </script>
				 &nbsp;&nbsp;&nbsp;&nbsp;
                 cron表达式
				 &nbsp;&nbsp; 
				 <input type="text" id="cronExpression" name="cronExpression" value="${mailPathSender.cronExpression}" size="20"
				        class="x-text span2"> 
				 &nbsp;&nbsp;(可以参考<a href="<%=request.getContextPath()%>/quartz.txt" target="_blank">quartz</a>文件)
				 </td>
			</tr>

			<tr>
				 <td height="28">是否压缩</td>
				 <td colspan="3">
				 <select id="compressFlag" name="compressFlag" class="span2">
					<option value="1">压缩</option>
					<option value="0">不压缩</option>
				 </select>
				 <script type="text/javascript">
				    $('#compressFlag').val('${mailPathSender.compressFlag}');
				 </script>
				 &nbsp;&nbsp;&nbsp;&nbsp;
                 邮件大小
				 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				 <select id="size" name="size" class="span2">
					<option value="2">2MB</option>
					<option value="5">5MB</option>
					<option value="10">10MB</option>
					<option value="15">15MB</option>
					<option value="20">20MB</option>
					<option value="30">30MB</option>
				 </select>&nbsp;&nbsp;&nbsp;&nbsp;(最大30兆)
				 <script type="text/javascript">
				    $('#size').val('${mailPathSender.size}');
				 </script>
				 </td>
			</tr>
			 
			<tr>
				 <td height="28">附件路径</td>
				 <td colspan="3">
				 <textarea  id="mailFilePath" name="mailFilePath" class="x-textarea" rows="5" cols="58" style="width:535px;height:80px;">${mailPathSender.mailFilePath}</textarea>
				 <br>（多个路径以,分隔）
				 </td>
			</tr>
		    
			<tr>
				 <td height="28">包含文件</td>
				 <td colspan="3">
                 <input id="includes" name="includes" class="span5 x-text" type="text"
				        value="${mailPathSender.includes}" size="80"
				 ></input>&nbsp;（多个文件扩展名以,分隔）
				 </td>
			</tr>
			<tr>
				 <td height="28">排除文件</td>
				 <td colspan="3">
                 <input id="excludes" name="excludes" class="span5 x-text" type="text"
				        value="${mailPathSender.excludes}" size="80"
				 ></input>&nbsp;（多个文件扩展名以,分隔）
				 </td>
			</tr>
			<tr>
				 <td height="28">邮件标题</td>
				 <td colspan="3">
                 <input id="textTitle" name="textTitle" class="span7 x-text" type="text"
				        value="${mailPathSender.textTitle}" size="80"
				 ></input>
				 </td>
			</tr>
			<tr>
				 <td height="28">邮件内容</td>
				 <td colspan="3">
				 <textarea  id="textContent" name="textContent" class="x-textarea" rows="5" cols="58" style="width:758px;height:380px;">${mailPathSender.textContent}</textarea> 
				 <br> （支持Freemarker模板语言）
				 </td>
			</tr>
			<tr>
				 <td height="28">邮件接收人</td>
				 <td colspan="3">
				 <textarea  id="mailRecipient" name="mailRecipient" class="x-textarea" rows="5" cols="58" style="width:715px;height:120px;">${mailPathSender.mailRecipient}</textarea>
				 <br>（多个邮件接收人以,分隔）
				 </td>
			</tr>
			 
	  <tr>
		<td colspan="4" align="center">
		<br><br>
		<input type="button" name="save" value=" 保 存 " class=" btn btn-primary" onclick="javascript:saveData();" />
		&nbsp;&nbsp;
		<input type="button" name="saveAs" value=" 另 存 " class=" btn" onclick="javascript:saveAsData();" />
		 
		&nbsp;&nbsp;
		<input type="button" name="sendMail" value=" 发送邮件 " class=" btn" onclick="javascript:sendTestMail();" />
		&nbsp;&nbsp;
		<input name="btn_back" type="button" value=" 返 回 " class=" btn" onclick="javascript:history.back(0);">
		</td>
	 </tr>
	</tbody>
   </table>
  </form>
  <br>
  <script type="text/javascript">
         initData();
  </script>
</body>
</html>