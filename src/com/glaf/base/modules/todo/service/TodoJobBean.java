package com.glaf.base.modules.todo.service;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.model.TaskItem;
import org.jpage.jbpm.service.ProcessContainer;
import org.jpage.util.DateTools;

import com.glaf.base.modules.others.service.WorkCalendarService;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.todo.model.ToDo;
import com.glaf.base.modules.todo.model.ToDoInstance;

public class TodoJobBean {
	private final static Log logger = LogFactory.getLog(TodoJobBean.class);

	private TodoService todoService;

	private MembershipService membershipService;

	private WorkCalendarService workCalendarService;

	private SysUserService sysUserService;

	public TodoJobBean() {

	}

	public void create(ToDo todo) {
		todoService.create(todo);
	}

	public void createTasks(Collection processInstanceIds) {
		List tasks = this.getJbpmTasksByProcessInstanceIds(processInstanceIds);
		todoService.createTasks(processInstanceIds, tasks);
		logger.info("流程 " + processInstanceIds + " 的任务已经更新,现在有任务:"
				+ tasks.size());
	}

	public void createTasks(String processInstanceId) {
		List tasks = this.getJbpmTasksByProcessInstanceId(processInstanceId);
		todoService.createTasks(processInstanceId, tasks);
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
		ToDo todo = todoService.getToDo(todoId);
		Map rowsMap = new HashMap();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			java.sql.Connection con = jbpmContext.getConnection();
			java.sql.PreparedStatement psmt = con.prepareStatement(todo
					.getSql());
			java.sql.ResultSet rs = psmt.executeQuery();
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				ToDoInstance model = new ToDoInstance();
				model.setRowId(rs.getString(1));
				model.setStartDate(rs.getDate(2));
				if (rsmd.getColumnCount() == 3) {
					switch (new Long(todo.getId()).intValue()) {
					case 8005:// 尚未置合同生效日！
					case 7001:
					case 7002:
					case 7003:
					case 7004:
					case 17001:// 采购付款待交财务
					case 17010:
					case 18001:
					case 19001:
					case 20001:
					case 20084001:// 资产初步财务确认
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
			Context.close(jbpmContext);
		}

		List rows = new ArrayList();

		if (rowsMap.size() > 0) {
			Iterator iter = rowsMap.keySet().iterator();
			while (iter.hasNext()) {
				String rowId = (String) iter.next();
				ToDoInstance model = (ToDoInstance) rowsMap.get(rowId);
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

				Date cautionDate = new Date(time - ahour * DateTools.HOUR);
				Date pastDueDate = new Date(time + bhour * DateTools.HOUR);
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
		List rows = new ArrayList();
		List taskItems = ProcessContainer.getContainer().getAllTaskItems();
		if (taskItems != null && taskItems.size() > 0) {
			logger.info("---------->taskItems size:" + taskItems.size());
			rows = this.getTodoInstances(taskItems);
		}
		return rows;
	}

	public List getAllToDoList() {
		return todoService.getAllToDoList();
	}

	public Collection getAllUsers() {
		SysUserService sysUserService = this.getSysUserService();
		Collection users = sysUserService.getSysUserList();
		return users;
	}

	public Collection getAppActorIds(String appId) {
		Collection actorIds = new HashSet();
		String sql = " select a.account, a.name, c.appId from sys_user a inner join sys_user_role b on a.id = b.userId inner join sys_access c on b.roleId = c.roleId where c.appId = ? ";
		JbpmContext jbpmContext = null;
		try {
			JbpmConfiguration cfg = JbpmConfiguration.getInstance();
			jbpmContext = cfg.createJbpmContext();
			java.sql.Connection con = jbpmContext.getConnection();
			java.sql.PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, appId);
			java.sql.ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				actorIds.add(rs.getString(1));
			}
			rs.close();
			psmt.close();
			rs = null;
			psmt = null;
		} catch (java.sql.SQLException ex) {
			ex.printStackTrace();
		} finally {
			Context.close(jbpmContext);
		}
		return actorIds;
	}

	public Map getDepartmentMap() {
		return todoService.getDepartmentMap();
	}

	public Map getEnabledToDoMap() {
		return todoService.getEnabledToDoMap();
	}

	public List getJbpmTasks(String actorId) {
		List rows = new ArrayList();
		List taskItems = ProcessContainer.getContainer().getTaskItems(actorId);
		if (taskItems != null && taskItems.size() > 0) {
			rows = this.getTodoInstances(taskItems);
		}
		return rows;
	}

	public List getJbpmTasksByProcessInstanceId(String processInstanceId) {
		List rows = new ArrayList();
		List taskItems = ProcessContainer.getContainer()
				.getTaskItemsByProcessInstanceId(processInstanceId);
		if (taskItems != null && taskItems.size() > 0) {
			rows = this.getTodoInstances(taskItems);
		}
		return rows;
	}

	public List getJbpmTasksByProcessInstanceIds(Collection processInstanceIds) {
		List rows = new ArrayList();
		List taskItems = ProcessContainer.getContainer()
				.getTaskItemsByProcessInstanceIds(processInstanceIds);
		if (taskItems != null && taskItems.size() > 0) {
			rows = this.getTodoInstances(taskItems);
		}
		return rows;
	}

	public MembershipService getMembershipService() {
		return membershipService;
	}

	public SysDepartment getParentDepartment(long id) {
		return todoService.getParentDepartment(id);
	}

	public Map getRoleMap() {
		return todoService.getRoleMap();
	}

	public SysUserService getSysUserService() {
		return sysUserService;
	}

	public List getTasks() {
		List todos = todoService.getSQLToDos();
		List rows = new ArrayList();
		if (todos != null && todos.size() > 0) {
			Iterator iterator = todos.iterator();
			while (iterator.hasNext()) {
				ToDo todo = (ToDo) iterator.next();
				if (StringUtils.isNotEmpty(todo.getSql())) {
					logger.info(todo.getId() + ":" + todo.getSql());
					Map rowsMap = new HashMap();
					JbpmContext jbpmContext = null;
					try {
						JbpmConfiguration cfg = JbpmConfiguration.getInstance();
						jbpmContext = cfg.createJbpmContext();
						java.sql.Connection con = jbpmContext.getConnection();
						java.sql.PreparedStatement psmt = con
								.prepareStatement(todo.getSql());
						java.sql.ResultSet rs = psmt.executeQuery();
						java.sql.ResultSetMetaData rsmd = rs.getMetaData();
						while (rs.next()) {
							ToDoInstance model = new ToDoInstance();
							model.setRowId(rs.getString(1));
							model.setStartDate(rs.getDate(2));
							if (rsmd.getColumnCount() == 3) {
								switch (new Long(todo.getId()).intValue()) {
								case 8005:// 尚未置合同生效日！
								case 7001:
								case 7002:
								case 7003:
								case 7004:
								case 17001:// 采购付款待交财务
								case 17010:
								case 18001:
								case 19001:
								case 20001:
								case 20084001:// 资产初步财务确认
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
						System.out.println(todo.getId() + ":" + todo.getSql());
						ex.printStackTrace();
					} finally {
						Context.close(jbpmContext);
					}

					if (rowsMap.size() > 0) {
						Iterator iter = rowsMap.keySet().iterator();
						while (iter.hasNext()) {
							String rowId = (String) iter.next();
							ToDoInstance model = (ToDoInstance) rowsMap
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
									* DateTools.HOUR);
							Date pastDueDate = new Date(time + bhour
									* DateTools.HOUR);
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

	public ToDo getToDo(long todoId) {
		return todoService.getToDo(todoId);
	}

	public List getToDoInstanceList(Map paramMap) {
		return todoService.getToDoInstanceList(paramMap);
	}

	public List getTodoInstances(List taskItems) {
		List rows = new ArrayList();
		if (taskItems != null && taskItems.size() > 0) {
			logger.info("---------->taskItems size:" + taskItems.size());
			Map userMap = this.getUserMap();
			Map todoMap = this.getToDoMap();

			Iterator iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = (TaskItem) iterator.next();

				SysUser user = (SysUser) userMap.get(taskItem.getActorId());
				if (user == null) {
					continue;
				}

				ToDo todo = null;

				String key001 = taskItem.getProcessName() + "_"
						+ taskItem.getTaskName();
				String key = taskItem.getProcessName() + "_"
						+ taskItem.getTaskName() + "_"
						+ user.getDepartment().getId();

				todo = (ToDo) todoMap.get(key);

				if (todo == null) {
					todo = (ToDo) todoMap.get(key001);
				}

				if (todo == null) {
					logger.info(key001 + " no todo config .................");
				}

				if (todo != null) {
					boolean exist = false;
					JbpmContext jbpmContext = null;
					try {
						JbpmConfiguration cfg = JbpmConfiguration.getInstance();
						jbpmContext = cfg.createJbpmContext();
						java.sql.Connection con = jbpmContext.getConnection();
						String sql = " select processinstanceid from "
								+ todo.getTablename()
								+ " where processinstanceid = ? ";
						java.sql.PreparedStatement psmt = con
								.prepareStatement(sql);
						psmt.setString(1, taskItem.getProcessInstanceId());
						java.sql.ResultSet rs = psmt.executeQuery();
						if (rs.next()) {
							exist = true;
						}
						rs.close();
						psmt.close();
						rs = null;
						psmt = null;
					} catch (java.sql.SQLException ex) {
						ex.printStackTrace();
					} finally {
						Context.close(jbpmContext);
					}

					if (!exist) {
						continue;
					}

					ToDoInstance model = new ToDoInstance();
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
					model.setStartDate(taskItem.getTaskCreateDate());
					int limitDay = todo.getLimitDay();
					int ahour = todo.getXa();
					int bhour = todo.getXb();

					Date limitWorkDate = workCalendarService.getWorkDate2(
							model.getStartDate(), limitDay);
					long time = limitWorkDate.getTime();

					Date cautionDate = new Date(time - ahour * DateTools.HOUR);
					Date pastDueDate = new Date(time + bhour * DateTools.HOUR);
					model.setAlarmDate(cautionDate);
					model.setPastDueDate(pastDueDate);
					model.setProcessInstanceId(taskItem.getProcessInstanceId());
					model.setTaskInstanceId(taskItem.getTaskInstanceId());
					model.setRowId(taskItem.getBusinessValue());
					model.setVersionNo(System.currentTimeMillis());
					String content = todo.getContent();
					if (StringUtils.isNotEmpty(taskItem.getTaskDescription())) {
						content = content + taskItem.getTaskDescription();
						model.setContent(content);
					} else {
						if (StringUtils.isNotEmpty(taskItem.getBusinessValue())) {
							content = content + " 单据编号（"
									+ taskItem.getBusinessValue() + "）";
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
		bean.setSysUserService(this.getSysUserService());
		bean.setMembershipService(membershipService);
		bean.setTodoService(todoService);
		return bean.getTodoInstances(actorId);
	}

	public List getToDoList() {
		return todoService.getToDoList();
	}

	public Map getToDoMap() {
		return todoService.getToDoMap();
	}

	public TodoService getTodoService() {
		return todoService;
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

	public WorkCalendarService getWorkCalendarService() {
		return workCalendarService;
	}

	public List getXYTodoInstances(String actorId) {
		SendMessageBean bean = new SendMessageBean();
		bean.setSysUserService(this.getSysUserService());
		bean.setMembershipService(membershipService);
		bean.setTodoService(todoService);
		return bean.getXYTodoInstances(actorId);
	}

	public void sendMessage() {
		// logger.info("sendMessage");
		// System.out.println("sendMessage");
		if (todoService.isWorkDate(new Date())) {
			// logger.info("ok--------");
			// System.out.println("ok--------");
			this.sendMessageToAllUsers();
		}
	}

	public void sendMessageToAllUsers() {
		if (todoService.isWorkDate(new Date())) {
			List rows = this.getTasks();
			todoService.createTasksOfSQL(rows);
			List tasks = this.getAllJbpmTasks();
			todoService.createTasksOfWorkflow(tasks);
			SysUserService sysUserService = this.getSysUserService();
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
		bean.setSysUserService(this.getSysUserService());
		bean.setMembershipService(membershipService);
		bean.setTodoService(todoService);
		bean.sendMessageToUser(actorId);
	}

	public void setMembershipService(MembershipService membershipService) {
		this.membershipService = membershipService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setTodoService(TodoService todoService) {
		this.todoService = todoService;
	}

	public void setWorkCalendarService(WorkCalendarService workCalendarService) {
		this.workCalendarService = workCalendarService;
		logger.info("setWorkCalendarService");
	}

	public void update(ToDo todo) {
		todoService.update(todo);
	}
}
