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
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.survey.util.*;

@Entity
@Table(name = "BASE_SURVEY_ITEM")
public class SurveyItem implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	@Column(name = "SURVEYID_")
	protected Long surveyId;

	@Column(name = "NAME_", length = 200)
	protected String name;

	@Column(name = "VALUE_", length = 2000)
	protected String value;

	@Column(name = "SORT_")
	protected Integer sort;

	@Column(name = "ICON_", length = 150)
	protected String icon;

	@Column(name = "URL_", length = 500)
	protected String url;

	public SurveyItem() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SurveyItem other = (SurveyItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getIcon() {
		return icon;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Integer getSort() {
		return sort;
	}

	public String getUrl() {
		return url;
	}

	public String getValue() {
		return this.value;
	}

	public Long getSurveyId() {
		return this.surveyId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public SurveyItem jsonToObject(JSONObject jsonObject) {
		return SurveyItemJsonFactory.jsonToObject(jsonObject);
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public JSONObject toJsonObject() {
		return SurveyItemJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SurveyItemJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
