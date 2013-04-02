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
import com.alibaba.fastjson.JSONObject;

public interface BlobItem extends DataFile {

	String getContentType();

	String getCreateBy();

	Date getCreateDate();

	byte[] getData();

	int getDeleteFlag();

	String getDeviceId();

	String getFileId();

	String getFilename();

	String getId();

	InputStream getInputStream();

	long getLastModified();

	int getLocked();

	String getName();

	String getObjectId();

	String getObjectValue();

	String getServiceKey();

	long getSize();

	int getStatus();

	String getType();

	void setContentType(String contentType);

	void setCreateBy(String createBy);

	void setCreateDate(Date createDate);

	void setData(byte[] data);

	void setDeleteFlag(int deleteFlag);

	void setDeviceId(String deviceId);

	void setFileId(String fileId);

	void setFilename(String filename);

	void setId(String id);

	void setInputStream(InputStream inputStream);

	void setLastModified(long lastModified);

	void setLocked(int locked);

	void setName(String name);

	void setObjectId(String objectId);

	void setObjectValue(String objectValue);

	void setServiceKey(String serviceKey);

	void setSize(long size);

	void setStatus(int status);

	void setType(String type);

	JSONObject toJsonObject();

}