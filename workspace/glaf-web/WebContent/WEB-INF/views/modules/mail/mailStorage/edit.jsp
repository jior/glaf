<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邮件存储</title>
<%@ include file="/WEB-INF/views/tm/header.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

	function saveFormData(){
	  var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/mail/mailStorage/save',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   alert('操作成功！');
					   location.href="<%=request.getContextPath()%>/mx/mail/mailStorage";
				   }
			 });
	}

	 </script>
</head>
<body>

	<div class="content-block" style="width: 545px;"><br>

	<div class="x_content_title">
    <img src="<%=request.getContextPath()%>/images/window.png" alt="邮件存储">
	&nbsp;邮件存储
    </div>
	<br>
    <fieldset class="x-fieldset" style="width: 96%;">
	 <form id="iForm" name="iForm" method="post">
	     <input type="hidden" id="id" name="id"/>
	     <input type="hidden" id="storageId" name="storageId"/>
	    <table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
		<tbody>
			<tr>
				 <td>主题</td>
				 <td>
                 <input id="subject" name="subject" required="true" size="50" class="x-text input-xlarge " type="text"
				 ></input>
				 </td>
			</tr>
			<tr>
				 <td>存储类型</td>
				 <td>
                 <select id="storageType" name="storageType">
					<option value="0" selected="selected">关系型数据库</option>
					<!-- <option value="1">MongoDB</option>
					<option value="2">Cassandra</option>
					<option value="3">HBase</option> -->
                 </select>
				 </td>
			</tr>
			<tr>
				 <td>使用状态</td>
				 <td>
				       <select id="status" name="status">
						<option value="0" selected="selected">使用中</option>
						<option value="1">存储空间满</option>
				       </select>
				 </td>
			</tr>
			<tr>
				 <td colspan="4" align="center">
				     <br />
				     <input type="button" name="save" class="button btn btn-primary" value="保存" onclick="javascript:saveFormData();" />
					 <input type="button" name="back" value="返回" class="button btn" onclick="javascript:history.back();" />
				 </td>
			</tr>
		</tbody>
	</table>
  </form>
  </fieldset>
  </div>
  <c:if test="${not empty storageId}">
  <script type="text/javascript">
         initData();
  </script>
  </c:if>
</body>
</html>