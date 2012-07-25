package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SysUser implements Serializable {		

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
	private Set userRoles = new HashSet();
	private Set roles = new HashSet();
	private Set functions = new HashSet();
	private Set apps = new HashSet();
	private String fax;
	private int accountType;
	private List nestingDepartment;
	private String loginIP;
	private int dumpFlag;
	
	public Set getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(Set userRoles) {
		this.userRoles = userRoles;
	}
	public String getLoginIP() {
		return loginIP;
	}
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}
	public void setApps(Set apps) {
		this.apps = apps;
	}
	public int getBlocked() {
		return blocked;
	}
	public void setBlocked(int blocked) {
		this.blocked = blocked;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public SysDepartment getDepartment() {
		return department;
	}
	public void setDepartment(SysDepartment department) {
		this.department = department;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	public Set getRoles() {
		return roles;
	}
	public void setRoles(Set roles) {
		this.roles = roles;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Set getFunctions() {
		return functions;
	}
	public void setFunctions(Set functions) {
		this.functions = functions;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getEvection() {
		return evection;
	}
	public void setEvection(int evection) {
		this.evection = evection;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Set getApps() {
		return apps;
	}
	public String getHeadship() {
		return headship;
	}
	public void setHeadship(String headship) {
		this.headship = headship;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public List getNestingDepartment() {
		return nestingDepartment;
	}
	public void setNestingDepartment(List nestingDepartment) {
		this.nestingDepartment = nestingDepartment;
	}
	public int getDumpFlag() {
		return dumpFlag;
	}
	public void setDumpFlag(int dumpFlag) {
		this.dumpFlag = dumpFlag;
	}
	public String getLastLoginIP() {
		return lastLoginIP;
	}
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}
	
	
}
