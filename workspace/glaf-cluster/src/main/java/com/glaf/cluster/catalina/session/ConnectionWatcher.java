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
import java.util.concurrent.CountDownLatch;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class ConnectionWatcher implements Watcher {
	private static Log log = LogFactory.getLog(ConnectionWatcher.class);

	private CountDownLatch signal = new CountDownLatch(1);

	private static final int SESSION_TIMEOUT = 60000;

	public ZooKeeper connection(String servers) {
		ZooKeeper zk;
		try {
			zk = new ZooKeeper(servers, SESSION_TIMEOUT, this);
			signal.await();
			return zk;
		} catch (IOException ex) {
			log.error(ex);
		} catch (InterruptedException ex) {
			log.error(ex);
		}
		return null;
	}

	public void process(WatchedEvent event) {
		KeeperState state = event.getState();
		if (state == KeeperState.SyncConnected) {
			signal.countDown();
		}
	}
}
