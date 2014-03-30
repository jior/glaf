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

package com.glaf.core.util.threads;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;

public class ThreadFactory {

	protected final static Configuration conf = BaseConfiguration.create();

	private static Executor executor = null;

	static {
		init();
	}

	private static void init() {
		TaskQueue taskqueue = new TaskQueue();
		TaskThreadFactory tf = new TaskThreadFactory("thread-exec-", true,
				Thread.NORM_PRIORITY);
		executor = new MxThreadPoolExecutor(conf.getInt(
				"ThreadPool.minThreads", 5), conf.getInt(
				"ThreadPool.maxThreads", 10), 60, TimeUnit.SECONDS, taskqueue,
				tf);
		taskqueue.setParent((MxThreadPoolExecutor) executor);
	}

	public static void run(java.lang.Runnable r) {
		if (executor == null) {
			init();
		}
		executor.execute(r);
	}

	private ThreadFactory() {

	}

}