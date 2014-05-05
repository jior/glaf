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

package com.glaf.activiti.executionlistener.factory;

import org.activiti.engine.delegate.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.KeyedObjectPoolFactory;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.apache.commons.pool.impl.StackKeyedObjectPoolFactory;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.LogUtils;
import com.glaf.core.config.CustomProperties;
 
 

@SuppressWarnings({"rawtypes","unchecked"})
public class ExecutionListenerFactory {
	private static KeyedPoolableObjectFactory factory = new PooledObjectFactory();
	private static KeyedObjectPoolFactory poolFactory = new StackKeyedObjectPoolFactory(
			factory, 50, 5);
	private static KeyedObjectPool pool = poolFactory.createPool();
	// 自定义ExecutionListener实现类执行调用类型，取值为spring或pool
	private final static String EXECUTION_LISTENER_FACTORY_TYPE = "activitiExecutionListenerFactoryType";

	public static void notify(String key, DelegateExecution execution) {
		String executionListenerType = "spring";
		if (StringUtils.isNotEmpty(SystemProperties
				.getString(EXECUTION_LISTENER_FACTORY_TYPE))) {
			executionListenerType = SystemProperties
					.getString(EXECUTION_LISTENER_FACTORY_TYPE);
		}
		if (StringUtils.isNotEmpty(CustomProperties
				.getString(EXECUTION_LISTENER_FACTORY_TYPE))) {
			executionListenerType = CustomProperties
					.getString(EXECUTION_LISTENER_FACTORY_TYPE);
		}

		key = key.trim();

		ExecutionListener executionListener = null;
		if (StringUtils.equals(executionListenerType, "spring")) {
			executionListener = (ExecutionListener) ExecutionListenerBeanFactory
					.getBean(key);
			if (executionListener != null) {
				try {
					executionListener.notify(execution);
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
		} else {
			try {
				executionListener = (ExecutionListener) pool.borrowObject(key);
				if (executionListener != null) {
					executionListener.notify(execution);
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			} finally {
				if (executionListener != null) {
					try {
						pool.returnObject(key, executionListener);
					} catch (Exception ex) {
						if (LogUtils.isDebug()) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}
}