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
package com.glaf.oa.reimbursement.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.oa.reimbursement.model.Ritem;
import com.glaf.core.util.DateUtils;

public class RitemJsonFactory {

	public static Ritem jsonToObject(JSONObject jsonObject) {
		Ritem model = new Ritem();
		if (jsonObject.containsKey("ritemid")) {
			model.setRitemid(jsonObject.getLong("ritemid"));
		}
		if (jsonObject.containsKey("reimbursementid")) {
			model.setReimbursementid(jsonObject.getLong("reimbursementid"));
		}
		if (jsonObject.containsKey("feetype")) {
			model.setFeetype(jsonObject.getInteger("feetype"));
		}
		if (jsonObject.containsKey("feedate")) {
			model.setFeedate(jsonObject.getDate("feedate"));
		}
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("currency")) {
			model.setCurrency(jsonObject.getString("currency"));
		}
		if (jsonObject.containsKey("itemsum")) {
			model.setItemsum(jsonObject.getDouble("itemsum"));
		}
		if (jsonObject.containsKey("exrate")) {
			model.setExrate(jsonObject.getDouble("exrate"));
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

	public static JSONObject toJsonObject(Ritem model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ritemid", model.getRitemid());
		jsonObject.put("_ritemid_", model.getRitemid());
		jsonObject.put("reimbursementid", model.getReimbursementid());
		jsonObject.put("feetype", model.getFeetype());
		if (model.getFeedate() != null) {
			jsonObject.put("feedate", DateUtils.getDate(model.getFeedate()));
			jsonObject.put("feedate_date",
					DateUtils.getDate(model.getFeedate()));
			jsonObject.put("feedate_datetime",
					DateUtils.getDateTime(model.getFeedate()));
		}
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		}
		if (model.getCurrency() != null) {
			jsonObject.put("currency", model.getCurrency());
		}
		jsonObject.put("itemsum", model.getItemsum());
		jsonObject.put("exrate", model.getExrate());
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

	public static ObjectNode toObjectNode(Ritem model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("ritemid", model.getRitemid());
		jsonObject.put("_ritemid_", model.getRitemid());
		jsonObject.put("reimbursementid", model.getReimbursementid());
		jsonObject.put("feetype", model.getFeetype());
		if (model.getFeedate() != null) {
			jsonObject.put("feedate", DateUtils.getDate(model.getFeedate()));
			jsonObject.put("feedate_date",
					DateUtils.getDate(model.getFeedate()));
			jsonObject.put("feedate_datetime",
					DateUtils.getDateTime(model.getFeedate()));
		}
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		}
		if (model.getCurrency() != null) {
			jsonObject.put("currency", model.getCurrency());
		}
		jsonObject.put("itemsum", model.getItemsum());
		jsonObject.put("exrate", model.getExrate());
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

	public static JSONArray listToArray(java.util.List<Ritem> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Ritem model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Ritem> arrayToList(JSONArray array) {
		java.util.List<Ritem> list = new java.util.ArrayList<Ritem>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Ritem model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private RitemJsonFactory() {

	}

}