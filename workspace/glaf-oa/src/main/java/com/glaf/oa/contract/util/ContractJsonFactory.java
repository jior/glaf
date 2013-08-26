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
package com.glaf.oa.contract.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.oa.contract.model.*;

public class ContractJsonFactory {

	public static Contract jsonToObject(JSONObject jsonObject) {
		Contract model = new Contract();
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
					.getString("processinstanceid"));
		}
		if (jsonObject.containsKey("wfstatus")) {
			model.setWfstatus(jsonObject.getDouble("wfstatus"));
		}
		if (jsonObject.containsKey("appusername")) {
			model.setAppusername(jsonObject.getString("appusername"));
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
		if (jsonObject.containsKey("area")) {
			model.setArea(jsonObject.getString("area"));
		}
		if (jsonObject.containsKey("post")) {
			model.setPost(jsonObject.getString("post"));
		}
		if (jsonObject.containsKey("dept")) {
			model.setDept(jsonObject.getString("dept"));
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

	public static JSONObject toJsonObject(Contract model) {
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
		if (model.getProcessinstanceid() != null) {
			jsonObject.put("processinstanceid", model.getProcessinstanceid());
		}
		jsonObject.put("wfstatus", model.getWfstatus());
		if (model.getAppusername() != null) {
			jsonObject.put("appusername", model.getAppusername());
		}
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
		if (model.getArea() != null) {
			jsonObject.put("area", model.getArea());
		}
		if (model.getPost() != null) {
			jsonObject.put("post", model.getPost());
		}
		if (model.getDept() != null) {
			jsonObject.put("dept", model.getDept());
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

	public static ObjectNode toObjectNode(Contract model) {
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
		if (model.getProcessinstanceid() != null) {
			jsonObject.put("processinstanceid", model.getProcessinstanceid());
		}
		jsonObject.put("wfstatus", model.getWfstatus());
		if (model.getAppusername() != null) {
			jsonObject.put("appusername", model.getAppusername());
		}
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
		if (model.getArea() != null) {
			jsonObject.put("area", model.getArea());
		}
		if (model.getPost() != null) {
			jsonObject.put("post", model.getPost());
		}
		if (model.getDept() != null) {
			jsonObject.put("dept", model.getDept());
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

	public static JSONArray listToArray(java.util.List<Contract> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Contract model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Contract> arrayToList(JSONArray array) {
		java.util.List<Contract> list = new java.util.ArrayList<Contract>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Contract model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private ContractJsonFactory() {

	}

}