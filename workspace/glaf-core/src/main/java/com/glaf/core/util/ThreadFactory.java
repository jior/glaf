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

package com.glaf.core.util;

import org.eclipse.jetty.util.thread.QueuedThreadPool;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;

public final class ThreadFactory {

	protected final static QueuedThreadPool pool = new QueuedThreadPool();

	protected final static Configuration conf = BaseConfiguration.create();

	static {
		pool.setName("ThreadPool");
		pool.setMinThreads(conf.getInt("ThreadPool.minThreads", 5));
		pool.setMaxThreads(conf.getInt("ThreadPool.maxThreads", 50));
		pool.setThreadsPriority(Thread.NORM_PRIORITY - 1);

		try {
			pool.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void run(java.lang.Runnable r) {
		pool.execute(r);
	}

	private ThreadFactory() {

	}
}