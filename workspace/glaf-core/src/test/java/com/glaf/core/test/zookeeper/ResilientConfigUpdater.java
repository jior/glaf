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
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.KeeperException;

public class ResilientConfigUpdater {

	public static final String PATH = "/config";

	private ResilientActiveKeyValueStore store;
	private Random random = new Random();

	public ResilientConfigUpdater(String hosts) throws IOException,
			InterruptedException {
		store = new ResilientActiveKeyValueStore();
		store.connect(hosts);
	}

	public void run() throws InterruptedException, KeeperException {
		while (true) {
			String value = random.nextInt(100) + "";
			store.write(PATH, value);
			System.out.printf("Set %s to %s\n", PATH, value);
			TimeUnit.SECONDS.sleep(random.nextInt(10));
		}
	}

	public static void main(String[] args) throws Exception {
		while (true) {
			try {
				ResilientConfigUpdater configUpdater = new ResilientConfigUpdater(
						args[0]);
				configUpdater.run();
			} catch (KeeperException.SessionExpiredException e) {
				// start a new session
			} catch (KeeperException e) {
				// already retried, so exit
				e.printStackTrace();
				break;
			}
		}
	}

}
