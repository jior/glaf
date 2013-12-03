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
package com.glaf.oa.paymentplan.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.oa.paymentplan.model.*;

public class PaymentplanJsonFactory {

	public static Paymentplan jsonToObject(JSONObject jsonObject) {
		Paymentplan model = new Paymentplan();
		if (jsonObject.containsKey("planid")) {
			model.setPlanid(jsonObject.getLong("planid"));
		}
		if (jsonObject.containsKey("budgetid")) {
			model.setBudgetid(jsonObject.getLong("budgetid"));
		}
		if (jsonObject.containsKey("paymemtsum")) {
			model.setPaymemtsum(jsonObject.getDouble("paymemtsum"));
		}
		if (jsonObject.containsKey("paymentdate")) {
			model.setPaymentdate(jsonObject.getDate("paymentdate"));
		}
		if (jsonObject.containsKey("sequence")) {
			model.setSequence(jsonObject.getInteger("sequence"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		return model;
	}

	public static JSONObject toJsonObject(Paymentplan model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("planid", model.getPlanid());
		jsonObject.put("_planid_", model.getPlanid());
		jsonObject.put("budgetid", model.getBudgetid());
		jsonObject.put("paymemtsum", model.getPaymemtsum());
		if (model.getPaymentdate() != null) {
			jsonObject.put("paymentdate",
					DateUtils.getDate(model.getPaymentdate()));
			jsonObject.put("paymentdate_date",
					DateUtils.getDate(model.getPaymentdate()));
			jsonObject.put("paymentdate_datetime",
					DateUtils.getDateTime(model.getPaymentdate()));
		}
		jsonObject.put("sequence", model.getSequence());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Paymentplan model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("planid", model.getPlanid());
		jsonObject.put("_planid_", model.getPlanid());
		jsonObject.put("budgetid", model.getBudgetid());
		jsonObject.put("paymemtsum", model.getPaymemtsum());
		if (model.getPaymentdate() != null) {
			jsonObject.put("paymentdate",
					DateUtils.getDate(model.getPaymentdate()));
			jsonObject.put("paymentdate_date",
					DateUtils.getDate(model.getPaymentdate()));
			jsonObject.put("paymentdate_datetime",
					DateUtils.getDateTime(model.getPaymentdate()));
		}
		jsonObject.put("sequence", model.getSequence());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<Paymentplan> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Paymentplan model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Paymentplan> arrayToList(JSONArray array) {
		java.util.List<Paymentplan> list = new java.util.ArrayList<Paymentplan>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Paymentplan model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private PaymentplanJsonFactory() {

	}

}