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

package com.glaf.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.base.Parameter;
import com.glaf.core.base.Scheduler;
import com.glaf.core.domain.util.SchedulerJsonFactory;

@Entity
@Table(name = "SYS_SCHEDULER")
public class SchedulerEntity implements Serializable, Scheduler, JSONable {
	private static final long serialVersionUID = 1L;

	@Column(name = "attribute_", length = 500)
	protected String attribute;

	@Column(name = "autoStartup")
	protected int autoStartup;

	@Column(name = "content")
	protected String content;

	@Column(name = "createBy")
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate")
	protected Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "endDate")
	protected Date endDate;

	@Column(name = "expression_")
	protected String expression;

	@Id
	@Column(name = "id", length = 50, nullable = false)
	protected String id;

	@Column(name = "intervalTime")
	protected String intervalTime;

	/**
	 * second,minute,hour,day,week,month
	 */
	@Column(name = "intervalType")
	protected String intervalType;

	@Column(name = "intervalValue")
	protected String intervalValue;

	@Column(name = "jobClass")
	protected String jobClass;

	@javax.persistence.Transient
	protected Map<String, Parameter> jobDataMap = new java.util.HashMap<String, Parameter>();

	@Column(name = "locked_")
	protected int locked;

	@javax.persistence.Transient
	protected List<SchedulerParam> params = new java.util.ArrayList<SchedulerParam>();

	@Column(name = "priority_")
	protected int priority;

	@Column(name = "repeatCount")
	protected int repeatCount;

	@Column(name = "repeatInterval")
	protected int repeatInterval;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "startDate")
	protected Date startDate;

	@Column(name = "startDelay")
	protected int startDelay;

	@Column(name = "startup_")
	protected int startup;

	@Column(name = "taskName")
	protected String taskName;

	@Column(name = "taskType")
	protected String taskType;

	@Column(name = "threadSize")
	protected int threadSize;

	@Column(name = "title")
	protected String title;

	public SchedulerEntity() {

	}

	public void addParam(SchedulerParam param) {
		if (params == null) {
			params = new java.util.ArrayList<SchedulerParam>();
		}
		params.add(param);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SchedulerEntity other = (SchedulerEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getAttribute() {
		return attribute;
	}

	public int getAutoStartup() {
		return autoStartup;
	}

	public String getContent() {
		return content;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getExpression() {
		return expression;
	}

	public String getId() {
		return id;
	}

	public String getIntervalTime() {
		return intervalTime;
	}

	public String getIntervalType() {
		return intervalType;
	}

	public String getIntervalValue() {
		return intervalValue;
	}

	public String getJobClass() {
		return jobClass;
	}

	public Map<String, Parameter> getJobDataMap() {
		return jobDataMap;
	}

	public int getLocked() {
		return locked;
	}

	public List<SchedulerParam> getParams() {
		return params;
	}

	public int getPriority() {
		return priority;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public int getRepeatInterval() {
		return repeatInterval;
	}

	public Date getStartDate() {
		return startDate;
	}

	public int getStartDelay() {
		return startDelay;
	}

	public int getStartup() {
		return startup;
	}

	public String getTaskId() {
		return id;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTaskType() {
		return taskType;
	}

	public int getThreadSize() {
		return threadSize;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean isSchedulerAutoStartup() {
		if (autoStartup == 1) {
			return true;
		}
		return false;
	}

	public boolean isSchedulerStartup() {
		if (startup == 1) {
			return true;
		}
		return false;
	}

	public boolean isValid() {
		boolean valid = false;
		if (locked != 0) {
			return valid;
		}
		Date now = new Date();
		if (startDate != null) {
			if (endDate != null) {
				if (now.getTime() >= startDate.getTime()
						&& now.getTime() <= endDate.getTime()) {
					valid = true;
				}
			} else {
				if (now.getTime() >= startDate.getTime()) {
					valid = true;
				}
			}
		}
		return valid;
	}

	public Scheduler jsonToObject(JSONObject jsonObject) {
		return SchedulerJsonFactory.jsonToObject(jsonObject);
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setAutoStartup(boolean autoStartup) {
		if (autoStartup) {
			this.autoStartup = 1;
		} else {
			this.autoStartup = 0;
		}
	}

	public void setAutoStartup(int autoStartup) {
		this.autoStartup = autoStartup;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIntervalTime(String intervalTime) {
		this.intervalTime = intervalTime;
	}

	public void setIntervalType(String intervalType) {
		this.intervalType = intervalType;
	}

	public void setIntervalValue(String intervalValue) {
		this.intervalValue = intervalValue;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public void setJobDataMap(Map<String, Parameter> jobDataMap) {
		this.jobDataMap = jobDataMap;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setParams(List<SchedulerParam> params) {
		this.params = params;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public void setRepeatInterval(int repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStartDelay(int startDelay) {
		this.startDelay = startDelay;
	}

	public void setStartup(boolean startup) {
		if (startup) {
			this.startup = 1;
		} else {
			this.startup = 0;
		}
	}

	public void setStartup(int startup) {
		this.startup = startup;
	}

	public void setTaskId(String taskId) {
		this.id = taskId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public void setThreadSize(int threadSize) {
		this.threadSize = threadSize;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JSONObject toJsonObject() {
		return SchedulerJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SchedulerJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}