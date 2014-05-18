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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;

public class ThreadFactory {

	protected final static Configuration conf = BaseConfiguration.create();

	private static volatile ExecutorService executorService;

	private static volatile MxThreadPoolExecutor executor;

	public static void execute(Runnable command) {
		getExecutor().execute(command);
	}

	public static void execute(Runnable command, long timeout, TimeUnit unit) {
		getExecutor().execute(command, timeout, unit);
	}

	private static synchronized MxThreadPoolExecutor getExecutor() {
		if (executor == null) {
			TaskQueue taskqueue = new TaskQueue();
			TaskThreadFactory tf = new TaskThreadFactory("thread-exec-", true,
					Thread.NORM_PRIORITY);
			executor = new MxThreadPoolExecutor(conf.getInt(
					"ThreadPool.minThreads", 5), conf.getInt(
					"ThreadPool.maxThreads", 50), 60, TimeUnit.SECONDS,
					taskqueue, tf);
			taskqueue.setParent((MxThreadPoolExecutor) executor);
		}
		return executor;
	}

	private static ExecutorService getExecutorService() {
		if (executorService == null) {
			executorService = Executors.newCachedThreadPool();
		}
		return executorService;
	}

	public static void run(Runnable command) {
		getExecutorService().execute(command);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Future submit(Callable task) {
		return getExecutorService().submit(task);
	}

	@SuppressWarnings("rawtypes")
	public static Future submit(Runnable command) {
		return getExecutorService().submit(command);
	}

	private ThreadFactory() {

	}

}