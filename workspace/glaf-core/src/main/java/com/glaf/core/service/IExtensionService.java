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

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.domain.InputDefinition;
import com.glaf.core.domain.SysDataTable;

@Transactional(readOnly = true)
public interface IExtensionService {

	/**
	 * 获取扩展模块
	 * 
	 * @return
	 */
	List<SysDataTable> getExtensionModules();

	/**
	 * 获取扩展模块的输入字段列表
	 * 
	 * @param serviceKey
	 * @return
	 */
	List<InputDefinition> getExtensionFields(String serviceKey);

	/**
	 * 
	 * @param serviceKey
	 * @param businessKey
	 * @return
	 */
	Map<String, Object> getDataMap(String serviceKey, String businessKey);

	/**
	 * 保存数据
	 * 
	 * @param serviceKey
	 * @param businessKey
	 * @param rows
	 */
	@Transactional
	void saveAll(String serviceKey, String businessKey,
			Map<String, Object> dataMap);

}
