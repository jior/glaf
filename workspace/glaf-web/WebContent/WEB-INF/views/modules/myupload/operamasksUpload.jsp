<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/operamasksui/themes/default/om-default.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/operamasksui/js/om-core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/operamasksui/js/om-fileupload.js"></script>
<script type="text/javascript">
	function praseDate(date){
		return date.fullYear + '-' + date.month + '-' + date.day + ' ' + date.hours + ':' + date.minutes + ':' + date.seconds;
	}

    $(document).ready(function() {
	  $('#file_upload').omFileUpload({
	    action : '<%=request.getContextPath()%>/mx/myupload?method=upload&responseType=json&serviceKey=${serviceKey}',
	    swf : '<%=request.getContextPath()%>/scripts/operamasksui/swf/om-fileupload.swf',
		//buttonImg : '<%=request.getContextPath()%>/images/upload.png',
	    multi  : true,
	    queueSizeLimit : 50,
		sizeLimit : 102400000,
		//fileExt : '*.jpg;*.png;*.gif',
	  	//fileDesc : 'Image Files'
	    onComplete : function(ID, fileObj, response, data, event) {
	    	var jsonData = eval("("+response+")");
			//alert(jsonData);
	    	$('#response').append('<div><div id="div_'+jsonData.fileId+'">')
	    				  .append('<span>文件' + fileObj.name + '</span>')
	    				  .append('<span>&nbsp;<a target="newFrame" href="<%=request.getContextPath()%>/mx/lob/lob/download?fileId='+jsonData.fileId+'">点击下载</a></span>')
				          .append('&nbsp;<span><a href="#" onclick="javascript:deleteFile(\''+jsonData.fileId+'\');">删除</a></span>')
	    				  .append('<hr/><br/></div></div>');

	     } 
	  });
	});  
	
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
	
	function uploadFiles(){
       $('#file_upload').omFileUpload({'actionData':{'responseType':'json','serviceKey':'${serviceKey}'}});
	   $('#file_upload').omFileUpload('upload');
	}
</script>
</head>
<body style="font-size: 12px;margin:5px;">
    <input id="file_upload" name="file_upload" type="file" /><br /> 
    <button value="上传所有"  onclick="javascript:uploadFiles();" class="button">上传所有</button>
    &nbsp;&nbsp;
    <button value="取消所有" onclick="$('#file_upload').omFileUpload('cancel')" class="button">取消所有</button>
	<br />
	<br />
	<iframe id="newFrame" name="newFrame" width="0" height="0"></iframe>
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
</body>
</html>
