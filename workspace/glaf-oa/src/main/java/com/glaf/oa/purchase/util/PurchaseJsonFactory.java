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
package com.glaf.oa.purchase.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.oa.purchase.model.*;

public class PurchaseJsonFactory {

	public static Purchase jsonToObject(JSONObject jsonObject) {
		Purchase model = new Purchase();
		if (jsonObject.containsKey("purchaseid")) {
			model.setPurchaseid(jsonObject.getLong("purchaseid"));
		}
		if (jsonObject.containsKey("purchaseno")) {
			model.setPurchaseno(jsonObject.getString("purchaseno"));
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
		if (jsonObject.containsKey("purchasesum")) {
			model.setPurchasesum(jsonObject.getDouble("purchasesum"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
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
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}

		return model;
	}

	public static JSONObject toJsonObject(Purchase model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("purchaseid", model.getPurchaseid());
		jsonObject.put("_purchaseid_", model.getPurchaseid());
		if (model.getPurchaseno() != null) {
			jsonObject.put("purchaseno", model.getPurchaseno());
		}
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
		jsonObject.put("purchasesum", model.getPurchasesum());
		jsonObject.put("status", model.getStatus());
		if (model.getProcessname() != null) {
			jsonObject.put("processname", model.getProcessname());
		}
		jsonObject.put("processinstanceid", model.getProcessinstanceid());
		jsonObject.put("wfstatus", model.getWfstatus());
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
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Purchase model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("purchaseid", model.getPurchaseid());
		jsonObject.put("_purchaseid_", model.getPurchaseid());
		if (model.getPurchaseno() != null) {
			jsonObject.put("purchaseno", model.getPurchaseno());
		}
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
		jsonObject.put("purchasesum", model.getPurchasesum());
		jsonObject.put("status", model.getStatus());
		if (model.getProcessname() != null) {
			jsonObject.put("processname", model.getProcessname());
		}
		jsonObject.put("processinstanceid", model.getProcessinstanceid());
		jsonObject.put("wfstatus", model.getWfstatus());
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
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<Purchase> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Purchase model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Purchase> arrayToList(JSONArray array) {
		java.util.List<Purchase> list = new java.util.ArrayList<Purchase>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Purchase model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private PurchaseJsonFactory() {

	}

}