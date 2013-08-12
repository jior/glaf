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
package com.glaf.oa.payment.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class PaymentQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> paymentids;
	protected String area;
	protected String areaLike;
	protected List<String> areas;
	protected String post;
	protected String postLike;
	protected List<String> posts;
	protected String company;
	protected String companyLike;
	protected List<String> companys;
	protected String dept;
	protected String deptLike;
	protected List<String> depts;
	protected String certificateno;
	protected String certificatenoLike;
	protected List<String> certificatenos;
	protected String receiptno;
	protected String receiptnoLike;
	protected List<String> receiptnos;
	protected String appuser;
	protected String appuserLike;
	protected List<String> appusers;
	protected Date appdate;
	protected Date appdateGreaterThanOrEqual;
	protected Date appdateLessThanOrEqual;
	protected List<Date> appdates;
	protected Date maturitydate;
	protected Date maturitydateGreaterThanOrEqual;
	protected Date maturitydateLessThanOrEqual;
	protected List<Date> maturitydates;
	protected Double appsum;
	protected Double appsumGreaterThanOrEqual;
	protected Double appsumLessThanOrEqual;
	protected List<Double> appsums;
	protected String currency;
	protected String currencyLike;
	protected List<String> currencys;
	protected String budgetno;
	protected String budgetnoLike;
	protected List<String> budgetnos;
	protected String use;
	protected String useLike;
	protected List<String> uses;
	protected String supname;
	protected String supnameLike;
	protected List<String> supnames;
	protected String supbank;
	protected String supbankLike;
	protected List<String> supbanks;
	protected String supaccount;
	protected String supaccountLike;
	protected List<String> supaccounts;
	protected String supaddress;
	protected String supaddressLike;
	protected List<String> supaddresss;
	protected String subject;
	protected String subjectLike;
	protected List<String> subjects;
	protected String checkno;
	protected String checknoLike;
	protected List<String> checknos;
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
	protected Double wfstatus;
	protected Double wfstatusGreaterThanOrEqual;
	protected Double wfstatusLessThanOrEqual;
	protected List<Double> wfstatuss;
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

	public PaymentQuery() {

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

	public String getCertificateno() {
		return certificateno;
	}

	public String getCertificatenoLike() {
		if (certificatenoLike != null && certificatenoLike.trim().length() > 0) {
			if (!certificatenoLike.startsWith("%")) {
				certificatenoLike = "%" + certificatenoLike;
			}
			if (!certificatenoLike.endsWith("%")) {
				certificatenoLike = certificatenoLike + "%";
			}
		}
		return certificatenoLike;
	}

	public List<String> getCertificatenos() {
		return certificatenos;
	}

	public String getReceiptno() {
		return receiptno;
	}

	public String getReceiptnoLike() {
		if (receiptnoLike != null && receiptnoLike.trim().length() > 0) {
			if (!receiptnoLike.startsWith("%")) {
				receiptnoLike = "%" + receiptnoLike;
			}
			if (!receiptnoLike.endsWith("%")) {
				receiptnoLike = receiptnoLike + "%";
			}
		}
		return receiptnoLike;
	}

	public List<String> getReceiptnos() {
		return receiptnos;
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

	public Date getMaturitydate() {
		return maturitydate;
	}

	public Date getMaturitydateGreaterThanOrEqual() {
		return maturitydateGreaterThanOrEqual;
	}

	public Date getMaturitydateLessThanOrEqual() {
		return maturitydateLessThanOrEqual;
	}

	public List<Date> getMaturitydates() {
		return maturitydates;
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

	public String getCurrency() {
		return currency;
	}

	public String getCurrencyLike() {
		if (currencyLike != null && currencyLike.trim().length() > 0) {
			if (!currencyLike.startsWith("%")) {
				currencyLike = "%" + currencyLike;
			}
			if (!currencyLike.endsWith("%")) {
				currencyLike = currencyLike + "%";
			}
		}
		return currencyLike;
	}

	public List<String> getCurrencys() {
		return currencys;
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

	public String getUse() {
		return use;
	}

	public String getUseLike() {
		if (useLike != null && useLike.trim().length() > 0) {
			if (!useLike.startsWith("%")) {
				useLike = "%" + useLike;
			}
			if (!useLike.endsWith("%")) {
				useLike = useLike + "%";
			}
		}
		return useLike;
	}

	public List<String> getUses() {
		return uses;
	}

	public String getSupname() {
		return supname;
	}

	public String getSupnameLike() {
		if (supnameLike != null && supnameLike.trim().length() > 0) {
			if (!supnameLike.startsWith("%")) {
				supnameLike = "%" + supnameLike;
			}
			if (!supnameLike.endsWith("%")) {
				supnameLike = supnameLike + "%";
			}
		}
		return supnameLike;
	}

	public List<String> getSupnames() {
		return supnames;
	}

	public String getSupbank() {
		return supbank;
	}

	public String getSupbankLike() {
		if (supbankLike != null && supbankLike.trim().length() > 0) {
			if (!supbankLike.startsWith("%")) {
				supbankLike = "%" + supbankLike;
			}
			if (!supbankLike.endsWith("%")) {
				supbankLike = supbankLike + "%";
			}
		}
		return supbankLike;
	}

	public List<String> getSupbanks() {
		return supbanks;
	}

	public String getSupaccount() {
		return supaccount;
	}

	public String getSupaccountLike() {
		if (supaccountLike != null && supaccountLike.trim().length() > 0) {
			if (!supaccountLike.startsWith("%")) {
				supaccountLike = "%" + supaccountLike;
			}
			if (!supaccountLike.endsWith("%")) {
				supaccountLike = supaccountLike + "%";
			}
		}
		return supaccountLike;
	}

	public List<String> getSupaccounts() {
		return supaccounts;
	}

	public String getSupaddress() {
		return supaddress;
	}

	public String getSupaddressLike() {
		if (supaddressLike != null && supaddressLike.trim().length() > 0) {
			if (!supaddressLike.startsWith("%")) {
				supaddressLike = "%" + supaddressLike;
			}
			if (!supaddressLike.endsWith("%")) {
				supaddressLike = supaddressLike + "%";
			}
		}
		return supaddressLike;
	}

	public List<String> getSupaddresss() {
		return supaddresss;
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

	public String getCheckno() {
		return checkno;
	}

	public String getChecknoLike() {
		if (checknoLike != null && checknoLike.trim().length() > 0) {
			if (!checknoLike.startsWith("%")) {
				checknoLike = "%" + checknoLike;
			}
			if (!checknoLike.endsWith("%")) {
				checknoLike = checknoLike + "%";
			}
		}
		return checknoLike;
	}

	public List<String> getChecknos() {
		return checknos;
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

	public void setPost(String post) {
		this.post = post;
	}

	public void setPostLike(String postLike) {
		this.postLike = postLike;
	}

	public void setPosts(List<String> posts) {
		this.posts = posts;
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

	public void setCertificateno(String certificateno) {
		this.certificateno = certificateno;
	}

	public void setCertificatenoLike(String certificatenoLike) {
		this.certificatenoLike = certificatenoLike;
	}

	public void setCertificatenos(List<String> certificatenos) {
		this.certificatenos = certificatenos;
	}

	public void setReceiptno(String receiptno) {
		this.receiptno = receiptno;
	}

	public void setReceiptnoLike(String receiptnoLike) {
		this.receiptnoLike = receiptnoLike;
	}

	public void setReceiptnos(List<String> receiptnos) {
		this.receiptnos = receiptnos;
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

	public void setMaturitydate(Date maturitydate) {
		this.maturitydate = maturitydate;
	}

	public void setMaturitydateGreaterThanOrEqual(
			Date maturitydateGreaterThanOrEqual) {
		this.maturitydateGreaterThanOrEqual = maturitydateGreaterThanOrEqual;
	}

	public void setMaturitydateLessThanOrEqual(Date maturitydateLessThanOrEqual) {
		this.maturitydateLessThanOrEqual = maturitydateLessThanOrEqual;
	}

	public void setMaturitydates(List<Date> maturitydates) {
		this.maturitydates = maturitydates;
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

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setCurrencyLike(String currencyLike) {
		this.currencyLike = currencyLike;
	}

	public void setCurrencys(List<String> currencys) {
		this.currencys = currencys;
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

	public void setUse(String use) {
		this.use = use;
	}

	public void setUseLike(String useLike) {
		this.useLike = useLike;
	}

	public void setUses(List<String> uses) {
		this.uses = uses;
	}

	public void setSupname(String supname) {
		this.supname = supname;
	}

	public void setSupnameLike(String supnameLike) {
		this.supnameLike = supnameLike;
	}

	public void setSupnames(List<String> supnames) {
		this.supnames = supnames;
	}

	public void setSupbank(String supbank) {
		this.supbank = supbank;
	}

	public void setSupbankLike(String supbankLike) {
		this.supbankLike = supbankLike;
	}

	public void setSupbanks(List<String> supbanks) {
		this.supbanks = supbanks;
	}

	public void setSupaccount(String supaccount) {
		this.supaccount = supaccount;
	}

	public void setSupaccountLike(String supaccountLike) {
		this.supaccountLike = supaccountLike;
	}

	public void setSupaccounts(List<String> supaccounts) {
		this.supaccounts = supaccounts;
	}

	public void setSupaddress(String supaddress) {
		this.supaddress = supaddress;
	}

	public void setSupaddressLike(String supaddressLike) {
		this.supaddressLike = supaddressLike;
	}

	public void setSupaddresss(List<String> supaddresss) {
		this.supaddresss = supaddresss;
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

	public void setCheckno(String checkno) {
		this.checkno = checkno;
	}

	public void setChecknoLike(String checknoLike) {
		this.checknoLike = checknoLike;
	}

	public void setChecknos(List<String> checknos) {
		this.checknos = checknos;
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

	public PaymentQuery area(String area) {
		if (area == null) {
			throw new RuntimeException("area is null");
		}
		this.area = area;
		return this;
	}

	public PaymentQuery areaLike(String areaLike) {
		if (areaLike == null) {
			throw new RuntimeException("area is null");
		}
		this.areaLike = areaLike;
		return this;
	}

	public PaymentQuery areas(List<String> areas) {
		if (areas == null) {
			throw new RuntimeException("areas is empty ");
		}
		this.areas = areas;
		return this;
	}

	public PaymentQuery post(String post) {
		if (post == null) {
			throw new RuntimeException("post is null");
		}
		this.post = post;
		return this;
	}

	public PaymentQuery postLike(String postLike) {
		if (postLike == null) {
			throw new RuntimeException("post is null");
		}
		this.postLike = postLike;
		return this;
	}

	public PaymentQuery posts(List<String> posts) {
		if (posts == null) {
			throw new RuntimeException("posts is empty ");
		}
		this.posts = posts;
		return this;
	}

	public PaymentQuery company(String company) {
		if (company == null) {
			throw new RuntimeException("company is null");
		}
		this.company = company;
		return this;
	}

	public PaymentQuery companyLike(String companyLike) {
		if (companyLike == null) {
			throw new RuntimeException("company is null");
		}
		this.companyLike = companyLike;
		return this;
	}

	public PaymentQuery companys(List<String> companys) {
		if (companys == null) {
			throw new RuntimeException("companys is empty ");
		}
		this.companys = companys;
		return this;
	}

	public PaymentQuery dept(String dept) {
		if (dept == null) {
			throw new RuntimeException("dept is null");
		}
		this.dept = dept;
		return this;
	}

	public PaymentQuery deptLike(String deptLike) {
		if (deptLike == null) {
			throw new RuntimeException("dept is null");
		}
		this.deptLike = deptLike;
		return this;
	}

	public PaymentQuery depts(List<String> depts) {
		if (depts == null) {
			throw new RuntimeException("depts is empty ");
		}
		this.depts = depts;
		return this;
	}

	public PaymentQuery certificateno(String certificateno) {
		if (certificateno == null) {
			throw new RuntimeException("certificateno is null");
		}
		this.certificateno = certificateno;
		return this;
	}

	public PaymentQuery certificatenoLike(String certificatenoLike) {
		if (certificatenoLike == null) {
			throw new RuntimeException("certificateno is null");
		}
		this.certificatenoLike = certificatenoLike;
		return this;
	}

	public PaymentQuery certificatenos(List<String> certificatenos) {
		if (certificatenos == null) {
			throw new RuntimeException("certificatenos is empty ");
		}
		this.certificatenos = certificatenos;
		return this;
	}

	public PaymentQuery receiptno(String receiptno) {
		if (receiptno == null) {
			throw new RuntimeException("receiptno is null");
		}
		this.receiptno = receiptno;
		return this;
	}

	public PaymentQuery receiptnoLike(String receiptnoLike) {
		if (receiptnoLike == null) {
			throw new RuntimeException("receiptno is null");
		}
		this.receiptnoLike = receiptnoLike;
		return this;
	}

	public PaymentQuery receiptnos(List<String> receiptnos) {
		if (receiptnos == null) {
			throw new RuntimeException("receiptnos is empty ");
		}
		this.receiptnos = receiptnos;
		return this;
	}

	public PaymentQuery appuser(String appuser) {
		if (appuser == null) {
			throw new RuntimeException("appuser is null");
		}
		this.appuser = appuser;
		return this;
	}

	public PaymentQuery appuserLike(String appuserLike) {
		if (appuserLike == null) {
			throw new RuntimeException("appuser is null");
		}
		this.appuserLike = appuserLike;
		return this;
	}

	public PaymentQuery appusers(List<String> appusers) {
		if (appusers == null) {
			throw new RuntimeException("appusers is empty ");
		}
		this.appusers = appusers;
		return this;
	}

	public PaymentQuery appdate(Date appdate) {
		if (appdate == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdate = appdate;
		return this;
	}

	public PaymentQuery appdateGreaterThanOrEqual(Date appdateGreaterThanOrEqual) {
		if (appdateGreaterThanOrEqual == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdateGreaterThanOrEqual = appdateGreaterThanOrEqual;
		return this;
	}

	public PaymentQuery appdateLessThanOrEqual(Date appdateLessThanOrEqual) {
		if (appdateLessThanOrEqual == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdateLessThanOrEqual = appdateLessThanOrEqual;
		return this;
	}

	public PaymentQuery appdates(List<Date> appdates) {
		if (appdates == null) {
			throw new RuntimeException("appdates is empty ");
		}
		this.appdates = appdates;
		return this;
	}

	public PaymentQuery maturitydate(Date maturitydate) {
		if (maturitydate == null) {
			throw new RuntimeException("maturitydate is null");
		}
		this.maturitydate = maturitydate;
		return this;
	}

	public PaymentQuery maturitydateGreaterThanOrEqual(
			Date maturitydateGreaterThanOrEqual) {
		if (maturitydateGreaterThanOrEqual == null) {
			throw new RuntimeException("maturitydate is null");
		}
		this.maturitydateGreaterThanOrEqual = maturitydateGreaterThanOrEqual;
		return this;
	}

	public PaymentQuery maturitydateLessThanOrEqual(
			Date maturitydateLessThanOrEqual) {
		if (maturitydateLessThanOrEqual == null) {
			throw new RuntimeException("maturitydate is null");
		}
		this.maturitydateLessThanOrEqual = maturitydateLessThanOrEqual;
		return this;
	}

	public PaymentQuery maturitydates(List<Date> maturitydates) {
		if (maturitydates == null) {
			throw new RuntimeException("maturitydates is empty ");
		}
		this.maturitydates = maturitydates;
		return this;
	}

	public PaymentQuery appsum(Double appsum) {
		if (appsum == null) {
			throw new RuntimeException("appsum is null");
		}
		this.appsum = appsum;
		return this;
	}

	public PaymentQuery appsumGreaterThanOrEqual(Double appsumGreaterThanOrEqual) {
		if (appsumGreaterThanOrEqual == null) {
			throw new RuntimeException("appsum is null");
		}
		this.appsumGreaterThanOrEqual = appsumGreaterThanOrEqual;
		return this;
	}

	public PaymentQuery appsumLessThanOrEqual(Double appsumLessThanOrEqual) {
		if (appsumLessThanOrEqual == null) {
			throw new RuntimeException("appsum is null");
		}
		this.appsumLessThanOrEqual = appsumLessThanOrEqual;
		return this;
	}

	public PaymentQuery appsums(List<Double> appsums) {
		if (appsums == null) {
			throw new RuntimeException("appsums is empty ");
		}
		this.appsums = appsums;
		return this;
	}

	public PaymentQuery currency(String currency) {
		if (currency == null) {
			throw new RuntimeException("currency is null");
		}
		this.currency = currency;
		return this;
	}

	public PaymentQuery currencyLike(String currencyLike) {
		if (currencyLike == null) {
			throw new RuntimeException("currency is null");
		}
		this.currencyLike = currencyLike;
		return this;
	}

	public PaymentQuery currencys(List<String> currencys) {
		if (currencys == null) {
			throw new RuntimeException("currencys is empty ");
		}
		this.currencys = currencys;
		return this;
	}

	public PaymentQuery budgetno(String budgetno) {
		if (budgetno == null) {
			throw new RuntimeException("budgetno is null");
		}
		this.budgetno = budgetno;
		return this;
	}

	public PaymentQuery budgetnoLike(String budgetnoLike) {
		if (budgetnoLike == null) {
			throw new RuntimeException("budgetno is null");
		}
		this.budgetnoLike = budgetnoLike;
		return this;
	}

	public PaymentQuery budgetnos(List<String> budgetnos) {
		if (budgetnos == null) {
			throw new RuntimeException("budgetnos is empty ");
		}
		this.budgetnos = budgetnos;
		return this;
	}

	public PaymentQuery use(String use) {
		if (use == null) {
			throw new RuntimeException("use is null");
		}
		this.use = use;
		return this;
	}

	public PaymentQuery useLike(String useLike) {
		if (useLike == null) {
			throw new RuntimeException("use is null");
		}
		this.useLike = useLike;
		return this;
	}

	public PaymentQuery uses(List<String> uses) {
		if (uses == null) {
			throw new RuntimeException("uses is empty ");
		}
		this.uses = uses;
		return this;
	}

	public PaymentQuery supname(String supname) {
		if (supname == null) {
			throw new RuntimeException("supname is null");
		}
		this.supname = supname;
		return this;
	}

	public PaymentQuery supnameLike(String supnameLike) {
		if (supnameLike == null) {
			throw new RuntimeException("supname is null");
		}
		this.supnameLike = supnameLike;
		return this;
	}

	public PaymentQuery supnames(List<String> supnames) {
		if (supnames == null) {
			throw new RuntimeException("supnames is empty ");
		}
		this.supnames = supnames;
		return this;
	}

	public PaymentQuery supbank(String supbank) {
		if (supbank == null) {
			throw new RuntimeException("supbank is null");
		}
		this.supbank = supbank;
		return this;
	}

	public PaymentQuery supbankLike(String supbankLike) {
		if (supbankLike == null) {
			throw new RuntimeException("supbank is null");
		}
		this.supbankLike = supbankLike;
		return this;
	}

	public PaymentQuery supbanks(List<String> supbanks) {
		if (supbanks == null) {
			throw new RuntimeException("supbanks is empty ");
		}
		this.supbanks = supbanks;
		return this;
	}

	public PaymentQuery supaccount(String supaccount) {
		if (supaccount == null) {
			throw new RuntimeException("supaccount is null");
		}
		this.supaccount = supaccount;
		return this;
	}

	public PaymentQuery supaccountLike(String supaccountLike) {
		if (supaccountLike == null) {
			throw new RuntimeException("supaccount is null");
		}
		this.supaccountLike = supaccountLike;
		return this;
	}

	public PaymentQuery supaccounts(List<String> supaccounts) {
		if (supaccounts == null) {
			throw new RuntimeException("supaccounts is empty ");
		}
		this.supaccounts = supaccounts;
		return this;
	}

	public PaymentQuery supaddress(String supaddress) {
		if (supaddress == null) {
			throw new RuntimeException("supaddress is null");
		}
		this.supaddress = supaddress;
		return this;
	}

	public PaymentQuery supaddressLike(String supaddressLike) {
		if (supaddressLike == null) {
			throw new RuntimeException("supaddress is null");
		}
		this.supaddressLike = supaddressLike;
		return this;
	}

	public PaymentQuery supaddresss(List<String> supaddresss) {
		if (supaddresss == null) {
			throw new RuntimeException("supaddresss is empty ");
		}
		this.supaddresss = supaddresss;
		return this;
	}

	public PaymentQuery subject(String subject) {
		if (subject == null) {
			throw new RuntimeException("subject is null");
		}
		this.subject = subject;
		return this;
	}

	public PaymentQuery subjectLike(String subjectLike) {
		if (subjectLike == null) {
			throw new RuntimeException("subject is null");
		}
		this.subjectLike = subjectLike;
		return this;
	}

	public PaymentQuery subjects(List<String> subjects) {
		if (subjects == null) {
			throw new RuntimeException("subjects is empty ");
		}
		this.subjects = subjects;
		return this;
	}

	public PaymentQuery checkno(String checkno) {
		if (checkno == null) {
			throw new RuntimeException("checkno is null");
		}
		this.checkno = checkno;
		return this;
	}

	public PaymentQuery checknoLike(String checknoLike) {
		if (checknoLike == null) {
			throw new RuntimeException("checkno is null");
		}
		this.checknoLike = checknoLike;
		return this;
	}

	public PaymentQuery checknos(List<String> checknos) {
		if (checknos == null) {
			throw new RuntimeException("checknos is empty ");
		}
		this.checknos = checknos;
		return this;
	}

	public PaymentQuery remark(String remark) {
		if (remark == null) {
			throw new RuntimeException("remark is null");
		}
		this.remark = remark;
		return this;
	}

	public PaymentQuery remarkLike(String remarkLike) {
		if (remarkLike == null) {
			throw new RuntimeException("remark is null");
		}
		this.remarkLike = remarkLike;
		return this;
	}

	public PaymentQuery remarks(List<String> remarks) {
		if (remarks == null) {
			throw new RuntimeException("remarks is empty ");
		}
		this.remarks = remarks;
		return this;
	}

	public PaymentQuery status(Integer status) {
		if (status == null) {
			throw new RuntimeException("status is null");
		}
		this.status = status;
		return this;
	}

	public PaymentQuery statusGreaterThanOrEqual(
			Integer statusGreaterThanOrEqual) {
		if (statusGreaterThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
		return this;
	}

	public PaymentQuery statusLessThanOrEqual(Integer statusLessThanOrEqual) {
		if (statusLessThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusLessThanOrEqual = statusLessThanOrEqual;
		return this;
	}

	public PaymentQuery statuss(List<Integer> statuss) {
		if (statuss == null) {
			throw new RuntimeException("statuss is empty ");
		}
		this.statuss = statuss;
		return this;
	}

	public PaymentQuery processname(String processname) {
		if (processname == null) {
			throw new RuntimeException("processname is null");
		}
		this.processname = processname;
		return this;
	}

	public PaymentQuery processnameLike(String processnameLike) {
		if (processnameLike == null) {
			throw new RuntimeException("processname is null");
		}
		this.processnameLike = processnameLike;
		return this;
	}

	public PaymentQuery processnames(List<String> processnames) {
		if (processnames == null) {
			throw new RuntimeException("processnames is empty ");
		}
		this.processnames = processnames;
		return this;
	}

	public PaymentQuery processinstanceid(Long processinstanceid) {
		if (processinstanceid == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceid = processinstanceid;
		return this;
	}

	public PaymentQuery processinstanceidGreaterThanOrEqual(
			Long processinstanceidGreaterThanOrEqual) {
		if (processinstanceidGreaterThanOrEqual == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceidGreaterThanOrEqual = processinstanceidGreaterThanOrEqual;
		return this;
	}

	public PaymentQuery processinstanceidLessThanOrEqual(
			Long processinstanceidLessThanOrEqual) {
		if (processinstanceidLessThanOrEqual == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceidLessThanOrEqual = processinstanceidLessThanOrEqual;
		return this;
	}

	public PaymentQuery processinstanceids(List<Long> processinstanceids) {
		if (processinstanceids == null) {
			throw new RuntimeException("processinstanceids is empty ");
		}
		this.processinstanceids = processinstanceids;
		return this;
	}

	public PaymentQuery wfstatus(Double wfstatus) {
		if (wfstatus == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatus = wfstatus;
		return this;
	}

	public PaymentQuery wfstatusGreaterThanOrEqual(
			Double wfstatusGreaterThanOrEqual) {
		if (wfstatusGreaterThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusGreaterThanOrEqual = wfstatusGreaterThanOrEqual;
		return this;
	}

	public PaymentQuery wfstatusLessThanOrEqual(Double wfstatusLessThanOrEqual) {
		if (wfstatusLessThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusLessThanOrEqual = wfstatusLessThanOrEqual;
		return this;
	}

	public PaymentQuery wfstatuss(List<Double> wfstatuss) {
		if (wfstatuss == null) {
			throw new RuntimeException("wfstatuss is empty ");
		}
		this.wfstatuss = wfstatuss;
		return this;
	}

	public PaymentQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public PaymentQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public PaymentQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public PaymentQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public PaymentQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public PaymentQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public PaymentQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public PaymentQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public PaymentQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public PaymentQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public PaymentQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public PaymentQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public PaymentQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public PaymentQuery updateBys(List<String> updateBys) {
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

			if ("post".equals(sortColumn)) {
				orderBy = "E.post" + a_x;
			}

			if ("company".equals(sortColumn)) {
				orderBy = "E.company" + a_x;
			}

			if ("dept".equals(sortColumn)) {
				orderBy = "E.dept" + a_x;
			}

			if ("certificateno".equals(sortColumn)) {
				orderBy = "E.certificateno" + a_x;
			}

			if ("receiptno".equals(sortColumn)) {
				orderBy = "E.receiptno" + a_x;
			}

			if ("appuser".equals(sortColumn)) {
				orderBy = "E.appuser" + a_x;
			}

			if ("appdate".equals(sortColumn)) {
				orderBy = "E.appdate" + a_x;
			}

			if ("maturitydate".equals(sortColumn)) {
				orderBy = "E.maturitydate" + a_x;
			}

			if ("appsum".equals(sortColumn)) {
				orderBy = "E.appsum" + a_x;
			}

			if ("currency".equals(sortColumn)) {
				orderBy = "E.currency" + a_x;
			}

			if ("budgetno".equals(sortColumn)) {
				orderBy = "E.budgetno" + a_x;
			}

			if ("use".equals(sortColumn)) {
				orderBy = "E.use" + a_x;
			}

			if ("supname".equals(sortColumn)) {
				orderBy = "E.supname" + a_x;
			}

			if ("supbank".equals(sortColumn)) {
				orderBy = "E.supbank" + a_x;
			}

			if ("supaccount".equals(sortColumn)) {
				orderBy = "E.supaccount" + a_x;
			}

			if ("supaddress".equals(sortColumn)) {
				orderBy = "E.supaddress" + a_x;
			}

			if ("subject".equals(sortColumn)) {
				orderBy = "E.subject" + a_x;
			}

			if ("checkno".equals(sortColumn)) {
				orderBy = "E.checkno" + a_x;
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
		addColumn("paymentid", "paymentid");
		addColumn("area", "area");
		addColumn("post", "post");
		addColumn("company", "company");
		addColumn("dept", "dept");
		addColumn("certificateno", "certificateno");
		addColumn("receiptno", "receiptno");
		addColumn("appuser", "appuser");
		addColumn("appdate", "appdate");
		addColumn("maturitydate", "maturitydate");
		addColumn("appsum", "appsum");
		addColumn("currency", "currency");
		addColumn("budgetno", "budgetno");
		addColumn("use", "use");
		addColumn("supname", "supname");
		addColumn("supbank", "supbank");
		addColumn("supaccount", "supaccount");
		addColumn("supaddress", "supaddress");
		addColumn("subject", "subject");
		addColumn("checkno", "checkno");
		addColumn("remark", "remark");
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