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
package com.glaf.oa.assessscore.model;

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
import com.glaf.oa.assessscore.util.AssessscoreJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "oa_assessscore")
public class Assessscore implements Serializable, JSONable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "scoreid", nullable = false)
	protected Long scoreid;

	@Column(name = "contentid")
	protected Long contentid;

	@Column(name = "resultid")
	protected Long resultid;

	@Column(name = "score")
	protected Long score;

	@Column(name = "reason")
	protected String reason;

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

	public Assessscore() {

	}

	public Long getScoreid() {

		return this.scoreid;
	}

	public void setScoreid(Long scoreid) {

		this.scoreid = scoreid;
	}

	public Long getContentid() {

		return this.contentid;
	}

	public Long getResultid() {

		return this.resultid;
	}

	public Long getScore() {

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

	public void setContentid(Long contentid) {

		this.contentid = contentid;
	}

	public void setResultid(Long resultid) {

		this.resultid = resultid;
	}

	public void setScore(Long score) {

		this.score = score;
	}

	public String getReason() {

		return reason;
	}

	public void setReason(String reason) {

		this.reason = reason;
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
		Assessscore other = (Assessscore) obj;
		if (scoreid == null) {
			if (other.scoreid != null)
				return false;
		} else if (!scoreid.equals(other.scoreid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((scoreid == null) ? 0 : scoreid.hashCode());
		return result;
	}

	public Assessscore jsonToObject(JSONObject jsonObject) {

		return AssessscoreJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {

		return AssessscoreJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {

		return AssessscoreJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}