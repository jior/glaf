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
package com.glaf.oa.contract.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ContractQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected String contactname;
	protected String contactnameLike;
	protected List<String> contactnames;
	protected String projrctname;
	protected String projrctnameLike;
	protected List<String> projrctnames;
	protected String companyname;
	protected String companynameLike;
	protected List<String> companynames;
	protected String supplisername;
	protected String supplisernameLike;
	protected List<String> supplisernames;
	protected String currency;
	protected String currencyLike;
	protected List<String> currencys;
	protected Double contractsum;
	protected Double contractsumGreaterThanOrEqual;
	protected Double contractsumLessThanOrEqual;
	protected List<Double> contractsums;
	protected Integer paytype;
	protected Integer paytypeGreaterThanOrEqual;
	protected Integer paytypeLessThanOrEqual;
	protected List<Integer> paytypes;
	protected String remarks;
	protected String remarksLike;
	protected List<String> remarkss;
	protected String attachment;
	protected String attachmentLike;
	protected List<String> attachments;
	protected List<Integer> statuss;
	protected String appuser;
	protected String appuserLike;
	protected List<String> appusers;
	protected Date appdate;
	protected Date appdateGreaterThanOrEqual;
	protected Date appdateLessThanOrEqual;
	protected List<Date> appdates;
	protected String contractno;
	protected String contractnoLike;
	protected List<String> contractnos;
	protected String processname;
	protected String processnameLike;
	protected List<String> processnames;
	protected String processinstanceid;
	protected String processinstanceidLike;
	protected List<String> processinstanceids;
	protected Double wfstatus;
	protected Double wfstatusGreaterThanOrEqual;
	protected Double wfstatusLessThanOrEqual;
	protected List<Double> wfstatuss;
	protected String appusername;
	protected String appusernameLike;
	protected List<String> appusernames;
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
	protected String strstauts;
	protected String strstautsLike;
	protected List<String> strstautss;
	protected String area;
	protected String areaLike;
	protected List<String> areas;
	protected String post;
	protected String postLike;
	protected List<String> posts;
	protected String dept;
	protected String deptLike;
	protected List<String> depts;
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

	public ContractQuery() {

	}

	public String getContactname() {
		return contactname;
	}

	public String getContactnameLike() {
		if (contactnameLike != null && contactnameLike.trim().length() > 0) {
			if (!contactnameLike.startsWith("%")) {
				contactnameLike = "%" + contactnameLike;
			}
			if (!contactnameLike.endsWith("%")) {
				contactnameLike = contactnameLike + "%";
			}
		}
		return contactnameLike;
	}

	public List<String> getContactnames() {
		return contactnames;
	}

	public String getProjrctname() {
		return projrctname;
	}

	public String getProjrctnameLike() {
		if (projrctnameLike != null && projrctnameLike.trim().length() > 0) {
			if (!projrctnameLike.startsWith("%")) {
				projrctnameLike = "%" + projrctnameLike;
			}
			if (!projrctnameLike.endsWith("%")) {
				projrctnameLike = projrctnameLike + "%";
			}
		}
		return projrctnameLike;
	}

	public List<String> getProjrctnames() {
		return projrctnames;
	}

	public String getCompanyname() {
		return companyname;
	}

	public String getCompanynameLike() {
		if (companynameLike != null && companynameLike.trim().length() > 0) {
			if (!companynameLike.startsWith("%")) {
				companynameLike = "%" + companynameLike;
			}
			if (!companynameLike.endsWith("%")) {
				companynameLike = companynameLike + "%";
			}
		}
		return companynameLike;
	}

	public List<String> getCompanynames() {
		return companynames;
	}

	public String getSupplisername() {
		return supplisername;
	}

	public String getSupplisernameLike() {
		if (supplisernameLike != null && supplisernameLike.trim().length() > 0) {
			if (!supplisernameLike.startsWith("%")) {
				supplisernameLike = "%" + supplisernameLike;
			}
			if (!supplisernameLike.endsWith("%")) {
				supplisernameLike = supplisernameLike + "%";
			}
		}
		return supplisernameLike;
	}

	public List<String> getSupplisernames() {
		return supplisernames;
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

	public Double getContractsum() {
		return contractsum;
	}

	public Double getContractsumGreaterThanOrEqual() {
		return contractsumGreaterThanOrEqual;
	}

	public Double getContractsumLessThanOrEqual() {
		return contractsumLessThanOrEqual;
	}

	public List<Double> getContractsums() {
		return contractsums;
	}

	public Integer getPaytype() {
		return paytype;
	}

	public Integer getPaytypeGreaterThanOrEqual() {
		return paytypeGreaterThanOrEqual;
	}

	public Integer getPaytypeLessThanOrEqual() {
		return paytypeLessThanOrEqual;
	}

	public List<Integer> getPaytypes() {
		return paytypes;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getRemarksLike() {
		if (remarksLike != null && remarksLike.trim().length() > 0) {
			if (!remarksLike.startsWith("%")) {
				remarksLike = "%" + remarksLike;
			}
			if (!remarksLike.endsWith("%")) {
				remarksLike = remarksLike + "%";
			}
		}
		return remarksLike;
	}

	public List<String> getRemarkss() {
		return remarkss;
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

	public String getContractno() {
		return contractno;
	}

	public String getContractnoLike() {
		if (contractnoLike != null && contractnoLike.trim().length() > 0) {
			if (!contractnoLike.startsWith("%")) {
				contractnoLike = "%" + contractnoLike;
			}
			if (!contractnoLike.endsWith("%")) {
				contractnoLike = contractnoLike + "%";
			}
		}
		return contractnoLike;
	}

	public List<String> getContractnos() {
		return contractnos;
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

	public String getProcessinstanceid() {
		return processinstanceid;
	}

	public String getProcessinstanceidLike() {
		if (processinstanceidLike != null
				&& processinstanceidLike.trim().length() > 0) {
			if (!processinstanceidLike.startsWith("%")) {
				processinstanceidLike = "%" + processinstanceidLike;
			}
			if (!processinstanceidLike.endsWith("%")) {
				processinstanceidLike = processinstanceidLike + "%";
			}
		}
		return processinstanceidLike;
	}

	public List<String> getProcessinstanceids() {
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

	public String getAppusername() {
		return appusername;
	}

	public String getAppusernameLike() {
		if (appusernameLike != null && appusernameLike.trim().length() > 0) {
			if (!appusernameLike.startsWith("%")) {
				appusernameLike = "%" + appusernameLike;
			}
			if (!appusernameLike.endsWith("%")) {
				appusernameLike = appusernameLike + "%";
			}
		}
		return appusernameLike;
	}

	public List<String> getAppusernames() {
		return appusernames;
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

	public String getStrstauts() {
		return strstauts;
	}

	public String getStrstautsLike() {
		if (strstautsLike != null && strstautsLike.trim().length() > 0) {
			if (!strstautsLike.startsWith("%")) {
				strstautsLike = "%" + strstautsLike;
			}
			if (!strstautsLike.endsWith("%")) {
				strstautsLike = strstautsLike + "%";
			}
		}
		return strstautsLike;
	}

	public List<String> getStrstautss() {
		return strstautss;
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

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public void setContactnameLike(String contactnameLike) {
		this.contactnameLike = contactnameLike;
	}

	public void setContactnames(List<String> contactnames) {
		this.contactnames = contactnames;
	}

	public void setProjrctname(String projrctname) {
		this.projrctname = projrctname;
	}

	public void setProjrctnameLike(String projrctnameLike) {
		this.projrctnameLike = projrctnameLike;
	}

	public void setProjrctnames(List<String> projrctnames) {
		this.projrctnames = projrctnames;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public void setCompanynameLike(String companynameLike) {
		this.companynameLike = companynameLike;
	}

	public void setCompanynames(List<String> companynames) {
		this.companynames = companynames;
	}

	public void setSupplisername(String supplisername) {
		this.supplisername = supplisername;
	}

	public void setSupplisernameLike(String supplisernameLike) {
		this.supplisernameLike = supplisernameLike;
	}

	public void setSupplisernames(List<String> supplisernames) {
		this.supplisernames = supplisernames;
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

	public void setContractsum(Double contractsum) {
		this.contractsum = contractsum;
	}

	public void setContractsumGreaterThanOrEqual(
			Double contractsumGreaterThanOrEqual) {
		this.contractsumGreaterThanOrEqual = contractsumGreaterThanOrEqual;
	}

	public void setContractsumLessThanOrEqual(Double contractsumLessThanOrEqual) {
		this.contractsumLessThanOrEqual = contractsumLessThanOrEqual;
	}

	public void setContractsums(List<Double> contractsums) {
		this.contractsums = contractsums;
	}

	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}

	public void setPaytypeGreaterThanOrEqual(Integer paytypeGreaterThanOrEqual) {
		this.paytypeGreaterThanOrEqual = paytypeGreaterThanOrEqual;
	}

	public void setPaytypeLessThanOrEqual(Integer paytypeLessThanOrEqual) {
		this.paytypeLessThanOrEqual = paytypeLessThanOrEqual;
	}

	public void setPaytypes(List<Integer> paytypes) {
		this.paytypes = paytypes;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setRemarksLike(String remarksLike) {
		this.remarksLike = remarksLike;
	}

	public void setRemarkss(List<String> remarkss) {
		this.remarkss = remarkss;
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

	public void setContractno(String contractno) {
		this.contractno = contractno;
	}

	public void setContractnoLike(String contractnoLike) {
		this.contractnoLike = contractnoLike;
	}

	public void setContractnos(List<String> contractnos) {
		this.contractnos = contractnos;
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

	public void setProcessinstanceid(String processinstanceid) {
		this.processinstanceid = processinstanceid;
	}

	public void setProcessinstanceidLike(String processinstanceidLike) {
		this.processinstanceidLike = processinstanceidLike;
	}

	public void setProcessinstanceids(List<String> processinstanceids) {
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

	public void setAppusername(String appusername) {
		this.appusername = appusername;
	}

	public void setAppusernameLike(String appusernameLike) {
		this.appusernameLike = appusernameLike;
	}

	public void setAppusernames(List<String> appusernames) {
		this.appusernames = appusernames;
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

	public void setStrstauts(String strstauts) {
		this.strstauts = strstauts;
	}

	public void setStrstautsLike(String strstautsLike) {
		this.strstautsLike = strstautsLike;
	}

	public void setStrstautss(List<String> strstautss) {
		this.strstautss = strstautss;
	}

	public void setArea(String eara) {
		this.area = eara;
	}

	public void setAreaLike(String earaLike) {
		this.areaLike = earaLike;
	}

	public void setAreas(List<String> earas) {
		this.areas = earas;
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

	public void setDept(String dept) {
		this.dept = dept;
	}

	public void setDeptLike(String deptLike) {
		this.deptLike = deptLike;
	}

	public void setDepts(List<String> depts) {
		this.depts = depts;
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

	public ContractQuery contactname(String contactname) {
		if (contactname == null) {
			throw new RuntimeException("contactname is null");
		}
		this.contactname = contactname;
		return this;
	}

	public ContractQuery contactnameLike(String contactnameLike) {
		if (contactnameLike == null) {
			throw new RuntimeException("contactname is null");
		}
		this.contactnameLike = contactnameLike;
		return this;
	}

	public ContractQuery contactnames(List<String> contactnames) {
		if (contactnames == null) {
			throw new RuntimeException("contactnames is empty ");
		}
		this.contactnames = contactnames;
		return this;
	}

	public ContractQuery projrctname(String projrctname) {
		if (projrctname == null) {
			throw new RuntimeException("projrctname is null");
		}
		this.projrctname = projrctname;
		return this;
	}

	public ContractQuery projrctnameLike(String projrctnameLike) {
		if (projrctnameLike == null) {
			throw new RuntimeException("projrctname is null");
		}
		this.projrctnameLike = projrctnameLike;
		return this;
	}

	public ContractQuery projrctnames(List<String> projrctnames) {
		if (projrctnames == null) {
			throw new RuntimeException("projrctnames is empty ");
		}
		this.projrctnames = projrctnames;
		return this;
	}

	public ContractQuery companyname(String companyname) {
		if (companyname == null) {
			throw new RuntimeException("companyname is null");
		}
		this.companyname = companyname;
		return this;
	}

	public ContractQuery companynameLike(String companynameLike) {
		if (companynameLike == null) {
			throw new RuntimeException("companyname is null");
		}
		this.companynameLike = companynameLike;
		return this;
	}

	public ContractQuery companynames(List<String> companynames) {
		if (companynames == null) {
			throw new RuntimeException("companynames is empty ");
		}
		this.companynames = companynames;
		return this;
	}

	public ContractQuery supplisername(String supplisername) {
		if (supplisername == null) {
			throw new RuntimeException("supplisername is null");
		}
		this.supplisername = supplisername;
		return this;
	}

	public ContractQuery supplisernameLike(String supplisernameLike) {
		if (supplisernameLike == null) {
			throw new RuntimeException("supplisername is null");
		}
		this.supplisernameLike = supplisernameLike;
		return this;
	}

	public ContractQuery supplisernames(List<String> supplisernames) {
		if (supplisernames == null) {
			throw new RuntimeException("supplisernames is empty ");
		}
		this.supplisernames = supplisernames;
		return this;
	}

	public ContractQuery currency(String currency) {
		if (currency == null) {
			throw new RuntimeException("currency is null");
		}
		this.currency = currency;
		return this;
	}

	public ContractQuery currencyLike(String currencyLike) {
		if (currencyLike == null) {
			throw new RuntimeException("currency is null");
		}
		this.currencyLike = currencyLike;
		return this;
	}

	public ContractQuery currencys(List<String> currencys) {
		if (currencys == null) {
			throw new RuntimeException("currencys is empty ");
		}
		this.currencys = currencys;
		return this;
	}

	public ContractQuery contractsum(Double contractsum) {
		if (contractsum == null) {
			throw new RuntimeException("contractsum is null");
		}
		this.contractsum = contractsum;
		return this;
	}

	public ContractQuery contractsumGreaterThanOrEqual(
			Double contractsumGreaterThanOrEqual) {
		if (contractsumGreaterThanOrEqual == null) {
			throw new RuntimeException("contractsum is null");
		}
		this.contractsumGreaterThanOrEqual = contractsumGreaterThanOrEqual;
		return this;
	}

	public ContractQuery contractsumLessThanOrEqual(
			Double contractsumLessThanOrEqual) {
		if (contractsumLessThanOrEqual == null) {
			throw new RuntimeException("contractsum is null");
		}
		this.contractsumLessThanOrEqual = contractsumLessThanOrEqual;
		return this;
	}

	public ContractQuery contractsums(List<Double> contractsums) {
		if (contractsums == null) {
			throw new RuntimeException("contractsums is empty ");
		}
		this.contractsums = contractsums;
		return this;
	}

	public ContractQuery paytype(Integer paytype) {
		if (paytype == null) {
			throw new RuntimeException("paytype is null");
		}
		this.paytype = paytype;
		return this;
	}

	public ContractQuery paytypeGreaterThanOrEqual(
			Integer paytypeGreaterThanOrEqual) {
		if (paytypeGreaterThanOrEqual == null) {
			throw new RuntimeException("paytype is null");
		}
		this.paytypeGreaterThanOrEqual = paytypeGreaterThanOrEqual;
		return this;
	}

	public ContractQuery paytypeLessThanOrEqual(Integer paytypeLessThanOrEqual) {
		if (paytypeLessThanOrEqual == null) {
			throw new RuntimeException("paytype is null");
		}
		this.paytypeLessThanOrEqual = paytypeLessThanOrEqual;
		return this;
	}

	public ContractQuery paytypes(List<Integer> paytypes) {
		if (paytypes == null) {
			throw new RuntimeException("paytypes is empty ");
		}
		this.paytypes = paytypes;
		return this;
	}

	public ContractQuery remarks(String remarks) {
		if (remarks == null) {
			throw new RuntimeException("remarks is null");
		}
		this.remarks = remarks;
		return this;
	}

	public ContractQuery remarksLike(String remarksLike) {
		if (remarksLike == null) {
			throw new RuntimeException("remarks is null");
		}
		this.remarksLike = remarksLike;
		return this;
	}

	public ContractQuery remarkss(List<String> remarkss) {
		if (remarkss == null) {
			throw new RuntimeException("remarkss is empty ");
		}
		this.remarkss = remarkss;
		return this;
	}

	public ContractQuery attachment(String attachment) {
		if (attachment == null) {
			throw new RuntimeException("attachment is null");
		}
		this.attachment = attachment;
		return this;
	}

	public ContractQuery attachmentLike(String attachmentLike) {
		if (attachmentLike == null) {
			throw new RuntimeException("attachment is null");
		}
		this.attachmentLike = attachmentLike;
		return this;
	}

	public ContractQuery attachments(List<String> attachments) {
		if (attachments == null) {
			throw new RuntimeException("attachments is empty ");
		}
		this.attachments = attachments;
		return this;
	}

	public ContractQuery status(Integer status) {
		if (status == null) {
			throw new RuntimeException("status is null");
		}
		this.status = status;
		return this;
	}

	public ContractQuery statusGreaterThanOrEqual(
			Integer statusGreaterThanOrEqual) {
		if (statusGreaterThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
		return this;
	}

	public ContractQuery statusLessThanOrEqual(Integer statusLessThanOrEqual) {
		if (statusLessThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusLessThanOrEqual = statusLessThanOrEqual;
		return this;
	}

	public ContractQuery statuss(List<Integer> statuss) {
		if (statuss == null) {
			throw new RuntimeException("statuss is empty ");
		}
		this.statuss = statuss;
		return this;
	}

	public ContractQuery appuser(String appuser) {
		if (appuser == null) {
			throw new RuntimeException("appuser is null");
		}
		this.appuser = appuser;
		return this;
	}

	public ContractQuery appuserLike(String appuserLike) {
		if (appuserLike == null) {
			throw new RuntimeException("appuser is null");
		}
		this.appuserLike = appuserLike;
		return this;
	}

	public ContractQuery appusers(List<String> appusers) {
		if (appusers == null) {
			throw new RuntimeException("appusers is empty ");
		}
		this.appusers = appusers;
		return this;
	}

	public ContractQuery appdate(Date appdate) {
		if (appdate == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdate = appdate;
		return this;
	}

	public ContractQuery appdateGreaterThanOrEqual(
			Date appdateGreaterThanOrEqual) {
		if (appdateGreaterThanOrEqual == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdateGreaterThanOrEqual = appdateGreaterThanOrEqual;
		return this;
	}

	public ContractQuery appdateLessThanOrEqual(Date appdateLessThanOrEqual) {
		if (appdateLessThanOrEqual == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdateLessThanOrEqual = appdateLessThanOrEqual;
		return this;
	}

	public ContractQuery appdates(List<Date> appdates) {
		if (appdates == null) {
			throw new RuntimeException("appdates is empty ");
		}
		this.appdates = appdates;
		return this;
	}

	public ContractQuery contractno(String contractno) {
		if (contractno == null) {
			throw new RuntimeException("contractno is null");
		}
		this.contractno = contractno;
		return this;
	}

	public ContractQuery contractnoLike(String contractnoLike) {
		if (contractnoLike == null) {
			throw new RuntimeException("contractno is null");
		}
		this.contractnoLike = contractnoLike;
		return this;
	}

	public ContractQuery contractnos(List<String> contractnos) {
		if (contractnos == null) {
			throw new RuntimeException("contractnos is empty ");
		}
		this.contractnos = contractnos;
		return this;
	}

	public ContractQuery processname(String processname) {
		if (processname == null) {
			throw new RuntimeException("processname is null");
		}
		this.processname = processname;
		return this;
	}

	public ContractQuery processnameLike(String processnameLike) {
		if (processnameLike == null) {
			throw new RuntimeException("processname is null");
		}
		this.processnameLike = processnameLike;
		return this;
	}

	public ContractQuery processnames(List<String> processnames) {
		if (processnames == null) {
			throw new RuntimeException("processnames is empty ");
		}
		this.processnames = processnames;
		return this;
	}

	public ContractQuery processinstanceid(String processinstanceid) {
		if (processinstanceid == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceid = processinstanceid;
		return this;
	}

	public ContractQuery processinstanceidLike(String processinstanceidLike) {
		if (processinstanceidLike == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceidLike = processinstanceidLike;
		return this;
	}

	public ContractQuery processinstanceids(List<String> processinstanceids) {
		if (processinstanceids == null) {
			throw new RuntimeException("processinstanceids is empty ");
		}
		this.processinstanceids = processinstanceids;
		return this;
	}

	public ContractQuery wfstatus(Double wfstatus) {
		if (wfstatus == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatus = wfstatus;
		return this;
	}

	public ContractQuery wfstatusGreaterThanOrEqual(
			Double wfstatusGreaterThanOrEqual) {
		if (wfstatusGreaterThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusGreaterThanOrEqual = wfstatusGreaterThanOrEqual;
		return this;
	}

	public ContractQuery wfstatusLessThanOrEqual(Double wfstatusLessThanOrEqual) {
		if (wfstatusLessThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusLessThanOrEqual = wfstatusLessThanOrEqual;
		return this;
	}

	public ContractQuery wfstatuss(List<Double> wfstatuss) {
		if (wfstatuss == null) {
			throw new RuntimeException("wfstatuss is empty ");
		}
		this.wfstatuss = wfstatuss;
		return this;
	}

	public ContractQuery appusername(String appusername) {
		if (appusername == null) {
			throw new RuntimeException("appusername is null");
		}
		this.appusername = appusername;
		return this;
	}

	public ContractQuery appusernameLike(String appusernameLike) {
		if (appusernameLike == null) {
			throw new RuntimeException("appusername is null");
		}
		this.appusernameLike = appusernameLike;
		return this;
	}

	public ContractQuery appusernames(List<String> appusernames) {
		if (appusernames == null) {
			throw new RuntimeException("appusernames is empty ");
		}
		this.appusernames = appusernames;
		return this;
	}

	public ContractQuery brands1(String brands1) {
		if (brands1 == null) {
			throw new RuntimeException("brands1 is null");
		}
		this.brands1 = brands1;
		return this;
	}

	public ContractQuery brands1Like(String brands1Like) {
		if (brands1Like == null) {
			throw new RuntimeException("brands1 is null");
		}
		this.brands1Like = brands1Like;
		return this;
	}

	public ContractQuery brands1s(List<String> brands1s) {
		if (brands1s == null) {
			throw new RuntimeException("brands1s is empty ");
		}
		this.brands1s = brands1s;
		return this;
	}

	public ContractQuery brands1account(Double brands1account) {
		if (brands1account == null) {
			throw new RuntimeException("brands1account is null");
		}
		this.brands1account = brands1account;
		return this;
	}

	public ContractQuery brands1accountGreaterThanOrEqual(
			Double brands1accountGreaterThanOrEqual) {
		if (brands1accountGreaterThanOrEqual == null) {
			throw new RuntimeException("brands1account is null");
		}
		this.brands1accountGreaterThanOrEqual = brands1accountGreaterThanOrEqual;
		return this;
	}

	public ContractQuery brands1accountLessThanOrEqual(
			Double brands1accountLessThanOrEqual) {
		if (brands1accountLessThanOrEqual == null) {
			throw new RuntimeException("brands1account is null");
		}
		this.brands1accountLessThanOrEqual = brands1accountLessThanOrEqual;
		return this;
	}

	public ContractQuery brands1accounts(List<Double> brands1accounts) {
		if (brands1accounts == null) {
			throw new RuntimeException("brands1accounts is empty ");
		}
		this.brands1accounts = brands1accounts;
		return this;
	}

	public ContractQuery brands2(String brands2) {
		if (brands2 == null) {
			throw new RuntimeException("brands2 is null");
		}
		this.brands2 = brands2;
		return this;
	}

	public ContractQuery brands2Like(String brands2Like) {
		if (brands2Like == null) {
			throw new RuntimeException("brands2 is null");
		}
		this.brands2Like = brands2Like;
		return this;
	}

	public ContractQuery brands2s(List<String> brands2s) {
		if (brands2s == null) {
			throw new RuntimeException("brands2s is empty ");
		}
		this.brands2s = brands2s;
		return this;
	}

	public ContractQuery brands2account(Double brands2account) {
		if (brands2account == null) {
			throw new RuntimeException("brands2account is null");
		}
		this.brands2account = brands2account;
		return this;
	}

	public ContractQuery brands2accountGreaterThanOrEqual(
			Double brands2accountGreaterThanOrEqual) {
		if (brands2accountGreaterThanOrEqual == null) {
			throw new RuntimeException("brands2account is null");
		}
		this.brands2accountGreaterThanOrEqual = brands2accountGreaterThanOrEqual;
		return this;
	}

	public ContractQuery brands2accountLessThanOrEqual(
			Double brands2accountLessThanOrEqual) {
		if (brands2accountLessThanOrEqual == null) {
			throw new RuntimeException("brands2account is null");
		}
		this.brands2accountLessThanOrEqual = brands2accountLessThanOrEqual;
		return this;
	}

	public ContractQuery brands2accounts(List<Double> brands2accounts) {
		if (brands2accounts == null) {
			throw new RuntimeException("brands2accounts is empty ");
		}
		this.brands2accounts = brands2accounts;
		return this;
	}

	public ContractQuery brands3(String brands3) {
		if (brands3 == null) {
			throw new RuntimeException("brands3 is null");
		}
		this.brands3 = brands3;
		return this;
	}

	public ContractQuery brands3Like(String brands3Like) {
		if (brands3Like == null) {
			throw new RuntimeException("brands3 is null");
		}
		this.brands3Like = brands3Like;
		return this;
	}

	public ContractQuery brands3s(List<String> brands3s) {
		if (brands3s == null) {
			throw new RuntimeException("brands3s is empty ");
		}
		this.brands3s = brands3s;
		return this;
	}

	public ContractQuery brands3account(Double brands3account) {
		if (brands3account == null) {
			throw new RuntimeException("brands3account is null");
		}
		this.brands3account = brands3account;
		return this;
	}

	public ContractQuery brands3accountGreaterThanOrEqual(
			Double brands3accountGreaterThanOrEqual) {
		if (brands3accountGreaterThanOrEqual == null) {
			throw new RuntimeException("brands3account is null");
		}
		this.brands3accountGreaterThanOrEqual = brands3accountGreaterThanOrEqual;
		return this;
	}

	public ContractQuery brands3accountLessThanOrEqual(
			Double brands3accountLessThanOrEqual) {
		if (brands3accountLessThanOrEqual == null) {
			throw new RuntimeException("brands3account is null");
		}
		this.brands3accountLessThanOrEqual = brands3accountLessThanOrEqual;
		return this;
	}

	public ContractQuery brands3accounts(List<Double> brands3accounts) {
		if (brands3accounts == null) {
			throw new RuntimeException("brands3accounts is empty ");
		}
		this.brands3accounts = brands3accounts;
		return this;
	}

	public ContractQuery strstauts(String strstauts) {
		if (strstauts == null) {
			throw new RuntimeException("strstauts is null");
		}
		this.strstauts = strstauts;
		return this;
	}

	public ContractQuery strstautsLike(String strstautsLike) {
		if (strstautsLike == null) {
			throw new RuntimeException("strstauts is null");
		}
		this.strstautsLike = strstautsLike;
		return this;
	}

	public ContractQuery strstautss(List<String> strstautss) {
		if (strstautss == null) {
			throw new RuntimeException("strstautss is empty ");
		}
		this.strstautss = strstautss;
		return this;
	}

	public ContractQuery eara(String eara) {
		if (eara == null) {
			throw new RuntimeException("eara is null");
		}
		this.area = eara;
		return this;
	}

	public ContractQuery earaLike(String earaLike) {
		if (earaLike == null) {
			throw new RuntimeException("eara is null");
		}
		this.areaLike = earaLike;
		return this;
	}

	public ContractQuery earas(List<String> earas) {
		if (earas == null) {
			throw new RuntimeException("earas is empty ");
		}
		this.areas = earas;
		return this;
	}

	public ContractQuery post(String post) {
		if (post == null) {
			throw new RuntimeException("post is null");
		}
		this.post = post;
		return this;
	}

	public ContractQuery postLike(String postLike) {
		if (postLike == null) {
			throw new RuntimeException("post is null");
		}
		this.postLike = postLike;
		return this;
	}

	public ContractQuery posts(List<String> posts) {
		if (posts == null) {
			throw new RuntimeException("posts is empty ");
		}
		this.posts = posts;
		return this;
	}

	public ContractQuery dept(String dept) {
		if (dept == null) {
			throw new RuntimeException("dept is null");
		}
		this.dept = dept;
		return this;
	}

	public ContractQuery deptLike(String deptLike) {
		if (deptLike == null) {
			throw new RuntimeException("dept is null");
		}
		this.deptLike = deptLike;
		return this;
	}

	public ContractQuery depts(List<String> depts) {
		if (depts == null) {
			throw new RuntimeException("depts is empty ");
		}
		this.depts = depts;
		return this;
	}

	public ContractQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public ContractQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public ContractQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public ContractQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public ContractQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public ContractQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public ContractQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public ContractQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public ContractQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public ContractQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public ContractQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public ContractQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public ContractQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public ContractQuery updateBys(List<String> updateBys) {
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

			if ("contactname".equals(sortColumn)) {
				orderBy = "E.contactname" + a_x;
			}

			if ("projrctname".equals(sortColumn)) {
				orderBy = "E.projrctname" + a_x;
			}

			if ("companyname".equals(sortColumn)) {
				orderBy = "E.companyname" + a_x;
			}

			if ("supplisername".equals(sortColumn)) {
				orderBy = "E.supplisername" + a_x;
			}

			if ("currency".equals(sortColumn)) {
				orderBy = "E.currency" + a_x;
			}

			if ("contractsum".equals(sortColumn)) {
				orderBy = "E.contractsum" + a_x;
			}

			if ("paytype".equals(sortColumn)) {
				orderBy = "E.paytype" + a_x;
			}

			if ("remarks".equals(sortColumn)) {
				orderBy = "E.remarks" + a_x;
			}

			if ("attachment".equals(sortColumn)) {
				orderBy = "E.attachment" + a_x;
			}

			if ("status".equals(sortColumn)) {
				orderBy = "E.status" + a_x;
			}

			if ("appuser".equals(sortColumn)) {
				orderBy = "E.appuser" + a_x;
			}

			if ("appdate".equals(sortColumn)) {
				orderBy = "E.appdate" + a_x;
			}

			if ("contractno".equals(sortColumn)) {
				orderBy = "E.contractno" + a_x;
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

			if ("appusername".equals(sortColumn)) {
				orderBy = "E.appusername" + a_x;
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

			if ("strstauts".equals(sortColumn)) {
				orderBy = "E.strstauts" + a_x;
			}

			if ("eara".equals(sortColumn)) {
				orderBy = "E.eara" + a_x;
			}

			if ("post".equals(sortColumn)) {
				orderBy = "E.post" + a_x;
			}

			if ("dept".equals(sortColumn)) {
				orderBy = "E.dept" + a_x;
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
		addColumn("id", "id");
		addColumn("contactname", "contactname");
		addColumn("projrctname", "projrctname");
		addColumn("companyname", "companyname");
		addColumn("supplisername", "supplisername");
		addColumn("currency", "currency");
		addColumn("contractsum", "contractsum");
		addColumn("paytype", "paytype");
		addColumn("remarks", "remarks");
		addColumn("attachment", "attachment");
		addColumn("status", "status");
		addColumn("appuser", "appuser");
		addColumn("appdate", "appdate");
		addColumn("contractno", "contractno");
		addColumn("processname", "processname");
		addColumn("processinstanceid", "processinstanceid");
		addColumn("wfstatus", "wfstatus");
		addColumn("appusername", "appusername");
		addColumn("brands1", "brands1");
		addColumn("brands1account", "brands1account");
		addColumn("brands2", "brands2");
		addColumn("brands2account", "brands2account");
		addColumn("brands3", "brands3");
		addColumn("brands3account", "brands3account");
		addColumn("strstauts", "strstauts");
		addColumn("eara", "eara");
		addColumn("post", "post");
		addColumn("dept", "dept");
		addColumn("createBy", "createBy");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("updateBy", "updateBy");
	}

}