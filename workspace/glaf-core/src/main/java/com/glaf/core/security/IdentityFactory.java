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

package com.glaf.core.security;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.TreeModel;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.identity.Agent;
import com.glaf.core.identity.Role;
import com.glaf.core.identity.User;
import com.glaf.core.identity.util.UserJsonFactory;
import com.glaf.core.query.MembershipQuery;
import com.glaf.core.service.EntityService;
import com.glaf.core.util.Constants;

public class IdentityFactory {
	protected static volatile EntityService entityService;

	protected final static Log logger = LogFactory
			.getLog(IdentityFactory.class);

	/**
	 * 验证用户名密码是否正确
	 * 
	 * @param actorId
	 * @param password
	 * @return
	 */
	public static boolean checkPassword(String actorId, String password) {
		boolean isAuthenticated = false;
		User user = (User) getEntityService().getById("getUserById", actorId);
		if (user != null) {
			String pwd = DigestUtil.digestString(password, "MD5");
			if (StringUtils.equals(pwd, user.getPassword())) {
				return true;
			}
			if (StringUtils.equals(password, user.getPassword())) {
				return true;
			}
		}
		return isAuthenticated;
	}

	/**
	 * 获取委托人编号集合
	 * 
	 * @param assignTo
	 *            受托人编号
	 * @return
	 */
	public static List<String> getAgentIds(String assignTo) {
		List<String> agentIds = new java.util.ArrayList<String>();
		List<Object> list = getEntityService().getList("getAgents", assignTo);
		if (list != null && !list.isEmpty()) {
			for (Object obj : list) {
				if (obj instanceof Agent) {
					Agent agent = (Agent) obj;
					if (!agent.isValid()) {
						continue;
					}
					switch (agent.getAgentType()) {
					case 0:// 全局代理
						agentIds.add(agent.getAssignFrom());
						break;
					default:
						break;
					}
				}
			}
		}
		return agentIds;
	}

	/**
	 * 获取委托人对象集合
	 * 
	 * @param assignTo
	 *            受托人编号
	 * @return
	 */
	public static List<Agent> getAgents(String assignTo) {
		List<Agent> agents = new java.util.ArrayList<Agent>();
		List<Object> list = getEntityService().getList("getAgents", assignTo);
		if (list != null && !list.isEmpty()) {
			for (Object obj : list) {
				if (obj instanceof Agent) {
					Agent agent = (Agent) obj;
					if (!agent.isValid()) {
						continue;
					}
					switch (agent.getAgentType()) {
					case 0:// 全局代理
						agents.add(agent);
						break;
					default:
						break;
					}
				}
			}
		}
		return agents;
	}

