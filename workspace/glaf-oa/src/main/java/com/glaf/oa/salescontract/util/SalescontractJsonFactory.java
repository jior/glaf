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
package com.glaf.oa.salescontract.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.oa.salescontract.model.*;

public class SalescontractJsonFactory {

	public static Salescontract jsonToObject(JSONObject jsonObject) {
		Salescontract model = new Salescontract();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("contactname")) {
			model.setContactname(jsonObject.getString("contactname"));
		}
		if (jsonObject.containsKey("projrctname")) {
			model.setProjrctname(jsonObject.getString("projrctname"));
		}
		if (jsonObject.containsKey("companyname")) {
			model.setCompanyname(jsonObject.getString("companyname"));
		}
		if (jsonObject.containsKey("supplisername")) {
			model.setSupplisername(jsonObject.getString("supplisername"));
		}
		if (jsonObject.containsKey("currency")) {
			model.setCurrency(jsonObject.getString("currency"));
		}
		if (jsonObject.containsKey("contractsum")) {
			model.setContractsum(jsonObject.getDouble("contractsum"));
		}
		if (jsonObject.containsKey("paytype")) {
			model.setPaytype(jsonObject.getInteger("paytype"));
		}
		if (jsonObject.containsKey("remarks")) {
			model.setRemarks(jsonObject.getString("remarks"));
		}
		if (jsonObject.containsKey("attachment")) {
			model.setAttachment(jsonObject.getString("attachment"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("appuser")) {
			model.setAppuser(jsonObject.getString("appuser"));
		}
		if (jsonObject.containsKey("appdate")) {
			model.setAppdate(jsonObject.getDate("appdate"));
		}
		if (jsonObject.containsKey("contractno")) {
			model.setContractno(jsonObject.getString("contractno"));
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
		 
		if (jsonObject.containsKey("optionalsum")) {
			model.setOptionalsum(jsonObject.getDouble("optionalsum"));
		}
		if (jsonObject.containsKey("firstpay")) {
			model.setFirstpay(jsonObject.getDouble("firstpay"));
		}
		if (jsonObject.containsKey("lastpay")) {
			model.setLastpay(jsonObject.getDouble("lastpay"));
		}
		if (jsonObject.containsKey("discount")) {
			model.setDiscount(jsonObject.getDouble("discount"));
		}
		if (jsonObject.containsKey("deliverydate")) {
			model.setDeliverydate(jsonObject.getDate("deliverydate"));
		}
		if (jsonObject.containsKey("sales")) {
			model.setSales(jsonObject.getString("sales"));
		}
		if (jsonObject.containsKey("contractsales")) {
			model.setContractsales(jsonObject.getString("contractsales"));
		}
		if (jsonObject.containsKey("giftsum")) {
			model.setGiftsum(jsonObject.getDouble("giftsum"));
		}
		if (jsonObject.containsKey("giftremark")) {
			model.setGiftremark(jsonObject.getString("giftremark"));
		}
		if (jsonObject.containsKey("remark")) {
			model.setRemark(jsonObject.getString("remark"));
		}
		if (jsonObject.containsKey("area")) {
			model.setArea(jsonObject.getString("area"));
		}
		if (jsonObject.containsKey("company")) {
			model.setCompany(jsonObject.getString("company"));
		}
		if (jsonObject.containsKey("createby")) {
			model.setCreateBy(jsonObject.getString("createby"));
		}
		if (jsonObject.containsKey("createdate")) {
			model.setCreatedate(jsonObject.getDate("createdate"));
		}
		if (jsonObject.containsKey("updatedate")) {
			model.setUpdatedate(jsonObject.getDate("updatedate"));
		}
		if (jsonObject.containsKey("updateby")) {
			model.setUpdateby(jsonObject.getString("updateby"));
		}
		return model;
	}

	public static JSONObject toJsonObject(Salescontract model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getContactname() != null) {
			jsonObject.put("contactname", model.getContactname());
		}
		if (model.getProjrctname() != null) {
			jsonObject.put("projrctname", model.getProjrctname());
		}
		if (model.getCompanyname() != null) {
			jsonObject.put("companyname", model.getCompanyname());
		}
		if (model.getSupplisername() != null) {
			jsonObject.put("supplisername", model.getSupplisername());
		}
		if (model.getCurrency() != null) {
			jsonObject.put("currency", model.getCurrency());
		}
		jsonObject.put("contractsum", model.getContractsum());
		jsonObject.put("paytype", model.getPaytype());
		if (model.getRemarks() != null) {
			jsonObject.put("remarks", model.getRemarks());
		}
		if (model.getAttachment() != null) {
			jsonObject.put("attachment", model.getAttachment());
		}
		jsonObject.put("status", model.getStatus());
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
		if (model.getContractno() != null) {
			jsonObject.put("contractno", model.getContractno());
		}
		if (model.getProcessname() != null) {
			jsonObject.put("processname", model.getProcessname());
		}
		jsonObject.put("processinstanceid", model.getProcessinstanceid());
		jsonObject.put("wfstatus", model.getWfstatus());
		jsonObject.put("optionalsum", model.getOptionalsum());
		jsonObject.put("firstpay", model.getFirstpay());
		jsonObject.put("lastpay", model.getLastpay());
		jsonObject.put("discount", model.getDiscount());
		if (model.getDeliverydate() != null) {
			jsonObject.put("deliverydate",
					DateUtils.getDate(model.getDeliverydate()));
			jsonObject.put("deliverydate_date",
					DateUtils.getDate(model.getDeliverydate()));
			jsonObject.put("deliverydate_datetime",
					DateUtils.getDateTime(model.getDeliverydate()));
		}
		if (model.getSales() != null) {
			jsonObject.put("sales", model.getSales());
		}
		if (model.getContractsales() != null) {
			jsonObject.put("contractsales", model.getContractsales());
		}
		jsonObject.put("giftsum", model.getGiftsum());
		if (model.getGiftremark() != null) {
			jsonObject.put("giftremark", model.getGiftremark());
		}
		if (model.getRemark() != null) {
			jsonObject.put("remark", model.getRemark());
		}
		jsonObject.put("area", model.getArea());
		if (model.getCompany() != null) {
			jsonObject.put("company", model.getCompany());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createby", model.getCreateBy());
		}
		if (model.getCreatedate() != null) {
			jsonObject.put("createdate",
					DateUtils.getDate(model.getCreatedate()));
			jsonObject.put("createdate_date",
					DateUtils.getDate(model.getCreatedate()));
			jsonObject.put("createdate_datetime",
					DateUtils.getDateTime(model.getCreatedate()));
		}
		if (model.getUpdatedate() != null) {
			jsonObject.put("updatedate",
					DateUtils.getDate(model.getUpdatedate()));
			jsonObject.put("updatedate_date",
					DateUtils.getDate(model.getUpdatedate()));
			jsonObject.put("updatedate_datetime",
					DateUtils.getDateTime(model.getUpdatedate()));
		}
		if (model.getUpdateby() != null) {
			jsonObject.put("updateby", model.getUpdateby());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Salescontract model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getContactname() != null) {
			jsonObject.put("contactname", model.getContactname());
		}
		if (model.getProjrctname() != null) {
			jsonObject.put("projrctname", model.getProjrctname());
		}
		if (model.getCompanyname() != null) {
			jsonObject.put("companyname", model.getCompanyname());
		}
		if (model.getSupplisername() != null) {
			jsonObject.put("supplisername", model.getSupplisername());
		}
		if (model.getCurrency() != null) {
			jsonObject.put("currency", model.getCurrency());
		}
		jsonObject.put("contractsum", model.getContractsum());
		jsonObject.put("paytype", model.getPaytype());
		if (model.getRemarks() != null) {
			jsonObject.put("remarks", model.getRemarks());
		}
		if (model.getAttachment() != null) {
			jsonObject.put("attachment", model.getAttachment());
		}
		jsonObject.put("status", model.getStatus());
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
		if (model.getContractno() != null) {
			jsonObject.put("contractno", model.getContractno());
		}
		if (model.getProcessname() != null) {
			jsonObject.put("processname", model.getProcessname());
		}
		jsonObject.put("processinstanceid", model.getProcessinstanceid());
		jsonObject.put("wfstatus", model.getWfstatus());
		jsonObject.put("optionalsum", model.getOptionalsum());
		jsonObject.put("firstpay", model.getFirstpay());
		jsonObject.put("lastpay", model.getLastpay());
		jsonObject.put("discount", model.getDiscount());
		if (model.getDeliverydate() != null) {
			jsonObject.put("deliverydate",
					DateUtils.getDate(model.getDeliverydate()));
			jsonObject.put("deliverydate_date",
					DateUtils.getDate(model.getDeliverydate()));
			jsonObject.put("deliverydate_datetime",
					DateUtils.getDateTime(model.getDeliverydate()));
		}
		if (model.getSales() != null) {
			jsonObject.put("sales", model.getSales());
		}
		if (model.getContractsales() != null) {
			jsonObject.put("contractsales", model.getContractsales());
		}
		jsonObject.put("giftsum", model.getGiftsum());
		if (model.getGiftremark() != null) {
			jsonObject.put("giftremark", model.getGiftremark());
		}
		if (model.getRemark() != null) {
			jsonObject.put("remark", model.getRemark());
		}
		jsonObject.put("area", model.getArea());
		if (model.getCompany() != null) {
			jsonObject.put("company", model.getCompany());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createby", model.getCreateBy());
		}
		if (model.getCreatedate() != null) {
			jsonObject.put("createdate",
					DateUtils.getDate(model.getCreatedate()));
			jsonObject.put("createdate_date",
					DateUtils.getDate(model.getCreatedate()));
			jsonObject.put("createdate_datetime",
					DateUtils.getDateTime(model.getCreatedate()));
		}
		if (model.getUpdatedate() != null) {
			jsonObject.put("updatedate",
					DateUtils.getDate(model.getUpdatedate()));
			jsonObject.put("updatedate_date",
					DateUtils.getDate(model.getUpdatedate()));
			jsonObject.put("updatedate_datetime",
					DateUtils.getDateTime(model.getUpdatedate()));
		}
		if (model.getUpdateby() != null) {
			jsonObject.put("updateby", model.getUpdateby());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<Salescontract> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Salescontract model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Salescontract> arrayToList(JSONArray array) {
		java.util.List<Salescontract> list = new java.util.ArrayList<Salescontract>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Salescontract model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private SalescontractJsonFactory() {

	}

}