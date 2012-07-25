Element.hide('progressBar');
//Event.observe('fileUploadForm','submit',startProgress,false);
Event.observe('cancelUploadButton','click',cancelProgress,false);

//刷新上传状态
function refreshUploadStatus(){
	var ajaxW = new AjaxWrapper(false);
	ajaxW.putRequest(
		'./BackGroundService.action',
		'uploadStatus=',
		function(responseText){
				eval("uploadInfo = " + responseText);
				var progressPercent = Math.ceil(
					(uploadInfo.ReadTotalSize) / uploadInfo.UploadTotalSize * 100);
	
				$('progressBarText').innerHTML = ' 上传处理进度: '+progressPercent+'% ['+
					(uploadInfo.ReadTotalSize)+'/'+uploadInfo.UploadTotalSize + ' bytes]'+
					' <br>正在处理第'+uploadInfo.CurrentUploadFileNum+'个文件'+
					' 耗时: '+(uploadInfo.ProcessRunningTime-uploadInfo.ProcessStartTime)+' ms';
				//$('progressStatusText').innerHTML=' 反馈状态:123 '+uploadInfo.Status + "|||";
				$('totalProgressBarBoxContent').style.width = parseInt(progressPercent * 3.5) + 'px';
		}
	);
}
//上传处理
function startProgress(){
	Element.show('progressBar');
    $('progressBarText').innerHTML = ' 上传处理进度: 0%';
    //$('progressStatusText').innerHTML=' 反馈状态:';
    $('uploadButton').disabled = true;
		$('cancelUploadButton').disabled = false;
    var periodicalExe=new PeriodicalExecuter(refreshUploadStatus,0.5);
    return true;
}
//取消上传处理
function cancelProgress(){
	$('cancelUploadButton').disabled = true;
	var ajaxW = new AjaxWrapper(false);
	ajaxW.putRequest(
		'./BackGroundService.action',
		'cancelUpload=true',
		//因为form的提交，这可能不会执行
		function(responseText){
			eval("uploadInfo = " + responseText);
			//$('progressStatusText').innerHTML=' 反馈状态: '+uploadInfo.status;
			if (msgInfo.cancel=='true'){
				alert('删除成功!');
				window.location.reload();
			};
		}
	);
}
