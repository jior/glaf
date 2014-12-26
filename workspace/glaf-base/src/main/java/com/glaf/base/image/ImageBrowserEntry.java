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

package com.glaf.base.modules.image;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.SysAccess;

public class ImageBrowserEntry implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String actorId;

	private String name;

	private String type;

	private long size;

	public ImageBrowserEntry() {
		type = "f";
	}

	public String getActorId() {
		return actorId;
	}

	public String getName() {
		return name;
	}

	public long getSize() {
		return size;
	}

	public String getType() {
		return type;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", name);
		jsonObject.put("type", type);
		jsonObject.put("size", size);
		return jsonObject;
	}

	public ObjectNode toObjectNode(SysAccess model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("name", name);
		jsonObject.put("type", type);
		jsonObject.put("size", size);
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
