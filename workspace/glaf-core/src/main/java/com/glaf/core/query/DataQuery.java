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

@SuppressWarnings("rawtypes")
public class DataQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	protected String businessKey;
	protected Date createDate;
	protected Integer deleteFlag;
	protected String formName;
	protected String objectId;
	protected String objectValue;
	protected Long parentId;
	protected List<Long> parentIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
	protected Object processInstanceId;
	protected List processInstanceIds = new java.util.concurrent.CopyOnWriteArrayList();
	protected boolean processInstanceIsNotNull;
	protected boolean processInstanceIsNull;
	protected String processName;
	protected String processNameLike;
	protected List<String> processNames;
	protected String sortColumn;
	protected Integer status;
	protected Integer statusGreaterThanOrEqual;
	protected Integer statusLessThanOrEqual;
	protected Integer statusNotEqual;
	protected List taskInstanceIds = new java.util.concurrent.CopyOnWriteArrayList();
	protected String treeId;
	protected String treeIdLike;
	protected Integer wfStatus;
	protected Integer wfStatusGreaterThanOrEqual;
	protected Integer wfStatusLessThanOrEqual;
	protected Integer wfStatusNotEqual;
	protected String workedProcessFlag;

	public DataQuery() {

	}

	public DataQuery businessKey(String businessKey) {
		if (businessKey == null) {
			throw new RuntimeException("businessKey is null");
		}
		this.businessKey = businessKey;
		return this;
	}

	public DataQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public DataQuery deleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
		return this;
	}

	public void ensureInitialized() {
		super.ensureInitialized();

		if (processInstanceIds != null && !processInstanceIds.isEmpty()) {
			isFilterPermission = false;
		}

	}

	public String getBusinessKey() {
		return businessKey;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public String getFormName() {
		return formName;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public boolean getOnlyDataModels() {
		return true;
	}

	public Long getParentId() {
		return parentId;
	}

	public List<Long> getParentIds() {
		return parentIds;
	}

	public Object getProcessInstanceId() {
		return processInstanceId;
	}

	public List getProcessInstanceIds() {
		return processInstanceIds;
	}

	public String getProcessName() {
		return processName;
	}

	public String getProcessNameLike() {
		return processNameLike;
	}

	public List<String> getProcessNames() {
		return processNames;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getStatusGreaterThanOrEqual() {
		return statusGreaterThanOrEqual;
	}

	public Integer getStatusLessThanOrEqual() {
		return statusLessThanOrEqual;
	}

	public Integer getStatusNotEqual() {
		return statusNotEqual;
	}

	public List getTaskInstanceIds() {
		return taskInstanceIds;
	}

	public String getTreeId() {
		return treeId;
	}

	public String getTreeIdLike() {
		if (treeIdLike != null && !treeIdLike.endsWith("%")) {
			treeIdLike = treeIdLike + "%";
		}
		return treeIdLike;
	}

	public Integer getWfStatus() {
		return wfStatus;
	}

	public Integer getWfStatusGreaterThanOrEqual() {
		return wfStatusGreaterThanOrEqual;
	}

	public Integer getWfStatusLessThanOrEqual() {
		return wfStatusLessThanOrEqual;
	}

	public Integer getWfStatusNotEqual() {
		return wfStatusNotEqual;
	}

	public String getWorkedProcessFlag() {
		return workedProcessFlag;
	}

	public boolean isProcessInstanceIsNotNull() {
		return processInstanceIsNotNull;
	}

	public boolean isProcessInstanceIsNull() {
		return processInstanceIsNull;
	}

	public DataQuery objectId(String objectId) {
		if (objectId == null) {
			throw new RuntimeException("objectId is null");
		}
		this.objectId = objectId;
		return this;
	}

	public DataQuery objectValue(String objectValue) {
		if (objectValue == null) {
			throw new RuntimeException("objectValue is null");
		}
		this.objectValue = objectValue;
		return this;
	}

	public DataQuery parentId(Long parentId) {
		if (parentId == null) {
			throw new RuntimeException("parentId is null");
		}
		this.parentId = parentId;
		return this;
	}

	public DataQuery parentIds(List<Long> parentIds) {
		if (parentIds == null) {
			throw new RuntimeException("parentIds is null");
		}
		this.parentIds = parentIds;
		return this;
	}

	public DataQuery processInstanceId(String processInstanceId) {
		if (processInstanceId == null) {
			throw new RuntimeException("Process instance id is null");
		}
		this.processInstanceId = processInstanceId;
		return this;
	}

	public BaseQuery processInstanceIds(List processInstanceIds) {
		if (processInstanceIds == null) {
			throw new RuntimeException("Process instance id is null");
		}
		this.processInstanceIds = processInstanceIds;
		return this;
	}

	public DataQuery processName(String processName) {
		if (processName == null) {
			throw new RuntimeException("Process definition key is null");
		}
		this.processName = processName;
		return this;
	}

	public DataQuery processNameLike(String processNameLike) {
		if (processNameLike == null) {
			throw new RuntimeException("processName is null");
		}
		this.processNameLike = processNameLike;
		return this;
	}

	public DataQuery processNames(List<String> processNames) {
		if (processNames == null) {
			throw new RuntimeException("processNames is empty ");
		}
		this.processNames = processNames;
		return this;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setFormName(String formName) {
		this.formName = formName;
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

	public void setParentIds(List<Long> parentIds) {
		this.parentIds = parentIds;
	}

	public void setProcessInstanceId(Object processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setProcessInstanceIds(List processInstanceIds) {
		this.processInstanceIds = processInstanceIds;
	}

	public void setProcessInstanceIsNotNull(boolean processInstanceIsNotNull) {
		this.processInstanceIsNotNull = processInstanceIsNotNull;
	}

	public void setProcessInstanceIsNull(boolean processInstanceIsNull) {
		this.processInstanceIsNull = processInstanceIsNull;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setProcessNameLike(String processNameLike) {
		this.processNameLike = processNameLike;
	}

	public void setProcessNames(List<String> processNames) {
		this.processNames = processNames;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatusGreaterThanOrEqual(Integer statusGreaterThanOrEqual) {
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
	}

	public void setStatusLessThanOrEqual(Integer statusLessThanOrEqual) {
		this.statusLessThanOrEqual = statusLessThanOrEqual;
	}

	public void setStatusNotEqual(Integer statusNotEqual) {
		this.statusNotEqual = statusNotEqual;
	}

	public void setTaskInstanceIds(List taskInstanceIds) {
		this.taskInstanceIds = taskInstanceIds;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public void setTreeIdLike(String treeIdLike) {
		this.treeIdLike = treeIdLike;
	}

	public void setWfStatus(Integer wfStatus) {
		this.wfStatus = wfStatus;
	}

	public void setWfStatusGreaterThanOrEqual(Integer wfStatusGreaterThanOrEqual) {
		this.wfStatusGreaterThanOrEqual = wfStatusGreaterThanOrEqual;
	}

	public void setWfStatusLessThanOrEqual(Integer wfStatusLessThanOrEqual) {
		this.wfStatusLessThanOrEqual = wfStatusLessThanOrEqual;
	}

	public void setWfStatusNotEqual(Integer wfStatusNotEqual) {
		this.wfStatusNotEqual = wfStatusNotEqual;
	}

	public void setWorkedProcessFlag(String workedProcessFlag) {
		this.workedProcessFlag = workedProcessFlag;
	}

	public DataQuery status(Integer status) {
		this.status = status;
		return this;
	}

	public DataQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual) {
		if (statusGreaterThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
		return this;
	}

	public DataQuery statusLessThanOrEqual(Integer statusLessThanOrEqual) {
		if (statusLessThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusLessThanOrEqual = statusLessThanOrEqual;
		return this;
	}

	public DataQuery statusNotEqual(Integer statusNotEqual) {
		if (statusNotEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusNotEqual = statusNotEqual;
		return this;
	}

	public DataQuery treeId(String treeId) {
		if (treeId == null) {
			throw new RuntimeException("treeId is null");
		}
		this.treeId = treeId;
		return this;
	}

	public DataQuery treeIdLike(String treeIdLike) {
		if (treeIdLike == null) {
			throw new RuntimeException("treeIdLike is null");
		}
		this.treeIdLike = treeIdLike;
		return this;
	}

	public DataQuery wfStatus(Integer wfStatus) {
		this.wfStatus = wfStatus;
		return this;
	}

	public DataQuery wfStatusGreaterThanOrEqual(
			Integer wfStatusGreaterThanOrEqual) {
		if (wfStatusGreaterThanOrEqual == null) {
			throw new RuntimeException("wfStatus is null");
		}
		this.wfStatusGreaterThanOrEqual = wfStatusGreaterThanOrEqual;
		return this;
	}

	public DataQuery wfStatusLessThanOrEqual(Integer wfStatusLessThanOrEqual) {
		if (wfStatusLessThanOrEqual == null) {
			throw new RuntimeException("wfStatus is null");
		}
		this.wfStatusLessThanOrEqual = wfStatusLessThanOrEqual;
		return this;
	}

	public DataQuery wfStatusNotEqual(Integer wfStatusNotEqual) {
		if (wfStatusNotEqual == null) {
			throw new RuntimeException("wfStatus is null");
		}
		this.wfStatusNotEqual = wfStatusNotEqual;
		return this;
	}

	public DataQuery workedProcessFlag(String workedProcessFlag) {
		if (workedProcessFlag == null) {
			throw new RuntimeException("workedProcessFlag is null");
		}
		this.workedProcessFlag = workedProcessFlag;
		return this;
	}

}