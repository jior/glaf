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

import com.glaf.core.domain.*;
import com.glaf.core.query.*;

@Transactional(readOnly = true)
public interface ISystemParamService {

	/**
	 * 根据参数定义创建参数实例
	 * 
	 * @param serviceKey
	 * @param businessKey
	 */
	@Transactional
	void createSystemParams(String serviceKey, String businessKey);

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据服务主键获取内容
	 * 
	 * @param serviceKey
	 * @return
	 */
	List<InputDefinition> getInputDefinitions(String serviceKey);

	/**
	 * 根据服务主键获取内容
	 * 
	 * @param serviceKey
	 * @return
	 */
	List<InputDefinition> getInputDefinitions(String serviceKey, String typeCd);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	SystemParam getSystemParam(String id);

	/**
	 * 获取系统参数
	 * 
	 * @param serviceKey
	 * @param businessKey
	 * @param keyName
	 * @return
	 */
	SystemParam getSystemParam(String serviceKey, String businessKey,
			String keyName);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getSystemParamCount(Map<String, Object> parameter);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getSystemParamCountByQueryCriteria(SystemParamQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<SystemParam> getSystemParams(Map<String, Object> parameter);

	/**
	 * 根据服务主键获取内容
	 * 
	 * @param serviceKey
	 * @param businessKey
	 * @return
	 */
	List<SystemParam> getSystemParams(String serviceKey, String businessKey);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<SystemParam> getSystemParamsByQueryCriteria(int start, int pageSize,
			SystemParamQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<SystemParam> list(SystemParamQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(SystemParam systemParam);

	/**
	 * 保存多条记录
	 * 
	 * @param serviceKey
	 * @param businessKey
	 * @return
	 */
	@Transactional
	void saveAll(String serviceKey, String businessKey, List<SystemParam> rows);

	/**
	 * 修改多条参数定义
	 * 
	 * @param serviceKey
	 * @param rows
	 */
	@Transactional
	void updateAll(String serviceKey, List<InputDefinition> rows);

}
