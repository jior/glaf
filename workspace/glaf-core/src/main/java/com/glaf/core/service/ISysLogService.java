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

import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.domain.SysLog;

@Transactional(readOnly = true)
public interface ISysLogService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            SysLog
	 * @return boolean
	 */
	@Transactional
	boolean create(SysLog bean);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SysLog
	 * @return boolean
	 */
	@Transactional
	boolean update(SysLog bean);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            SysLog
	 * @return boolean
	 */
	@Transactional
	boolean delete(SysLog bean);

	/**
	 * 删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	@Transactional
	boolean delete(long id);

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	SysLog findById(long id);
}