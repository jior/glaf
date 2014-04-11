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

package com.glaf.dts.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;

/**
 * 传递活动执行情况
 * 
 * @author jior2008@gmail.com
 * 
 */
@Entity
@Table(name = "DTS_EXCECUTION")
public class TransformExcecution implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "ID_", length = 50)
	protected String id;

	/**
	 * 活动编号
	 */
	@Column(name = "ACTIVITYID_", length = 50)
	protected String activityId;

	/**
	 * 步骤编号
	 */
	@Column(name = "STEPID_", length = 50)
	protected String stepId;

	/**
	 * 开始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTTIME_", updatable = false)
	protected Date startTime;

	/**
	 * 结束时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDTIME_", updatable = false)
	protected Date endTime;

	/**
	 * 优先级(级别高的先执行)
	 */
	@Column(name = "PRIORITY_")
	protected int priority;

	/**
	 * 是否执行
	 */
	@Column(name = "EXECUTE_", length = 10)
	protected String execute;

	/**
	 * 执行是否成功
	 */
	@Column(name = "SUCCESS_", length = 10)
	protected String success;

	public TransformExcecution() {

	}

	public String getActivityId() {
		return activityId;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getExecute() {
		return execute;
	}

	public String getId() {
		return id;
	}

	public int getPriority() {
		return priority;
	}

	public Date getStartTime() {
		return startTime;
	}

	public String getStepId() {
		return stepId;
	}

	public String getSuccess() {
		return success;
	}

	public boolean isExecutable() {
		if (StringUtils.equalsIgnoreCase(execute, "1")
				|| StringUtils.equalsIgnoreCase(execute, "Y")
				|| StringUtils.equalsIgnoreCase(execute, "true")) {
			return true;
		}
		return false;
	}

	public boolean isSuccessful() {
		if (StringUtils.equalsIgnoreCase(success, "1")
				|| StringUtils.equalsIgnoreCase(success, "Y")
				|| StringUtils.equalsIgnoreCase(success, "true")) {
			return true;
		}
		return false;
	}

	public TransformExcecution jsonToObject(JSONObject jsonObject) {
		TransformExcecution model = new TransformExcecution();
		if (jsonObject.containsKey("activityId")) {
			model.setActivityId(jsonObject.getString("activityId"));
		}
		if (jsonObject.containsKey("stepId")) {
			model.setStepId(jsonObject.getString("stepId"));
		}
		if (jsonObject.containsKey("startTime")) {
			model.setStartTime(jsonObject.getDate("startTime"));
		}
		if (jsonObject.containsKey("endTime")) {
			model.setEndTime(jsonObject.getDate("endTime"));
		}
		if (jsonObject.containsKey("priority")) {
			model.setPriority(jsonObject.getInteger("priority"));
		}
		if (jsonObject.containsKey("execute")) {
			model.setExecute(jsonObject.getString("execute"));
		}
		if (jsonObject.containsKey("success")) {
			model.setSuccess(jsonObject.getString("success"));
		}
		return model;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setExecutable(boolean executable) {
		if (executable) {
			this.execute = "1";
		} else {
			this.execute = "0";
		}
	}

	public void setExecute(String execute) {
		this.execute = execute;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public void setSuccessful(boolean successful) {
		if (successful) {
			this.success = "1";
		} else {
			this.success = "0";
		}
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		if (activityId != null) {
			jsonObject.put("activityId", activityId);
		}
		if (stepId != null) {
			jsonObject.put("stepId", stepId);
		}
		if (startTime != null) {
			jsonObject.put("startTime", DateUtils.getDate(startTime));
			jsonObject.put("startTime_date", DateUtils.getDate(startTime));
			jsonObject.put("startTime_datetime",
					DateUtils.getDateTime(startTime));
		}
		if (endTime != null) {
			jsonObject.put("endTime", DateUtils.getDate(endTime));
			jsonObject.put("endTime_date", DateUtils.getDate(endTime));
			jsonObject.put("endTime_datetime", DateUtils.getDateTime(endTime));
		}
		jsonObject.put("priority", priority);
		if (execute != null) {
			jsonObject.put("execute", execute);
		}
		if (success != null) {
			jsonObject.put("success", success);
		}
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		if (activityId != null) {
			jsonObject.put("activityId", activityId);
		}
		if (stepId != null) {
			jsonObject.put("stepId", stepId);
		}
		if (startTime != null) {
			jsonObject.put("startTime", DateUtils.getDate(startTime));
			jsonObject.put("startTime_date", DateUtils.getDate(startTime));
			jsonObject.put("startTime_datetime",
					DateUtils.getDateTime(startTime));
		}
		if (endTime != null) {
			jsonObject.put("endTime", DateUtils.getDate(endTime));
			jsonObject.put("endTime_date", DateUtils.getDate(endTime));
			jsonObject.put("endTime_datetime", DateUtils.getDateTime(endTime));
		}
		jsonObject.put("priority", priority);
		if (execute != null) {
			jsonObject.put("execute", execute);
		}
		if (success != null) {
			jsonObject.put("success", success);
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}