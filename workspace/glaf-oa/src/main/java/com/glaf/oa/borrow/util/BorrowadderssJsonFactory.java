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
package com.glaf.oa.borrow.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.oa.borrow.model.Borrowadderss;
import com.glaf.core.util.DateUtils;

public class BorrowadderssJsonFactory {

	public static Borrowadderss jsonToObject(JSONObject jsonObject) {
		Borrowadderss model = new Borrowadderss();
		if (jsonObject.containsKey("addressid")) {
			model.setAddressid(jsonObject.getLong("addressid"));
		}
		if (jsonObject.containsKey("borrowid")) {
			model.setBorrowid(jsonObject.getLong("borrowid"));
		}
		if (jsonObject.containsKey("start")) {
			model.setStart(jsonObject.getString("start"));
		}
		if (jsonObject.containsKey("reach")) {
			model.setReach(jsonObject.getString("reach"));
		}
		if (jsonObject.containsKey("remark")) {
			model.setRemark(jsonObject.getString("remark"));
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

	public static JSONObject toJsonObject(Borrowadderss model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("addressid", model.getAddressid());
		jsonObject.put("_addressid_", model.getAddressid());
		jsonObject.put("borrowid", model.getBorrowid());
		if (model.getStart() != null) {
			jsonObject.put("start", model.getStart());
		}
		if (model.getReach() != null) {
			jsonObject.put("reach", model.getReach());
		}
		if (model.getRemark() != null) {
			jsonObject.put("remark", model.getRemark());
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

	public static ObjectNode toObjectNode(Borrowadderss model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("addressid", model.getAddressid());
		jsonObject.put("_addressid_", model.getAddressid());
		jsonObject.put("borrowid", model.getBorrowid());
		if (model.getStart() != null) {
			jsonObject.put("start", model.getStart());
		}
		if (model.getReach() != null) {
			jsonObject.put("reach", model.getReach());
		}
		if (model.getRemark() != null) {
			jsonObject.put("remark", model.getRemark());
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

	public static JSONArray listToArray(java.util.List<Borrowadderss> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Borrowadderss model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Borrowadderss> arrayToList(JSONArray array) {
		java.util.List<Borrowadderss> list = new java.util.ArrayList<Borrowadderss>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Borrowadderss model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private BorrowadderssJsonFactory() {

	}

}