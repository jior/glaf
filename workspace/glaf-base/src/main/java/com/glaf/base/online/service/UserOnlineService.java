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

package com.glaf.base.online.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.online.domain.*;
import com.glaf.base.online.query.*;

@Transactional(readOnly = true)
public interface UserOnlineService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> ids);
	
	/**
	 * 删除超时的在线用户
	 * @param timeoutSeconds
	 */
	@Transactional
	void deleteTimeoutUsers(int timeoutSeconds);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<UserOnline> list(UserOnlineQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getUserOnlineCountByQueryCriteria(UserOnlineQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<UserOnline> getUserOnlinesByQueryCriteria(int start, int pageSize,
			UserOnlineQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	UserOnline getUserOnline(String actorId);

	/**
	 * 记录在线用户
	 * @param model
	 */
	@Transactional
	void login(UserOnline model);
	
	/**
	 * 持续在线用户
	 * @param actorId
	 */
	@Transactional
	void remain(String actorId);

	/**
	 * 退出系统
	 * 
	 * @param actorId
	 */
	@Transactional
	void logout(String actorId);

}
