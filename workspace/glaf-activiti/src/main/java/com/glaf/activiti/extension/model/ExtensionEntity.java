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

package com.glaf.activiti.extension.model;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.activiti.engine.impl.util.json.JSONException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "EX_ACT_EXTENSION")
public class ExtensionEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Column(name = "EXTENDID_", nullable = false)
	protected String extendId = null;

	@Column(name = "NAME_")
	protected String name = null;

	@Column(name = "TYPE_")
	protected String type = null;

	@Column(name = "DESCRIPTION_")
	protected String description = null;

	@Column(name = "PROCESSNAME_")
	protected String processName = null;

	@Transient
	protected String processDescription = null;

	@Column(name = "TASKNAME_")
	protected String taskName = null;

	@Transient
	protected String taskDescription = null;

	@Column(name = "OBJECTID_")
	protected String objectId = null;

	@Column(name = "OBJECTVALUE_")
	protected String objectValue = null;

	@Column(name = "LOCKED_")
	protected int locked = 0;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate = null;

	@Column(name = "CREATEBY_")
	protected String createBy = null;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE_")
	protected Date updateDate = null;

	@Column(name = "UPDATEBY_")
	protected String updateBy = null;

	@Transient
	protected List<ExtensionParamEntity> params = new java.util.ArrayList<ExtensionParamEntity>();

	@Transient
	protected Map<String, ExtensionFieldEntity> fields = new java.util.HashMap<String, ExtensionFieldEntity>();

	public ExtensionEntity() {

	}

	public void addField(ExtensionFieldEntity extensionField) {
		if (fields == null) {
			fields = new java.util.HashMap<String, ExtensionFieldEntity>();
		}
		extensionField.setExtension(this);
		fields.put(extensionField.getName(), extensionField);
	}

	public void addParam(ExtensionParamEntity extensionParam) {
		if (params == null) {
			params = new java.util.ArrayList<ExtensionParamEntity>();
		}
		extensionParam.setExtension(this);
		params.add(extensionParam);
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getDescription() {
		return description;
	}

	public double getDoubleFieldValue(String name) {
		if (fields != null) {
			ExtensionFieldEntity extensionField = fields.get(name);
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

	public ExtensionFieldEntity getField(String name) {
		if (fields != null) {
			ExtensionFieldEntity extensionField = fields.get(name);
			return extensionField;
		}
		return null;
	}

	public Map<String, ExtensionFieldEntity> getFields() {
		return fields;
	}

	public String getFieldValue(String name) {
		if (fields != null) {
			ExtensionFieldEntity extensionField = fields.get(name);
			if (extensionField != null) {
				return extensionField.getValue();
			}
		}
		return null;
	}

	public String getId() {
		return id;
	}

	public int getIntFieldValue(String name) {
		if (fields != null) {
			ExtensionFieldEntity extensionField = fields.get(name);
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
			ExtensionFieldEntity extensionField = fields.get(name);
			if (extensionField != null && extensionField.getValue() != null) {
				String value = extensionField.getValue();
				if (StringUtils.isNotEmpty(value)) {
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

	public List<ExtensionParamEntity> getParams() {
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

	public String getUpdateBy() {
		return updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public Object getValue(String name) {
		if (fields != null) {
			ExtensionFieldEntity extensionField = fields.get(name);
			if (extensionField != null) {
				return extensionField.getValue();
			}
		}
		return null;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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
			Collection<ExtensionFieldEntity> values = fields.values();
			Iterator<ExtensionFieldEntity> iter = values.iterator();
			while (iter.hasNext()) {
				ExtensionFieldEntity field = iter.next();
				field.setExtendId(extendId);
			}
		}
		if (params != null && params.size() > 0) {
			Iterator<ExtensionParamEntity> iter = params.iterator();
			while (iter.hasNext()) {
				ExtensionParamEntity param = iter.next();
				param.setExtendId(extendId);
			}
		}
	}

	public void setFields(Map<String, ExtensionFieldEntity> fields) {
		this.fields = fields;
	}

	public void setId(String id) {
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

	public void setParams(List<ExtensionParamEntity> params) {
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

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
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
			Collection<JSONObject> rows = new java.util.ArrayList<JSONObject>();
			for (ExtensionParamEntity param : params) {
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
			Collection<JSONObject> rows = new java.util.ArrayList<JSONObject>();
			Set<Entry<String, ExtensionFieldEntity>> entrySet = fields
					.entrySet();
			for (Entry<String, ExtensionFieldEntity> entry : entrySet) {
				ExtensionFieldEntity field = entry.getValue();
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

}