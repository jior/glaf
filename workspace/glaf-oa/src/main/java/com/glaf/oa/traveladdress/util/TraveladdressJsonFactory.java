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
package com.glaf.oa.traveladdress.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.oa.traveladdress.model.*;

public class TraveladdressJsonFactory {

	public static Traveladdress jsonToObject(JSONObject jsonObject) {
		Traveladdress model = new Traveladdress();
		if (jsonObject.containsKey("addressid")) {
			model.setAddressid(jsonObject.getLong("addressid"));
		}
		if (jsonObject.containsKey("travelid")) {
			model.setTravelid(jsonObject.getLong("travelid"));
		}
		if (jsonObject.containsKey("startadd")) {
			model.setStartadd(jsonObject.getString("startadd"));
		}
		if (jsonObject.containsKey("endadd")) {
			model.setEndadd(jsonObject.getString("endadd"));
		}
		if (jsonObject.containsKey("transportation")) {
			model.setTransportation(jsonObject.getString("transportation"));
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

	public static JSONObject toJsonObject(Traveladdress model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("addressid", model.getAddressid());
		jsonObject.put("_addressid_", model.getAddressid());
		jsonObject.put("travelid", model.getTravelid());
		if (model.getStartadd() != null) {
			jsonObject.put("startadd", model.getStartadd());
		}
		if (model.getEndadd() != null) {
			jsonObject.put("endadd", model.getEndadd());
		}
		if (model.getTransportation() != null) {
			jsonObject.put("transportation", model.getTransportation());
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
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Traveladdress model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("addressid", model.getAddressid());
		jsonObject.put("_addressid_", model.getAddressid());
		jsonObject.put("travelid", model.getTravelid());
		if (model.getStartadd() != null) {
			jsonObject.put("startadd", model.getStartadd());
		}
		if (model.getEndadd() != null) {
			jsonObject.put("endadd", model.getEndadd());
		}
		if (model.getTransportation() != null) {
			jsonObject.put("transportation", model.getTransportation());
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
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<Traveladdress> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Traveladdress model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Traveladdress> arrayToList(JSONArray array) {
		java.util.List<Traveladdress> list = new java.util.ArrayList<Traveladdress>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Traveladdress model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private TraveladdressJsonFactory() {

	}

}