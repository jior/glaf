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

package com.glaf.survey.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.survey.domain.*;

public class SurveyJsonFactory {

	public static java.util.List<Survey> arrayToList(JSONArray array) {
		java.util.List<Survey> list = new java.util.ArrayList<Survey>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Survey model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static Survey jsonToObject(JSONObject jsonObject) {
		Survey model = new Survey();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}

		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("keywords")) {
			model.setKeywords(jsonObject.getString("keywords"));
		}
		if (jsonObject.containsKey("icon")) {
			model.setIcon(jsonObject.getString("icon"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("signFlag")) {
			model.setSignFlag(jsonObject.getInteger("signFlag"));
		}
		if (jsonObject.containsKey("showIconFlag")) {
			model.setShowIconFlag(jsonObject.getInteger("showIconFlag"));
		}
		if (jsonObject.containsKey("multiFlag")) {
			model.setMultiFlag(jsonObject.getInteger("multiFlag"));
		}
		if (jsonObject.containsKey("limitFlag")) {
			model.setLimitFlag(jsonObject.getInteger("limitFlag"));
		}
		if (jsonObject.containsKey("limitTimeInterval")) {
			model.setLimitTimeInterval(jsonObject
					.getInteger("limitTimeInterval"));
		}
		if (jsonObject.containsKey("resultFlag")) {
			model.setResultFlag(jsonObject.getInteger("resultFlag"));
		}
		if (jsonObject.containsKey("startDate")) {
			model.setStartDate(jsonObject.getDate("startDate"));
		}
		if (jsonObject.containsKey("endDate")) {
			model.setEndDate(jsonObject.getDate("endDate"));
		}
		if (jsonObject.containsKey("relationIds")) {
			model.setRelationIds(jsonObject.getString("relationIds"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<Survey> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Survey model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(Survey model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());

		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getKeywords() != null) {
			jsonObject.put("keywords", model.getKeywords());
		}
		if (model.getIcon() != null) {
			jsonObject.put("icon", model.getIcon());
		}
		jsonObject.put("sort", model.getSort());
		jsonObject.put("status", model.getStatus());
		jsonObject.put("signFlag", model.getShowIconFlag());
		jsonObject.put("showIconFlag", model.getSignFlag());
		jsonObject.put("multiFlag", model.getMultiFlag());
		jsonObject.put("limitFlag", model.getLimitFlag());
		jsonObject.put("limitTimeInterval", model.getLimitTimeInterval());
		jsonObject.put("resultFlag", model.getResultFlag());

		if (model.getStartDate() != null) {
			jsonObject
					.put("startDate", DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_date",
					DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(model.getStartDate()));
		}
		if (model.getEndDate() != null) {
			jsonObject.put("endDate", DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_date",
					DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_datetime",
					DateUtils.getDateTime(model.getEndDate()));
		}
		if (model.getRelationIds() != null) {
			jsonObject.put("relationIds", model.getRelationIds());
		}
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
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Survey model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());

		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getKeywords() != null) {
			jsonObject.put("keywords", model.getKeywords());
		}
		if (model.getIcon() != null) {
			jsonObject.put("icon", model.getIcon());
		}
		jsonObject.put("sort", model.getSort());
		jsonObject.put("status", model.getStatus());
		jsonObject.put("signFlag", model.getSignFlag());
		jsonObject.put("showIconFlag", model.getSignFlag());
		jsonObject.put("multiFlag", model.getMultiFlag());
		jsonObject.put("limitFlag", model.getLimitFlag());
		jsonObject.put("limitTimeInterval", model.getLimitTimeInterval());
		jsonObject.put("resultFlag", model.getResultFlag());

		if (model.getStartDate() != null) {
			jsonObject
					.put("startDate", DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_date",
					DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(model.getStartDate()));
		}
		if (model.getEndDate() != null) {
			jsonObject.put("endDate", DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_date",
					DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_datetime",
					DateUtils.getDateTime(model.getEndDate()));
		}
		if (model.getRelationIds() != null) {
			jsonObject.put("relationIds", model.getRelationIds());
		}
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
		return jsonObject;
	}

	private SurveyJsonFactory() {

	}

}
