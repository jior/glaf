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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;

/**
 * 传递活动步骤定义
 * 
 * @author jior2008@gmail.com
 * 
 */
@Entity
@Table(name = "SYS_DTS_STEP")
public class TransformStep implements java.io.Serializable {

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
	 * 查询集编号
	 */
	@Column(name = "QUERYID_", length = 50)
	protected String queryId;

	/**
	 * 主题
	 */
	@Column(name = "TITLE_")
	protected String title;

	/**
	 * 描述
	 */
	@Column(name = "DESCRIPTION_", length = 500)
	protected String description;

	/**
	 * 优先级(级别低的先执行，即按照从低到高的顺序执行,优先级相同的同步执行)
	 */
	@Column(name = "PRIORITY_")
	protected int priority;

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
	 * 是否锁定
	 */
	@Column(name = "LOCKED_")
	protected int locked;

	/**
	 * 修订版本
	 */
	@Column(name = "REVISION_")
	protected int revision;

	public TransformStep() {

	}

	public int compareTo(TransformStep o) {
		if (o == null) {
			return -1;
		}

		TransformStep obj = o;

		int l = this.priority - obj.getPriority();

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
		TransformStep other = (TransformStep) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getActivityId() {
		return activityId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getDescription() {
		return description;
	}

	public String getId() {
		return id;
	}

	public int getLocked() {
		return locked;
	}

	public int getPriority() {
		return priority;
	}

	public String getQueryId() {
		return queryId;
	}

	public int getRevision() {
		return revision;
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

	public TransformStep jsonToObject(JSONObject jsonObject) {
		TransformStep model = new TransformStep();
		if (jsonObject.containsKey("activityId")) {
			model.setActivityId(jsonObject.getString("activityId"));
		}
		if (jsonObject.containsKey("queryId")) {
			model.setQueryId(jsonObject.getString("queryId"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("description")) {
			model.setDescription(jsonObject.getString("description"));
		}
		if (jsonObject.containsKey("priority")) {
			model.setPriority(jsonObject.getInteger("priority"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("revision")) {
			model.setRevision(jsonObject.getInteger("revision"));
		}
		return model;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public void setRevision(int revision) {
		this.revision = revision;
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
		if (queryId != null) {
			jsonObject.put("queryId", queryId);
		}
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (description != null) {
			jsonObject.put("description", description);
		}
		jsonObject.put("priority", priority);
		if (createTime != null) {
			jsonObject.put("createTime", DateUtils.getDate(createTime));
			jsonObject.put("createTime_date", DateUtils.getDate(createTime));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(createTime));
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		jsonObject.put("locked", locked);
		jsonObject.put("revision", revision);
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		if (activityId != null) {
			jsonObject.put("activityId", activityId);
		}
		if (queryId != null) {
			jsonObject.put("queryId", queryId);
		}
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (description != null) {
			jsonObject.put("description", description);
		}
		jsonObject.put("priority", priority);
		if (createTime != null) {
			jsonObject.put("createTime", DateUtils.getDate(createTime));
			jsonObject.put("createTime_date", DateUtils.getDate(createTime));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(createTime));
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
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