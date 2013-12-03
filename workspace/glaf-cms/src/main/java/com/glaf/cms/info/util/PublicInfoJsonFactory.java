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

package com.glaf.cms.info.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.cms.info.model.*;

public class PublicInfoJsonFactory {

	public static java.util.List<PublicInfo> arrayToList(JSONArray array) {
		java.util.List<PublicInfo> list = new java.util.ArrayList<PublicInfo>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			PublicInfo model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static PublicInfo jsonToObject(JSONObject jsonObject) {
		PublicInfo model = new PublicInfo();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("summary")) {
			model.setSummary(jsonObject.getString("summary"));
		}
		if (jsonObject.containsKey("wfStatus")) {
			model.setWfStatus(jsonObject.getInteger("wfStatus"));
		}
		if (jsonObject.containsKey("originalFlag")) {
			model.setOriginalFlag(jsonObject.getInteger("originalFlag"));
		}
		if (jsonObject.containsKey("startDate")) {
			model.setStartDate(jsonObject.getDate("startDate"));
		}
		if (jsonObject.containsKey("serviceKey")) {
			model.setServiceKey(jsonObject.getString("serviceKey"));
		}
		if (jsonObject.containsKey("tag")) {
			model.setTag(jsonObject.getString("tag"));
		}
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("link")) {
			model.setLink(jsonObject.getString("link"));
		}
		if (jsonObject.containsKey("processName")) {
			model.setProcessName(jsonObject.getString("processName"));
		}
		if (jsonObject.containsKey("sortNo")) {
			model.setSortNo(jsonObject.getInteger("sortNo"));
		}
		if (jsonObject.containsKey("endDate")) {
			model.setEndDate(jsonObject.getDate("endDate"));
		}
		if (jsonObject.containsKey("refererUrl")) {
			model.setRefererUrl(jsonObject.getString("refererUrl"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("publishFlag")) {
			model.setPublishFlag(jsonObject.getInteger("publishFlag"));
		}
		if (jsonObject.containsKey("author")) {
			model.setAuthor(jsonObject.getString("author"));
		}
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
		}
		if (jsonObject.containsKey("processInstanceId")) {
			model.setProcessInstanceId(jsonObject.getLong("processInstanceId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}
		if (jsonObject.containsKey("commentFlag")) {
			model.setCommentFlag(jsonObject.getInteger("commentFlag"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("fallbackFlag")) {
			model.setFallbackFlag(jsonObject.getString("fallbackFlag"));
		}
		if (jsonObject.containsKey("keywords")) {
			model.setKeywords(jsonObject.getString("keywords"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("digg")) {
			model.setDigg(jsonObject.getInteger("digg"));
		}
		if (jsonObject.containsKey("bury")) {
			model.setBury(jsonObject.getInteger("bury"));
		}
		if (jsonObject.containsKey("commentCount")) {
			model.setCommentCount(jsonObject.getInteger("commentCount"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("unitName")) {
			model.setUnitName(jsonObject.getString("unitName"));
		}
		if (jsonObject.containsKey("viewCount")) {
			model.setViewCount(jsonObject.getInteger("viewCount"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<PublicInfo> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (PublicInfo model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(PublicInfo model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("wfStatus", model.getWfStatus());
		jsonObject.put("originalFlag", model.getOriginalFlag());
		if (model.getStartDate() != null) {
			jsonObject
					.put("startDate", DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_date",
					DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(model.getStartDate()));
		}
		if (model.getServiceKey() != null) {
			jsonObject.put("serviceKey", model.getServiceKey());
		}
		if (model.getSummary() != null) {
			jsonObject.put("summary", model.getSummary());
		}
		if (model.getTag() != null) {
			jsonObject.put("tag", model.getTag());
		}
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		}
		if (model.getLink() != null) {
			jsonObject.put("link", model.getLink());
		}
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		jsonObject.put("sortNo", model.getSortNo());
		if (model.getEndDate() != null) {
			jsonObject.put("endDate", DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_date",
					DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_datetime",
					DateUtils.getDateTime(model.getEndDate()));
		}
		if (model.getRefererUrl() != null) {
			jsonObject.put("refererUrl", model.getRefererUrl());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		jsonObject.put("publishFlag", model.getPublishFlag());
		if (model.getAuthor() != null) {
			jsonObject.put("author", model.getAuthor());
		}
		jsonObject.put("nodeId", model.getNodeId());
		jsonObject.put("processInstanceId", model.getProcessInstanceId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		jsonObject.put("commentFlag", model.getCommentFlag());
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getFallbackFlag() != null) {
			jsonObject.put("fallbackFlag", model.getFallbackFlag());
		}
		if (model.getKeywords() != null) {
			jsonObject.put("keywords", model.getKeywords());
		}
		jsonObject.put("status", model.getStatus());
		jsonObject.put("digg", model.getDigg());
		jsonObject.put("bury", model.getBury());
		jsonObject.put("commentCount", model.getCommentCount());
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getUnitName() != null) {
			jsonObject.put("unitName", model.getUnitName());
		}
		jsonObject.put("viewCount", model.getViewCount());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(PublicInfo model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("wfStatus", model.getWfStatus());
		jsonObject.put("originalFlag", model.getOriginalFlag());
		if (model.getStartDate() != null) {
			jsonObject
					.put("startDate", DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_date",
					DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(model.getStartDate()));
		}
		if (model.getServiceKey() != null) {
			jsonObject.put("serviceKey", model.getServiceKey());
		}
		if (model.getSummary() != null) {
			jsonObject.put("summary", model.getSummary());
		}
		if (model.getTag() != null) {
			jsonObject.put("tag", model.getTag());
		}
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		}
		if (model.getLink() != null) {
			jsonObject.put("link", model.getLink());
		}
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		jsonObject.put("sortNo", model.getSortNo());
		if (model.getEndDate() != null) {
			jsonObject.put("endDate", DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_date",
					DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_datetime",
					DateUtils.getDateTime(model.getEndDate()));
		}
		if (model.getRefererUrl() != null) {
			jsonObject.put("refererUrl", model.getRefererUrl());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		jsonObject.put("publishFlag", model.getPublishFlag());
		if (model.getAuthor() != null) {
			jsonObject.put("author", model.getAuthor());
		}
		jsonObject.put("nodeId", model.getNodeId());
		jsonObject.put("processInstanceId", model.getProcessInstanceId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		jsonObject.put("commentFlag", model.getCommentFlag());
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getFallbackFlag() != null) {
			jsonObject.put("fallbackFlag", model.getFallbackFlag());
		}
		if (model.getKeywords() != null) {
			jsonObject.put("keywords", model.getKeywords());
		}
		jsonObject.put("status", model.getStatus());
		jsonObject.put("digg", model.getDigg());
		jsonObject.put("bury", model.getBury());
		jsonObject.put("commentCount", model.getCommentCount());
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getUnitName() != null) {
			jsonObject.put("unitName", model.getUnitName());
		}
		jsonObject.put("viewCount", model.getViewCount());
		return jsonObject;
	}

	private PublicInfoJsonFactory() {

	}

}
