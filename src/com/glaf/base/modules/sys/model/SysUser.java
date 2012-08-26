package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

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
	private String fax;
	private int accountType;
	private String loginIP;
	private int dumpFlag;
	private String adminFlag;
	private String menus;
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

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public String getLoginIP() {
		return loginIP;
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

	public Set<SysDeptRole> getRoles() {
		return roles;
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

	public void setAccount(String account) {
		this.account = account;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
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

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDepartment(SysDepartment department) {
		this.department = department;
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

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
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

	public void setRoles(Set<SysDeptRole> roles) {
		this.roles = roles;
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

}
