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
package com.glaf.oa.travelpersonnel.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.oa.travelpersonnel.model.*;

public class TravelpersonnelJsonFactory {

	public static Travelpersonnel jsonToObject(JSONObject jsonObject) {
		Travelpersonnel model = new Travelpersonnel();
		if (jsonObject.containsKey("personnelid")) {
			model.setPersonnelid(jsonObject.getLong("personnelid"));
		}
		if (jsonObject.containsKey("travelid")) {
			model.setTravelid(jsonObject.getLong("travelid"));
		}
		if (jsonObject.containsKey("dept")) {
			model.setDept(jsonObject.getString("dept"));
		}
		if (jsonObject.containsKey("personnel")) {
			model.setPersonnel(jsonObject.getString("personnel"));
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

	public static JSONObject toJsonObject(Travelpersonnel model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("personnelid", model.getPersonnelid());
		jsonObject.put("_personnelid_", model.getPersonnelid());
		jsonObject.put("travelid", model.getTravelid());
		if (model.getDept() != null) {
			jsonObject.put("dept", model.getDept());
		}
		if (model.getPersonnel() != null) {
			jsonObject.put("personnel", model.getPersonnel());
		}
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

	public static ObjectNode toObjectNode(Travelpersonnel model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("personnelid", model.getPersonnelid());
		jsonObject.put("_personnelid_", model.getPersonnelid());
		jsonObject.put("travelid", model.getTravelid());
		if (model.getDept() != null) {
			jsonObject.put("dept", model.getDept());
		}
		if (model.getPersonnel() != null) {
			jsonObject.put("personnel", model.getPersonnel());
		}
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

	public static JSONArray listToArray(java.util.List<Travelpersonnel> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Travelpersonnel model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Travelpersonnel> arrayToList(JSONArray array) {
		java.util.List<Travelpersonnel> list = new java.util.ArrayList<Travelpersonnel>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Travelpersonnel model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private TravelpersonnelJsonFactory() {

	}

}