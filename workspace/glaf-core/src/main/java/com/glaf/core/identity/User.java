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

import com.alibaba.fastjson.JSONObject;

public interface User extends java.io.Serializable {

	int getAccountType();

	String getActorId();

	String getAdminFlag();

	long getDeptId();

	String getFax();

	long getId();

	Date getLastLoginDate();

	int getLocked();

	String getLoginIP();

	int getLoginRetry();

	String getMail();

	String getMobile();

	String getName();

	String getPassword();

	String getRemark();

	String getToken();

	int getUserType();

	boolean isSystemAdministrator();

	User jsonToObject(JSONObject json);

	void setAccountType(int accountType);

	void setActorId(String actorId);

	void setAdminFlag(String adminFlag);

	void setCreateDate(Date createDate);

	void setDeptId(long deptId);

	void setId(long id);

	void setLastLoginDate(Date lastLoginDate);

	void setLocked(int locked);

	void setLoginIP(String loginIP);

	void setLoginRetry(int loginRetry);

	void setMail(String mail);

	void setMobile(String mobile);

	void setName(String name);

	void setPassword(String password);

	void setRemark(String remark);

	void setToken(String token);

	void setUserType(int userType);

	JSONObject toJsonObject();

}