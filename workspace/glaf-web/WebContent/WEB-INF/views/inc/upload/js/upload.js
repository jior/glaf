Element.hide('progressBar');
//Event.observe('fileUploadForm','submit',startProgress,false);
Event.observe('cancelUploadButton','click',cancelProgress,false);

//ˢ���ϴ�״̬
function refreshUploadStatus(){
	var ajaxW = new AjaxWrapper(false);
	ajaxW.putRequest(
		'./BackGroundService.action',
		'uploadStatus=',
		function(responseText){
				eval("uploadInfo = " + responseText);
				var progressPercent = Math.ceil(
					(uploadInfo.ReadTotalSize) / uploadInfo.UploadTotalSize * 100);
	
				$('progressBarText').innerHTML = ' �ϴ��������: '+progressPercent+'% ['+
					(uploadInfo.ReadTotalSize)+'/'+uploadInfo.UploadTotalSize + ' bytes]'+
					' <br>���ڴ����'+uploadInfo.CurrentUploadFileNum+'���ļ�'+
					' ��ʱ: '+(uploadInfo.ProcessRunningTime-uploadInfo.ProcessStartTime)+' ms';
				//$('progressStatusText').innerHTML=' ����״̬:123 '+uploadInfo.Status + "|||";
				$('totalProgressBarBoxContent').style.width = parseInt(progressPercent * 3.5) + 'px';
		}
	);
}
//�ϴ�����
function startProgress(){
	Element.show('progressBar');
    $('progressBarText').innerHTML = ' �ϴ��������: 0%';
    //$('progressStatusText').innerHTML=' ����״̬:';
    $('uploadButton').disabled = true;
		$('cancelUploadButton').disabled = false;
    var periodicalExe=new PeriodicalExecuter(refreshUploadStatus,0.5);
    return true;
}
//ȡ���ϴ�����
function cancelProgress(){
	$('cancelUploadButton').disabled = true;
	var ajaxW = new AjaxWrapper(false);
	ajaxW.putRequest(
		'./BackGroundService.action',
		'cancelUpload=true',
		//��Ϊform���ύ������ܲ���ִ��
		function(responseText){
			eval("uploadInfo = " + responseText);
			//$('progressStatusText').innerHTML=' ����״̬: '+uploadInfo.status;
			if (msgInfo.cancel=='true'){
				alert('ɾ���ɹ�!');
				window.location.reload();
			};
		}
	);
}
