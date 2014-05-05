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
import java.util.List;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

/**
 * 与zk交互的工具类
 * 
 */
public class ZKClientUtils {

	private static Log logger = LogFactory.getLog(ZKClientUtils.class);

	/**
	 * Create an ephemeral node with the given path and data. Create parents if
	 * necessary.
	 */
	public static void createEphemeralPath(final ZkClient client,
			final String path, final byte[] data) throws Exception {
		try {
			client.createEphemeral(path, data);
		} catch (final ZkNoNodeException e) {
			createParentPath(client, path);
			client.createEphemeral(path, data);
		}
	}

	/**
	 * Create an ephemeral node with the given path and data. Throw
	 * NodeExistException if node already exists.
	 */
	public static void createEphemeralPathExpectConflict(final ZkClient client,
			final String path, final byte[] data) throws Exception {
		try {
			createEphemeralPath(client, path, data);
		} catch (final ZkNodeExistsException e) {

			// this canZkConfig happen when there is connection loss; make sure
			// the data
			// is what we intend to write
			byte[] storedData = null;
			try {
				storedData = readData(client, path);
			} catch (final ZkNoNodeException e1) {
				// the node disappeared; treat as if node existed and let caller
				// handles this
			} catch (final Exception e2) {
				throw e2;
			}
			if (storedData == null || !Arrays.equals(storedData, data)) {
				throw e;
			} else {
				// otherwise, the creation succeeded, return normally
				logger.info(path
						+ " exists  during connection loss; this is ok");
			}
		} catch (final Exception e) {
			throw e;
		}

	}

	/**
	 * create the parent path
	 */
	public static void createParentPath(final ZkClient client, final String path)
			throws Exception {
		final String parentDir = path.substring(0, path.lastIndexOf('/'));
		if (parentDir.length() != 0) {
			client.createPersistent(parentDir, true);
		}
	}

	/**
	 * Create a persistent node with the given path and data. Create parents if
	 * necessary.
	 */
	public static void createPath(final ZkClient client, final String path,
			final byte[] data) throws Exception {
		try {
			client.createPersistent(path, data);
		} catch (ZkNoNodeException e) {
			createParentPath(client, path);
			client.createPersistent(path, data);
		}
	}

	/**
	 * Create a persistent node with the given path and data. Throw
	 * NodeExistException if node already exists.
	 */
	public static void createPathExpectConflict(final ZkClient client,
			final String path, final byte[] data) throws Exception {
		try {
			createPath(client, path, data);
		} catch (final ZkNodeExistsException e) {

			// this canZkConfig happen when there is connection loss; make sure
			// the data
			// is what we intend to write
			byte[] storedData = null;
			try {
				storedData = readData(client, path);
			} catch (final ZkNoNodeException e1) {
				// the node disappeared; treat as if node existed and let caller
				// handles this
			} catch (final Exception e2) {
				throw e2;
			}
			if (storedData == null || !Arrays.equals(storedData, data)) {
				throw e;
			} else {
				// otherwise, the creation succeeded, return normally
				logger.info(path
						+ " exists with value  during connection loss; this is ok");
			}
		} catch (final Exception e) {
			throw e;
		}

	}

	public static void deletePath(final ZkClient client, final String path)
			throws Exception {
		try {
			client.delete(path);
		} catch (final ZkNoNodeException e) {
			logger.info(path + " deleted during connection loss; this is ok");
		} catch (final Exception e) {
			throw e;
		}
	}

	public static void deletePathRecursive(final ZkClient client,
			final String path) throws Exception {
		try {
			client.deleteRecursive(path);
		} catch (final ZkNoNodeException e) {
			logger.info(path + " deleted during connection loss; this is ok");

		} catch (final Exception e) {
			throw e;
		}
	}

	public static List<String> getChildren(final ZkClient client,
			final String path) {
		return client.getChildren(path);
	}

	public static List<String> getChildrenMaybeNull(final ZkClient client,
			final String path) {
		try {
			return client.getChildren(path);
		} catch (final ZkNoNodeException e) {
			return null;
		}
	}

	public static String getLastPart(final String path) {
		if (path == null) {
			return null;
		}
		final int index = path.lastIndexOf('/');
		if (index >= 0) {
			return path.substring(index + 1);
		} else {
			return null;
		}
	}

	/**
	 * make sure a persiste.nt path exists in ZK. Create the path if not exist.
	 */
	public static void makeSurePersistentPathExists(final ZkClient client,
			final String path) throws Exception {
		if (!client.exists(path)) {
			try {
				client.createPersistent(path, true);
			} catch (final ZkNodeExistsException e) {
			} catch (final Exception e) {
				throw e;
			}

		}
	}

	/**
	 * Check if the given path exists
	 */
	public static boolean pathExists(final ZkClient client, final String path) {
		return client.exists(path);
	}

	public static byte[] readData(final ZkClient client, final String path) {
		return client.readData(path);
	}

	public static byte[] readDataMaybeNull(final ZkClient client,
			final String path) {
		return client.readData(path, true);
	}

	/**
	 * Update the value of a persistent node with the given path and data.
	 * create parrent directory if necessary. Never throw NodeExistException.
	 */
	public static void updateEphemeralPath(final ZkClient client,
			final String path, final byte[] data) throws Exception {
		try {
			client.writeData(path, data);
		} catch (final ZkNoNodeException e) {

			createParentPath(client, path);
			client.createEphemeral(path, data);

		} catch (final Exception e) {
			throw e;
		}
	}

	/**
	 * Update the value of a persistent node with the given path and data.
	 * create parrent directory if necessary. Never throw NodeExistException.
	 */
	public static void updatePersistentPath(final ZkClient client,
			final String path, final byte[] data) throws Exception {
		try {
			client.writeData(path, data);
		} catch (final ZkNoNodeException e) {
			createParentPath(client, path);
			client.createPersistent(path, data);
		} catch (final Exception e) {
			throw e;
		}
	}

	public static void writeData(final ZkClient client, final String path,
			final byte[] data) {
		client.writeData(path, data);
	}

}