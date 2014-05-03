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

package com.glaf.core.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.StringTools;

public class DataModelEntity implements DataModel, Serializable {
	private static final long serialVersionUID = 1L;
	protected String actorName;
	protected String businessKey;
	protected String createBy;
	protected Date createDate;
	protected Map<String, Object> dataMap = new java.util.HashMap<String, Object>();
	protected int deleteFlag;
	protected String fallbackFlag;
	protected Map<String, ColumnModel> fields = new java.util.HashMap<String, ColumnModel>();
	protected String formName;
	protected Long id;
	protected int level;
	protected int listNo;
	protected int locked;
	protected String name;
	protected String objectId;
	protected String objectValue;
	protected Long parentId;
	protected String processInstanceId;
	protected String processName;
	protected String serviceKey;
	protected String signForFlag;
	protected int status;
	protected String subject;
	protected String tableName;
	protected String taskName;
	protected String treeId;
	protected String typeId;
	protected String updateBy;
	protected Date updateDate;
	protected int wfStatus;
	protected List<ColumnDefinition> columns = new java.util.ArrayList<ColumnDefinition>();

	public DataModelEntity() {

	}

	public void setColumns(List<ColumnDefinition> columns) {
		this.columns = columns;
	}

	public void addField(ColumnModel field) {
		if (fields == null) {
			fields = new java.util.HashMap<String, ColumnModel>();
		}
		fields.put(field.getName(), field);

		if (dataMap == null) {
			dataMap = new java.util.HashMap<String, Object>();
		}
		dataMap.put(field.getName(), field.getValue());

	}

	public void addField(String columnName, String key, Object value) {
		if (dataMap == null) {
			dataMap = new java.util.HashMap<String, Object>();
		}
		dataMap.put(key, value);
		ColumnModel field = new ColumnModel();
		field.setColumnName(columnName);
		field.setName(key);
		field.setValue(value);
	}

