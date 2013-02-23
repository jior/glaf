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
import java.util.*;

import javax.persistence.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
 
import com.glaf.core.base.Parameter;
import com.glaf.core.base.Scheduler;

@Entity
@Table(name = "MX_SYS_SCHEDULER")
public class SchedulerEntity implements Serializable, Scheduler {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Column(name = "TASKID_")
	protected String taskId;

	@Column(name = "TASKNAME_")
	protected String taskName;

	@Column(name = "TASKTYPE_")
	protected String taskType;

	@Column(name = "JOBCLASS_")
	protected String jobClass;

	@Column(name = "TITLE_")
	protected String title;

	@Column(name = "CONTENT_")
	protected String content;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTDATE_")
	protected Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDDATE_")
	protected Date endDate;

	@Column(name = "THREADSIZE_")
	protected int threadSize;

	@Column(name = "REPEATCOUNT_")
	protected int repeatCount;

	@Column(name = "REPEATINTERVAL_")
	protected int repeatInterval;

	@Column(name = "STARTDELAY_")
	protected int startDelay;

	@Column(name = "PRIORITY_")
	protected int priority;

	@Column(name = "LOCKED_")
	protected int locked;

	@Column(name = "STARTUP_")
	protected String startup;

	@Column(name = "AUTOSTARTUP_")
	protected String autoStartup;

	@Column(name = "EXPRESSION_")
	protected String expression;

	@Column(name = "ATTRIBUTE_", length = 500)
	protected String attribute;

	@Column(name = "CREATEBY_")
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	@javax.persistence.Transient
	protected Map<String, Parameter> jobDataMap = new HashMap<String, Parameter>();

	@javax.persistence.Transient
	protected List<SchedulerParam> params = new ArrayList<SchedulerParam>();

	public SchedulerEntity() {

	}

