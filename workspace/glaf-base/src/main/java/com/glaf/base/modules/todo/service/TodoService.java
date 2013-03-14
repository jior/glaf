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
	 * ����Todo
	 * 
	 * @param todo
	 */
	@Transactional
	void create(Todo todo);

	/**
	 * �ؽ�ָ������ʵ���Ĵ�������
	 * 
	 * @param processInstanceIds
	 * @param rows
	 */
	@Transactional
	void createTasks(Collection<String> processInstanceIds,
			List<TodoInstance> rows);

	/**
	 * �ؽ�ָ������ʵ���Ĵ�������
	 * 
	 * @param processInstanceId
	 * @param rows
	 */
	@Transactional
	void createTasks(String processInstanceId, List<TodoInstance> rows);

	/**
	 * �ؽ�SQL��ѯ�Ĵ�������
	 * 
	 * @param rows
	 */
	@Transactional
	void createTasksOfSQL(List<TodoInstance> rows);

	/**
	 * �ؽ��������Ĵ�������
	 * 
	 * @param rows
	 */
	@Transactional
	void createTasksOfWorkflow(List<TodoInstance> rows);

	/**
	 * �ؽ�ĳ���û��������Ĵ�������
	 * 
	 * @param actorId
	 * @param rows
	 */
	@Transactional
	void createTasksOfWorkflow(String actorId, List<TodoInstance> rows);

	/**
	 * �ؽ�ĳ��todo�Ĵ�������
	 * 
	 * @param todoId
	 * @param rows
	 */
	@Transactional
	void createTodoInstances(long todoId, List<TodoInstance> rows);

	/**
	 * ��ȡȫ��Todo�б�
	 * 
	 * @return
	 */
	List<Todo> getAllTodoList();

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @return
	 */
	Map<Long, SysDepartment> getDepartmentMap();

	/**
	 * ��ȡĳ�����ŵĸ�����
	 * 
	 * @param id
	 * @return
	 */
	SysDepartment getParentDepartment(long id);

	/**
	 * ��ȡ��ɫ��Ϣ
	 * 
	 * @return
	 */
	Map<Long, SysRole> getRoleMap();

	/**
	 * ��ȡSQL���õ�Todo
	 * 
	 * @return
	 */
	List<Todo> getSQLTodos();

	/**
	 * ����Todo��Ż�ȡTodo
	 * 
	 * @param todoId
	 * @return
	 */
	Todo getTodo(long todoId);

	/**
	 * ���ݲ�����ȡ���������б�
	 * 
	 * @param paramMap
	 * @return
	 */
	List<TodoInstance> getTodoInstanceList(TodoQuery query);

	/**
	 * ��ȡTodo�б����ų��Ѿ����õ�
	 * 
	 * @return
	 */
	List<Todo> getTodoList();

	/**
	 * ��ȡTodo��Ϣ
	 * 
	 * @return
	 */
	Map<String, Todo> getTodoMap();

	/**
	 * ��ȡ���õ�Todo��Ϣ
	 * 
	 * @return
	 */
	Map<Long, Todo> getEnabledTodoMap();

	/**
	 * ��ȡ�û���Ϣ
	 * 
	 * @param actorId
	 * @return
	 */
	SysUser getUser(String actorId);

	/**
	 * ��ȡ�û���Ϣ���������š���ɫ
	 * 
	 * @param actorId
	 * @return
	 */
	List<UserEntity> getUserEntityList(String actorId);

	/**
	 * ��ȡ�û�����
	 * 
	 * @return
	 */
	Map<String, SysUser> getUserMap();

	/**
	 * ��ȡÿ���û��Ľ�ɫ����
	 * 
	 * @param actorId
	 * @return
	 */
	Map<String, SysRole> getUserRoleMap(String actorId);

	/**
	 * ��ȡĳ���û���Todo����
	 * 
	 * @param actorId
	 * @return
	 */
	Map<Long, Todo> getUserTodoMap(String actorId);

	/**
	 * �ж�ĳ���û��Ƿ�������
	 * 
	 * @param actorId
	 * @param taskInstanceId
	 * @return
	 */
	boolean hasTask(String actorId, String taskInstanceId);

	/**
	 * �ж�ĳ�������Ƿ�Ϊ������
	 * 
	 * @param date
	 * @return
	 */
	boolean isWorkDate(java.util.Date date);

	/**
	 * ���������Todo���з���
	 * 
	 * @return
	 */
	Collection<TodoInstance> populate(Collection<TodoInstance> rows,
			Map<Long, Todo> todoMap);

	/**
	 * ��������Todo
	 * 
	 * @param rows
	 */
	@Transactional
	void saveAll(List<Todo> rows);

	/**
	 * �޸�Todo
	 * 
	 * @param todo
	 */
	@Transactional
	void update(Todo todo);
}