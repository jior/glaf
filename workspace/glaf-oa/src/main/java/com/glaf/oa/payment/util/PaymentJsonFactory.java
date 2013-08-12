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
package com.glaf.oa.payment.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.oa.payment.model.*;

public class PaymentJsonFactory {

	public static Payment jsonToObject(JSONObject jsonObject) {
		Payment model = new Payment();
		if (jsonObject.containsKey("paymentid")) {
			model.setPaymentid(jsonObject.getLong("paymentid"));
		}
		if (jsonObject.containsKey("area")) {
			model.setArea(jsonObject.getString("area"));
		}
		if (jsonObject.containsKey("post")) {
			model.setPost(jsonObject.getString("post"));
		}
		if (jsonObject.containsKey("company")) {
			model.setCompany(jsonObject.getString("company"));
		}
		if (jsonObject.containsKey("dept")) {
			model.setDept(jsonObject.getString("dept"));
		}
		if (jsonObject.containsKey("certificateno")) {
			model.setCertificateno(jsonObject.getString("certificateno"));
		}
		if (jsonObject.containsKey("receiptno")) {
			model.setReceiptno(jsonObject.getString("receiptno"));
		}
		if (jsonObject.containsKey("appuser")) {
			model.setAppuser(jsonObject.getString("appuser"));
		}
		if (jsonObject.containsKey("appdate")) {
			model.setAppdate(jsonObject.getDate("appdate"));
		}
		if (jsonObject.containsKey("maturitydate")) {
			model.setMaturitydate(jsonObject.getDate("maturitydate"));
		}
		if (jsonObject.containsKey("appsum")) {
			model.setAppsum(jsonObject.getDouble("appsum"));
		}
		if (jsonObject.containsKey("currency")) {
			model.setCurrency(jsonObject.getString("currency"));
		}
		if (jsonObject.containsKey("budgetno")) {
			model.setBudgetno(jsonObject.getString("budgetno"));
		}
		if (jsonObject.containsKey("use")) {
			model.setUse(jsonObject.getString("use"));
		}
		if (jsonObject.containsKey("supname")) {
			model.setSupname(jsonObject.getString("supname"));
		}
		if (jsonObject.containsKey("supbank")) {
			model.setSupbank(jsonObject.getString("supbank"));
		}
		if (jsonObject.containsKey("supaccount")) {
			model.setSupaccount(jsonObject.getString("supaccount"));
		}
		if (jsonObject.containsKey("supaddress")) {
			model.setSupaddress(jsonObject.getString("supaddress"));
		}
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("checkno")) {
			model.setCheckno(jsonObject.getString("checkno"));
		}
		if (jsonObject.containsKey("remark")) {
			model.setRemark(jsonObject.getString("remark"));
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
			model.setWfstatus(jsonObject.getDouble("wfstatus"));
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

	public static JSONObject toJsonObject(Payment model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("paymentid", model.getPaymentid());
		jsonObject.put("_paymentid_", model.getPaymentid());
		if (model.getArea() != null) {
			jsonObject.put("area", model.getArea());
		}
		if (model.getPost() != null) {
			jsonObject.put("post", model.getPost());
		}
		if (model.getCompany() != null) {
			jsonObject.put("company", model.getCompany());
		}
		if (model.getDept() != null) {
			jsonObject.put("dept", model.getDept());
		}
		if (model.getCertificateno() != null) {
			jsonObject.put("certificateno", model.getCertificateno());
		}
		if (model.getReceiptno() != null) {
			jsonObject.put("receiptno", model.getReceiptno());
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
		if (model.getMaturitydate() != null) {
			jsonObject.put("maturitydate",
					DateUtils.getDate(model.getMaturitydate()));
			jsonObject.put("maturitydate_date",
					DateUtils.getDate(model.getMaturitydate()));
			jsonObject.put("maturitydate_datetime",
					DateUtils.getDateTime(model.getMaturitydate()));
		}
		jsonObject.put("appsum", model.getAppsum());
		if (model.getCurrency() != null) {
			jsonObject.put("currency", model.getCurrency());
		}
		if (model.getBudgetno() != null) {
			jsonObject.put("budgetno", model.getBudgetno());
		}
		if (model.getUse() != null) {
			jsonObject.put("use", model.getUse());
		}
		if (model.getSupname() != null) {
			jsonObject.put("supname", model.getSupname());
		}
		if (model.getSupbank() != null) {
			jsonObject.put("supbank", model.getSupbank());
		}
		if (model.getSupaccount() != null) {
			jsonObject.put("supaccount", model.getSupaccount());
		}
		if (model.getSupaddress() != null) {
			jsonObject.put("supaddress", model.getSupaddress());
		}
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		}
		if (model.getCheckno() != null) {
			jsonObject.put("checkno", model.getCheckno());
		}
		if (model.getRemark() != null) {
			jsonObject.put("remark", model.getRemark());
		}
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

	public static ObjectNode toObjectNode(Payment model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("paymentid", model.getPaymentid());
		jsonObject.put("_paymentid_", model.getPaymentid());
		if (model.getArea() != null) {
			jsonObject.put("area", model.getArea());
		}
		if (model.getPost() != null) {
			jsonObject.put("post", model.getPost());
		}
		if (model.getCompany() != null) {
			jsonObject.put("company", model.getCompany());
		}
		if (model.getDept() != null) {
			jsonObject.put("dept", model.getDept());
		}
		if (model.getCertificateno() != null) {
			jsonObject.put("certificateno", model.getCertificateno());
		}
		if (model.getReceiptno() != null) {
			jsonObject.put("receiptno", model.getReceiptno());
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
		if (model.getMaturitydate() != null) {
			jsonObject.put("maturitydate",
					DateUtils.getDate(model.getMaturitydate()));
			jsonObject.put("maturitydate_date",
					DateUtils.getDate(model.getMaturitydate()));
			jsonObject.put("maturitydate_datetime",
					DateUtils.getDateTime(model.getMaturitydate()));
		}
		jsonObject.put("appsum", model.getAppsum());
		if (model.getCurrency() != null) {
			jsonObject.put("currency", model.getCurrency());
		}
		if (model.getBudgetno() != null) {
			jsonObject.put("budgetno", model.getBudgetno());
		}
		if (model.getUse() != null) {
			jsonObject.put("use", model.getUse());
		}
		if (model.getSupname() != null) {
			jsonObject.put("supname", model.getSupname());
		}
		if (model.getSupbank() != null) {
			jsonObject.put("supbank", model.getSupbank());
		}
		if (model.getSupaccount() != null) {
			jsonObject.put("supaccount", model.getSupaccount());
		}
		if (model.getSupaddress() != null) {
			jsonObject.put("supaddress", model.getSupaddress());
		}
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		}
		if (model.getCheckno() != null) {
			jsonObject.put("checkno", model.getCheckno());
		}
		if (model.getRemark() != null) {
			jsonObject.put("remark", model.getRemark());
		}
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

	public static JSONArray listToArray(java.util.List<Payment> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Payment model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Payment> arrayToList(JSONArray array) {
		java.util.List<Payment> list = new java.util.ArrayList<Payment>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Payment model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private PaymentJsonFactory() {

	}

}