//================================================================================================
//项目名称 ：    基盘
//功    能 ：    文件上传监听程序，刷新文件上传状态
//文件名称 ：    FileUploadListener.java                                   
//描    述 ：    
//================================================================================================
//修改履历                                                                								标识        
//年 月 日		区分			所 属/担 当           内 容									标识        
//----------   ----   -------------------- ---------------                          ------        
//2009/4/28   	编写   		Intasect/李闻海     新規作成                                                                            
//================================================================================================

package baseSrc.common.upload;

import org.apache.commons.fileupload.ProgressListener;
import javax.servlet.http.HttpServletRequest;

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

