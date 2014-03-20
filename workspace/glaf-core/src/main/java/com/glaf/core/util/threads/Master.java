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

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {
	// �������
	protected Queue<Object> workQueue = new ConcurrentLinkedQueue<Object>();
	// Worker�̶߳���
	protected Map<String, Thread> threadMap = new HashMap<String, Thread>();
	// ������������
	protected Map<String, Object> resultMap = new ConcurrentHashMap<String, Object>();

	// Master�Ĺ��죬��Ҫһ��Worker�����߼�������Ҫ��Worker��������
	public Master(Worker worker, int countWorker) {
		worker.setWorkQueue(workQueue);
		worker.setResultMap(resultMap);
		for (int i = 0; i < countWorker; i++) {
			threadMap.put(Integer.toString(i),
					new Thread(worker, Integer.toString(i)));
		}
	}

	// ��ʼ�������е�Worker���̣����д���
	public void execute() {
		for (Map.Entry<String, Thread> entry : threadMap.entrySet()) {
			entry.getValue().start();
		}
	}

	// ��������������
	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	// �Ƿ����е������񶼽�����
	public boolean isComplete() {
		for (Map.Entry<String, Thread> entry : threadMap.entrySet()) {
			if (entry.getValue().getState() != Thread.State.TERMINATED) {
				return false;
			}
		}
		return true;
	}

	// �ύһ������
	public void submit(Object job) {
		workQueue.add(job);
	}
}
