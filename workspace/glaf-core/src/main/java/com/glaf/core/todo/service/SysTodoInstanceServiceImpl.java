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

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.IdGenerator;
import com.glaf.core.todo.TodoInstance;
import com.glaf.core.todo.mapper.TodoInstanceMapper;
import com.glaf.core.todo.query.TodoInstanceQuery;

@Service("sysTodoInstanceService")
@Transactional(readOnly = true)
public class SysTodoInstanceServiceImpl implements ISysTodoInstanceService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TodoInstanceMapper todoInstanceMapper;

	public SysTodoInstanceServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			todoInstanceMapper.deleteTodoInstanceById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			TodoInstanceQuery query = new TodoInstanceQuery();
			query.rowIds(rowIds);
			todoInstanceMapper.deleteTodoInstances(query);
		}
	}

	public int count(TodoInstanceQuery query) {
		query.ensureInitialized();
		return todoInstanceMapper.getTodoInstanceCount(query);
	}

	public List<TodoInstance> list(TodoInstanceQuery query) {
		query.ensureInitialized();
		List<TodoInstance> list = todoInstanceMapper.getTodoInstances(query);
		return list;
	}

	public int getTodoInstanceCountByQueryCriteria(TodoInstanceQuery query) {
		return todoInstanceMapper.getTodoInstanceCount(query);
	}

	public List<TodoInstance> getTodoInstancesByQueryCriteria(int start,
			int pageSize, TodoInstanceQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TodoInstance> rows = sqlSessionTemplate.selectList(
				"getTodoInstances", query, rowBounds);
		return rows;
	}

	public TodoInstance getTodoInstance(Long id) {
		if (id == null) {
			return null;
		}
		TodoInstance todoInstance = todoInstanceMapper.getTodoInstanceById(id);
		return todoInstance;
	}

	@Transactional
	public void save(TodoInstance todoInstance) {
		if (todoInstance.getId() == 0L) {
			todoInstance.setId(idGenerator.nextId());
			// todoInstance.setCreateDate(new Date());
			todoInstanceMapper.insertTodoInstance(todoInstance);
		} else {
			todoInstanceMapper.updateTodoInstance(todoInstance);
		}
	}

	@javax.annotation.Resource
	public void setLongIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setTodoInstanceMapper(TodoInstanceMapper todoInstanceMapper) {
		this.todoInstanceMapper = todoInstanceMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
