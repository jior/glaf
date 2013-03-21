/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.glaf.base.utils.upload;

import java.util.ArrayList;
import java.util.List;

public class FileUploadStatus {
    //�ϴ��û���ַ
    private String uploadAddr;
    //�ϴ�����
    private long uploadTotalSize = 0;
    //��ȡ�ϴ�����
    private long readTotalSize = 0;
    //��ǰ�ϴ��ļ���
    private int currentUploadFileNum = 0;
    //�ɹ���ȡ�ϴ��ļ���
    private int successUploadFileCount = 0;
    //״̬
    private String status = "";
    //������ʼʱ��
    private long processStartTime = 0l;
    //������ֹʱ��
    private long processEndTime = 0l;
    //����ִ��ʱ��
    private long processRunningTime = 0l;
    //�ϴ��ļ�URL�б�
    private List<String> uploadFileUrlList = new ArrayList<String>();
    //ȡ���ϴ�
    private boolean cancel = false;
    //�ϴ�baseĿ¼
    private String baseDir = "";

    public FileUploadStatus() {

    }

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public boolean getCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public List<String> getUploadFileUrlList() {
        return uploadFileUrlList;
    }

    public void setUploadFileUrlList(List<String> uploadFileUrlList) {
        this.uploadFileUrlList = uploadFileUrlList;
    }

    public long getProcessRunningTime() {
        return processRunningTime;
    }

    public void setProcessRunningTime(long processRunningTime) {
        this.processRunningTime = processRunningTime;
    }

    public long getProcessEndTime() {
        return processEndTime;
    }

    public void setProcessEndTime(long processEndTime) {
        this.processEndTime = processEndTime;
    }

    public long getProcessStartTime() {
        return processStartTime;
    }

    public void setProcessStartTime(long processStartTime) {
        this.processStartTime = processStartTime;
    }

    public long getReadTotalSize() {
        return readTotalSize;
    }

    public void setReadTotalSize(long readTotalSize) {
        this.readTotalSize = readTotalSize;
    }

    public int getSuccessUploadFileCount() {
        return successUploadFileCount;
    }

    public void setSuccessUploadFileCount(int successUploadFileCount) {
        this.successUploadFileCount = successUploadFileCount;
    }

    public int getCurrentUploadFileNum() {
        return currentUploadFileNum;
    }

    public void setCurrentUploadFileNum(int currentUploadFileNum) {
        this.currentUploadFileNum = currentUploadFileNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getUploadTotalSize() {
        return uploadTotalSize;
    }

    public String getUploadAddr() {
        return uploadAddr;
    }

    public void setUploadTotalSize(long uploadTotalSize) {
        this.uploadTotalSize = uploadTotalSize;
    }

    public void setUploadAddr(String uploadAddr) {
        this.uploadAddr = uploadAddr;
    }

    public String toJSon() {
    	StringBuffer strJSon = new StringBuffer();
    	strJSon.append("{UploadTotalSize:").append(getUploadTotalSize())
    	.append(",").append("ReadTotalSize:").append(getReadTotalSize())
    	.append(",").append("CurrentUploadFileNum:").append(getCurrentUploadFileNum())
    	.append(",").append("SuccessUploadFileCount:").append(getSuccessUploadFileCount())
    	.append(",").append("Status:'").append(getStatus()).append("',")
    	.append("ProcessStartTime:").append(getProcessStartTime()).append(",")
    	.append("ProcessEndTime:").append(getProcessEndTime()).append(",")
    	.append("ProcessRunningTime:").append(getProcessRunningTime())
    	.append(",").append("Cancel:").append(getCancel()).append("}");
    	return strJSon.toString();    	
    }
}