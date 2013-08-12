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
package com.glaf.oa.assessinfo.model;

import java.io.Serializable;
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
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.oa.assessinfo.util.AssessinfoJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "oa_assessinfo")
public class Assessinfo implements Serializable, JSONable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "indexid", nullable = false)
	protected Long indexid;

	@Column(name = "name", length = 200)
	protected String name;

	@Column(name = "basis")
	protected String basis;

	@Column(name = "standard")
	protected Double standard;

	@Column(name = "iseffective")
	protected Integer iseffective;

	@Column(name = "createBy", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate")
	protected Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateDate")
	protected Date updateDate;

	@Column(name = "updateBy", length = 50)
	protected String updateBy;

	public Assessinfo() {

	}

	public Long getIndexid() {

		return this.indexid;
	}

	public void setIndexid(Long indexid) {

		this.indexid = indexid;
	}

	public String getName() {

		return this.name;
	}

	public Double getStandard() {

		return this.standard;
	}

	public Integer getIseffective() {

		return this.iseffective;
	}

	public String getCreateBy() {

		return this.createBy;
	}

	public Date getCreateDate() {

		return this.createDate;
	}

	public Date getUpdateDate() {

		return this.updateDate;
	}

	public String getUpdateBy() {

		return this.updateBy;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getBasis() {

		return basis;
	}

	public void setBasis(String basis) {

		this.basis = basis;
	}

	public void setStandard(Double standard) {

		this.standard = standard;
	}

	public void setIseffective(Integer iseffective) {

		this.iseffective = iseffective;
	}

	public void setCreateBy(String createBy) {

		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {

		this.createDate = createDate;
	}

	public void setUpdateDate(Date updateDate) {

		this.updateDate = updateDate;
	}

	public void setUpdateBy(String updateBy) {

		this.updateBy = updateBy;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Assessinfo other = (Assessinfo) obj;
		if (indexid == null) {
			if (other.indexid != null)
				return false;
		} else if (!indexid.equals(other.indexid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((indexid == null) ? 0 : indexid.hashCode());
		return result;
	}

	public Assessinfo jsonToObject(JSONObject jsonObject) {

		return AssessinfoJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {

		return AssessinfoJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {

		return AssessinfoJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}