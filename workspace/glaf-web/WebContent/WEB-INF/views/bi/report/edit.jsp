<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表定义</title>
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/scripts/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript">

           KE.show({  id : 'textContent',
	               allowFileManager : true
           });

			function initData(){
			   // $('#iForm').form('load','<%=request.getContextPath()%>/rs/bi/report/view/${rowId}');
			}

			function saveData(){
				   document.getElementById("textContent").value=KE.html('textContent');
			       var params = jQuery("#iForm").formSerialize();
				   jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/bi/report/saveReport',
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
				   document.getElementById("reportId").value="";
				   document.getElementById("textContent").value=KE.html('textContent');
			       var params = jQuery("#iForm").formSerialize();
				   jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/bi/report/saveReport',
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

		function createReport(){
			 window.open('<%=request.getContextPath()%>/mx/bi/report/createReport?reportId=${report.id}');
		}

		function sendTestMail(){
            jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/bi/report/sendMail?reportId=${report.id}',
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

        function openQx(){
                var link = '<%=request.getContextPath()%>/mx/bi/report/chooseQuery?reportId=${report.id}&elementId=queryIds&elementName=queryNames';
				var x=100;
				var y=100;
				if(is_ie) {
					x=document.body.scrollLeft+event.clientX-event.offsetX-200;
					y=document.body.scrollTop+event.clientY-event.offsetY-200;
				 }
				openWindow(link,self,x, y, 695, 480);
			}

        function openChart(){
                var link = '<%=request.getContextPath()%>/mx/bi/report/chooseChart?reportId=${report.id}&elementId=chartIds&elementName=chartNames';
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
<body><br>
	<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="报表定义"> &nbsp;报表定义
	</div>
	 <form id="iForm" name="iForm" method="post">
	    <input type="hidden" id="id" name="id" value="${report.id}"/>
	    <input type="hidden" id="reportId" name="reportId" value="${report.id}"/>
	    <table class="easyui-form" style="width:920px;" align="center">
		<tbody>
			 
			<tr>
				 <td width="180">名称</td>
				 <td >
                 <input id="name" name="name" class="span7 x-text" type="text"
				        value="${report.name}" size="80"
				 ></input>
				 </td>
			</tr>
			<tr>
				 <td>标题</td>
				 <td >
                 <input id="subject" name="subject" class="span7 x-text" type="text"
				        value="${report.subject}" size="80"
				 ></input>
				 </td>
			</tr>
			
			<tr>
				 <td>报表名称</td>
				 <td >
                 <input id="reportName" name="reportName" class="span7 x-text" type="text"
				        value="${report.reportName}" size="80"
				 ></input>
				 </td>
			</tr>
			<tr>
				 <td>模板文件</td>
				 <td >
                 <input id="reportTemplate" name="reportTemplate" class="span7 x-text" type="text"
				        value="${report.reportTemplate}" size="80"
				 ></input>
				 </td>
			</tr>
			<tr>
				 <td>报表标题日期</td>
				 <td >
                 <input id="reportTitleDate" name="reportTitleDate" class="span7 x-text" type="text"
				        value="${report.reportTitleDate}" size="80"
				 ></input>
				 </td>
			</tr>
			<tr>
				 <td>报表年月</td>
				 <td >
                 <input id="reportMonth" name="reportMonth" class="span7 x-text" type="text"
				        value="${report.reportMonth}" size="80"
				 ></input>
				 </td>
			</tr>
			<tr>
				 <td>报表年月日参数</td>
				 <td >
                 <input id="reportDateYYYYMMDD" name="reportDateYYYYMMDD" class="span7 x-text" type="text"
				        value="${report.reportDateYYYYMMDD}" size="80"
				 ></input>
				 </td>
			</tr>
			<tr>
				 <td>报表类型</td>
				 <td >
				 <select id="type" name="type" class="span2">
					<option value="jasper">JasperReport</option>
					<option value="jxls">JXLS</option>
				 </select>
				 <script type="text/javascript">
				    $('#type').val('${report.type}');
				 </script>
				  &nbsp;&nbsp;&nbsp;&nbsp;生成格式&nbsp; 
				 <select id="reportFormat" name="reportFormat" class="span2">
					<option value="pdf">PDF</option>
					<option value="xls">EXCEL</option>
				 </select>
				 <script type="text/javascript">
				    $('#reportFormat').val('${report.reportFormat}');
				 </script>
			</tr>

			<tr>
				 <td>是否启用</td>
				 <td >
				 <select id="enableFlag" name="enableFlag" class="span2">
					<option value="1">启用</option>
					<option value="0">不启用</option>
				 </select>
				 <script type="text/javascript">
				    $('#enableFlag').val('${report.enableFlag}');
				 </script>
				 &nbsp;&nbsp;&nbsp;&nbsp;
                 cron表达式&nbsp;&nbsp; 
				 <input type="text" id="cronExpression" name="cronExpression" value="${report.cronExpression}" size="20"
				        class="x-text span2"> 
				 &nbsp;&nbsp;
				 (可以参考<a href="<%=request.getContextPath()%>/quartz.txt" target="_blank">quartz</a>文件)
				 </td>
			</tr>

			<tr>
				 <td>邮件标题</td>
				 <td >
                 <input id="textTitle" name="textTitle" class="span7 x-text" type="text"
				        value="${report.textTitle}" size="80"
				 ></input>
				 </td>
			</tr>
			<tr>
				 <td>邮件模板</td>
				 <td >
				 <textarea  id="textContent" name="textContent" class="x-textarea"  rows="5" cols="58" style="width:715px;height:480px;">${report.textContent}</textarea> 
				 <br> （支持Freemarker模板语言）
				 </td>
			</tr>
			<tr>
				 <td>邮件接收人</td>
				 <td >
				 <textarea  id="mailRecipient" name="mailRecipient" class="x-textarea" rows="5" cols="58" style="width:535px;height:90px;">${report.mailRecipient}</textarea>
				 <br>（多个邮件接收人以,分隔）
				 </td>
			</tr>
			<tr>
				 <td>JSON格式参数</td>
				 <td >
				 <textarea  id="jsonParameter" name="jsonParameter" class="x-textarea" rows="5" cols="58" style="width:535px;height:90px;">${report.jsonParameter}</textarea>
				 </td>
			</tr>
			<tr>
				 <td>组合查询</td>
                 <td >
				   <input type="hidden" id="queryIds" name="queryIds" value="${report.queryIds}">
				   <textarea type="textarea" id="queryNames" name="queryNames" value="${queryNames}"
				          readonly="true"    onclick="javascript:openQx();" style="width:535px;height:90px;">${queryNames}</textarea>
						  &nbsp;
						  <a href="#" onclick="javascript:openQx();">
						  <img src="<%=request.getContextPath()%>/images/search_results.gif" border="0"
						       title="如果图表需要多个查询数据集组成一个图表，请先建好查询数据再选择。">
						  </a>
				 </td>
			</tr>
			<tr>
				 <td>相关图表</td>
                 <td >
				   <input type="hidden" id="chartIds" name="chartIds" value="${report.chartIds}">
				   <textarea type="textarea" id="chartNames" name="chartNames" value="${chartNames}"
				          readonly="true"  onclick="javascript:openChart();"  
						  style="width:535px;height:90px;">${chartNames}</textarea>
						  &nbsp;
						  <a href="#" onclick="javascript:openChart();">
						  <img src="<%=request.getContextPath()%>/images/process.gif" border="0">
						  </a>
				 </td>
			</tr>
			<tr>
			 <td>说明</td>
			 <td >
			 <br><%out.println("${curr_yyyymmdd}");%>代表当天的日期，如<%=com.glaf.core.config.SystemConfig.getCurrentYYYYMMDD()%>
                 <br><%out.println("${curr_yyyymm}");%>代表当天的月份，如<%=com.glaf.core.config.SystemConfig.getCurrentYYYYMM()%>
				 <br><%out.println("${input_yyyymmdd}");%>代表报表输入的日期，如<%=com.glaf.core.config.SystemConfig.getInputYYYYMMDD()%>
                 <br><%out.println("${input_yyyymm}");%>代表报表输入的月份，如<%=com.glaf.core.config.SystemConfig.getInputYYYYMM()%>
			</td>
	  <tr>
		<td colspan="4" align="center">
		<br><br>
		<input type="button" name="save" value=" 保 存 " class=" btn btn-primary" onclick="javascript:saveData();" />
		&nbsp;&nbsp;
		<input type="button" name="saveAs" value=" 另 存 " class=" btn" onclick="javascript:saveAsData();" />
		&nbsp;&nbsp;
		<input type="button" name="createRpt" value=" 生成报表 " class=" btn" onclick="javascript:createReport();" />
		&nbsp;&nbsp;
		<input type="button" name="sendMail" value=" 发送邮件 " class=" btn" onclick="javascript:sendTestMail();" />
		&nbsp;&nbsp;
		<input name="btn_back" type="button" value=" 返 回 " class=" btn" onclick="javascript:history.back(0);">
		</td>
	 </tr>
	</tbody>
   </table>
  </form>
  <br> <br> <br>
  <script type="text/javascript">
         initData();
  </script>
</body>
</html>