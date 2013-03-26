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

package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.core.util.DateUtils;

public class DictoryJsonFactory {

	public static Dictory jsonToObject(JSONObject jsonObject) {
		Dictory model = new Dictory();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("value")) {
			model.setValue(jsonObject.getString("value"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}
		if (jsonObject.containsKey("blocked")) {
			model.setBlocked(jsonObject.getInteger("blocked"));
		}
		if (jsonObject.containsKey("ext1")) {
			model.setExt1(jsonObject.getString("ext1"));
		}
		if (jsonObject.containsKey("ext2")) {
			model.setExt2(jsonObject.getString("ext2"));
		}
		if (jsonObject.containsKey("ext3")) {
			model.setExt3(jsonObject.getString("ext3"));
		}
		if (jsonObject.containsKey("ext4")) {
			model.setExt4(jsonObject.getString("ext4"));
		}
		if (jsonObject.containsKey("ext5")) {
			model.setExt5(jsonObject.getDate("ext5"));
		}
		if (jsonObject.containsKey("ext6")) {
			model.setExt6(jsonObject.getDate("ext6"));
		}
		if (jsonObject.containsKey("ext7")) {
			model.setExt7(jsonObject.getString("ext7"));
		}
		if (jsonObject.containsKey("ext8")) {
			model.setExt8(jsonObject.getString("ext8"));
		}
		if (jsonObject.containsKey("ext9")) {
			model.setExt9(jsonObject.getLong("ext9"));
		}
		if (jsonObject.containsKey("ext10")) {
			model.setExt10(jsonObject.getLong("ext10"));
		}
		if (jsonObject.containsKey("ext11")) {
			model.setExt11(jsonObject.getDouble("ext11"));
		}
		if (jsonObject.containsKey("ext12")) {
			model.setExt12(jsonObject.getDouble("ext12"));
		}
		if (jsonObject.containsKey("ext13")) {
			model.setExt13(jsonObject.getDouble("ext13"));
		}
		if (jsonObject.containsKey("ext14")) {
			model.setExt14(jsonObject.getDouble("ext14"));
		}
		if (jsonObject.containsKey("ext15")) {
			model.setExt15(jsonObject.getDouble("ext15"));
		}

		return model;
	}

	public static JSONObject toJsonObject(Dictory model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("nodeId", model.getNodeId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		if (model.getValue() != null) {
			jsonObject.put("value", model.getValue());
		}
		jsonObject.put("sort", model.getSort());
		jsonObject.put("blocked", model.getBlocked());
		if (model.getExt1() != null) {
			jsonObject.put("ext1", model.getExt1());
		}
		if (model.getExt2() != null) {
			jsonObject.put("ext2", model.getExt2());
		}
		if (model.getExt3() != null) {
			jsonObject.put("ext3", model.getExt3());
		}
		if (model.getExt4() != null) {
			jsonObject.put("ext4", model.getExt4());
		}
		if (model.getExt5() != null) {
			jsonObject.put("ext5", DateUtils.getDate(model.getExt5()));
			jsonObject.put("ext5_date", DateUtils.getDate(model.getExt5()));
			jsonObject.put("ext5_datetime",
					DateUtils.getDateTime(model.getExt5()));
		}
		if (model.getExt6() != null) {
			jsonObject.put("ext6", DateUtils.getDate(model.getExt6()));
			jsonObject.put("ext6_date", DateUtils.getDate(model.getExt6()));
			jsonObject.put("ext6_datetime",
					DateUtils.getDateTime(model.getExt6()));
		}

		if (model.getExt7() != null) {
			jsonObject.put("ext7", model.getExt7());
		}
		if (model.getExt8() != null) {
			jsonObject.put("ext8", model.getExt8());
		}
		if (model.getExt9() != null) {
			jsonObject.put("ext9", model.getExt9());
		}
		if (model.getExt10() != null) {
			jsonObject.put("ext10", model.getExt10());
		}
		if (model.getExt11() != null) {
			jsonObject.put("ext11", model.getExt11());
		}
		if (model.getExt12() != null) {
			jsonObject.put("ext12", model.getExt12());
		}
		if (model.getExt13() != null) {
			jsonObject.put("ext13", model.getExt13());
		}
		if (model.getExt14() != null) {
			jsonObject.put("ext14", model.getExt14());
		}
		if (model.getExt15() != null) {
			jsonObject.put("ext15", model.getExt15());
		}

		return jsonObject;
	}

	public static ObjectNode toObjectNode(Dictory model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("nodeId", model.getNodeId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		if (model.getValue() != null) {
			jsonObject.put("value", model.getValue());
		}
		jsonObject.put("sort", model.getSort());
		jsonObject.put("blocked", model.getBlocked());
		if (model.getExt1() != null) {
			jsonObject.put("ext1", model.getExt1());
		}
		if (model.getExt2() != null) {
			jsonObject.put("ext2", model.getExt2());
		}
		if (model.getExt3() != null) {
			jsonObject.put("ext3", model.getExt3());
		}
		if (model.getExt4() != null) {
			jsonObject.put("ext4", model.getExt4());
		}
		if (model.getExt5() != null) {
			jsonObject.put("ext5", DateUtils.getDate(model.getExt5()));
			jsonObject.put("ext5_date", DateUtils.getDate(model.getExt5()));
			jsonObject.put("ext5_datetime",
					DateUtils.getDateTime(model.getExt5()));
		}
		if (model.getExt6() != null) {
			jsonObject.put("ext6", DateUtils.getDate(model.getExt6()));
			jsonObject.put("ext6_date", DateUtils.getDate(model.getExt6()));
			jsonObject.put("ext6_datetime",
					DateUtils.getDateTime(model.getExt6()));
		}

		if (model.getExt7() != null) {
			jsonObject.put("ext7", model.getExt7());
		}
		if (model.getExt8() != null) {
			jsonObject.put("ext8", model.getExt8());
		}
		if (model.getExt9() != null) {
			jsonObject.put("ext9", model.getExt9());
		}
		if (model.getExt10() != null) {
			jsonObject.put("ext10", model.getExt10());
		}
		if (model.getExt11() != null) {
			jsonObject.put("ext11", model.getExt11());
		}
		if (model.getExt12() != null) {
			jsonObject.put("ext12", model.getExt12());
		}
		if (model.getExt13() != null) {
			jsonObject.put("ext13", model.getExt13());
		}
		if (model.getExt14() != null) {
			jsonObject.put("ext14", model.getExt14());
		}
		if (model.getExt15() != null) {
			jsonObject.put("ext15", model.getExt15());
		}

		return jsonObject;
	}

	private DictoryJsonFactory() {

	}

}
