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
package com.glaf.report.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.report.domain.*;

public class ReportTaskJsonFactory {

	public static ReportTask jsonToObject(JSONObject jsonObject) {
		ReportTask model = new ReportTask();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("reportIds")) {
			model.setReportIds(jsonObject.getString("reportIds"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("mailRecipient")) {
			model.setMailRecipient(jsonObject.getString("mailRecipient"));
		}
		if (jsonObject.containsKey("mobileRecipient")) {
			model.setMobileRecipient(jsonObject.getString("mobileRecipient"));
		}
		if (jsonObject.containsKey("sendTitle")) {
			model.setSendTitle(jsonObject.getString("sendTitle"));
		}
		if (jsonObject.containsKey("sendContent")) {
			model.setSendContent(jsonObject.getString("sendContent"));
		}
		if (jsonObject.containsKey("cronExpression")) {
			model.setCronExpression(jsonObject.getString("cronExpression"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("enableFlag")) {
			model.setEnableFlag(jsonObject.getString("enableFlag"));
		}

		return model;
	}

	public static JSONObject toJsonObject(ReportTask model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getReportIds() != null) {
			jsonObject.put("reportIds", model.getReportIds());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		}
		if (model.getMailRecipient() != null) {
			jsonObject.put("mailRecipient", model.getMailRecipient());
		}
		if (model.getMobileRecipient() != null) {
			jsonObject.put("mobileRecipient", model.getMobileRecipient());
		}
		if (model.getSendTitle() != null) {
			jsonObject.put("sendTitle", model.getSendTitle());
		}
		if (model.getSendContent() != null) {
			jsonObject.put("sendContent", model.getSendContent());
		}
		if (model.getCronExpression() != null) {
			jsonObject.put("cronExpression", model.getCronExpression());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getEnableFlag() != null) {
			jsonObject.put("enableFlag", model.getEnableFlag());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(ReportTask model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getReportIds() != null) {
			jsonObject.put("reportIds", model.getReportIds());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		}
		if (model.getMailRecipient() != null) {
			jsonObject.put("mailRecipient", model.getMailRecipient());
		}
		if (model.getMobileRecipient() != null) {
			jsonObject.put("mobileRecipient", model.getMobileRecipient());
		}
		if (model.getSendTitle() != null) {
			jsonObject.put("sendTitle", model.getSendTitle());
		}
		if (model.getSendContent() != null) {
			jsonObject.put("sendContent", model.getSendContent());
		}
		if (model.getCronExpression() != null) {
			jsonObject.put("cronExpression", model.getCronExpression());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getEnableFlag() != null) {
			jsonObject.put("enableFlag", model.getEnableFlag());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<ReportTask> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ReportTask model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ReportTask> arrayToList(JSONArray array) {
		java.util.List<ReportTask> list = new java.util.ArrayList<ReportTask>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ReportTask model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private ReportTaskJsonFactory() {

	}

}
