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


package org.jpage.jbpm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jbpm.JbpmContext;
import org.jpage.actor.Actor;
import org.jpage.jbpm.ibatis.MutableSQLMapContainer;
import org.jpage.jbpm.model.AgentInstance;
import org.jpage.jbpm.model.DeployInstance;
import org.jpage.jbpm.model.LongIdInstance;
import org.jpage.jbpm.model.StateInstance;
import org.jpage.jbpm.util.MappingConstant;
import org.jpage.persistence.Executor;
import org.jpage.persistence.SQLParameter;
import org.jpage.util.JdbcUtil;
import org.jpage.util.UUID32;

public class ProcessDAOImpl implements ProcessDAO {

	/**
	 * 删除消息实例
	 * 
	 * @param processInstanceId
	 */
	public void deleteMessageInstances(JbpmContext jbpmContext,
			String processInstanceId) {
		String sql = " delete from JPAGE_MESSAGE_INSTANCE where PROCESSINSTANCEID_ = ? ";
		PreparedStatement psmt = null;
		try {
			Connection con = jbpmContext.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, processInstanceId);
			psmt.executeUpdate();
			psmt.close();
			psmt = null;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				if (psmt != null) {
					psmt.close();
				}
			} catch (SQLException ex) {
			}
		}
	}

	/**
	 * 删除流程实例，同时删除该流程实例的状态实例和数据实例
	 * 
	 * @param processInstanceId
	 */
	public void deleteProcessInstance(JbpmContext jbpmContext,
			String processInstanceId) {
		String message_sql = " delete from JPAGE_MESSAGE_INSTANCE where PROCESSINSTANCEID_ = ? ";
		String file_sql = " delete from JPAGE_JBPM_FILE_INSTANCE where PROCESSINSTANCEID_ = ? ";
		String process_sql = " delete from JPAGE_JBPM_BUSINESS_INSTANCE where processInstanceId = ? ";
		String state_sql = " delete from JPAGE_JBPM_DATA_INSTANCE where processInstanceId = ? ";
		String data_sql = " delete from JPAGE_JBPM_STATE_INSTANCE where processInstanceId = ? ";

		PreparedStatement psmt01 = null;
		PreparedStatement psmt02 = null;
		PreparedStatement psmt03 = null;
		PreparedStatement psmt04 = null;
		PreparedStatement psmt05 = null;
		try {
			Connection con = jbpmContext.getConnection();
			psmt01 = con.prepareStatement(message_sql);
			psmt01.setString(1, processInstanceId);
			psmt01.executeUpdate();
			psmt01.close();
			psmt01 = null;

			psmt02 = con.prepareStatement(file_sql);
			psmt02.setString(1, processInstanceId);
			psmt02.executeUpdate();
			psmt02.close();
			psmt02 = null;

			psmt03 = con.prepareStatement(data_sql);
			psmt03.setString(1, processInstanceId);
			psmt03.executeUpdate();
			psmt03.close();
			psmt03 = null;

			psmt04 = con.prepareStatement(state_sql);
			psmt04.setString(1, processInstanceId);
			psmt04.executeUpdate();
			psmt04.close();
			psmt04 = null;

			psmt05 = con.prepareStatement(process_sql);
			psmt05.setString(1, processInstanceId);
			psmt05.executeUpdate();
			psmt05.close();
			psmt05 = null;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				if (psmt01 != null) {
					psmt01.close();
				}
				if (psmt02 != null) {
					psmt02.close();
				}
				if (psmt03 != null) {
					psmt03.close();
				}
				if (psmt04 != null) {
					psmt04.close();
				}
				if (psmt05 != null) {
					psmt05.close();
				}
			} catch (SQLException ex) {
			}
		}
	}

	/**
	 * 获取任务池中的任务实例
	 * 
	 * @param actorId
	 * @param maxResults
	 * @return
	 */
	public List findPooledTaskInstances(JbpmContext jbpmContext,
			String actorId, int maxResults) {
		Session session = jbpmContext.getSession();
		Query query = session
				.getNamedQuery("TaskMgmtSession.findPooledTaskInstancesByActorId");
		query.setString("swimlaneActorId", actorId);
		query.setFirstResult(0);
		query.setMaxResults(maxResults);
		List rows = query.list();
		return rows;
	}

	/**
	 * 获取任务实例
	 * 
	 * @param actorId
	 * @param maxResults
	 * @return
	 */
	public List findTaskInstances(JbpmContext jbpmContext, String actorId,
			int maxResults) {
		Session session = jbpmContext.getSession();
		Query query = session
				.getNamedQuery("TaskMgmtSession.findTaskInstancesByActorId");
		query.setString("actorId", actorId);
		query.setFirstResult(0);
		query.setMaxResults(maxResults);
		List rows = query.list();
		return rows;
	}

	/**
	 * 根据条件获取参与者
	 * 
	 * @param queryExecutor
	 * @return
	 */
	public Set getActorIds(JbpmContext jbpmContext, Executor queryExecutor) {
		Set actorIds = new HashSet();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Connection con = jbpmContext.getConnection();
			stmt = con.prepareStatement(queryExecutor.getQuery());
			JdbcUtil.fillStatement(stmt, queryExecutor.getValues());
			rs = stmt.executeQuery();
			while (rs.next()) {
				String actorId = rs.getString(1);
				actorIds.add(actorId);
			}
			rs.close();
			stmt.close();
			return actorIds;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException ex) {
			}
		}
	}

	/**
	 * 获取某个流程某些角色的用户ID
	 * 
	 * @param roleIds
	 * @return
	 */
	public List getActors(JbpmContext jbpmContext, Collection roleIds) {
		Session session = jbpmContext.getSession();
		Query query = session
				.createQuery(" from org.jpage.actor.Actor as a where a.roleId in (:roleIds) ");
		query.setParameterList("roleIds", roleIds);
		return query.list();
	}

	 
	/**
	 * 获取某个流程某个任务的用户
	 * 
	 * @param String
	 *            roleId
	 * @return
	 */
	public List getActors(JbpmContext jbpmContext, String roleId) {
		Session session = jbpmContext.getSession();
		Query query = session
				.createQuery(" from org.jpage.actor.Actor as a where a.roleId = :roleId ");
		query.setString("roleId", roleId);
		return query.list();
	}

 

	public java.util.Date getAfterDate(Date startDate, int day) {
		long onetimes = (long) 1 * 24 * 60 * 60 * 1000; // 一天的毫秒数
		long now = startDate.getTime();
		long after = now + onetimes * day;
		java.util.Date date = new java.util.Date(after);
		return date;
	}

	 

	/**
	 * 获取业务流程实例
	 * 
	 * @param processInstanceIds
	 * @return
	 */
	public List getBusinessInstances(JbpmContext jbpmContext,
			Collection processInstanceIds) {
		if (processInstanceIds == null || processInstanceIds.size() == 0) {
			return null;
		}
		Session session = jbpmContext.getSession();
		Query query = session
				.createQuery(" from org.jpage.jbpm.model.BusinessInstance as a where a.processInstanceId in (:processInstanceIds) ");
		query.setParameterList("processInstanceIds", processInstanceIds);
		List rows = query.list();
		return rows;
	}

	/**
	 * 获取最新部署的流程定义实例
	 * 
	 * @param processName
	 * @return
	 */
	public DeployInstance getMaxDeployInstance(JbpmContext jbpmContext,
			String processName) {
		Session session = jbpmContext.getSession();
		Query query = session
				.createQuery(" from org.jpage.jbpm.model.DeployInstance as a where a.processName = :processName order by a.lastModifiedTimeMillis desc ");
		query.setString("processName", processName);
		List rows = query.list();
		if (rows != null && rows.size() > 0) {
			return (DeployInstance) rows.get(0);
		}
		return null;
	}
 
	 
	 
	/**
	 * 获取某个流程最新状态的实例数据
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public StateInstance getMaxStateInstance(JbpmContext jbpmContext,
			String processInstanceId) {
		Session session = jbpmContext.getSession();
		Query query = session
				.createQuery(" from org.jpage.jbpm.model.StateInstance as a where a.processInstanceId = :processInstanceId order by a.versionNo desc ");
		query.setString("processInstanceId", processInstanceId);
		List rows = query.list();
		if (rows != null && rows.size() > 0) {
			return (StateInstance) rows.get(0);
		}
		return null;
	}

	/**
	 * 获取下一个ID
	 * 
	 * @param objectId
	 * @param increment
	 * @return
	 */
	public Long getNextId(JbpmContext jbpmContext, String objectId,
			int increment) {
		Session session = jbpmContext.getSession();
		Long nextId = new Long(1);
		Query query = session
				.createQuery(" from org.jpage.jbpm.model.LongIdInstance as a where a.objectId = :objectId ");
		query.setString("objectId", objectId);
		List list = query.list();
		if (list != null && list.size() > 0) {
			LongIdInstance model = (LongIdInstance) list.get(0);
			model.setNextId(new Long(model.getNextId().longValue() + increment));
			nextId = model.getNextId();
			session.update(model);
		} else {
			LongIdInstance model = new LongIdInstance();
			model.setNextId(new Long(1));
			model.setObjectId(objectId);
			session.save(model);
		}
		return nextId;
	}

	 

	/**
	 * 获取用户Map。其中key是角色ID，value是org.jpage.actor.Actor对象。
	 * 
	 * @return
	 */
	public Map getRoleMap(JbpmContext jbpmContext, Executor queryExecutor) {
		List rows = new ArrayList();
		Map roleMap = new LinkedHashMap();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Connection con = jbpmContext.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(queryExecutor.getQuery());
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			Map columnMap = new LinkedHashMap();
			for (int i = 1; i <= count; i++) {
				String columnName = rsmd.getColumnName(i);
				columnMap.put(columnName, columnName);
			}
			while (rs.next()) {
				int index = 0;
				Map dataMap = new HashMap();
				Iterator iterator = columnMap.keySet().iterator();
				while (iterator.hasNext()) {
					index++;
					String name = (String) iterator.next();
					String value = rs.getString(index);
					dataMap.put(name.toLowerCase(), value);
				}
				rows.add(dataMap);
			}
			rs.close();
			stmt.close();

			if (rows.size() > 0) {
				Map mapping = new HashMap();
				if (queryExecutor.getFields() != null) {
					Iterator iterator = queryExecutor.getFields().iterator();
					while (iterator.hasNext()) {
						SQLParameter param = (SQLParameter) iterator.next();
						mapping.put(param.getMapping(), param.getName()
								.toLowerCase());
					}
				}
				Iterator iterator = rows.iterator();
				while (iterator.hasNext()) {
					Map dataMap = (Map) iterator.next();
					Actor actor = new Actor();

					if (mapping.get(MappingConstant.ACTOR_ID) != null) {
						String name = (String) mapping
								.get(MappingConstant.ACTOR_ID);
						if (dataMap.get(name) != null) {
							actor.setActorId((String) dataMap.get(name));
						}
					}
					if (mapping.get(MappingConstant.ACTOR_NAME) != null) {
						String name = (String) mapping
								.get(MappingConstant.ACTOR_NAME);
						if (dataMap.get(name) != null) {
							actor.setName((String) dataMap.get(name));
						}
					}

					if (StringUtils.isBlank(actor.getName())) {
						actor.setName(actor.getActorId());
					}
					roleMap.put(actor.getActorId(), actor);
					roleMap.put(actor.getActorId().toLowerCase(), actor);
				}
			}
			return roleMap;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException ex) {
			}
		}
	}

	/**
	 * 获取状态实例
	 * 
	 * @param stateInstanceId
	 * @return
	 */
	public StateInstance getStateInstance(JbpmContext jbpmContext,
			String stateInstanceId) {
		Session session = jbpmContext.getSession();
		Query query = session
				.createQuery(" from org.jpage.jbpm.model.StateInstance as a where a.stateInstanceId = :stateInstanceId ");
		query.setString("stateInstanceId", stateInstanceId);
		List rows = query.list();
		if (rows != null && rows.size() > 0) {
			return (StateInstance) rows.get(0);
		}
		return null;
	}

	/**
	 * 获取状态实例
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getStateInstances(JbpmContext jbpmContext,
			String processInstanceId) {
		Session session = jbpmContext.getSession();
		Query query = session
				.createQuery(" from org.jpage.jbpm.model.StateInstance as a where a.processInstanceId = :processInstanceId order by a.versionNo asc ");
		query.setString("processInstanceId", processInstanceId);
		List rows = query.list();
		return rows;
	}

	/**
	 * 获取状态实例
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getStateInstances(JbpmContext jbpmContext, String processName,
			String actorId) {
		Session session = jbpmContext.getSession();
		Query query = session
				.createQuery(" from org.jpage.jbpm.model.StateInstance as a where a.processName = :processName and a.actorId = :actorId order by a.versionNo asc ");
		query.setString("processName", processName);
		query.setString("actorId", actorId);
		List rows = query.list();
		return rows;
	}

	/**
	 * 获取用户Map。其中key是用户帐号，value是org.jpage.actor.User对象。
	 * 
	 * @return
	 */
	public Map getUserMap(JbpmContext jbpmContext, Executor queryExecutor) {
		Map userMap = new LinkedHashMap();
		try {
			List actors = MutableSQLMapContainer.getContainer().getList(
					jbpmContext, queryExecutor.getQuery(),
					queryExecutor.getParams());
			if (actors != null && actors.size() > 0) {
				Iterator iter = actors.iterator();
				while (iter.hasNext()) {
					org.jpage.actor.User user = (org.jpage.actor.User) iter
							.next();
					userMap.put(user.getActorId(), user);
					userMap.put(user.getActorId().toUpperCase(), user);
				}
			}
			return userMap;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 保存某个流程任务的参与者
	 * 
	 * @param taskName
	 * @param processName
	 * @param roleUsers
	 */
	public void saveActors(JbpmContext jbpmContext, String roleId, List actors) {
		Session session = jbpmContext.getSession();
		Query query = session
				.createQuery(" delete from org.jpage.actor.Actor where roleId = :roleId ");
		query.setString("roleId", roleId);
		query.executeUpdate();

		if (actors != null) {
			Iterator iterator = actors.iterator();
			while (iterator.hasNext()) {
				Actor actor = (Actor) iterator.next();
				actor.setRoleId(roleId);
				session.save(actor);
			}
		}
	}

	/**
	 * 保存代理人
	 * 
	 * @param jbpmContext
	 * @param actorId
	 * @param agents
	 */
	public void saveAgents(JbpmContext jbpmContext, String actorId,
			List agents, String objectValue) {
		String sql = " delete from org.jpage.jbpm.model.AgentInstance where actorId = :actorId ";
		if (StringUtils.isNotBlank(objectValue)) {
			sql = sql + " and objectValue = :objectValue ";
		} else {
			objectValue = "jbpm";
			sql = sql + " and objectValue = :objectValue ";
		}
		Session session = jbpmContext.getSession();
		Query query = session.createQuery(sql);
		query.setString("actorId", actorId);
		query.setString("objectValue", objectValue);
		query.executeUpdate();
		if (agents != null) {
			Iterator iterator = agents.iterator();
			while (iterator.hasNext()) {
				AgentInstance agent = (AgentInstance) iterator.next();
				if (!StringUtils.equals(actorId, agent.getAgentId())) {
					agent.setActorId(actorId);
					if (StringUtils.isBlank(agent.getId())) {
						agent.setId(UUID32.getUUID());
					}
					if (agent.getStartDate() == null) {
						agent.setStartDate(new Date(System.currentTimeMillis()));
					}
					if (agent.getEndDate() == null) {
						Date startDate = agent.getStartDate();
						Date endDate = this.getAfterDate(startDate, 30);
						agent.setEndDate(endDate);
					}
					session.save(agent);
				}
			}
		}
	}

}
