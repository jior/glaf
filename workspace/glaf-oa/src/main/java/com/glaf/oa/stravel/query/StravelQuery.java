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
package com.glaf.oa.stravel.query;

import java.util.*;

import com.glaf.core.query.DataQuery;

public class StravelQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> travelids;
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
	protected Date startdate;
	protected Date startdateGreaterThanOrEqual;
	protected Date startdateLessThanOrEqual;
	protected List<Date> startdates;
	protected Date enddate;
	protected Date enddateGreaterThanOrEqual;
	protected Date enddateLessThanOrEqual;
	protected List<Date> enddates;
	protected Double travelsum;
	protected Double travelsumGreaterThanOrEqual;
	protected Double travelsumLessThanOrEqual;
	protected List<Double> travelsums;
	protected Integer traveltype;
	protected Integer traveltypeGreaterThanOrEqual;
	protected Integer traveltypeLessThanOrEqual;
	protected List<Integer> traveltypes;
	protected List<Integer> statuss;
	protected String processname;
	protected String processnameLike;
	protected List<String> processnames;
	protected Double processinstanceid;
	protected Double processinstanceidGreaterThanOrEqual;
	protected Double processinstanceidLessThanOrEqual;
	protected List<Double> processinstanceids;
	protected Double wfstatus;
	protected Double wfstatusGreaterThanOrEqual;
	protected Double wfstatusLessThanOrEqual;
	protected List<Double> wfstatuss;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected List<Date> createDates;
	protected Date updateDate;
	protected Date updateDateGreaterThanOrEqual;
	protected Date updateDateLessThanOrEqual;
	protected List<Date> updateDates;
	protected String createByLike;
	protected List<String> createBys;
	protected String updateBy;
	protected String updateByLike;
	protected List<String> updateBys;
	protected String createByAndApply;

	protected String contentLike;

	public StravelQuery() {

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

	public Double getTravelsum() {
		return travelsum;
	}

	public Double getTravelsumGreaterThanOrEqual() {
		return travelsumGreaterThanOrEqual;
	}

	public Double getTravelsumLessThanOrEqual() {
		return travelsumLessThanOrEqual;
	}

	public List<Double> getTravelsums() {
		return travelsums;
	}

	public Integer getTraveltype() {
		return traveltype;
	}

	public Integer getTraveltypeGreaterThanOrEqual() {
		return traveltypeGreaterThanOrEqual;
	}

	public Integer getTraveltypeLessThanOrEqual() {
		return traveltypeLessThanOrEqual;
	}

	public List<Integer> getTraveltypes() {
		return traveltypes;
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

	public Double getProcessinstanceid() {
		return processinstanceid;
	}

	public Double getProcessinstanceidGreaterThanOrEqual() {
		return processinstanceidGreaterThanOrEqual;
	}

	public Double getProcessinstanceidLessThanOrEqual() {
		return processinstanceidLessThanOrEqual;
	}

	public List<Double> getProcessinstanceids() {
		return processinstanceids;
	}

	public Double getWfstatus() {
		return wfstatus;
	}

	public Double getWfstatusGreaterThanOrEqual() {
		return wfstatusGreaterThanOrEqual;
	}

	public Double getWfstatusLessThanOrEqual() {
		return wfstatusLessThanOrEqual;
	}

	public List<Double> getWfstatuss() {
		return wfstatuss;
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

	public List<String> getCreateBys() {
		return createBys;
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

	public String getCreateByAndApply() {
		return createByAndApply;
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

	public void setTravelsum(Double travelsum) {
		this.travelsum = travelsum;
	}

	public void setTravelsumGreaterThanOrEqual(
			Double travelsumGreaterThanOrEqual) {
		this.travelsumGreaterThanOrEqual = travelsumGreaterThanOrEqual;
	}

	public void setTravelsumLessThanOrEqual(Double travelsumLessThanOrEqual) {
		this.travelsumLessThanOrEqual = travelsumLessThanOrEqual;
	}

	public void setTravelsums(List<Double> travelsums) {
		this.travelsums = travelsums;
	}

	public void setTraveltype(Integer traveltype) {
		this.traveltype = traveltype;
	}

	public void setTraveltypeGreaterThanOrEqual(
			Integer traveltypeGreaterThanOrEqual) {
		this.traveltypeGreaterThanOrEqual = traveltypeGreaterThanOrEqual;
	}

	public void setTraveltypeLessThanOrEqual(Integer traveltypeLessThanOrEqual) {
		this.traveltypeLessThanOrEqual = traveltypeLessThanOrEqual;
	}

	public void setTraveltypes(List<Integer> traveltypes) {
		this.traveltypes = traveltypes;
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

	public void setProcessinstanceid(Double processinstanceid) {
		this.processinstanceid = processinstanceid;
	}

	public void setProcessinstanceidGreaterThanOrEqual(
			Double processinstanceidGreaterThanOrEqual) {
		this.processinstanceidGreaterThanOrEqual = processinstanceidGreaterThanOrEqual;
	}

	public void setProcessinstanceidLessThanOrEqual(
			Double processinstanceidLessThanOrEqual) {
		this.processinstanceidLessThanOrEqual = processinstanceidLessThanOrEqual;
	}

	public void setProcessinstanceids(List<Double> processinstanceids) {
		this.processinstanceids = processinstanceids;
	}

	public void setWfstatus(Double wfstatus) {
		this.wfstatus = wfstatus;
	}

	public void setWfstatusGreaterThanOrEqual(Double wfstatusGreaterThanOrEqual) {
		this.wfstatusGreaterThanOrEqual = wfstatusGreaterThanOrEqual;
	}

	public void setWfstatusLessThanOrEqual(Double wfstatusLessThanOrEqual) {
		this.wfstatusLessThanOrEqual = wfstatusLessThanOrEqual;
	}

	public void setWfstatuss(List<Double> wfstatuss) {
		this.wfstatuss = wfstatuss;
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

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateByLike(String createByLike) {
		this.createByLike = createByLike;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
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

	public void setCreateByAndApply(String createByAndApply) {
		this.createByAndApply = createByAndApply;
	}

	public StravelQuery area(String area) {
		if (area == null) {
			throw new RuntimeException("area is null");
		}
		this.area = area;
		return this;
	}

	public StravelQuery areaLike(String areaLike) {
		if (areaLike == null) {
			throw new RuntimeException("area is null");
		}
		this.areaLike = areaLike;
		return this;
	}

	public StravelQuery areas(List<String> areas) {
		if (areas == null) {
			throw new RuntimeException("areas is empty ");
		}
		this.areas = areas;
		return this;
	}

	public StravelQuery company(String company) {
		if (company == null) {
			throw new RuntimeException("company is null");
		}
		this.company = company;
		return this;
	}

	public StravelQuery companyLike(String companyLike) {
		if (companyLike == null) {
			throw new RuntimeException("company is null");
		}
		this.companyLike = companyLike;
		return this;
	}

	public StravelQuery companys(List<String> companys) {
		if (companys == null) {
			throw new RuntimeException("companys is empty ");
		}
		this.companys = companys;
		return this;
	}

	public StravelQuery dept(String dept) {
		if (dept == null) {
			throw new RuntimeException("dept is null");
		}
		this.dept = dept;
		return this;
	}

	public StravelQuery deptLike(String deptLike) {
		if (deptLike == null) {
			throw new RuntimeException("dept is null");
		}
		this.deptLike = deptLike;
		return this;
	}

	public StravelQuery depts(List<String> depts) {
		if (depts == null) {
			throw new RuntimeException("depts is empty ");
		}
		this.depts = depts;
		return this;
	}

	public StravelQuery post(String post) {
		if (post == null) {
			throw new RuntimeException("post is null");
		}
		this.post = post;
		return this;
	}

	public StravelQuery postLike(String postLike) {
		if (postLike == null) {
			throw new RuntimeException("post is null");
		}
		this.postLike = postLike;
		return this;
	}

	public StravelQuery posts(List<String> posts) {
		if (posts == null) {
			throw new RuntimeException("posts is empty ");
		}
		this.posts = posts;
		return this;
	}

	public StravelQuery appuser(String appuser) {
		if (appuser == null) {
			throw new RuntimeException("appuser is null");
		}
		this.appuser = appuser;
		return this;
	}

	public StravelQuery appuserLike(String appuserLike) {
		if (appuserLike == null) {
			throw new RuntimeException("appuser is null");
		}
		this.appuserLike = appuserLike;
		return this;
	}

	public StravelQuery appusers(List<String> appusers) {
		if (appusers == null) {
			throw new RuntimeException("appusers is empty ");
		}
		this.appusers = appusers;
		return this;
	}

	public StravelQuery appdate(Date appdate) {
		if (appdate == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdate = appdate;
		return this;
	}

	public StravelQuery appdateGreaterThanOrEqual(Date appdateGreaterThanOrEqual) {
		if (appdateGreaterThanOrEqual == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdateGreaterThanOrEqual = appdateGreaterThanOrEqual;
		return this;
	}

	public StravelQuery appdateLessThanOrEqual(Date appdateLessThanOrEqual) {
		if (appdateLessThanOrEqual == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdateLessThanOrEqual = appdateLessThanOrEqual;
		return this;
	}

	public StravelQuery appdates(List<Date> appdates) {
		if (appdates == null) {
			throw new RuntimeException("appdates is empty ");
		}
		this.appdates = appdates;
		return this;
	}

	public StravelQuery startdate(Date startdate) {
		if (startdate == null) {
			throw new RuntimeException("startdate is null");
		}
		this.startdate = startdate;
		return this;
	}

	public StravelQuery startdateGreaterThanOrEqual(
			Date startdateGreaterThanOrEqual) {
		if (startdateGreaterThanOrEqual == null) {
			throw new RuntimeException("startdate is null");
		}
		this.startdateGreaterThanOrEqual = startdateGreaterThanOrEqual;
		return this;
	}

	public StravelQuery startdateLessThanOrEqual(Date startdateLessThanOrEqual) {
		if (startdateLessThanOrEqual == null) {
			throw new RuntimeException("startdate is null");
		}
		this.startdateLessThanOrEqual = startdateLessThanOrEqual;
		return this;
	}

	public StravelQuery startdates(List<Date> startdates) {
		if (startdates == null) {
			throw new RuntimeException("startdates is empty ");
		}
		this.startdates = startdates;
		return this;
	}

	public StravelQuery enddate(Date enddate) {
		if (enddate == null) {
			throw new RuntimeException("enddate is null");
		}
		this.enddate = enddate;
		return this;
	}

	public StravelQuery enddateGreaterThanOrEqual(Date enddateGreaterThanOrEqual) {
		if (enddateGreaterThanOrEqual == null) {
			throw new RuntimeException("enddate is null");
		}
		this.enddateGreaterThanOrEqual = enddateGreaterThanOrEqual;
		return this;
	}

	public StravelQuery enddateLessThanOrEqual(Date enddateLessThanOrEqual) {
		if (enddateLessThanOrEqual == null) {
			throw new RuntimeException("enddate is null");
		}
		this.enddateLessThanOrEqual = enddateLessThanOrEqual;
		return this;
	}

	public StravelQuery enddates(List<Date> enddates) {
		if (enddates == null) {
			throw new RuntimeException("enddates is empty ");
		}
		this.enddates = enddates;
		return this;
	}

	public StravelQuery travelsum(Double travelsum) {
		if (travelsum == null) {
			throw new RuntimeException("travelsum is null");
		}
		this.travelsum = travelsum;
		return this;
	}

	public StravelQuery travelsumGreaterThanOrEqual(
			Double travelsumGreaterThanOrEqual) {
		if (travelsumGreaterThanOrEqual == null) {
			throw new RuntimeException("travelsum is null");
		}
		this.travelsumGreaterThanOrEqual = travelsumGreaterThanOrEqual;
		return this;
	}

	public StravelQuery travelsumLessThanOrEqual(Double travelsumLessThanOrEqual) {
		if (travelsumLessThanOrEqual == null) {
			throw new RuntimeException("travelsum is null");
		}
		this.travelsumLessThanOrEqual = travelsumLessThanOrEqual;
		return this;
	}

	public StravelQuery travelsums(List<Double> travelsums) {
		if (travelsums == null) {
			throw new RuntimeException("travelsums is empty ");
		}
		this.travelsums = travelsums;
		return this;
	}

	public StravelQuery traveltype(Integer traveltype) {
		if (traveltype == null) {
			throw new RuntimeException("traveltype is null");
		}
		this.traveltype = traveltype;
		return this;
	}

	public StravelQuery traveltypeGreaterThanOrEqual(
			Integer traveltypeGreaterThanOrEqual) {
		if (traveltypeGreaterThanOrEqual == null) {
			throw new RuntimeException("traveltype is null");
		}
		this.traveltypeGreaterThanOrEqual = traveltypeGreaterThanOrEqual;
		return this;
	}

	public StravelQuery traveltypeLessThanOrEqual(
			Integer traveltypeLessThanOrEqual) {
		if (traveltypeLessThanOrEqual == null) {
			throw new RuntimeException("traveltype is null");
		}
		this.traveltypeLessThanOrEqual = traveltypeLessThanOrEqual;
		return this;
	}

	public StravelQuery traveltypes(List<Integer> traveltypes) {
		if (traveltypes == null) {
			throw new RuntimeException("traveltypes is empty ");
		}
		this.traveltypes = traveltypes;
		return this;
	}

	public StravelQuery status(Integer status) {
		if (status == null) {
			throw new RuntimeException("status is null");
		}
		this.status = status;
		return this;
	}

	public StravelQuery statusGreaterThanOrEqual(
			Integer statusGreaterThanOrEqual) {
		if (statusGreaterThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
		return this;
	}

	public StravelQuery statusLessThanOrEqual(Integer statusLessThanOrEqual) {
		if (statusLessThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusLessThanOrEqual = statusLessThanOrEqual;
		return this;
	}

	public StravelQuery statuss(List<Integer> statuss) {
		if (statuss == null) {
			throw new RuntimeException("statuss is empty ");
		}
		this.statuss = statuss;
		return this;
	}

	public StravelQuery processname(String processname) {
		if (processname == null) {
			throw new RuntimeException("processname is null");
		}
		this.processname = processname;
		return this;
	}

	public StravelQuery processnameLike(String processnameLike) {
		if (processnameLike == null) {
			throw new RuntimeException("processname is null");
		}
		this.processnameLike = processnameLike;
		return this;
	}

	public StravelQuery processnames(List<String> processnames) {
		if (processnames == null) {
			throw new RuntimeException("processnames is empty ");
		}
		this.processnames = processnames;
		return this;
	}

	public StravelQuery processinstanceid(Double processinstanceid) {
		if (processinstanceid == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceid = processinstanceid;
		return this;
	}

	public StravelQuery processinstanceidGreaterThanOrEqual(
			Double processinstanceidGreaterThanOrEqual) {
		if (processinstanceidGreaterThanOrEqual == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceidGreaterThanOrEqual = processinstanceidGreaterThanOrEqual;
		return this;
	}

	public StravelQuery processinstanceidLessThanOrEqual(
			Double processinstanceidLessThanOrEqual) {
		if (processinstanceidLessThanOrEqual == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceidLessThanOrEqual = processinstanceidLessThanOrEqual;
		return this;
	}

	public StravelQuery processinstanceids(List<Double> processinstanceids) {
		if (processinstanceids == null) {
			throw new RuntimeException("processinstanceids is empty ");
		}
		this.processinstanceids = processinstanceids;
		return this;
	}

	public StravelQuery wfstatus(Double wfstatus) {
		if (wfstatus == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatus = wfstatus;
		return this;
	}

	public StravelQuery wfstatusGreaterThanOrEqual(
			Double wfstatusGreaterThanOrEqual) {
		if (wfstatusGreaterThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusGreaterThanOrEqual = wfstatusGreaterThanOrEqual;
		return this;
	}

	public StravelQuery wfstatusLessThanOrEqual(Double wfstatusLessThanOrEqual) {
		if (wfstatusLessThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusLessThanOrEqual = wfstatusLessThanOrEqual;
		return this;
	}

	public StravelQuery wfstatuss(List<Double> wfstatuss) {
		if (wfstatuss == null) {
			throw new RuntimeException("wfstatuss is empty ");
		}
		this.wfstatuss = wfstatuss;
		return this;
	}

	public StravelQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public StravelQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public StravelQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public StravelQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public StravelQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public StravelQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public StravelQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public StravelQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public StravelQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public StravelQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public StravelQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public StravelQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public StravelQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public StravelQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public StravelQuery createByAndApply(String createByAndApply) {
		if (createByAndApply == null) {
			throw new RuntimeException("createByAndApply is null");
		}
		this.createByAndApply = createByAndApply;
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

			if ("startdate".equals(sortColumn)) {
				orderBy = "E.startdate" + a_x;
			}

			if ("enddate".equals(sortColumn)) {
				orderBy = "E.enddate" + a_x;
			}

			if ("travelsum".equals(sortColumn)) {
				orderBy = "E.travelsum" + a_x;
			}

			if ("traveltype".equals(sortColumn)) {
				orderBy = "E.traveltype" + a_x;
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

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.createDate" + a_x;
			}

			if ("updateDate".equals(sortColumn)) {
				orderBy = "E.updateDate" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.createBy" + a_x;
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
		addColumn("travelid", "travelid");
		addColumn("area", "area");
		addColumn("company", "company");
		addColumn("dept", "dept");
		addColumn("post", "post");
		addColumn("appuser", "appuser");
		addColumn("appdate", "appdate");
		addColumn("startdate", "startdate");
		addColumn("enddate", "enddate");
		addColumn("travelsum", "travelsum");
		addColumn("traveltype", "traveltype");
		addColumn("status", "status");
		addColumn("processname", "processname");
		addColumn("processinstanceid", "processinstanceid");
		addColumn("wfstatus", "wfstatus");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("createBy", "createBy");
		addColumn("updateBy", "updateBy");
	}

}