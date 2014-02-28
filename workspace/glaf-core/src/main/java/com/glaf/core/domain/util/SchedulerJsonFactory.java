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

package com.glaf.core.domain.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.Scheduler;
import com.glaf.core.domain.SchedulerEntity;
import com.glaf.core.util.DateUtils;

public class SchedulerJsonFactory {

	public static java.util.List<Scheduler> arrayToList(JSONArray array) {
		java.util.List<Scheduler> list = new java.util.ArrayList<Scheduler>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Scheduler model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static Scheduler jsonToObject(JSONObject jsonObject) {
		SchedulerEntity model = new SchedulerEntity();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("taskName")) {
			model.setTaskName(jsonObject.getString("taskName"));
		}
		if (jsonObject.containsKey("taskType")) {
			model.setTaskType(jsonObject.getString("taskType"));
		}
		if (jsonObject.containsKey("jobClass")) {
			model.setJobClass(jsonObject.getString("jobClass"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("startDate")) {
			model.setStartDate(jsonObject.getDate("startDate"));
		}
		if (jsonObject.containsKey("endDate")) {
			model.setEndDate(jsonObject.getDate("endDate"));
		}
		if (jsonObject.containsKey("threadSize")) {
			model.setThreadSize(jsonObject.getInteger("threadSize"));
		}
		if (jsonObject.containsKey("repeatCount")) {
			model.setRepeatCount(jsonObject.getInteger("repeatCount"));
		}
		if (jsonObject.containsKey("repeatInterval")) {
			model.setRepeatInterval(jsonObject.getInteger("repeatInterval"));
		}
		if (jsonObject.containsKey("startDelay")) {
			model.setStartDelay(jsonObject.getInteger("startDelay"));
		}
		if (jsonObject.containsKey("priority")) {
			model.setPriority(jsonObject.getInteger("priority"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("startup")) {
			model.setStartup(jsonObject.getInteger("startup"));
		}
		if (jsonObject.containsKey("autoStartup")) {
			model.setAutoStartup(jsonObject.getInteger("autoStartup"));
		}
		if (jsonObject.containsKey("expression")) {
			model.setExpression(jsonObject.getString("expression"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("attribute")) {
			model.setAttribute(jsonObject.getString("attribute"));
		}
		if (jsonObject.containsKey("intervalType")) {
			model.setIntervalType(jsonObject.getString("intervalType"));
		}
		if (jsonObject.containsKey("intervalValue")) {
			model.setIntervalValue(jsonObject.getString("intervalValue"));
		}
		if (jsonObject.containsKey("intervalTime")) {
			model.setIntervalTime(jsonObject.getString("intervalTime"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<Scheduler> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Scheduler model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(Scheduler model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTaskName() != null) {
			jsonObject.put("taskName", model.getTaskName());
		}
		if (model.getTaskType() != null) {
			jsonObject.put("taskType", model.getTaskType());
		}
		if (model.getJobClass() != null) {
			jsonObject.put("jobClass", model.getJobClass());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
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
		jsonObject.put("threadSize", model.getThreadSize());
		jsonObject.put("repeatCount", model.getRepeatCount());
		jsonObject.put("repeatInterval", model.getRepeatInterval());
		jsonObject.put("startDelay", model.getStartDelay());
		jsonObject.put("priority", model.getPriority());
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("startup", model.getStartup());
		jsonObject.put("autoStartup", model.getAutoStartup());
		if (model.getExpression() != null) {
			jsonObject.put("expression", model.getExpression());
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
		if (model.getAttribute() != null) {
			jsonObject.put("attribute", model.getAttribute());
		}

		if (model.getIntervalType() != null) {
			jsonObject.put("intervalType", model.getIntervalType());
		}
		if (model.getIntervalValue() != null) {
			jsonObject.put("intervalValue", model.getIntervalValue());
		}
		if (model.getIntervalTime() != null) {
			jsonObject.put("intervalTime", model.getIntervalTime());
		}

		return jsonObject;
	}

	public static ObjectNode toObjectNode(Scheduler model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTaskName() != null) {
			jsonObject.put("taskName", model.getTaskName());
		}
		if (model.getTaskType() != null) {
			jsonObject.put("taskType", model.getTaskType());
		}
		if (model.getJobClass() != null) {
			jsonObject.put("jobClass", model.getJobClass());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
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
		jsonObject.put("threadSize", model.getThreadSize());
		jsonObject.put("repeatCount", model.getRepeatCount());
		jsonObject.put("repeatInterval", model.getRepeatInterval());
		jsonObject.put("startDelay", model.getStartDelay());
		jsonObject.put("priority", model.getPriority());
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("startup", model.getStartup());
		jsonObject.put("autoStartup", model.getAutoStartup());
		if (model.getExpression() != null) {
			jsonObject.put("expression", model.getExpression());
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
		if (model.getAttribute() != null) {
			jsonObject.put("attribute", model.getAttribute());
		}

		if (model.getIntervalType() != null) {
			jsonObject.put("intervalType", model.getIntervalType());
		}
		if (model.getIntervalValue() != null) {
			jsonObject.put("intervalValue", model.getIntervalValue());
		}
		if (model.getIntervalTime() != null) {
			jsonObject.put("intervalTime", model.getIntervalTime());
		}
		return jsonObject;
	}

	private SchedulerJsonFactory() {

	}

}
