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

import java.util.*;
 
public class FileUploadStatus {
	// 上传base目录
	private String baseDir = "";
	// 取消上传
	private boolean cancel = false;
	// 当前上传文件号
	private int currentUploadFileNum = 0;
	// 处理终止时间
	private long processEndTime = 0l;
	// 处理执行时间
	private long processRunningTime = 0l;
	// 处理起始时间
	private long processStartTime = 0l;
	// 读取上传总量
	private long readTotalSize = 0;
	// 状态
	private String status = "";
	// 成功读取上传文件数
	private int successUploadFileCount = 0;
	// 上传用户地址
	private String uploadAddr;
	// 上传文件URL列表
	private List<FileInfo> uploadFileUrlList = new ArrayList<FileInfo>();
	// 上传总量
	private long uploadTotalSize = 0;

	public FileUploadStatus() {

	}

	public String getBaseDir() {
		return baseDir;
	}

	public boolean getCancel() {
		return cancel;
	}

	public int getCurrentUploadFileNum() {
		return currentUploadFileNum;
	}

	public long getProcessEndTime() {
		return processEndTime;
	}

	public long getProcessRunningTime() {
		return processRunningTime;
	}

	public long getProcessStartTime() {
		return processStartTime;
	}

	public long getReadTotalSize() {
		return readTotalSize;
	}

	public String getStatus() {
		return status;
	}

	public int getSuccessUploadFileCount() {
		return successUploadFileCount;
	}

	public String getUploadAddr() {
		return uploadAddr;
	}

	public List<FileInfo> getUploadFileUrlList() {
		return uploadFileUrlList;
	}

	public long getUploadTotalSize() {
		return uploadTotalSize;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public void setCurrentUploadFileNum(int currentUploadFileNum) {
		this.currentUploadFileNum = currentUploadFileNum;
	}

	public void setProcessEndTime(long processEndTime) {
		this.processEndTime = processEndTime;
	}

	public void setProcessRunningTime(long processRunningTime) {
		this.processRunningTime = processRunningTime;
	}

	public void setProcessStartTime(long processStartTime) {
		this.processStartTime = processStartTime;
	}

	public void setReadTotalSize(long readTotalSize) {
		this.readTotalSize = readTotalSize;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSuccessUploadFileCount(int successUploadFileCount) {
		this.successUploadFileCount = successUploadFileCount;
	}

	public void setUploadAddr(String uploadAddr) {
		this.uploadAddr = uploadAddr;
	}

	public void setUploadFileUrlList(List<FileInfo> uploadFileUrlList) {
		this.uploadFileUrlList = uploadFileUrlList;
	}

	public void setUploadTotalSize(long uploadTotalSize) {
		this.uploadTotalSize = uploadTotalSize;
	}

	public String toJson() {
		StringBuffer strJSon = new StringBuffer();
		strJSon.append("{UploadTotalSize:").append(getUploadTotalSize())
				.append(",").append("ReadTotalSize:")
				.append(getReadTotalSize()).append(",")
				.append("CurrentUploadFileNum:")
				.append(getCurrentUploadFileNum()).append(",")
				.append("SuccessUploadFileCount:")
				.append(getSuccessUploadFileCount()).append(",")
				.append("Status:'").append(getStatus()).append("',")
				.append("ProcessStartTime:").append(getProcessStartTime())
				.append(",").append("ProcessEndTime:")
				.append(getProcessEndTime()).append(",")
				.append("ProcessRunningTime:").append(getProcessRunningTime())
				.append(",").append("Cancel:").append(getCancel()).append("}");
		return strJSon.toString();
	}
}
