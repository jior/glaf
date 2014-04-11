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
package com.glaf.oa.assesscontent.model;

import java.io.Serializable;
import java.util.Date;

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
import com.glaf.oa.assesscontent.util.AssesscontentJsonFactory;
import com.glaf.oa.assessinfo.model.Assessinfo;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "oa_assesscontent")
public class Assesscontent implements Serializable, JSONable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "contentid", nullable = false)
	protected Long contentid;

	@Column(name = "sortid")
	protected Long sortid;

	@Column(name = "name", length = 200)
	protected String name;

	@Column(name = "basis")
	protected String basis;

	@Column(name = "standard")
	protected Double standard;

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

	// 指标ID
	@Column(name = "assessId")
	protected Long assessId;
	// 指标信息
	protected Assessinfo assessInfo;

	public Assesscontent() {

	}

	public Long getAssessId() {

		return assessId;
	}

	public void setAssessId(Long assessId) {

		this.assessId = assessId;
	}

	public Assessinfo getAssessInfo() {

		return assessInfo;
	}

	public void setAssessInfo(Assessinfo assessInfo) {

		this.assessInfo = assessInfo;
	}

	public Long getContentid() {

		return this.contentid;
	}

	public void setContentid(Long contentid) {

		this.contentid = contentid;
	}

	public Long getSortid() {

		return this.sortid;
	}

	public String getName() {

		return this.name;
	}

	public Double getStandard() {

		return this.standard;
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

	public void setSortid(Long sortid) {

		this.sortid = sortid;
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
		Assesscontent other = (Assesscontent) obj;
		if (contentid == null) {
			if (other.contentid != null)
				return false;
		} else if (!contentid.equals(other.contentid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contentid == null) ? 0 : contentid.hashCode());
		return result;
	}

	public Assesscontent jsonToObject(JSONObject jsonObject) {

		return AssesscontentJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {

		return AssesscontentJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {

		return AssesscontentJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}