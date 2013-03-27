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
import org.springframework.beans.factory.annotation.Qualifier;
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

	protected TodoMapper todoMapper;

	protected TodoInstanceMapper todoInstanceMapper;

	private SysDepartmentService sysDepartmentService;

	private SysRoleService sysRoleService;

	private ISysTodoService sysTodoService;

	private SysUserService sysUserService;

	private SysTreeService sysTreeService;

	private WorkCalendarService workCalendarService;

	private ITableDataService tableDataService;

	private ITablePageService tablePageService;

	@Transactional
	public void create(Todo todo) {
		sysTodoService.save(todo);
	}

	public void createTasks(Collection processInstanceIds, List rows) {
		if (processInstanceIds != null && processInstanceIds.size() > 0) {
			TableModel table = new TableModel();
			table.setTableName("sys_todo_instance");
			table.addColumn("provider", "String", "jbpm");
			table.addCollectionColumn("processInstanceId", processInstanceIds);
			tableDataService.deleteTableData(table);
		}
		if (rows.size() > 0) {
			logger.info("---------->rows size:" + rows.size());

		}
	}

	public void createTasks(String processInstanceId, List rows) {
		List processInstanceIds = new ArrayList();
		processInstanceIds.add(processInstanceId);
		TableModel table = new TableModel();
		table.setTableName("sys_todo_instance");
		table.addColumn("provider", "String", "jbpm");
		table.addCollectionColumn("processInstanceId", processInstanceIds);
		tableDataService.deleteTableData(table);

		if (rows.size() > 0) {
			logger.info("---------->rows size:" + rows.size());

		}
	}

	public void createTasksOfSQL(List rows) {
		TableModel table = new TableModel();
		table.setTableName("sys_todo_instance");
		table.addColumn("provider", "String", "sql");
		tableDataService.deleteTableData(table);

		if (rows.size() > 0) {

		}
	}

	public void createTasksOfWorkflow(List rows) {
		TableModel table = new TableModel();
		table.setTableName("sys_todo_instance");
		table.addColumn("provider", "String", "jbpm");
		tableDataService.deleteTableData(table);
		if (rows.size() > 0) {
			logger.info("---------->rows size:" + rows.size());

		}
	}

	public void createTasksOfWorkflow(String actorId, List rows) {
		TableModel table = new TableModel();
		table.setTableName("sys_todo_instance");
		table.addColumn("actorId", "String", actorId);
		table.addColumn("provider", "String", "sql");
		tableDataService.deleteTableData(table);

		if (rows.size() > 0) {
			logger.info("---------->rows size:" + rows.size());

		}
	}

	public void createTodoInstances(long todoId, List rows) {
		TableModel table = new TableModel();
		table.setTableName("sys_todo_instance");
		table.addColumn("todoId", "Long", todoId);
		table.addColumn("provider", "String", "sql");
		tableDataService.deleteTableData(table);

		if (rows.size() > 0) {

		}
	}

	public List getAllTodoList() {
		TodoQuery query = new TodoQuery();
		return todoMapper.getTodoList(query);
	}

	public Map getDepartmentMap() {
		Map rowMap = new HashMap();
		List roles = sysDepartmentService.getSysDepartmentList();
		if (roles != null && roles.size() > 0) {
			Iterator iterator = roles.iterator();
			while (iterator.hasNext()) {
				SysDepartment model = (SysDepartment) iterator.next();
				rowMap.put(Long.valueOf(model.getId()), model);
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
				rowMap.put(Long.valueOf(todo.getId()), todo);
			}
		}
		return rowMap;
	}

	public SysDepartment getParentDepartment(long id) {
		SysDepartment parent = null;
		SysDepartment node = (SysDepartment) sysDepartmentService.findById(id);
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
		Map roleMap = new HashMap();
		List roles = sysRoleService.getSysRoleList();
		if (roles != null && roles.size() > 0) {
			Iterator iterator = roles.iterator();
			while (iterator.hasNext()) {
				SysRole sysRole = (SysRole) iterator.next();
				roleMap.put(new Long(sysRole.getId()), sysRole);
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
		Map params = new LinkedHashMap();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" from com.glaf.core.todo.TodoInstance as a where 1=1 ");

		if (query.getActorId() != null) {
			params.put("actorId", query.getActorId());
			buffer.append(" and a.actorId = :actorId ");
		}

		String actorIdx = query.getActorIdx();
		List rows = getUserEntityList(actorIdx);
		if (rows != null && rows.size() > 0) {

			boolean isDeptAdmin = false;
			boolean isStockTopManager = false;

			Set roleIds = new HashSet();
			Set deptIds = new HashSet();
			Set actorxIds = new HashSet();

			actorxIds.add(actorIdx);

			Collection roles = sysUserService.findByAccount(actorIdx)
					.getRoles();
			if (roles != null && roles.size() > 0) {
				Iterator iteratorxy = roles.iterator();
				while (iteratorxy.hasNext()) {
					SysDeptRole sysDeptRole = (SysDeptRole) iteratorxy.next();
					SysRole role = sysDeptRole.getRole();
					SysDepartment dept = sysDeptRole.getDept();
					roleIds.add(Long.valueOf(role.getId()));
					deptIds.add(Long.valueOf(dept.getId()));
				}
			}

			logger.debug("@@deptIds:" + deptIds);
			logger.debug("@@roleIds:" + roleIds);

			Iterator iterator = rows.iterator();
			while (iterator.hasNext()) {
				UserEntity entity = (UserEntity) iterator.next();
				roleIds.add(new Long(entity.getRoleId()));
				deptIds.add(new Long(entity.getDeptId()));
				actorxIds.add(entity.getActorId());

				if (entity.getRoleId() == 6 || entity.getRoleId() == 1) {
					isDeptAdmin = true;
					List list = new ArrayList();
					SysDepartment node = sysDepartmentService.findById(entity
							.getDeptId());
					sysDepartmentService.findNestingDepartment(list, node);
					if (list != null && list.size() > 0) {
						Iterator iter007 = list.iterator();
						while (iter007.hasNext()) {
							SysDepartment dept = (SysDepartment) iter007.next();
							deptIds.add(new Long(dept.getId()));
						}
					}
				}
				if (entity.getRoleId() == 1 && entity.getDeptId() == 134) {
					isStockTopManager = true;
				}
			}

			if (!isDeptAdmin) {
				params.put("roleIds", roleIds);
				buffer.append(" and a.roleId in ( :roleIds ) ");
			}

			if (!isStockTopManager) {
				params.put("deptIds", deptIds);
				buffer.append(" and a.deptId in ( :deptIds ) ");
			}

			if (!(isDeptAdmin || isStockTopManager)) {
				params.put("actorxIds", actorxIds);
				buffer.append(" and a.actorId in ( :actorxIds ) ");
			}

		}

		if (query.getActorIds() != null && !query.getActorIds().isEmpty()) {
			params.put("actorIds", query.getActorIds());
			buffer.append(" and a.actorId in ( :actorIds ) ");
		}

		if (query.getObjectId() != null) {
			params.put("objectId", query.getObjectId());
			buffer.append(" and a.objectId = :objectId ");
		}

		if (query.getObjectIds() != null && !query.getObjectIds().isEmpty()) {
			params.put("objectIds", query.getObjectIds());
			buffer.append(" and a.objectId in ( :objectIds ) ");
		}

		if (query.getObjectValue() != null) {
			params.put("objectValue", query.getObjectValue());
			buffer.append(" and a.objectValue = :objectValue ");
		}

		if (query.getObjectValues() != null
				&& !query.getObjectValues().isEmpty()) {
			params.put("objectValues", query.getObjectValues());
			buffer.append(" and a.objectValue in ( :objectValues ) ");
		}

		if (query.getProcessInstanceId() != null) {
			params.put("processInstanceId", query.getProcessInstanceId());
			buffer.append(" and a.processInstanceId = :processInstanceId ");
		}

		if (query.getProcessInstanceIds() != null
				&& !query.getProcessInstanceIds().isEmpty()) {
			params.put("processInstanceIds", query.getProcessInstanceIds());
			buffer.append(" and a.processInstanceId in ( :processInstanceIds ) ");
		}

		if (query.getProvider() != null) {
			params.put("provider", query.getProvider());
			buffer.append(" and a.provider = :provider ");
		}

		if (query.getRoleId() != null) {
			params.put("roleId", query.getRoleId());
			buffer.append(" and ( a.roleId = :roleId )");
		}

		if (query.getRoleIds() != null && !query.getRoleIds().isEmpty()) {
			params.put("roleIds", query.getRoleIds());
			buffer.append(" and a.roleId in ( :roleIds ) ");
		}

		if (query.getDeptId() != null) {

			params.put("deptId", query.getDeptId());

			buffer.append(" and ( a.deptId = :deptId )");
		}

		if (query.getDeptIds() != null && !query.getDeptIds().isEmpty()) {
			params.put("deptIds", query.getDeptIds());
			buffer.append(" and a.deptId in ( :deptIds ) ");
		}

		if (query.getTodoId() != null) {

			params.put("todoId", query.getTodoId());

			buffer.append(" and ( a.todoId = :todoId )");
		}

		if (query.getTodoIds() != null && !query.getTodoIds().isEmpty()) {
			params.put("todoIds", query.getTodoIds());
			buffer.append(" and a.todoId in ( :todoIds ) ");
		}

		if (query.getAppId() != null) {

			params.put("appId", query.getAppId());

			buffer.append(" and ( a.appId = :appId )");
		}

		if (query.getAppIds() != null && !query.getAppIds().isEmpty()) {
			params.put("appIds", query.getAppIds());
			buffer.append(" and a.appId in ( :appIds ) ");
		}

		if (query.getModuleId() != null) {

			params.put("moduleId", query.getModuleId());

			buffer.append(" and ( a.moduleId = :moduleId )");
		}

		if (query.getModuleIds() != null && !query.getModuleIds().isEmpty()) {
			params.put("moduleIds", query.getModuleIds());
			buffer.append(" and a.moduleId in ( :moduleIds ) ");
		}

		if (query.getAfterCreateDate() != null) {
			params.put("createDate_start", query.getAfterCreateDate());
			buffer.append(" and ( a.createDate >= :createDate_start )");
		}

		if (query.getBeforeCreateDate() != null) {

			params.put("createDate_end", query.getBeforeCreateDate());

			buffer.append(" and ( a.createDate <= :createDate_end )");
		}

		String orderBy = query.getOrderBy();
		if (orderBy != null) {
			buffer.append(" order by a.").append(orderBy).append(" asc ");
		} else {
			buffer.append(" order by a.moduleId asc ");
		}

		logger.debug(buffer.toString());
		logger.debug(params);

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
				rowMap.put(new Long(todo.getId()), todo);
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
		List<UserEntity> rows = new ArrayList<UserEntity>();
		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT distinct d.account actorId, b.deptId deptId, b.sysRoleId roleId ")
				.append(" FROM sys_user_role a  ")
				.append(" INNER JOIN sys_dept_role b ON a.roleId = b.id  ")
				.append(" INNER JOIN sys_role c ON b.sysRoleId = c.id ")
				.append(" INNER JOIN sys_user d ON a.userId = d.id ")
				.append(" WHERE ( d.account = '").append(actorId)
				.append("' ) ");
		Map<String, Object> params = new HashMap<String, Object>();
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
		Map userMap = new HashMap();
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
		Map roleMap = new HashMap();
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
		Map todoMap = new HashMap();
		Map roleMap = new HashMap();
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
					todoMap.put(Long.valueOf(todo.getId()), todo);
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
		Map dataMap = new HashMap();
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
				dataMap.put(Long.valueOf(tdi.getTodoId()), xx);
			}
		}
		return dataMap.values();
	}

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
	@Qualifier("myBatisEntityDAO")
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	@Qualifier("myBatisDbIdGenerator")
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
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@javax.annotation.Resource
	public void setTodoMapper(TodoMapper todoMapper) {
		this.todoMapper = todoMapper;
	}

	@javax.annotation.Resource
	public void setTodoInstanceMapper(TodoInstanceMapper todoInstanceMapper) {
		this.todoInstanceMapper = todoInstanceMapper;
	}

	@javax.annotation.Resource
	public void setWorkCalendarService(WorkCalendarService workCalendarService) {
		this.workCalendarService = workCalendarService;
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
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	public void update(Todo todo) {
		todoMapper.updateTodo(todo);
	}

}