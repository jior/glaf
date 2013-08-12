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
package com.glaf.oa.assessresult.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class AssessresultQuery extends DataQuery {

	private static final long serialVersionUID = 1L;
	protected List<Long> resultids;
	protected Long resultid;
	protected Long qustionid;
	protected Long qustionidGreaterThanOrEqual;
	protected Long qustionidLessThanOrEqual;
	protected List<Long> qustionids;
	protected Long area;
	protected Long areaGreaterThanOrEqual;
	protected Long areaLessThanOrEqual;
	protected List<Long> areas;
	protected String company;
	protected String companyLike;
	protected List<String> companys;
	protected String dept;
	protected String deptLike;
	protected List<String> depts;
	protected String post;
	protected String postLike;
	protected List<String> posts;
	protected Integer year;
	protected Integer yearGreaterThanOrEqual;
	protected Integer yearLessThanOrEqual;
	protected List<Integer> years;
	protected Integer season;
	protected Integer seasonGreaterThanOrEqual;
	protected Integer seasonLessThanOrEqual;
	protected List<Integer> seasons;
	protected Integer month;
	protected Integer monthGreaterThanOrEqual;
	protected Integer monthLessThanOrEqual;
	protected List<Integer> months;
	protected String beevaluation;
	protected String beevaluationLike;
	protected List<String> beevaluations;
	protected String evaluation;
	protected String evaluationLike;
	protected List<String> evaluations;
	protected Double rewardsum;
	protected Double rewardsumGreaterThanOrEqual;
	protected Double rewardsumLessThanOrEqual;
	protected List<Double> rewardsums;
	protected Double score;
	protected Double scoreGreaterThanOrEqual;
	protected Double scoreLessThanOrEqual;
	protected List<Double> scores;
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
	protected String title;
	protected String titleLike;

	public AssessresultQuery() {

	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public String getTitleLike() {
		if (titleLike != null && titleLike.trim().length() > 0) {
			if (!titleLike.startsWith("%")) {
				titleLike = "%" + titleLike;
			}
			if (!titleLike.endsWith("%")) {
				titleLike = titleLike + "%";
			}
		}
		return titleLike;
	}

	public void setTitleLike(String titleLike) {

		this.titleLike = titleLike;
	}

	public List<Long> getResultids() {

		return resultids;
	}

	public void setResultids(List<Long> resultids) {

		this.resultids = resultids;
	}

	public Long getResultid() {

		return resultid;
	}

	public void setResultid(Long resultid) {

		this.resultid = resultid;
	}

	public Long getQustionid() {

		return qustionid;
	}

	public Long getQustionidGreaterThanOrEqual() {

		return qustionidGreaterThanOrEqual;
	}

	public Long getQustionidLessThanOrEqual() {

		return qustionidLessThanOrEqual;
	}

	public List<Long> getQustionids() {

		return qustionids;
	}

	public Long getArea() {

		return area;
	}

	public Long getAreaGreaterThanOrEqual() {

		return areaGreaterThanOrEqual;
	}

	public Long getAreaLessThanOrEqual() {

		return areaLessThanOrEqual;
	}

	public List<Long> getAreas() {

		return areas;
	}

	public String getCompany() {

		return company;
	}

	public String getCompanyLike() {

		if (companyLike != null && companyLike.trim().length() > 0) {
			if (!companyLike.startsWith("%")) {
				companyLike = "%" + companyLike;
			}
			if (!companyLike.endsWith("%")) {
				companyLike = companyLike + "%";
			}
		}
		return companyLike;
	}

	public List<String> getCompanys() {

		return companys;
	}

	public String getDept() {

		return dept;
	}

	public String getDeptLike() {

		if (deptLike != null && deptLike.trim().length() > 0) {
			if (!deptLike.startsWith("%")) {
				deptLike = "%" + deptLike;
			}
			if (!deptLike.endsWith("%")) {
				deptLike = deptLike + "%";
			}
		}
		return deptLike;
	}

	public List<String> getDepts() {

		return depts;
	}

	public String getPost() {

		return post;
	}

	public String getPostLike() {

		if (postLike != null && postLike.trim().length() > 0) {
			if (!postLike.startsWith("%")) {
				postLike = "%" + postLike;
			}
			if (!postLike.endsWith("%")) {
				postLike = postLike + "%";
			}
		}
		return postLike;
	}

	public List<String> getPosts() {

		return posts;
	}

	public Integer getYear() {

		return year;
	}

	public Integer getYearGreaterThanOrEqual() {

		return yearGreaterThanOrEqual;
	}

	public Integer getYearLessThanOrEqual() {

		return yearLessThanOrEqual;
	}

	public List<Integer> getYears() {

		return years;
	}

	public Integer getSeason() {

		return season;
	}

	public Integer getSeasonGreaterThanOrEqual() {

		return seasonGreaterThanOrEqual;
	}

	public Integer getSeasonLessThanOrEqual() {

		return seasonLessThanOrEqual;
	}

	public List<Integer> getSeasons() {

		return seasons;
	}

	public Integer getMonth() {

		return month;
	}

	public Integer getMonthGreaterThanOrEqual() {

		return monthGreaterThanOrEqual;
	}

	public Integer getMonthLessThanOrEqual() {

		return monthLessThanOrEqual;
	}

	public List<Integer> getMonths() {

		return months;
	}

	public String getBeevaluation() {

		return beevaluation;
	}

	public String getBeevaluationLike() {

		if (beevaluationLike != null && beevaluationLike.trim().length() > 0) {
			if (!beevaluationLike.startsWith("%")) {
				beevaluationLike = "%" + beevaluationLike;
			}
			if (!beevaluationLike.endsWith("%")) {
				beevaluationLike = beevaluationLike + "%";
			}
		}
		return beevaluationLike;
	}

	public List<String> getBeevaluations() {

		return beevaluations;
	}

	public String getEvaluation() {

		return evaluation;
	}

	public String getEvaluationLike() {

		if (evaluationLike != null && evaluationLike.trim().length() > 0) {
			if (!evaluationLike.startsWith("%")) {
				evaluationLike = "%" + evaluationLike;
			}
			if (!evaluationLike.endsWith("%")) {
				evaluationLike = evaluationLike + "%";
			}
		}
		return evaluationLike;
	}

	public List<String> getEvaluations() {

		return evaluations;
	}

	public Double getRewardsum() {

		return rewardsum;
	}

	public Double getRewardsumGreaterThanOrEqual() {

		return rewardsumGreaterThanOrEqual;
	}

	public Double getRewardsumLessThanOrEqual() {

		return rewardsumLessThanOrEqual;
	}

	public List<Double> getRewardsums() {

		return rewardsums;
	}

	public Double getScore() {

		return score;
	}

	public Double getScoreGreaterThanOrEqual() {

		return scoreGreaterThanOrEqual;
	}

	public Double getScoreLessThanOrEqual() {

		return scoreLessThanOrEqual;
	}

	public List<Double> getScores() {

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

	public void setQustionid(Long qustionid) {

		this.qustionid = qustionid;
	}

	public void setQustionidGreaterThanOrEqual(Long qustionidGreaterThanOrEqual) {

		this.qustionidGreaterThanOrEqual = qustionidGreaterThanOrEqual;
	}

	public void setQustionidLessThanOrEqual(Long qustionidLessThanOrEqual) {

		this.qustionidLessThanOrEqual = qustionidLessThanOrEqual;
	}

	public void setQustionids(List<Long> qustionids) {

		this.qustionids = qustionids;
	}

	public void setArea(Long area) {

		this.area = area;
	}

	public void setAreaGreaterThanOrEqual(Long areaGreaterThanOrEqual) {

		this.areaGreaterThanOrEqual = areaGreaterThanOrEqual;
	}

	public void setAreaLessThanOrEqual(Long areaLessThanOrEqual) {

		this.areaLessThanOrEqual = areaLessThanOrEqual;
	}

	public void setAreas(List<Long> areas) {

		this.areas = areas;
	}

	public void setCompany(String company) {

		this.company = company;
	}

	public void setCompanyLike(String companyLike) {

		this.companyLike = companyLike;
	}

	public void setCompanys(List<String> companys) {

		this.companys = companys;
	}

	public void setDept(String dept) {

		this.dept = dept;
	}

	public void setDeptLike(String deptLike) {

		this.deptLike = deptLike;
	}

	public void setDepts(List<String> depts) {

		this.depts = depts;
	}

	public void setPost(String post) {

		this.post = post;
	}

	public void setPostLike(String postLike) {

		this.postLike = postLike;
	}

	public void setPosts(List<String> posts) {

		this.posts = posts;
	}

	public void setYear(Integer year) {

		this.year = year;
	}

	public void setYearGreaterThanOrEqual(Integer yearGreaterThanOrEqual) {

		this.yearGreaterThanOrEqual = yearGreaterThanOrEqual;
	}

	public void setYearLessThanOrEqual(Integer yearLessThanOrEqual) {

		this.yearLessThanOrEqual = yearLessThanOrEqual;
	}

	public void setYears(List<Integer> years) {

		this.years = years;
	}

	public void setSeason(Integer season) {

		this.season = season;
	}

	public void setSeasonGreaterThanOrEqual(Integer seasonGreaterThanOrEqual) {

		this.seasonGreaterThanOrEqual = seasonGreaterThanOrEqual;
	}

	public void setSeasonLessThanOrEqual(Integer seasonLessThanOrEqual) {

		this.seasonLessThanOrEqual = seasonLessThanOrEqual;
	}

	public void setSeasons(List<Integer> seasons) {

		this.seasons = seasons;
	}

	public void setMonth(Integer month) {

		this.month = month;
	}

	public void setMonthGreaterThanOrEqual(Integer monthGreaterThanOrEqual) {

		this.monthGreaterThanOrEqual = monthGreaterThanOrEqual;
	}

	public void setMonthLessThanOrEqual(Integer monthLessThanOrEqual) {

		this.monthLessThanOrEqual = monthLessThanOrEqual;
	}

	public void setMonths(List<Integer> months) {

		this.months = months;
	}

	public void setBeevaluation(String beevaluation) {

		this.beevaluation = beevaluation;
	}

	public void setBeevaluationLike(String beevaluationLike) {

		this.beevaluationLike = beevaluationLike;
	}

	public void setBeevaluations(List<String> beevaluations) {

		this.beevaluations = beevaluations;
	}

	public void setEvaluation(String evaluation) {

		this.evaluation = evaluation;
	}

	public void setEvaluationLike(String evaluationLike) {

		this.evaluationLike = evaluationLike;
	}

	public void setEvaluations(List<String> evaluations) {

		this.evaluations = evaluations;
	}

	public void setRewardsum(Double rewardsum) {

		this.rewardsum = rewardsum;
	}

	public void setRewardsumGreaterThanOrEqual(
			Double rewardsumGreaterThanOrEqual) {

		this.rewardsumGreaterThanOrEqual = rewardsumGreaterThanOrEqual;
	}

	public void setRewardsumLessThanOrEqual(Double rewardsumLessThanOrEqual) {

		this.rewardsumLessThanOrEqual = rewardsumLessThanOrEqual;
	}

	public void setRewardsums(List<Double> rewardsums) {

		this.rewardsums = rewardsums;
	}

	public void setScore(Double score) {

		this.score = score;
	}

	public void setScoreGreaterThanOrEqual(Double scoreGreaterThanOrEqual) {

		this.scoreGreaterThanOrEqual = scoreGreaterThanOrEqual;
	}

	public void setScoreLessThanOrEqual(Double scoreLessThanOrEqual) {

		this.scoreLessThanOrEqual = scoreLessThanOrEqual;
	}

	public void setScores(List<Double> scores) {

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

	public AssessresultQuery qustionid(Long qustionid) {

		if (qustionid == null) {
			throw new RuntimeException("qustionid is null");
		}
		this.qustionid = qustionid;
		return this;
	}

	public AssessresultQuery qustionidGreaterThanOrEqual(
			Long qustionidGreaterThanOrEqual) {

		if (qustionidGreaterThanOrEqual == null) {
			throw new RuntimeException("qustionid is null");
		}
		this.qustionidGreaterThanOrEqual = qustionidGreaterThanOrEqual;
		return this;
	}

	public AssessresultQuery qustionidLessThanOrEqual(
			Long qustionidLessThanOrEqual) {

		if (qustionidLessThanOrEqual == null) {
			throw new RuntimeException("qustionid is null");
		}
		this.qustionidLessThanOrEqual = qustionidLessThanOrEqual;
		return this;
	}

	public AssessresultQuery qustionids(List<Long> qustionids) {

		if (qustionids == null) {
			throw new RuntimeException("qustionids is empty ");
		}
		this.qustionids = qustionids;
		return this;
	}

	public AssessresultQuery area(Long area) {

		if (area == null) {
			throw new RuntimeException("area is null");
		}
		this.area = area;
		return this;
	}

	public AssessresultQuery areaGreaterThanOrEqual(Long areaGreaterThanOrEqual) {

		if (areaGreaterThanOrEqual == null) {
			throw new RuntimeException("area is null");
		}
		this.areaGreaterThanOrEqual = areaGreaterThanOrEqual;
		return this;
	}

	public AssessresultQuery areaLessThanOrEqual(Long areaLessThanOrEqual) {

		if (areaLessThanOrEqual == null) {
			throw new RuntimeException("area is null");
		}
		this.areaLessThanOrEqual = areaLessThanOrEqual;
		return this;
	}

	public AssessresultQuery areas(List<Long> areas) {

		if (areas == null) {
			throw new RuntimeException("areas is empty ");
		}
		this.areas = areas;
		return this;
	}

	public AssessresultQuery company(String company) {

		if (company == null) {
			throw new RuntimeException("company is null");
		}
		this.company = company;
		return this;
	}

	public AssessresultQuery companyLike(String companyLike) {

		if (companyLike == null) {
			throw new RuntimeException("company is null");
		}
		this.companyLike = companyLike;
		return this;
	}

	public AssessresultQuery companys(List<String> companys) {

		if (companys == null) {
			throw new RuntimeException("companys is empty ");
		}
		this.companys = companys;
		return this;
	}

	public AssessresultQuery dept(String dept) {

		if (dept == null) {
			throw new RuntimeException("dept is null");
		}
		this.dept = dept;
		return this;
	}

	public AssessresultQuery deptLike(String deptLike) {

		if (deptLike == null) {
			throw new RuntimeException("dept is null");
		}
		this.deptLike = deptLike;
		return this;
	}

	public AssessresultQuery depts(List<String> depts) {

		if (depts == null) {
			throw new RuntimeException("depts is empty ");
		}
		this.depts = depts;
		return this;
	}

	public AssessresultQuery post(String post) {

		if (post == null) {
			throw new RuntimeException("post is null");
		}
		this.post = post;
		return this;
	}

	public AssessresultQuery postLike(String postLike) {

		if (postLike == null) {
			throw new RuntimeException("post is null");
		}
		this.postLike = postLike;
		return this;
	}

	public AssessresultQuery posts(List<String> posts) {

		if (posts == null) {
			throw new RuntimeException("posts is empty ");
		}
		this.posts = posts;
		return this;
	}

	public AssessresultQuery year(Integer year) {

		if (year == null) {
			throw new RuntimeException("year is null");
		}
		this.year = year;
		return this;
	}

	public AssessresultQuery yearGreaterThanOrEqual(
			Integer yearGreaterThanOrEqual) {

		if (yearGreaterThanOrEqual == null) {
			throw new RuntimeException("year is null");
		}
		this.yearGreaterThanOrEqual = yearGreaterThanOrEqual;
		return this;
	}

	public AssessresultQuery yearLessThanOrEqual(Integer yearLessThanOrEqual) {

		if (yearLessThanOrEqual == null) {
			throw new RuntimeException("year is null");
		}
		this.yearLessThanOrEqual = yearLessThanOrEqual;
		return this;
	}

	public AssessresultQuery years(List<Integer> years) {

		if (years == null) {
			throw new RuntimeException("years is empty ");
		}
		this.years = years;
		return this;
	}

	public AssessresultQuery season(Integer season) {

		if (season == null) {
			throw new RuntimeException("season is null");
		}
		this.season = season;
		return this;
	}

	public AssessresultQuery seasonGreaterThanOrEqual(
			Integer seasonGreaterThanOrEqual) {

		if (seasonGreaterThanOrEqual == null) {
			throw new RuntimeException("season is null");
		}
		this.seasonGreaterThanOrEqual = seasonGreaterThanOrEqual;
		return this;
	}

	public AssessresultQuery seasonLessThanOrEqual(Integer seasonLessThanOrEqual) {

		if (seasonLessThanOrEqual == null) {
			throw new RuntimeException("season is null");
		}
		this.seasonLessThanOrEqual = seasonLessThanOrEqual;
		return this;
	}

	public AssessresultQuery seasons(List<Integer> seasons) {

		if (seasons == null) {
			throw new RuntimeException("seasons is empty ");
		}
		this.seasons = seasons;
		return this;
	}

	public AssessresultQuery month(Integer month) {

		if (month == null) {
			throw new RuntimeException("month is null");
		}
		this.month = month;
		return this;
	}

	public AssessresultQuery monthGreaterThanOrEqual(
			Integer monthGreaterThanOrEqual) {

		if (monthGreaterThanOrEqual == null) {
			throw new RuntimeException("month is null");
		}
		this.monthGreaterThanOrEqual = monthGreaterThanOrEqual;
		return this;
	}

	public AssessresultQuery monthLessThanOrEqual(Integer monthLessThanOrEqual) {

		if (monthLessThanOrEqual == null) {
			throw new RuntimeException("month is null");
		}
		this.monthLessThanOrEqual = monthLessThanOrEqual;
		return this;
	}

	public AssessresultQuery months(List<Integer> months) {

		if (months == null) {
			throw new RuntimeException("months is empty ");
		}
		this.months = months;
		return this;
	}

	public AssessresultQuery beevaluation(String beevaluation) {

		if (beevaluation == null) {
			throw new RuntimeException("beevaluation is null");
		}
		this.beevaluation = beevaluation;
		return this;
	}

	public AssessresultQuery beevaluationLike(String beevaluationLike) {

		if (beevaluationLike == null) {
			throw new RuntimeException("beevaluation is null");
		}
		this.beevaluationLike = beevaluationLike;
		return this;
	}

	public AssessresultQuery beevaluations(List<String> beevaluations) {

		if (beevaluations == null) {
			throw new RuntimeException("beevaluations is empty ");
		}
		this.beevaluations = beevaluations;
		return this;
	}

	public AssessresultQuery evaluation(String evaluation) {

		if (evaluation == null) {
			throw new RuntimeException("evaluation is null");
		}
		this.evaluation = evaluation;
		return this;
	}

	public AssessresultQuery evaluationLike(String evaluationLike) {

		if (evaluationLike == null) {
			throw new RuntimeException("evaluation is null");
		}
		this.evaluationLike = evaluationLike;
		return this;
	}

	public AssessresultQuery evaluations(List<String> evaluations) {

		if (evaluations == null) {
			throw new RuntimeException("evaluations is empty ");
		}
		this.evaluations = evaluations;
		return this;
	}

	public AssessresultQuery rewardsum(Double rewardsum) {

		if (rewardsum == null) {
			throw new RuntimeException("rewardsum is null");
		}
		this.rewardsum = rewardsum;
		return this;
	}

	public AssessresultQuery rewardsumGreaterThanOrEqual(
			Double rewardsumGreaterThanOrEqual) {

		if (rewardsumGreaterThanOrEqual == null) {
			throw new RuntimeException("rewardsum is null");
		}
		this.rewardsumGreaterThanOrEqual = rewardsumGreaterThanOrEqual;
		return this;
	}

	public AssessresultQuery rewardsumLessThanOrEqual(
			Double rewardsumLessThanOrEqual) {

		if (rewardsumLessThanOrEqual == null) {
			throw new RuntimeException("rewardsum is null");
		}
		this.rewardsumLessThanOrEqual = rewardsumLessThanOrEqual;
		return this;
	}

	public AssessresultQuery rewardsums(List<Double> rewardsums) {

		if (rewardsums == null) {
			throw new RuntimeException("rewardsums is empty ");
		}
		this.rewardsums = rewardsums;
		return this;
	}

	public AssessresultQuery score(Double score) {

		if (score == null) {
			throw new RuntimeException("score is null");
		}
		this.score = score;
		return this;
	}

	public AssessresultQuery scoreGreaterThanOrEqual(
			Double scoreGreaterThanOrEqual) {

		if (scoreGreaterThanOrEqual == null) {
			throw new RuntimeException("score is null");
		}
		this.scoreGreaterThanOrEqual = scoreGreaterThanOrEqual;
		return this;
	}

	public AssessresultQuery scoreLessThanOrEqual(Double scoreLessThanOrEqual) {

		if (scoreLessThanOrEqual == null) {
			throw new RuntimeException("score is null");
		}
		this.scoreLessThanOrEqual = scoreLessThanOrEqual;
		return this;
	}

	public AssessresultQuery scores(List<Double> scores) {

		if (scores == null) {
			throw new RuntimeException("scores is empty ");
		}
		this.scores = scores;
		return this;
	}

	public AssessresultQuery createBy(String createBy) {

		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public AssessresultQuery createByLike(String createByLike) {

		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public AssessresultQuery createBys(List<String> createBys) {

		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public AssessresultQuery createDate(Date createDate) {

		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public AssessresultQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {

		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public AssessresultQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {

		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public AssessresultQuery createDates(List<Date> createDates) {

		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public AssessresultQuery updateDate(Date updateDate) {

		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public AssessresultQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {

		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public AssessresultQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {

		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public AssessresultQuery updateDates(List<Date> updateDates) {

		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public AssessresultQuery updateBy(String updateBy) {

		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public AssessresultQuery updateByLike(String updateByLike) {

		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public AssessresultQuery updateBys(List<String> updateBys) {

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

			if ("qustionid".equals(sortColumn)) {
				orderBy = "E.qustionid" + a_x;
			}

			if ("area".equals(sortColumn)) {
				orderBy = "E.area" + a_x;
			}

			if ("company".equals(sortColumn)) {
				orderBy = "E.company" + a_x;
			}

			if ("dept".equals(sortColumn)) {
				orderBy = "E.dept" + a_x;
			}

			if ("post".equals(sortColumn)) {
				orderBy = "E.post" + a_x;
			}

			if ("year".equals(sortColumn)) {
				orderBy = "E.year" + a_x;
			}

			if ("season".equals(sortColumn)) {
				orderBy = "E.season" + a_x;
			}

			if ("month".equals(sortColumn)) {
				orderBy = "E.month" + a_x;
			}

			if ("beevaluation".equals(sortColumn)) {
				orderBy = "E.beevaluation" + a_x;
			}

			if ("evaluation".equals(sortColumn)) {
				orderBy = "E.evaluation" + a_x;
			}

			if ("rewardsum".equals(sortColumn)) {
				orderBy = "E.rewardsum" + a_x;
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
		addColumn("resultid", "resultid");
		addColumn("qustionid", "qustionid");
		addColumn("area", "area");
		addColumn("company", "company");
		addColumn("dept", "dept");
		addColumn("post", "post");
		addColumn("year", "year");
		addColumn("season", "season");
		addColumn("month", "month");
		addColumn("beevaluation", "beevaluation");
		addColumn("evaluation", "evaluation");
		addColumn("rewardsum", "rewardsum");
		addColumn("score", "score");
		addColumn("createBy", "createBy");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("updateBy", "updateBy");
	}

}