	public void addParam(SchedulerParam param) {
		if (params == null) {
			params = new ArrayList<SchedulerParam>();
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

	public String getAutoStartup() {
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

	public String getStartup() {
		return startup;
	}

	public String getTaskId() {
		return taskId;
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

	public boolean isAutoStartup() {
		if (StringUtils.equalsIgnoreCase(autoStartup, "1")
				|| StringUtils.equalsIgnoreCase(autoStartup, "Y")
				|| StringUtils.equalsIgnoreCase(autoStartup, "true")) {
			return true;
		}
		return false;
	}

	public boolean isStartup() {
		if (StringUtils.equalsIgnoreCase(startup, "1")
				|| StringUtils.equalsIgnoreCase(startup, "Y")
				|| StringUtils.equalsIgnoreCase(startup, "true")) {
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

	public SchedulerEntity jsonToObject(JSONObject jsonObject) {
		SchedulerEntity model = new SchedulerEntity();
		if (jsonObject.containsKey("taskId")) {
			model.setTaskId(jsonObject.getString("taskId"));
		}
		if (jsonObject.containsKey("taskName")) {
			model.setTaskName(jsonObject.getString("taskName"));
		}
		if (jsonObject.containsKey("taskType")) {
			model.setTaskType(jsonObject.getString("taskType"));
		}
		if (jsonObject.containsKey("jobClass")) {
			model.setJobClass(jsonObject.getString("jobClass"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("startDate")) {
			model.setStartDate(jsonObject.getDate("startDate"));
		}
		if (jsonObject.containsKey("endDate")) {
			model.setEndDate(jsonObject.getDate("endDate"));
		}
		if (jsonObject.containsKey("threadSize")) {
			model.setThreadSize(jsonObject.getInteger("threadSize"));
		}
		if (jsonObject.containsKey("repeatCount")) {
			model.setRepeatCount(jsonObject.getInteger("repeatCount"));
		}
		if (jsonObject.containsKey("repeatInterval")) {
			model.setRepeatInterval(jsonObject.getInteger("repeatInterval"));
		}
		if (jsonObject.containsKey("startDelay")) {
			model.setStartDelay(jsonObject.getInteger("startDelay"));
		}
		if (jsonObject.containsKey("priority")) {
			model.setPriority(jsonObject.getInteger("priority"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("startup")) {
			model.setStartup(jsonObject.getString("startup"));
		}
		if (jsonObject.containsKey("autoStartup")) {
			model.setAutoStartup(jsonObject.getString("autoStartup"));
		}
		if (jsonObject.containsKey("expression")) {
			model.setExpression(jsonObject.getString("expression"));
		}
		if (jsonObject.containsKey("attribute")) {
			model.setAttribute(jsonObject.getString("attribute"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		return model;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setAutoStartup(boolean autoStartup) {
		if (autoStartup) {
			this.autoStartup = "1";
		} else {
			this.autoStartup = "0";
		}
	}

	public void setAutoStartup(String autoStartup) {
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
			this.startup = "1";
		} else {
			this.startup = "0";
		}
	}

	public void setStartup(String startup) {
		this.startup = startup;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		if (taskId != null) {
			jsonObject.put("taskId", taskId);
		}
		if (taskName != null) {
			jsonObject.put("taskName", taskName);
		}
		if (taskType != null) {
			jsonObject.put("taskType", taskType);
		}
		if (jobClass != null) {
			jsonObject.put("jobClass", jobClass);
		}
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (content != null) {
			jsonObject.put("content", content);
		}
		if (startDate != null) {
			jsonObject.put("startDate", DateUtils.getDate(startDate));
			jsonObject.put("startDate_date", DateUtils.getDate(startDate));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(startDate));
		}
		if (endDate != null) {
			jsonObject.put("endDate", DateUtils.getDate(endDate));
			jsonObject.put("endDate_date", DateUtils.getDate(endDate));
			jsonObject.put("endDate_datetime", DateUtils.getDateTime(endDate));
		}
		jsonObject.put("threadSize", threadSize);
		jsonObject.put("repeatCount", repeatCount);
		jsonObject.put("repeatInterval", repeatInterval);
		jsonObject.put("startDelay", startDelay);
		jsonObject.put("priority", priority);
		jsonObject.put("locked", locked);
		if (startup != null) {
			jsonObject.put("startup", startup);
		}
		if (autoStartup != null) {
			jsonObject.put("autoStartup", autoStartup);
		}
		if (expression != null) {
			jsonObject.put("expression", expression);
		}
		if (attribute != null) {
			jsonObject.put("attribute", attribute);
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		if (taskId != null) {
			jsonObject.put("taskId", taskId);
		}
		if (taskName != null) {
			jsonObject.put("taskName", taskName);
		}
		if (taskType != null) {
			jsonObject.put("taskType", taskType);
		}
		if (jobClass != null) {
			jsonObject.put("jobClass", jobClass);
		}
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (content != null) {
			jsonObject.put("content", content);
		}
		if (startDate != null) {
			jsonObject.put("startDate", DateUtils.getDate(startDate));
			jsonObject.put("startDate_date", DateUtils.getDate(startDate));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(startDate));
		}
		if (endDate != null) {
			jsonObject.put("endDate", DateUtils.getDate(endDate));
			jsonObject.put("endDate_date", DateUtils.getDate(endDate));
			jsonObject.put("endDate_datetime", DateUtils.getDateTime(endDate));
		}
		jsonObject.put("threadSize", threadSize);
		jsonObject.put("repeatCount", repeatCount);
		jsonObject.put("repeatInterval", repeatInterval);
		jsonObject.put("startDelay", startDelay);
		jsonObject.put("priority", priority);
		jsonObject.put("locked", locked);
		if (startup != null) {
			jsonObject.put("startup", startup);
		}
		if (autoStartup != null) {
			jsonObject.put("autoStartup", autoStartup);
		}
		if (expression != null) {
			jsonObject.put("expression", expression);
		}
		if (attribute != null) {
			jsonObject.put("attribute", attribute);
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}