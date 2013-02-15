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

package org.jpage.jbpm.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jpage.actor.Actor;
import org.jpage.actor.User;
import org.jpage.core.cache.CacheFactory;
import org.jpage.jbpm.dao.ProcessDAO;
import org.jpage.jbpm.model.AgentInstance;
import org.jpage.jbpm.persistence.PersistenceDAO;
import org.jpage.jbpm.util.Constant;
import org.jpage.persistence.Executor;
import org.jpage.persistence.SQLParameter;
import org.jpage.util.DateTools;
import org.jpage.util.FieldType;
import org.jpage.util.SQLFormatter;
import org.jpage.util.Tools;

public class ActorManagerImpl implements ActorManager {
	private final static Log logger = LogFactory.getLog(ActorManagerImpl.class);

	 
	protected final static String USER_CACHE_KEY = "cache_user_";

	protected ProcessDAO processDAO;

	protected PersistenceDAO persistenceDAO;

	public ActorManagerImpl() {

	}

	public ProcessDAO getProcessDAO() {
		return processDAO;
	}

	public void setProcessDAO(ProcessDAO processDAO) {
		this.processDAO = processDAO;
	}

	public PersistenceDAO getPersistenceDAO() {
		return persistenceDAO;
	}

	public void setPersistenceDAO(PersistenceDAO persistenceDAO) {
		this.persistenceDAO = persistenceDAO;
	}

	/**
	 * 获取用户 此处采用推-拉技术（Push-Pop）
	 * 
	 * @param actorId
	 * @return org.jpage.actor.User
	 */
	public User getUser(JbpmContext jbpmContext, String actorId) {
		actorId = actorId.trim().toLowerCase();
		String cacheKey = USER_CACHE_KEY + actorId;
		if (CacheFactory.get(cacheKey) != null) {
			logger.debug("从缓存中获取用户");
			return (User) CacheFactory.get(cacheKey);
		}
		User user = null;
		Map userMap = this.getUserMap(jbpmContext);
		Iterator iterator = userMap.keySet().iterator();
		while (iterator.hasNext()) {
			String userId = (String) iterator.next();
			userId = userId.trim().toLowerCase();
			if (userId.equalsIgnoreCase(actorId)) {
				user = (User) userMap.get(actorId);
			}
			String tKey = USER_CACHE_KEY + userId;
			CacheFactory.put(tKey, userMap.get(userId));
		}
		return user;
	}

	/**
	 * 根据条件获取参与者
	 * 
	 * @param queryExecutor
	 * @return
	 */
	public Set getActorIds(JbpmContext jbpmContext, Executor queryExecutor) {
		int typesHashCode = queryExecutor.getTypesHashCode();
		int valuesHashCode = queryExecutor.getValuesHashCode();
		if (valuesHashCode == 0) {
			if (queryExecutor.getValues() != null) {
				valuesHashCode = queryExecutor.getValues().length;
			}
		}
		String cacheKey = String.valueOf(queryExecutor.getQuery().hashCode())
				+ "-" + String.valueOf(typesHashCode) + "-"
				+ String.valueOf(valuesHashCode);
		if (CacheFactory.get(cacheKey) != null) {
			logger.debug("从缓存中获取参与者用户");
			return (Set) CacheFactory.get(cacheKey);
		}
		Set actorIds = processDAO.getActorIds(jbpmContext, queryExecutor);
		if (actorIds != null && actorIds.size() > 0) {
			CacheFactory.put(cacheKey, actorIds);
		}
		return actorIds;
	}

	/**
	 * 获取用户Map。其中key是用户帐号，value是org.jpage.actor.User对象。 此处采用推-拉技术（Push-Pop）
	 * 
	 * @return
	 */
	public Map getUserMap(JbpmContext jbpmContext) {
		String cacheKey = "cache_users";
		if (CacheFactory.get(cacheKey) != null) {
			logger.debug("从缓存中获取用户");
			return (Map) CacheFactory.get(cacheKey);
		}
		Executor executor = new Executor();
		executor.setQuery(Constant.JBPM_USERS);
		Map userMap = processDAO.getUserMap(jbpmContext, executor);
		CacheFactory.put(cacheKey, userMap);
		return userMap;
	}

	 

