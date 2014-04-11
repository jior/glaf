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

package com.glaf.ui.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.IdGenerator;
import com.glaf.ui.mapper.UserPortalMapper;
import com.glaf.ui.model.UserPortal;
import com.glaf.ui.service.UserPortalService;

@Service("userPortalService")
@Transactional(readOnly = true)
public class MxUserPortalServiceImpl implements UserPortalService {
	protected final static Log logger = LogFactory
			.getLog(MxUserPortalServiceImpl.class);

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected UserPortalMapper userPortalMapper;

	public MxUserPortalServiceImpl() {

	}

	@Transactional
	public void deleteUserPortalByActorId(String actorId) {
		userPortalMapper.deleteUserPortalByActorId(actorId);
	}

	public List<UserPortal> getUserPortals(String actorId) {
		return userPortalMapper.getUserPortals(actorId);
	}

	@Transactional
	public void save(String actorId, List<UserPortal> userPortals) {
		userPortalMapper.deleteUserPortalByActorId(actorId);
		for (UserPortal userPortal : userPortals) {
			userPortal.setActorId(actorId);
			userPortal.setCreateDate(new Date());
			userPortal.setId(idGenerator.getNextId());
			userPortalMapper.insertUserPortal(userPortal);
		}
	}

	@Transactional
	public void save(String actorId, String panelId, int columnIndex,
			int position) {
		List<UserPortal> portals = this.getUserPortals(actorId);
		if (portals != null && !portals.isEmpty()) {
			for (UserPortal p : portals) {
				if (StringUtils.equals(panelId, p.getPanelId())) {
					p.setColumnIndex(columnIndex);
					p.setPosition(position);
					userPortalMapper.updateUserPortal(p);
				} else {
					if (p.getColumnIndex() == columnIndex) {
						if (p.getPosition() >= position) {
							p.setPosition(p.getPosition() + 1);
							userPortalMapper.updateUserPortal(p);
						}
					}
				}
			}
		}
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@javax.annotation.Resource
	public void setUserPortalMapper(UserPortalMapper userPortalMapper) {
		this.userPortalMapper = userPortalMapper;
	}

}