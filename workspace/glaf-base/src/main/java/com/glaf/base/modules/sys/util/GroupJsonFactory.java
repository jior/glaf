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

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.*;
import com.glaf.core.util.DateUtils;

public class GroupJsonFactory {

	public static java.util.List<Group> arrayToList(JSONArray array) {
		java.util.List<Group> list = new java.util.ArrayList<Group>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Group model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static Group jsonToObject(JSONObject jsonObject) {
		Group model = new Group();
		if (jsonObject.containsKey("groupId")) {
			model.setGroupId(jsonObject.getString("groupId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
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
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<Group> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Group model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(Group model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("groupId", model.getGroupId());
		jsonObject.put("_groupId_", model.getGroupId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
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
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		jsonObject.put("sort", model.getSort());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Group model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("groupId", model.getGroupId());
		jsonObject.put("_groupId_", model.getGroupId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
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
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		jsonObject.put("sort", model.getSort());
		return jsonObject;
	}

	private GroupJsonFactory() {

	}

}
