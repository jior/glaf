<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String serviceUrl = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
				    + request.getContextPath();
	String callbackUrl = serviceUrl+"/rs/mail/receive/view";
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>邮件任务</title>
	<%@ include file="/WEB-INF/views/tm/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/scripts/kindeditor/kindeditor-min.js"></script>

	 <script type="text/javascript">

	 
           KE.show({  id : 'content',
	               allowFileManager : true,
	               imageUploadJson : '<%=request.getContextPath()%>/mx/uploadJson',
			       fileManagerJson : '<%=request.getContextPath()%>/mx/fileManagerJson'
                });

			function initData(){
			    $('#iForm').form('load','<%=request.getContextPath()%>/rs/mail/mailTask/view/${taskId}');
			}

			function saveData(){
				   document.getElementById("content").value=KE.html('content');
			       var params = jQuery("#iForm").formSerialize();
				   //alert(params);
				    jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/mail/mailTask/save',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   alert('操作成功！');
					   location.href="<%=request.getContextPath()%>/mx/mail/mailTask";
				   }
			 });
			}

	 </script>
</head>
<body>
<div class="content-block" style="width: 845px;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="邮件任务">&nbsp;邮件任务</div>
<br>
	 <form id="iForm" name="iForm" method="post">
	     <input type="hidden" id="id" name="id"/>
	     <input type="hidden" id="taskId" name="taskId"/>
	     <table class="easyui-form" style="width:700px;height:250px">
		<tbody>
			<tr>
				 <td>主题</td>
				 <td>
                 <input id="subject" name="subject" required="true" class="x-text input-xlarge easyui-validatebox" type="text"
				 size="50"></input>
				 </td>
			</tr>
			<tr>
				 <td>回调地址</td>
				 <td>
                 <input id="callbackUrl" name="callbackUrl" class="x-text input-xlarge easyui-validatebox" type="text"
				 size="50" value="<%=callbackUrl%>"></input>
				 </td>
			</tr>
			<tr>
				 <td>数据表</td>
				 <td>
                    <select id="storageId" name="storageId">
						<c:forEach items="${rows}" var="a">
                                <option value="${a.id}">${a.subject}</option>
						</c:forEach>
                    </select>
				 </td>
			</tr>
			<!-- <tr>
				 <td>线程大小</td>
				 <td>
				 <input id="threadSize" name="threadSize" class="easyui-numberspinner" value="0" 
				            increment="2" max="20" style="width:120px;" ></input>
				 </td>
			</tr> -->
			<tr>
				 <td>时间间隔</td>
				 <td>
				 <input id="delayTime" name="delayTime" class="x-text easyui-numberspinner" value="0" 
				         required="true"   min="1" increment="10" style="width:120px;" ></input>(秒)
				 </td>
			</tr>
			<tr>
				 <td>开始时间</td>
				 <td>
				  <input id="startDate" name="startDate" class="x-text easyui-datetimebox"
				         required="true"    ></input>
				 </td>
			</tr>
			<tr>
				 <td>结束时间</td>
				 <td>
				  <input id="endDate" name="endDate" class="x-text easyui-datetimebox"
				        required="true"  ></input>
				 </td>
			</tr>
			<tr>
				 <td>是否HTML</td>
				 <td>
				 <select id="isHtml" name="isHtml">
					<option value="true" >是</option>
					<option value="false">否</option>
				  </select>
				 </td>
			</tr>
			<tr>
				 <td>包含回执</td>
				 <td>
				 <select id="isBack" name="isBack">
					<option value="true" >是</option>
					<option value="false">否</option>
				  </select>
				 </td>
			</tr>
			<tr>
				 <td>启用退订</td>
				 <td>
				 <select id="isUnSubscribe" name="isUnSubscribe">
					<option value="true" >启用</option>
					<option value="false">不启用</option>
				  </select>
				 </td>
			</tr>
			<tr>
				 <td>是否启用</td>
				 <td>
				  <select id="locked" name="locked">
					<option value="0" >启用</option>
					<option value="1">锁定</option>
				  </select>
				 </td>
			</tr>
			<tr>
				<td colspan="2">
				<div id="x-content" align="left" style="padding-left: 20px;"><textarea
					id="content" name="content" rows="8" cols="100"  class="x-textarea"
					style="width: 715px; height: 450px; text-align: left;"><c:out
					value="${mailTask.content}" escapeXml="false" /></textarea></div>
				</td>
			</tr>
			<tr>
				 <td colspan="4" align="center">
				     <br />
				     <input type="button" name="save" value="保存" class="button btn" onclick="javascript:saveData();" />
					 <input type="button" name="back" value="返回" class="button btn" onclick="javascript:history.back();" />
				 </td>
			</tr>
		</tbody>
	</table>
  </form>
 
  </div>
  <c:if test="${not empty taskId}">
  <script type="text/javascript">
         initData();
  </script>
  </c:if>
</body>
</html>