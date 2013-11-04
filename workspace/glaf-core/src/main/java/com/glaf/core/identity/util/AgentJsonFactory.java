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

package com.glaf.core.identity.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.identity.Agent;
import com.glaf.core.identity.impl.AgentImpl;
import com.glaf.core.util.DateUtils;

public class AgentJsonFactory {

	public static Agent jsonToObject(JSONObject jsonObject) {
		Agent model = new AgentImpl();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("assignFrom")) {
			model.setAssignFrom(jsonObject.getString("assignFrom"));
		}
		if (jsonObject.containsKey("assignTo")) {
			model.setAssignTo(jsonObject.getString("assignTo"));
		}

		if (jsonObject.containsKey("agentType")) {
			model.setAgentType(jsonObject.getInteger("agentType"));
		}

		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}

		if (jsonObject.containsKey("startDate")) {
			model.setStartDate(jsonObject.getDate("startDate"));
		}

		if (jsonObject.containsKey("endDate")) {
			model.setEndDate(jsonObject.getDate("endDate"));
		}

		if (jsonObject.containsKey("serviceKey")) {
			model.setServiceKey(jsonObject.getString("serviceKey"));
		}

		if (jsonObject.containsKey("processName")) {
			model.setProcessName(jsonObject.getString("processName"));
		}

		if (jsonObject.containsKey("taskName")) {
			model.setTaskName(jsonObject.getString("taskName"));
		}

		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}

		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}

		return model;
	}

	public static JSONObject toJsonObject(Agent agent) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", agent.getId());
		jsonObject.put("assignFrom", agent.getAssignFrom());
		jsonObject.put("assignTo", agent.getAssignTo());
		jsonObject.put("agentType", agent.getAgentType());
		jsonObject.put("createDate", agent.getCreateDate());
		jsonObject.put("serviceKey", agent.getServiceKey());
		if (agent.getProcessName() != null) {
			jsonObject.put("processName", agent.getProcessName());
		}
		if (agent.getTaskName() != null) {
			jsonObject.put("taskName", agent.getTaskName());
		}
		if (agent.getStartDate() != null) {
			jsonObject.put("startDate", agent.getStartDate());
		}
		if (agent.getEndDate() != null) {
			jsonObject.put("endDate", agent.getEndDate());
		}
		if (agent.getObjectId() != null) {
			jsonObject.put("objectId", agent.getObjectId());
		}
		if (agent.getObjectValue() != null) {
			jsonObject.put("objectValue", agent.getObjectValue());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Agent agent) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", agent.getId());
		jsonObject.put("assignFrom", agent.getAssignFrom());
		jsonObject.put("assignTo", agent.getAssignTo());
		jsonObject.put("agentType", agent.getAgentType());
		jsonObject.put("createDate",
				DateUtils.getDateTime(agent.getCreateDate()));
		jsonObject.put("serviceKey", agent.getServiceKey());
		if (agent.getProcessName() != null) {
			jsonObject.put("processName", agent.getProcessName());
		}
		if (agent.getTaskName() != null) {
			jsonObject.put("taskName", agent.getTaskName());
		}
		if (agent.getStartDate() != null) {
			jsonObject.put("startDate",
					DateUtils.getDateTime(agent.getStartDate()));
		}
		if (agent.getEndDate() != null) {
			jsonObject
					.put("endDate", DateUtils.getDateTime(agent.getEndDate()));
		}
		if (agent.getObjectId() != null) {
			jsonObject.put("objectId", agent.getObjectId());
		}
		if (agent.getObjectValue() != null) {
			jsonObject.put("objectValue", agent.getObjectValue());
		}
		return jsonObject;
	}

}
