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

package com.glaf.base.modules.sys.query;

import java.util.*;

import com.glaf.core.query.DataQuery;

public class SysUserQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String account;
	protected String accountLike;
	protected List<String> accounts;
	protected String code;
	protected String codeLike;
	protected List<String> codes;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected Integer blocked;
	protected Integer blockedGreaterThanOrEqual;
	protected Integer blockedLessThanOrEqual;
	protected List<Integer> blockeds;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;
	protected Date lastLoginTimeGreaterThanOrEqual;
	protected Date lastLoginTimeLessThanOrEqual;
	protected String lastLoginIP;
	protected String lastLoginIPLike;
	protected List<String> lastLoginIPs;
	protected Integer evection;
	protected Integer evectionGreaterThanOrEqual;
	protected Integer evectionLessThanOrEqual;
	protected List<Integer> evections;
	protected String mobile;
	protected String mobileLike;
	protected String email;
	protected String emailLike;
	protected String telephone;
	protected String telephoneLike;
	protected Integer gender;
	protected Integer genderGreaterThanOrEqual;
	protected Integer genderLessThanOrEqual;
	protected String headship;
	protected String headshipLike;
	protected Integer userType;
	protected Integer userTypeGreaterThanOrEqual;
	protected Integer userTypeLessThanOrEqual;
	protected List<Integer> userTypes;
	protected String fax;
	protected String faxLike;
	protected Integer accountType;
	protected Integer accountTypeGreaterThanOrEqual;
	protected Integer accountTypeLessThanOrEqual;
	protected List<Integer> accountTypes;
	protected Integer dumpFlag;
	protected Integer dumpFlagGreaterThanOrEqual;
	protected Integer dumpFlagLessThanOrEqual;
	protected List<Integer> dumpFlags;
	protected Long deptId;
	protected List<Long> deptIds;
	protected String adminFlag;
	protected List<String> adminFlags;
	protected String superiorIdsLike;
	protected Date availDateStartGreaterThanOrEqual;
	protected Date availDateStartLessThanOrEqual;
	protected Date availDateEndGreaterThanOrEqual;
	protected Date availDateEndLessThanOrEqual;

	public SysUserQuery() {

	}

	public SysUserQuery account(String account) {
		if (account == null) {
			throw new RuntimeException("account is null");
		}
		this.account = account;
		return this;
	}

	public SysUserQuery accountLike(String accountLike) {
		if (accountLike == null) {
			throw new RuntimeException("account is null");
		}
		this.accountLike = accountLike;
		return this;
	}

	public SysUserQuery accounts(List<String> accounts) {
		if (accounts == null) {
			throw new RuntimeException("accounts is empty ");
		}
		this.accounts = accounts;
		return this;
	}

	public SysUserQuery accountType(Integer accountType) {
		if (accountType == null) {
			throw new RuntimeException("accountType is null");
		}
		this.accountType = accountType;
		return this;
	}

	public SysUserQuery accountTypeGreaterThanOrEqual(
			Integer accountTypeGreaterThanOrEqual) {
		if (accountTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("accountType is null");
		}
		this.accountTypeGreaterThanOrEqual = accountTypeGreaterThanOrEqual;
		return this;
	}

	public SysUserQuery accountTypeLessThanOrEqual(
			Integer accountTypeLessThanOrEqual) {
		if (accountTypeLessThanOrEqual == null) {
			throw new RuntimeException("accountType is null");
		}
		this.accountTypeLessThanOrEqual = accountTypeLessThanOrEqual;
		return this;
	}

	public SysUserQuery accountTypes(List<Integer> accountTypes) {
		if (accountTypes == null) {
			throw new RuntimeException("accountTypes is empty ");
		}
		this.accountTypes = accountTypes;
		return this;
	}

	public SysUserQuery adminFlag(String adminFlag) {
		if (adminFlag == null) {
			throw new RuntimeException("adminFlag is null");
		}
		this.adminFlag = adminFlag;
		return this;
	}

	public SysUserQuery adminFlags(List<String> adminFlags) {
		if (adminFlags == null) {
			throw new RuntimeException("adminFlags is empty ");
		}
		this.adminFlags = adminFlags;
		return this;
	}

	public SysUserQuery blocked(Integer blocked) {
		if (blocked == null) {
			throw new RuntimeException("blocked is null");
		}
		this.blocked = blocked;
		return this;
	}

	public SysUserQuery blockedGreaterThanOrEqual(
			Integer blockedGreaterThanOrEqual) {
		if (blockedGreaterThanOrEqual == null) {
			throw new RuntimeException("blocked is null");
		}
		this.blockedGreaterThanOrEqual = blockedGreaterThanOrEqual;
		return this;
	}

	public SysUserQuery blockedLessThanOrEqual(Integer blockedLessThanOrEqual) {
		if (blockedLessThanOrEqual == null) {
			throw new RuntimeException("blocked is null");
		}
		this.blockedLessThanOrEqual = blockedLessThanOrEqual;
		return this;
	}

	public SysUserQuery blockeds(List<Integer> blockeds) {
		if (blockeds == null) {
			throw new RuntimeException("blockeds is empty ");
		}
		this.blockeds = blockeds;
		return this;
	}

	public SysUserQuery code(String code) {
		if (code == null) {
			throw new RuntimeException("code is null");
		}
		this.code = code;
		return this;
	}

	public SysUserQuery codeLike(String codeLike) {
		if (codeLike == null) {
			throw new RuntimeException("code is null");
		}
		this.codeLike = codeLike;
		return this;
	}

	public SysUserQuery codes(List<String> codes) {
		if (codes == null) {
			throw new RuntimeException("codes is empty ");
		}
		this.codes = codes;
		return this;
	}

	public SysUserQuery createTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public SysUserQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public SysUserQuery deptId(Long deptId) {
		if (deptId == null) {
			throw new RuntimeException("deptId is null");
		}
		this.deptId = deptId;
		return this;
	}

	public SysUserQuery deptIds(List<Long> deptIds) {
		if (deptIds == null) {
			throw new RuntimeException("deptIds is empty ");
		}
		this.deptIds = deptIds;
		return this;
	}

	public SysUserQuery dumpFlag(Integer dumpFlag) {
		if (dumpFlag == null) {
			throw new RuntimeException("dumpFlag is null");
		}
		this.dumpFlag = dumpFlag;
		return this;
	}

	public SysUserQuery dumpFlagGreaterThanOrEqual(
			Integer dumpFlagGreaterThanOrEqual) {
		if (dumpFlagGreaterThanOrEqual == null) {
			throw new RuntimeException("dumpFlag is null");
		}
		this.dumpFlagGreaterThanOrEqual = dumpFlagGreaterThanOrEqual;
		return this;
	}

	public SysUserQuery dumpFlagLessThanOrEqual(Integer dumpFlagLessThanOrEqual) {
		if (dumpFlagLessThanOrEqual == null) {
			throw new RuntimeException("dumpFlag is null");
		}
		this.dumpFlagLessThanOrEqual = dumpFlagLessThanOrEqual;
		return this;
	}

	public SysUserQuery dumpFlags(List<Integer> dumpFlags) {
		if (dumpFlags == null) {
			throw new RuntimeException("dumpFlags is empty ");
		}
		this.dumpFlags = dumpFlags;
		return this;
	}

	public SysUserQuery email(String email) {
		if (email == null) {
			throw new RuntimeException("email is null");
		}
		this.email = email;
		return this;
	}

	public SysUserQuery emailLike(String emailLike) {
		if (emailLike == null) {
			throw new RuntimeException("email is null");
		}
		this.emailLike = emailLike;
		return this;
	}

	public SysUserQuery evection(Integer evection) {
		if (evection == null) {
			throw new RuntimeException("evection is null");
		}
		this.evection = evection;
		return this;
	}

	public SysUserQuery evectionGreaterThanOrEqual(
			Integer evectionGreaterThanOrEqual) {
		if (evectionGreaterThanOrEqual == null) {
			throw new RuntimeException("evection is null");
		}
		this.evectionGreaterThanOrEqual = evectionGreaterThanOrEqual;
		return this;
	}

	public SysUserQuery evectionLessThanOrEqual(Integer evectionLessThanOrEqual) {
		if (evectionLessThanOrEqual == null) {
			throw new RuntimeException("evection is null");
		}
		this.evectionLessThanOrEqual = evectionLessThanOrEqual;
		return this;
	}

	public SysUserQuery evections(List<Integer> evections) {
		if (evections == null) {
			throw new RuntimeException("evections is empty ");
		}
		this.evections = evections;
		return this;
	}

	public SysUserQuery fax(String fax) {
		if (fax == null) {
			throw new RuntimeException("fax is null");
		}
		this.fax = fax;
		return this;
	}

	public SysUserQuery faxLike(String faxLike) {
		if (faxLike == null) {
			throw new RuntimeException("fax is null");
		}
		this.faxLike = faxLike;
		return this;
	}

	public SysUserQuery gender(Integer gender) {
		if (gender == null) {
			throw new RuntimeException("gender is null");
		}
		this.gender = gender;
		return this;
	}

	public SysUserQuery genderGreaterThanOrEqual(
			Integer genderGreaterThanOrEqual) {
		if (genderGreaterThanOrEqual == null) {
			throw new RuntimeException("gender is null");
		}
		this.genderGreaterThanOrEqual = genderGreaterThanOrEqual;
		return this;
	}

	public SysUserQuery genderLessThanOrEqual(Integer genderLessThanOrEqual) {
		if (genderLessThanOrEqual == null) {
			throw new RuntimeException("gender is null");
		}
		this.genderLessThanOrEqual = genderLessThanOrEqual;
		return this;
	}

	public String getAccount() {
		return account;
	}

	public String getAccountLike() {
		if (accountLike != null && accountLike.trim().length() > 0) {
			if (!accountLike.startsWith("%")) {
				accountLike = "%" + accountLike;
			}
			if (!accountLike.endsWith("%")) {
				accountLike = accountLike + "%";
			}
		}
		return accountLike;
	}

	public List<String> getAccounts() {
		return accounts;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public Integer getAccountTypeGreaterThanOrEqual() {
		return accountTypeGreaterThanOrEqual;
	}

	public Integer getAccountTypeLessThanOrEqual() {
		return accountTypeLessThanOrEqual;
	}

	public List<Integer> getAccountTypes() {
		return accountTypes;
	}

	public String getAdminFlag() {
		return adminFlag;
	}

	public List<String> getAdminFlags() {
		return adminFlags;
	}

	public Date getAvailDateEndGreaterThanOrEqual() {
		return availDateEndGreaterThanOrEqual;
	}

	public Date getAvailDateEndLessThanOrEqual() {
		return availDateEndLessThanOrEqual;
	}

	public Date getAvailDateStartGreaterThanOrEqual() {
		return availDateStartGreaterThanOrEqual;
	}

	public Date getAvailDateStartLessThanOrEqual() {
		return availDateStartLessThanOrEqual;
	}

	public Integer getBlocked() {
		return blocked;
	}

	public Integer getBlockedGreaterThanOrEqual() {
		return blockedGreaterThanOrEqual;
	}

	public Integer getBlockedLessThanOrEqual() {
		return blockedLessThanOrEqual;
	}

	public List<Integer> getBlockeds() {
		return blockeds;
	}

	public String getCode() {
		return code;
	}

	public String getCodeLike() {
		if (codeLike != null && codeLike.trim().length() > 0) {
			if (!codeLike.startsWith("%")) {
				codeLike = "%" + codeLike;
			}
			if (!codeLike.endsWith("%")) {
				codeLike = codeLike + "%";
			}
		}
		return codeLike;
	}

	public List<String> getCodes() {
		return codes;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public Long getDeptId() {
		return deptId;
	}

	public List<Long> getDeptIds() {
		return deptIds;
	}

	public Integer getDumpFlag() {
		return dumpFlag;
	}

	public Integer getDumpFlagGreaterThanOrEqual() {
		return dumpFlagGreaterThanOrEqual;
	}

	public Integer getDumpFlagLessThanOrEqual() {
		return dumpFlagLessThanOrEqual;
	}

	public List<Integer> getDumpFlags() {
		return dumpFlags;
	}

	public String getEmail() {
		return email;
	}

	public String getEmailLike() {
		if (emailLike != null && emailLike.trim().length() > 0) {
			if (!emailLike.startsWith("%")) {
				emailLike = "%" + emailLike;
			}
			if (!emailLike.endsWith("%")) {
				emailLike = emailLike + "%";
			}
		}
		return emailLike;
	}

	public Integer getEvection() {
		return evection;
	}

	public Integer getEvectionGreaterThanOrEqual() {
		return evectionGreaterThanOrEqual;
	}

	public Integer getEvectionLessThanOrEqual() {
		return evectionLessThanOrEqual;
	}

	public List<Integer> getEvections() {
		return evections;
	}

	public String getFax() {
		return fax;
	}

	public String getFaxLike() {
		if (faxLike != null && faxLike.trim().length() > 0) {
			if (!faxLike.startsWith("%")) {
				faxLike = "%" + faxLike;
			}
			if (!faxLike.endsWith("%")) {
				faxLike = faxLike + "%";
			}
		}
		return faxLike;
	}

	public Integer getGender() {
		return gender;
	}

	public Integer getGenderGreaterThanOrEqual() {
		return genderGreaterThanOrEqual;
	}

	public Integer getGenderLessThanOrEqual() {
		return genderLessThanOrEqual;
	}

	public String getHeadship() {
		return headship;
	}

	public String getHeadshipLike() {
		if (headshipLike != null && headshipLike.trim().length() > 0) {
			if (!headshipLike.startsWith("%")) {
				headshipLike = "%" + headshipLike;
			}
			if (!headshipLike.endsWith("%")) {
				headshipLike = headshipLike + "%";
			}
		}
		return headshipLike;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public String getLastLoginIPLike() {
		if (lastLoginIPLike != null && lastLoginIPLike.trim().length() > 0) {
			if (!lastLoginIPLike.startsWith("%")) {
				lastLoginIPLike = "%" + lastLoginIPLike;
			}
			if (!lastLoginIPLike.endsWith("%")) {
				lastLoginIPLike = lastLoginIPLike + "%";
			}
		}
		return lastLoginIPLike;
	}

	public List<String> getLastLoginIPs() {
		return lastLoginIPs;
	}

	public Date getLastLoginTimeGreaterThanOrEqual() {
		return lastLoginTimeGreaterThanOrEqual;
	}

	public Date getLastLoginTimeLessThanOrEqual() {
		return lastLoginTimeLessThanOrEqual;
	}

	public String getMobile() {
		return mobile;
	}

	public String getMobileLike() {
		if (mobileLike != null && mobileLike.trim().length() > 0) {
			if (!mobileLike.startsWith("%")) {
				mobileLike = "%" + mobileLike;
			}
			if (!mobileLike.endsWith("%")) {
				mobileLike = mobileLike + "%";
			}
		}
		return mobileLike;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
		if (nameLike != null && nameLike.trim().length() > 0) {
			if (!nameLike.startsWith("%")) {
				nameLike = "%" + nameLike;
			}
			if (!nameLike.endsWith("%")) {
				nameLike = nameLike + "%";
			}
		}
		return nameLike;
	}

	public List<String> getNames() {
		return names;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("account".equals(sortColumn)) {
				orderBy = "E.ACCOUNT" + a_x;
			}

			if ("password".equals(sortColumn)) {
				orderBy = "E.PASSWORD" + a_x;
			}

			if ("code".equals(sortColumn)) {
				orderBy = "E.CODE" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME" + a_x;
			}

			if ("blocked".equals(sortColumn)) {
				orderBy = "E.BLOCKED" + a_x;
			}

			if ("createTime".equals(sortColumn)) {
				orderBy = "E.CREATETIME" + a_x;
			}

			if ("lastLoginTime".equals(sortColumn)) {
				orderBy = "E.LASTLOGINTIME" + a_x;
			}

			if ("lastLoginIP".equals(sortColumn)) {
				orderBy = "E.LASTLOGINIP" + a_x;
			}

			if ("evection".equals(sortColumn)) {
				orderBy = "E.EVECTION" + a_x;
			}

			if ("mobile".equals(sortColumn)) {
				orderBy = "E.MOBILE" + a_x;
			}

			if ("email".equals(sortColumn)) {
				orderBy = "E.EMAIL" + a_x;
			}

			if ("telephone".equals(sortColumn)) {
				orderBy = "E.TELEPHONE" + a_x;
			}

			if ("gender".equals(sortColumn)) {
				orderBy = "E.GENDER" + a_x;
			}

			if ("headship".equals(sortColumn)) {
				orderBy = "E.HEADSHIP" + a_x;
			}

			if ("userType".equals(sortColumn)) {
				orderBy = "E.USERTYPE" + a_x;
			}

			if ("fax".equals(sortColumn)) {
				orderBy = "E.FAX" + a_x;
			}

			if ("accountType".equals(sortColumn)) {
				orderBy = "E.ACCOUNTTYPE" + a_x;
			}

			if ("dumpFlag".equals(sortColumn)) {
				orderBy = "E.DUMPFLAG" + a_x;
			}

			if ("deptId".equals(sortColumn)) {
				orderBy = "E.DEPTID" + a_x;
			}

			if ("adminFlag".equals(sortColumn)) {
				orderBy = "E.ADMINFLAG" + a_x;
			}

			if ("superiorIds".equals(sortColumn)) {
				orderBy = "E.SUPERIORIDS" + a_x;
			}

		}
		return orderBy;
	}

	public String getSuperiorIdsLike() {
		if (superiorIdsLike != null && superiorIdsLike.trim().length() > 0) {
			if (!superiorIdsLike.startsWith("%")) {
				superiorIdsLike = "%" + superiorIdsLike;
			}
			if (!superiorIdsLike.endsWith("%")) {
				superiorIdsLike = superiorIdsLike + "%";
			}
		}
		return superiorIdsLike;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getTelephoneLike() {
		if (telephoneLike != null && telephoneLike.trim().length() > 0) {
			if (!telephoneLike.startsWith("%")) {
				telephoneLike = "%" + telephoneLike;
			}
			if (!telephoneLike.endsWith("%")) {
				telephoneLike = telephoneLike + "%";
			}
		}
		return telephoneLike;
	}

	public Integer getUserType() {
		return userType;
	}

	public Integer getUserTypeGreaterThanOrEqual() {
		return userTypeGreaterThanOrEqual;
	}

	public Integer getUserTypeLessThanOrEqual() {
		return userTypeLessThanOrEqual;
	}

	public List<Integer> getUserTypes() {
		return userTypes;
	}

	public SysUserQuery headship(String headship) {
		if (headship == null) {
			throw new RuntimeException("headship is null");
		}
		this.headship = headship;
		return this;
	}

	public SysUserQuery headshipLike(String headshipLike) {
		if (headshipLike == null) {
			throw new RuntimeException("headship is null");
		}
		this.headshipLike = headshipLike;
		return this;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("account", "ACCOUNT");
		addColumn("code", "CODE");
		addColumn("name", "NAME");
		addColumn("blocked", "BLOCKED");
		addColumn("createTime", "CREATETIME");
		addColumn("lastLoginTime", "LASTLOGINTIME");
		addColumn("lastLoginIP", "LASTLOGINIP");
		addColumn("evection", "EVECTION");
		addColumn("mobile", "MOBILE");
		addColumn("email", "EMAIL");
		addColumn("telephone", "TELEPHONE");
		addColumn("gender", "GENDER");
		addColumn("headship", "HEADSHIP");
		addColumn("userType", "USERTYPE");
		addColumn("fax", "FAX");
		addColumn("accountType", "ACCOUNTTYPE");
		addColumn("dumpFlag", "DUMPFLAG");
		addColumn("deptId", "DEPTID");
		addColumn("adminFlag", "ADMINFLAG");
		addColumn("superiorIds", "SUPERIORIDS");
	}

	public SysUserQuery lastLoginIP(String lastLoginIP) {
		if (lastLoginIP == null) {
			throw new RuntimeException("lastLoginIP is null");
		}
		this.lastLoginIP = lastLoginIP;
		return this;
	}

	public SysUserQuery lastLoginIPLike(String lastLoginIPLike) {
		if (lastLoginIPLike == null) {
			throw new RuntimeException("lastLoginIP is null");
		}
		this.lastLoginIPLike = lastLoginIPLike;
		return this;
	}

	public SysUserQuery lastLoginIPs(List<String> lastLoginIPs) {
		if (lastLoginIPs == null) {
			throw new RuntimeException("lastLoginIPs is empty ");
		}
		this.lastLoginIPs = lastLoginIPs;
		return this;
	}

	public SysUserQuery lastLoginTimeGreaterThanOrEqual(
			Date lastLoginTimeGreaterThanOrEqual) {
		if (lastLoginTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("lastLoginTime is null");
		}
		this.lastLoginTimeGreaterThanOrEqual = lastLoginTimeGreaterThanOrEqual;
		return this;
	}

	public SysUserQuery lastLoginTimeLessThanOrEqual(
			Date lastLoginTimeLessThanOrEqual) {
		if (lastLoginTimeLessThanOrEqual == null) {
			throw new RuntimeException("lastLoginTime is null");
		}
		this.lastLoginTimeLessThanOrEqual = lastLoginTimeLessThanOrEqual;
		return this;
	}

	public SysUserQuery mobile(String mobile) {
		if (mobile == null) {
			throw new RuntimeException("mobile is null");
		}
		this.mobile = mobile;
		return this;
	}

	public SysUserQuery mobileLike(String mobileLike) {
		if (mobileLike == null) {
			throw new RuntimeException("mobile is null");
		}
		this.mobileLike = mobileLike;
		return this;
	}

	public SysUserQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public SysUserQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public SysUserQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setAccountLike(String accountLike) {
		this.accountLike = accountLike;
	}

	public void setAccounts(List<String> accounts) {
		this.accounts = accounts;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public void setAccountTypeGreaterThanOrEqual(
			Integer accountTypeGreaterThanOrEqual) {
		this.accountTypeGreaterThanOrEqual = accountTypeGreaterThanOrEqual;
	}

	public void setAccountTypeLessThanOrEqual(Integer accountTypeLessThanOrEqual) {
		this.accountTypeLessThanOrEqual = accountTypeLessThanOrEqual;
	}

	public void setAccountTypes(List<Integer> accountTypes) {
		this.accountTypes = accountTypes;
	}

	public void setAdminFlag(String adminFlag) {
		this.adminFlag = adminFlag;
	}

	public void setAdminFlags(List<String> adminFlags) {
		this.adminFlags = adminFlags;
	}

	public void setAvailDateEndGreaterThanOrEqual(
			Date availDateEndGreaterThanOrEqual) {
		this.availDateEndGreaterThanOrEqual = availDateEndGreaterThanOrEqual;
	}

	public void setAvailDateEndLessThanOrEqual(Date availDateEndLessThanOrEqual) {
		this.availDateEndLessThanOrEqual = availDateEndLessThanOrEqual;
	}

	public void setAvailDateStartGreaterThanOrEqual(
			Date availDateStartGreaterThanOrEqual) {
		this.availDateStartGreaterThanOrEqual = availDateStartGreaterThanOrEqual;
	}

	public void setAvailDateStartLessThanOrEqual(
			Date availDateStartLessThanOrEqual) {
		this.availDateStartLessThanOrEqual = availDateStartLessThanOrEqual;
	}

	public void setBlocked(Integer blocked) {
		this.blocked = blocked;
	}

	public void setBlockedGreaterThanOrEqual(Integer blockedGreaterThanOrEqual) {
		this.blockedGreaterThanOrEqual = blockedGreaterThanOrEqual;
	}

	public void setBlockedLessThanOrEqual(Integer blockedLessThanOrEqual) {
		this.blockedLessThanOrEqual = blockedLessThanOrEqual;
	}

	public void setBlockeds(List<Integer> blockeds) {
		this.blockeds = blockeds;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public void setCreateTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public void setDeptIds(List<Long> deptIds) {
		this.deptIds = deptIds;
	}

	public void setDumpFlag(Integer dumpFlag) {
		this.dumpFlag = dumpFlag;
	}

	public void setDumpFlagGreaterThanOrEqual(Integer dumpFlagGreaterThanOrEqual) {
		this.dumpFlagGreaterThanOrEqual = dumpFlagGreaterThanOrEqual;
	}

	public void setDumpFlagLessThanOrEqual(Integer dumpFlagLessThanOrEqual) {
		this.dumpFlagLessThanOrEqual = dumpFlagLessThanOrEqual;
	}

	public void setDumpFlags(List<Integer> dumpFlags) {
		this.dumpFlags = dumpFlags;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEmailLike(String emailLike) {
		this.emailLike = emailLike;
	}

	public void setEvection(Integer evection) {
		this.evection = evection;
	}

	public void setEvectionGreaterThanOrEqual(Integer evectionGreaterThanOrEqual) {
		this.evectionGreaterThanOrEqual = evectionGreaterThanOrEqual;
	}

	public void setEvectionLessThanOrEqual(Integer evectionLessThanOrEqual) {
		this.evectionLessThanOrEqual = evectionLessThanOrEqual;
	}

	public void setEvections(List<Integer> evections) {
		this.evections = evections;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public void setFaxLike(String faxLike) {
		this.faxLike = faxLike;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public void setGenderGreaterThanOrEqual(Integer genderGreaterThanOrEqual) {
		this.genderGreaterThanOrEqual = genderGreaterThanOrEqual;
	}

	public void setGenderLessThanOrEqual(Integer genderLessThanOrEqual) {
		this.genderLessThanOrEqual = genderLessThanOrEqual;
	}

	public void setHeadship(String headship) {
		this.headship = headship;
	}

	public void setHeadshipLike(String headshipLike) {
		this.headshipLike = headshipLike;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	public void setLastLoginIPLike(String lastLoginIPLike) {
		this.lastLoginIPLike = lastLoginIPLike;
	}

	public void setLastLoginIPs(List<String> lastLoginIPs) {
		this.lastLoginIPs = lastLoginIPs;
	}

	public void setLastLoginTimeGreaterThanOrEqual(
			Date lastLoginTimeGreaterThanOrEqual) {
		this.lastLoginTimeGreaterThanOrEqual = lastLoginTimeGreaterThanOrEqual;
	}

	public void setLastLoginTimeLessThanOrEqual(
			Date lastLoginTimeLessThanOrEqual) {
		this.lastLoginTimeLessThanOrEqual = lastLoginTimeLessThanOrEqual;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setMobileLike(String mobileLike) {
		this.mobileLike = mobileLike;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setSuperiorIdsLike(String superiorIdsLike) {
		this.superiorIdsLike = superiorIdsLike;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setTelephoneLike(String telephoneLike) {
		this.telephoneLike = telephoneLike;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public void setUserTypeGreaterThanOrEqual(Integer userTypeGreaterThanOrEqual) {
		this.userTypeGreaterThanOrEqual = userTypeGreaterThanOrEqual;
	}

	public void setUserTypeLessThanOrEqual(Integer userTypeLessThanOrEqual) {
		this.userTypeLessThanOrEqual = userTypeLessThanOrEqual;
	}

	public void setUserTypes(List<Integer> userTypes) {
		this.userTypes = userTypes;
	}

	public SysUserQuery superiorIdsLike(String superiorIdsLike) {
		if (superiorIdsLike == null) {
			throw new RuntimeException("superiorIds is null");
		}
		this.superiorIdsLike = superiorIdsLike;
		return this;
	}

	public SysUserQuery telephone(String telephone) {
		if (telephone == null) {
			throw new RuntimeException("telephone is null");
		}
		this.telephone = telephone;
		return this;
	}

	public SysUserQuery telephoneLike(String telephoneLike) {
		if (telephoneLike == null) {
			throw new RuntimeException("telephone is null");
		}
		this.telephoneLike = telephoneLike;
		return this;
	}

	public SysUserQuery userType(Integer userType) {
		if (userType == null) {
			throw new RuntimeException("userType is null");
		}
		this.userType = userType;
		return this;
	}

	public SysUserQuery userTypeGreaterThanOrEqual(
			Integer userTypeGreaterThanOrEqual) {
		if (userTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("userType is null");
		}
		this.userTypeGreaterThanOrEqual = userTypeGreaterThanOrEqual;
		return this;
	}

	public SysUserQuery userTypeLessThanOrEqual(Integer userTypeLessThanOrEqual) {
		if (userTypeLessThanOrEqual == null) {
			throw new RuntimeException("userType is null");
		}
		this.userTypeLessThanOrEqual = userTypeLessThanOrEqual;
		return this;
	}

	public SysUserQuery userTypes(List<Integer> userTypes) {
		if (userTypes == null) {
			throw new RuntimeException("userTypes is empty ");
		}
		this.userTypes = userTypes;
		return this;
	}

}