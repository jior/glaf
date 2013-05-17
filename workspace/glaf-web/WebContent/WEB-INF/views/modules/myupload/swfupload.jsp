<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=request.getContextPath()%>">
<title>文件上传</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/swfupload/swfupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/swfupload/handlers.js"></script>

<script type="text/javascript">
    var contextPath = "<%=request.getContextPath()%>";
	var swfu;
	window.onload = function () {
		swfu = new SWFUpload({
					upload_url: "<%=request.getContextPath()%>/mx/myupload?method=upload&serviceKey=${serviceKey}",
					
					// File Upload Settings
					file_size_limit : "5000 MB",	// 20MB
					file_types : "*.*",//设置可上传的类型
					file_types_description : "所有文件",
					file_upload_limit : "100",
									
					file_queue_error_handler : fileQueueError,//选择文件后出错
					file_dialog_complete_handler : fileDialogComplete,//选择好文件后提交
					file_queued_handler : fileQueued,
					upload_progress_handler : uploadProgress,
					upload_error_handler : uploadError,
					upload_success_handler : uploadSuccess,
					upload_complete_handler : uploadComplete,
	
					// Button Settings
					button_image_url : "<%=request.getContextPath()%>/images/SmallSpyGlassWithTransperancy_17x18.png",
					button_placeholder_id : "spanButtonPlaceholder",
					button_width: 100,
					button_height: 18,
					button_text : '<span class="button">添加附件</span>',
					button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
					button_text_top_padding: 0,
					button_text_left_padding: 18,
					button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
					button_cursor: SWFUpload.CURSOR.HAND,
					
					// Flash Settings
					flash_url : "<%=request.getContextPath()%>/scripts/swfupload/swfupload.swf",
	
					custom_settings : {
						upload_target : "divFileProgressContainer"
					},
					// Debug Settings
					debug: false  //是否显示调试窗口
				});
			};

	function startUploadFile(){
		swfu.startUpload();
	}

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
<span id="spanButtonPlaceholder"></span>
<div id="divFileProgressContainer" style="width:200;display:none;"></div>
<div id="thumbnails">
<table id="infoTable" border="0" width="50%" style="border: solid 1px #7FAAFF; background-color: #C5D9FF; padding: 2px;margin-top:8px;">
</table>
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
</div>
</body>
</html>
