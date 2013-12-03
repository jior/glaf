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

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.oa.reimbursement.model.*;

public class ReimbursementJsonFactory {

	public static Reimbursement jsonToObject(JSONObject jsonObject) {
		Reimbursement model = new Reimbursement();
		if (jsonObject.containsKey("reimbursementid")) {
			model.setReimbursementid(jsonObject.getLong("reimbursementid"));
		}
		if (jsonObject.containsKey("reimbursementno")) {
			model.setReimbursementno(jsonObject.getString("reimbursementno"));
		}
		if (jsonObject.containsKey("area")) {
			model.setArea(jsonObject.getString("area"));
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
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("budgetno")) {
			model.setBudgetno(jsonObject.getString("budgetno"));
		}
		if (jsonObject.containsKey("appdate")) {
			model.setAppdate(jsonObject.getDate("appdate"));
		}
		if (jsonObject.containsKey("appsum")) {
			model.setAppsum(jsonObject.getDouble("appsum"));
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
		if (jsonObject.containsKey("company")) {
			model.setCompany(jsonObject.getString("company"));
		}
		if (jsonObject.containsKey("budgetsum")) {
			model.setBudgetsum(jsonObject.getDouble("budgetsum"));
		}
		if (jsonObject.containsKey("budgetDate")) {
			model.setBudgetDate(jsonObject.getDate("budgetDate"));
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

	public static JSONObject toJsonObject(Reimbursement model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("reimbursementid", model.getReimbursementid());
		jsonObject.put("_reimbursementid_", model.getReimbursementid());
		if (model.getReimbursementno() != null) {
			jsonObject.put("reimbursementno", model.getReimbursementno());
		}
		if (model.getArea() != null) {
			jsonObject.put("area", model.getArea());
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
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		}
		if (model.getBudgetno() != null) {
			jsonObject.put("budgetno", model.getBudgetno());
		}
		if (model.getAppdate() != null) {
			jsonObject.put("appdate", DateUtils.getDate(model.getAppdate()));
			jsonObject.put("appdate_date",
					DateUtils.getDate(model.getAppdate()));
			jsonObject.put("appdate_datetime",
					DateUtils.getDateTime(model.getAppdate()));
		}
		jsonObject.put("appsum", model.getAppsum());
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
		if (model.getCompany() != null) {
			jsonObject.put("company", model.getCompany());
		}
		jsonObject.put("budgetsum", model.getBudgetsum());
		if (model.getBudgetDate() != null) {
			jsonObject.put("budgetDate",
					DateUtils.getDate(model.getBudgetDate()));
			jsonObject.put("budgetDate_date",
					DateUtils.getDate(model.getBudgetDate()));
			jsonObject.put("budgetDate_datetime",
					DateUtils.getDateTime(model.getBudgetDate()));
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

	public static ObjectNode toObjectNode(Reimbursement model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("reimbursementid", model.getReimbursementid());
		jsonObject.put("_reimbursementid_", model.getReimbursementid());
		if (model.getReimbursementno() != null) {
			jsonObject.put("reimbursementno", model.getReimbursementno());
		}
		if (model.getArea() != null) {
			jsonObject.put("area", model.getArea());
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
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		}
		if (model.getBudgetno() != null) {
			jsonObject.put("budgetno", model.getBudgetno());
		}
		if (model.getAppdate() != null) {
			jsonObject.put("appdate", DateUtils.getDate(model.getAppdate()));
			jsonObject.put("appdate_date",
					DateUtils.getDate(model.getAppdate()));
			jsonObject.put("appdate_datetime",
					DateUtils.getDateTime(model.getAppdate()));
		}
		jsonObject.put("appsum", model.getAppsum());
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
		if (model.getCompany() != null) {
			jsonObject.put("company", model.getCompany());
		}
		jsonObject.put("budgetsum", model.getBudgetsum());
		if (model.getBudgetDate() != null) {
			jsonObject.put("budgetDate",
					DateUtils.getDate(model.getBudgetDate()));
			jsonObject.put("budgetDate_date",
					DateUtils.getDate(model.getBudgetDate()));
			jsonObject.put("budgetDate_datetime",
					DateUtils.getDateTime(model.getBudgetDate()));
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

	public static JSONArray listToArray(java.util.List<Reimbursement> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Reimbursement model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Reimbursement> arrayToList(JSONArray array) {
		java.util.List<Reimbursement> list = new java.util.ArrayList<Reimbursement>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Reimbursement model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private ReimbursementJsonFactory() {

	}

}