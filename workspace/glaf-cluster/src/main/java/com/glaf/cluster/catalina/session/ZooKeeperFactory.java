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

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class ZooKeeperFactory {

	private static final int SESSION_TIMEOUT = 60000;

	private String servers;

	public ZooKeeperFactory(String servers) {
          this.servers = servers;
	}

	public ZooKeeper newZooKeeper() {
		return newZooKeeper(new Watcher() {
			@Override
			public void process(WatchedEvent event) {
			}
		});
	}

	public ZooKeeper newZooKeeper(Watcher watcher) {
		try {
			return new ZooKeeper(servers, SESSION_TIMEOUT, watcher);
		} catch (IOException ex) {
			throw new RuntimeException("Cannot start ZooKeeper", ex);
		}
	}

}