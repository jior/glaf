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

public class SurveyResultQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Long surveyId;
	protected List<Long> surveyIds;
	protected String ip;
	protected Date surveyDateGreaterThanOrEqual;
	protected Date surveyDateLessThanOrEqual;

	public SurveyResultQuery() {

	}

	public String getActorId() {
		return actorId;
	}

	public List<String> getActorIds() {
		return actorIds;
	}

	public String getIp() {
		return ip;
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

			if ("result".equals(sortColumn)) {
				orderBy = "E.RESULT_" + a_x;
			}

			if ("ip".equals(sortColumn)) {
				orderBy = "E.IP_" + a_x;
			}

			if ("surveyDate".equals(sortColumn)) {
				orderBy = "E.SURVEYDATE_" + a_x;
			}

			if ("actorId".equals(sortColumn)) {
				orderBy = "E.ACTORID_" + a_x;
			}

		}
		return orderBy;
	}

	public Date getSurveyDateGreaterThanOrEqual() {
		return surveyDateGreaterThanOrEqual;
	}

	public Date getSurveyDateLessThanOrEqual() {
		return surveyDateLessThanOrEqual;
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
		addColumn("result", "RESULT_");
		addColumn("ip", "IP_");
		addColumn("surveyDate", "SURVEYDATE_");
		addColumn("actorId", "ACTORID_");
	}

	public SurveyResultQuery ip(String ip) {
		if (ip == null) {
			throw new RuntimeException("ip is null");
		}
		this.ip = ip;
		return this;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setActorIds(List<String> actorIds) {
		this.actorIds = actorIds;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setSurveyDateGreaterThanOrEqual(
			Date surveyDateGreaterThanOrEqual) {
		this.surveyDateGreaterThanOrEqual = surveyDateGreaterThanOrEqual;
	}

	public void setSurveyDateLessThanOrEqual(Date surveyDateLessThanOrEqual) {
		this.surveyDateLessThanOrEqual = surveyDateLessThanOrEqual;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public void setSurveyIds(List<Long> surveyIds) {
		this.surveyIds = surveyIds;
	}

	public SurveyResultQuery surveyDateGreaterThanOrEqual(
			Date surveyDateGreaterThanOrEqual) {
		if (surveyDateGreaterThanOrEqual == null) {
			throw new RuntimeException("surveyDate is null");
		}
		this.surveyDateGreaterThanOrEqual = surveyDateGreaterThanOrEqual;
		return this;
	}

	public SurveyResultQuery surveyDateLessThanOrEqual(
			Date surveyDateLessThanOrEqual) {
		if (surveyDateLessThanOrEqual == null) {
			throw new RuntimeException("surveyDate is null");
		}
		this.surveyDateLessThanOrEqual = surveyDateLessThanOrEqual;
		return this;
	}

	public SurveyResultQuery surveyId(Long surveyId) {
		if (surveyId == null) {
			throw new RuntimeException("surveyId is null");
		}
		this.surveyId = surveyId;
		return this;
	}

	public SurveyResultQuery surveyIds(List<Long> surveyIds) {
		if (surveyIds == null) {
			throw new RuntimeException("surveyIds is empty ");
		}
		this.surveyIds = surveyIds;
		return this;
	}

}