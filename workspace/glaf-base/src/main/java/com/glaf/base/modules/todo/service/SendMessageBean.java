package com.glaf.base.modules.todo.service;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.core.mail.service.MailMessage;
import org.jpage.core.mail.util.MailTools;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.mail.MailSender;

import org.jpage.jbpm.service.ProcessContainer;
import org.jpage.util.DateTools;
import org.jpage.util.UUID32;

import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysDeptRole;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysDeptRoleService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.todo.TodoConstants;
import com.glaf.base.modules.todo.model.TMembership;
import com.glaf.base.modules.todo.model.ToDo;
import com.glaf.base.modules.todo.model.ToDoInstance;
import com.glaf.base.modules.workspace.model.Message;

public class SendMessageBean {
	private final static Log logger = LogFactory.getLog(SendMessageBean.class);

	private TodoService todoService;

	private SysUserService sysUserService;

	private SysDeptRoleService sysDeptRoleService;

	private MembershipService membershipService;

	public SendMessageBean() {

	}

	public List getTodoInstances(String actorId) {
		List list = new ArrayList();
		SysUser user = sysUserService.findByAccountWithAll(actorId);

		if (user == null) {
			return list;
		}

		user = sysUserService.getUserPrivileges(user);

		logger.info("user name:" + user.getName());

		Collection agentIds = ProcessContainer.getContainer().getAgentIds(
				actorId);

		Collection appXIds = new HashSet();
		Collection appIds = new HashSet();

		Collection apps = user.getApps();

		SysDepartment dept = user.getDepartment();

		logger.info("apps size:" + apps.size());

		Iterator it = apps.iterator();
		while (it.hasNext()) {
			SysApplication app = (SysApplication) it.next();
			appIds.add(new Long(app.getId()));
			appXIds.add(new Integer((int) app.getId()));
		}

		if (agentIds != null && agentIds.size() > 0) {
			Iterator iter = agentIds.iterator();
			while (iter.hasNext()) {
				String agentId = (String) iter.next();
				// System.out.println("000-----"+agentId);
				SysUser u = sysUserService.findByAccountWithAll(agentId);
				if (u != null) {
					u = sysUserService.getUserPrivileges(u);
					Collection appx = u.getApps();
					Iterator it2 = appx.iterator();
					while (it2.hasNext()) {
						SysApplication app = (SysApplication) it2.next();
						appIds.add(new Long(app.getId()));
						appXIds.add(new Integer((int) app.getId()));
					}
				}
			}
		}

		Set roleCodes = new HashSet();

		Collection roles = user.getRoles();
		if (roles != null && roles.size() > 0) {
			Iterator iteratorxy = roles.iterator();
			while (iteratorxy.hasNext()) {
				SysDeptRole sysDeptRole = (SysDeptRole) iteratorxy.next();
				if (sysDeptRole != null) {
					SysRole role = sysDeptRole.getRole();
					roleCodes.add(role.getCode());
				}
			}
		}

		Collection rows99 = new ArrayList();

		if (appXIds.size() > 0) {
			Collection actorIds = new HashSet();
			actorIds.add(actorId);
			if (agentIds != null && agentIds.size() > 0) {
				actorIds.addAll(agentIds);
			}
			Map params = new HashMap();
			Collection taskInstanceIds = ProcessContainer.getContainer()
					.getRunningTaskInstanceIds(actorIds);
			if (taskInstanceIds != null && taskInstanceIds.size() > 0) {
				params.put("taskInstanceIds", taskInstanceIds);
				params.put("actorIds", actorIds);
				params.put("provider", "jbpm");
			} else {
				params.put("actorIds", actorIds);
				params.put("provider", "jbpm");
			}

			/**
			 * 获取工作流的TODO实例
			 */
			Collection rows = todoService.getToDoInstanceList(params);
			if (rows != null && rows.size() > 0) {
				logger.info(user.getName() + "的工作流任务有" + rows.size() + "项.");
				Iterator iterator008 = rows.iterator();
				while (iterator008.hasNext()) {
					// update by key 2012-05-14 begin
					ToDoInstance tdi = (ToDoInstance) iterator008.next();
					String processName = "";
					ToDo toDo = todoService.getToDo(tdi.getTodoId());
					if (null != toDo)
						processName = toDo.getProcessName();
					if (actorId.equals(tdi.getActorId())) {
						rows99.add(tdi);
					} else {
						// System.out.println(actorId+"---"+tdi.getActorId()+"---"+processName);
						Collection agentList = ProcessContainer.getContainer()
								.getAgentIds(actorId, processName);
						if (null != agentList && agentList.size() > 0) {// 判断代理是否有该流程代理，有则add
							rows99.add(tdi);
						}
					}
					// update by key 2012-05-14 end
					logger.info("todo:" + tdi.getTodoId() + " rowId:"
							+ tdi.getRowId());
				}
			}

			Map p = new HashMap();
			p.put("provider", "sql");
			p.put("appIds", appXIds);

			/**
			 * 根据TODO sql配置取得的TODO实例
			 */
			Collection rows_sql = todoService.getToDoInstanceList(p);

			if (rows_sql != null && rows_sql.size() > 0) {
				logger.info("全部的sql任务共有" + rows_sql.size() + "项.");
				Set rowIds = new HashSet();
				int qty = 0;
				Iterator iter9988 = rows_sql.iterator();
				while (iter9988.hasNext()) {
					ToDoInstance model = (ToDoInstance) iter9988.next();
					if (model.getActorId() != null) {
						if (model.getActorId().equals(user.getAccount())) {
							// ##System.out.println("11111111111111 " + model);
							if (!rowIds.contains(model.getTodoId() + "-"
									+ model.getRowId())) {
								rowIds.add(model.getTodoId() + "-"
										+ model.getRowId());
								rows99.add(model);
								logger.info("todo:" + model.getTodoId()
										+ " rowId:" + model.getRowId());
								qty++;
							}
						}
					} else {
						if (dept != null && model.getDeptId() > 0) {
							if (model.getDeptId() == dept.getId()) {
								if (model.getTodoId() == 7003
										|| model.getTodoId() == 7004) {
									if (roleCodes.contains(model.getRoleCode())) {
										// ##System.out.println(
										// "2222222222222222 " + model);
										if (!rowIds.contains(model.getTodoId()
												+ "-" + model.getRowId())) {
											rowIds.add(model.getTodoId() + "-"
													+ model.getRowId());
											rows99.add(model);
											logger.info("todo:"
													+ model.getTodoId()
													+ " rowId:"
													+ model.getRowId());
											qty++;
										}
									}
								} else {
									// ##System.out.println("33333333333333333 "
									// + model);
									if (!rowIds.contains(model.getTodoId()
											+ "-" + model.getRowId())) {
										rowIds.add(model.getTodoId() + "-"
												+ model.getRowId());
										rows99.add(model);
										logger.info("todo:" + model.getTodoId()
												+ " rowId:" + model.getRowId());
										qty++;
									}
								}
							}
						} else {
							// ##System.out.println("4444444444444444 " +
							// model);
							if (!rowIds.contains(model.getTodoId() + "-"
									+ model.getRowId())) {
								rowIds.add(model.getTodoId() + "-"
										+ model.getRowId());
								rows99.add(model);
								logger.info("todo:" + model.getTodoId()
										+ " rowId:" + model.getRowId());
								qty++;
							}
						}
					}
				}
				logger.info(user.getName() + "的sql任务有" + qty + "项.");
			}
		}

		Map qtyMap = new HashMap();
		Set linkTypes = new HashSet();
		Map todoMap = todoService.getToDoMap();

		Collection todoList = todoService.getToDoList();

		logger.info(user.getName() + "的TODO任务有" + rows99.size() + "项.");

		/**
		 * 处理TODO实例
		 */
		if (rows99 != null && rows99.size() > 0) {
			Iterator iterator = rows99.iterator();
			while (iterator.hasNext()) {
				ToDoInstance tdi = (ToDoInstance) iterator.next();
				int status = TodoConstants.getTodoStatus(tdi);
				tdi.setStatus(status);
				ToDo todo = (ToDo) todoMap.get(new Long(tdi.getTodoId()));
				if (todo == null) {
					continue;
				}
				ToDoInstance y = (ToDoInstance) qtyMap.get(todo.getLinkType());
				if (y == null) {
					y = new ToDoInstance();
					y.setTodoId(tdi.getTodoId());
					y.setDeptId(tdi.getDeptId());
					y.setRoleCode(tdi.getRoleCode());
					if (todo != null) {
						y.setToDo(todo);
						y.setTitle(todo.getTitle());
						y.setContent(todo.getContent());
						y.setLink(todo.getLink());
						y.setListLink(todo.getListLink());
						y.setLinkType(todo.getLinkType());
					}
				}

				switch (status) {
				case TodoConstants.OK_STATUS:
					y.setQty01(y.getQty01() + 1);
					y.getToDo().getOk().add(tdi.getRowId());
					break;
				case TodoConstants.CAUTION_STATUS:
					y.setQty02(y.getQty02() + 1);
					y.getToDo().getCaution().add(tdi.getRowId());
					break;
				case TodoConstants.PAST_DUE_STATUS:
					y.setQty03(y.getQty03() + 1);
					y.getToDo().getPastDue().add(tdi.getRowId());
					break;
				default:
					y.getToDo().getOk().add(tdi.getRowId());
					y.setQty01(y.getQty01() + 1);
					break;
				}
				qtyMap.put(todo.getLinkType(), y);
			}
		}

		if (todoList != null && todoList.size() > 0) {
			Iterator iter = todoList.iterator();
			while (iter.hasNext()) {
				ToDo todo = (ToDo) iter.next();
				boolean isOK = false;
				if (appIds.contains(new Long(todo.getAppId()))) {
					isOK = true;
				}
				if (!isOK) {
					continue;
				}
				if (linkTypes.contains(todo.getLinkType())) {
					continue;
				}
				linkTypes.add(todo.getLinkType());
				ToDoInstance tdi = (ToDoInstance) qtyMap
						.get(todo.getLinkType());
				if (tdi == null) {
					tdi = new ToDoInstance();
					tdi.setTodoId(todo.getId());
					tdi.setToDo(todo);
				}
				list.add(tdi);
			}
		}

		return list;
	}

