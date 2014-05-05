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

import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.domain.SysDataLog;
import com.glaf.core.query.SysDataLogQuery;

@Transactional(readOnly = true)
public interface SysDataLogService {

	/**
	 * 获取日志总记录数
	 * 
	 * @param query
	 * @return
	 */
	int getSysDataLogCountByQueryCriteria(SysDataLogQuery query);

	/**
	 * 获取一页日志
	 * 
	 * @param start
	 * @param pageSize
	 * @param query
	 * @return
	 */
	List<SysDataLog> getSysDataLogsByQueryCriteria(int start, int pageSize,
			SysDataLogQuery query);

	/**
	 * 根据查询条件获取日志
	 * 
	 * @param query
	 * @return
	 */
	List<SysDataLog> list(SysDataLogQuery query);

	/**
	 * 保存
	 * 
	 * @param bean
	 *            SysDataLog
	 * @return boolean
	 */
	@Transactional
	void save(SysDataLog bean);

	@Transactional
	void saveAll();

}