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

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class TaskItem implements Serializable, Comparable<TaskItem> {

	private static final long serialVersionUID = 1L;

	/**
	 * 参与者编号
	 */
	protected String actorId;

	/**
	 * 参与者姓名
	 */
	protected String actorName;

	/**
	 * 业务编号
	 */
	protected String rowId;

	/**
	 * 业务编号
	 */
	protected String rowKey;

	/**
	 * 任务实例编号
	 */
	protected Long taskInstanceId;

	/**
	 * 任务名称
	 */
	protected String taskName;

	/**
	 * 任务描述
	 */
	protected String taskDescription;

	/**
	 * 任务创建日期
	 */
	protected Date createDate;

	/**
	 * 任务开始时间
	 */
	protected Date startDate;

	/**
	 * 任务完成时间
	 */
	protected Date endDate;

	/**
	 * 超期天数
	 */
	protected double pastDueDay;

	/**
	 * 用时
	 */
	protected double duration;

	/**
	 * 流程名称
	 */
	protected String processName;

	/**
	 * 流程描述
	 */
	protected String processDescription;

	/**
	 * 流程定义编号
	 */
	protected Long processDefinitionId;

	/**
	 * 流程实例名称
	 */
	protected Long processInstanceId;

	/**
	 * 对象名称
	 */
	protected String objectId;

	/**
	 * 对象值
	 */
	protected String objectValue;

	protected String isAgree;

	protected String opinion;

	protected String json;

	/**
	 * 工作流状态
	 */
	protected int wfStatus;

	protected Map<String, Object> variables = new java.util.concurrent.ConcurrentHashMap<String, Object>();

	public TaskItem() {

	}

	public int compareTo(TaskItem o) {
		if (o == null) {
			return -1;
		}

		TaskItem obj = o;

		long l = taskInstanceId - obj.getTaskInstanceId();

		int ret = 0;

		if (l > 0) {
			ret = 1;
		} else if (l < 0) {
			ret = -1;
		}
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskItem other = (TaskItem) obj;
		if (taskInstanceId == null) {
			if (other.taskInstanceId != null)
				return false;
		} else if (!taskInstanceId.equals(other.taskInstanceId))
			return false;
		return true;
	}

	public String getActorId() {
		return actorId;
	}

	public String getActorName() {
		return actorName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public double getDuration() {
		return duration;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getIsAgree() {
		return isAgree;
	}

	public String getJson() {
		return json;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public String getOpinion() {
		return opinion;
	}

	public double getPastDueDay() {
		return pastDueDay;
	}

	public Long getProcessDefinitionId() {
		return processDefinitionId;
	}

	public String getProcessDescription() {
		return processDescription;
	}

	public Long getProcessInstanceId() {
		return processInstanceId;
	}

	public String getProcessName() {
		return processName;
	}

	public String getRowId() {
		if (rowId == null) {
			rowId = rowKey;
		}
		return rowId;
	}

	public String getRowKey() {
		if (rowKey == null) {
			rowKey = rowId;
		}
		return rowKey;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public Long getTaskInstanceId() {
		return taskInstanceId;
	}

	public long getTaskInstId() {
		return taskInstanceId;
	}

	public String getTaskName() {
		return taskName;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public int getWfStatus() {
		return wfStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((taskInstanceId == null) ? 0 : taskInstanceId.hashCode());
		return result;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public void setPastDueDay(double pastDueDay) {
		this.pastDueDay = pastDueDay;
	}

	public void setProcessDefinitionId(Long processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
		this.rowKey = rowId;
	}

	public void setRowKey(String rowKey) {
		this.rowId = rowKey;
		this.rowKey = rowKey;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public void setTaskInstanceId(Long taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public void setWfStatus(int wfStatus) {
		this.wfStatus = wfStatus;
	}

	public JSONObject toJsonObject() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("actorId", actorId);
		jsonObject.put("createDate", createDate);
		jsonObject.put("wfStatus", wfStatus);
		if (actorName != null) {
			jsonObject.put("actorName", actorName);
		}
		if (taskName != null) {
			jsonObject.put("taskName", taskName);
		}
		if (taskInstanceId != null) {
			jsonObject.put("taskInstanceId", taskInstanceId);
		}
		if (taskDescription != null) {
			jsonObject.put("taskDescription", taskDescription);
		}
		if (processName != null) {
			jsonObject.put("processName", processName);
		}
		if (processDescription != null) {
			jsonObject.put("processDescription", processDescription);
		}
		if (processDefinitionId != null) {
			jsonObject.put("processDefinitionId", processDefinitionId);
		}
		if (isAgree != null) {
			jsonObject.put("isAgree", isAgree);
		}
		if (opinion != null) {
			jsonObject.put("opinion", opinion);
		}
		if (startDate != null) {
			jsonObject.put("startDate", startDate);
		}
		if (endDate != null) {
			jsonObject.put("endDate", endDate);
		}
		if (objectId != null) {
			jsonObject.put("objectId", objectId);
		}
		if (objectValue != null) {
			jsonObject.put("objectValue", objectValue);
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}