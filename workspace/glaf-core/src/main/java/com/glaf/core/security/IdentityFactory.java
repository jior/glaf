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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.base.TreeModel;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.identity.Agent;
import com.glaf.core.identity.Role;
import com.glaf.core.identity.User;
import com.glaf.core.query.MembershipQuery;
import com.glaf.core.service.EntityService;

public class IdentityFactory {
	protected static EntityService entityService;

	protected final static Log logger = LogFactory
			.getLog(IdentityFactory.class);

	/**
	 * ��ȡί���˱�ż���
	 * 
	 * @param assignTo
	 *            �����˱��
	 * @return
	 */
	public static List<String> getAgentIds(String assignTo) {
		List<String> agentIds = new ArrayList<String>();
		List<Object> list = getEntityService().getList("getAgents", assignTo);
		if (list != null && !list.isEmpty()) {
			for (Object obj : list) {
				if (obj instanceof Agent) {
					Agent agent = (Agent) obj;
					if (!agent.isValid()) {
						continue;
					}
					switch (agent.getAgentType()) {
					case 0:// ȫ�ִ���
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
	 * ��ȡί���˶��󼯺�
	 * 
	 * @param assignTo
	 *            �����˱��
	 * @return
	 */
	public static List<Agent> getAgents(String assignTo) {
		List<Agent> agents = new ArrayList<Agent>();
		List<Object> list = getEntityService().getList("getAgents", assignTo);
		if (list != null && !list.isEmpty()) {
			for (Object obj : list) {
				if (obj instanceof Agent) {
					Agent agent = (Agent) obj;
					if (!agent.isValid()) {
						continue;
					}
					switch (agent.getAgentType()) {
					case 0:// ȫ�ִ���
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
		List<TreeModel> treeModels = new ArrayList<TreeModel>();
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
	 * ��ȡȫ������Map
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
	 * ��ȡ��¼�û���Ϣ
	 * 
	 * @param actorId
	 * @return
	 */
	public static LoginContext getLoginContext(String actorId) {
		LoginHandler loginHandler = null;
		/**
		 * ��ȡSpring�����ж���ĵ�¼Handler����Handler����ʵ��LoginHandler�ӿ�
		 */
		if (ContextFactory.hasBean("loginHandler")) {
			loginHandler = ContextFactory.getBean("loginHandler");
			if (loginHandler != null) {
				/**
				 * ��ȡ�Զ���ĵ�¼������
				 */
				return loginHandler.getLoginContext(actorId);
			}
		}
		User user = (User) getEntityService().getById("getUserById", actorId);
		if (user != null) {
			LoginContext loginContext = new LoginContext(user);
			List<String> roles = new ArrayList<String>();

			/**
			 * ��ȡ���˵Ľ�ɫȨ��
			 */
			List<String> list = getUserRoleCodes(actorId);
			if (list != null && !list.isEmpty()) {
				roles.addAll(list);
			}

			/**
			 * ��ȡ�����˵Ľ�ɫȨ��
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
			return loginContext;
		}
		return null;
	}

	/**
	 * ��ȡȫ���û�Map
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
	 * ��ȡȫ����ɫMap
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

	public static TreeModel getTreeModelByCode(String code) {
		return (TreeModel) getEntityService().getById("getTreeModelByCode",
				code);
	}

	public static TreeModel getTreeModelById(Long id) {
		return (TreeModel) getEntityService().getById("getTreeModelById", id);
	}

	/**
	 * �����û�����ȡ�û�����
	 * 
	 * @param actorId
	 * @return
	 */
	public static User getUser(String actorId) {
		return (User) getEntityService().getById("getUserById", actorId);
	}

	/**
	 * ��ȡȫ���û�Map
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

	public static List<String> getUserRoleCodes(String actorId) {
		MembershipQuery query = new MembershipQuery();
		query.actorId(actorId);
		List<Object> list = getEntityService().getList("getUserRoleCodes",
				query);
		List<String> roles = new ArrayList<String>();
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
