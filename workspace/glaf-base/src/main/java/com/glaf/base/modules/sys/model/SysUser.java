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

package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.identity.User;
import com.glaf.core.util.DateUtils;

public class SysUser implements Serializable, User {
	private static final long serialVersionUID = -7677600372139823989L;
	private long id;
	private SysDepartment department;
	private String account;
	private String password;
	private String code;
	private String name;
	private int blocked;
	private Date createTime;
	private Date lastLoginTime;
	private String lastLoginIP;
	private int evection;
	private String mobile;
	private String email;
	private String telephone;
	private int gender;
	private String headship;
	private int userType;
	private String fax;
	private int accountType;
	private String loginIP;
	private int dumpFlag;
	private String adminFlag;
	private String menus;
	private String superiorIds;
	private int deptId;
	private Set<SysUserRole> userRoles = new HashSet<SysUserRole>();
	private Set<SysDeptRole> roles = new HashSet<SysDeptRole>();
	private Set<SysFunction> functions = new HashSet<SysFunction>();
	private Set<SysApplication> apps = new HashSet<SysApplication>();
	private List<SysDepartment> nestingDepartment;

	public SysUser() {

	}

	public String getAccount() {
		return account;
	}

	public int getAccountType() {
		return accountType;
	}

	public String getActorId() {
		return account;
	}

	public String getAdminFlag() {
		return adminFlag;
	}

	public Set<SysApplication> getApps() {
		return apps;
	}

	public int getBlocked() {
		return blocked;
	}

