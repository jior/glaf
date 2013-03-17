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

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.identity.*;
import com.glaf.core.identity.impl.UserImpl;

/**
 * 安全上下文 <br/>
 * 本对象包含用户基本信息，角色信息，权限点信息，用户下属信息 <br/>
 * 用户登录系统时已经放到HTTP会话中
 */
public class LoginContext implements java.io.Serializable, Cloneable {

	public static final String CURRENT_USER = "CURRENT_USER";

	protected static final Log logger = LogFactory.getLog(LoginContext.class);

	public final static int SENIOR_MANAGER = 99;

	private static final long serialVersionUID = 1L;

	public final static int SYSTEM_ADMINISTRATOR = 1000;

	public final static int USER = 0;

	/**
	 * 代理人编号集合
	 */
	protected Collection<String> agents = new HashSet<String>();

	/**
	 * 当前访问级别
	 */
	protected int currentAccessLevel;

	/**
	 * 部门编号
	 */
	protected int deptId;

	/**
	 * 功能集合
	 */
	protected Collection<String> functions = new HashSet<String>();

	/**
	 * 观察者集合
	 */
	protected Collection<String> observers = new HashSet<String>();

	/**
	 * 权限点集合
	 */
	protected Collection<String> permissions = new HashSet<String>();

	/**
	 * 角色编号集合
	 */
	protected Collection<Integer> roleIds = new HashSet<Integer>();

	/**
	 * 角色代码集合
	 */
	protected Collection<String> roles = new HashSet<String>();

	/**
	 * 皮肤
	 */
	protected String skin;

	/**
	 * 子部门编号
	 */
	protected Collection<Integer> subDeptIds = new HashSet<Integer>();

	/**
	 * 用户系统
	 */
	protected int systemType;

	/**
	 * 登录用户
	 */
	protected User user;

	public LoginContext() {

	}

	public LoginContext(User user) {
		this.user = user;
	}

	public void addAgent(String agent) {
		if (agents == null) {
			agents = new HashSet<String>();
		}
		if (!agents.contains(agent)) {
			agents.add(agent);
		}
	}

	public void addFunction(String function) {
		if (functions == null) {
			functions = new HashSet<String>();
		}
		if (!functions.contains(function)) {
			functions.add(function);
		}
	}

