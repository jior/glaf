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
import java.util.Map;

public interface AccessEntry {

	public void addAccessPoint(AccessPoint accessPoint);

	public Map<String, AccessPoint> getAccessPoints();

	public String getApplicationName();

	public String getCreateBy();

	public Date getCreateDate();

	public String getEditFile();

	public int getEntryType();

	public String getFormName();

	public String getId();

	public String getObjectId();

	public String getObjectValue();

	public String getProcessDefinitionId();

	public String getProcessName();

	public Long getRoleId();

	public String getTaskName();

	public void removeAccessPoint(AccessPoint accessPoint);

	public void setAccessPoints(Map<String, AccessPoint> accessPoints);

	public void setApplicationName(String applicationName);

	public void setCreateBy(String createBy);

	public void setCreateDate(Date createDate);

	public void setEditFile(String editFile);

	public void setEntryType(int entryType);

	public void setFormName(String formName);

	public void setId(String id);

	public void setObjectId(String objectId);

	public void setObjectValue(String objectValue);

	public void setProcessDefinitionId(String processDefinitionId);

	public void setProcessName(String processName);

	public void setRoleId(Long roleId);

	public void setTaskName(String taskName);

}