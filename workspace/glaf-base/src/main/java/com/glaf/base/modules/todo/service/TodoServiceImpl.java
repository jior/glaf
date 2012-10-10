package com.glaf.base.modules.todo.service;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import org.jpage.core.cache.CacheFactory;

import org.jpage.util.DateTools;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.others.service.WorkCalendarService;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.service.*;
import com.glaf.base.modules.todo.TodoConstants;
import com.glaf.base.modules.todo.dao.TodoDAO;
import com.glaf.base.modules.todo.model.ToDo;
import com.glaf.base.modules.todo.model.ToDoInstance;
import com.glaf.base.modules.todo.model.UserEntity;
import com.glaf.base.modules.workspace.service.MessageService;

public class TodoServiceImpl implements TodoService {

	private final static Log logger = LogFactory.getLog(TodoServiceImpl.class);

	private AbstractSpringDao abstractDao;

	private SysUserService sysUserService;

	private SysRoleService sysRoleService;

	private SysDepartmentService sysDepartmentService;

	private WorkCalendarService workCalendarService;

	private TodoDAO todoDAO;

	public void create(ToDo todo) {
		abstractDao.create(todo);
	}

	public void createTasks(Collection processInstanceIds, List rows) {
		if (processInstanceIds != null && processInstanceIds.size() > 0) {
			String sql = " delete from sys_todo_instance where provider = 'jbpm' ";
			sql = sql + " and processInstanceId "
					+ this.getINSQL(processInstanceIds);
			todoDAO.executeSQL(sql, null);
		}
		if (rows.size() > 0) {
			logger.info("---------->rows size:" + rows.size());
			todoDAO.saveAll(rows);
		}
	}

	public void createTasks(String processInstanceId, List rows) {
		String sql = " delete from sys_todo_instance where provider = 'jbpm' and processInstanceId = ? ";
		List values = new ArrayList();
		values.add(processInstanceId);
		todoDAO.executeSQL(sql, values);
		if (rows.size() > 0) {
			logger.info("---------->rows size:" + rows.size());
			todoDAO.saveAll(rows);
		}
	}

	public void createTasksOfSQL(List rows) {
		String sql = " delete from sys_todo_instance where provider = 'sql' ";
		todoDAO.executeSQL(sql, null);
		if (rows.size() > 0) {
			todoDAO.saveAll(rows);
		}

	}

	public void createTasksOfWorkflow(List rows) {
		String sql = " delete from sys_todo_instance where provider = 'jbpm' ";
		todoDAO.executeSQL(sql, null);
		if (rows.size() > 0) {
			logger.info("---------->rows size:" + rows.size());
			todoDAO.saveAll(rows);
		}
	}

	public void createTasksOfWorkflow(String actorId, List rows) {
		String sql = " delete from sys_todo_instance where provider = 'jbpm' and actorId = ? ";
		List values = new ArrayList();
		values.add(actorId);
		todoDAO.executeSQL(sql, values);
		if (rows.size() > 0) {
			logger.info("---------->rows size:" + rows.size());
			todoDAO.saveAll(rows);
		}
	}

	public void createTodoInstances(long todoId, List rows) {
		String sql = " delete from sys_todo_instance where provider = 'sql' and todoId = ? ";
		List values = new ArrayList();
		values.add(new Long(todoId));
		todoDAO.executeSQL(sql, values);
		if (rows.size() > 0) {
			todoDAO.saveAll(rows);
		}
	}

	public List getAllToDoList() {
		return abstractDao
				.getList(
						" from com.glaf.base.modules.todo.model.ToDo as a order by a.moduleId asc ",
						null);
	}

	public Map getDepartmentMap() {
		String cacheKey = "cache_deptmap";
		if (CacheFactory.get(cacheKey) != null) {
			return (Map) CacheFactory.get(cacheKey);
		}
		Map rowMap = new HashMap();
		List roles = sysDepartmentService.getSysDepartmentList();
		if (roles != null && roles.size() > 0) {
			Iterator iterator = roles.iterator();
			while (iterator.hasNext()) {
				SysDepartment model = (SysDepartment) iterator.next();
				rowMap.put(new Long(model.getId()), model);
			}
		}
		CacheFactory.put(cacheKey, rowMap);
		return rowMap;
	}

