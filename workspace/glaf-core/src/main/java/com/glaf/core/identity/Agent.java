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
package com.glaf.core.identity;

import java.io.Serializable;
import java.util.Date;

public interface Agent extends Serializable {

	int getAgentType();

	String getAssignFrom();

	String getAssignFromName();

	String getAssignTo();

	String getAssignToName();

	Date getCreateDate();

	Date getEndDate();

	String getId();

	int getLocked();

	String getObjectId();

	String getObjectValue();

	String getProcessName();

	String getServiceKey();

	Date getStartDate();

	String getTaskName();

	boolean isValid();

	void setAgentType(int agentType);

	void setAssignFrom(String assignFrom);

	void setAssignFromName(String assignFromName);

	void setAssignTo(String assignTo);

	void setAssignToName(String assignToName);

	void setCreateDate(Date createDate);

	void setEndDate(Date endDate);

	void setId(String id);

	void setLocked(int locked);

	void setObjectId(String objectId);

	void setObjectValue(String objectValue);

	void setProcessName(String processName);

	void setServiceKey(String serviceKey);

	void setStartDate(Date startDate);

	void setTaskName(String taskName);

	void setValid(boolean valid);

}