	public static List<TreeModel> getChildrenTreeModels(Long id) {
		List<Object> list = getEntityService().getList("getChildrenTreeModels",
				id);
		List<TreeModel> treeModels = new java.util.ArrayList<TreeModel>();
		if (list != null && !list.isEmpty()) {
			Iterator<Object> iter = list.iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				if (obj instanceof TreeModel) {
					TreeModel treeModel = (TreeModel) obj;
					List<TreeModel> children = getChildrenTreeModels(treeModel
							.getId());
					treeModel.setChildren(children);
					treeModels.add(treeModel);
				}
			}
		}
		return treeModels;
	}

	/**
	 * 获取全部部门Map
	 * 
	 * @return
	 */
	public static Map<Long, TreeModel> getDepartmentMap() {
		Map<Long, TreeModel> deptMap = new LinkedHashMap<Long, TreeModel>();
		List<Object> list = getEntityService().getList("getDepartments", null);
		if (list != null && !list.isEmpty()) {
			for (Object obj : list) {
				if (obj instanceof TreeModel) {
					TreeModel dept = (TreeModel) obj;
					deptMap.put(Long.valueOf(dept.getId()), dept);
				}
			}
		}
		return deptMap;
	}

	public static EntityService getEntityService() {
		if (entityService == null) {
			entityService = ContextFactory.getBean("entityService");
		}
		return entityService;
	}

	/**
	 * 获取登录用户信息
	 * 
	 * @param actorId
	 * @return
	 */
	public static LoginContext getLoginContext(String actorId) {
		LoginHandler loginHandler = null;
		/**
		 * 获取Spring容器中定义的登录Handler，该Handler必须实现LoginHandler接口
		 */
		if (ContextFactory.hasBean("loginHandler")) {
			loginHandler = ContextFactory.getBean("loginHandler");
			if (loginHandler != null) {
				/**
				 * 获取自定义的登录上下文
				 */
				return loginHandler.getLoginContext(actorId);
			}
		}
		String cacheKey = Constants.LOGIN_USER_CACHE + actorId;
		if (SystemConfig.getBoolean("use_query_cache")
				&& CacheFactory.getString(cacheKey) != null) {
			String text = CacheFactory.getString(cacheKey);
			JSONObject jsonObject = JSON.parseObject(text);
			return LoginContextUtils.jsonToObject(jsonObject);
		}
		User user = (User) getEntityService().getById("getUserById", actorId);
		if (user != null) {
			LoginContext loginContext = new LoginContext(user);
			List<String> roles = new java.util.ArrayList<String>();

			/**
			 * 获取本人的角色权限
			 */
			List<String> list = getUserRoleCodes(actorId);
			if (list != null && !list.isEmpty()) {
				roles.addAll(list);
			}

			/**
			 * 获取代理人的角色权限
			 */
			List<String> agentIds = getAgentIds(actorId);
			if (agentIds != null && !agentIds.isEmpty()) {
				for (String agentId : agentIds) {
					list = getUserRoleCodes(agentId);
					if (list != null && !list.isEmpty()) {
						roles.addAll(list);
					}
				}
				loginContext.setAgents(agentIds);
			}

			logger.debug("user roles:" + roles);

			loginContext.setRoles(roles);
			if (SystemConfig.getBoolean("use_query_cache")) {
				CacheFactory.put(cacheKey, loginContext.toJsonObject()
						.toJSONString());
			}
			return loginContext;
		}
		return null;
	}

	/**
	 * 获取全部用户Map
	 * 
	 * @return
	 */
	public static Map<Long, User> getLongUserMap() {
		Map<Long, User> userMap = new LinkedHashMap<Long, User>();
		List<Object> list = getEntityService().getList("getUsers", null);
		if (list != null && !list.isEmpty()) {
			for (Object obj : list) {
				if (obj instanceof User) {
					User user = (User) obj;
					userMap.put(user.getId(), user);
				}
			}
		}
		return userMap;
	}

	/**
	 * 获取全部角色Map
	 * 
	 * @return
	 */
	public static Map<Long, Role> getRoleMap() {
		Map<Long, Role> roleMap = new LinkedHashMap<Long, Role>();
		List<Object> list = getEntityService().getList("getRoles", null);
		if (list != null && !list.isEmpty()) {
			for (Object obj : list) {
				if (obj instanceof Role) {
					Role role = (Role) obj;
					roleMap.put(Long.valueOf(role.getRoleId()), role);
				}
			}
		}
		return roleMap;
	}

	public static List<Role> getRoles() {
		List<Role> roles = new ArrayList<Role>();
		List<Object> list = getEntityService().getList("getRoles", null);
		if (list != null && !list.isEmpty()) {
			for (Object obj : list) {
				if (obj instanceof Role) {
					Role role = (Role) obj;
					roles.add(role);
				}
			}
		}
		return roles;
	}

	public static TreeModel getTreeModelByCode(String code) {
		return (TreeModel) getEntityService().getById("getTreeModelByCode",
				code);
	}

	public static TreeModel getTreeModelById(Long id) {
		return (TreeModel) getEntityService().getById("getTreeModelById", id);
	}

	/**
	 * 根据用户名获取用户对象
	 * 
	 * @param actorId
	 * @return
	 */
	public static User getUser(String actorId) {
		String cacheKey = "mx_user_" + actorId;
		if (SystemConfig.getBoolean("use_query_cache")
				&& CacheFactory.getString(cacheKey) != null) {
			String txt = CacheFactory.getString(cacheKey);
			JSONObject json = JSON.parseObject(txt);
			return UserJsonFactory.jsonToObject(json);
		}
		User user = (User) getEntityService().getById("getUserById", actorId);
		if (user != null) {
			if (SystemConfig.getBoolean("use_query_cache")) {
				JSONObject json = UserJsonFactory.toJsonObject(user);
				CacheFactory.put(cacheKey, json.toJSONString());
			}
		}
		return user;
	}

	/**
	 * 根据用户ID获取用户对象
	 * 
	 * @param userId
	 * @return
	 */
	public static User getUserByUserId(Long userId) {
		String cacheKey = "mx_user2_" + userId;
		if (SystemConfig.getBoolean("use_query_cache")
				&& CacheFactory.getString(cacheKey) != null) {
			String txt = CacheFactory.getString(cacheKey);
			JSONObject json = JSON.parseObject(txt);
			return UserJsonFactory.jsonToObject(json);
		}
		User user = (User) getEntityService()
				.getById("getUserByUserId", userId);
		if (user != null) {
			if (SystemConfig.getBoolean("use_query_cache")) {
				JSONObject json = UserJsonFactory.toJsonObject(user);
				CacheFactory.put(cacheKey, json.toJSONString());
			}
		}
		return user;
	}

	/**
	 * 获取全部用户Map
	 * 
	 * @return
	 */
	public static Map<String, User> getUserMap() {
		Map<String, User> userMap = new LinkedHashMap<String, User>();
		List<Object> list = getEntityService().getList("getUsers", null);
		if (list != null && !list.isEmpty()) {
			for (Object obj : list) {
				if (obj instanceof User) {
					User user = (User) obj;
					userMap.put(user.getActorId(), user);
				}
			}
		}
		return userMap;
	}

	public static List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		List<Object> list = getEntityService().getList("getUsers", null);
		if (list != null && !list.isEmpty()) {
			for (Object obj : list) {
				if (obj instanceof User) {
					User u = (User) obj;
					users.add(u);
				}
			}
		}
		return users;
	}

	public static List<String> getUserRoleCodes(String actorId) {
		MembershipQuery query = new MembershipQuery();
		query.actorId(actorId);
		List<Object> list = getEntityService().getList("getUserRoleCodes",
				query);
		List<String> roles = new java.util.ArrayList<String>();
		if (list != null && !list.isEmpty()) {
			for (Object object : list) {
				roles.add(object.toString());
			}
		}
		return roles;
	}

	public static void setEntityService(EntityService entityService) {
		IdentityFactory.entityService = entityService;
	}

	private IdentityFactory() {

	}

}