	public String getCode() {
		return code;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public SysDepartment getDepartment() {
		return department;
	}

	public int getDeptId() {
		if (department != null) {
			return (int) department.getId();
		}
		return deptId;
	}

	public int getDumpFlag() {
		return dumpFlag;
	}

	public String getEmail() {
		return email;
	}

	public int getEvection() {
		return evection;
	}

	public String getFax() {
		return fax;
	}

	public Set<SysFunction> getFunctions() {
		return functions;
	}

	public int getGender() {
		return gender;
	}

	public String getHeadship() {
		return headship;
	}

	public long getId() {
		return id;
	}

	public Date getLastLoginDate() {
		return lastLoginTime;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public int getLocked() {
		return blocked;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public int getLoginRetry() {
		return 0;
	}

	public String getMail() {
		return email;
	}

	public String getMenus() {
		return menus;
	}

	public String getMobile() {
		return mobile;
	}

	public String getName() {
		return name;
	}

	public List<SysDepartment> getNestingDepartment() {
		return nestingDepartment;
	}

	public String getPassword() {
		return password;
	}

	public String getRemark() {
		return null;
	}

	public Set<SysDeptRole> getRoles() {
		return roles;
	}

	public String getSuperiorIds() {
		return superiorIds;
	}

	public String getTelephone() {
		return telephone;
	}

	public Set<SysUserRole> getUserRoles() {
		return userRoles;
	}

	public int getUserType() {
		return userType;
	}

	public boolean isDepartmentAdmin() {
		boolean isDeptAdmin = false;

		if (roles != null && !roles.isEmpty()) {
			for (SysDeptRole r : roles) {
				if (r.getRole() != null && "R006".equals(r.getRole().getCode())) {
					isDeptAdmin = true;
					break;
				}
			}
		}

		if (!isDeptAdmin) {
			if (userRoles != null && !userRoles.isEmpty()) {
				for (SysUserRole r : userRoles) {
					if (r.getDeptRole() != null
							&& r.getDeptRole().getRole() != null) {
						if ("R006".equals(r.getDeptRole().getRole().getCode())) {
							isDeptAdmin = true;
							break;
						}
					}
				}
			}
		}
		return isDeptAdmin;
	}

	public boolean isSystemAdmin() {
		return isSystemAdministrator();
	}

	public boolean isSystemAdministrator() {
		boolean isAdmin = false;

		if (StringUtils.equals(adminFlag, "1")) {
			isAdmin = true;
			return isAdmin;
		}

		if (roles != null && !roles.isEmpty()) {
			for (SysDeptRole r : roles) {
				if (r.getRole() != null && "R015".equals(r.getRole().getCode())) {
					isAdmin = true;
					break;
				}
			}
		}

		if (!isAdmin) {
			if (userRoles != null && !userRoles.isEmpty()) {
				for (SysUserRole r : userRoles) {
					if (r.getDeptRole() != null
							&& r.getDeptRole().getRole() != null) {
						if ("R015".equals(r.getDeptRole().getRole().getCode())) {
							isAdmin = true;
							break;
						}
					}
				}
			}
		}
		return isAdmin;
	}

	@Override
	public User jsonToObject(JSONObject jsonObject) {
		SysUser model = new SysUser();
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

		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}

		if (jsonObject.containsKey("evection")) {
			model.setEvection(jsonObject.getIntValue("evection"));
		}

		if (jsonObject.containsKey("gender")) {
			model.setGender(jsonObject.getIntValue("gender"));
		}

		if (jsonObject.containsKey("userType")) {
			model.setUserType(jsonObject.getIntValue("userType"));
		}

		if (jsonObject.containsKey("accountType")) {
			model.setAccountType(jsonObject.getIntValue("accountType"));
		}

		if (jsonObject.containsKey("dumpFlag")) {
			model.setDumpFlag(jsonObject.getIntValue("dumpFlag"));
		}

		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}

		if (jsonObject.containsKey("lastLoginTime")) {
			model.setLastLoginTime(jsonObject.getDate("lastLoginTime"));
		}

		if (jsonObject.containsKey("lastLoginIP")) {
			model.setLastLoginIP(jsonObject.getString("lastLoginIP"));
		}

		if (jsonObject.containsKey("remark")) {
			model.setRemark(jsonObject.getString("remark"));
		}

		if (jsonObject.containsKey("deptId")) {
			model.setDeptId(jsonObject.getInteger("deptId"));
		}

		if (jsonObject.containsKey("mail")) {
			model.setMail(jsonObject.getString("mail"));
		}

		if (jsonObject.containsKey("mobile")) {
			model.setMobile(jsonObject.getString("mobile"));
		}

		if (jsonObject.containsKey("telephone")) {
			model.setTelephone(jsonObject.getString("telephone"));
		}

		if (jsonObject.containsKey("headship")) {
			model.setHeadship(jsonObject.getString("headship"));
		}

		if (jsonObject.containsKey("superiorIds")) {
			model.setSuperiorIds(jsonObject.getString("superiorIds"));
		}

		if (jsonObject.containsKey("blocked")) {
			model.setBlocked(jsonObject.getIntValue("blocked"));
		}

		if (jsonObject.containsKey("adminFlag")) {
			model.setAdminFlag(jsonObject.getString("adminFlag"));
		}

		return model;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public void setActorId(String actorId) {
		this.account = actorId;
	}

	public void setAdminFlag(String adminFlag) {
		this.adminFlag = adminFlag;
	}

	public void setApps(Set<SysApplication> apps) {
		this.apps = apps;
	}

	public void setBlocked(int blocked) {
		this.blocked = blocked;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCreateDate(Date createDate) {
		this.createTime = createDate;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDepartment(SysDepartment department) {
		this.department = department;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public void setDumpFlag(int dumpFlag) {
		this.dumpFlag = dumpFlag;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEvection(int evection) {
		this.evection = evection;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public void setFunctions(Set<SysFunction> functions) {
		this.functions = functions;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public void setHeadship(String headship) {
		this.headship = headship;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginTime = lastLoginDate;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public void setLocked(int locked) {
		this.blocked = locked;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public void setLoginRetry(int loginRetry) {

	}

	public void setMail(String mail) {
		this.email = mail;
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNestingDepartment(List<SysDepartment> nestingDepartment) {
		this.nestingDepartment = nestingDepartment;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRemark(String remark) {

	}

	public void setRoles(Set<SysDeptRole> roles) {
		this.roles = roles;
	}

	public void setSuperiorIds(String superiorIds) {
		this.superiorIds = superiorIds;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setUserRoles(Set<SysUserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("actorId", account);
		jsonObject.put("name", name);
		jsonObject.put("locked", blocked);
		if (department != null) {
			jsonObject.put("deptId", department.getId());
		}

		jsonObject.put("accountType", accountType);
		jsonObject.put("userType", userType);
		jsonObject.put("dumpFlag", dumpFlag);
		jsonObject.put("gender", gender);
		jsonObject.put("evection", evection);
		jsonObject.put("superiorIds", superiorIds);

		jsonObject.put("fax", fax);
		jsonObject.put("telephone", telephone);
		jsonObject.put("headship", headship);
		jsonObject.put("adminFlag", adminFlag);

		if (email != null) {
			jsonObject.put("mail", email);
			jsonObject.put("email", email);
		}
		if (mobile != null) {
			jsonObject.put("mobile", mobile);
		}
		if (lastLoginTime != null) {
			jsonObject.put("lastLoginDate", lastLoginTime);
			jsonObject.put("lastLoginTime", lastLoginTime);
		}
		if (loginIP != null) {
			jsonObject.put("loginIP", loginIP);
		}

		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		jsonObject.put("actorId", account);
		jsonObject.put("name", name);
		jsonObject.put("locked", blocked);
		if (department != null) {
			jsonObject.put("deptId", department.getId());
		}

		jsonObject.put("accountType", accountType);
		jsonObject.put("userType", userType);
		jsonObject.put("dumpFlag", dumpFlag);
		jsonObject.put("gender", gender);
		jsonObject.put("evection", evection);
		jsonObject.put("superiorIds", superiorIds);

		jsonObject.put("fax", fax);
		jsonObject.put("telephone", telephone);
		jsonObject.put("headship", headship);
		jsonObject.put("adminFlag", adminFlag);

		if (email != null) {
			jsonObject.put("mail", email);
			jsonObject.put("email", email);
		}
		if (mobile != null) {
			jsonObject.put("mobile", mobile);
		}
		if (lastLoginTime != null) {
			jsonObject.put("lastLoginDate",
					DateUtils.getDateTime(lastLoginTime));
			jsonObject.put("lastLoginTime",
					DateUtils.getDateTime(lastLoginTime));
		}
		if (loginIP != null) {
			jsonObject.put("loginIP", loginIP);
		}

		return jsonObject;
	}

}