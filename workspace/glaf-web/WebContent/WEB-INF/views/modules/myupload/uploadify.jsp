<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">  
<title>文件上传</title>
<link href="<%=request.getContextPath()%>/scripts/dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="<%=request.getContextPath()%>/scripts/dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="<%=request.getContextPath()%>/scripts/dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="<%=request.getContextPath()%>/scripts/dwz/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="<%=request.getContextPath()%>/scripts/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>

<style type="text/css" media="screen">
.my-uploadify-button {
	background:none;
	border: none;
	text-shadow: none;
	border-radius:0;
}

.uploadify:hover .my-uploadify-button {
	background:none;
	border: none;
}

.fileQueue {
	width: 400px;
	height: 150px;
	overflow: auto;
	border: 1px solid #E5E5E5;
	margin-bottom: 10px;
}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dwz/speedup.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.bgiframe.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dwz/dwz.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dwz/dwz.regional.zh.js"></script>
<script type="text/javascript">
 function deleteFile(fileId){
		if(confirm("确定删除文件吗？")){
          jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/lob/lob/delete?fileId='+fileId,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   alert('操作成功！');
                       removeElement(document.getElementById("div_"+fileId));
				   }
			 });
		}
	}

	function removeElement(_element){
         var _parentElement = _element.parentNode;
         if(_parentElement){
                _parentElement.removeChild(_element);
         }
    }
</script>
</head>
<body style="font-size: 12px;">
  <div class="pageContent" style="margin: 0 10px" layoutH="50">
	<input id="testFileInput" type="file" name="image" 
		uploaderOption="{
			swf:'<%=request.getContextPath()%>/scripts/uploadify/uploadify.swf',
			uploader:'<%=request.getContextPath()%>/mx/myupload?method=upload&serviceKey=${serviceKey}',
			formData:{aa:'xxx', bb:1},
			buttonText:'请选择文件',
			fileSizeLimit:'2000KB',
			fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
			fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
			auto:true,
			multi:true,
			method:'post',
			onUploadSuccess:uploadifySuccess,
			onQueueComplete:uploadifyQueueComplete
		}"
	/>

	<div class="divider"></div>

	<input id="testFileInput2" type="file" name="image2" 
		uploaderOption="{
			swf:'<%=request.getContextPath()%>/scripts/uploadify/uploadify.swf',
			uploader:'<%=request.getContextPath()%>/mx/myupload?method=upload&serviceKey=${serviceKey}',
			formData:{aa:'xxx', bb:1},
			queueID:'fileQueue',
			buttonImage:'<%=request.getContextPath()%>/scripts/uploadify/img/add.jpg',
			buttonClass:'my-uploadify-button',
			method:'post',
			width:102,
			auto:false
		}"
	/>
	
	<div id="fileQueue" class="fileQueue"></div>
	
	<input type="image" src="<%=request.getContextPath()%>/scripts/uploadify/img/upload.jpg" onclick="$('#testFileInput2').uploadify('upload', '*');"/>
	<input type="image" src="<%=request.getContextPath()%>/scripts/uploadify/img/cancel.jpg" onclick="$('#testFileInput2').uploadify('cancel', '*');"/>

    <iframe id="newFrame" name="newFrame" width="0" height="0"></iframe>
	<div class="divider"></div>
	<div id="response" >
	  <c:forEach items="${dataFiles}" var="a">
	   <div id="div_${a.fileId}">
	     <div>
         <span>文件 ${a.filename}</span>
         <span>&nbsp;<a target="newFrame" href="<%=request.getContextPath()%>/mx/lob/lob/download?fileId=${a.fileId}">下载</a></span>
		 <span>&nbsp;<span><a href="#" onclick="javascript:deleteFile('${a.fileId}');">删除</a></span>
		 </div>
	   </div>
	  </c:forEach>
	</div>
</div>	
</body>
</html>
