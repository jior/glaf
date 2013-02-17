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

package com.glaf.base.query;

import java.util.Date;
import java.util.List;

public class DataQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	protected String actorId;
	protected List<String> appActorIds;
	protected String businessKey;
	protected String formName;
	protected String dataInstanceId;
	protected String processName;
	protected String processNameLike;
	protected List<String> processNames;
	protected String processInstanceId;
	protected String parentId;
	protected String objectId;
	protected String objectValue;
	protected Integer status;
	protected Integer statusNotEqual;
	protected Integer statusGreaterThanOrEqual;
	protected Integer statusLessThanOrEqual;
	protected Integer wfstatus;
	protected Integer wfstatusNotEqual;
	protected Integer wfstatusGreaterThanOrEqual;
	protected Integer wfstatusLessThanOrEqual;
	protected Integer deleteFlag;
	protected String workedProcessFlag;
	protected Date createDate;
	protected Date beforeCreateDate;
	protected Date afterCreateDate;

	public DataQuery() {

	}

	public DataQuery actorId(String actorId) {
		if (actorId == null) {
			throw new RuntimeException("actorId is null");
		}
		this.actorId = actorId;
		return this;
	}

	public DataQuery afterCreateDate(Date afterCreateDate) {
		if (afterCreateDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.afterCreateDate = afterCreateDate;
		return this;
	}

	public DataQuery appActorIds(List<String> appActorIds) {
		if (appActorIds == null) {
			throw new RuntimeException("appActorIds is null");
		}
		this.appActorIds = appActorIds;
		return this;
	}

	public DataQuery beforeCreateDate(Date beforeCreateDate) {
		if (beforeCreateDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.beforeCreateDate = beforeCreateDate;
		return this;
	}

	public DataQuery businessKey(String businessKey) {
		if (businessKey == null) {
			throw new RuntimeException("BusinessKey is null");
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

	public DataQuery dataInstanceId(String dataInstanceId) {
		if (dataInstanceId == null) {
			throw new RuntimeException("dataInstanceId is null");
		}
		this.dataInstanceId = dataInstanceId;
		return this;
	}

	public DataQuery deleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
		return this;
	}

	public String getActorId() {
		return actorId;
	}

	public Date getAfterCreateDate() {
		return afterCreateDate;
	}

	public List<String> getAppActorIds() {
		return appActorIds;
	}

	public Date getBeforeCreateDate() {
		return beforeCreateDate;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getDataInstanceId() {
		return dataInstanceId;
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

	public String getParentId() {
		return parentId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
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

	public Integer getWfstatus() {
		return wfstatus;
	}

	public Integer getWfstatusGreaterThanOrEqual() {
		return wfstatusGreaterThanOrEqual;
	}

	public Integer getWfstatusLessThanOrEqual() {
		return wfstatusLessThanOrEqual;
	}

	public Integer getWfstatusNotEqual() {
		return wfstatusNotEqual;
	}

	public String getWorkedProcessFlag() {
		return workedProcessFlag;
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

	public DataQuery parentId(String parentId) {
		if (parentId == null) {
			throw new RuntimeException("parentId is null");
		}
		this.parentId = parentId;
		return this;
	}

	public DataQuery processInstanceId(String processInstanceId) {
		if (processInstanceId == null) {
			throw new RuntimeException("Process instance id is null");
		}
		this.processInstanceId = processInstanceId;
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

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setAfterCreateDate(Date afterCreateDate) {
		this.afterCreateDate = afterCreateDate;
	}

	public void setAppActorIds(List<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setBeforeCreateDate(Date beforeCreateDate) {
		this.beforeCreateDate = beforeCreateDate;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDataInstanceId(String dataInstanceId) {
		this.dataInstanceId = dataInstanceId;
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

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
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

	public void setWfstatus(Integer wfstatus) {
		this.wfstatus = wfstatus;
	}

	public void setWfstatusGreaterThanOrEqual(Integer wfstatusGreaterThanOrEqual) {
		this.wfstatusGreaterThanOrEqual = wfstatusGreaterThanOrEqual;
	}

	public void setWfstatusLessThanOrEqual(Integer wfstatusLessThanOrEqual) {
		this.wfstatusLessThanOrEqual = wfstatusLessThanOrEqual;
	}

	public void setWfstatusNotEqual(Integer wfstatusNotEqual) {
		this.wfstatusNotEqual = wfstatusNotEqual;
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

	public DataQuery wfstatus(Integer wfstatus) {
		this.wfstatus = wfstatus;
		return this;
	}

	public DataQuery wfstatusGreaterThanOrEqual(
			Integer wfstatusGreaterThanOrEqual) {
		if (wfstatusGreaterThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusGreaterThanOrEqual = wfstatusGreaterThanOrEqual;
		return this;
	}

	public DataQuery wfstatusLessThanOrEqual(Integer wfstatusLessThanOrEqual) {
		if (wfstatusLessThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusLessThanOrEqual = wfstatusLessThanOrEqual;
		return this;
	}

	public DataQuery wfstatusNotEqual(Integer wfstatusNotEqual) {
		if (wfstatusNotEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusNotEqual = wfstatusNotEqual;
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
