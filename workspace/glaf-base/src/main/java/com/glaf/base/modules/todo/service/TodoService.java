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

package com.glaf.base.modules.todo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.core.todo.Todo;
import com.glaf.core.todo.TodoInstance;
import com.glaf.core.todo.query.TodoQuery;
import com.glaf.base.modules.todo.model.UserEntity;

@Transactional(readOnly = true)
public interface TodoService {

	/**
	 * 创建Todo
	 * 
	 * @param todo
	 */
	@Transactional
	void create(Todo todo);

	/**
	 * 重建指定流程实例的待办事项
	 * 
	 * @param processInstanceIds
	 * @param rows
	 */
	@Transactional
	void createTasks(Collection<String> processInstanceIds,
			List<TodoInstance> rows);

	/**
	 * 重建指定流程实例的待办事项
	 * 
	 * @param processInstanceId
	 * @param rows
	 */
	@Transactional
	void createTasks(String processInstanceId, List<TodoInstance> rows);

	/**
	 * 重建SQL查询的待办事项
	 * 
	 * @param rows
	 */
	@Transactional
	void createTasksOfSQL(List<TodoInstance> rows);

	/**
	 * 重建工作流的待办事项
	 * 
	 * @param rows
	 */
	@Transactional
	void createTasksOfWorkflow(List<TodoInstance> rows);

	/**
	 * 重建某个用户工作流的待办事项
	 * 
	 * @param actorId
	 * @param rows
	 */
	@Transactional
	void createTasksOfWorkflow(String actorId, List<TodoInstance> rows);

	/**
	 * 重建某个todo的待办事项
	 * 
	 * @param todoId
	 * @param rows
	 */
	@Transactional
	void createTodoInstances(long todoId, List<TodoInstance> rows);

	/**
	 * 获取全部Todo列表
	 * 
	 * @return
	 */
	List<Todo> getAllTodoList();

	/**
	 * 获取部门信息
	 * 
	 * @return
	 */
	Map<Long, SysDepartment> getDepartmentMap();

	/**
	 * 获取某个部门的父部门
	 * 
	 * @param id
	 * @return
	 */
	SysDepartment getParentDepartment(long id);

	/**
	 * 获取角色信息
	 * 
	 * @return
	 */
	Map<Long, SysRole> getRoleMap();

	/**
	 * 获取SQL配置的Todo
	 * 
	 * @return
	 */
	List<Todo> getSQLTodos();

	/**
	 * 根据Todo编号获取Todo
	 * 
	 * @param todoId
	 * @return
	 */
	Todo getTodo(long todoId);

	/**
	 * 根据参数获取待办事项列表
	 * 
	 * @param paramMap
	 * @return
	 */
	List<TodoInstance> getTodoInstanceList(TodoQuery query);

	/**
	 * 获取Todo列表，但排除已经禁用的
	 * 
	 * @return
	 */
	List<Todo> getTodoList();

	/**
	 * 获取Todo信息
	 * 
	 * @return
	 */
	Map<String, Todo> getTodoMap();

	/**
	 * 获取可用的Todo信息
	 * 
	 * @return
	 */
	Map<Long, Todo> getEnabledTodoMap();

	/**
	 * 获取用户信息
	 * 
	 * @param actorId
	 * @return
	 */
	SysUser getUser(String actorId);

	/**
	 * 获取用户信息，包含部门、角色
	 * 
	 * @param actorId
	 * @return
	 */
	List<UserEntity> getUserEntityList(String actorId);

	/**
	 * 获取用户集合
	 * 
	 * @return
	 */
	Map<String, SysUser> getUserMap();

	/**
	 * 获取每个用户的角色集合
	 * 
	 * @param actorId
	 * @return
	 */
	Map<String, SysRole> getUserRoleMap(String actorId);

	/**
	 * 获取某个用户的Todo集合
	 * 
	 * @param actorId
	 * @return
	 */
	Map<Long, Todo> getUserTodoMap(String actorId);

	/**
	 * 判断某个用户是否有任务
	 * 
	 * @param actorId
	 * @param taskInstanceId
	 * @return
	 */
	boolean hasTask(String actorId, String taskInstanceId);

	/**
	 * 判断某个日期是否为工作日
	 * 
	 * @param date
	 * @return
	 */
	boolean isWorkDate(java.util.Date date);

	/**
	 * 将待办事项按Todo进行分组
	 * 
	 * @return
	 */
	Collection<TodoInstance> populate(Collection<TodoInstance> rows,
			Map<Long, Todo> todoMap);

	/**
	 * 批量保存Todo
	 * 
	 * @param rows
	 */
	@Transactional
	void saveAll(List<Todo> rows);

	/**
	 * 修改Todo
	 * 
	 * @param todo
	 */
	@Transactional
	void update(Todo todo);
}