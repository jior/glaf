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

package com.glaf.cluster.catalina.session;

import java.io.IOException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.Session;
import org.apache.catalina.session.StandardManager;
import org.apache.catalina.session.StandardSession;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

public class MemcachedManager extends StandardManager {
	private final Log log = LogFactory.getLog(this.getClass()); // must not be
																// static
	protected MemCachedClient memCachedClient = null;
	protected SockIOPool pool = null;
	protected String servers = "127.0.0.1:11211";
	protected String perfix = "GLAF_";
	protected String flag = "true";

	public MemcachedManager() {
		super();
	}

	protected void closePool() {
		if (memCachedClient != null) {
			try {
			} catch (Exception ex) {
				log.error("error:", ex);
			}
			memCachedClient = null;
		}
		if (pool != null) {
			try {
				pool.shutDown();
			} catch (Exception ex) {
				log.error("error:", ex);
			}
		}
	}

	public Session createSession(String sessionId) {
		Session session = super.createSession(sessionId);
		memCachedClient.set(this.getPerfix() + session.getId(), flag);
		return session;
	}

	public Session findSession(String id) throws IOException {
		Session session = super.findSession(id);
		if (session == null && id != null) {
			try {
				Object sid = memCachedClient.get(this.getPerfix() + id);
				if (sid != null) {
					session = createSession(id);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return session;
	}

	public String getFlag() {
		return flag;
	}

	protected StandardSession getNewSession() {
		return new MemcachedSession(this, memCachedClient);
	}

	public String getPerfix() {
		return perfix;
	}

	public String getServers() {
		return servers;
	}

	protected void initPool() {
		try {
			if (pool == null) {
				try {
					pool = SockIOPool.getInstance();
					pool.setServers(servers.split(","));
					pool.setInitConn(5);
					pool.setMinConn(5);
					pool.setMaxConn(50);
					pool.setMaintSleep(30);
					pool.setNagle(false);
					pool.initialize();
				} catch (Exception ex) {
					ex.printStackTrace();
					log.error("error:", ex);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("error:", ex);
		}

		if (memCachedClient == null) {
			memCachedClient = new MemCachedClient();
		}
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setPerfix(String perfix) {
		this.perfix = perfix;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	public void startInternal() throws LifecycleException {
		this.setPathname(""); // must disable session persistence across Tomcat
								// restarts
		super.startInternal();
		this.initPool();
	}

	public void stopInternal() throws LifecycleException {
		super.stopInternal();
		this.closePool();
	}

}
