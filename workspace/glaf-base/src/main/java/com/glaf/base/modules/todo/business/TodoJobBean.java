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

package com.glaf.base.modules.todo.business;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.jbpm.model.TaskItem;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.todo.service.TodoService;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.WorkCalendarService;
import com.glaf.core.todo.Todo;
import com.glaf.core.todo.TodoInstance;
import com.glaf.core.todo.query.TodoQuery;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TodoJobBean {
	private final static Log logger = LogFactory.getLog(TodoJobBean.class);

	protected SysUserService sysUserService;

	protected TodoService todoService;

	protected WorkCalendarService workCalendarService;

	public TodoJobBean() {

	}

	public void create(Todo todo) {
		todoService.create(todo);
	}

	public void createTasks(List processInstanceIds) {
		List tasks = this.getJbpmTasksByProcessInstanceIds(processInstanceIds);
		todoService.createTasks(processInstanceIds, tasks);
		logger.info("流程 " + processInstanceIds + " 的任务已经更新,现在有任务:"
				+ tasks.size());
	}

	public void createTasks(Long processInstanceId) {
		List tasks = this.getJbpmTasksByProcessInstanceId(processInstanceId);
		// todoService.createTasks(processInstanceId, tasks);
		logger.info("流程 " + processInstanceId + " 的任务已经更新,现在有任务:"
				+ tasks.size());
	}

	public void createTasksFromSQL() {
		List rows = this.getTasks();
		todoService.createTasksOfSQL(rows);
	}

	public void createTasksFromWorkflow() {
		List tasks = this.getAllJbpmTasks();
		todoService.createTasksOfWorkflow(tasks);
	}

	public void createTasksFromWorkflow(String actorId) {
		List tasks = this.getJbpmTasks(actorId);
		todoService.createTasksOfWorkflow(actorId, tasks);
	}

	public void createTodoInstances(long todoId) {
		Todo todo = todoService.getTodo(todoId);
		Map<String, TodoInstance> rowsMap = new java.util.HashMap<String, TodoInstance>();
		java.sql.Connection conn = null;
		java.sql.PreparedStatement psmt = null;
		java.sql.ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection();
			psmt = conn.prepareStatement(todo.getSql());
			rs = psmt.executeQuery();
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				TodoInstance model = new TodoInstance();
				model.setRowId(rs.getString(1));
				model.setStartDate(rs.getDate(2));
				if (rsmd.getColumnCount() == 3) {
					switch (new Long(todo.getId()).intValue()) {
					case 8005:
					case 7001:
					case 7002:
					case 7003:
					case 7004:
					case 17001:
					case 17010:
					case 18001:
					case 19001:
					case 20001:
					case 20084001:
						model.setDeptId(rs.getLong(3));
						break;
					default:
						model.setActorId(rs.getString(3));
						break;
					}
				}
				rowsMap.put(model.getRowId(), model);
			}
			rs.close();
			psmt.close();
			rs = null;
			psmt = null;
		} catch (java.sql.SQLException ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
			JdbcUtils.close(conn);
		}

		List<TodoInstance> rows = new java.util.ArrayList<TodoInstance>();

		if (rowsMap.size() > 0) {
			Iterator<String> iter = rowsMap.keySet().iterator();
			while (iter.hasNext()) {
				String rowId = (String) iter.next();
				TodoInstance model = (TodoInstance) rowsMap.get(rowId);
				Date startDate = model.getStartDate();
				if (startDate == null) {
					startDate = new java.util.Date();
				}

				model.setProvider("sql");
				model.setLinkType(todo.getLinkType());
				model.setAppId(todo.getAppId());
				model.setModuleId(todo.getModuleId());
				model.setTodoId(todo.getId());
				model.setRoleId(todo.getRoleId());
				model.setRoleCode(todo.getRoleCode());
				model.setTitle(todo.getTitle());
				model.setCreateDate(new Date(System.currentTimeMillis()));
				model.setStartDate(startDate);
				int limitDay = todo.getLimitDay();
				int ahour = todo.getXa();
				int bhour = todo.getXb();

				Date limitWorkDate = workCalendarService.getWorkDate(startDate,
						limitDay);
				long time = limitWorkDate.getTime();

				Date cautionDate = new Date(time - ahour * DateUtils.HOUR);
				Date pastDueDate = new Date(time + bhour * DateUtils.HOUR);
				model.setAlarmDate(cautionDate);
				model.setPastDueDate(pastDueDate);
				model.setRowId(rowId);
				model.setVersionNo(System.currentTimeMillis());
				rows.add(model);
			}
		}

		todoService.createTodoInstances(todoId, rows);

	}

	public void execute() {
		this.createTasksFromWorkflow();
	}

	public List getAllJbpmTasks() {
		List rows = new java.util.ArrayList();
		List taskItems = ProcessContainer.getContainer().getAllTaskItems();
		if (taskItems != null && taskItems.size() > 0) {
			logger.info("---------->taskItems size:" + taskItems.size());
			rows = this.getTodoInstances(taskItems);
		}
		return rows;
	}

	public List getAllTodoList() {
		return todoService.getAllTodoList();
	}

	public Collection getAllUsers() {
		Collection users = sysUserService.getSysUserList();
		return users;
	}

	public Collection getAppActorIds(String appId) {
		Collection actorIds = new HashSet();
		List<SysUser> users = sysUserService.getSysUsersByAppId(Long
				.parseLong(appId));
		if (users != null && !users.isEmpty()) {
			for (SysUser user : users) {
				actorIds.add(user.getAccount());
			}
		}
		return actorIds;
	}

	public Map getDepartmentMap() {
		return todoService.getDepartmentMap();
	}

	public Map getEnabledTodoMap() {
		return todoService.getEnabledTodoMap();
	}

	public List getJbpmTasks(String actorId) {
		List rows = new java.util.ArrayList();
		List taskItems = ProcessContainer.getContainer().getTaskItems(actorId);
		if (taskItems != null && taskItems.size() > 0) {
			rows = this.getTodoInstances(taskItems);
		}
		return rows;
	}

	public List getJbpmTasksByProcessInstanceId(Long processInstanceId) {
		List rows = new java.util.ArrayList();
		List taskItems = ProcessContainer.getContainer()
				.getTaskItemsByProcessInstanceId(processInstanceId);
		if (taskItems != null && taskItems.size() > 0) {
			rows = this.getTodoInstances(taskItems);
		}
		return rows;
	}

	public List getJbpmTasksByProcessInstanceIds(List processInstanceIds) {
		List rows = new java.util.ArrayList();
		List taskItems = ProcessContainer.getContainer()
				.getTaskItemsByProcessInstanceIds(processInstanceIds);
		if (taskItems != null && taskItems.size() > 0) {
			rows = this.getTodoInstances(taskItems);
		}
		return rows;
	}

	public SysDepartment getParentDepartment(long id) {
		return todoService.getParentDepartment(id);
	}

	public Map getRoleMap() {
		return todoService.getRoleMap();
	}

	public List getTasks() {
		List todos = todoService.getSQLTodos();
		List rows = new java.util.ArrayList();
		if (todos != null && todos.size() > 0) {
			Iterator iterator = todos.iterator();
			while (iterator.hasNext()) {
				Todo todo = (Todo) iterator.next();
				if (StringUtils.isNotEmpty(todo.getSql())) {
					logger.info(todo.getId() + ":" + todo.getSql());
					Map rowsMap = new java.util.HashMap();
					java.sql.Connection conn = null;
					java.sql.PreparedStatement psmt = null;
					java.sql.ResultSet rs = null;
					try {
						conn = DBConnectionFactory.getConnection();
						psmt = conn.prepareStatement(todo.getSql());
						rs = psmt.executeQuery();
						java.sql.ResultSetMetaData rsmd = rs.getMetaData();
						while (rs.next()) {
							TodoInstance model = new TodoInstance();
							model.setRowId(rs.getString(1));
							model.setStartDate(rs.getDate(2));
							if (rsmd.getColumnCount() == 3) {
								switch (Long.valueOf(todo.getId()).intValue()) {
								case 8005:
								case 7001:
								case 7002:
								case 7003:
								case 7004:
								case 17001:
								case 17010:
								case 18001:
								case 19001:
								case 20001:
								case 20084001:
									model.setDeptId(rs.getLong(3));
									break;
								default:
									model.setActorId(rs.getString(3));
									break;
								}
							}
							rowsMap.put(model.getRowId(), model);
						}
						rs.close();
						psmt.close();
						rs = null;
						psmt = null;

					} catch (java.sql.SQLException ex) {
						logger.debug(todo.getId() + ":" + todo.getSql());
						ex.printStackTrace();
					} finally {
						JdbcUtils.close(rs);
						JdbcUtils.close(psmt);
						JdbcUtils.close(conn);
					}

					if (rowsMap.size() > 0) {
						Iterator iter = rowsMap.keySet().iterator();
						while (iter.hasNext()) {
							String rowId = (String) iter.next();
							TodoInstance model = (TodoInstance) rowsMap
									.get(rowId);
							Date startDate = model.getStartDate();
							if (startDate == null) {
								startDate = new java.util.Date();
							}

							model.setProvider("sql");
							model.setLinkType(todo.getLinkType());
							model.setAppId(todo.getAppId());
							model.setModuleId(todo.getModuleId());
							model.setTodoId(todo.getId());
							model.setRoleId(todo.getRoleId());
							model.setRoleCode(todo.getRoleCode());
							model.setTitle(todo.getTitle());
							model.setCreateDate(new Date(System
									.currentTimeMillis()));
							model.setStartDate(startDate);
							int limitDay = todo.getLimitDay();
							int ahour = todo.getXa();
							int bhour = todo.getXb();

							Date limitWorkDate = workCalendarService
									.getWorkDate(startDate, limitDay);
							long time = limitWorkDate.getTime();

							Date cautionDate = new Date(time - ahour
									* DateUtils.HOUR);
							Date pastDueDate = new Date(time + bhour
									* DateUtils.HOUR);
							model.setAlarmDate(cautionDate);
							model.setPastDueDate(pastDueDate);
							model.setRowId(rowId);
							model.setVersionNo(System.currentTimeMillis());
							rows.add(model);
						}
					}
				}
			}

		}
		return rows;
	}

	public Todo getTodo(long todoId) {
		return todoService.getTodo(todoId);
	}

	public List getTodoInstanceList(TodoQuery query) {
		return todoService.getTodoInstanceList(query);
	}

	public List getTodoInstances(List taskItems) {
		List rows = new java.util.ArrayList();
		if (taskItems != null && taskItems.size() > 0) {
			logger.info("---------->taskItems size:" + taskItems.size());
			Map userMap = this.getUserMap();
			Map todoMap = this.getTodoMap();

			Iterator iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = (TaskItem) iterator.next();

				SysUser user = (SysUser) userMap.get(taskItem.getActorId());
				if (user == null) {
					continue;
				}

				Todo todo = null;

				String key001 = taskItem.getProcessName() + "_"
						+ taskItem.getTaskName();
				String key = taskItem.getProcessName() + "_"
						+ taskItem.getTaskName() + "_"
						+ user.getDepartment().getId();

				todo = (Todo) todoMap.get(key);

				if (todo == null) {
					todo = (Todo) todoMap.get(key001);
				}

				if (todo == null) {
					logger.info(key001 + " no todo config .................");
				}

				if (todo != null) {

					TodoInstance model = new TodoInstance();
					model.setProvider("jbpm");
					model.setLinkType(todo.getLinkType());
					model.setAppId(todo.getAppId());
					model.setModuleId(todo.getModuleId());
					model.setTodoId(todo.getId());
					model.setRoleId(todo.getRoleId());
					model.setRoleCode(todo.getRoleCode());
					model.setTitle(todo.getTitle());
					model.setActorId(taskItem.getActorId());
					model.setActorName(user.getName());
					model.setDeptId(user.getDepartment().getId());
					model.setDeptName(user.getDepartment().getName());
					model.setCreateDate(new Date(System.currentTimeMillis()));
					model.setStartDate(taskItem.getCreateDate());
					int limitDay = todo.getLimitDay();
					int ahour = todo.getXa();
					int bhour = todo.getXb();

					Date limitWorkDate = workCalendarService.getWorkDate2(
							model.getStartDate(), limitDay);
					long time = limitWorkDate.getTime();

					Date cautionDate = new Date(time - ahour * DateUtils.HOUR);
					Date pastDueDate = new Date(time + bhour * DateUtils.HOUR);
					model.setAlarmDate(cautionDate);
					model.setPastDueDate(pastDueDate);
					model.setProcessInstanceId(String.valueOf(taskItem
							.getProcessInstanceId()));
					model.setTaskInstanceId(String.valueOf(taskItem
							.getTaskInstanceId()));
					model.setRowId(taskItem.getRowId());
					model.setVersionNo(System.currentTimeMillis());
					String content = todo.getContent();
					if (StringUtils.isNotEmpty(taskItem.getTaskDescription())) {
						content = content + taskItem.getTaskDescription();
						model.setContent(content);
					} else {
						if (StringUtils.isNotEmpty(taskItem.getRowId())) {
							content = content + " 单据编号（" + taskItem.getRowId()
									+ "）";
							model.setContent(content);
						}
					}
					rows.add(model);
				}
			}
		}
		return rows;
	}

	public List getTodoInstances(String actorId) {
		SendMessageBean bean = new SendMessageBean();
		bean.setSysUserService(this.sysUserService);

		bean.setTodoService(todoService);
		return bean.getTodoInstances(actorId);
	}

	public List getTodoList() {
		return todoService.getTodoList();
	}

	public Map getTodoMap() {
		return todoService.getTodoMap();
	}

	public SysUser getUser(String actorId) {
		return todoService.getUser(actorId);
	}

	public Map getUserMap() {
		return todoService.getUserMap();
	}

	public Map getUserRoleMap(String actorId) {
		return todoService.getUserRoleMap(actorId);
	}

	public Map getUserTodoMap(String actorId) {
		return todoService.getUserTodoMap(actorId);
	}

	public List getXYTodoInstances(String actorId) {
		SendMessageBean bean = new SendMessageBean();
		bean.setSysUserService(this.sysUserService);
		bean.setTodoService(todoService);
		return bean.getXYTodoInstances(actorId);
	}

	public void sendMessage() {
		if (todoService.isWorkDate(new Date())) {
			this.sendMessageToAllUsers();
		}
	}

	public void sendMessageToAllUsers() {
		if (todoService.isWorkDate(new Date())) {
			List rows = this.getTasks();
			todoService.createTasksOfSQL(rows);
			List tasks = this.getAllJbpmTasks();
			todoService.createTasksOfWorkflow(tasks);
			Collection users = sysUserService.getSysUserList();
			if (users != null && users.size() > 0) {
				logger.info("########user size:" + users.size());
				Iterator iterator = users.iterator();
				while (iterator.hasNext()) {
					SysUser user = (SysUser) iterator.next();
					if (user.getBlocked() == 0) {
						try {
							this.sendMessageToUser(user.getAccount());
						} catch (Exception ex) {
							logger.error(ex);
						}
					}
				}
			}
		}
	}

	public void sendMessageToUser(String actorId) {
		SendMessageBean bean = new SendMessageBean();
		bean.setSysUserService(sysUserService);
		bean.setTodoService(todoService);
		bean.sendMessageToUser(actorId);
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setTodoService(TodoService todoService) {
		this.todoService = todoService;
	}

	public void setWorkCalendarService(WorkCalendarService workCalendarService) {
		this.workCalendarService = workCalendarService;
	}

	public void update(Todo todo) {
		todoService.update(todo);
	}
}