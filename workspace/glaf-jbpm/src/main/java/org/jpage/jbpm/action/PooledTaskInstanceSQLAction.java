/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.jbpm.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;
import org.jpage.jbpm.config.ObjectFactory;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;
import org.jpage.jbpm.instantiation.CustomFieldInstantiator;
import org.jpage.jbpm.util.Constant;
import org.jpage.util.JdbcUtil;
import org.jpage.util.Tools;

public class PooledTaskInstanceSQLAction implements ActionHandler {
	private static final Log logger = LogFactory
			.getLog(PooledTaskInstanceSQLAction.class);

	private static final long serialVersionUID = 1L;

	private String sql;

	private Element elements;

	/**
	 * ��̬���õĲ����ߵĲ�������������������ͨ��contextInstance.getVariable()ȡ��
	 * ���磺contextInstance.getVariable("SendDocAuditor");
	 */
	private String dynamicActors;

	private String signalling;

	private String taskName;

	/**
	 * ������ܻ�ȡ����������Ƿ��뿪���ڵ㣨����ڵ㣩
	 */
	private String leaveNodeIfActorNotAvailable;

	private String transitionName;

	public PooledTaskInstanceSQLAction() {

	}

	public String getLeaveNodeIfActorNotAvailable() {
		return leaveNodeIfActorNotAvailable;
	}

	public void setLeaveNodeIfActorNotAvailable(
			String leaveNodeIfActorNotAvailable) {
		this.leaveNodeIfActorNotAvailable = leaveNodeIfActorNotAvailable;
	}

