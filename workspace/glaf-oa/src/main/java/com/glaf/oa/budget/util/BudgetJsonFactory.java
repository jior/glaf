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
package com.glaf.oa.budget.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.oa.budget.model.*;

public class BudgetJsonFactory {

	public static Budget jsonToObject(JSONObject jsonObject) {
		Budget model = new Budget();
		if (jsonObject.containsKey("budgetid")) {
			model.setBudgetid(jsonObject.getLong("budgetid"));
		}
		if (jsonObject.containsKey("budgetno")) {
			model.setBudgetno(jsonObject.getString("budgetno"));
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
		if (jsonObject.containsKey("proname")) {
			model.setProname(jsonObject.getString("proname"));
		}
		if (jsonObject.containsKey("procontent")) {
			model.setProcontent(jsonObject.getString("procontent"));
		}
		if (jsonObject.containsKey("budgetsum")) {
			model.setBudgetsum(jsonObject.getDouble("budgetsum"));
		}
		if (jsonObject.containsKey("currency")) {
			model.setCurrency(jsonObject.getString("currency"));
		}
		if (jsonObject.containsKey("paymentmodel")) {
			model.setPaymentmodel(jsonObject.getInteger("paymentmodel"));
		}
		if (jsonObject.containsKey("paymenttype")) {
			model.setPaymenttype(jsonObject.getInteger("paymenttype"));
		}
		if (jsonObject.containsKey("supname")) {
			model.setSupname(jsonObject.getString("supname"));
		}
		if (jsonObject.containsKey("supaccount")) {
			model.setSupaccount(jsonObject.getString("supaccount"));
		}
		if (jsonObject.containsKey("supbank")) {
			model.setSupbank(jsonObject.getString("supbank"));
		}
		if (jsonObject.containsKey("supaddress")) {
			model.setSupaddress(jsonObject.getString("supaddress"));
		}
		if (jsonObject.containsKey("attachment")) {
			model.setAttachment(jsonObject.getString("attachment"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("processname")) {
			model.setProcessname(jsonObject.getString("processname"));
		}
		if (jsonObject.containsKey("processinstanceid")) {
			model.setProcessinstanceid(jsonObject
					.getDouble("processinstanceid"));
		}
		if (jsonObject.containsKey("wfstatus")) {
			model.setWfstatus(jsonObject.getDouble("wfstatus"));
		}
		if (jsonObject.containsKey("brands1")) {
			model.setBrands1(jsonObject.getString("brands1"));
		}
		if (jsonObject.containsKey("brands1account")) {
			model.setBrands1account(jsonObject.getDouble("brands1account"));
		}
		if (jsonObject.containsKey("brands2")) {
			model.setBrands2(jsonObject.getString("brands2"));
		}
		if (jsonObject.containsKey("brands2account")) {
			model.setBrands2account(jsonObject.getDouble("brands2account"));
		}
		if (jsonObject.containsKey("brands3")) {
			model.setBrands3(jsonObject.getString("brands3"));
		}
		if (jsonObject.containsKey("brands3account")) {
			model.setBrands3account(jsonObject.getDouble("brands3account"));
		}
		if (jsonObject.containsKey("paymentdate")) {
			model.setPaymentdate(jsonObject.getDate("paymentdate"));
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

	public static JSONObject toJsonObject(Budget model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("budgetid", model.getBudgetid());
		jsonObject.put("_budgetid_", model.getBudgetid());
		if (model.getBudgetno() != null) {
			jsonObject.put("budgetno", model.getBudgetno());
		}
		if (model.getArea() != null) {
			jsonObject.put("area", model.getArea());
		}
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
		if (model.getProname() != null) {
			jsonObject.put("proname", model.getProname());
		}
		if (model.getProcontent() != null) {
			jsonObject.put("procontent", model.getProcontent());
		}
		jsonObject.put("budgetsum", model.getBudgetsum());
		if (model.getCurrency() != null) {
			jsonObject.put("currency", model.getCurrency());
		}
		jsonObject.put("paymentmodel", model.getPaymentmodel());
		jsonObject.put("paymenttype", model.getPaymenttype());
		if (model.getSupname() != null) {
			jsonObject.put("supname", model.getSupname());
		}
		if (model.getSupaccount() != null) {
			jsonObject.put("supaccount", model.getSupaccount());
		}
		if (model.getSupbank() != null) {
			jsonObject.put("supbank", model.getSupbank());
		}
		if (model.getSupaddress() != null) {
			jsonObject.put("supaddress", model.getSupaddress());
		}
		if (model.getAttachment() != null) {
			jsonObject.put("attachment", model.getAttachment());
		}
		jsonObject.put("status", model.getStatus());
		if (model.getProcessname() != null) {
			jsonObject.put("processname", model.getProcessname());
		}
		jsonObject.put("processinstanceid", model.getProcessinstanceid());
		jsonObject.put("wfstatus", model.getWfstatus());
		if (model.getBrands1() != null) {
			jsonObject.put("brands1", model.getBrands1());
		}
		jsonObject.put("brands1account", model.getBrands1account());
		if (model.getBrands2() != null) {
			jsonObject.put("brands2", model.getBrands2());
		}
		jsonObject.put("brands2account", model.getBrands2account());
		if (model.getBrands3() != null) {
			jsonObject.put("brands3", model.getBrands3());
		}
		jsonObject.put("brands3account", model.getBrands3account());
		if (model.getPaymentdate() != null) {
			jsonObject.put("paymentdate",
					DateUtils.getDate(model.getPaymentdate()));
			jsonObject.put("paymentdate_date",
					DateUtils.getDate(model.getPaymentdate()));
			jsonObject.put("paymentdate_datetime",
					DateUtils.getDateTime(model.getPaymentdate()));
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

	public static ObjectNode toObjectNode(Budget model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("budgetid", model.getBudgetid());
		jsonObject.put("_budgetid_", model.getBudgetid());
		if (model.getBudgetno() != null) {
			jsonObject.put("budgetno", model.getBudgetno());
		}
		if (model.getArea() != null) {
			jsonObject.put("area", model.getArea());
		}
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
		if (model.getProname() != null) {
			jsonObject.put("proname", model.getProname());
		}
		if (model.getProcontent() != null) {
			jsonObject.put("procontent", model.getProcontent());
		}
		jsonObject.put("budgetsum", model.getBudgetsum());
		if (model.getCurrency() != null) {
			jsonObject.put("currency", model.getCurrency());
		}
		jsonObject.put("paymentmodel", model.getPaymentmodel());
		jsonObject.put("paymenttype", model.getPaymenttype());
		if (model.getSupname() != null) {
			jsonObject.put("supname", model.getSupname());
		}
		if (model.getSupaccount() != null) {
			jsonObject.put("supaccount", model.getSupaccount());
		}
		if (model.getSupbank() != null) {
			jsonObject.put("supbank", model.getSupbank());
		}
		if (model.getSupaddress() != null) {
			jsonObject.put("supaddress", model.getSupaddress());
		}
		if (model.getAttachment() != null) {
			jsonObject.put("attachment", model.getAttachment());
		}
		jsonObject.put("status", model.getStatus());
		if (model.getProcessname() != null) {
			jsonObject.put("processname", model.getProcessname());
		}
		jsonObject.put("processinstanceid", model.getProcessinstanceid());
		jsonObject.put("wfstatus", model.getWfstatus());
		if (model.getBrands1() != null) {
			jsonObject.put("brands1", model.getBrands1());
		}
		jsonObject.put("brands1account", model.getBrands1account());
		if (model.getBrands2() != null) {
			jsonObject.put("brands2", model.getBrands2());
		}
		jsonObject.put("brands2account", model.getBrands2account());
		if (model.getBrands3() != null) {
			jsonObject.put("brands3", model.getBrands3());
		}
		jsonObject.put("brands3account", model.getBrands3account());
		if (model.getPaymentdate() != null) {
			jsonObject.put("paymentdate",
					DateUtils.getDate(model.getPaymentdate()));
			jsonObject.put("paymentdate_date",
					DateUtils.getDate(model.getPaymentdate()));
			jsonObject.put("paymentdate_datetime",
					DateUtils.getDateTime(model.getPaymentdate()));
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

	public static JSONArray listToArray(java.util.List<Budget> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Budget model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Budget> arrayToList(JSONArray array) {
		java.util.List<Budget> list = new java.util.ArrayList<Budget>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Budget model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private BudgetJsonFactory() {

	}

}