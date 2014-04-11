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

package com.glaf.survey.domain;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.glaf.core.base.*;
import com.glaf.survey.util.*;

@Entity
@Table(name = "BASE_SURVEY")
public class Survey implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	@Column(name = "TITLE_", length = 200)
	protected String title;

	/**
	 * 描述
	 */
	@Column(name = "DESC_", length = 500)
	protected String desc;

	@Column(name = "CONTENT_", length = 2048)
	protected String content;

	@Column(name = "KEYWORDS_", length = 100)
	protected String keywords;

	@Column(name = "ICON_", length = 250)
	protected String icon;

	/**
	 * 顺序
	 */
	@Column(name = "SORT_")
	protected Integer sort;

	@Column(name = "STATUS_")
	protected Integer status;

	@Column(name = "SHOWICONFLAG_")
	protected Integer showIconFlag;

	@Column(name = "SIGNFLAG_")
	protected Integer signFlag;

	@Column(name = "MULTIFLAG_")
	protected Integer multiFlag;

	@Column(name = "LIMITFLAG_")
	protected Integer limitFlag;

	@Column(name = "LIMITTIMEINTERVAL_")
	protected Integer limitTimeInterval;

	@Column(name = "RESULTFLAG_")
	protected Integer resultFlag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTDATE_")
	protected Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDDATE_")
	protected Date endDate;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	/**
	 * 关联的内容编号，多个之间用逗号隔开
	 */
	@Column(name = "RELATIONIDS_", length = 100)
	protected String relationIds;

	@javax.persistence.Transient
	protected List<Survey> relations = new java.util.concurrent.CopyOnWriteArrayList<Survey>();

	@javax.persistence.Transient
	protected List<SurveyItem> items = new java.util.concurrent.CopyOnWriteArrayList<SurveyItem>();

	public Survey() {

	}

	public void addItem(SurveyItem item) {
		if (items == null) {
			items = new java.util.concurrent.CopyOnWriteArrayList<SurveyItem>();
		}
		items.add(item);
	}

	public void addRelation(Survey item) {
		if (relations == null) {
			relations = new java.util.concurrent.CopyOnWriteArrayList<Survey>();
		}
		relations.add(item);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Survey other = (Survey) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getContent() {
		return this.content;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getDesc() {
		return desc;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public String getIcon() {
		return this.icon;
	}

	public Long getId() {
		return this.id;
	}

	public List<SurveyItem> getItems() {
		return items;
	}

	public String getKeywords() {
		return keywords;
	}

	public Integer getLimitFlag() {
		return this.limitFlag;
	}

	public Integer getLimitTimeInterval() {
		return this.limitTimeInterval;
	}

	public Integer getMultiFlag() {
		return this.multiFlag;
	}

	public String getRelationIds() {
		return relationIds;
	}

	public List<Survey> getRelations() {
		return relations;
	}

	public Integer getResultFlag() {
		return resultFlag;
	}

	public Integer getShowIconFlag() {
		return showIconFlag;
	}

	public Integer getSignFlag() {
		return this.signFlag;
	}

	public Integer getSort() {
		return sort;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getTitle() {
		return this.title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public Survey jsonToObject(JSONObject jsonObject) {
		return SurveyJsonFactory.jsonToObject(jsonObject);
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setItems(List<SurveyItem> items) {
		this.items = items;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setLimitFlag(Integer limitFlag) {
		this.limitFlag = limitFlag;
	}

	public void setLimitTimeInterval(Integer limitTimeInterval) {
		this.limitTimeInterval = limitTimeInterval;
	}

	public void setMultiFlag(Integer multiFlag) {
		this.multiFlag = multiFlag;
	}

	public void setRelationIds(String relationIds) {
		this.relationIds = relationIds;
	}

	public void setRelations(List<Survey> relations) {
		this.relations = relations;
	}

	public void setResultFlag(Integer resultFlag) {
		this.resultFlag = resultFlag;
	}

	public void setShowIconFlag(Integer showIconFlag) {
		this.showIconFlag = showIconFlag;
	}

	public void setSignFlag(Integer signFlag) {
		this.signFlag = signFlag;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JSONObject toJsonObject() {
		return SurveyJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SurveyJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