	public String getTransitionName() {
		return transitionName;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

	public Element getElements() {
		return elements;
	}

	public void setElements(Element elements) {
		this.elements = elements;
	}

	public String getDynamicActors() {
		return dynamicActors;
	}

	public void setDynamicActors(String dynamicActors) {
		this.dynamicActors = dynamicActors;
	}

	public String getSignalling() {
		return signalling;
	}

	public void setSignalling(String signalling) {
		this.signalling = signalling;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void execute(ExecutionContext ctx) {
		logger.debug("-------------------------------------------------------");
		logger.debug("--------------PooledTaskInstanceSQLAction--------------");
		logger.debug("-------------------------------------------------------");

		Task task = ctx.getTask();

		ContextInstance contextInstance = ctx.getContextInstance();
		List actorIds = new ArrayList();
		if (StringUtils.isNotBlank(dynamicActors)) {
			String actorId = (String) contextInstance
					.getVariable(dynamicActors);
			if (StringUtils.isNotBlank(actorId)) {
				StringTokenizer st2 = new StringTokenizer(actorId, ",");
				while (st2.hasMoreTokens()) {
					String elem = st2.nextToken();
					if (StringUtils.isNotBlank(elem)) {
						actorIds.add(elem);
					}
				}
			}
		}

		Map params = new HashMap();
		ProcessInstance processInstance = ctx.getProcessInstance();
		String processInstanceId = String.valueOf(processInstance.getId());
		params.put("processInstanceId", processInstanceId);
		String rowId = (String) contextInstance.getVariable(Constant.ROW_ID);
		String process_starter = (String) contextInstance
				.getVariable(Constant.PROCESS_START_ACTORID);
		String latestActorId = (String) contextInstance
				.getVariable(Constant.PROCESS_LATEST_ACTORID);

		if (elements != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("elements:" + elements.asXML());
			}
			Object obj = CustomFieldInstantiator.getValue(Map.class, elements);
			if (obj instanceof Map) {
				Map paramMap = (Map) obj;
				Iterator iterator = paramMap.keySet().iterator();
				while (iterator.hasNext()) {
					Object key = iterator.next();
					Object value = paramMap.get(key);
					if (key != null && value != null) {
						params.put(key, value);
					}
				}
			}
		}

		/**
		 * �����ݿ��и��ݲ�ѯ�������Ҳ�����
		 */
		if (StringUtils.isNotBlank(sql)) {
			if (logger.isDebugEnabled()) {
				logger.debug("sql:" + sql);
			}

			java.util.Date now = new java.util.Date();

			List values = new ArrayList();

			if (elements != null) {
				Map dataMap = (Map) CustomFieldInstantiator.getValue(Map.class,
						elements);
				Iterator iterator = dataMap.keySet().iterator();
				while (iterator.hasNext()) {
					Object key = iterator.next();
					Object value = dataMap.get(key);
					if (value instanceof String) {
						String tmp = (String) value;
						if (StringUtils.isNotBlank(tmp)) {
							if (tmp.equals("#{processInstanceId}")) {
								value = String.valueOf(contextInstance
										.getProcessInstance().getId());
							} else if (tmp.equals("#{taskInstanceId}")) {
								TaskInstance taskInstance = ctx
										.getTaskInstance();
								if (taskInstance != null) {
									value = String
											.valueOf(taskInstance.getId());
								}
							} else if (tmp.equals("#{taskName}")) {
								if (task != null) {
									value = task.getName();
								} else {
									if (ctx.getTaskInstance() != null) {
										value = ctx.getTaskInstance().getName();
									}
								}
							} else if (tmp.equals("now()")) {
								value = new java.sql.Date(now.getTime());
							} else if (tmp.equals("date()")) {
								value = new java.sql.Date(now.getTime());
							} else if (tmp.equals("time()")) {
								value = new java.sql.Time(now.getTime());
							} else if (tmp.equals("timestamp()")) {
								value = new java.sql.Timestamp(now.getTime());
							} else if (tmp.equals("dateTime()")) {
								value = new java.sql.Timestamp(now.getTime());
							} else if (tmp.equals("currentTimeMillis()")) {
								value = new Long(System.currentTimeMillis());
							} else if (tmp
									.equals(Constant.PROCESS_STARTER_EXPRESSION)) {
								value = process_starter;
							} else if (tmp
									.equals(Constant.PROCESS_LATESTER_EXPRESSION)) {
								value = latestActorId;
							} else if (tmp.equals("#{rowId}")) {
								value = rowId;
							} else if (tmp.startsWith("#P{")
									&& tmp.endsWith("}")) {
								tmp = Tools.replaceIgnoreCase(tmp, "#P{", "");
								tmp = Tools.replaceIgnoreCase(tmp, "}", "");
								value = contextInstance.getVariable(tmp);
							} else if (tmp.startsWith("#{")
									&& tmp.endsWith("}")) {
								value = DefaultExpressionEvaluator.evaluate(
										tmp, params);
							}
						}
					}
					values.add(value);
				}
			}

			Connection con = null;
			try {
				con = ctx.getJbpmContext().getConnection();
				PreparedStatement psmt = con.prepareStatement(sql);
				JdbcUtil.fillStatement(psmt, values);
				ResultSet rs = psmt.executeQuery();
				while (rs.next()) {
					actorIds.add(rs.getString(1));
				}
				psmt.close();
				rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
				logger.error(sql);
				logger.error(values);
				throw new RuntimeException(ex);
			}

		}

		if (actorIds.size() == 0) {

			if (StringUtils.isNotBlank(leaveNodeIfActorNotAvailable)) {
				if (StringUtils.equals("true", leaveNodeIfActorNotAvailable)) {

					contextInstance.setVariable(Constant.IS_AGREE, "true");

					if (StringUtils.isNotBlank(transitionName)) {
						ctx.leaveNode(transitionName);
					} else {
						ctx.leaveNode();
					}
					if (ctx.getNode() != null) {
						logger.debug(ctx.getNode().getName()
								+ "->���ܻ�ȡ��������ߣ��뿪��ǰ�ڵ㡣");
					} else {
						if (ctx.getToken() != null) {
							logger.debug(ctx.getToken().getName()
									+ "->���ܻ�ȡ��������ߣ��뿪��ǰ�ڵ㡣");
						}
					}
					return;
				}
			}

			if (ObjectFactory.isDefaultActorEnable()) {
				String defaultActors = ObjectFactory.getDefaultActors();
				if (StringUtils.isNotBlank(defaultActors)) {
					StringTokenizer st2 = new StringTokenizer(defaultActors,
							",");
					while (st2.hasMoreTokens()) {
						String elem = st2.nextToken();
						if (StringUtils.isNotBlank(elem)) {
							actorIds.add(elem);
						}
					}
				}
			}
		}

		if (actorIds.size() > 0) {

			if (StringUtils.isNotBlank(taskName)) {
				Node node = ctx.getNode();
				if (node instanceof TaskNode) {
					TaskNode taskNode = (TaskNode) node;
					task = taskNode.getTask(taskName);
				}
			}

			if (task == null) {
				task = ctx.getTask();
			}

			Token token = ctx.getToken();
			TaskMgmtInstance tmi = ctx.getTaskMgmtInstance();
			TaskInstance taskInstance = tmi.createTaskInstance(task, token);
			taskInstance.setCreate(new Date());
			if (StringUtils.equals(signalling, "false")) {
				taskInstance.setSignalling(false);
			} else {
				taskInstance.setSignalling(true);
			}

			if (actorIds.size() == 1) {
				String actorId = (String) actorIds.iterator().next();
				taskInstance.setActorId(actorId);
			} else {
				int i = 0;
				String[] pooledIds = new String[actorIds.size()];
				Iterator iterator2 = actorIds.iterator();
				while (iterator2.hasNext()) {
					pooledIds[i++] = (String) iterator2.next();
				}
				taskInstance.setPooledActors(pooledIds);
			}
		}

	}

}