	public String getActorName() {
		return actorName;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public Collection<ColumnModel> getColumns() {
		return fields.values();
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Map<String, Object> getDataMap() {
		if (dataMap == null) {
			dataMap = new java.util.HashMap<String, Object>();
		}
		return dataMap;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public String getFallbackFlag() {
		return fallbackFlag;
	}

	public Map<String, ColumnModel> getFields() {
		return fields;
	}

	public String getFormName() {
		return formName;
	}

	public Long getId() {
		return id;
	}

	public int getLevel() {
		return level;
	}

	public int getListNo() {
		return listNo;
	}

	public int getLocked() {
		return locked;
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

	public Long getParentId() {
		return parentId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public String getProcessName() {
		return processName;
	}

	public String getServiceKey() {
		return serviceKey;
	}

	public String getSignForFlag() {
		return signForFlag;
	}

	public int getStatus() {
		return status;
	}

	public String getString(String key) {
		if (key != null && dataMap != null) {
			Object value = dataMap.get(key);
			if (value != null) {
				if (value instanceof Date) {
					return DateUtils.getDate((Date) value);
				}
				return value.toString();
			}
		}
		return "";
	}

	public String getSubject() {
		return subject;
	}

	public String getTableName() {
		return tableName;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTreeId() {
		return treeId;
	}

	public String getTypeId() {
		return typeId;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public int getWfStatus() {
		return wfStatus;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setFallbackFlag(String fallbackFlag) {
		this.fallbackFlag = fallbackFlag;
	}

	public void setFields(Map<String, ColumnModel> fields) {
		this.fields = fields;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setListNo(int listNo) {
		this.listNo = listNo;
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

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public void setSignForFlag(String signForFlag) {
		this.signForFlag = signForFlag;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setWfStatus(int wfStatus) {
		this.wfStatus = wfStatus;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();

		if (dataMap != null && dataMap.size() > 0) {
			dataMap.remove("dataMap");
			Map<String, Object> rowMap = new java.util.HashMap<String, Object>();
			rowMap.putAll(dataMap);
			// dataMap.remove(StringTools.lower(this.getFormName()));
			Set<Entry<String, Object>> entrySet = dataMap.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {
					jsonObject.put(name, value);
					rowMap.put(name.toLowerCase(), value);
				}
			}
			if (columns != null && !columns.isEmpty()) {
				for (ColumnDefinition col : columns) {
					Object value = rowMap
							.get(col.getColumnName().toUpperCase());
					if (value != null) {
						if (value instanceof Date) {
							Date date = (Date) value;
							jsonObject.put(col.getName(),
									DateUtils.getDate(date));
							jsonObject.put(col.getName() + "_datetime",
									DateUtils.getDateTime(date));
							jsonObject.put(col.getColumnName(),
									DateUtils.getDate(date));
							jsonObject.put(col.getColumnName() + "_datetime",
									DateUtils.getDateTime(date));
						} else {
							jsonObject.put(col.getColumnName(), value);
							jsonObject.put(col.getName(), value);
						}
					}
				}
			}
		}
		jsonObject.put("id", id);
		jsonObject.put("parentId", parentId);

		if (businessKey != null) {
			jsonObject.put("businessKey", businessKey);
		}
		if (subject != null) {
			jsonObject.put("subject", subject);
		}
		if (formName != null) {
			jsonObject.put("formName", formName);
		}
		if (processInstanceId != null) {
			jsonObject.put("processInstanceId", processInstanceId);
		}
		if (processName != null) {
			jsonObject.put("processName", processName);
		}
		if (objectId != null) {
			jsonObject.put("objectId", objectId);
		}
		if (objectValue != null) {
			jsonObject.put("objectValue", objectValue);
		}
		if (createDate != null) {
			jsonObject.put("createDate", createDate);
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		if (updateDate != null) {
			jsonObject.put("updateDate", updateDate);
		}
		if (updateBy != null) {
			jsonObject.put("updateBy", updateBy);
		}

		if (signForFlag != null) {
			jsonObject.put("signForFlag", signForFlag);
		}

		jsonObject.put("status", status);
		jsonObject.put("wfStatus", wfStatus);

		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();

		if (dataMap != null && dataMap.size() > 0) {
			dataMap.remove("dataMap");
			dataMap.remove(StringTools.lower(this.getFormName()));
			Set<Entry<String, Object>> entrySet = dataMap.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {
					if (value instanceof Integer) {
						Integer x = (Integer) value;
						jsonObject.put(name, x);
					} else if (value instanceof Long) {
						Long x = (Long) value;
						jsonObject.put(name, x);
					} else if (value instanceof Double) {
						Double x = (Double) value;
						jsonObject.put(name, x);
					} else if (value instanceof Date) {
						Date x = (Date) value;
						jsonObject.put(name, DateUtils.getDate(x));
						jsonObject.put(name + "_datetime",
								DateUtils.getDateTime(x));
					} else if (value instanceof java.math.BigDecimal) {
						java.math.BigDecimal x = (java.math.BigDecimal) value;
						jsonObject.put(name, x);
					} else if (value instanceof String) {
						String x = (String) value;
						jsonObject.put(name, x);
					} else {
						jsonObject.put(name, value.toString());
					}
				}
			}
		}
		jsonObject.put("id", id);
		jsonObject.put("parentId", parentId);

		if (businessKey != null) {
			jsonObject.put("businessKey", businessKey);
		}
		if (subject != null) {
			jsonObject.put("subject", subject);
		}
		if (formName != null) {
			jsonObject.put("formName", formName);
		}
		if (processInstanceId != null) {
			jsonObject.put("processInstanceId", processInstanceId);
		}
		if (processName != null) {
			jsonObject.put("processName", processName);
		}
		if (objectId != null) {
			jsonObject.put("objectId", objectId);
		}
		if (objectValue != null) {
			jsonObject.put("objectValue", objectValue);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		if (updateDate != null) {
			jsonObject.put("updateDate", DateUtils.getDate(updateDate));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(updateDate));
		}
		if (updateBy != null) {
			jsonObject.put("updateBy", updateBy);
		}

		if (signForFlag != null) {
			jsonObject.put("signForFlag", signForFlag);
		}

		jsonObject.put("status", status);
		jsonObject.put("wfStatus", wfStatus);

		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}