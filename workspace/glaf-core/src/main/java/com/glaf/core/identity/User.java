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

package com.glaf.core.identity;

import java.util.Date;

public interface User extends java.io.Serializable {

	int getAccountType();

	String getActivationCode();

	String getActorId();

	String getComputerId();

	int getDeptId();

	int getDomainIndex();

	int getDumpflag();

	String getFax();

	String getIsBind();

	String getIsSystem();

	Date getLastLoginDate();

	int getLocked();

	String getLoginIP();

	int getLoginRetry();

	String getMail();

	String getMobile();

	String getName();

	String getPassword();

	String getRemark();

	String getStatus();

	String getSuperiorId();

	int getUserType();

	void setAccountType(int accountType);

	void setActivationCode(String activationCode);

	void setActorId(String actorId);

	void setComputerId(String computerId);

	void setCreateDate(Date createDate);

	void setDeptId(int deptId);

	void setDomainIndex(int domainIndex);

	void setDumpflag(int dumpflag);

	void setFax(String fax);

	void setIsBind(String isBind);

	void setIsSystem(String isSystem);

	void setLastLoginDate(Date lastLoginDate);

	void setLocked(int locked);

	void setLoginIP(String loginIP);

	void setLoginRetry(int loginRetry);

	void setMail(String mail);

	void setMobile(String mobile);

	void setName(String name);

	void setPassword(String password);

	void setRemark(String remark);

	void setStatus(String status);

	void setSuperiorId(String superiorId);

	void setUserType(int userType);

}