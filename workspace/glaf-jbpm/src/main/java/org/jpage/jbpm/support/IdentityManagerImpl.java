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


package org.jpage.jbpm.support;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jpage.actor.Actor;
import org.jpage.actor.User;
import org.jpage.core.cache.CacheFactory;
import org.jpage.jbpm.service.ActorManagerImpl;
import org.jpage.jbpm.util.Constant;

public class IdentityManagerImpl extends ActorManagerImpl {
	private final static Log logger = LogFactory
			.getLog(IdentityManagerImpl.class);

	public IdentityManagerImpl() {

	}

	/**
	 * ��ȡ�û� �˴�������-��������Push-Pop��
	 * 
	 * @param actorId
	 * @return org.jpage.actor.User
	 */
	public User getUser(JbpmContext jbpmContext, String actorId) {
		actorId = actorId.trim().toLowerCase();
		String cacheKey = USER_CACHE_KEY + actorId;
		if (CacheFactory.get(cacheKey) != null) {
			logger.debug("�ӻ����л�ȡ�û�:" + actorId);
			return (User) CacheFactory.get(cacheKey);
		}
		User user = null;
		Map userMap = this.getUserMap(jbpmContext);
		Iterator iterator = userMap.keySet().iterator();
		while (iterator.hasNext()) {
			String userId = (String) iterator.next();
			userId = userId.trim().toLowerCase();
			if (userId.equalsIgnoreCase(actorId)) {
				user = (User) userMap.get(actorId);
			}
			String tKey = USER_CACHE_KEY + userId;
			CacheFactory.put(tKey, userMap.get(userId));
		}
		return user;
	}

	/**
	 * ��ȡ�û�Map��<br>
	 * ����key���û��ʺţ�value��org.jpage.actor.User���� �˴�������-��������Push-Pop��
	 * 
	 * @return
	 */
	public Map getUserMap(JbpmContext jbpmContext) {
		String cacheKey = "cache_users_xyz";
		if (CacheFactory.get(cacheKey) != null) {
			logger.debug("�ӻ����л�ȡ�û�");
			return (Map) CacheFactory.get(cacheKey);
		}
		Map userMap = new LinkedHashMap();
		SqlMapManager sqlMapManager = (SqlMapManager) ComponentFactory
				.getBean("sqlMapManager");
		List rows = sqlMapManager.query(Constant.JBPM_USERS);
		if (rows != null && rows.size() > 0) {
			Iterator iterator = rows.iterator();
			while (iterator.hasNext()) {
				User user = (User) iterator.next();
				String actorId = user.getActorId();
				actorId = actorId.trim().toLowerCase();
				user.setActorId(actorId);
				userMap.put(actorId, user);
			}
		}
		logger.debug("��ȡ�û���Ŀ:" + userMap.size());
		CacheFactory.put(cacheKey, userMap);
		return userMap;
	}

	/**
	 * ��ȡ��ɫMap��<br>
	 * ����key�ǽ�ɫID��value��org.jpage.actor.Actor����
	 * 
	 * @return
	 */
	public Map getRoleMap(JbpmContext jbpmContext) {
		String cacheKey = "cache_roles_xyz";
		if (CacheFactory.get(cacheKey) != null) {
			logger.debug("�ӻ����л�ȡ��ɫ");
			return (Map) CacheFactory.get(cacheKey);
		}
		Map roleMap = new LinkedHashMap();
		SqlMapManager sqlMapManager = (SqlMapManager) ComponentFactory
				.getBean("sqlMapManager");
		List rows = sqlMapManager.query(Constant.JBPM_ROLES);
		if (rows != null && rows.size() > 0) {
			Iterator iterator = rows.iterator();
			while (iterator.hasNext()) {
				Actor actor = (Actor) iterator.next();
				roleMap.put(actor.getActorId(), actor);
			}
		}
		logger.debug("��ȡ��ɫ��Ŀ:" + roleMap.size());
		CacheFactory.put(cacheKey, roleMap);
		return roleMap;
	}

}
