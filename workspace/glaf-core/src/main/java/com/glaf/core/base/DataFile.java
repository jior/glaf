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

import java.io.InputStream;
import java.util.Date;

public interface DataFile extends JSONable {

	/**
	 * 业务标识
	 * 
	 * @return
	 */
	String getBusinessKey();

	/**
	 * 获取ContentType
	 * 
	 * @return
	 */
	String getContentType();

	/**
	 * 创建人
	 * 
	 * @return
	 */
	String getCreateBy();

	/**
	 * 创建日期
	 * 
	 * @return
	 */
	Date getCreateDate();

	/**
	 * 数据流
	 * 
	 * @return
	 */
	byte[] getData();

	/**
	 * 删除标记
	 * 
	 * @return
	 */
	int getDeleteFlag();

	/**
	 * 获取设备编号
	 * 
	 * @return
	 */
	String getDeviceId();

	/**
	 * 文件编号
	 * 
	 * @return
	 */
	String getFileId();

	/**
	 * 文件名称
	 * 
	 * @return
	 */
	String getFilename();

	/**
	 * 编号
	 * 
	 * @return
	 */
	String getId();

	InputStream getInputStream();

	/**
	 * 最后修改日期
	 * 
	 * @return
	 */
	long getLastModified();

	/**
	 * 锁定标记
	 * 
	 * @return
	 */
	int getLocked();

	/**
	 * 名称
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 获取目标ID
	 * 
	 * @return
	 */
	String getObjectId();

	/**
	 * 获取目标值
	 * 
	 * @return
	 */
	String getObjectValue();

	String getPath();

	/**
	 * 服务标识
	 * 
	 * @return
	 */
	String getServiceKey();

	/**
	 * 文件大小
	 * 
	 * @return
	 */
	long getSize();

	/**
	 * 状态
	 * 
	 * @return
	 */
	int getStatus();

	/**
	 * 类型
	 * 
	 * @return
	 */
	String getType();

	void setBusinessKey(String businessKey);

	void setContentType(String contentType);

	void setCreateBy(String createBy);

	/**
	 * 记录创建时间
	 * 
	 * @param createDate
	 */
	void setCreateDate(Date createDate);

	/**
	 * 设置数据流
	 * 
	 * @param data
	 */
	void setData(byte[] data);

	void setDeleteFlag(int deleteFlag);

	/**
	 * 设置设备编号
	 * 
	 * @param deviceId
	 */
	void setDeviceId(String deviceId);

	/**
	 * 设置文件编号
	 * 
	 * @param fileId
	 */
	void setFileId(String fileId);

	/**
	 * 设置文件名称
	 * 
	 * @param filename
	 */
	void setFilename(String filename);

	/**
	 * 设置记录主键
	 * 
	 * @param id
	 */
	void setId(String id);

	void setInputStream(InputStream inputStream);

	/**
	 * 获取数据最后修改时间
	 * 
	 * @param lastModified
	 */
	void setLastModified(long lastModified);

	void setLocked(int locked);

	/**
	 * 设置名称
	 * 
	 * @param name
	 */
	void setName(String name);

	void setObjectId(String objectId);

	void setObjectValue(String objectValue);

	void setPath(String path);

	/**
	 * 设置服务标识
	 * 
	 * @param serviceKey
	 */
	void setServiceKey(String serviceKey);

	/**
	 * 设置数据长度
	 * 
	 * @param size
	 */
	void setSize(long size);

	void setStatus(int status);

	void setType(String type);

}