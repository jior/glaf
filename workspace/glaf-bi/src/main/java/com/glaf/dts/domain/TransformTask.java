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
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;

@Entity
@Table(name = "SYS_DTS_TASK")
public class TransformTask implements java.io.Serializable {

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
	 * 查询编号
	 */
	@Column(name = "QUERYID_", length = 50)
	protected String queryId;

	/**
	 * 表名
	 */
	@Column(name = "TABLENAME_", length = 50)
	protected String tableName;

	/**
	 * 主题
	 */
	@Column(name = "TITLE_")
	protected String title;

	/**
	 * 查询参数
	 */
	@Lob
	@Column(name = "PARAMETER_")
	protected String parameter;

	/**
	 * 开始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTTIME_")
	protected Date startTime;

	/**
	 * 结束时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDTIME_")
	protected Date endTime;

	/**
	 * 顺序号
	 */
	@Column(name = "SORTNO_")
	protected int sortNo;

	@Column(name = "DURATION_")
	protected long duration;
	/**
	 * 状态，0-代表未执行，1-执行中，2-处理错误，9-处理成功
	 */
	@Column(name = "STATUS_")
	protected int status;

	/**
	 * 出错后的重试次数
	 */
	@Column(name = "RETRYTIMES_")
	protected int retryTimes;

	public TransformTask() {

	}

	public String getActivityId() {
		return activityId;
	}

	public long getDuration() {
		return duration;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getId() {
		return id;
	}

	public String getParameter() {
		return parameter;
	}

	public String getQueryId() {
		return queryId;
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public int getSortNo() {
		return sortNo;
	}

	public Date getStartTime() {
		return startTime;
	}

	public int getStatus() {
		return status;
	}

	public String getStepId() {
		return stepId;
	}

	public String getTableName() {
		return tableName;
	}

	public String getTitle() {
		return title;
	}

	public TransformTask jsonToObject(JSONObject jsonObject) {
		TransformTask model = new TransformTask();
		if (jsonObject.containsKey("activityId")) {
			model.setActivityId(jsonObject.getString("activityId"));
		}
		if (jsonObject.containsKey("stepId")) {
			model.setStepId(jsonObject.getString("stepId"));
		}
		if (jsonObject.containsKey("queryId")) {
			model.setQueryId(jsonObject.getString("queryId"));
		}
		if (jsonObject.containsKey("tableName")) {
			model.setTableName(jsonObject.getString("tableName"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("parameter")) {
			model.setParameter(jsonObject.getString("parameter"));
		}
		if (jsonObject.containsKey("startTime")) {
			model.setStartTime(jsonObject.getDate("startTime"));
		}
		if (jsonObject.containsKey("endTime")) {
			model.setEndTime(jsonObject.getDate("endTime"));
		}
		if (jsonObject.containsKey("sortNo")) {
			model.setSortNo(jsonObject.getInteger("sortNo"));
		}
		if (jsonObject.containsKey("duration")) {
			model.setDuration(jsonObject.getLong("duration"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("retryTimes")) {
			model.setRetryTimes(jsonObject.getInteger("retryTimes"));
		}
		return model;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTitle(String title) {
		this.title = title;
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
		if (queryId != null) {
			jsonObject.put("queryId", queryId);
		}
		if (tableName != null) {
			jsonObject.put("tableName", tableName);
		}
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (parameter != null) {
			jsonObject.put("parameter", parameter);
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
		jsonObject.put("sortNo", sortNo);
		jsonObject.put("duration", duration);
		jsonObject.put("status", status);
		jsonObject.put("retryTimes", retryTimes);
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
		if (queryId != null) {
			jsonObject.put("queryId", queryId);
		}
		if (tableName != null) {
			jsonObject.put("tableName", tableName);
		}
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (parameter != null) {
			jsonObject.put("parameter", parameter);
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
		jsonObject.put("sortNo", sortNo);
		jsonObject.put("duration", duration);
		jsonObject.put("status", status);
		jsonObject.put("retryTimes", retryTimes);
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}