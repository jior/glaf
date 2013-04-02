<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=request.getContextPath()%>">
<title>文件上传</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/swfupload/swfupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/swfupload/handlers.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<body style="font-size: 12px;">
<script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
			var swfu;
			window.onload = function () {
				swfu = new SWFUpload({
					upload_url: "<%=request.getContextPath()%>/mx/myupload?method=upload&serviceKey=${serviceKey}&resourceId=${resourceId}",
					
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

		</script>
		<!--  -->
		<span id="spanButtonPlaceholder"></span>
		  <div id="divFileProgressContainer" style="width:200;display:none;"></div>
		<div id="thumbnails">
			<table id="infoTable" border="0" width="50%" style="border: solid 1px #7FAAFF; background-color: #C5D9FF; padding: 2px;margin-top:8px;">
			</table>
		</div>
  </body>
</html>
