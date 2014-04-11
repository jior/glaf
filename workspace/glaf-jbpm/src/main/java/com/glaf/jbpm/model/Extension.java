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

package com.glaf.jbpm.model;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class Extension implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	protected long id = 0;
	protected String extendId = null;
	protected String name = null;
	protected String description = null;
	protected String type = null;
	protected String processName = null;
	protected String processDescription = null;
	protected String taskName = null;
	protected String taskDescription = null;
	protected String objectId = null;
	protected String objectValue = null;
	protected int locked = 0;
	protected Date createDate = null;
	protected String createActorId = null;
	protected Date updateDate = null;
	protected String updateActorId = null;
	protected List<ExtensionParam> params = new java.util.concurrent.CopyOnWriteArrayList<ExtensionParam>();
	protected Map<String, ExtensionField> fields = new java.util.concurrent.ConcurrentHashMap<String, ExtensionField>();

	public Extension() {

	}

	public void addField(ExtensionField extensionField) {
		if (fields == null) {
			fields = new java.util.concurrent.ConcurrentHashMap<String, ExtensionField>();
		}
		extensionField.setExtension(this);
		fields.put(extensionField.getName(), extensionField);
	}

	public void addParam(ExtensionParam extensionParam) {
		if (params == null) {
			params = new java.util.concurrent.CopyOnWriteArrayList<ExtensionParam>();
		}
		extensionParam.setExtension(this);
		params.add(extensionParam);
	}

	public String getCreateActorId() {
		return createActorId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getDescription() {
		return description;
	}

	public double getDoubleFieldValue(String name) {
		if (fields != null) {
			ExtensionField extensionField = fields.get(name);
			if (extensionField != null && extensionField.getValue() != null) {
				String value = extensionField.getValue();
				if (StringUtils.isNumeric(value)) {
					return Double.parseDouble(value);
				}
			}
		}
		return 0;
	}

	public String getExtendId() {
		return extendId;
	}

	public ExtensionField getField(String name) {
		if (fields != null) {
			ExtensionField extensionField = fields.get(name);
			return extensionField;
		}
		return null;
	}

	public Map<String, ExtensionField> getFields() {
		return fields;
	}

	public String getFieldValue(String name) {
		if (fields != null) {
			ExtensionField extensionField = fields.get(name);
			if (extensionField != null) {
				return extensionField.getValue();
			}
		}
		return null;
	}

	public long getId() {
		return id;
	}

	public int getIntFieldValue(String name) {
		if (fields != null) {
			ExtensionField extensionField = fields.get(name);
			if (extensionField != null && extensionField.getValue() != null) {
				String value = extensionField.getValue();
				if (StringUtils.isNumeric(value)) {
					return Integer.parseInt(value);
				}
			}
		}
		return 0;
	}

	public int getLocked() {
		return locked;
	}

	public long getLongFieldValue(String name) {
		if (fields != null) {
			ExtensionField extensionField = fields.get(name);
			if (extensionField != null && extensionField.getValue() != null) {
				String value = extensionField.getValue();
				if (StringUtils.isNumeric(value)) {
					return Long.parseLong(value);
				}
			}
		}
		return 0;
	}

	public String getName() {
		return name;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public List<ExtensionParam> getParams() {
		return params;
	}

	public String getProcessDescription() {
		return processDescription;
	}

	public String getProcessName() {
		return processName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getType() {
		return type;
	}

	public String getUpdateActorId() {
		return updateActorId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public Object getValue(String name) {
		if (fields != null) {
			ExtensionField extensionField = fields.get(name);
			if (extensionField != null) {
				return extensionField.getValue();
			}
		}
		return null;
	}

	public void setCreateActorId(String createActorId) {
		this.createActorId = createActorId;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExtendId(String extendId) {
		this.extendId = extendId;
		if (fields != null && fields.size() > 0) {
			Collection<ExtensionField> values = fields.values();
			Iterator<ExtensionField> iter = values.iterator();
			while (iter.hasNext()) {
				ExtensionField field = iter.next();
				field.setExtendId(extendId);
			}
		}
		if (params != null && params.size() > 0) {
			Iterator<ExtensionParam> iter = params.iterator();
			while (iter.hasNext()) {
				ExtensionParam param = iter.next();
				param.setExtendId(extendId);
			}
		}
	}

	public void setFields(Map<String, ExtensionField> fields) {
		this.fields = fields;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setParams(List<ExtensionParam> params) {
		this.params = params;
	}

	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUpdateActorId(String updateActorId) {
		this.updateActorId = updateActorId;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public JSONObject toJsonObject() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("extendId", extendId);
		jsonObject.put("createDate", createDate);
		if (name != null) {
			jsonObject.put("name", name);
		}
		if (description != null) {
			jsonObject.put("description", description);
		}
		if (processName != null) {
			jsonObject.put("processName", processName);
		}
		if (processDescription != null) {
			jsonObject.put("processDescription", processDescription);
		}
		if (taskName != null) {
			jsonObject.put("taskName", taskName);
		}
		if (taskDescription != null) {
			jsonObject.put("taskDescription", taskDescription);
		}
		if (objectId != null) {
			jsonObject.put("objectId", objectId);
		}
		if (objectValue != null) {
			jsonObject.put("objectValue", objectValue);
		}

		if (params != null && params.size() > 0) {
			Collection<JSONObject> rows = new java.util.concurrent.CopyOnWriteArrayList<JSONObject>();
			for (ExtensionParam param : params) {
				JSONObject json = new JSONObject();
				json.put("id", param.getId());
				json.put("name", param.getName());
				if (param.getType() != null) {
					json.put("type", param.getType());
				}
				if (param.getValue() != null) {
					json.put("value", param.getValue());
				}
				rows.add(json);
			}
			jsonObject.put("params", rows);
		}

		if (fields != null && fields.size() > 0) {
			Collection<JSONObject> rows = new java.util.concurrent.CopyOnWriteArrayList<JSONObject>();
			Set<Entry<String, ExtensionField>> entrySet = fields.entrySet();
			for (Entry<String, ExtensionField> entry : entrySet) {
				ExtensionField field = entry.getValue();
				if (field != null) {
					JSONObject json = new JSONObject();
					json.put("id", field.getId());
					json.put("name", field.getName());
					if (field.getValue() != null) {
						json.put("value", field.getValue());
					}
					rows.add(json);
				}
			}
			jsonObject.put("fields", rows);
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}