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

package com.glaf.core.future;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface TaskFuture extends Future<Object> {

	TaskFuture addAttribute(String key, Object value);

	/**
	 * 等待直到任务完成
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	TaskFuture await() throws InterruptedException;

	/**
	 * 最大等待超时时间，如果任务没完成返回 false, 如果完成则返回 true
	 * 
	 * @param timeout
	 *            最大等待超时时间
	 * @param unit
	 *            超时时间单位
	 * @return
	 * @throws InterruptedException
	 */
	boolean await(long timeout, TimeUnit unit) throws InterruptedException;

	/**
	 * 获取任务失败原因
	 * 
	 * @return
	 */
	Throwable cause();

	Object getAttribute(String key);

	/**
	 * 如果任务完成，则返回任务结果，如果任务失败或者未完成则直接返回 null
	 * 
	 * @return
	 */
	Object getNow();

	boolean hasAttribute(String key);

	/**
	 * 检查任务是否成功完成
	 * 
	 * @return
	 */
	boolean isSuccess();

	/**
	 * 任务完成时回调动作
	 * 
	 * @param callback
	 *            回调动作
	 * @return
	 */
	TaskFuture onComplete(TaskCallback callback);

	/**
	 * 任务失败时回调动作
	 * 
	 * @param callback
	 *            回调动作
	 * @return
	 */
	TaskFuture onFailure(TaskCallback callback);

	/**
	 * 任务成功时回调动作
	 * 
	 * @param callback
	 *            回调动作
	 * @return
	 */
	TaskFuture onSuccess(TaskCallback callback);

	Object removeAttribute(String key);

}
