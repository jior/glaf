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

package com.glaf.core.service;

import java.util.List;

import com.glaf.core.base.AccessEntry;
import com.glaf.core.base.Accessable;
import com.glaf.core.base.DataAccess;
import com.glaf.core.base.ModuleAccess;
import com.glaf.core.query.AccessEntryQuery;

public interface IAccessService {

	/**
	 * 获取访问列表
	 * 
	 * @param serviceKey
	 * @param businessKey
	 * @return
	 */
	List<Accessable> getAccessableList(String serviceKey, String businessKey);

	/**
	 * 通过参数获取访问实体
	 * 
	 * @param params
	 * @return
	 */
	List<AccessEntry> getAccessEntries(AccessEntryQuery query);

	/**
	 * 通过流程名称获取访问实体
	 * 
	 * @param processName
	 * @return
	 */
	List<AccessEntry> getAccessEntries(String processName);

	/**
	 * 获取数据访问对象
	 * 
	 * @param serviceKey
	 * @param dataInstanceId
	 * @return
	 */
	List<DataAccess> getDataAccesses(String serviceKey, String dataInstanceId);

	/**
	 * 通过应用名称获取访问实体
	 * 
	 * @param applicationName
	 * @return
	 */
	AccessEntry getLatestAccessEntry(String applicationName);

	/**
	 * 通过流程名称及任务名称获取访问实体
	 * 
	 * @param processName
	 * @param taskName
	 * @return
	 */
	AccessEntry getLatestAccessEntry(String processName, String taskName);

	/**
	 * 获取模块访问对象
	 * 
	 * @param serviceKey
	 * @return
	 */
	List<ModuleAccess> getModuleAccesses(String serviceKey);

	/**
	 * 保存访问实体
	 * 
	 * @param accessEntry
	 */

	void saveAccessEntry(AccessEntry accessEntry);

	/**
	 * 保存多个访问实体
	 * 
	 * @param rows
	 */

	void saveAll(List<AccessEntry> rows);
}