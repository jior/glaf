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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.base.online.mapper.*;
import com.glaf.base.online.domain.*;
import com.glaf.base.online.query.*;

@Service("userOnlineLogService")
@Transactional(readOnly = true)
public class UserOnlineLogServiceImpl implements UserOnlineLogService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected UserOnlineLogMapper userOnlineLogMapper;

	public UserOnlineLogServiceImpl() {

	}

	public int count(UserOnlineLogQuery query) {
		return userOnlineLogMapper.getUserOnlineLogCount(query);
	}

	public int getUserOnlineLogCountByQueryCriteria(UserOnlineLogQuery query) {
		return userOnlineLogMapper.getUserOnlineLogCount(query);
	}

	public List<UserOnlineLog> getUserOnlineLogs(String actorId) {
		UserOnlineLogQuery query = new UserOnlineLogQuery();
		query.actorId(actorId);
		List<UserOnlineLog> list = this.list(query);
		return list;
	}

	public List<UserOnlineLog> getUserOnlineLogsByQueryCriteria(int start,
			int pageSize, UserOnlineLogQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<UserOnlineLog> rows = sqlSessionTemplate.selectList(
				"getUserOnlineLogs", query, rowBounds);
		return rows;
	}

	public List<UserOnlineLog> list(UserOnlineLogQuery query) {
		List<UserOnlineLog> list = userOnlineLogMapper.getUserOnlineLogs(query);
		return list;
	}

	@Transactional
	public void login(UserOnlineLog model) {
		model.setId(idGenerator.nextId());

		if (model.getLoginDate() == null) {
			model.setLoginDate(new Date());
		}

		userOnlineLogMapper.insertUserOnlineLog(model);
	}

	/**
	 * 退出系统
	 * 
	 * @param actorId
	 */
	@Transactional
	public void logout(String actorId, String sessionId) {
		UserOnlineLogQuery query = new UserOnlineLogQuery();
		query.actorId(actorId);
		query.sessionId(sessionId);
		List<UserOnlineLog> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			UserOnlineLog model = list.get(0);
			model.setLogoutDate(new Date());
			userOnlineLogMapper.updateUserOnlineLogLogoutDate(model);
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setUserOnlineLogMapper(UserOnlineLogMapper userOnlineLogMapper) {
		this.userOnlineLogMapper = userOnlineLogMapper;
	}

}
