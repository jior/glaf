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
package com.glaf.oa.ltravel.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.oa.ltravel.model.*;

public class LtravelJsonFactory {

	public static Ltravel jsonToObject(JSONObject jsonObject) {
		Ltravel model = new Ltravel();
		if (jsonObject.containsKey("travelid")) {
			model.setTravelid(jsonObject.getLong("travelid"));
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
		if (jsonObject.containsKey("appuser")) {
			model.setAppuser(jsonObject.getString("appuser"));
		}
		if (jsonObject.containsKey("appdate")) {
			model.setAppdate(jsonObject.getDate("appdate"));
		}
		if (jsonObject.containsKey("traveladdress")) {
			model.setTraveladdress(jsonObject.getString("traveladdress"));
		}
		if (jsonObject.containsKey("startdate")) {
			model.setStartdate(jsonObject.getDate("startdate"));
		}
		if (jsonObject.containsKey("enddate")) {
			model.setEnddate(jsonObject.getDate("enddate"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("attachment")) {
			model.setAttachment(jsonObject.getString("attachment"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("travelnum")) {
			model.setTravelnum(jsonObject.getDouble("travelnum"));
		}
		if (jsonObject.containsKey("processname")) {
			model.setProcessname(jsonObject.getString("processname"));
		}
		if (jsonObject.containsKey("processinstanceid")) {
			model.setProcessinstanceid(jsonObject.getLong("processinstanceid"));
		}
		if (jsonObject.containsKey("wfstatus")) {
			model.setWfstatus(jsonObject.getLong("wfstatus"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}

		return model;
	}

	public static JSONObject toJsonObject(Ltravel model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("travelid", model.getTravelid());
		jsonObject.put("_travelid_", model.getTravelid());
		jsonObject.put("_otravelid_", model.getTravelid());
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
		if (model.getAppuser() != null) {
			jsonObject.put("appuser", model.getAppuser());
		}
		if (model.getAppdate() != null) {
			jsonObject.put("appdate", DateUtils.getDate(model.getAppdate()));
			jsonObject.put("appdate_date",
					DateUtils.getDate(model.getAppdate()));
			jsonObject.put("appdate_datetime",
					DateUtils.getDateTime(model.getAppdate()));
		}
		if (model.getTraveladdress() != null) {
			jsonObject.put("traveladdress", model.getTraveladdress());
		}
		if (model.getStartdate() != null) {
			jsonObject
					.put("startdate", DateUtils.getDate(model.getStartdate()));
			jsonObject.put("startdate_date",
					DateUtils.getDate(model.getStartdate()));
			jsonObject.put("startdate_datetime",
					DateUtils.getDateTime(model.getStartdate()));
		}
		if (model.getEnddate() != null) {
			jsonObject.put("enddate", DateUtils.getDate(model.getEnddate()));
			jsonObject.put("enddate_date",
					DateUtils.getDate(model.getEnddate()));
			jsonObject.put("enddate_datetime",
					DateUtils.getDateTime(model.getEnddate()));
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getAttachment() != null) {
			jsonObject.put("attachment", model.getAttachment());
		}
		jsonObject.put("status", model.getStatus());
		jsonObject.put("travelnum", model.getTravelnum());
		if (model.getProcessname() != null) {
			jsonObject.put("processname", model.getProcessname());
		}
		jsonObject.put("processinstanceid", model.getProcessinstanceid());
		jsonObject.put("wfstatus", model.getWfstatus());
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
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Ltravel model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("travelid", model.getTravelid());
		jsonObject.put("_travelid_", model.getTravelid());
		jsonObject.put("_otravelid_", model.getTravelid());
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
		if (model.getAppuser() != null) {
			jsonObject.put("appuser", model.getAppuser());
		}
		if (model.getAppdate() != null) {
			jsonObject.put("appdate", DateUtils.getDate(model.getAppdate()));
			jsonObject.put("appdate_date",
					DateUtils.getDate(model.getAppdate()));
			jsonObject.put("appdate_datetime",
					DateUtils.getDateTime(model.getAppdate()));
		}
		if (model.getTraveladdress() != null) {
			jsonObject.put("traveladdress", model.getTraveladdress());
		}
		if (model.getStartdate() != null) {
			jsonObject
					.put("startdate", DateUtils.getDate(model.getStartdate()));
			jsonObject.put("startdate_date",
					DateUtils.getDate(model.getStartdate()));
			jsonObject.put("startdate_datetime",
					DateUtils.getDateTime(model.getStartdate()));
		}
		if (model.getEnddate() != null) {
			jsonObject.put("enddate", DateUtils.getDate(model.getEnddate()));
			jsonObject.put("enddate_date",
					DateUtils.getDate(model.getEnddate()));
			jsonObject.put("enddate_datetime",
					DateUtils.getDateTime(model.getEnddate()));
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getAttachment() != null) {
			jsonObject.put("attachment", model.getAttachment());
		}
		jsonObject.put("status", model.getStatus());
		jsonObject.put("travelnum", model.getTravelnum());
		if (model.getProcessname() != null) {
			jsonObject.put("processname", model.getProcessname());
		}
		jsonObject.put("processinstanceid", model.getProcessinstanceid());
		jsonObject.put("wfstatus", model.getWfstatus());
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
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<Ltravel> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Ltravel model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Ltravel> arrayToList(JSONArray array) {
		java.util.List<Ltravel> list = new java.util.ArrayList<Ltravel>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Ltravel model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private LtravelJsonFactory() {

	}

}