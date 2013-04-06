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

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface DataModel {

	String getBusinessKey();

	String getCreateBy();

	Date getCreateDate();

	int getDeleteFlag();

	String getFormName();

	Long getId();

	int getLocked();

	Long getParentId();

	String getProcessInstanceId();

	String getProcessName();

	int getStatus();

	String getSubject();

	String getTableName();

	String getUpdateBy();

	Date getUpdateDate();

	int getWfStatus();

	void setBusinessKey(String businessKey);

	void setCreateBy(String createBy);

	void setCreateDate(Date createDate);

	void setDeleteFlag(int deleteFlag);

	void setFormName(String formName);

	void setId(Long id);

	void setLocked(int locked);

	Map<String, ColumnModel> getFields();
	
	Map<String, Object> getDataMap();

	void setParentId(Long parentId);

	void setProcessInstanceId(String processInstanceId);

	void setProcessName(String processName);

	void setStatus(int status);

	void setSubject(String subject);

	void setTableName(String tableName);

	void setUpdateBy(String updateBy);

	void setUpdateDate(Date updateDate);

	void setWfStatus(int wfStatus);

	JSONObject toJsonObject();

	ObjectNode toObjectNode();

}