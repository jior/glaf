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
package com.glaf.oa.assessscore.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class AssessscoreQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> scoreids;
	protected Long contentid;
	protected Long contentidGreaterThanOrEqual;
	protected Long contentidLessThanOrEqual;
	protected List<Long> contentids;
	protected Long resultid;
	protected Long sortid;
	protected Long resultidGreaterThanOrEqual;
	protected Long resultidLessThanOrEqual;
	protected List<Long> resultids;
	protected Long score;
	protected Long scoreGreaterThanOrEqual;
	protected Long scoreLessThanOrEqual;
	protected List<Long> scores;
	protected String createByLike;
	protected List<String> createBys;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected List<Date> createDates;
	protected Date updateDate;
	protected Date updateDateGreaterThanOrEqual;
	protected Date updateDateLessThanOrEqual;
	protected List<Date> updateDates;
	protected String updateBy;
	protected String updateByLike;
	protected List<String> updateBys;

	public AssessscoreQuery() {

	}

	public Long getContentid() {
		return contentid;
	}

	public Long getContentidGreaterThanOrEqual() {
		return contentidGreaterThanOrEqual;
	}

	public Long getContentidLessThanOrEqual() {
		return contentidLessThanOrEqual;
	}

	public List<Long> getContentids() {
		return contentids;
	}

	public Long getResultid() {
		return resultid;
	}

	public Long getResultidGreaterThanOrEqual() {
		return resultidGreaterThanOrEqual;
	}

	public Long getResultidLessThanOrEqual() {
		return resultidLessThanOrEqual;
	}

	public List<Long> getResultids() {
		return resultids;
	}

	public Long getScore() {
		return score;
	}

	public Long getScoreGreaterThanOrEqual() {
		return scoreGreaterThanOrEqual;
	}

	public Long getScoreLessThanOrEqual() {
		return scoreLessThanOrEqual;
	}

	public List<Long> getScores() {
		return scores;
	}

	public String getCreateBy() {
		return createBy;
	}

	public String getCreateByLike() {
		if (createByLike != null && createByLike.trim().length() > 0) {
			if (!createByLike.startsWith("%")) {
				createByLike = "%" + createByLike;
			}
			if (!createByLike.endsWith("%")) {
				createByLike = createByLike + "%";
			}
		}
		return createByLike;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public List<Date> getCreateDates() {
		return createDates;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public Date getUpdateDateGreaterThanOrEqual() {
		return updateDateGreaterThanOrEqual;
	}

	public Date getUpdateDateLessThanOrEqual() {
		return updateDateLessThanOrEqual;
	}

	public List<Date> getUpdateDates() {
		return updateDates;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public String getUpdateByLike() {
		if (updateByLike != null && updateByLike.trim().length() > 0) {
			if (!updateByLike.startsWith("%")) {
				updateByLike = "%" + updateByLike;
			}
			if (!updateByLike.endsWith("%")) {
				updateByLike = updateByLike + "%";
			}
		}
		return updateByLike;
	}

	public List<String> getUpdateBys() {
		return updateBys;
	}

	public void setContentid(Long contentid) {
		this.contentid = contentid;
	}

	public void setContentidGreaterThanOrEqual(Long contentidGreaterThanOrEqual) {
		this.contentidGreaterThanOrEqual = contentidGreaterThanOrEqual;
	}

	public void setContentidLessThanOrEqual(Long contentidLessThanOrEqual) {
		this.contentidLessThanOrEqual = contentidLessThanOrEqual;
	}

	public void setContentids(List<Long> contentids) {
		this.contentids = contentids;
	}

	public void setResultid(Long resultid) {
		this.resultid = resultid;
	}

	public void setResultidGreaterThanOrEqual(Long resultidGreaterThanOrEqual) {
		this.resultidGreaterThanOrEqual = resultidGreaterThanOrEqual;
	}

	public void setResultidLessThanOrEqual(Long resultidLessThanOrEqual) {
		this.resultidLessThanOrEqual = resultidLessThanOrEqual;
	}

	public void setResultids(List<Long> resultids) {
		this.resultids = resultids;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public void setScoreGreaterThanOrEqual(Long scoreGreaterThanOrEqual) {
		this.scoreGreaterThanOrEqual = scoreGreaterThanOrEqual;
	}

	public void setScoreLessThanOrEqual(Long scoreLessThanOrEqual) {
		this.scoreLessThanOrEqual = scoreLessThanOrEqual;
	}

	public void setScores(List<Long> scores) {
		this.scores = scores;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateByLike(String createByLike) {
		this.createByLike = createByLike;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setCreateDates(List<Date> createDates) {
		this.createDates = createDates;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setUpdateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
	}

	public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
	}

	public void setUpdateDates(List<Date> updateDates) {
		this.updateDates = updateDates;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateByLike(String updateByLike) {
		this.updateByLike = updateByLike;
	}

	public void setUpdateBys(List<String> updateBys) {
		this.updateBys = updateBys;
	}

	public AssessscoreQuery contentid(Long contentid) {
		if (contentid == null) {
			throw new RuntimeException("contentid is null");
		}
		this.contentid = contentid;
		return this;
	}

	public AssessscoreQuery contentidGreaterThanOrEqual(
			Long contentidGreaterThanOrEqual) {
		if (contentidGreaterThanOrEqual == null) {
			throw new RuntimeException("contentid is null");
		}
		this.contentidGreaterThanOrEqual = contentidGreaterThanOrEqual;
		return this;
	}

	public AssessscoreQuery contentidLessThanOrEqual(
			Long contentidLessThanOrEqual) {
		if (contentidLessThanOrEqual == null) {
			throw new RuntimeException("contentid is null");
		}
		this.contentidLessThanOrEqual = contentidLessThanOrEqual;
		return this;
	}

	public AssessscoreQuery contentids(List<Long> contentids) {
		if (contentids == null) {
			throw new RuntimeException("contentids is empty ");
		}
		this.contentids = contentids;
		return this;
	}

	public AssessscoreQuery resultid(Long resultid) {
		if (resultid == null) {
			throw new RuntimeException("resultid is null");
		}
		this.resultid = resultid;
		return this;
	}

	public AssessscoreQuery resultidGreaterThanOrEqual(
			Long resultidGreaterThanOrEqual) {
		if (resultidGreaterThanOrEqual == null) {
			throw new RuntimeException("resultid is null");
		}
		this.resultidGreaterThanOrEqual = resultidGreaterThanOrEqual;
		return this;
	}

	public AssessscoreQuery resultidLessThanOrEqual(Long resultidLessThanOrEqual) {
		if (resultidLessThanOrEqual == null) {
			throw new RuntimeException("resultid is null");
		}
		this.resultidLessThanOrEqual = resultidLessThanOrEqual;
		return this;
	}

	public AssessscoreQuery resultids(List<Long> resultids) {
		if (resultids == null) {
			throw new RuntimeException("resultids is empty ");
		}
		this.resultids = resultids;
		return this;
	}

	public AssessscoreQuery score(Long score) {
		if (score == null) {
			throw new RuntimeException("score is null");
		}
		this.score = score;
		return this;
	}

	public AssessscoreQuery scoreGreaterThanOrEqual(Long scoreGreaterThanOrEqual) {
		if (scoreGreaterThanOrEqual == null) {
			throw new RuntimeException("score is null");
		}
		this.scoreGreaterThanOrEqual = scoreGreaterThanOrEqual;
		return this;
	}

	public AssessscoreQuery scoreLessThanOrEqual(Long scoreLessThanOrEqual) {
		if (scoreLessThanOrEqual == null) {
			throw new RuntimeException("score is null");
		}
		this.scoreLessThanOrEqual = scoreLessThanOrEqual;
		return this;
	}

	public AssessscoreQuery scores(List<Long> scores) {
		if (scores == null) {
			throw new RuntimeException("scores is empty ");
		}
		this.scores = scores;
		return this;
	}

	public AssessscoreQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public AssessscoreQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public AssessscoreQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public AssessscoreQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public AssessscoreQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public AssessscoreQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public AssessscoreQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public AssessscoreQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public AssessscoreQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public AssessscoreQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public AssessscoreQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public AssessscoreQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public AssessscoreQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public AssessscoreQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("contentid".equals(sortColumn)) {
				orderBy = "E.contentid" + a_x;
			}

			if ("resultid".equals(sortColumn)) {
				orderBy = "E.resultid" + a_x;
			}

			if ("score".equals(sortColumn)) {
				orderBy = "E.score" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.createBy" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.createDate" + a_x;
			}

			if ("updateDate".equals(sortColumn)) {
				orderBy = "E.updateDate" + a_x;
			}

			if ("updateBy".equals(sortColumn)) {
				orderBy = "E.updateBy" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("scoreid", "scoreid");
		addColumn("contentid", "contentid");
		addColumn("resultid", "resultid");
		addColumn("score", "score");
		addColumn("createBy", "createBy");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("updateBy", "updateBy");
	}

	public Long getSortid() {
		return sortid;
	}

	public void setSortid(Long sortid) {
		this.sortid = sortid;
	}

}