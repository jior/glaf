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
package com.glaf.oa.assessquestion.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.oa.assessquestion.model.Assessquestion;
import com.glaf.core.util.DateUtils;

public class AssessquestionJsonFactory {

	public static Assessquestion jsonToObject(JSONObject jsonObject) {
		Assessquestion model = new Assessquestion();
		if (jsonObject.containsKey("qustionid")) {
			model.setQustionid(jsonObject.getLong("qustionid"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("validdate")) {
			model.setValiddate(jsonObject.getDate("validdate"));
		}
		if (jsonObject.containsKey("rate")) {
			model.setRate(jsonObject.getInteger("rate"));
		}
		if (jsonObject.containsKey("iseffective")) {
			model.setIseffective(jsonObject.getInteger("iseffective"));
		}
		if (jsonObject.containsKey("targetsum")) {
			model.setTargetsum(jsonObject.getDouble("targetsum"));
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

	public static JSONObject toJsonObject(Assessquestion model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("qustionid", model.getQustionid());
		jsonObject.put("_qustionid_", model.getQustionid());
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getValiddate() != null) {
			jsonObject
					.put("validdate", DateUtils.getDate(model.getValiddate()));
			jsonObject.put("validdate_date",
					DateUtils.getDate(model.getValiddate()));
			jsonObject.put("validdate_datetime",
					DateUtils.getDateTime(model.getValiddate()));
		}
		jsonObject.put("rate", model.getRate());
		jsonObject.put("iseffective", model.getIseffective());
		jsonObject.put("targetsum", model.getTargetsum());
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

	public static ObjectNode toObjectNode(Assessquestion model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("qustionid", model.getQustionid());
		jsonObject.put("_qustionid_", model.getQustionid());
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getValiddate() != null) {
			jsonObject
					.put("validdate", DateUtils.getDate(model.getValiddate()));
			jsonObject.put("validdate_date",
					DateUtils.getDate(model.getValiddate()));
			jsonObject.put("validdate_datetime",
					DateUtils.getDateTime(model.getValiddate()));
		}
		jsonObject.put("rate", model.getRate());
		jsonObject.put("iseffective", model.getIseffective());
		jsonObject.put("targetsum", model.getTargetsum());
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

	public static JSONArray listToArray(java.util.List<Assessquestion> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Assessquestion model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Assessquestion> arrayToList(JSONArray array) {
		java.util.List<Assessquestion> list = new java.util.ArrayList<Assessquestion>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Assessquestion model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private AssessquestionJsonFactory() {

	}

}