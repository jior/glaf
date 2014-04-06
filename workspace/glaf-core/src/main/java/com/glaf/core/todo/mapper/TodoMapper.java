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

package com.glaf.core.todo.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.core.todo.query.TodoQuery;
import com.glaf.core.todo.Todo;

@Component
public interface TodoMapper {

	void deleteTodos(TodoQuery query);

	void deleteTodoById(Long id);

	Todo getTodoById(Long id);

	Todo getTodoByCode(String code);

	int getTodoCount(TodoQuery query);

	List<Todo> getTodos(TodoQuery query);

	List<Todo> getTodoList(TodoQuery query);

	void insertTodo(Todo model);

	void updateTodo(Todo model);

}