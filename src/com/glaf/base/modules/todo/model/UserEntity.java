package com.glaf.base.modules.todo.model;

public class UserEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String actorId;

	private long deptId;

	private long roleId;

	public UserEntity() {

	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public long getDeptId() {
		return deptId;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

}
