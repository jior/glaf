package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.Date;

public class SysUserRole implements Serializable {
	private static final long serialVersionUID = 4335486314285694158L;
	private long id;
	private SysUser user;
	private SysDeptRole deptRole;
	private int authorized;
	private SysUser authorizeFrom;
	private Date availDateStart;
	private Date availDateEnd;
	private String processDescription;

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

	public long getId() {
		return id;
	}

	public String getProcessDescription() {
		return processDescription;
	}

	public SysUser getUser() {
		return user;
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

	public void setId(long id) {
		this.id = id;
	}

	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

}
