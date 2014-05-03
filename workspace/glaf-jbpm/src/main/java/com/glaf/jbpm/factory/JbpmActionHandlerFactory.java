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

package com.glaf.jbpm.factory;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.KeyedObjectPoolFactory;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.apache.commons.pool.impl.StackKeyedObjectPoolFactory;
import org.jbpm.JbpmException;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.LogUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class JbpmActionHandlerFactory {
	private static KeyedPoolableObjectFactory factory = new JbpmPooledObjectFactory();
	private static KeyedObjectPoolFactory poolFactory = new StackKeyedObjectPoolFactory(
			factory, 50, 5);
	private static KeyedObjectPool pool = poolFactory.createPool();
	// JBPM 自定义ActionHandler实现类执行调用类型，取值为spring或pool
	private final static String JBPM_ACTION_HANDLER_FACTORY_TYPE = "jbpmActionHandlerFactoryType";

	public static void execute(String key, ExecutionContext executionContext) {
		String actionHandlerExecution = "spring";
		if (StringUtils.isNotEmpty(SystemProperties
				.getString(JBPM_ACTION_HANDLER_FACTORY_TYPE))) {
			actionHandlerExecution = SystemProperties
					.getString(JBPM_ACTION_HANDLER_FACTORY_TYPE);
		}
		if (StringUtils.isNotEmpty(CustomProperties
				.getString(JBPM_ACTION_HANDLER_FACTORY_TYPE))) {
			actionHandlerExecution = CustomProperties
					.getString(JBPM_ACTION_HANDLER_FACTORY_TYPE);
		}

		key = key.trim();

		ActionHandler actionHandler = null;
		if (StringUtils.equals(actionHandlerExecution, "spring")) {
			actionHandler = (ActionHandler) JbpmActionHandlerBeanFactory
					.getBean(key);
			if (actionHandler != null) {
				try {
					actionHandler.execute(executionContext);
				} catch (Exception ex) {
					throw new JbpmException(ex);
				}
			}
		} else {
			try {
				actionHandler = (ActionHandler) pool.borrowObject(key);
				if (actionHandler != null) {
					actionHandler.execute(executionContext);
				}
			} catch (Exception ex) {
				throw new JbpmException(ex);
			} finally {
				if (actionHandler != null) {
					try {
						pool.returnObject(key, actionHandler);
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