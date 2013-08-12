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
package com.glaf.oa.budget.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class BudgetQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> budgetids;
	protected String budgetno;
	protected String budgetnoLike;
	protected List<String> budgetnos;
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
	protected String proname;
	protected String pronameLike;
	protected List<String> pronames;
	protected Double budgetsum;
	protected Double budgetsumGreaterThanOrEqual;
	protected Double budgetsumLessThanOrEqual;
	protected List<Double> budgetsums;
	protected String currency;
	protected String currencyLike;
	protected List<String> currencys;
	protected Integer paymentmodel;
	protected Integer paymentmodelGreaterThanOrEqual;
	protected Integer paymentmodelLessThanOrEqual;
	protected List<Integer> paymentmodels;
	protected Integer paymenttype;
	protected Integer paymenttypeGreaterThanOrEqual;
	protected Integer paymenttypeLessThanOrEqual;
	protected List<Integer> paymenttypes;
	protected String supname;
	protected String supnameLike;
	protected List<String> supnames;
	protected String supaccount;
	protected String supaccountLike;
	protected List<String> supaccounts;
	protected String supbank;
	protected String supbankLike;
	protected List<String> supbanks;
	protected String supaddress;
	protected String supaddressLike;
	protected List<String> supaddresss;
	protected String attachment;
	protected String attachmentLike;
	protected List<String> attachments;
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
	protected Date paymentdate;
	protected Date paymentdateGreaterThanOrEqual;
	protected Date paymentdateLessThanOrEqual;
	protected List<Date> paymentdates;
	protected String remark;
	protected String remarkLike;
	protected List<String> remarks;
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

	public BudgetQuery() {

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

	public String getProname() {
		return proname;
	}

	public String getPronameLike() {
		if (pronameLike != null && pronameLike.trim().length() > 0) {
			if (!pronameLike.startsWith("%")) {
				pronameLike = "%" + pronameLike;
			}
			if (!pronameLike.endsWith("%")) {
				pronameLike = pronameLike + "%";
			}
		}
		return pronameLike;
	}

	public List<String> getPronames() {
		return pronames;
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

	public Integer getPaymentmodel() {
		return paymentmodel;
	}

	public Integer getPaymentmodelGreaterThanOrEqual() {
		return paymentmodelGreaterThanOrEqual;
	}

	public Integer getPaymentmodelLessThanOrEqual() {
		return paymentmodelLessThanOrEqual;
	}

	public List<Integer> getPaymentmodels() {
		return paymentmodels;
	}

	public Integer getPaymenttype() {
		return paymenttype;
	}

	public Integer getPaymenttypeGreaterThanOrEqual() {
		return paymenttypeGreaterThanOrEqual;
	}

	public Integer getPaymenttypeLessThanOrEqual() {
		return paymenttypeLessThanOrEqual;
	}

	public List<Integer> getPaymenttypes() {
		return paymenttypes;
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

	public String getAttachment() {
		return attachment;
	}

	public String getAttachmentLike() {
		if (attachmentLike != null && attachmentLike.trim().length() > 0) {
			if (!attachmentLike.startsWith("%")) {
				attachmentLike = "%" + attachmentLike;
			}
			if (!attachmentLike.endsWith("%")) {
				attachmentLike = attachmentLike + "%";
			}
		}
		return attachmentLike;
	}

	public List<String> getAttachments() {
		return attachments;
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

	public Date getPaymentdate() {
		return paymentdate;
	}

	public Date getPaymentdateGreaterThanOrEqual() {
		return paymentdateGreaterThanOrEqual;
	}

	public Date getPaymentdateLessThanOrEqual() {
		return paymentdateLessThanOrEqual;
	}

	public List<Date> getPaymentdates() {
		return paymentdates;
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

	public void setBudgetno(String budgetno) {
		this.budgetno = budgetno;
	}

	public void setBudgetnoLike(String budgetnoLike) {
		this.budgetnoLike = budgetnoLike;
	}

	public void setBudgetnos(List<String> budgetnos) {
		this.budgetnos = budgetnos;
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

	public void setProname(String proname) {
		this.proname = proname;
	}

	public void setPronameLike(String pronameLike) {
		this.pronameLike = pronameLike;
	}

	public void setPronames(List<String> pronames) {
		this.pronames = pronames;
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

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setCurrencyLike(String currencyLike) {
		this.currencyLike = currencyLike;
	}

	public void setCurrencys(List<String> currencys) {
		this.currencys = currencys;
	}

	public void setPaymentmodel(Integer paymentmodel) {
		this.paymentmodel = paymentmodel;
	}

	public void setPaymentmodelGreaterThanOrEqual(
			Integer paymentmodelGreaterThanOrEqual) {
		this.paymentmodelGreaterThanOrEqual = paymentmodelGreaterThanOrEqual;
	}

	public void setPaymentmodelLessThanOrEqual(
			Integer paymentmodelLessThanOrEqual) {
		this.paymentmodelLessThanOrEqual = paymentmodelLessThanOrEqual;
	}

	public void setPaymentmodels(List<Integer> paymentmodels) {
		this.paymentmodels = paymentmodels;
	}

	public void setPaymenttype(Integer paymenttype) {
		this.paymenttype = paymenttype;
	}

	public void setPaymenttypeGreaterThanOrEqual(
			Integer paymenttypeGreaterThanOrEqual) {
		this.paymenttypeGreaterThanOrEqual = paymenttypeGreaterThanOrEqual;
	}

	public void setPaymenttypeLessThanOrEqual(Integer paymenttypeLessThanOrEqual) {
		this.paymenttypeLessThanOrEqual = paymenttypeLessThanOrEqual;
	}

	public void setPaymenttypes(List<Integer> paymenttypes) {
		this.paymenttypes = paymenttypes;
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

	public void setSupaccount(String supaccount) {
		this.supaccount = supaccount;
	}

	public void setSupaccountLike(String supaccountLike) {
		this.supaccountLike = supaccountLike;
	}

	public void setSupaccounts(List<String> supaccounts) {
		this.supaccounts = supaccounts;
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

	public void setSupaddress(String supaddress) {
		this.supaddress = supaddress;
	}

	public void setSupaddressLike(String supaddressLike) {
		this.supaddressLike = supaddressLike;
	}

	public void setSupaddresss(List<String> supaddresss) {
		this.supaddresss = supaddresss;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public void setAttachmentLike(String attachmentLike) {
		this.attachmentLike = attachmentLike;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
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

	public void setPaymentdate(Date paymentdate) {
		this.paymentdate = paymentdate;
	}

	public void setPaymentdateGreaterThanOrEqual(
			Date paymentdateGreaterThanOrEqual) {
		this.paymentdateGreaterThanOrEqual = paymentdateGreaterThanOrEqual;
	}

	public void setPaymentdateLessThanOrEqual(Date paymentdateLessThanOrEqual) {
		this.paymentdateLessThanOrEqual = paymentdateLessThanOrEqual;
	}

	public void setPaymentdates(List<Date> paymentdates) {
		this.paymentdates = paymentdates;
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

	public BudgetQuery budgetno(String budgetno) {
		if (budgetno == null) {
			throw new RuntimeException("budgetno is null");
		}
		this.budgetno = budgetno;
		return this;
	}

	public BudgetQuery budgetnoLike(String budgetnoLike) {
		if (budgetnoLike == null) {
			throw new RuntimeException("budgetno is null");
		}
		this.budgetnoLike = budgetnoLike;
		return this;
	}

	public BudgetQuery budgetnos(List<String> budgetnos) {
		if (budgetnos == null) {
			throw new RuntimeException("budgetnos is empty ");
		}
		this.budgetnos = budgetnos;
		return this;
	}

	public BudgetQuery area(String area) {
		if (area == null) {
			throw new RuntimeException("area is null");
		}
		this.area = area;
		return this;
	}

	public BudgetQuery areaLike(String areaLike) {
		if (areaLike == null) {
			throw new RuntimeException("area is null");
		}
		this.areaLike = areaLike;
		return this;
	}

	public BudgetQuery areas(List<String> areas) {
		if (areas == null) {
			throw new RuntimeException("areas is empty ");
		}
		this.areas = areas;
		return this;
	}

	public BudgetQuery company(String company) {
		if (company == null) {
			throw new RuntimeException("company is null");
		}
		this.company = company;
		return this;
	}

	public BudgetQuery companyLike(String companyLike) {
		if (companyLike == null) {
			throw new RuntimeException("company is null");
		}
		this.companyLike = companyLike;
		return this;
	}

	public BudgetQuery companys(List<String> companys) {
		if (companys == null) {
			throw new RuntimeException("companys is empty ");
		}
		this.companys = companys;
		return this;
	}

	public BudgetQuery dept(String dept) {
		if (dept == null) {
			throw new RuntimeException("dept is null");
		}
		this.dept = dept;
		return this;
	}

	public BudgetQuery deptLike(String deptLike) {
		if (deptLike == null) {
			throw new RuntimeException("dept is null");
		}
		this.deptLike = deptLike;
		return this;
	}

	public BudgetQuery depts(List<String> depts) {
		if (depts == null) {
			throw new RuntimeException("depts is empty ");
		}
		this.depts = depts;
		return this;
	}

	public BudgetQuery post(String post) {
		if (post == null) {
			throw new RuntimeException("post is null");
		}
		this.post = post;
		return this;
	}

	public BudgetQuery postLike(String postLike) {
		if (postLike == null) {
			throw new RuntimeException("post is null");
		}
		this.postLike = postLike;
		return this;
	}

	public BudgetQuery posts(List<String> posts) {
		if (posts == null) {
			throw new RuntimeException("posts is empty ");
		}
		this.posts = posts;
		return this;
	}

	public BudgetQuery appuser(String appuser) {
		if (appuser == null) {
			throw new RuntimeException("appuser is null");
		}
		this.appuser = appuser;
		return this;
	}

	public BudgetQuery appuserLike(String appuserLike) {
		if (appuserLike == null) {
			throw new RuntimeException("appuser is null");
		}
		this.appuserLike = appuserLike;
		return this;
	}

	public BudgetQuery appusers(List<String> appusers) {
		if (appusers == null) {
			throw new RuntimeException("appusers is empty ");
		}
		this.appusers = appusers;
		return this;
	}

	public BudgetQuery appdate(Date appdate) {
		if (appdate == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdate = appdate;
		return this;
	}

	public BudgetQuery appdateGreaterThanOrEqual(Date appdateGreaterThanOrEqual) {
		if (appdateGreaterThanOrEqual == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdateGreaterThanOrEqual = appdateGreaterThanOrEqual;
		return this;
	}

	public BudgetQuery appdateLessThanOrEqual(Date appdateLessThanOrEqual) {
		if (appdateLessThanOrEqual == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdateLessThanOrEqual = appdateLessThanOrEqual;
		return this;
	}

	public BudgetQuery appdates(List<Date> appdates) {
		if (appdates == null) {
			throw new RuntimeException("appdates is empty ");
		}
		this.appdates = appdates;
		return this;
	}

	public BudgetQuery proname(String proname) {
		if (proname == null) {
			throw new RuntimeException("proname is null");
		}
		this.proname = proname;
		return this;
	}

	public BudgetQuery pronameLike(String pronameLike) {
		if (pronameLike == null) {
			throw new RuntimeException("proname is null");
		}
		this.pronameLike = pronameLike;
		return this;
	}

	public BudgetQuery pronames(List<String> pronames) {
		if (pronames == null) {
			throw new RuntimeException("pronames is empty ");
		}
		this.pronames = pronames;
		return this;
	}

	public BudgetQuery budgetsum(Double budgetsum) {
		if (budgetsum == null) {
			throw new RuntimeException("budgetsum is null");
		}
		this.budgetsum = budgetsum;
		return this;
	}

	public BudgetQuery budgetsumGreaterThanOrEqual(
			Double budgetsumGreaterThanOrEqual) {
		if (budgetsumGreaterThanOrEqual == null) {
			throw new RuntimeException("budgetsum is null");
		}
		this.budgetsumGreaterThanOrEqual = budgetsumGreaterThanOrEqual;
		return this;
	}

	public BudgetQuery budgetsumLessThanOrEqual(Double budgetsumLessThanOrEqual) {
		if (budgetsumLessThanOrEqual == null) {
			throw new RuntimeException("budgetsum is null");
		}
		this.budgetsumLessThanOrEqual = budgetsumLessThanOrEqual;
		return this;
	}

	public BudgetQuery budgetsums(List<Double> budgetsums) {
		if (budgetsums == null) {
			throw new RuntimeException("budgetsums is empty ");
		}
		this.budgetsums = budgetsums;
		return this;
	}

	public BudgetQuery currency(String currency) {
		if (currency == null) {
			throw new RuntimeException("currency is null");
		}
		this.currency = currency;
		return this;
	}

	public BudgetQuery currencyLike(String currencyLike) {
		if (currencyLike == null) {
			throw new RuntimeException("currency is null");
		}
		this.currencyLike = currencyLike;
		return this;
	}

	public BudgetQuery currencys(List<String> currencys) {
		if (currencys == null) {
			throw new RuntimeException("currencys is empty ");
		}
		this.currencys = currencys;
		return this;
	}

	public BudgetQuery paymentmodel(Integer paymentmodel) {
		if (paymentmodel == null) {
			throw new RuntimeException("paymentmodel is null");
		}
		this.paymentmodel = paymentmodel;
		return this;
	}

	public BudgetQuery paymentmodelGreaterThanOrEqual(
			Integer paymentmodelGreaterThanOrEqual) {
		if (paymentmodelGreaterThanOrEqual == null) {
			throw new RuntimeException("paymentmodel is null");
		}
		this.paymentmodelGreaterThanOrEqual = paymentmodelGreaterThanOrEqual;
		return this;
	}

	public BudgetQuery paymentmodelLessThanOrEqual(
			Integer paymentmodelLessThanOrEqual) {
		if (paymentmodelLessThanOrEqual == null) {
			throw new RuntimeException("paymentmodel is null");
		}
		this.paymentmodelLessThanOrEqual = paymentmodelLessThanOrEqual;
		return this;
	}

	public BudgetQuery paymentmodels(List<Integer> paymentmodels) {
		if (paymentmodels == null) {
			throw new RuntimeException("paymentmodels is empty ");
		}
		this.paymentmodels = paymentmodels;
		return this;
	}

	public BudgetQuery paymenttype(Integer paymenttype) {
		if (paymenttype == null) {
			throw new RuntimeException("paymenttype is null");
		}
		this.paymenttype = paymenttype;
		return this;
	}

	public BudgetQuery paymenttypeGreaterThanOrEqual(
			Integer paymenttypeGreaterThanOrEqual) {
		if (paymenttypeGreaterThanOrEqual == null) {
			throw new RuntimeException("paymenttype is null");
		}
		this.paymenttypeGreaterThanOrEqual = paymenttypeGreaterThanOrEqual;
		return this;
	}

	public BudgetQuery paymenttypeLessThanOrEqual(
			Integer paymenttypeLessThanOrEqual) {
		if (paymenttypeLessThanOrEqual == null) {
			throw new RuntimeException("paymenttype is null");
		}
		this.paymenttypeLessThanOrEqual = paymenttypeLessThanOrEqual;
		return this;
	}

	public BudgetQuery paymenttypes(List<Integer> paymenttypes) {
		if (paymenttypes == null) {
			throw new RuntimeException("paymenttypes is empty ");
		}
		this.paymenttypes = paymenttypes;
		return this;
	}

	public BudgetQuery supname(String supname) {
		if (supname == null) {
			throw new RuntimeException("supname is null");
		}
		this.supname = supname;
		return this;
	}

	public BudgetQuery supnameLike(String supnameLike) {
		if (supnameLike == null) {
			throw new RuntimeException("supname is null");
		}
		this.supnameLike = supnameLike;
		return this;
	}

	public BudgetQuery supnames(List<String> supnames) {
		if (supnames == null) {
			throw new RuntimeException("supnames is empty ");
		}
		this.supnames = supnames;
		return this;
	}

	public BudgetQuery supaccount(String supaccount) {
		if (supaccount == null) {
			throw new RuntimeException("supaccount is null");
		}
		this.supaccount = supaccount;
		return this;
	}

	public BudgetQuery supaccountLike(String supaccountLike) {
		if (supaccountLike == null) {
			throw new RuntimeException("supaccount is null");
		}
		this.supaccountLike = supaccountLike;
		return this;
	}

	public BudgetQuery supaccounts(List<String> supaccounts) {
		if (supaccounts == null) {
			throw new RuntimeException("supaccounts is empty ");
		}
		this.supaccounts = supaccounts;
		return this;
	}

	public BudgetQuery supbank(String supbank) {
		if (supbank == null) {
			throw new RuntimeException("supbank is null");
		}
		this.supbank = supbank;
		return this;
	}

	public BudgetQuery supbankLike(String supbankLike) {
		if (supbankLike == null) {
			throw new RuntimeException("supbank is null");
		}
		this.supbankLike = supbankLike;
		return this;
	}

	public BudgetQuery supbanks(List<String> supbanks) {
		if (supbanks == null) {
			throw new RuntimeException("supbanks is empty ");
		}
		this.supbanks = supbanks;
		return this;
	}

	public BudgetQuery supaddress(String supaddress) {
		if (supaddress == null) {
			throw new RuntimeException("supaddress is null");
		}
		this.supaddress = supaddress;
		return this;
	}

	public BudgetQuery supaddressLike(String supaddressLike) {
		if (supaddressLike == null) {
			throw new RuntimeException("supaddress is null");
		}
		this.supaddressLike = supaddressLike;
		return this;
	}

	public BudgetQuery supaddresss(List<String> supaddresss) {
		if (supaddresss == null) {
			throw new RuntimeException("supaddresss is empty ");
		}
		this.supaddresss = supaddresss;
		return this;
	}

	public BudgetQuery attachment(String attachment) {
		if (attachment == null) {
			throw new RuntimeException("attachment is null");
		}
		this.attachment = attachment;
		return this;
	}

	public BudgetQuery attachmentLike(String attachmentLike) {
		if (attachmentLike == null) {
			throw new RuntimeException("attachment is null");
		}
		this.attachmentLike = attachmentLike;
		return this;
	}

	public BudgetQuery attachments(List<String> attachments) {
		if (attachments == null) {
			throw new RuntimeException("attachments is empty ");
		}
		this.attachments = attachments;
		return this;
	}

	public BudgetQuery status(Integer status) {
		if (status == null) {
			throw new RuntimeException("status is null");
		}
		this.status = status;
		return this;
	}

	public BudgetQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual) {
		if (statusGreaterThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
		return this;
	}

	public BudgetQuery statusLessThanOrEqual(Integer statusLessThanOrEqual) {
		if (statusLessThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusLessThanOrEqual = statusLessThanOrEqual;
		return this;
	}

	public BudgetQuery statuss(List<Integer> statuss) {
		if (statuss == null) {
			throw new RuntimeException("statuss is empty ");
		}
		this.statuss = statuss;
		return this;
	}

	public BudgetQuery processname(String processname) {
		if (processname == null) {
			throw new RuntimeException("processname is null");
		}
		this.processname = processname;
		return this;
	}

	public BudgetQuery processnameLike(String processnameLike) {
		if (processnameLike == null) {
			throw new RuntimeException("processname is null");
		}
		this.processnameLike = processnameLike;
		return this;
	}

	public BudgetQuery processnames(List<String> processnames) {
		if (processnames == null) {
			throw new RuntimeException("processnames is empty ");
		}
		this.processnames = processnames;
		return this;
	}

	public BudgetQuery processinstanceid(Double processinstanceid) {
		if (processinstanceid == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceid = processinstanceid;
		return this;
	}

	public BudgetQuery processinstanceidGreaterThanOrEqual(
			Double processinstanceidGreaterThanOrEqual) {
		if (processinstanceidGreaterThanOrEqual == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceidGreaterThanOrEqual = processinstanceidGreaterThanOrEqual;
		return this;
	}

	public BudgetQuery processinstanceidLessThanOrEqual(
			Double processinstanceidLessThanOrEqual) {
		if (processinstanceidLessThanOrEqual == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceidLessThanOrEqual = processinstanceidLessThanOrEqual;
		return this;
	}

	public BudgetQuery processinstanceids(List<Double> processinstanceids) {
		if (processinstanceids == null) {
			throw new RuntimeException("processinstanceids is empty ");
		}
		this.processinstanceids = processinstanceids;
		return this;
	}

	public BudgetQuery wfstatus(Double wfstatus) {
		if (wfstatus == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatus = wfstatus;
		return this;
	}

	public BudgetQuery wfstatusGreaterThanOrEqual(
			Double wfstatusGreaterThanOrEqual) {
		if (wfstatusGreaterThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusGreaterThanOrEqual = wfstatusGreaterThanOrEqual;
		return this;
	}

	public BudgetQuery wfstatusLessThanOrEqual(Double wfstatusLessThanOrEqual) {
		if (wfstatusLessThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusLessThanOrEqual = wfstatusLessThanOrEqual;
		return this;
	}

	public BudgetQuery wfstatuss(List<Double> wfstatuss) {
		if (wfstatuss == null) {
			throw new RuntimeException("wfstatuss is empty ");
		}
		this.wfstatuss = wfstatuss;
		return this;
	}

	public BudgetQuery brands1(String brands1) {
		if (brands1 == null) {
			throw new RuntimeException("brands1 is null");
		}
		this.brands1 = brands1;
		return this;
	}

	public BudgetQuery brands1Like(String brands1Like) {
		if (brands1Like == null) {
			throw new RuntimeException("brands1 is null");
		}
		this.brands1Like = brands1Like;
		return this;
	}

	public BudgetQuery brands1s(List<String> brands1s) {
		if (brands1s == null) {
			throw new RuntimeException("brands1s is empty ");
		}
		this.brands1s = brands1s;
		return this;
	}

	public BudgetQuery brands1account(Double brands1account) {
		if (brands1account == null) {
			throw new RuntimeException("brands1account is null");
		}
		this.brands1account = brands1account;
		return this;
	}

	public BudgetQuery brands1accountGreaterThanOrEqual(
			Double brands1accountGreaterThanOrEqual) {
		if (brands1accountGreaterThanOrEqual == null) {
			throw new RuntimeException("brands1account is null");
		}
		this.brands1accountGreaterThanOrEqual = brands1accountGreaterThanOrEqual;
		return this;
	}

	public BudgetQuery brands1accountLessThanOrEqual(
			Double brands1accountLessThanOrEqual) {
		if (brands1accountLessThanOrEqual == null) {
			throw new RuntimeException("brands1account is null");
		}
		this.brands1accountLessThanOrEqual = brands1accountLessThanOrEqual;
		return this;
	}

	public BudgetQuery brands1accounts(List<Double> brands1accounts) {
		if (brands1accounts == null) {
			throw new RuntimeException("brands1accounts is empty ");
		}
		this.brands1accounts = brands1accounts;
		return this;
	}

	public BudgetQuery brands2(String brands2) {
		if (brands2 == null) {
			throw new RuntimeException("brands2 is null");
		}
		this.brands2 = brands2;
		return this;
	}

	public BudgetQuery brands2Like(String brands2Like) {
		if (brands2Like == null) {
			throw new RuntimeException("brands2 is null");
		}
		this.brands2Like = brands2Like;
		return this;
	}

	public BudgetQuery brands2s(List<String> brands2s) {
		if (brands2s == null) {
			throw new RuntimeException("brands2s is empty ");
		}
		this.brands2s = brands2s;
		return this;
	}

	public BudgetQuery brands2account(Double brands2account) {
		if (brands2account == null) {
			throw new RuntimeException("brands2account is null");
		}
		this.brands2account = brands2account;
		return this;
	}

	public BudgetQuery brands2accountGreaterThanOrEqual(
			Double brands2accountGreaterThanOrEqual) {
		if (brands2accountGreaterThanOrEqual == null) {
			throw new RuntimeException("brands2account is null");
		}
		this.brands2accountGreaterThanOrEqual = brands2accountGreaterThanOrEqual;
		return this;
	}

	public BudgetQuery brands2accountLessThanOrEqual(
			Double brands2accountLessThanOrEqual) {
		if (brands2accountLessThanOrEqual == null) {
			throw new RuntimeException("brands2account is null");
		}
		this.brands2accountLessThanOrEqual = brands2accountLessThanOrEqual;
		return this;
	}

	public BudgetQuery brands2accounts(List<Double> brands2accounts) {
		if (brands2accounts == null) {
			throw new RuntimeException("brands2accounts is empty ");
		}
		this.brands2accounts = brands2accounts;
		return this;
	}

	public BudgetQuery brands3(String brands3) {
		if (brands3 == null) {
			throw new RuntimeException("brands3 is null");
		}
		this.brands3 = brands3;
		return this;
	}

	public BudgetQuery brands3Like(String brands3Like) {
		if (brands3Like == null) {
			throw new RuntimeException("brands3 is null");
		}
		this.brands3Like = brands3Like;
		return this;
	}

	public BudgetQuery brands3s(List<String> brands3s) {
		if (brands3s == null) {
			throw new RuntimeException("brands3s is empty ");
		}
		this.brands3s = brands3s;
		return this;
	}

	public BudgetQuery brands3account(Double brands3account) {
		if (brands3account == null) {
			throw new RuntimeException("brands3account is null");
		}
		this.brands3account = brands3account;
		return this;
	}

	public BudgetQuery brands3accountGreaterThanOrEqual(
			Double brands3accountGreaterThanOrEqual) {
		if (brands3accountGreaterThanOrEqual == null) {
			throw new RuntimeException("brands3account is null");
		}
		this.brands3accountGreaterThanOrEqual = brands3accountGreaterThanOrEqual;
		return this;
	}

	public BudgetQuery brands3accountLessThanOrEqual(
			Double brands3accountLessThanOrEqual) {
		if (brands3accountLessThanOrEqual == null) {
			throw new RuntimeException("brands3account is null");
		}
		this.brands3accountLessThanOrEqual = brands3accountLessThanOrEqual;
		return this;
	}

	public BudgetQuery brands3accounts(List<Double> brands3accounts) {
		if (brands3accounts == null) {
			throw new RuntimeException("brands3accounts is empty ");
		}
		this.brands3accounts = brands3accounts;
		return this;
	}

	public BudgetQuery paymentdate(Date paymentdate) {
		if (paymentdate == null) {
			throw new RuntimeException("paymentdate is null");
		}
		this.paymentdate = paymentdate;
		return this;
	}

	public BudgetQuery paymentdateGreaterThanOrEqual(
			Date paymentdateGreaterThanOrEqual) {
		if (paymentdateGreaterThanOrEqual == null) {
			throw new RuntimeException("paymentdate is null");
		}
		this.paymentdateGreaterThanOrEqual = paymentdateGreaterThanOrEqual;
		return this;
	}

	public BudgetQuery paymentdateLessThanOrEqual(
			Date paymentdateLessThanOrEqual) {
		if (paymentdateLessThanOrEqual == null) {
			throw new RuntimeException("paymentdate is null");
		}
		this.paymentdateLessThanOrEqual = paymentdateLessThanOrEqual;
		return this;
	}

	public BudgetQuery paymentdates(List<Date> paymentdates) {
		if (paymentdates == null) {
			throw new RuntimeException("paymentdates is empty ");
		}
		this.paymentdates = paymentdates;
		return this;
	}

	public BudgetQuery remark(String remark) {
		if (remark == null) {
			throw new RuntimeException("remark is null");
		}
		this.remark = remark;
		return this;
	}

	public BudgetQuery remarkLike(String remarkLike) {
		if (remarkLike == null) {
			throw new RuntimeException("remark is null");
		}
		this.remarkLike = remarkLike;
		return this;
	}

	public BudgetQuery remarks(List<String> remarks) {
		if (remarks == null) {
			throw new RuntimeException("remarks is empty ");
		}
		this.remarks = remarks;
		return this;
	}

	public BudgetQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public BudgetQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public BudgetQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public BudgetQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public BudgetQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public BudgetQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public BudgetQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public BudgetQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public BudgetQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public BudgetQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public BudgetQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public BudgetQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public BudgetQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public BudgetQuery updateBys(List<String> updateBys) {
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

			if ("budgetno".equals(sortColumn)) {
				orderBy = "E.budgetno" + a_x;
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

			if ("proname".equals(sortColumn)) {
				orderBy = "E.proname" + a_x;
			}

			if ("budgetsum".equals(sortColumn)) {
				orderBy = "E.budgetsum" + a_x;
			}

			if ("currency".equals(sortColumn)) {
				orderBy = "E.currency" + a_x;
			}

			if ("paymentmodel".equals(sortColumn)) {
				orderBy = "E.paymentmodel" + a_x;
			}

			if ("paymenttype".equals(sortColumn)) {
				orderBy = "E.paymenttype" + a_x;
			}

			if ("supname".equals(sortColumn)) {
				orderBy = "E.supname" + a_x;
			}

			if ("supaccount".equals(sortColumn)) {
				orderBy = "E.supaccount" + a_x;
			}

			if ("supbank".equals(sortColumn)) {
				orderBy = "E.supbank" + a_x;
			}

			if ("supaddress".equals(sortColumn)) {
				orderBy = "E.supaddress" + a_x;
			}

			if ("attachment".equals(sortColumn)) {
				orderBy = "E.attachment" + a_x;
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

			if ("paymentdate".equals(sortColumn)) {
				orderBy = "E.paymentdate" + a_x;
			}

			if ("remark".equals(sortColumn)) {
				orderBy = "E.remark" + a_x;
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
		addColumn("budgetid", "budgetid");
		addColumn("budgetno", "budgetno");
		addColumn("area", "area");
		addColumn("company", "company");
		addColumn("dept", "dept");
		addColumn("post", "post");
		addColumn("appuser", "appuser");
		addColumn("appdate", "appdate");
		addColumn("proname", "proname");
		addColumn("budgetsum", "budgetsum");
		addColumn("currency", "currency");
		addColumn("paymentmodel", "paymentmodel");
		addColumn("paymenttype", "paymenttype");
		addColumn("supname", "supname");
		addColumn("supaccount", "supaccount");
		addColumn("supbank", "supbank");
		addColumn("supaddress", "supaddress");
		addColumn("attachment", "attachment");
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
		addColumn("paymentdate", "paymentdate");
		addColumn("remark", "remark");
		addColumn("createBy", "createBy");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("updateBy", "updateBy");
	}

}