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

package com.glaf.activiti.tasklistener.factory;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.KeyedObjectPoolFactory;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.apache.commons.pool.impl.StackKeyedObjectPoolFactory;

import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.SystemProperties;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TaskListenerFactory {
	private static KeyedPoolableObjectFactory factory = new PooledObjectFactory();
	private static KeyedObjectPoolFactory poolFactory = new StackKeyedObjectPoolFactory(
			factory, 50, 5);
	private static KeyedObjectPool pool = poolFactory.createPool();
	// Activiti 自定义TaskListener实现类执行调用类型，取值为spring或pool
	private final static String EXECUTION_LISTENER_FACTORY_TYPE = "activitiTaskListenerFactoryType";

	public static void notify(String key, DelegateTask delegateTask) {
		String taskListenerType = "spring";
		if (StringUtils.isNotEmpty(SystemProperties
				.getString(EXECUTION_LISTENER_FACTORY_TYPE))) {
			taskListenerType = SystemProperties
					.getString(EXECUTION_LISTENER_FACTORY_TYPE);
		}
		if (StringUtils.isNotEmpty(CustomProperties
				.getString(EXECUTION_LISTENER_FACTORY_TYPE))) {
			taskListenerType = CustomProperties
					.getString(EXECUTION_LISTENER_FACTORY_TYPE);
		}

		key = key.trim();

		TaskListener taskListener = null;
		if (StringUtils.equals(taskListenerType, "spring")) {
			taskListener = (TaskListener) TaskListenerBeanFactory.getBean(key);
			if (taskListener != null) {
				try {
					taskListener.notify(delegateTask);
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
		} else {
			try {
				taskListener = (TaskListener) pool.borrowObject(key);
				if (taskListener != null) {
					taskListener.notify(delegateTask);
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			} finally {
				if (taskListener != null) {
					try {
						pool.returnObject(key, taskListener);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}
}