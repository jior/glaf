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
package com.glaf.form.core.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.form.core.util.FormLinkJsonFactory;

@Entity
@Table(name = "FORM_LINK")
public class FormLink implements java.lang.Comparable<FormLink>, Serializable,
		JSONable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 应用名称
	 */
	@Basic
	@Column(name = "APPId_")
	protected String appId;

	/**
	 * 子应用名称
	 */
	@Basic
	@Column(name = "CHILDAPPID_")
	protected String childAppId;

	@Column(name = "OBJECTID_")
	protected String objectId;

	@Column(name = "OBJECTVALUE_")
	protected String objectValue;

	/**
	 * 关联关系，一对一（1）还是一对多（2）
	 */
	@Basic
	@Column(name = "ONETOMANY_")
	protected int oneToMany;

	/**
	 * 关联表单的排序字段（表单定义节点名称）
	 */
	@Basic
	@Column(name = "ORDERBY_")
	protected String orderBy;

	/**
	 * 顺序号
	 */
	@Basic
	@Column(name = "SORTNO_")
	protected int sortNo;

	public FormLink() {

	}

	public int compareTo(FormLink o) {
		if (o == null) {
			return -1;
		}

		FormLink field = o;

		int l = this.sortNo - field.getSortNo();

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
		FormLink other = (FormLink) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getAppId() {
		return appId;
	}

	public String getChildAppId() {
		return childAppId;
	}

	public String getId() {
		return id;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public int getOneToMany() {
		return oneToMany;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public int getSortNo() {
		return sortNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public FormLink jsonToObject(JSONObject jsonObject) {
		return FormLinkJsonFactory.jsonToObject(jsonObject);
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setChildAppId(String childAppId) {
		this.childAppId = childAppId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setOneToMany(int oneToMany) {
		this.oneToMany = oneToMany;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public JSONObject toJsonObject() {
		return FormLinkJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormLinkJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}