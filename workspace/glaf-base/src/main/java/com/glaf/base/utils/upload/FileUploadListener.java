package com.glaf.base.utils.upload;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.ProgressListener;

public class FileUploadListener implements ProgressListener {
	private HttpServletRequest request = null;

	public FileUploadListener(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 更新状态
	 */
	public void update(long pBytesRead, long pContentLength, int pItems) {
		FileUploadStatus statusBean = BackGroundService.getStatusBean(request);
		statusBean.setUploadTotalSize(pContentLength);
		// 读取完成
		if (pContentLength == -1) {
			statusBean.setStatus("完成对" + pItems + "个文件的读取:读取了 " + pBytesRead
					+ " bytes.");
			statusBean.setReadTotalSize(pBytesRead);
			statusBean.setSuccessUploadFileCount(pItems);
			statusBean.setProcessEndTime(System.currentTimeMillis());
			statusBean.setProcessRunningTime(statusBean.getProcessEndTime());
			// 读取中
		} else {
			statusBean.setStatus("当前正在处理第" + pItems + "个文件:已经读取了 " + pBytesRead
					+ " / " + pContentLength + " bytes.");
			statusBean.setReadTotalSize(pBytesRead);
			statusBean.setCurrentUploadFileNum(pItems);
			statusBean.setProcessRunningTime(System.currentTimeMillis());
		}
		BackGroundService.saveStatusBean(request, statusBean);
	}
}
