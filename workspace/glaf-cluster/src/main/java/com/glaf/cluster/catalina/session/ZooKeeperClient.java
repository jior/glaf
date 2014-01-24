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

import java.util.Set;

public interface ZooKeeperClient {

	interface NodeListChangedListener {
		public void onNodeListChanged();
	}

	interface NodeListener {
		public void onNodeCreated(String id);

		public void onNodeDataChanged(String id);

		public void onNodeDeleted(String id);
	}

	interface SessionStateListener {
		public void sessionConnected();

		public void sessionDisconnected();

		public void sessionExpired();
	}

	void addSessionStateListener(SessionStateListener sessionStateListener);

	boolean connected();
	
	String createLargeSequentialNode(String pathPrefix, byte[] data)
			throws InterruptedException;
	
	String saveDataNode(String pathPrefix, byte[] data)
			throws InterruptedException;
	
	void createPersistentNode(String path) throws InterruptedException;

	void deleteLargeNode(String path) throws InterruptedException;

	void deleteNode(String path) throws InterruptedException;

	void deleteNodeRecursively(String path) throws InterruptedException;

	byte[] getNode(final String path)
			throws InterruptedException;
	
	byte[] getNode(final String path, final NodeListener nodeListener)
			throws InterruptedException;

	byte[] getOrCreateTransientNode(String path, byte[] data,
			NodeListener nodeListener) throws InterruptedException;

	Set<String> listNodes(String path,
			NodeListChangedListener nodeListChangedListener)
			throws InterruptedException;

	void removeSessionStateListener(SessionStateListener sessionStateListener);

	long sessionId();

	void setOrCreatePersistentNode(String path, byte[] data)
			throws InterruptedException;

	void setOrCreateTransientNode(String path, byte[] data)
			throws InterruptedException;

	void start();

	void stop();
}
