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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

public class ZooKeeperClientImpl implements ZooKeeperClient {

	private final Log logger = LogFactory.getLog(ZooKeeperClientImpl.class);

	private String servers;

	private volatile ZooKeeper zooKeeper;

	private static final String GROUP_NAME = "/TOMCAT_SESSION";

	private final ZooKeeperFactory zooKeeperFactory;

	// Make it less than 1M to leave some space for extra zookeeper data
	private static final int MAX_NODE_SIZE = 1000000;

	private static final long CONNECTION_LOSS_RETRY_WAIT = 1000;

	private final int maxNodeSize = MAX_NODE_SIZE;

	private final Lock sessionRestartLock = new ReentrantLock();

	private final CopyOnWriteArrayList<SessionStateListener> sessionStateListeners = new CopyOnWriteArrayList<SessionStateListener>();

	public ZooKeeperClientImpl(ZooKeeperFactory zooKeeperFactory) {
		this.zooKeeperFactory = zooKeeperFactory;
	}

	public void addSessionStateListener(
			SessionStateListener sessionStateListener) {
		sessionStateListeners.add(sessionStateListener);
	}

	public boolean connected() {
		return zooKeeper != null
				&& zooKeeper.getState() == ZooKeeper.States.CONNECTED;
	}

	public String saveDataNode(final String pathPrefix, final byte[] data)
			throws InterruptedException {
		String rootPath = pathPrefix;
		try {
			// Create Root node with version and size of the state part
			rootPath = zooKeeperCall("Cannot create node at " + pathPrefix,
					new Callable<String>() {
						@Override
						public String call() throws Exception {
							Stat stat = zooKeeper.exists(pathPrefix, false);
							// stat为null表示无此节点，需要创建
							if (stat == null) {
								String createPath = zooKeeper.create(
										pathPrefix, null,
										ZooDefs.Ids.OPEN_ACL_UNSAFE,
										CreateMode.PERSISTENT);
								zooKeeper.setData(pathPrefix, data, -1);
								logger.info("Created node: " + pathPrefix);
								return createPath;
							} else {
								zooKeeper.setData(pathPrefix, data, -1);
								logger.info("Update node: " + pathPrefix);
							}
							return pathPrefix;
						}
					});
		} catch (KeeperException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Cannot create node at " + pathPrefix,
					ex);
		}
		return rootPath;
	}

	public String createLargeSequentialNode(final String pathPrefix, byte[] data)
			throws InterruptedException {
		String rootPath = pathPrefix;
		try {
			final int size = data.length;
			// Create Root node with version and size of the state part
			rootPath = zooKeeperCall("Cannot create node at " + pathPrefix,
					new Callable<String>() {
						@Override
						public String call() throws Exception {
							Stat stat = zooKeeper.exists(pathPrefix, false);
							// stat为null表示无此节点，需要创建
							if (stat == null) {
								return zooKeeper.create(pathPrefix, Integer
										.toString(size).getBytes("US-ASCII"),
										ZooDefs.Ids.OPEN_ACL_UNSAFE,
										CreateMode.PERSISTENT_SEQUENTIAL);
							}
							return pathPrefix;
						}
					});
			int chunkNum = 0;
			// Store state part in chunks in case it's too big for a single node
			// It should be able to fit into a single node in most cases
			for (int i = 0; i < size; i += maxNodeSize) {
				final String chunkPath = rootPath + "/" + chunkNum;
				final byte[] chunk;
				if (size > maxNodeSize) {
					chunk = Arrays.copyOfRange(data, i,
							Math.min(size, i + maxNodeSize));
				} else {
					chunk = data;
				}
				zooKeeperCall("Cannot create node at " + chunkPath,
						new Callable<String>() {
							@Override
							public String call() throws Exception {
								return zooKeeper.create(chunkPath, chunk,
										ZooDefs.Ids.OPEN_ACL_UNSAFE,
										CreateMode.PERSISTENT);
							}
						});
				chunkNum++;
			}
		} catch (KeeperException e) {
			throw new RuntimeException("Cannot create node at " + pathPrefix, e);
		}
		return rootPath;
	}

	public void createPersistentNode(final String path)
			throws InterruptedException {
		if (!path.startsWith("/")) {
			throw new RuntimeException("Path " + path
					+ " doesn't start with \"/\"");
		}
		try {
			zooKeeperCall("Cannot create leader node", new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					String[] nodes = path.split("/");
					String currentPath = "";
					for (int i = 1; i < nodes.length; i++) {
						currentPath = currentPath + "/" + nodes[i];
						if (zooKeeper.exists(currentPath, null) == null) {
							try {
								zooKeeper.create(currentPath, new byte[0],
										ZooDefs.Ids.OPEN_ACL_UNSAFE,
										CreateMode.PERSISTENT);
								logger.debug("Created node " + currentPath);
							} catch (KeeperException.NodeExistsException e) {
								// Ignore - node was created between our check
								// and attempt to create it
							}
						}
					}
					return null;
				}
			});
		} catch (KeeperException e) {
			throw new RuntimeException("Cannot create node at " + path, e);
		}

	}

	public void deleteLargeNode(final String path) throws InterruptedException {
		try {
			zooKeeperCall("Cannot delete node at " + path,
					new Callable<Object>() {
						@Override
						public Object call() throws Exception {
							List<String> children = zooKeeper.getChildren(path,
									null);
							for (String child : children) {
								zooKeeper.delete(path + "/" + child, -1);
							}
							zooKeeper.delete(path, -1);
							return null;
						}
					});
		} catch (KeeperException e) {
			throw new RuntimeException("Cannot delete node at " + path, e);
		}
	}

	public void deleteNode(final String path) throws InterruptedException {
		try {
			zooKeeperCall("Cannot delete node", new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					zooKeeper.delete(path, -1);
					return null;
				}
			});
		} catch (KeeperException e) {
			throw new RuntimeException("Cannot delete node" + path, e);
		}
	}

	public void deleteNodeRecursively(final String path)
			throws InterruptedException {
		try {
			zooKeeperCall("Cannot delete node recursively",
					new Callable<Object>() {
						@Override
						public Object call() throws Exception {
							deleteNodeRecursively(path);
							return null;
						}

						private void deleteNodeRecursively(String path)
								throws InterruptedException, KeeperException {
							List<String> nodes = zooKeeper.getChildren(path,
									false);
							for (String node : nodes) {
								deleteNodeRecursively(path + "/" + node);
							}
							zooKeeper.delete(path, -1);
						}
					});
		} catch (KeeperException e) {
			throw new RuntimeException("Cannot delete node" + path, e);
		}
	}

	protected void doClose() {
	}

	protected void doStart() {
		try {
			final Watcher watcher = new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					switch (event.getState()) {
					case Expired:
						resetSession();
						break;
					case SyncConnected:
						notifySessionConnected();
						break;
					case Disconnected:
						notifySessionDisconnected();
						break;
					default:
						break;
					}
				}
			};
			zooKeeper = zooKeeperFactory.newZooKeeper(watcher);
			createPersistentNode(GROUP_NAME);
		} catch (InterruptedException e) {
			throw new RuntimeException("Cannot start ZooKeeper client", e);
		}
	}

	protected void doStop() {
		if (zooKeeper != null) {
			try {
				logger.debug("Closing zooKeeper");
				zooKeeper.close();
			} catch (InterruptedException e) {
				// Ignore
			}
			zooKeeper = null;
		}
	}

	private String extractLastPart(String path) {
		int index = path.lastIndexOf('/');
		if (index >= 0) {
			return path.substring(index + 1);
		} else {
			return path;
		}
	}

	public byte[] getNode(final String path) throws InterruptedException {
		while (true) {
			try {
				// First we check if the node exists and set createdWatcher
				Stat stat = zooKeeperCall("Checking if node exists",
						new Callable<Stat>() {
							@Override
							public Stat call() throws Exception {
								return zooKeeper.exists(path, false);
							}
						});

				// If the node exists, returning the current node data
				if (stat != null) {
					return zooKeeperCall("Getting node data",
							new Callable<byte[]>() {
								@Override
								public byte[] call() throws Exception {
									return zooKeeper.getData(path, false, null);
								}
							});
				} else {
					return null;
				}
			} catch (KeeperException.NoNodeException e) {
				// Node disappeared between exists() and getData() calls
				// We will try again
			} catch (KeeperException e) {
				throw new RuntimeException("Cannot obtain node " + path, e);
			}
		}
	}

	public byte[] getNode(final String path, final NodeListener nodeListener)
			throws InterruptedException {
		// If the node doesn't exist, we will use createdWatcher to wait for its
		// appearance
		// If the node exists, we will use deletedWatcher to monitor its
		// disappearance
		final Watcher watcher = wrapNodeListener(nodeListener);
		while (true) {
			try {
				// First we check if the node exists and set createdWatcher
				Stat stat = zooKeeperCall("Checking if node exists",
						new Callable<Stat>() {
							@Override
							public Stat call() throws Exception {
								return zooKeeper.exists(path, watcher);
							}
						});

				// If the node exists, returning the current node data
				if (stat != null) {
					return zooKeeperCall("Getting node data",
							new Callable<byte[]>() {
								@Override
								public byte[] call() throws Exception {
									return zooKeeper.getData(path, watcher,
											null);
								}
							});
				} else {
					return null;
				}
			} catch (KeeperException.NoNodeException e) {
				// Node disappeared between exists() and getData() calls
				// We will try again
			} catch (KeeperException e) {
				throw new RuntimeException("Cannot obtain node " + path, e);
			}
		}
	}

	public byte[] getOrCreateTransientNode(final String path,
			final byte[] data, final NodeListener nodeListener)
			throws InterruptedException {
		while (true) {
			try {
				// First, we try to obtain existing node
				return zooKeeperCall("Getting master data",
						new Callable<byte[]>() {
							@Override
							public byte[] call() throws Exception {
								return zooKeeper.getData(path,
										wrapNodeListener(nodeListener), null);
							}
						});
			} catch (KeeperException.NoNodeException e) {
				try {
					// If node doesn't exist - we try to create the node and
					// return data without setting the
					// watcher
					zooKeeperCall("Cannot create leader node",
							new Callable<Object>() {
								@Override
								public Object call() throws Exception {
									zooKeeper.create(path, data,
											ZooDefs.Ids.OPEN_ACL_UNSAFE,
											CreateMode.EPHEMERAL);
									return null;
								}
							});
					return data;
				} catch (KeeperException.NodeExistsException e1) {
					// If node is already created - we will try to read created
					// node on the next iteration of the loop
				} catch (KeeperException e1) {
					throw new RuntimeException("Cannot create node " + path, e1);
				}
			} catch (KeeperException e) {
				throw new RuntimeException("Cannot obtain node" + path, e);
			}
		}
	}

	public String getServers() {
		return servers;
	}

	public Set<String> listNodes(final String path,
			final NodeListChangedListener nodeListChangedListener)
			throws InterruptedException {
		Set<String> res = new HashSet<String>();
		final Watcher watcher = (nodeListChangedListener != null) ? new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				if (event.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
					nodeListChangedListener.onNodeListChanged();
				}
			}
		}
				: null;
		try {
			List<String> children = zooKeeperCall("Cannot list nodes",
					new Callable<List<String>>() {
						@Override
						public List<String> call() throws Exception {
							return zooKeeper.getChildren(path, watcher);
						}
					});

			if (children == null) {
				return null;
			}
			for (String childPath : children) {
				res.add(extractLastPart(childPath));
			}
			return res;
		} catch (KeeperException e) {
			throw new RuntimeException("Cannot list nodes", e);
		}
	}

	private void notifySessionConnected() {
		for (SessionStateListener listener : sessionStateListeners) {
			listener.sessionConnected();
		}
	}

	private void notifySessionDisconnected() {
		for (SessionStateListener listener : sessionStateListeners) {
			listener.sessionDisconnected();
		}
	}

	private void notifySessionReset() {
		for (SessionStateListener listener : sessionStateListeners) {
			listener.sessionExpired();
		}
	}

	public void removeSessionStateListener(
			SessionStateListener sessionStateListener) {
		sessionStateListeners.remove(sessionStateListener);
	}

	private void resetSession() {
		zooKeeper.sync("/", new AsyncCallback.VoidCallback() {
			@Override
			public void processResult(int i, String s, Object o) {
				sessionRestartLock.lock();
				try {
					logger.trace("Checking if ZooKeeper session should be restarted");
					if (!connected()) {
						logger.info("Restarting ZooKeeper discovery");
						try {
							logger.trace("Stopping ZooKeeper");
							doStop();
						} catch (Exception ex) {
							logger.error("Error stopping ZooKeeper", ex);
						}
						while (!started()) {
							try {
								logger.trace("Starting ZooKeeper");
								doStart();
								logger.trace("Started ZooKeeper");
								notifySessionReset();
								return;
							} catch (Exception ex) {
								if (ex.getCause() != null
										&& ex.getCause() instanceof InterruptedException) {
									logger.info(
											"ZooKeeper startup was interrupted",
											ex);
									Thread.currentThread().interrupt();
									return;
								}
								logger.warn("Error starting ZooKeeper ", ex);
							}
							try {
								Thread.sleep(1000);
							} catch (InterruptedException ex) {
								return;
							}
						}
					} else {
						logger.trace("ZooKeeper is already restarted. Ignoring");
					}

				} finally {
					sessionRestartLock.unlock();
				}
			}
		}, null);

	}

	public long sessionId() {
		return zooKeeper.getSessionId();
	}

	public void setOrCreatePersistentNode(final String path, final byte[] data)
			throws InterruptedException {
		try {
			zooKeeperCall("Cannot create persistent node",
					new Callable<Object>() {
						@Override
						public Object call() throws Exception {
							if (zooKeeper.exists(path, null) != null) {
								zooKeeper.setData(path, data, -1);
							} else {
								zooKeeper.create(path, data,
										ZooDefs.Ids.OPEN_ACL_UNSAFE,
										CreateMode.PERSISTENT);
							}
							return null;
						}
					});

		} catch (KeeperException e) {
			throw new RuntimeException("Cannot persistent node", e);
		}
	}

	public void setOrCreateTransientNode(final String path, final byte[] data)
			throws InterruptedException {
		try {
			try {
				zooKeeperCall("Creating node " + path, new Callable<Object>() {
					@Override
					public Object call() throws Exception {
						zooKeeper.create(path, data,
								ZooDefs.Ids.OPEN_ACL_UNSAFE,
								CreateMode.EPHEMERAL);
						return null;
					}
				});
			} catch (KeeperException.NodeExistsException e1) {
				// Ignore
			}
		} catch (KeeperException e) {
			throw new RuntimeException("Cannot create node " + path, e);
		}
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	public void start() {
		doStart();
	}

	public boolean started() {
		return zooKeeper != null
				&& zooKeeper.getState() == ZooKeeper.States.CONNECTED;
	}

	public void stop() {
		doStop();
	}

	private Watcher wrapNodeListener(final NodeListener nodeListener) {
		if (nodeListener != null) {
			return new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					switch (event.getType()) {
					case NodeCreated:
						nodeListener.onNodeCreated(event.getPath());
						break;
					case NodeDeleted:
						nodeListener.onNodeDeleted(event.getPath());
						break;
					case NodeDataChanged:
						nodeListener.onNodeDataChanged(event.getPath());
						break;
					default:
						break;
					}
				}
			};
		} else {
			return null;
		}
	}

	private <T> T zooKeeperCall(String reason, Callable<T> callable)
			throws InterruptedException, KeeperException {
		boolean connectionLossReported = false;
		while (true) {
			try {
				if (zooKeeper == null) {
					throw new RuntimeException(
							"ZooKeeper is not available - reconnecting");
				}
				return callable.call();
			} catch (KeeperException.ConnectionLossException ex) {
				if (!connectionLossReported) {
					logger.debug("Connection Loss Exception");
					connectionLossReported = true;
				}
				Thread.sleep(CONNECTION_LOSS_RETRY_WAIT);
			} catch (KeeperException.SessionExpiredException e) {
				logger.warn("Session Expired Exception");
				resetSession();
				throw new RuntimeException(reason, e);
			} catch (KeeperException e) {
				throw e;
			} catch (InterruptedException e) {
				throw e;
			} catch (Exception e) {
				logger.warn("Unknown Exception", e);
				throw new RuntimeException(reason, e);
			}
		}
	}

}
