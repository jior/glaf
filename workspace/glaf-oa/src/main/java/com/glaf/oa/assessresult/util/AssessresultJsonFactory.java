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
package com.glaf.oa.assessresult.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.oa.assessresult.model.Assessresult;
import com.glaf.core.util.DateUtils;

public class AssessresultJsonFactory {

	public static Assessresult jsonToObject(JSONObject jsonObject) {
		Assessresult model = new Assessresult();
		if (jsonObject.containsKey("resultid")) {
			model.setResultid(jsonObject.getLong("resultid"));
		}
		if (jsonObject.containsKey("qustionid")) {
			model.setQustionid(jsonObject.getLong("qustionid"));
		}
		if (jsonObject.containsKey("area")) {
			model.setArea(jsonObject.getString("area"));
		}
		if (jsonObject.containsKey("company")) {
			model.setCompany(jsonObject.getString("company"));
		}
		if (jsonObject.containsKey("dept")) {
			model.setDept(jsonObject.getString("dept"));
		}
		if (jsonObject.containsKey("post")) {
			model.setPost(jsonObject.getString("post"));
		}
		if (jsonObject.containsKey("year")) {
			model.setYear(jsonObject.getInteger("year"));
		}
		if (jsonObject.containsKey("season")) {
			model.setSeason(jsonObject.getInteger("season"));
		}
		if (jsonObject.containsKey("month")) {
			model.setMonth(jsonObject.getInteger("month"));
		}
		if (jsonObject.containsKey("beevaluation")) {
			model.setBeevaluation(jsonObject.getString("beevaluation"));
		}
		if (jsonObject.containsKey("evaluation")) {
			model.setEvaluation(jsonObject.getString("evaluation"));
		}
		if (jsonObject.containsKey("comment")) {
			model.setComment(jsonObject.getString("comment"));
		}
		if (jsonObject.containsKey("rewardsum")) {
			model.setRewardsum(jsonObject.getDouble("rewardsum"));
		}
		if (jsonObject.containsKey("score")) {
			model.setScore(jsonObject.getDouble("score"));
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
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}

		return model;
	}

	public static JSONObject toJsonObject(Assessresult model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("resultid", model.getResultid());
		jsonObject.put("_resultid_", model.getResultid());
		jsonObject.put("qustionid", model.getQustionid());
		jsonObject.put("area", model.getArea());
		if (model.getCompany() != null) {
			jsonObject.put("company", model.getCompany());
		}
		if (model.getDept() != null) {
			jsonObject.put("dept", model.getDept());
		}
		if (model.getPost() != null) {
			jsonObject.put("post", model.getPost());
		}
		jsonObject.put("year", model.getYear());
		jsonObject.put("season", model.getSeason());
		jsonObject.put("month", model.getMonth());
		if (model.getBeevaluation() != null) {
			jsonObject.put("beevaluation", model.getBeevaluation());
		}
		if (model.getEvaluation() != null) {
			jsonObject.put("evaluation", model.getEvaluation());
		}
		if (model.getComment() != null) {
			jsonObject.put("comment", model.getComment());
		}
		jsonObject.put("rewardsum", model.getRewardsum());
		jsonObject.put("score", model.getScore());
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
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}

		return jsonObject;
	}

	public static ObjectNode toObjectNode(Assessresult model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("resultid", model.getResultid());
		jsonObject.put("_resultid_", model.getResultid());
		jsonObject.put("qustionid", model.getQustionid());
		jsonObject.put("area", model.getArea());
		if (model.getCompany() != null) {
			jsonObject.put("company", model.getCompany());
		}
		if (model.getDept() != null) {
			jsonObject.put("dept", model.getDept());
		}
		if (model.getPost() != null) {
			jsonObject.put("post", model.getPost());
		}
		jsonObject.put("year", model.getYear());
		jsonObject.put("season", model.getSeason());
		jsonObject.put("month", model.getMonth());
		if (model.getBeevaluation() != null) {
			jsonObject.put("beevaluation", model.getBeevaluation());
		}
		if (model.getEvaluation() != null) {
			jsonObject.put("evaluation", model.getEvaluation());
		}
		if (model.getComment() != null) {
			jsonObject.put("comment", model.getComment());
		}
		jsonObject.put("rewardsum", model.getRewardsum());
		jsonObject.put("score", model.getScore());
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
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<Assessresult> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Assessresult model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Assessresult> arrayToList(JSONArray array) {
		java.util.List<Assessresult> list = new java.util.ArrayList<Assessresult>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Assessresult model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private AssessresultJsonFactory() {

	}

}