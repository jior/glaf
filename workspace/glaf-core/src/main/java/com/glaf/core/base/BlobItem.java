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

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public interface BlobItem extends DataFile {

	public String getContentType();

	public String getCreateBy();

	public Date getCreateDate();

	public byte[] getData();

	public int getDeleteFlag();

	public String getDeviceId();

	public String getFileId();

	public String getFilename();

	public String getId();

	public InputStream getInputStream();

	public long getLastModified();

	public int getLocked();

	public String getName();

	public String getObjectId();

	public String getObjectValue();

	public String getResourceId();

	public String getServiceKey();

	public long getSize();

	public int getStatus();

	public String getType();

	public void setContentType(String contentType);

	public void setCreateBy(String createBy);

	public void setCreateDate(Date createDate);

	public void setData(byte[] data);

	public void setDeleteFlag(int deleteFlag);

	public void setDeviceId(String deviceId);

	public void setFileId(String fileId);

	public void setFilename(String filename);

	public void setId(String id);

	public void setInputStream(InputStream inputStream);

	public void setLastModified(long lastModified);

	public void setLocked(int locked);

	public void setName(String name);

	public void setObjectId(String objectId);

	public void setObjectValue(String objectValue);

	public void setResourceId(String resourceId);

	public void setServiceKey(String serviceKey);

	public void setSize(long size);

	public void setStatus(int status);

	public void setType(String type);

	public JSONObject toJsonObject() throws JSONException;

}