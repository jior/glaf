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

package com.glaf.core.todo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.glaf.core.security.LoginContext;
import com.glaf.core.todo.Todo;
import com.glaf.core.todo.TodoTotal;

public interface ISysTodoService {

	/**
	 * 获取Todo定义
	 * 
	 * @param todoId
	 * @return
	 */
	Todo getTodo(Long todoId);

	/**
	 * 获取Todo定义
	 * 
	 * @param todoId
	 * @return
	 */
	Todo getTodoByCode(String code);

	/**
	 * 获取Todo定义列表
	 * 
	 * @return
	 */
	List<Todo> getTodoList();

	List<TodoTotal> getTodoTotalList(LoginContext loginContext,
			Map<String, Object> params);

	/**
	 * 获取Todo定义Map
	 * 
	 * @return
	 */
	Map<Long, Todo> getTodoMap();

	/**
	 * 锁定或解锁某个TODO
	 * 
	 * @param todoId
	 * @param locked
	 */

	void locked(Long todoId, int locked);

	/**
	 * 重新装载配置文件
	 */

	void reloadConfig() throws Exception;

	/**
	 * 保存TODO
	 * 
	 * @param todo
	 */

	void save(Todo todo);

	/**
	 * 保存多个TODO
	 * 
	 * @param todos
	 */

	void saveAll(Collection<Todo> todos);

}