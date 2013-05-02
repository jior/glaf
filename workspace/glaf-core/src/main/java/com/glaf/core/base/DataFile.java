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

package com.glaf.core.base;

import java.util.Date;

public interface DataFile extends JSONable {

	/**
	 * ҵ���ʶ
	 * 
	 * @return
	 */
	String getBusinessKey();

	/**
	 * ��ȡContentType
	 * 
	 * @return
	 */
	String getContentType();

	/**
	 * ������
	 * 
	 * @return
	 */
	String getCreateBy();

	/**
	 * ��������
	 * 
	 * @return
	 */
	Date getCreateDate();

	/**
	 * ������
	 * 
	 * @return
	 */
	byte[] getData();

	/**
	 * ɾ�����
	 * 
	 * @return
	 */
	int getDeleteFlag();

	/**
	 * ��ȡ�豸���
	 * 
	 * @return
	 */
	String getDeviceId();

	/**
	 * �ļ����
	 * 
	 * @return
	 */
	String getFileId();

	/**
	 * �ļ�����
	 * 
	 * @return
	 */
	String getFilename();

	/**
	 * ���
	 * 
	 * @return
	 */
	String getId();

	/**
	 * ����޸�����
	 * 
	 * @return
	 */
	long getLastModified();

	/**
	 * �������
	 * 
	 * @return
	 */
	int getLocked();

	/**
	 * ����
	 * 
	 * @return
	 */
	String getName();

	/**
	 * ��ȡĿ��ID
	 * 
	 * @return
	 */
	String getObjectId();

	/**
	 * ��ȡĿ��ֵ
	 * 
	 * @return
	 */
	String getObjectValue();

	String getPath();

	/**
	 * �����ʶ
	 * 
	 * @return
	 */
	String getServiceKey();

	/**
	 * �ļ���С
	 * 
	 * @return
	 */
	long getSize();

	/**
	 * ״̬
	 * 
	 * @return
	 */
	int getStatus();

	/**
	 * ����
	 * 
	 * @return
	 */
	String getType();

	void setBusinessKey(String businessKey);

	void setCreateBy(String createBy);

	/**
	 * ��¼����ʱ��
	 * 
	 * @param createDate
	 */
	void setCreateDate(Date createDate);

	/**
	 * ����������
	 * 
	 * @param data
	 */
	void setData(byte[] data);

	/**
	 * �����豸���
	 * 
	 * @param deviceId
	 */
	void setDeviceId(String deviceId);

	/**
	 * �����ļ����
	 * 
	 * @param fileId
	 */
	void setFileId(String fileId);

	/**
	 * �����ļ�����
	 * 
	 * @param filename
	 */
	void setFilename(String filename);

	/**
	 * ���ü�¼����
	 * 
	 * @param id
	 */
	void setId(String id);

	/**
	 * ��ȡ��������޸�ʱ��
	 * 
	 * @param lastModified
	 */
	void setLastModified(long lastModified);

	/**
	 * ��������
	 * 
	 * @param name
	 */
	void setName(String name);

	void setPath(String path);

	/**
	 * ���÷����ʶ
	 * 
	 * @param serviceKey
	 */
	void setServiceKey(String serviceKey);

	/**
	 * �������ݳ���
	 * 
	 * @param size
	 */
	void setSize(long size);

	void setStatus(int status);

	void setType(String type);

}