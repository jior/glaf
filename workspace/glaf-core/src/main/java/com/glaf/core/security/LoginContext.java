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

import java.util.Collection;
import java.util.HashSet;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.JSONable;
import com.glaf.core.identity.User;

/**
 * 安全上下文 <br/>
 * 本对象包含用户基本信息，角色信息，权限点信息，用户下属信息 <br/>
 * 用户登录系统时已经放到HTTP会话中
 */
public class LoginContext implements java.io.Serializable, Cloneable, JSONable {

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
	protected Long deptId;

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
	protected Collection<Long> roleIds = new HashSet<Long>();

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
	protected Collection<Long> subDeptIds = new HashSet<Long>();

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

	public void addRoleId(Long role) {
		if (roleIds == null) {
			roleIds = new HashSet<Long>();
		}
		if (!roleIds.contains(role)) {
			roleIds.add(role);
		}
		this.addPermission(String.valueOf(role));
	}

	public void addSubDept(Long deptId) {
		if (subDeptIds == null) {
			subDeptIds = new HashSet<Long>();
		}
		if (!subDeptIds.contains(deptId)) {
			subDeptIds.add(deptId);
		}
	}

	public LoginContext clone() {
		return clone(this);
	}

	public LoginContext clone(LoginContext loginContext) {
		return LoginContextUtils.clone(loginContext);
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

	public Long getDeptId() {
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

	public Collection<Long> getRoleIds() {
		if (roleIds == null) {
			roleIds = new HashSet<Long>();
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

	public Collection<Long> getSubDeptIds() {
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
		Collection<Long> roles = this.getRoleIds();
		if (roles != null) {
			if (roles.contains(10000L)) {
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
		Collection<Long> roleIds = this.getRoleIds();
		if (roleIds != null) {
			if (roleIds.contains(10000L)) {
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
		Collection<Long> roleIds = this.getRoleIds();
		if (roleIds != null) {
			if (roleIds.contains(10000L)) {
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
		return LoginContextUtils.jsonToObject(jsonObject);
	}

	public void setAgents(Collection<String> agents) {
		this.agents = agents;
	}

	public void setCurrentAccessLevel(int currentAccessLevel) {
		this.currentAccessLevel = currentAccessLevel;
	}

	public void setDeptId(Long deptId) {
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

	public void setRoleIds(Collection<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public void setRoles(Collection<String> roles) {
		this.roles = roles;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public void setSubDeptIds(Collection<Long> subDeptIds) {
		this.subDeptIds = subDeptIds;
	}

	public void setSystemType(int systemType) {
		this.systemType = systemType;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public JSONObject toJsonObject() {
		return LoginContextUtils.toJsonObject(this);
	}

	public JSONObject toJsonObject(LoginContext loginContext) {
		return LoginContextUtils.toJsonObject(loginContext);
	}

}