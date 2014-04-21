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

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.TableModel;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.service.*;
import com.glaf.base.modules.todo.TodoConstants;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.todo.Todo;
import com.glaf.core.todo.TodoInstance;
import com.glaf.core.todo.mapper.TodoInstanceMapper;
import com.glaf.core.todo.mapper.TodoMapper;
import com.glaf.core.todo.query.TodoInstanceQuery;
import com.glaf.core.todo.query.TodoQuery;
import com.glaf.core.todo.service.ISysTodoService;
import com.glaf.core.todo.util.TodoJsonFactory;
import com.glaf.core.util.Tools;
import com.glaf.base.modules.todo.model.UserEntity;

@Service("todoService")
@Transactional(readOnly = true)
@SuppressWarnings({ "unchecked", "rawtypes" })
public class TodoServiceMyBatisImpl implements TodoService {

	private final static Log logger = LogFactory
			.getLog(TodoServiceMyBatisImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected SysDepartmentService sysDepartmentService;

	protected SysRoleService sysRoleService;

	protected ISysTodoService sysTodoService;

	protected SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	protected ITableDataService tableDataService;

	protected ITablePageService tablePageService;

	protected TodoInstanceMapper todoInstanceMapper;

	protected TodoMapper todoMapper;

	protected WorkCalendarService workCalendarService;

	@Transactional
	public void create(Todo todo) {
		sysTodoService.save(todo);
	}

	@Transactional
	public void createTasks(Collection processInstanceIds, List rows) {
		if (processInstanceIds != null && processInstanceIds.size() > 0) {
			TableModel table = new TableModel();
			table.setTableName("SYS_TODO_INSTANCE");
			table.addStringColumn("provider", "jbpm");
			table.addCollectionColumn("processInstanceId", processInstanceIds);
			tableDataService.deleteTableData(table);
		}
		if (rows.size() > 0) {
			logger.info("---------->rows size:" + rows.size());

		}
	}

	@Transactional
	public void createTasks(String processInstanceId, List rows) {
		List processInstanceIds = new java.util.ArrayList();
		processInstanceIds.add(processInstanceId);
		TableModel table = new TableModel();
		table.setTableName("SYS_TODO_INSTANCE");
		table.addStringColumn("provider", "jbpm");
		table.addCollectionColumn("processInstanceId", processInstanceIds);
		tableDataService.deleteTableData(table);

		if (rows.size() > 0) {
			logger.info("---------->rows size:" + rows.size());

		}
	}

	@Transactional
	public void createTasksOfSQL(List rows) {
		TableModel table = new TableModel();
		table.setTableName("SYS_TODO_INSTANCE");
		table.addStringColumn("provider", "sql");
		tableDataService.deleteTableData(table);

		if (rows.size() > 0) {

		}
	}

	@Transactional
	public void createTasksOfWorkflow(List rows) {
		TableModel table = new TableModel();
		table.setTableName("SYS_TODO_INSTANCE");
		table.addStringColumn("provider", "jbpm");
		tableDataService.deleteTableData(table);
		if (rows.size() > 0) {
			logger.info("---------->rows size:" + rows.size());

		}
	}

	@Transactional
	public void createTasksOfWorkflow(String actorId, List rows) {
		TableModel table = new TableModel();
		table.setTableName("SYS_TODO_INSTANCE");
		table.addStringColumn("actorId", actorId);
		table.addStringColumn("provider", "sql");
		tableDataService.deleteTableData(table);

		if (rows.size() > 0) {
			logger.info("---------->rows size:" + rows.size());

		}
	}

	@Transactional
	public void createTodoInstances(long todoId, List rows) {
		TableModel table = new TableModel();
		table.setTableName("SYS_TODO_INSTANCE");
		table.addLongColumn("todoId", todoId);
		table.addStringColumn("provider", "sql");
		tableDataService.deleteTableData(table);

		if (rows.size() > 0) {

		}
	}

	public List getAllTodoList() {
		TodoQuery query = new TodoQuery();
		return todoMapper.getTodoList(query);
	}

	public Map getDepartmentMap() {
		Map rowMap = new java.util.HashMap();
		List roles = sysDepartmentService.getSysDepartmentList();
		if (roles != null && roles.size() > 0) {
			Iterator iterator = roles.iterator();
			while (iterator.hasNext()) {
				SysDepartment model = (SysDepartment) iterator.next();
				rowMap.put(model.getId(), model);
			}
		}
		return rowMap;
	}

	public Map getEnabledTodoMap() {
		TodoQuery query = new TodoQuery();
		query.setEnableFlag(1);
		Map rowMap = new LinkedHashMap();
		List rows = todoMapper.getTodoList(query);
		if (rows != null && rows.size() > 0) {
			Iterator iterator = rows.iterator();
			while (iterator.hasNext()) {
				Todo todo = (Todo) iterator.next();
				rowMap.put(todo.getId(), todo);
			}
		}
		return rowMap;
	}

	public SysDepartment getParentDepartment(long id) {
		SysDepartment parent = null;
		SysDepartment node = (SysDepartment) sysDepartmentService
				.getSysDepartment(id);
		if (node != null) {
			SysTree tree = node.getNode();
			if (tree.getParentId() != 0) {
				SysTree treeParent = sysTreeService
						.findById(tree.getParentId());
				parent = treeParent.getDepartment();
			}
		}
		return parent;
	}

	public Map getRoleMap() {
		Map roleMap = new java.util.HashMap();
		List roles = sysRoleService.getSysRoleList();
		if (roles != null && roles.size() > 0) {
			Iterator iterator = roles.iterator();
			while (iterator.hasNext()) {
				SysRole sysRole = (SysRole) iterator.next();
				roleMap.put(sysRole.getId(), sysRole);
			}
		}
		return roleMap;
	}

	public List getSQLTodos() {
		TodoQuery query = new TodoQuery();
		query.setEnableFlag(1);
		query.setType("sql");
		List rows = todoMapper.getTodoList(query);
		return rows;
	}

	public Todo getTodo(long todoId) {
		String cacheKey = "todo_" + todoId;
		if (CacheFactory.get(cacheKey) != null) {
			String text = (String) CacheFactory.get(cacheKey);
			JSONObject jsonObject = JSON.parseObject(text);
			Todo model = TodoJsonFactory.jsonToObject(jsonObject);
			return model;
		}
		Todo todo = todoMapper.getTodoById(todoId);
		if (todo != null) {
			CacheFactory.put(cacheKey, todo.toJsonObject().toJSONString());
		}
		return todo;
	}

	public List getTodoInstanceList(TodoQuery query) {
		String actorIdx = query.getActorIdx();
		List rows = getUserEntityList(actorIdx);
		if (rows != null && rows.size() > 0) {

			boolean isDeptAdmin = false;
			boolean isStockTopManager = false;

			List roleIds = new java.util.ArrayList();
			List deptIds = new java.util.ArrayList();
			List actorxIds = new java.util.ArrayList();

			actorxIds.add(actorIdx);

			Collection roles = sysUserService.findByAccount(actorIdx)
					.getRoles();
			if (roles != null && roles.size() > 0) {
				Iterator iteratorxy = roles.iterator();
				while (iteratorxy.hasNext()) {
					SysDeptRole sysDeptRole = (SysDeptRole) iteratorxy.next();
					SysRole role = sysDeptRole.getRole();
					SysDepartment dept = sysDeptRole.getDept();
					roleIds.add(role.getId());
					deptIds.add(dept.getId());
				}
			}

			logger.debug("@@deptIds:" + deptIds);
			logger.debug("@@roleIds:" + roleIds);

			Iterator iterator = rows.iterator();
			while (iterator.hasNext()) {
				UserEntity entity = (UserEntity) iterator.next();
				roleIds.add(entity.getRoleId());
				deptIds.add(entity.getDeptId());
				actorxIds.add(entity.getActorId());

				if (entity.getRoleId() == 6 || entity.getRoleId() == 1) {
					isDeptAdmin = true;
					List list = new java.util.ArrayList();
					SysDepartment node = sysDepartmentService
							.getSysDepartment(entity.getDeptId());
					sysDepartmentService.findNestingDepartment(list, node);
					if (list != null && list.size() > 0) {
						Iterator iter007 = list.iterator();
						while (iter007.hasNext()) {
							SysDepartment dept = (SysDepartment) iter007.next();
							deptIds.add(dept.getId());
						}
					}
				}
			}

			if (!isDeptAdmin) {
				query.roleIds(roleIds);
			}

			if (!isStockTopManager) {
				query.deptIds(deptIds);
			}

			if (!(isDeptAdmin || isStockTopManager)) {
				query.actorIds(actorxIds);
			}

		}

		return null;
	}

	public List getTodoList() {
		TodoQuery query = new TodoQuery();
		query.setEnableFlag(1);
		List rows = todoMapper.getTodoList(query);
		return rows;
	}

	public Map getTodoMap() {
		Map rowMap = new LinkedHashMap();
		TodoQuery query = new TodoQuery();
		query.setEnableFlag(1);
		List rows = todoMapper.getTodoList(query);
		if (rows != null && rows.size() > 0) {
			Iterator iterator = rows.iterator();
			while (iterator.hasNext()) {
				Todo todo = (Todo) iterator.next();
				rowMap.put(todo.getId(), todo);
				rowMap.put(todo.getCode(), todo);
				String processName = todo.getProcessName();
				String taskName = todo.getTaskName();
				if (processName != null && taskName != null) {
					String key001 = processName + "_" + taskName;
					String key = processName + "_" + taskName + "_"
							+ todo.getDeptId();
					rowMap.put(key001, todo);
					rowMap.put(key, todo);
				}
			}
		}
		return rowMap;
	}

	public SysUser getUser(String actorId) {
		SysUser user = (SysUser) sysUserService.findByAccountWithAll(actorId);
		return user;
	}

	public List getUserEntityList(String actorId) {
		if (actorId == null) {
			return null;
		}
		List<UserEntity> rows = new java.util.ArrayList<UserEntity>();
		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT distinct d.account actorId, b.deptId deptId, b.sysRoleId roleId ")
				.append(" FROM SYS_USER_ROLE a  ")
				.append(" INNER JOIN SYS_DEPT_ROLE b ON a.roleId = b.id  ")
				.append(" INNER JOIN SYS_ROLE c ON b.sysRoleId = c.id ")
				.append(" INNER JOIN SYS_USER d ON a.userId = d.id ")
				.append(" WHERE ( d.account = '").append(actorId)
				.append("' ) ");
		Map<String, Object> params = new java.util.HashMap<String, Object>();
		List<Map<String, Object>> list = tablePageService.getListData(
				sb.toString(), params);
		if (list != null && !list.isEmpty()) {
			for (Map<String, Object> dataMap : list) {
				UserEntity model = new UserEntity();
				Tools.populate(model, dataMap);
				rows.add(model);
			}
		}
		return rows;
	}

	public Map getUserMap() {
		Map userMap = new java.util.HashMap();
		List users = sysUserService.getSysUserWithDeptList();
		if (users != null && users.size() > 0) {
			Iterator iterator = users.iterator();
			while (iterator.hasNext()) {
				SysUser sysUser = (SysUser) iterator.next();
				userMap.put(sysUser.getAccount(), sysUser);
			}
		}
		return userMap;
	}

	public Map getUserRoleMap(String actorId) {
		Map roleMap = new java.util.HashMap();
		Collection roles = sysUserService.findByAccount(actorId).getRoles();
		if (roles != null && roles.size() > 0) {
			Iterator iterator = roles.iterator();
			while (iterator.hasNext()) {
				SysDeptRole sysDeptRole = (SysDeptRole) iterator.next();
				SysRole role = sysDeptRole.getRole();
				logger.debug("role.getCode():" + role.getCode());
				roleMap.put(role.getCode(), role);
			}
		}
		return roleMap;
	}

	public Map getUserTodoMap(String actorId) {
		Map todoMap = new java.util.HashMap();
		Map roleMap = new java.util.HashMap();
		Collection roles = sysUserService.findByAccountWithAll(actorId)
				.getRoles();
		if (roles != null && roles.size() > 0) {
			Iterator iterator = roles.iterator();
			while (iterator.hasNext()) {
				SysDeptRole sysDeptRole = (SysDeptRole) iterator.next();
				SysRole role = sysRoleService.findById(sysDeptRole
						.getSysRoleId());
				logger.debug("role.getCode():" + role.getCode());
				roleMap.put(role.getCode(), role.getCode());
			}
		}

		List todoList = this.getTodoList();
		if (todoList != null && todoList.size() > 0) {
			Iterator iterator = todoList.iterator();
			while (iterator.hasNext()) {
				Todo todo = (Todo) iterator.next();
				if (roleMap.containsKey(todo.getRoleCode())) {
					todoMap.put(todo.getId(), todo);
				}
			}
		}
		return todoMap;
	}

	public boolean hasTask(String actorId, String taskInstanceId) {
		TodoInstanceQuery query = new TodoInstanceQuery();
		query.setActorId(actorId);
		query.setTaskInstanceId(taskInstanceId);
		List rows = todoInstanceMapper.getTodoInstances(query);
		if (rows != null && rows.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean isWorkDate(java.util.Date date) {
		return workCalendarService.checkWorkDate(date);
	}

	public Collection populate(Collection rows, Map todoMap) {
		Map dataMap = new java.util.HashMap();
		if (rows != null && rows.size() > 0) {
			Iterator iterator008 = rows.iterator();
			while (iterator008.hasNext()) {
				TodoInstance tdi = (TodoInstance) iterator008.next();
				int status = TodoConstants.getTodoStatus(tdi);
				TodoInstance xx = (TodoInstance) dataMap.get(new Long(tdi
						.getTodoId()));
				if (xx == null) {
					xx = new TodoInstance();
					xx.setTodoId(tdi.getTodoId());
					Todo todo = (Todo) todoMap.get(new Long(tdi.getTodoId()));
					if (todo != null) {
						xx.setTitle(todo.getTitle());
						xx.setContent(todo.getContent());
						xx.setLink(todo.getLink());
						xx.setListLink(todo.getListLink());
					}
				}
				switch (status) {
				case TodoConstants.OK_STATUS:
					xx.setQty01(xx.getQty01() + 1);
					break;
				case TodoConstants.CAUTION_STATUS:
					xx.setQty02(xx.getQty02() + 1);
					break;
				case TodoConstants.PAST_DUE_STATUS:
					xx.setQty03(xx.getQty03() + 1);
					break;
				default:
					break;
				}
				dataMap.put(tdi.getTodoId(), xx);
			}
		}
		return dataMap.values();
	}

	@Transactional
	public void saveAll(List<Todo> rows) {
		if (rows != null && !rows.isEmpty()) {
			for (Todo todo : rows) {
				if (this.getTodo(todo.getId()) == null) {
					todo.setEnableFlag(1);
					todo.setVersionNo(System.currentTimeMillis());
					this.create(todo);
				} else {
					this.update(todo);
				}
			}
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
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	@javax.annotation.Resource
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	@javax.annotation.Resource
	public void setSysTodoService(ISysTodoService sysTodoService) {
		this.sysTodoService = sysTodoService;
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	@javax.annotation.Resource
	public void setTablePageService(ITablePageService tablePageService) {
		this.tablePageService = tablePageService;
	}

	@javax.annotation.Resource
	public void setTodoInstanceMapper(TodoInstanceMapper todoInstanceMapper) {
		this.todoInstanceMapper = todoInstanceMapper;
	}

	@javax.annotation.Resource
	public void setTodoMapper(TodoMapper todoMapper) {
		this.todoMapper = todoMapper;
	}

	@javax.annotation.Resource
	public void setWorkCalendarService(WorkCalendarService workCalendarService) {
		this.workCalendarService = workCalendarService;
	}

	@Transactional
	public void update(Todo todo) {
		todoMapper.updateTodo(todo);
	}

}