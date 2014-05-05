/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glaf.core.util.threads;

import java.util.Map;
import java.util.Queue;

public abstract class Worker implements Runnable {
	// 任务队列，用于取得子任务
	protected Queue<Object> workQueue;
	// 子任务处理结果集
	protected Map<String, Object> resultMap;

	// 子任务处理的逻辑，在子类中实现具体逻辑
	public abstract Object process(Object input);

	@Override
	public void run() {
		while (true) {
			// 获取子任务
			Object input = workQueue.poll();
			if (input == null) {
				break;
			}
			// 处理子任务
			Object ret = process(input);
			// 将处理结果写入结果集
			resultMap.put(Integer.toString(input.hashCode()), ret);
		}
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public void setWorkQueue(Queue<Object> workQueue) {
		this.workQueue = workQueue;
	}
}
