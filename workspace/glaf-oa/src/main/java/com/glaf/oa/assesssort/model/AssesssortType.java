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
package com.glaf.oa.assesssort.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.glaf.oa.assesscontent.model.AssesscontentAndScore;

/**
 * 所有指标分类类型
 * 
 * @author
 * 
 */
public class AssesssortType implements Serializable {

	private static final long serialVersionUID = 1L;

	protected long id;

	protected String name;

	protected String code;

	protected String fat;

	protected String scores;

	private List<Assesssort> assessSortList;

	private List<AssesssortType> subAssessList;

	private List<AssesscontentAndScore> adsList;

	public AssesssortType() {

	}

	public List<Assesssort> getAssessSortList() {

		return assessSortList;
	}

	public List<AssesssortType> getSubAssessList() {

		return subAssessList;
	}

	public String getCode() {

		return code;
	}

	public void setCode(String code) {

		this.code = code;
	}

	public void setSubAssessList(List<AssesssortType> subAssessList) {

		this.subAssessList = subAssessList;
	}

	public void setAssessSortList(List<Assesssort> assessSortList) {

		this.assessSortList = assessSortList;
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public List<AssesscontentAndScore> getAdsList() {
		return adsList;
	}

	public void setAdsList(List<AssesscontentAndScore> adsList) {
		this.adsList = adsList;
	}

	public String getFat() {
		return fat;
	}

	public void setFat(String fat) {
		this.fat = fat;
	}

	public String getScores() {
		return scores;
	}

	public void setScores(String scores) {
		this.scores = scores;
	}

}