	protected String getINSQL(java.util.Collection rowIds) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" ( ");
		Iterator item = rowIds.iterator();
		while (item.hasNext()) {
			String rowId = (String) item.next();
			rowId = rowId.replaceAll("'", "");
			rowId = rowId.replaceAll("%", "");
			buffer.append("'").append(rowId).append("'  ");
			if (item.hasNext()) {
				buffer.append(" , ");
			}
		}
		buffer.append(" ) ");
		return buffer.toString();
	}

	public Map getEnabledToDoMap() {
		String cacheKey = "cache_mmtodomap";
		if (CacheFactory.get(cacheKey) != null) {
			return (Map) CacheFactory.get(cacheKey);
		}
		Map rowMap = new LinkedHashMap();
		List rows = abstractDao
				.getList(
						" from com.glaf.base.modules.todo.model.ToDo as a where a.enableFlag = 1 order by a.moduleId asc ",
						null, null);
		if (rows != null && rows.size() > 0) {
			Iterator iterator = rows.iterator();
			while (iterator.hasNext()) {
				ToDo todo = (ToDo) iterator.next();
				rowMap.put(new Long(todo.getId()), todo);
			}
		}
		CacheFactory.put(cacheKey, rowMap);
		return rowMap;
	}

	public SysDepartment getParentDepartment(long id) {
		SysDepartment parent = null;
		SysDepartment node = (SysDepartment) abstractDao.find(
				SysDepartment.class, new Long(id));
		if (node != null) {
			SysTree tree = node.getNode();
			if (tree.getParent() != 0) {
				SysTree treeParent = (SysTree) abstractDao.find(SysTree.class,
						new Long(tree.getParent()));
				parent = treeParent.getDepartment();
			}
		}
		return parent;
	}

	public Map getRoleMap() {
		String cacheKey = "cache_rolemap";
		if (CacheFactory.get(cacheKey) != null) {
			return (Map) CacheFactory.get(cacheKey);
		}
		Map roleMap = new HashMap();
		List roles = sysRoleService.getSysRoleList();
		if (roles != null && roles.size() > 0) {
			Iterator iterator = roles.iterator();
			while (iterator.hasNext()) {
				SysRole sysRole = (SysRole) iterator.next();
				roleMap.put(new Long(sysRole.getId()), sysRole);
			}
		}
		CacheFactory.put(cacheKey, roleMap);
		return roleMap;
	}

	public List getSQLToDos() {
		String cacheKey = "cache_sqltodos";
		if (CacheFactory.get(cacheKey) != null) {
			return (List) CacheFactory.get(cacheKey);
		}
		List rows = abstractDao
				.getList(
						" from com.glaf.base.modules.todo.model.ToDo as a where a.enableFlag = 1 and a.type = 'sql' ",
						null);
		CacheFactory.put(cacheKey, rows);
		return rows;
	}

	public List getSysUserWithDeptList() {
		// ¼ÆËã×ÜÊý
		String query = "from SysUser a order by a.department.id asc ";
		List rows = abstractDao.getList(query, null, null);
		Iterator iterator = rows.iterator();
		while (iterator.hasNext()) {
			SysUser user = (SysUser) iterator.next();
			if (!Hibernate.isInitialized(user.getDepartment())) {
				Hibernate.initialize(user.getDepartment());
			}
		}
		return rows;
	}

	public ToDo getToDo(long todoId) {
		String cacheKey = "cache_todoxy_" + todoId;
		if (CacheFactory.get(cacheKey) != null) {
			return (ToDo) CacheFactory.get(cacheKey);
		}
		ToDo todo = (ToDo) abstractDao.find(ToDo.class, new Long(todoId));
		CacheFactory.put(cacheKey, todo);
		return todo;
	}

	public List getToDoInstanceList(Map paramMap) {
		Map params = new LinkedHashMap();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" from com.glaf.base.modules.todo.model.ToDoInstance as a where 1=1 ");

		if (paramMap.get("actorId") != null) {
			params.put("actorId", paramMap.get("actorId"));
			buffer.append(" and a.actorId = :actorId ");
		}

		if (paramMap.get("actorIdx") != null) {
			String actorIdx = (String) paramMap.get("actorIdx");
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
						SysDeptRole sysDeptRole = (SysDeptRole) iteratorxy
								.next();
						SysRole role = sysDeptRole.getRole();
						SysDepartment dept = sysDeptRole.getDept();
						roleIds.add(new Long(role.getId()));
						deptIds.add(new Long(dept.getId()));
					}
				}

				logger.info("@@deptIds:" + deptIds);
				logger.info("@@roleIds:" + roleIds);

				Iterator iterator = rows.iterator();
				while (iterator.hasNext()) {
					UserEntity entity = (UserEntity) iterator.next();
					roleIds.add(new Long(entity.getRoleId()));
					deptIds.add(new Long(entity.getDeptId()));
					actorxIds.add(entity.getActorId());

					if (entity.getRoleId() == 6 || entity.getRoleId() == 1) {
						isDeptAdmin = true;
						List list = new ArrayList();
						SysDepartment node = sysDepartmentService
								.findById(entity.getDeptId());
						sysDepartmentService.findNestingDepartment(list, node);
						if (list != null && list.size() > 0) {
							Iterator iter007 = list.iterator();
							while (iter007.hasNext()) {
								SysDepartment dept = (SysDepartment) iter007
										.next();
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
		}

		if (paramMap.get("actorIds") != null) {
			params.put("actorIds", paramMap.get("actorIds"));
			buffer.append(" and a.actorId in ( :actorIds ) ");
		}

		if (paramMap.get("objectId") != null) {
			params.put("objectId", paramMap.get("objectId"));
			buffer.append(" and a.objectId = :objectId ");
		}

		if (paramMap.get("objectIds") != null) {
			params.put("objectIds", paramMap.get("objectIds"));
			buffer.append(" and a.objectId in ( :objectIds ) ");
		}

		if (paramMap.get("objectValue") != null) {
			params.put("objectValue", paramMap.get("objectValue"));
			buffer.append(" and a.objectValue = :objectValue ");
		}

		if (paramMap.get("objectValues") != null) {
			params.put("objectValues", paramMap.get("objectValues"));
			buffer.append(" and a.objectValue in ( :objectValues ) ");
		}

		if (paramMap.get("processInstanceId") != null) {
			params.put("processInstanceId", paramMap.get("processInstanceId"));
			buffer.append(" and a.processInstanceId = :processInstanceId ");
		}

		if (paramMap.get("processInstanceIds") != null) {
			params.put("processInstanceIds", paramMap.get("processInstanceIds"));
			buffer.append(" and a.processInstanceId in ( :processInstanceIds ) ");
		}

		if (paramMap.get("taskInstanceId") != null) {
			params.put("taskInstanceId", paramMap.get("taskInstanceId"));
			buffer.append(" and a.taskInstanceId = :taskInstanceId ");
		}

		if (paramMap.get("taskInstanceIds") != null) {
			params.put("taskInstanceIds", paramMap.get("taskInstanceIds"));
			buffer.append(" and a.taskInstanceId in ( :taskInstanceIds ) ");
		}

		if (paramMap.get("provider") != null) {
			params.put("provider", paramMap.get("provider"));
			buffer.append(" and a.provider = :provider ");
		}

		if (paramMap.get("roleId") != null) {
			Object obj = paramMap.get("roleId");
			if (obj instanceof java.lang.Long) {
				params.put("roleId", paramMap.get("roleId"));
			} else {
				String roleId = (String) obj;
				params.put("roleId", new Long(roleId));
			}
			buffer.append(" and ( a.roleId = :roleId )");
		}

		if (paramMap.get("roleIds") != null) {
			params.put("roleIds", paramMap.get("roleIds"));
			buffer.append(" and a.roleId in ( :roleIds ) ");
		}

		if (paramMap.get("deptId") != null) {
			Object obj = paramMap.get("deptId");
			if (obj instanceof java.lang.Long) {
				params.put("deptId", paramMap.get("deptId"));
			} else {
				String deptId = (String) obj;
				params.put("deptId", new Long(deptId));
			}
			buffer.append(" and ( a.deptId = :deptId )");
		}

		if (paramMap.get("deptIds") != null) {
			params.put("deptIds", paramMap.get("deptIds"));
			buffer.append(" and a.deptId in ( :deptIds ) ");
		}

		if (paramMap.get("todoId") != null) {
			Object obj = paramMap.get("todoId");
			if (obj instanceof java.lang.Long) {
				params.put("todoId", paramMap.get("todoId"));
			} else {
				String todoId = (String) obj;
				params.put("todoId", new Long(todoId));
			}
			buffer.append(" and ( a.todoId = :todoId )");
		}

		if (paramMap.get("todoIds") != null) {
			params.put("todoIds", paramMap.get("todoIds"));
			buffer.append(" and a.todoId in ( :todoIds ) ");
		}

		if (paramMap.get("appId") != null) {
			Object obj = paramMap.get("appId");
			if (obj instanceof java.lang.Integer) {
				params.put("appId", paramMap.get("appId"));
			} else {
				String appId = (String) obj;
				params.put("appId", new Integer(appId));
			}
			buffer.append(" and ( a.appId = :appId )");
		}

		if (paramMap.get("appIds") != null) {
			params.put("appIds", paramMap.get("appIds"));
			buffer.append(" and a.appId in ( :appIds ) ");
		}

		if (paramMap.get("moduleId") != null) {
			Object obj = paramMap.get("moduleId");
			if (obj instanceof java.lang.Integer) {
				params.put("moduleId", paramMap.get("moduleId"));
			} else {
				String moduleId = (String) obj;
				params.put("moduleId", new Integer(moduleId));
			}
			buffer.append(" and ( a.moduleId = :moduleId )");
		}

		if (paramMap.get("moduleIds") != null) {
			params.put("moduleIds", paramMap.get("moduleIds"));
			buffer.append(" and a.moduleId in ( :moduleIds ) ");
		}

		if (paramMap.get("actorName") != null) {
			String actorName = (String) paramMap.get("actorName");
			if (StringUtils.isNumeric(actorName)) {
				params.put("actorId_xyz", "%" + paramMap.get("actorName") + "%");
				buffer.append(" and a.actorId like :actorId_xyz ");
			} else {
				params.put("actorName", "%" + paramMap.get("actorName") + "%");
				buffer.append(" and a.actorName like :actorName ");
			}
		}

		if (paramMap.get("deptName") != null) {
			params.put("deptName", "%" + paramMap.get("deptName") + "%");
			buffer.append(" and a.deptName like :deptName ");
		}

		if (paramMap.get("createDate") != null) {
			Object obj = paramMap.get("createDate");
			if (obj instanceof java.util.Date) {
				params.put("createDate", paramMap.get("createDate"));
			} else {
				String dateTime = (String) obj;
				if (!dateTime.endsWith(" 00:00:00")) {
					dateTime += " 00:00:00";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("createDate", date);
			}
			buffer.append(" and ( a.createDate >= :createDate )");
		}

		if (paramMap.get("createDate_start") != null) {
			Object obj = paramMap.get("createDate_start");
			if (obj instanceof java.util.Date) {
				params.put("createDate_start", paramMap.get("createDate_start"));
			} else {
				String dateTime = (String) obj;
				if (!dateTime.endsWith(" 00:00:00")) {
					dateTime += " 00:00:00";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("createDate_start", date);
			}
			buffer.append(" and ( a.createDate >= :createDate_start )");
		}

		if (paramMap.get("createDate_end") != null) {
			Object obj = paramMap.get("createDate_end");
			if (obj instanceof java.util.Date) {
				params.put("createDate_end", paramMap.get("createDate_end"));
			} else {
				String dateTime = (String) obj;
				if (!dateTime.endsWith(" 23:59:59")) {
					dateTime += " 23:59:59";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("createDate_end", date);
			}
			buffer.append(" and ( a.createDate <= :createDate_end )");
		}

		if (paramMap.get("startDate") != null) {
			Object obj = paramMap.get("startDate");
			if (obj instanceof java.util.Date) {
				params.put("startDate", paramMap.get("startDate"));
			} else {
				String dateTime = (String) obj;
				if (!dateTime.endsWith(" 00:00:00")) {
					dateTime += " 00:00:00";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("startDate", date);
			}
			buffer.append(" and ( a.startDate >= :startDate )");
		}

		if (paramMap.get("startDate_start") != null) {
			Object obj = paramMap.get("startDate_start");
			if (obj instanceof java.util.Date) {
				params.put("startDate_start", paramMap.get("startDate_start"));
			} else {
				String dateTime = (String) obj;
				if (!dateTime.endsWith(" 00:00:00")) {
					dateTime += " 00:00:00";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("startDate_start", date);
			}
			buffer.append(" and ( a.startDate >= :startDate_start )");
		}

		if (paramMap.get("startDate_end") != null) {
			Object obj = paramMap.get("startDate_end");
			if (obj instanceof java.util.Date) {
				params.put("startDate_end", paramMap.get("startDate_end"));
			} else {
				String dateTime = (String) obj;
				if (!dateTime.endsWith(" 23:59:59")) {
					dateTime += " 23:59:59";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("startDate_end", date);
			}
			buffer.append(" and ( a.startDate <= :startDate_end )");
		}

		if (paramMap.get("endDate") != null) {
			Object obj = paramMap.get("endDate");
			if (obj instanceof java.util.Date) {
				params.put("endDate", paramMap.get("endDate"));
			} else {
				String dateTime = (String) obj;
				if (!dateTime.endsWith(" 23:59:59")) {
					dateTime += " 23:59:59";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("endDate", date);
			}
			buffer.append(" and ( a.endDate <= :endDate )");
		}

		if (paramMap.get("endDate_start") != null) {
			Object obj = paramMap.get("endDate_start");
			if (obj instanceof java.util.Date) {
				params.put("endDate_start", paramMap.get("endDate_start"));
			} else {
				String dateTime = (String) obj;
				if (!dateTime.endsWith(" 00:00:00")) {
					dateTime += " 00:00:00";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("endDate_start", date);
			}
			buffer.append(" and ( a.endDate >= :endDate_start )");
		}

		if (paramMap.get("endDate_end") != null) {
			Object obj = paramMap.get("endDate_end");
			if (obj instanceof java.util.Date) {
				params.put("endDate_end", paramMap.get("endDate_end"));
			} else {
				String dateTime = (String) obj;
				if (!dateTime.endsWith(" 23:59:59")) {
					dateTime += " 23:59:59";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("endDate_end", date);
			}
			buffer.append(" and ( a.endDate <= :endDate_end )");
		}

		if (paramMap.get("alarmDate_start") != null) {
			Object obj = paramMap.get("alarmDate_start");
			if (obj instanceof java.util.Date) {
				params.put("alarmDate_start", paramMap.get("alarmDate_start"));
			} else {
				String dateTime = (String) obj;
				if (!dateTime.endsWith(" 00:00:00")) {
					dateTime += " 00:00:00";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("alarmDate_start", date);
			}
			buffer.append(" and ( a.alarmDate >= :alarmDate_start )");
		}

		if (paramMap.get("alarmDate_end") != null) {
			Object obj = paramMap.get("alarmDate_end");
			if (obj instanceof java.util.Date) {
				params.put("alarmDate_end", paramMap.get("alarmDate_end"));
			} else {
				String dateTime = (String) obj;
				if (!dateTime.endsWith(" 23:59:59")) {
					dateTime += " 23:59:59";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("alarmDate_end", date);
			}
			buffer.append(" and ( a.alarmDate <= :alarmDate_end )");
		}

		if (paramMap.get("pastDueDate_start") != null) {
			Object obj = paramMap.get("pastDueDate_start");
			if (obj instanceof java.util.Date) {
				params.put("pastDueDate_start",
						paramMap.get("pastDueDate_start"));
			} else {
				String dateTime = (String) obj;
				if (!dateTime.endsWith(" 00:00:00")) {
					dateTime += " 00:00:00";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("pastDueDate_start", date);
			}
			buffer.append(" and ( a.pastDueDate >= :pastDueDate_start )");
		}

		if (paramMap.get("pastDueDate_end") != null) {
			Object obj = paramMap.get("pastDueDate_end");
			if (obj instanceof java.util.Date) {
				params.put("pastDueDate_end", paramMap.get("pastDueDate_end"));
			} else {
				String dateTime = (String) obj;
				if (!dateTime.endsWith(" 23:59:59")) {
					dateTime += " 23:59:59";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("pastDueDate_end", date);
			}
			buffer.append(" and ( a.pastDueDate <= :pastDueDate_end )");
		}

		String orderBy = (String) paramMap.get("orderBy");
		if (orderBy != null) {
			buffer.append(" order by a.").append(orderBy).append(" asc ");
		} else {
			buffer.append(" order by a.moduleId asc ");
		}

		logger.info(buffer.toString());
		logger.info(params);

		return abstractDao.getList(buffer.toString(), params);
	}

	public List getToDoList() {
		String cacheKey = "cache_todolist";
		if (CacheFactory.get(cacheKey) != null) {
			return (List) CacheFactory.get(cacheKey);
		}
		List rows = abstractDao
				.getList(
						" from com.glaf.base.modules.todo.model.ToDo as a where a.enableFlag = 1 order by a.moduleId asc ",
						null);
		CacheFactory.put(cacheKey, rows);
		return rows;
	}

	public Map getToDoMap() {
		String cacheKey = "cache_todomap";
		if (CacheFactory.get(cacheKey) != null) {
			return (Map) CacheFactory.get(cacheKey);
		}
		Map rowMap = new LinkedHashMap();
		List rows = abstractDao
				.getList(
						" from com.glaf.base.modules.todo.model.ToDo as a where a.enableFlag = 1 order by a.moduleId asc ",
						null, null);
		if (rows != null && rows.size() > 0) {
			Iterator iterator = rows.iterator();
			while (iterator.hasNext()) {
				ToDo todo = (ToDo) iterator.next();
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
		CacheFactory.put(cacheKey, rowMap);
		return rowMap;
	}

	public SysUser getUser(String actorId) {
		SysUser user = (SysUser) sysUserService.findByAccount(actorId);
		if (user != null) {
			Set apps = user.getApps();
			Set roles = user.getRoles();
			Set functions = user.getFunctions();
			logger.info(apps);
			logger.info(roles);
			logger.info(functions);
		}
		return user;
	}

	public List getUserEntityList(String actorId) {
		return todoDAO.getUserEntityList(actorId);
	}

	public Map getUserMap() {
		String cacheKey = "cache_usermap";
		if (CacheFactory.get(cacheKey) != null) {
			return (Map) CacheFactory.get(cacheKey);
		}
		Map userMap = new HashMap();
		List users = sysUserService.getSysUserWithDeptList();
		if (users != null && users.size() > 0) {
			Iterator iterator = users.iterator();
			while (iterator.hasNext()) {
				SysUser sysUser = (SysUser) iterator.next();
				userMap.put(sysUser.getAccount(), sysUser);
			}
		}
		CacheFactory.put(cacheKey, userMap);
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
				logger.info("role.getCode():" + role.getCode());
				roleMap.put(role.getCode(), role);
			}
		}
		return roleMap;
	}

	public Map getUserTodoMap(String actorId) {
		Map todoMap = new HashMap();
		Map roleMap = new HashMap();
		Collection roles = sysUserService.findByAccount(actorId).getRoles();
		if (roles != null && roles.size() > 0) {
			Iterator iterator = roles.iterator();
			while (iterator.hasNext()) {
				SysDeptRole sysDeptRole = (SysDeptRole) iterator.next();
				SysRole role = sysDeptRole.getRole();
				logger.info("role.getCode():" + role.getCode());
				roleMap.put(role.getCode(), role.getCode());
			}
		}

		List todoList = this.getToDoList();
		if (todoList != null && todoList.size() > 0) {
			Iterator iterator = todoList.iterator();
			while (iterator.hasNext()) {
				ToDo todo = (ToDo) iterator.next();
				if (roleMap.containsKey(todo.getRoleCode())) {
					todoMap.put(new Long(todo.getId()), todo);
				}
			}
		}
		return todoMap;
	}

	public WorkCalendarService getWorkCalendarService() {
		return workCalendarService;
	}

	public boolean hasTask(String actorId, String taskInstanceId) {
		Object[] values = { actorId, taskInstanceId };
		List rows = abstractDao
				.getList(
						" from com.glaf.base.modules.todo.model.ToDoInstance as a where a.actorId = ? and a.taskInstanceId = ? ",
						values, null);
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
				ToDoInstance tdi = (ToDoInstance) iterator008.next();
				int status = TodoConstants.getTodoStatus(tdi);
				ToDoInstance xx = (ToDoInstance) dataMap.get(new Long(tdi
						.getTodoId()));
				if (xx == null) {
					xx = new ToDoInstance();
					xx.setTodoId(tdi.getTodoId());
					ToDo todo = (ToDo) todoMap.get(new Long(tdi.getTodoId()));
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
				dataMap.put(new Long(tdi.getTodoId()), xx);
			}
		}
		return dataMap.values();
	}

	public void saveAll(List<ToDo> rows) {
		if (rows != null && !rows.isEmpty()) {
			for (ToDo todo : rows) {
				if (this.getToDo(todo.getId()) == null) {
					todo.setEnableFlag(1);
					todo.setVersionNo(System.currentTimeMillis());
					this.create(todo);
				} else {
					this.update(todo);
				}
			}
		}
	}

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
	}

	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setTodoDAO(TodoDAO todoDAO) {
		this.todoDAO = todoDAO;
	}

	public void setWorkCalendarService(WorkCalendarService workCalendarService) {
		this.workCalendarService = workCalendarService;
	}

	public void update(ToDo todo) {
		abstractDao.update(todo);
	}

}
