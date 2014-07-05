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

package com.glaf.core.test.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

public class ConfigWatcher implements Watcher {

	private ActiveKeyValueStore store;

	public ConfigWatcher(String hosts) throws IOException, InterruptedException {
		store = new ActiveKeyValueStore();
		store.connect(hosts);
	}

	public void displayConfig() throws InterruptedException, KeeperException {
		String value = store.read(ConfigUpdater.PATH, this);
		System.out.printf("Read %s as %s\n", ConfigUpdater.PATH, value);
	}

	@Override
	public void process(WatchedEvent event) {
		if (event.getType() == EventType.NodeDataChanged) {
			try {
				displayConfig();
			} catch (InterruptedException e) {
				System.err.println("Interrupted. Exiting.");
				Thread.currentThread().interrupt();
			} catch (KeeperException e) {
				System.err.printf("KeeperException: %s. Exiting.\n", e);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		ConfigWatcher configWatcher = new ConfigWatcher(args[0]);
		configWatcher.displayConfig();

		// stay alive until process is killed or thread is interrupted
		Thread.sleep(Long.MAX_VALUE);
	}
}
