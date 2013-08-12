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
package com.glaf.oa.withdrawal.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class WithdrawalQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> withdrawalids;
	protected String area;
	protected String areaLike;
	protected List<String> areas;
	protected String company;
	protected String companyLike;
	protected List<String> companys;
	protected String dept;
	protected String deptLike;
	protected List<String> depts;
	protected String post;
	protected String postLike;
	protected List<String> posts;
	protected String appuser;
	protected String appuserLike;
	protected List<String> appusers;
	protected Date appdate;
	protected Date appdateGreaterThanOrEqual;
	protected Date appdateLessThanOrEqual;
	protected List<Date> appdates;
	protected Double appsum;
	protected Double appsumGreaterThanOrEqual;
	protected Double appsumLessThanOrEqual;
	protected List<Double> appsums;
	protected String content;
	protected String contentLike;
	protected List<String> contents;
	protected String remark;
	protected String remarkLike;
	protected List<String> remarks;
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

	public WithdrawalQuery() {

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

	public String getContent() {
		return content;
	}

	public String getContentLike() {
		if (contentLike != null && contentLike.trim().length() > 0) {
			if (!contentLike.startsWith("%")) {
				contentLike = "%" + contentLike;
			}
			if (!contentLike.endsWith("%")) {
				contentLike = contentLike + "%";
			}
		}
		return contentLike;
	}

	public List<String> getContents() {
		return contents;
	}

	public String getRemark() {
		return remark;
	}

	public String getRemarkLike() {
		if (remarkLike != null && remarkLike.trim().length() > 0) {
			if (!remarkLike.startsWith("%")) {
				remarkLike = "%" + remarkLike;
			}
			if (!remarkLike.endsWith("%")) {
				remarkLike = remarkLike + "%";
			}
		}
		return remarkLike;
	}

	public List<String> getRemarks() {
		return remarks;
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

	public void setArea(String area) {
		this.area = area;
	}

	public void setAreaLike(String areaLike) {
		this.areaLike = areaLike;
	}

	public void setAreas(List<String> areas) {
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

	public void setAppuser(String appuser) {
		this.appuser = appuser;
	}

	public void setAppuserLike(String appuserLike) {
		this.appuserLike = appuserLike;
	}

	public void setAppusers(List<String> appusers) {
		this.appusers = appusers;
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

	public void setContent(String content) {
		this.content = content;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setContents(List<String> contents) {
		this.contents = contents;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRemarkLike(String remarkLike) {
		this.remarkLike = remarkLike;
	}

	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
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

	public WithdrawalQuery area(String area) {
		if (area == null) {
			throw new RuntimeException("area is null");
		}
		this.area = area;
		return this;
	}

	public WithdrawalQuery areaLike(String areaLike) {
		if (areaLike == null) {
			throw new RuntimeException("area is null");
		}
		this.areaLike = areaLike;
		return this;
	}

	public WithdrawalQuery areas(List<String> areas) {
		if (areas == null) {
			throw new RuntimeException("areas is empty ");
		}
		this.areas = areas;
		return this;
	}

	public WithdrawalQuery company(String company) {
		if (company == null) {
			throw new RuntimeException("company is null");
		}
		this.company = company;
		return this;
	}

	public WithdrawalQuery companyLike(String companyLike) {
		if (companyLike == null) {
			throw new RuntimeException("company is null");
		}
		this.companyLike = companyLike;
		return this;
	}

	public WithdrawalQuery companys(List<String> companys) {
		if (companys == null) {
			throw new RuntimeException("companys is empty ");
		}
		this.companys = companys;
		return this;
	}

	public WithdrawalQuery dept(String dept) {
		if (dept == null) {
			throw new RuntimeException("dept is null");
		}
		this.dept = dept;
		return this;
	}

	public WithdrawalQuery deptLike(String deptLike) {
		if (deptLike == null) {
			throw new RuntimeException("dept is null");
		}
		this.deptLike = deptLike;
		return this;
	}

	public WithdrawalQuery depts(List<String> depts) {
		if (depts == null) {
			throw new RuntimeException("depts is empty ");
		}
		this.depts = depts;
		return this;
	}

	public WithdrawalQuery post(String post) {
		if (post == null) {
			throw new RuntimeException("post is null");
		}
		this.post = post;
		return this;
	}

	public WithdrawalQuery postLike(String postLike) {
		if (postLike == null) {
			throw new RuntimeException("post is null");
		}
		this.postLike = postLike;
		return this;
	}

	public WithdrawalQuery posts(List<String> posts) {
		if (posts == null) {
			throw new RuntimeException("posts is empty ");
		}
		this.posts = posts;
		return this;
	}

	public WithdrawalQuery appuser(String appuser) {
		if (appuser == null) {
			throw new RuntimeException("appuser is null");
		}
		this.appuser = appuser;
		return this;
	}

	public WithdrawalQuery appuserLike(String appuserLike) {
		if (appuserLike == null) {
			throw new RuntimeException("appuser is null");
		}
		this.appuserLike = appuserLike;
		return this;
	}

	public WithdrawalQuery appusers(List<String> appusers) {
		if (appusers == null) {
			throw new RuntimeException("appusers is empty ");
		}
		this.appusers = appusers;
		return this;
	}

	public WithdrawalQuery appdate(Date appdate) {
		if (appdate == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdate = appdate;
		return this;
	}

	public WithdrawalQuery appdateGreaterThanOrEqual(
			Date appdateGreaterThanOrEqual) {
		if (appdateGreaterThanOrEqual == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdateGreaterThanOrEqual = appdateGreaterThanOrEqual;
		return this;
	}

	public WithdrawalQuery appdateLessThanOrEqual(Date appdateLessThanOrEqual) {
		if (appdateLessThanOrEqual == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdateLessThanOrEqual = appdateLessThanOrEqual;
		return this;
	}

	public WithdrawalQuery appdates(List<Date> appdates) {
		if (appdates == null) {
			throw new RuntimeException("appdates is empty ");
		}
		this.appdates = appdates;
		return this;
	}

	public WithdrawalQuery appsum(Double appsum) {
		if (appsum == null) {
			throw new RuntimeException("appsum is null");
		}
		this.appsum = appsum;
		return this;
	}

	public WithdrawalQuery appsumGreaterThanOrEqual(
			Double appsumGreaterThanOrEqual) {
		if (appsumGreaterThanOrEqual == null) {
			throw new RuntimeException("appsum is null");
		}
		this.appsumGreaterThanOrEqual = appsumGreaterThanOrEqual;
		return this;
	}

	public WithdrawalQuery appsumLessThanOrEqual(Double appsumLessThanOrEqual) {
		if (appsumLessThanOrEqual == null) {
			throw new RuntimeException("appsum is null");
		}
		this.appsumLessThanOrEqual = appsumLessThanOrEqual;
		return this;
	}

	public WithdrawalQuery appsums(List<Double> appsums) {
		if (appsums == null) {
			throw new RuntimeException("appsums is empty ");
		}
		this.appsums = appsums;
		return this;
	}

	public WithdrawalQuery content(String content) {
		if (content == null) {
			throw new RuntimeException("content is null");
		}
		this.content = content;
		return this;
	}

	public WithdrawalQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public WithdrawalQuery contents(List<String> contents) {
		if (contents == null) {
			throw new RuntimeException("contents is empty ");
		}
		this.contents = contents;
		return this;
	}

	public WithdrawalQuery remark(String remark) {
		if (remark == null) {
			throw new RuntimeException("remark is null");
		}
		this.remark = remark;
		return this;
	}

	public WithdrawalQuery remarkLike(String remarkLike) {
		if (remarkLike == null) {
			throw new RuntimeException("remark is null");
		}
		this.remarkLike = remarkLike;
		return this;
	}

	public WithdrawalQuery remarks(List<String> remarks) {
		if (remarks == null) {
			throw new RuntimeException("remarks is empty ");
		}
		this.remarks = remarks;
		return this;
	}

	public WithdrawalQuery status(Integer status) {
		if (status == null) {
			throw new RuntimeException("status is null");
		}
		this.status = status;
		return this;
	}

	public WithdrawalQuery statusGreaterThanOrEqual(
			Integer statusGreaterThanOrEqual) {
		if (statusGreaterThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
		return this;
	}

	public WithdrawalQuery statusLessThanOrEqual(Integer statusLessThanOrEqual) {
		if (statusLessThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusLessThanOrEqual = statusLessThanOrEqual;
		return this;
	}

	public WithdrawalQuery statuss(List<Integer> statuss) {
		if (statuss == null) {
			throw new RuntimeException("statuss is empty ");
		}
		this.statuss = statuss;
		return this;
	}

	public WithdrawalQuery processname(String processname) {
		if (processname == null) {
			throw new RuntimeException("processname is null");
		}
		this.processname = processname;
		return this;
	}

	public WithdrawalQuery processnameLike(String processnameLike) {
		if (processnameLike == null) {
			throw new RuntimeException("processname is null");
		}
		this.processnameLike = processnameLike;
		return this;
	}

	public WithdrawalQuery processnames(List<String> processnames) {
		if (processnames == null) {
			throw new RuntimeException("processnames is empty ");
		}
		this.processnames = processnames;
		return this;
	}

	public WithdrawalQuery processinstanceid(Long processinstanceid) {
		if (processinstanceid == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceid = processinstanceid;
		return this;
	}

	public WithdrawalQuery processinstanceidGreaterThanOrEqual(
			Long processinstanceidGreaterThanOrEqual) {
		if (processinstanceidGreaterThanOrEqual == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceidGreaterThanOrEqual = processinstanceidGreaterThanOrEqual;
		return this;
	}

	public WithdrawalQuery processinstanceidLessThanOrEqual(
			Long processinstanceidLessThanOrEqual) {
		if (processinstanceidLessThanOrEqual == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceidLessThanOrEqual = processinstanceidLessThanOrEqual;
		return this;
	}

	public WithdrawalQuery processinstanceids(List<Long> processinstanceids) {
		if (processinstanceids == null) {
			throw new RuntimeException("processinstanceids is empty ");
		}
		this.processinstanceids = processinstanceids;
		return this;
	}

	public WithdrawalQuery wfstatus(Long wfstatus) {
		if (wfstatus == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatus = wfstatus;
		return this;
	}

	public WithdrawalQuery wfstatusGreaterThanOrEqual(
			Long wfstatusGreaterThanOrEqual) {
		if (wfstatusGreaterThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusGreaterThanOrEqual = wfstatusGreaterThanOrEqual;
		return this;
	}

	public WithdrawalQuery wfstatusLessThanOrEqual(Long wfstatusLessThanOrEqual) {
		if (wfstatusLessThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusLessThanOrEqual = wfstatusLessThanOrEqual;
		return this;
	}

	public WithdrawalQuery wfstatuss(List<Long> wfstatuss) {
		if (wfstatuss == null) {
			throw new RuntimeException("wfstatuss is empty ");
		}
		this.wfstatuss = wfstatuss;
		return this;
	}

	public WithdrawalQuery brands1(String brands1) {
		if (brands1 == null) {
			throw new RuntimeException("brands1 is null");
		}
		this.brands1 = brands1;
		return this;
	}

	public WithdrawalQuery brands1Like(String brands1Like) {
		if (brands1Like == null) {
			throw new RuntimeException("brands1 is null");
		}
		this.brands1Like = brands1Like;
		return this;
	}

	public WithdrawalQuery brands1s(List<String> brands1s) {
		if (brands1s == null) {
			throw new RuntimeException("brands1s is empty ");
		}
		this.brands1s = brands1s;
		return this;
	}

	public WithdrawalQuery brands1account(Double brands1account) {
		if (brands1account == null) {
			throw new RuntimeException("brands1account is null");
		}
		this.brands1account = brands1account;
		return this;
	}

	public WithdrawalQuery brands1accountGreaterThanOrEqual(
			Double brands1accountGreaterThanOrEqual) {
		if (brands1accountGreaterThanOrEqual == null) {
			throw new RuntimeException("brands1account is null");
		}
		this.brands1accountGreaterThanOrEqual = brands1accountGreaterThanOrEqual;
		return this;
	}

	public WithdrawalQuery brands1accountLessThanOrEqual(
			Double brands1accountLessThanOrEqual) {
		if (brands1accountLessThanOrEqual == null) {
			throw new RuntimeException("brands1account is null");
		}
		this.brands1accountLessThanOrEqual = brands1accountLessThanOrEqual;
		return this;
	}

	public WithdrawalQuery brands1accounts(List<Double> brands1accounts) {
		if (brands1accounts == null) {
			throw new RuntimeException("brands1accounts is empty ");
		}
		this.brands1accounts = brands1accounts;
		return this;
	}

	public WithdrawalQuery brands2(String brands2) {
		if (brands2 == null) {
			throw new RuntimeException("brands2 is null");
		}
		this.brands2 = brands2;
		return this;
	}

	public WithdrawalQuery brands2Like(String brands2Like) {
		if (brands2Like == null) {
			throw new RuntimeException("brands2 is null");
		}
		this.brands2Like = brands2Like;
		return this;
	}

	public WithdrawalQuery brands2s(List<String> brands2s) {
		if (brands2s == null) {
			throw new RuntimeException("brands2s is empty ");
		}
		this.brands2s = brands2s;
		return this;
	}

	public WithdrawalQuery brands2account(Double brands2account) {
		if (brands2account == null) {
			throw new RuntimeException("brands2account is null");
		}
		this.brands2account = brands2account;
		return this;
	}

	public WithdrawalQuery brands2accountGreaterThanOrEqual(
			Double brands2accountGreaterThanOrEqual) {
		if (brands2accountGreaterThanOrEqual == null) {
			throw new RuntimeException("brands2account is null");
		}
		this.brands2accountGreaterThanOrEqual = brands2accountGreaterThanOrEqual;
		return this;
	}

	public WithdrawalQuery brands2accountLessThanOrEqual(
			Double brands2accountLessThanOrEqual) {
		if (brands2accountLessThanOrEqual == null) {
			throw new RuntimeException("brands2account is null");
		}
		this.brands2accountLessThanOrEqual = brands2accountLessThanOrEqual;
		return this;
	}

	public WithdrawalQuery brands2accounts(List<Double> brands2accounts) {
		if (brands2accounts == null) {
			throw new RuntimeException("brands2accounts is empty ");
		}
		this.brands2accounts = brands2accounts;
		return this;
	}

	public WithdrawalQuery brands3(String brands3) {
		if (brands3 == null) {
			throw new RuntimeException("brands3 is null");
		}
		this.brands3 = brands3;
		return this;
	}

	public WithdrawalQuery brands3Like(String brands3Like) {
		if (brands3Like == null) {
			throw new RuntimeException("brands3 is null");
		}
		this.brands3Like = brands3Like;
		return this;
	}

	public WithdrawalQuery brands3s(List<String> brands3s) {
		if (brands3s == null) {
			throw new RuntimeException("brands3s is empty ");
		}
		this.brands3s = brands3s;
		return this;
	}

	public WithdrawalQuery brands3account(Double brands3account) {
		if (brands3account == null) {
			throw new RuntimeException("brands3account is null");
		}
		this.brands3account = brands3account;
		return this;
	}

	public WithdrawalQuery brands3accountGreaterThanOrEqual(
			Double brands3accountGreaterThanOrEqual) {
		if (brands3accountGreaterThanOrEqual == null) {
			throw new RuntimeException("brands3account is null");
		}
		this.brands3accountGreaterThanOrEqual = brands3accountGreaterThanOrEqual;
		return this;
	}

	public WithdrawalQuery brands3accountLessThanOrEqual(
			Double brands3accountLessThanOrEqual) {
		if (brands3accountLessThanOrEqual == null) {
			throw new RuntimeException("brands3account is null");
		}
		this.brands3accountLessThanOrEqual = brands3accountLessThanOrEqual;
		return this;
	}

	public WithdrawalQuery brands3accounts(List<Double> brands3accounts) {
		if (brands3accounts == null) {
			throw new RuntimeException("brands3accounts is empty ");
		}
		this.brands3accounts = brands3accounts;
		return this;
	}

	public WithdrawalQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public WithdrawalQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public WithdrawalQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public WithdrawalQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public WithdrawalQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public WithdrawalQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public WithdrawalQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public WithdrawalQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public WithdrawalQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public WithdrawalQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public WithdrawalQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public WithdrawalQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public WithdrawalQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public WithdrawalQuery updateBys(List<String> updateBys) {
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

			if ("appuser".equals(sortColumn)) {
				orderBy = "E.appuser" + a_x;
			}

			if ("appdate".equals(sortColumn)) {
				orderBy = "E.appdate" + a_x;
			}

			if ("appsum".equals(sortColumn)) {
				orderBy = "E.appsum" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.content" + a_x;
			}

			if ("remark".equals(sortColumn)) {
				orderBy = "E.remark" + a_x;
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
		addColumn("withdrawalid", "withdrawalid");
		addColumn("area", "area");
		addColumn("company", "company");
		addColumn("dept", "dept");
		addColumn("post", "post");
		addColumn("appuser", "appuser");
		addColumn("appdate", "appdate");
		addColumn("appsum", "appsum");
		addColumn("content", "content");
		addColumn("remark", "remark");
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
		addColumn("createBy", "createBy");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("updateBy", "updateBy");
	}

}