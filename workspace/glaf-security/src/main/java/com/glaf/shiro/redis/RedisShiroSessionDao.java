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
package com.glaf.shiro.redis;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.util.SerializerUtils;
import com.glaf.shiro.Cacheable;

@SuppressWarnings("unchecked")
public class RedisShiroSessionDao extends AbstractSessionDAO {
	public Logger logger = LoggerFactory.getLogger(getClass());
	private String sessionPrefix = "ss-";

	private Cacheable cacheable;

	public RedisShiroSessionDao() {
	}

	@Override
	public void delete(Session session) {
		try {
			cacheable.remove(session.getId().toString().getBytes());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = session.getId();
		try {
			super.assignSessionId(session,
					sessionPrefix + super.generateSessionId(session));
			update(session);
			sessionId = session.getId();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		Session session = null;
		try {
			session = (Session) cacheable.getCache(sessionId.toString()
					.getBytes());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return session;
	}

	@Override
	public Collection<Session> getActiveSessions() {
		String keys = sessionPrefix + "*";
		List<Session> list = null;
		try {
			list = (List<Session>) cacheable.getKeys(keys.getBytes());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return list;
	}

	public Cacheable getCacheable() {
		return cacheable;
	}

	public String getSessionPrefix() {
		return sessionPrefix;
	}

	public void setCacheable(Cacheable cacheable) {
		this.cacheable = cacheable;
	}

	public void setSessionPrefix(String sessionPrefix) {
		this.sessionPrefix = sessionPrefix;
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		try {
			cacheable.update(session.getId().toString().getBytes(),
					SerializerUtils.serialize(session),
					session.getTimeout() / 1000);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

}
