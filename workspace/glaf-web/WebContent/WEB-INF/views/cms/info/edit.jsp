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

    KE.show({  id : 'content'
	           <c:if test="${(empty publicInfo) || (publicInfo.status <= 0)  }">
	           ,allowFileManager : true
	           ,imageUploadJson : '<%=request.getContextPath()%>/mx/uploadJson'
			   ,fileManagerJson : '<%=request.getContextPath()%>/mx/fileManagerJson'
			   </c:if>
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
					   location.href="<%=request.getContextPath()%>/mx/cms/info?serviceKey=${serviceKey}";
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


	function startProcess(){
		var rowId = jQuery('#id').val();
        jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/cms/info/startProcess?id='+rowId,
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

    function completeTask(isAgree){
        if(isAgree == 'false'){
			if(jQuery('#opinion').val() == ""){
				alert("请输入不通过的原因。");
				document.getElementById('#opinion').focus();
				return;
			}
		}
		var rowId = jQuery('#id').val();
        var opinion = jQuery('#opinion').val();
		var params = jQuery("#iForm").formSerialize();
        jQuery.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/mx/cms/info/completeTask?id='+rowId+'&isAgree='+isAgree,
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
				window.location.reload();
			}
		 });
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

            <c:if test="${not empty dataFiles}">
			<tr>
			  <td>已上传附件</td>
			  <td>
			    <c:forEach items="${dataFiles}" var="a">
				   <div id="div_${a.fileId}">
					 <div>
					 <span>${a.filename}</span>
					 <span>&nbsp;<a target="_blank" href="<%=request.getContextPath()%>/mx/lob/lob/download?fileId=${a.fileId}"><img src="<%=request.getContextPath()%>/images/download.gif" border="0">下载</a></span>
					 <c:if test="${(empty publicInfo) || (publicInfo.status <= 0)  }">
					 &nbsp;<span><a href="#" onclick="javascript:deleteFile('${a.fileId}');"><img src="<%=request.getContextPath()%>/images/delete.gif" border="0">删除</a></span>
					 </c:if>
					 </div>
				   </div>
				</c:forEach>
			  </td>
		    </tr>
		   </c:if>

           <c:if test="${(empty publicInfo) || (publicInfo.status <= 0)  }">
			<tr>
			  <td valign="middle">上传附件</td>
			  <td valign="middle">
			    <br>
			    <iframe src="<%=request.getContextPath()%>/myupload.do?method=showUpload&serviceKey=${serviceKey}&view=operamasksUpload"
				border="0" frameborder="0" width="680" height="240"></iframe>
			  </td>
		    </tr>
			</c:if>

            <c:if test="${publicInfo.processInstanceId != null && publicInfo.processInstanceId > 0 && publicInfo.status != -1 && publicInfo.wfStatus != 9999 }">
			<tr>
			  <td>审批意见</td>
			  <td>
				<input type="hidden" id="isAgree" name="isAgree" value="true">
				<textarea id="opinion" name="opinion" rows="7" cols="62" style="padding:5px;width:625px;height:150px;"></textarea>
			  </td>
			</tr>
			</c:if>
 
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
		<c:choose>
		  <c:when test="${publicInfo.status == 50}">
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
		  </c:when>
		  <c:when test="${publicInfo.status == 0 }">
             <input name="btn_submit" type="button" class="btn" value=" 提 交 "  
			        onclick="javascript:startProcess();">
		  </c:when>
		  <c:when test="${ publicInfo.status ==-1}">
             <input name="btn_resubmit" type="button" class="btn" value=" 重新提交 "  
			        onclick="javascript:completeTask('true');">
		  </c:when>
		  <c:when test="${ publicInfo.processInstanceId > 0 && publicInfo.wfStatus != 9999 }">
             <input name="btn_yes" type="button" class="btn btn-primary" value=" 通 过 "  
			        onclick="javascript:completeTask('true');">
		     <input name="btn_no"  type="button" class="btn" value=" 不通过 " 
			        onclick="javascript:completeTask('false');">
		  </c:when>
		  <c:otherwise>
		  
		  </c:otherwise>
		 </c:choose>
		 <input name="btn_back" type="button" value=" 返 回 " class=" btn" onclick="javascript:history.back(0);">
		</td>
	 </tr>
	</tbody>
   </table>
  </form>
  <br> 
  <br>
  <br>
 
</body>
</html>