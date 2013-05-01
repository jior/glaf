<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${treeModel.name}信息</title>
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript">

        KE.show({  id : 'content',
	               allowFileManager : true,
	               imageUploadJson : '<%=request.getContextPath()%>/mx/uploadJson',
			       fileManagerJson : '<%=request.getContextPath()%>/mx/fileManagerJson'
        });

		 
		function saveData(){
			document.getElementById("content").value=KE.html('content');
			var params = jQuery("#iForm").formSerialize();
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/cms/info/savePublicInfo',
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

		function publishXY(publishFlag){
		   var msg="";
           if(publishFlag==1){
			   msg="确定要发布该条信息吗？";
		   } else {
               msg="确定取消发布该条信息吗？";
		   }
		   if(confirm(msg)){
               jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/cms/info/publish?id=${publicInfo.id}&publishFlag='+publishFlag,
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
					   window.location.reload();
				   }
			});
		   }
		}

</script>
</head>
<body><br>
	<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="编辑${treeModel.name}信息"> &nbsp;编辑${treeModel.name}信息
	</div>
	 <form id="iForm" name="iForm" method="post">
	    <input type="hidden" id="id" name="id" value="${publicInfo.id}"/>
	    <input type="hidden" id="nodeId" name="nodeId" value="${nodeId}"/>
		<input type="hidden" id="serviceKey" name="serviceKey" value="${serviceKey}"/>
	    <table class="easyui-form" style="width:920px;" align="center">
		<tbody>
			 
			<tr>
				 <td>主题</td>
				 <td >
                 <input id="subject" name="subject" class="span7 x-text" type="text"
				        value="${publicInfo.subject}" size="80"
				 ></input>
				 </td>
			</tr>

			<tr>
				 <td>关键字</td>
				 <td >
                 <input id="keywords" name="keywords" class="span7 x-text" type="text"
				        value="${publicInfo.keywords}" size="80"
				 ></input>
				 </td>
			</tr>

			<tr>
				 <td>摘要</td>
				 <td >
                 <input id="summary" name="summary" class="span7 x-text" type="text"
				        value="${publicInfo.summary}" size="80"
				 ></input>
				 </td>
			</tr>

			<tr>
				 <td>Tag</td>
				 <td >
                 <input id="tag" name="tag" class="span7 x-text" type="text"
				        value="${publicInfo.tag}" size="80"
				 ></input>(多个tag用空格分隔)
				 </td>
			</tr>

			<tr>
				 <td>作者</td>
				 <td >
                 <input id="author" name="author" class="span7 x-text" type="text"
				        value="${publicInfo.author}" size="80"
				 ></input>
				 </td>
			</tr>

			<tr>
				 <td>发布单位</td>
				 <td >
                 <input id="unitName" name="unitName" class="span7 x-text" type="text"
				        value="${publicInfo.unitName}" size="80"
				 ></input>
				 </td>
			</tr>

 
			<tr>
				 <td>内容</td>
				 <td >
				 <textarea  id="content" name="content" class="x-textarea"  rows="5" cols="58" style="width:715px;height:580px;">${publicInfo.content}</textarea> 
				 <br>  
				 </td>
			</tr>
 
	  <tr>
		<td colspan="4" align="center">
		<br><br>
		<c:choose>
		   <c:when test="${empty publicInfo}">
		      <input type="button" name="save" value=" 保 存 " class=" btn btn-primary" onclick="javascript:saveData();" />
		   </c:when>
		   <c:when test="${publicInfo.status <= 0 }">
		      <input type="button" name="save" value=" 保 存 " class=" btn btn-primary" onclick="javascript:saveData();" />
		   </c:when>
		   <c:otherwise>
		   </c:otherwise>
		</c:choose>
		
		&nbsp;&nbsp;
		<c:if test="${publicInfo.status == 50}">
		    <c:choose>
			<c:when test="${publicInfo.publishFlag==1}">
			  <input type="button" name="save" value=" 取消发布 " class=" btn btn-primary" 
				onclick="javascript:publishXY(0);" />
			</c:when>
			<c:otherwise>
			  <input type="button" name="save" value=" 发 布 " class=" btn btn-primary" 
				onclick="javascript:publishXY(1);" />
			</c:otherwise>
			</c:choose>
		</c:if>
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