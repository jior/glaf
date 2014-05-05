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

package com.glaf.core.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.glaf.core.config.CustomProperties;
import com.glaf.core.dao.MyBatisEntityDAO;

public class IdentityUtils {
	protected final static Log logger = LogFactory.getLog(IdentityUtils.class);

	/**
	 * 获取默认配置的用户集合
	 * 
	 * @param sqlSession
	 * @param paramMap
	 * @return
	 */
	public static List<String> getActorIds(SqlSession sqlSession,
			Map<String, Object> paramMap) {
		List<String> actorIds = new java.util.ArrayList<String>();
		String statementId = CustomProperties.getString("sys.getActorIds");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getActorIds";
		}
		MyBatisEntityDAO entityDAO = new MyBatisEntityDAO(sqlSession);
		List<Object> rows = entityDAO.getList(statementId, paramMap);
		if (rows != null && !rows.isEmpty()) {
			for (Object object : rows) {
				if (object instanceof com.glaf.core.identity.User) {
					String actorId = ((com.glaf.core.identity.User) object)
							.getActorId();
					if (!actorIds.contains(actorId)) {
						actorIds.add(actorId);
					}
				} else if (object instanceof String) {
					String actorId = (String) object;
					if (!actorIds.contains(actorId)) {
						actorIds.add(actorId);
					}
				}
			}
		}
		return actorIds;
	}

	/**
	 * 获取指定查询语句的用户集合
	 * 
	 * @param sqlSession
	 * @param statement
	 * @param paramMap
	 * @return
	 */
	public static List<String> getActorIds(SqlSession sqlSession,
			String statement, Map<String, Object> paramMap) {
		List<String> actorIds = new java.util.ArrayList<String>();
		MyBatisEntityDAO entityDAO = new MyBatisEntityDAO(sqlSession);
		List<Object> rows = entityDAO.getList(statement, paramMap);
		if (rows != null && !rows.isEmpty()) {
			for (Object object : rows) {
				if (object instanceof com.glaf.core.identity.User) {
					String actorId = ((com.glaf.core.identity.User) object)
							.getActorId();
					if (!actorIds.contains(actorId)) {
						actorIds.add(actorId);
					}
				} else if (object instanceof String) {
					String actorId = (String) object;
					if (!actorIds.contains(actorId)) {
						actorIds.add(actorId);
					}
				}
			}
		}
		return actorIds;
	}

	/**
	 * 获取某个用户的直接上级
	 * 
	 * @param actorId
	 * @return
	 */

	public static List<String> getLeaderIds(SqlSession sqlSession, String userId) {
		Map<String, Object> paramMap = new java.util.HashMap<String, Object>();
		paramMap.put("actorId", userId);

		String statementId = CustomProperties.getString("sys.getLeaders");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getLeaders";
		}

		List<String> actorIds = new java.util.ArrayList<String>();
		MyBatisEntityDAO entityDAO = new MyBatisEntityDAO(sqlSession);
		List<Object> rows = entityDAO.getList(statementId, paramMap);
		if (rows != null && rows.size() > 0) {
			for (Object object : rows) {
				if (object instanceof com.glaf.core.identity.User) {
					String actorId = ((com.glaf.core.identity.User) object)
							.getActorId();
					if (!actorIds.contains(actorId)) {
						actorIds.add(actorId);
					}
				} else if (object instanceof String) {
					String actorId = (String) object;
					if (!actorIds.contains(actorId)) {
						actorIds.add(actorId);
					}
				}
			}
		}

		return actorIds;
	}

}