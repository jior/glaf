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

package com.glaf.core.test;

import com.glaf.core.future.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class TaskFutureTest {

	final TaskPromise promise = new DefaultTaskPromise();
	final TaskFuture future = promise.getFuture();
	final CountDownLatch latch = new CountDownLatch(1);

	@Test
	public void test1() throws Exception {
		future.onComplete(new TaskCallback() { // 添加结束 Callback
			@Override
			public TaskFuture apply(TaskFuture f) {
				latch.countDown();
				return f;
			}
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				promise.setSuccess(null);
			}
		}).start();
		latch.await();
	}

	@Test
	public void test2() throws Exception {
		future.onSuccess(new TaskCallback() { // 添加成功结束 Callback
			@Override
			public TaskFuture apply(TaskFuture f) {
				latch.countDown();
				return f;
			}
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				promise.setSuccess(null);
			}
		}).start();
		latch.await();
	}

	@Test
	public void test3() throws Exception {
		future.onFailure(new TaskCallback() {
			@Override
			public TaskFuture apply(TaskFuture f) {
				latch.countDown();
				return f;
			}
		}.andThen(new TaskCallback() {
			@Override
			public TaskFuture apply(TaskFuture f2) {
				latch.countDown();
				return f2;
			}
		}));
		new Thread(new Runnable() {
			@Override
			public void run() {
				promise.setFailure(new IllegalStateException("cm"));
			}
		}).start();
		latch.await();
	}

	@Test
	public void test4() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
				}
				promise.setFailure(new IllegalStateException("cm"));
			}
		}).start();
		future.await();
	}

}