	/**
	 * 保存某个流程任务的参与者
	 * 
	 * @param roleId
	 * @param roleUsers
	 */
	public void saveActors(JbpmContext jbpmContext, String roleId, List actors) {
		 
		processDAO.saveActors(jbpmContext, roleId, actors);
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
		 
		processDAO.saveAgents(jbpmContext, actorId, agents, objectValue);
	}

	/**
	 * 获取某个流程某个任务的用户
	 * 
	 * @param roleId
	 * @return
	 */
	public List getActors(JbpmContext jbpmContext, String roleId) {
		return processDAO.getActors(jbpmContext, roleId);
	}

	/**
	 * 获取某个流程某些角色的用户ID
	 * 
	 * @param roleIds
	 * @return
	 */
	public List getActors(JbpmContext jbpmContext, Collection roleIds) {
		if (roleIds == null || roleIds.size() == 0) {
			return null;
		}
		return processDAO.getActors(jbpmContext, roleIds);
	}

	/**
	 * 获取某个流程某个任务的参与者
	 * 
	 * @param roleId
	 * @return
	 */
	public List getActorIds(JbpmContext jbpmContext, String roleId) {
		List actorIds = new ArrayList();
		List actors = this.getActors(jbpmContext, roleId);
		if (actors != null && actors.size() > 0) {
			Iterator iterator = actors.iterator();
			while (iterator.hasNext()) {
				Actor actor = (Actor) iterator.next();
				actorIds.add(actor.getActorId());
			}
		}
		return actorIds;
	}

	/**
	 * 获取某个用户的代理人列表
	 * 
	 * @param jbpmContext
	 * @param actorId
	 * @return
	 */
	public List getAgentIds(JbpmContext jbpmContext, String actorId) {
		String cacheKey = "jbpm_agent_" + actorId;
		if (CacheFactory.get(cacheKey) != null) {
			logger.debug("从缓存中获取参与者用户");
			return (List) CacheFactory.get(cacheKey);
		}
		List agentIds = new ArrayList();
		Map params = new HashMap();
		params.put("actorId", actorId);
		params.put("objectId", "agent");
		List actors = this.getAgents(jbpmContext, params);
		if (actors != null && actors.size() > 0) {
			Iterator iterator = actors.iterator();
			while (iterator.hasNext()) {
				AgentInstance agent = (AgentInstance) iterator.next();
				agentIds.add(agent.getAgentId());
			}
		}
		CacheFactory.put(cacheKey, agentIds);
		return agentIds;
	}

	/**
	 * 获取某个用户的某个流程的代理人列表
	 * 
	 * @param jbpmContext
	 * @param actorId
	 * @param processName
	 * @return
	 */
	public List getAgentIds(JbpmContext jbpmContext, String actorId,
			String processName) {
		String cacheKey = "jbpm_agent_" + actorId + "_" + processName;
		if (CacheFactory.get(cacheKey) != null) {
			logger.debug("从缓存中获取参与者用户");
			return (List) CacheFactory.get(cacheKey);
		}
		List agentIds = new ArrayList();
		Map params = new HashMap();
		params.put("actorId", actorId);
		params.put("objectId", "agent");
		params.put("objectValue", processName);
		List actors = this.getAgents(jbpmContext, params);
		if (actors != null && actors.size() > 0) {
			Iterator iterator = actors.iterator();
			while (iterator.hasNext()) {
				AgentInstance agent = (AgentInstance) iterator.next();
				agentIds.add(agent.getAgentId());
			}
		}
		CacheFactory.put(cacheKey, agentIds);
		return agentIds;
	}

	/**
	 * 根据查询条件获取参与者
	 * 
	 * @param jbpmContext
	 * @param paramMap
	 * @return
	 */
	public List getActors(JbpmContext jbpmContext, Map paramMap) {
		Map params = new HashMap();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" from org.jpage.actor.Actor as a where 1=1 ");

