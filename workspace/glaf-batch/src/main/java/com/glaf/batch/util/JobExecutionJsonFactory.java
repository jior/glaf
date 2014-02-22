/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.batch.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.batch.domain.JobExecution;
import com.glaf.core.util.DateUtils;

public class JobExecutionJsonFactory {

	public static java.util.List<JobExecution> arrayToList(JSONArray array) {
		java.util.List<JobExecution> list = new java.util.ArrayList<JobExecution>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			JobExecution model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static JobExecution jsonToObject(JSONObject jsonObject) {
		JobExecution model = new JobExecution();
		if (jsonObject.containsKey("jobExecutionId")) {
			model.setJobExecutionId(jsonObject.getLong("jobExecutionId"));
		}
		if (jsonObject.containsKey("version")) {
			model.setVersion(jsonObject.getInteger("version"));
		}
		if (jsonObject.containsKey("jobInstanceId")) {
			model.setJobInstanceId(jsonObject.getLong("jobInstanceId"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("startTime")) {
			model.setStartTime(jsonObject.getDate("startTime"));
		}
		if (jsonObject.containsKey("endTime")) {
			model.setEndTime(jsonObject.getDate("endTime"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getString("status"));
		}
		if (jsonObject.containsKey("exitCode")) {
			model.setExitCode(jsonObject.getString("exitCode"));
		}
		if (jsonObject.containsKey("exitMessage")) {
			model.setExitMessage(jsonObject.getString("exitMessage"));
		}
		if (jsonObject.containsKey("lastUpdated")) {
			model.setLastUpdated(jsonObject.getDate("lastUpdated"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<JobExecution> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (JobExecution model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(JobExecution model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("jobExecutionId", model.getJobExecutionId());
		jsonObject.put("_jobExecutionId_", model.getJobExecutionId());
		jsonObject.put("version", model.getVersion());
		jsonObject.put("jobInstanceId", model.getJobInstanceId());
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getStartTime() != null) {
			jsonObject
					.put("startTime", DateUtils.getDate(model.getStartTime()));
			jsonObject.put("startTime_date",
					DateUtils.getDate(model.getStartTime()));
			jsonObject.put("startTime_datetime",
					DateUtils.getDateTime(model.getStartTime()));
		}
		if (model.getEndTime() != null) {
			jsonObject.put("endTime", DateUtils.getDate(model.getEndTime()));
			jsonObject.put("endTime_date",
					DateUtils.getDate(model.getEndTime()));
			jsonObject.put("endTime_datetime",
					DateUtils.getDateTime(model.getEndTime()));
		}
		if (model.getStatus() != null) {
			jsonObject.put("status", model.getStatus());
		}
		if (model.getExitCode() != null) {
			jsonObject.put("exitCode", model.getExitCode());
		}
		if (model.getExitMessage() != null) {
			jsonObject.put("exitMessage", model.getExitMessage());
		}
		if (model.getLastUpdated() != null) {
			jsonObject.put("lastUpdated",
					DateUtils.getDate(model.getLastUpdated()));
			jsonObject.put("lastUpdated_date",
					DateUtils.getDate(model.getLastUpdated()));
			jsonObject.put("lastUpdated_datetime",
					DateUtils.getDateTime(model.getLastUpdated()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(JobExecution model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("jobExecutionId", model.getJobExecutionId());
		jsonObject.put("_jobExecutionId_", model.getJobExecutionId());
		jsonObject.put("version", model.getVersion());
		jsonObject.put("jobInstanceId", model.getJobInstanceId());
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getStartTime() != null) {
			jsonObject
					.put("startTime", DateUtils.getDate(model.getStartTime()));
			jsonObject.put("startTime_date",
					DateUtils.getDate(model.getStartTime()));
			jsonObject.put("startTime_datetime",
					DateUtils.getDateTime(model.getStartTime()));
		}
		if (model.getEndTime() != null) {
			jsonObject.put("endTime", DateUtils.getDate(model.getEndTime()));
			jsonObject.put("endTime_date",
					DateUtils.getDate(model.getEndTime()));
			jsonObject.put("endTime_datetime",
					DateUtils.getDateTime(model.getEndTime()));
		}
		if (model.getStatus() != null) {
			jsonObject.put("status", model.getStatus());
		}
		if (model.getExitCode() != null) {
			jsonObject.put("exitCode", model.getExitCode());
		}
		if (model.getExitMessage() != null) {
			jsonObject.put("exitMessage", model.getExitMessage());
		}
		if (model.getLastUpdated() != null) {
			jsonObject.put("lastUpdated",
					DateUtils.getDate(model.getLastUpdated()));
			jsonObject.put("lastUpdated_date",
					DateUtils.getDate(model.getLastUpdated()));
			jsonObject.put("lastUpdated_datetime",
					DateUtils.getDateTime(model.getLastUpdated()));
		}
		return jsonObject;
	}

	private JobExecutionJsonFactory() {

	}

}
