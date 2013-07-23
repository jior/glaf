package com.glaf.core.security;

import java.util.Collection;
import java.util.Iterator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.identity.User;
import com.glaf.core.identity.impl.UserImpl;

public class LoginContextUtils {

	private LoginContextUtils() {

	}

	public static LoginContext clone(LoginContext loginContext) {
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
			for (Long x : loginContext.getRoleIds()) {
				m.addRoleId(x);
			}
		}

		if (loginContext.getSubDeptIds() != null) {
			for (Long x : loginContext.getSubDeptIds()) {
				m.addSubDept(x);
			}
		}

		return m;
	}

	public static LoginContext jsonToObject(JSONObject jsonObject) {
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
			loginContext.setDeptId(jsonObject.getLong("deptId"));
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
				loginContext.addRoleId(Long.parseLong(role));
			}
		}

		if (jsonObject.containsKey("subDeptIds")) {
			JSONArray jsonArray = jsonObject.getJSONArray("subDeptIds");
			Iterator<Object> iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
				String subDeptId = (String) iterator.next();
				loginContext.addSubDept(Long.parseLong(subDeptId));
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

		return loginContext;
	}

	public static JSONObject toJsonObject(LoginContext loginContext) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("actorId", loginContext.getActorId());
		jsonObject.put("currentAccessLevel",
				loginContext.getCurrentAccessLevel());
		jsonObject.put("deptId", loginContext.getDeptId());
		jsonObject.put("skin", loginContext.getSkin());
		jsonObject.put("systemType", loginContext.getSystemType());

		if (loginContext.getUser() != null) {
			jsonObject.put("user", loginContext.getUser().toJsonObject());
		}

		Collection<String> roles = loginContext.getRoles();
		if (roles != null && !roles.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			for (String role : roles) {
				jsonArray.add(role);
			}
			jsonObject.put("roles", jsonArray);
		}

		Collection<Long> roleIds = loginContext.getRoleIds();
		if (roleIds != null && !roleIds.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			for (Long roleId : roleIds) {
				jsonArray.add(String.valueOf(roleId));
			}
			jsonObject.put("roleIds", jsonArray);
		}

		Collection<Long> subDeptIds = loginContext.getSubDeptIds();
		if (subDeptIds != null && !subDeptIds.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			for (Long subDeptId : subDeptIds) {
				jsonArray.add(String.valueOf(subDeptId));
			}
			jsonObject.put("subDeptIds", jsonArray);
		}

		Collection<String> agents = loginContext.getAgents();
		if (agents != null && !agents.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			for (String agentId : agents) {
				jsonArray.add(agentId);
			}
			jsonObject.put("agents", jsonArray);
		}

		Collection<String> functions = loginContext.getFunctions();
		if (functions != null && !functions.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			for (String function : functions) {
				jsonArray.add(function);
			}
			jsonObject.put("functions", jsonArray);
		}

		Collection<String> permissions = loginContext.getPermissions();
		if (permissions != null && !permissions.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			for (String permission : permissions) {
				jsonArray.add(permission);
			}
			jsonObject.put("permissions", jsonArray);
		}

		Collection<String> observers = loginContext.getObservers();
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