		if (paramMap.get("name") != null) {
			params.put("name", paramMap.get("name"));
			buffer.append(" and  a.name = :name ");
		}

		if (paramMap.get("roleId") != null) {
			params.put("roleId", paramMap.get("roleId"));
			buffer.append(" and  a.roleId = :roleId ");
		}

		if (paramMap.get("actorId") != null) {
			params.put("actorId", paramMap.get("actorId"));
			buffer.append(" and  a.actorId = :actorId ");
		}

		if (paramMap.get("objectId") != null) {
			params.put("objectId", paramMap.get("objectId"));
			buffer.append(" and  a.objectId = :objectId ");
		}

		if (paramMap.get("objectValue") != null) {
			params.put("objectValue", paramMap.get("objectValue"));
			buffer.append(" and  a.objectValue = :objectValue ");
		}

		if (isNotEmpty(paramMap, "actorIds")) {
			Object obj = paramMap.get("actorIds");
			if (obj instanceof java.util.Collection) {
				Collection rows = (Collection) obj;
				List values = new ArrayList();
				Iterator iterator = rows.iterator();
				while (iterator.hasNext()) {
					Object value = iterator.next();
					if (value != null) {
						if (!(value instanceof Collection)) {
							values.add(value);
						}
					}
				}
				buffer.append(" and a.actorId in (:actorIds) ");
				params.put("actorIds", values);
			}
		}

		if (isNotEmpty(paramMap, "roleIds")) {
			Object obj = paramMap.get("roleIds");
			if (obj instanceof java.util.Collection) {
				Collection rows = (Collection) obj;
				List values = new ArrayList();
				Iterator iterator = rows.iterator();
				while (iterator.hasNext()) {
					Object value = iterator.next();
					if (value != null) {
						if (!(value instanceof Collection)) {
							values.add(value);
						}
					}
				}
				buffer.append(" and a.roleId in (:roleIds) ");
				params.put("roleIds", values);
			}
		}

		if (isNotEmpty(paramMap, "objectIds")) {
			Object obj = paramMap.get("objectIds");
			if (obj instanceof java.util.Collection) {
				Collection rows = (Collection) obj;
				List values = new ArrayList();
				Iterator iterator = rows.iterator();
				while (iterator.hasNext()) {
					Object value = iterator.next();
					if (value != null) {
						if (!(value instanceof Collection)) {
							values.add(value);
						}
					}
				}
				buffer.append(" and a.objectId in (:objectIds) ");
				params.put("objectIds", values);
			}
		}

		if (isNotEmpty(paramMap, "objectValues")) {
			Object obj = paramMap.get("objectValues");
			if (obj instanceof java.util.Collection) {
				Collection rows = (Collection) obj;
				List values = new ArrayList();
				Iterator iterator = rows.iterator();
				while (iterator.hasNext()) {
					Object value = iterator.next();
					if (value != null) {
						if (!(value instanceof Collection)) {
							values.add(value);
						}
					}
				}
				buffer.append(" and a.objectValue in (:objectValues) ");
				params.put("objectValues", values);
			}
		}

		Executor executor = new Executor();
		executor.setParams(params);
		executor.setQuery(buffer.toString());

