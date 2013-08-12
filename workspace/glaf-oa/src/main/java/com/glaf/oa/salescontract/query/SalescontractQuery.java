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
package com.glaf.oa.salescontract.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SalescontractQuery extends DataQuery {
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
	protected Double processinstanceid;
	protected Double processinstanceidGreaterThanOrEqual;
	protected Double processinstanceidLessThanOrEqual;
	protected List<Double> processinstanceids;
	protected Double wfstatus;
	protected Double wfstatusGreaterThanOrEqual;
	protected Double wfstatusLessThanOrEqual;
	protected List<Double> wfstatuss;

	protected Double optionalsum;
	protected Double optionalsumGreaterThanOrEqual;
	protected Double optionalsumLessThanOrEqual;
	protected List<Double> optionalsums;
	protected Double firstpay;
	protected Double firstpayGreaterThanOrEqual;
	protected Double firstpayLessThanOrEqual;
	protected List<Double> firstpays;
	protected Double lastpay;
	protected Double lastpayGreaterThanOrEqual;
	protected Double lastpayLessThanOrEqual;
	protected List<Double> lastpays;

	protected Double discount;
	protected Double discountGreaterThanOrEqual;
	protected Double discountLessThanOrEqual;
	protected List<Double> discounts;
	protected Date deliverydate;
	protected Date deliverydateGreaterThanOrEqual;
	protected Date deliverydateLessThanOrEqual;
	protected List<Date> deliverydates;
	protected String sales;
	protected String salesLike;
	protected List<String> saless;
	protected String contractsales;
	protected String contractsalesLike;
	protected List<String> contractsaless;
	protected Double giftsum;
	protected Double giftsumGreaterThanOrEqual;
	protected Double giftsumLessThanOrEqual;
	protected List<Double> giftsums;
	protected String giftremark;
	protected String giftremarkLike;
	protected List<String> giftremarks;
	protected String remark;
	protected String remarkLike;
	protected List<String> remarkes;
	protected String area;
	protected String company;
	protected String companyLike;
	protected List<String> companys;
	protected String createby;
	protected String createbyLike;
	protected List<String> createbys;
	protected Date createdate;
	protected Date createdateGreaterThanOrEqual;
	protected Date createdateLessThanOrEqual;
	protected List<Date> createdates;
	protected Date updatedate;
	protected Date updatedateGreaterThanOrEqual;
	protected Date updatedateLessThanOrEqual;
	protected List<Date> updatedates;
	protected String updateby;
	protected String updatebyLike;
	protected List<String> updatebys;
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

	public SalescontractQuery() {

	}

	public void setRemarkes(List<String> remarkes) {
		this.remarkes = remarkes;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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

	public Double getOptionalsum() {
		return optionalsum;
	}

	public Double getOptionalsumGreaterThanOrEqual() {
		return optionalsumGreaterThanOrEqual;
	}

	public Double getOptionalsumLessThanOrEqual() {
		return optionalsumLessThanOrEqual;
	}

	public List<Double> getOptionalsums() {
		return optionalsums;
	}

	public Double getFirstpay() {
		return firstpay;
	}

	public Double getFirstpayGreaterThanOrEqual() {
		return firstpayGreaterThanOrEqual;
	}

	public Double getFirstpayLessThanOrEqual() {
		return firstpayLessThanOrEqual;
	}

	public List<Double> getFirstpays() {
		return firstpays;
	}

	public Double getLastpay() {
		return lastpay;
	}

	public Double getLastpayGreaterThanOrEqual() {
		return lastpayGreaterThanOrEqual;
	}

	public Double getLastpayLessThanOrEqual() {
		return lastpayLessThanOrEqual;
	}

	public List<Double> getLastpays() {
		return lastpays;
	}

	public Double getDiscount() {
		return discount;
	}

	public Double getDiscountGreaterThanOrEqual() {
		return discountGreaterThanOrEqual;
	}

	public Double getDiscountLessThanOrEqual() {
		return discountLessThanOrEqual;
	}

	public List<Double> getDiscounts() {
		return discounts;
	}

	public Date getDeliverydate() {
		return deliverydate;
	}

	public Date getDeliverydateGreaterThanOrEqual() {
		return deliverydateGreaterThanOrEqual;
	}

	public Date getDeliverydateLessThanOrEqual() {
		return deliverydateLessThanOrEqual;
	}

	public List<Date> getDeliverydates() {
		return deliverydates;
	}

	public String getSales() {
		return sales;
	}

	public String getSalesLike() {
		if (salesLike != null && salesLike.trim().length() > 0) {
			if (!salesLike.startsWith("%")) {
				salesLike = "%" + salesLike;
			}
			if (!salesLike.endsWith("%")) {
				salesLike = salesLike + "%";
			}
		}
		return salesLike;
	}

	public List<String> getSaless() {
		return saless;
	}

	public String getContractsales() {
		return contractsales;
	}

	public String getContractsalesLike() {
		if (contractsalesLike != null && contractsalesLike.trim().length() > 0) {
			if (!contractsalesLike.startsWith("%")) {
				contractsalesLike = "%" + contractsalesLike;
			}
			if (!contractsalesLike.endsWith("%")) {
				contractsalesLike = contractsalesLike + "%";
			}
		}
		return contractsalesLike;
	}

	public List<String> getContractsaless() {
		return contractsaless;
	}

	public Double getGiftsum() {
		return giftsum;
	}

	public Double getGiftsumGreaterThanOrEqual() {
		return giftsumGreaterThanOrEqual;
	}

	public Double getGiftsumLessThanOrEqual() {
		return giftsumLessThanOrEqual;
	}

	public List<Double> getGiftsums() {
		return giftsums;
	}

	public String getGiftremark() {
		return giftremark;
	}

	public String getGiftremarkLike() {
		if (giftremarkLike != null && giftremarkLike.trim().length() > 0) {
			if (!giftremarkLike.startsWith("%")) {
				giftremarkLike = "%" + giftremarkLike;
			}
			if (!giftremarkLike.endsWith("%")) {
				giftremarkLike = giftremarkLike + "%";
			}
		}
		return giftremarkLike;
	}

	public List<String> getGiftremarks() {
		return giftremarks;
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

	public List<String> getRemarkes() {
		return remarkes;
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

	public String getCreateby() {
		return createby;
	}

	public String getCreatebyLike() {
		if (createbyLike != null && createbyLike.trim().length() > 0) {
			if (!createbyLike.startsWith("%")) {
				createbyLike = "%" + createbyLike;
			}
			if (!createbyLike.endsWith("%")) {
				createbyLike = createbyLike + "%";
			}
		}
		return createbyLike;
	}

	public List<String> getCreatebys() {
		return createbys;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public Date getCreatedateGreaterThanOrEqual() {
		return createdateGreaterThanOrEqual;
	}

	public Date getCreatedateLessThanOrEqual() {
		return createdateLessThanOrEqual;
	}

	public List<Date> getCreatedates() {
		return createdates;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public Date getUpdatedateGreaterThanOrEqual() {
		return updatedateGreaterThanOrEqual;
	}

	public Date getUpdatedateLessThanOrEqual() {
		return updatedateLessThanOrEqual;
	}

	public List<Date> getUpdatedates() {
		return updatedates;
	}

	public String getUpdateby() {
		return updateby;
	}

	public String getUpdatebyLike() {
		if (updatebyLike != null && updatebyLike.trim().length() > 0) {
			if (!updatebyLike.startsWith("%")) {
				updatebyLike = "%" + updatebyLike;
			}
			if (!updatebyLike.endsWith("%")) {
				updatebyLike = updatebyLike + "%";
			}
		}
		return updatebyLike;
	}

	public List<String> getUpdatebys() {
		return updatebys;
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

	public void setOptionalsum(Double optionalsum) {
		this.optionalsum = optionalsum;
	}

	public void setOptionalsumGreaterThanOrEqual(
			Double optionalsumGreaterThanOrEqual) {
		this.optionalsumGreaterThanOrEqual = optionalsumGreaterThanOrEqual;
	}

	public void setOptionalsumLessThanOrEqual(Double optionalsumLessThanOrEqual) {
		this.optionalsumLessThanOrEqual = optionalsumLessThanOrEqual;
	}

	public void setOptionalsums(List<Double> optionalsums) {
		this.optionalsums = optionalsums;
	}

	public void setFirstpay(Double firstpay) {
		this.firstpay = firstpay;
	}

	public void setFirstpayGreaterThanOrEqual(Double firstpayGreaterThanOrEqual) {
		this.firstpayGreaterThanOrEqual = firstpayGreaterThanOrEqual;
	}

	public void setFirstpayLessThanOrEqual(Double firstpayLessThanOrEqual) {
		this.firstpayLessThanOrEqual = firstpayLessThanOrEqual;
	}

	public void setFirstpays(List<Double> firstpays) {
		this.firstpays = firstpays;
	}

	public void setLastpay(Double lastpay) {
		this.lastpay = lastpay;
	}

	public void setLastpayGreaterThanOrEqual(Double lastpayGreaterThanOrEqual) {
		this.lastpayGreaterThanOrEqual = lastpayGreaterThanOrEqual;
	}

	public void setLastpayLessThanOrEqual(Double lastpayLessThanOrEqual) {
		this.lastpayLessThanOrEqual = lastpayLessThanOrEqual;
	}

	public void setLastpays(List<Double> lastpays) {
		this.lastpays = lastpays;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public void setDiscountGreaterThanOrEqual(Double discountGreaterThanOrEqual) {
		this.discountGreaterThanOrEqual = discountGreaterThanOrEqual;
	}

	public void setDiscountLessThanOrEqual(Double discountLessThanOrEqual) {
		this.discountLessThanOrEqual = discountLessThanOrEqual;
	}

	public void setDiscounts(List<Double> discounts) {
		this.discounts = discounts;
	}

	public void setDeliverydate(Date deliverydate) {
		this.deliverydate = deliverydate;
	}

	public void setDeliverydateGreaterThanOrEqual(
			Date deliverydateGreaterThanOrEqual) {
		this.deliverydateGreaterThanOrEqual = deliverydateGreaterThanOrEqual;
	}

	public void setDeliverydateLessThanOrEqual(Date deliverydateLessThanOrEqual) {
		this.deliverydateLessThanOrEqual = deliverydateLessThanOrEqual;
	}

	public void setDeliverydates(List<Date> deliverydates) {
		this.deliverydates = deliverydates;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public void setSalesLike(String salesLike) {
		this.salesLike = salesLike;
	}

	public void setSaless(List<String> saless) {
		this.saless = saless;
	}

	public void setContractsales(String contractsales) {
		this.contractsales = contractsales;
	}

	public void setContractsalesLike(String contractsalesLike) {
		this.contractsalesLike = contractsalesLike;
	}

	public void setContractsaless(List<String> contractsaless) {
		this.contractsaless = contractsaless;
	}

	public void setGiftsum(Double giftsum) {
		this.giftsum = giftsum;
	}

	public void setGiftsumGreaterThanOrEqual(Double giftsumGreaterThanOrEqual) {
		this.giftsumGreaterThanOrEqual = giftsumGreaterThanOrEqual;
	}

	public void setGiftsumLessThanOrEqual(Double giftsumLessThanOrEqual) {
		this.giftsumLessThanOrEqual = giftsumLessThanOrEqual;
	}

	public void setGiftsums(List<Double> giftsums) {
		this.giftsums = giftsums;
	}

	public void setGiftremark(String giftremark) {
		this.giftremark = giftremark;
	}

	public void setGiftremarkLike(String giftremarkLike) {
		this.giftremarkLike = giftremarkLike;
	}

	public void setGiftremarks(List<String> giftremarks) {
		this.giftremarks = giftremarks;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRemarkLike(String remarkLike) {
		this.remarkLike = remarkLike;
	}

	public void setRemarks(List<String> remarkes) {
		this.remarkes = remarkes;
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

	public void setCreateby(String createby) {
		this.createby = createby;
	}

	public void setCreatebyLike(String createbyLike) {
		this.createbyLike = createbyLike;
	}

	public void setCreatebys(List<String> createbys) {
		this.createbys = createbys;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public void setCreatedateGreaterThanOrEqual(
			Date createdateGreaterThanOrEqual) {
		this.createdateGreaterThanOrEqual = createdateGreaterThanOrEqual;
	}

	public void setCreatedateLessThanOrEqual(Date createdateLessThanOrEqual) {
		this.createdateLessThanOrEqual = createdateLessThanOrEqual;
	}

	public void setCreatedates(List<Date> createdates) {
		this.createdates = createdates;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public void setUpdatedateGreaterThanOrEqual(
			Date updatedateGreaterThanOrEqual) {
		this.updatedateGreaterThanOrEqual = updatedateGreaterThanOrEqual;
	}

	public void setUpdatedateLessThanOrEqual(Date updatedateLessThanOrEqual) {
		this.updatedateLessThanOrEqual = updatedateLessThanOrEqual;
	}

	public void setUpdatedates(List<Date> updatedates) {
		this.updatedates = updatedates;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

	public void setUpdatebyLike(String updatebyLike) {
		this.updatebyLike = updatebyLike;
	}

	public void setUpdatebys(List<String> updatebys) {
		this.updatebys = updatebys;
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

	public SalescontractQuery contactname(String contactname) {
		if (contactname == null) {
			throw new RuntimeException("contactname is null");
		}
		this.contactname = contactname;
		return this;
	}

	public SalescontractQuery contactnameLike(String contactnameLike) {
		if (contactnameLike == null) {
			throw new RuntimeException("contactname is null");
		}
		this.contactnameLike = contactnameLike;
		return this;
	}

	public SalescontractQuery contactnames(List<String> contactnames) {
		if (contactnames == null) {
			throw new RuntimeException("contactnames is empty ");
		}
		this.contactnames = contactnames;
		return this;
	}

	public SalescontractQuery projrctname(String projrctname) {
		if (projrctname == null) {
			throw new RuntimeException("projrctname is null");
		}
		this.projrctname = projrctname;
		return this;
	}

	public SalescontractQuery projrctnameLike(String projrctnameLike) {
		if (projrctnameLike == null) {
			throw new RuntimeException("projrctname is null");
		}
		this.projrctnameLike = projrctnameLike;
		return this;
	}

	public SalescontractQuery projrctnames(List<String> projrctnames) {
		if (projrctnames == null) {
			throw new RuntimeException("projrctnames is empty ");
		}
		this.projrctnames = projrctnames;
		return this;
	}

	public SalescontractQuery companyname(String companyname) {
		if (companyname == null) {
			throw new RuntimeException("companyname is null");
		}
		this.companyname = companyname;
		return this;
	}

	public SalescontractQuery companynameLike(String companynameLike) {
		if (companynameLike == null) {
			throw new RuntimeException("companyname is null");
		}
		this.companynameLike = companynameLike;
		return this;
	}

	public SalescontractQuery companynames(List<String> companynames) {
		if (companynames == null) {
			throw new RuntimeException("companynames is empty ");
		}
		this.companynames = companynames;
		return this;
	}

	public SalescontractQuery supplisername(String supplisername) {
		if (supplisername == null) {
			throw new RuntimeException("supplisername is null");
		}
		this.supplisername = supplisername;
		return this;
	}

	public SalescontractQuery supplisernameLike(String supplisernameLike) {
		if (supplisernameLike == null) {
			throw new RuntimeException("supplisername is null");
		}
		this.supplisernameLike = supplisernameLike;
		return this;
	}

	public SalescontractQuery supplisernames(List<String> supplisernames) {
		if (supplisernames == null) {
			throw new RuntimeException("supplisernames is empty ");
		}
		this.supplisernames = supplisernames;
		return this;
	}

	public SalescontractQuery currency(String currency) {
		if (currency == null) {
			throw new RuntimeException("currency is null");
		}
		this.currency = currency;
		return this;
	}

	public SalescontractQuery currencyLike(String currencyLike) {
		if (currencyLike == null) {
			throw new RuntimeException("currency is null");
		}
		this.currencyLike = currencyLike;
		return this;
	}

	public SalescontractQuery currencys(List<String> currencys) {
		if (currencys == null) {
			throw new RuntimeException("currencys is empty ");
		}
		this.currencys = currencys;
		return this;
	}

	public SalescontractQuery contractsum(Double contractsum) {
		if (contractsum == null) {
			throw new RuntimeException("contractsum is null");
		}
		this.contractsum = contractsum;
		return this;
	}

	public SalescontractQuery contractsumGreaterThanOrEqual(
			Double contractsumGreaterThanOrEqual) {
		if (contractsumGreaterThanOrEqual == null) {
			throw new RuntimeException("contractsum is null");
		}
		this.contractsumGreaterThanOrEqual = contractsumGreaterThanOrEqual;
		return this;
	}

	public SalescontractQuery contractsumLessThanOrEqual(
			Double contractsumLessThanOrEqual) {
		if (contractsumLessThanOrEqual == null) {
			throw new RuntimeException("contractsum is null");
		}
		this.contractsumLessThanOrEqual = contractsumLessThanOrEqual;
		return this;
	}

	public SalescontractQuery contractsums(List<Double> contractsums) {
		if (contractsums == null) {
			throw new RuntimeException("contractsums is empty ");
		}
		this.contractsums = contractsums;
		return this;
	}

	public SalescontractQuery paytype(Integer paytype) {
		if (paytype == null) {
			throw new RuntimeException("paytype is null");
		}
		this.paytype = paytype;
		return this;
	}

	public SalescontractQuery paytypeGreaterThanOrEqual(
			Integer paytypeGreaterThanOrEqual) {
		if (paytypeGreaterThanOrEqual == null) {
			throw new RuntimeException("paytype is null");
		}
		this.paytypeGreaterThanOrEqual = paytypeGreaterThanOrEqual;
		return this;
	}

	public SalescontractQuery paytypeLessThanOrEqual(
			Integer paytypeLessThanOrEqual) {
		if (paytypeLessThanOrEqual == null) {
			throw new RuntimeException("paytype is null");
		}
		this.paytypeLessThanOrEqual = paytypeLessThanOrEqual;
		return this;
	}

	public SalescontractQuery paytypes(List<Integer> paytypes) {
		if (paytypes == null) {
			throw new RuntimeException("paytypes is empty ");
		}
		this.paytypes = paytypes;
		return this;
	}

	public SalescontractQuery remarks(String remarks) {
		if (remarks == null) {
			throw new RuntimeException("remarks is null");
		}
		this.remarks = remarks;
		return this;
	}

	public SalescontractQuery remarksLike(String remarksLike) {
		if (remarksLike == null) {
			throw new RuntimeException("remarks is null");
		}
		this.remarksLike = remarksLike;
		return this;
	}

	public SalescontractQuery remarkss(List<String> remarkss) {
		if (remarkss == null) {
			throw new RuntimeException("remarkss is empty ");
		}
		this.remarkss = remarkss;
		return this;
	}

	public SalescontractQuery attachment(String attachment) {
		if (attachment == null) {
			throw new RuntimeException("attachment is null");
		}
		this.attachment = attachment;
		return this;
	}

	public SalescontractQuery attachmentLike(String attachmentLike) {
		if (attachmentLike == null) {
			throw new RuntimeException("attachment is null");
		}
		this.attachmentLike = attachmentLike;
		return this;
	}

	public SalescontractQuery attachments(List<String> attachments) {
		if (attachments == null) {
			throw new RuntimeException("attachments is empty ");
		}
		this.attachments = attachments;
		return this;
	}

	public SalescontractQuery status(Integer status) {
		if (status == null) {
			throw new RuntimeException("status is null");
		}
		this.status = status;
		return this;
	}

	public SalescontractQuery statusGreaterThanOrEqual(
			Integer statusGreaterThanOrEqual) {
		if (statusGreaterThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
		return this;
	}

	public SalescontractQuery statusLessThanOrEqual(
			Integer statusLessThanOrEqual) {
		if (statusLessThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusLessThanOrEqual = statusLessThanOrEqual;
		return this;
	}

	public SalescontractQuery statuss(List<Integer> statuss) {
		if (statuss == null) {
			throw new RuntimeException("statuss is empty ");
		}
		this.statuss = statuss;
		return this;
	}

	public SalescontractQuery appuser(String appuser) {
		if (appuser == null) {
			throw new RuntimeException("appuser is null");
		}
		this.appuser = appuser;
		return this;
	}

	public SalescontractQuery appuserLike(String appuserLike) {
		if (appuserLike == null) {
			throw new RuntimeException("appuser is null");
		}
		this.appuserLike = appuserLike;
		return this;
	}

	public SalescontractQuery appusers(List<String> appusers) {
		if (appusers == null) {
			throw new RuntimeException("appusers is empty ");
		}
		this.appusers = appusers;
		return this;
	}

	public SalescontractQuery appdate(Date appdate) {
		if (appdate == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdate = appdate;
		return this;
	}

	public SalescontractQuery appdateGreaterThanOrEqual(
			Date appdateGreaterThanOrEqual) {
		if (appdateGreaterThanOrEqual == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdateGreaterThanOrEqual = appdateGreaterThanOrEqual;
		return this;
	}

	public SalescontractQuery appdateLessThanOrEqual(Date appdateLessThanOrEqual) {
		if (appdateLessThanOrEqual == null) {
			throw new RuntimeException("appdate is null");
		}
		this.appdateLessThanOrEqual = appdateLessThanOrEqual;
		return this;
	}

	public SalescontractQuery appdates(List<Date> appdates) {
		if (appdates == null) {
			throw new RuntimeException("appdates is empty ");
		}
		this.appdates = appdates;
		return this;
	}

	public SalescontractQuery contractno(String contractno) {
		if (contractno == null) {
			throw new RuntimeException("contractno is null");
		}
		this.contractno = contractno;
		return this;
	}

	public SalescontractQuery contractnoLike(String contractnoLike) {
		if (contractnoLike == null) {
			throw new RuntimeException("contractno is null");
		}
		this.contractnoLike = contractnoLike;
		return this;
	}

	public SalescontractQuery contractnos(List<String> contractnos) {
		if (contractnos == null) {
			throw new RuntimeException("contractnos is empty ");
		}
		this.contractnos = contractnos;
		return this;
	}

	public SalescontractQuery processname(String processname) {
		if (processname == null) {
			throw new RuntimeException("processname is null");
		}
		this.processname = processname;
		return this;
	}

	public SalescontractQuery processnameLike(String processnameLike) {
		if (processnameLike == null) {
			throw new RuntimeException("processname is null");
		}
		this.processnameLike = processnameLike;
		return this;
	}

	public SalescontractQuery processnames(List<String> processnames) {
		if (processnames == null) {
			throw new RuntimeException("processnames is empty ");
		}
		this.processnames = processnames;
		return this;
	}

	public SalescontractQuery processinstanceid(Double processinstanceid) {
		if (processinstanceid == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceid = processinstanceid;
		return this;
	}

	public SalescontractQuery processinstanceidGreaterThanOrEqual(
			Double processinstanceidGreaterThanOrEqual) {
		if (processinstanceidGreaterThanOrEqual == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceidGreaterThanOrEqual = processinstanceidGreaterThanOrEqual;
		return this;
	}

	public SalescontractQuery processinstanceidLessThanOrEqual(
			Double processinstanceidLessThanOrEqual) {
		if (processinstanceidLessThanOrEqual == null) {
			throw new RuntimeException("processinstanceid is null");
		}
		this.processinstanceidLessThanOrEqual = processinstanceidLessThanOrEqual;
		return this;
	}

	public SalescontractQuery processinstanceids(List<Double> processinstanceids) {
		if (processinstanceids == null) {
			throw new RuntimeException("processinstanceids is empty ");
		}
		this.processinstanceids = processinstanceids;
		return this;
	}

	public SalescontractQuery wfstatus(Double wfstatus) {
		if (wfstatus == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatus = wfstatus;
		return this;
	}

	public SalescontractQuery wfstatusGreaterThanOrEqual(
			Double wfstatusGreaterThanOrEqual) {
		if (wfstatusGreaterThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusGreaterThanOrEqual = wfstatusGreaterThanOrEqual;
		return this;
	}

	public SalescontractQuery wfstatusLessThanOrEqual(
			Double wfstatusLessThanOrEqual) {
		if (wfstatusLessThanOrEqual == null) {
			throw new RuntimeException("wfstatus is null");
		}
		this.wfstatusLessThanOrEqual = wfstatusLessThanOrEqual;
		return this;
	}

	public SalescontractQuery wfstatuss(List<Double> wfstatuss) {
		if (wfstatuss == null) {
			throw new RuntimeException("wfstatuss is empty ");
		}
		this.wfstatuss = wfstatuss;
		return this;
	}

	public SalescontractQuery optionalsum(Double optionalsum) {
		if (optionalsum == null) {
			throw new RuntimeException("optionalsum is null");
		}
		this.optionalsum = optionalsum;
		return this;
	}

	public SalescontractQuery optionalsumGreaterThanOrEqual(
			Double optionalsumGreaterThanOrEqual) {
		if (optionalsumGreaterThanOrEqual == null) {
			throw new RuntimeException("optionalsum is null");
		}
		this.optionalsumGreaterThanOrEqual = optionalsumGreaterThanOrEqual;
		return this;
	}

	public SalescontractQuery optionalsumLessThanOrEqual(
			Double optionalsumLessThanOrEqual) {
		if (optionalsumLessThanOrEqual == null) {
			throw new RuntimeException("optionalsum is null");
		}
		this.optionalsumLessThanOrEqual = optionalsumLessThanOrEqual;
		return this;
	}

	public SalescontractQuery optionalsums(List<Double> optionalsums) {
		if (optionalsums == null) {
			throw new RuntimeException("optionalsums is empty ");
		}
		this.optionalsums = optionalsums;
		return this;
	}

	public SalescontractQuery firstpay(Double firstpay) {
		if (firstpay == null) {
			throw new RuntimeException("firstpay is null");
		}
		this.firstpay = firstpay;
		return this;
	}

	public SalescontractQuery firstpayGreaterThanOrEqual(
			Double firstpayGreaterThanOrEqual) {
		if (firstpayGreaterThanOrEqual == null) {
			throw new RuntimeException("firstpay is null");
		}
		this.firstpayGreaterThanOrEqual = firstpayGreaterThanOrEqual;
		return this;
	}

	public SalescontractQuery firstpayLessThanOrEqual(
			Double firstpayLessThanOrEqual) {
		if (firstpayLessThanOrEqual == null) {
			throw new RuntimeException("firstpay is null");
		}
		this.firstpayLessThanOrEqual = firstpayLessThanOrEqual;
		return this;
	}

	public SalescontractQuery firstpays(List<Double> firstpays) {
		if (firstpays == null) {
			throw new RuntimeException("firstpays is empty ");
		}
		this.firstpays = firstpays;
		return this;
	}

	public SalescontractQuery lastpay(Double lastpay) {
		if (lastpay == null) {
			throw new RuntimeException("lastpay is null");
		}
		this.lastpay = lastpay;
		return this;
	}

	public SalescontractQuery lastpayGreaterThanOrEqual(
			Double lastpayGreaterThanOrEqual) {
		if (lastpayGreaterThanOrEqual == null) {
			throw new RuntimeException("lastpay is null");
		}
		this.lastpayGreaterThanOrEqual = lastpayGreaterThanOrEqual;
		return this;
	}

	public SalescontractQuery lastpayLessThanOrEqual(
			Double lastpayLessThanOrEqual) {
		if (lastpayLessThanOrEqual == null) {
			throw new RuntimeException("lastpay is null");
		}
		this.lastpayLessThanOrEqual = lastpayLessThanOrEqual;
		return this;
	}

	public SalescontractQuery lastpays(List<Double> lastpays) {
		if (lastpays == null) {
			throw new RuntimeException("lastpays is empty ");
		}
		this.lastpays = lastpays;
		return this;
	}

	public SalescontractQuery discount(Double discount) {
		if (discount == null) {
			throw new RuntimeException("discount is null");
		}
		this.discount = discount;
		return this;
	}

	public SalescontractQuery discountGreaterThanOrEqual(
			Double discountGreaterThanOrEqual) {
		if (discountGreaterThanOrEqual == null) {
			throw new RuntimeException("discount is null");
		}
		this.discountGreaterThanOrEqual = discountGreaterThanOrEqual;
		return this;
	}

	public SalescontractQuery discountLessThanOrEqual(
			Double discountLessThanOrEqual) {
		if (discountLessThanOrEqual == null) {
			throw new RuntimeException("discount is null");
		}
		this.discountLessThanOrEqual = discountLessThanOrEqual;
		return this;
	}

	public SalescontractQuery discounts(List<Double> discounts) {
		if (discounts == null) {
			throw new RuntimeException("discounts is empty ");
		}
		this.discounts = discounts;
		return this;
	}

	public SalescontractQuery deliverydate(Date deliverydate) {
		if (deliverydate == null) {
			throw new RuntimeException("deliverydate is null");
		}
		this.deliverydate = deliverydate;
		return this;
	}

	public SalescontractQuery deliverydateGreaterThanOrEqual(
			Date deliverydateGreaterThanOrEqual) {
		if (deliverydateGreaterThanOrEqual == null) {
			throw new RuntimeException("deliverydate is null");
		}
		this.deliverydateGreaterThanOrEqual = deliverydateGreaterThanOrEqual;
		return this;
	}

	public SalescontractQuery deliverydateLessThanOrEqual(
			Date deliverydateLessThanOrEqual) {
		if (deliverydateLessThanOrEqual == null) {
			throw new RuntimeException("deliverydate is null");
		}
		this.deliverydateLessThanOrEqual = deliverydateLessThanOrEqual;
		return this;
	}

	public SalescontractQuery deliverydates(List<Date> deliverydates) {
		if (deliverydates == null) {
			throw new RuntimeException("deliverydates is empty ");
		}
		this.deliverydates = deliverydates;
		return this;
	}

	public SalescontractQuery sales(String sales) {
		if (sales == null) {
			throw new RuntimeException("sales is null");
		}
		this.sales = sales;
		return this;
	}

	public SalescontractQuery salesLike(String salesLike) {
		if (salesLike == null) {
			throw new RuntimeException("sales is null");
		}
		this.salesLike = salesLike;
		return this;
	}

	public SalescontractQuery saless(List<String> saless) {
		if (saless == null) {
			throw new RuntimeException("saless is empty ");
		}
		this.saless = saless;
		return this;
	}

	public SalescontractQuery contractsales(String contractsales) {
		if (contractsales == null) {
			throw new RuntimeException("contractsales is null");
		}
		this.contractsales = contractsales;
		return this;
	}

	public SalescontractQuery contractsalesLike(String contractsalesLike) {
		if (contractsalesLike == null) {
			throw new RuntimeException("contractsales is null");
		}
		this.contractsalesLike = contractsalesLike;
		return this;
	}

	public SalescontractQuery contractsaless(List<String> contractsaless) {
		if (contractsaless == null) {
			throw new RuntimeException("contractsaless is empty ");
		}
		this.contractsaless = contractsaless;
		return this;
	}

	public SalescontractQuery giftsum(Double giftsum) {
		if (giftsum == null) {
			throw new RuntimeException("giftsum is null");
		}
		this.giftsum = giftsum;
		return this;
	}

	public SalescontractQuery giftsumGreaterThanOrEqual(
			Double giftsumGreaterThanOrEqual) {
		if (giftsumGreaterThanOrEqual == null) {
			throw new RuntimeException("giftsum is null");
		}
		this.giftsumGreaterThanOrEqual = giftsumGreaterThanOrEqual;
		return this;
	}

	public SalescontractQuery giftsumLessThanOrEqual(
			Double giftsumLessThanOrEqual) {
		if (giftsumLessThanOrEqual == null) {
			throw new RuntimeException("giftsum is null");
		}
		this.giftsumLessThanOrEqual = giftsumLessThanOrEqual;
		return this;
	}

	public SalescontractQuery giftsums(List<Double> giftsums) {
		if (giftsums == null) {
			throw new RuntimeException("giftsums is empty ");
		}
		this.giftsums = giftsums;
		return this;
	}

	public SalescontractQuery giftremark(String giftremark) {
		if (giftremark == null) {
			throw new RuntimeException("giftremark is null");
		}
		this.giftremark = giftremark;
		return this;
	}

	public SalescontractQuery giftremarkLike(String giftremarkLike) {
		if (giftremarkLike == null) {
			throw new RuntimeException("giftremark is null");
		}
		this.giftremarkLike = giftremarkLike;
		return this;
	}

	public SalescontractQuery giftremarks(List<String> giftremarks) {
		if (giftremarks == null) {
			throw new RuntimeException("giftremarks is empty ");
		}
		this.giftremarks = giftremarks;
		return this;
	}

	public SalescontractQuery remark(String remark) {
		if (remark == null) {
			throw new RuntimeException("remark is null");
		}
		this.remark = remark;
		return this;
	}

	public SalescontractQuery remarkLike(String remarkLike) {
		if (remarkLike == null) {
			throw new RuntimeException("remark is null");
		}
		this.remarkLike = remarkLike;
		return this;
	}

	public SalescontractQuery remarks(List<String> remarkes) {
		if (remarks == null) {
			throw new RuntimeException("remarks is empty ");
		}
		this.remarkes = remarkes;
		return this;
	}

	public SalescontractQuery company(String company) {
		if (company == null) {
			throw new RuntimeException("company is null");
		}
		this.company = company;
		return this;
	}

	public SalescontractQuery companyLike(String companyLike) {
		if (companyLike == null) {
			throw new RuntimeException("company is null");
		}
		this.companyLike = companyLike;
		return this;
	}

	public SalescontractQuery companys(List<String> companys) {
		if (companys == null) {
			throw new RuntimeException("companys is empty ");
		}
		this.companys = companys;
		return this;
	}

	public SalescontractQuery createby(String createby) {
		if (createby == null) {
			throw new RuntimeException("createby is null");
		}
		this.createby = createby;
		return this;
	}

	public SalescontractQuery createbyLike(String createbyLike) {
		if (createbyLike == null) {
			throw new RuntimeException("createby is null");
		}
		this.createbyLike = createbyLike;
		return this;
	}

	public SalescontractQuery createbys(List<String> createbys) {
		if (createbys == null) {
			throw new RuntimeException("createbys is empty ");
		}
		this.createbys = createbys;
		return this;
	}

	public SalescontractQuery createdate(Date createdate) {
		if (createdate == null) {
			throw new RuntimeException("createdate is null");
		}
		this.createdate = createdate;
		return this;
	}

	public SalescontractQuery createdateGreaterThanOrEqual(
			Date createdateGreaterThanOrEqual) {
		if (createdateGreaterThanOrEqual == null) {
			throw new RuntimeException("createdate is null");
		}
		this.createdateGreaterThanOrEqual = createdateGreaterThanOrEqual;
		return this;
	}

	public SalescontractQuery createdateLessThanOrEqual(
			Date createdateLessThanOrEqual) {
		if (createdateLessThanOrEqual == null) {
			throw new RuntimeException("createdate is null");
		}
		this.createdateLessThanOrEqual = createdateLessThanOrEqual;
		return this;
	}

	public SalescontractQuery createdates(List<Date> createdates) {
		if (createdates == null) {
			throw new RuntimeException("createdates is empty ");
		}
		this.createdates = createdates;
		return this;
	}

	public SalescontractQuery updatedate(Date updatedate) {
		if (updatedate == null) {
			throw new RuntimeException("updatedate is null");
		}
		this.updatedate = updatedate;
		return this;
	}

	public SalescontractQuery updatedateGreaterThanOrEqual(
			Date updatedateGreaterThanOrEqual) {
		if (updatedateGreaterThanOrEqual == null) {
			throw new RuntimeException("updatedate is null");
		}
		this.updatedateGreaterThanOrEqual = updatedateGreaterThanOrEqual;
		return this;
	}

	public SalescontractQuery updatedateLessThanOrEqual(
			Date updatedateLessThanOrEqual) {
		if (updatedateLessThanOrEqual == null) {
			throw new RuntimeException("updatedate is null");
		}
		this.updatedateLessThanOrEqual = updatedateLessThanOrEqual;
		return this;
	}

	public SalescontractQuery updatedates(List<Date> updatedates) {
		if (updatedates == null) {
			throw new RuntimeException("updatedates is empty ");
		}
		this.updatedates = updatedates;
		return this;
	}

	public SalescontractQuery updateby(String updateby) {
		if (updateby == null) {
			throw new RuntimeException("updateby is null");
		}
		this.updateby = updateby;
		return this;
	}

	public SalescontractQuery updatebyLike(String updatebyLike) {
		if (updatebyLike == null) {
			throw new RuntimeException("updateby is null");
		}
		this.updatebyLike = updatebyLike;
		return this;
	}

	public SalescontractQuery updatebys(List<String> updatebys) {
		if (updatebys == null) {
			throw new RuntimeException("updatebys is empty ");
		}
		this.updatebys = updatebys;
		return this;
	}

	public SalescontractQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public SalescontractQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public SalescontractQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public SalescontractQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public SalescontractQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public SalescontractQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public SalescontractQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public SalescontractQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public SalescontractQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public SalescontractQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public SalescontractQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public SalescontractQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public SalescontractQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public SalescontractQuery updateBys(List<String> updateBys) {
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

			if ("brands".equals(sortColumn)) {
				orderBy = "E.brands" + a_x;
			}

			if ("cartype".equals(sortColumn)) {
				orderBy = "E.cartype" + a_x;
			}

			if ("factoryno".equals(sortColumn)) {
				orderBy = "E.factoryno" + a_x;
			}

			if ("bodycolor".equals(sortColumn)) {
				orderBy = "E.bodycolor" + a_x;
			}

			if ("carpetcolor".equals(sortColumn)) {
				orderBy = "E.carpetcolor" + a_x;
			}

			if ("interiorcolor".equals(sortColumn)) {
				orderBy = "E.interiorcolor" + a_x;
			}

			if ("optionalsum".equals(sortColumn)) {
				orderBy = "E.optionalsum" + a_x;
			}

			if ("firstpay".equals(sortColumn)) {
				orderBy = "E.firstpay" + a_x;
			}

			if ("lastpay".equals(sortColumn)) {
				orderBy = "E.lastpay" + a_x;
			}

			if ("standardmsrp".equals(sortColumn)) {
				orderBy = "E.standardmsrp" + a_x;
			}

			if ("carmsrp".equals(sortColumn)) {
				orderBy = "E.carmsrp" + a_x;
			}

			if ("discount".equals(sortColumn)) {
				orderBy = "E.discount" + a_x;
			}

			if ("deliverydate".equals(sortColumn)) {
				orderBy = "E.deliverydate" + a_x;
			}

			if ("sales".equals(sortColumn)) {
				orderBy = "E.sales" + a_x;
			}

			if ("contractsales".equals(sortColumn)) {
				orderBy = "E.contractsales" + a_x;
			}

			if ("giftsum".equals(sortColumn)) {
				orderBy = "E.giftsum" + a_x;
			}

			if ("giftremark".equals(sortColumn)) {
				orderBy = "E.giftremark" + a_x;
			}

			if ("remark".equals(sortColumn)) {
				orderBy = "E.remark" + a_x;
			}

			if ("area".equals(sortColumn)) {
				orderBy = "E.area" + a_x;
			}

			if ("company".equals(sortColumn)) {
				orderBy = "E.company" + a_x;
			}

			if ("createby".equals(sortColumn)) {
				orderBy = "E.createby" + a_x;
			}

			if ("createdate".equals(sortColumn)) {
				orderBy = "E.createdate" + a_x;
			}

			if ("updatedate".equals(sortColumn)) {
				orderBy = "E.updatedate" + a_x;
			}

			if ("updateby".equals(sortColumn)) {
				orderBy = "E.updateby" + a_x;
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
		addColumn("brands", "brands");
		addColumn("cartype", "cartype");
		addColumn("factoryno", "factoryno");
		addColumn("bodycolor", "bodycolor");
		addColumn("carpetcolor", "carpetcolor");
		addColumn("interiorcolor", "interiorcolor");
		addColumn("optionalsum", "optionalsum");
		addColumn("firstpay", "firstpay");
		addColumn("lastpay", "lastpay");
		addColumn("standardmsrp", "standardmsrp");
		addColumn("carmsrp", "carmsrp");
		addColumn("discount", "discount");
		addColumn("deliverydate", "deliverydate");
		addColumn("sales", "sales");
		addColumn("contractsales", "contractsales");
		addColumn("giftsum", "giftsum");
		addColumn("giftremark", "giftremark");
		addColumn("remark", "remark");
		addColumn("area", "area");
		addColumn("company", "company");
		addColumn("createby", "createby");
		addColumn("createdate", "createdate");
		addColumn("updatedate", "updatedate");
		addColumn("updateby", "updateby");
		addColumn("createBy", "createBy");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("updateBy", "updateBy");
	}

}