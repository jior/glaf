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
public interface UserOnlineLogService {

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getUserOnlineLogCountByQueryCriteria(UserOnlineLogQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	List<UserOnlineLog> getUserOnlineLogs(String actorId);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<UserOnlineLog> getUserOnlineLogsByQueryCriteria(int start,
			int pageSize, UserOnlineLogQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<UserOnlineLog> list(UserOnlineLogQuery query);

	/**
	 * 记录在线用户
	 * 
	 * @param model
	 */
	@Transactional
	void login(UserOnlineLog model);

	/**
	 * 退出系统
	 * 
	 * @param actorId
	 * @param sessionId
	 */
	@Transactional
	void logout(String actorId, String sessionId);

}
