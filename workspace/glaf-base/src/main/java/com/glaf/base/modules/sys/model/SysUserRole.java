package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.Date;

public class SysUserRole implements Serializable {
	private static final long serialVersionUID = 4335486314285694158L;
	private long id;
	private SysUser user;
	private long userId;
	private SysDeptRole deptRole;
	private long deptRoleId;
	private int authorized;
	private SysUser authorizeFrom;
	private Date availDateStart;
	private Date availDateEnd;
	private String processDescription;

	public SysUserRole() {

	}

	public int getAuthorized() {
		return authorized;
	}

	public SysUser getAuthorizeFrom() {
		return authorizeFrom;
	}

	public Date getAvailDateEnd() {
		return availDateEnd;
	}

	public Date getAvailDateStart() {
		return availDateStart;
	}

	public SysDeptRole getDeptRole() {
		return deptRole;
	}

	public long getDeptRoleId() {
		return deptRoleId;
	}

	public long getId() {
		return id;
	}

	public String getProcessDescription() {
		return processDescription;
	}

	public SysUser getUser() {
		return user;
	}

	public long getUserId() {
		return userId;
	}

	public void setAuthorized(int authorized) {
		this.authorized = authorized;
	}

	public void setAuthorizeFrom(SysUser authorizeFrom) {
		this.authorizeFrom = authorizeFrom;
	}

	public void setAvailDateEnd(Date availDateEnd) {
		this.availDateEnd = availDateEnd;
	}

	public void setAvailDateStart(Date availDateStart) {
		this.availDateStart = availDateStart;
	}

	public void setDeptRole(SysDeptRole deptRole) {
		this.deptRole = deptRole;
	}

	public void setDeptRoleId(long deptRoleId) {
		this.deptRoleId = deptRoleId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
