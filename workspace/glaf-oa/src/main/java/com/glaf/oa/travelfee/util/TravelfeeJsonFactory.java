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
package com.glaf.oa.travelfee.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.oa.travelfee.model.*;

public class TravelfeeJsonFactory {

	public static Travelfee jsonToObject(JSONObject jsonObject) {
		Travelfee model = new Travelfee();
		if (jsonObject.containsKey("feeid")) {
			model.setFeeid(jsonObject.getLong("feeid"));
		}
		if (jsonObject.containsKey("travelid")) {
			model.setTravelid(jsonObject.getLong("travelid"));
		}
		if (jsonObject.containsKey("feename")) {
			model.setFeename(jsonObject.getString("feename"));
		}
		if (jsonObject.containsKey("feesum")) {
			model.setFeesum(jsonObject.getDouble("feesum"));
		}
		if (jsonObject.containsKey("remark")) {
			model.setRemark(jsonObject.getString("remark"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}

		return model;
	}

	public static JSONObject toJsonObject(Travelfee model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("feeid", model.getFeeid());
		jsonObject.put("_feeid_", model.getFeeid());
		jsonObject.put("travelid", model.getTravelid());
		if (model.getFeename() != null) {
			jsonObject.put("feename", model.getFeename());
		}
		jsonObject.put("feesum", model.getFeesum());
		if (model.getRemark() != null) {
			jsonObject.put("remark", model.getRemark());
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
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Travelfee model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("feeid", model.getFeeid());
		jsonObject.put("_feeid_", model.getFeeid());
		jsonObject.put("travelid", model.getTravelid());
		if (model.getFeename() != null) {
			jsonObject.put("feename", model.getFeename());
		}
		jsonObject.put("feesum", model.getFeesum());
		if (model.getRemark() != null) {
			jsonObject.put("remark", model.getRemark());
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
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<Travelfee> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Travelfee model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Travelfee> arrayToList(JSONArray array) {
		java.util.List<Travelfee> list = new java.util.ArrayList<Travelfee>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Travelfee model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private TravelfeeJsonFactory() {

	}

}