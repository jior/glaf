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

public class ReportJsonFactory {

	public static Report jsonToObject(JSONObject jsonObject) {
		Report model = new Report();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("queryIds")) {
			model.setQueryIds(jsonObject.getString("queryIds"));
		}
		if (jsonObject.containsKey("chartIds")) {
			model.setChartIds(jsonObject.getString("chartIds"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("reportName")) {
			model.setReportName(jsonObject.getString("reportName"));
		}
		if (jsonObject.containsKey("reportFormat")) {
			model.setReportFormat(jsonObject.getString("reportFormat"));
		}
		if (jsonObject.containsKey("reportTemplate")) {
			model.setReportTemplate(jsonObject.getString("reportTemplate"));
		}
		if (jsonObject.containsKey("reportTitleDate")) {
			model.setReportTitleDate(jsonObject.getString("reportTitleDate"));
		}
		if (jsonObject.containsKey("reportMonth")) {
			model.setReportMonth(jsonObject.getString("reportMonth"));
		}
		if (jsonObject.containsKey("reportDateYYYYMMDD")) {
			model.setReportDateYYYYMMDD(jsonObject
					.getString("reportDateYYYYMMDD"));
		}
		if (jsonObject.containsKey("jsonParameter")) {
			model.setJsonParameter(jsonObject.getString("jsonParameter"));
		}
		if (jsonObject.containsKey("textTitle")) {
			model.setTextTitle(jsonObject.getString("textTitle"));
		}
		if (jsonObject.containsKey("textContent")) {
			model.setTextContent(jsonObject.getString("textContent"));
		}
		if (jsonObject.containsKey("mailRecipient")) {
			model.setMailRecipient(jsonObject.getString("mailRecipient"));
		}
		if (jsonObject.containsKey("mobileRecipient")) {
			model.setMobileRecipient(jsonObject.getString("mobileRecipient"));
		}
		if (jsonObject.containsKey("cronExpression")) {
			model.setCronExpression(jsonObject.getString("cronExpression"));
		}
		if (jsonObject.containsKey("enableFlag")) {
			model.setEnableFlag(jsonObject.getString("enableFlag"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}

		return model;
	}

	public static JSONObject toJsonObject(Report model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getQueryIds() != null) {
			jsonObject.put("queryIds", model.getQueryIds());
		}
		if (model.getChartIds() != null) {
			jsonObject.put("chartIds", model.getChartIds());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getReportName() != null) {
			jsonObject.put("reportName", model.getReportName());
		}
		if (model.getReportFormat() != null) {
			jsonObject.put("reportFormat", model.getReportFormat());
		}
		if (model.getReportTemplate() != null) {
			jsonObject.put("reportTemplate", model.getReportTemplate());
		}
		if (model.getReportTitleDate() != null) {
			jsonObject.put("reportTitleDate", model.getReportTitleDate());
		}
		if (model.getReportMonth() != null) {
			jsonObject.put("reportMonth", model.getReportMonth());
		}
		if (model.getReportDateYYYYMMDD() != null) {
			jsonObject.put("reportDateYYYYMMDD", model.getReportDateYYYYMMDD());
		}
		if (model.getJsonParameter() != null) {
			jsonObject.put("jsonParameter", model.getJsonParameter());
		}
		if (model.getTextTitle() != null) {
			jsonObject.put("textTitle", model.getTextTitle());
		}
		if (model.getTextContent() != null) {
			jsonObject.put("textContent", model.getTextContent());
		}
		if (model.getMailRecipient() != null) {
			jsonObject.put("mailRecipient", model.getMailRecipient());
		}
		if (model.getMobileRecipient() != null) {
			jsonObject.put("mobileRecipient", model.getMobileRecipient());
		}
		if (model.getCronExpression() != null) {
			jsonObject.put("cronExpression", model.getCronExpression());
		}
		if (model.getEnableFlag() != null) {
			jsonObject.put("enableFlag", model.getEnableFlag());
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
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Report model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getQueryIds() != null) {
			jsonObject.put("queryIds", model.getQueryIds());
		}
		if (model.getChartIds() != null) {
			jsonObject.put("chartIds", model.getChartIds());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getReportName() != null) {
			jsonObject.put("reportName", model.getReportName());
		}
		if (model.getReportFormat() != null) {
			jsonObject.put("reportFormat", model.getReportFormat());
		}
		if (model.getReportTemplate() != null) {
			jsonObject.put("reportTemplate", model.getReportTemplate());
		}
		if (model.getReportTitleDate() != null) {
			jsonObject.put("reportTitleDate", model.getReportTitleDate());
		}
		if (model.getReportMonth() != null) {
			jsonObject.put("reportMonth", model.getReportMonth());
		}
		if (model.getReportDateYYYYMMDD() != null) {
			jsonObject.put("reportDateYYYYMMDD", model.getReportDateYYYYMMDD());
		}
		if (model.getJsonParameter() != null) {
			jsonObject.put("jsonParameter", model.getJsonParameter());
		}
		if (model.getTextTitle() != null) {
			jsonObject.put("textTitle", model.getTextTitle());
		}
		if (model.getTextContent() != null) {
			jsonObject.put("textContent", model.getTextContent());
		}
		if (model.getMailRecipient() != null) {
			jsonObject.put("mailRecipient", model.getMailRecipient());
		}
		if (model.getMobileRecipient() != null) {
			jsonObject.put("mobileRecipient", model.getMobileRecipient());
		}
		if (model.getCronExpression() != null) {
			jsonObject.put("cronExpression", model.getCronExpression());
		}
		if (model.getEnableFlag() != null) {
			jsonObject.put("enableFlag", model.getEnableFlag());
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
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<Report> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Report model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Report> arrayToList(JSONArray array) {
		java.util.List<Report> list = new java.util.ArrayList<Report>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Report model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private ReportJsonFactory() {

	}

}
