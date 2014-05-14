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

package com.glaf.core.todo.config;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.todo.Todo;
import com.glaf.core.todo.service.*;

public class TodoConfig {
	protected final static Log logger = LogFactory.getLog(TodoConfig.class);

	protected final static ConcurrentMap<String, Todo> todoMap = new ConcurrentHashMap<String, Todo>();

	protected static AtomicBoolean loading = new AtomicBoolean(false);

	public static String getLink(String code) {
		String ret = null;
		if (todoMap.isEmpty()) {
			reload();
		}
		Todo todo = todoMap.get(code);
		if (todo != null) {
			ret = todo.getLink();
		}
		return ret;
	}

	public static String getListLink(String code) {
		String ret = null;
		if (todoMap.isEmpty()) {
			reload();
		}
		Todo todo = todoMap.get(code);
		if (todo != null) {
			ret = todo.getListLink();
		}
		return ret;
	}

	public static Todo getTodo(String code) {
		if (todoMap.isEmpty()) {
			reload();
		}
		Todo todo = todoMap.get(code);
		if (todo != null) {

		}
		return todo;
	}

	public static Map<String, Todo> getTodoMap() {
		return todoMap;
	}

	public static void reload() {
		if (!loading.get()) {
			try {
				loading.set(true);
				ISysTodoService todoService = ContextFactory
						.getBean("sysTodoService");
				List<Todo> list = todoService.getTodoList();
				if (list != null && !list.isEmpty()) {
					for (Todo todo : list) {
						todoMap.put(todo.getCode(), todo);
						todoMap.put(String.valueOf(todo.getId()), todo);
						if (StringUtils.isNotEmpty(todo.getProcessName())) {
							String key = todo.getProcessName();
							if (!todoMap.containsKey(key)) {
								todoMap.put(key, todo);
							}
							if (StringUtils.isNotEmpty(todo.getTaskName())) {
								key = key + "_" + todo.getTaskName();
								if (!todoMap.containsKey(key)) {
									todoMap.put(key, todo);
								}
							}
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			} finally {
				loading.set(false);
			}
		}
	}

	public static void setProperty(Todo todo) {
		if (todo != null && todo.getCode() != null) {
			todoMap.put(todo.getCode(), todo);
			todoMap.put(String.valueOf(todo.getId()), todo);
			if (StringUtils.isNotEmpty(todo.getProcessName())) {
				String key = todo.getProcessName();
				if (!todoMap.containsKey(key)) {
					todoMap.put(key, todo);
				}
				if (StringUtils.isNotEmpty(todo.getTaskName())) {
					key = key + "_" + todo.getTaskName();
					if (!todoMap.containsKey(key)) {
						todoMap.put(key, todo);
					}
				}
			}
		}
	}

}