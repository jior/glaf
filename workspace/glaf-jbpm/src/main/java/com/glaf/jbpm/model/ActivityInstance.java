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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class ActivityInstance implements Serializable,
		Comparable<ActivityInstance> {
	private static final long serialVersionUID = 1L;

	protected String actorId;

	protected String actorName;

	protected String content;

	protected Date date;

	protected Long id;

	protected String isAgree;

	protected String objectId;

	protected String objectValue;

	protected String previousActors;

	protected Long processInstanceId;

	protected String rowId;

	protected String taskDescription;

	protected Long taskInstanceId;

	protected String taskName;

	protected String title;

	protected String variable;

	public ActivityInstance() {

	}

	public int compareTo(ActivityInstance o) {
		if (o == null) {
			return -1;
		}

		ActivityInstance obj = o;

		long l = id - obj.getId();

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
		ActivityInstance other = (ActivityInstance) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getActorId() {
		return actorId;
	}

	public String getActorName() {
		return actorName;
	}

	public String getContent() {
		return content;
	}

	public Date getDate() {
		return date;
	}

	public Long getId() {
		return id;
	}

	public String getIsAgree() {
		return isAgree;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public String getPreviousActors() {
		return previousActors;
	}

	public Long getProcessInstanceId() {
		return processInstanceId;
	}

	public String getRowId() {
		return rowId;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public Long getTaskInstanceId() {
		return taskInstanceId;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTitle() {
		return title;
	}

	public String getVariable() {
		return variable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setPreviousActors(String previousActors) {
		this.previousActors = previousActors;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
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

	public void setTitle(String title) {
		this.title = title;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public JSONObject toJsonObject() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("actorId", actorId);
		jsonObject.put("date", date);
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
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (content != null) {
			jsonObject.put("content", content);
		}
		if (isAgree != null) {
			jsonObject.put("isAgree", isAgree);
		}
		if (objectId != null) {
			jsonObject.put("objectId", objectId);
		}
		if (objectValue != null) {
			jsonObject.put("objectValue", objectValue);
		}
		if (variable != null) {
			jsonObject.put("variable", variable);
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}