		return persistenceDAO.query(jbpmContext, executor);
	}

	/**
	 * 根据查询条件获取代理人
	 * 
	 * @param jbpmContext
	 * @param paramMap
	 * @return
	 */
	public List getAgentIds(JbpmContext jbpmContext, Map paramMap) {
		List agentIds = new ArrayList();
		List agents = this.getAgents(jbpmContext, paramMap);
		if (agents != null && agents.size() > 0) {
			Iterator iterator = agents.iterator();
			while (iterator.hasNext()) {
				AgentInstance agent = (AgentInstance) iterator.next();
				agentIds.add(agent.getAgentId());
			}
		}
		return agentIds;
	}

	/**
	 * 根据查询条件获取代理人
	 * 
	 * @param jbpmContext
	 * @param paramMap
	 * @return
	 */
	public List getAgents(JbpmContext jbpmContext, Map paramMap) {
		Map params = new HashMap();
		StringBuffer buffer = new StringBuffer();
		buffer
				.append(" from org.jpage.jbpm.model.AgentInstance as a where 1=1 ");

		if (paramMap.get("actorId") != null) {
			params.put("actorId", paramMap.get("actorId"));
			buffer.append(" and  a.actorId = :actorId ");
		}

		if (paramMap.get("agentId") != null) {
			params.put("agentId", paramMap.get("agentId"));
			buffer.append(" and  a.agentId = :agentId ");
		}

		if (paramMap.get("objectId") != null) {
			params.put("objectId", paramMap.get("objectId"));
			buffer.append(" and  a.objectId = :objectId ");
		}

		if (paramMap.get("objectValue") != null) {
			params.put("objectValue", paramMap.get("objectValue"));
			buffer.append(" and  a.objectValue = :objectValue ");
		}

		if (paramMap.get("attribute01") != null) {
			params.put("attribute01", paramMap.get("attribute01"));
			buffer.append(" and  a.attribute01 = :attribute01 ");
		}

		if (paramMap.get("attribute02") != null) {
			params.put("attribute02", paramMap.get("attribute02"));
			buffer.append(" and  a.attribute02 = :attribute02 ");
		}

		if (isNotEmpty(paramMap, "actorIds")) {
			Object obj = paramMap.get("actorIds");
			if (obj instanceof java.util.Collection) {
				Collection rows = (Collection) obj;
				List values = new ArrayList();
				Iterator iterator = rows.iterator();
				while (iterator.hasNext()) {
					Object value = iterator.next();
					if (value != null) {
						if (!(value instanceof Collection)) {
							values.add(value);
						}
					}
				}
				buffer.append(" and a.actorId in (:actorIds) ");
				params.put("actorIds", values);
			}
		}

		if (isNotEmpty(paramMap, "agentIds")) {
			Object obj = paramMap.get("agentIds");
			if (obj instanceof java.util.Collection) {
				Collection rows = (Collection) obj;
				List values = new ArrayList();
				Iterator iterator = rows.iterator();
				while (iterator.hasNext()) {
					Object value = iterator.next();
					if (value != null) {
						if (!(value instanceof Collection)) {
							values.add(value);
						}
					}
				}
				buffer.append(" and a.agentId in (:agentIds) ");
				params.put("agentIds", values);
			}
		}

		if (isNotEmpty(paramMap, "objectIds")) {
			Object obj = paramMap.get("objectIds");
			if (obj instanceof java.util.Collection) {
				Collection rows = (Collection) obj;
				List values = new ArrayList();
				Iterator iterator = rows.iterator();
				while (iterator.hasNext()) {
					Object value = iterator.next();
					if (value != null) {
						if (!(value instanceof Collection)) {
							values.add(value);
						}
					}
				}
				buffer.append(" and a.objectId in (:objectIds) ");
				params.put("objectIds", values);
			}
		}

		if (isNotEmpty(paramMap, "objectValues")) {
			Object obj = paramMap.get("objectValues");
			if (obj instanceof java.util.Collection) {
				Collection rows = (Collection) obj;
				List values = new ArrayList();
				Iterator iterator = rows.iterator();
				while (iterator.hasNext()) {
					Object value = iterator.next();
					if (value != null) {
						if (!(value instanceof Collection)) {
							values.add(value);
						}
					}
				}
				buffer.append(" and a.objectValue in (:objectValues) ");
				params.put("objectValues", values);
			}
		}

		Executor executor = new Executor();
		executor.setParams(params);
		executor.setQuery(buffer.toString());

		return persistenceDAO.query(jbpmContext, executor);
	}

	/**
	 * 获取某个流程某个任务的参与者
	 * 
	 * @param jbpmContext
	 * @param queryId
	 * @param params
	 * @return
	 */
	public Collection getActorIds(JbpmContext jbpmContext, String queryId,
			Map params) {
		Executor executor = new Executor();
		List fields = executor.getFields();
		Executor queryExecutor = this.getExecutor(executor.getQuery(), fields,
				params);
		Collection actorIds = processDAO
				.getActorIds(jbpmContext, queryExecutor);
		return actorIds;
	}

	public Executor getExecutor(String sql, List params, Map valueMap) {
		Executor executor = new Executor();
		List values = new ArrayList();
		if (params != null && valueMap != null) {
			Iterator iterator = params.iterator();
			while (iterator.hasNext()) {
				SQLParameter param = (SQLParameter) iterator.next();
				String name = param.getName();
				int type = param.getType();
				Object obj = valueMap.get(name);
				if (obj == null) {
					obj = valueMap.get(name.toLowerCase());
				}
				if (obj == null) {
					String defaultValue = param.getDefaultValue();
					if (StringUtils.isNotBlank(defaultValue)) {
						if (defaultValue.startsWith("${")
								&& defaultValue.endsWith("}")) {
							String paramName = Tools.replaceIgnoreCase(defaultValue, "${", "");
							paramName = paramName.replaceAll("}", "");
							if (valueMap.get(paramName) != null) {
								obj = valueMap.get(paramName);
							}
						}
					}
				}

				if (obj == null) {
					/**
					 * 如果该参数是必须的，而参数值为空，则不能继续往下处理，抛出异常。
					 */
					if (param.getRequired() > 0) {
						throw new RuntimeException("parameter \"" + name
								+ "\" is not set");
					}
					values.add(null);
					continue;
				}

				String value = null;
				java.util.Date date = null;

				if (obj instanceof String) {
					value = (String) obj;
				}

				switch (type) {
				case FieldType.DATE_TYPE:
					if (!(obj instanceof java.util.Date)) {
						if (StringUtils.isNotBlank(value)) {
							date = DateTools.toDate(value);
							obj = date;
						}
					}
					break;
				case FieldType.TIMESTAMP_TYPE:
					if (!(obj instanceof java.sql.Timestamp)) {
						if (obj instanceof java.util.Date) {
							date = (java.util.Date) obj;
						} else {
							if (StringUtils.isNotBlank(value)) {
								date = DateTools.toDate(value);
							}
						}
						obj = DateTools.toTimestamp(date);
					}
					break;
				case FieldType.DOUBLE_TYPE:
					if (!(obj instanceof Double)) {
						if (StringUtils.isNotBlank(value)) {
							Double d = new Double(value);
							obj = d;
						}
					}
					break;
				case FieldType.BOOLEAN_TYPE:
					if (!(obj instanceof Boolean)) {
						if (StringUtils.isNotBlank(value)) {
							Boolean b = new Boolean(value);
							obj = b;
						}
					}
					break;
				case FieldType.SHORT_TYPE:
					if (!(obj instanceof Short)) {
						if (StringUtils.isNotBlank(value)) {
							Short s = new Short(value);
							obj = s;
						}
					}
					break;
				case FieldType.INTEGER_TYPE:
					if (!(obj instanceof Integer)) {
						if (StringUtils.isNotBlank(value)) {
							Integer integer = new Integer(value);
							obj = integer;
						}
					}
					break;
				case FieldType.LONG_TYPE:
					if (!(obj instanceof Long)) {
						if (StringUtils.isNotBlank(value)) {
							Long l = new Long(value);
							obj = l;
						}
					}
					break;
				default:
					break;
				}
				values.add(obj);
			}
			executor.setValues(values.toArray());
		}

		executor.setQuery(sql);

		SQLFormatter formatter = new SQLFormatter();
		sql = formatter.format(sql);

		logger.debug("[resolve value] = " + values);
		logger.debug("[resolve query] = " + sql);
		return executor;
	}

	private boolean isNotEmpty(Map paramMap, String name) {
		if (paramMap.get(name) != null) {
			Object obj = paramMap.get(name);
			if (obj instanceof Collection) {
				Collection rows = (Collection) obj;
				if (rows != null && rows.size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

}
