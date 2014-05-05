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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;

/**
 * 传递活动定义
 * 
 * @author jior2008@gmail.com
 * 
 */
@Entity
@Table(name = "DTS_ACTIVITY")
public class TransformActivity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "ID_", length = 50)
	protected String id;

	/**
	 * 主题
	 */
	@Column(name = "TITLE_")
	protected String title;

	/**
	 * 执行表达式
	 */
	@Column(name = "CRONEXPRESSION_")
	protected String cronExpression;

	/**
	 * 执行周期
	 */
	@Column(name = "EXECUTECYCLE_")
	protected String executeCycle;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_", updatable = false)
	protected Date createTime;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_", updatable = false)
	protected String createBy;

	/**
	 * 描述
	 */
	@Column(name = "DESCRIPTION_", length = 500)
	protected String description;

	/**
	 * 是否锁定
	 */
	@Column(name = "LOCKED_")
	protected int locked;

	/**
	 * 修订版本
	 */
	@Column(name = "REVISION_")
	protected int revision;

	@Transient
	protected List<TransformStep> steps = new java.util.ArrayList<TransformStep>();

	public TransformActivity() {

	}

	public void addStep(TransformStep step) {
		if (steps == null) {
			steps = new java.util.ArrayList<TransformStep>();
		}
		steps.add(step);
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public String getDescription() {
		return description;
	}

	public String getExecuteCycle() {
		return executeCycle;
	}

	public String getId() {
		return id;
	}

	public int getLocked() {
		return locked;
	}

	public int getRevision() {
		return revision;
	}

	public List<TransformStep> getSteps() {
		return steps;
	}

	public String getTitle() {
		return title;
	}

	public TransformActivity jsonToObject(JSONObject jsonObject) {
		TransformActivity model = new TransformActivity();
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("cronExpression")) {
			model.setCronExpression(jsonObject.getString("cronExpression"));
		}
		if (jsonObject.containsKey("executeCycle")) {
			model.setExecuteCycle(jsonObject.getString("executeCycle"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("description")) {
			model.setDescription(jsonObject.getString("description"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("revision")) {
			model.setRevision(jsonObject.getInteger("revision"));
		}
		return model;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExecuteCycle(String executeCycle) {
		this.executeCycle = executeCycle;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public void setSteps(List<TransformStep> steps) {
		this.steps = steps;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (cronExpression != null) {
			jsonObject.put("cronExpression", cronExpression);
		}
		if (executeCycle != null) {
			jsonObject.put("executeCycle", executeCycle);
		}
		if (createTime != null) {
			jsonObject.put("createTime", DateUtils.getDate(createTime));
			jsonObject.put("createTime_date", DateUtils.getDate(createTime));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(createTime));
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		if (description != null) {
			jsonObject.put("description", description);
		}
		jsonObject.put("locked", locked);
		jsonObject.put("revision", revision);
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (cronExpression != null) {
			jsonObject.put("cronExpression", cronExpression);
		}
		if (executeCycle != null) {
			jsonObject.put("executeCycle", executeCycle);
		}
		if (createTime != null) {
			jsonObject.put("createTime", DateUtils.getDate(createTime));
			jsonObject.put("createTime_date", DateUtils.getDate(createTime));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(createTime));
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		if (description != null) {
			jsonObject.put("description", description);
		}
		jsonObject.put("locked", locked);
		jsonObject.put("revision", revision);
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}