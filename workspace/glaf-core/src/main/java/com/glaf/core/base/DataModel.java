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
import com.alibaba.fastjson.JSONObject;

public interface DataModel {

	String getActorId();

	String getBusinessKey();

	String getCreateBy();

	Date getCreateDate();

	int getDeleteFlag();

	String getFormName();

	Long getId();

	int getLevel();

	int getListNo();

	String getName();

	String getObjectId();

	String getObjectValue();

	Long getParentId();

	String getProcessInstanceId();

	String getProcessName();

	String getSignForFlag();

	int getStatus();

	String getSubject();

	String getTreeId();

	String getTypeId();

	String getUpdateBy();

	Date getUpdateDate();

	int getWfStatus();

	void setBusinessKey(String businessKey);

	void setId(Long id);

	void setLevel(int level);

	void setListNo(int listNo);

	void setName(String name);

	void setParentId(Long parentId);

	void setTreeId(String treeId);

	JSONObject toJsonObject();

}