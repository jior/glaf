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

import java.io.IOException;
import java.util.Enumeration;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class ZooKeeperSessionManager extends ManagerBase implements Lifecycle {

	private final Log log = LogFactory.getLog(ZooKeeperSessionManager.class);

	protected String servers = "localhost:2181";

	protected int timeout = 3600;

	protected ZooKeeperSessionHandlerValve handlerValve;
	protected ThreadLocal<ZooKeeperSession> currentSession = new ThreadLocal<ZooKeeperSession>();
	protected ThreadLocal<String> currentSessionId = new ThreadLocal<String>();
	protected ThreadLocal<Boolean> currentSessionIsPersisted = new ThreadLocal<Boolean>();
	protected Serializer serializer;

	protected static String DATA_SESSION_KEY = "DATA_SESSION_KEY";

	protected static String name = "ZooKeeperSessionManager";

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

		// String jvmRoute = getJvmRoute();

		try {

			SessionMetaData metadata = new SessionMetaData();
			// Ensure generation of a unique session identifier.
			do {
				if (null == sessionId) {
					// sessionId = generateSessionId();
					sessionId = UUID32.getUUID() + System.currentTimeMillis();
				}

				// if (jvmRoute != null) {
				// sessionId += '.' + jvmRoute;
				// }

				metadata.setId(sessionId);

			} while (ZooKeeperUtils.asynCreateSessionNode(metadata, true) != null);

			session.setId(sessionId);
			session.tellNew();

			currentSession.set(session);
			currentSessionId.set(sessionId);
			currentSessionIsPersisted.set(false);
		} finally {

		}

		return session;
	}

	/**
	 * Get the lifecycle listeners associated with this lifecycle. If this
	 * Lifecycle has no listeners registered, a zero-length array is returned.
	 */
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
			session = loadSessionFromZooKeeper(id);
			if (session != null) {
				currentSessionIsPersisted.set(true);
			}
		}

		currentSession.set(session);
		currentSessionId.set(id);

		return session;
	}

	@Override
	public int getRejectedSessions() {
		// Essentially do nothing.
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
		log.info("Attempting to use serializer :" + serializationStrategyClass);
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
		ZooKeeperSession session;
		try {
			log.trace("Attempting to load session " + id + " from ZooKeeper");

			byte[] data = ZooKeeperUtils.getBytesData(id, DATA_SESSION_KEY);

			if (data == null) {
				log.trace("Session " + id + " not found in ZooKeeper");
				session = null;
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

			return session;
		} catch (IOException e) {
			log.fatal(e.getMessage());
			throw e;
		} catch (ClassNotFoundException ex) {
			log.fatal("Unable to deserialize into session", ex);
			throw new IOException("Unable to deserialize into session", ex);
		} finally {

		}
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
		ZooKeeperUtils.asynDeleteSessionNode(session.getId(), false);
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
				ZooKeeperUtils.setSessionData(zooKeeperSession.getId(),
						DATA_SESSION_KEY,
						serializer.serializeFrom(zooKeeperSession));
			}

			currentSessionIsPersisted.set(true);

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		} finally {

		}
	}

	public void setRejectedSessions(int i) {
		// Do nothing.
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
			if (valve instanceof ZooKeeperSessionHandlerValve) {
				this.handlerValve = (ZooKeeperSessionHandlerValve) valve;
				this.handlerValve.setZooKeeperSessionManager(this);
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

		ZooKeeperUtils.initialize(this.getServers());

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

		try {
			ZooKeeperUtils.destroy();
		} catch (Exception e) {
			// Do nothing.
		}

		// Require a new random number generator if we are restarted
		super.stopInternal();
	}

	@Override
	public void unload() throws IOException {

	}
}
