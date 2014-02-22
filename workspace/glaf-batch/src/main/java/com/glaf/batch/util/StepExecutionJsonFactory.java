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
import com.glaf.batch.domain.StepExecution;
import com.glaf.core.util.DateUtils;

public class StepExecutionJsonFactory {

	public static java.util.List<StepExecution> arrayToList(JSONArray array) {
		java.util.List<StepExecution> list = new java.util.ArrayList<StepExecution>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			StepExecution model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static StepExecution jsonToObject(JSONObject jsonObject) {
		StepExecution model = new StepExecution();
		if (jsonObject.containsKey("stepExecutionId")) {
			model.setStepExecutionId(jsonObject.getLong("stepExecutionId"));
		}
		if (jsonObject.containsKey("version")) {
			model.setVersion(jsonObject.getInteger("version"));
		}
		if (jsonObject.containsKey("stepKey")) {
			model.setStepKey(jsonObject.getString("stepKey"));
		}
		if (jsonObject.containsKey("stepName")) {
			model.setStepName(jsonObject.getString("stepName"));
		}
		if (jsonObject.containsKey("jobStepKey")) {
			model.setJobStepKey(jsonObject.getString("jobStepKey"));
		}
		if (jsonObject.containsKey("jobClass")) {
			model.setJobClass(jsonObject.getString("jobClass"));
		}
		if (jsonObject.containsKey("jobInstanceId")) {
			model.setJobInstanceId(jsonObject.getLong("jobInstanceId"));
		}
		if (jsonObject.containsKey("jobExecutionId")) {
			model.setJobExecutionId(jsonObject.getLong("jobExecutionId"));
		}
		if (jsonObject.containsKey("listno")) {
			model.setListno(jsonObject.getInteger("listno"));
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
		if (jsonObject.containsKey("commitCount")) {
			model.setCommitCount(jsonObject.getInteger("commitCount"));
		}
		if (jsonObject.containsKey("readCount")) {
			model.setReadCount(jsonObject.getInteger("readCount"));
		}
		if (jsonObject.containsKey("filterCount")) {
			model.setFilterCount(jsonObject.getInteger("filterCount"));
		}
		if (jsonObject.containsKey("writeCount")) {
			model.setWriteCount(jsonObject.getInteger("writeCount"));
		}
		if (jsonObject.containsKey("readSkipCount")) {
			model.setReadSkipCount(jsonObject.getInteger("readSkipCount"));
		}
		if (jsonObject.containsKey("writeSkipCount")) {
			model.setWriteSkipCount(jsonObject.getInteger("writeSkipCount"));
		}
		if (jsonObject.containsKey("processSkipCount")) {
			model.setProcessSkipCount(jsonObject.getInteger("processSkipCount"));
		}
		if (jsonObject.containsKey("rollbackCount")) {
			model.setRollbackCount(jsonObject.getInteger("rollbackCount"));
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

	public static JSONArray listToArray(java.util.List<StepExecution> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (StepExecution model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(StepExecution model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("stepExecutionId", model.getStepExecutionId());
		jsonObject.put("_stepExecutionId_", model.getStepExecutionId());
		jsonObject.put("version", model.getVersion());
		if (model.getStepKey() != null) {
			jsonObject.put("stepKey", model.getStepKey());
		}
		if (model.getStepName() != null) {
			jsonObject.put("stepName", model.getStepName());
		}
		if (model.getJobStepKey() != null) {
			jsonObject.put("jobStepKey", model.getJobStepKey());
		}
		if (model.getJobClass() != null) {
			jsonObject.put("jobClass", model.getJobClass());
		}
		jsonObject.put("jobInstanceId", model.getJobInstanceId());
		jsonObject.put("jobExecutionId", model.getJobExecutionId());
		jsonObject.put("listno", model.getListno());
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
		jsonObject.put("commitCount", model.getCommitCount());
		jsonObject.put("readCount", model.getReadCount());
		jsonObject.put("filterCount", model.getFilterCount());
		jsonObject.put("writeCount", model.getWriteCount());
		jsonObject.put("readSkipCount", model.getReadSkipCount());
		jsonObject.put("writeSkipCount", model.getWriteSkipCount());
		jsonObject.put("processSkipCount", model.getProcessSkipCount());
		jsonObject.put("rollbackCount", model.getRollbackCount());
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

	public static ObjectNode toObjectNode(StepExecution model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("stepExecutionId", model.getStepExecutionId());
		jsonObject.put("_stepExecutionId_", model.getStepExecutionId());
		jsonObject.put("version", model.getVersion());
		if (model.getStepKey() != null) {
			jsonObject.put("stepKey", model.getStepKey());
		}
		if (model.getStepName() != null) {
			jsonObject.put("stepName", model.getStepName());
		}
		if (model.getJobStepKey() != null) {
			jsonObject.put("jobStepKey", model.getJobStepKey());
		}
		if (model.getJobClass() != null) {
			jsonObject.put("jobClass", model.getJobClass());
		}
		jsonObject.put("jobInstanceId", model.getJobInstanceId());
		jsonObject.put("jobExecutionId", model.getJobExecutionId());
		jsonObject.put("listno", model.getListno());
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
		jsonObject.put("commitCount", model.getCommitCount());
		jsonObject.put("readCount", model.getReadCount());
		jsonObject.put("filterCount", model.getFilterCount());
		jsonObject.put("writeCount", model.getWriteCount());
		jsonObject.put("readSkipCount", model.getReadSkipCount());
		jsonObject.put("writeSkipCount", model.getWriteSkipCount());
		jsonObject.put("processSkipCount", model.getProcessSkipCount());
		jsonObject.put("rollbackCount", model.getRollbackCount());
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

	private StepExecutionJsonFactory() {

	}

}
