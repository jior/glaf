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

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.core.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class SchedulerLogJsonFactory {

	public static SchedulerLog jsonToObject(JSONObject jsonObject) {
		SchedulerLog model = new SchedulerLog();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("taskId")) {
			model.setTaskId(jsonObject.getString("taskId"));
		}
		if (jsonObject.containsKey("startDate")) {
			model.setStartDate(jsonObject.getDate("startDate"));
		}
		if (jsonObject.containsKey("endDate")) {
			model.setEndDate(jsonObject.getDate("endDate"));
		}
		if (jsonObject.containsKey("jobRunTime")) {
			model.setJobRunTime(jsonObject.getLong("jobRunTime"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("taskName")) {
			model.setTaskName(jsonObject.getString("taskName"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("exitCode")) {
			model.setExitCode(jsonObject.getString("exitCode"));
		}
		if (jsonObject.containsKey("exitMessage")) {
			model.setExitMessage(jsonObject.getString("exitMessage"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SchedulerLog model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("taskId", model.getTaskId());
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
		jsonObject.put("jobRunTime", model.getJobRunTime());
		if (model.getJobRunTime() > 0) {
			StringBuffer buffer = new StringBuffer();
			long times = model.getJobRunTime();
			if (model.getJobRunTime() >= 3600000) {
				times = model.getJobRunTime() % 3600000;
				buffer.append(model.getJobRunTime() / 3600000).append("小时");
			}
			if (times >= 60000) {
				buffer.append(times / 60000).append("分");
				times = times % 60000;
			}
			if (times >= 1000) {
				buffer.append(times / 1000).append("秒");
				times = times % 1000;
			}
			if (model.getJobRunTime() < 1000) {
				buffer.append(model.getJobRunTime()).append("毫秒");
			}
			jsonObject.put("runTimes", buffer.toString());
		}
		jsonObject.put("status", model.getStatus());
		if (model.getTaskName() != null) {
			jsonObject.put("taskName", model.getTaskName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getExitCode() != null) {
			jsonObject.put("exitCode", model.getExitCode());
		}
		if (model.getExitMessage() != null) {
			jsonObject.put("exitMessage", model.getExitMessage());
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

	public static ObjectNode toObjectNode(SchedulerLog model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("taskId", model.getTaskId());
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
		jsonObject.put("jobRunTime", model.getJobRunTime());
		if (model.getJobRunTime() > 0) {
			StringBuffer buffer = new StringBuffer();
			long times = model.getJobRunTime();
			if (model.getJobRunTime() >= 3600000) {
				times = model.getJobRunTime() % 3600000;
				buffer.append(model.getJobRunTime() / 3600000).append("小时");
			}
			if (times >= 60000) {
				buffer.append(times / 60000).append("分");
				times = times % 60000;
			}
			if (times >= 1000) {
				buffer.append(times / 1000).append("秒");
				times = times % 1000;
			}
			if (model.getJobRunTime() < 1000) {
				buffer.append(model.getJobRunTime()).append("毫秒");
			}
			jsonObject.put("runTimes", buffer.toString());
		}
		jsonObject.put("status", model.getStatus());
		if (model.getTaskName() != null) {
			jsonObject.put("taskName", model.getTaskName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getExitCode() != null) {
			jsonObject.put("exitCode", model.getExitCode());
		}
		if (model.getExitMessage() != null) {
			jsonObject.put("exitMessage", model.getExitMessage());
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

	public static JSONArray listToArray(java.util.List<SchedulerLog> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SchedulerLog model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SchedulerLog> arrayToList(JSONArray array) {
		java.util.List<SchedulerLog> list = new java.util.ArrayList<SchedulerLog>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SchedulerLog model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private SchedulerLogJsonFactory() {

	}

}