	public void addObserver(String observer) {
		if (observers == null) {
			observers = new HashSet<String>();
		}
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	public void addPermission(String permission) {
		if (permissions == null) {
			permissions = new HashSet<String>();
		}
		if (!permissions.contains(permission)) {
			permissions.add(permission);
		}
	}

	public void addRole(String role) {
		if (roles == null) {
			roles = new HashSet<String>();
		}
		if (!roles.contains(role)) {
			roles.add(role);
		}
		this.addPermission(String.valueOf(role));
	}

	public void addRoleId(Integer role) {
		if (roleIds == null) {
			roleIds = new HashSet<Integer>();
		}
		if (!roleIds.contains(role)) {
			roleIds.add(role);
		}
		this.addPermission(String.valueOf(role));
	}

	public void addSubDept(Integer deptId) {
		if (subDeptIds == null) {
			subDeptIds = new HashSet<Integer>();
		}
		if (!subDeptIds.contains(deptId)) {
			subDeptIds.add(deptId);
		}
	}

	public LoginContext clone() {
		return clone(this);
	}

	public LoginContext clone(LoginContext loginContext) {
		LoginContext m = new LoginContext();
		m.setSkin(loginContext.getSkin());
		m.setUser(loginContext.getUser());
		m.setDeptId(loginContext.getDeptId());
		m.setSystemType(loginContext.getSystemType());
		m.setCurrentAccessLevel(loginContext.getCurrentAccessLevel());

		if (loginContext.getAgents() != null) {
			for (String x : loginContext.getAgents()) {
				m.addAgent(x);
			}
		}

		if (loginContext.getFunctions() != null) {
			for (String x : loginContext.getFunctions()) {
				m.addFunction(x);
			}
		}

		if (loginContext.getObservers() != null) {
			for (String x : loginContext.getObservers()) {
				m.addObserver(x);
			}
		}

		if (loginContext.getPermissions() != null) {
			for (String x : loginContext.getPermissions()) {
				m.addPermission(x);
			}
		}

		if (loginContext.getRoles() != null) {
			for (String x : loginContext.getRoles()) {
				m.addRole(x);
			}
		}

		if (loginContext.getRoleIds() != null) {
			for (Integer x : loginContext.getRoleIds()) {
				m.addRoleId(x);
			}
		}

		if (loginContext.getSubDeptIds() != null) {
			for (Integer x : loginContext.getSubDeptIds()) {
				m.addSubDept(x);
			}
		}

		return m;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof LoginContext)) {
			return false;
		}
		LoginContext other = (LoginContext) obj;
		if (user == null) {
			if (other.getUser() != null) {
				return false;
			}
		} else if (!user.equals(other.getUser())) {
			return false;
		}
		return true;
	}

	public String getActorId() {
		if (user != null) {
			return user.getActorId();
		}
		return null;
	}

	public Collection<String> getAgents() {
		return agents;
	}

	public int getCurrentAccessLevel() {
		return currentAccessLevel;
	}

	public int getDeptId() {
		return deptId;
	}

	public Collection<String> getFunctions() {
		return functions;
	}

	public Collection<String> getObservers() {
		return observers;
	}

	public Collection<String> getPermissions() {
		if (permissions == null) {
			permissions = new HashSet<String>();
		}
		return permissions;
	}

	public Collection<Integer> getRoleIds() {
		if (roleIds == null) {
			roleIds = new HashSet<Integer>();
		}
		return roleIds;
	}

	public Collection<String> getRoles() {
		if (roles == null) {
			roles = new HashSet<String>();
		}
		return roles;
	}

	public String getSkin() {
		return skin;
	}

	public Collection<Integer> getSubDeptIds() {
		return subDeptIds;
	}

	public int getSystemType() {
		return systemType;
	}

	public User getUser() {
		return user;
	}

	public boolean hasAdvancedPermission() {
		boolean hasPermission = false;
		Collection<Integer> roles = this.getRoleIds();
		if (roles != null) {
			if (roles.contains(10000)) {
				hasPermission = true;
			}
		}
		return hasPermission;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	public boolean hasPermission(String key, String operator) {
		boolean hasPermission = false;
		StringTokenizer token = new StringTokenizer(key, ",");
		if (operator != null) {
			if ((operator.equals("||") || operator.equalsIgnoreCase("or"))) {
				while (token.hasMoreTokens()) {
					String permKey = token.nextToken();
					if (permissions != null) {
						if (permissions.contains(permKey)) {
							hasPermission = true;
							break;
						}
					}
				}
			}
		} else {
			if (permissions != null) {
				while (token.hasMoreTokens()) {
					String permKey = token.nextToken();
					if (permissions.contains(permKey)) {
						hasPermission = true;
						break;
					}
				}
			}
		}
		return hasPermission;
	}

	public boolean hasSystemPermission() {
		if (this.isSystemAdministrator()) {
			return true;
		}
		boolean hasPermission = false;
		Collection<Integer> roleIds = this.getRoleIds();
		if (roleIds != null) {
			if (roleIds.contains(10000)) {
				hasPermission = true;
			}
		}
		Collection<String> roles = this.getRoles();
		if (roles != null) {
			if (roles.contains("SystemAdministrator")) {
				hasPermission = true;
			}
		}
		return hasPermission;
	}

	public boolean isSystemAdministrator() {
		boolean isSystemAdministrator = false;
		Collection<Integer> roleIds = this.getRoleIds();
		if (roleIds != null) {
			if (roleIds.contains(10000)) {
				isSystemAdministrator = true;
			}
		}

		Collection<String> roles = this.getRoles();
		if (roles != null) {
			if (roles.contains("SystemAdministrator")) {
				isSystemAdministrator = true;
			}
		}

		if (user != null && user.isSystemAdministrator()) {
			isSystemAdministrator = true;
		}

		return isSystemAdministrator;
	}

	public LoginContext jsonToObject(JSONObject jsonObject) {
		LoginContext loginContext = new LoginContext();
		if (jsonObject.containsKey("user")) {
			JSONObject json = jsonObject.getJSONObject("user");
			User user = new UserImpl();
			user = (User) user.jsonToObject(json);
			loginContext.setUser(user);
		}

		if (jsonObject.containsKey("currentAccessLevel")) {
			loginContext.setCurrentAccessLevel(jsonObject
					.getInteger("currentAccessLevel"));
		}

		if (jsonObject.containsKey("deptId")) {
			loginContext.setDeptId(jsonObject.getInteger("deptId"));
		}

		if (jsonObject.containsKey("systemType")) {
			loginContext.setSystemType(jsonObject.getInteger("systemType"));
		}

		if (jsonObject.containsKey("skin")) {
			loginContext.setSkin(jsonObject.getString("skin"));
		}

		if (jsonObject.containsKey("roles")) {
			JSONArray jsonArray = jsonObject.getJSONArray("roles");
			Iterator<Object> iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
				String role = (String) iterator.next();
				loginContext.addRole(role);
			}
		}

		if (jsonObject.containsKey("roleIds")) {
			JSONArray jsonArray = jsonObject.getJSONArray("roleIds");
			Iterator<Object> iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
				String role = (String) iterator.next();
				loginContext.addRoleId(Integer.parseInt(role));
			}
		}

		if (jsonObject.containsKey("subDeptIds")) {
			JSONArray jsonArray = jsonObject.getJSONArray("subDeptIds");
			Iterator<Object> iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
				String subDeptId = (String) iterator.next();
				loginContext.addSubDept(Integer.parseInt(subDeptId));
			}
		}

		if (jsonObject.containsKey("agents")) {
			JSONArray jsonArray = jsonObject.getJSONArray("agents");
			Iterator<Object> iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
				String agent = (String) iterator.next();
				loginContext.addAgent(agent);
			}
		}

		if (jsonObject.containsKey("functions")) {
			JSONArray jsonArray = jsonObject.getJSONArray("functions");
			Iterator<Object> iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
				String function = (String) iterator.next();
				loginContext.addFunction(function);
			}
		}

		if (jsonObject.containsKey("permissions")) {
			JSONArray jsonArray = jsonObject.getJSONArray("permissions");
			Iterator<Object> iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
				String permission = (String) iterator.next();
				loginContext.addPermission(permission);
			}
		}

		if (jsonObject.containsKey("observers")) {
			JSONArray jsonArray = jsonObject.getJSONArray("observers");
			Iterator<Object> iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
				String observer = (String) iterator.next();
				loginContext.addObserver(observer);
			}
		}

		logger.debug(jsonObject.toJSONString());

		return loginContext;
	}

	public void setAgents(Collection<String> agents) {
		this.agents = agents;
	}

	public void setCurrentAccessLevel(int currentAccessLevel) {
		this.currentAccessLevel = currentAccessLevel;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public void setFunctions(Collection<String> functions) {
		this.functions = functions;
	}

	public void setObservers(Collection<String> observers) {
		this.observers = observers;
	}

	public void setPermissions(Collection<String> permissions) {
		this.permissions = permissions;
	}

	public void setRoleIds(Collection<Integer> roleIds) {
		this.roleIds = roleIds;
	}

	public void setRoles(Collection<String> roles) {
		this.roles = roles;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public void setSubDeptIds(Collection<Integer> subDeptIds) {
		this.subDeptIds = subDeptIds;
	}

	public void setSystemType(int systemType) {
		this.systemType = systemType;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public JSONObject toJsonObject() {
		return toJsonObject(this);
	}

	public JSONObject toJsonObject(LoginContext loginContext) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("actorId", loginContext.getActorId());
		jsonObject.put("currentAccessLevel",
				loginContext.getCurrentAccessLevel());
		jsonObject.put("deptId", loginContext.getDeptId());
		jsonObject.put("skin", loginContext.getSkin());
		jsonObject.put("systemType", loginContext.getSystemType());

		if (user != null) {
			jsonObject.put("user", user.toJsonObject());
		}

		if (roles != null && !roles.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			for (String role : roles) {
				jsonArray.add(role);
			}
			jsonObject.put("roles", jsonArray);
		}

		if (roleIds != null && !roleIds.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			for (Integer roleId : roleIds) {
				jsonArray.add(String.valueOf(roleId));
			}
			jsonObject.put("roleIds", jsonArray);
		}

		if (subDeptIds != null && !subDeptIds.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			for (Integer subDeptId : subDeptIds) {
				jsonArray.add(String.valueOf(subDeptId));
			}
			jsonObject.put("subDeptIds", jsonArray);
		}

		if (agents != null && !agents.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			for (String agentId : agents) {
				jsonArray.add(agentId);
			}
			jsonObject.put("agents", jsonArray);
		}

		if (functions != null && !functions.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			for (String function : functions) {
				jsonArray.add(function);
			}
			jsonObject.put("functions", jsonArray);
		}

		if (permissions != null && !permissions.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			for (String permission : permissions) {
				jsonArray.add(permission);
			}
			jsonObject.put("permissions", jsonArray);
		}

		if (observers != null && !observers.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			for (String observer : observers) {
				jsonArray.add(observer);
			}
			jsonObject.put("observers", jsonArray);
		}

		return jsonObject;
	}

}