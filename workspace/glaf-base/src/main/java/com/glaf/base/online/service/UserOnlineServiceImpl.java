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

@Service("userOnlineService")
@Transactional(readOnly = true)
public class UserOnlineServiceImpl implements UserOnlineService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected UserOnlineMapper userOnlineMapper;

	public UserOnlineServiceImpl() {

	}

	public int count(UserOnlineQuery query) {
		query.ensureInitialized();
		return userOnlineMapper.getUserOnlineCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			userOnlineMapper.deleteUserOnlineById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				userOnlineMapper.deleteUserOnlineById(id);
			}
		}
	}

	/**
	 * 删除超时的在线用户
	 * 
	 * @param timeoutSeconds
	 */
	@Transactional
	public void deleteTimeoutUsers(int timeoutSeconds) {
		UserOnlineQuery query = new UserOnlineQuery();
		List<UserOnline> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			for (UserOnline bean : list) {
				long ts = System.currentTimeMillis() - bean.getCheckDateMs();
				if (ts / 1000 > timeoutSeconds) {// 如果超时，从在线用户列表中删除
					userOnlineMapper.deleteUserOnlineById(bean.getId());
				}
			}
		}
	}

	public UserOnline getUserOnline(Long id) {
		if (id == null) {
			return null;
		}
		UserOnline userOnline = userOnlineMapper.getUserOnlineById(id);
		return userOnline;
	}

	public UserOnline getUserOnline(String actorId) {
		UserOnlineQuery query = new UserOnlineQuery();
		query.actorId(actorId);
		List<UserOnline> list = this.list(query);
		UserOnline userOnline = null;
		if (list != null && !list.isEmpty()) {
			userOnline = list.get(0);
		}
		return userOnline;
	}

	public int getUserOnlineCountByQueryCriteria(UserOnlineQuery query) {
		return userOnlineMapper.getUserOnlineCount(query);
	}

	public List<UserOnline> getUserOnlinesByQueryCriteria(int start,
			int pageSize, UserOnlineQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<UserOnline> rows = sqlSessionTemplate.selectList("getUserOnlines",
				query, rowBounds);
		return rows;
	}

	public List<UserOnline> list(UserOnlineQuery query) {
		List<UserOnline> list = userOnlineMapper.getUserOnlines(query);
		return list;
	}

	@Transactional
	public void login(UserOnline model) {
		UserOnline userOnline = this.getUserOnline(model.getActorId());
		if (userOnline != null) {
			userOnline.setLoginDate(model.getLoginDate());
			userOnline.setLoginIP(model.getLoginIP());
			userOnline.setSessionId(model.getSessionId());
			if (model.getLoginDate() == null) {
				userOnline.setLoginDate(new Date());
			}
			userOnlineMapper.updateUserOnline(userOnline);
		} else {
			if (model.getLoginDate() == null) {
				model.setLoginDate(new Date());
			}
			model.setId(idGenerator.nextId());
			userOnlineMapper.insertUserOnline(model);
		}
	}

	/**
	 * 持续在线用户
	 * 
	 * @param actorId
	 */
	@Transactional
	public void remain(String actorId) {
		UserOnline userOnline = this.getUserOnline(actorId);
		if (userOnline != null) {
			userOnline.setCheckDateMs(System.currentTimeMillis());
			userOnline.setCheckDate(new Date(userOnline.getCheckDateMs()));
			userOnlineMapper.updateUserOnlineCheckDate(userOnline);
		}
	}

	/**
	 * 退出系统
	 * 
	 * @param actorId
	 */
	@Transactional
	public void logout(String actorId) {
		UserOnline userOnline = this.getUserOnline(actorId);
		if (userOnline != null) {
			this.deleteById(userOnline.getId());
		}
	}

	@Transactional
	public void save(UserOnline userOnline) {
		if (userOnline.getId() == null) {
			userOnline.setId(idGenerator.nextId());
			userOnlineMapper.insertUserOnline(userOnline);
		} else {
			userOnlineMapper.updateUserOnline(userOnline);
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
	public void setUserOnlineMapper(UserOnlineMapper userOnlineMapper) {
		this.userOnlineMapper = userOnlineMapper;
	}

}
