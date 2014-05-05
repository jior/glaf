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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.oa.assessresult.util.JobEvaluationJsonFactory;
import com.glaf.core.base.JSONable;

public class JobEvaluation implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	protected String treeName; // 考评类型

	protected String dictoryName;// 考评类型

	protected Long contentId; // 指标考核内容id

	protected String contentName;// 指标名称 kpi关键绩效指标

	protected String basis;// 评分依据

	protected Double standard;// 评分标准

	protected Integer score; // 得分

	protected String reason; // 扣分原因

	public JobEvaluation() {

	}

	public String getTreeName() {
		return treeName;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	public String getDictoryName() {
		return dictoryName;
	}

	public void setDictoryName(String dictoryName) {
		this.dictoryName = dictoryName;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public String getBasis() {
		return basis;
	}

	public void setBasis(String basis) {
		this.basis = basis;
	}

	public Double getStandard() {
		return standard;
	}

	public void setStandard(Double standard) {
		this.standard = standard;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobEvaluation other = (JobEvaluation) obj;
		if (contentId == null) {
			if (other.contentId != null)
				return false;
		} else if (!contentId.equals(other.contentId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contentId == null) ? 0 : contentId.hashCode());
		return result;
	}

	public JobEvaluation jsonToObject(JSONObject jsonObject) {
		return JobEvaluationJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return JobEvaluationJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return JobEvaluationJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}