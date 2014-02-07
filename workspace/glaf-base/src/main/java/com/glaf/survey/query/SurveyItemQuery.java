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

package com.glaf.survey.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SurveyItemQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Long surveyId;
	protected List<Long> surveyIds;

	public SurveyItemQuery() {

	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("surveyId".equals(sortColumn)) {
				orderBy = "E.SURVEYID_" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("value".equals(sortColumn)) {
				orderBy = "E.VALUE_" + a_x;
			}

		}
		return orderBy;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public List<Long> getSurveyIds() {
		return surveyIds;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("surveyId", "SURVEYID_");
		addColumn("name", "NAME_");
		addColumn("value", "VALUE_");
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public void setSurveyIds(List<Long> surveyIds) {
		this.surveyIds = surveyIds;
	}

	public SurveyItemQuery surveyId(Long surveyId) {
		if (surveyId == null) {
			throw new RuntimeException("surveyId is null");
		}
		this.surveyId = surveyId;
		return this;
	}

	public SurveyItemQuery surveyIds(List<Long> surveyIds) {
		if (surveyIds == null) {
			throw new RuntimeException("surveyIds is empty ");
		}
		this.surveyIds = surveyIds;
		return this;
	}

}