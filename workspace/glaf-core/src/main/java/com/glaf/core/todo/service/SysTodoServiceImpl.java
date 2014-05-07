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

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.security.LoginContext;
import com.glaf.core.todo.Todo;
import com.glaf.core.todo.TodoTotal;
import com.glaf.core.todo.mapper.TodoMapper;
import com.glaf.core.todo.query.TodoQuery;
import com.glaf.core.todo.util.TodoJsonFactory;
import com.glaf.core.todo.util.TodoUtils;
import com.glaf.core.todo.util.TodoXmlReader;

@Service("sysTodoService")
@Transactional(readOnly = true)
public class SysTodoServiceImpl implements ISysTodoService {
	protected final static Log logger = LogFactory
			.getLog(SysTodoServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected TodoMapper todoMapper;

	public SysTodoServiceImpl() {

	}

	public int count(TodoQuery query) {
		query.ensureInitialized();
		return todoMapper.getTodoCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		todoMapper.deleteTodoById(id);
	}

	public Todo getTodo(Long todoId) {
		String cacheKey = "todo_" + todoId;
		if (SystemConfig.getBoolean("use_query_cache")
				&& CacheFactory.getString(cacheKey) != null) {
			String text = (String) CacheFactory.getString(cacheKey);
			JSONObject jsonObject = JSON.parseObject(text);
			Todo model = TodoJsonFactory.jsonToObject(jsonObject);
			return model;
		}
		Todo todo = todoMapper.getTodoById(todoId);
		if (SystemConfig.getBoolean("use_query_cache") && todo != null) {
			CacheFactory.put(cacheKey, todo.toJsonObject().toJSONString());
		}
		return todo;
	}

	public Todo getTodoByCode(String code) {
		String cacheKey = "todo_code_" + code;
		if (SystemConfig.getBoolean("use_query_cache")
				&& CacheFactory.getString(cacheKey) != null) {
			String text = CacheFactory.getString(cacheKey);
			JSONObject jsonObject = JSON.parseObject(text);
			Todo todo = TodoJsonFactory.jsonToObject(jsonObject);
			return todo;
		}
		Todo todo = todoMapper.getTodoByCode(code);
		if (SystemConfig.getBoolean("use_query_cache") && todo != null) {
			CacheFactory.put(cacheKey, todo.toJsonObject().toJSONString());
		}

		return todo;
	}

	public List<Todo> getTodoList() {
		TodoQuery query = new TodoQuery();
		query.locked(0);
		return this.list(query);
	}

	public Map<Long, Todo> getTodoMap() {
		List<Todo> todos = this.getTodoList();
		Map<Long, Todo> todoMap = new java.util.HashMap<Long, Todo>();
		for (Todo todo : todos) {
			todoMap.put(todo.getId(), todo);
		}
		return todoMap;
	}

	public List<TodoTotal> getTodoTotalList(LoginContext loginContext,
			Map<String, Object> params) {
		List<TodoTotal> list = new java.util.ArrayList<TodoTotal>();
		List<Todo> todoList = this.getTodoList();
		for (Todo todo : todoList) {
			if (!StringUtils.equals(todo.getProvider(), "mybatis3")) {
				continue;
			}

			String queryId = todo.getSql();
			if (StringUtils.isNotEmpty(queryId) && loginContext != null
					&& params != null) {
				params.put("actorId", loginContext.getActorId());
				params.put("deptId", loginContext.getUser().getDeptId());
				params.put("agentIds", loginContext.getAgents());
				params.put("roles", loginContext.getRoles());
				params.put("permissions", loginContext.getPermissions());
				params.put("user", loginContext.getUser());

				List<?> rows = sqlSession.selectList(queryId, params);
				if (rows != null && !rows.isEmpty()) {
					TodoTotal total = new TodoTotal();
					total.setTodo(todo);
					for (Object object : rows) {
						if (object instanceof String) {
							String rowId = (String) object;
							total.getRowIds().add(rowId);
						} else if (object instanceof Integer) {
							Integer rowId = (Integer) object;
							total.getRowIds().add(rowId);
						} else if (object instanceof Long) {
							Long rowId = (Long) object;
							total.getRowIds().add(rowId);
						} else {
							Object rowId = object;
							total.getRowIds().add(rowId);
						}
					}
					list.add(total);
				}
			}
		}
		return list;
	}

	public List<Todo> list(TodoQuery query) {
		query.ensureInitialized();
		List<Todo> list = todoMapper.getTodos(query);
		return list;
	}

	@Transactional
	public void locked(Long todoId, int locked) {
		Todo todo = this.getTodo(todoId);
		if (todo != null) {
			todo.setEnableFlag(locked);
			todoMapper.updateTodo(todo);
			String cacheKey = "todo_code_" + todo.getCode();
			CacheFactory.remove(cacheKey);
			cacheKey = "todo_" + todo.getId();
			CacheFactory.remove(cacheKey);
		}
	}

	@Transactional
	public void reloadConfig() throws Exception {
		Map<Long, Todo> todoMap = new LinkedHashMap<Long, Todo>();
		List<Todo> rows = this.getTodoList();
		if (rows != null && rows.size() > 0) {
			Iterator<Todo> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Todo todo = iterator.next();
				todoMap.put(todo.getId(), todo);
			}
		}
		TodoXmlReader reader = new TodoXmlReader();
		String configPath = SystemProperties.getConfigRootPath()
				+ TodoUtils.TODO_CONFIG;
		File file = new File(configPath);
		if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				String filename = filelist[i];
				if (filename.toLowerCase().endsWith(".xml")) {
					Resource resource = new FileSystemResource(configPath
							+ filename);

					List<Todo> todos = reader.read(resource.getInputStream());
					Iterator<Todo> iterator = todos.iterator();
					while (iterator.hasNext()) {
						Todo todo = iterator.next();
						Todo model = todoMap.get(todo.getId());
						if (model != null) {
							if (model.getConfigFlag() != 1) {
								this.deleteById(model.getId());
								if (todo.getId() == null) {
									todo.setId(idGenerator.nextId());
								}
								this.save(todo);
							}
						} else {

							this.save(todo);
						}
					}

				}
			}
		}
	}

	@Transactional
	public void save(Todo todo) {
		Todo model = this.getTodo(todo.getId());
		if (model == null) {
			if (todo.getId() == null) {
				todo.setId(idGenerator.nextId());
			}
			todoMapper.insertTodo(todo);
		} else {
			todoMapper.updateTodo(todo);
		}
		String cacheKey = "todo_code_" + todo.getCode();
		CacheFactory.remove(cacheKey);
		cacheKey = "todo_" + todo.getId();
		CacheFactory.remove(cacheKey);
	}

	@Transactional
	public void saveAll(Collection<Todo> todos) {
		for (Todo todo : todos) {
			this.save(todo);
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@javax.annotation.Resource
	public void setTodoMapper(TodoMapper todoMapper) {
		this.todoMapper = todoMapper;
	}

}