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

public interface TaskPromise extends TaskFuture {

	/**
	 * 获取和 Promise 关联的 Future
	 * 
	 * @return
	 */
	TaskFuture getFuture();

	/**
	 * 标记任务完成失败, 如果任务已经完成，则可能抛出 {@link IllegalStateException}
	 * 
	 * @param cause
	 *            失败原因
	 * @return
	 */
	TaskPromise setFailure(Throwable cause);

	/**
	 * 标记任务成功完成, 如果任务已经完成，则可能抛出 {@code IllegalStateException}
	 * 
	 * @param result
	 *            任务结果
	 * @return
	 */
	TaskPromise setSuccess(Object result);

	/**
	 * 尝试标记任务完成失败, 如果任务已经完成，则可能抛出 {@link IllegalStateException}
	 * 
	 * @param cause
	 *            失败原因
	 * @return
	 */
	boolean tryFailure(Throwable cause);

	/**
	 * 尝试标记任务成功完成
	 * 
	 * @param result
	 *            任务结果
	 * @return
	 */
	boolean trySuccess(Object result);
}
