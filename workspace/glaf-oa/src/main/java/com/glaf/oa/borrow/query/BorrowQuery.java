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
package com.glaf.oa.borrow.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class BorrowQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> borrowids;
	protected String area;
	protected String areaLike;
	protected List<String> areas;
	protected String borrowNo;
	protected String borrowNoLike;
	protected List<String> borrowNos;
	protected String company;
	protected String companyLike;
	protected List<String> companys;
	protected String dept;
	protected String deptLike;
	protected List<String> depts;
	protected String appuser;
	protected String appuserLike;
	protected List<String> appusers;
	protected Date appdate;
	protected Date appdateGreaterThanOrEqual;
	protected Date appdateLessThanOrEqual;
	protected List<Date> appdates;
	protected String post;
	protected String postLike;
	protected List<String> posts;
	protected String content;
	protected String contentLike;
	protected List<String> contents;
	protected Date startdate;
	protected Date startdateGreaterThanOrEqual;
	protected Date startdateLessThanOrEqual;
	protected List<Date> startdates;
	protected Date enddate;
	protected Date enddateGreaterThanOrEqual;
	protected Date enddateLessThanOrEqual;
	protected List<Date> enddates;
	protected Integer daynum;
	protected Integer daynumGreaterThanOrEqual;
	protected Integer daynumLessThanOrEqual;
	protected List<Integer> daynums;
	protected String details;
	protected String detailsLike;
	protected List<String> detailss;
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

	public BorrowQuery() {

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

	public Date getStartdate() {
		return startdate;
	}

	public Date getStartdateGreaterThanOrEqual() {
		return startdateGreaterThanOrEqual;
	}

	public Date getStartdateLessThanOrEqual() {
		return startdateLessThanOrEqual;
	}

	public List<Date> getStartdates() {
		return startdates;
	}

	public Date getEnddate() {
		return enddate;
	}

	public Date getEnddateGreaterThanOrEqual() {
		return enddateGreaterThanOrEqual;
	}

	public Date getEnddateLessThanOrEqual() {
		return enddateLessThanOrEqual;
	}

	public List<Date> getEnddates() {
		return enddates;
	}

	public Integer getDaynum() {
		return daynum;
	}

	public Integer getDaynumGreaterThanOrEqual() {
		return daynumGreaterThanOrEqual;
	}

	public Integer getDaynumLessThanOrEqual() {
		return daynumLessThanOrEqual;
	}

	public List<Integer> getDaynums() {
		return daynums;
	}

	public String getDetails() {
		return details;
	}

	public String getDetailsLike() {
		if (detailsLike != null && detailsLike.trim().length() > 0) {
			if (!detailsLike.startsWith("%")) {
				detailsLike = "%" + detailsLike;
			}
			if (!detailsLike.endsWith("%")) {
				detailsLike = detailsLike + "%";
			}
		}
		return detailsLike;
	}

	public List<String> getDetailss() {
		return detailss;
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

	public void setPost(String post) {
		this.post = post;
	}

	public void setPostLike(String postLike) {
		this.postLike = postLike;
	}

	public void setPosts(List<String> posts) {
		this.posts = posts;
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

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public void setStartdateGreaterThanOrEqual(Date startdateGreaterThanOrEqual) {
		this.startdateGreaterThanOrEqual = startdateGreaterThanOrEqual;
	}

	public void setStartdateLessThanOrEqual(Date startdateLessThanOrEqual) {
		this.startdateLessThanOrEqual = startdateLessThanOrEqual;
	}

	public void setStartdates(List<Date> startdates) {
		this.startdates = startdates;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public void setEnddateGreaterThanOrEqual(Date enddateGreaterThanOrEqual) {
		this.enddateGreaterThanOrEqual = enddateGreaterThanOrEqual;
	}

	public void setEnddateLessThanOrEqual(Date enddateLessThanOrEqual) {
		this.enddateLessThanOrEqual = enddateLessThanOrEqual;
	}

	public void setEnddates(List<Date> enddates) {
		this.enddates = enddates;
	}

	public void setDaynum(Integer daynum) {
		this.daynum = daynum;
	}

	public void setDaynumGreaterThanOrEqual(Integer daynumGreaterThanOrEqual) {
		this.daynumGreaterThanOrEqual = daynumGreaterThanOrEqual;
	}

	public void setDaynumLessThanOrEqual(Integer daynumLessThanOrEqual) {
		this.daynumLessThanOrEqual = daynumLessThanOrEqual;
	}

	public void setDaynums(List<Integer> daynums) {
		this.daynums = daynums;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setDetailsLike(String detailsLike) {
		this.detailsLike = detailsLike;
	}

	public void setDetailss(List<String> detailss) {
		this.detailss = detailss;
	}

	public void setAppsum(Double appsum) {
		this.appsum = appsum;
	}

	public String getBorrowNo() {
		return borrowNo;
	}

	public String getBorrowNoLike() {
		if (borrowNoLike != null && borrowNoLike.trim().length() > 0) {
			if (!borrowNoLike.startsWith("%")) {
				borrowNoLike = "%" + borrowNoLike;
			}
			if (!borrowNoLike.endsWith("%")) {
				borrowNoLike = borrowNoLike + "%";
			}
		}
		return borrowNoLike;
	}

	public List<String> getBorrowNos() {
		return borrowNos;
	}

	public void setBorrowNo(String borrowNo) {
		this.borrowNo = borrowNo;
	}

	public void setBorrowNoLike(String borrowNoLike) {
		this.borrowNoLike = borrowNoLike;
	}

	public void setBorrowNos(List<String> borrowNos) {
		this.borrowNos = borrowNos;
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

	public BorrowQuery area(String area) {
		if (area == null) {
			throw new RuntimeException("area is null");
		}
		this.area = area;
		return this;
	}

	public BorrowQuery areaLike(String areaLike) {
		if (areaLike == null) {
			throw new RuntimeException("area is null");
		}
		this.areaLike = areaLike;
		return this;
	}

	public BorrowQuery areas(List<String> areas) {
		if (areas == null) {
			throw new RuntimeException("areas is empty ");
		}
		this.areas = areas;
		return this;
	}

	public BorrowQuery borrowNo(String borrowNo) {
		if (borrowNo == null) {
			throw new RuntimeException("borrowNo is null");
		}
		this.borrowNo = borrowNo;
		return this;
	}

	public BorrowQuery borrowNoLike(String borrowNoLike) {
		if (borrowNoLike == null) {
			throw new RuntimeException("borrowNo is null");
		}
		this.borrowNoLike = borrowNoLike;
		return this;
	}

	public BorrowQuery borrowNos(List<String> borrowNos) {
		if (borrowNos == null) {
			throw new RuntimeException("borrowNos is empty ");
		}
		this.borrowNos = borrowNos;
		return this;
	}

	public BorrowQuery company(String company) {
		if (company == null) {
			throw new RuntimeException("company is null");
		}
		this.company = company;
		return this;
	}

	public BorrowQuery companyLike(String companyLike) {
		if (companyLike == null) {
			throw new RuntimeException("company is null");
		}
		this.companyLike = companyLike;
		return this;
	}

	public BorrowQuery companys(List<String> companys) {
		if (companys == null) {
			throw new RuntimeException("companys is empty ");
		}
		this.companys = companys;
		return this;
	}

	public BorrowQuery dept(String dept) {
		if (dept == null) {
			throw new RuntimeException("dept is null");
		}
		this.dept = dept;
		return this;
	}

	public BorrowQuery deptLike(String deptLike) {
		if (deptLike == null) {
			throw new RuntimeException("dept is null");
		}
		this.deptLike = deptLike;
		return this;
	}

	public BorrowQuery depts(List<String> depts) {
		if (depts == null) {
			throw new RuntimeException("depts is empty ");
		}
		this.depts = depts;
		return this;
	}

	public BorrowQuery appuser(String appuser) {
		if (appuser == null) {
			throw new RuntimeException("appuser is null");
		}
		this.appuser = appuser;
		return this;
	}

	public BorrowQuery appuserLike(String appuserLike) {
		if (appuserLike == null) {
			throw new RuntimeException("appuser is null");
		}
		this.appuserLike = appuserLike;
		return this;
	}

	public BorrowQuery appusers(List<String> appusers) {
		if (appusers == null) {
			throw new RuntimeException("appusers is empty ");
		}
		this.appusers = appusers;
		return this;
	}

	public BorrowQuery appdate(Date appdate) {
		if (appdate == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdate = appdate;
		return this;
	}

	public BorrowQuery appdateGreaterThanOrEqual(Date appdateGreaterThanOrEqual) {
		if (appdateGreaterThanOrEqual == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdateGreaterThanOrEqual = appdateGreaterThanOrEqual;
		return this;
	}

	public BorrowQuery appdateLessThanOrEqual(Date appdateLessThanOrEqual) {
		if (appdateLessThanOrEqual == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdateLessThanOrEqual = appdateLessThanOrEqual;
		return this;
	}

	public BorrowQuery appdates(List<Date> appdates) {
		if (appdates == null) {
			throw new RuntimeException("appdates is empty ");
		}
		this.appdates = appdates;
		return this;
	}

	public BorrowQuery post(String post) {
		if (post == null) {
			throw new RuntimeException("post is null");
		}
		this.post = post;
		return this;
	}

	public BorrowQuery postLike(String postLike) {
		if (postLike == null) {
			throw new RuntimeException("post is null");
		}
		this.postLike = postLike;
		return this;
	}

	public BorrowQuery posts(List<String> posts) {
		if (posts == null) {
			throw new RuntimeException("posts is empty ");
		}
		this.posts = posts;
		return this;
	}

	public BorrowQuery content(String content) {
		if (content == null) {
			throw new RuntimeException("content is null");
		}
		this.content = content;
		return this;
	}

	public BorrowQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public BorrowQuery contents(List<String> contents) {
		if (contents == null) {
			throw new RuntimeException("contents is empty ");
		}
		this.contents = contents;
		return this;
	}

	public BorrowQuery startdate(Date startdate) {
		if (startdate == null) {
			throw new RuntimeException("startdate is null");
		}
		this.startdate = startdate;
		return this;
	}

	public BorrowQuery startdateGreaterThanOrEqual(
			Date startdateGreaterThanOrEqual) {
		if (startdateGreaterThanOrEqual == null) {
			throw new RuntimeException("startdate is null");
		}
		this.startdateGreaterThanOrEqual = startdateGreaterThanOrEqual;
		return this;
	}

	public BorrowQuery startdateLessThanOrEqual(Date startdateLessThanOrEqual) {
		if (startdateLessThanOrEqual == null) {
			throw new RuntimeException("startdate is null");
		}
		this.startdateLessThanOrEqual = startdateLessThanOrEqual;
		return this;
	}

	public BorrowQuery startdates(List<Date> startdates) {
		if (startdates == null) {
			throw new RuntimeException("startdates is empty ");
		}
		this.startdates = startdates;
		return this;
	}

	public BorrowQuery enddate(Date enddate) {
		if (enddate == null) {
			throw new RuntimeException("enddate is null");
		}
		this.enddate = enddate;
		return this;
	}

	public BorrowQuery enddateGreaterThanOrEqual(Date enddateGreaterThanOrEqual) {
		if (enddateGreaterThanOrEqual == null) {
			throw new RuntimeException("enddate is null");
		}
		this.enddateGreaterThanOrEqual = enddateGreaterThanOrEqual;
		return this;
	}

	public BorrowQuery enddateLessThanOrEqual(Date enddateLessThanOrEqual) {
		if (enddateLessThanOrEqual == null) {
			throw new RuntimeException("enddate is null");
		}
		this.enddateLessThanOrEqual = enddateLessThanOrEqual;
		return this;
	}

	public BorrowQuery enddates(List<Date> enddates) {
		if (enddates == null) {
			throw new RuntimeException("enddates is empty ");
		}
		this.enddates = enddates;
		return this;
	}

	public BorrowQuery daynum(Integer daynum) {
		if (daynum == null) {
			throw new RuntimeException("daynum is null");
		}
		this.daynum = daynum;
		return this;
	}

	public BorrowQuery daynumGreaterThanOrEqual(Integer daynumGreaterThanOrEqual) {
		if (daynumGreaterThanOrEqual == null) {
			throw new RuntimeException("daynum is null");
		}
		this.daynumGreaterThanOrEqual = daynumGreaterThanOrEqual;
		return this;
	}

	public BorrowQuery daynumLessThanOrEqual(Integer daynumLessThanOrEqual) {
		if (daynumLessThanOrEqual == null) {
			throw new RuntimeException("daynum is null");
		}
		this.daynumLessThanOrEqual = daynumLessThanOrEqual;
		return this;
	}

	public BorrowQuery daynums(List<Integer> daynums) {
		if (daynums == null) {
			throw new RuntimeException("daynums is empty ");
		}
		this.daynums = daynums;
		return this;
	}

	public BorrowQuery details(String details) {
		if (details == null) {
			throw new RuntimeException("details is null");
		}
		this.details = details;
		return this;
	}

	public BorrowQuery detailsLike(String detailsLike) {
		if (detailsLike == null) {
			throw new RuntimeException("details is null");
		}
		this.detailsLike = detailsLike;
		return this;
	}

	public BorrowQuery detailss(List<String> detailss) {
		if (detailss == null) {
			throw new RuntimeException("detailss is empty ");
		}
		this.detailss = detailss;
		return this;
	}

	public BorrowQuery appsum(Double appsum) {
		if (appsum == null) {
			throw new RuntimeException("appsum is null");
		}
		this.appsum = appsum;
		return this;
	}

	public BorrowQuery appsumGreaterThanOrEqual(Double appsumGreaterThanOrEqual) {
		if (appsumGreaterThanOrEqual == null) {
			throw new RuntimeException("appsum is null");
		}
		this.appsumGreaterThanOrEqual = appsumGreaterThanOrEqual;
		return this;
	}

	public BorrowQuery appsumLessThanOrEqual(Double appsumLessThanOrEqual) {
		if (appsumLessThanOrEqual == null) {
			throw new RuntimeException("appsum is null");
		}
		this.appsumLessThanOrEqual = appsumLessThanOrEqual;
		return this;
	}

	public BorrowQuery appsums(List<Double> appsums) {
		if (appsums == null) {
			throw new RuntimeException("appsums is empty ");
		}
		this.appsums = appsums;
		return this;
	}

	public BorrowQuery status(Integer status) {
		if (status == null) {
			throw new RuntimeException("status is null");
		}
		this.status = status;
		return this;
	}

	public BorrowQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual) {
		if (statusGreaterThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
		return this;
	}

	public BorrowQuery statusLessThanOrEqual(Integer statusLessThanOrEqual) {
		if (statusLessThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusLessThanOrEqual = statusLessThanOrEqual;
		return this;
	}

	public BorrowQuery statuss(List<Integer> statuss) {
		if (statuss == null) {
			throw new RuntimeException("statuss is empty ");
		}
		this.statuss = statuss;
		return this;
	}

	public BorrowQuery processname(String processname) {
		if (processname == null) {
			throw new RuntimeException("processname is null");
		}
		this.processname = processname;
		return this;
	}

	public BorrowQuery processnameLike(String processnameLike) {
		if (processnameLike == null) {
			throw new RuntimeException("processname is null");
		}
		this.processnameLike = processnameLike;
		return this;
	}

	public BorrowQuery processnames(List<String> processnames) {
		if (processnames == null) {
			throw new RuntimeException("processnames is empty ");
		}
		this.processnames = processnames;
		return this;
	}

	public BorrowQuery processinstanceid(Long processinstanceid) {
		if (processinstanceid == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceid = processinstanceid;
		return this;
	}

	public BorrowQuery processinstanceidGreaterThanOrEqual(
			Long processinstanceidGreaterThanOrEqual) {
		if (processinstanceidGreaterThanOrEqual == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceidGreaterThanOrEqual = processinstanceidGreaterThanOrEqual;
		return this;
	}

	public BorrowQuery processinstanceidLessThanOrEqual(
			Long processinstanceidLessThanOrEqual) {
		if (processinstanceidLessThanOrEqual == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceidLessThanOrEqual = processinstanceidLessThanOrEqual;
		return this;
	}

	public BorrowQuery processinstanceids(List<Long> processinstanceids) {
		if (processinstanceids == null) {
			throw new RuntimeException("processinstanceids is empty ");
		}
		this.processinstanceids = processinstanceids;
		return this;
	}

	public BorrowQuery wfstatus(Long wfstatus) {
		if (wfstatus == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatus = wfstatus;
		return this;
	}

	public BorrowQuery wfstatusGreaterThanOrEqual(
			Long wfstatusGreaterThanOrEqual) {
		if (wfstatusGreaterThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusGreaterThanOrEqual = wfstatusGreaterThanOrEqual;
		return this;
	}

	public BorrowQuery wfstatusLessThanOrEqual(Long wfstatusLessThanOrEqual) {
		if (wfstatusLessThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusLessThanOrEqual = wfstatusLessThanOrEqual;
		return this;
	}

	public BorrowQuery wfstatuss(List<Long> wfstatuss) {
		if (wfstatuss == null) {
			throw new RuntimeException("wfstatuss is empty ");
		}
		this.wfstatuss = wfstatuss;
		return this;
	}

	public BorrowQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public BorrowQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public BorrowQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public BorrowQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public BorrowQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public BorrowQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public BorrowQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public BorrowQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public BorrowQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public BorrowQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public BorrowQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public BorrowQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public BorrowQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public BorrowQuery updateBys(List<String> updateBys) {
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

			if ("appuser".equals(sortColumn)) {
				orderBy = "E.appuser" + a_x;
			}

			if ("appdate".equals(sortColumn)) {
				orderBy = "E.appdate" + a_x;
			}

			if ("post".equals(sortColumn)) {
				orderBy = "E.post" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.content" + a_x;
			}

			if ("startdate".equals(sortColumn)) {
				orderBy = "E.startdate" + a_x;
			}

			if ("borrowNo".equals(sortColumn)) {
				orderBy = "E.borrowNo" + a_x;
			}

			if ("enddate".equals(sortColumn)) {
				orderBy = "E.enddate" + a_x;
			}

			if ("daynum".equals(sortColumn)) {
				orderBy = "E.daynum" + a_x;
			}

			if ("details".equals(sortColumn)) {
				orderBy = "E.details" + a_x;
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
		addColumn("borrowid", "borrowid");
		addColumn("area", "area");
		addColumn("borrowNo", "borrowNo");
		addColumn("company", "company");
		addColumn("dept", "dept");
		addColumn("appuser", "appuser");
		addColumn("appdate", "appdate");
		addColumn("post", "post");
		addColumn("content", "content");
		addColumn("startdate", "startdate");
		addColumn("enddate", "enddate");
		addColumn("daynum", "daynum");
		addColumn("details", "details");
		addColumn("appsum", "appsum");
		addColumn("status", "status");
		addColumn("processname", "processname");
		addColumn("processinstanceid", "processinstanceid");
		addColumn("wfstatus", "wfstatus");
		addColumn("createBy", "createBy");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("updateBy", "updateBy");
	}

}