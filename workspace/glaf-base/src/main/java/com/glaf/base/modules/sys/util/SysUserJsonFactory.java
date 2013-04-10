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

package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysDeptRole;
import com.glaf.base.modules.sys.model.SysFunction;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.model.SysUserRole;
import com.glaf.core.util.DateUtils;

public class SysUserJsonFactory {

	public static SysUser jsonToObject(JSONObject jsonObject) {
		SysUser model = new SysUser();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getIntValue("id"));
		}

		if (jsonObject.containsKey("actorId")) {
			model.setActorId(jsonObject.getString("actorId"));
		}
		if (jsonObject.containsKey("account")) {
			model.setActorId(jsonObject.getString("account"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}

		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}

		if (jsonObject.containsKey("evection")) {
			model.setEvection(jsonObject.getIntValue("evection"));
		}

		if (jsonObject.containsKey("gender")) {
			model.setGender(jsonObject.getIntValue("gender"));
		}

		if (jsonObject.containsKey("userType")) {
			model.setUserType(jsonObject.getIntValue("userType"));
		}

		if (jsonObject.containsKey("accountType")) {
			model.setAccountType(jsonObject.getIntValue("accountType"));
		}

		if (jsonObject.containsKey("dumpFlag")) {
			model.setDumpFlag(jsonObject.getIntValue("dumpFlag"));
		}

		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}

		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}

		if (jsonObject.containsKey("lastLoginTime")) {
			model.setLastLoginTime(jsonObject.getDate("lastLoginTime"));
		}

		if (jsonObject.containsKey("lastLoginIP")) {
			model.setLastLoginIP(jsonObject.getString("lastLoginIP"));
		}

		if (jsonObject.containsKey("remark")) {
			model.setRemark(jsonObject.getString("remark"));
		}

		if (jsonObject.containsKey("deptId")) {
			model.setDeptId(jsonObject.getInteger("deptId"));
		}

		if (jsonObject.containsKey("mail")) {
			model.setMail(jsonObject.getString("mail"));
		}

		if (jsonObject.containsKey("mobile")) {
			model.setMobile(jsonObject.getString("mobile"));
		}

		if (jsonObject.containsKey("telephone")) {
			model.setTelephone(jsonObject.getString("telephone"));
		}

		if (jsonObject.containsKey("headship")) {
			model.setHeadship(jsonObject.getString("headship"));
		}

		if (jsonObject.containsKey("superiorIds")) {
			model.setSuperiorIds(jsonObject.getString("superiorIds"));
		}

		if (jsonObject.containsKey("blocked")) {
			model.setBlocked(jsonObject.getIntValue("blocked"));
		}

		if (jsonObject.containsKey("adminFlag")) {
			model.setAdminFlag(jsonObject.getString("adminFlag"));
		}

		if (jsonObject.containsKey("userRoles")) {
			JSONArray array = jsonObject.getJSONArray("userRoles");
			if (array != null && !array.isEmpty()) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject json = array.getJSONObject(i);
					SysUserRole m = new SysUserRole();
					SysUserRole r = m.jsonToObject(json);
					model.getUserRoles().add(r);
				}
			}
		}

		if (jsonObject.containsKey("roles")) {
			JSONArray array = jsonObject.getJSONArray("roles");
			if (array != null && !array.isEmpty()) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject json = array.getJSONObject(i);
					SysDeptRole m = new SysDeptRole();
					SysDeptRole r = m.jsonToObject(json);
					model.getRoles().add(r);
				}
			}
		}

		if (jsonObject.containsKey("functions")) {
			JSONArray array = jsonObject.getJSONArray("functions");
			if (array != null && !array.isEmpty()) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject json = array.getJSONObject(i);
					SysFunction m = new SysFunction();
					SysFunction r = m.jsonToObject(json);
					model.getFunctions().add(r);
				}
			}
		}

		if (jsonObject.containsKey("apps")) {
			JSONArray array = jsonObject.getJSONArray("apps");
			if (array != null && !array.isEmpty()) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject json = array.getJSONObject(i);
					SysApplication m = new SysApplication();
					SysApplication r = m.jsonToObject(json);
					model.getApps().add(r);
				}
			}
		}

		return model;
	}

	public static JSONObject toJsonObject(SysUser user) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", user.getId());
		jsonObject.put("actorId", user.getAccount());
		jsonObject.put("code", user.getCode());
		jsonObject.put("name", user.getName());
		jsonObject.put("locked", user.getBlocked());
		if (user.getDepartment() != null) {
			jsonObject.put("deptId", user.getDepartment().getId());
		} else {
			jsonObject.put("deptId", user.getDeptId());
		}
		jsonObject.put("accountType", user.getAccountType());
		jsonObject.put("userType", user.getUserType());
		jsonObject.put("dumpFlag", user.getDumpFlag());
		jsonObject.put("gender", user.getGender());
		jsonObject.put("evection", user.getEvection());
		jsonObject.put("superiorIds", user.getSuperiorIds());

		jsonObject.put("fax", user.getFax());
		jsonObject.put("telephone", user.getTelephone());
		jsonObject.put("headship", user.getHeadship());
		jsonObject.put("adminFlag", user.getAdminFlag());

		if (user.getEmail() != null) {
			jsonObject.put("mail", user.getEmail());
			jsonObject.put("email", user.getEmail());
		}
		if (user.getMobile() != null) {
			jsonObject.put("mobile", user.getMobile());
		}
		if (user.getLastLoginTime() != null) {
			jsonObject.put("lastLoginDate",
					DateUtils.getDateTime(user.getLastLoginDate()));
			jsonObject.put("lastLoginTime",
					DateUtils.getDateTime(user.getLastLoginDate()));
		}
		if (user.getLastLoginIP() != null) {
			jsonObject.put("loginIP", user.getLastLoginIP());
		}

		if (user.getCreateBy() != null) {
			jsonObject.put("createBy", user.getCreateBy());
		}
		if (user.getUpdateBy() != null) {
			jsonObject.put("updateBy", user.getUpdateBy());
		}
		if (user.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(user.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(user.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(user.getUpdateDate()));
		}

		if (user.getUserRoles() != null && !user.getUserRoles().isEmpty()) {
			JSONArray array = new JSONArray();
			for (SysUserRole sysUserRole : user.getUserRoles()) {
				array.add(sysUserRole.toJsonObject());
			}
			jsonObject.put("userRoles", array);
		}

		if (user.getRoles() != null && !user.getRoles().isEmpty()) {
			JSONArray array = new JSONArray();
			for (SysDeptRole sysDeptRole : user.getRoles()) {
				array.add(sysDeptRole.toJsonObject());
			}
			jsonObject.put("roles", array);
		}

		if (user.getFunctions() != null && !user.getFunctions().isEmpty()) {
			JSONArray array = new JSONArray();
			for (SysFunction sysFunction : user.getFunctions()) {
				array.add(sysFunction.toJsonObject());
			}
			jsonObject.put("functions", array);
		}

		if (user.getApps() != null && !user.getApps().isEmpty()) {
			JSONArray array = new JSONArray();
			for (SysApplication app : user.getApps()) {
				array.add(app.toJsonObject());
			}
			jsonObject.put("apps", array);
		}

		return jsonObject;
	}

	public static ObjectNode toObjectNode(SysUser user) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();

		jsonObject.put("id", user.getId());
		jsonObject.put("actorId", user.getAccount());
		jsonObject.put("code", user.getCode());
		jsonObject.put("name", user.getName());
		jsonObject.put("locked", user.getBlocked());
		if (user.getDepartment() != null) {
			jsonObject.put("deptId", user.getDepartment().getId());
		} else {
			jsonObject.put("deptId", user.getDeptId());
		}

		jsonObject.put("accountType", user.getAccountType());
		jsonObject.put("userType", user.getUserType());
		jsonObject.put("dumpFlag", user.getDumpFlag());
		jsonObject.put("gender", user.getGender());
		jsonObject.put("evection", user.getEvection());
		jsonObject.put("superiorIds", user.getSuperiorIds());

		jsonObject.put("fax", user.getFax());
		jsonObject.put("telephone", user.getTelephone());
		jsonObject.put("headship", user.getHeadship());
		jsonObject.put("adminFlag", user.getAdminFlag());

		if (user.getEmail() != null) {
			jsonObject.put("mail", user.getEmail());
			jsonObject.put("email", user.getEmail());
		}
		if (user.getMobile() != null) {
			jsonObject.put("mobile", user.getMobile());
		}
		if (user.getLastLoginTime() != null) {
			jsonObject.put("lastLoginDate",
					DateUtils.getDateTime(user.getLastLoginDate()));
			jsonObject.put("lastLoginTime",
					DateUtils.getDateTime(user.getLastLoginDate()));
		}
		if (user.getLastLoginIP() != null) {
			jsonObject.put("loginIP", user.getLastLoginIP());
		}

		if (user.getCreateBy() != null) {
			jsonObject.put("createBy", user.getCreateBy());
		}
		if (user.getUpdateBy() != null) {
			jsonObject.put("updateBy", user.getUpdateBy());
		}
		if (user.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(user.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(user.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(user.getUpdateDate()));
		}

		if (user.getUserRoles() != null && !user.getUserRoles().isEmpty()) {
			ArrayNode array = new ObjectMapper().createArrayNode();
			for (SysUserRole sysUserRole : user.getUserRoles()) {
				array.add(sysUserRole.toObjectNode());
			}
			jsonObject.put("userRoles", array);
		}

		if (user.getRoles() != null && !user.getRoles().isEmpty()) {
			ArrayNode array = new ObjectMapper().createArrayNode();
			for (SysDeptRole sysDeptRole : user.getRoles()) {
				array.add(sysDeptRole.toObjectNode());
			}
			jsonObject.put("roles", array);
		}

		if (user.getFunctions() != null && !user.getFunctions().isEmpty()) {
			ArrayNode array = new ObjectMapper().createArrayNode();
			for (SysFunction sysFunction : user.getFunctions()) {
				array.add(sysFunction.toObjectNode());
			}
			jsonObject.put("functions", array);
		}

		if (user.getApps() != null && !user.getApps().isEmpty()) {
			ArrayNode array = new ObjectMapper().createArrayNode();
			for (SysApplication app : user.getApps()) {
				array.add(app.toObjectNode());
			}
			jsonObject.put("apps", array);
		}

		return jsonObject;
	}

	private SysUserJsonFactory() {

	}

}
