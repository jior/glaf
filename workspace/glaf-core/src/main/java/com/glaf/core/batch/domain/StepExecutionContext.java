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

package com.glaf.core.batch.domain;

import java.io.*;
import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name = "MX_STEP_EXECUTION_CONTEXT")
public class StepExecutionContext implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "step_execution_id", nullable = false)
	protected int stepExecutionId;

	/**
	 * short_context
	 */
	@Column(name = "short_context", length = 2500)
	protected String shortContext;

	/**
	 * serialized_context
	 */
	@Lob
	@Column(name = "serialized_context")
	protected String serializedContext;

	public StepExecutionContext() {

	}

	public String getSerializedContext() {
		return this.serializedContext;
	}

	public String getShortContext() {
		return this.shortContext;
	}

	public int getStepExecutionId() {
		return this.stepExecutionId;
	}

	public StepExecutionContext jsonToObject(JSONObject jsonObject) {
		StepExecutionContext model = new StepExecutionContext();
		if (jsonObject.containsKey("shortContext")) {
			model.setShortContext(jsonObject.getString("shortContext"));
		}
		if (jsonObject.containsKey("serializedContext")) {
			model.setSerializedContext(jsonObject
					.getString("serializedContext"));
		}
		return model;
	}

	public void setSerializedContext(String serializedContext) {
		this.serializedContext = serializedContext;
	}

	public void setShortContext(String shortContext) {
		this.shortContext = shortContext;
	}

	public void setStepExecutionId(int stepExecutionId) {
		this.stepExecutionId = stepExecutionId;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("stepExecutionId", stepExecutionId);
		if (shortContext != null) {
			jsonObject.put("shortContext", shortContext);
		}
		if (serializedContext != null) {
			jsonObject.put("serializedContext", serializedContext);
		}
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("stepExecutionId", stepExecutionId);
		if (shortContext != null) {
			jsonObject.put("shortContext", shortContext);
		}
		if (serializedContext != null) {
			jsonObject.put("serializedContext", serializedContext);
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}