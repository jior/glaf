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

package com.glaf.core.identity.impl;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.identity.User;
import com.glaf.core.util.DateUtils;

public class UserImpl implements User {

	private static final long serialVersionUID = 1L;

	protected int accountType;

	protected String activationCode;

	protected String actorId;

	protected String adminFlag;

	protected String computerId;

	protected Date createDate;

	protected int deptId;

	protected int domainIndex;

	protected int dumpflag;

	protected String fax;

	protected long id;

	protected String isBind;

	protected Date lastLoginDate;

	protected int locked;

	protected String loginIP;

	protected int loginRetry;

	protected String mail;

	protected String mailPwd;

	protected String mailUser;

	protected String mobile;

	protected String name;

	protected String password;

	protected String phoneNumber;

	protected String principalshipCode;

	protected String remark;

	protected Integer remoteAttr;

	protected String status;

	protected String superiorId;

	protected int userType;

	public UserImpl() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserImpl other = (UserImpl) obj;
		if (actorId == null) {
			if (other.actorId != null)
				return false;
		} else if (!actorId.equals(other.actorId))
			return false;
		return true;
	}

	public int getAccountType() {
		return accountType;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public String getActorId() {
		return actorId;
	}

	public String getAdminFlag() {
		return adminFlag;
	}

	public String getComputerId() {
		return computerId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public int getDeptId() {
		return deptId;
	}

	public int getDomainIndex() {
		return domainIndex;
	}

	public int getDumpflag() {
		return dumpflag;
	}

	public String getFax() {
		return fax;
	}

	public long getId() {
		return id;
	}

	public String getIsBind() {
		return isBind;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public int getLocked() {
		return locked;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public int getLoginRetry() {
		return loginRetry;
	}

	public String getMail() {
		return mail;
	}

	public String getMailPwd() {
		return mailPwd;
	}

	public String getMailUser() {
		return mailUser;
	}

	public String getMobile() {
		return mobile;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getPrincipalshipCode() {
		return principalshipCode;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getRemoteAttr() {
		return remoteAttr;
	}

	public String getStatus() {
		return status;
	}

	public String getSuperiorId() {
		return superiorId;
	}

	public int getUserType() {
		return userType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actorId == null) ? 0 : actorId.hashCode());
		return result;
	}

	public boolean isSystemAdministrator() {
		if ("1".equals(adminFlag)) {
			return true;
		}
		return false;
	}

	public User jsonToObject(JSONObject jsonObject) {
		UserImpl model = new UserImpl();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getIntValue("id"));
		}
		if (jsonObject.containsKey("actorId")) {
			model.setActorId(jsonObject.getString("actorId"));
		}

		if (jsonObject.containsKey("account")) {
			model.setActorId(jsonObject.getString("account"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}

		if (jsonObject.containsKey("userType")) {
			model.setUserType(jsonObject.getIntValue("userType"));
		}

		if (jsonObject.containsKey("accountType")) {
			model.setAccountType(jsonObject.getIntValue("accountType"));
		}

		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}

		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getString("status"));
		}

		if (jsonObject.containsKey("adminFlag")) {
			model.setAdminFlag(jsonObject.getString("adminFlag"));
		}

		if (jsonObject.containsKey("remark")) {
			model.setRemark(jsonObject.getString("remark"));
		}

		if (jsonObject.containsKey("deptId")) {
			model.setDeptId(jsonObject.getInteger("deptId"));
		}
		if (jsonObject.containsKey("isBind")) {
			model.setIsBind(jsonObject.getString("isBind"));
		}
		if (jsonObject.containsKey("computerId")) {
			model.setComputerId(jsonObject.getString("computerId"));
		}
		if (jsonObject.containsKey("mail")) {
			model.setMail(jsonObject.getString("mail"));
		}

		if (jsonObject.containsKey("mobile")) {
			model.setMobile(jsonObject.getString("mobile"));
		}

		if (jsonObject.containsKey("domainIndex")) {
			model.setDomainIndex(jsonObject.getInteger("domainIndex"));
		}

		return model;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setAdminFlag(String adminFlag) {
		this.adminFlag = adminFlag;
	}

	public void setComputerId(String computerId) {
		this.computerId = computerId;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public void setDomainIndex(int domainIndex) {
		this.domainIndex = domainIndex;
	}

	public void setDumpflag(int dumpflag) {
		this.dumpflag = dumpflag;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIsBind(String isBind) {
		this.isBind = isBind;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public void setLoginRetry(int loginRetry) {
		this.loginRetry = loginRetry;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setMailPwd(String mailPwd) {
		this.mailPwd = mailPwd;
	}

	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPrincipalshipCode(String principalshipCode) {
		this.principalshipCode = principalshipCode;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRemoteAttr(Integer remoteAttr) {
		this.remoteAttr = remoteAttr;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("actorId", actorId);
		jsonObject.put("name", name);
		jsonObject.put("locked", locked);
		jsonObject.put("deptId", deptId);
		jsonObject.put("adminFlag", adminFlag);

		jsonObject.put("accountType", accountType);
		jsonObject.put("userType", userType);
		jsonObject.put("loginRetry", loginRetry);
		jsonObject.put("fax", fax);
		jsonObject.put("dumpflag", dumpflag);
		jsonObject.put("isBind", isBind);
		jsonObject.put("computerId", computerId);
		jsonObject.put("remoteAttr", remoteAttr);
		jsonObject.put("status", status);
		jsonObject.put("phoneNumber", phoneNumber);
		jsonObject.put("superiorId", superiorId);
		jsonObject.put("principalshipCode", principalshipCode);
		jsonObject.put("remark", remark);
		jsonObject.put("domainIndex", domainIndex);

		if (mail != null) {
			jsonObject.put("mail", mail);
		}
		if (mobile != null) {
			jsonObject.put("mobile", mobile);
		}
		if (lastLoginDate != null) {
			jsonObject.put("lastLoginDate", lastLoginDate);
		}
		if (loginIP != null) {
			jsonObject.put("loginIP", loginIP);
		}

		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		jsonObject.put("actorId", actorId);
		jsonObject.put("name", name);
		jsonObject.put("locked", locked);
		jsonObject.put("deptId", deptId);
		jsonObject.put("adminFlag", adminFlag);
		jsonObject.put("accountType", accountType);
		jsonObject.put("userType", userType);
		jsonObject.put("loginRetry", loginRetry);
		jsonObject.put("fax", fax);
		jsonObject.put("dumpflag", dumpflag);
		jsonObject.put("isBind", isBind);
		jsonObject.put("computerId", computerId);
		jsonObject.put("remoteAttr", remoteAttr);
		jsonObject.put("status", status);
		jsonObject.put("phoneNumber", phoneNumber);
		jsonObject.put("superiorId", superiorId);
		jsonObject.put("remark", remark);
		jsonObject.put("domainIndex", domainIndex);

		if (mail != null) {
			jsonObject.put("mail", mail);
		}
		if (mobile != null) {
			jsonObject.put("mobile", mobile);
		}
		if (lastLoginDate != null) {
			jsonObject.put("lastLoginDate",
					DateUtils.getDateTime(lastLoginDate));
		}
		if (loginIP != null) {
			jsonObject.put("loginIP", loginIP);
		}

		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}