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

package com.glaf.core.query;

import java.util.Date;
import java.util.List;

public class EntityEntryQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Long nodeId;
	protected List<Long> nodeIds;
	protected String entityId;
	protected List<String> entityIds;
	protected String entryKey;
	protected List<String> entryKeys;
	protected Integer entryType;
	protected Integer entryTypeGreaterThanOrEqual;
	protected Integer entryTypeLessThanOrEqual;
	protected List<Integer> entryTypes;
	protected String moduleId;
	protected List<String> moduleIds;
	protected String dataCode;
	protected List<String> dataCodes;
	protected Boolean isPropagationAllowed;
	protected Date startDateGreaterThanOrEqual;
	protected Date startDateLessThanOrEqual;
	protected Date endDateGreaterThanOrEqual;
	protected Date endDateLessThanOrEqual;
	protected List<String> objectIds;
	protected List<String> objectValues;

	public EntityEntryQuery() {

	}

	public EntityEntryQuery dataCode(String dataCode) {
		if (dataCode == null) {
			throw new RuntimeException("dataCode is null");
		}
		this.dataCode = dataCode;
		return this;
	}

	public EntityEntryQuery dataCodes(List<String> dataCodes) {
		if (dataCodes == null) {
			throw new RuntimeException("dataCodes is empty ");
		}
		this.dataCodes = dataCodes;
		return this;
	}

	public EntityEntryQuery endDateGreaterThanOrEqual(
			Date endDateGreaterThanOrEqual) {
		if (endDateGreaterThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
		return this;
	}

	public EntityEntryQuery endDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		if (endDateLessThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
		return this;
	}

	public EntityEntryQuery entityId(String entityId) {
		if (entityId == null) {
			throw new RuntimeException("entityId is null");
		}
		this.entityId = entityId;
		return this;
	}

	public EntityEntryQuery entityIds(List<String> entityIds) {
		if (entityIds == null) {
			throw new RuntimeException("entityIds is empty ");
		}
		this.entityIds = entityIds;
		return this;
	}

	public EntityEntryQuery entryKey(String entryKey) {
		if (entryKey == null) {
			throw new RuntimeException("entryKey is null");
		}
		this.entryKey = entryKey;
		return this;
	}

	public EntityEntryQuery entryKeys(List<String> entryKeys) {
		if (entryKeys == null) {
			throw new RuntimeException("entryKeys is empty ");
		}
		this.entryKeys = entryKeys;
		return this;
	}

	public EntityEntryQuery entryType(Integer entryType) {
		if (entryType == null) {
			throw new RuntimeException("entryType is null");
		}
		this.entryType = entryType;
		return this;
	}

	public EntityEntryQuery entryTypeGreaterThanOrEqual(
			Integer entryTypeGreaterThanOrEqual) {
		if (entryTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("entryType is null");
		}
		this.entryTypeGreaterThanOrEqual = entryTypeGreaterThanOrEqual;
		return this;
	}

	public EntityEntryQuery entryTypeLessThanOrEqual(
			Integer entryTypeLessThanOrEqual) {
		if (entryTypeLessThanOrEqual == null) {
			throw new RuntimeException("entryType is null");
		}
		this.entryTypeLessThanOrEqual = entryTypeLessThanOrEqual;
		return this;
	}

	public EntityEntryQuery entryTypes(List<Integer> entryTypes) {
		if (entryTypes == null) {
			throw new RuntimeException("entryTypes is empty ");
		}
		this.entryTypes = entryTypes;
		return this;
	}

	public String getDataCode() {
		return dataCode;
	}

	public List<String> getDataCodes() {
		return dataCodes;
	}

	public Date getEndDateGreaterThanOrEqual() {
		return endDateGreaterThanOrEqual;
	}

	public Date getEndDateLessThanOrEqual() {
		return endDateLessThanOrEqual;
	}

	public String getEntityId() {
		return entityId;
	}

	public List<String> getEntityIds() {
		return entityIds;
	}

	public String getEntryKey() {
		return entryKey;
	}

	public List<String> getEntryKeys() {
		return entryKeys;
	}

	public Integer getEntryType() {
		return entryType;
	}

	public Integer getEntryTypeGreaterThanOrEqual() {
		return entryTypeGreaterThanOrEqual;
	}

	public Integer getEntryTypeLessThanOrEqual() {
		return entryTypeLessThanOrEqual;
	}

	public List<Integer> getEntryTypes() {
		return entryTypes;
	}

	public Boolean getIsPropagationAllowed() {
		return isPropagationAllowed;
	}

	public String getModuleId() {
		return moduleId;
	}

	public List<String> getModuleIds() {
		return moduleIds;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public List<Long> getNodeIds() {
		return nodeIds;
	}

	public List<String> getObjectIds() {
		return objectIds;
	}

	public List<String> getObjectValues() {
		return objectValues;
	}

	public Date getStartDateGreaterThanOrEqual() {
		return startDateGreaterThanOrEqual;
	}

	public Date getStartDateLessThanOrEqual() {
		return startDateLessThanOrEqual;
	}

	public EntityEntryQuery isPropagationAllowed(Boolean isPropagationAllowed) {
		if (isPropagationAllowed == null) {
			throw new RuntimeException("isPropagationAllowed is null");
		}
		this.isPropagationAllowed = isPropagationAllowed;
		return this;
	}

	public EntityEntryQuery moduleId(String moduleId) {
		if (moduleId == null) {
			throw new RuntimeException("moduleId is null");
		}
		this.moduleId = moduleId;
		return this;
	}

	public EntityEntryQuery moduleIds(List<String> moduleIds) {
		if (moduleIds == null) {
			throw new RuntimeException("moduleIds is empty ");
		}
		this.moduleIds = moduleIds;
		return this;
	}

	public EntityEntryQuery nodeId(Long nodeId) {
		if (nodeId == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeId = nodeId;
		return this;
	}

	public EntityEntryQuery nodeIds(List<Long> nodeIds) {
		if (nodeIds == null) {
			throw new RuntimeException("nodeIds is empty ");
		}
		this.nodeIds = nodeIds;
		return this;
	}

	public EntityEntryQuery objectIds(List<String> objectIds) {
		if (objectIds == null) {
			throw new RuntimeException("objectIds is empty ");
		}
		this.objectIds = objectIds;
		return this;
	}

	public EntityEntryQuery objectValues(List<String> objectValues) {
		if (objectValues == null) {
			throw new RuntimeException("objectValues is empty ");
		}
		this.objectValues = objectValues;
		return this;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public void setDataCodes(List<String> dataCodes) {
		this.dataCodes = dataCodes;
	}

	public void setEndDateGreaterThanOrEqual(Date endDateGreaterThanOrEqual) {
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
	}

	public void setEndDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public void setEntityIds(List<String> entityIds) {
		this.entityIds = entityIds;
	}

	public void setEntryKey(String entryKey) {
		this.entryKey = entryKey;
	}

	public void setEntryKeys(List<String> entryKeys) {
		this.entryKeys = entryKeys;
	}

	public void setEntryType(Integer entryType) {
		this.entryType = entryType;
	}

	public void setEntryTypeGreaterThanOrEqual(
			Integer entryTypeGreaterThanOrEqual) {
		this.entryTypeGreaterThanOrEqual = entryTypeGreaterThanOrEqual;
	}

	public void setEntryTypeLessThanOrEqual(Integer entryTypeLessThanOrEqual) {
		this.entryTypeLessThanOrEqual = entryTypeLessThanOrEqual;
	}

	public void setEntryTypes(List<Integer> entryTypes) {
		this.entryTypes = entryTypes;
	}

	public void setIsPropagationAllowed(Boolean isPropagationAllowed) {
		this.isPropagationAllowed = isPropagationAllowed;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public void setModuleIds(List<String> moduleIds) {
		this.moduleIds = moduleIds;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodeIds(List<Long> nodeIds) {
		this.nodeIds = nodeIds;
	}

	public void setObjectIds(List<String> objectIds) {
		this.objectIds = objectIds;
	}

	public void setObjectValues(List<String> objectValues) {
		this.objectValues = objectValues;
	}

	public void setStartDateGreaterThanOrEqual(Date startDateGreaterThanOrEqual) {
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
	}

	public void setStartDateLessThanOrEqual(Date startDateLessThanOrEqual) {
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
	}

	public EntityEntryQuery startDateGreaterThanOrEqual(
			Date startDateGreaterThanOrEqual) {
		if (startDateGreaterThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
		return this;
	}

	public EntityEntryQuery startDateLessThanOrEqual(
			Date startDateLessThanOrEqual) {
		if (startDateLessThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
		return this;
	}

}