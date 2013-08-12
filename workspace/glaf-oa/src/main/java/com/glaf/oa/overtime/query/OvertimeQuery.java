
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
package com.glaf.oa.overtime.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class OvertimeQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Long overtimeid;
	protected Long overtimeidGreaterThanOrEqual;
	protected Long overtimeidLessThanOrEqual;
	protected List<Long> overtimeids;
	protected String area;
	protected String areaLike;
	protected List<String> areas;
	protected String company;
	protected String companyLike;
	protected List<String> companys;
	protected String dept;
	protected String deptLike;
	protected List<String> depts;
	protected String appuser;
	protected String appuserLike;
	protected List<String> appusers;
	protected String post;
	protected String postLike;
	protected List<String> posts;
	protected Date appdate;
	protected Date appdateGreaterThanOrEqual;
	protected Date appdateLessThanOrEqual;
	protected List<Date> appdates;
	protected Integer type;
	protected Integer typeGreaterThanOrEqual;
	protected Integer typeLessThanOrEqual;
	protected List<Integer> types;
	protected Date startdate;
	protected Date startdateGreaterThanOrEqual;
	protected Date startdateLessThanOrEqual;
	protected List<Date> startdates;
	protected Integer starttime;
	protected Integer starttimeGreaterThanOrEqual;
	protected Integer starttimeLessThanOrEqual;
	protected List<Integer> starttimes;
	protected Date enddate;
	protected Date enddateGreaterThanOrEqual;
	protected Date enddateLessThanOrEqual;
	protected List<Date> enddates;
	protected Integer endtime;
	protected Integer endtimeGreaterThanOrEqual;
	protected Integer endtimeLessThanOrEqual;
	protected List<Integer> endtimes;
	protected Double overtimesum;
	protected Double overtimesumGreaterThanOrEqual;
	protected Double overtimesumLessThanOrEqual;
	protected List<Double> overtimesums;
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

	public OvertimeQuery() {

	}

	public Long getOvertimeid() {
		return overtimeid;
	}

	public Long getOvertimeidGreaterThanOrEqual() {
		return overtimeidGreaterThanOrEqual;
	}

	public Long getOvertimeidLessThanOrEqual() {
		return overtimeidLessThanOrEqual;
	}

	public List<Long> getOvertimeids() {
		return overtimeids;
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

	public Integer getType() {
		return type;
	}

	public Integer getTypeGreaterThanOrEqual() {
		return typeGreaterThanOrEqual;
	}

	public Integer getTypeLessThanOrEqual() {
		return typeLessThanOrEqual;
	}

	public List<Integer> getTypes() {
		return types;
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

	public Integer getStarttime() {
		return starttime;
	}

	public Integer getStarttimeGreaterThanOrEqual() {
		return starttimeGreaterThanOrEqual;
	}

	public Integer getStarttimeLessThanOrEqual() {
		return starttimeLessThanOrEqual;
	}

	public List<Integer> getStarttimes() {
		return starttimes;
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

	public Integer getEndtime() {
		return endtime;
	}

	public Integer getEndtimeGreaterThanOrEqual() {
		return endtimeGreaterThanOrEqual;
	}

	public Integer getEndtimeLessThanOrEqual() {
		return endtimeLessThanOrEqual;
	}

	public List<Integer> getEndtimes() {
		return endtimes;
	}

	public Double getOvertimesum() {
		return overtimesum;
	}

	public Double getOvertimesumGreaterThanOrEqual() {
		return overtimesumGreaterThanOrEqual;
	}

	public Double getOvertimesumLessThanOrEqual() {
		return overtimesumLessThanOrEqual;
	}

	public List<Double> getOvertimesums() {
		return overtimesums;
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

	public void setOvertimeid(Long overtimeid) {
		this.overtimeid = overtimeid;
	}

	public void setOvertimeidGreaterThanOrEqual(
			Long overtimeidGreaterThanOrEqual) {
		this.overtimeidGreaterThanOrEqual = overtimeidGreaterThanOrEqual;
	}

	public void setOvertimeidLessThanOrEqual(Long overtimeidLessThanOrEqual) {
		this.overtimeidLessThanOrEqual = overtimeidLessThanOrEqual;
	}

	public void setOvertimeids(List<Long> overtimeids) {
		this.overtimeids = overtimeids;
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

	public void setPost(String post) {
		this.post = post;
	}

	public void setPostLike(String postLike) {
		this.postLike = postLike;
	}

	public void setPosts(List<String> posts) {
		this.posts = posts;
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

	public void setType(Integer type) {
		this.type = type;
	}

	public void setTypeGreaterThanOrEqual(Integer typeGreaterThanOrEqual) {
		this.typeGreaterThanOrEqual = typeGreaterThanOrEqual;
	}

	public void setTypeLessThanOrEqual(Integer typeLessThanOrEqual) {
		this.typeLessThanOrEqual = typeLessThanOrEqual;
	}

	public void setTypes(List<Integer> types) {
		this.types = types;
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

	public void setStarttime(Integer starttime) {
		this.starttime = starttime;
	}

	public void setStarttimeGreaterThanOrEqual(
			Integer starttimeGreaterThanOrEqual) {
		this.starttimeGreaterThanOrEqual = starttimeGreaterThanOrEqual;
	}

	public void setStarttimeLessThanOrEqual(Integer starttimeLessThanOrEqual) {
		this.starttimeLessThanOrEqual = starttimeLessThanOrEqual;
	}

	public void setStarttimes(List<Integer> starttimes) {
		this.starttimes = starttimes;
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

	public void setEndtime(Integer endtime) {
		this.endtime = endtime;
	}

	public void setEndtimeGreaterThanOrEqual(Integer endtimeGreaterThanOrEqual) {
		this.endtimeGreaterThanOrEqual = endtimeGreaterThanOrEqual;
	}

	public void setEndtimeLessThanOrEqual(Integer endtimeLessThanOrEqual) {
		this.endtimeLessThanOrEqual = endtimeLessThanOrEqual;
	}

	public void setEndtimes(List<Integer> endtimes) {
		this.endtimes = endtimes;
	}

	public void setOvertimesum(Double overtimesum) {
		this.overtimesum = overtimesum;
	}

	public void setOvertimesumGreaterThanOrEqual(
			Double overtimesumGreaterThanOrEqual) {
		this.overtimesumGreaterThanOrEqual = overtimesumGreaterThanOrEqual;
	}

	public void setOvertimesumLessThanOrEqual(Double overtimesumLessThanOrEqual) {
		this.overtimesumLessThanOrEqual = overtimesumLessThanOrEqual;
	}

	public void setOvertimesums(List<Double> overtimesums) {
		this.overtimesums = overtimesums;
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

	public OvertimeQuery overtimeid(Long overtimeid) {
		if (overtimeid == null) {
			throw new RuntimeException("overtimeid is null");
		}
		this.overtimeid = overtimeid;
		return this;
	}

	public OvertimeQuery overtimeidGreaterThanOrEqual(
			Long overtimeidGreaterThanOrEqual) {
		if (overtimeidGreaterThanOrEqual == null) {
			throw new RuntimeException("overtimeid is null");
		}
		this.overtimeidGreaterThanOrEqual = overtimeidGreaterThanOrEqual;
		return this;
	}

	public OvertimeQuery overtimeidLessThanOrEqual(
			Long overtimeidLessThanOrEqual) {
		if (overtimeidLessThanOrEqual == null) {
			throw new RuntimeException("overtimeid is null");
		}
		this.overtimeidLessThanOrEqual = overtimeidLessThanOrEqual;
		return this;
	}

	public OvertimeQuery overtimeids(List<Long> overtimeids) {
		if (overtimeids == null) {
			throw new RuntimeException("overtimeids is empty ");
		}
		this.overtimeids = overtimeids;
		return this;
	}

	public OvertimeQuery area(String area) {
		if (area == null) {
			throw new RuntimeException("area is null");
		}
		this.area = area;
		return this;
	}

	public OvertimeQuery areaLike(String areaLike) {
		if (areaLike == null) {
			throw new RuntimeException("area is null");
		}
		this.areaLike = areaLike;
		return this;
	}

	public OvertimeQuery areas(List<String> areas) {
		if (areas == null) {
			throw new RuntimeException("areas is empty ");
		}
		this.areas = areas;
		return this;
	}

	public OvertimeQuery company(String company) {
		if (company == null) {
			throw new RuntimeException("company is null");
		}
		this.company = company;
		return this;
	}

	public OvertimeQuery companyLike(String companyLike) {
		if (companyLike == null) {
			throw new RuntimeException("company is null");
		}
		this.companyLike = companyLike;
		return this;
	}

	public OvertimeQuery companys(List<String> companys) {
		if (companys == null) {
			throw new RuntimeException("companys is empty ");
		}
		this.companys = companys;
		return this;
	}

	public OvertimeQuery dept(String dept) {
		if (dept == null) {
			throw new RuntimeException("dept is null");
		}
		this.dept = dept;
		return this;
	}

	public OvertimeQuery deptLike(String deptLike) {
		if (deptLike == null) {
			throw new RuntimeException("dept is null");
		}
		this.deptLike = deptLike;
		return this;
	}

	public OvertimeQuery depts(List<String> depts) {
		if (depts == null) {
			throw new RuntimeException("depts is empty ");
		}
		this.depts = depts;
		return this;
	}

	public OvertimeQuery appuser(String appuser) {
		if (appuser == null) {
			throw new RuntimeException("appuser is null");
		}
		this.appuser = appuser;
		return this;
	}

	public OvertimeQuery appuserLike(String appuserLike) {
		if (appuserLike == null) {
			throw new RuntimeException("appuser is null");
		}
		this.appuserLike = appuserLike;
		return this;
	}

	public OvertimeQuery appusers(List<String> appusers) {
		if (appusers == null) {
			throw new RuntimeException("appusers is empty ");
		}
		this.appusers = appusers;
		return this;
	}

	public OvertimeQuery post(String post) {
		if (post == null) {
			throw new RuntimeException("post is null");
		}
		this.post = post;
		return this;
	}

	public OvertimeQuery postLike(String postLike) {
		if (postLike == null) {
			throw new RuntimeException("post is null");
		}
		this.postLike = postLike;
		return this;
	}

	public OvertimeQuery posts(List<String> posts) {
		if (posts == null) {
			throw new RuntimeException("posts is empty ");
		}
		this.posts = posts;
		return this;
	}

	public OvertimeQuery appdate(Date appdate) {
		if (appdate == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdate = appdate;
		return this;
	}

	public OvertimeQuery appdateGreaterThanOrEqual(
			Date appdateGreaterThanOrEqual) {
		if (appdateGreaterThanOrEqual == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdateGreaterThanOrEqual = appdateGreaterThanOrEqual;
		return this;
	}

	public OvertimeQuery appdateLessThanOrEqual(Date appdateLessThanOrEqual) {
		if (appdateLessThanOrEqual == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdateLessThanOrEqual = appdateLessThanOrEqual;
		return this;
	}

	public OvertimeQuery appdates(List<Date> appdates) {
		if (appdates == null) {
			throw new RuntimeException("appdates is empty ");
		}
		this.appdates = appdates;
		return this;
	}

	public OvertimeQuery type(Integer type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public OvertimeQuery typeGreaterThanOrEqual(Integer typeGreaterThanOrEqual) {
		if (typeGreaterThanOrEqual == null) {
			throw new RuntimeException("type is null");
		}
		this.typeGreaterThanOrEqual = typeGreaterThanOrEqual;
		return this;
	}

	public OvertimeQuery typeLessThanOrEqual(Integer typeLessThanOrEqual) {
		if (typeLessThanOrEqual == null) {
			throw new RuntimeException("type is null");
		}
		this.typeLessThanOrEqual = typeLessThanOrEqual;
		return this;
	}

	public OvertimeQuery types(List<Integer> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

	public OvertimeQuery startdate(Date startdate) {
		if (startdate == null) {
			throw new RuntimeException("startdate is null");
		}
		this.startdate = startdate;
		return this;
	}

	public OvertimeQuery startdateGreaterThanOrEqual(
			Date startdateGreaterThanOrEqual) {
		if (startdateGreaterThanOrEqual == null) {
			throw new RuntimeException("startdate is null");
		}
		this.startdateGreaterThanOrEqual = startdateGreaterThanOrEqual;
		return this;
	}

	public OvertimeQuery startdateLessThanOrEqual(Date startdateLessThanOrEqual) {
		if (startdateLessThanOrEqual == null) {
			throw new RuntimeException("startdate is null");
		}
		this.startdateLessThanOrEqual = startdateLessThanOrEqual;
		return this;
	}

	public OvertimeQuery startdates(List<Date> startdates) {
		if (startdates == null) {
			throw new RuntimeException("startdates is empty ");
		}
		this.startdates = startdates;
		return this;
	}

	public OvertimeQuery starttime(Integer starttime) {
		if (starttime == null) {
			throw new RuntimeException("starttime is null");
		}
		this.starttime = starttime;
		return this;
	}

	public OvertimeQuery starttimeGreaterThanOrEqual(
			Integer starttimeGreaterThanOrEqual) {
		if (starttimeGreaterThanOrEqual == null) {
			throw new RuntimeException("starttime is null");
		}
		this.starttimeGreaterThanOrEqual = starttimeGreaterThanOrEqual;
		return this;
	}

	public OvertimeQuery starttimeLessThanOrEqual(
			Integer starttimeLessThanOrEqual) {
		if (starttimeLessThanOrEqual == null) {
			throw new RuntimeException("starttime is null");
		}
		this.starttimeLessThanOrEqual = starttimeLessThanOrEqual;
		return this;
	}

	public OvertimeQuery starttimes(List<Integer> starttimes) {
		if (starttimes == null) {
			throw new RuntimeException("starttimes is empty ");
		}
		this.starttimes = starttimes;
		return this;
	}

	public OvertimeQuery enddate(Date enddate) {
		if (enddate == null) {
			throw new RuntimeException("enddate is null");
		}
		this.enddate = enddate;
		return this;
	}

	public OvertimeQuery enddateGreaterThanOrEqual(
			Date enddateGreaterThanOrEqual) {
		if (enddateGreaterThanOrEqual == null) {
			throw new RuntimeException("enddate is null");
		}
		this.enddateGreaterThanOrEqual = enddateGreaterThanOrEqual;
		return this;
	}

	public OvertimeQuery enddateLessThanOrEqual(Date enddateLessThanOrEqual) {
		if (enddateLessThanOrEqual == null) {
			throw new RuntimeException("enddate is null");
		}
		this.enddateLessThanOrEqual = enddateLessThanOrEqual;
		return this;
	}

	public OvertimeQuery enddates(List<Date> enddates) {
		if (enddates == null) {
			throw new RuntimeException("enddates is empty ");
		}
		this.enddates = enddates;
		return this;
	}

	public OvertimeQuery endtime(Integer endtime) {
		if (endtime == null) {
			throw new RuntimeException("endtime is null");
		}
		this.endtime = endtime;
		return this;
	}

	public OvertimeQuery endtimeGreaterThanOrEqual(
			Integer endtimeGreaterThanOrEqual) {
		if (endtimeGreaterThanOrEqual == null) {
			throw new RuntimeException("endtime is null");
		}
		this.endtimeGreaterThanOrEqual = endtimeGreaterThanOrEqual;
		return this;
	}

	public OvertimeQuery endtimeLessThanOrEqual(Integer endtimeLessThanOrEqual) {
		if (endtimeLessThanOrEqual == null) {
			throw new RuntimeException("endtime is null");
		}
		this.endtimeLessThanOrEqual = endtimeLessThanOrEqual;
		return this;
	}

	public OvertimeQuery endtimes(List<Integer> endtimes) {
		if (endtimes == null) {
			throw new RuntimeException("endtimes is empty ");
		}
		this.endtimes = endtimes;
		return this;
	}

	public OvertimeQuery overtimesum(Double overtimesum) {
		if (overtimesum == null) {
			throw new RuntimeException("overtimesum is null");
		}
		this.overtimesum = overtimesum;
		return this;
	}

	public OvertimeQuery overtimesumGreaterThanOrEqual(
			Double overtimesumGreaterThanOrEqual) {
		if (overtimesumGreaterThanOrEqual == null) {
			throw new RuntimeException("overtimesum is null");
		}
		this.overtimesumGreaterThanOrEqual = overtimesumGreaterThanOrEqual;
		return this;
	}

	public OvertimeQuery overtimesumLessThanOrEqual(
			Double overtimesumLessThanOrEqual) {
		if (overtimesumLessThanOrEqual == null) {
			throw new RuntimeException("overtimesum is null");
		}
		this.overtimesumLessThanOrEqual = overtimesumLessThanOrEqual;
		return this;
	}

	public OvertimeQuery overtimesums(List<Double> overtimesums) {
		if (overtimesums == null) {
			throw new RuntimeException("overtimesums is empty ");
		}
		this.overtimesums = overtimesums;
		return this;
	}

	public OvertimeQuery status(Integer status) {
		if (status == null) {
			throw new RuntimeException("status is null");
		}
		this.status = status;
		return this;
	}

	public OvertimeQuery statusGreaterThanOrEqual(
			Integer statusGreaterThanOrEqual) {
		if (statusGreaterThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
		return this;
	}

	public OvertimeQuery statusLessThanOrEqual(Integer statusLessThanOrEqual) {
		if (statusLessThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusLessThanOrEqual = statusLessThanOrEqual;
		return this;
	}

	public OvertimeQuery statuss(List<Integer> statuss) {
		if (statuss == null) {
			throw new RuntimeException("statuss is empty ");
		}
		this.statuss = statuss;
		return this;
	}

	public OvertimeQuery processname(String processname) {
		if (processname == null) {
			throw new RuntimeException("processname is null");
		}
		this.processname = processname;
		return this;
	}

	public OvertimeQuery processnameLike(String processnameLike) {
		if (processnameLike == null) {
			throw new RuntimeException("processname is null");
		}
		this.processnameLike = processnameLike;
		return this;
	}

	public OvertimeQuery processnames(List<String> processnames) {
		if (processnames == null) {
			throw new RuntimeException("processnames is empty ");
		}
		this.processnames = processnames;
		return this;
	}

	public OvertimeQuery processinstanceid(Long processinstanceid) {
		if (processinstanceid == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceid = processinstanceid;
		return this;
	}

	public OvertimeQuery processinstanceidGreaterThanOrEqual(
			Long processinstanceidGreaterThanOrEqual) {
		if (processinstanceidGreaterThanOrEqual == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceidGreaterThanOrEqual = processinstanceidGreaterThanOrEqual;
		return this;
	}

	public OvertimeQuery processinstanceidLessThanOrEqual(
			Long processinstanceidLessThanOrEqual) {
		if (processinstanceidLessThanOrEqual == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceidLessThanOrEqual = processinstanceidLessThanOrEqual;
		return this;
	}

	public OvertimeQuery processinstanceids(List<Long> processinstanceids) {
		if (processinstanceids == null) {
			throw new RuntimeException("processinstanceids is empty ");
		}
		this.processinstanceids = processinstanceids;
		return this;
	}

	public OvertimeQuery wfstatus(Long wfstatus) {
		if (wfstatus == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatus = wfstatus;
		return this;
	}

	public OvertimeQuery wfstatusGreaterThanOrEqual(
			Long wfstatusGreaterThanOrEqual) {
		if (wfstatusGreaterThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusGreaterThanOrEqual = wfstatusGreaterThanOrEqual;
		return this;
	}

	public OvertimeQuery wfstatusLessThanOrEqual(Long wfstatusLessThanOrEqual) {
		if (wfstatusLessThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusLessThanOrEqual = wfstatusLessThanOrEqual;
		return this;
	}

	public OvertimeQuery wfstatuss(List<Long> wfstatuss) {
		if (wfstatuss == null) {
			throw new RuntimeException("wfstatuss is empty ");
		}
		this.wfstatuss = wfstatuss;
		return this;
	}

	public OvertimeQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public OvertimeQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public OvertimeQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public OvertimeQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public OvertimeQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public OvertimeQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public OvertimeQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public OvertimeQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public OvertimeQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public OvertimeQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public OvertimeQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public OvertimeQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public OvertimeQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public OvertimeQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public OvertimeQuery createByAndApply(String createByAndApply) {
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

			if ("overtimeid".equals(sortColumn)) {
				orderBy = "E.overtimeid" + a_x;
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

			if ("post".equals(sortColumn)) {
				orderBy = "E.post" + a_x;
			}

			if ("appdate".equals(sortColumn)) {
				orderBy = "E.appdate" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.type" + a_x;
			}

			if ("startdate".equals(sortColumn)) {
				orderBy = "E.startdate" + a_x;
			}

			if ("starttime".equals(sortColumn)) {
				orderBy = "E.starttime" + a_x;
			}

			if ("enddate".equals(sortColumn)) {
				orderBy = "E.enddate" + a_x;
			}

			if ("endtime".equals(sortColumn)) {
				orderBy = "E.endtime" + a_x;
			}

			if ("overtimesum".equals(sortColumn)) {
				orderBy = "E.overtimesum" + a_x;
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
		addColumn("id", "ID_");
		addColumn("overtimeid", "overtimeid");
		addColumn("area", "area");
		addColumn("company", "company");
		addColumn("dept", "dept");
		addColumn("appuser", "appuser");
		addColumn("post", "post");
		addColumn("appdate", "appdate");
		addColumn("type", "type");
		addColumn("startdate", "startdate");
		addColumn("starttime", "starttime");
		addColumn("enddate", "enddate");
		addColumn("endtime", "endtime");
		addColumn("overtimesum", "overtimesum");
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