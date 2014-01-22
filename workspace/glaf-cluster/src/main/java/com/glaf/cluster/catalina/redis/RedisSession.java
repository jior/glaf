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

package com.glaf.cluster.catalina.redis;

import org.apache.catalina.session.StandardSession;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import redis.clients.jedis.PipelineBlock;
import redis.clients.jedis.Protocol;

public class RedisSession extends StandardSession {
	private static final long serialVersionUID = 1L;

	protected transient Log log = LogFactory.getLog(RedisSession.class);
	protected transient RedisManager _manager;
	protected transient PipelineBlock _expirePipeline = new RedisPipelineBlock(
			this) {
		@Override
		public void execute() {
			_redisSession._manager.jedisHset(RedisManager.TOMCAT_SESSION_PREFIX
					+ _redisSession.id, "__[creationTime]__",
					String.valueOf(System.currentTimeMillis()).getBytes());
			_redisSession._manager.jedisExpire(
					RedisManager.TOMCAT_SESSION_PREFIX + _redisSession.id,
					_redisSession.maxInactiveInterval);
		}
	};

	public RedisSession(RedisManager manager) {
		super(manager);

		this._manager = manager;
	}

	/**
	 * Update the accessed time information for this session. This method should
	 * be called by the context when a request comes in for a particular
	 * session, even if the application does not reference it.
	 */
	@Override
	public void access() {
		if (_manager.debugEnabled) {
			log.info("id=" + this.id);
		}
		super.access();

		if (!_manager.isInitialized()) {
			return;
		}
		try {
			if (_manager.jedisExpire(RedisManager.TOMCAT_SESSION_PREFIX
					+ this.id, this.maxInactiveInterval) == 0) {
				_manager.jedisPipelined(RedisManager.TOMCAT_SESSION_PREFIX
						+ this.id, _expirePipeline);
			}
		} catch (Exception ex) {
			log.error("error:", ex);
		}
	}

	@Override
	public Object getAttribute(String name) {
		Object value = super.getAttribute(name);

		if (name.startsWith("javax.zkoss.zk.ui.Session")) {
			return value;
		}

		if (!_manager.isInitialized()) {
			return value;
		}
		try {
			if (_manager.stickySessionEnabled) {
				if (value == null) {
					byte[] bytesValue = _manager.jedisHget(
							RedisManager.TOMCAT_SESSION_PREFIX + this.id, name);
					if (bytesValue == null) {
						return value;
					}

					if (_manager.debugEnabled) {
						log.info("id=" + this.id + ",name=" + name
								+ ",strValue="
								+ new String(bytesValue, Protocol.CHARSET));
					}

					value = _manager.deserialize(bytesValue);
					super.setAttribute(name, value, false); // ������ӵ����ص�attributes��.

					return value;
				} else {
					return value;
				}
			} else { // ����stickySessionEnabled,��ôÿ�ζ�Ҫ��redis���ȡ����ֵ.
				byte[] bytesValue = _manager.jedisHget(
						RedisManager.TOMCAT_SESSION_PREFIX + this.id, name);
				if (bytesValue == null) {
					return value;
				}

				if (_manager.debugEnabled) {
					log.info("id=" + this.id + ",name=" + name + ",strValue="
							+ new String(bytesValue, Protocol.CHARSET));
				}

				return _manager.deserialize(bytesValue);
			}
		} catch (Exception ex) {
			log.error("error:name=" + name + ";value=" + value, ex);
			return value;
		}
	}

	@Override
	public void setAttribute(String name, Object value) {
		super.setAttribute(name, value);

		if (value == null) {
			return;
		}

		if (name.startsWith("javax.zkoss.zk.ui.Session")) {
			return;
		}

		if (!_manager.isInitialized()) {
			return;
		}
		try {
			byte[] bytesValue = _manager.serialize(value);
			if (_manager.debugEnabled) {
				log.info("id=" + this.id + ",name=" + name + ",strValue="
						+ new String(bytesValue, Protocol.CHARSET));
			}
			_manager.jedisHset(RedisManager.TOMCAT_SESSION_PREFIX + this.id,
					name, bytesValue);
		} catch (Exception ex) {
			log.error("error:name=" + name + ";value=" + value, ex);
		}
	}

	@Override
	protected void removeAttributeInternal(String name, boolean notify) {
		if (_manager.debugEnabled) {
			log.info("id=" + this.id + ",name=" + name + ",notify=" + notify);
		}
		super.removeAttributeInternal(name, notify);

		if (!_manager.isInitialized()) {
			return;
		}
		try {
			_manager.jedisHdel(RedisManager.TOMCAT_SESSION_PREFIX + this.id,
					name);
		} catch (Exception ex) {
			log.error("error:", ex);
		}
	}

	@Override
	public void expire(boolean notify) {
		if (_manager.debugEnabled) {
			log.info("id=" + this.id + ",notify=" + notify);
		}
		super.expire(notify); // ��expire��ͻ���յ�ǰsession����������

		if (!_manager.isInitialized()) {
			return;
		}
		try {
			_manager.jedisDel(RedisManager.TOMCAT_SESSION_PREFIX + this.id);
		} catch (Exception ex) {
			log.error("error:", ex);
		}
	}

	void setCachedId(String id) {
		this.id = id;
	}

	private static abstract class RedisPipelineBlock extends PipelineBlock {
		RedisSession _redisSession;

		RedisPipelineBlock(RedisSession redisSession) {
			super();
			_redisSession = redisSession;
		}
	}
}
