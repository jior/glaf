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
package com.glaf.oa.assesscontent.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.oa.assesscontent.model.Assesscontent;
import com.glaf.core.util.DateUtils;

public class AssesscontentJsonFactory {

	public static Assesscontent jsonToObject(JSONObject jsonObject) {
		Assesscontent model = new Assesscontent();
		if (jsonObject.containsKey("contentid")) {
			model.setContentid(jsonObject.getLong("contentid"));
		}
		if (jsonObject.containsKey("sortid")) {
			model.setSortid(jsonObject.getLong("sortid"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("basis")) {
			model.setBasis(jsonObject.getString("basis"));
		}
		if (jsonObject.containsKey("standard")) {
			model.setStandard(jsonObject.getDouble("standard"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}

		return model;
	}

	public static JSONObject toJsonObject(Assesscontent model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contentid", model.getContentid());
		jsonObject.put("_contentid_", model.getContentid());
		jsonObject.put("sortid", model.getSortid());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getBasis() != null) {
			jsonObject.put("basis", model.getBasis());
		}
		jsonObject.put("standard", model.getStandard());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Assesscontent model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("contentid", model.getContentid());
		jsonObject.put("_contentid_", model.getContentid());
		jsonObject.put("sortid", model.getSortid());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getBasis() != null) {
			jsonObject.put("basis", model.getBasis());
		}
		jsonObject.put("standard", model.getStandard());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<Assesscontent> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Assesscontent model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Assesscontent> arrayToList(JSONArray array) {
		java.util.List<Assesscontent> list = new java.util.ArrayList<Assesscontent>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Assesscontent model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private AssesscontentJsonFactory() {

	}

}