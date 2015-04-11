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
package com.glaf.chart.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.chart.domain.Chart;

public class ChartJsonFactory {

	public static java.util.List<Chart> arrayToList(JSONArray array) {
		java.util.List<Chart> list = new java.util.ArrayList<Chart>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Chart model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static Chart jsonToObject(JSONObject jsonObject) {
		Chart model = new Chart();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("queryIds")) {
			model.setQueryIds(jsonObject.getString("queryIds"));
		}
		if (jsonObject.containsKey("querySQL")) {
			model.setQuerySQL(jsonObject.getString("querySQL"));
		}
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("chartName")) {
			model.setChartName(jsonObject.getString("chartName"));
		}
		if (jsonObject.containsKey("chartType")) {
			model.setChartType(jsonObject.getString("chartType"));
		}
		if (jsonObject.containsKey("chartFont")) {
			model.setChartFont(jsonObject.getString("chartFont"));
		}
		if (jsonObject.containsKey("chartTitle")) {
			model.setChartTitle(jsonObject.getString("chartTitle"));
		}
		if (jsonObject.containsKey("chartSubTitle")) {
			model.setChartTitle(jsonObject.getString("chartSubTitle"));
		}
		if (jsonObject.containsKey("chartTitleFont")) {
			model.setChartTitleFont(jsonObject.getString("chartTitleFont"));
		}
		if (jsonObject.containsKey("chartTitleFontSize")) {
			model.setChartTitleFontSize(jsonObject
					.getInteger("chartTitleFontSize"));
		}
		if (jsonObject.containsKey("chartSubTitleFontSize")) {
			model.setChartSubTitleFontSize(jsonObject
					.getInteger("chartSubTitleFontSize"));
		}
		if (jsonObject.containsKey("legend")) {
			model.setLegend(jsonObject.getString("legend"));
		}
		if (jsonObject.containsKey("tooltip")) {
			model.setTooltip(jsonObject.getString("tooltip"));
		}
		if (jsonObject.containsKey("mapping")) {
			model.setMapping(jsonObject.getString("mapping"));
		}
		if (jsonObject.containsKey("coordinateX")) {
			model.setCoordinateX(jsonObject.getString("coordinateX"));
		}
		if (jsonObject.containsKey("coordinateY")) {
			model.setCoordinateY(jsonObject.getString("coordinateY"));
		}
		if (jsonObject.containsKey("plotOrientation")) {
			model.setPlotOrientation(jsonObject.getString("plotOrientation"));
		}
		if (jsonObject.containsKey("imageType")) {
			model.setImageType(jsonObject.getString("imageType"));
		}
		if (jsonObject.containsKey("enableFlag")) {
			model.setEnableFlag(jsonObject.getString("enableFlag"));
		}
		if (jsonObject.containsKey("enable3DFlag")) {
			model.setEnable3DFlag(jsonObject.getString("enable3DFlag"));
		}
		if (jsonObject.containsKey("databaseId")) {
			model.setDatabaseId(jsonObject.getLong("databaseId"));
		}
		if (jsonObject.containsKey("maxRowCount")) {
			model.setMaxRowCount(jsonObject.getInteger("maxRowCount"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<Chart> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Chart model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(Chart model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getQueryIds() != null) {
			jsonObject.put("queryIds", model.getQueryIds());
		}
		if (model.getQuerySQL() != null) {
			jsonObject.put("querySQL", model.getQuerySQL());
		}
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		}
		if (model.getChartName() != null) {
			jsonObject.put("chartName", model.getChartName());
		}
		if (model.getChartType() != null) {
			jsonObject.put("chartType", model.getChartType());
		}
		if (model.getChartFont() != null) {
			jsonObject.put("chartFont", model.getChartFont());
		}
		if (model.getChartTitle() != null) {
			jsonObject.put("chartTitle", model.getChartTitle());
		}
		if (model.getChartSubTitle() != null) {
			jsonObject.put("chartSubTitle", model.getChartSubTitle());
		}
		if (model.getChartTitleFont() != null) {
			jsonObject.put("chartTitleFont", model.getChartTitleFont());
		}
		if (model.getChartTitleFontSize() != null) {
			jsonObject.put("chartTitleFontSize", model.getChartTitleFontSize());
		}
		if (model.getChartSubTitleFontSize() != null) {
			jsonObject.put("chartSubTitleFontSize",
					model.getChartSubTitleFontSize());
		}
		if (model.getLegend() != null) {
			jsonObject.put("legend", model.getLegend());
		}
		if (model.getTooltip() != null) {
			jsonObject.put("tooltip", model.getTooltip());
		}
		if (model.getMapping() != null) {
			jsonObject.put("mapping", model.getMapping());
		}
		if (model.getCoordinateX() != null) {
			jsonObject.put("coordinateX", model.getCoordinateX());
		}
		if (model.getCoordinateY() != null) {
			jsonObject.put("coordinateY", model.getCoordinateY());
		}
		if (model.getPlotOrientation() != null) {
			jsonObject.put("plotOrientation", model.getPlotOrientation());
		}
		if (model.getImageType() != null) {
			jsonObject.put("imageType", model.getImageType());
		}
		if (model.getEnableFlag() != null) {
			jsonObject.put("enableFlag", model.getEnableFlag());
		}
		if (model.getEnable3DFlag() != null) {
			jsonObject.put("enable3DFlag", model.getEnable3DFlag());
		}
		jsonObject.put("databaseId", model.getDatabaseId());
		jsonObject.put("maxRowCount", model.getMaxRowCount());
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

	public static ObjectNode toObjectNode(Chart model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getQueryIds() != null) {
			jsonObject.put("queryIds", model.getQueryIds());
		}
		if (model.getQuerySQL() != null) {
			jsonObject.put("querySQL", model.getQuerySQL());
		}
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		}
		if (model.getChartName() != null) {
			jsonObject.put("chartName", model.getChartName());
		}
		if (model.getChartType() != null) {
			jsonObject.put("chartType", model.getChartType());
		}
		if (model.getChartFont() != null) {
			jsonObject.put("chartFont", model.getChartFont());
		}
		if (model.getChartTitle() != null) {
			jsonObject.put("chartTitle", model.getChartTitle());
		}
		if (model.getChartSubTitle() != null) {
			jsonObject.put("chartSubTitle", model.getChartSubTitle());
		}
		if (model.getChartTitleFont() != null) {
			jsonObject.put("chartTitleFont", model.getChartTitleFont());
		}
		if (model.getChartTitleFontSize() != null) {
			jsonObject.put("chartTitleFontSize", model.getChartTitleFontSize());
		}
		if (model.getChartSubTitleFontSize() != null) {
			jsonObject.put("chartSubTitleFontSize",
					model.getChartSubTitleFontSize());
		}
		if (model.getLegend() != null) {
			jsonObject.put("legend", model.getLegend());
		}
		if (model.getTooltip() != null) {
			jsonObject.put("tooltip", model.getTooltip());
		}
		if (model.getMapping() != null) {
			jsonObject.put("mapping", model.getMapping());
		}
		if (model.getCoordinateX() != null) {
			jsonObject.put("coordinateX", model.getCoordinateX());
		}
		if (model.getCoordinateY() != null) {
			jsonObject.put("coordinateY", model.getCoordinateY());
		}
		if (model.getPlotOrientation() != null) {
			jsonObject.put("plotOrientation", model.getPlotOrientation());
		}
		if (model.getImageType() != null) {
			jsonObject.put("imageType", model.getImageType());
		}
		if (model.getEnableFlag() != null) {
			jsonObject.put("enableFlag", model.getEnableFlag());
		}
		if (model.getEnable3DFlag() != null) {
			jsonObject.put("enable3DFlag", model.getEnable3DFlag());
		}
		jsonObject.put("databaseId", model.getDatabaseId());
		jsonObject.put("maxRowCount", model.getMaxRowCount());
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

	private ChartJsonFactory() {

	}

}