	public TodoService getTodoService() {
		return todoService;
	}

	public List getXYTodoInstances(String actorId) {
		List list = new ArrayList();
		SysUser user = sysUserService.findByAccount(actorId);

		if (user == null) {
			return list;
		}

		user = sysUserService.getUserPrivileges(user);

		logger.info("user name:" + user.getName());

		Collection agentIds = ProcessContainer.getContainer().getAgentIds(
				actorId);

		Collection appXIds = new HashSet();
		Collection appIds = new HashSet();

		Collection apps = user.getApps();

		SysDepartment dept = user.getDepartment();

		logger.info("apps size:" + apps.size());

		Iterator it = apps.iterator();
		while (it.hasNext()) {
			SysApplication app = (SysApplication) it.next();
			appIds.add(new Long(app.getId()));
			appXIds.add(new Integer((int) app.getId()));
		}

		if (agentIds != null && agentIds.size() > 0) {
			Iterator iter = agentIds.iterator();
			while (iter.hasNext()) {
				String agentId = (String) iter.next();
				SysUser u = sysUserService.findByAccount(agentId);
				if (u != null) {
					u = sysUserService.getUserPrivileges(u);
					Collection appx = u.getApps();
					Iterator it2 = appx.iterator();
					while (it2.hasNext()) {
						SysApplication app = (SysApplication) it2.next();
						appIds.add(new Long(app.getId()));
						appXIds.add(new Integer((int) app.getId()));
					}
				}
			}
		}

		Set roleCodes = new HashSet();

		Collection roles = user.getRoles();
		if (roles != null && roles.size() > 0) {
			Iterator iteratorxy = roles.iterator();
			while (iteratorxy.hasNext()) {
				SysDeptRole sysDeptRole = (SysDeptRole) iteratorxy.next();
				SysRole role = sysDeptRole.getRole();
				roleCodes.add(role.getCode());
			}
		}

		Collection rows99 = new ArrayList();

		if (appXIds.size() > 0) {

			Collection actorIds = new HashSet();
			actorIds.add(actorId);
			if (agentIds != null && agentIds.size() > 0) {
				actorIds.addAll(agentIds);
			}

			Map params = new HashMap();
			params.put("actorIds", actorIds);
			params.put("provider", "jbpm");

			/**
			 * 获取工作流的TODO实例
			 */
			Collection rows = todoService.getToDoInstanceList(params);

			if (rows != null && rows.size() > 0) {
				logger.info(user.getName() + "的工作流任务有" + rows.size() + "项.");
				Iterator iterator008 = rows.iterator();
				while (iterator008.hasNext()) {
					// ToDoInstance tdi = (ToDoInstance) iterator008.next();
					// rows99.add(tdi);
					// update by key 2012-05-14 begin
					ToDoInstance tdi = (ToDoInstance) iterator008.next();
					String processName = "";
					ToDo toDo = todoService.getToDo(tdi.getTodoId());
					if (null != toDo)
						processName = toDo.getProcessName();
					if (actorId.equals(tdi.getActorId())) {
						rows99.add(tdi);
					} else {
						// System.out.println(actorId+"---"+tdi.getActorId()+"---"+processName);
						Collection agentList = ProcessContainer.getContainer()
								.getAgentIds(actorId, processName);
						if (null != agentList && agentList.size() > 0) {// 判断代理是否有该流程代理，有则add
							rows99.add(tdi);
						}
					}
					// update by key 2012-05-14 end
				}
			}

			Map p = new HashMap();
			p.put("provider", "sql");
			p.put("appIds", appXIds);

			/**
			 * 根据TODO sql配置取得的TODO实例
			 */
			Collection rows_sql = todoService.getToDoInstanceList(p);

			if (rows_sql != null && rows_sql.size() > 0) {
				logger.info("全部的sql任务共有" + rows_sql.size() + "项.");
				int qty = 0;
				Set rowIds = new HashSet();
				Iterator iter9988 = rows_sql.iterator();
				while (iter9988.hasNext()) {
					ToDoInstance model = (ToDoInstance) iter9988.next();
					if (model.getActorId() != null) {
						if (model.getActorId().equals(user.getAccount())) {
							if (!rowIds.contains(model.getRowId())) {
								rowIds.add(model.getRowId());
								rows99.add(model);
								qty++;
							}
						}
					} else {
						if (dept != null && model.getDeptId() > 0) {
							if (model.getDeptId() == dept.getId()) {
								if (model.getTodoId() == 7003
										|| model.getTodoId() == 7004) {
									if (roleCodes.contains(model.getRoleCode())) {
										if (!rowIds.contains(model.getRowId())) {
											rowIds.add(model.getRowId());
											rows99.add(model);
											qty++;
										}
									}
								} else {
									if (!rowIds.contains(model.getRowId())) {
										rowIds.add(model.getRowId());
										rows99.add(model);
										qty++;
									}
								}
							}
						} else {
							if (!rowIds.contains(model.getRowId())) {
								rowIds.add(model.getRowId());
								rows99.add(model);
								qty++;
							}
						}
					}
				}
				logger.info(user.getName() + "的sql任务有" + qty + "项.");
			}
		}

		Map qtyMap = new HashMap();
		Set linkTypes = new HashSet();
		Map todoMap = todoService.getToDoMap();

		Collection todoList = todoService.getToDoList();

		logger.info(user.getName() + "的TODO任务有" + rows99.size() + "项.");

		/**
		 * 处理TODO实例
		 */
		if (rows99 != null && rows99.size() > 0) {
			Iterator iterator = rows99.iterator();
			while (iterator.hasNext()) {
				ToDoInstance tdi = (ToDoInstance) iterator.next();
				int status = TodoConstants.getTodoStatus(tdi);
				tdi.setStatus(status);
				ToDo todo = (ToDo) todoMap.get(new Long(tdi.getTodoId()));
				if (todo == null) {
					continue;
				}
				ToDoInstance y = (ToDoInstance) qtyMap.get(todo.getLinkType());
				if (y == null) {
					y = new ToDoInstance();
					y.setTodoId(tdi.getTodoId());
					y.setDeptId(tdi.getDeptId());
					y.setRoleCode(tdi.getRoleCode());
					if (todo != null) {
						y.setTitle(todo.getTitle());
						y.setContent(todo.getContent());
						y.setLink(todo.getLink());
						y.setListLink(todo.getListLink());
						y.setLinkType(todo.getLinkType());
					}
				}

				switch (status) {
				case TodoConstants.OK_STATUS:
					y.setQty01(y.getQty01() + 1);
					y.getToDo().getOk().add(tdi.getRowId());
					break;
				case TodoConstants.CAUTION_STATUS:
					y.setQty02(y.getQty02() + 1);
					y.getToDo().getCaution().add(tdi.getRowId());
					break;
				case TodoConstants.PAST_DUE_STATUS:
					y.setQty03(y.getQty03() + 1);
					y.getToDo().getPastDue().add(tdi.getRowId());
					break;
				default:
					y.getToDo().getOk().add(tdi.getRowId());
					y.setQty01(y.getQty01() + 1);
					break;
				}
				qtyMap.put(todo.getLinkType(), y);
			}
		}

		if (todoList != null && todoList.size() > 0) {
			Iterator iter = todoList.iterator();
			while (iter.hasNext()) {
				ToDo todo = (ToDo) iter.next();
				boolean isOK = false;
				if (appIds.contains(new Long(todo.getAppId()))) {
					isOK = true;
				}
				if (!isOK) {
					continue;
				}
				if (linkTypes.contains(todo.getLinkType())) {
					continue;
				}
				linkTypes.add(todo.getLinkType());
				ToDoInstance tdi = (ToDoInstance) qtyMap
						.get(todo.getLinkType());
				if (tdi == null) {
					tdi = new ToDoInstance();
					tdi.setToDo(todo);
				}

				list.add(tdi);
			}
		}

		return list;
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

	public void sendMessageToUser(String actorId) {
		this.sendMessageToUser(actorId, false);
	}

	public void sendMessageToUser(String actorId, boolean notifySuperior) {

		SysUser user = sysUserService.findByAccount(actorId);

		if (user == null) {
			return;
		}

		user = sysUserService.getUserPrivileges(user);

		logger.info("user name:" + user.getName());

		Collection agentIds = ProcessContainer.getContainer().getAgentIds(
				actorId);

		Collection appXIds = new HashSet();
		Collection appIds = new HashSet();

		Collection apps = user.getApps();

		SysDepartment dept = user.getDepartment();

		logger.info("apps size:" + apps.size());

		Iterator it = apps.iterator();
		while (it.hasNext()) {
			SysApplication app = (SysApplication) it.next();
			appIds.add(new Long(app.getId()));
			appXIds.add(new Integer((int) app.getId()));
		}

		if (agentIds != null && agentIds.size() > 0) {
			Iterator iter = agentIds.iterator();
			while (iter.hasNext()) {
				String agentId = (String) iter.next();
				SysUser u = sysUserService.findByAccount(agentId);
				if (u != null) {
					u = sysUserService.getUserPrivileges(u);
					Collection appx = u.getApps();
					Iterator it2 = appx.iterator();
					while (it2.hasNext()) {
						SysApplication app = (SysApplication) it2.next();
						appIds.add(new Long(app.getId()));
						appXIds.add(new Integer((int) app.getId()));
					}
				}
			}
		}

		Set roleCodes = new HashSet();

		Collection roles = user.getRoles();
		if (roles != null && roles.size() > 0) {
			Iterator iteratorxy = roles.iterator();
			while (iteratorxy.hasNext()) {
				SysDeptRole sysDeptRole = (SysDeptRole) iteratorxy.next();
				SysRole role = sysDeptRole.getRole();
				roleCodes.add(role.getCode());
			}
		}

		Collection rows99 = new ArrayList();

		if (appXIds.size() > 0) {

			Collection actorIds = new HashSet();
			actorIds.add(actorId);
			if (agentIds != null && agentIds.size() > 0) {
				actorIds.addAll(agentIds);
			}
			Map params = new HashMap();
			Collection taskInstanceIds = ProcessContainer.getContainer()
					.getRunningTaskInstanceIds(actorIds);
			if (taskInstanceIds != null && taskInstanceIds.size() > 0) {
				params.put("taskInstanceIds", taskInstanceIds);
				params.put("actorIds", actorIds);
				params.put("provider", "jbpm");
			} else {
				params.put("actorIds", actorIds);
				params.put("provider", "jbpm");
			}

			/**
			 * 获取工作流的TODO实例
			 */
			Collection rows = todoService.getToDoInstanceList(params);

			if (rows != null && rows.size() > 0) {
				// ##System.out.println(user.getName() + "的工作流任务有" +
				// rows.size()+ "项.");
				Iterator iterator008 = rows.iterator();
				while (iterator008.hasNext()) {
					// ToDoInstance tdi = (ToDoInstance) iterator008.next();
					// rows99.add(tdi);
					// update by key 2012-05-14 begin
					ToDoInstance tdi = (ToDoInstance) iterator008.next();
					String processName = "";
					ToDo toDo = todoService.getToDo(tdi.getTodoId());
					if (null != toDo)
						processName = toDo.getProcessName();
					if (actorId.equals(tdi.getActorId())) {
						rows99.add(tdi);
					} else {
						// System.out.println(actorId+"---"+tdi.getActorId()+"---"+processName);
						Collection agentList = ProcessContainer.getContainer()
								.getAgentIds(actorId, processName);
						if (null != agentList && agentList.size() > 0) {// 判断代理是否有该流程代理，有则add
							rows99.add(tdi);
						}
					}
					// update by key 2012-05-14 end
				}
			}

			Map p = new HashMap();
			p.put("provider", "sql");
			p.put("appIds", appXIds);

			/**
			 * 根据TODO sql配置取得的TODO实例
			 */
			Collection rows_sql = todoService.getToDoInstanceList(p);

			if (rows_sql != null && rows_sql.size() > 0) {
				logger.info("全部的sql任务共有" + rows_sql.size() + "项.");
				int qty = 0;
				Set rowIds = new HashSet();
				Iterator iter9988 = rows_sql.iterator();
				while (iter9988.hasNext()) {
					ToDoInstance model = (ToDoInstance) iter9988.next();
					if (model.getActorId() != null) {
						if (model.getActorId().equals(user.getAccount())) {
							if (!rowIds.contains(model.getRowId())) {
								rowIds.add(model.getRowId());
								rows99.add(model);
								qty++;
							}
						}
					} else {
						if (dept != null && model.getDeptId() > 0) {
							if (model.getDeptId() == dept.getId()) {
								if (model.getTodoId() == 7003
										|| model.getTodoId() == 7004) {
									if (roleCodes.contains(model.getRoleCode())) {
										if (!rowIds.contains(model.getRowId())) {
											rowIds.add(model.getRowId());
											rows99.add(model);
											qty++;
										}
									}
								} else {
									if (!rowIds.contains(model.getRowId())) {
										rowIds.add(model.getRowId());
										rows99.add(model);
										qty++;
									}
								}
							}
						} else {
							if (!rowIds.contains(model.getRowId())) {
								rowIds.add(model.getRowId());
								rows99.add(model);
								qty++;
							}
						}
					}
				}
				logger.info(user.getName() + "的sql任务有" + qty + "项.");
			}
		}

		Map qtyMap = new HashMap();
		Set linkTypes = new HashSet();
		Map todoMap = todoService.getToDoMap();

		Collection todoList = todoService.getToDoList();

		logger.info(user.getName() + "的TODO任务有" + rows99.size() + "项.");

		/**
		 * 处理TODO实例
		 */
		if (rows99 != null && rows99.size() > 0) {
			Date now = new Date();
			Iterator iterator = rows99.iterator();
			while (iterator.hasNext()) {
				ToDoInstance tdi = (ToDoInstance) iterator.next();
				int status = TodoConstants.getTodoStatus(tdi);
				tdi.setStatus(status);
				ToDo todo = (ToDo) todoMap.get(new Long(tdi.getTodoId()));
				if (todo == null) {
					continue;
				}
				ToDoInstance xy = (ToDoInstance) qtyMap.get(todo.getLinkType());
				if (xy == null) {
					xy = new ToDoInstance();
					xy.setTodoId(tdi.getTodoId());
					xy.setDeptId(tdi.getDeptId());
					xy.setRoleCode(tdi.getRoleCode());
					if (todo != null) {
						xy.setToDo(todo);
						xy.setTitle(todo.getTitle());
						xy.setContent(todo.getContent());
						xy.setLink(todo.getLink());
						xy.setListLink(todo.getListLink());
						xy.setLinkType(todo.getLinkType());
					}
				}

				switch (status) {
				case TodoConstants.OK_STATUS:
					xy.setQty01(xy.getQty01() + 1);
					break;
				case TodoConstants.CAUTION_STATUS:
					xy.setQty02(xy.getQty02() + 1);
					break;
				case TodoConstants.PAST_DUE_STATUS:
					xy.setQty03(xy.getQty03() + 1);
					Date pastDue = tdi.getPastDueDate();
					if (pastDue != null) {
						// 超过7天
						if ((now.getTime() - pastDue.getTime()) > DateTools.WEEK) {
							xy.setQtyRedWarn(xy.getQtyRedWarn() + 1);
						}
					}
					break;
				default:
					xy.setQty03(xy.getQty01() + 1);
					break;
				}
				qtyMap.put(todo.getLinkType(), xy);
			}

			if (todoList != null && todoList.size() > 0) {
				Collection rowsXY = new ArrayList();
				Collection redWarnXY = new ArrayList();
				Iterator iter = todoList.iterator();
				while (iter.hasNext()) {
					ToDo todo = (ToDo) iter.next();
					boolean isOK = false;
					if (appIds.contains(new Long(todo.getAppId()))) {
						isOK = true;
					}
					if (!isOK) {
						continue;
					}
					if (linkTypes.contains(todo.getLinkType())) {
						continue;
					}
					linkTypes.add(todo.getLinkType());
					ToDoInstance tdi = (ToDoInstance) qtyMap.get(todo
							.getLinkType());
					if (tdi != null) {
						rowsXY.add(tdi);
						if (tdi.getQtyRedWarn() > 0) {
							redWarnXY.add(tdi);
						}
					}
				}

				if (rowsXY.size() > 0) {
					Map mailMap = new HashMap();
					mailMap.put("user", user);
					mailMap.put("dept", dept);
					mailMap.put("rows", rowsXY);

					String subject = (dept != null ? dept.getName() : "") + " "
							+ user.getName() + " "
							+ DateTools.getDate(new Date()) + " 特定采购系统Todo事项";
					logger.info(subject);
					MailMessage mailMessage = new MailMessage();
					mailMessage.setDataMap(mailMap);
					mailMessage.setTo(user.getEmail());
					// mailMessage.setCc("pps@glaf.com.cn");
					// mailMessage.setBcc("pps@glaf.com.cn");
					mailMessage.setTemplateId("tms_todo");
					mailMessage.setSaveMessage(false);
					mailMessage.setReplyTo("pps@glaf.com.cn");
					mailMessage.setMessageId(UUID32.getUUID());
					mailMessage.setSubject(subject);
					try {
						MailSender mailSender = (MailSender) JbpmContextFactory
								.getBean("velocityMailSender");
						mailSender.send(mailMessage);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				if (redWarnXY.size() > 0) {
					// 通知直接上级
					if (notifySuperior) {
						List memberships = membershipService
								.getMemberships(actorId);
						if (memberships != null && memberships.size() > 0) {
							Iterator iterxy = memberships.iterator();
							while (iterxy.hasNext()) {
								TMembership m = (TMembership) iterxy.next();
								SysUser leader = sysUserService.findByAccount(m
										.getSuperiorId());
								if (leader == null
										|| StringUtils.isEmpty(leader
												.getEmail())) {
									continue;
								}
								Map mailMap = new HashMap();
								mailMap.put("user", user);
								mailMap.put("dept", dept);
								mailMap.put("leader", leader);
								mailMap.put("rows", redWarnXY);

								String subject = (dept != null ? dept.getName()
										: "")
										+ " "
										+ user.getName()
										+ " "
										+ DateTools.getDate(new Date())
										+ " 特定采购系统超过7天期限TODO事项";
								logger.info(subject);
								MailMessage mailMessage = new MailMessage();
								mailMessage.setDataMap(mailMap);
								mailMessage.setTo(leader.getEmail());
								// mailMessage.setCc("pps@glaf.com.cn");
								// mailMessage.setBcc("pps@glaf.com.cn");
								mailMessage.setTemplateId("tms_todo_redwarn");
								mailMessage.setSaveMessage(false);
								mailMessage.setReplyTo("pps@glaf.com.cn");
								mailMessage.setMessageId(UUID32.getUUID());
								mailMessage.setSubject(subject);
								try {
									MailSender mailSender = (MailSender) JbpmContextFactory
											.getBean("velocityMailSender");
									mailSender.send(mailMessage);
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
	}

	public void sendMessagex() {
		logger.info("...................sendMessage().....................");
		Map params = new HashMap();
		List rows = todoService.getToDoInstanceList(params);
		if (rows == null || rows.size() == 0) {
			return;
		}

		logger.info("todo 事项数量:" + rows.size());

		Map dataMap = new HashMap();
		Map roleMap = new HashMap();

		Iterator iterator008 = rows.iterator();
		while (iterator008.hasNext()) {
			ToDoInstance tdi = (ToDoInstance) iterator008.next();
			String actorId = tdi.getActorId();
			String roleCode = tdi.getRoleCode();
			int status = TodoConstants.getTodoStatus(tdi);
			switch (status) {
			case TodoConstants.OK_STATUS:
				break;
			case TodoConstants.CAUTION_STATUS:
			case TodoConstants.PAST_DUE_STATUS:
				if (actorId != null) {
					List list = (List) dataMap.get(actorId);
					if (list == null) {
						list = new ArrayList();
					}
					list.add(tdi);
					dataMap.put(actorId, list);
				}
				if (roleCode != null) {
					List listx = (List) roleMap.get(roleCode);
					if (listx == null) {
						listx = new ArrayList();
					}
					listx.add(tdi);
					roleMap.put(roleCode, listx);
				}
				break;
			default:
				break;
			}
		}

		logger.info("dataMap size:" + dataMap.size());

		Map todoMap = todoService.getToDoMap();
		Map userMap = todoService.getUserMap();

		Iterator iterator = dataMap.keySet().iterator();
		while (iterator.hasNext()) {
			String actorId = (String) iterator.next();
			List list = (List) dataMap.get(actorId);
			Collection oks = new ArrayList();
			Collection alerts = new ArrayList();
			Collection pastDues = new ArrayList();
			Iterator iterator009 = list.iterator();
			while (iterator009.hasNext()) {
				ToDoInstance tdi = (ToDoInstance) iterator009.next();
				int status = TodoConstants.getTodoStatus(tdi);
				switch (status) {
				case TodoConstants.OK_STATUS:
					oks.add(tdi);
					alerts.add(tdi);
					break;
				case TodoConstants.CAUTION_STATUS:
					alerts.add(tdi);
					break;
				case TodoConstants.PAST_DUE_STATUS:
					alerts.add(tdi);
					pastDues.add(tdi);
					break;
				default:
					break;
				}
			}

			oks = this.populate(oks, todoMap);
			alerts = this.populate(alerts, todoMap);
			pastDues = this.populate(pastDues, todoMap);

			logger.info("caution size:" + alerts.size());
			logger.info("past due size:" + pastDues.size());

			if (alerts.size() > 0) {
				SysUser user = (SysUser) userMap.get(actorId);
				List messages = new ArrayList();
				Iterator iteratorxy = alerts.iterator();
				while (iteratorxy.hasNext()) {
					ToDoInstance tdi = (ToDoInstance) iteratorxy.next();
					Message message = new Message();
					message.setTitle(tdi.getTitle());
					message.setContent(tdi.getContent());
					message.setCreateDate(new Date());
					message.setRecver(user);
					messages.add(message);
					logger.info("message:" + message.getContent());
				}
				todoService.saveAll(messages);
			}

			if (oks.size() > 0 || alerts.size() > 0 || pastDues.size() > 0) {
				SysUser user = (SysUser) userMap.get(actorId);
				if (user == null) {
					continue;
				}
				SysDepartment dept = user.getDepartment();

				Map mailMap = new HashMap();
				mailMap.put("user", user);
				mailMap.put("dept", dept);
				mailMap.put("rows", alerts);
				mailMap.put("rowsOK", oks);

				Set actors = new HashSet();

				if (pastDues.size() > 0) {
					// 发邮件提示TODO的担当的上级。
					// 当角色为部长时，邮件提示TODO发送给部门内的系统管理员。
					SysUser sysUser = sysUserService.findByAccount(user
							.getAccount());
					sysUser = sysUserService.getUserPrivileges(sysUser);
					Collection roles = sysUser.getRoles();
					if (logger.isDebugEnabled()) {
						logger.debug("查找" + user.getName() + "的角色");
					}
					if (roles != null && roles.size() > 0) {
						if (logger.isDebugEnabled()) {
							logger.debug("roles size:" + roles.size());
						}
						Iterator iter = roles.iterator();
						while (iter.hasNext()) {
							SysDeptRole role = (SysDeptRole) iter.next();
							String code = role.getRole().getCode();
							if (logger.isDebugEnabled()) {
								logger.debug("role code:" + code);
							}
							if ("R001".equals(code)) {
								Collection users = sysDeptRoleService
										.findRoleUser(dept.getId(), "R006");
								if (users != null && users.size() > 0) {
									if (logger.isDebugEnabled()) {
										logger.debug("[R001-R006] users size:"
												+ users.size());
									}
									actors.addAll(users);
								}
							}
							if ("R002".equals(code) || "R017".equals(code)) {
								Collection users = sysDeptRoleService
										.findRoleUser(dept.getId(), "R001");
								if (users != null && users.size() > 0) {
									if (logger.isDebugEnabled()) {
										logger.debug("[R002-R017-R001] users size:"
												+ users.size());
									}
									actors.addAll(users);
								}
							}
							if ("R003".equals(code)) {
								Collection users = sysDeptRoleService
										.findRoleUser(dept.getId(), "R002");
								if (users != null && users.size() > 0) {
									if (logger.isDebugEnabled()) {
										logger.debug("[R003-R002]-> users size:"
												+ users.size());
									}
									actors.addAll(users);
								} else {
									users = sysDeptRoleService.findRoleUser(
											dept.getId(), "R001");
									if (users != null && users.size() > 0) {
										if (logger.isDebugEnabled()) {
											logger.debug("[R003-R001]-> users size:"
													+ users.size());
										}
										actors.addAll(users);
									}
								}
							}
							if ("R004".equals(code)) {
								Collection users = sysDeptRoleService
										.findRoleUser(dept.getId(), "R003");
								if (users != null && users.size() > 0) {
									if (logger.isDebugEnabled()) {
										logger.debug("[R004-R003] users size:"
												+ users.size());
									}
									actors.addAll(users);
								} else {
									users = sysDeptRoleService.findRoleUser(
											dept.getId(), "R002");
									if (users != null && users.size() > 0) {
										if (logger.isDebugEnabled()) {
											logger.debug("[R004-R002] users size:"
													+ users.size());
										}
										actors.addAll(users);
									}
								}
							}
						}
					}
				}

				if (actors.size() > 0) {
					Iterator it = actors.iterator();
					while (it.hasNext()) {
						SysUser u = (SysUser) it.next();

						if (!MailTools.isMailAddress(u.getEmail())) {
							continue;
						}

						mailMap.put("leader", u);

						String subject = dept.getName() + " " + user.getName()
								+ " " + DateTools.getDateTime(new Date())
								+ " 特定采购系统Todo事项";
						String messageId = DateTools.getNowYearMonthDay()
								+ "-cc-" + u.getAccount();
						MailMessage mailMessage = new MailMessage();
						mailMessage.setDataMap(mailMap);
						mailMessage.setTo(u.getEmail());
						if (StringUtils.equals(u.getAccount(),
								user.getAccount())) {
							mailMessage.setTemplateId("tms_todo");
						} else {
							mailMessage.setTemplateId("tms_todo_cc");
						}
						mailMessage.setReplyTo("pps@glaf.com.cn");
						mailMessage.setSaveMessage(false);
						mailMessage.setMessageId(messageId);
						mailMessage.setSubject(subject);

						try {
							MailSender mailSender = (MailSender) JbpmContextFactory
									.getBean("velocityMailSender");
							// mailSender.send(mailMessage);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				} else {
					if (!MailTools.isMailAddress(user.getEmail())) {
						continue;
					}
					String messageId = DateTools.getNowYearMonthDay() + "-"
							+ actorId;
					String subject = dept.getName() + " " + user.getName()
							+ " " + DateTools.getDateTime(new Date())
							+ " 特定采购系统Todo事项";
					MailMessage mailMessage = new MailMessage();
					mailMessage.setDataMap(mailMap);
					mailMessage.setTo(user.getEmail());
					mailMessage.setTemplateId("tms_todo");
					mailMessage.setSaveMessage(false);
					mailMessage.setReplyTo("pps@glaf.com.cn");
					mailMessage.setMessageId(messageId);
					mailMessage.setSubject(subject);
					try {
						MailSender mailSender = (MailSender) JbpmContextFactory
								.getBean("velocityMailSender");
						mailSender.send(mailMessage);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	public void setMembershipService(MembershipService membershipService) {
		this.membershipService = membershipService;
	}

	public void setSysDeptRoleService(SysDeptRoleService sysDeptRoleService) {
		this.sysDeptRoleService = sysDeptRoleService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setTodoService(TodoService todoService) {
		this.todoService = todoService;
	}
}
