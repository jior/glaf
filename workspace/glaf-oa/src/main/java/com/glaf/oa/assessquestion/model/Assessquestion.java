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
package com.glaf.oa.assessquestion.model;

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
import com.glaf.oa.assessquestion.util.AssessquestionJsonFactory;
import com.glaf.core.base.JSONable;

/**
 * @author Administrator
 * 
 */
@Entity
@Table(name = "oa_assessquestion")
public class Assessquestion implements Serializable, JSONable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "qustionid", nullable = false)
	protected Long qustionid;

	@Column(name = "title", length = 200)
	protected String title;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "validdate")
	protected Date validdate;

	@Column(name = "rate")
	protected Integer rate;

	protected String rateText;

	@Column(name = "iseffective")
	protected Integer iseffective;

	protected String iseffectiveText;

	@Column(name = "targetsum")
	protected Double targetsum;

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

	@Column(name = "assessid")
	protected Long assessid;

	public Assessquestion() {

	}

	public String getIseffectiveText() {

		return iseffectiveText;
	}

	public Long getAssessid() {

		return assessid;
	}

	public void setAssessid(Long assessid) {

		this.assessid = assessid;
	}

	public void setIseffectiveText(String iseffectiveText) {

		this.iseffectiveText = iseffectiveText;
	}

	public Long getQustionid() {

		return this.qustionid;
	}

	public void setQustionid(Long qustionid) {

		this.qustionid = qustionid;
	}

	public String getTitle() {

		return this.title;
	}

	public Date getValiddate() {

		return this.validdate;
	}

	public Integer getRate() {

		return this.rate;
	}

	public Integer getIseffective() {

		return this.iseffective;
	}

	public Double getTargetsum() {

		return this.targetsum;
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

	public String getRateText() {

		return rateText;
	}

	public void setRateText(String rateText) {

		this.rateText = rateText;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public void setValiddate(Date validdate) {

		this.validdate = validdate;
	}

	public void setRate(Integer rate) {

		this.rate = rate;
	}

	public void setIseffective(Integer iseffective) {

		this.iseffective = iseffective;
	}

	public void setTargetsum(Double targetsum) {

		this.targetsum = targetsum;
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
		Assessquestion other = (Assessquestion) obj;
		if (qustionid == null) {
			if (other.qustionid != null)
				return false;
		} else if (!qustionid.equals(other.qustionid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((qustionid == null) ? 0 : qustionid.hashCode());
		return result;
	}

	public Assessquestion jsonToObject(JSONObject jsonObject) {

		return AssessquestionJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {

		return AssessquestionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {

		return AssessquestionJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}