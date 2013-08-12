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
package com.glaf.oa.assessresult.model;

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
import com.glaf.oa.assessresult.util.AssessresultJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "oa_assessresult")
public class Assessresult implements Serializable, JSONable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "resultid", nullable = false)
	protected Long resultid;

	@Column(name = "qustionid")
	protected Long qustionid;

	@Column(name = "area")
	protected String area;

	@Column(name = "company", length = 100)
	protected String company;

	@Column(name = "dept", length = 100)
	protected String dept;

	@Column(name = "post", length = 100)
	protected String post;

	@Column(name = "year")
	protected Integer year;

	@Column(name = "season")
	protected Integer season;

	@Column(name = "month")
	protected Integer month;

	@Column(name = "beevaluation", length = 20)
	protected String beevaluation;

	@Column(name = "evaluation", length = 20)
	protected String evaluation;

	@Column(name = "comment")
	protected String comment;

	@Column(name = "rewardsum")
	protected Double rewardsum;

	@Column(name = "score")
	protected Double score;

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

	protected String title;

	public Assessresult() {

	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public Long getResultid() {

		return this.resultid;
	}

	public void setResultid(Long resultid) {

		this.resultid = resultid;
	}

	public Long getQustionid() {

		return this.qustionid;
	}

	public String getArea() {

		return this.area;
	}

	public String getCompany() {

		return this.company;
	}

	public String getDept() {

		return this.dept;
	}

	public String getPost() {

		return this.post;
	}

	public Integer getYear() {

		return this.year;
	}

	public Integer getSeason() {

		return this.season;
	}

	public Integer getMonth() {

		return this.month;
	}

	public String getBeevaluation() {

		return this.beevaluation;
	}

	public String getEvaluation() {

		return this.evaluation;
	}

	public Double getRewardsum() {

		return this.rewardsum;
	}

	public Double getScore() {

		return this.score;
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

	public void setQustionid(Long qustionid) {

		this.qustionid = qustionid;
	}

	public void setArea(String area) {

		this.area = area;
	}

	public void setCompany(String company) {

		this.company = company;
	}

	public void setDept(String dept) {

		this.dept = dept;
	}

	public void setPost(String post) {

		this.post = post;
	}

	public void setYear(Integer year) {

		this.year = year;
	}

	public void setSeason(Integer season) {

		this.season = season;
	}

	public void setMonth(Integer month) {

		this.month = month;
	}

	public void setBeevaluation(String beevaluation) {

		this.beevaluation = beevaluation;
	}

	public void setEvaluation(String evaluation) {

		this.evaluation = evaluation;
	}

	public void setRewardsum(Double rewardsum) {

		this.rewardsum = rewardsum;
	}

	public void setScore(Double score) {

		this.score = score;
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
		Assessresult other = (Assessresult) obj;
		if (resultid == null) {
			if (other.resultid != null)
				return false;
		} else if (!resultid.equals(other.resultid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((resultid == null) ? 0 : resultid.hashCode());
		return result;
	}

	public Assessresult jsonToObject(JSONObject jsonObject) {

		return AssessresultJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {

		return AssessresultJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {

		return AssessresultJsonFactory.toObjectNode(this);
	}

	public String getComment() {

		return comment;
	}

	public void setComment(String comment) {

		this.comment = comment;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}