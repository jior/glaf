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
package com.glaf.oa.reimbursement.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ReimbursementQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> reimbursementids;
	protected String reimbursementno;
	protected String reimbursementnoLike;
	protected List<String> reimbursementnos;
	protected String area;
	protected String areaLike;
	protected List<String> areas;
	protected String dept;
	protected String deptLike;
	protected List<String> depts;
	protected String post;
	protected String postLike;
	protected List<String> posts;
	protected String appuser;
	protected String appuserLike;
	protected List<String> appusers;
	protected String subject;
	protected String subjectLike;
	protected List<String> subjects;
	protected String budgetno;
	protected String budgetnoLike;
	protected List<String> budgetnos;
	protected Date appdate;
	protected Date appdateGreaterThanOrEqual;
	protected Date appdateLessThanOrEqual;
	protected List<Date> appdates;
	protected Double appsum;
	protected Double appsumGreaterThanOrEqual;
	protected Double appsumLessThanOrEqual;
	protected List<Double> appsums;
	protected List<Integer> statuss;
	protected String processname;
	protected String processnameLike;
	protected List<String> processnames;
	protected Long processinstanceid;
	protected Long processinstanceidGreaterThanOrEqual;
	protected Long processinstanceidLessThanOrEqual;
	protected List<Long> processinstanceids;
	protected Long wfstatus;
	protected Long wfstatusGreaterThanOrEqual;
	protected Long wfstatusLessThanOrEqual;
	protected List<Long> wfstatuss;
	protected String brands1;
	protected String brands1Like;
	protected List<String> brands1s;
	protected Double brands1account;
	protected Double brands1accountGreaterThanOrEqual;
	protected Double brands1accountLessThanOrEqual;
	protected List<Double> brands1accounts;
	protected String brands2;
	protected String brands2Like;
	protected List<String> brands2s;
	protected Double brands2account;
	protected Double brands2accountGreaterThanOrEqual;
	protected Double brands2accountLessThanOrEqual;
	protected List<Double> brands2accounts;
	protected String brands3;
	protected String brands3Like;
	protected List<String> brands3s;
	protected Double brands3account;
	protected Double brands3accountGreaterThanOrEqual;
	protected Double brands3accountLessThanOrEqual;
	protected List<Double> brands3accounts;
	protected String company;
	protected String companyLike;
	protected List<String> companys;
	protected Double budgetsum;
	protected Double budgetsumGreaterThanOrEqual;
	protected Double budgetsumLessThanOrEqual;
	protected List<Double> budgetsums;
	protected Date budgetDate;
	protected Date budgetDateGreaterThanOrEqual;
	protected Date budgetDateLessThanOrEqual;
	protected List<Date> budgetDates;
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

	public ReimbursementQuery() {

	}

	public String getReimbursementno() {
		return reimbursementno;
	}

	public String getReimbursementnoLike() {
		if (reimbursementnoLike != null
				&& reimbursementnoLike.trim().length() > 0) {
			if (!reimbursementnoLike.startsWith("%")) {
				reimbursementnoLike = "%" + reimbursementnoLike;
			}
			if (!reimbursementnoLike.endsWith("%")) {
				reimbursementnoLike = reimbursementnoLike + "%";
			}
		}
		return reimbursementnoLike;
	}

	public List<String> getReimbursementnos() {
		return reimbursementnos;
	}

	public String getArea() {
		return area;
	}

	public String getAreaLike() {
		if (areaLike != null && areaLike.trim().length() > 0) {
			if (!areaLike.startsWith("%")) {
				areaLike = "%" + areaLike;
			}
			if (!areaLike.endsWith("%")) {
				areaLike = areaLike + "%";
			}
		}
		return areaLike;
	}

	public List<String> getAreas() {
		return areas;
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

	public String getAppuser() {
		return appuser;
	}

	public String getAppuserLike() {
		if (appuserLike != null && appuserLike.trim().length() > 0) {
			if (!appuserLike.startsWith("%")) {
				appuserLike = "%" + appuserLike;
			}
			if (!appuserLike.endsWith("%")) {
				appuserLike = appuserLike + "%";
			}
		}
		return appuserLike;
	}

	public List<String> getAppusers() {
		return appusers;
	}

	public String getSubject() {
		return subject;
	}

	public String getSubjectLike() {
		if (subjectLike != null && subjectLike.trim().length() > 0) {
			if (!subjectLike.startsWith("%")) {
				subjectLike = "%" + subjectLike;
			}
			if (!subjectLike.endsWith("%")) {
				subjectLike = subjectLike + "%";
			}
		}
		return subjectLike;
	}

	public List<String> getSubjects() {
		return subjects;
	}

	public String getBudgetno() {
		return budgetno;
	}

	public String getBudgetnoLike() {
		if (budgetnoLike != null && budgetnoLike.trim().length() > 0) {
			if (!budgetnoLike.startsWith("%")) {
				budgetnoLike = "%" + budgetnoLike;
			}
			if (!budgetnoLike.endsWith("%")) {
				budgetnoLike = budgetnoLike + "%";
			}
		}
		return budgetnoLike;
	}

	public List<String> getBudgetnos() {
		return budgetnos;
	}

	public Date getAppdate() {
		return appdate;
	}

	public Date getAppdateGreaterThanOrEqual() {
		return appdateGreaterThanOrEqual;
	}

	public Date getAppdateLessThanOrEqual() {
		return appdateLessThanOrEqual;
	}

	public List<Date> getAppdates() {
		return appdates;
	}

	public Double getAppsum() {
		return appsum;
	}

	public Double getAppsumGreaterThanOrEqual() {
		return appsumGreaterThanOrEqual;
	}

	public Double getAppsumLessThanOrEqual() {
		return appsumLessThanOrEqual;
	}

	public List<Double> getAppsums() {
		return appsums;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getStatusGreaterThanOrEqual() {
		return statusGreaterThanOrEqual;
	}

	public Integer getStatusLessThanOrEqual() {
		return statusLessThanOrEqual;
	}

	public List<Integer> getStatuss() {
		return statuss;
	}

	public String getProcessname() {
		return processname;
	}

	public String getProcessnameLike() {
		if (processnameLike != null && processnameLike.trim().length() > 0) {
			if (!processnameLike.startsWith("%")) {
				processnameLike = "%" + processnameLike;
			}
			if (!processnameLike.endsWith("%")) {
				processnameLike = processnameLike + "%";
			}
		}
		return processnameLike;
	}

	public List<String> getProcessnames() {
		return processnames;
	}

	public Long getProcessinstanceid() {
		return processinstanceid;
	}

	public Long getProcessinstanceidGreaterThanOrEqual() {
		return processinstanceidGreaterThanOrEqual;
	}

	public Long getProcessinstanceidLessThanOrEqual() {
		return processinstanceidLessThanOrEqual;
	}

	public List<Long> getProcessinstanceids() {
		return processinstanceids;
	}

	public Long getWfstatus() {
		return wfstatus;
	}

	public Long getWfstatusGreaterThanOrEqual() {
		return wfstatusGreaterThanOrEqual;
	}

	public Long getWfstatusLessThanOrEqual() {
		return wfstatusLessThanOrEqual;
	}

	public List<Long> getWfstatuss() {
		return wfstatuss;
	}

	public String getBrands1() {
		return brands1;
	}

	public String getBrands1Like() {
		if (brands1Like != null && brands1Like.trim().length() > 0) {
			if (!brands1Like.startsWith("%")) {
				brands1Like = "%" + brands1Like;
			}
			if (!brands1Like.endsWith("%")) {
				brands1Like = brands1Like + "%";
			}
		}
		return brands1Like;
	}

	public List<String> getBrands1s() {
		return brands1s;
	}

	public Double getBrands1account() {
		return brands1account;
	}

	public Double getBrands1accountGreaterThanOrEqual() {
		return brands1accountGreaterThanOrEqual;
	}

	public Double getBrands1accountLessThanOrEqual() {
		return brands1accountLessThanOrEqual;
	}

	public List<Double> getBrands1accounts() {
		return brands1accounts;
	}

	public String getBrands2() {
		return brands2;
	}

	public String getBrands2Like() {
		if (brands2Like != null && brands2Like.trim().length() > 0) {
			if (!brands2Like.startsWith("%")) {
				brands2Like = "%" + brands2Like;
			}
			if (!brands2Like.endsWith("%")) {
				brands2Like = brands2Like + "%";
			}
		}
		return brands2Like;
	}

	public List<String> getBrands2s() {
		return brands2s;
	}

	public Double getBrands2account() {
		return brands2account;
	}

	public Double getBrands2accountGreaterThanOrEqual() {
		return brands2accountGreaterThanOrEqual;
	}

	public Double getBrands2accountLessThanOrEqual() {
		return brands2accountLessThanOrEqual;
	}

	public List<Double> getBrands2accounts() {
		return brands2accounts;
	}

	public String getBrands3() {
		return brands3;
	}

	public String getBrands3Like() {
		if (brands3Like != null && brands3Like.trim().length() > 0) {
			if (!brands3Like.startsWith("%")) {
				brands3Like = "%" + brands3Like;
			}
			if (!brands3Like.endsWith("%")) {
				brands3Like = brands3Like + "%";
			}
		}
		return brands3Like;
	}

	public List<String> getBrands3s() {
		return brands3s;
	}

	public Double getBrands3account() {
		return brands3account;
	}

	public Double getBrands3accountGreaterThanOrEqual() {
		return brands3accountGreaterThanOrEqual;
	}

	public Double getBrands3accountLessThanOrEqual() {
		return brands3accountLessThanOrEqual;
	}

	public List<Double> getBrands3accounts() {
		return brands3accounts;
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

	public Double getBudgetsum() {
		return budgetsum;
	}

	public Double getBudgetsumGreaterThanOrEqual() {
		return budgetsumGreaterThanOrEqual;
	}

	public Double getBudgetsumLessThanOrEqual() {
		return budgetsumLessThanOrEqual;
	}

	public List<Double> getBudgetsums() {
		return budgetsums;
	}

	public Date getBudgetDate() {
		return budgetDate;
	}

	public Date getBudgetDateGreaterThanOrEqual() {
		return budgetDateGreaterThanOrEqual;
	}

	public Date getBudgetDateLessThanOrEqual() {
		return budgetDateLessThanOrEqual;
	}

	public List<Date> getBudgetDates() {
		return budgetDates;
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

	public void setReimbursementno(String reimbursementno) {
		this.reimbursementno = reimbursementno;
	}

	public void setReimbursementnoLike(String reimbursementnoLike) {
		this.reimbursementnoLike = reimbursementnoLike;
	}

	public void setReimbursementnos(List<String> reimbursementnos) {
		this.reimbursementnos = reimbursementnos;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setAreaLike(String areaLike) {
		this.areaLike = areaLike;
	}

	public void setAreas(List<String> areas) {
		this.areas = areas;
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

	public void setAppuser(String appuser) {
		this.appuser = appuser;
	}

	public void setAppuserLike(String appuserLike) {
		this.appuserLike = appuserLike;
	}

	public void setAppusers(List<String> appusers) {
		this.appusers = appusers;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setSubjectLike(String subjectLike) {
		this.subjectLike = subjectLike;
	}

	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}

	public void setBudgetno(String budgetno) {
		this.budgetno = budgetno;
	}

	public void setBudgetnoLike(String budgetnoLike) {
		this.budgetnoLike = budgetnoLike;
	}

	public void setBudgetnos(List<String> budgetnos) {
		this.budgetnos = budgetnos;
	}

	public void setAppdate(Date appdate) {
		this.appdate = appdate;
	}

	public void setAppdateGreaterThanOrEqual(Date appdateGreaterThanOrEqual) {
		this.appdateGreaterThanOrEqual = appdateGreaterThanOrEqual;
	}

	public void setAppdateLessThanOrEqual(Date appdateLessThanOrEqual) {
		this.appdateLessThanOrEqual = appdateLessThanOrEqual;
	}

	public void setAppdates(List<Date> appdates) {
		this.appdates = appdates;
	}

	public void setAppsum(Double appsum) {
		this.appsum = appsum;
	}

	public void setAppsumGreaterThanOrEqual(Double appsumGreaterThanOrEqual) {
		this.appsumGreaterThanOrEqual = appsumGreaterThanOrEqual;
	}

	public void setAppsumLessThanOrEqual(Double appsumLessThanOrEqual) {
		this.appsumLessThanOrEqual = appsumLessThanOrEqual;
	}

	public void setAppsums(List<Double> appsums) {
		this.appsums = appsums;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatusGreaterThanOrEqual(Integer statusGreaterThanOrEqual) {
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
	}

	public void setStatusLessThanOrEqual(Integer statusLessThanOrEqual) {
		this.statusLessThanOrEqual = statusLessThanOrEqual;
	}

	public void setStatuss(List<Integer> statuss) {
		this.statuss = statuss;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public void setProcessnameLike(String processnameLike) {
		this.processnameLike = processnameLike;
	}

	public void setProcessnames(List<String> processnames) {
		this.processnames = processnames;
	}

	public void setProcessinstanceid(Long processinstanceid) {
		this.processinstanceid = processinstanceid;
	}

	public void setProcessinstanceidGreaterThanOrEqual(
			Long processinstanceidGreaterThanOrEqual) {
		this.processinstanceidGreaterThanOrEqual = processinstanceidGreaterThanOrEqual;
	}

	public void setProcessinstanceidLessThanOrEqual(
			Long processinstanceidLessThanOrEqual) {
		this.processinstanceidLessThanOrEqual = processinstanceidLessThanOrEqual;
	}

	public void setProcessinstanceids(List<Long> processinstanceids) {
		this.processinstanceids = processinstanceids;
	}

	public void setWfstatus(Long wfstatus) {
		this.wfstatus = wfstatus;
	}

	public void setWfstatusGreaterThanOrEqual(Long wfstatusGreaterThanOrEqual) {
		this.wfstatusGreaterThanOrEqual = wfstatusGreaterThanOrEqual;
	}

	public void setWfstatusLessThanOrEqual(Long wfstatusLessThanOrEqual) {
		this.wfstatusLessThanOrEqual = wfstatusLessThanOrEqual;
	}

	public void setWfstatuss(List<Long> wfstatuss) {
		this.wfstatuss = wfstatuss;
	}

	public void setBrands1(String brands1) {
		this.brands1 = brands1;
	}

	public void setBrands1Like(String brands1Like) {
		this.brands1Like = brands1Like;
	}

	public void setBrands1s(List<String> brands1s) {
		this.brands1s = brands1s;
	}

	public void setBrands1account(Double brands1account) {
		this.brands1account = brands1account;
	}

	public void setBrands1accountGreaterThanOrEqual(
			Double brands1accountGreaterThanOrEqual) {
		this.brands1accountGreaterThanOrEqual = brands1accountGreaterThanOrEqual;
	}

	public void setBrands1accountLessThanOrEqual(
			Double brands1accountLessThanOrEqual) {
		this.brands1accountLessThanOrEqual = brands1accountLessThanOrEqual;
	}

	public void setBrands1accounts(List<Double> brands1accounts) {
		this.brands1accounts = brands1accounts;
	}

	public void setBrands2(String brands2) {
		this.brands2 = brands2;
	}

	public void setBrands2Like(String brands2Like) {
		this.brands2Like = brands2Like;
	}

	public void setBrands2s(List<String> brands2s) {
		this.brands2s = brands2s;
	}

	public void setBrands2account(Double brands2account) {
		this.brands2account = brands2account;
	}

	public void setBrands2accountGreaterThanOrEqual(
			Double brands2accountGreaterThanOrEqual) {
		this.brands2accountGreaterThanOrEqual = brands2accountGreaterThanOrEqual;
	}

	public void setBrands2accountLessThanOrEqual(
			Double brands2accountLessThanOrEqual) {
		this.brands2accountLessThanOrEqual = brands2accountLessThanOrEqual;
	}

	public void setBrands2accounts(List<Double> brands2accounts) {
		this.brands2accounts = brands2accounts;
	}

	public void setBrands3(String brands3) {
		this.brands3 = brands3;
	}

	public void setBrands3Like(String brands3Like) {
		this.brands3Like = brands3Like;
	}

	public void setBrands3s(List<String> brands3s) {
		this.brands3s = brands3s;
	}

	public void setBrands3account(Double brands3account) {
		this.brands3account = brands3account;
	}

	public void setBrands3accountGreaterThanOrEqual(
			Double brands3accountGreaterThanOrEqual) {
		this.brands3accountGreaterThanOrEqual = brands3accountGreaterThanOrEqual;
	}

	public void setBrands3accountLessThanOrEqual(
			Double brands3accountLessThanOrEqual) {
		this.brands3accountLessThanOrEqual = brands3accountLessThanOrEqual;
	}

	public void setBrands3accounts(List<Double> brands3accounts) {
		this.brands3accounts = brands3accounts;
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

	public void setBudgetsum(Double budgetsum) {
		this.budgetsum = budgetsum;
	}

	public void setBudgetsumGreaterThanOrEqual(
			Double budgetsumGreaterThanOrEqual) {
		this.budgetsumGreaterThanOrEqual = budgetsumGreaterThanOrEqual;
	}

	public void setBudgetsumLessThanOrEqual(Double budgetsumLessThanOrEqual) {
		this.budgetsumLessThanOrEqual = budgetsumLessThanOrEqual;
	}

	public void setBudgetsums(List<Double> budgetsums) {
		this.budgetsums = budgetsums;
	}

	public void setBudgetDate(Date budgetDate) {
		this.budgetDate = budgetDate;
	}

	public void setBudgetDateGreaterThanOrEqual(
			Date budgetDateGreaterThanOrEqual) {
		this.budgetDateGreaterThanOrEqual = budgetDateGreaterThanOrEqual;
	}

	public void setBudgetDateLessThanOrEqual(Date budgetDateLessThanOrEqual) {
		this.budgetDateLessThanOrEqual = budgetDateLessThanOrEqual;
	}

	public void setBudgetDates(List<Date> budgetDates) {
		this.budgetDates = budgetDates;
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

	public ReimbursementQuery reimbursementno(String reimbursementno) {
		if (reimbursementno == null) {
			throw new RuntimeException("reimbursementno is null");
		}
		this.reimbursementno = reimbursementno;
		return this;
	}

	public ReimbursementQuery reimbursementnoLike(String reimbursementnoLike) {
		if (reimbursementnoLike == null) {
			throw new RuntimeException("reimbursementno is null");
		}
		this.reimbursementnoLike = reimbursementnoLike;
		return this;
	}

	public ReimbursementQuery reimbursementnos(List<String> reimbursementnos) {
		if (reimbursementnos == null) {
			throw new RuntimeException("reimbursementnos is empty ");
		}
		this.reimbursementnos = reimbursementnos;
		return this;
	}

	public ReimbursementQuery area(String area) {
		if (area == null) {
			throw new RuntimeException("area is null");
		}
		this.area = area;
		return this;
	}

	public ReimbursementQuery areaLike(String areaLike) {
		if (areaLike == null) {
			throw new RuntimeException("area is null");
		}
		this.areaLike = areaLike;
		return this;
	}

	public ReimbursementQuery areas(List<String> areas) {
		if (areas == null) {
			throw new RuntimeException("areas is empty ");
		}
		this.areas = areas;
		return this;
	}

	public ReimbursementQuery dept(String dept) {
		if (dept == null) {
			throw new RuntimeException("dept is null");
		}
		this.dept = dept;
		return this;
	}

	public ReimbursementQuery deptLike(String deptLike) {
		if (deptLike == null) {
			throw new RuntimeException("dept is null");
		}
		this.deptLike = deptLike;
		return this;
	}

	public ReimbursementQuery depts(List<String> depts) {
		if (depts == null) {
			throw new RuntimeException("depts is empty ");
		}
		this.depts = depts;
		return this;
	}

	public ReimbursementQuery post(String post) {
		if (post == null) {
			throw new RuntimeException("post is null");
		}
		this.post = post;
		return this;
	}

	public ReimbursementQuery postLike(String postLike) {
		if (postLike == null) {
			throw new RuntimeException("post is null");
		}
		this.postLike = postLike;
		return this;
	}

	public ReimbursementQuery posts(List<String> posts) {
		if (posts == null) {
			throw new RuntimeException("posts is empty ");
		}
		this.posts = posts;
		return this;
	}

	public ReimbursementQuery appuser(String appuser) {
		if (appuser == null) {
			throw new RuntimeException("appuser is null");
		}
		this.appuser = appuser;
		return this;
	}

	public ReimbursementQuery appuserLike(String appuserLike) {
		if (appuserLike == null) {
			throw new RuntimeException("appuser is null");
		}
		this.appuserLike = appuserLike;
		return this;
	}

	public ReimbursementQuery appusers(List<String> appusers) {
		if (appusers == null) {
			throw new RuntimeException("appusers is empty ");
		}
		this.appusers = appusers;
		return this;
	}

	public ReimbursementQuery subject(String subject) {
		if (subject == null) {
			throw new RuntimeException("subject is null");
		}
		this.subject = subject;
		return this;
	}

	public ReimbursementQuery subjectLike(String subjectLike) {
		if (subjectLike == null) {
			throw new RuntimeException("subject is null");
		}
		this.subjectLike = subjectLike;
		return this;
	}

	public ReimbursementQuery subjects(List<String> subjects) {
		if (subjects == null) {
			throw new RuntimeException("subjects is empty ");
		}
		this.subjects = subjects;
		return this;
	}

	public ReimbursementQuery budgetno(String budgetno) {
		if (budgetno == null) {
			throw new RuntimeException("budgetno is null");
		}
		this.budgetno = budgetno;
		return this;
	}

	public ReimbursementQuery budgetnoLike(String budgetnoLike) {
		if (budgetnoLike == null) {
			throw new RuntimeException("budgetno is null");
		}
		this.budgetnoLike = budgetnoLike;
		return this;
	}

	public ReimbursementQuery budgetnos(List<String> budgetnos) {
		if (budgetnos == null) {
			throw new RuntimeException("budgetnos is empty ");
		}
		this.budgetnos = budgetnos;
		return this;
	}

	public ReimbursementQuery appdate(Date appdate) {
		if (appdate == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdate = appdate;
		return this;
	}

	public ReimbursementQuery appdateGreaterThanOrEqual(
			Date appdateGreaterThanOrEqual) {
		if (appdateGreaterThanOrEqual == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdateGreaterThanOrEqual = appdateGreaterThanOrEqual;
		return this;
	}

	public ReimbursementQuery appdateLessThanOrEqual(Date appdateLessThanOrEqual) {
		if (appdateLessThanOrEqual == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdateLessThanOrEqual = appdateLessThanOrEqual;
		return this;
	}

	public ReimbursementQuery appdates(List<Date> appdates) {
		if (appdates == null) {
			throw new RuntimeException("appdates is empty ");
		}
		this.appdates = appdates;
		return this;
	}

	public ReimbursementQuery appsum(Double appsum) {
		if (appsum == null) {
			throw new RuntimeException("appsum is null");
		}
		this.appsum = appsum;
		return this;
	}

	public ReimbursementQuery appsumGreaterThanOrEqual(
			Double appsumGreaterThanOrEqual) {
		if (appsumGreaterThanOrEqual == null) {
			throw new RuntimeException("appsum is null");
		}
		this.appsumGreaterThanOrEqual = appsumGreaterThanOrEqual;
		return this;
	}

	public ReimbursementQuery appsumLessThanOrEqual(Double appsumLessThanOrEqual) {
		if (appsumLessThanOrEqual == null) {
			throw new RuntimeException("appsum is null");
		}
		this.appsumLessThanOrEqual = appsumLessThanOrEqual;
		return this;
	}

	public ReimbursementQuery appsums(List<Double> appsums) {
		if (appsums == null) {
			throw new RuntimeException("appsums is empty ");
		}
		this.appsums = appsums;
		return this;
	}

	public ReimbursementQuery status(Integer status) {
		if (status == null) {
			throw new RuntimeException("status is null");
		}
		this.status = status;
		return this;
	}

	public ReimbursementQuery statusGreaterThanOrEqual(
			Integer statusGreaterThanOrEqual) {
		if (statusGreaterThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
		return this;
	}

	public ReimbursementQuery statusLessThanOrEqual(
			Integer statusLessThanOrEqual) {
		if (statusLessThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusLessThanOrEqual = statusLessThanOrEqual;
		return this;
	}

	public ReimbursementQuery statuss(List<Integer> statuss) {
		if (statuss == null) {
			throw new RuntimeException("statuss is empty ");
		}
		this.statuss = statuss;
		return this;
	}

	public ReimbursementQuery processname(String processname) {
		if (processname == null) {
			throw new RuntimeException("processname is null");
		}
		this.processname = processname;
		return this;
	}

	public ReimbursementQuery processnameLike(String processnameLike) {
		if (processnameLike == null) {
			throw new RuntimeException("processname is null");
		}
		this.processnameLike = processnameLike;
		return this;
	}

	public ReimbursementQuery processnames(List<String> processnames) {
		if (processnames == null) {
			throw new RuntimeException("processnames is empty ");
		}
		this.processnames = processnames;
		return this;
	}

	public ReimbursementQuery processinstanceid(Long processinstanceid) {
		if (processinstanceid == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceid = processinstanceid;
		return this;
	}

	public ReimbursementQuery processinstanceidGreaterThanOrEqual(
			Long processinstanceidGreaterThanOrEqual) {
		if (processinstanceidGreaterThanOrEqual == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceidGreaterThanOrEqual = processinstanceidGreaterThanOrEqual;
		return this;
	}

	public ReimbursementQuery processinstanceidLessThanOrEqual(
			Long processinstanceidLessThanOrEqual) {
		if (processinstanceidLessThanOrEqual == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceidLessThanOrEqual = processinstanceidLessThanOrEqual;
		return this;
	}

	public ReimbursementQuery processinstanceids(List<Long> processinstanceids) {
		if (processinstanceids == null) {
			throw new RuntimeException("processinstanceids is empty ");
		}
		this.processinstanceids = processinstanceids;
		return this;
	}

	public ReimbursementQuery wfstatus(Long wfstatus) {
		if (wfstatus == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatus = wfstatus;
		return this;
	}

	public ReimbursementQuery wfstatusGreaterThanOrEqual(
			Long wfstatusGreaterThanOrEqual) {
		if (wfstatusGreaterThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusGreaterThanOrEqual = wfstatusGreaterThanOrEqual;
		return this;
	}

	public ReimbursementQuery wfstatusLessThanOrEqual(
			Long wfstatusLessThanOrEqual) {
		if (wfstatusLessThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusLessThanOrEqual = wfstatusLessThanOrEqual;
		return this;
	}

	public ReimbursementQuery wfstatuss(List<Long> wfstatuss) {
		if (wfstatuss == null) {
			throw new RuntimeException("wfstatuss is empty ");
		}
		this.wfstatuss = wfstatuss;
		return this;
	}

	public ReimbursementQuery brands1(String brands1) {
		if (brands1 == null) {
			throw new RuntimeException("brands1 is null");
		}
		this.brands1 = brands1;
		return this;
	}

	public ReimbursementQuery brands1Like(String brands1Like) {
		if (brands1Like == null) {
			throw new RuntimeException("brands1 is null");
		}
		this.brands1Like = brands1Like;
		return this;
	}

	public ReimbursementQuery brands1s(List<String> brands1s) {
		if (brands1s == null) {
			throw new RuntimeException("brands1s is empty ");
		}
		this.brands1s = brands1s;
		return this;
	}

	public ReimbursementQuery brands1account(Double brands1account) {
		if (brands1account == null) {
			throw new RuntimeException("brands1account is null");
		}
		this.brands1account = brands1account;
		return this;
	}

	public ReimbursementQuery brands1accountGreaterThanOrEqual(
			Double brands1accountGreaterThanOrEqual) {
		if (brands1accountGreaterThanOrEqual == null) {
			throw new RuntimeException("brands1account is null");
		}
		this.brands1accountGreaterThanOrEqual = brands1accountGreaterThanOrEqual;
		return this;
	}

	public ReimbursementQuery brands1accountLessThanOrEqual(
			Double brands1accountLessThanOrEqual) {
		if (brands1accountLessThanOrEqual == null) {
			throw new RuntimeException("brands1account is null");
		}
		this.brands1accountLessThanOrEqual = brands1accountLessThanOrEqual;
		return this;
	}

	public ReimbursementQuery brands1accounts(List<Double> brands1accounts) {
		if (brands1accounts == null) {
			throw new RuntimeException("brands1accounts is empty ");
		}
		this.brands1accounts = brands1accounts;
		return this;
	}

	public ReimbursementQuery brands2(String brands2) {
		if (brands2 == null) {
			throw new RuntimeException("brands2 is null");
		}
		this.brands2 = brands2;
		return this;
	}

	public ReimbursementQuery brands2Like(String brands2Like) {
		if (brands2Like == null) {
			throw new RuntimeException("brands2 is null");
		}
		this.brands2Like = brands2Like;
		return this;
	}

	public ReimbursementQuery brands2s(List<String> brands2s) {
		if (brands2s == null) {
			throw new RuntimeException("brands2s is empty ");
		}
		this.brands2s = brands2s;
		return this;
	}

	public ReimbursementQuery brands2account(Double brands2account) {
		if (brands2account == null) {
			throw new RuntimeException("brands2account is null");
		}
		this.brands2account = brands2account;
		return this;
	}

	public ReimbursementQuery brands2accountGreaterThanOrEqual(
			Double brands2accountGreaterThanOrEqual) {
		if (brands2accountGreaterThanOrEqual == null) {
			throw new RuntimeException("brands2account is null");
		}
		this.brands2accountGreaterThanOrEqual = brands2accountGreaterThanOrEqual;
		return this;
	}

	public ReimbursementQuery brands2accountLessThanOrEqual(
			Double brands2accountLessThanOrEqual) {
		if (brands2accountLessThanOrEqual == null) {
			throw new RuntimeException("brands2account is null");
		}
		this.brands2accountLessThanOrEqual = brands2accountLessThanOrEqual;
		return this;
	}

	public ReimbursementQuery brands2accounts(List<Double> brands2accounts) {
		if (brands2accounts == null) {
			throw new RuntimeException("brands2accounts is empty ");
		}
		this.brands2accounts = brands2accounts;
		return this;
	}

	public ReimbursementQuery brands3(String brands3) {
		if (brands3 == null) {
			throw new RuntimeException("brands3 is null");
		}
		this.brands3 = brands3;
		return this;
	}

	public ReimbursementQuery brands3Like(String brands3Like) {
		if (brands3Like == null) {
			throw new RuntimeException("brands3 is null");
		}
		this.brands3Like = brands3Like;
		return this;
	}

	public ReimbursementQuery brands3s(List<String> brands3s) {
		if (brands3s == null) {
			throw new RuntimeException("brands3s is empty ");
		}
		this.brands3s = brands3s;
		return this;
	}

	public ReimbursementQuery brands3account(Double brands3account) {
		if (brands3account == null) {
			throw new RuntimeException("brands3account is null");
		}
		this.brands3account = brands3account;
		return this;
	}

	public ReimbursementQuery brands3accountGreaterThanOrEqual(
			Double brands3accountGreaterThanOrEqual) {
		if (brands3accountGreaterThanOrEqual == null) {
			throw new RuntimeException("brands3account is null");
		}
		this.brands3accountGreaterThanOrEqual = brands3accountGreaterThanOrEqual;
		return this;
	}

	public ReimbursementQuery brands3accountLessThanOrEqual(
			Double brands3accountLessThanOrEqual) {
		if (brands3accountLessThanOrEqual == null) {
			throw new RuntimeException("brands3account is null");
		}
		this.brands3accountLessThanOrEqual = brands3accountLessThanOrEqual;
		return this;
	}

	public ReimbursementQuery brands3accounts(List<Double> brands3accounts) {
		if (brands3accounts == null) {
			throw new RuntimeException("brands3accounts is empty ");
		}
		this.brands3accounts = brands3accounts;
		return this;
	}

	public ReimbursementQuery company(String company) {
		if (company == null) {
			throw new RuntimeException("company is null");
		}
		this.company = company;
		return this;
	}

	public ReimbursementQuery companyLike(String companyLike) {
		if (companyLike == null) {
			throw new RuntimeException("company is null");
		}
		this.companyLike = companyLike;
		return this;
	}

	public ReimbursementQuery companys(List<String> companys) {
		if (companys == null) {
			throw new RuntimeException("companys is empty ");
		}
		this.companys = companys;
		return this;
	}

	public ReimbursementQuery budgetsum(Double budgetsum) {
		if (budgetsum == null) {
			throw new RuntimeException("budgetsum is null");
		}
		this.budgetsum = budgetsum;
		return this;
	}

	public ReimbursementQuery budgetsumGreaterThanOrEqual(
			Double budgetsumGreaterThanOrEqual) {
		if (budgetsumGreaterThanOrEqual == null) {
			throw new RuntimeException("budgetsum is null");
		}
		this.budgetsumGreaterThanOrEqual = budgetsumGreaterThanOrEqual;
		return this;
	}

	public ReimbursementQuery budgetsumLessThanOrEqual(
			Double budgetsumLessThanOrEqual) {
		if (budgetsumLessThanOrEqual == null) {
			throw new RuntimeException("budgetsum is null");
		}
		this.budgetsumLessThanOrEqual = budgetsumLessThanOrEqual;
		return this;
	}

	public ReimbursementQuery budgetsums(List<Double> budgetsums) {
		if (budgetsums == null) {
			throw new RuntimeException("budgetsums is empty ");
		}
		this.budgetsums = budgetsums;
		return this;
	}

	public ReimbursementQuery budgetDate(Date budgetDate) {
		if (budgetDate == null) {
			throw new RuntimeException("budgetDate is null");
		}
		this.budgetDate = budgetDate;
		return this;
	}

	public ReimbursementQuery budgetDateGreaterThanOrEqual(
			Date budgetDateGreaterThanOrEqual) {
		if (budgetDateGreaterThanOrEqual == null) {
			throw new RuntimeException("budgetDate is null");
		}
		this.budgetDateGreaterThanOrEqual = budgetDateGreaterThanOrEqual;
		return this;
	}

	public ReimbursementQuery budgetDateLessThanOrEqual(
			Date budgetDateLessThanOrEqual) {
		if (budgetDateLessThanOrEqual == null) {
			throw new RuntimeException("budgetDate is null");
		}
		this.budgetDateLessThanOrEqual = budgetDateLessThanOrEqual;
		return this;
	}

	public ReimbursementQuery budgetDates(List<Date> budgetDates) {
		if (budgetDates == null) {
			throw new RuntimeException("budgetDates is empty ");
		}
		this.budgetDates = budgetDates;
		return this;
	}

	public ReimbursementQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public ReimbursementQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public ReimbursementQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public ReimbursementQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public ReimbursementQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public ReimbursementQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public ReimbursementQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public ReimbursementQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public ReimbursementQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public ReimbursementQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public ReimbursementQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public ReimbursementQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public ReimbursementQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public ReimbursementQuery updateBys(List<String> updateBys) {
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

			if ("reimbursementno".equals(sortColumn)) {
				orderBy = "E.reimbursementno" + a_x;
			}

			if ("area".equals(sortColumn)) {
				orderBy = "E.area" + a_x;
			}

			if ("dept".equals(sortColumn)) {
				orderBy = "E.dept" + a_x;
			}

			if ("post".equals(sortColumn)) {
				orderBy = "E.post" + a_x;
			}

			if ("appuser".equals(sortColumn)) {
				orderBy = "E.appuser" + a_x;
			}

			if ("subject".equals(sortColumn)) {
				orderBy = "E.subject" + a_x;
			}

			if ("budgetno".equals(sortColumn)) {
				orderBy = "E.budgetno" + a_x;
			}

			if ("appdate".equals(sortColumn)) {
				orderBy = "E.appdate" + a_x;
			}

			if ("appsum".equals(sortColumn)) {
				orderBy = "E.appsum" + a_x;
			}

			if ("status".equals(sortColumn)) {
				orderBy = "E.status" + a_x;
			}

			if ("processname".equals(sortColumn)) {
				orderBy = "E.processname" + a_x;
			}

			if ("processinstanceid".equals(sortColumn)) {
				orderBy = "E.processinstanceid" + a_x;
			}

			if ("wfstatus".equals(sortColumn)) {
				orderBy = "E.wfstatus" + a_x;
			}

			if ("brands1".equals(sortColumn)) {
				orderBy = "E.brands1" + a_x;
			}

			if ("brands1account".equals(sortColumn)) {
				orderBy = "E.brands1account" + a_x;
			}

			if ("brands2".equals(sortColumn)) {
				orderBy = "E.brands2" + a_x;
			}

			if ("brands2account".equals(sortColumn)) {
				orderBy = "E.brands2account" + a_x;
			}

			if ("brands3".equals(sortColumn)) {
				orderBy = "E.brands3" + a_x;
			}

			if ("brands3account".equals(sortColumn)) {
				orderBy = "E.brands3account" + a_x;
			}

			if ("company".equals(sortColumn)) {
				orderBy = "E.company" + a_x;
			}

			if ("budgetsum".equals(sortColumn)) {
				orderBy = "E.budgetsum" + a_x;
			}

			if ("budgetDate".equals(sortColumn)) {
				orderBy = "E.budgetDate" + a_x;
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
		addColumn("reimbursementid", "reimbursementid");
		addColumn("reimbursementno", "reimbursementno");
		addColumn("area", "area");
		addColumn("dept", "dept");
		addColumn("post", "post");
		addColumn("appuser", "appuser");
		addColumn("subject", "subject");
		addColumn("budgetno", "budgetno");
		addColumn("appdate", "appdate");
		addColumn("appsum", "appsum");
		addColumn("status", "status");
		addColumn("processname", "processname");
		addColumn("processinstanceid", "processinstanceid");
		addColumn("wfstatus", "wfstatus");
		addColumn("brands1", "brands1");
		addColumn("brands1account", "brands1account");
		addColumn("brands2", "brands2");
		addColumn("brands2account", "brands2account");
		addColumn("brands3", "brands3");
		addColumn("brands3account", "brands3account");
		addColumn("company", "company");
		addColumn("budgetsum", "budgetsum");
		addColumn("budgetDate", "budgetDate");
		addColumn("createBy", "createBy");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("updateBy", "updateBy");
	}

}