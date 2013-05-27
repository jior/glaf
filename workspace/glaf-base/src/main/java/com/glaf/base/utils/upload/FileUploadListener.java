/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glaf.base.utils.upload;

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
		FileUploadStatus statusBean = FileMgmtFactory.getStatusBean(request);
		statusBean.setUploadTotalSize(pContentLength);
		// 读取完成
		if (pContentLength == -1) {
			statusBean.setStatus("完成对" + pItems + "个文件的读取,读取了 " + pBytesRead
					+ " bytes.");
			statusBean.setReadTotalSize(pBytesRead);
			statusBean.setSuccessUploadFileCount(pItems);
			statusBean.setProcessEndTime(System.currentTimeMillis());
			statusBean.setProcessRunningTime(statusBean.getProcessEndTime());
			// 读取中
		} else {
			statusBean.setStatus("当前正在处理第" + pItems + "个文件,已经读取了 " + pBytesRead
					+ " / " + pContentLength + " bytes.");
			statusBean.setReadTotalSize(pBytesRead);
			statusBean.setCurrentUploadFileNum(pItems);
			statusBean.setProcessRunningTime(System.currentTimeMillis());
		}
		FileMgmtFactory.saveStatusBean(request, statusBean);
	}
}
