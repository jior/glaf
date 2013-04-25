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
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.SysUserRole;
import com.glaf.core.util.DateUtils;

public class SysUserRoleJsonFactory {

	public static java.util.List<SysUserRole> arrayToList(JSONArray array) {
		java.util.List<SysUserRole> list = new java.util.ArrayList<SysUserRole>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SysUserRole model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static SysUserRole jsonToObject(JSONObject jsonObject) {
		SysUserRole model = new SysUserRole();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getLong("userId"));
		}
		if (jsonObject.containsKey("deptRoleId")) {
			model.setDeptRoleId(jsonObject.getLong("deptRoleId"));
		}
		if (jsonObject.containsKey("authorized")) {
			model.setAuthorized(jsonObject.getInteger("authorized"));
		}
		if (jsonObject.containsKey("authorizeFrom")) {
			model.setAuthorizeFrom(jsonObject.getLong("authorizeFrom"));
		}
		if (jsonObject.containsKey("availDateStart")) {
			model.setAvailDateStart(jsonObject.getDate("availDateStart"));
		}
		if (jsonObject.containsKey("availDateEnd")) {
			model.setAvailDateEnd(jsonObject.getDate("availDateEnd"));
		}
		if (jsonObject.containsKey("processDescription")) {
			model.setProcessDescription(jsonObject
					.getString("processDescription"));
		}

		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<SysUserRole> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SysUserRole model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(SysUserRole model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("userId", model.getUserId());
		jsonObject.put("deptRoleId", model.getDeptRoleId());
		jsonObject.put("authorized", model.getAuthorized());
		jsonObject.put("authorizeFrom", model.getAuthorizeFrom());
		if (model.getAvailDateStart() != null) {
			jsonObject.put("availDateStart",
					DateUtils.getDate(model.getAvailDateStart()));
			jsonObject.put("availDateStart_date",
					DateUtils.getDate(model.getAvailDateStart()));
			jsonObject.put("availDateStart_datetime",
					DateUtils.getDateTime(model.getAvailDateStart()));
		}
		if (model.getAvailDateEnd() != null) {
			jsonObject.put("availDateEnd",
					DateUtils.getDate(model.getAvailDateEnd()));
			jsonObject.put("availDateEnd_date",
					DateUtils.getDate(model.getAvailDateEnd()));
			jsonObject.put("availDateEnd_datetime",
					DateUtils.getDateTime(model.getAvailDateEnd()));
		}
		if (model.getProcessDescription() != null) {
			jsonObject.put("processDescription", model.getProcessDescription());
		}

		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}

		return jsonObject;
	}

	public static ObjectNode toObjectNode(SysUserRole model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("userId", model.getUserId());
		jsonObject.put("deptRoleId", model.getDeptRoleId());
		jsonObject.put("authorized", model.getAuthorized());
		jsonObject.put("authorizeFrom", model.getAuthorizeFrom());
		if (model.getAvailDateStart() != null) {
			jsonObject.put("availDateStart",
					DateUtils.getDate(model.getAvailDateStart()));
			jsonObject.put("availDateStart_date",
					DateUtils.getDate(model.getAvailDateStart()));
			jsonObject.put("availDateStart_datetime",
					DateUtils.getDateTime(model.getAvailDateStart()));
		}
		if (model.getAvailDateEnd() != null) {
			jsonObject.put("availDateEnd",
					DateUtils.getDate(model.getAvailDateEnd()));
			jsonObject.put("availDateEnd_date",
					DateUtils.getDate(model.getAvailDateEnd()));
			jsonObject.put("availDateEnd_datetime",
					DateUtils.getDateTime(model.getAvailDateEnd()));
		}
		if (model.getProcessDescription() != null) {
			jsonObject.put("processDescription", model.getProcessDescription());
		}

		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}

		return jsonObject;
	}

	private SysUserRoleJsonFactory() {

	}

}
