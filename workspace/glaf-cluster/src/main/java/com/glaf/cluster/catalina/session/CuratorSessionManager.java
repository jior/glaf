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

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.util.LifecycleSupport;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Loader;
import org.apache.catalina.Valve;
import org.apache.catalina.Session;
import org.apache.catalina.session.ManagerBase;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.zookeeper.CreateMode;

public class CuratorSessionManager extends ManagerBase implements Lifecycle {

	private final Log log = LogFactory.getLog(CuratorSessionManager.class);

	protected String servers = "localhost:2181";

	protected int timeout = 3600;

	private static final String GROUP_NAME = "/TOMCAT_SESSION";

	protected byte[] NULL_SESSION = "null".getBytes();

	protected CuratorSessionHandlerValve handlerValve;
	protected ThreadLocal<ZooKeeperSession> currentSession = new ThreadLocal<ZooKeeperSession>();
	protected ThreadLocal<String> currentSessionId = new ThreadLocal<String>();
	protected ThreadLocal<Boolean> currentSessionIsPersisted = new ThreadLocal<Boolean>();
	protected Serializer serializer;

	private volatile CuratorFramework zkClient;

	protected static String name = "CuratorSessionManager";

	protected String serializationStrategyClass = "com.glaf.cluster.catalina.session.JavaSerializer";

	/**
	 * The lifecycle event support for this component.
	 */
	protected LifecycleSupport lifecycle = new LifecycleSupport(this);

	@Override
	public void add(Session session) {
		try {
			save(session);
		} catch (Exception ex) {
			log.warn("Unable to add to session manager store: "
					+ ex.getMessage());
			throw new RuntimeException(
					"Unable to add to session manager store.", ex);
		}
	}

	/**
	 * Add a lifecycle event listener to this component.
	 * 
	 * @param listener
	 *            The listener to add
	 */
	@Override
	public void addLifecycleListener(LifecycleListener listener) {
		lifecycle.addLifecycleListener(listener);
	}

	public void afterRequest() {
		ZooKeeperSession zooKeeperSession = currentSession.get();
		if (zooKeeperSession != null) {
			currentSession.remove();
			currentSessionId.remove();
			currentSessionIsPersisted.remove();
			log.trace("Session removed from ThreadLocal :"
					+ zooKeeperSession.getIdInternal());
		}
	}

	@Override
	public Session createEmptySession() {
		return new ZooKeeperSession(this);
	}

	@Override
	public Session createSession(String sessionId) {
		ZooKeeperSession session = (ZooKeeperSession) createEmptySession();

		// Initialize the properties of the new session and return it
		session.setNew(true);
		session.setValid(true);
		session.setCreationTime(System.currentTimeMillis());
		session.setMaxInactiveInterval(getMaxInactiveInterval());

		try {

			if (null == sessionId) {
				sessionId = UUID32.getUUID() + System.currentTimeMillis();
			}

			String path = GROUP_NAME + "/" + sessionId;
			zkClient.create().withMode(CreateMode.PERSISTENT)
					.forPath(path, NULL_SESSION);

			session.setId(sessionId);
			session.tellNew();

			currentSession.set(session);
			currentSessionId.set(sessionId);
			currentSessionIsPersisted.set(false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return session;
	}

	@Override
	public LifecycleListener[] findLifecycleListeners() {
		return lifecycle.findLifecycleListeners();
	}

	@Override
	public Session findSession(String id) throws IOException {
		ZooKeeperSession session;

		if (id == null) {
			session = null;
			currentSessionIsPersisted.set(false);
		} else if (id.equals(currentSessionId.get())) {
			session = currentSession.get();
		} else {
			log.trace("parepare load session from zk:" + id);
			session = loadSessionFromZooKeeper(id);
			if (session != null) {
				currentSessionIsPersisted.set(true);
			}
		}

		if (session != null) {
			currentSession.set(session);
			currentSessionId.set(id);
		}

		return session;
	}

	@Override
	public int getRejectedSessions() {
		return 0;
	}

	public String getServers() {
		return servers;
	}

	public int getTimeout() {
		return timeout;
	}

	private void initializeSerializer() throws ClassNotFoundException,
			IllegalAccessException, InstantiationException {
		log.trace("Attempting to use serializer :" + serializationStrategyClass);
		serializer = (Serializer) Class.forName(serializationStrategyClass)
				.newInstance();

		Loader loader = null;

		if (container != null) {
			loader = container.getLoader();
		}

		ClassLoader classLoader = null;

		if (loader != null) {
			classLoader = loader.getClassLoader();
		}
		serializer.setClassLoader(classLoader);
	}

	@Override
	public void load() throws ClassNotFoundException, IOException {

	}

	public ZooKeeperSession loadSessionFromZooKeeper(String id)
			throws IOException {
		ZooKeeperSession session = null;
		if (id != null && id.trim().length() > 0) {
			try {
				log.trace("Attempting to load session " + id
						+ " from ZooKeeper");

				String path = GROUP_NAME + "/" + id;

				byte[] data = zkClient.getData().forPath(path);

				if (data == null) {
					log.trace("Session " + id + " not found in ZooKeeper");
					session = null;
				} else if (Arrays.equals(NULL_SESSION, data)) {
					throw new IllegalStateException(
							"Race condition encountered: attempted to load session["
									+ id
									+ "] which has been created but not yet serialized.");
				} else {
					log.trace("Deserializing session " + id + " from ZooKeeper");
					session = (ZooKeeperSession) createEmptySession();
					serializer.deserializeInto(data, session);
					session.setId(id);
					session.setNew(false);
					session.setMaxInactiveInterval(getMaxInactiveInterval() * 1000);
					session.access();
					session.setValid(true);
					session.resetDirtyTracking();

					if (log.isTraceEnabled()) {
						log.trace("Session Contents [" + id + "]:");
						Enumeration<String> en = session.getAttributeNames();
						while (en.hasMoreElements()) {
							log.trace("  " + en.nextElement());
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
				log.fatal(e.getMessage());
				throw e;
			} catch (Exception ex) {
				ex.printStackTrace();
				log.fatal("Unable to deserialize into session", ex);
				throw new IOException("Unable to deserialize into session", ex);
			}
		}
		return session;
	}

	@Override
	public void processExpires() {

	}

	@Override
	public void remove(Session session) {
		remove(session, false);
	}

	@Override
	public void remove(Session session, boolean update) {
		log.trace("Removing session ID : " + session.getId());
		String path = GROUP_NAME + "/" + session.getId();
		try {
			zkClient.delete().inBackground().forPath(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Remove a lifecycle event listener from this component.
	 * 
	 * @param listener
	 *            The listener to remove
	 */
	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		lifecycle.removeLifecycleListener(listener);
	}

	public void save(Session session) {
		try {
			log.trace("Saving session " + session + " into ZooKeeper");

			ZooKeeperSession zooKeeperSession = (ZooKeeperSession) session;

			if (log.isTraceEnabled()) {
				log.trace("Session Contents [" + zooKeeperSession.getId()
						+ "]:");
				Enumeration<String> en = zooKeeperSession.getAttributeNames();
				while (en.hasMoreElements()) {
					log.trace("  " + en.nextElement());
				}
			}

			Boolean sessionIsDirty = zooKeeperSession.isDirty();

			zooKeeperSession.resetDirtyTracking();

			if (sessionIsDirty || currentSessionIsPersisted.get() != true) {
				String path = GROUP_NAME + "/" + zooKeeperSession.getId();
				try {
					zkClient.delete().inBackground().forPath(path);
				} catch (Exception e) {
					e.printStackTrace();
				}
				zkClient.create()
						.withMode(CreateMode.PERSISTENT)
						.forPath(path,
								serializer.serializeFrom(zooKeeperSession));
			}

			currentSessionIsPersisted.set(true);

		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new RuntimeException(ex);
		}
	}

	public void setRejectedSessions(int i) {

	}

	public void setSerializationStrategyClass(String strategy) {
		this.serializationStrategyClass = strategy;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * Start this component and implement the requirements of
	 * {@link org.apache.catalina.util.LifecycleBase#startInternal()}.
	 * 
	 * @exception LifecycleException
	 *                if this component detects a fatal error that prevents this
	 *                component from being used
	 */
	@Override
	protected synchronized void startInternal() throws LifecycleException {
		super.startInternal();

		setState(LifecycleState.STARTING);

		Boolean attachedToValve = false;
		for (Valve valve : getContainer().getPipeline().getValves()) {
			if (valve instanceof CuratorSessionHandlerValve) {
				this.handlerValve = (CuratorSessionHandlerValve) valve;
				this.handlerValve.setCuratorSessionManager(this);
				log.info("Attached to ZooKeeperSessionHandlerValve");
				attachedToValve = true;
				break;
			}
		}

		if (!attachedToValve) {
			String error = "Unable to attach to session handling valve; sessions cannot be saved after the request without the valve starting properly.";
			log.fatal(error);
			throw new LifecycleException(error);
		}

		try {
			initializeSerializer();
		} catch (ClassNotFoundException e) {
			log.fatal("Unable to load serializer", e);
			throw new LifecycleException(e);
		} catch (InstantiationException e) {
			log.fatal("Unable to load serializer", e);
			throw new LifecycleException(e);
		} catch (IllegalAccessException e) {
			log.fatal("Unable to load serializer", e);
			throw new LifecycleException(e);
		}

		log.info("Will expire sessions after " + getMaxInactiveInterval()
				+ " seconds");

		RetryPolicy retryPolicy = new ExponentialBackoffRetry(5000,
				Integer.MAX_VALUE);
		zkClient = CuratorFrameworkFactory.newClient(servers, retryPolicy);
		// CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory
		// .builder();
		// zkClient = builder
		// .connectString(servers)
		// .sessionTimeoutMs(30000)
		// .connectionTimeoutMs(30000)
		// .canBeReadOnly(false)
		// .retryPolicy(
		// new ExponentialBackoffRetry(5000, Integer.MAX_VALUE))
		// .namespace("TOMCAT").defaultData(null).build();

		zkClient.start();

		try {
			zkClient.create().forPath(GROUP_NAME);
		} catch (Exception ex) {

		}

		setDistributable(true);
	}

	/**
	 * Stop this component and implement the requirements of
	 * {@link org.apache.catalina.util.LifecycleBase#stopInternal()}.
	 * 
	 * @exception LifecycleException
	 *                if this component detects a fatal error that prevents this
	 *                component from being used
	 */
	@Override
	protected synchronized void stopInternal() throws LifecycleException {
		if (log.isDebugEnabled()) {
			log.debug("Stopping");
		}

		setState(LifecycleState.STOPPING);
		zkClient.close();
		// Require a new random number generator if we are restarted
		super.stopInternal();
	}

	@Override
	public void unload() throws IOException {

	}
}
